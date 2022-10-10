package com.performanceAppraisal.system.domain.vo;

public class FileVo {

    private String name;

    private Long uid;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FileVo()
    {
    }

    public FileVo(String name, Long uid, String url)
    {
        this.name = name;
        this.uid = uid;
        this.url = url;
    }
}
