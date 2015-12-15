package com.wong.richard.birdherd;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FollowerActivity extends ListActivity {
    final Context followerContext = this;
    final Pattern imgPattern = Pattern.compile("(.*)_normal(\\..*)");
    private static final String TAG = "FollowerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        MyTwApiClient client = new MyTwApiClient(session);

        client.getFollowerService().followerList(session.getUserId(), 10, new Callback<MyTwApiClient.Followers>() {
            @Override
            public void success(Result<MyTwApiClient.Followers> result) {
                User[] users = result.data.users;
                String query = "";

                for (User u : users) {
                    query += "@" + u.screenName + " OR ";
                }
                query = query.substring(0, query.length() - " OR ".length());
                Log.d(TAG, "query: " + query);

                ImageView im = (ImageView) findViewById(R.id.followerImageView);

                SearchTimeline searchTimeline = new SearchTimeline.Builder().query(query).build();
                final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(followerContext)
                    .setTimeline(searchTimeline)
                    .build();
                setListAdapter(adapter);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("Sandbox", "Failed to get followers for user.", exception);
            }
        });

    }

    private String normalImgUrl(String url) {
        Matcher m = imgPattern.matcher(url);
        m.matches();
        return m.group(1) + m.group(2);
    }
}
