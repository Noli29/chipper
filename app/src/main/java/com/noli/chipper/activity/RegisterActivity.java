package com.noli.chipper.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.vladvysotsky.ahelper.intents.BaseIntents;
import com.github.vladvysotsky.ahelper.uis.Toasts;
import com.noli.chipper.R;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.UserResponse;
import com.noli.chipper.util.UserUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.email_reg_et)
    EditText email;

    @InjectView(R.id.pass_first_reg_et)
    EditText password;

    @InjectView(R.id.pass_confirm_reg_et)
    EditText passwordConf;

    @InjectView(R.id.name_reg_et)
    EditText name;

    @InjectView(R.id.surname_reg_et)
    EditText surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.reg_submit_btn)
    void doRegister() {
        RestClient.getInstance().getUserService().registerUser(
                name.getText().toString(),
                email.getText().toString(),
                surname.getText().toString(),
                password.getText().toString(),
                passwordConf.getText().toString(),
                new Callback<UserResponse>() {
                    @Override
                    public void success(UserResponse userResponse, Response response) {
                        UserUtil.writeUser(RegisterActivity.this, userResponse);
                        BaseIntents.startActivity(RegisterActivity.this,
                                MainActivity.class);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toasts.showToast(RegisterActivity.this, "Error");
                    }
                }
        );
    }

}
