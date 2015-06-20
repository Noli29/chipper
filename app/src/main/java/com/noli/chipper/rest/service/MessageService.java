package com.noli.chipper.rest.service;

import com.noli.chipper.rest.models.MessageResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by noli on 13.06.15.
 */
public interface MessageService {

    @GET("/api/messages/{number}")
    void getMessage(@Path("number") int number,
                    Callback<MessageResponse> callback);


}
