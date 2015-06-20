package com.noli.chipper.rest.service;

import com.noli.chipper.rest.models.PostResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by noli on 16/06/15.
 */
public interface PostService {

    @GET("/api/posts/{number}")
    void getPost(@Path("number") int number,
                 Callback<PostResponse> callback);

//    @PUT("/api/posts/new")
//    void createPost(@PUT() Post post, Callback<Post> cb);
}
