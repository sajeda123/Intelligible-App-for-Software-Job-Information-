package com.cse.cou.praptimoni.softwarefirmsbd.modal;

public class Comments {
    String id;
    String comment_details;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    String user_name;
public  Comments(){}
    public Comments(String id, String comment_details, String user_name) {
        this.id = id;
        this.comment_details = comment_details;
        this.user_name = user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment_details() {
        return comment_details;
    }

    public void setComment_details(String comment_details) {
        this.comment_details = comment_details;
    }
}
