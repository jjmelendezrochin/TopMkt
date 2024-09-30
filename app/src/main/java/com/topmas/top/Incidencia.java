package com.topmas.top;

import static android.widget.Toast.LENGTH_LONG;
import static com.topmas.top.Constants.DEV_ENVIROMENT;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_DESCRIPCIONINCIDENCIA;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_TIENDA;
import static com.topmas.top.Constants.TAG_USUARIO;
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

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


// FUENTE chat gpt busqueda de archivos de capturas de pantalla
public class Incidencia extends AppCompatActivity {
    int pidPromotor = 0;
    int pidRuta = 0;
    double pLatitud = 0;
    double pLongitud = 0;
    String pdireccion = "";
    String ptienda = "";
    String pUsuario = "";
    String idUsuario = "";
    String observaciones = "";
    AlmacenaImagen almacenaImagen;
    private final Funciones funciones = new Funciones();
    private final Usuario usr = new Usuario();
    private ImageView imageView;
    Button btnPaste;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    ProgressDialog pDialog;
    public static final String UPLOAD_INCIDENCIA = TAG_SERVIDOR + "/PhotoUpload/upload_incidencia.php";
    public static final String UPLOAD_INCIDENCIA_O = TAG_SERVIDOR + "/PhotoUpload/upload_incidencia_o.php";
    public static final String UPLOAD_IDINCIDENCIA = "idincidencia";
    public static final String UPLOAD_OBSERVACIONES = "observaciones";
    int iFoto = 0;            // Es el id de la tabla almacenfoto de la foto recien subida
    int idoperacion = 11;
    int idincidencia = 0;
    String fechahora = "";
    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia);
        almacenaImagen = new AlmacenaImagen(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        imageView = findViewById(R.id.imageClipboard);
        btnPaste = findViewById(R.id.btnPaste);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PICK_IMAGE);
            }
        }

        Intent i = getIntent();
        pidRuta = i.getIntExtra(TAG_IDRUTA, 0);
        pidPromotor = i.getIntExtra(TAG_IDPROMOTOR, 0);
        pLatitud = i.getDoubleExtra(TAG_LATITUD, 0.0);
        pLongitud = i.getDoubleExtra(TAG_LONGITUD, 0.0);
        ptienda = i.getStringExtra(TAG_TIENDA);
        pdireccion = i.getStringExtra(TAG_DIRECCION);

        //****************************
        // Agregando losdatos
        //ptienda = ptienda + " "  + usr.getnombre();
        TextView txtTituloTienda = findViewById(R.id.txtTienda);
        txtTituloTienda.setText(ptienda.toUpperCase());

        // **********************
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fechahora = sdf.format(d);

        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        pUsuario = preferencias.getString(TAG_USUARIO, usr.getnombre());
        TextView txtUsuario = findViewById(R.id.txtUsuario);
        txtUsuario.setText(pUsuario);

        // *****************************
        // llenando los datos de la lista de productos
        Cursor c = almacenaImagen.CursorIncidencias();
        String[] from = new String[]{TAG_DESCRIPCIONINCIDENCIA};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinIncidencia = findViewById(R.id.spinIncidencia);
        spinIncidencia.setAdapter(adapter);

        // ******************************
        spinIncidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idincidencia = Integer.parseInt(String.valueOf(id));
                // Toast.makeText(getApplicationContext(),"Incidencia " + idincidencia, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //****************************
        FloatingActionButton fab = findViewById(R.id.fabMenu);
        fab.setOnClickListener(view -> {
            Intent MenuTienda = new Intent(getApplicationContext(), com.topmas.top.MenuTienda.class);
            MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(pidRuta));
            MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
            MenuTienda.putExtra(TAG_TIENDA, ptienda);
            MenuTienda.putExtra(TAG_LATITUD, pLatitud);
            MenuTienda.putExtra(TAG_LONGITUD, pLongitud);
            MenuTienda.putExtra(TAG_DIRECCION, pdireccion);
            startActivity(MenuTienda);
        });

        //****************************
        btnPaste.setOnClickListener(v -> openImagePicker());
        //****************************

        // *****************************
        // Botón para guardar
        Button cmdGuardar = findViewById(R.id.cmdGuardar);
        // Boton foto puede tomar una foto
        cmdGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText txtObservaciones = findViewById(R.id.txtObservaciones);
                observaciones = txtObservaciones.getText().toString().trim();

                //***********************************
                // Validación de Lote y Caducidad
                if (observaciones.isEmpty() ||
                        idincidencia == 1) {
                    Toast.makeText(getApplicationContext(), "Favor de seleccionar tipo incidencia y observaciones",
                            LENGTH_LONG).show();
                    return;
                }
                try {
                    // *****************************
                    // Verifica la forma en que subirá los datos
                    if (funciones.RevisarConexion(getApplicationContext())) {
                        if (pLatitud != 0.00 &&
                                pLongitud != 0.00 &&
                                pidRuta > 0 &&
                                pidPromotor > 0
                        ) {
                            uploadIncidencia();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No se registraron datos de latitud, longitud, ruta o promotor favor de  salir de esta vista e intentar nuevamente",
                                    LENGTH_LONG).show();
                        }
                    } else {

                        // **********************
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        fechahora = sdf.format(d);
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());

                        imageView = findViewById(R.id.imageClipboard);
                        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        bm = funciones.Compacta(bm);
                        String uploadImage = almacenaImagen.getStringImage(bm);

                        // Consulta tabla de incidencias por si no existe la crea
                        int iRegs = almacenaImagen.ObtenRegistros(22);
                        int iResultado = almacenaImagen.inserta_incidencia(idincidencia, iFoto, pidPromotor, pidRuta, fechahora, observaciones);
                        Log.e(TAG_INFO, "Valor de resultado de inserción " + iResultado);
                        Log.e(TAG_INFO, "Nùmero de incidencias locales " + iRegs);
                        if (iResultado > 0) {
                            LimpiaCajas();
                            Toast.makeText(getApplicationContext(), "Dato almacenado", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                } catch (NumberFormatException ex) {
                    // funciones.RegistraError(idUsuario, "Caducidad, cmdGuardar.setOnClickListener", ex, Caducidad.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Todos los campos deben tener datos",
                            LENGTH_LONG).show();
                } catch (Exception ex) {
                    // funciones.RegistraError(idUsuario, "Caducidad, cmdGuardar.setOnClickListener", ex, Caducidad.this, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Error " + ex.getMessage(),
                            LENGTH_LONG).show();
                }
            }
        });

        // **************************************
        // Ambiente desarrollo establecer DEV_ENVIROMENT a true
        if (DEV_ENVIROMENT) {
            spinIncidencia.setSelection(2);
            EditText txtObservaciones = findViewById(R.id.txtObservaciones);
            txtObservaciones.setText("Observaciones para pruebas");
        }

        // *******************
        // Obtiene geoposición
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Use the location object
                            pLatitud = location.getLatitude();
                            pLongitud = location.getLongitude();
                            // Do something with the location data
                        }
                    }
                });
    }

    // ****************************
    // Funcion para limpiar cajas
    private void LimpiaCajas() {
        ImageView imageClipboard = findViewById(R.id.imageClipboard);
        Spinner spinIncidencia = (Spinner) findViewById(R.id.spinIncidencia);
        imageClipboard.setImageDrawable(null);
        spinIncidencia.setSelection(0, true);
        TextView txtObservaciones = findViewById(R.id.txtObservaciones);
        txtObservaciones.setText("");
    }

    // ****************************
    // Abrir seleccion de imagen
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
                imageView = findViewById(R.id.imageClipboard);
                imageView.setVisibility(View.VISIBLE);

                /*
                FusedLocation loc = new FusedLocation();
                loc.getLastLocation();
                platitud = loc.latitud;
                plongitud = loc.longitud;
                 */

                // platitud = usr.getLatitud();
                // plongitud = usr.getLongitud();

                // ***************************************
                // Obtiene el nombre del usuario en y promotor las preferencias
                SharedPreferences preferencias =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String spromotor = preferencias.getString(TAG_IDPROMOTOR, String.valueOf(pidPromotor));
                idUsuario = preferencias.getString(TAG_USUARIO, idUsuario);
                pidPromotor = Integer.parseInt(spromotor != null ? spromotor : "");
                // ***************************************

                // **********************
                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                // ***********************

                // Guardar la imagen despues de tomarla
                iFoto = almacenaImagen.guardaFotos(pidPromotor, pLatitud, pLongitud, fechahora.trim(), idoperacion, idUsuario, pidRuta, bitmap);
                Toast.makeText(getApplicationContext(), "Foto Guardada", LENGTH_LONG).show();

            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Error al cargar una foto de caducidad", LENGTH_LONG).show();
        }
    }

    // ****************************
    // Funcion para subir los datos de caducidad
    private void uploadIncidencia() {
        class UploadIncidencia extends AsyncTask<Bitmap, Void, String> {

            private final RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Incidencia.this);
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

                HashMap<String, String> data = new HashMap<>();
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp = versionName + ":" + versionCode;

                data.put(UPLOAD_IDINCIDENCIA, String.valueOf(idincidencia));
                data.put(UPLOAD_IDRUTA, String.valueOf(pidRuta));
                data.put(UPLOAD_IDPROMOTOR, String.valueOf(pidPromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, almacenaImagen.idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_OBSERVACIONES, observaciones.trim());
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_INCIDENCIA, data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                imageView = findViewById(R.id.imageClipboard);
                // Log.e(TAG_INFO, "Valor de resultado de inserción " + s);
                // **************************************
                if (s.equals(TAG_CARGA_FOTO_EXITOSA)) {
                    // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                    AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                    int i = almacenaImagen.BorraFotoEnviada(iFoto);
                    if (i > 0) Log.e(TAG_INFO, "Foto borrada exitosamente");
                    Toast.makeText(getApplicationContext(), s, LENGTH_LONG).show();
                    imageView.setImageResource(android.R.color.transparent);
                    LimpiaCajas();
                } else {
                    Toast.makeText(getApplicationContext(), s, LENGTH_LONG).show();
                    imageView.setImageResource(android.R.color.transparent);
                }
                finish();
                // **************************************
            }
        }

        try {
            UploadIncidencia ui = new UploadIncidencia();
            imageView = findViewById(R.id.imageClipboard);
            Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ui.execute(bm);
        } catch (NullPointerException e) {
            // funciones.RegistraError(idUsuario, "Caducidad, uploadCaducidad ", e, Caducidad.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(getApplicationContext(), "Error al colocar una foto de incidencia", LENGTH_LONG).show();
        }
    }
}