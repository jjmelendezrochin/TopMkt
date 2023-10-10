package com.topmas.top;

import static com.topmas.top.Competencia.REQUEST_IMAGE_CAPTURE;
import static com.topmas.top.Constants.CONST_ACCESOLOCAL;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Foto.rotateImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Canjes extends AppCompatActivity {

    Button btnFoto1 = null;
    Button imgizq1;
    Button imgder1;
    Button btnFoto2 = null;
    Button imgizq2;
    Button imgder2;
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
    int iconPromo;
    int idRuta = 0;
    String idUsuario = "";
    int idoperacion = 0;
    double pLatitud = 0;
    double pLongitud = 0;
    int idpromotor = 0;

    ListView lista;

    int por_participa = 0 ;
    int no_frentes = 0;
    float precio =  0;
    String comentario = null;

    ProgressDialog pDialog;

    Usuario usr = new Usuario();
    Funciones funciones = new Funciones();
    AlmacenaImagen almacenaImagen;

    int iCuentaProductos = 50;
    int[] idproducto = new int[iCuentaProductos];
    int[] idruta = new int[iCuentaProductos];
    String[] descripcionproducto = new String[iCuentaProductos];
    String[] categoriaproducto = new String[iCuentaProductos];
    String[] upc = new String[iCuentaProductos];

    int pidPromotor = 0;
    int pidRuta = 0;
    String pidEmpresa = "0";
    Double platitud = 0.0;
    Double plongitud = 0.0;
    String pdireccion = "";
    String ptienda = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canjes);

        Intent i = getIntent();
        pidRuta = i.getIntExtra(TAG_IDRUTA, pidRuta);
        ptienda = i.getStringExtra(TAG_TIENDA);
        platitud = i.getDoubleExtra(TAG_LATITUD, 0.0);
        plongitud = i.getDoubleExtra(TAG_LONGITUD, 0.0);
        pdireccion = i.getStringExtra(TAG_DIRECCION);
        pidEmpresa = usr.getidempresa();
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());

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
                    iFoto1 = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), 7, idUsuario, idRuta, bitmap);
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
                    idoperacion =  8;       // COMPETENCIA 8
                    iFoto2 = almacenaImagen.guardaFotos(idpromotor, pLatitud, pLongitud, strDate.trim(), idoperacion, idUsuario, idRuta, bitmap);
                    Toast.makeText(getApplicationContext(), "Foto Guardada " + iFoto2, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        } catch (NullPointerException e) {
            // unciones.RegistraError(idUsuario, "Competencia, onActivityResult", e, Competencia.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
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
}