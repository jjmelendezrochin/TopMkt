package com.topmas.top;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.topmas.top.Constants.CONST_ACCESOLOCAL;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_DISTANCIA;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_OPERACION;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;


public class Foto extends AppCompatActivity {
    // TODO PhotoUpload/upload1.php
    // TODO PhotoUpload/upload_errores.php

    public static final String UPLOAD_URL = TAG_SERVIDOR + "/PhotoUpload/upload1.php";  // Usado para pruebas
    public static final String UPLOAD_ERRORES = TAG_SERVIDOR + "/PhotoUpload/upload_errores.php";

    public static final String UPLOAD_IDPROMOTOR = "idpromotor";
    public static final String UPLOAD_LATITUD = "latitud";
    public static final String UPLOAD_LONGITUD = "longitud";
    public static final String UPLOAD_IDUSUARIO = "idusuario";
    public static final String UPLOAD_IDOPERACION = "idoperacion";
    public static final String UPLOAD_IDRUTA = "idruta";
    public static final String UPLOAD_FECHAHORA = "fechahora";
    public static final String UPLOAD_IMAGEN = "image";
    public static final String UPLOAD_IMAGEN1 = "image1";
    public static final String UPLOAD_VERSION = "version_app";
    public static final String UPLOAD_SINDATOS = "sindatos";

    public static final String UPLOAD_FABRICANTE = "fabricante";
    public static final String UPLOAD_MARCA = "marca";
    public static final String UPLOAD_MODELO = "modelo";
    public static final String UPLOAD_BOARD = "board";
    public static final String UPLOAD_HARDWARE = "hardware";
    public static final String UPLOAD_SERIE = "serie";
    public static final String UPLOAD_UID = "uid";
    public static final String UPLOAD_ANDROID_ID = "android_id";
    public static final String UPLOAD_RESOLUCION = "resolucion";
    public static final String UPLOAD_TAMANIOPANTALLA = "tamaniopantalla";
    public static final String UPLOAD_DENSIDAD = "densidad";
    public static final String UPLOAD_BOOTLOADER = "bootloader";
    public static final String UPLOAD_USER_VALUE = "user_value";
    public static final String UPLOAD_HOST_VALUE = "host_value";
    public static final String UPLOAD_API_VALUE = "api_value";
    public static final String UPLOAD_BUILD_ID = "build_id";
    public static final String UPLOAD_BUILD_TIME = "build_time";
    public static final String UPLOAD_FINGERPRINT = "fingerprint";
    public static final String UPLOAD_USUARIO = "usuario";
    public static final String UPLOAD_SECCION = "seccion";
    public static final String UPLOAD_ERROR = "error";

    private int idpromotor = 0;
    private String idempresa = "";
    String pName = "";
    private double pLatitud = 0;
    private double pLongitud = 0;
    private int iResp=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    private Acceso formaacceso;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imagenFoto;
    Button imgizq;
    Button imgder;
    Button btnSubir;
    Button btnNoSubir;

    ImageView btnFoto = null;
    String sRutaFoto;
    File photoFile = null;
    Uri photoURI = null;

    private ProgressDialog pDialog;
    private final Usuario usr = new Usuario();
    private int idoperacion=0, idRuta=0;
    Funciones funciones = new Funciones();
    AlmacenaImagen almacenaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        // Obteniendo parametros
        Intent i = getIntent();
        //idpromotor = i.getStringExtra(TAG_ID);
        almacenaImagen = new AlmacenaImagen(getApplicationContext());

        idRuta =  i.getIntExtra(TAG_IDRUTA,0);
        idoperacion =  i.getIntExtra(TAG_OPERACION,0);
        idpromotor =  i.getIntExtra(TAG_IDPROMOTOR,0);
        if (idpromotor == 0){
            idpromotor = usr.getid();
        }
        //idpromotor = usr.getid();
        idempresa = usr.getidempresa();
        btnFoto = findViewById(R.id.btnFoto);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pName = preferences.getString(TAG_USUARIO, pName);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "Foto setDefaultUncaughtExceptionHandler", (Exception) throwable, Foto.this, getApplicationContext());
        });

        switch (idoperacion) {
            case 1:
                btnFoto.setImageResource(R.drawable.img_checkin);
                break;
            case 2:
                btnFoto.setImageResource(R.drawable.img_checkout);
                break;
            case 3:
                btnFoto.setImageResource(R.drawable.img_anaquel_entrada);
                break;
            case 4:
                btnFoto.setImageResource(R.drawable.img_anaquel_salida);
                break;
        }

        // *****************************
        // Inicia Geolocalizaciòn
        funciones.locationStart(this);

        btnSubir =  findViewById(R.id.btnSubir);
        btnNoSubir=  findViewById(R.id.btnNoSubir);
        imgizq =  findViewById(R.id.btnizquierda);
        imgder =  findViewById(R.id.btnderecha);
        imagenFoto =  findViewById(R.id.imagenFoto);

        // Muestra imagen de inicio
         btnSubir.setVisibility(View.INVISIBLE);
         btnNoSubir.setVisibility(View.INVISIBLE);
         imgizq.setVisibility(View.INVISIBLE);
         imgder.setVisibility(View.INVISIBLE);

        // ********************************
        // Rotacion izquierda
        imgizq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imagenFoto = findViewById(R.id.imagenFoto);
                if (imagenFoto.getDrawable() != null) {
                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    int angle = -90;
                    //imagenFoto.setRotation(angle);
                    bitmap = rotateImage(bitmap ,angle);
                    imagenFoto.setImageBitmap(bitmap);
                }
            }
        });

        // ********************************
        // Rotacion derecha
        imgder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imagenFoto = findViewById(R.id.imagenFoto);
                if (imagenFoto.getDrawable() != null) {

                    pDialog = new ProgressDialog(Foto.this);
                    pDialog.setMessage("Consultando en el servicio Web ...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();

                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    int angle = 90;
                    //imagenFoto.setRotation(angle);
                    bitmap = rotateImage(bitmap ,angle);
                    imagenFoto.setImageBitmap(bitmap);

                    pDialog.dismiss();
                }
            }
        });

        // ********************************
        // Boton foto puede tomar una foto
        btnFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Context context = getApplicationContext();
                Context contexto = getApplicationContext().getApplicationContext();
                int iFotosTienda = almacenaImagen.ObtenFotosTienda(idRuta);
                if (iFotosTienda>=10){
                    Toast.makeText(getApplicationContext() , " Solo se permiten 10 fotos por tienda " ,Toast.LENGTH_LONG).show();
                    return;
                }
                // ********************
                // Create the File where the photo should go
                try {
                    photoFile = createImageFile();
                    // Log.e("Mensaje", "archivo creado" + photoFile.getAbsolutePath());
                } catch (IOException ex) {
                    // funciones.RegistraError(pName, "Foto,btnFoto.setOnClickListener", ex, Foto.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al crear archivo de foto " +  ex.getMessage(),Toast.LENGTH_LONG).show();
                    // Log.e(ERROR_FOTO, "Error al crear archivo de foto ");
                }

                // ********************
                // Tomar foto
                try {
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(contexto,
                                "com.topmas.android.fileprovider",
                                photoFile);
                        dispatchTakePictureIntent(photoURI);

                    }
                } catch (Exception ex) {
                    // funciones.RegistraError(pName, "Foto,btnFoto.setOnClickListener 1", ex, Foto.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al tomar la foto " +  ex.getMessage(),Toast.LENGTH_LONG).show();
                    // Log.e(ERROR_FOTO, ex.getMessage());
                }
            }
        });

        // ********************************
        // Boton para subir una foto
        btnSubir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                idpromotor = usr.getid();
                pName = usr.getnombre();
                String idUsuario = usr.getidusuario();
                pLatitud = usr.getLatitud();
                pLongitud = usr.getLongitud();


                try {
                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                }
                catch( java.lang.NullPointerException e)
                {
                    // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
                    // funciones.RegistraError(pName, "Foto,btnSubir.setOnClickListener", e, Foto.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
                    return;
                }

                if(pLatitud==0 || idUsuario.length()==0 || pLongitud==0)
                {
                    // funciones.RegistraError(pName, "Foto,btnSubir.setOnClickListener",null, Foto.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Sucedió un error favor de intentar nuevamente, idUsuario: " + String.valueOf(idUsuario) + ", Longitud: " + String.valueOf(pLongitud) + ", Latitud: " + String.valueOf(pLatitud) ,
                            Toast.LENGTH_LONG).show();
                }
                else {
                    // ************************************************
                    int iFormaAcceso = formaacceso.ObtieneAcceso();
                    // Si el acceso es local entonces guardara los datos de las imagenes
                    // en la tabla local de imagenes
                    // tambien la guarda  antes de enviarla ya que se detecto que en ocasiones
                    // después de haber tomado la foto no se envia correctamente

                    if (iFormaAcceso == CONST_ACCESOLOCAL){
                        Toast.makeText(getApplicationContext(),"Las Fotos almacenadas, se enviarán cuando tenga datos", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        if (funciones.RevisarConexion(getApplicationContext())) {
                            uploadImage();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No hay conexion favor de verificar, la foto ya esta guardada, en la siguiente conexión se subirá a la plataforma", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        // ***************************
        // BOTON NO SUBIR
        btnNoSubir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // regresa al intent anterior
                finish();
            }
        });

        // ***************************
        // Hace la validaciòn de los datos solamente si hay conexión de datos
        // de otra forma esta ingresando en modo desconectado y no debe hacer
        // la validación
        if (funciones.RevisarConexion(getApplicationContext()) && idpromotor>0){
            //  Consulta operacion para saber si esa operacion ya se registro para esa ruta ese promotor y operacion
            ConsultaOperacion consulta = new ConsultaOperacion();
            consulta.execute();
        }
        else{
            // Log.e("idpromotor modo desconectado", String.valueOf(idpromotor));
        }

        // ***************************
    }

    protected void Verifica() {
        // *********************************************************
        // Proceso que verifica el resultado para regresar si ya realizo esa operacion
        switch (iResp)
        {
            case 0:
                break;
            case 1:
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Usted ya realizó esta operación", Toast.LENGTH_LONG);
                toast1.show();
                finish();
                break;
            case 2:
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Debe de realizar Checkout en la tienda anterior antes de realizar Checkin en esta", Toast.LENGTH_LONG);
                toast2.show();
                finish();
                break;
            case 3:
                Toast toast3 = Toast.makeText(getApplicationContext(),
                        "No se puede realizar Checkout sin haber realizado Checkin en una tienda", Toast.LENGTH_LONG);
                toast3.show();
                finish();
                break;
            case 4:
                Toast toast4 = Toast.makeText(getApplicationContext(),
                        "No se puede realizar Inventario Entrada/Salida sin haber realizado Checkin en esta tienda", Toast.LENGTH_LONG);
                toast4.show();
                finish();
                break;
        }
        // *********************************************************

    }

    //***********************
    // Upload image function
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Foto.this);
                pDialog.setMessage("Enviando datos ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();

                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int i = almacenaImagen.BorraFotoEnviada(iResp);
                // Log.e(TAG_ERROR, "Se Borro la foto almacenada " + iResp);
                // Log.e(TAG_ERROR, "Respuesta  " + s);
                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                if (s == TAG_CARGA_FOTO_EXITOSA) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    btnSubir.setVisibility(View.INVISIBLE);
                    btnNoSubir.setVisibility(View.INVISIBLE);
                    imgizq.setVisibility(View.INVISIBLE);
                    imgder.setVisibility(View.INVISIBLE);
                }
                else if (s == TAG_CARGA_FOTO_DISTANCIA) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    btnSubir.setVisibility(View.VISIBLE);
                    btnNoSubir.setVisibility(View.VISIBLE);
                    imgizq.setVisibility(View.VISIBLE);
                    imgder.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    btnSubir.setVisibility(View.VISIBLE);
                    btnNoSubir.setVisibility(View.VISIBLE);
                    imgizq.setVisibility(View.VISIBLE);
                    imgder.setVisibility(View.VISIBLE);
                }
                finish();
                // **************************************
            }


            @Override
            protected String doInBackground(Bitmap... params) {
                Funciones funciones = new Funciones();
                Bitmap bitmap = params[0];
                bitmap = funciones.Compacta(bitmap);
                bitmap = funciones.Compacta(bitmap);
                bitmap = funciones.Compacta(bitmap);
                String uploadImage = almacenaImagen.getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechahora = sdf.format(c.getTime());

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp =  versionName + ":" + versionCode;

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, pName);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_URL,data);
            }
        }

        try {
            UploadImage ui = new UploadImage();
            Bitmap bm=((BitmapDrawable)imagenFoto.getDrawable()).getBitmap();
            ui.execute(bm);
        }
        catch( java.lang.NullPointerException e)
        {
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            // funciones.RegistraError(pName, "Foto,Uploadimage 0", e, Foto.this, getApplicationContext());
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
        catch( Exception e)
        {
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            // funciones.RegistraError(pName, "Foto,Uploadimage 1", e, Foto.this, getApplicationContext());
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
    }


    private void dispatchTakePictureIntent(Uri photoURI) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imagenFoto = findViewById(R.id.imagenFoto);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                funciones.grabImage(this,photoURI,imagenFoto);

                Button btnNoSubir = findViewById(R.id.btnNoSubir);
                Button btnSubir = findViewById(R.id.btnSubir);
                Button btnderecha = findViewById(R.id.btnderecha);
                Button btnizquierda = findViewById(R.id.btnizquierda);
                btnNoSubir.setVisibility(View.VISIBLE);
                btnSubir.setVisibility(View.VISIBLE);
                btnderecha.setVisibility(View.VISIBLE);
                btnizquierda.setVisibility(View.VISIBLE);


                idpromotor = usr.getid();
                pName = usr.getnombre();
                pLatitud = usr.getLatitud();
                pLongitud = usr.getLongitud();

                // **********************
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime()).trim();
                // **********************
                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                // ***********************
                // Guardar la imagen despues de tomarla
                iResp = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, pName, idRuta, bitmap);
                // Log.e(TAG_ERROR, "Se Guardo la foto almacenada " + iResp);
                //Toast.makeText(getApplicationContext(), "Foto Guardada", Toast.LENGTH_LONG).show();

                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iCuenta = almacenaImagen.ObtenFotosTienda(idRuta);
                if(iResp==0)
                {
                    Toast.makeText(getApplicationContext(), "No se permiten Chekin/out duplicados ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Imágenes almacenadas " + String.valueOf(iCuenta),Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch( java.lang.NullPointerException e)
        {
            // funciones.RegistraError(pName, "Foto,Uploadimage 2", e, Foto.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
        catch( Exception e)
        {
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            //funciones.RegistraError(pName, "Foto,Uploadimage 3", e, Foto.this, getApplicationContext());
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            sRutaFoto = image.getAbsolutePath();
            return image;
        } catch (IOException e) {
            funciones.RegistraError(pName, "Foto,createImageFile", e, Foto.this, getApplicationContext());
            //e.printStackTrace();
            return null;
        }
    }

    // ********************************
    // Rotación de imagen
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    // ********************************************
    // Cambia el tamaño de un bitmap
    public static Bitmap resizeBitmap(final Bitmap input, int destWidth, int destHeight, int rotation) throws OutOfMemoryError {

        int dstWidth = destWidth;
        int dstHeight = destHeight;
        final int srcWidth = input.getWidth();
        final int srcHeight = input.getHeight();

        boolean needsResize = false;
        float p;
        if ((srcWidth > dstWidth) || (srcHeight > dstHeight)) {
            needsResize = true;
            if ((srcWidth > srcHeight) && (srcWidth > dstWidth)) {
                p = (float) dstWidth / (float) srcWidth;
                dstHeight = (int) (srcHeight * p);
            } else {
                p = (float) dstHeight / (float) srcHeight;
                dstWidth = (int) (srcWidth * p);
            }
        } else {
            dstWidth = srcWidth;
            dstHeight = srcHeight;
        }

        if (needsResize || rotation != 0) {
            Bitmap output;

            if (rotation == 0) {
                output = Bitmap.createScaledBitmap(input, dstWidth, dstHeight, true);
            } else {
                Matrix matrix = new Matrix();
                matrix.postScale((float) dstWidth / srcWidth, (float) dstHeight / srcHeight);
                matrix.postRotate(rotation);
                output = Bitmap.createBitmap(input, 0, 0, srcWidth, srcHeight, matrix, true);
            }
            return output;
        } else
            return input;
    }

    class ConsultaOperacion extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;
        int id = 0;

        @Override
        protected void onPreExecute() {
            // TODO /Promotor/obteneroperacion1.php
            sRuta = TAG_SERVIDOR + "/Promotor/obteneroperacion1.php?"+
                    "idruta=" + idRuta +
                    "&idpromotor=" + idpromotor +
                    "&idoperacion=" + idoperacion +
                    "&idempresa="+ idempresa;
            //Log.e("idruta", String.valueOf(idRuta));
            //Log.e("idpromotor", String.valueOf(idpromotor));
            //Log.e("sruta", sRuta);
            // Log.e("idoperacoin", String.valueOf(idoperacion));
            // Log.e("idempresa", String.valueOf(idempresa));

            super.onPreExecute();
            pDialog = new ProgressDialog(Foto.this);
            pDialog.setMessage("Consultando en el servicio Web ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // Log.i("Ruta", "0");
            BufferedReader reader= null;
            // Log.i("Ruta", "01");
            String sRespusta = null;
            // Log.i("Ruta", "02");

            try {
                // Defined URL where to send data
                URL url = new URL(sRuta);
                // Log.i("Ruta", "1");
                // Send POST data request
                URLConnection conn = url.openConnection();
                // Log.i("Ruta", "2");
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                // Log.i("Ruta", "3");
                wr.write(data);
                wr.flush();
                // Log.i("Ruta", "4");
                // Get the server response
                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                // Log.i("Ruta", "5");

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line).append(" ");
                }
                // Log.i("Ruta", "6");
                // Append Server Response To Content String
                sRespusta = sb.toString();
                // Log.i(TAG_RESPUESTA, sRespusta);/* */

            } catch (Exception ex) {
                funciones.RegistraError(pName, "Foto,ConsultaOperacion", ex, Foto.this, getApplicationContext());
                Error = ex.getMessage();
                // Log.i("Ruta", Error);
            }

            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                // funciones.RegistraError(pName, "Foto,ConsultaOperacion 1", Error.trim(), Foto.this, getApplicationContext());
                String Resultado = "Se generó el siguiente error : " + Error;

                // Log.i(TAG_RESPUESTA, Resultado);
            } else {
                try {
                    JSONObject jsonResponse;
                    // Revisando la respusta
                    if(sRespusta!=null) {
                        jsonResponse = new JSONObject(sRespusta);

                        iResp = jsonResponse.getInt(TAG_RESPUESTA);
                        //Log.i("Respuesta de foto", String.valueOf(iResp));
                    }
                } catch (JSONException e) {
                    funciones.RegistraError(pName, "Foto,ConsultaOperacion 2", e, Foto.this, getApplicationContext());
                    String Resultado = "Se generó el siguiente error : " + e.toString();
                    // Log.i(TAG_ERROR, Resultado);
                }
            }/**/
            // **************************
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            Verifica();
            pDialog.dismiss();
        }
    }

}