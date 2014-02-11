package com.example.carecounts;

import java.util.Locale;

import com.example.carecounts.q_and_a.QandAActivity;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	RelativeLayout rlTop,rlMiddle,rlBottom;
	SharedPreferences preferences;
	static int changedCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
		
		setContentView(R.layout.activity_main);
		rlTop = (RelativeLayout) findViewById(R.id.layout_top_option);
		rlMiddle = (RelativeLayout) findViewById(R.id.layout_middle_option);
		rlBottom = (RelativeLayout) findViewById(R.id.layout_bottom_option);
		
		rlTop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, WaysWomenWin.class));
			}
			
		});
		rlMiddle.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, QandAActivity.class));
			}
			
		});
		rlBottom.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, HealthcareCallActivity.class));
			}
			
		});
		
	}
	@Override
	protected void onResume(){
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
		updateLanguage();
        
    }
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			Intent i = new Intent(this, FragmentPreferences.class);
			//startActivity(i);
			startActivityForResult(i,0);
            return super.onOptionsItemSelected(item);
		}
	}
	
	public void updateLanguage(){
		if (preferences.getBoolean("pref_language", false)){
			if(changedCount!=1){
				if(changedCount==0){
					changedCount=1;
				}
				if(changedCount==2){
					changedCount = 1;
				}
				Resources res = getResources();
				// Change locale settings in the app.
				DisplayMetrics dm = res.getDisplayMetrics();
				android.content.res.Configuration conf = res.getConfiguration();
				conf.locale = new Locale("es");
				res.updateConfiguration(conf, dm);
				//Intent refresh = new Intent(this, MainActivity.class); 
				//startActivity(refresh); 
				recreate();
			}
		}else{
			if(changedCount!=2){
				if(changedCount==0){
					changedCount=2;
				}
				if(changedCount==1){
					changedCount =2;
				}

				Resources res = getResources();
				// Change locale settings in the app.
				DisplayMetrics dm = res.getDisplayMetrics();
				android.content.res.Configuration conf = res.getConfiguration();
				conf.locale = new Locale("en");
				res.updateConfiguration(conf, dm);
				//Intent refresh = new Intent(this, MainActivity.class); 
				//startActivity(refresh);
				recreate();
			}
		}
	}

}
