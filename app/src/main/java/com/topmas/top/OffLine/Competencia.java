package com.topmas.top.OffLine;

import static com.topmas.top.Constants.TAG_ACTIVIDADBTL;
import static com.topmas.top.Constants.TAG_CANJES;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDEMOSTRADOR;
import static com.topmas.top.Constants.TAG_IDEMPAQUE;
import static com.topmas.top.Constants.TAG_IEMPLAYE;
import static com.topmas.top.Constants.TAG_IEXHIBIDOR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRESENTACION;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_producto;
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
import com.topmas.top.RequestHandler;

import java.util.HashMap;

public class Competencia {
    public static final String UPLOAD_COMPETENCIA_O = TAG_SERVIDOR + "/PhotoUpload/upload_competencia_o.php";
    //***********************
    // Upload image Competencia function
    public void uploadImagenCompetencia(
            Context contexto,
            String _idpromotor,
            String _latitud,
            String _longitud,
            String _fechahora,
            String _idoperacion,
            String _idusuario,
            String _idruta,
            String _imagen,
            String _producto,
            String _precio,
            String _presentacion,
            String _idempaque,
            String _demostrador,
            String _exhibidor,
            String _emplaye,
            String _actividadbtl,
            String _canjes,
            String _id,
            String _idcompetencia
    ) {
        class UploadImageCompetencia extends AsyncTask<String, Void, String> {

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
                        // ******************************
                        // Borrando el registro recien colocado en el telefono solo si el resultado es > 0
                        AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                        almacenaImagen.Borrar_Competencia(Integer.valueOf(_id), Integer.valueOf(_idcompetencia));
                    }
                }
            }


            @Override
            protected String doInBackground(String... params) {
                String idpromotor = params[0];
                String latitud = params[1];
                String longitud = params[2];
                String fechahora = params[3];
                String idoperacion = params[4];
                String idusuario = params[5];
                String idruta = params[6];
                String imagen100 = params[7];
                String producto = params[8];
                String precio = params[9];
                String presentacion = params[10];
                String idEmpaque = params[11];
                String demostrador = params[12];
                String exhibidor = params[13];
                String emplaye = params[14];
                String actividadbtl = params[15];
                String canjes = params[16];
                String id = params[17];
                String idcompetencia = params[18];

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
                Log.e(TAG_ERROR, String.valueOf(UPLOAD_COMPETENCIA_O));
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
                data.put(TAG_producto, String.valueOf(producto));
                data.put(TAG_PRECIO, String.valueOf(precio));
                data.put(TAG_PRESENTACION, String.valueOf(presentacion));
                data.put(TAG_IDEMPAQUE, String.valueOf(idEmpaque));
                data.put(TAG_IDEMOSTRADOR, String.valueOf(demostrador));
                data.put(TAG_IEXHIBIDOR, String.valueOf(exhibidor));
                data.put(TAG_IEMPLAYE, String.valueOf(emplaye));
                data.put(TAG_ACTIVIDADBTL, String.valueOf(actividadbtl));
                data.put(TAG_CANJES, String.valueOf(canjes));

                return rh.sendPostRequest(UPLOAD_COMPETENCIA_O,data);
            }
        }

        UploadImageCompetencia ui = new UploadImageCompetencia();
        ui.execute(_idpromotor, _latitud, _longitud, _fechahora, _idoperacion, _idusuario, _idruta, _imagen,
                _producto, _precio, _presentacion, _idempaque,_demostrador, _exhibidor,_emplaye,
                _actividadbtl,_canjes, _id, _idcompetencia);
    }
}
