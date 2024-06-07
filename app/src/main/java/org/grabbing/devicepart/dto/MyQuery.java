package org.grabbing.devicepart.dto;

public class MyQuery {
    private String url;
    private long id;

    public MyQuery(String url, long id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {return url;}
    public long getId() {return id;}

    public void setUrl(String url) {this.url = url;}
    public void setId(long id) {this.id = id;}
}
