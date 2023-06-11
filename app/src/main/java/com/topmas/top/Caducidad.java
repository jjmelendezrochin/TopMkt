package com.topmas.top;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;

public class Caducidad extends AppCompatActivity {
    // TODO /CatalogoProductos/upload_caducidad.php
    public static final String UPLOAD_CADUCIDAD = TAG_SERVIDOR + "/PhotoUpload/upload_caducidad.php";
    public static final String UPLOAD_idproducto = "idproducto";
    public static final String UPLOAD_lote = "lote";
    public static final String UPLOAD_caducidad = "caducidad";
    public static final String UPLOAD_piezas = "piezas";

    public Date fechacaducidad = null;
    Button btnFoto= null;
    File photoFile = null;
    Uri photoURI = null;
    String sRutaFoto;
    ImageView imagenFoto;

    double pLatitud = 0;
    double pLongitud = 0;

    int idpromotor = 0;
    int idproducto = 0;
    int idRuta = 0;
    int idoperacion = 0;
    int iFoto=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    String lote;
    int piezas;

    ProgressDialog pDialog;

    Usuario usr = new Usuario();
    String pName = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Funciones funciones = new Funciones();
    AlmacenaImagen almacenaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caducidad);

        // Obteniendo parametros y preferencias
        Intent i = getIntent();
        idRuta =  i.getIntExtra(TAG_IDRUTA,0);
        idoperacion =  6;       // OPERACIÓN FOTO COMPETENCIA
        almacenaImagen = new AlmacenaImagen(getApplicationContext());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pName = preferences.getString(TAG_USUARIO, pName);

        // Activity activity = (Activity) context;
        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "Caducidad setDefaultUncaughtExceptionHandler", (Exception) throwable, Caducidad.this, getApplicationContext());
        });


        // *****************************
        // llenando los datos de la lista de productos
        Cursor c = almacenaImagen.CursorProductos();
        String[] from = new String[]{TAG_UPC};
        int[] to = new int[]{android.R.id.text1};
        // This is your simple cursor adapter
        //SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner spinProducto = (Spinner) findViewById( R.id.spinProducto );
        spinProducto.setAdapter(adapter);

        // ******************************
        spinProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                idproducto = Integer.parseInt(String.valueOf(id));
                Toast.makeText(getApplicationContext(),"Producto " + idproducto, Toast.LENGTH_SHORT);
                // Log.e(TAG_ERROR, String.valueOf(idproducto));
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // *****************************
        // Botón de foto
        btnFoto =  findViewById(R.id.btnFoto);

        // Boton foto puede tomar una foto
        btnFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Context context = getApplicationContext();
                Context contexto = getApplicationContext().getApplicationContext();

                // ********************
                // Create the File where the photo should go
                try {
                    photoFile = createImageFile();
                    // Log.e("Mensaje", "archivo creado" + photoFile.getAbsolutePath());
                } catch (IOException ex) {
                   //  funciones.RegistraError(pName, "Caducidad, SetOnClickListener", ex, Caducidad.this, getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al tomar la foto " +  ex.getMessage(),Toast.LENGTH_LONG).show();
                    // Log.e(ERROR_FOTO, ex.getMessage());
                }
            }
        });

        // *****************************
        // Botón para guardar
        Button cmdGuardar = findViewById(R.id.cmdGuardar);
        // Boton foto puede tomar una foto
        cmdGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText cajalote = findViewById(R.id.txtLote);
                EditText cajapiezas = findViewById(R.id.txtPiezas);
                TextView cajacaducidad = findViewById(R.id.txtFechaCaducidad);

                String slote = cajalote.getText().toString().trim();
                String spiezas = cajapiezas.getText().toString().trim();

                // Validación de Lote y Caducidad
                if (slote=="" || spiezas=="")
                {
                    Toast.makeText( getApplicationContext(),"Todos los campos deben tener datos;",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                try
                {
                    lote = cajalote.getText().toString();
                    piezas = Integer.parseInt(cajapiezas.getText().toString());
                    if(idproducto == 0 || lote.trim().length()==0 || piezas == 0 || fechacaducidad == null || iFoto == 0)
                    {
                        Toast.makeText( getApplicationContext(),"Todos los campos deben tener datos;",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    // *****************************
                    // Verifica la forma en que subirá los datos
                    if (funciones.RevisarConexion(getApplicationContext())) {
                        uploadCaducidad();
                    } else {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String sfechacaducidad = sdf.format(fechacaducidad);
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                        int iResultado = almacenaImagen.inserta_caducidad(idproducto, lote,  sfechacaducidad, piezas, idRuta,  idpromotor, iFoto);
                        //Log.e(TAG_INFO, "Valor de resultado de inserción " + iResultado);
                        if (iResultado>0)
                        {
                            LimpiaCajas();
                            Toast.makeText(getApplicationContext(), "Dato almacenado",Toast.LENGTH_LONG);
                        }
                    }
                } catch (NumberFormatException ex) {
                    // funciones.RegistraError(pName, "Caducidad, cmdGuardar.setOnClickListener", ex, Caducidad.this, getApplicationContext());
                    Toast.makeText( getApplicationContext(),"Todos los campos deben tener datos",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    // funciones.RegistraError(pName, "Caducidad, cmdGuardar.setOnClickListener", ex, Caducidad.this, getApplicationContext());
                    Toast.makeText( getApplicationContext(),"Error " + ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // ********************************************
        // Icono de salir de lista productos
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finish();
                } catch (Exception e) {
                    funciones.RegistraError(pName, "Caducidad, fab.setOnClickListener", e, Caducidad.this, getApplicationContext());
                }
            }
        });

        //****************************
        FloatingActionButton fab0 = findViewById(R.id.fab0);
        fab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almacenaImagen.muestradatosAlmacenados();
            }
        });

        //****************************
        // Muestra el calendario
        Button cmdCaducidad = findViewById(R.id.cmdCaducidad);
        cmdCaducidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    // ****************************
    // Funcion para limpiar cajas
    private void LimpiaCajas()
    {
        ImageView imageView = findViewById(R.id.imagenFoto);
        EditText cajalote = findViewById(R.id.txtLote);
        EditText cajapiezas = findViewById(R.id.txtPiezas);
        TextView cajacaducidad = findViewById(R.id.txtFechaCaducidad);
        Spinner spinProducto = (Spinner) findViewById( R.id.spinProducto );
        imageView.setImageDrawable(null);
        spinProducto.setSelection(0,true);
        cajalote.setText("");
        cajapiezas.setText("");
        cajacaducidad.setText("");
        fechacaducidad = null;
    }

    // ****************************
    // Funcion para subir los datos de caducidad
    private void uploadCaducidad(){
        class UploadCaducidad extends AsyncTask<Bitmap,Void,String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Caducidad.this);
                pDialog.setMessage("Enviando datos ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Funciones funciones = new Funciones();
                Bitmap bitmap = params[0];
                bitmap = funciones.Compacta(bitmap);
                String uploadImage = almacenaImagen.getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf;

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp =  versionName + ":" + versionCode;

                sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sfechacaducidad = sdf.format(fechacaducidad);

                data.put(UPLOAD_idproducto, String.valueOf(idproducto));
                data.put(UPLOAD_lote, String.valueOf(lote));
                data.put(UPLOAD_caducidad, sfechacaducidad);
                data.put(UPLOAD_piezas, String.valueOf(piezas));

                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, pName);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));

                data.put(UPLOAD_IMAGEN, uploadImage);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_CADUCIDAD,data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();

                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int i = almacenaImagen.BorraFotoEnviada(iFoto);
                // Log.e(TAG_ERROR, "Se Borro la foto almacenada " + iFoto);
                // Log.e(TAG_ERROR, "Respuesta  " + s);
                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                if (s == TAG_CARGA_FOTO_EXITOSA) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);

                }
                else{
                    Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                }
                LimpiaCajas();
            }

        }

        try {
            UploadCaducidad ui = new UploadCaducidad();
            Bitmap bm=((BitmapDrawable)imagenFoto.getDrawable()).getBitmap();
            ui.execute(bm);
        }
        catch( java.lang.NullPointerException e)
        {
            // funciones.RegistraError(pName, "Caducidad, uploadCaducidad ", e, Caducidad.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al colocar una foto de caducidad", Toast.LENGTH_LONG).show();
        }
    }

    // ****************************
    // Toma la fotografía
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

    // ****************************
    // Creación de archivo
    private File createImageFile() throws IOException {
        try {
            // Create an image file name
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
            // funciones.RegistraError(pName, "Caducidad, createImageFile ", e, Caducidad.this, getApplicationContext());
            // e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imagenFoto = findViewById(R.id.imagenFoto);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                funciones.grabImage(this, photoURI, imagenFoto);

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
                iFoto = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, pName, idRuta, bitmap);
                //Log.e(TAG_ERROR, "Se Guardo la foto almacenada " + iFoto);
                Toast.makeText(getApplicationContext(), "Foto Guardada", Toast.LENGTH_LONG).show();

                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iCuenta = almacenaImagen.ObtenRegistros(11);
                if(iFoto==0)
                {
                    Toast.makeText(getApplicationContext(), "No se permiten Chekin/out duplicados ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Toast.makeText(getApplicationContext(), "Imágenes almacenadas " + String.valueOf(iCuenta),Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch( java.lang.NullPointerException e)
        {
            // funciones.RegistraError(pName, "Caducidad, onActivityResult ", e, Caducidad.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al cargar una foto de caducidad", Toast.LENGTH_LONG).show();
        }
    }

}