package com.topmas.top.OffLine;

import static android.widget.Toast.LENGTH_LONG;
import static com.topmas.top.Canjes.UPLOAD_CANJES;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_CARGA_FOTO_EXITOSA;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Foto.UPLOAD_ARREGLOPRODUCTO;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_IMAGEN1;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LLAVE;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;
import static com.topmas.top.Incidencia.UPLOAD_IDINCIDENCIA;
import static com.topmas.top.Incidencia.UPLOAD_INCIDENCIA;
import static com.topmas.top.Incidencia.UPLOAD_OBSERVACIONES;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.BuildConfig;
import com.topmas.top.Foto;
import com.topmas.top.Funciones;
import com.topmas.top.Incidencia;
import com.topmas.top.R;
import com.topmas.top.RequestHandler;

import java.util.HashMap;


public class Incidencias {
    // ****************************
    // Funcion para subir los datos de caducidad
    Context elcontexto = null;
    public void uploadIncidencia(
            Context contexto,
            String idinc,
            String idincidencia,
            String pidRuta,
            String pidPromotor,
            String platitud,
            String plongitud,
            String idUsuario,
            String idoperacion,
            String observaciones,
            String fechahora,
            String uploadImage,
            int iFoto,
            int sinDatos
    ) {
        elcontexto = contexto;
        AlmacenaImagen almacenaImagen = new AlmacenaImagen(this.elcontexto);
        class UploadIncidencia extends AsyncTask<Void, Void, String> {

            private final RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> data = new HashMap<>();
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp = versionName + ":" + versionCode;

                /*
                Log.e(TAG_INFO,"***************************");
                Log.e(TAG_INFO,idincidencia);
                Log.e(TAG_INFO,pidRuta);
                Log.e(TAG_INFO,pidPromotor);
                Log.e(TAG_INFO,platitud);
                Log.e(TAG_INFO,plongitud);
                Log.e(TAG_INFO,idUsuario);
                Log.e(TAG_INFO,idoperacion);
                Log.e(TAG_INFO,observaciones);
                Log.e(TAG_INFO,fechahora);
                Log.e(TAG_INFO,uploadImage);
                Log.e(TAG_INFO,sVerApp);
                Log.e(TAG_INFO, String.valueOf(sinDatos));
                Log.e(TAG_INFO,idincidencia);
                Log.e(TAG_INFO,UPLOAD_INCIDENCIA);
                 */

                data.put(UPLOAD_IDINCIDENCIA, String.valueOf(idincidencia));
                data.put(UPLOAD_IDRUTA, String.valueOf(pidRuta));
                data.put(UPLOAD_IDPROMOTOR, String.valueOf(pidPromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(platitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(plongitud));
                data.put(UPLOAD_IDUSUARIO, String.valueOf(idUsuario));
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_OBSERVACIONES, observaciones.trim());
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, String.valueOf(sinDatos));

                return rh.sendPostRequest(UPLOAD_INCIDENCIA, data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // pDialog.dismiss();
                // Log.e(TAG_INFO, "Valor de resultado de inserción " + s);
                // **************************************
                if (s.equals("1")) {
                    // *****************************
                    // Borrando el registro recien colocado
                    // Se debe borrar la foto porque ya se subio
                    AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                    // Log.i(TAG_INFO, "Borrando incidencia " + idinc);
                    int i = almacenaImagen.BorraIncidencia(Integer.parseInt(idinc));
                    // *****************************
                }
                else{
                    // No se debe borrar la foto
                    // Log.i(TAG_INFO, "No se debe borrar foto " + _ifoto);
                }
                // **************************************
            }
        }

        try {
            UploadIncidencia ui = new UploadIncidencia();
            ui.execute();
        } catch (java.lang.NullPointerException e) {
        }
    }
}
