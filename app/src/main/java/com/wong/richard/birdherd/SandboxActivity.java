package com.wong.richard.birdherd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

public class SandboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        TwitterApiClient client = Twitter.getApiClient();
        StatusesService srv = client.getStatusesService();

        srv.retweetsOfMe(5, null, null, null, null, null, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                for (Tweet t : result.data) {
                    Log.d("Sandbox", t.text);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterAPI", "Failed to retrieve retweets of logged-in user.", exception);
            }
        });
    }
}
