package com.wong.richard.birdherd;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rwong on 12/11/15.
 */
public class BirdHerdTwitterApiClient extends TwitterApiClient {
    public BirdHerdTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public FollowerService getFollowerService() {
        return getService(FollowerService.class);
    }
    public interface FollowerService {
        @GET("/1.1/followers/ids.json")
        void followerIds(@Query("user_id") Long id,
                  @Query("count") Integer count,
                  Callback<Followers> cb);
    }
    public class Followers {
        @SerializedName("ids")
        public final Long[] ids;

        public Followers(Long previousCursor, Long[] ids, Long nextCursor) {
            this.ids = ids;
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