package com.noli.chipper.rest;

import com.noli.chipper.rest.service.MessageService;
import com.noli.chipper.rest.service.PostService;
import com.noli.chipper.rest.service.UserService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by noli on 13.06.15.
 */
public class RestClient {
    private MessageService messageService;
    private PostService postService;
    private UserService userService;

    private static volatile RestClient instance;

    public static RestClient getInstance() {
        RestClient localInstance = instance;
        if (localInstance == null) {
            synchronized (RestClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RestClient();
                }
            }
        }
        return localInstance;
    }

    private RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.0.103:3000")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", "Token token=QOjkS-4BsYnTOvAHQO5w9A");
                        request.addHeader("Accept", "*/*;q=0.8");
                    }
                })
                .build();
        messageService = restAdapter.create(MessageService.class);
        postService = restAdapter.create(PostService.class);
        userService = restAdapter.create(UserService.class);
    }

    public MessageService getMessageService() {
        return messageService;
    }
    public PostService getPostService() { return postService; }
    public UserService getUserService() {return userService; }

}
