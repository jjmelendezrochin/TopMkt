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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.topmas.top.Objetos.oPromocion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.topmas.top.Constants.TAG_APLICA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDPROMOCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_url;

public class Promocion extends AppCompatActivity {
    // TODO /CatalogoProductos/insertapromocionfecha.php
    public static final String PROMOCION_URL = TAG_SERVIDOR + "/Promociones/insertapromocionfecha.php";

    AlmacenaImagen almacenaImagen;
    RadioButton radio_aplica;
    RadioButton radio_noaplica;
    ProgressDialog pDialog;
    Bitmap imagenespromo = null;
    int idpromocion = 0;
    int idpromotor = 0;
    int idruta = 0;
    int iAplica = 0;
    String url = null;
    private Funciones funciones = new Funciones();
    private final Usuario usr = new Usuario();
    String pName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);

        Intent i = getIntent();
        idpromocion = i.getIntExtra(TAG_IDPROMOCION, 0);
        idpromotor = i.getIntExtra(TAG_IDPROMOTOR, 0);
        idruta = i.getIntExtra(TAG_IDRUTA, 0);
        url = i.getStringExtra(TAG_url);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pName = preferences.getString(TAG_USUARIO, pName);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "Promocion setDefaultUncaughtExceptionHandler", (Exception) throwable, Promocion.this, getApplicationContext());
        });

        //Log.e(TAG_ERROR, " Url obtenido " + url);

        // ****************************
        // Descarga imágen
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    imagenespromo = downloadFile(url);
                } catch (Exception e) {
                    funciones.RegistraError(pName, "Promocion,OnCreate", e, Promocion.this, getApplicationContext());
                    // e.printStackTrace();
                }
            }
        });
        thread.start();

        // ******************************
        // Obtiene los datos de la promocion
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        oPromocion promo = almacenaImagen.ObtenPromocionTienda(idpromocion);
        int iRes = almacenaImagen.consulta_promocion_tienda(idpromocion,idpromotor, idruta);

        // Log.e(TAG_INFO, "Valor obtenido de aplica " + iRes);

        radio_aplica =  findViewById(R.id.radio_aplica);
        radio_noaplica =  findViewById(R.id.radio_noaplica);
        switch (iRes) {
            case -1:
                radio_aplica.setChecked(false);
                radio_noaplica.setChecked(false);
                break;
            case 1:
                radio_aplica.setChecked(true);
                radio_noaplica.setChecked(false);
                break;
            case 0:
                radio_aplica.setChecked(false);
                radio_noaplica.setChecked(true);
                break;
        }

        TextView cajanombre = this.findViewById(R.id.txtNombre);
        TextView cajacapacidad = this.findViewById(R.id.txtCapacidad);
        TextView cajacanal = this.findViewById(R.id.txtCanal);
        TextView cajaalcance = this.findViewById(R.id.txtAlcance);
        TextView cajainicio = this.findViewById(R.id.txtInicio);
        TextView cajafinal = this.findViewById(R.id.txtFinal);
        TextView cajaperiodo = this.findViewById(R.id.txtPeriodo);
        TextView cajaactividad = this.findViewById(R.id.txtActividad);
        TextView cajaprecioregular = this.findViewById(R.id.txtPrecioRegular);
        TextView cajapreciopromocion = this.findViewById(R.id.txtPrecioPromocion);


        LinearLayout.LayoutParams lp;

        cajanombre.setText(promo.get_nombre());
        cajanombre.setVisibility(View.VISIBLE);
        cajacapacidad.setText(promo.get_capacidad());
        cajacapacidad.setVisibility(View.VISIBLE);
        cajacanal.setText(promo.get_canal());
        cajacanal.setVisibility(View.VISIBLE);
        cajaalcance.setText(promo.get_alcance());
        cajaalcance.setVisibility(View.VISIBLE);
        cajainicio.setText(promo.get_inicio());
        cajainicio.setVisibility(View.VISIBLE);
        cajafinal.setText(promo.get_final());
        cajafinal.setVisibility(View.VISIBLE);
        cajaperiodo.setText(promo.get_periodo());
        cajaperiodo.setVisibility(View.VISIBLE);

        TextView Titulo8 = findViewById(R.id.Titulo8);
        lp = (LinearLayout.LayoutParams) Titulo8.getLayoutParams();
        double margen =  lp.leftMargin + lp.width;
        //Log.e(TAG_ERROR, "margen " +  margen);

        cajaactividad.setText(promo.get_actividad());
        cajaactividad.setVisibility(View.VISIBLE);
        TextView Titulo9 = findViewById(R.id.Titulo9);
        lp = (LinearLayout.LayoutParams) Titulo9.getLayoutParams();
        margen =  lp.leftMargin + lp.width;
        //Log.e(TAG_ERROR, "margen " +  margen);

        cajaprecioregular.setText(promo.get_precioregular());
        cajaprecioregular.setVisibility(View.VISIBLE);
        cajapreciopromocion.setText(promo.get_preciopromocion());
        cajapreciopromocion.setVisibility(View.VISIBLE);

       /* ImageView imagen1 = this.findViewById(R.id.imagenproducto1);
        imagen1.setImageBitmap(imagenespromo);
        imagen1.setVisibility(View.VISIBLE);
*/
        // ********************************************
        // Icono de salir de lista productos
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finish();
                } catch (Throwable throwable) {
                    funciones.RegistraError(pName, "Promocion,OnCreate", (Exception) throwable, Promocion.this, getApplicationContext());
                    //throwable.printStackTrace();
                }
            }
        });

        // ********************************************
        // Boton de precio
        Button cmdGuardarPrecio = this.findViewById(R.id.cmdGuardarPrecio);
        cmdGuardarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    boolean baplica = radio_aplica.isChecked();
                    boolean bnoaplica = radio_noaplica.isChecked();
                    if(!baplica && !bnoaplica){
                        Toast.makeText(getApplicationContext(), "Favor de seleccionar una opción", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(baplica){
                        iAplica = 1;
                    }
                    else{
                        iAplica = 0;
                    }

                    if (funciones.RevisarConexion(getApplicationContext())) {
                        // *****************************************
                        // Guardar los datos en la plataforma
                        establecepromocion();
                    }
                    else {
                        // *****************************************
                        // Debe de guardar el precio al producto en la tabla local
                        int iRes = almacenaImagen.inserta_promocion_tienda(idpromocion, idpromotor, idruta, iAplica, 1);
                        String mensaje;
                        if (iRes > 0) {
                            mensaje = "Dato guardado";
                        } else {
                            mensaje = "Error al guardar";
                        }
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                        finish();
                    }


                } catch (Throwable throwable) {
                    funciones.RegistraError(pName, "Promocion, Guardar Precio", (Exception) throwable, Promocion.this, getApplicationContext());
                    // throwable.printStackTrace();
                }
            }
        });
    }


    //***********************
    // Función utilizada para guardar el precio del producto
    private void establecepromocion() {
        class EstablecePromocion extends AsyncTask<Void, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Promocion.this);
                pDialog.setMessage("Guardando promoción ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> data = new HashMap<>();

                data.put(TAG_IDPROMOCION, String.valueOf(idpromocion));
                data.put(TAG_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(TAG_IDEMPRESA, String.valueOf(1));
                data.put(TAG_IDRUTA, String.valueOf(idruta));
                data.put(TAG_APLICA, String.valueOf(iAplica));
                return rh.sendPostRequest(PROMOCION_URL, data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                // **************************************
                // Debe de guardar el precio al producto en la tabla local
                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iRes = almacenaImagen.inserta_promocion_tienda(idpromocion,idpromotor,idruta, iAplica, 0);
                String mensaje;
                if (iRes > 0) {
                    mensaje = "Dato guardado";
                } else {
                    mensaje = "Error al guardar";
                }
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                finish();
                // **************************************
            }
        }

        // *****************************************
        // Ejecuta procedimiento
        try {
            EstablecePromocion ep = new EstablecePromocion();
            //Fija el valor obtenido en la caja de texto
            ep.execute();
        } catch (java.lang.NullPointerException e) {
            // funciones.RegistraError(pName, "Promocion, establecepromocion", e, Promocion.this, getApplicationContext());
            Toast.makeText(getApplicationContext(), "Error al tratar de establecer la promocion", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap downloadFile(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap loadedImage;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            return loadedImage;
        } catch (IOException e) {
            funciones.RegistraError(pName, "Promocion, downloadFile", e, Promocion.this, getApplicationContext());
            // e.printStackTrace();
            return null;
        }
    }
}