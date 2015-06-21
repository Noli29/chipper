package com.noli.chipper.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.vladvysotsky.ahelper.intents.BaseIntents;
import com.github.vladvysotsky.ahelper.uis.Toasts;
import com.noli.chipper.R;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.UserResponse;
import com.noli.chipper.rest.service.UserService;
import com.noli.chipper.util.SPEditor;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.email_te)
    public EditText mEmail;

    @InjectView(R.id.pass_te)
    public EditText mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        RestClient.getInstance().init(this);
        finishIfAuthed();
    }

    @OnClick(R.id.submit_btn)
    public void doLogin() {
        String pass = mPass.getText().toString();
        String email = mEmail.getText().toString();
        UserService userService = RestClient.getInstance().getUserService();
        userService.doLogin(email, pass, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                SPEditor spEditor = getSPEditor();
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
                Toasts.showToast(getApplicationContext(), "Success");
                BaseIntents.startActivity(LoginActivity.this, MainActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
                Toasts.showToast(LoginActivity.this, "Error: " + error.getMessage());
            }
        });
    }

    private void finishIfAuthed() {
        if (!getSPEditor().readToken("").equals("")) {
            BaseIntents.startActivity(LoginActivity.this,
                    MainActivity.class);
        }
    }

}
