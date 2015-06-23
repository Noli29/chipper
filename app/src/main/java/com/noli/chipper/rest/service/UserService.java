package com.noli.chipper.rest.service;

import com.noli.chipper.rest.models.UserResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserService {

    @POST("/api/sessions")
    void doLogin(@Query("email") String email, @Query("password") String pass, Callback<UserResponse> callback);

    @GET("/api/users/{number}")
    void getUser(@Path("number") int number, Callback<UserResponse> callback);

    @GET("/api/users")
    void getUsersList(Callback<List<UserResponse>> callback);

    @PUT("/api/users/{number}")
    void updateInfo(@Path("number") int userId,
                    @Query("user[name]") String name,
                    @Query("user[email]") String email,
                    @Query("user[surname]") String surname,
                    @Query("user[password]") String pass,
                    @Query("user[password_confirmation]") String passConfirm,
                    Callback<UserResponse> callback);

    @POST("/api/users/")
    void registerUser(@Query("user[name]") String name,
                      @Query("user[email]") String email,
                      @Query("user[surname]") String surname,
                      @Query("user[password]") String pass,
                      @Query("user[password_confirmation]") String passConfirm,
                      Callback<UserResponse> callback);

}
