package com.common.dto;

import java.util.Date;

public class Article {
    private Integer articleid;

    private String title;

    private String description;

    private Date postdate;

    private Integer status;

    private String content;

    private String email;

    private String image;

    public Article(Integer articleid, String title, String description, Date postdate, Integer status, String content, String email, String image) {
        this.articleid = articleid;
        this.title = title;
        this.description = description;
        this.postdate = postdate;
        this.status = status;
        this.content = content;
        this.email = email;
        this.image = image;
    }

    public Article() {
        super();
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}