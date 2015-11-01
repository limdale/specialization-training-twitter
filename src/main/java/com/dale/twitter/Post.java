package com.dale.twitter;

import org.json.*;
import java.util.ArrayList;

public class Post
{
	private String username;
	private String userHandle;
	private String body;
	private String createdAt;

	public Post(String a, String b) {
		username = a;
		body = b;
	}

	public Post(String username, String userHandle, String body, String createdAt) {
		this.username = username;
		this.userHandle = userHandle;
		this.body = body;
		this.createdAt = createdAt;
	}

	public void setUser(String u) {
		username = u;
	}

	public void setBody(String b) {
		body = b;
	}

	public void setUserHandle(String u) {
		userHandle = u;
	}

	public void setCreatedAt(String c) {
		createdAt = c;
	}

	public String getUsername() {
		return username;
	}

	public String getBody() {
		return body;
	}

	public String getUserHandle() {
		return userHandle;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public static ArrayList<Post> getPostsFromJSON(String response) throws Exception {
		ArrayList<Post> postList = new ArrayList<Post>();
		JSONArray postArray = new JSONArray(response);
		for(int i=0; i<postArray.length(); i++) {
			String username;
			String userHandle;
			String body;
			String datePosted;

			JSONObject currentPost = postArray.getJSONObject(i);
			body = currentPost.optString("text");
			datePosted = currentPost.optString("created_at");

			JSONObject userObject = currentPost.optJSONObject("user");
			username = userObject.optString("name");
			userHandle = userObject.optString("screen_name");

			postList.add(new Post(username, userHandle, body, datePosted));
		}
		return postList;
	}
}