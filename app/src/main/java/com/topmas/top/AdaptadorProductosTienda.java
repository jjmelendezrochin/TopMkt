package com.topmas.top;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.topmas.top.Constants.TAG_ERROR;

public class AdaptadorProductosTienda extends BaseAdapter {
    private int[] ArrProductos;
    private int[] ArrRutas;
    private String[] ArrDescripciones;
    //private int[] ArrPosiciones;
    private String[] ArrUpcs;
    private Funciones funciones = new Funciones();
    private Activity context;

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
        //this.ArrPosiciones = aposiciones;
        this.ArrUpcs = aupcs;

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
        //TextView cajaposiciones = (TextView) view.findViewById(R.id.posiciones);
        TextView cajaupc = (TextView) view.findViewById(R.id.upc_producto);

        if (!ArrDescripciones[position].equals("") &&
                ArrDescripciones[position] != null ) {

            if (funciones.RevisarConexion(context)) {
                // ********************************
                // Verifica si hay un dato en el arreglo si no lo hay lo obtiene de web
                try {
                    if (funciones.ObtenImagen(position) == null) {
                        // Log.e(TAG_ERROR, "1. No Hay imagen " + String.valueOf(position));
                        sRutaImagen = "https://www.topmas.mx/TopMas/ImagenesProductos/" +
                                String.valueOf(ArrProductos[position]) + "_" + ArrUpcs[position] + ".png";
                        // Log.e(TAG_ERROR, "Ruta imagen " + sRutaImagen);
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
                     // Log.e(TAG_ERROR, "1a. No Hay imagen "+ String.valueOf( position));
                    sRutaImagen = "https://www.topmas.mx/TopMas/ImagenesProductos/" +
                            String.valueOf(ArrProductos[position]) + "_" + ArrUpcs[position] + ".png";
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
                } catch (Exception e) {
                    e.printStackTrace();
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
        // Lamada a cargar y guardar imágen
        ImageLoadTask imageLoadTask =
                new ImageLoadTask(_url, _imageView, _almacenaImagen, _idproducto, _position);
        imageLoadTask.execute();
    }
}
