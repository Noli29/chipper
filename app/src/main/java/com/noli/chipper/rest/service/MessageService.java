package com.noli.chipper.rest.service;

import com.noli.chipper.rest.models.MessageResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MessageService {

    @GET("/api/messages/{number}")
    void getMessage(@Path("number") int number,
                    Callback<MessageResponse> callback);

}
