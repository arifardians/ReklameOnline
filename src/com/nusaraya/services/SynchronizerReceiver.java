package com.nusaraya.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class SynchronizerReceiver extends WakefulBroadcastReceiver{
	
	private static final String TAG = SynchronizerReceiver.class.getSimpleName();
	private static final long INTERVAL_TEN_MINUTES = 10 * 60 * 1000; // in milliseconds
	private AlarmManager alarmMgr; 
	private PendingIntent alarmIntent; 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Starting service from receiver..!"); 
		Intent service = new Intent(context, SynchronizerService.class);
		startWakefulService(context, service);
	}
	
	public void setAlarm(Context context){
		alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, SynchronizerReceiver.class); 
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, INTERVAL_TEN_MINUTES, INTERVAL_TEN_MINUTES, alarmIntent);
		// Enable {@code NotificationBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
		ComponentName receiver = new ComponentName(context, SynchronizeBootReceiver.class); 
		PackageManager pm = context.getPackageManager(); 
		
		pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		Log.d(TAG, "Setting the alarm!"); 
	}
	
	public void cancelAlarm(Context context){
		if(alarmMgr != null){
			alarmMgr.cancel(alarmIntent);
		}else{
			alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); 
			Intent intent = new Intent(context, SynchronizerReceiver.class);
			alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0); 
			alarmMgr.cancel(alarmIntent);
		}
		
		ComponentName receiver = new ComponentName(context, SynchronizerReceiver.class); 
		PackageManager pm = context.getPackageManager(); 
		
		pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		Log.d(TAG, "Canceling alarm!");
	}

}
