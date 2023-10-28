package com.topmas.top;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDFORMATO;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;


public class listapromociones extends AppCompatActivity {

    int pidPromotor = 0;
    int pidRuta = 0;
    int pidformato = 0;
    Double platitud = 0.0;
    Double plongitud = 0.0;
    String pdireccion = "";
    String ptienda = "";

    String pName = "";
    EditText txtBuscar ;

    private Usuario usr = new Usuario();
    private Funciones funciones = new Funciones();
    private ListView lista;
    private int iLongitudArreglo;

    private AlmacenaImagen almacenaImagen;
    private ProgressDialog pDialog;
    private Bitmap bitmap1 = null;

    // Este numero debe de ser el numero de registros de la tabla
    int iCuentaProductos  = 50;
    int[] idproducto = new int[iCuentaProductos];
    int[] idruta = new int[iCuentaProductos];
    String[] descripcionproducto = new String[iCuentaProductos];
    String[] categoriaproducto = new String[iCuentaProductos];
    String[] upc = new String[iCuentaProductos];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapromociones);

        //View view = this.findViewById(R.id.LinearLayout);
        lista =  findViewById(R.id.lista1);
        txtBuscar = (findViewById(R.id.txtBuscar));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pName = preferences.getString(TAG_USUARIO, pName);

        Intent i = getIntent();
        pidRuta = i.getIntExtra(TAG_IDRUTA, pidRuta );
        ptienda = i.getStringExtra(TAG_TIENDA);
        platitud = i.getDoubleExtra(TAG_LATITUD, 0.0);
        plongitud = i.getDoubleExtra(TAG_LONGITUD, 0.0);
        pdireccion = i.getStringExtra(TAG_DIRECCION);
        pidPromotor = usr.getid();
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        pidformato = i.getIntExtra(TAG_IDFORMATO, pidformato);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "listapromociones setDefaultUncaughtExceptionHandler", (Exception) throwable, listapromociones.this, getApplicationContext());
        });

        // Log.e(TAG_ERROR,  " ** idformato " + pidformato);

        //****************************
        // Evento click al adaptador
        lista.setOnItemClickListener(new OnItemClickListenerAdaptadorPromociones());

        MuestraListaPromociones();
        //****************************
        // Icono de salir de lista productos
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MenuTienda = new Intent(getApplicationContext(), com.topmas.top.MenuTienda.class);
                MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(pidRuta));
                MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
                MenuTienda.putExtra(TAG_TIENDA, ptienda);
                MenuTienda.putExtra(TAG_LATITUD, platitud);
                MenuTienda.putExtra(TAG_LONGITUD, plongitud);
                MenuTienda.putExtra(TAG_DIRECCION, pdireccion);
                startActivity(MenuTienda);
            }
        });

        // **************************
        ImageButton imgboton = findViewById(R.id.imgBuscar);
        imgboton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
        // **************************
    }

    // **************************
    // Muestra lista después del proceso
    public void MuestraListaPromociones(){
        // Declarar el numero de elementos exacto del areglo

        int iNumPromociones = almacenaImagen.ObtenPromocionesFormato(pidformato);
        /*Log.e(TAG_ERROR, "*** Numero de promociones " + iNumPromociones);*/
        oPromocion[] promociones = almacenaImagen.ObtenPromociones(pidformato);

        String[] Promociones = new String[iNumPromociones];
        String[] DatosPromociones = new String[iNumPromociones];
        final String[] Urls = new String[iNumPromociones];
        int[] idRutas = new int[iNumPromociones];
        int[] idPromotores = new int[iNumPromociones];
        int[] idPromociones = new int[iNumPromociones];
        final Bitmap[] imagenespromo  = new Bitmap[iNumPromociones];

        for(int k=0; k<iNumPromociones; k++){
            oPromocion promocion;
            promocion = promociones[k];
            idPromociones[k]    =  promocion.get_idpromocion();
            Promociones[k]      =  promocion.get_promo().toUpperCase()  + ", " + promocion.get_actividad().toUpperCase() + ", " + promocion.get_alcance().toUpperCase();
            DatosPromociones[k] =  promocion.get_canal().toUpperCase() + ", " + promocion.get_preciopromocion().toUpperCase() + ", " + promocion.get_capacidad().toUpperCase();
            idRutas[k]          =  pidRuta;
            idPromotores[k]     =  pidPromotor;
            Urls[k]             =  promocion.get_url();

            final int finalK = k;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        imagenespromo[finalK] = downloadFile(Urls[finalK]);
                    } catch (Exception e) {
                        funciones.RegistraError(pName, "listapromociones, MuestraListaPromociones", e, listapromociones.this, getApplicationContext());
                        // e.printStackTrace();
                    }
                }
            });
            thread.start();

        }

        if(iNumPromociones==0){
            Toast.makeText( getApplicationContext(),"Este formato no tiene promociones",
                    Toast.LENGTH_LONG).show();
        }


        // Llamada al proceso de asignacion del adaptador a la lista
        AdaptadorPromociones adaptador = new AdaptadorPromociones(
                this,
                idPromociones,
                idRutas,
                idPromotores,
                Promociones,
                DatosPromociones,
                imagenespromo,
                Urls

        );
        lista.setAdapter(adaptador);
    }

    private  Bitmap downloadFile(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap loadedImage;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            return loadedImage;
        } catch (IOException e) {
            funciones.RegistraError(pName, "listapromociones, downloadFile", e, listapromociones.this, getApplicationContext());
            e.printStackTrace();
            return null;
        }
    }

}