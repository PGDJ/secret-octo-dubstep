package com.example.carecounts.q_and_a;

import com.example.carecounts.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopicListAdapter extends ArrayAdapter<String> {
	private LayoutInflater inflater;
	private int resource;

	public TopicListAdapter(Context context, int resource, String[] arr) {
		super(context, 0, arr);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		String topic = getItem(position);
		TextView tv;

		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			//Re-use a view that is off the screen
			view = convertView;
		}

		tv = (TextView) view.findViewById(R.id.topic_text_view);
		if(tv != null){
			tv.setText(topic);
		}else{
			Log.d("TopicListApdater" , "NULL Textview ");
		}
		return view;
	}
}
