package com.geek.aagamshah.lolchef.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * A service for fetching the Location based on GPS.
 */

public class MyService extends Service {
    public static final String BROADCAST_ACTION = "Hello World";
    public LocationManager locationManager;
    public MyLocationListener listener;
    Intent intent;


    public MyService() {
    }


    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        try {
            //Request GPS location every 4 seconds. Will later change it to 15.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
        } catch (SecurityException e) {
            Log.d("security exception", e.toString());
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        try {
            locationManager.removeUpdates(listener);
        } catch (SecurityException e) {
            Log.d("security exception", e.toString());
        }
    }


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(final Location loc) {
            Log.i("****", "Location changed");
            loc.getLatitude(); //get latitude
            loc.getLongitude();  //get longitude
            intent.putExtra("Latitude", loc.getLatitude());
            intent.putExtra("Longitude", loc.getLongitude());
            intent.putExtra("Provider", loc.getProvider());

            //Send brodcast to main activity.
            sendBroadcast(intent);
        }

        public void onProviderDisabled(String provider) {
            //Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }


        public void onProviderEnabled(String provider) {
            //Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}
