package com.noli.chipper.util;


import com.noli.chipper.activity.BaseActivity;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.UserResponse;

public class UserUtil {

    public static void writeUser(BaseActivity baseActivity, UserResponse userResponse) {
        SPEditor spEditor = baseActivity.getSPEditor();
        spEditor.writeToken(userResponse.token);
        spEditor.writeUserId(userResponse.id);
        String name = userResponse.surname != null ? userResponse.name + " " + userResponse.surname : userResponse.name;
        spEditor.writeUsername(name);
        String userpickUrl = null;
        if (userResponse.userpicfn != null) {
            userpickUrl = userResponse.userpicfn;
        }
        if (userResponse.userpic != null) {
            userpickUrl = userResponse.userpic;
        }
        spEditor.writeUserpickURL(RestClient.ROOT + "/avatars/small/" + userpickUrl);
        spEditor.writeEmail(userResponse.email);
    }

}
