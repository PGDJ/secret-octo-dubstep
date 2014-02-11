package com.example.carecounts;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MyPageAdapter extends FragmentPagerAdapter {
	private List<MyFragment> fragments;

	public MyPageAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public MyPageAdapter(FragmentManager fm, List<MyFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	@Override 
	public Fragment getItem(int position) {
		if(this.fragments == null){
			return MyFragment.newInstance("position: "+ position,HealthcareCallActivity.NO_CALL);
		}
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		if(this.fragments == null){
			return 5;
		}
		return this.fragments.size();
	}

}
