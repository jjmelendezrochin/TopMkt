package com.topmas.top;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.topmas.top.Adaptadores.AdaptadorTiendasPromotor;
import com.topmas.top.Adaptadores.OnItemClickListenerAdaptadorTiendasPromotor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import static com.topmas.top.Constants.TAG_ACCESSTOKEN;
import static com.topmas.top.Constants.TAG_ACTIV;
import static com.topmas.top.Constants.TAG_ACTIVIDAD;
import static com.topmas.top.Constants.TAG_ALCANCE;
import static com.topmas.top.Constants.TAG_CADENA;
import static com.topmas.top.Constants.TAG_CANAL;
import static com.topmas.top.Constants.TAG_CANTIDADCAJA;
import static com.topmas.top.Constants.TAG_CANTIDADKGS;
import static com.topmas.top.Constants.TAG_CAPACIDAD;
import static com.topmas.top.Constants.TAG_CATEGORIA1;
import static com.topmas.top.Constants.TAG_CATEGORIA2;
import static com.topmas.top.Constants.TAG_CONSULTAENWEB;
import static com.topmas.top.Constants.TAG_DESCRIPCION;
import static com.topmas.top.Constants.TAG_DESCRIPCION1;
import static com.topmas.top.Constants.TAG_DETERMINANTE;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_EMAIL;
import static com.topmas.top.Constants.TAG_EMPAQUE;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_EXPIRESIN;
import static com.topmas.top.Constants.TAG_FDA;
import static com.topmas.top.Constants.TAG_FDC;
import static com.topmas.top.Constants.TAG_FECHAVISITA;
import static com.topmas.top.Constants.TAG_FINAL;
import static com.topmas.top.Constants.TAG_IDACTIVIDAD;
import static com.topmas.top.Constants.TAG_IDCADENA;
import static com.topmas.top.Constants.TAG_IDEMPAQUE;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDFORMATO;
import static com.topmas.top.Constants.TAG_IDOBS;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDPRODUCTOFORMATOPRECIO;
import static com.topmas.top.Constants.TAG_IDPROMOCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_INICIO;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_NAME;
import static com.topmas.top.Constants.TAG_NOMBRE;
import static com.topmas.top.Constants.TAG_NOMBRECORTO;
import static com.topmas.top.Constants.TAG_OBSERV;
import static com.topmas.top.Constants.TAG_OBSERVACIONES;
import static com.topmas.top.Constants.TAG_PERIODO;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRECIOPROMOCION;
import static com.topmas.top.Constants.TAG_PRECIOREAL;
import static com.topmas.top.Constants.TAG_PRECIOREGULAR;
import static com.topmas.top.Constants.TAG_PROD;
import static com.topmas.top.Constants.TAG_PROD_FTO;
import static com.topmas.top.Constants.TAG_PROMO;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_RUTA;
import static com.topmas.top.Constants.TAG_RUTA_ARCHIVO;
import static com.topmas.top.Constants.TAG_RUTA_CAT;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_SOLICITAINV;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_UDA;
import static com.topmas.top.Constants.TAG_UDC;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_VERSIONAPP;
import static com.topmas.top.Constants.TAG_VISTA;
import static com.topmas.top.Constants.TAG_empaque;
import static com.topmas.top.Constants.TAG_ruta;
import static com.topmas.top.Constants.TAG_solicita;

public class listatiendas extends AppCompatActivity {

    boolean tiendasCargadas=false;
    int pidPromotor = 0;
    String pIdempresa = "";
    int pConsultaenWeb = 0;
    String pName = "";
    String pEmail = "";
    String pToken = "";
    String pExpira = "";
    EditText txtBuscar ;
    String sMensaje="";

    private static Activity context;
    private ProgressDialog pDialog;
    private final Usuario usr = new Usuario();
    private Funciones funciones = new Funciones();
    private ListView lista;
    private int iLongitudArreglo;
    //private int iLongitudArregloTiendas;
    private AlmacenaImagen almacenaImagen;
    private int iNumTiendas = 0;
    // private ProgressBar progressBar;

    // *******************************
    // Variables lista de tiendas
    int[] ruta = new int[1000];
    int[] idruta = new int[1000];
    int[] idruta2 = new int[2000];

    String[] determinante = new String[1000];
    String[] tienda = new String[1000];
    String[] direccion = new String[1000];
    Double[] latitud = new Double[1000];
    Double[] longitud = new Double[1000];
    String[] fechavisita = new String[1000];
    // *******************************
    // Variables productos
    int[] idproducto = new int[1000];
    int[] idproducto1 = new int[1000];
    int[] idproducto2 = new int[2000];

    String[] upc = new String[1000];
    String[] descripcion = new String[1000];
    String[] descripcion1 = new String[1000];
    int[] cantidad_caja = new int[1000];
    Double[] cantidad_kgs = new Double[1000];
    int[] idempresa = new int[1000];
    String[] categoria1 = new String[1000];
    String[] categoria2 = new String[1000];
    String[] udc = new String[1000];
    String[] fdc = new String[1000];
    String[] uda = new String[1000];
    String[] fda = new String[1000];
    String[] udc1 = new String[1000];
    String[] fdc1 = new String[1000];
    String[] uda1 = new String[1000];
    String[] fda1 = new String[1000];

    String[] ruta_archivo = new String[1000];
    // *******************************
    // Variables formatoprecio
    int[] idproductoformatoprecio = new int[1000];
    int[] idformato = new int[1000];
    int[] idformato1 = new int[1000];
    int[] idcadena = new int[1000];
    Double[] precio = new Double[1000];
    // *******************************
    // Variables vista vw_producto_ruta_fecha
    Double[] precioreal = new Double[2000];
    String[] fda2 = new String[2000];
    String[] nombrecorto = new String[1000];
    // *******************************
    // Variables observaciones
    int[] idobs = new int[200];
    String[] observaciones = new String[200];
    // *******************************
    // Variables promociones
    int[] idpromocion = new int[200];
    int[] idempresa1 = new int[200];
    String[] nombre = new String[200];
    String[] capacidad = new String[200];
    String[] canal = new String[200];
    String[] alcance = new String[200];
    String[] inicio = new String[200];
    String[] fina = new String[200];
    String[] periodo = new String[200];
    String[] actividad = new String[200];
    String[] precioregular = new String[200];
    String[] preciopromocion = new String[200];
    String[] empaque = new String[200];
    String[] ruta1 = new String[200];
    int[] idformato2 = new int[200];
    int[] idproducto3 = new int[200];
    int[] idactividad = new int[200];
    int[] idempaque = new int[200];
    int[] solicita = new int[1];

    String sTienda = "";

    // TextView textoAvance;

    // LinearLayout LayoutProgreso;
    // LinearLayout LayoutProgreso1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listatiendas);
        context = listatiendas.this;
        //View view = this.findViewById(R.id.LinearLayout);
        lista =  findViewById(R.id.lista1);
        txtBuscar = findViewById(R.id.txtBuscar);
        // progressBar =  findViewById(R.id.progressBar);
        // textoAvance = findViewById(R.id.textoAvance);
        // LayoutProgreso = findViewById(R.id.LayoutProgreso);
        // LayoutProgreso1 = findViewById(R.id.LayoutProgreso1);

        Intent i = getIntent();
        pidPromotor = i.getIntExtra(TAG_IDPROMOTOR,pidPromotor );
        pIdempresa = i.getStringExtra(TAG_IDEMPRESA);
        // Log.e(TAG_ERROR,"obtiene empresa pasada por parametro" + pIdempresa);
        pName = i.getStringExtra(TAG_NAME);
        pEmail = i.getStringExtra(TAG_EMAIL);
        pToken = i.getStringExtra(TAG_ACCESSTOKEN);
        pExpira = i.getStringExtra(TAG_EXPIRESIN);
        pConsultaenWeb = i.getIntExtra(TAG_CONSULTAENWEB, pConsultaenWeb);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            funciones.RegistraError(pName, "listatiendas setDefaultUncaughtExceptionHandler", (Exception) throwable, listatiendas.this, getApplicationContext());
        });

        // Obtiene el idpromotor si no trae este valor
        // Log.e(TAG_ERROR, "* Promotor " + pidPromotor);
        if (pidPromotor==0)
        {
            pidPromotor = usr.getid();
            // Log.e(TAG_ERROR, "* Promotor " + pidPromotor);
        }
        // Si no hay promotor debe de mandar un mensaje de error y solicitar ingresar
        if(pidPromotor==0)
        {
            sMensaje = "No se pudo obtener la información del promotor favor de salir e ingresar nuevamente";
            AlertDialog.Builder alerta = new AlertDialog.Builder(listatiendas.this);
            alerta.setMessage(sMensaje)
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent main = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(main);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            try {
                alerta.show();
            }
            catch (WindowManager.BadTokenException e) {
                Toast.makeText(getApplicationContext(), sMensaje,Toast.LENGTH_LONG);
            }

            return;
        }
        // Establece el idusuario
        usr.setidusuario(pName);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            funciones.RegistraError(pName, "listatiendas setDefaultUncaughtExceptionHandler", (Exception) throwable, listatiendas.this, getApplicationContext());
        });

        try {
            final AdaptadorTiendasPromotor adaptador =
                    new AdaptadorTiendasPromotor(listatiendas.this,
                    ruta,
                    determinante,
                    tienda,
                    direccion,
                    latitud,
                    longitud);
            lista.setAdapter(adaptador);
        } catch (Exception e) {
            funciones.RegistraError(pName, "MainActivity,ConsultaWebService", e, listatiendas.this, getApplicationContext());
            //e.printStackTrace();
        }

        // Log.e(TAG_ERROR, "PIDPROMOTOR "+ pidPromotor);
        // Log.e(TAG_ERROR, "STIENDA "+ sTienda);
        // *****************************
        // Numero de tiendas en la tabla
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        iNumTiendas = almacenaImagen.ObtenRegistrosTiendas(pidPromotor, sTienda);
        // iLongitudArregloTiendas= iNumTiendas;
        //Log.e(TAG_ERROR,"** Número de tiendas " + iLongitudArregloTiendas );

        // *****************************
        // Inicia Geolocalizaciòn
        funciones.locationStart(this);

        //****************************
        // Icono de salir de lista tiendas
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iCuenta = almacenaImagen.ObtenRegistros(0);
                int iCuentaPreciosCambiados = almacenaImagen.ObtenRegistros(9);
                int iCuentaPromociones = almacenaImagen.ObtenRegistros(10);
                int iCuentaRegistrosCompetencia = almacenaImagen.ObtenRegistros(12);
                int iCuentaCaducidad = almacenaImagen.ObtenRegistros(14);
                int iCuentaErrores = almacenaImagen.ObtenRegistros(16);
                int iCuentaCompetenciaPromocion = almacenaImagen.ObtenRegistros(18);
                int iCuentaCanjes = almacenaImagen.ObtenRegistros(20);
                int iPendientes = (iCuenta+iCuentaPreciosCambiados+iCuentaPromociones+iCuentaRegistrosCompetencia+
                        iCuentaCaducidad+iCuentaErrores+iCuentaCompetenciaPromocion+iCuentaCanjes);

                if (iPendientes>0) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(listatiendas.this);
                    String sMensaje = "Usted tiene " + "\n" + String.valueOf(iCuenta) + " imágenes almacenadas "  + "\n" +
                            iCuentaPreciosCambiados + " precios cambiados, "  + "\n" +
                            iCuentaPromociones + " promociones, "  + "\n" +
                            iCuentaRegistrosCompetencia + " datos de competencia,"  + "\n" +
                            iCuentaCaducidad  + " datos de caducidad, "  + "\n" +
                            iCuentaCompetenciaPromocion + " datos de competencia promoción, "  + "\n" +
                            iCuentaErrores  + " datos de error(es) "  + "\n" +
                            iCuentaCanjes + " datos de canjes" + "\n" +
                            "No olvide conectarse en cuanto tenga señal suficiente, para colocar sus fotos en plataforma" + "\n" +
                            "(pulsar SI para salir)";

                    alerta.setMessage(sMensaje)
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    funciones.limpiaVariablessesion(getApplicationContext());
                                    Intent main = new Intent(getApplicationContext(),
                                            MainActivity.class);
                                    startActivity(main);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alerta.show();
                }
                else
                {
                    funciones.limpiaVariablessesion(getApplicationContext());
                    Intent main = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(main);
                }
            }
        });

        //****************************
        FloatingActionButton fab1 = findViewById(R.id.fab1);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                almacenaImagen.muestradatosAlmacenados();
            }
        });

        //****************************
        // Evento click al adaptador
        lista.setOnItemClickListener(new OnItemClickListenerAdaptadorTiendasPromotor());


        // TODO ****************************
        // TODO AQUI SE VERIFICA SI HAY CONEXION Y NO HAY TIENDAS CARGADAS ENTONCES LOS DATOS SE ACTUALIZAN DE LA PLATAFORMA DE OTRA FORMA SE CARGARAN SIEMPRE DE LA LISTA DE TIENDAS YA CARGADAS
        // TODO ****************************
        // Si hay conexion a internet obtiene los datos
        // Modificación para que si tiene tiendas siempre las cargue desde
        // el telefono y no consulte información en la web
        if (funciones.RevisarConexion(getApplicationContext())
                && pConsultaenWeb>0
                && iNumTiendas==0)
        {
            // **************************
            // Llamado a la consulta del servicio web si hay internet
            ConsultaTiendas consulta = new ConsultaTiendas();
            consulta.execute();
        }
        else
        {
            // ******************************************
            // Si hay tiendas en la tabla de tiendas entonces debe de leerlas y colocarlas
            // en los arreglos
            //Log.e(TAG_ERROR, "iNumTiendas " + iNumTiendas);
            if (iNumTiendas > 0) {
                sTienda= txtBuscar.getText().toString().trim();
                MuestraTiendasTelefono(sTienda);
            }
            else{
                Toast.makeText(listatiendas.this, "No hay conexión a internet, favor de verificar", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // **************************
        ImageButton imgboton = findViewById(R.id.imgBuscar);
        imgboton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                sTienda= txtBuscar.getText().toString().trim();
                MuestraTiendasTelefono(sTienda);

                /*
                if (funciones.RevisarConexion(getApplicationContext())) {
                    // Llamado a la consulta del servicio web
                    ConsultaTiendas consulta = new ConsultaTiendas();
                    consulta.execute();
                }
                else{
                    sTienda= txtBuscar.getText().toString().trim();
                    // Log.e(TAG_ERROR, "iNumTiendas " + iNumTiendas);
                    // Log.e(TAG_ERROR, "Tiendas " + sTienda);
                    MuestraTiendasTelefono(sTienda);
                }
                 */
            }
        });




    }

    //************************************
    // Muestra la lista de las tiendas registradas en el teléfono
    public void MuestraTiendasTelefono(String sTienda){

        try {
            int[] Rutas = almacenaImagen.ObtenRutas(pidPromotor, sTienda);
            String[] Determinantes = almacenaImagen.ObtenDeterminantes(pidPromotor, sTienda);
            String[] Tiendas = almacenaImagen.ObtenTiendas(pidPromotor, sTienda);
            String[] Direcciones = almacenaImagen.ObtenDirecciones(pidPromotor, sTienda);
            double[] Latitudes = almacenaImagen.ObtenLatitudes(pidPromotor, sTienda);
            double[] Longitudes = almacenaImagen.ObtenLongitudes(pidPromotor, sTienda);

            for (int k = 0; k < iNumTiendas; k++) {
                ruta[k] = Rutas[k];
                determinante[k] = Determinantes[k];
                tienda[k] = Tiendas[k];
                direccion[k] = Direcciones[k];
                latitud[k] = Latitudes[k];
                longitud[k] = Longitudes[k];
            }
            // ******************************
            // Establece la forma de acceso y muestra las tiendas
            MuestraLista();
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e){
            Toast.makeText(this, TAG_INFO + " Se presento una inconsistencia, favor de intentar nuevamente " + e.toString() , Toast.LENGTH_LONG).show();
        }
    }

      //************************************
    // Clase que consulta las tiendas
    //  Obtiene la de  informacion de todos los catalogos para luego compararla con los de sqlite  e insertar lo nuevo
    class ConsultaTiendas extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;
        String versionapp = null;

        @Override
        protected void onPreExecute()
        {
            // TODO ****************************
            // TODO EN ESTA SECCIÓN SE DESCARGAN TODOS LOS DATOS DESDE LA PLATAFORMA USANDO EL API /Promotor/obtenertiendaspromotor_calendario.php
            // TODO Obtiene la de  informacion de todos los catalogos para luego compararla con los de sqlite  e insertar lo nuevo
            // TODO ****************************

            String sTienda= txtBuscar.getText().toString().trim();
            if (sTienda.equals("")) {
                sRuta = TAG_SERVIDOR + "/Promotor/obtenertiendaspromotor_calendario.php?idpromotor=" + pidPromotor + "&tienda=%&idempresa=" + pIdempresa;
            }
            else{
                sRuta = TAG_SERVIDOR + "/Promotor/obtenertiendaspromotor_calendario.php?idpromotor=" + pidPromotor + "&tienda=" + sTienda + "&idempresa=" + pIdempresa;
            }
            // Log.e(TAG_ERROR, "Consulta Tiendas " + sRuta);

            super.onPreExecute();
            pDialog = new ProgressDialog(listatiendas.this);
            pDialog.setMessage("Consultando lista de tiendas y productos ...");
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
                // Log.e(TAG_ERROR,"Url invocada: " + url);
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
                String line ;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line).append(" ");
                }

                // Append Server Response To Content String
                sRespusta = sb.toString();

                // Log.e(TAG_ERROR,"Respuesta recibida: " + sRespusta);

            } catch (Exception ex) {
                Error = ex.getMessage();
                // Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al obtener los datos del promotor " +  ex.getMessage(),Toast.LENGTH_LONG).show();
                funciones.RegistraError(pName, "listatiendas,ConsultaTiendas", ex, listatiendas.this, getApplicationContext());
                 // Log.e("Error al procesar api: ", Error);
            } finally {
                try {
                    assert reader != null;
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    funciones.RegistraError(pName, "listatiendas,ConsultaTiendas 1", ex, listatiendas.this, getApplicationContext());
                    //Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error en el proceso de lectura de datos " +  Error,Toast.LENGTH_LONG).show();
                    // Log.e("Error al procesar api: ", Error);
                }
            }

            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                String Resultado = "Se generó el siguiente error : " + Error;
                //funciones.RegistraError(pName, "listatiendas,ConsultaTiendas 1", Error, listatiendas.this, getApplicationContext());
                //Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error en el proceso de lectura de datos " +  Resultado,Toast.LENGTH_LONG).show();
                 // Log.e(TAG_ERROR, Resultado);
            } else {
                try {
                    JSONObject jsonResponse, jsonChidNode, jsonObjRuta, jsonObjProd, jsonObjProdFto;
                    JSONObject jsonObjRutaCat, jsonObjVista, jsonObjCadena, jsonObjObs, jsonObjProm, jsonObjAct, jsonObjEmp, jsonObjConf;
                    JSONArray jsonarray;
                    // Revisando la respuesta
                    assert sRespusta != null;
                    jsonResponse = new JSONObject(sRespusta);
                    jsonarray = jsonResponse.getJSONArray(TAG_RESPUESTA);       // Arreglo
                    iLongitudArreglo = jsonarray.length();

                    // ******************************************
                    // Ciclo de obtención de datos del servicio web
                    int jj = 0, k = 0, l = 0, m = 0, n = 0, o = 0, p = 0, q = 0, r = 0, s = 0, t = 0;
                    for (int i = 0; i < iLongitudArreglo; i++) {
                        jsonChidNode = jsonarray.getJSONObject(i);
                        if (jsonChidNode.has(TAG_RUTA)){
                            // solicita información de rutas
                            jsonObjRuta = jsonChidNode.getJSONObject(TAG_RUTA);
                            // Colocacion de datos en los arreglos de la lista de rutas
                            ruta[jj] = Integer.parseInt(jsonObjRuta.getString(TAG_IDRUTA));
                            determinante[jj] = jsonObjRuta.getString(TAG_DETERMINANTE);
                            tienda[jj] = jsonObjRuta.getString(TAG_TIENDA);
                            direccion[jj] = jsonObjRuta.getString(TAG_DIRECCION);
                            latitud[jj] = jsonObjRuta.getDouble(TAG_LATITUD);
                            longitud[jj] = jsonObjRuta.getDouble(TAG_LONGITUD);
                            versionapp = jsonObjRuta.getString(TAG_VERSIONAPP);
                            fechavisita[jj] = jsonObjRuta.getString(TAG_FECHAVISITA);
                            jj++;
                        }
                        else if(jsonChidNode.has(TAG_PROD)){
                            // Solicita información de productos
                            jsonObjProd = jsonChidNode.getJSONObject(TAG_PROD);
                            // Colocacion de datos en los arreglos de la lista de productos
                            idproducto[k] = Integer.parseInt(jsonObjProd.getString(TAG_IDPRODUCTO));
                            upc[k] = jsonObjProd.getString(TAG_UPC);
                            descripcion[k] = jsonObjProd.getString(TAG_DESCRIPCION);
                            descripcion1[k] = jsonObjProd.getString(TAG_DESCRIPCION1);
                            cantidad_caja[k] = Integer.parseInt(jsonObjProd.getString(TAG_CANTIDADCAJA));
                            cantidad_kgs[k] = Double.parseDouble(jsonObjProd.getString(TAG_CANTIDADKGS));
                            idempresa[k] = Integer.parseInt(jsonObjProd.getString(TAG_IDEMPRESA));
                            categoria1[k] = jsonObjProd.getString(TAG_CATEGORIA1);
                            categoria2[k] = jsonObjProd.getString(TAG_CATEGORIA2);
                            udc[k] = jsonObjProd.getString(TAG_UDC);
                            fdc[k] = jsonObjProd.getString(TAG_FDC);
                            uda[k] = jsonObjProd.getString(TAG_UDA);
                            fda[k] = jsonObjProd.getString(TAG_FDA);

                            ruta_archivo[k] = jsonObjProd.getString(TAG_RUTA_ARCHIVO);
                            k++;
                        }
                        else if(jsonChidNode.has(TAG_PROD_FTO))
                        {
                            // solicita información de formatos
                            jsonObjProdFto = jsonChidNode.getJSONObject(TAG_PROD_FTO);
                            idproductoformatoprecio[l] =  Integer.parseInt(jsonObjProdFto.getString(TAG_IDPRODUCTOFORMATOPRECIO));
                            idproducto1[l] = Integer.parseInt(jsonObjProdFto.getString(TAG_IDPRODUCTO));
                            idformato[l] = Integer.parseInt(jsonObjProdFto.getString(TAG_IDFORMATO));
                            idempresa[l] = Integer.parseInt(jsonObjProdFto.getString(TAG_IDEMPRESA));
                            precio[l] = Double.parseDouble(jsonObjProdFto.getString(TAG_PRECIO));
                            udc1[l] = jsonObjProdFto.getString(TAG_UDC);
                            fdc1[l] = jsonObjProdFto.getString(TAG_FDC);
                            uda1[l] = jsonObjProdFto.getString(TAG_UDA);
                            fda1[l] = jsonObjProdFto.getString(TAG_FDA);
                            l++;
                        }
                        else if(jsonChidNode.has(TAG_RUTA_CAT))
                        {
                            // Solicita información de catálogos
                            jsonObjRutaCat = jsonChidNode.getJSONObject(TAG_RUTA_CAT);
                            idruta[m] = Integer.parseInt(jsonObjRutaCat.getString(TAG_IDRUTA));
                            idformato1[m] = Integer.parseInt(jsonObjRutaCat.getString(TAG_IDFORMATO));
                            idcadena[m] = Integer.parseInt(jsonObjRutaCat.getString(TAG_IDCADENA));
                            m++;
                        }
                        else if(jsonChidNode.has(TAG_VISTA))
                        {
                            // Solicita información de vistas
                            jsonObjVista = jsonChidNode.getJSONObject(TAG_VISTA);
                            idproducto2[n] = Integer.parseInt(jsonObjVista.getString(TAG_IDPRODUCTO));
                            idruta2[n] = Integer.parseInt(jsonObjVista.getString(TAG_IDRUTA));
                            precioreal[n] = Double.parseDouble(jsonObjVista.getString(TAG_PRECIOREAL));
                            fda2[n] = jsonObjVista.getString(TAG_FDA);
                            n++;
                        }
                        else if(jsonChidNode.has(TAG_CADENA))
                        {
                            // Solicita información de cadenas
                            jsonObjCadena = jsonChidNode.getJSONObject(TAG_CADENA);
                            idcadena[o] = Integer.parseInt(jsonObjCadena.getString(TAG_IDCADENA));
                            idempresa[o] = Integer.parseInt(jsonObjCadena.getString(TAG_IDEMPRESA));
                            nombrecorto[o] = jsonObjCadena.getString(TAG_NOMBRECORTO);
                            o++;
                        }
                        else if(jsonChidNode.has(TAG_OBSERV))
                        {
                            // Solicita información de observaciones
                            jsonObjObs = jsonChidNode.getJSONObject(TAG_OBSERV);
                            idobs[p] = Integer.parseInt(jsonObjObs.getString(TAG_IDOBS));
                            observaciones[p] = jsonObjObs.getString(TAG_OBSERVACIONES);
                            p++;
                        }
                        else if(jsonChidNode.has(TAG_PROMO))
                        {
                            // Solicita infomración de promo
                            jsonObjProm = jsonChidNode.getJSONObject(TAG_PROMO);
                            idpromocion[q] = jsonObjProm.getInt(TAG_IDPROMOCION);
                            idempresa1[q] = jsonObjProm.getInt(TAG_IDEMPRESA);
                            nombre[q] = jsonObjProm.getString(TAG_NOMBRE);
                            capacidad[q]  = jsonObjProm.getString(TAG_CAPACIDAD);
                            canal[q]  = jsonObjProm.getString(TAG_CANAL);
                            alcance[q]  = jsonObjProm.getString(TAG_ALCANCE);
                            inicio[q]  = jsonObjProm.getString(TAG_INICIO);
                            fina[q]  = jsonObjProm.getString(TAG_FINAL);
                            periodo[q] = jsonObjProm.getString(TAG_PERIODO);
                            actividad[q] = jsonObjProm.getString(TAG_ACTIVIDAD);
                            precioregular[q] = jsonObjProm.getString(TAG_PRECIOREGULAR);
                            preciopromocion[q] = jsonObjProm.getString(TAG_PRECIOPROMOCION);
                            idformato2[q] = jsonObjProm.getInt(TAG_IDFORMATO);
                            idproducto3[q] = jsonObjProm.getInt(TAG_IDPRODUCTO);
                            ruta1[q] = jsonObjProm.getString(TAG_ruta);
                            q++;
                        }
                        else if(jsonChidNode.has(TAG_ACTIV))
                        {
                            // Solicita informacion de actividad
                            jsonObjAct = jsonChidNode.getJSONObject(TAG_ACTIV);
                            idactividad[r] = Integer.parseInt(jsonObjAct.getString(TAG_IDACTIVIDAD));
                            actividad[r] = jsonObjAct.getString(TAG_ACTIVIDAD);
                            r++;
                        }
                        else if(jsonChidNode.has(TAG_EMPAQUE))
                        {
                            // Obtiene la información de los empaques
                            jsonObjEmp = jsonChidNode.getJSONObject(TAG_EMPAQUE);
                            idempaque[s] = Integer.parseInt(jsonObjEmp.getString(TAG_IDEMPAQUE));
                            empaque[s] = jsonObjEmp.getString(TAG_empaque);
                            s++;
                        }
                        else if(jsonChidNode.has(TAG_SOLICITAINV))
                        {
                            // Obtiene información de solicita inv
                            jsonObjConf = jsonChidNode.getJSONObject(TAG_SOLICITAINV);
                            solicita[t] = Integer.parseInt(jsonObjConf.getString(TAG_solicita));
                            t++;
                        }
                    }

                    int iCuenta = almacenaImagen.ObtenRegistrosTiendas(pidPromotor,sTienda);
                    int iCuentaProductos  = almacenaImagen.ObtenRegistros(1);
                    int iCuentaProFtoPrecio  = almacenaImagen.ObtenRegistros(2);
                    int iCuentaRutas  = almacenaImagen.ObtenRegistros(3);
                    int iCuentaVista  = almacenaImagen.ObtenRegistros(4);
                    int iCuentaCadena  = almacenaImagen.ObtenRegistros(5);
                    int iCuentaObs = almacenaImagen.ObtenRegistros(6);
                    int iCuentaPromo = almacenaImagen.ObtenRegistros(7);
                    int iCuentaActiv = almacenaImagen.ObtenRegistros(8);
                    int iCuentaEmpaque = almacenaImagen.ObtenRegistros(13);

                    // ******************************************
                    // Inserciòn de tiendas si el numero de registros es diferente
                    if (iCuenta != jj){
                        //pDialog.setMessage("Inserción de tiendas ...");
                        almacenaImagen.TruncarTablaTiendas(pidPromotor);
                        for (int j = 0; j < jj; j++)
                            almacenaImagen.insertatienda(pidPromotor, ruta[j], Integer.valueOf(determinante[j]),
                                    tienda[j], direccion[j], latitud[j], longitud[j], fechavisita[j]);
                    }
                    // Log.e(TAG_ERROR, " conteo de tiendas " + jj);
                    // ******************************************
                    // Inserciòn de productos si el numero de registros es diferente
                    if (iCuentaProductos != k){
                        //pDialog.setMessage("Inserción de productos ...");
                        almacenaImagen.TruncarTabla(1);
                        for (int a = 0; a < k; a++) {
                            almacenaImagen.insertaproducto(idproducto[a], upc[a] , descripcion[a], descripcion1[a], cantidad_caja[a], cantidad_kgs[a], idempresa[a], categoria1[a], categoria2[a], udc[a], fdc[a], uda[a], fda[a], ruta_archivo[a]);
                        }
                    }
                    //final int e1 = Log.e(TAG_ERROR, " conteo de productos " + k);
                    // ******************************************
                    // Inserciòn de productosformatoprecio  si el numero de registros es diferente
                    if (iCuentaProFtoPrecio != l){
                        //pDialog.setMessage("Inserción de precios ...");
                        almacenaImagen.TruncarTabla(2);
                        for (int a = 0; a < l; a++) {
                            almacenaImagen.inserta_productoformatoprecio(idproductoformatoprecio[a], idproducto1[a],idformato[a], idempresa[a], precio[a], udc1[a], fdc1[a], uda1[a], fda1[a]);
                        }
                    }
                    //final int e2 = Log.e(TAG_ERROR, " conteo de formatos precio " + l);
                    // ******************************************
                    // Inserciòn de rutas si el numero de registros es diferente
                    if (iCuentaRutas != m){
                       //pDialog.setMessage("Inserción de rutas ...");
                        almacenaImagen.TruncarTabla(3);
                        for (int a = 0; a < m; a++) {
                            almacenaImagen.inserta_rutas(idruta[a], idcadena[a], idformato1[a],1, udc1[a], fdc1[a], uda1[a], fda1[a]);
                        }
                    }
                    //final int e3 = Log.e(TAG_ERROR, " conteo de rutas " + m);
                    // ******************************************
                    // Inserción de vista que siempre se debe de hacer para tener actualizados los precios
                    //if (iCuentaVista != n){
                    if (n>0){
                        almacenaImagen.TruncarTabla(4);
                        for (int a = 0; a < n; a++) {
                            almacenaImagen.inserta_vista(idproducto2[a], idruta2[a], precioreal[a], fda2[a]);
                        }
                    }
                    //final int e4 = Log.e(TAG_ERROR, " conteo de vistas " + n);
                    // ******************************************
                    // Inserciòn de cadena
                    if (iCuentaCadena != o){
                        almacenaImagen.TruncarTabla(5);
                        for (int a = 0; a < o; a++) {
                            almacenaImagen.inserta_cadena(idcadena[a], idempresa[a], nombrecorto[a]);
                        }
                    }
                    //final int e5 = Log.e(TAG_ERROR, " conteo de cadenas " + o);
                    // ******************************************
                    // Inserción de observaciones
                    if (iCuentaObs != p){
                        almacenaImagen.TruncarTabla(6);
                        for (int a = 0; a < p; a++) {
                            almacenaImagen.inserta_obs(idobs[a], observaciones[a]);
                        }
                    }
                    //final int e6 = Log.e(TAG_ERROR, " conteo de observaciones " + p);
                    // ******************************************
                    // Inserción de vw_promociones
                    if (iCuentaPromo != q){
                        almacenaImagen.TruncarTabla(7);
                        for (int a = 0; a < q; a++) {
                            almacenaImagen.inserta_promo(idpromocion[a],idempresa1[a], nombre[a], capacidad[a], canal[a], alcance[a], inicio[a], fina[a], periodo[a], actividad[a], precioregular[a], preciopromocion[a], idformato2[a], idproducto3[a], ruta1[a]);
                        }
                    }
                    //final int e7 = Log.e(TAG_ERROR, " conteo de promociones " + q);
                    // ******************************************
                    // Inserción de cat_actividad
                    if (iCuentaActiv != r){
                        almacenaImagen.TruncarTabla(8);
                        for (int a = 0; a < r; a++) {
                            almacenaImagen.inserta_actividad(idactividad[a],actividad[a]);
                        }
                    }
                    //final int e8 = Log.e(TAG_ERROR, " conteo de actividades " + r);
                    // ******************************************
                    // Inserción de cat_empaque
                    if (iCuentaEmpaque != s){
                        almacenaImagen.TruncarTabla(12);
                        for (int a = 0; a < s; a++) {
                            almacenaImagen.inserta_empaque(idempaque[a],empaque[a]);
                        }
                    }
                    // ******************************************
                    // Actualizaciòn de configuracion
                    if(solicita[0]> -1) {
                        almacenaImagen.TruncarTabla(10);
                        almacenaImagen.inserta_configuracion(solicita[0]);
                    }

                    // Obtiene la version aunque las tablas sean iguales
                    almacenaImagen.insertaversion_app(versionapp);
                    // ******************************************
                    tiendasCargadas=true;
                } catch (JSONException e) {
                    funciones.RegistraError(pName, "ListaTiendas,ConsultaTiendas2", e, listatiendas.this, getApplicationContext());
                    String Resultado = "Se generó el siguiente error : " + e.toString();
                     Log.e(TAG_ERROR,Resultado);
                }
            }
            // **************************
            // Log.e(TAG_ERROR,"carga Listview despues de carga los registros de la api PHP");
            return null;
        }

        @Override
        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();
            MuestraLista();
        }
    }

    // **************************
    // Muestra lista después del proceso
    public void MuestraLista(){

        int cuentaTiendas = almacenaImagen.ObtenRegistrosTiendas(pidPromotor,sTienda);

        // Declarar el numero de elementos exacto del areglo
        int[] ruta1 = new int[cuentaTiendas];
        String[] determinante1 = new String[cuentaTiendas];
        String[] tienda1 = new String[cuentaTiendas];
        String[] direccion1 = new String[cuentaTiendas];
        Double[] latitud1 = new Double[cuentaTiendas];
        Double[] longitud1 = new Double[cuentaTiendas];

        for(int k=0; k<cuentaTiendas; k++){
            ruta1[k]=ruta[k];
            determinante1[k]=determinante[k];
            tienda1[k]=tienda[k];
            direccion1[k]=tienda[k] + "\n" + direccion[k];
            latitud1[k]=latitud[k];
            longitud1[k]=longitud[k];
        }

        if(cuentaTiendas==0){
            Toast.makeText(getApplicationContext(),"Usted no tiene tiendas asignadas por el momento",
                    Toast.LENGTH_LONG).show();
        }

        try {
            // Llamada al proceso de asignacion del adaptador a la lista
            AdaptadorTiendasPromotor adaptador = new AdaptadorTiendasPromotor(
                    this,
                    ruta1,
                    determinante1,
                    tienda1,
                    direccion1,
                    latitud1,
                    longitud1);
            lista.setAdapter(adaptador);
            tiendasCargadas = true;
        } catch (Exception e) {
            funciones.RegistraError(pName, "ListaTiendas,ConsultaTiendas 3 AdaptadorTiendasPromo", e, listatiendas.this, getApplicationContext());
        }
    }

    // **********************************
    // Oculta Progress Bar
    public void OcultaProgress(){
        try {
            // Sleep for 500 milliseconds.
            Thread.sleep(500);
        } catch (InterruptedException e) {
            funciones.RegistraError(pName, "ListaTiendas, OcultaProgress", e, listatiendas.this, getApplicationContext());
            // e.printStackTrace();
        }
        // progressBar.setVisibility(View.GONE);
        // LayoutProgreso.setVisibility(View.GONE);
        // LayoutProgreso1.setVisibility(View.GONE);
    }
}
