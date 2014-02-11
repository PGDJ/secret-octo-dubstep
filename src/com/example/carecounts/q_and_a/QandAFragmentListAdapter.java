package com.example.carecounts.q_and_a;

import android.R.interpolator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.carecounts.R;

public class QandAFragmentListAdapter extends ArrayAdapter<QandAObject> { 
private LayoutInflater inflater;
private int resource;

public QandAFragmentListAdapter(Context context, int resource, QandAObject[] arr) {
	super(context, 0, arr);
	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.resource = resource;
}
public View getView(int position, View convertView, ViewGroup parent) {
	View view;
	QandAObject qa = getItem(position);
	TextView question,answer;
	RelativeLayout qRL;
	final RelativeLayout aRL;

	if (convertView == null) {
		view = inflater.inflate(resource, parent, false);
	} else {
		//Re-use a view that is off the screen
		view = convertView;
	}
	qRL = (RelativeLayout) view.findViewById(R.id.relativeLayoutQuestion);
	aRL = (RelativeLayout) view.findViewById(R.id.relativeLayoutAnswer);
	qRL.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(aRL.getVisibility() == View.GONE){
				aRL.setVisibility(View.VISIBLE);
				Animation anim1 = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
				anim1.setInterpolator(getContext(), interpolator.overshoot);
				//anim1.getInterpolator().getInterpolation(.1f);
				anim1.scaleCurrentDuration(3);
				anim1.setAnimationListener(new AnimationListener(){

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onAnimationStart(Animation animation) {
						aRL.setVisibility(View.VISIBLE);
						
					}
					
				});
				aRL.startAnimation(anim1);
			}else{
				Animation anim2 = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
				anim2.scaleCurrentDuration(2);
				anim2.setAnimationListener(new AnimationListener(){

					@Override
					public void onAnimationEnd(Animation animation) {
						aRL.setVisibility(View.GONE);
						
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
				});
				aRL.startAnimation(anim2);
			}
		}
		
	});
	
	question = (TextView) view.findViewById(R.id.textViewQuestion);
	question.setText(qa.question);
	
	answer = (TextView) view.findViewById(R.id.textViewAnswer);
	answer.setText(qa.answer+"\n"+qa.link);
	
	return view;
}

}
