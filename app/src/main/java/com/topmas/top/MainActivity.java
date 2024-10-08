package com.topmas.top;

import static com.topmas.top.Constants.DEV_ENVIROMENT;
import static com.topmas.top.Constants.DEV_PWD;
import static com.topmas.top.Constants.DEV_USER;
import static com.topmas.top.Constants.FAKE_VALIDATION;
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
import static com.topmas.top.Constants.TAG_DESCRIPCIONINCIDENCIA;
import static com.topmas.top.Constants.TAG_DETERMINANTE;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_EMAIL;
import static com.topmas.top.Constants.TAG_EMPAQUE;
import static com.topmas.top.Constants.TAG_EMPRESA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_EXPIRESIN;
import static com.topmas.top.Constants.TAG_FDA;
import static com.topmas.top.Constants.TAG_FDC;
import static com.topmas.top.Constants.TAG_FECHAHORA;
import static com.topmas.top.Constants.TAG_FECHAHORARESPUESTA;
import static com.topmas.top.Constants.TAG_FECHAVISITA;
import static com.topmas.top.Constants.TAG_FINAL;
import static com.topmas.top.Constants.TAG_IDACTIVIDAD;
import static com.topmas.top.Constants.TAG_IDCADENA;
import static com.topmas.top.Constants.TAG_IDEMPAQUE;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDFORMATO;
import static com.topmas.top.Constants.TAG_IDFOTO;
import static com.topmas.top.Constants.TAG_IDINC;
import static com.topmas.top.Constants.TAG_IDINCIDENCIA;
import static com.topmas.top.Constants.TAG_IDOBS;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDPRODUCTOFORMATOPRECIO;
import static com.topmas.top.Constants.TAG_IDPROMOCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_IMAGE;
import static com.topmas.top.Constants.TAG_INCIDENCIA;
import static com.topmas.top.Constants.TAG_INICIO;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LEIDA;
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
import static com.topmas.top.Constants.TAG_RESPINCIDENCIA;
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
import static com.topmas.top.Constants.TAG_VISTA;
import static com.topmas.top.Constants.TAG_alias;
import static com.topmas.top.Constants.TAG_empaque;
import static com.topmas.top.Constants.TAG_nombreempresa;
import static com.topmas.top.Constants.TAG_ruta;
import static com.topmas.top.Constants.TAG_solicita;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private Button cmdIngresar;
    private EditText txtUsuario;
    private EditText txtPwd;
    private int pidPromotor = 0;
    private String pName = "";
    private String pEmail = "";
    private String pToken = "";
    private String pExpira = "";
    public ProgressDialog pDialog;
    private Spinner spCliente;
    private String pIdempresa = "";
    Funciones funciones = new Funciones();
    private AlmacenaImagen almacenaImagen;
    private String versionapp = "";
    // private final Usuario usr = new Usuario();
    private ListView lista;
    private int iLongitudArreglo;
    private int iLongitudArregloTiendas;
    private int iNumTiendas = 0;
    private ProgressBar progressBar;

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

    String [] fechavisita = new String[1000];
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
    int[] idincidencia = new int[200];
    int[] solicita = new int[1];
    String[] descripcion_incidencia = new String[200];

    // ********************************************
    // Variables usadas en respuesta de incidencias
    int[] idinc_ri = new int[200];
    int[] idincidencia_ri = new int[200];
    int[] idfoto_ri = new int[200];
    int[] idpromotor_ri = new int[200];
    int[] idruta_ri = new int[200];
    String[] fechahora_ri = new String[200];
    String[] observaciones_ri = new String[200];
    String[] respuesta_ri = new String[200];
    String[] fechahora_respuesta_ri = new String[200];
    String[] image_ri = new String[200];
    String[] leida_ri = new String[200];


    boolean bActualiza = false;
    AtomicBoolean isReady = new AtomicBoolean(false);
    Handler handler = new Handler();


    int progressStatus = 0;
    // products JSONArray
    JSONArray Datos = null;

    int iMagenesGuardadas = 0;      // Imagenes guardadas
    int iPreciosCambiados = 0;      // Precios cambiados
    int iRegistrosCompetencia = 0;  // Registros competencia
    int iPromociones = 0;           // Promociones
    int iCaducidad = 0;             // Caducidades
    int iErrores = 0;               // Errores
    int iCompetenciaPromocion = 0;  // Datos de competencia promoción
    int iCanjes = 0;                // Datos de canjes
    int iIncidencia = 0;                // Datos de canjes
    int iSumaCuentas = 0;           // Total de imagenes por cargar

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            funciones.RegistraError(pName, "MainActivity setDefaultUncaughtExceptionHandler", (Exception) throwable, MainActivity.this, getApplicationContext());
        });

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            AlmacenaImagen almacenaImagen1 = new AlmacenaImagen(this.getApplicationContext());
            almacenaImagen1.inserta_error1("0", e, "MainActivity");
        }


        try {
            almacenaImagen = new AlmacenaImagen(getApplicationContext());
            versionapp = almacenaImagen.ConsultaVersionApp();

            // *******************************
            // Obteniendo la versión de android
            int iVersion = android.os.Build.VERSION.SDK_INT;
            // Api 29 Android 10
            // *******************************
            // Inicia la verificación de activación del GPS
            // funciones.locationStart(this);
            // *******************************

            cmdIngresar = findViewById(R.id.cmdIngresar);

            txtUsuario = findViewById(R.id.txtNombre);
            txtPwd = findViewById(R.id.txtPwd);
            spCliente = findViewById(R.id.spinClientes);


            // *****************************
            // llenando los datos de la lista de catalogo empresas
            SharedPreferences preferencias =
                    PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor editor = preferencias.edit();
            String pidEmpresaSel = preferencias.getString(TAG_IDEMPRESA, "1");

            int iCtaEmpresas = 0;

            almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
            iCtaEmpresas = almacenaImagen.ObtenRegistros(15);
            // Log.e(TAG_ERROR, "3");

            if (iCtaEmpresas == 0) {
                // Verifica si hay conexión para descargar las empresas
                String sMensaje = "No hay conexión a internet y/o el usuario no se encontro, favor de verificar (Debe ingresar al menos una vez a la plataforma para poder visualizar los datos en modo desconectado)";
                // Log.e(TAG_ERROR, "4a");
                if (!funciones.RevisarConexion(getApplicationContext())) {
                    Toast.makeText(MainActivity.this, sMensaje, Toast.LENGTH_LONG).show();
                    //funciones.RegistraLog(sMensaje, txtUsuario.toString());
                    // Log.e(TAG_ERROR, "4a1");
                } else {
                    // **************************
                    // Descarga las empresas
                    // Log.e(TAG_ERROR, "4a2");
                    CargaEmpresas cargaEmpresas = new CargaEmpresas();
                    cargaEmpresas.execute(pidEmpresaSel);
                    // *****************************
                }
            } else {
                // Log.e(TAG_ERROR, "4b");
                // Consulta las empresas del catálogo y las pone en el spinner
                if (pidEmpresaSel == "") {
                    pidEmpresaSel = "2";
                }

                LlenaSpinnerEmpresas(Integer.valueOf(pidEmpresaSel));
            }

        } catch (Exception e) {
            //e.printStackTrace();
            funciones.RegistraError("", "MainActivity_OnCreate", e, MainActivity.this, getApplicationContext());
        }

        final Spinner spinClientes = (Spinner) findViewById(R.id.spinClientes);
        // Ubicacion de la seleccion en spin
        spinClientes.setSelection(1);
        // *******************************
        // Botón de ingresar
        cmdIngresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                enableSubmitIfReady();
                if (isReady.get()) {
                    // Procesos consulta y actualizacion
                    bActualiza = false;
                    try {
                        Procesos();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Capturar datos de ingreso", Toast.LENGTH_LONG).show();
                }
            }
        });

        // *******************************
        // Botón de actualizar
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                enableSubmitIfReady();
                if (isReady.get()) {
                    // Procesos consulta y actualizacion
                    bActualiza = true;
                    try {
                        Procesos();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Capturar datos de ingreso", Toast.LENGTH_LONG).show();
                }
            }
        });

        //****************************
        // Muestra datos almacenados
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // almacenaImagen = new AlmacenaImagen(getApplicationContext());
                // almacenaImagen.muestradatosAlmacenados();

                int iCuenta = almacenaImagen.ObtenRegistros(0);
                int iCuentaPreciosCambiados = almacenaImagen.ObtenRegistros(9);
                int iCuentaRegistrosCompetencia = almacenaImagen.ObtenRegistros(10);
                int iCuentaPromociones = almacenaImagen.ObtenRegistros(12);
                int iCuentaCaducidad = almacenaImagen.ObtenRegistros(14);
                int iCuentaErrores = almacenaImagen.ObtenRegistros(16);
                int iCuentaCompetenciaPromocion = almacenaImagen.ObtenRegistros(18);
                int iCuentaCanjes = almacenaImagen.ObtenRegistros(20);
                int iCuentaIncidencias = almacenaImagen.ObtenRegistros(22);

                Log.e(TAG_ERROR, "Hay " + iMagenesGuardadas +  " imagenes guardadas");
                Log.e(TAG_ERROR, "Hay " + iPreciosCambiados+  " precios cambiados");
                Log.e(TAG_ERROR, "Hay " + iRegistrosCompetencia+  " registros competencia");
                Log.e(TAG_ERROR, "Hay " + iPromociones +  " promociones");
                Log.e(TAG_ERROR, "Hay " + iCaducidad +  " caducidades");
                Log.e(TAG_ERROR, "Hay " + iErrores +  " errores");
                Log.e(TAG_ERROR, "Hay " + iCompetenciaPromocion +  " competencia promociòn");
                Log.e(TAG_ERROR, "Hay " + iCanjes +  " canjes");
                Log.e(TAG_ERROR, "Hay " + iCuentaIncidencias +  " incidencias");

                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                String sMensaje = "Usted tiene " + "\n" + String.valueOf(iCuenta) + " imágenes almacenadas "  + "\n" +
                        iCuentaPreciosCambiados + " precios cambiados, "  + "\n" +
                        iCuentaPromociones + " promociones, "  + "\n" +
                        iCuentaRegistrosCompetencia + " datos de competencia,"  + "\n" +
                        iCuentaCaducidad  + " datos de caducidad, "  + "\n" +
                        iCuentaCompetenciaPromocion + " datos de comparativa, "  + "\n" +
                        iCuentaCanjes + " datos de canjes" + "\n" +
                        iCuentaIncidencias + " datos de incidencias" + "\n" +
                        iCuentaErrores  + " datos de error(es) "  + "\n" +
                        "No olvide conectarse en cuanto tenga señal suficiente, para colocar sus fotos en plataforma" + "\n" +
                        "(pulsar SI para salir)";

                alerta.setMessage(sMensaje)
                        .setCancelable(false)
                        .setNegativeButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alerta.show();
            }
        });

        // *******************************
        // Botón de actualizar
        FloatingActionButton fab0 = findViewById(R.id.fab0);
        fab0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // ********************
                // Aqui se tiene que verificar la hora, solo se pueden subir datos despues de
                // las 8 de la noche de cada dia.
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Date now = new Date();
                String sHora = sdf.format(now);
                // Log.i(TAG_INFO, "Hora actual " + sHora);
                /*
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    boolean result = isAfter8PM(sHora);
                    if (!result){
                        Toast.makeText(MainActivity.this, "Esta función está disponible solo después de las 8 pm", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                 */

                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                iMagenesGuardadas = almacenaImagen.ObtenRegistros(0);
                iPreciosCambiados = almacenaImagen.ObtenRegistros(9);
                iRegistrosCompetencia = almacenaImagen.ObtenRegistros(10);
                iPromociones = almacenaImagen.ObtenRegistros(12);
                iCaducidad = almacenaImagen.ObtenRegistros(14);
                iErrores = almacenaImagen.ObtenRegistros(16);
                iCompetenciaPromocion = almacenaImagen.ObtenRegistros(18);
                iCanjes = almacenaImagen.ObtenRegistros(20);
                iIncidencia = almacenaImagen.ObtenRegistros(22);

                int iSumaCuentas = (iMagenesGuardadas+iPreciosCambiados+iRegistrosCompetencia+iPromociones+iCaducidad+iErrores+iCompetenciaPromocion+iCanjes+iIncidencia);

                if (iSumaCuentas > 0) {
                    // **************************************
                    // Carga de datos
                    pDialog = new ProgressDialog(MainActivity.this);
                    pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pDialog.setMessage("Procesando...");
                    pDialog.setCancelable(true);
                    pDialog.setMax(iSumaCuentas);

                    CargaLasFotos cargaFotos = new CargaLasFotos();
                    cargaFotos.execute();
                }
            }
        });

        // ******************************
        // ValidaConexion
        ValidaConexion(fab0, fab1, fab);

        // ******************************
        spinClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pIdempresa = String.valueOf(id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
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

        if (versionapp.length() == 0)
            versionapp = sVersionApp;

        // **********************************
        // Evita el texto null
        if (versionName == null) {
            versionName = "nueva";
        }
        if (versionapp == "") {
            versionapp = "nueva";
        }

        version.setTextColor(Color.BLUE);
        version.setText("Ver " + versionName + ":" + versionCode + " " + (FAKE_VALIDATION ? " a " : " b "));

        // **************************************
        // Gesto para identificar si se pulso hacia abajo el activity
        findViewById(R.id.swap).setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeBottom() {
                ValidaConexion(fab0, fab1, fab);
                // Toast.makeText(MainActivity.this, "Pantalla actualizada", Toast.LENGTH_SHORT).show();
            }
        });

        // **************************************
        // Ambiente desarrollo establecer DEV_ENVIROMENT a true
        if (DEV_ENVIROMENT) {
            txtUsuario.setText(DEV_USER);
            txtPwd.setText(DEV_PWD);
        }
        else{
            txtUsuario.setText("");
            txtPwd.setText("");
        }
    }

    // ************************************
    // Ejecuta proceos de consulta o actualizacion
    protected void ValidaConexion( FloatingActionButton fab0, FloatingActionButton fab1, FloatingActionButton fab  ){
        // **************************
        // Muestra u Oculta los fabs
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        iMagenesGuardadas = almacenaImagen.ObtenRegistros(0);
        iPreciosCambiados = almacenaImagen.ObtenRegistros(9);
        iRegistrosCompetencia = almacenaImagen.ObtenRegistros(10);
        iPromociones = almacenaImagen.ObtenRegistros(12);
        iCaducidad = almacenaImagen.ObtenRegistros(14);
        iErrores = almacenaImagen.ObtenRegistros(16);
        iCompetenciaPromocion= almacenaImagen.ObtenRegistros(18);
        iCanjes = almacenaImagen.ObtenRegistros(20);
        iIncidencia = almacenaImagen.ObtenRegistros(22);

        iSumaCuentas =(iMagenesGuardadas+iPreciosCambiados+iRegistrosCompetencia+iPromociones+iCaducidad+iErrores+iCompetenciaPromocion+iCanjes+iIncidencia);

        if (iSumaCuentas>0
                && funciones.RevisarConexion(getApplicationContext()))
        {
            fab0.setVisibility(View.VISIBLE);
            fab1.setVisibility(View.VISIBLE);
        }
        else{
            fab0.setVisibility(View.GONE);
            fab1.setVisibility(View.GONE);
        }

        if (!funciones.RevisarConexion(getApplicationContext())) {
            fab.setVisibility(View.GONE);
        }
        else{
            fab.setVisibility(View.VISIBLE);
        }
        // **************************

    }

    // ************************************
    // Ejecuta proceos de consulta o actualizacion
    protected void Procesos() throws InterruptedException {
        String pNombre = txtUsuario.getText().toString();
        String pClave = txtPwd.getText().toString();

        // *****************************
        // Verifica si tiene un servicio GPS fake
        Funciones funciones = new Funciones();
        Usuario usuario = new Usuario();

        // TODO ****************************
        // TODO VALIDACION DE UBICACION FAKE
        // TODO ****************************
        if (funciones.ValidaUbicacionFake(usuario, getApplicationContext()))
        {
            return;
        }

        // **************************************
        /* Alamcena en la variable idempresa el valor seleccionado en el spinner */
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(TAG_IDEMPRESA, String.valueOf(pIdempresa));
        editor.commit();

        // **************************************
        // Verifica si no hay conexión para consultar en la base de datos de Sqlite
        if (!funciones.RevisarConexion(getApplicationContext())) {
            int pidPromotor = almacenaImagen.ObtenRegistrosPromotor(pNombre, pClave, pIdempresa);

            editor.putString(TAG_IDPROMOTOR, String.valueOf(pidPromotor));
            editor.commit();

            // Log.e(TAG_INFO,"Promotor " + pidPromotor );
            if (pidPromotor > 0) {
                // ***************************
                // Inicio de lista de tiendas
                Intent listatiendas = new Intent(getApplicationContext(), listatiendas.class);

                listatiendas.putExtra(TAG_IDPROMOTOR, pidPromotor);
                listatiendas.putExtra(TAG_NAME, pNombre);
                listatiendas.putExtra(TAG_IDEMPRESA, pIdempresa);
                listatiendas.putExtra(TAG_EMAIL, "null");
                listatiendas.putExtra(TAG_ACCESSTOKEN, "null");
                listatiendas.putExtra(TAG_EXPIRESIN, "null");
                listatiendas.putExtra(TAG_CONSULTAENWEB, 1);    // Indica que debe buscar en la lista de tiendas en web

                Usuario usr = new Usuario(pidPromotor, pName, pEmail, pToken, pExpira, pIdempresa, false);
                startActivity(listatiendas);
                // ***************************
            } else {
                String sMensaje = "No hay conexión a internet y/o el usuario no se encontro, favor de verificar (Debe ingresar al menos una vez a la plataforma para poder visualizar los datos en modo desconectado)";
                Toast.makeText(MainActivity.this, sMensaje, Toast.LENGTH_LONG).show();
            }
            return;
        } else {
            // **************************************
            // Llamado a la consulta del servicio web por si hay conexión
            if (funciones.RevisarConexion(getApplicationContext()))
            {
                ConsultaWebService consulta = new ConsultaWebService();
                consulta.execute();
            }

        }
    }

    // ************************************
    // Proceso que llena spinner de empresas
    protected void LlenaSpinnerEmpresas(int pidEmpresaSel) {
        // *****************************
        // llenando los datos de la lista de empresas
        // Log.e(TAG_ERROR, "5");
        try {
            // Log.e(TAG_ERROR, "6");
            Cursor c = almacenaImagen.CursorEmpresas();
            if (c==null)
            {
                // Log.e("Cursor", "nulo" );
            }
            else{
                // Log.e("Cursor", "No nulo" );
            }
            // Log.e(TAG_ERROR, "9");
            String[] from = new String[]{TAG_alias};
            int[] to = new int[]{android.R.id.text1};
            // This is your simple cursor adapter
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner s = findViewById(R.id.spinClientes);
            // Log.e(TAG_ERROR, "10");
            s.setAdapter(adapter);
            /*
            Aqui se genera un error no des comentar
            s.setSelection(Integer.valueOf(pidEmpresaSel) - 1);
            */
        } catch (Exception e) {
            funciones.RegistraError("","MainActivity,LlenaSpinnerEmpresas", e, MainActivity.this, getApplicationContext());
        }
    }

    // ************************************
    // Clase que consulta datos del promotor
    class ConsultaWebService extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;
        String name = "";
        String email = "";
        String token = "";
        String expira = "";

        @Override
        protected void onPreExecute() {
            // TODO /Promotor/obtenerpromotor2.php
            sRuta = TAG_SERVIDOR + "/Promotor/obtenerpromotor2.php?"
                    + "idusuario=" + txtUsuario.getText().toString().trim()
                    + "&clave=" + txtPwd.getText().toString().trim()
                    + "&idempresa=" + pIdempresa;

            // Log.e(TAG_ERROR, sRuta);
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
                sRespusta = sb.toString();
                //Log.e(TAG_ERROR, sRespusta);

            } catch (Exception e) {
                Error = e.getMessage();
                funciones.RegistraError(txtUsuario.getText().toString().trim(), "MainActivity,ConsultaWebService", e, MainActivity.this, getApplicationContext());
                // Toast.makeText(getApplicationContext(), " Error al obtener los datos del promotor, favor de intentar nuevamente en un sitio con mayor señal" +  Error,Toast.LENGTH_LONG).show();
                this.cancel(true);
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    funciones.RegistraError(txtUsuario.getText().toString().trim(), "MainActivity,ConsultaWebService", ex, MainActivity.this, getApplicationContext());
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

                    int iResp = jsonResponse.getInt(TAG_IDPROMOTOR);
                    //Log.e("RESPUESTA", String.valueOf( iResp));
                    if (iResp > 0) {
                        // ******************************
                        // Obtención de variables y acceso al sistema
                        pidPromotor = jsonResponse.getInt(TAG_IDPROMOTOR);
                        name = jsonResponse.getString(TAG_NAME);
                        email = jsonResponse.getString(TAG_EMAIL);
                        token = jsonResponse.getString(TAG_ACCESSTOKEN);
                        expira = jsonResponse.getString(TAG_EXPIRESIN);

                        // Log.e(TAG_ERROR, "pidPromotor =" + pidPromotor);
                        pName = name;
                        pEmail = email;
                        pToken = token;
                        pExpira = expira;
                        // ******************************
                    } else {
                        pidPromotor = 0;

                    }

                } catch (JSONException e) {
                    pidPromotor = 0;

                    // String Resultado = "Se generó el siguiente error : " + e.toString();
                    // funciones.RegistraError(txtUsuario.getText().toString().trim(), "MainActivity,ConsultaWebService Proceso de lectura de datos", e, MainActivity.this, getApplicationContext());
                    // Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al obtener las variables de tiendas " +  Resultado,Toast.LENGTH_LONG).show();
                }
            }
            // **************************
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
           pDialog.dismiss();

            // Log.e(TAG_ERROR, "Antes de actualiza 1");
            boolean bRes = Verifica();
            // Log.e(TAG_ERROR, "Antes de actualiza 2");
            if (bRes && !bActualiza){
                // ***************************
                // Inicio de lista de tiendas
                Intent listatiendas = new Intent(getApplicationContext(), listatiendas.class);

                //Log.e(TAG_ERROR, "** pidPromotor = "+ pidPromotor);

                listatiendas.putExtra(TAG_IDPROMOTOR, pidPromotor);
                listatiendas.putExtra(TAG_IDEMPRESA, pIdempresa);
                listatiendas.putExtra(TAG_NAME, pName);
                listatiendas.putExtra(TAG_EMAIL, pEmail);
                listatiendas.putExtra(TAG_ACCESSTOKEN, pToken);
                listatiendas.putExtra(TAG_EXPIRESIN, pExpira);
                listatiendas.putExtra(TAG_CONSULTAENWEB, 1);    // Indica que debe buscar en la lista de tiendas en web

                Usuario usr = new Usuario(pidPromotor, pName, pEmail, pToken, pExpira, pIdempresa, false);
                startActivity(listatiendas);
                // ***************************
            }

            // Log.e(TAG_ERROR, "Antes de actualiza 3");
            if (bRes && bActualiza) {
                // **************************
                // Llamado a la consulta del servicio web si hay internet
                ConsultaTiendas consulta = new ConsultaTiendas();
                // Log.e(TAG_ERROR, "Ejecutando ConsultaTiendas");
                consulta.execute();
                Toast.makeText(getApplicationContext(), "Proceso de actualización concluido" ,Toast.LENGTH_LONG).show();
            }

        }
    }

    // ************************************
    // Proceso que verifica datos para ingresar
    protected boolean Verifica() {
        // *********************************************************
        // Proceso que verifica el resultado para ingresar
        if (pidPromotor > 0) {

            // ******************************
            // Aqui se inicia el servicio
            // Solo inicia el servicio de localización la primera ocasión
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            String nombre = txtUsuario.getText().toString();
            String pwd = txtPwd.getText().toString();

            // ***************************
            // Inserta o actualiza promotor en la tabla cat_promotores
            almacenaImagen.insertaoactualizapromotor(pidPromotor, nombre, pwd, pIdempresa);

            // Establece el nombre del usuario en las preferencias
            SharedPreferences preferencias =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = preferencias.edit();
            editor.putString(TAG_USUARIO, nombre);
            editor.putString(TAG_IDPROMOTOR, String.valueOf(pidPromotor));
            // Log.e(TAG_ERROR, "idUsuario enviado " + nombre +  " pidPromotor " + String.valueOf(pidPromotor));
            editor.commit();
            return true;

        } else {
            Toast toast2 = Toast.makeText(getApplicationContext(),
                    "Acceso no permitido", Toast.LENGTH_SHORT);
            toast2.show();
            return false;
        }
        // *********************************************************

    }

    // ************************************
    // Activa boton de inicio
    private void enableSubmitIfReady() {
        EditText txtUsuario = findViewById(R.id.txtNombre);
        EditText txtPwd = findViewById(R.id.txtPwd);


        if (txtUsuario.getText().toString().length() >= 5)
            isReady.set(true);
        else
            isReady.set(false);

        if (isReady.get() && (txtPwd.getText().toString().length() >= 5)) {
            isReady.set(true);
        } else {
            isReady.set(false);
        }

        cmdIngresar.setEnabled(isReady.get());
    }

    // ************************************
    // Función que valida si es después de la 8pm
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isAfter8PM(String timeString) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime timeToCompare;
        try {
            timeToCompare = LocalTime.parse(timeString, timeFormatter);
        } catch (DateTimeParseException e) {
            // Handle the case where the time string is not in the expected format
            System.out.println("Invalid time format: " + e.getMessage());
            return false;
        }

        LocalTime eightPM = LocalTime.of(20, 0); // 20:00 in 24-hour format

        return timeToCompare.isAfter(eightPM);
    }

    // ************************************
    // Clase que carga las fotos guardadas
    class CargaEmpresas extends AsyncTask<String, Void, String> {
        String sRuta = null;
        String Error = null;
        String data = "";
        int jj = 0;
        int[] pIdempresa = new int[100];
        String[] nombreEmpresa = new String[100];
        String[] aliasEmpresa = new String[100];
        String pidEmpresaSel = "1";


        int i = 0;

        @Override
        protected void onPreExecute() {
            // TODO /CatalogoProductos/obtenerempresas.php
            sRuta = TAG_SERVIDOR + "/Promotor/obtenerempresas.php";
            // Log.e(TAG_ERROR, sRuta);

            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Consultando las empresas en Web  ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            pidEmpresaSel = params[0];
            BufferedReader reader = null;
            String sRespuesta = null;
            // Log.e(TAG_ERROR, "a");

            try {
                // Defined URL where to send data
                URL url = new URL(sRuta);

                // Log.e(TAG_ERROR, "b");
                // Send POST data request
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                wr.write(data);
                wr.flush();
                // Log.e(TAG_ERROR, "c");
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
                // Log.e(TAG_ERROR, "d");
                // **************************
                // Proceso de lectura de datos
                if (Error != null) {
                    String Resultado = "Se generó el siguiente error : " + Error;
                } else {
                    try {
                        // Log.e(TAG_ERROR, "e");
                        JSONObject jsonResponse, jsonChidNode, jsonObjEmpresa;
                        JSONArray jsonarray;
                        // Revisando la respusta
                        assert sRespuesta != null;
                        jsonResponse = new JSONObject(sRespuesta);
                        jsonarray = jsonResponse.getJSONArray(TAG_RESPUESTA);       // Arreglo
                        int iLongitudArreglo = jsonarray.length();
                        // Log.e(TAG_ERROR, "f");
                        if (iLongitudArreglo > 0) {
                            almacenaImagen.TruncarTabla(15);
                        }

                        // Log.e(TAG_ERROR, "g");
                        for (int i = 0; i < iLongitudArreglo; i++) {
                            jsonChidNode = jsonarray.getJSONObject(i);
                            // Log.e(TAG_ERROR, "h");
                            // almacenaImagen.inserta_empresa(0, "Seleccionar", "***");
                            if (jsonChidNode.has(TAG_EMPRESA)) {
                                jsonObjEmpresa = jsonChidNode.getJSONObject(TAG_EMPRESA);
                                // Colocacion de datos en los arreglos

                                // Log.e(TAG_ERROR, "i");
                                pIdempresa[i] = Integer.parseInt(jsonObjEmpresa.getString(TAG_IDEMPRESA));
                                nombreEmpresa[i] = jsonObjEmpresa.getString(TAG_nombreempresa);
                                aliasEmpresa[i] = jsonObjEmpresa.getString(TAG_alias);
                                almacenaImagen.inserta_empresa(pIdempresa[i], nombreEmpresa[i], aliasEmpresa[i]);
                                // Log.e(TAG_ERROR, "j");
                            }
                        }

                    } catch (JSONException e) {
                        String Resultado = "Se generó el siguiente error : " + e.toString();
                        // Log.e(TAG_ERROR, "k");
                        funciones.RegistraError(txtUsuario.getText().toString().trim(), "MainActivity,ConsultaWebService Cargar Empresas", e, MainActivity.this, getApplicationContext());
                    }
                }

            } catch (Exception ex) {
                Error = ex.getMessage();
                funciones.RegistraError(txtUsuario.getText().toString().trim(), "MainActivity,ConsultaWebService Cargar Empresas 1", ex, MainActivity.this, getApplicationContext());
                // Log.e(TAG_ERROR, Error);
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    funciones.RegistraError(txtUsuario.getText().toString().trim(), "MainActivity,ConsultaWebService Cargar Empresas 2", ex, MainActivity.this, getApplicationContext());
                    // Log.e(TAG_ERROR, Error);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            // Verificando si tiene un dato
            if (pidEmpresaSel != "") {
                // Log.e(TAG_ERROR, "l");
                LlenaSpinnerEmpresas(Integer.valueOf(pidEmpresaSel));
                // Log.e(TAG_ERROR, "m");
            }
            pDialog.dismiss();
        }

    }

    //************************************
    // Clase que consulta las tiendas
    //  Obtiene la de  informacion de todos los catalogos para luego compararla
    //  con los de sqlite  e insertar lo nuevo
    class ConsultaTiendas extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;
        String versionapp = null;
        String sTienda="";

        @Override
        protected void onPreExecute()
        {
            // TODO ****************************
            // TODO EN ESTA SECCIÓN SE DESCARGAN TODOS LOS DATOS DESDE LA PLATAFORMA USANDO EL API /Promotor/obtenertiendaspromotor.php Obtiene la de  informacion de todos los catalogos para luego compararla con los de sqlite  e insertar lo nuevo
            // TODO ****************************

            if (sTienda.equals("")) {
                sRuta = TAG_SERVIDOR + "/Promotor/obtenertiendaspromotor.php?idpromotor=" + pidPromotor + "&tienda=%&idempresa=" + pIdempresa;
            }
            else{
                sRuta = TAG_SERVIDOR + "/Promotor/obtenertiendaspromotor.php?idpromotor=" + pidPromotor + "&tienda=" + sTienda + "&idempresa=" + pIdempresa;
            }
            // Log.e(TAG_ERROR, "Consulta Tiendas " + sRuta);

            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
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
                funciones.RegistraError(pName, "listatiendas,ConsultaTiendas", ex, MainActivity.this, getApplicationContext());
                // Log.e("Error al procesar api: ", Error);
            } finally {
                try {
                    assert reader != null;
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    funciones.RegistraError(pName, "listatiendas,ConsultaTiendas 1", ex, MainActivity.this, getApplicationContext());
                    //Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error en el proceso de lectura de datos " +  Error,Toast.LENGTH_LONG).show();
                    Log.e("Error al procesar api: ", Error);
                }
            }

            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                String Resultado = "Se generó el siguiente error : " + Error;
                //funciones.RegistraError(pName, "listatiendas,ConsultaTiendas 1", Error, MainActivity.this, getApplicationContext());
                //Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error en el proceso de lectura de datos " +  Resultado,Toast.LENGTH_LONG).show();
                Log.e(TAG_ERROR, Resultado);
            } else {
                try {
                    JSONObject jsonResponse, jsonChidNode, jsonObjRuta, jsonObjProd, jsonObjProdFto;
                    JSONObject jsonObjRutaCat, jsonObjVista, jsonObjCadena, jsonObjObs, jsonObjProm, jsonObjAct, jsonObjEmp, jsonObjConf, jsonIncidencia, jsonRespIncidencia;
                    JSONArray jsonarray;
                    // Revisando la respuesta
                    assert sRespusta != null;
                    jsonResponse = new JSONObject(sRespusta);
                    jsonarray = jsonResponse.getJSONArray(TAG_RESPUESTA);       // Arreglo
                    iLongitudArreglo = jsonarray.length();

                    // ******************************************
                    // Ciclo de obtención de datos del servicio web
                    int jj = 0, k = 0, l = 0, m = 0, n = 0, o = 0, p = 0, q = 0, r = 0, s = 0, t = 0, u = 0, v = 0;
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
                        else if(jsonChidNode.has(TAG_INCIDENCIA))
                        {
                            // Log.e(TAG_INCIDENCIA, TAG_INCIDENCIA);
                            // Obtiene información de solicita inv
                            jsonIncidencia = jsonChidNode.getJSONObject(TAG_INCIDENCIA);
                            idincidencia[u] = Integer.parseInt(jsonIncidencia.getString(TAG_IDINCIDENCIA));
                            descripcion_incidencia[u]= jsonIncidencia.getString(TAG_DESCRIPCIONINCIDENCIA);

                            // Log.e(TAG_INCIDENCIA, String.valueOf(idincidencia[u]));
                            // Log.e(TAG_INCIDENCIA,descripcion_incidencia[u]);
                            u++;
                        }
                        else if(jsonChidNode.has(TAG_RESPINCIDENCIA))
                        {
                            // Log.e(TAG_RESPINCIDENCIA, TAG_RESPINCIDENCIA);
                            jsonRespIncidencia = jsonChidNode.getJSONObject(TAG_RESPINCIDENCIA);
                            // Log.e(TAG_RESPINCIDENCIA, String.valueOf(jsonRespIncidencia.length()));

                            idinc_ri[v] = Integer.parseInt(jsonRespIncidencia.getString(TAG_IDINC));
                            idincidencia_ri[v]= Integer.parseInt(jsonRespIncidencia.getString(TAG_IDINCIDENCIA));
                            idfoto_ri[v] = Integer.parseInt(jsonRespIncidencia.getString(TAG_IDFOTO));
                            idpromotor_ri[v]= Integer.parseInt(jsonRespIncidencia.getString(TAG_IDPROMOTOR));
                            idruta_ri[v] = Integer.parseInt(jsonRespIncidencia.getString(TAG_IDRUTA));
                            fechahora_ri[v]= jsonRespIncidencia.getString(TAG_FECHAHORA);
                            observaciones_ri[v] = (jsonRespIncidencia.getString(TAG_OBSERVACIONES));
                            respuesta_ri[v]= jsonRespIncidencia.getString(TAG_RESPUESTA);
                            fechahora_respuesta_ri[v] = (jsonRespIncidencia.getString(TAG_FECHAHORARESPUESTA));
                            image_ri[v] = (jsonRespIncidencia.getString(TAG_IMAGE));
                            leida_ri[v]= (jsonRespIncidencia.getString(TAG_LEIDA));
                            v++;
                        }
                        // Log.e(TAG_RESPINCIDENCIA, "Lecturas de incidencia " + String.valueOf(v));
                    }

                    int iCuenta = almacenaImagen.ObtenRegistrosTiendas(pidPromotor,sTienda);
                    iLongitudArregloTiendas = iCuenta;
                    int iCuentaProductos  = almacenaImagen.ObtenRegistros(1);
                    int iCuentaProFtoPrecio  = almacenaImagen.ObtenRegistros(2);
                    int iCuentaRutas  = almacenaImagen.ObtenRegistros(3);
                    int iCuentaVista  = almacenaImagen.ObtenRegistros(4);
                    int iCuentaCadena  = almacenaImagen.ObtenRegistros(5);
                    int iCuentaObs = almacenaImagen.ObtenRegistros(6);
                    int iCuentaPromo = almacenaImagen.ObtenRegistros(7);
                    int iCuentaActiv = almacenaImagen.ObtenRegistros(8);
                    int iCuentaEmpaque = almacenaImagen.ObtenRegistros(13);
                    int iCuentaCatalIncidencias = almacenaImagen.ObtenRegistros(21);

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
                    // Log.e(TAG_ERROR, " conteo de productos " + k);
                    // ******************************************
                    // Inserciòn de productosformatoprecio  si el numero de registros es diferente
                    if (iCuentaProFtoPrecio != l){
                        //pDialog.setMessage("Inserción de precios ...");
                        almacenaImagen.TruncarTabla(2);
                        for (int a = 0; a < l; a++) {
                            almacenaImagen.inserta_productoformatoprecio(idproductoformatoprecio[a], idproducto1[a],idformato[a], idempresa[a], precio[a], udc1[a], fdc1[a], uda1[a], fda1[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de formatos precio " + l);
                    // ******************************************
                    // Inserciòn de rutas si el numero de registros es diferente
                    if (iCuentaRutas != m){
                        //pDialog.setMessage("Inserción de rutas ...");
                        almacenaImagen.TruncarTabla(3);
                        for (int a = 0; a < m; a++) {
                            almacenaImagen.inserta_rutas(idruta[a], idcadena[a], idformato1[a],1, udc1[a], fdc1[a], uda1[a], fda1[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de rutas " + m);
                    // ******************************************
                    // Inserción de vista que siempre se debe de hacer para tener actualizados los precios
                    //if (iCuentaVista != n){
                    if (n>0){
                        almacenaImagen.TruncarTabla(4);
                        for (int a = 0; a < n; a++) {
                            almacenaImagen.inserta_vista(idproducto2[a], idruta2[a], precioreal[a], fda2[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de vistas " + n);
                    // ******************************************
                    // Inserciòn de cadena
                    if (iCuentaCadena != o){
                        almacenaImagen.TruncarTabla(5);
                        for (int a = 0; a < o; a++) {
                            almacenaImagen.inserta_cadena(idcadena[a], idempresa[a], nombrecorto[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de cadenas " + o);
                    // ******************************************
                    // Inserción de observaciones
                    if (iCuentaObs != p){
                        almacenaImagen.TruncarTabla(6);
                        for (int a = 0; a < p; a++) {
                            almacenaImagen.inserta_obs(idobs[a], observaciones[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de observaciones " + p);
                    // ******************************************
                    // Inserción de vw_promociones
                    if (iCuentaPromo != q){
                        almacenaImagen.TruncarTabla(7);
                        for (int a = 0; a < q; a++) {
                            almacenaImagen.inserta_promo(idpromocion[a],idempresa1[a], nombre[a], capacidad[a], canal[a], alcance[a], inicio[a], fina[a], periodo[a], actividad[a], precioregular[a], preciopromocion[a], idformato2[a], idproducto3[a], ruta1[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de promociones " + q);
                    // ******************************************
                    // Inserción de cat_actividad
                    if (iCuentaActiv != r){
                        almacenaImagen.TruncarTabla(8);
                        for (int a = 0; a < r; a++) {
                            almacenaImagen.inserta_actividad(idactividad[a],actividad[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de actividades " + r);
                    // ******************************************
                    // Inserción de cat_empaque
                    if (iCuentaEmpaque != s){
                        almacenaImagen.TruncarTabla(12);
                        for (int a = 0; a < s; a++) {
                            almacenaImagen.inserta_empaque(idempaque[a],empaque[a]);
                        }
                    }
                    // Log.e(TAG_ERROR, " conteo de incidencias " + t);
                    // ******************************************
                    // Inserción de cat_empaque
                    Log.e(TAG_ERROR, " conteo de incidencias " + iCuentaCatalIncidencias);
                    Log.e(TAG_ERROR, " valor de u " + u);
                    if (iCuentaCatalIncidencias != u){
                        almacenaImagen.TruncarTabla(21);
                        for (int a = 0; a < u; a++) {
                            almacenaImagen.inserta_incidencia(idincidencia[a],descripcion_incidencia[a]);
                        }
                        // Log.e(TAG_ERROR, " conteo de incidencias " + t);
                    }

                    // ******************************************
                    // Inserción de resp_incidencias que no hayan sido descargadas
                    for (int a = 0; a < v; a++) {
                        almacenaImagen.inserta_respuestaincidencia(idinc_ri[a], idincidencia_ri[a], idfoto_ri[a], idpromotor_ri[a], idruta_ri[a], fechahora_ri[a],
                                observaciones_ri[a], respuesta_ri[a], fechahora_respuesta_ri[a], image_ri[a]);
                    }
                    Log.e(TAG_ERROR, " conteo de respuesta incidencias " + v);

                    // ******************************************
                    // Actualizaciòn de configuracion
                    if(solicita[0]> -1) {
                        almacenaImagen.TruncarTabla(10);
                        almacenaImagen.inserta_configuracion(solicita[0]);
                    }
                    // Log.e(TAG_ERROR, "Actualización de configuración");
                    // Obtiene la version aunque las tablas sean iguales
                    almacenaImagen.insertaversion_app(versionapp);
                    // ******************************************

                } catch (JSONException e) {
                    funciones.RegistraError(pName, "ListaTiendas,ConsultaTiendas2", e, MainActivity.this, getApplicationContext());
                    String Resultado = "Se generó el siguiente error : " + e.toString();
                    // Log.e(TAG_ERROR,Resultado);
                }
            }
            // **************************
            // Log.e(TAG_ERROR,"Final del proceso");
            return null;
        }

        @Override
        protected void onPostExecute(String file_url)
        {
            // Log.e(TAG_ERROR,"Final del proceso1");
            pDialog.dismiss();
        }
    }

    //************************************
    // Clase que carga las fotos guardadas, precios, competencia, promoción, caducidad, errores
    // Que se encuentren en el teléfono cargados en modo desconectado para luego subirlos en modo conectado

    public class CargaLasFotos extends AsyncTask<Void, Integer, Boolean> {
        int iMagenesGuardadas = 0;      // Imagenes guardadas
        int iPreciosCambiados = 0;      // Precios cambiados
        int iRegistrosCompetencia = 0;  // Registros competencia
        int iPromociones = 0;           // Promociones
        int iCaducidad = 0;             // Caducidades
        int iErrores = 0;               // Errores
        int iCompetenciaPromocion = 0;  // Datos de competencia promoción
        int iCanjes = 0;                // Datos de canjes
        int iIncidencias = 0;            // Datos de Incidencias

        protected Boolean doInBackground(Void... params) {
            progressStatus = 0;
            almacenaImagen = new AlmacenaImagen(getApplicationContext());
            iMagenesGuardadas = almacenaImagen.ObtenRegistros(0);
            iPreciosCambiados = almacenaImagen.ObtenRegistros(9);
            iRegistrosCompetencia = almacenaImagen.ObtenRegistros(10);
            iPromociones = almacenaImagen.ObtenRegistros(12);
            iCaducidad = almacenaImagen.ObtenRegistros(14);
            iErrores = almacenaImagen.ObtenRegistros(16);
            iCompetenciaPromocion= almacenaImagen.ObtenRegistros(18);
            iCanjes = almacenaImagen.ObtenRegistros(20);
            iIncidencias = almacenaImagen.ObtenRegistros(22);

            int iSumaCuentas =(iMagenesGuardadas+iPreciosCambiados+iRegistrosCompetencia+iPromociones+iCaducidad+iErrores+iCompetenciaPromocion+iCanjes+iIncidencia);

            // Log.e(TAG_ERROR, "VALOR DE progressStatus e iSumaCuentas son " + progressStatus + " y " + iSumaCuentas);
            Log.e(TAG_ERROR, "Hay " + iSumaCuentas + " elementos de iSumaCuentas");

            // TODO ****************************
            // TODO AQUI SE SUBIRAN TODAS LAS FOTOS Y REGISTROS EN A PETICION DEL PROMOTOR PARA QUE NO SE SATURE EL PROCESO
            // TODO ****************************
            int h = 0;
            iMagenesGuardadas = almacenaImagen.ObtenRegistros(0);
            iPreciosCambiados = almacenaImagen.ObtenRegistros(9);
            iRegistrosCompetencia = almacenaImagen.ObtenRegistros(10);
            iPromociones = almacenaImagen.ObtenRegistros(12);
            iCaducidad = almacenaImagen.ObtenRegistros(14);
            iErrores = almacenaImagen.ObtenRegistros(16);
            iCompetenciaPromocion= almacenaImagen.ObtenRegistros(18);
            iCanjes = almacenaImagen.ObtenRegistros(20);
            iIncidencias = almacenaImagen.ObtenRegistros(22);

            Log.e(TAG_ERROR, "Hay " + iMagenesGuardadas +  " imagenes guardadas");
            Log.e(TAG_ERROR, "Hay " + iPreciosCambiados+  " precios cambiados");
            Log.e(TAG_ERROR, "Hay " + iRegistrosCompetencia+  " registros competencia");
            Log.e(TAG_ERROR, "Hay " + iPromociones +  " promociones");
            Log.e(TAG_ERROR, "Hay " + iCaducidad +  " caducidades");
            Log.e(TAG_ERROR, "Hay " + iErrores +  " errores");
            Log.e(TAG_ERROR, "Hay " + iCompetenciaPromocion +  " competencia promociòn");
            Log.e(TAG_ERROR, "Hay " + iCanjes +  " canjes");
            Log.e(TAG_ERROR, "Hay " + iIncidencias +  " incidencias");

            int  iMag = 0;
            int  iPre = 0;
            int  iReg = 0;
            int  iPro = 0;
            int  iCad = 0;
            int  iErr = 0;
            int  iCom = 0;
            int  iCan = 0;
            int  iInc = 0;

            do {
                int i = 0;
                int j = 0;
                if (iMagenesGuardadas>0 && iMagenesGuardadas>iMag){
                    // Sube imagenes upload1a.php
                    i = almacenaImagen.Colocarfoto();
                    Log.e(TAG_ERROR, "ColocarFoto " + h);
                    iMag++;
                }
<<<<<<< HEAD
                else if(iPreciosCambiados>0){
                    // Log.e(TAG_INFO, "precios cambiados " + iPreciosCambiados);
=======
                else if(iPreciosCambiados>0 && iPreciosCambiados>iPre){
>>>>>>> development
                    i = almacenaImagen.ColocaPreciosCambiados();
                    Log.e(TAG_ERROR, "ColocaPreciosCambiados "  + h);
                    iPre++;
                }
                else if(iRegistrosCompetencia>0 && iRegistrosCompetencia>iReg){
                    // Sube imagenes upload_competencia.php
                    i = almacenaImagen.ColocaCompetencia();
                    Log.e(TAG_ERROR, "ColocaCompetencia " + h);
                    iReg++;
                }
                else if(iPromociones>0 && iPromociones>iPro){
                    i = almacenaImagen.ColocaPromocion();
                    Log.e(TAG_ERROR, "ColocarPromocion " + h);
                    iPro++;
                }
                else if(iCaducidad>0 && iCaducidad>iCad){
                    // Sube imagenes upload_caducidad.php
                    i = almacenaImagen.ColocaCaducidad();
                    Log.e(TAG_ERROR, "ColocarCaducidad " + h);
                    iCad++;
                }
                else if(iCompetenciaPromocion>0 && iCompetenciaPromocion>iCom){
                    i = almacenaImagen.ColocaCompetenciaPromocion();
                    Log.e(TAG_ERROR, "ColocarCompetenciaPromocion " + h);
                    iCom++;
                }
                else if(iCanjes>0 && iCanjes>iCan){
                    // Sube imagenes upload_canjes.php y upload_canjes_complemento.php
                    if (progressStatus <= iSumaCuentas) {
                        i = almacenaImagen.ColocaCanjes();
                        j = almacenaImagen.ColocaCanjesComplemento();
                        Log.e(TAG_ERROR, "ColocarCanjes " + h);
                        iCan++;
                    }
                }
                else if(iIncidencias>0 && iIncidencias>iInc){
                    // Sube imagenes de incidencias upload_incidencia.php
                    i = almacenaImagen.ColocaIncidencias();
                    Log.e(TAG_ERROR, "ColocarIncidencias " + h);
                    iInc++;
                }
                else if(iErrores>0 && iErrores>iErr){
                    i = almacenaImagen.ColocaErrores();
                    Log.e(TAG_ERROR, "ColocarErrores " + h);
                    iErr++;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    funciones.RegistraError(pName, "MainActivity, MainActivity", e, MainActivity.this, getApplicationContext() );
                }

                publishProgress(progressStatus+1);
                progressStatus += 1;
                h++;
                if(isCancelled())
                    break;

            }
            while (progressStatus < iSumaCuentas);

            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pDialog.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    CargaLasFotos.this.cancel(true);
                }
            });

            pDialog.setProgress(0);
            pDialog.show();
        }

        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            // Toast.makeText(MainActivity.this, "Carga finalizada!", Toast.LENGTH_SHORT).show();
            FloatingActionButton fab0 = findViewById(R.id.fab0);
            fab0.setVisibility(View.GONE);
            FloatingActionButton fab1 = findViewById(R.id.fab1);
            fab1.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "Tarea cancelada !", Toast.LENGTH_SHORT).show();
        }

    }

}
