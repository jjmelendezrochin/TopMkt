package com.topmas.top;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.topmas.top.Constants.TAG_ACCESSTOKEN;
import static com.topmas.top.Constants.TAG_CONSULTAENWEB;
import static com.topmas.top.Constants.TAG_DETERMINANTE;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_EMAIL;
import static com.topmas.top.Constants.TAG_EMPRESA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_EXPIRESIN;
import static com.topmas.top.Constants.TAG_ID;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_NAME;
import static com.topmas.top.Constants.TAG_OBSERVACIONES;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_RUTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_VERSIONAPP;
import static com.topmas.top.Constants.TAG_alias;
import static com.topmas.top.Constants.TAG_nombreempresa;

public class MainActivity extends AppCompatActivity {

    private Button b1;
    private EditText txtUsuario;
    private EditText txtPwd;
    private int    pid = 0;
    private String pName = "";
    private String pEmail = "";
    private String pToken = "";
    private String pExpira = "";
    private ProgressDialog pDialog;
    private Spinner spCliente;
    private String idEmpresa = "";
    Funciones funciones = new Funciones();
    private AlmacenaImagen almacenaImagen;
    private String versionapp = "";


    // products JSONArray
    JSONArray Datos = null;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        versionapp = almacenaImagen.ConsultaVersionApp();

        // *******************************
        // Inicia la verificación de activación del GPS
        funciones.locationStart(this);
        // *******************************

        b1 = findViewById(R.id.cmdIngresar);
        txtUsuario =  findViewById(R.id.txtNombre);
        txtPwd = findViewById(R.id.txtPwd);
        spCliente = findViewById(R.id.spinClientes);

        // *****************************
        // llenando los datos de la lista de catalogo empresas
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = preferencias.edit();
        String pidEmpresaSel = preferencias.getString(TAG_IDEMPRESA, "1");

        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        int iCtaEmpresas = almacenaImagen.ObtenRegistros(15);

        // Log.e(TAG_ERROR, String.valueOf(iCtaEmpresas));

        if(iCtaEmpresas==0){
            // Verifica si hay conexión para descargar las empresas
            if (!funciones.RevisarConexion(getApplicationContext())){
                Toast.makeText(MainActivity.this, "No hay conexión a internet y/o no se encontró la lista de empresas, " +
                        " favor de verificar (Debe ingresar al menos una vez a la plataforma para poder visualizar los datos en modo desconectado)", Toast.LENGTH_LONG).show();
            }
            else{
                // **************************
                // Descarga las empresas
                CargaEmpresas cargaEmpresas = new CargaEmpresas();
                cargaEmpresas.execute(pidEmpresaSel);
                // *****************************
            }

        }
        else{
            // Consulta las empresas del catálogo y las pone en el spinner
            if (pidEmpresaSel=="")
                pidEmpresaSel = "1";
            LlenaSpinnerEmpresas(Integer.valueOf(pidEmpresaSel));
        }

        final Spinner spinClientes = (Spinner) findViewById( R.id.spinClientes );
       /* CmbEmpresas[] strClientes;
        List<CmbEmpresas> listaEmpresas;
        ArrayAdapter<CmbEmpresas> comboAdapter;
        String nombreFruta;
        //Convierto la variable List<> en un ArrayList<>()
        listaEmpresas = new ArrayList<>();
        //Arreglo con nombre de frutas
        strClientes = new CmbEmpresas[] {
                new CmbEmpresas(1,"CAL"),
                new CmbEmpresas(2,"ALU")};
        //Agrego las frutas del arreglo `strFrutas` a la listaEmpresas
        Collections.addAll(listaEmpresas, strClientes);
        //Implemento el adapter con el contexto, layout, listaEmpresas
        comboAdapter = new ArrayAdapter<CmbEmpresas>(this,android.R.layout.simple_spinner_dropdown_item, listaEmpresas);
        comboAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Cargo el spinner con los datos
        spinClientes.setAdapter(comboAdapter);
        spinClientes.setSelection(Integer.valueOf(pidEmpresaSel)-1);
        */

        // *******************************
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // **************************************
                /* Alamcena en la variable idempresa el valor seleccionado en el spinner */
                //idEmpresa =  String.valueOf((spinClientes.getSelectedItem()).idEmpresa);
                SharedPreferences preferencias =
                        PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(TAG_IDEMPRESA, String.valueOf(idEmpresa));
                editor.commit();

                // **************************************
                // Verifica si no hay conexión para consultar en la base de datos de Sqlite
                if (!funciones.RevisarConexion(getApplicationContext())){
                    String pNombre  = txtUsuario.getText().toString();
                    String pClave   = txtPwd.getText().toString();

                    int idpromotor = almacenaImagen.ObtenRegistrosPromotor(pNombre, pClave, idEmpresa);
                    // Log.e(TAG_INFO,"Promotor " + idpromotor );
                    if (idpromotor>0){
                        // ***************************
                        // Inicio de lista de tiendas
                        Intent listatiendas = new Intent(getApplicationContext(), listatiendas.class);

                        listatiendas.putExtra(TAG_IDPROMOTOR, idpromotor);
                        listatiendas.putExtra(TAG_NAME, pNombre);
                        listatiendas.putExtra(TAG_IDEMPRESA, idEmpresa);
                        // Log.e(TAG_ERROR,"idempresa: "+ idEmpresa);
                        listatiendas.putExtra(TAG_EMAIL, "null");
                        listatiendas.putExtra(TAG_ACCESSTOKEN, "null");
                        listatiendas.putExtra(TAG_EXPIRESIN, "null");
                        listatiendas.putExtra(TAG_CONSULTAENWEB, 1);    // Indica que debe buscar en la lista de tiendas en web

                        Usuario usr = new Usuario(idpromotor,pName, pEmail, pToken, pExpira, idEmpresa);
                        startActivity(listatiendas);
                        // ***************************
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "No hay conexión a internet y/o el usuario no se encontro, favor de verificar (Debe ingresar al menos una vez a la plataforma para poder visualizar los datos en modo desconectado)", Toast.LENGTH_LONG).show();
                    }

                    return;
                }
                else {
                    // **************************************
                    // Llamado a la consulta del servicio web por si hay conexión
                    ConsultaWebService consulta = new ConsultaWebService();
                    consulta.execute();
                }
            }
        });

        // ******************************
        spinClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                idEmpresa = String.valueOf(id);
                //         idEmpresa = String.valueOf(id+1);
                //Toast.makeText(getApplicationContext(),"Empresa " + idEmpresa, Toast.LENGTH_SHORT);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // ***********************************
        // Habilita boton solo si tiene texto
        txtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfReady();
            }
        });

        // ***********************************
        // Habilita boton solo si tiene texto
        txtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfReady();
            }
        });

        // ***********************************
        // Version
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        TextView version = findViewById(R.id.Version);
        String sVersionApp = versionName.trim() + ":" + String.valueOf(versionCode).trim();

         if(versionapp.length()==0)
            versionapp=sVersionApp;

        // **********************************
        // Evita el texto null
        if(versionName==null)
        {
            versionName = "nueva";
        }
        if(versionapp=="")
        {
            versionapp = "nueva";
        }

        if(sVersionApp.equals(versionapp))
        {
            version.setText(String.format("Versión %s:%d", versionName, versionCode));
        }
        else
        {
            version.setTextColor(Color.BLUE);
            version.setText("Versión " + versionapp + " disponible");
        }
    }


    protected void LlenaSpinnerEmpresas(int pidEmpresaSel){
        // *****************************
        // llenando los datos de la lista de empresas
        Cursor c = almacenaImagen.CursorEmpresas();
        String[] from = new String[]{TAG_alias};
        int[] to = new int[]{android.R.id.text1};
        // This is your simple cursor adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner s = findViewById( R.id.spinClientes );
        s.setAdapter(adapter);
        s.setSelection(Integer.valueOf(pidEmpresaSel)-1);
    }

    class ConsultaWebService extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;

        int id = 0;
        String name = "";
        String email = "";
        String token = "";
        String expira = "";


        @Override
        protected void onPreExecute() {
            sRuta = TAG_SERVIDOR + "/Promotor/obtenerpromotor1.php?"
                    + "idusuario=" + txtUsuario.getText().toString().trim()
                    + "&clave=" + txtPwd.getText().toString().trim()
                    + "&idempresa=" + idEmpresa;
            //Log.e("Ruta", sRuta);

            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Consultando en el servicio Web ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            BufferedReader reader = null;
            String sRespusta = null;
            // Log.e("Ruta", "1");
            try {
                // Defined URL where to send data
                URL url = new URL(sRuta);
                // Log.e("Ruta", "2");

                // Send POST data request
                URLConnection conn = url.openConnection();
                // Log.e("Ruta", "3");
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                wr.write(data);
                wr.flush();
                // Log.e("Ruta", "4");
                // Get the server response
                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                // Log.e("Ruta", "5");
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + " ");
                }

                // Log.e("Ruta", "6");
                // Append Server Response To Content String
                sRespusta = sb.toString();
                // Log.e(TAG_RESPUESTA, sRespusta);

            } catch (Exception ex) {
                Error = ex.getMessage();
               // Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al obtener los datos del promotor " +  Error,Toast.LENGTH_LONG).show();
                // Log.e("Ruta", Error);
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    //Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al obtener los datos del promotor 1" +  Error,Toast.LENGTH_LONG).show();
                    // Log.e("Ruta", Error);
                }
            }

            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                String Resultado = "Se generó el siguiente error : " + Error;
            } else {
                try {
                    JSONObject jsonResponse;
                    // Revisando la respusta
                    jsonResponse = new JSONObject(sRespusta);

                    int iResp = jsonResponse.getInt(TAG_ID);
                    // Log.e("RESPUESTA", String.valueOf( iResp));
                    if (iResp>0)
                    {
                        // ******************************
                        // Obtención de variables y acceso al sistema
                        id = jsonResponse.getInt(TAG_ID);
                        name = jsonResponse.getString(TAG_NAME);
                        email = jsonResponse.getString(TAG_EMAIL);
                        token = jsonResponse.getString(TAG_ACCESSTOKEN);
                        expira = jsonResponse.getString(TAG_EXPIRESIN);

                        pid = id;
                        pName = name;
                        pEmail = email;
                        pToken = token;
                        pExpira = expira;


                        // ******************************
                    }
                    else {
                        id=0;
                        pid=0;
                    }

                } catch (JSONException e) {
                    String Resultado = "Se generó el siguiente error : " + e.toString();
                    // Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al obtener las variables de tiendas " +  Resultado,Toast.LENGTH_LONG).show();
                }
            }
            // **************************
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            Verifica();
            pDialog.dismiss();
        }
    }

    protected void Verifica() {
        // *********************************************************
        // Proceso que verifica el resultado para ingresar
        if (pid>0) {

            // ******************************
            // Aqui se inicia el servicio
            // Solo inicia el servicio de localización la primera ocasión
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();


            int iServicioIniciado = preferences.getInt("ServicioIniciado", 0);
            if (iServicioIniciado==0) {
                editor.putInt("ServicioIniciado", 1);
                editor.apply();

                Intent iServicio = new Intent(MainActivity.this, LocationService.class);
                iServicio.putExtra(TAG_IDPROMOTOR, pid);
                preferences.getInt("ServicioIniciado", 0);
                startService(iServicio);
            }

            String nombre = txtUsuario.getText().toString();
            String pwd = txtPwd.getText().toString();


            // ***************************
            // Inserta o actualiza promotor en la tabla cat_promotores
            almacenaImagen.insertaoactualizapromotor(pid,nombre,pwd, idEmpresa);

            // ***************************
            // Inicio de lista de tiendas
            Intent listatiendas = new Intent(getApplicationContext(), listatiendas.class);

            listatiendas.putExtra(TAG_IDPROMOTOR, pid);
            listatiendas.putExtra(TAG_IDEMPRESA, idEmpresa);
            // Log.e(TAG_ERROR,"lista tiendas: " + idEmpresa);
            listatiendas.putExtra(TAG_NAME, pName);
            listatiendas.putExtra(TAG_EMAIL, pEmail);
            listatiendas.putExtra(TAG_ACCESSTOKEN, pToken);
            listatiendas.putExtra(TAG_EXPIRESIN, pExpira);
            listatiendas.putExtra(TAG_CONSULTAENWEB, 1);    // Indica que debe buscar en la lista de tiendas en web

            Usuario usr = new Usuario(pid,pName, pEmail, pToken, pExpira, idEmpresa);
            startActivity(listatiendas);
            // ***************************

        }
       else
           {
            Toast toast2 = Toast.makeText(getApplicationContext(),
                    "Acceso no permitido", Toast.LENGTH_SHORT);
            toast2.show();
        }
        // *********************************************************

    }

    private void enableSubmitIfReady() {
        EditText txtUsuario = findViewById(R.id.txtNombre);
        EditText txtPwd = findViewById(R.id.txtPwd);

        AtomicBoolean isReady = new AtomicBoolean(false);
        if (txtUsuario.getText().toString().length() >= 5)
            isReady.set(true);
        else
            isReady.set(false);

        if (isReady.get() && (txtPwd.getText().toString().length() >= 5)) {
            isReady.set(true);
        } else {
            isReady.set(false);
        }

        b1.setEnabled(isReady.get());
    }

    //************************************
    // Clase que carga las fotos guardadas
    class CargaEmpresas extends AsyncTask<String, Void, String> {
        String sRuta = null;
        String Error = null;
        String data = "";
        int jj=0;
        int[] idEmpresa = new int[100];
        String[] nombreEmpresa = new String[100];
        String[] aliasEmpresa = new String[100];
        String pidEmpresaSel = "1";


        int i =0;
        @Override
        protected void onPreExecute()
        {
            sRuta = TAG_SERVIDOR + "/Promotor/obtenerempresas.php";
            // Log.e(TAG_ERROR, sRuta);

            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Consultando en el empresas Web  ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            pidEmpresaSel = params[0];
            BufferedReader reader = null;
            String sRespuesta = null;
            // Log.e("Ruta", "1");
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
                    sb.append(line + " ");
                }

                // Append Server Response To Content String
                sRespuesta = sb.toString();
                //Log.e(TAG_ERROR, sRespuesta);

                // **************************
                // Proceso de lectura de datos
                if (Error != null) {
                    String Resultado = "Se generó el siguiente error : " + Error;
                } else {
                    try {
                        JSONObject jsonResponse, jsonChidNode, jsonObjEmpresa;
                        JSONArray jsonarray;
                        // Revisando la respusta
                        assert sRespuesta != null;
                        jsonResponse = new JSONObject(sRespuesta);
                        jsonarray = jsonResponse.getJSONArray(TAG_RESPUESTA);       // Arreglo
                        int iLongitudArreglo = jsonarray.length();
                        if (iLongitudArreglo>0){
                            almacenaImagen.TruncarTabla(15);
                        }

                        for (int i = 0; i < iLongitudArreglo; i++) {
                            jsonChidNode = jsonarray.getJSONObject(i);
                            // almacenaImagen.inserta_empresa(0, "Seleccionar", "***");
                            if (jsonChidNode.has(TAG_EMPRESA)) {
                                jsonObjEmpresa = jsonChidNode.getJSONObject(TAG_EMPRESA);
                                // Colocacion de datos en los arreglos

                                idEmpresa[i] = Integer.parseInt(jsonObjEmpresa.getString(TAG_IDEMPRESA));
                                nombreEmpresa[i] = jsonObjEmpresa.getString(TAG_nombreempresa);
                                aliasEmpresa[i] = jsonObjEmpresa.getString(TAG_alias);
                                almacenaImagen.inserta_empresa(idEmpresa[i], nombreEmpresa[i], aliasEmpresa[i]);

                            }
                        }

                    } catch (JSONException e) {
                        String Resultado = "Se generó el siguiente error : " + e.toString();
                        // Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al obtener las variables de tiendas " +  Resultado,Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception ex) {
                Error = ex.getMessage();
                Log.e(TAG_ERROR, Error);
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    Log.e(TAG_ERROR, Error);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url)
        {
            LlenaSpinnerEmpresas(Integer.valueOf(pidEmpresaSel));
            pDialog.dismiss();
        }

    }

}
