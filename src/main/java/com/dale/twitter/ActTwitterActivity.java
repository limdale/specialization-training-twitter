package com.dale.twitter;

import android.app.Activity;
import android.os.*;
import android.widget.*;
import android.view.View;

public class ActTwitterActivity extends Activity
{
	Button mButtonSendTweet;
	Button mButtonLoadHome;
	EditText mEditTextTweet;
	ListView mListViewHomeTimeline;
	AsyncTask<String, Void, String> tweetAsyncTask;
	AsyncTask<Void, Void, String> homeAsyncTask;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeViews();
        initializeListeners();
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
				}
                else {
                    Toast.makeText(getApplicationContext(), "Cannot get home timeline", Toast.LENGTH_SHORT).show();
                }
			}
    	};
    }

}
