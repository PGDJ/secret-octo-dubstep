package com.example.carecounts;


import java.util.ArrayList;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.protocol.HTTP;


//TODO:change extends ArrayAdapter<YourNewObjectType>
public class WWWListAdapter extends ArrayAdapter<WWWobject>{

	private LayoutInflater inflater;
	private int resource;
	private Context ctx;
	public WWWListAdapter(Context context, int resource, ArrayList<WWWobject> arr) {
		super(context, 0, arr);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		ctx = context;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		TextView text;

		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			//Re-use a view that is off the screen
			view = convertView;
		}

		text = (TextView) view.findViewById(R.id.textViewInfo);
		final String message = this.getItem(position).message; 
		text.setText(message);
		ImageView numView =(ImageView) view.findViewById(R.id.list_image_num);
		numView.setImageResource(this.getItem(position).num);

		Button shareView =(Button) view.findViewById(R.id.share_this);
		shareView.setOnClickListener((new ImageView.OnClickListener() {
			public void onClick(View v) 
			{
				/*
				 * On my phone this code 
				 * lets me choose to share the info with, email, sms, g+, etc 
				 */
				//http://developer.android.com/training/basics/intents/sending.html
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType(HTTP.PLAIN_TEXT_TYPE);
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Health Care");
				shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                ctx.startActivity(shareIntent);
			}
		}));
		/*ImageView fbView = (ImageView) view.findViewById(R.id.facebook_icon);
		fbView.setOnClickListener((new ImageView.OnClickListener() {
			public void onClick(View v) {

			}
		}));

		ImageView twitterView = (ImageView) view.findViewById(R.id.twitter_icon);
		twitterView.setOnClickListener((new ImageView.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
			}
		}));
		ImageView googleView = (ImageView) view.findViewById(R.id.google_icon);
		googleView.setOnClickListener((new ImageView.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
			}
		}));
		*/

		return view;
	}

	class EmailOnClickListener implements OnClickListener
	{
		int position;
		Context ctx;

		EmailOnClickListener(int position, Context ctx)  
		{  
			this.position = position;  
			this.ctx = ctx;
		}  

		public void onClick(View v)  
		{  
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("text/html");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Care Counts wants to share a message with you");
			emailIntent.putExtra(Intent.EXTRA_TEXT, getItem(position).message);
			ctx.startActivity(emailIntent);
		}  

	}
}
