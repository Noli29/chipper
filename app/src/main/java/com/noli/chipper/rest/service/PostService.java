package com.noli.chipper.rest.service;

import com.noli.chipper.rest.models.PostResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PostService {

    @GET("/api/posts/{number}")
    void getPost(@Path("number") int number, Callback<PostResponse> callback);

    @GET("/api/posts")
    void getAllPosts(Callback<List<PostResponse>> callback);

    @POST("/api/posts")
    void doPost(@Query("post[post") String post,
                @Query("post[user_id") int userId, Callback<PostResponse> callback);

    @DELETE("/api/posts/{number}")
    void deletePost(@Path("number") int number, Callback<Response> callback);

}
