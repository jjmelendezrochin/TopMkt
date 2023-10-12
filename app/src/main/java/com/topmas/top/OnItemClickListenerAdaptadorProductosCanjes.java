package com.topmas.top;

import static com.topmas.top.Constants.TAG_CANTIDADCAJA;
import static com.topmas.top.Constants.TAG_CATEGORIA1;
import static com.topmas.top.Constants.TAG_CATEGORIA2;
import static com.topmas.top.Constants.TAG_DESCRIPCION;
import static com.topmas.top.Constants.TAG_DESCRIPCION1;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_OBSERVACIONES;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRECIOREAL;
import static com.topmas.top.Constants.TAG_PRODUCTO;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

public class OnItemClickListenerAdaptadorProductosCanjes implements AdapterView.OnItemClickListener{

    final Funciones funciones = new Funciones();
    private final Usuario usr = new Usuario();
    String pName = null;
    Context context ;
    String idruta;
    String idproducto;

    int idpromotor;
    String upc;
    String cantidad_caja = "";
    String pIdempresa = "";
    double precio = 0;
    double precioreal = 0;
    String descripcion = "";
    String descripcion1 = "";
    String categoria1 = "";
    String categoria2 = "";
    int pidcanjes;
    int pCantidad;
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        context = view.getContext();

        TextView cajaruta = (view.findViewById(R.id.idruta));
        TextView cajaproducto = (view.findViewById(R.id.idproducto));
        TextView cajaupc = (view.findViewById(R.id.upc_producto));

        idruta = cajaruta.getText().toString();
        idproducto = cajaproducto.getText().toString();
        upc = cajaupc.getText().toString();

        Intent Producto = new Intent(context, Producto.class);
        Producto.putExtra(TAG_IDPRODUCTO, Integer.parseInt(idproducto));
        Producto.putExtra(TAG_IDRUTA, Integer.parseInt(idruta));
        Producto.putExtra(TAG_UPC,upc);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        pName = preferences.getString(TAG_USUARIO, pName);

        /*
        Thread.setDefaultUncaughtExceptionHandler( (thread, throwable) -> {
            //log(throwable.getMessage(), thread.getId());
            funciones.RegistraError(pName, "Producto setDefaultUncaughtExceptionHandler",
                    (Exception) throwable, OnItemClickListenerAdaptadorProductosCanjes.this, context);
        });

         */


        // Toast.makeText(context.getApplicationContext(),  " idproducto " + idproducto, Toast.LENGTH_LONG).show();

        //AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view1 = inflater.inflate(R.layout.activity_producto_cantidad, null);
        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Datos Producto");
        alertDialog.show();

        ImageView imagenProducto = view1.findViewById(R.id.imagenproducto1);
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(context);
        int solicitainventario = almacenaImagen.consulta_configuracion();

        Usuario usr = new Usuario();
        idpromotor = usr.getid();
        String pIdempresa = usr.getidempresa();

        ConsultaProducto(Integer.parseInt(idproducto), Integer.parseInt(idruta), view1);

        // *****************************
        // Si hay conexion a internet obtiene los datos
        if (funciones.RevisarConexion(context)) {
            // Llamado a la consulta del servicio web si hay internet
            //ConsultaProducto consulta = new ConsultaProducto();
            //consulta.execute();

            // ********************************
            // Verifica si hay un dato en el arreglo si no lo hay lo obtiene de web
            try {
                {
                    String sRutaImagen = TAG_SERVIDOR + "/ImagenesProductos/" + String.valueOf(idproducto) + "_" + upc + ".png";
                    // Log.e(TAG_ERROR, "Ruta imagen " + sRutaImagen);
                    MuestraImagen(sRutaImagen,
                            imagenProducto,
                            almacenaImagen,
                            Integer.parseInt(idproducto)
                    );
                }

            } catch (NullPointerException e) {
                // funciones.RegistraError(pName, "Producto ,cmdGuardarPrecio.setOnClickListener", e, Producto.this,getApplicationContext());
                String sRutaImagen = TAG_SERVIDOR + "/ImagenesProductos/" + String.valueOf(idproducto) + "_" + upc + ".png";
                MuestraImagen(sRutaImagen,
                        imagenProducto,
                        almacenaImagen,
                        Integer.parseInt(idproducto)
                );
            }
        } else {
            // Colocar los productos en la pantalla obtenidos de la base de datos local
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.icono_producto_gris);
            imagenProducto.setImageBitmap(bitmap);
        }

        // *************************
        // Estbleciendo el campo cantidad
        pidcanjes =  almacenaImagen.consulta_idcanjes(Integer.parseInt(idruta), idpromotor,Integer.parseInt(idproducto));
        pCantidad = 0;
        if (pidcanjes>0){
            pCantidad = almacenaImagen.consulta_cantidadcanjes(pidcanjes);
        }
        EditText txtCantidadAdquirida = view1.findViewById(R.id.txtCantidadAdquirida);
        txtCantidadAdquirida.setText(String.valueOf(pCantidad));

        // *************************
        // Botón guardar
        Button cmdGuardar = view1.findViewById(R.id.cmdGuardarCantidad);
        cmdGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sCantidad = String.valueOf(txtCantidadAdquirida.getText());
                pCantidad = Integer.valueOf(sCantidad);
                if (sCantidad.trim().length()==0){
                    Toast.makeText(context, " Cantidad debe de tener un valor" , Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    oCanje oncanje = new oCanje(pidcanjes, Integer.parseInt(idruta),idpromotor,
                            Integer.parseInt(idproducto), pCantidad, "");
                    almacenaImagen.insertaoactualizacanjes(oncanje);
                    alertDialog.dismiss();
                }
            }
        });

        // *************************
        // Botón regresar
        Button cmdSalir = view1.findViewById(R.id.cmdRegresar);
        cmdSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    //************************************
    // Consulta un producto
    public void ConsultaProducto(
            int pidproducto,
            int pidruta,
            View view1
    ) {
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(context);
        oProducto Producto = almacenaImagen.ObtenProducto(pidproducto, pidruta);
        // Colocacion de datos en los arreglos
        descripcion = oProducto.get_descripcion();
        descripcion1 = oProducto.get_descripcion();
        upc = oProducto.get_upc();
        categoria1 = oProducto.get_categoria1();
        categoria2 = oProducto.get_categoria2();
        cantidad_caja = oProducto.get_cantidadcaja();
        precio = oProducto.get_precio();

        TextView cajadescripcion1 = view1.findViewById(R.id.txtDescripcion1);
        TextView cajaupc = view1.findViewById(R.id.txtUpc);
        TextView cajacategoria2 = view1.findViewById(R.id.txtCategoria02);
        TextView cajacantidadporcaja = view1.findViewById(R.id.txtCantidadporCaja);
        TextView cajaprecio = view1.findViewById(R.id.txtPrecio);

        cajadescripcion1.setText(descripcion);
        cajaupc.setText(upc);
        cajacategoria2.setText(categoria1);
        cajacantidadporcaja.setText(cantidad_caja);
        cajaprecio.setText(String.valueOf(precio));
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
                    //funciones.RegistraError(pName, "Producto,MuestraImagen", e, OnItemClickListenerAdaptadorProductosCanjes.this,context);
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

