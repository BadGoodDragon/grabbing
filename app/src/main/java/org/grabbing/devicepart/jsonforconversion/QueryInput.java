package org.grabbing.devicepart.jsonforconversion;

import java.util.Map;

public class QueryInput {
    private int id;
    private String url;
    private Map<String, String> parameters;
    private Map<String, String> queryHeaders;
    private String queryBody;


    public QueryInput(int id, String url, Map<String, String> parameters, Map<String, String> queryHeaders, String queryBody) {
        this.id = id;
        this.url = url;
        this.parameters = parameters;
        this.queryHeaders = queryHeaders;
        this.queryBody = queryBody;
    }

    public void setId(int id) {this.id = id;}
    public void setUrl(String url) {this.url = url;}
    public void setParameters(Map<String, String> parameters) {this.parameters = parameters;}
    public void setQueryHeaders(Map<String, String> queryHeaders) {this.queryHeaders = queryHeaders;}
    public void setQueryBody(String queryBody) {this.queryBody = queryBody;}

    public int getId() {return id;}
    public String getUrl() {return url;}
    public Map<String, String> getParameters() {return parameters;}
    public Map<String, String> getQueryHeaders() {return queryHeaders;}
    public String getQueryBody() {return queryBody;}
}
