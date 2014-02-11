package com.example.carecounts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment{
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	public static final String EXTRA_TYPE = "EXTRA_TYPE";

	public static final MyFragment newInstance(String message, String type)
	{
		MyFragment f = new MyFragment();
		Bundle bdl = new Bundle();
		bdl.putString(EXTRA_MESSAGE, message);
		bdl.putString(EXTRA_TYPE, type);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		String message = bundle.getString(EXTRA_MESSAGE);
		String callLayout = bundle.getString(EXTRA_TYPE);
		TextView messageTextView =null;
		View v = null;
		Button callButton = null;
		Button webButton = null;
		Button coverageButton = null;
		Button calculatorButton = null;
		if(callLayout.equals(HealthcareCallActivity.WEB)){
			v = inflater.inflate(R.layout.myfragment_web, container, false);
			messageTextView = (TextView)v.findViewById(R.id.textView_web);
			webButton = (Button)v.findViewById(R.id.button_web);
			webButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					String url = "https://www.healthcare.gov/";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
					//Toast.makeText(getActivity(), "visit website test", Toast.LENGTH_SHORT).show();
				}

			});
		}else if(callLayout.equals(HealthcareCallActivity.EMPLOYER_WEB)){
			v = inflater.inflate(R.layout.myfragment_web_buttons, container, false);
			messageTextView = (TextView)v.findViewById(R.id.textView_web_buttons);
			coverageButton = (Button)v.findViewById(R.id.button_coverage);
			coverageButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					String url = "https://www.healthcare.gov/downloads/ECT_Application_508_130615.pdf";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
					//Toast.makeText(getActivity(), "visit website test", Toast.LENGTH_SHORT).show();
				}

			});
			calculatorButton = (Button)v.findViewById(R.id.button_calculator);
			calculatorButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					String url = "http://kff.org/interactive/subsidy-calculator/";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
					//Toast.makeText(getActivity(), "visit website test", Toast.LENGTH_SHORT).show();
				}

			});
		}else if(callLayout.equals(HealthcareCallActivity.CALL)){
			v = inflater.inflate(R.layout.myfragment_call, container, false);
			messageTextView = (TextView)v.findViewById(R.id.textView_call);
			callButton = (Button)v.findViewById(R.id.button_call);
			
			//disables call button if it is a tablet
			if(isTablet(this.getActivity().getApplicationContext())){
				callButton.setEnabled(false);
			}
			callButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1-800-318-2596"));
					startActivity(intent);
					//Toast.makeText(getActivity(), "call test", Toast.LENGTH_SHORT).show();
				}

			});
		}else{
			v = inflater.inflate(R.layout.myfragment_no_call, container, false);
			messageTextView = (TextView)v.findViewById(R.id.textView_no_call);
		}
		Log.v("MyFRAGMENT", message);
		messageTextView.setText(message);

		return v;
	}
	
	//check whether using phone or tablet
	public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
//        Log.d("HealthCareActivity", String.valueOf(xlarge));
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
//        Log.d("HealthCareActivity", String.valueOf(large));
        return (xlarge || large);
    }

}
