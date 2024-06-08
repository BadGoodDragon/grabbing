package org.grabbing.devicepart.dto;

import java.util.Map;

public class QueryVisual {
    private String url;

    private Map<String, String> parameters;
    private Map<String, String> queryHeaders;
    private String queryBody;

    private int statusCode;
    private Map<String, String> responseHeaders;
    private String responseBody;

    private boolean hasResponse;
    private boolean error;

    public QueryVisual(String url, Map<String, String> parameters, Map<String, String> queryHeaders, String queryBody, int statusCode, Map<String, String> responseHeaders, String responseBody, boolean hasResponse, boolean error) {
        this.url = url;
        this.parameters = parameters;
        this.queryHeaders = queryHeaders;
        this.queryBody = queryBody;
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.hasResponse = hasResponse;
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getQueryHeaders() {
        return queryHeaders;
    }

    public String getQueryBody() {
        return queryBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public boolean isHasResponse() {
        return hasResponse;
    }

    public boolean isError() {
        return error;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setQueryHeaders(Map<String, String> queryHeaders) {
        this.queryHeaders = queryHeaders;
    }

    public void setQueryBody(String queryBody) {
        this.queryBody = queryBody;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setHasResponse(boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    private String getMap(Map<String, String> map) {
        String result = "";
        for (Map.Entry<String, String> entry: map.entrySet()) {
            result += "  ";
            result += entry.getKey();
            result += ": ";
            result += entry.getValue();
            result += "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "Url: " + url + "\n" +
                "Parameters: " + "\n" + getMap(parameters) +
                "Query Headers: " + "\n" + getMap(queryHeaders) +
                "Query Body: " + queryBody + "\n";

        if (!hasResponse) {
            result += "Response: No Response";
            return result;
        } else if (error) {
            result += "Response: Error";
            return result;
        }

        result += "Status Code: " + statusCode + "\n" +
                "Response Headers: " + "\n" + getMap(responseHeaders) +
                "Response Body: " + responseBody;

        return  result;
    }

    /*@Override
    public String toString() {
        return "QueryVisual{" +
                "url='" + url + '\'' +
                ", parameters=" + parameters +
                ", queryHeaders=" + queryHeaders +
                ", queryBody='" + queryBody + '\'' +
                ", statusCode=" + statusCode +
                ", responseHeaders=" + responseHeaders +
                ", responseBody='" + responseBody + '\'' +
                ", hasResponse=" + hasResponse +
                ", error=" + error +
                '}';
    }*/
}
