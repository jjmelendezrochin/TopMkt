package com.topmas.top;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_ACTIVIDADBTL;
import static com.topmas.top.Constants.TAG_CANJES;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_DISTANCIA;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_IDEMOSTRADOR;
import static com.topmas.top.Constants.TAG_IDEMPAQUE;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_IEMPLAYE;
import static com.topmas.top.Constants.TAG_IEXHIBIDOR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRESENTACION;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_empaque;
import static com.topmas.top.Constants.TAG_producto;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;
import static com.topmas.top.Foto.rotateImage;

public class Competencia extends AppCompatActivity {

    // TODO /PhotoUpload/upload_competencia.php
    public static final String UPLOAD_COMPETENCIA = TAG_SERVIDOR + "/PhotoUpload/upload_competencia.php";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String sRutaFoto;
    String pName = "";
    String producto;
    String presentacion;
    String actividadbtl;
    String canjes;

    Button btnFoto= null;
    Button imgizq;
    Button imgder;
    File photoFile = null;
    Uri photoURI = null;
    ImageView imagenFoto;
    ProgressDialog pDialog;

    double pLatitud = 0;
    double pLongitud = 0;
    double precio = 0;

    int idEmpaque = 0;
    int idpromotor = 0;
    int idRuta = 0;
    int idoperacion = 0;
    int iFoto=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    int idemostrador = 0;
    int iexhibidor = 0;
    int iemplaye = 0;

    Usuario usr = new Usuario();
    Funciones funciones = new Funciones();
    AlmacenaImagen almacenaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencia);

        // Obteniendo parametros
        Intent i = getIntent();
        idRuta =  i.getIntExtra(TAG_IDRUTA,0);
        idoperacion =  5;       // OPERACIÓN FOTO COMPETENCIA
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        idpromotor = usr.getid();
        pLatitud = usr.getLatitud();
        pLongitud = usr.getLongitud();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pName = preferences.getString(TAG_USUARIO, pName);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "Competencia setDefaultUncaughtExceptionHandler", (Exception) throwable, Competencia.this, getApplicationContext());
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
        btnFoto =  findViewById(R.id.btnFoto);
        imgizq =  findViewById(R.id.btnizquierda);
        imgder =  findViewById(R.id.btnderecha);

        // Oculta botones rotación
        imgizq.setVisibility(View.INVISIBLE);
        imgder.setVisibility(View.INVISIBLE);

        // ********************************
        // Rotacion izquierda
        imgizq.setOnClickListener(view -> {
            imagenFoto = findViewById(R.id.imagenFoto);
            if (imagenFoto.getDrawable() != null) {

                pDialog = new ProgressDialog(Competencia.this);
                pDialog.setMessage("Rotando ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                BitmapDrawable drawable = (BitmapDrawable) imagenFoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int angle = -90;
                //imagenFoto.setRotation(angle);
                bitmap = rotateImage(bitmap ,angle);
                imagenFoto.setImageBitmap(bitmap);

                pDialog.dismiss();
            }
        });

        // ********************************
        // Rotacion derecha
        imgder.setOnClickListener(view -> {
            imagenFoto = findViewById(R.id.imagenFoto);
            if (imagenFoto.getDrawable() != null) {

                pDialog = new ProgressDialog(Competencia.this);
                pDialog.setMessage("Rotando ...");
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
                    // funciones.RegistraError(pName, "Competencia, btnFoto.setOnClickListener 1", ex, Competencia.this, getApplicationContext());
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
                    // funciones.RegistraError(pName, "Competencia, btnFoto.setOnClickListener 2", ex, Competencia.this, getApplicationContext());
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
                EditText cajaproducto = findViewById(R.id.txtProducto);
                EditText cajaprecio = findViewById(R.id.txtPrecioCompetencia);
                EditText cajapresentacion = findViewById(R.id.txtPresentacion);
                EditText cajactividadbtl = findViewById(R.id.txtpor_participa);
                EditText cajacanjes = findViewById(R.id.txt_no_frentes);

                CheckBox chkDemostrador = findViewById(R.id.chkDemostrador);
                CheckBox chkExhibidor = findViewById(R.id.chkExhibidor);
                CheckBox chkEmplayes = findViewById(R.id.chkEmplayes);

                idemostrador = (chkDemostrador.isChecked()?1:0);
                iexhibidor = (chkExhibidor.isChecked()?1:0);
                iemplaye = (chkEmplayes.isChecked()?1:0);

                try {
                    producto = cajaproducto.getText().toString();
                    precio = Double.parseDouble(cajaprecio.getText().toString());
                    presentacion = cajapresentacion.getText().toString();
                    actividadbtl = cajactividadbtl.getText().toString();
                    canjes = cajacanjes.getText().toString();

                    // *****************************
                    // Verifica la forma en que subirá los datos
                    if (funciones.RevisarConexion(getApplicationContext())) {
                        uploadImageCompetencia();
                    } else {
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                        int iResultado = almacenaImagen.inserta_competencia(producto,precio,presentacion,idEmpaque, idRuta, idpromotor, idemostrador, iexhibidor, iemplaye, actividadbtl,canjes,iFoto);
                        Log.e(TAG_INFO, "* Valor de resultado de inserción  de competencia" + iResultado);
                        if (iResultado>0)
                        {
                            Toast.makeText(getApplicationContext(), "Dato almacenado",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                } catch (NumberFormatException ex) {
                    // funciones.RegistraError(pName, "Competencia, cmdGuardar.setOnClickListener", ex, Competencia.this, getApplicationContext());
                    Toast.makeText( getApplicationContext(),"Todos los campos deben tener datos",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // *****************************
        // llenando los datos de la lista de observaciones
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        Cursor c = almacenaImagen.CursorEmpaque();
        String[] from = new String[]{TAG_empaque};
        int[] to = new int[]{android.R.id.text1};
        // This is your simple cursor adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner spinEmpaque = (Spinner) findViewById( R.id.spinEmpaque );
        spinEmpaque.setAdapter(adapter);

        // ******************************
        spinEmpaque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                idEmpaque = Integer.parseInt(String.valueOf(id));
                Toast.makeText(getApplicationContext(),"Empaque " + idEmpaque, Toast.LENGTH_SHORT);
                // Log.e(TAG_ERROR, String.valueOf(idEmpaque));
            }
            public void onNothingSelected(AdapterView<?> parent) {}
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
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagenFoto = findViewById(R.id.imagenFoto);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                funciones.grabImage(this, photoURI, imagenFoto);

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

                Toast.makeText(getApplicationContext(), "Foto Guardada", Toast.LENGTH_LONG).show();

                imgizq.setVisibility(View.VISIBLE);
                imgder.setVisibility(View.VISIBLE);

                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iCuenta = almacenaImagen.ObtenRegistros(11);
                if (iFoto == 0) {
                    Toast.makeText(getApplicationContext(), "No se permiten Chekin/out duplicados ", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(getApplicationContext(), "Imágenes almacenadas " + String.valueOf(iCuenta),Toast.LENGTH_SHORT).show();
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
    public void uploadImageCompetencia(){
        class UploadImageCompetencia extends AsyncTask<Bitmap,Void,String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Competencia.this);
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
                bitmap = funciones.Compacta(bitmap);
                bitmap = funciones.Compacta(bitmap);

                String uploadImage = almacenaImagen.getStringImage(bitmap);

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
                data.put(UPLOAD_IMAGEN, uploadImage);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                data.put(TAG_producto, String.valueOf(producto));
                data.put(TAG_PRECIO, String.valueOf(precio));
                data.put(TAG_PRESENTACION, String.valueOf(presentacion));
                data.put(TAG_IDEMPAQUE, String.valueOf(idEmpaque));

                data.put(TAG_IDEMOSTRADOR, String.valueOf(idemostrador));
                data.put(TAG_IEXHIBIDOR, String.valueOf(iexhibidor));
                data.put(TAG_IEMPLAYE, String.valueOf(iemplaye));
                data.put(TAG_ACTIVIDADBTL, String.valueOf(actividadbtl));
                data.put(TAG_CANJES, String.valueOf(canjes));

                return rh.sendPostRequest(UPLOAD_COMPETENCIA,data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();

                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int i = almacenaImagen.BorraFotoEnviada(iFoto);

                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                if (s.equals(TAG_CARGA_FOTO_EXITOSA)) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    imgizq.setVisibility(View.INVISIBLE);
                    imgder.setVisibility(View.INVISIBLE);
                }
                else if (s.equals(TAG_CARGA_FOTO_DISTANCIA)) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    imgizq.setVisibility(View.VISIBLE);
                    imgder.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    imagenFoto.setImageResource(android.R.color.transparent);
                    imgizq.setVisibility(View.VISIBLE);
                    imgder.setVisibility(View.VISIBLE);
                }

                finish();
                // **************************************
            }

        }

        try {
            UploadImageCompetencia ui = new UploadImageCompetencia();
            Bitmap bm=((BitmapDrawable)imagenFoto.getDrawable()).getBitmap();
            ui.execute(bm);
        }
        catch( java.lang.NullPointerException e)
        {
            // funciones.RegistraError(pName, "Competencia, uploadImageCompetencia", e, Competencia.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al cargar una foto de competencia", Toast.LENGTH_LONG).show();
        }
    }

}