package com.topmas.top;

import static com.topmas.top.Constants.CONST_ACCESOLOCAL;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_DISTANCIA;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_OPERACION;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;

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
    public static final String UPLOAD_ARREGLOPRODUCTO = "arregloproductos";
    public static final String UPLOAD_IDCANJE = "idcanje";
    public static final String UPLOAD_COMENTARIOS = "comentarios";
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
    public static final String UPLOAD_LLAVE = "llave";
    private int idpromotor = 0;
    private String idempresa = "";
    String idUsuario = "";
    private double pLatitud = 0;
    private double pLongitud = 0;
    private int iResp=0;            // Es el id de la tabla almacenfoto de la foto recien subida

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
        almacenaImagen = new AlmacenaImagen(getApplicationContext());

        idRuta =  i.getIntExtra(TAG_IDRUTA,0);
        idoperacion =  i.getIntExtra(TAG_OPERACION,0);
        idpromotor =  i.getIntExtra(TAG_IDPROMOTOR,0);
        if (idpromotor == 0){
            idpromotor = usr.getid();
        }

        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String spromotor = preferencias.getString(TAG_IDPROMOTOR, String.valueOf(idpromotor));
        idUsuario = preferencias.getString(TAG_USUARIO, idUsuario);
        idpromotor = Integer.valueOf(spromotor);
        // ***************************************

        idempresa = usr.getidempresa();
        btnFoto = findViewById(R.id.btnFoto);
        //Log.e(TAG_ERROR, "idUsuario recibido " + idUsuario + " idpromotor " + idpromotor);

        // Si la ruta es 0 se debe especificar una ruta valida
        if (idRuta == 0) {
            Toast.makeText(getApplicationContext(), " Esta ruta es invalida, favor de especificar una ruta correcta", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

        // Si la empresa es nulla es que se perdio el dato por lo que es conveniente volver a solicitarla de algun modo
        if (idempresa == null){
            Toast.makeText(getApplicationContext(), " Esta empresa es invalida, favor de especificar una ruta correcta", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(idUsuario, "Foto setDefaultUncaughtExceptionHandler", (Exception) throwable, Foto.this, getApplicationContext());
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
        imgizq.setOnClickListener(view -> {
            imagenFoto = findViewById(R.id.imagenFoto);
            if (imagenFoto.getDrawable() != null) {
                BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int angle = -90;
                //imagenFoto.setRotation(angle);
                bitmap = rotateImage(bitmap ,angle);
                imagenFoto.setImageBitmap(bitmap);
            }
        });

        // ********************************
        // Rotacion derecha
        imgder.setOnClickListener(view -> {
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
        });

        // ********************************
        // Boton foto puede tomar una foto
        btnFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Context context = getApplicationContext();
                Context contexto = getApplicationContext().getApplicationContext();
                int iFotosTienda = almacenaImagen.ObtenFotosTienda(idRuta);
                if (iFotosTienda>=20){
                    Toast.makeText(getApplicationContext() , " Solo se permiten 20 fotos por tienda " ,Toast.LENGTH_LONG).show();
                    return;
                }
                // ********************
                // Create the File where the photo should go
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al crear archivo de foto " +  ex.getMessage(),Toast.LENGTH_LONG).show();
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
                    funciones.RegistraError(idUsuario, "Foto setDefaultUncaughtExceptionHandler1", (Exception) ex, Foto.this, getApplicationContext());
                    // Log.e(TAG_ERROR, "Error camara " + ex.getMessage());
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al tomar la foto " +  ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        // ********************************
        // Boton para subir una foto
        btnSubir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // TODO Proceso para subir imágenes
                idpromotor = usr.getid();
                pLatitud = usr.getLatitud();
                pLongitud = usr.getLongitud();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                idUsuario = preferences.getString(TAG_USUARIO, idUsuario);
                // Log.e(TAG_ERROR, "idUsuario en foto " + idUsuario);

                try {
                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                }
                catch( java.lang.NullPointerException e)
                {
                    funciones.RegistraError(idUsuario, "Foto,btnSubir.setOnClickListener", e, Foto.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
                    return;
                }

                if(pLatitud==0 || idUsuario.length()==0 || pLongitud==0)
                {
                    Toast.makeText(getApplicationContext(),"No hay latitud, longitud o usuario, idUsuario: " + String.valueOf(idUsuario) + ", Longitud: " + String.valueOf(pLongitud) + ", Latitud: " + String.valueOf(pLatitud)  + " cierre la app e intente nuevamente",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    // ************************************************

                    // Si el acceso es local entonces guardara los datos de las imagenes
                    // en la tabla local de imagenes
                    // tambien la guarda  antes de enviarla ya que se detecto que en ocasiones
                    // después de haber tomado la foto no se envia correctamente
                    if (!funciones.RevisarConexion(getApplicationContext())) {
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

        // *****************************
        // Verifica si este promotor y ruta realizaron checkin el dìa de hoy si es asì no manda el mensaje
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        int iCheckin = almacenaImagen.consultaCheckinPromotorTienda(idpromotor, idRuta);

        // ***************************
        // Hace la validaciòn de los datos solamente si hay conexión de datos
        // de otra forma esta ingresando en modo desconectado y no debe hacer
        // la validación
        if (funciones.RevisarConexion(getApplicationContext()) && idpromotor>0){
            if (iCheckin==1 && (idoperacion==2 || idoperacion==3 || idoperacion==4)){
                // Aqui no se realiza la validación debido a que se hizo checkin y se guardo en el teléfono
                // por tanto este valor no estará en la base de datos del servidor
                // y no es necesario validarlo, solo para las operaciones 2,3 y 4
            }
            else {
                // Consulta operacion para saber si esa operacion ya se registro para esa ruta ese promotor y operacion
                // Si cualquiera de estas condiciones se cumple en automático el proceso
                // saca al promotor del intent y no puede continuar
                if (funciones.RevisarConexion(getApplicationContext())) {
                    ConsultaOperacion consulta = new ConsultaOperacion();
                    consulta.execute();
                }
            }
        }
        // ***************************
    }

    //***********************
    // Multiples validaciones, si alguna de ellas se cumple en automatico sale de este content
    protected void Verifica(int idpromotor, int idruta) {
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

                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                if (s.equals(TAG_CARGA_FOTO_EXITOSA)) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    btnSubir.setVisibility(View.INVISIBLE);
                    btnNoSubir.setVisibility(View.INVISIBLE);
                    imgizq.setVisibility(View.INVISIBLE);
                    imgder.setVisibility(View.INVISIBLE);
                }
                else if (s.equals(TAG_CARGA_FOTO_DISTANCIA)) {
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

                /*
                Log.e(TAG_ERROR, String.valueOf(idpromotor));
                Log.e(TAG_ERROR, String.valueOf(pLatitud));
                Log.e(TAG_ERROR, String.valueOf(pLongitud));
                Log.e(TAG_ERROR, idUsuario);
                Log.e(TAG_ERROR, String.valueOf(idoperacion));
                Log.e(TAG_ERROR, String.valueOf(idRuta));
                Log.e(TAG_ERROR, fechahora);
                 */

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
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
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
        catch( Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
    }

    //***********************
    // Guarda imágen
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

    //***********************
    // Proceso de guardado de foto
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagenFoto = findViewById(R.id.imagenFoto);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                funciones.grabImage(this, photoURI, imagenFoto);

                Button btnNoSubir = findViewById(R.id.btnNoSubir);
                Button btnSubir = findViewById(R.id.btnSubir);
                Button btnderecha = findViewById(R.id.btnderecha);
                Button btnizquierda = findViewById(R.id.btnizquierda);
                btnNoSubir.setVisibility(View.VISIBLE);
                btnSubir.setVisibility(View.VISIBLE);
                btnderecha.setVisibility(View.VISIBLE);
                btnizquierda.setVisibility(View.VISIBLE);

                pLatitud = usr.getLatitud();
                pLongitud = usr.getLongitud();

                // ***************************************
                // Obtiene el nombre del usuario en y promotor las preferencias
                SharedPreferences preferencias =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String spromotor = preferencias.getString(TAG_IDPROMOTOR, String.valueOf(idpromotor));
                idUsuario = preferencias.getString(TAG_USUARIO, idUsuario);
                idpromotor = Integer.valueOf(spromotor);
                // ***************************************

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
                iResp = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, idUsuario, idRuta, bitmap);

                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iCuenta = almacenaImagen.ObtenFotosTienda(idRuta);
                if (iResp == 0) {
                    Toast.makeText(getApplicationContext(), "No se permiten Chekin/out duplicados ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Imágenes almacenadas " + String.valueOf(iCuenta), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al tomar la foto, favor de intentar nuevamente", Toast.LENGTH_LONG).show();
        }
    }

    //***********************
    // Creación de imágen
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
            funciones.RegistraError(idUsuario, "Foto,createImageFile", e, Foto.this, getApplicationContext());
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

    // ********************************************
    // Consulta operación para validaciones
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

            super.onPreExecute();
            pDialog = new ProgressDialog(Foto.this);
            pDialog.setMessage("Consultando en el servicio Web ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            BufferedReader reader;
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
                funciones.RegistraError(idUsuario, "Foto,ConsultaOperacion", ex, Foto.this, getApplicationContext());
                Error = ex.getMessage();
                return null;
            }

            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                String Resultado = "Se generó el siguiente error : " + Error;

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
                    funciones.RegistraError(idUsuario, "Foto,ConsultaOperacion 2", e, Foto.this, getApplicationContext());
                    String Resultado = "Se generó el siguiente error : " + e.toString();
                }
            }/**/
            // **************************
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            Verifica(idpromotor, idoperacion);
            pDialog.dismiss();
        }
    }

}