package com.noli.chipper.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vladvysotsky.ahelper.uis.Dialogs;
import com.github.vladvysotsky.ahelper.uis.Toasts;
import com.noli.chipper.R;
import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.PostResponse;
import com.noli.chipper.util.SPEditor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.userpick_iv)
    public ImageView userpic;

    @InjectView(R.id.username_tv)
    public TextView username;

    @InjectView(R.id.email_tv)
    public TextView email;

    @InjectView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    public PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        ButterKnife.inject(this);
        initUser();
        postAdapter = new PostAdapter(this);
        mRecyclerView.setAdapter(postAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        updateList();
    }

    private void updateList() {
        RestClient.getInstance().getPostService().getAllPosts(new Callback<List<PostResponse>>() {
            @Override
            public void success(List<PostResponse> postResponses, Response response) {
                if (postResponses == null) {
                    return;
                }
                List<PostResponse> target = new ArrayList<>();
                for (PostResponse post : postResponses) {
                    if (getSPEditor().readUserId(0) == post.getUserId()) {
                        target.add(post);
                    }
                }
                postAdapter.updateDataset(target);
            }

            @Override
            public void failure(RetrofitError error) {
                Toasts.showToast(MainActivity.this, "Error: " + error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 || requestCode == 102) {
            if (resultCode == RESULT_OK) {
                initUser();
                updateList();
            }
        }
    }

    private void initUser() {
        SPEditor spEditor = getSPEditor();
        email.setText(getSPEditor().readEmail(""));
        username.setText(getSPEditor().readUsername(""));
        ImageLoader.getInstance().displayImage(spEditor.readUserpicURL(""), userpic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout: {
                SPEditor spEditor = getSPEditor();
                spEditor.writeEmail("");
                spEditor.writeUserId(0);
                spEditor.writeUsername("");
                spEditor.writeToken("");
                spEditor.writeUserpickURL("");
                finish();
                break;
            }
            case R.id.action_messages: {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivityForResult(intent, 101);
                break;
            }
            case R.id.action_edit: {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 102);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

        private List<PostResponse> mDataset;
        private Context mContext;

        public PostAdapter(Context context) {
            this.mContext = context;
            this.mDataset = Collections.EMPTY_LIST;
        }

        public PostAdapter(Context context, @NonNull List<PostResponse> changesData) {
            this.mDataset = changesData;
        }

        public void updateDataset(@NonNull List<PostResponse> changesData) {
            mDataset = changesData;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.post_item, null);
            return new ViewHolder(itemLayoutView);
        }

        public void emptyDataset() {
            mDataset = Collections.EMPTY_LIST;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            fillItems(viewHolder, i);
        }

        private void fillItems(ViewHolder viewHolder, int i) {
            viewHolder.post.setText(mDataset.get(i).getPost());
            viewHolder.title.setText("Post #" + (i + 1));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @InjectView(R.id.title_tv)
            TextView title;
            @InjectView(R.id.post_tv)
            TextView post;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.inject(this, itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Dialogs.showYN(MainActivity.this, "Delete it?", "Yes", "No", true, new Dialogs.ButtonListener() {
                    @Override
                    public void onYesPressed(final DialogInterface dialogInterface, int i) {
                        RestClient.getInstance().getPostService().deletePost(mDataset.get(getAdapterPosition()).getId(), new
                                Callback<Response>() {
                                    @Override
                                    public void success(Response response, Response response2) {
                                        Toasts.showToast(MainActivity.this, "Success");
                                        updateList();
                                        dialogInterface.dismiss();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toasts.showToast(MainActivity.this, "Failure");
                                        dialogInterface.dismiss();
                                    }
                                });
                    }

                    @Override
                    public void onNoPressed(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            }
        }

    }

}
