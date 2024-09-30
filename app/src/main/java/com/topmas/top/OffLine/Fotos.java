package com.topmas.top.OffLine;

import static com.topmas.top.Competencia_Promocion.TAG_INFO;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_URL_O;
import static com.topmas.top.Foto.UPLOAD_VERSION;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.BuildConfig;
import com.topmas.top.Constants;
import com.topmas.top.RequestHandler;

import java.util.HashMap;

public class Fotos {
    //***********************
    // Upload image function
    public void uploadImage(
            Context contexto,
            String _idpromotor,
            String _latitud,
            String _longitud,
            String _fechahora,
            String _idoperacion,
            String _idusuario,
            String _idruta,
            String _imagen,
            String _ifoto
    ) {
        class UploadImage extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Log.i(TAG_INFO, "Respuesta " + s);
                if (s.equals("1")) {
                    // *****************************
                    // Borrando el registro recien colocado
                    // Se debe borrar la foto porque ya se subio
                    AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                    // Log.i(TAG_INFO, "Borrando foto " + _ifoto);
                    int i = almacenaImagen.BorraFotoEnviada(Integer.parseInt(_ifoto));
                    // *****************************
                }
                else{
                    Log.e(Constants.TAG_INFO, "Sin valor de retorno");
                }
            }


            @Override
            protected String doInBackground(String... params) {
                String idpromotor   = params[0];
                String latitud      = params[1];
                String longitud     = params[2];
                String fechahora    = params[3];
                String idoperacion  = params[4];
                String idusuario    = params[5];
                String idruta       = params[6];
                String imagen100    = params[7];
                String iFoto        = params[8];

                String uploadImage100 = imagen100;

                HashMap<String, String> data = new HashMap<>();

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp = versionName + ":" + versionCode;

                /*
                Log.e(TAG_ERROR, "*************************");
                Log.e(TAG_ERROR, String.valueOf(idpromotor));
                Log.e(TAG_ERROR, String.valueOf(latitud));
                Log.e(TAG_ERROR, String.valueOf(longitud));
                Log.e(TAG_ERROR, idusuario);
                Log.e(TAG_ERROR, String.valueOf(idoperacion));
                Log.e(TAG_ERROR, String.valueOf(idruta));
                Log.e(TAG_ERROR, fechahora);
                Log.e(TAG_ERROR, String.valueOf(uploadImage100));
                Log.e(TAG_ERROR, sVerApp);
                Log.e(TAG_ERROR, "1");
                Log.e(TAG_ERROR, String.valueOf(UPLOAD_URL_O));
                Log.e(TAG_ERROR, "*************************");
*/

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(latitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(longitud));
                data.put(UPLOAD_IDUSUARIO, idusuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idruta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage100);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "1");
                // Log.e(TAG_INFO, "-- Enviando Imagen ");

                return rh.sendPostRequest(UPLOAD_URL_O, data);
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(_idpromotor, _latitud, _longitud, _fechahora, _idoperacion, _idusuario, _idruta, _imagen, _ifoto);
    }
}
