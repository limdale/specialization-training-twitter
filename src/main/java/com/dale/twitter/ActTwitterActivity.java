package com.dale.twitter;

import android.app.Activity;
import android.os.*;
import android.widget.*;
import android.view.View;
import org.json.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActTwitterActivity extends Activity
{
	Button mButtonSendTweet;
	Button mButtonLoadHome;
	EditText mEditTextTweet;
	ListView mListViewHomeTimeline;
	PostAdapter mPostAdapter;
	AsyncTask<String, Void, String> tweetAsyncTask;
	AsyncTask<Void, Void, String> homeAsyncTask;

    public static SimpleDateFormat apiFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
    public static SimpleDateFormat postFormat = new SimpleDateFormat("MMM dd");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeViews();
        initializeListeners();
        mPostAdapter = new PostAdapter(this);
        mListViewHomeTimeline.setAdapter(mPostAdapter);
        //System.out.println(response);
    }

    private void initializeViews() {
    	mButtonSendTweet = (Button) findViewById(R.id.button_send_tweet);
    	mButtonLoadHome = (Button) findViewById(R.id.button_load_home);
    	mEditTextTweet = (EditText) findViewById(R.id.edittext_new_tweet);
    	mListViewHomeTimeline = (ListView) findViewById(R.id.listview_home_timeline);
    }

    private void initializeListeners() {
    	mButtonSendTweet.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			initializeTweetAsyncTask();
    			tweetAsyncTask.execute(mEditTextTweet.getText().toString());
    		}
    	});
    	mButtonLoadHome.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			initializeHomeAsyncTask();
    			homeAsyncTask.execute();
    		}
    	});
    }

    private void initializeTweetAsyncTask() {
    	tweetAsyncTask = new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... str) {
				try {
					String tweet = str[0];
					return Tweet.post(tweet);
				}
				catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			protected void onPostExecute(String response) {
				if(response != null) {
					System.out.println(response);
					Toast.makeText(getApplicationContext(), "Your tweet has been posted", Toast.LENGTH_SHORT).show();
					mEditTextTweet.setText("");
				}
                else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                }
			}
    	};
    }

    private void initializeHomeAsyncTask() {
    	homeAsyncTask = new AsyncTask<Void, Void, String>() {
			protected String doInBackground(Void... str) {
				try {
					return Tweet.getHomeTimeline();
				}
				catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			protected void onPostExecute(String response) {
				if(response != null) {
					System.out.println(response);
					try {
						ArrayList<Post> postList = Post.getPostsFromJSON(response);
						mPostAdapter.setPostList(postList);
					}
					catch(Exception e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Error parsing timeline", Toast.LENGTH_SHORT).show();
					}
				}
                else {
                    Toast.makeText(getApplicationContext(), "Cannot get home timeline", Toast.LENGTH_SHORT).show();
                }
			}
    	};
    }

    public static String parseDate(String dateString) {
        try {
            Date date = apiFormat.parse(dateString);
            return postFormat.format(date);
        }
        catch(Exception e) {
            e.printStackTrace();
            return dateString;
        }
    }

}
