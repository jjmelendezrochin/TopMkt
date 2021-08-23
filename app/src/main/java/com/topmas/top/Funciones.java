package com.topmas.top;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.IOException;


// ***************************************
// Funciones de utilidad para el sistema
public class Funciones {
    private Bitmap[] ArrImagenesProd = new Bitmap[100];

    // Limpia imágenes
    public void LimpiaImagenes(){
        for (int k=0; k<100; k++) {
            ArrImagenesProd[k] = null;
        }
    }

    // Coloca imágen de una posición del arreglo
    public void ColocaImagen(int k, Bitmap bitmap){
        ArrImagenesProd[k] = bitmap;
    }

    // Obtiene imagen de una posición
    public Bitmap ObtenImagen(int posicion){
        return ArrImagenesProd[posicion];
    }

    // Revisa conexión
    public boolean RevisarConexion(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Si hay conexión a Internet en este momento
        //Toast.makeText(context, "Hay conexion", Toast.LENGTH_LONG).show();
        // No hay conexión a Internet en este momento
        // Toast.makeText(context, "No Hay conexion", Toast.LENGTH_LONG).show();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void locationStart(Context context) {
        LocationManager mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Activity activity = (Activity) context;
        //Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Local);
    }

    public  Bitmap Compacta(android.graphics.Bitmap foto)
    {
        // *****************************************
        // Compacta la imagen y la manda de regreso
        int w = foto.getWidth();
        int h = foto.getHeight();
        double factor = 0.75;
        double w1 = w * factor;
        double h1 = h * factor;
        int w2 = (int) w1;
        int h2 = (int) h1;
        return Bitmap.createScaledBitmap(foto,
                w2,
                h2,
                false);
    }

    // *******************************
    // Colocar la imagen en imageview
    public void grabImage(Context contexto, Uri photoURI, ImageView imageView)
    {
        try
        {
            contexto.getContentResolver().notifyChange(photoURI, null);
            ContentResolver cr = contexto.getContentResolver();
            Bitmap bitmap;

            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI);
            imageView.setImageBitmap(bitmap);
        }
        catch (OutOfMemoryError e)
        {
            Toast.makeText(contexto, "Su dispositivo no cuenta con espacio suficiente para almacenar las fotos, favor de liberar para poder continuar [" + e.getMessage() + "]", Toast.LENGTH_LONG).show();
            // Log.d(TAG, "Failed to load", e);
        }
        catch(FileNotFoundException e1)
        {
            Toast.makeText(contexto, "Archivo no encontrado [" + e1.getMessage() + "]", Toast.LENGTH_LONG).show();
            // Log.d(TAG, "Failed to load", e1);
        }
        catch(IOException e2)
        {
            Toast.makeText(contexto, "Error al guardar datos, favor de revisar su dispositivo [" + e2.getMessage() + "]", Toast.LENGTH_LONG).show();
            // Log.d(TAG, "Failed to load", e2);
        }
    }
}
