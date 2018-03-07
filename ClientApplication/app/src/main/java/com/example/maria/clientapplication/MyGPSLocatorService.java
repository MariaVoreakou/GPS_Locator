package com.example.maria.clientapplication;

import android.Manifest;
import android.app.Service;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimerTask;

public class MyGPSLocatorService extends Service {
    LocationManager myLocManager;
    LocationListener myLocList;


    public MyGPSLocatorService() {
    }

    public class LocationTask extends TimerTask{
        Context context;
        public LocationTask(Context context){
            this.context = context;
        }
        public void run(){
            Looper.prepare();
            while(true){
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                myLocManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, myLocList, null);
            }
        }
    }

    //Location Listener που όταν κληθεί από την OnStart δημιουργεί τα fields που χρειαζόμαστε
    //και τα στέλνει μέσω ContentResolver με insert
    private class MyLocationListener implements android.location.LocationListener {
        public void onLocationChanged(Location loc) {

            Log.d("OnStartService", "dbg msg 3");
            String text = " My location is  Latitude =" + loc.getLatitude() + " Longitude =" + loc.getLongitude();
            String lat = loc.getLatitude() + "";
            String log = loc.getLongitude() + "";

            Log.d("Location:  ", text);
            //updateDatabase();
            //Set thr URI parse of content Provider of Project MyFirstApplication
            Uri uri = Uri.parse("content://com.example.maria.myfirstapplication.ContentProvider/User");
            ContentValues values = new ContentValues();
            ContentProviderClient myResolver = getContentResolver().acquireContentProviderClient(uri);

            values.put("user_id", "GPSuser");
            values.put("longtitude", Float.parseFloat(log));
            values.put("latitude", Float.parseFloat(lat));

            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(System.currentTimeMillis());
            String date = DateFormat.format("dd/MM/yyyy hh:mm", cal).toString();
            values.put("dt", date);

            //Call ContentResolver ωστε να σταλθουν τα δεδομενα στο MyFirstApplication
            Uri resultUri = getApplicationContext().getContentResolver().insert(uri, values);
            Log.d("OnStartService", "dbg msg 4");
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // Returns Null
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

    }

    //Γίνονται τα Requests Location Updates
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("OnStartService", "dbg msg 1");
        myLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocList = new MyLocationListener();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.e("OnStartService", "dbg msg 2");
        //myLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, myLocList);



        myLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 20, myLocList);

    }

    //Return START_STICKY
    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        onStart(intent,startId);
        return START_STICKY;
    }

    //Stop Updates when open airplaneMode
    @Override
    public void onDestroy(){
        Log.e("OnStartService","No more updates nigga");
        if(myLocManager!=null)
            myLocManager.removeUpdates(myLocList);

    }
}

