package com.example.carecounts.q_and_a;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import com.example.carecounts.R;
import com.example.carecounts.R.id;
import com.example.carecounts.R.layout;
import com.example.carecounts.q_and_a.TopicFragment;
import com.example.carecounts.qadb.QandAContentProvider;
import com.example.carecounts.qadb.QuestionAnswerTable;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SlidingPaneLayout;

public class QandAActivity extends FragmentActivity implements TopicFragment.OnTopicSelectedListener {

	SharedPreferences preferences;
	static int changedCount = 0;


	//private QandACursorAdapter mAdapter;
	private SlidingPaneLayout mSlidingPaneLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState){
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

		setContentView(R.layout.activity_q_and_a_sliding);
		mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane);
		mSlidingPaneLayout.openPane();
		mSlidingPaneLayout.setParallaxDistance(100);
		//mSlidingPaneLayout.setSliderFadeColor(Color.parseColor("#e9f1f2"));
		//mSlidingPaneLayout.setSliderFadeColor(Color.WHITE);

		if(savedInstanceState == null){
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			TopicFragment topicFragment = TopicFragment.newInstance();
			fragmentTransaction.add(R.id.topics_container, topicFragment);
			QandAFragment qaf = QandAFragment.newInstance(null);
			fragmentTransaction.add(R.id.qa_container, qaf);
			fragmentTransaction.commit();
		}
		SharedPreferences settings = getPreferences(0);
		boolean read = settings.getBoolean("read", true);
		Log.d("QandActivity", "Read1: "+ read);
		if(read){
			new FilesToDBTask(this).execute(this.getAssets());
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("read", false);
			editor.commit();
			read = settings.getBoolean("read", true);
			Log.d("QandActivity", "Read2: "+ read);
		}


	}

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			if(mSlidingPaneLayout.isOpen()){
				NavUtils.navigateUpFromSameTask(this);
			}else{
				mSlidingPaneLayout.openPane();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed (){
		if(mSlidingPaneLayout.isOpen()){
			super.onBackPressed();
		}else{
			mSlidingPaneLayout.openPane();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.ways_women_win, menu);
		return false;
	}

	@Override
	public void onTopicSelected(Uri uri, String note) {
		mSlidingPaneLayout.closePane();
		QandAFragment qa = QandAFragment.newInstance(note);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.qa_container, qa);//, uri.toString());
		//ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.commit();
	}

	private class FilesToDBTask extends AsyncTask<AssetManager, Integer, Integer> {

		Context context;
		public FilesToDBTask(Context context){
			this.context = context;
		}
		ProgressDialog dialog;
		@Override
		protected void onPreExecute (){
			
			dialog = new ProgressDialog(context);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setProgress(0);
			dialog.setProgressNumberFormat(null);
			dialog.show();
		}

		protected Integer doInBackground(AssetManager... assestMgr) {
			/*int count = urls.length;
	         long totalSize = 0;
	         for (int i = 0; i < count; i++) {
	             totalSize += Downloader.downloadFile(urls[i]);
	             publishProgress((int) ((i / (float) count) * 100));
	             // Escape early if cancel() is called
	             if (isCancelled()) break;
	         }*/
			InputStream inputStream = null;
			ContentValues values = new ContentValues();
			int count = 0; 
			try {
				for(int i = 0; i < 2; i++){
					if(i == 0){
						inputStream = assestMgr[0].open("qa_en.txt");
					}else{
						inputStream = assestMgr[0].open("qa_es.txt");
					}
					InputStreamReader isr = new InputStreamReader(inputStream);
					BufferedReader reader = new BufferedReader(isr);
					String question = null;
					String answer = null;
					String link = null;
					String type = null;
					while (null != (question = reader.readLine())) {
						answer = reader.readLine();
						link = reader.readLine();
						type = reader.readLine();
						values.put(QuestionAnswerTable.QUESTION, question);
						values.put(QuestionAnswerTable.ANSWER, answer);
						values.put(QuestionAnswerTable.LINK, link);
						values.put(QuestionAnswerTable.TOPIC_NAME, type);
						values.put(QuestionAnswerTable.LANG, i);
						getContentResolver().insert(QandAContentProvider.CONTENT_QandA_URI,values);
						count++;
						publishProgress(count);
					}
				}
				Log.d("Task", "Count1 "+count);
			} catch (IOException e) {
				//e.printStackTrace();
				Log.d("QandAActvity", "file open failed");
			}
			Log.d("Task", "Count1 "+count);
			return 0;
		}

		protected void onProgressUpdate(Integer... progress) {
			dialog.setProgress((int)(progress[0]*(100/255f)));
			if(progress[0] == 255){
				dialog.setProgress(100);
			}
		}

		protected void onPostExecute(Integer result) {
			if(result == 0){
				//dialog.dismiss();
				//Log.d("Task", "Dismissed called");
				dialog.cancel();
			}
		}
	}
}
