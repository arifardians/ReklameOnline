package com.nusaraya.activity;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.donbaka.reklameuser.R;
import com.donbaka.reklameuser.helper.SessionManager;
import com.donbaka.reklameuser.helper.TabHelper;
import com.nusaraya.dbhelper.SimpleReklameDAO;
import com.nusaraya.services.SynchronizerReceiver;

public class HomeFragmentActivity extends FragmentActivity{
	
	private static final String TAG = HomeFragmentActivity.class.getSimpleName();
	public static final int TAB_HOME  	= 1; 
	public static final int TAB_SEARCH 	= 2; 	
	public static final int TAB_MAP 	= 3; 
	
	private ViewPager pager; 
	private ActionBar actionBar; 
	
	public static Activity ACTIVITY;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_home_fragment);
		setActionBar();
		actionBar = getActionBar();
		
		ACTIVITY = this;
		
		HomePagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(pagerAdapter);
		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				pager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		LayoutInflater inflater = getLayoutInflater();
		
		TextView textHome = (TextView) inflater.inflate(R.layout.item_tab_home, null) ; 
		TextView textMap  = (TextView) inflater.inflate(R.layout.item_tab_map, null);
		TextView textSearch = (TextView) inflater.inflate(R.layout.item_tab_search, null); 
		
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		//actionBar.setSplitBackgroundDrawable(getResources().getDrawable(R.color.blue_text_placeholder));
		actionBar.addTab(actionBar.newTab().setCustomView(textHome).setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setCustomView(textSearch).setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setCustomView(textMap).setTabListener(tabListener));
		
		TabHelper.setHasEmbeddedTabs(actionBar, false);
		
		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_home_fragment, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout:
			showLogoutWarning();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showLogoutWarning(){
		SweetAlertDialog alert = new SweetAlertDialog(this, 
				SweetAlertDialog.WARNING_TYPE);
		alert.setTitleText("Warning..!"); 
		alert.setContentText("Are you sure to logout?");
		alert.setConfirmText("Yes, Logout"); 
		alert.setCancelText("Cancel"); 
		alert.showCancelButton(true); 
		alert.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sweetAlertDialog.cancel();
			}
		});
		
		alert.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				SessionManager session = new SessionManager(getApplicationContext());
				session.destroySession();
				
				SynchronizerReceiver syncAlarm = new SynchronizerReceiver(); 
				syncAlarm.cancelAlarm(getApplicationContext());
				
				SimpleReklameDAO reklameDAO = new SimpleReklameDAO(getApplicationContext()); 
				reklameDAO.deleteAll(); 
				reklameDAO.close();
				
				Intent intent = new Intent(HomeFragmentActivity.this, LoginNewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
				finish();
			}
		});
		alert.show();
	}
	
	private void setActionBar(){

		int title = getResources().getIdentifier("action_bar_title", "id", "android"); 
		TextView textTitle = (TextView) findViewById(title);
		textTitle.setTextColor(Color.WHITE);
		
		getActionBar().setIcon(R.drawable.ic_road_bilboard);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue_primary));
		setTitle("Reklame Online");
	}
	
		
}
