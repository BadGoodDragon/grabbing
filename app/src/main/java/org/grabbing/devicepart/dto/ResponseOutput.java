package org.grabbing.devicepart.dto;

import java.util.Map;

public class ResponseOutput {
    private long id;
    private int statusCode;
    private Map<String, String> responseHeaders;
    private String responseBody;
    private boolean error;


    public ResponseOutput(long id, int statusCode, Map<String, String> responseHeaders, String responseBody, boolean error) {
        this.id = id;
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.error = error;
    }

    public void setId(long id) {this.id = id;}
    public void setStatusCode(int statusCode) {this.statusCode = statusCode;}
    public void setResponseHeaders(Map<String, String> responseHeaders) {this.responseHeaders = responseHeaders;}
    public void setResponseBody(String responseBody) {this.responseBody = responseBody;}
    public void setError(boolean error) {this.error = error;}

    public long getId() {return id;}
    public int getStatusCode() {return statusCode;}
    public Map<String, String> getResponseHeaders() {return responseHeaders;}
    public String getResponseBody() {return responseBody;}
    public boolean isError() {return error;}
}
