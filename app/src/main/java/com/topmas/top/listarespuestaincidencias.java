package com.topmas.top;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class listarespuestaincidencias extends AppCompatActivity {
    int pidPromotor = 0;
    int pidRuta = 0;
    String pidEmpresa = "0";
    Double platitud = 0.0;
    Double plongitud = 0.0;
    String pdireccion = "";
    String ptienda = "";
    String idUsuario = "";
    EditText txtBuscar;

    private final Usuario usr = new Usuario();
    private final Funciones funciones = new Funciones();
    private ListView lista;

    private AlmacenaImagen almacenaImagen;

    // private ProgressDialog pDialog;

    // Este numero debe de ser el numero de registros de la tabla
    int iCuentaIncidencias = 1000;
    int[] idinc = new int[iCuentaIncidencias];
    String[] fechasdeincidencias = new String[iCuentaIncidencias];
    String[] tiposdeincidencias = new String[iCuentaIncidencias];
    String[] observacionesdeincidencias = new String[iCuentaIncidencias];
    int[] rutasdeincidencias = new int[iCuentaIncidencias];
    String[] tiendasdeincidencias = new String[iCuentaIncidencias];
    String[] direccionesdeincidencias = new String[iCuentaIncidencias];
    String[] fechasderespuestasincidencias = new String[iCuentaIncidencias];
    String[] respuestasdeincidencias = new String[iCuentaIncidencias];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarespuestaincidencias);
        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                getDefaultSharedPreferences(getApplicationContext());
        idUsuario = preferencias.getString(TAG_USUARIO, usr.getnombre());
        String spromotor = preferencias.getString(TAG_IDPROMOTOR, String.valueOf(usr.getid()));
        pidPromotor = Integer.parseInt(Objects.requireNonNull(spromotor));
        // ***************************************

        //View view = this.findViewById(R.id.LinearLayout);
        lista = findViewById(R.id.lista1);
        txtBuscar = (findViewById(R.id.txtBuscar));

        Intent i = getIntent();
        pidRuta = i.getIntExtra(TAG_IDRUTA, pidRuta);
        ptienda = i.getStringExtra(TAG_TIENDA);
        platitud = i.getDoubleExtra(TAG_LATITUD, 0.0);
        plongitud = i.getDoubleExtra(TAG_LONGITUD, 0.0);
        pdireccion = i.getStringExtra(TAG_DIRECCION);
        pidEmpresa = usr.getidempresa();
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) ->
                funciones.RegistraError(idUsuario, "listarespuestaincidencias setDefaultUncaughtExceptionHandler", (Exception) throwable, listarespuestaincidencias.this, getApplicationContext()));

        MuestraRespuestaIncidencias(pidPromotor);

        //****************************
        // Evento click al adaptador
        lista.setOnItemClickListener(new OnItemClickListenerAdaptadorRespuestaIncidencias());

        // ***************************************
        // Icono de salir de lista incidencias
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            Intent MenuTienda = new Intent(getApplicationContext(), com.topmas.top.MenuTienda.class);
            MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(pidRuta));
            MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
            MenuTienda.putExtra(TAG_TIENDA, ptienda);
            MenuTienda.putExtra(TAG_LATITUD, platitud);
            MenuTienda.putExtra(TAG_LONGITUD, plongitud);
            MenuTienda.putExtra(TAG_DIRECCION, pdireccion);
            startActivity(MenuTienda);
        });
        // ***************************************
    }

    //************************************
    // Muestra la lista de respuestas de incidencias en el telefono
    public void MuestraRespuestaIncidencias(int pidPromotor) {
        int iNumIncidencias = almacenaImagen.ObtenRegistros(23);
        int[] idsinc;
        String[] fechasincidencias;
        String[] tipoincidencias;
        String[] observaciones;
        int[] idrutas;
        String[] tiendas;
        String[] direcciones;
        String[] fechasrespuestasincidencias;
        String[] respuestasincidencias;

        idsinc = almacenaImagen.Obtenidsincidencias(this.pidPromotor);
        fechasincidencias = almacenaImagen.Obtenfechasincidencias(this.pidPromotor);
        tipoincidencias = almacenaImagen.Obtentiposincidencias(this.pidPromotor);
        observaciones = almacenaImagen.Obtenobservacionesincidencias(pidPromotor);
        idrutas = almacenaImagen.ObtenRutasIncidencias(pidPromotor);
        tiendas = almacenaImagen.Obtentiendasincidencias(pidPromotor);
        direcciones = almacenaImagen.Obtendireccionesincidencias(pidPromotor);
        fechasrespuestasincidencias = almacenaImagen.Obtenfechasrespuestaincidencias(this.pidPromotor);
        respuestasincidencias = almacenaImagen.Obtenrespuestaincidencias(this.pidPromotor);

        for (int k = 0; k < iNumIncidencias; k++) {
            idinc[k] = idsinc[k];
            fechasdeincidencias[k] = fechasincidencias[k];
            tiposdeincidencias[k] = tipoincidencias[k];
            observacionesdeincidencias[k] = observaciones[k];
            rutasdeincidencias[k] = idrutas[k];
            tiendasdeincidencias[k] = tiendas[k];
            direccionesdeincidencias[k] = direcciones[k];
            fechasderespuestasincidencias[k] = fechasrespuestasincidencias[k];
            respuestasdeincidencias[k] = respuestasincidencias[k];
        }
        // ******************************
        // Establece la forma de acceso y muestra las tiendas
        MuestraLista();
    }

    // **************************
    // Muestra lista después del proceso
    public void MuestraLista() {
        // Declarar el numero de elementos exacto del areglo
        int iNumIncidencias = almacenaImagen.ObtenRegistros(23);
        int[] idinc1                    = new int[iNumIncidencias];
        String[] fechasdeincidencias1   = new String[iNumIncidencias];
        String[] tiposdeincidencias1    = new String[iNumIncidencias];
        String[] observacionesincidencias1 = new String[iNumIncidencias];
        int[] idrutasincidencias1       = new int[iNumIncidencias];
        String[] tiendasincidencias1    = new String[iNumIncidencias];
        String[] direccionesincidencias1 = new String[iNumIncidencias];
        String[] fechasderespuestasincidencias1    = new String[iNumIncidencias];
        String[] respuestasincidencias1 = new String[iNumIncidencias];

        for (int k = 0; k < iNumIncidencias; k++) {
            idinc1[k] = idinc[k];
            fechasdeincidencias1[k] = "[" + idinc1[k] + "] " + fechasdeincidencias[k];
            tiposdeincidencias1[k] = tiposdeincidencias[k].toUpperCase();
            observacionesincidencias1[k] = observacionesdeincidencias[k].toUpperCase();
            idrutasincidencias1[k] = rutasdeincidencias[k];
            tiendasincidencias1[k] = tiendasdeincidencias[k].toUpperCase();
            direccionesincidencias1[k] = direccionesdeincidencias[k].toUpperCase();
            fechasderespuestasincidencias1[k] = fechasderespuestasincidencias[k].toUpperCase();
            respuestasincidencias1[k] = respuestasdeincidencias[k].toUpperCase();
        }

        if (iNumIncidencias == 0) {
            Toast.makeText(getApplicationContext(), "Este promotor no tiene respuestas de incidencias",
                    Toast.LENGTH_LONG).show();
        }

        // *********************************
        // Llamada al proceso de asignacion del adaptador a la lista de respuesta de incidencias
        AdaptadorRespuestasIncidencias adaptador = new AdaptadorRespuestasIncidencias(
                this,
                idinc1,
                fechasdeincidencias1,
                tiposdeincidencias1,
                observacionesincidencias1,
                idrutasincidencias1,
                tiendasincidencias1,
                direccionesincidencias1,
                fechasderespuestasincidencias1,
                respuestasincidencias1
                );
        lista.setAdapter(adaptador);

/* */
    }
}
