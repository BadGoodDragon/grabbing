package org.grabbing.devicepart.domain;

import java.util.HashMap;
import java.util.Map;

public class QueryData {
    private String url;
    private int id;

    private Map<String, String> parameters;
    private Map<String, String> queryHeaders;
    private String queryBody;

    private int statusCode;
    private Map<String, String> responseHeaders;
    private String responseBody;

    private boolean hasResponse;
    private boolean error;

    public QueryData(String url, int id) {
        this.url = url;
        this.id = id;

        parameters = new HashMap<>();
        queryHeaders = new HashMap<>();
        queryBody = "";

    }


    public void setUrl(String url) { this.url = url; }
    public void setId(int id) { this.id = id; }
    public void setParameters(Map<String, String> parameters) {this.parameters = parameters;}
    public void setQueryHeaders(Map<String, String> queryHeaders) {this.queryHeaders = queryHeaders;}
    public void setQueryBody(String queryBody) {this.queryBody = queryBody;}
    public void setStatusCode(int statusCode) {this.statusCode = statusCode;}
    public void setResponseHeaders(Map<String, String> responseHeaders) {this.responseHeaders = responseHeaders;}
    public void setResponseBody(String responseBody) {this.responseBody = responseBody;}
    public void setHasResponse(boolean hasResponse) {this.hasResponse = hasResponse;}
    public void setError(boolean error) {this.error = error;}

    public String getUrl() {return url;}
    public int getId() {return id;}
    public Map<String, String> getParameters() {return parameters;}
    public Map<String, String> getQueryHeaders() {return queryHeaders;}
    public String getQueryBody() {return queryBody;}
    public int getStatusCode() {return statusCode;}
    public Map<String, String> getResponseHeaders() {return responseHeaders;}
    public String getResponseBody() {return responseBody;}
    public boolean isHasResponse() {return hasResponse;}
    public boolean isError() {return error;}
}
