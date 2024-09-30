package com.topmas.top;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.topmas.top.Objetos.oRespuestaIncidencia;

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
    int[] leida = new int[iCuentaIncidencias];

    int iNumIncidencias = 0;

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
        // txtBuscar = (findViewById(R.id.txtBuscar));

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
        iNumIncidencias = almacenaImagen.ObtenRegistros(23);
        // oRespuestaIncidencia[] respuestas =  almacenaImagen.obtenRespuestaIncidencias();

        // ******************************
        // Establece la forma de acceso y muestra las tiendas
        idinc = almacenaImagen.Obtenidsincidencias(this.pidPromotor);
        leida = almacenaImagen.obtenLeidaincidencias(this.pidPromotor);
        fechasdeincidencias = almacenaImagen.Obtenfechasincidencias(this.pidPromotor);
        tiposdeincidencias = almacenaImagen.Obtentiposincidencias(this.pidPromotor);
        observacionesdeincidencias = almacenaImagen.Obtenobservacionesincidencias(this.pidPromotor);
        MuestraLista();
    }

    // **************************
    // Muestra lista después del proceso
    public void MuestraLista() {
        if (iNumIncidencias == 0) {
            Toast.makeText(getApplicationContext(), "Este promotor no tiene respuestas de incidencias",
                    Toast.LENGTH_LONG).show();
        }

        // *********************************
        // Llamada al proceso de asignacion del adaptador a la lista de respuesta de incidencias
        AdaptadorRespuestasIncidencias adaptador = new AdaptadorRespuestasIncidencias(
                this,
                idinc,
                leida,
                fechasdeincidencias,
                tiposdeincidencias,
                observacionesdeincidencias
                );
        lista.setAdapter(adaptador);

/* */
    }
}
