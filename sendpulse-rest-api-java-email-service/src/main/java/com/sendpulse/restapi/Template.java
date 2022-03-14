package com.sendpulse.restapi;

public class Template {

    private String preview;
    private Integer realId;
    private String name;

    public Template() {
    }

    public Template(String preview, Integer realId, String name) {
        this.preview = preview;
        this.realId = realId;
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Integer getRealId() {
        return realId;
    }

    public void setRealId(Integer realId) {
        this.realId = realId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
