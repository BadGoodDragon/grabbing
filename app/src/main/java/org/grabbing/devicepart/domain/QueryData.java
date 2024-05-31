package org.grabbing.devicepart.domain;

import java.util.HashMap;
import java.util.Map;

public class QueryData {
    private String url;
    private String addedUrl;
    private int id;

    private Map<String, String> parameters;
    private Map<String, String> queryHeaders;
    private String queryBody;

    private Map<String, String> authorizationHeaders;
    private Map<String, String> registrationHeaders;


    private int statusCode;
    private Map<String, String> responseHeaders;
    private String responseBody;

    private boolean hasResponse;
    private boolean error;

    public QueryData(String url, int id) {
        this.url = url;
        addedUrl = "";
        this.id = id;

        parameters = new HashMap<>();
        queryHeaders = new HashMap<>();
        queryBody = "";

        authorizationHeaders = new HashMap<>();
        registrationHeaders = new HashMap<>();

        statusCode = -1;
        responseHeaders = new HashMap<>();
        responseBody = "";

        hasResponse = false;
        error = false;
    }

    public QueryData(QueryData queryData) {
        this.url = queryData.url;
        this.addedUrl = queryData.addedUrl;
        this.id = queryData.id;
        this.parameters = queryData.parameters;
        this.queryHeaders = queryData.queryHeaders;
        this.queryBody = queryData.queryBody;
        this.authorizationHeaders = queryData.authorizationHeaders;
        this.registrationHeaders = queryData.registrationHeaders;
        this.statusCode = queryData.statusCode;
        this.responseHeaders = queryData.responseHeaders;
        this.responseBody = queryData.responseBody;
        this.hasResponse = queryData.hasResponse;
        this.error = queryData.error;
    }

    public void setUrl(String url) { this.url = url; }
    public void setAddedUrl(String addedUrl) {this.addedUrl = addedUrl;}
    public void setId(int id) { this.id = id; }
    public void setParameters(Map<String, String> parameters) {this.parameters = parameters;}
    public void setQueryHeaders(Map<String, String> queryHeaders) {this.queryHeaders = queryHeaders;}
    public void setQueryBody(String queryBody) {this.queryBody = queryBody;}
    public void setStatusCode(int statusCode) {this.statusCode = statusCode;}
    public void setResponseHeaders(Map<String, String> responseHeaders) {this.responseHeaders = responseHeaders;}
    public void setResponseBody(String responseBody) {this.responseBody = responseBody;}
    public void setHasResponse(boolean hasResponse) {this.hasResponse = hasResponse;}
    public void setError(boolean error) {this.error = error;}
    public void setAuthorizationHeaders(Map<String, String> authorizationHeaders) {this.authorizationHeaders = authorizationHeaders;}
    public void setRegistrationHeaders(Map<String, String> registrationHeaders) {this.registrationHeaders = registrationHeaders;}

    public String getUrl() {
        return url + addedUrl;
    }
    public int getId() {return id;}
    public Map<String, String> getParameters() {return parameters;}
    public Map<String, String> getQueryHeaders() {
        Map<String, String> result = new HashMap<>();
        result.putAll(queryHeaders);
        result.putAll(authorizationHeaders);
        result.putAll(registrationHeaders);
        return result;
    }
    public String getQueryBody() {return queryBody;}
    public int getStatusCode() {return statusCode;}
    public Map<String, String> getResponseHeaders() {return responseHeaders;}
    public String getResponseBody() {return responseBody;}
    public boolean isHasResponse() {return hasResponse;}
    public boolean isError() {return error;}
    public Map<String, String> getAuthorizationHeaders() {return authorizationHeaders;}

    @Override
    public String toString() {
        return "QueryData{" +
                "url='" + url + '\'' +
                ", id=" + id +
                '}';
    }
}
