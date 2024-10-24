package com.aubank.apischeduler.model;

import java.util.Map;


public class ApiRequest {
    private String url;                  // The URL of the external API (Node.js or Python)
    private String method;               // GET, POST, PUT, DELETE
    private Map<String, String> headers; // Optional headers like Authorization
    private String body;                 // The body for POST/PUT requests
	public ApiRequest(String url, String method, Map<String, String> headers, String body) {
		super();
		this.url = url;
		this.method = method;
		this.headers = headers;
		this.body = body;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
      
    
}
