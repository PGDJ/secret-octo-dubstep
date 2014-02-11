package com.example.carecounts;

import java.util.Locale;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;


public class FragmentPreferences extends Activity {
	
SharedPreferences preferences;
	
	SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
            SharedPreferences.OnSharedPreferenceChangeListener() {

				@Override
				public void onSharedPreferenceChanged(SharedPreferences prefs,
						String key) {
					// TODO Auto-generated method stub
					if(key.contentEquals("pref_language")){
						boolean isSpanish = prefs.getBoolean(key, false);
						if(isSpanish){
							Resources res = getApplicationContext().getResources();
						    // Change locale settings in the app.
						    DisplayMetrics dm = res.getDisplayMetrics();
						    android.content.res.Configuration conf = res.getConfiguration();
						    conf.locale = new Locale("es");
						    res.updateConfiguration(conf, dm);
						}else{
							Resources res = getApplicationContext().getResources();
						    // Change locale settings in the app.
						    DisplayMetrics dm = res.getDisplayMetrics();
						    android.content.res.Configuration conf = res.getConfiguration();
						    conf.locale = new Locale("en");
						    res.updateConfiguration(conf, dm);
						}
					}
				}
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.language_preference);
        //set up preferences
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(spChanged);
        
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(R.id.content,
                new PrefsFragment()).commit();
    }


    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}