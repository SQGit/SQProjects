package com.emergencysavior.emergencysavior;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


public class AndroidTwitter extends Activity {
    private TwitterLoginButton twitterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setUpViews();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterButton.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpViews() {
        setUpTwitterButton();
    }

    private void setUpTwitterButton() {
        twitterButton = (TwitterLoginButton) findViewById(R.id.twitter_button);
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {


                setUpViewsForTweetComposer();
            }

            @Override
            public void failure(TwitterException exception) {

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpViewsForTweetComposer() {
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text("PROTECT YOURSELF, CUSTOMERS AND WORKERS FROM CRIME, TERRORISM AND NATIONAL DISASTERS");
        builder.show();
    }
}
