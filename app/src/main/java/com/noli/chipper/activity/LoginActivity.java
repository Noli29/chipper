package com.noli.chipper.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.vladvysotsky.ahelper.intents.BaseIntents;
import com.github.vladvysotsky.ahelper.uis.Toasts;
import com.noli.chipper.R;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.UserResponse;
import com.noli.chipper.rest.service.UserService;
import com.noli.chipper.util.UserUtil;

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
                if (response.getStatus() == 200) {
                    UserUtil.writeUser(LoginActivity.this, userResponse);
                    Toasts.showToast(getApplicationContext(), "Success");
                    BaseIntents.startActivity(LoginActivity.this, MainActivity.class);
                } else {
                    Toasts.showToast(LoginActivity.this, "Incorrect data");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toasts.showToast(LoginActivity.this, "Error: " + error.getMessage());
            }
        });
    }

    @OnClick(R.id.register_btn)
    public void startRegistration() {
        BaseIntents.startActivity(this, RegisterActivity.class);
    }

    private void finishIfAuthed() {
        if (!getSPEditor().readToken("").equals("")) {
            BaseIntents.startActivity(LoginActivity.this,
                    MainActivity.class);
        }
    }

}
