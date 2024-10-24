package com.aubank.apischeduler.services;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aubank.apischeduler.model.ApiConfig;
import com.aubank.apischeduler.repository.ApiConfigRepository;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

@Service
public class SchedulerService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiConfigRepository apiConfigRepository;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    @Scheduled(cron = "0 0 * * * *")  // Check every hour
    public void scheduleAllApis() {
        List<ApiConfig> apiConfigs = apiConfigRepository.findAll();
        for (ApiConfig apiConfig : apiConfigs) {
            if (isCronExpression(apiConfig.getSchedule())) {
                // Use Spring for cron scheduling
                // scheduleWithCron(apiConfig); // No need to use executor for cron
            } else {
                scheduleWithFixedDelay(apiConfig);
            }
        }
    }

    // Method to immediately schedule an API
    public void scheduleApiImmediately(ApiConfig apiConfig) {
        if (isCronExpression(apiConfig.getSchedule())) {
        	 scheduleWithCron(apiConfig);
        } else {
            scheduleWithFixedDelay(apiConfig);
        }
    }

    private void scheduleWithFixedDelay(ApiConfig apiConfig) {
        long delayInSeconds = Long.parseLong(apiConfig.getSchedule());
        scheduler.scheduleWithFixedDelay(() -> {
            apiService.executeApi(apiConfig);
            updateExecutionTimes(apiConfig);
        }, delayInSeconds, delayInSeconds, TimeUnit.SECONDS);
    }

    // Calculate next execution time for fixed delay (in seconds)
    private LocalDateTime calculateNextFixedDelayExecutionTime(String delayInSeconds) {
        long delay = Long.parseLong(delayInSeconds);
        // Add the delay to the current time to get the next execution time
        return LocalDateTime.now().plus(delay, ChronoUnit.SECONDS);
    }

    // Calculate next execution time for cron expressions
    private LocalDateTime calculateNextCronExecutionTime(String cronExpression) {
        // Remove the "cron:" prefix if you are using a prefix to identify cron expressions
        cronExpression = cronExpression.replace("cron:", "");

        // Create a Cron parser for UNIX cron expressions (using cron-utils library)
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        Cron cron = parser.parse(cronExpression);
        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        // Calculate the next execution time based on the cron expression
        // This will return an Optional<ZonedDateTime>
        ZonedDateTime nextExecutionTime = executionTime.nextExecution(ZonedDateTime.now()).orElse(null);

        // If there is a next execution time, convert it to LocalDateTime
        if (nextExecutionTime != null) {
            return nextExecutionTime.toLocalDateTime();
        } else {
            return null;  // Handle the case where no next execution is found
        }
    }

    // Handle the execution time calculation
    private void updateExecutionTimes(ApiConfig apiConfig) {
        apiConfig.setLastExecutionTime(LocalDateTime.now());
        apiConfig.setNextExecutionTime(calculateNextExecutionTime(apiConfig));
        apiConfigRepository.save(apiConfig);
    }

    // Determine whether the schedule is a cron expression
    private boolean isCronExpression(String schedule) {
        return schedule.startsWith("cron:");
    }

    // Calculate the next execution time based on the schedule
    public LocalDateTime calculateNextExecutionTime(ApiConfig apiConfig) {
        String schedule = apiConfig.getSchedule();

        if (isCronExpression(schedule)) {
            return calculateNextCronExecutionTime(schedule);
        } else {
            return calculateNextFixedDelayExecutionTime(schedule);
        }
    }
    private void scheduleWithCron(ApiConfig apiConfig) {
        // Parse the cron expression
        String cronExpression = apiConfig.getSchedule().replace("cron:", "");  // Removing any 'cron:' prefix

        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        Cron cron = parser.parse(cronExpression);
        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        // Calculate the next execution time
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextExecutionTime = executionTime.nextExecution(now).orElse(null);

        if (nextExecutionTime != null) {
            // Calculate delay in milliseconds
            long delayInMillis = ChronoUnit.MILLIS.between(now, nextExecutionTime);

            // Schedule the task with the calculated delay
            scheduler.schedule(() -> {
                apiService.executeApi(apiConfig);  // Execute the API call
                updateExecutionTimes(apiConfig);  // Update the execution times in the database
            }, delayInMillis, TimeUnit.MILLISECONDS);
        } else {
            // Handle case where no next execution time is found
            System.out.println("No next execution time found for cron expression: " + cronExpression);
        }
    }

}
