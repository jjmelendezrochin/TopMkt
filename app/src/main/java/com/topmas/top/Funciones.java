package com.topmas.top;

import static com.topmas.top.Constants.FAKE_VALIDATION;
import static com.topmas.top.Constants.TAG_FAKEGPS_MSG;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.icu.text.DecimalFormat;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.topmas.top.Objetos.oInfoDispositivo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

// ***************************************
// Funciones de utilidad para el sistema
public class Funciones {
    private Bitmap[] ArrImagenesProd = new Bitmap[100];
    Usuario usr = new Usuario();
    String pName = usr.getidusuario();

    // *******************************
    // Limpia imágenes
    public void LimpiaImagenes() {
        for (int k = 0; k < 100; k++) {
            ArrImagenesProd[k] = null;
        }
    }

    // *******************************
    // Coloca imágen de una posición del arreglo
    public void ColocaImagen(int k, Bitmap bitmap) {
        ArrImagenesProd[k] = bitmap;
    }

    // *******************************
    // Obtiene imagen de una posición
    public Bitmap ObtenImagen(int posicion) {
        return ArrImagenesProd[posicion];
    }

    // *******************************
    // Revisa conexión
    public boolean RevisarConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    // *******************************
    // Revisa tipo de conexión
    public boolean RevisarTipoConexion(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMetered = cm.isActiveNetworkMetered();
        return isMetered;
    }

    // *******************************
    // Inicia procese de ubicación
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

        // ******************************************************

        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Local);
    }

    // *******************************
    // Compacta bitmap
    public Bitmap Compacta(android.graphics.Bitmap foto) {
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
    public void grabImage(Context contexto, Uri photoURI, ImageView imageView) {
        Activity activity = (Activity) contexto;
        try {
            contexto.getContentResolver().notifyChange(photoURI, null);
            ContentResolver cr = contexto.getContentResolver();
            Bitmap bitmap;

            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI);
            imageView.setImageBitmap(bitmap);
        }
        /*
        catch (OutOfMemoryError e) {
            RegistraError(pName, "Funciones.java GrabarImagen1", e, activity, contexto);
            Toast.makeText(contexto, "Su dispositivo no cuenta con espacio suficiente para almacenar las fotos, favor de liberar para poder continuar [" + e.getMessage() + "]", Toast.LENGTH_LONG).show();
            // Log.d(TAG, "Failed to load", e);
        }
        */
        catch (FileNotFoundException e1) {
            //RegistraError(pName, "Funciones.java GrabarImagen2", e1, activity, contexto);
            Toast.makeText(contexto, "Archivo no encontrado [" + e1.getMessage() + "]", Toast.LENGTH_LONG).show();
            // Log.d(TAG, "Failed to load", e1);
        } catch (IOException e2) {
            //RegistraError(pName, "Funciones.java GrabarImagen3", e2, activity, contexto);
            Toast.makeText(contexto, "Error al guardar datos, favor de revisar su dispositivo [" + e2.getMessage() + "]", Toast.LENGTH_LONG).show();
            // Log.d(TAG, "Failed to load", e2);
        }
    }

    // ***************************************
    // Funcion que registra errores en la tabla sqlite de errores para luego subirlos a la plataforma
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void RegistraError(String sUsuario, String sSeccion, Exception e, Activity activity, Context contexto) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString();

        TelephonyManager tManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        oInfoDispositivo oinfo = new oInfoDispositivo();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        double x = Math.pow(Resources.getSystem().getDisplayMetrics().widthPixels / dm.xdpi, 2);
        double y = Math.pow(Resources.getSystem().getDisplayMetrics().heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        DecimalFormat df2 = new DecimalFormat("#,###,###,##0");
        String rounded = df2.format((double) screenInches);
        double densityDpi = (int) (dm.density * 160f);

        oinfo.set_fabricante(Build.MANUFACTURER);
        oinfo.set_modelo(Build.BRAND);
        oinfo.set_modelo(Build.MODEL);
        oinfo.set_board(Build.BOARD);
        oinfo.set_hardware(Build.HARDWARE);
        oinfo.set_serie(Build.SERIAL);
        // oinfo.set_uid(tManager.getDeviceId());
        oinfo.set_android_id(Settings.Secure.getString(contexto.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        oinfo.set_resolucion(Resources.getSystem().getDisplayMetrics().heightPixels + " * " + Resources.getSystem().getDisplayMetrics().widthPixels + " Pixels");
        oinfo.set_tamaniopantalla(rounded + " Inches");
        oinfo.set_densidad((densityDpi) + " dpi");
        oinfo.set_bootloader(Build.BOOTLOADER);
        oinfo.set_user_value(Build.USER);
        oinfo.set_host_value(Build.HOST);
        oinfo.set_version(Build.VERSION.RELEASE);
        oinfo.set_api_value(Build.VERSION.SDK_INT + "");
        oinfo.set_build_id(Build.ID);
        oinfo.set_build_time(Build.TIME + "");
        oinfo.set_fingerprint(Build.FINGERPRINT);
        oinfo.set_usuario(sUsuario);
        oinfo.set_seccion(sSeccion);
        oinfo.set_error(sStackTrace);       // Datos del trazado
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto);
        almacenaImagen.inserta_error(oinfo);
    }

    // ***************************************
    // Limpia variables al salir
    public void limpiaVariablessesion(Context contexto) {
        // **************************************
        /* Limpia variables de sesion */
        // TODO almacena los valores de la empresa al inicio
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(contexto);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(TAG_IDEMPRESA
                , String.valueOf("0"));
        editor.putString(TAG_IDPROMOTOR, String.valueOf("0"));
        editor.putString(TAG_USUARIO, String.valueOf("0"));
        editor.commit();
        // **************************************
    }

    // ***************************************************
    // Verifica cuantas Aplicaciones esta unsando GPS Fake
    public static boolean isMockSettingsON(Context context) {
        // Fuente https://stackoverflow.com/questions/6880232/disable-check-for-mock-location-prevent-gps-spoofing
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }

    // ***************************************************
    // Verifica cuantas Aplicaciones esta unsando GPS Fake
    public static boolean areThereMockPermissionApps(Context context) {
        // Fuente https://stackoverflow.com/questions/6880232/disable-check-for-mock-location-prevent-gps-spoofing
        String PAQUETE_RADIO = "com.android.fmradio";
        String sPaquete = "Hay programas no permitidos";
        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);

                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;

                // Log.e(" ** paquete ",  " ** paquete " + applicationInfo.packageName.toString());
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        // Log.e(" -- permiso "," -- permiso " + String.valueOf(i)  +  " " + requestedPermissions[i].toString());
                        if (requestedPermissions[i]
                                .equals("android.permission.ACCESS_MOCK_LOCATION")
                                && !applicationInfo.packageName.equals(context.getPackageName())
                                && (!packageInfo.packageName.toString().equals(PAQUETE_RADIO))
                        ) {
                            // Log.e(" *** paquete ", String.valueOf(i)  +  " " +  applicationInfo.packageName.toString());
                            sPaquete = sPaquete + "\n" +  applicationInfo.packageName.toString();
                            count++;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                // Log.e("Got exception " , e.getMessage());
            }
        }

        if (count > 0) {
            // Toast.makeText(context.getApplicationContext(), sPaquete, Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    // ***************************************************
    // Verifica si tiene un servicio GPS fake
    public static boolean ValidaUbicacionFake(Usuario usuario, Context contexto){
        boolean bResp1 = isMockSettingsON(contexto);                 // Validaciòn para Android 9
        boolean bResp2 = areThereMockPermissionApps(contexto);       // Validaciòn para Android 9
        boolean bResp3 = usuario.getisFromMockProvider();            // Validaciòn para Android 13

        // Log.e(TAG_INFO, "Valores " + bResp1 + ", " +bResp2 + ", " + bResp3);
        String sResultado =  TAG_FAKEGPS_MSG;
        if((bResp1||bResp2||bResp3)
                && FAKE_VALIDATION){
            Toast.makeText(contexto, sResultado , Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return  false;
        }
    }

    // ***************************************************
    // No usadas
    public static List<String> getListOfFakeLocationAppsFromAll(Context context) {
        List<String> fakeApps = new ArrayList<>();
        List<ApplicationInfo> packages = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo aPackage : packages) {
            boolean isSystemPackage = ((aPackage.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
            fakeApps.add(context.getApplicationContext().getApplicationInfo().loadLabel(context.getPackageManager()).toString());
            // Log.e(TAG_INFO, context.getApplicationContext().getApplicationInfo().loadLabel(context.getPackageManager()).toString());
        }
        return fakeApps;
    }

}
