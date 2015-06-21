package com.noli.chipper.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.github.vladvysotsky.ahelper.uis.Dialogs;
import com.noli.chipper.R;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.UserResponse;
import com.noli.chipper.util.SPEditor;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EditActivity extends BaseActivity {

    @InjectView(R.id.email_edit_et)
    EditText email;

    @InjectView(R.id.pass_first_et)
    EditText password;

    @InjectView(R.id.pass_confirm_et)
    EditText passwordConf;

    @InjectView(R.id.name_et)
    EditText name;

    @InjectView(R.id.surname_et)
    EditText surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (!password.getText().toString().equals(passwordConf.getText().toString())) {
                Dialogs.showOk(this, "Passwords isn't equal", "OK");
            } else {
                RestClient.getInstance().getUserService().updateInfo(
                        getSPEditor().readUserId(0),
                        name.getText().toString(),
                        email.getText().toString(),
                        surname.getText().toString(),
                        password.getText().toString(),
                        passwordConf.getText().toString(),
                        new Callback<UserResponse>() {
                            @Override
                            public void success(UserResponse userResponse, Response response) {
                                setResult(RESULT_OK);
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
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                setResult(RESULT_CANCELED);
                            }
                        }
                );
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
