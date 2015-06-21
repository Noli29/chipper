package com.noli.chipper.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.noli.chipper.R;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.PostResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostActivity extends BaseActivity {

    @InjectView(R.id.post_et)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.post_submit_btn)
    void doPost() {
        String post = editText.getText().toString();
        RestClient.getInstance().getPostService().doPost(post, getSPEditor().readUserId(0), new Callback<PostResponse>() {
            @Override
            public void success(PostResponse postResponse, Response response) {
                PostActivity.this.setResult(RESULT_OK);
                PostActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error) {
                PostActivity.this.setResult(RESULT_CANCELED);
                PostActivity.this.finish();
            }
        });
    }

}
