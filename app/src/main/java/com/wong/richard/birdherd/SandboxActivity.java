package com.wong.richard.birdherd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class SandboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        BirdHerdTwitterApiClient client = new BirdHerdTwitterApiClient(session);
        client.getFollowerService().followerIds(session.getUserId(), 5, new Callback<BirdHerdTwitterApiClient.Followers>() {
            @Override
            public void success(Result<BirdHerdTwitterApiClient.Followers> result) {
                for (Long id : result.data.ids) {
                    Log.d("Sandbox", id.toString());
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("Sandbox", "Failed to get followers for user.", exception);
            }
        });
    }
}
