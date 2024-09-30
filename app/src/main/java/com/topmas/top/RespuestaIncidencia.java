package com.topmas.top;

import static com.topmas.top.Constants.TAG_DESCRIPCIONINCIDENCIA;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_FECHAHORA;
import static com.topmas.top.Constants.TAG_FECHAHORARESPUESTA;
import static com.topmas.top.Constants.TAG_IDINC;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_OBSERVACIONES;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.topmas.top.Objetos.oRespuestaIncidencia;

import java.text.SimpleDateFormat;
import java.util.Date;


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
    String pfechahora = "";
    String pfechahora_respuesta = "";
    String prespuesta = "";
    String pobservaciones = "";
    AlmacenaImagen almacenaImagen;
    private final Funciones funciones = new Funciones();
    private final Usuario usr = new Usuario();
    private ImageView imageView;
    Button btnPaste;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    ProgressDialog pDialog;
    public static final String UPLOAD_INCIDENCIA = TAG_SERVIDOR + "/PhotoUpload/upload_incidencia.php";
    public static final String UPLOAD_INCIDENCIA_O = TAG_SERVIDOR + "/PhotoUpload/upload_incidencia_o.php";
    public static final String UPLOAD_IDINCIDENCIA = "idincidencia";
    public static final String UPLOAD_OBSERVACIONES = "observaciones";
    int iFoto = 0;            // Es el id de la tabla almacenfoto de la foto recien subida
    int idoperacion = 11;
    int idincidencia = 0;
    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestaincidencia);
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        imageView = findViewById(R.id.imageClipboard);
        btnPaste = findViewById(R.id.btnPaste);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PICK_IMAGE);
            }
        }

        Intent i = getIntent();
        pidInc= i.getIntExtra(TAG_IDINC, 0);
        ptienda = i.getStringExtra(TAG_TIENDA);
        /*
        pidRuta = i.getIntExtra(TAG_IDRUTA, 0);
        pidPromotor = i.getIntExtra(TAG_IDPROMOTOR, 0);
        pLatitud = i.getDoubleExtra(TAG_LATITUD, 0.0);
        pLongitud = i.getDoubleExtra(TAG_LONGITUD, 0.0);

        pdireccion = i.getStringExtra(TAG_DIRECCION);
        pfechahora = i.getStringExtra(TAG_FECHAHORA);
        pfechahora_respuesta = i.getStringExtra(TAG_FECHAHORARESPUESTA);
        prespuesta = i.getStringExtra(TAG_RESPUESTA);
        pobservaciones= i.getStringExtra(TAG_OBSERVACIONES);
         */

        oRespuestaIncidencia orespinc = almacenaImagen.obtenRespuestaIncidencia(pidInc);
        // idincidencia, idfoto, idpromotor, idruta, fechahora, observaciones, respuesta, fechahora_respuesta, image, leida
        Log.e(TAG_ERROR, String.valueOf(pidInc));
        Log.e(TAG_ERROR, String.valueOf(orespinc._idincidencia));
        Log.e(TAG_ERROR, String.valueOf(orespinc._idfoto));
        Log.e(TAG_ERROR, String.valueOf(orespinc._idruta));
        Log.e(TAG_ERROR, orespinc._fechahora);
        Log.e(TAG_ERROR, orespinc._observaciones);
        Log.e(TAG_ERROR, orespinc._respuesta);
        Log.e(TAG_ERROR, orespinc._fechahora_respuesta);
        Log.e(TAG_ERROR, orespinc._image);
        Log.e(TAG_ERROR, String.valueOf(orespinc._leida));


        //****************************
        // Agregando losdatos
        TextView txtTituloTienda = findViewById(R.id.txtTienda);
        txtTituloTienda.setText(ptienda.toUpperCase());

        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        pUsuario = preferencias.getString(TAG_USUARIO, usr.getnombre());
        TextView txtUsuario = findViewById(R.id.txtUsuario);
        txtUsuario.setText(pUsuario);

        TextView txtFechaHora = findViewById(R.id.txtFecha);
        txtFechaHora.setText(orespinc._fechahora);

        TextView txtfechahora_respuesta = findViewById(R.id.txtFechaRespuesta);
        txtfechahora_respuesta.setText(orespinc._fechahora_respuesta);

        TextView txtRespuesta = findViewById(R.id.txtRespuesta);
        txtRespuesta.setText(orespinc._respuesta);

        EditText txtObservaciones = findViewById(R.id.txtObservaciones);
        txtObservaciones.setText(orespinc._observaciones);

        // *****************************
        // llenando los datos de la lista de productos
        Cursor c = almacenaImagen.CursorIncidencias();
        String[] from = new String[]{TAG_DESCRIPCIONINCIDENCIA};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinIncidencia = findViewById(R.id.spinIncidencia);
        spinIncidencia.setAdapter(adapter);
        spinIncidencia.setSelection(orespinc._idincidencia);

        // ******************************
        spinIncidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idincidencia = Integer.parseInt(String.valueOf(id));
                // Toast.makeText(getApplicationContext(),"Incidencia " + idincidencia, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        cmdCerrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                almacenaImagen.estableceLeidorespuesta(pidInc);
                Intent MenuTienda = new Intent(getApplicationContext(), listarespuestaincidencias.class);
                MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(pidRuta));
                MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
                MenuTienda.putExtra(TAG_TIENDA, ptienda);
                MenuTienda.putExtra(TAG_LATITUD, pLatitud);
                MenuTienda.putExtra(TAG_LONGITUD, pLongitud);
                MenuTienda.putExtra(TAG_DIRECCION, pdireccion);
                startActivity(MenuTienda);
            }
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
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Use the location object
                            pLatitud = location.getLatitude();
                            pLongitud = location.getLongitude();
                            // Do something with the location data
                        }
                    }
                });
    }

}