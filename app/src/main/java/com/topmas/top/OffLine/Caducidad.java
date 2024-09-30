package com.topmas.top.OffLine;

import static com.topmas.top.Caducidad.UPLOAD_caducidad;
import static com.topmas.top.Caducidad.UPLOAD_idproducto;
import static com.topmas.top.Caducidad.UPLOAD_lote;
import static com.topmas.top.Caducidad.UPLOAD_piezas;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.BuildConfig;
import com.topmas.top.RequestHandler;

import java.util.Calendar;
import java.util.HashMap;

public class Caducidad {
    //***********************
    // Función utilizada para guardar la caducidad del producto
    String UPLOAD_CADUCIDAD_O = TAG_SERVIDOR + "/PhotoUpload/upload_caducidad_o.php";
    public void cargaCaducidad(
            Context contexto,
            String _id,
            String _idpromotor,
            String _latitud,
            String _longitud,
            String _fechahora,
            String _idoperacion,
            String _idusuario,
            String _idruta,
            String _imagen,
            String _idproducto,
            String _lote,
            String _caducidad,
            String _piezas,
            String _idcaducidad
    )
    {
        class EstableceCaducidad extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s == ""){
                    Log.e(TAG_INFO, "Sin valor de retorno");
                }
                else {
                    if (Integer.valueOf(s) > 0) {
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                        almacenaImagen.Borra_Caducidad(Integer.valueOf(_id), Integer.valueOf(_idcaducidad));
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String id           = params[0];
                String idpromotor   = params[1];
                String latitud      = params[2];
                String longitud     = params[3];
                String fechahora    = params[4];
                String idoperacion   = params[5];
                String idusuario   = params[6];
                String idruta       = params[7];
                String imagen       = params[8];
                String idproducto  = params[9];
                String lote        = params[10];
                String caducidad    = params[11];
                String piezas       = params[12];

                Calendar c = Calendar.getInstance();
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //String fechahora1 = sdf.format(fechahora);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp =  versionName + ":" + versionCode;

                /*
                Log.e(TAG_ERROR, "*************************");
                Log.e(TAG_ERROR, String.valueOf(idpromotor));
                Log.e(TAG_ERROR, String.valueOf(latitud));
                Log.e(TAG_ERROR, String.valueOf(longitud));
                Log.e(TAG_ERROR, idusuario);
                Log.e(TAG_ERROR, String.valueOf(idoperacion));
                Log.e(TAG_ERROR, String.valueOf(idruta));
                Log.e(TAG_ERROR, fechahora);
                Log.e(TAG_ERROR, String.valueOf(imagen));
                Log.e(TAG_ERROR, sVerApp);
                Log.e(TAG_ERROR, "1");
                Log.e(TAG_ERROR, String.valueOf(UPLOAD_CADUCIDAD_O));
                Log.e(TAG_ERROR, "*************************");
                */

                data.put(UPLOAD_idproducto, String.valueOf(idproducto));
                data.put(UPLOAD_lote, String.valueOf(lote));
                data.put(UPLOAD_caducidad, caducidad);
                data.put(UPLOAD_piezas, String.valueOf(piezas));
                data.put(UPLOAD_IDRUTA, String.valueOf(idruta));
                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(latitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(longitud));
                data.put(UPLOAD_IDUSUARIO, idusuario);
                data.put(UPLOAD_IDOPERACION, idoperacion);
                data.put(UPLOAD_IMAGEN, imagen);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "1");

                return rh.sendPostRequest(UPLOAD_CADUCIDAD_O,data);

            }

        }

        EstableceCaducidad ui = new EstableceCaducidad();
        ui.execute(_id, _idpromotor, _latitud, _longitud, _fechahora, _idoperacion, _idusuario, _idruta, _imagen, _idproducto, _lote, _caducidad, _piezas, _id, _idcaducidad);

    }
}
