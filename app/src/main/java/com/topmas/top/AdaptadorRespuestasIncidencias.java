package com.topmas.top;

import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

public class AdaptadorRespuestasIncidencias extends BaseAdapter {
    private int[] aidinc;
    private String[] afechasdeincidencias;
    private String[] atiposdeincidencias;
    private String[] aobservacionesincidencias;
    private int[] arutasincidencias;
    private String[] atiendasincidencias;
    private String[] adireccionesincidencias;
    private String[] afechasrespuestaincidencias;
    private String[] arespuestasincidencias;
    private final Funciones funciones = new Funciones();
    private final Activity context;
    private final Usuario usr = new Usuario();
    private String pName = "";

    public AdaptadorRespuestasIncidencias(
            Activity context,
            int[] idinc2,
            String[] fechasdeincidencias2,
            String[] tiposdeincidencias2,
            String[] observacionesincidencias2,
            int[] idrutasincidencias2,
            String[] tiendasincidencias2,
            String[] direccionesincidencias2,
            String[] fechasderespuestasincidencias2,
            String[] respuestasincidencias2
    ) {
        this.context = context;
        this.aidinc = idinc2;
        this.afechasdeincidencias= fechasdeincidencias2;
        this.atiposdeincidencias= tiposdeincidencias2;
        this.aobservacionesincidencias = observacionesincidencias2;
        this.arutasincidencias= idrutasincidencias2;
        this.atiendasincidencias= tiendasincidencias2;
        this.adireccionesincidencias = direccionesincidencias2;
        this.afechasrespuestaincidencias = fechasderespuestasincidencias2;
        this.arespuestasincidencias = respuestasincidencias2;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        pName = preferences.getString(TAG_USUARIO, pName);
    }

    @Override
    public int getCount() {
        return aidinc.length;
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
        View view = inflater.inflate(R.layout.lista_respuestasincidencias, parent, false);

        ImageView imagen                = view.findViewById(R.id.imagenincidencia);
        TextView txtIdinc               = view.findViewById(R.id.idinc);
        TextView txtFecha               = view.findViewById(R.id.txtFecha);
        TextView txtIncidencia          = view.findViewById(R.id.txtIncidencia);
        TextView txtObservaciones       = view.findViewById(R.id.txtObservaciones);
        TextView idruta                 = view.findViewById(R.id.idruta);
        TextView txttienda              = view.findViewById(R.id.txttienda);
        TextView txtdireccion           = view.findViewById(R.id.txtdireccion);
        TextView txtfechahora_respuesta = view.findViewById(R.id.txtfechahora_respuesta);
        TextView txtrespuesta           = view.findViewById(R.id.txtrespuesta);

        try {
            if (!afechasdeincidencias[position].equals("") &&
                    afechasdeincidencias[position] != null ) {

                /*
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
                */

                // *************************************************
                txtIdinc.setText(String.valueOf(aidinc[position]));
                txtIdinc.setVisibility(View.GONE);
                idruta.setText(String.valueOf(arutasincidencias[position]));
                idruta.setVisibility(View.GONE);
                txttienda.setText(String.valueOf(atiendasincidencias[position]));
                txttienda.setVisibility(View.GONE);
                txtdireccion.setText(String.valueOf(adireccionesincidencias[position]));
                txtdireccion.setVisibility(View.GONE);
                txtFecha.setText(afechasdeincidencias[position]);
                txtFecha.setVisibility(View.VISIBLE);
                txtIncidencia.setText(String.valueOf(atiposdeincidencias[position]));
                txtIncidencia.setVisibility(View.VISIBLE);
                txtObservaciones.setText(aobservacionesincidencias[position]);
                txtObservaciones.setVisibility(View.VISIBLE);
                txtfechahora_respuesta.setText(String.valueOf(afechasrespuestaincidencias[position]));
                txtfechahora_respuesta.setVisibility(View.VISIBLE);
                txtrespuesta.setText(arespuestasincidencias[position]);
                txtrespuesta.setVisibility(View.VISIBLE);
                return view;
            } else {
                return null;
            }
        } catch (Exception e) {
            String Resultado = "Se generó el siguiente error : " + e.toString();
        }
        return view;
    }

    /*
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
    */
}
