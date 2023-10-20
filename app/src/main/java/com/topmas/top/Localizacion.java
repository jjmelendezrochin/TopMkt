package com.topmas.top;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;


// ***************************************************
/* Aqui empieza la Clase Localizacion */
public class Localizacion implements LocationListener {
    private static final String TAG_GEOPOSICION = "GEOPOSICION";
    private String sLatitud = "";
    private String sLongitud = "";

    MainActivity mainActivity;
    public MainActivity getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public void onLocationChanged(Location loc) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la deteccion de un cambio de ubicacion
        Usuario usuario = new Usuario();
        loc.getLatitude();
        loc.getLongitude();
        sLatitud = String.valueOf(loc.getLatitude());
        sLongitud = String.valueOf(loc.getLongitude());
        // Log.e(TAG_GEOPOSICION, "Latitud " + String.valueOf(sLatitud));
        // Log.e(TAG_GEOPOSICION, "Longitud " + String.valueOf(sLongitud));
        usuario.setLatitud(Double.parseDouble(sLatitud));
        usuario.setLongitud(Double.parseDouble(sLongitud));


    }
    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        // Log.e("ERROR", "GPS Desactivado");
    }
    @Override
    public void onProviderEnabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es activado
        // Log.e("ERROR", "GPS Activado");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                // Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                // Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                // Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }
}
