package com.example.carecounts.q_and_a;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.carecounts.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;



public class TopicFragment extends ListFragment{

	public static final String TOPIC_KEY = "tk";
	
	public interface OnTopicSelectedListener {
        public void onTopicSelected(Uri uri, String note);
    }
	
	String[] topics;
	
	public TopicFragment(){
	}
	
	public static TopicFragment newInstance() {
		final TopicFragment tf = new TopicFragment();
		final Bundle args = new Bundle();
		tf.setArguments(args);
		return tf;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.q_and_a_topic_list, container, false);
		return view;
	}
	
	OnTopicSelectedListener mListener;
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTopicSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
	
	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Resources res = getResources();
		String[] parts = res.getStringArray(R.array.q_and_a_topics);
		setListAdapter(new TopicListAdapter(this.getActivity(), R.layout.q_and_a_topic_item, parts));
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		TextView tv = (TextView)v.findViewById(R.id.topic_text_view);
        mListener.onTopicSelected(null,tv.getText().toString());
    }

}
