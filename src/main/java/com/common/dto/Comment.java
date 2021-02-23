package com.common.dto;

import java.util.Date;

public class Comment {
    private Integer commentid;

    private String email;

    private Integer articleid;

    private String comment;

    private Integer status;

    private Date commentdate;

    public Comment(Integer commentid, String email, Integer articleid, String comment, Integer status, Date commentdate) {
        this.commentid = commentid;
        this.email = email;
        this.articleid = articleid;
        this.comment = comment;
        this.status = status;
        this.commentdate = commentdate;
    }

    public Comment() {
        super();
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
    }
}