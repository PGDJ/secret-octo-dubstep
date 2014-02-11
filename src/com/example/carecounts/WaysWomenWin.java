package com.example.carecounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;


public class WaysWomenWin extends ListActivity {

	SharedPreferences preferences;
	static int changedCount = 0;
	
	private WWWListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//set language
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (preferences.getBoolean("pref_language", false)){
			Resources res = getResources();
			// Change locale settings in the app.
			DisplayMetrics dm = res.getDisplayMetrics();
			android.content.res.Configuration conf = res.getConfiguration();
			conf.locale = new Locale("es");
			res.updateConfiguration(conf, dm);
		}else{
			Resources res = getResources();
			// Change locale settings in the app.
			DisplayMetrics dm = res.getDisplayMetrics();
			android.content.res.Configuration conf = res.getConfiguration();
			conf.locale = new Locale("en");
			res.updateConfiguration(conf, dm);
		}
		
		
		setContentView(R.layout.activity_ways_women_win);
		this.getListView().setHeaderDividersEnabled(true);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		this.getListView().addHeaderView(inflater.inflate(R.layout.www_header, null, false));
		Resources res = getResources();
		HashMap <Integer,Integer> map = new HashMap<Integer,Integer>();
		map.put(1, R.drawable.num1);
		map.put(2, R.drawable.num2);
		map.put(3, R.drawable.num3);
		map.put(4, R.drawable.num4);
		map.put(5, R.drawable.num5);
		map.put(6, R.drawable.num6);
		map.put(7, R.drawable.num7);
		map.put(8, R.drawable.num8);
		map.put(9, R.drawable.num9);
		map.put(10, R.drawable.num10);
		map.put(11, R.drawable.num11);
		map.put(12, R.drawable.num12);
		map.put(13, R.drawable.num13);
		map.put(14, R.drawable.num14);
		map.put(15, R.drawable.num15);
		map.put(16, R.drawable.num16);
		map.put(17, R.drawable.num17);
		map.put(18, R.drawable.num18);
		map.put(19, R.drawable.num19);
		map.put(20, R.drawable.num20);
		map.put(21, R.drawable.num21);
		map.put(22, R.drawable.num22);
		
		String[] strfacts = res.getStringArray(R.array.www_facts);
		ArrayList <WWWobject> facts = new ArrayList<WWWobject> ();
		for(int i = 1; i <= 22; i++)
		{
			facts.add(new WWWobject(strfacts[i-1], map.get(i)));
		}
		
		mAdapter = new WWWListAdapter(this, R.layout.www_list_item, facts);
		this.setListAdapter(mAdapter);
		//mAdapter.add(getString(R.string.fact_1));
		setupActionBar();
		//this.setListAdapter(new WWWListAdapter(this, R.layout.www_list_item, facts));
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.ways_women_win, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In   the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
