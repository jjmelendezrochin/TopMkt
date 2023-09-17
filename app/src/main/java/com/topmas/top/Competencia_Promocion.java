package com.topmas.top;

import static com.topmas.top.Competencia.REQUEST_IMAGE_CAPTURE;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_ACTIVIDADBTL;
import static com.topmas.top.Constants.TAG_CANJES;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDEMOSTRADOR;
import static com.topmas.top.Constants.TAG_IDEMPAQUE;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_IEMPLAYE;
import static com.topmas.top.Constants.TAG_IEXHIBIDOR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRESENTACION;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_producto;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_IMAGEN1;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Competencia_Promocion extends AppCompatActivity {

    // TODO /Competencia_Promocion/upload_competencia_promocion.php
    // TODO /Competencia_Promocion/upload_competencia_promocion_complemento.php
    public static final String UPLOAD_COMPETENCIA_PROMOCION = TAG_SERVIDOR + "/PhotoUpload/upload_competencia_promocion.php";
    public static final String UPLOAD_COMPETENCIA_PROMOCION_COMPLEMENTO = TAG_SERVIDOR + "/PhotoUpload/upload_competencia_promocion_complemento.php";
    public static final String UPLOAD_CON_SIN_PARTICIPACION = "con_sin_participacion";
    public static final String UPLOAD_POR_PARTICIPACION = "por_participacion";
    public static final String UPLOAD_NO_FRENTES = "no_frentes";
    public static final String UPLOAD_POR_DESCUENTO = "por_descuento";
    public static final String UPLOAD_COMENTARIOS = "comentarios";
    public static final String UPLOAD_IDPRODUCTO = "idproducto";
    public static final String UPLOAD_PRECIO = "precio";
    public static final String TAG_INFO = "INFORMACION";

    Button btnFoto1 = null;
    Button btnFoto2 = null;
    File photoFile1 = null;
    File photoFile2 = null;
    Uri photoURI1 = null;
    Uri photoURI2 = null;
    String producto = null;
    int idproducto = 0;
    int iFoto1=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    int iFoto2=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    String sRutaFoto;
    ImageView imagenFoto1;
    ImageView imagenFoto2;
    int iconPromo;
    int idRuta = 0;
    String pName = "";
    int idoperacion = 0;
    double pLatitud = 0;
    double pLongitud = 0;
    int idpromotor = 0;

    int por_participa = 0 ;
    int no_frentes = 0;
    float precio =  0;
    String comentario = null;

    ProgressDialog pDialog;

    Usuario usr = new Usuario();
    Funciones funciones = new Funciones();
    AlmacenaImagen almacenaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencia_promocion);

        // Obteniendo parametros
        Intent i = getIntent();
        idRuta =  i.getIntExtra(TAG_IDRUTA,0);
        idoperacion =  7;       // PRODUCTO EXHIBIDO , 8 COMPETENCIA (Se usan ambos en ete proceso pero solo se pasa 1)
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        idpromotor = usr.getid();
        pLatitud = usr.getLatitud();
        pLongitud = usr.getLongitud();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pName = preferences.getString(TAG_USUARIO, pName);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            funciones.RegistraError(pName, "Competencia setDefaultUncaughtExceptionHandler", (Exception) throwable, Competencia_Promocion.this, getApplicationContext());
        });

        //****************************
        FloatingActionButton fab1 = findViewById(R.id.fab1);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // *****************************
        // Botón de foto
        btnFoto1 = findViewById(R.id.btnfoto1);
        btnFoto2 = findViewById(R.id.btnfoto2);

        // *****************************
        // Boton foto puede tomar una foto
        btnFoto1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Context contexto = getApplicationContext().getApplicationContext();

                // ********************
                // Create the File where the photo should go
                try {
                    photoFile1 = createImageFile();
                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al crear archivo de foto " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                // ********************
                // Tomar foto
                try {
                    if (photoFile1 != null) {
                        photoURI1 = FileProvider.getUriForFile(contexto,
                                "com.topmas.android.fileprovider",
                                photoFile1);
                        dispatchTakePictureIntent(photoURI1);

                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al tomar la foto " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // *****************************
        // Boton foto puede tomar una foto
        btnFoto2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Context contexto = getApplicationContext().getApplicationContext();

                // ********************
                // Create the File where the photo should go
                try {
                    photoFile2 = createImageFile();
                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al crear archivo de foto " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                // ********************
                // Tomar foto
                try {
                    if (photoFile2 != null) {
                        photoURI2 = FileProvider.getUriForFile(contexto,
                                "com.topmas.android.fileprovider",
                                photoFile2);
                        dispatchTakePictureIntent(photoURI2);
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ERROR_FOTO + " Error al tomar la foto " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
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
        // Botón para guardar
        Button cmdGuardar = findViewById(R.id.cmdGuardar);
        // Boton foto puede tomar una foto
        cmdGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText cajapor_participa = findViewById(R.id.txtpor_participa);
                EditText cajano_frentes = findViewById(R.id.txtno_frentes);
                EditText cajapor_descuento = findViewById(R.id.txtpor_descuento);
                EditText cajacomentario = findViewById(R.id.txtcomentario);
                CheckBox chkconPromo = findViewById(R.id.chkconPromo);
                iconPromo = (chkconPromo.isChecked() ? 1 : 0);

                try {
                    String imagen1 = (imagenFoto1.getDrawable() == null ? "imagen1 nula" : "imagen1 no nula");
                    String imagen2 = (imagenFoto2.getDrawable() == null ? "imagen2 nula" : "imagen2 no nula");

                    int iparticipa = cajapor_participa.getText().toString().length();
                    int ifrentes = cajano_frentes.getText().toString().length();
                    int idescuento = cajapor_descuento.getText().toString().length();
                    int icomentarios = cajacomentario.getText().toString().length();

                    Log.e(TAG_ERROR, String.valueOf(imagen1));
                    Log.e(TAG_ERROR, String.valueOf(imagen2));
                    Log.e(TAG_ERROR, "Participa " + String.valueOf(iparticipa));
                    Log.e(TAG_ERROR, "Frentes " +String.valueOf(ifrentes));
                    Log.e(TAG_ERROR, "Descuento " +String.valueOf(idescuento));
                    Log.e(TAG_ERROR, "Comentarios " +String.valueOf(icomentarios));
                    Log.e(TAG_ERROR, "Producto " +String.valueOf(idproducto));

                    if (    imagen1.equals("imagen1 nula") ||
                            imagen2.equals("imagen2 nula") ||
                            iparticipa == 0 || ifrentes == 0 || idescuento == 0 || icomentarios == 0 || idproducto == 0
                    ) {
                        Toast.makeText(getApplicationContext(), " Todas las imágenes y campos deben tener valores ", Toast.LENGTH_LONG).show();
                        return;
                    }

                    por_participa = Integer.parseInt(cajapor_participa.getText().toString());
                    no_frentes = Integer.parseInt(cajano_frentes.getText().toString());
                    precio = Float.parseFloat(cajapor_descuento.getText().toString());
                    comentario = cajacomentario.getText().toString();

                    // *****************************
                    // Verifica la forma en que subirá los datos
                    if (funciones.RevisarConexion(getApplicationContext())) {
                        UploadImagenesCompetenciaPromocion();
                    } else {
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                        int iResultado = almacenaImagen.inserta_competencia_promocion(
                                idRuta, idpromotor,
                                iFoto1,iFoto2,
                                por_participa, no_frentes,
                                iconPromo,0, comentario,
                                idproducto, precio);
                        Log.e(TAG_INFO, "* Valor de resultado de inserción  de competencia " + iResultado);
                        if (iResultado>0)
                        {
                            Toast.makeText(getApplicationContext(), "Dato almacenado en el teléfono",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), " Todas las imágenes y campos deben tener valores ", Toast.LENGTH_LONG).show();
                    Log.e(TAG_INFO,  e.toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), " Todas las imágenes y campos deben tener valores ", Toast.LENGTH_LONG).show();
                    Log.e(TAG_INFO,  e.toString());
                }
            }
        });
    }

    // ****************************
    // Creación de archivo
    private File createImageFile() throws IOException {
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
    }

    // ****************************
    // Toma la fotografía
    private void dispatchTakePictureIntent(Uri photoURI) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Continue only if the File was successfully created
            if (photoFile1 != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                String sruta = photoURI.getPath();
                File file = new File(sruta);
                return;
            }
            if (photoFile2 != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                String sruta = photoURI.getPath();
                File file = new File(sruta);
                return;
            }
        }
    }

    // ****************************
    // Coloca las imágenes
    // en los controles
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagenFoto1 = findViewById(R.id.imagenFoto1);
        imagenFoto2 = findViewById(R.id.imagenFoto2);

        // **********************
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime()).trim();
        // **********************
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());

        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                if (imagenFoto1.getDrawable() == null) {
                    funciones.grabImage(this, photoURI1, imagenFoto1);

                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto1.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    // ***********************
                    // Guardar la imagen despues de tomarla
                    idoperacion =  7;       // PRODUCTO EXHIBIDO
                    iFoto1 = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), 7, pName, idRuta, bitmap);
                    Toast.makeText(getApplicationContext(), "Foto Guardada " + iFoto1, Toast.LENGTH_LONG).show();

                    return;
                }
                if (imagenFoto2.getDrawable() == null) {
                    funciones.grabImage(this, photoURI2, imagenFoto2);

                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto2.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    // ***********************
                    // Guardar la imagen despues de tomarla
                    idoperacion =  8;       // COMPETENCIA 8
                    iFoto2 = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, pName, idRuta, bitmap);
                    Toast.makeText(getApplicationContext(), "Foto Guardada " + iFoto2, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        } catch (NullPointerException e) {
            // unciones.RegistraError(pName, "Competencia, onActivityResult", e, Competencia.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al colocar una foto de competencia", Toast.LENGTH_LONG).show();
        }
    }

    //***********************
    // Upload image function
    public void UploadImagenesCompetenciaPromocion(){
        class UploadImagenesCompetenciaPromocion extends AsyncTask<Bitmap,Void,String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Competencia_Promocion.this);
                pDialog.setMessage("Enviando datos ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Funciones funciones = new Funciones();
                Bitmap bitmap1 = params[0];
                Bitmap bitmap2 = params[1];
                bitmap1 = funciones.Compacta(bitmap1);
                bitmap1 = funciones.Compacta(bitmap1);
                bitmap1 = funciones.Compacta(bitmap1);
                bitmap2 = funciones.Compacta(bitmap2);
                bitmap2 = funciones.Compacta(bitmap2);
                bitmap2 = funciones.Compacta(bitmap2);
                String uploadImage1 = almacenaImagen.getStringImage(bitmap1);
                String uploadImage2 = almacenaImagen.getStringImage(bitmap2);

                HashMap<String,String> data = new HashMap<>();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);

                data.put(UPLOAD_CON_SIN_PARTICIPACION, String.valueOf(iconPromo));
                data.put(UPLOAD_POR_PARTICIPACION, String.valueOf(por_participa));
                data.put(UPLOAD_NO_FRENTES, String.valueOf(no_frentes));
                data.put(UPLOAD_POR_DESCUENTO, String.valueOf("0"));
                data.put(UPLOAD_PRECIO, String.valueOf(precio));
                data.put(UPLOAD_COMENTARIOS, String.valueOf(comentario));
                data.put(UPLOAD_IDPRODUCTO, String.valueOf(idproducto));

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_COMPETENCIA_PROMOCION,data);
                // return rh.sendPostRequest(UPLOAD_COMPETENCIA_PROMOCION_COMPLEMENTO,data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();

                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int i = almacenaImagen.BorraFotoEnviada(iFoto1);
                int j = almacenaImagen.BorraFotoEnviada(iFoto2);
                // Log.e(TAG_ERROR, "Se Borro la foto almacenada " + iFoto);
                // Log.e(TAG_ERROR, "Respuesta  " + s);
                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                if (s == TAG_CARGA_FOTO_EXITOSA) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto1.setImageResource(android.R.color.transparent);
                    imagenFoto2.setImageResource(android.R.color.transparent);
                }
                else{
                    Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    imagenFoto1.setImageResource(android.R.color.transparent);
                    imagenFoto2.setImageResource(android.R.color.transparent);
                }
                finish();
                // **************************************
            }
        }

        try {
            UploadImagenesCompetenciaPromocion ui = new UploadImagenesCompetenciaPromocion();
            Bitmap bm1=((BitmapDrawable)imagenFoto1.getDrawable()).getBitmap();
            Bitmap bm2=((BitmapDrawable)imagenFoto2.getDrawable()).getBitmap();
            ui.execute(bm1, bm2);
        }
        catch( java.lang.NullPointerException e)
        {
            // funciones.RegistraError(pName, "Competencia, uploadImageCompetencia", e, Competencia.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al cargar una foto de competencia", Toast.LENGTH_LONG).show();
        }
    }
}