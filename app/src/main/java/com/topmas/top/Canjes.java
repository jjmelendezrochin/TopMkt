package com.topmas.top;

import static com.topmas.top.Competencia.REQUEST_IMAGE_CAPTURE;
import static com.topmas.top.Constants.CONST_ACCESOLOCAL;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Foto.UPLOAD_ARREGLOPRODUCTO;
import static com.topmas.top.Foto.UPLOAD_COMENTARIOS;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_IDCANJE;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_IMAGEN1;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LLAVE;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;
import static com.topmas.top.Foto.rotateImage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class Canjes extends AppCompatActivity {

    Button btnFoto1 = null;
    Button imgizq1;
    Button imgder1;
    Button btnFoto2 = null;
    Button imgizq2;
    Button imgder2;
    Button cmdGuardar = null;
    File photoFile1 = null;
    File photoFile2 = null;
    Uri photoURI1 = null;
    Uri photoURI2 = null;
    String producto = null;
    int iFoto1=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    int iFoto2=0;            // Es el id de la tabla almacenfoto de la foto recien subida
    String sRutaFoto;
    ImageView imagenFoto1;
    ImageView imagenFoto2;
    EditText editTextComentario;
    String idUsuario = "";
    int idoperacion = 0;
    double pLatitud = 0;
    double pLongitud = 0;
    String sArregloProductos="";
    String sComentarios="";
    String llave = "";
    ListView lista;
    ProgressDialog pDialog;
    Usuario usr = new Usuario();
    Funciones funciones = new Funciones();
    AlmacenaImagen almacenaImagen;
    int iCuentaProductos = 50;
    int[] idproducto = new int[iCuentaProductos];
    int[] idArregloruta = new int[iCuentaProductos];
    String[] descripcionproducto = new String[iCuentaProductos];
    String[] categoriaproducto = new String[iCuentaProductos];
    String[] upc = new String[iCuentaProductos];

    int pidPromotor = 0;
    int pidRuta = 0;
    String pidEmpresa = "0";
    String pdireccion = "";
    String ptienda = "";
    public static final String UPLOAD_CANJES = TAG_SERVIDOR + "/PhotoUpload/upload_canjes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canjes);

        Intent i = getIntent();
        pidRuta = i.getIntExtra(TAG_IDRUTA, pidRuta);
        ptienda = i.getStringExtra(TAG_TIENDA);
        pdireccion = i.getStringExtra(TAG_DIRECCION);
        pidEmpresa = usr.getidempresa();
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        pidPromotor = usr.getid();
        pLatitud = usr.getLatitud();
        pLongitud = usr.getLongitud();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        idUsuario = preferences.getString(TAG_USUARIO, idUsuario);

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
        imgizq1 =  findViewById(R.id.btnizquierda1);
        imgder1 =  findViewById(R.id.btnderecha1);
        imgizq2 =  findViewById(R.id.btnizquierda2);
        imgder2 =  findViewById(R.id.btnderecha2);
        cmdGuardar = findViewById(R.id.cmdGuardar10);
        editTextComentario = findViewById(R.id.txtcomentario);
        // Lista
        lista = findViewById(R.id.listaproductoscanjes);

        //****************************
        // Evento click al adaptador
        lista.setOnItemClickListener(new OnItemClickListenerAdaptadorProductosCanjes());

        //****************************
        // Oculta botones rotación
        imgizq1.setVisibility(View.INVISIBLE);
        imgder1.setVisibility(View.INVISIBLE);
        imgizq2.setVisibility(View.INVISIBLE);
        imgder2.setVisibility(View.INVISIBLE);
        // ********************************
        // Rotacion izquierda
        imgizq1.setOnClickListener(view -> {
            imagenFoto1 = findViewById(R.id.imagenFoto1);
            if (imagenFoto1.getDrawable() != null) {

                pDialog = new ProgressDialog(Canjes.this);
                pDialog.setMessage("Rotando ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                BitmapDrawable drawable = (BitmapDrawable) imagenFoto1.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int angle = -90;
                //imagenFoto.setRotation(angle);
                bitmap = rotateImage(bitmap ,angle);
                imagenFoto1.setImageBitmap(bitmap);

                pDialog.dismiss();
            }
        });

        imgizq2.setOnClickListener(view -> {
            imagenFoto2 = findViewById(R.id.imagenFoto2);
            if (imagenFoto2.getDrawable() != null) {

                pDialog = new ProgressDialog(Canjes.this);
                pDialog.setMessage("Rotando ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                BitmapDrawable drawable = (BitmapDrawable) imagenFoto2.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int angle = -90;
                bitmap = rotateImage(bitmap ,angle);
                imagenFoto2.setImageBitmap(bitmap);

                pDialog.dismiss();
            }
        });

        // ********************************
        // Rotacion derecha
        imgder1.setOnClickListener(view -> {
            imagenFoto1 = findViewById(R.id.imagenFoto1);
            if (imagenFoto1.getDrawable() != null) {

                pDialog = new ProgressDialog(Canjes.this);
                pDialog.setMessage("Rotando ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                BitmapDrawable drawable = (BitmapDrawable) imagenFoto1.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int angle = 90;
                //imagenFoto.setRotation(angle);
                bitmap = rotateImage(bitmap ,angle);
                imagenFoto1.setImageBitmap(bitmap);

                pDialog.dismiss();
            }
        });

        imgder2.setOnClickListener(view -> {
            imagenFoto2 = findViewById(R.id.imagenFoto2);
            if (imagenFoto2.getDrawable() != null) {

                pDialog = new ProgressDialog(Canjes.this);
                pDialog.setMessage("Rotando ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                BitmapDrawable drawable = (BitmapDrawable) imagenFoto2.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int angle = 90;
                bitmap = rotateImage(bitmap ,angle);
                imagenFoto2.setImageBitmap(bitmap);

                pDialog.dismiss();
            }
        });

        MuestraProductosTelefono(pidRuta);
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
        // Botón para guardar
        cmdGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String imagen1 = (imagenFoto1.getDrawable() == null ? "imagen1 nula" : "imagen1 no nula");
                    String imagen2 = (imagenFoto2.getDrawable() == null ? "imagen2 nula" : "imagen2 no nula");
                    sComentarios = editTextComentario.getText().toString();
                    int iCta = almacenaImagen.consulta_canjes_tienda_promotor(pidPromotor, pidRuta);

                    if (    imagen1.equals("imagen1 nula") ||
                            imagen2.equals("imagen2 nula") ||
                            pidPromotor == 0 ||
                            pidRuta == 0 ||
                            iCta < 4
                    ) {
                        Toast.makeText(getApplicationContext(), " Favor de capturar imágenes y cuando menos 4 en cantidad de productos",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    // *****************
                    // Obteniendo los valores de aquellos productos con cantidad de este promotor y ruta
                    sArregloProductos = almacenaImagen.consulta_cadena_canjes_tienda_promotor(pidPromotor, pidRuta);
                    Log.e(TAG_ERROR, sArregloProductos + "1");
                    // *****************************
                    // Verifica la forma en que subirá los datos
                    if (funciones.RevisarConexion(getApplicationContext())) {
                        UploadImagenesCanjes();
                    } 
                    else{
                        // Implementar el guardado de datodo no conectado.
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                        llave = almacenaImagen.inserta_canjes(pidRuta, pidPromotor,iFoto1,iFoto2, sComentarios);
                        Log.e(TAG_INFO, "* Valor de resultado de inserción  de llave " + llave);
                        if (llave.length()>0)
                        {
                            Toast.makeText(getApplicationContext(), "Dato almacenado en el teléfono",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }


                    // Toast.makeText(getApplicationContext(), " Guardando valores", Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    /**/Toast.makeText(getApplicationContext(), " Todas las imágenes son requeridas", Toast.LENGTH_LONG).show();
                    Log.e(TAG_INFO,  e.toString());
                } catch (NumberFormatException e) {
                   /**/ Toast.makeText(getApplicationContext(), " Todas las imágenes y campos deben tener valores 2", Toast.LENGTH_LONG).show();
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
                    idoperacion =  9;       // TICKET CANJE 9
                    iFoto1 = almacenaImagen.guardaFotos(pidPromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, idUsuario, pidRuta, bitmap);
                    Toast.makeText(getApplicationContext(), "Foto Guardada " + iFoto1, Toast.LENGTH_LONG).show();

                    imgizq1.setVisibility(View.VISIBLE);
                    imgder1.setVisibility(View.VISIBLE);
                    imgizq2.setVisibility(View.VISIBLE);
                    imgder2.setVisibility(View.VISIBLE);

                    return;
                }
                if (imagenFoto2.getDrawable() == null) {
                    funciones.grabImage(this, photoURI2, imagenFoto2);

                    BitmapDrawable drawable = (BitmapDrawable) imagenFoto2.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    // ***********************
                    // Guardar la imagen despues de tomarla
                    idoperacion =  10;       // FOTO EVIDENCIA 10
                    iFoto2 = almacenaImagen.guardaFotos(pidPromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, idUsuario, pidRuta, bitmap);
                    Toast.makeText(getApplicationContext(), "Foto Guardada " + iFoto2, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Error al colocar una foto de competencia", Toast.LENGTH_LONG).show();
        }
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
            idArregloruta[k] = pidRuta;
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
            idruta1[k] = idArregloruta[k];
            categoriaproducto1[k] = categoriaproducto[k];
            descripcionproducto1[k] = descripcionproducto[k] + " [" + upc[k] + "] ";
            upc1[k] = upc[k];

            // Lista los productos
            Log.e(TAG_ERROR, descripcionproducto1[k]);
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

    //***********************
    // Upload image function
    public void UploadImagenesCanjes()
    {
        class UploadImagenesCanjes extends AsyncTask<Bitmap,Void,String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e(TAG_ERROR, sArregloProductos + "1");
                pDialog = new ProgressDialog(Canjes.this);
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

                // Obtención de datos de llave
                almacenaImagen.consulta_total_canjes();
                llave = almacenaImagen.inserta_canjes(pidRuta, pidPromotor,iFoto1,iFoto2, sComentarios);

                HashMap<String,String> data = new HashMap<>();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechahora = sdf.format(c.getTime());

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp =  versionName + ":" + versionCode;

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(pidPromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(pidRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);
                data.put(UPLOAD_LLAVE, llave);
                data.put(UPLOAD_COMENTARIOS,sComentarios);
                data.put(UPLOAD_ARREGLOPRODUCTO,sArregloProductos);

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_CANJES,data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();

                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int i = almacenaImagen.BorraFotoEnviada(iFoto1);
                int j = almacenaImagen.BorraFotoEnviada(iFoto2);

                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada y los de canjes len a tocal
                if (s == TAG_CARGA_FOTO_EXITOSA) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    imagenFoto1.setImageResource(android.R.color.transparent);
                    imagenFoto2.setImageResource(android.R.color.transparent);
                    almacenaImagen.borra_canjes(pidRuta, pidPromotor,llave);
                }
                else{
                    Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    imagenFoto1.setImageResource(android.R.color.transparent);
                    imagenFoto2.setImageResource(android.R.color.transparent);
                    almacenaImagen.borra_canjes(pidRuta, pidPromotor,llave);
                }
                finish();
                // **************************************
            }
        }

        try {
            UploadImagenesCanjes ui = new UploadImagenesCanjes();
            Bitmap bm1=((BitmapDrawable)imagenFoto1.getDrawable()).getBitmap();
            Bitmap bm2=((BitmapDrawable)imagenFoto2.getDrawable()).getBitmap();
            Log.e(TAG_ERROR, sArregloProductos);
            ui.execute(bm1, bm2);
        }
        catch( java.lang.NullPointerException e)
        {
            Toast.makeText(getApplicationContext(), "Error al cargar una foto de canjes", Toast.LENGTH_LONG).show();
        }
    }
}