package com.topmas.top;

import android.annotation.SuppressLint;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static com.topmas.top.Constants.CONST_ACCESOLOCAL;
import static com.topmas.top.Constants.TAG_CATEGORIAPRODUCTO;
import static com.topmas.top.Constants.TAG_DESCRIPCIONPRODUCTO;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_PRODUCTO;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_USUARIO;


public class listaproductos extends AppCompatActivity {

    int pidPromotor = 0;
    int pidRuta = 0;
    String pidEmpresa = "0";
    Double platitud = 0.0;
    Double plongitud = 0.0;
    String pdireccion = "";
    String ptienda = "";
    String idUsuario = "";
    EditText txtBuscar;

    private Usuario usr = new Usuario();
    private Funciones funciones = new Funciones();
    private ListView lista;

    private AlmacenaImagen almacenaImagen;
    private Acceso formaacceso;
    private ProgressDialog pDialog;

    // Este numero debe de ser el numero de registros de la tabla
    int iCuentaProductos = 50;
    int[] idproducto = new int[iCuentaProductos];
    int[] idruta = new int[iCuentaProductos];
    String[] descripcionproducto = new String[iCuentaProductos];
    String[] categoriaproducto = new String[iCuentaProductos];
    String[] upc = new String[iCuentaProductos];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaproductos);
        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        idUsuario = preferencias.getString(TAG_USUARIO, usr.getnombre());
        String spromotor = preferencias.getString(TAG_IDPROMOTOR, String.valueOf(usr.getid()));
        pidPromotor = Integer.valueOf(spromotor);
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

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(idUsuario, "listaproductos setDefaultUncaughtExceptionHandler", (Exception) throwable, listaproductos.this, getApplicationContext());
        });

        //****************************
        // Evento click al adaptador
        lista.setOnItemClickListener(new OnItemClickListenerAdaptadorProductosTiendas());

        /*
        // *****************************
        // Si hay conexion a internet obtiene los datos
        if (funciones.RevisarConexion(getApplicationContext())) {
            // **************************
            // Llamado a la consulta del servicio web si hay internet
            ConsultaProductos consulta = new ConsultaProductos();
            consulta.execute();
        } else {
            // **************************
            // Llamado a la consulta del servicio desde una consulta local
            MuestraProductosTelefono(pidRuta);
        }
        */

        MuestraProductosTelefono(pidRuta);
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
                if (funciones.RevisarConexion(getApplicationContext())) {
                    // Llamado a la consulta del servicio web
                    ConsultaProductos consulta = new ConsultaProductos();
                    consulta.execute();
                } else {
                    // **************************
                    // Llamado a la consulta del servicio desde una consulta local
                    MuestraProductosTelefono(pidRuta);
                }
            }
        });
        // **************************
    }

    //************************************
    // Clase que consulta las productos
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    //************************************
    // Clase que consulta las productos
    @SuppressLint("StaticFieldLeak")
    public class ConsultaProductos extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;

        int[] idproducto2 ;
        int[] idruta2 ;
        String[] descripcionproducto2;
        String[] upc2;


        @Override
        protected void onPreExecute() {
            String sProducto = txtBuscar.getText().toString().trim();
            // TODO /CatalogoProductos/obtenerproductostienda1.php
            if (sProducto.equals("")) {
                sRuta = TAG_SERVIDOR + "/CatalogoProductos/obtenerproductostienda1.php?idruta=" + pidRuta + "&producto=%&idempresa=" + pidEmpresa;
            } else {
                sRuta = TAG_SERVIDOR + "/CatalogoProductos/obtenerproductostienda1.php?idruta=" + pidRuta + "&producto=%" + sProducto + "%&idempresa=" + pidEmpresa;
            }
            // Log.e(TAG_ERROR,  "La ruta de la api usada es:" + sRuta);

            //Log.e("Error", "La ruta de la api usada es:" + sRuta);
            super.onPreExecute();
            pDialog = new ProgressDialog(listaproductos.this);
            pDialog.setMessage("Consultando en el servicio Web ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            BufferedReader reader = null;
            String sRespusta = null;
            try {
                // Defined URL where to send data
                URL url = new URL(sRuta);

                // Send POST data request
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response
                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line).append(" ");
                }

                // Append Server Response To Content String
                sRespusta = sb.toString();

            } catch (Exception ex) {
                funciones.RegistraError(idUsuario, "listaproductos ,MuestraImagen 1", ex, listaproductos.this,getApplicationContext());
                Error = ex.getMessage();
            } finally {
                try {
                    assert reader != null;
                    reader.close();
                } catch (Exception ex) {
                    funciones.RegistraError(idUsuario, "listaproductos ,MuestraImagen 2", ex, listaproductos.this,getApplicationContext());
                    Error = ex.getMessage();
                }
            }


            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                String Resultado = "Se generó el siguiente error : " + Error;
                // Log.e(TAG_ERROR, Resultado);
            } else {
                try {

                    JSONObject jsonResponse, jsonChidNode, jsonObjRuta;
                    JSONArray jsonarray;
                    // Revisando la respusta
                    assert sRespusta != null;
                    jsonResponse = new JSONObject(sRespusta);
                    //Log.e("AQUI", sRespusta.toString());
                    jsonarray = jsonResponse.getJSONArray(TAG_RESPUESTA);       // Arreglo
                    iCuentaProductos = jsonarray.length();
                    // iCuentaProductos = almacenaImagen.ObtenRegistros(1);
                    // Log.e(TAG_ERROR, " ** Cuenta Productos " + iCuentaProductos );

                    idproducto2 = new int[iCuentaProductos];
                    idruta2 = new int[iCuentaProductos];
                    descripcionproducto2 = new String[iCuentaProductos];
                    upc2 = new String[iCuentaProductos];

                    // ******************************************
                    // Ciclo de obtención de datos del servicio web
                    for (int i = 0; i < iCuentaProductos; i++) {
                        jsonChidNode = jsonarray.getJSONObject(i);
                        jsonObjRuta = jsonChidNode.getJSONObject(TAG_PRODUCTO);
                        // Colocacion de datos en los arreglos
                        idruta2[i] = Integer.parseInt(jsonObjRuta.getString(TAG_IDRUTA));
                        idproducto2[i] = Integer.parseInt(jsonObjRuta.getString(TAG_IDPRODUCTO));
                        descripcionproducto2[i] = jsonObjRuta.getString(TAG_DESCRIPCIONPRODUCTO);
                        //categoriaproducto2[i] = jsonObjRuta.getString(TAG_CATEGORIAPRODUCTO);
                        upc2[i] = jsonObjRuta.getString(TAG_UPC);
                    }


                } catch (JSONException e) {
                    funciones.RegistraError(idUsuario, "listaproductos, ConsultaProductos", e, listaproductos.this,getApplicationContext());
                    String Resultado = "Se generó el siguiente error : " + e.toString();

                    // Log.e(TAG_ERROR,Resultado);
                }
            }
            // **************************
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            //MuestraLista1();
            AdaptadorProductosTienda adaptador = new AdaptadorProductosTienda(
                    listaproductos.this,
                    idproducto2,
                    idruta2,
                    descripcionproducto2,
                    upc2);
            lista.setAdapter(adaptador);
        }
    }

    // **************************
    // Muestra lista después obtenida de consulta web
    public void MuestraLista1(){
        // Llamada al proceso de asignacion del adaptador a la lista

    }

    //************************************
    // Muestra la lista de las tiendas registradas en el teléfono
    public void MuestraProductosTelefono(int pidRuta) {
        int iNumProductos = almacenaImagen.ObtenRegistros(1);

        int[] idsproducto;
        String[] descripcionesproducto;
        String[] categoriasproducto;
        String[] upcsproducto;

        idsproducto = almacenaImagen.Obtenidsproducto();
        descripcionesproducto = almacenaImagen.Obtendescripcionesproducto();
        categoriasproducto = almacenaImagen.Obtencategoriasproducto();
        upcsproducto = almacenaImagen.Obtenupcsproducto();

        for (int k = 0; k < iNumProductos; k++) {
            idproducto[k] = idsproducto[k];
            idruta[k] = pidRuta;
            categoriaproducto[k] = categoriasproducto[k];
            descripcionproducto[k] = descripcionesproducto[k];
            upc[k] = upcsproducto[k];
        }
        // ******************************
        // Establece la forma de acceso y muestra las tiendas
        Acceso.EstableceAcceso(CONST_ACCESOLOCAL);
        MuestraLista();
    }

    // **************************
    // Muestra lista después del proceso
    public void MuestraLista() {
        // Declarar el numero de elementos exacto del areglo

        int iCuentaProductos = almacenaImagen.ObtenRegistros(1);
        int[] idproducto1 = new int[iCuentaProductos];
        int[] idruta1 = new int[iCuentaProductos];
        String[] descripcionproducto1 = new String[iCuentaProductos];
        String[] categoriaproducto1 = new String[iCuentaProductos];
        String[] upc1 = new String[iCuentaProductos];
        final Bitmap[] imagenesprod = new Bitmap[iCuentaProductos];

        for (int k = 0; k < iCuentaProductos; k++) {
            idproducto1[k] = idproducto[k];
            idruta1[k] = idruta[k];
            categoriaproducto1[k] = categoriaproducto[k];
            descripcionproducto1[k] = descripcionproducto[k] + " [" + upc[k] + "] ";
            upc1[k] = upc[k];

            // Lista los productos
            // Log.e(TAG_ERROR, descripcionproducto1[k]);
        }

        if (iCuentaProductos == 0) {
            Toast.makeText(getApplicationContext(), "Esta tienda no tiene productos",
                    Toast.LENGTH_LONG).show();
        }

        // Llamada al proceso de asignacion del adaptador a la lista
        AdaptadorProductosTienda adaptador = new AdaptadorProductosTienda(
                this,
                idproducto1,
                idruta1,
                descripcionproducto1,
                upc1);
        lista.setAdapter(adaptador);
    }
}