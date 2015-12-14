package com.wong.richard.birdherd;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rwong on 12/11/15.
 */
public class MyTwApiClient extends TwitterApiClient {
    public MyTwApiClient(TwitterSession session) {
        super(session);
    }

    public FollowerService getFollowerService() {
        return getService(FollowerService.class);
    }
    public interface FollowerService {
        @GET("/1.1/followers/ids.json")
        void followerIds(@Query("user_id") Long id,
                         @Query("count") Integer count,
                         Callback<FollowerIds> cb);
        @GET("/1.1/followers/list.json")
        void followerList(@Query("user_id") Long id,
                         @Query("count") Integer count,
                         Callback<Followers> cb);
    }
    public class FollowerIds {
        @SerializedName("ids")
        public final Long[] ids;

        public FollowerIds(Long[] ids) {
            this.ids = ids;
        }
    }
    public class Followers {
        @SerializedName("previous_cursor")
        public final Long prevCursor;

        @SerializedName("next_cursor")
        public final Long nextCursor;

        @SerializedName("users")
        public final User[] users;

        public Followers(Long prevCursor, User[] users, Long nextCursor) {
            this.users = users;
            this.prevCursor = prevCursor;
            this.nextCursor = nextCursor;
        }
    }

    public FriendsService getFriendsService() {
        return getService(FriendsService.class);
    }
    public interface FriendsService {
        @GET("/1.1/friends/ids.json")
        void ids(@Query("user_id") Long userId,
                 @Query("screen_name") String screenName,
                 @Query("cursor") Long cursor,
                 @Query("stringify_ids") Boolean stringifyIds,
                 @Query("count") Integer count,
                 Callback<Ids> cb);

        @GET("/1.1/friends/ids.json")
        void idsByUserId(@Query("user_id") Long userId,
                         Callback<Ids> cb);
    }
    public class Ids {
        @SerializedName("previous_cursor")
        public final Long previousCursor;

        @SerializedName("ids")
        public final Long[] ids;

        @SerializedName("next_cursor")
        public final Long nextCursor;


        public Ids(Long previousCursor, Long[] ids, Long nextCursor) {
            this.previousCursor = previousCursor;
            this.ids = ids;
            this.nextCursor = nextCursor;
        }
    }
}