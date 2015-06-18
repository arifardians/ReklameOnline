package com.nusaraya.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SynchronizeBootReceiver extends BroadcastReceiver{
	
	private SynchronizerReceiver alarm;
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			alarm.setAlarm(context);
		}
	}

}
