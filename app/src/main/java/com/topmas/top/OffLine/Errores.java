package com.topmas.top.OffLine;

import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Foto.UPLOAD_ANDROID_ID;
import static com.topmas.top.Foto.UPLOAD_API_VALUE;
import static com.topmas.top.Foto.UPLOAD_BOARD;
import static com.topmas.top.Foto.UPLOAD_BOOTLOADER;
import static com.topmas.top.Foto.UPLOAD_BUILD_ID;
import static com.topmas.top.Foto.UPLOAD_BUILD_TIME;
import static com.topmas.top.Foto.UPLOAD_DENSIDAD;
import static com.topmas.top.Foto.UPLOAD_ERROR;
import static com.topmas.top.Foto.UPLOAD_ERRORES;
import static com.topmas.top.Foto.UPLOAD_FABRICANTE;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_FINGERPRINT;
import static com.topmas.top.Foto.UPLOAD_HARDWARE;
import static com.topmas.top.Foto.UPLOAD_HOST_VALUE;
import static com.topmas.top.Foto.UPLOAD_MARCA;
import static com.topmas.top.Foto.UPLOAD_MODELO;
import static com.topmas.top.Foto.UPLOAD_RESOLUCION;
import static com.topmas.top.Foto.UPLOAD_SECCION;
import static com.topmas.top.Foto.UPLOAD_SERIE;
import static com.topmas.top.Foto.UPLOAD_TAMANIOPANTALLA;
import static com.topmas.top.Foto.UPLOAD_UID;
import static com.topmas.top.Foto.UPLOAD_USER_VALUE;
import static com.topmas.top.Foto.UPLOAD_USUARIO;
import static com.topmas.top.Foto.UPLOAD_VERSION;

import android.content.Context;
import android.os.AsyncTask;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.RequestHandler;

import java.util.HashMap;

public class Errores {
    String UPLOAD_ERRORES_0 = TAG_SERVIDOR + "/PhotoUpload/upload_errores_0.php";
    //***********************
    // Función utilizada para guardar los errores de la App
    public void cargaErrores(
            Context contexto,
            String _fabricante,
            String _marca,
            String _modelo,
            String _board,
            String _hardware,
            String _serie,
            String _uid,
            String _android_id,
            String _resolucion,
            String _tamaniopantalla,
            String _densidad,
            String _bootloader,
            String _user_value,
            String _host_value,
            String _version,
            String _api_value,
            String _build_id,
            String _build_time,
            String _fingerprint,
            String _usuario,
            String _seccion,
            String _error,
            String _fechahora,
            String _id
    )
    {
        class EstableceErrores extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (Integer.valueOf(s) > 0) {
                    AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                    almacenaImagen.Borra_Errores(Integer.parseInt(_id));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String  fabricante  = params[0];
                String  marca       = params[1];
                String  modelo      = params[2];
                String  board       = params[3];
                String  hardware    = params[4];
                String  serie       = params[5];
                String  uid         = params[6];
                String  android_id  = params[7];
                String  resolucion  = params[8];
                String  tamaniopantalla= params[9];
                String  densidad    = params[10];
                String  bootloader  = params[11];
                String  user_value  = params[12];
                String  host_value  = params[13];
                String  version     = params[14];
                String  api_value   = params[15];
                String  build_id    = params[16];
                String  build_time  = params[17];
                String  fingerprint = params[18];
                String  usuario     = params[19];
                String  seccion     = params[20];
                String  error       = params[21];
                String  fechahora   = params[22];
                String  id          = params[23];

                data.put(UPLOAD_FABRICANTE, fabricante);
                data.put(UPLOAD_MARCA,marca);
                data.put(UPLOAD_MODELO,modelo);
                data.put(UPLOAD_BOARD,board);
                data.put(UPLOAD_HARDWARE,hardware);
                data.put(UPLOAD_SERIE,serie);
                data.put(UPLOAD_UID,uid);
                data.put(UPLOAD_ANDROID_ID,android_id);
                data.put(UPLOAD_RESOLUCION,resolucion);
                data.put(UPLOAD_TAMANIOPANTALLA,tamaniopantalla);
                data.put(UPLOAD_DENSIDAD,densidad);
                data.put(UPLOAD_BOOTLOADER,bootloader);
                data.put(UPLOAD_USER_VALUE,user_value);
                data.put(UPLOAD_HOST_VALUE,host_value);
                data.put(UPLOAD_VERSION,version);
                data.put(UPLOAD_API_VALUE,api_value);
                data.put(UPLOAD_BUILD_ID,build_id);
                data.put(UPLOAD_BUILD_TIME,build_time);
                data.put(UPLOAD_FINGERPRINT,fingerprint);
                data.put(UPLOAD_USUARIO,usuario);
                data.put(UPLOAD_SECCION,seccion);
                data.put(UPLOAD_ERROR,error);
                data.put(UPLOAD_FECHAHORA,fechahora);


                return rh.sendPostRequest(UPLOAD_ERRORES_0,data);
            }
        }

        EstableceErrores ui = new EstableceErrores();
        ui.execute(
                _fabricante,
                _marca,
                _modelo,
                _board,
                _hardware,
                _serie,
                _uid,
                _android_id,
                _resolucion,
                _tamaniopantalla,
                _densidad,
                _bootloader,
                _user_value,
                _host_value,
                _version,
                _api_value,
                _build_id,
                _build_time,
                _fingerprint,
                _usuario,
                _seccion,
                _error,
                _fechahora,
                _id);
    }
}
