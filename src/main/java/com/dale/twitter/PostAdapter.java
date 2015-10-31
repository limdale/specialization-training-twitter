package com.dale.twitter;

public class PostAdapter extends BaseAdapter
{
	private Context mContext;
    private LayoutInflater mLayoutInflater;
    public ArrayList<Post> mPostList;

    private static class ViewHolder {
        public LinearLayout container;
        public TextView songNameTextView;
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
            convertView = mLayoutInflater.inflate(R.layout.song_in_list, null);
            holder = new ViewHolder();
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Song currentSong = (Song) getItem(position);
        holder.songNameTextView.setText(currentSong.getSongName());
        if(position == currentSongIndex) {
            holder.container.setBackgroundColor(Color.LTGRAY);
        }
        else {
            holder.container.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }
}