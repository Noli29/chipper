package com.noli.chipper.rest.service;

import com.noli.chipper.rest.models.UserResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by noli on 16/06/15.
 */
public interface UserService {

    @GET("/api/users/{number}")
    void getUser(@Path("number") int number,
                    Callback<UserResponse> callback);

    @GET("/api/users/")
    void userList(Callback<List<UserResponse>> cb);

}
