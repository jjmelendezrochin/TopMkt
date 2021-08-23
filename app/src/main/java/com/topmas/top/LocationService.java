package com.topmas.top;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import static android.widget.Toast.LENGTH_SHORT;

public class LocationService extends Service implements LocationListener {

    LocationManager m_locationManager;
    int pidPromotor;
    Usuario usr = new Usuario();

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        pidPromotor = usr.getid();
        // Log.e("PARAMETRO OBTENIDO", String.valueOf(pidPromotor));

        // *****************************
        //  Guarda el valor de el id promotor para cuando el programa principal haya concluido
        // use ese valor para continuar guardando datos
        //SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        if(pidPromotor>0) {
            editor.putInt("idProveedorAlmacenado", pidPromotor);
            editor.apply();
        }

        // *****************************

        this.m_locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Toast.makeText(getApplicationContext(), "Servicio iniciado", LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //  Here I offer two options: either you are using satellites or the Wi-Fi services to get user's location
        //this.m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, this); //  User's location is retrieve every 3 seconds
        this.m_locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return START_STICKY;
        }
        this.m_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
        //Toast.makeText(getApplicationContext(), "Service starts", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Toast.makeText(getApplicationContext(), "Service Task destroyed", Toast.LENGTH_LONG).show();
        /*Intent myIntent = new Intent(getApplicationContext(), LocationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent, 0);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        alarmManager1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Start Alarm", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        /*Intent myIntent = new Intent(getApplicationContext(), LocationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent, 0);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        alarmManager1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Start Alarm", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (loc == null)    //  Filtering out null values
            return ;
        Double lat = loc.getLatitude();
        Double lon = loc.getLongitude();
        //////////// Log.e(TAG_LATITUD,String.valueOf(lat));
        //////////// Log.e(TAG_LONGITUD, String.valueOf(lon));
        //////////// Log.e(TAG_IDPROMOTOR, String.valueOf(pidPromotor));

        // **********************
        // Si el idpromotor es igual a cero entonces obtiene el valor de las preferencias
        // establecidas en el proceso on create
        if(pidPromotor==0) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            pidPromotor = preferences.getInt("idProveedorAlmacenado", 1);
        }
        // **********************

        //Calling AsyncTask for upload latitude and longitude
        /*
        ProcesoColocaUbicacion procesoColocaUbicacion = new ProcesoColocaUbicacion();
        procesoColocaUbicacion.setsLatitud(lat);
        procesoColocaUbicacion.setsLongitud(lon);
        procesoColocaUbicacion.setIdPromotor(pidPromotor);
        procesoColocaUbicacion.execute();
        */
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
