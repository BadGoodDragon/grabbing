package org.grabbing.devicepart.dto;

import java.util.Map;

public class NewQuery {
    private String url;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private String body;
    private long hostId;

    public NewQuery(String url, Map<String, String> parameters, Map<String, String> headers, String body, long hostId) {
        this.url = url;
        this.parameters = parameters;
        this.headers = headers;
        this.body = body;
        this.hostId = hostId;
    }

    public String getUrl() {return url;}
    public Map<String, String> getParameters() {return parameters;}
    public Map<String, String> getHeaders() {return headers;}
    public String getBody() {return body;}
    public long getHostId() {return hostId;}

    public void setUrl(String url) {this.url = url;}
    public void setParameters(Map<String, String> parameters) {this.parameters = parameters;}
    public void setHeaders(Map<String, String> headers) {this.headers = headers;}
    public void setBody(String body) {this.body = body;}
    public void setHostId(long hostId) {this.hostId = hostId;}


    @Override
    public String toString() {
        return "NewQuery{" +
                "url='" + url + '\'' +
                ", parameters=" + parameters +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                ", hostId=" + hostId +
                '}';
    }
}
