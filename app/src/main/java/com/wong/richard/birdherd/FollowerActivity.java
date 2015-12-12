package com.wong.richard.birdherd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

public class FollowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        BirdHerdTwitterApiClient client = new BirdHerdTwitterApiClient(session);
        client.getFollowerService().followerList(session.getUserId(), 200, new Callback<BirdHerdTwitterApiClient.Followers>() {
            @Override
            public void success(Result<BirdHerdTwitterApiClient.Followers> result) {
                for (User u : result.data.users) {
                    Log.d("Sandbox", u.screenName);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("Sandbox", "Failed to get followers for user.", exception);
            }
        });
    }
}
