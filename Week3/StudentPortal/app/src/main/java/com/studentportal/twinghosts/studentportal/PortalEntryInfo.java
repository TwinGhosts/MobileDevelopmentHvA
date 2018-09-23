package com.studentportal.twinghosts.studentportal;

public class PortalEntryInfo {

    private String url;
    private String title;

    public PortalEntryInfo(String url, String title){
        this.url = url;
        this.title = title;
    }

    public String getUrl(){
        return url;
    }

    public String getTitle(){
        return title;
    }
}
