package com.nusaraya.activity;

import com.nusaraya.fragments.ReklameHomeFragment;
import com.nusaraya.fragments.ReklameMapFragment;
import com.nusaraya.fragments.ReklamePlaceholderFragment;
import com.nusaraya.fragments.ReklameSearchFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
	private static final int TOTAL_FRAGMENT = 3; 
	private static final int TAB_HOME   = 0; 
	private static final int TAB_SEARCH = 1;
	private static final int TAB_MAP    = 2;

	private static final String[] TAB_TITLES = {"Home", "Search", "Map"};
	
	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int selectedTab) {
		Fragment fragment = null;
		switch (selectedTab) {
		case TAB_HOME:
			fragment = new ReklameHomeFragment();
			break;
		case TAB_MAP: 
			// fragment = MapFragment(); 
			fragment = new ReklameMapFragment();

			break; 
		case TAB_SEARCH:
			fragment = new ReklameSearchFragment();

			// fragment = SearchFragment();
			break;
		default:
			fragment = new ReklamePlaceholderFragment();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return TOTAL_FRAGMENT;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return TAB_TITLES[position];
	}
}
