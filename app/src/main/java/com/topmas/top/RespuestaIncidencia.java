package com.topmas.top;

import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_FUENTE;
import static com.topmas.top.Constants.TAG_IDINC;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.topmas.top.Objetos.oRespuestaIncidencia;


// FUENTE chat gpt busqueda de archivos de capturas de pantalla
public class RespuestaIncidencia extends AppCompatActivity {
    int pidPromotor = 0;
    int pidRuta = 0;
    int pidInc = 0;
    double pLatitud = 0;
    double pLongitud = 0;
    String pdireccion = "";
    String ptienda = "";
    String pUsuario = "";
    String pfuente = "";

    AlmacenaImagen almacenaImagen;

    private final Usuario usr = new Usuario();
    ImageView imageView;
    Button btnPaste;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;


    FusedLocationProviderClient fusedLocationClient;

    @SuppressLint({"MissingInflatedId", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestaincidencia);
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        imageView = findViewById(R.id.imageClipboard);
        btnPaste = findViewById(R.id.btnPaste);

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PICK_IMAGE);
            }
        }
 */

        Intent i = getIntent();
        pidInc= i.getIntExtra(TAG_IDINC, 0);
        pfuente= i.getStringExtra(TAG_FUENTE);
        Log.e(TAG_ERROR, "TAG_IDINC " + pidInc);
        Log.e(TAG_ERROR, "TAG_FUENTE " + pfuente);

        oRespuestaIncidencia orespinc = almacenaImagen.obtenRespuestaIncidencia(pidInc);
/*
        Log.e(TAG_ERROR, String.valueOf(pidInc));
        Log.e(TAG_ERROR, String.valueOf(oRespuestaIncidencia._idincidencia));
        Log.e(TAG_ERROR, String.valueOf(oRespuestaIncidencia._idfoto));
        Log.e(TAG_ERROR, String.valueOf(oRespuestaIncidencia._idruta));
        Log.e(TAG_ERROR, String.valueOf(oRespuestaIncidencia._tienda));
        Log.e(TAG_ERROR, String.valueOf(oRespuestaIncidencia._direccioncompleta));
        Log.e(TAG_ERROR, oRespuestaIncidencia._fechahora);
        Log.e(TAG_ERROR, oRespuestaIncidencia._observaciones);
        Log.e(TAG_ERROR, oRespuestaIncidencia._respuesta);
        Log.e(TAG_ERROR, oRespuestaIncidencia._fechahora_respuesta);
        Log.e(TAG_ERROR, oRespuestaIncidencia._image);
        Log.e(TAG_ERROR, String.valueOf(oRespuestaIncidencia._leida));

 */

        //****************************
        // Agregando losdatos
        TextView lblNotif = findViewById(R.id.lblNotif);
        String textonotif = "Incidencia " + pidInc;
        lblNotif.setText(textonotif);

        //****************************
        TextView txtTituloTienda = findViewById(R.id.txtTienda);
        txtTituloTienda.setText(oRespuestaIncidencia._tienda.toUpperCase());

        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        pUsuario = preferencias.getString(TAG_USUARIO, usr.getnombre());
        TextView txtUsuario = findViewById(R.id.txtUsuario);
        txtUsuario.setText(pUsuario);

        // *****************************
        TextView txtFechaHora = findViewById(R.id.txtFecha);
        txtFechaHora.setText(oRespuestaIncidencia._fechahora);

        // *****************************
        TextView txtfechahora_respuesta = findViewById(R.id.txtFechaRespuesta);
        txtfechahora_respuesta.setText(oRespuestaIncidencia._fechahora_respuesta);

        // *****************************
        TextView txtRespuesta = findViewById(R.id.txtRespuesta);
        txtRespuesta.setText(oRespuestaIncidencia._respuesta);

        // *****************************
        EditText txtObservaciones = findViewById(R.id.txtObservaciones);
        txtObservaciones.setText(oRespuestaIncidencia._observaciones);

        // *****************************
        // llenando de tipo de incidencia
        TextView txtTipoIncidencia = findViewById(R.id.txtTipoIncidencia);
        txtTipoIncidencia.setText(almacenaImagen.obtenTipoIncidencia(oRespuestaIncidencia._idincidencia));

        //****************************
        FloatingActionButton fab = findViewById(R.id.fabMenu);
        fab.setOnClickListener(view -> {
            Intent MenuTienda = new Intent(getApplicationContext(), listarespuestaincidencias.class);
            MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(pidRuta));
            MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
            MenuTienda.putExtra(TAG_TIENDA, ptienda);
            MenuTienda.putExtra(TAG_LATITUD, pLatitud);
            MenuTienda.putExtra(TAG_LONGITUD, pLongitud);
            MenuTienda.putExtra(TAG_DIRECCION, pdireccion);
            startActivity(MenuTienda);
        });

        // *****************************
        // Botón para guardar
        Button cmdCerrar = findViewById(R.id.cmdCerrar);
        // Boton foto puede tomar una foto
        cmdCerrar.setOnClickListener(view -> {
            almacenaImagen.estableceLeidorespuesta(pidInc);
            Intent MenuTienda = new Intent(getApplicationContext(), listarespuestaincidencias.class);
            MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(pidRuta));
            MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
            MenuTienda.putExtra(TAG_TIENDA, ptienda);
            MenuTienda.putExtra(TAG_LATITUD, pLatitud);
            MenuTienda.putExtra(TAG_LONGITUD, pLongitud);
            MenuTienda.putExtra(TAG_DIRECCION, pdireccion);
            startActivity(MenuTienda);
        });

        // *******************
        // Obtiene geoposición
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // Use the location object
                        pLatitud = location.getLatitude();
                        pLongitud = location.getLongitude();
                        // Do something with the location data
                    }
                });
    }

}