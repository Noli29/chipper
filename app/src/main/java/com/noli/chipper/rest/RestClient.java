package com.noli.chipper.rest;

import android.util.Log;

import com.noli.chipper.activity.BaseActivity;
import com.noli.chipper.rest.service.MessageService;
import com.noli.chipper.rest.service.PostService;
import com.noli.chipper.rest.service.UserService;
import com.noli.chipper.util.SPEditor;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class RestClient {

    public static final String ROOT = "http://192.168.0.103:3000";
    private static volatile RestClient instance;
    private MessageService messageService;
    private PostService postService;
    private UserService userService;

    private RestClient() {
    }

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

    public void init(final BaseActivity baseActivity) {
        final SPEditor spEditor = baseActivity.getSPEditor();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String token = spEditor.readToken("");
                        if (!token.equals("")) {
                            Log.d("JHDGWYJ", token);
                            request.addHeader("Authorization", "Token token=" + token);
                        }
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

    public PostService getPostService() {
        return postService;
    }

    public UserService getUserService() {
        return userService;
    }

}
