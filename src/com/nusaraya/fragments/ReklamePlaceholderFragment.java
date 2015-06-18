package com.nusaraya.fragments;

import com.donbaka.reklameuser.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReklamePlaceholderFragment extends Fragment {
	public ReklamePlaceholderFragment() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_splash_new, container, false); 
		
		return rootView;
	}
	
	
}
