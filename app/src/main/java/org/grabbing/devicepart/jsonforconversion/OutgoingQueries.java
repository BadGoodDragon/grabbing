package org.grabbing.devicepart.jsonforconversion;

import java.util.Map;

public class OutgoingQueries {
    private int quantity;
    private Map<String, Integer> statusCode;
    private Map<String, Map<String, String>> responseHeaders;
    private Map<String, String> responseBody;
    private Map<String, Boolean> error;

    public OutgoingQueries() {}

    public OutgoingQueries(int quantity, Map<String, Integer> statusCode, Map<String, Map<String, String>> responseHeaders, Map<String, String> responseBody, Map<String, Boolean> error) {
        this.quantity = quantity;
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.error = error;
    }

    public int getQuantity() {return quantity;}
    public Map<String, Integer> getStatusCode() {return statusCode;}
    public Map<String, Map<String, String>> getResponseHeaders() {return responseHeaders;}
    public Map<String, String> getResponseBody() {return responseBody;}
    public Map<String, Boolean> getError() {return error;}

    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setStatusCode(Map<String, Integer> statusCode) {this.statusCode = statusCode;}
    public void setResponseHeaders(Map<String, Map<String, String>> responseHeaders) {this.responseHeaders = responseHeaders;}
    public void setResponseBody(Map<String, String> responseBody) {this.responseBody = responseBody;}
    public void setError(Map<String, Boolean> error) {this.error = error;}
}
