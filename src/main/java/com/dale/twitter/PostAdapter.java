package com.dale.twitter;

import android.widget.*;
import android.content.Context;
import java.util.ArrayList;
import android.view.*;

public class PostAdapter extends BaseAdapter
{
	private Context mContext;
    private LayoutInflater mLayoutInflater;
    public ArrayList<Post> mPostList;

    private static class ViewHolder {
        //public LinearLayout container;
        public TextView usernameTextView;
        public TextView userHandleTextView;
        public TextView bodyTextView;
        public TextView createdAtTextView;
    }

    public PostAdapter(Context context) {
    	mContext = context;
    	mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	mPostList = new ArrayList<Post>();
    }

    @Override
    public int getCount() {
        return mPostList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = mLayoutInflater.inflate(R.layout.post_in_list, null);
            holder = new ViewHolder();
            holder.usernameTextView = (TextView) convertView.findViewById(R.id.textview_username);
            holder.userHandleTextView = (TextView) convertView.findViewById(R.id.textview_userhandle);
            holder.createdAtTextView = (TextView) convertView.findViewById(R.id.textview_created_at);
            holder.bodyTextView = (TextView) convertView.findViewById(R.id.textview_body);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Post currentPost = (Post) getItem(position);
        holder.usernameTextView.setText(currentPost.getUsername());
        holder.userHandleTextView.setText(currentPost.getUserHandle());
        holder.createdAtTextView.setText(currentPost.getCreatedAt());
        holder.bodyTextView.setText(currentPost.getBody());
        
        return convertView;
    }

    public void setPostList(ArrayList<Post> arrayList) {
        mPostList = arrayList;
        notifyDataSetChanged();
    }
}