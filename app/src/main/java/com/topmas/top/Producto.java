package com.topmas.top;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import static com.topmas.top.Constants.TAG_CANTIDADCAJA;
import static com.topmas.top.Constants.TAG_CATEGORIA1;
import static com.topmas.top.Constants.TAG_CATEGORIA2;
import static com.topmas.top.Constants.TAG_DESCRIPCION;
import static com.topmas.top.Constants.TAG_DESCRIPCION1;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDOBS;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INVFINAL;
import static com.topmas.top.Constants.TAG_INVINICIAL;
import static com.topmas.top.Constants.TAG_OBSERVACIONES;
import static com.topmas.top.Constants.TAG_POSICION;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRECIOREAL;
import static com.topmas.top.Constants.TAG_PRODUCTO;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_imagen;

public class Producto extends AppCompatActivity {

    int pidproducto = 0;
    double precio = 0;
    double precioreal = 0;
    int pidruta = 0;
    int pidpromotor = 0;
    int idObs = 0;
    int invinicial = 0;
    int invfinal = 0;
    int solicitainventario = 0;

    String upc = "";
    String cantidad_caja = "";
    String pIdempresa = "";
    String pUpc = "";

    String descripcion = "";
    String descripcion1 = "";
    String categoria1 = "";
    String categoria2 = "";
    byte[] byteArrayImagen = null;
    ImageView imagenProducto = null;
    String sRutaImagen = null;

    ProgressDialog pDialog;
    final Funciones funciones = new Funciones();
    private final Usuario usr = new Usuario();
    String pName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        Intent i = getIntent();
        pidproducto = i.getIntExtra(TAG_IDPRODUCTO, pidproducto);
        pidruta = i.getIntExtra(TAG_IDRUTA, pidruta);
        pUpc = i.getStringExtra(TAG_UPC);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pName = preferences.getString(TAG_USUARIO, pName);

        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "Producto setDefaultUncaughtExceptionHandler", (Exception) throwable, Producto.this, getApplicationContext());
        });

        imagenProducto = findViewById(R.id.imagenproducto1);

        // Log.e(TAG_ERROR, "-- pidproducto " + (pidproducto));
        // Log.e(TAG_ERROR, "-- pidruta " + (pidruta));
        // Log.e(TAG_ERROR, "-- pUpc " + (pUpc));

        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
        solicitainventario = almacenaImagen.consulta_configuracion();

        Usuario usr = new Usuario();
        pidpromotor = usr.getid();
        pIdempresa = usr.getidempresa();
        // Log.e(TAG_ERROR,"Al cargar Producto , idEmpresa leida. " + pIdempresa);

        // Log.e(TAG_ERROR, "idproducto leido " + String.valueOf(pidproducto));
        // Log.e(TAG_ERROR, "idruta leida " + String.valueOf(pidruta));

        //****************************
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //****************************
        // Botón de guardar cmdGuardarPrcecio
        Button cmdGuardarPrecio = findViewById(R.id.cmdGuardarPrecio);
        cmdGuardarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // *****************************************
                // Obtiene el precio capturado en la caja de texto
                double miprecioreal;
                TextView cajaPrecioReal = findViewById(R.id.txtPrecioReal);
                TextView cajainvinicial = findViewById(R.id.txtInvInicial);
                TextView cajainvfinal = findViewById(R.id.txtInvFinal);

                String sinvInicial = cajainvinicial.getText().toString().trim();
                String sinvFinal = cajainvfinal.getText().toString().trim();
                sinvInicial = (sinvInicial.length()==0?"0":sinvInicial.trim());
                sinvFinal = (sinvFinal.length()==0?"0":sinvFinal.trim());
                cajainvinicial.setText(sinvInicial);
                cajainvfinal.setText(sinvFinal);

                try {
                    miprecioreal = Double.parseDouble(cajaPrecioReal.getText().toString());
                    invinicial = Integer.parseInt(cajainvinicial.getText().toString());
                    invfinal = Integer.parseInt(cajainvfinal.getText().toString());
                } catch (Exception e) {
                    // funciones.RegistraError(pName, "Producto ,cmdGuardarPrecio.setOnClickListener", e, Producto.this,getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Solo se permiten valores numéricos en el precio e inventario", Toast.LENGTH_LONG).show();
                    return;
                }

                // ****************************
                // Si la variable solicitainventario es >0 entonces debe solicitar inventario inicial y final
                if (solicitainventario>0 && (invinicial==0 || invfinal == 0)){
                    Toast.makeText(getApplicationContext(), "Inventario inicial y final son datos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }

                // ****************************
                // Valida monto del precio real
                if (miprecioreal < 1 && idObs <3) {
                    Toast.makeText(getApplicationContext(), "El precio debe ser mayor a cero", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(miprecioreal < 1 && idObs ==3) {
                    // Debe permitir guardar precio en cero pues las observaciones dicen no catalogado
                    miprecioreal = 0;
                }

                if (funciones.RevisarConexion(getApplicationContext())) {
                    // *****************************************
                    // Guardar los datos en la plataforma
                    estableceprecio();
                } else {
                    // *****************************************
                    // Debe de guardar el precio al producto en la tabla local
                    AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                    int iRes = almacenaImagen.actualiza_precioproducto(pidproducto, pidruta, pidpromotor, miprecioreal, invinicial, invfinal, 1, idObs);
                    String mensaje;
                    if (iRes > 0) {
                        mensaje = "Dato guardado";
                    } else {
                        mensaje = "Error al guardar";
                    }
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        //****************************
        // Botón de guardar el mismo precio
        Button cmdMismoPrecio = findViewById(R.id.cmdMismoOrecio);
        cmdMismoPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double miprecioreal;
                TextView cajaPrecio = findViewById(R.id.txtPrecio);
                TextView cajaPrecioReal = findViewById(R.id.txtPrecioReal);
                try {
                    miprecioreal = Double.parseDouble(cajaPrecio.getText().toString());
                } catch (Exception e) {
                    // funciones.RegistraError(pName, "Producto ,cmdMismoPrecio.setOnClickListener", e, Producto.this,getApplicationContext());
                    cajaPrecioReal.setText("0");
                    return;
                }
                cajaPrecioReal.setText(String.valueOf(miprecioreal));

            }
        });

        // *****************************
        // Si hay conexion a internet obtiene los datos
        if (funciones.RevisarConexion(getApplicationContext())) {
            // Llamado a la consulta del servicio web si hay internet
            ConsultaProducto consulta = new ConsultaProducto();
            consulta.execute();

            // ********************************
            // Verifica si hay un dato en el arreglo si no lo hay lo obtiene de web
            try {
                //if (funciones.ObtenImagen(pposicion) == null)
                {
                   // Log.e(TAG_ERROR, "1. No Hay imagen " + (pposicion));
                    sRutaImagen = TAG_SERVIDOR + "/ImagenesProductos/" + String.valueOf(pidproducto) + "_" + pUpc + ".png";
                    // Log.e(TAG_ERROR, "Ruta imagen " + sRutaImagen);
                    MuestraImagen(sRutaImagen,
                            imagenProducto,
                            almacenaImagen,
                            pidproducto
                            );
                }
                /*
                else {
                    // Log.e(TAG_ERROR, "2. Hay imagen "+ String.valueOf( position));
                    imagenProducto.setImageBitmap(funciones.ObtenImagen(pposicion));
                }*/
            } catch (NullPointerException e) {
                // Log.e(TAG_ERROR, "1a. No Hay imagen "+ ( pposicion));
                // funciones.RegistraError(pName, "Producto ,cmdGuardarPrecio.setOnClickListener", e, Producto.this,getApplicationContext());
                sRutaImagen = TAG_SERVIDOR + "/ImagenesProductos/" + String.valueOf(pidproducto) + "_" + pUpc + ".png";
                MuestraImagen(sRutaImagen,
                        imagenProducto,
                        almacenaImagen,
                        pidproducto);

            }
        } else {
            // Colocar los productos en la pantalla obtenidos de la base de datos local
            ConsultaProducto(pidproducto, pidruta);

            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.icono_producto_gris);
            imagenProducto.setImageBitmap(bitmap);
        }

        // ******************************
        Spinner spinObs = findViewById(R.id.spinObs);
        spinObs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                idObs = Integer.parseInt(String.valueOf(id));
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //************************************
    // Consulta un producto
    public void ConsultaProducto(int pidproducto, int pidruta) {
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        oProducto Producto = almacenaImagen.ObtenProducto(pidproducto, pidruta);
        // Colocacion de datos en los arreglos
        descripcion = oProducto.get_descripcion();
        descripcion1 = oProducto.get_descripcion();
        upc = oProducto.get_upc();
        categoria1 = oProducto.get_categoria1();
        categoria2 = oProducto.get_categoria2();
        cantidad_caja = oProducto.get_cantidadcaja();
        precio = oProducto.get_precio();
        precioreal = oProducto.get_precioreal();

        MuestraLista(upc);
    }

    //************************************
    // Clase que consulta las productos
    @SuppressLint("StaticFieldLeak")
    public class ConsultaProducto extends AsyncTask<Void, Void, String> {
        String sRuta = "";
        String data = "";
        String Error = null;

        @Override
        protected void onPreExecute() {
            // TODO /CatalogoProductos/obtenerproducto2.php
            sRuta = TAG_SERVIDOR + "/CatalogoProductos/obtenerproducto2.php?idproducto=" + pidproducto + "&idruta=" + pidruta + "&idempresa=" + pIdempresa;
            Log.e("Ruta", sRuta);
            super.onPreExecute();
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
                    sb.append(line).append(" ");
                }

                // Append Server Response To Content String
                sRespusta = sb.toString();

            } catch (Exception ex) {
                funciones.RegistraError(pName, "Producto ,ConsultaProducto 1", ex, Producto.this,getApplicationContext());
                Error = ex.getMessage();
            } finally {
                try {
                    assert reader != null;
                    reader.close();
                } catch (Exception ex) {
                    funciones.RegistraError(pName, "Producto ,ConsultaProducto 2", ex, Producto.this,getApplicationContext());
                    Error = ex.getMessage();
                }
            }

            // **************************
            // Proceso de lectura de datos
            if (Error != null) {
                String Resultado = "Se generó el siguiente error : " + Error;
                // Log.e(TAG_ERROR, Resultado);
            } else {
                try {
                    JSONObject jsonResponse, jsonChidNode, jsonObjRuta;
                    JSONArray jsonarray;
                    // Revisando la respusta
                    assert sRespusta != null;
                    jsonResponse = new JSONObject(sRespusta);
                    // Log.e("AQUI", sRespusta.toString());
                    jsonarray = jsonResponse.getJSONArray(TAG_RESPUESTA);       // Arreglo

                    int iLongitudArreglo = jsonarray.length();
                    // ******************************************
                    // Ciclo de obtención de datos del servicio web
                    for (int i = 0; i < iLongitudArreglo; i++) {
                        jsonChidNode = jsonarray.getJSONObject(i);
                        jsonObjRuta = jsonChidNode.getJSONObject(TAG_PRODUCTO);

                        // Colocacion de datos en los arreglos
                        descripcion = jsonObjRuta.getString(TAG_DESCRIPCION);
                        descripcion1 = jsonObjRuta.getString(TAG_DESCRIPCION1);
                        upc = (jsonObjRuta.getString(TAG_UPC));
                        categoria1 = jsonObjRuta.getString(TAG_CATEGORIA1);
                        categoria2 = jsonObjRuta.getString(TAG_CATEGORIA2);
                        cantidad_caja = (jsonObjRuta.getString(TAG_CANTIDADCAJA));
                        precio = jsonObjRuta.getDouble(TAG_PRECIO);
                        precioreal = jsonObjRuta.getDouble(TAG_PRECIOREAL);
                    }

                } catch (JSONException e) {
                    funciones.RegistraError(pName, "Producto ,ConsultaProducto 3", e, Producto.this,getApplicationContext());
                    String Resultado = "Se generó el siguiente error : " + e.toString();
                    // Log.e(TAG_ERROR, Resultado);
                }
            }
            // **************************
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            MuestraLista(upc);
        }
    }

    // **************************
    // Muestra lista después del proceso
    public void MuestraLista(String upc) {
        //TextView cajadescripcion =  this.findViewById(R.id.txtDescripcion);
        TextView cajadescripcion1 = this.findViewById(R.id.txtDescripcion1);
        TextView cajaupc = this.findViewById(R.id.txtUpc);
        TextView cajacategoria2 = this.findViewById(R.id.txtCategoria02);
        TextView cajacantidadporcaja = this.findViewById(R.id.txtCantidadporCaja);
        TextView cajaprecio = this.findViewById(R.id.txtPrecio);
        TextView cajaprecioreal = this.findViewById(R.id.txtPrecioReal);
        Button cmdMismoPrecio = this.findViewById(R.id.cmdMismoOrecio);
        ImageView imagen = this.findViewById(R.id.imagenproducto1);

        // *****************
        // Guarda imágen para que se pueda consultar en el intent de producto
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());

        // Aqui se coloca la imágen del producto

        cajadescripcion1.setText(descripcion);
        cajadescripcion1.setVisibility(View.VISIBLE);
        cajaupc.setText(upc);
        cajaupc.setVisibility(View.VISIBLE);
        cajacategoria2.setText(categoria2);
        cajacategoria2.setVisibility(View.VISIBLE);
        cajacantidadporcaja.setText(cantidad_caja);
        cajacantidadporcaja.setVisibility(View.VISIBLE);
        cajaprecio.setText(String.valueOf(precio));
        cajaprecio.setVisibility(View.VISIBLE);
        cajaprecioreal.setText(String.valueOf(precioreal));
        cajaprecioreal.setVisibility(View.VISIBLE);

        // *****************************
        // llenando los datos de la lista de observaciones
        Cursor c = almacenaImagen.CursorObservaciones();
        String[] from = new String[]{TAG_OBSERVACIONES};
        int[] to = new int[]{android.R.id.text1};
        // This is your simple cursor adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner s = findViewById( R.id.spinObs );
        s.setAdapter(adapter);

        // *****************************
        // Deshabilitar boton de mismo precio si el precio es cero
        if (precio == 0) {
            cmdMismoPrecio.setVisibility(View.GONE);
        } else {
            cmdMismoPrecio.setVisibility(View.VISIBLE);
        }

    }

    //***********************
    // Función utilizada para guardar el precio del producto
    private void estableceprecio() {
        class FijaPrecio extends AsyncTask<Void, Void, String> {

            private final RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(Producto.this);
                pDialog.setMessage("Guardando precio ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> data = new HashMap<>();

                data.put(TAG_IDRUTA, String.valueOf(pidruta));
                data.put(TAG_IDPRODUCTO, String.valueOf(pidproducto));
                data.put(TAG_IDPROMOTOR, String.valueOf(pidpromotor));
                data.put(TAG_PRECIO, String.valueOf(precioreal));
                data.put(TAG_INVINICIAL, String.valueOf(invinicial));
                data.put(TAG_INVFINAL, String.valueOf(invfinal));
                data.put(TAG_IDEMPRESA, pIdempresa);
                data.put(TAG_IDOBS, String.valueOf(idObs));
                // TODO /CatalogoProductos/createProductoRutaFecha1.php
                String PRODUCTO_URL = TAG_SERVIDOR + "/CatalogoProductos/createProductoRutaFecha1.php";
                // Log.e(TAG_ERROR, "-- Enviando datos de precios");
                return rh.sendPostRequest(PRODUCTO_URL, data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                // **************************************
                // Debe de guardar el precio al producto en la tabla local
                AlmacenaImagen almacenaImagen = new AlmacenaImagen(getApplicationContext());
                int iRes = almacenaImagen.actualiza_precioproducto(pidproducto, pidruta, pidpromotor, precioreal, invinicial, invfinal, 0, idObs);
                String mensaje;
                if (iRes > 0) {
                    mensaje = "Dato guardado";
                } else {
                    mensaje = "Error al guardar";
                }
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                finish();
                // **************************************
            }
        }

        // *****************************************
        // Ejecuta procedimiento
        try {
            FijaPrecio fp = new FijaPrecio();
            double miprecioreal;
            TextView cajaPrecioReal = findViewById(R.id.txtPrecioReal);
            try {
                miprecioreal = Double.parseDouble(cajaPrecioReal.getText().toString());
            } catch (NumberFormatException nfe) {
                Toast.makeText(getApplicationContext(), "Solo se permiten valores numéricos en el precio", Toast.LENGTH_LONG).show();
                return;
            }
            // *****************************************
            //Fija el valor obtenido en la caja de texto
            precioreal = miprecioreal;
            fp.execute();
        } catch (java.lang.NullPointerException e) {
            // funciones.RegistraError(pName, "Producto ,estableceprecio", e, Producto.this,getApplicationContext());
            Toast.makeText(getApplicationContext(), "Error al tratar de establecer el precio", Toast.LENGTH_LONG).show();
        }
    }


    // ***************************
    // Carga una imagen desde url y la guarda en la base de datos Sqlite para que la carge de ahi la proxima ocasión
    public void MuestraImagen(
            String _url,
            ImageView _imageView,
            AlmacenaImagen _almacenaImagen,
            int _idproducto
    ) {
        class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
            private final String url;
            private final ImageView imageView;
            private final AlmacenaImagen almacenaImagen;
            private final int idproducto;
            //private final int position;

            public ImageLoadTask(
                    String purl,
                    ImageView pimageView,
                    AlmacenaImagen palmacenaImagen,
                    int pidproducto
                    //int pposition
            ) {
                this.url = purl;
                this.imageView = pimageView;
                this.almacenaImagen = palmacenaImagen;
                this.idproducto = pidproducto;
                //this.position = pposition;
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    URL urlConnection = new URL(this.url);
                    HttpURLConnection connection = (HttpURLConnection) urlConnection
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    return BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    funciones.RegistraError(pName, "Producto,MuestraImagen", e, Producto.this,getApplicationContext());
                    // e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                //almacenaImagen.guardarImagenProducto(idproducto,bitmap);    // Guarda imágen de ese producto para que la proxima vez la tome del teléfono y no de la url
                imageView.setImageBitmap(bitmap);
                // Log.e(TAG_ERROR, "Guardando en posicion " + String.valueOf(position));
                //funciones.ColocaImagen(position, bitmap);
            }
        }
        // Lamada a cargar y guardar imágen
        ImageLoadTask imageLoadTask =
                new ImageLoadTask(_url, _imageView, _almacenaImagen, _idproducto);
        imageLoadTask.execute();
    }
}
