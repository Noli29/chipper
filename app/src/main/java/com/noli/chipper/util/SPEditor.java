package com.noli.chipper.util;


import android.content.Context;

import com.github.vladvysotsky.ahelper.wrappers.SPWrapper;

public class SPEditor {

    public static final String SP_NAME = "com.noli.chipper.SHARED_PREFS";
    private static final String TOKEN = "token";
    private static final String USERNAME = "username";
    private static final String USER_ID = "user_id";
    private static final String URL_USERPIC = "userpic";
    private static final String EMAIL = "email";
    private SPWrapper mSpWrapper;

    public SPEditor(Context context) {
        mSpWrapper = new SPWrapper(context, SP_NAME);
    }

    public void writeToken(String token) {
        mSpWrapper.write(TOKEN, token);
    }

    public String readToken(String defValue) {
        return mSpWrapper.read(TOKEN, defValue);
    }

    public void writeEmail(String email) {
        mSpWrapper.write(EMAIL, email);
    }

    public String readEmail(String defValue) {
        return mSpWrapper.read(EMAIL, defValue);
    }

    public void writeUserpickURL(String userpicUrl) {
        mSpWrapper.write(URL_USERPIC, userpicUrl);
    }

    public String readUserpicURL(String defValue) {
        return mSpWrapper.read(URL_USERPIC, defValue);
    }

    public void writeUsername(String username) {
        mSpWrapper.write(USERNAME, username);
    }

    public String readUsername(String defValue) {
        return mSpWrapper.read(USERNAME, defValue);
    }

    public void writeUserId(int id) {
        mSpWrapper.write(USER_ID, id);
    }

    public int readUserId(int defValue) {
        return mSpWrapper.read(USER_ID, defValue);
    }

}
