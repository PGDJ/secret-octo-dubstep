package com.example.carecounts.q_and_a;


import com.example.carecounts.R;
import com.example.carecounts.R.id;
import com.example.carecounts.R.layout;
import com.example.carecounts.qadb.QandAContentProvider;
import com.example.carecounts.qadb.QuestionAnswerTable;

import android.R.interpolator;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QandACursorAdapter extends CursorAdapter{

	private LayoutInflater mInflater;
	private class ViewHolder {
         TextView question;
         TextView answer;
    }
	private String TAG = "QandACursorAdapter";
	public QandACursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = LayoutInflater.from(context);
		
		/*Cursor cr = context.getContentResolver().query(QandAContentProvider.CONTENT_QandA_URI, null, null, null, null);
		String cols = "";
		for (String s : cr.getColumnNames()){
			cols += s + " ";
		}
		cols = cols.trim();
		Log.d(TAG, cols);*/
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		final View itemView = mInflater.inflate(
                        R.layout.q_and_a_item,
                        viewGroup,
                        false);
        final ViewHolder holder = new ViewHolder();
        holder.question = (TextView) itemView.findViewById(R.id.textViewQuestion);
        holder.answer = (TextView) itemView.findViewById(R.id.textViewAnswer);
        itemView.setTag(holder);
        return itemView;
	}
	
	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final ViewHolder holder = (ViewHolder) view.getTag();
		int q,a,l;
		q = cursor.getColumnIndex(QuestionAnswerTable.QUESTION);
		a = cursor.getColumnIndex(QuestionAnswerTable.ANSWER);
		l = cursor.getColumnIndex(QuestionAnswerTable.LINK);
		holder.question.setText(cursor.getString(q));
		holder.answer.setText(cursor.getString(a)+"\n"+cursor.getString(l));
		RelativeLayout qRL;
		final RelativeLayout aRL;
		qRL = (RelativeLayout) view.findViewById(R.id.relativeLayoutQuestion);
		aRL = (RelativeLayout) view.findViewById(R.id.relativeLayoutAnswer);
		qRL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(aRL.getVisibility() == View.GONE){
					aRL.setVisibility(View.VISIBLE);
					Animation anim1 = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
					anim1.setInterpolator(context, interpolator.overshoot);
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
					Animation anim2 = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
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
		
	}
}
