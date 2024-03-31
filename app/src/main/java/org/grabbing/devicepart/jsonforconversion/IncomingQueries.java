package org.grabbing.devicepart.jsonforconversion;

import java.util.Map;

public class IncomingQueries {
    private int quantity;
    private Map<String, Integer> id;
    private Map<String, String> url;
    private Map<String, Map<String, String>> parameters;
    private Map<String, Map<String, String>> headers;
    private Map<String, String> body;

    public IncomingQueries(int quantity, Map<String, Integer> id, Map<String, String> url, Map<String, Map<String, String>> parameters, Map<String, Map<String, String>> headers, Map<String, String> body) {
        this.quantity = quantity;
        this.id = id;
        this.url = url;
        this.parameters = parameters;
        this.headers = headers;
        this.body = body;
    }

    public int getQuantity() {return quantity;}
    public Map<String, Integer> getId() {return id;}
    public Map<String, String> getUrl() {return url;}
    public Map<String, Map<String, String>> getParameters() {return parameters;}
    public Map<String, Map<String, String>> getHeaders() {return headers;}
    public Map<String, String> getBody() {return body;}

    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setId(Map<String, Integer> id) {this.id = id;}
    public void setUrl(Map<String, String> url) {this.url = url;}
    public void setParameters(Map<String, Map<String, String>> parameters) {this.parameters = parameters;}
    public void setHeaders(Map<String, Map<String, String>> headers) {this.headers = headers;}
    public void setBody(Map<String, String> body) {this.body = body;}
}
