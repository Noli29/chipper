package com.noli.chipper;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import com.noli.chipper.rest.RestClient;
import com.noli.chipper.rest.models.MessageResponse;
import com.noli.chipper.rest.models.PostResponse;
import com.noli.chipper.rest.models.UserResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Button messageButton;
        private Button postButton;
        private Button userButton;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            messageButton = (Button) view.findViewById(R.id.message_btn);
            postButton = (Button) view.findViewById(R.id.post_btn);
            userButton = (Button) view.findViewById(R.id.user_btn);

            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RestClient.getInstance().getMessageService().getMessage(
                            1, new Callback<MessageResponse>() {
                        @Override
                        public void success(MessageResponse messageResponse, Response response) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Recipient: ").append(messageResponse.getRecipientId())
                                    .append("Subject ").append(messageResponse.getSubject()).
                                    append("Body ").append(messageResponse.getBody());
                            Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            postButton.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                   RestClient.getInstance().getPostService().getPost(
                       1, new Callback<PostResponse>() {
                       @Override
                       public void success(PostResponse postResponse, Response response) {
                           StringBuilder stringBuilder = new StringBuilder();
                           stringBuilder .append("Id:").append(postResponse.getId())
                                   .append("Post:").append(postResponse.getPost());
                           Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
                       }
                       @Override
                       public void failure(RetrofitError error) {
                          Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                       }

                   });
               }
            });

            userButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    RestClient.getInstance().getUserService().getUser(
                       1, new Callback<UserResponse>() {
                           @Override
                           public void success(UserResponse userResponse, Response response) {
                             StringBuilder stringBuilder = new StringBuilder();
                             stringBuilder .append("Id:").append(userResponse.getId())
                                       .append("Name:").append(userResponse.getName())
                                     .append("Surname:").append(userResponse.getSurname())
                                   .append("Email:").append(userResponse.getEmail());
                             Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
                           }
                           @Override
                           public void failure(RetrofitError error) {
                             Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                           }

                       });
                }
            });
        }
    }
}
