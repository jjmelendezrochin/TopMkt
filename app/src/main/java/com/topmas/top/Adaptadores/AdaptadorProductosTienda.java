package com.topmas.top.Adaptadores;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_SERVIDOR;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.Funciones;
import com.topmas.top.R;
import com.topmas.top.Usuario;

import org.json.JSONException;

public class AdaptadorProductosTienda extends BaseAdapter {
    private int[] ArrProductos;
    private int[] ArrRutas;
    private String[] ArrDescripciones;
    //private int[] ArrPosiciones;
    private String[] ArrUpcs;
    private Funciones funciones = new Funciones();
    private Activity context;
    private final Usuario usr = new Usuario();
    private String pName = "";

    public AdaptadorProductosTienda(
            Activity context,
            int[] aproductos,
            int[] arutas,
            String[] adescripciones,
            //int[] aposiciones,
            String[] aupcs
    ) {
        this.context = context;
        this.ArrProductos = aproductos;
        this.ArrRutas= arutas;
        this.ArrDescripciones= adescripciones;
        this.ArrUpcs = aupcs;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        pName = preferences.getString(TAG_USUARIO, pName);
    }

    @Override
    public int getCount() {
        return ArrProductos.length;
    }
    @Override
    public Object getItem(int position){return  null;}
    public long getItemId(int position) { return 0;}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        AlmacenaImagen almacenaImagen =
                new AlmacenaImagen(context);

        String sRutaImagen = null;
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.lista_productos, parent, false);

        ImageView imagen = view.findViewById(R.id.imagenproducto);
        TextView cajaidproducto = (TextView) view.findViewById(R.id.idproducto);
        TextView cajaidruta = (TextView) view.findViewById(R.id.idruta);
        TextView cajaproducto = (TextView) view.findViewById(R.id.txtProducto);
        TextView cajaupc = (TextView) view.findViewById(R.id.upc_producto);

        try {
            if (!ArrDescripciones[position].equals("") &&
                    ArrDescripciones[position] != null ) {

                if (funciones.RevisarConexion(context)) {
                    // ********************************
                    // Verifica si hay un dato en el arreglo si no lo hay lo obtiene de web
                    try {
                        if (funciones.ObtenImagen(position) == null) {
                            // Log.e(TAG_ERROR, "1. No Hay imagen " + String.valueOf(position));
                            sRutaImagen = TAG_SERVIDOR +  "/ImagenesProductos/" +
                                    String.valueOf(ArrProductos[position]) + "_" + ArrUpcs[position] + ".png";
                            MuestraImagen(sRutaImagen,
                                    imagen,
                                     almacenaImagen,
                                    ArrProductos[position],
                                    position);
                        } else {
                             // Log.e(TAG_ERROR, "2. Hay imagen "+ String.valueOf( position));
                            imagen.setImageBitmap(funciones.ObtenImagen(position));
                        }
                    } catch (NullPointerException e) {
                        /*
                        sRutaImagen = "https://www.topmas.mx/TopMas/ImagenesProductos/" +
                                String.valueOf(ArrProductos[position]) + "_" + ArrUpcs[position] + ".png";
                        */

                        sRutaImagen = TAG_SERVIDOR +  "/ImagenesProductos/" +
                                String.valueOf(ArrProductos[position]) + "_" + ArrUpcs[position] + ".png";

                        // Log.e(TAG_ERROR, "Ruta imagen2 " + sRutaImagen);
                        MuestraImagen(sRutaImagen,
                                imagen,
                                almacenaImagen,
                                ArrProductos[position],
                                position);

                    }
                }

                // *************************************************
                cajaidproducto.setText(String.valueOf(ArrProductos[position]));
                cajaidproducto.setVisibility(View.GONE);
                cajaidruta.setText(String.valueOf(ArrRutas[position]));
                cajaidruta.setVisibility(View.GONE);
                cajaproducto.setText(ArrDescripciones[position]);
                cajaproducto.setVisibility(View.VISIBLE);
                cajaupc.setText(ArrUpcs[position]);
                cajaupc.setVisibility(View.GONE);

                return view;

            } else {
                return null;
            }
        } catch (Exception e) {
            String Resultado = "Se generó el siguiente error : " + e.toString();
        }
        return view;
    }


    // ***************************
    // Carga una imagen desde url y la guarda en la base de datos Sqlite para que la carge de ahi la proxima ocasión
    public void MuestraImagen(
            String _url,
            ImageView _imageView,
            AlmacenaImagen _almacenaImagen,
            int _idproducto,
            int _position
    ) {
        class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
            private final String url;
            private final ImageView imageView;
            private final AlmacenaImagen almacenaImagen;
            private final int idproducto;
            private final int position;

            public ImageLoadTask(
                    String purl,
                    ImageView pimageView,
                    AlmacenaImagen palmacenaImagen,
                    int pidproducto,
                    int pposition
            ) {
                this.url = purl;
                this.imageView = pimageView;
                this.almacenaImagen = palmacenaImagen;
                this.idproducto = pidproducto;
                this.position = pposition;
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
                } catch (java.net.ConnectException e0) {
                    funciones.RegistraError(pName, "AdaptadorProductosTienda, MuestraImagen (java.net.ConnectException)", e0, context, context);
                } catch (FileNotFoundException e1) {
                    funciones.RegistraError(pName, "AdaptadorProductosTienda, MuestraImagen (FileNotFoundException)", e1, context, context);
                } catch (Exception e) {
                    funciones.RegistraError(pName, "AdaptadorProductosTienda, MuestraImagen", e, context, context);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                //almacenaImagen.guardarImagenProducto(idproducto,bitmap);    // Guarda imágen de ese producto para que la proxima vez la tome del teléfono y no de la url
                imageView.setImageBitmap(bitmap);
                // Log.e(TAG_ERROR, "Guardando en posicion " + String.valueOf(position));
                funciones.ColocaImagen(position, bitmap);
            }
        }
        if (funciones.RevisarConexion(this.context)) {
            // Lamada a cargar y guardar imágen
            ImageLoadTask imageLoadTask =
                    new ImageLoadTask(_url, _imageView, _almacenaImagen, _idproducto, _position);
            imageLoadTask.execute();
        }
    }
}
