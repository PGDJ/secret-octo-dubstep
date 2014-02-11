package com.example.carecounts.q_and_a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carecounts.R;
import com.example.carecounts.q_and_a.TopicFragment.OnTopicSelectedListener;
import com.example.carecounts.qadb.QandAContentProvider;

public class QandAFragment extends ListFragment{

	public final static String TYPE_KEY = "tk";

	public QandAFragment(){
	}

	public static QandAFragment newInstance(String type) {
		final QandAFragment qaf = new QandAFragment();
		final Bundle args = new Bundle();
		args.putString(TYPE_KEY, type);
		qaf.setArguments(args);
		return qaf;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.q_and_a_topic_list, container, false);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Resources res = getResources();
		//String[] parts = res.getStringArray(R.array.q_and_a_topics);
		//Resources res = getResources();
		//String[] parts = res.getStringArray(R.array.QandA);
		//QandAObject[] qa = new QandAObject[parts.length/3];  
		//int part = 0;
		//for(int i = 0; i < qa.length; i++){
		//qa[i] = new QandAObject(parts[part++],parts[part++],parts[part++]);
		//}
		//setListAdapter(new QandAFragmentListAdapter(this.getActivity(), R.layout.q_and_a_item, qa));
		String type = getArguments().getString(TYPE_KEY);
		if(type != null){
			Cursor c = getActivity().getContentResolver().query(
					QandAContentProvider.CONTENT_QandA_URI, 
					null, null, new String[]{type}, null);
			setListAdapter(new QandACursorAdapter(getActivity(), c, 0));
		}else{
			;
		}
	}

}
