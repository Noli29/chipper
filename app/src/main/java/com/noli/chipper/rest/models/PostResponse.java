package com.noli.chipper.rest.models;

import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("post")
    public String post;

    @SerializedName("user_id")
    public int userId;

    public PostResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "id=" + id +
                ", post='" + post + '\'' +
                ", userId=" + userId +
                '}';
    }

}
