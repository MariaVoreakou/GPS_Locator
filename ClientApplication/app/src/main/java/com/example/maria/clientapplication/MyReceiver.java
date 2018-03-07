package com.example.maria.clientapplication;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    Intent myIntent = null;

    //When myReceiver has called from Main, checks if AirplaneMode is on
    //And checks if Service is running

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("OnReceiveBroadcast","dbg msg 1");
        if(myIntent == null) {
            myIntent = new Intent(context, MyGPSLocatorService.class);
        }
        if (isAirplaneModeOn(context) && isMyServiceRunning(MyGPSLocatorService.class,context)) {
            context.stopService(myIntent);
            Log.e("OnReceiveBroadcast","dbg msg 2");
        }else if(!isAirplaneModeOn(context) && !isMyServiceRunning(MyGPSLocatorService.class,context)){
            context.startService(myIntent);
        }
        Log.e("OnReceiveBroadcast","dbg msg 3");
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass,Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equalsIgnoreCase(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
