package com.example.carecounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/*
 * XML files being used:
 *  R.layout.activity_insurance
 *  R.layout.myfragment_call    (Used with MyFragment)
 *  R.layout.myfragment_no_call (Used with MyFragment)
 * 
 * Outside class files being used
 *  MyPageAdapter.java
 *  MyFragment.java
 * 
 * Inner class
 *  MyPageViewListener
 */
public class HealthcareCallActivity extends FragmentActivity {
	MyPageAdapter pageAdapter;
	private static final String TAG = "HealthcareCallActivity";
	SharedPreferences preferences;
	static int changedCount = 0;
	public static final String CALL = "call";
	public static final String NO_CALL = "no call";
	public static final String WEB = "web";
	public static final String EMPLOYER_WEB = "employer_web";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Inside onCreate");
		super.onCreate(savedInstanceState);

		//Set languages
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

		setContentView(R.layout.activity_insurance);
		List<MyFragment> fragments =  getFragments();
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(),fragments);

		/* 
		 * I think it was returning null because the Viewpager was not
		 * inside the layout use by setContentView()  
		 */
		final ViewPager pager = (ViewPager)findViewById(R.id.pager);
		pager.setAdapter(pageAdapter);

		final Button backButton = (Button)findViewById(R.id.back_button);
		final Button nextButton = (Button)findViewById(R.id.next_button);

		final ImageView[] progressViews = new ImageView[5]; 
		progressViews[0] = (ImageView) findViewById(R.id.progress1);
		progressViews[1] = (ImageView) findViewById(R.id.progress2);
		progressViews[2] = (ImageView) findViewById(R.id.progress3);
		progressViews[3] = (ImageView) findViewById(R.id.progress4);
		progressViews[4] = (ImageView) findViewById(R.id.progress5);

		pager.setOnPageChangeListener(new MyPageViewListener(this, pager, backButton, nextButton, progressViews));

	}

	private List<MyFragment> getFragments(){
		List<MyFragment> fList = new ArrayList<MyFragment>();
		String page4 = getString(R.string.call_page4);
		
		fList.add(MyFragment.newInstance(getString(R.string.call_page1),NO_CALL));
		if(isTablet(this)){
			page4 += getString(R.string.tablet_check);
		}
		fList.add(MyFragment.newInstance(getString(R.string.call_page2),NO_CALL));
		fList.add(MyFragment.newInstance(getString(R.string.call_page3),EMPLOYER_WEB));
		fList.add(MyFragment.newInstance(page4,CALL));
		fList.add(MyFragment.newInstance(getString(R.string.call_thanks),WEB));
		
		return fList;

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.ways_women_win, menu);
		return false;
	}
	
	//check whether using phone or tablet
	public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
//        Log.d("HealthCareActivity", String.valueOf(xlarge));
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
//        Log.d("HealthCareActivity", String.valueOf(large));
        return (xlarge || large);
    }
	
	public static class MyPageViewListener implements ViewPager.OnPageChangeListener{
		final private FragmentActivity activity;
		final private ViewPager pager;
		final private Button back;
		final private Button next;
		final ImageView[] progressViews;
		public MyPageViewListener(final FragmentActivity activity, final ViewPager pager, final Button back, final Button next, final ImageView[] progressViews){
			this.activity = activity;
			this.pager = pager;
			this.back = back;
			this.next = next;
			this.progressViews = progressViews;

			back.setVisibility(View.INVISIBLE);
			back.setClickable(false);
			next.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					pager.setCurrentItem(1, true);
				}
			});
		}

		@Override
		public void onPageScrollStateChanged(int position) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		private int lastPosition;
		@Override
		public void onPageSelected(int position) {
			if(lastPosition > position){
				progressViews[lastPosition].setBackgroundResource(R.drawable.uncomplete_circle);
			}
			progressViews[position].setBackgroundResource(R.drawable.complete_circle);
			lastPosition = position;
			if(position == 0){
				back.setVisibility(View.INVISIBLE);
				back.setClickable(false);
			}else{
				back.setVisibility(View.VISIBLE);
				back.setClickable(true);
			}
			if(pager.getAdapter().getCount() - 1 == position){
				next.setVisibility(View.INVISIBLE);
				next.setClickable(false);
			}else{
				next.setVisibility(View.VISIBLE);
				next.setClickable(true);
			}
			final int p = position;
			back.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					pager.setCurrentItem(p-1, true);
				}
			});
			next.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					pager.setCurrentItem(p+1, true);
				}
			});

		}

	}
}
