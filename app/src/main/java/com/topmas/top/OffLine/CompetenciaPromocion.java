package com.topmas.top.OffLine;

import static com.topmas.top.Competencia_Promocion.UPLOAD_COMENTARIOS;
import static com.topmas.top.Competencia_Promocion.UPLOAD_CON_SIN_PARTICIPACION;
import static com.topmas.top.Competencia_Promocion.UPLOAD_IDPRODUCTO;
import static com.topmas.top.Competencia_Promocion.UPLOAD_NO_FRENTES;
import static com.topmas.top.Competencia_Promocion.UPLOAD_POR_DESCUENTO;
import static com.topmas.top.Competencia_Promocion.UPLOAD_POR_PARTICIPACION;
import static com.topmas.top.Competencia_Promocion.UPLOAD_PRECIO;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_IMAGEN1;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_VERSION;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.RequestHandler;

import java.util.HashMap;

public class CompetenciaPromocion {
    String UPLOAD_COMPETENCIA_PROMOCION_O = TAG_SERVIDOR + "/PhotoUpload/upload_competencia_promocion_o.php";
    //***********************
    // Función utilizada para guardar competencia promoción
    public void cargaCompetenciaPromocion(
            Context contexto,
            String _idpromotor,
            String _pLatitud,
            String _pLongitud,
            String _pName,
            String _idoperacion,
            String _idRuta,
            String _fechahora,
            String _uploadImage1,
            String _uploadImage2,
            String _iconPromo,
            String _por_participa,
            String _no_frentes,
            String _por_descuento,
            String _comentario,
            String _idproducto,
            String _precio,
            String _sVerApp,
            String _idfoto1,
            String _idfoto2,
            String _idCompetenciaPromo
    )
    {
        class EstableceCompetenciaPromocion extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                // Log.i(TAG_INFO, "Resultado " + s );
                // **************************************
                // Si se pudo cargar la foto entonces debe de borrar la foto almacenada
                if (s == ""){
                    Log.e(TAG_INFO, "Sin valor de retorno");
                }
                else {
                    if (s.equals("1")) {
                        almacenaImagen.BorraFotoEnviada(Integer.valueOf(_idfoto1), Integer.valueOf(_idfoto2));
                        almacenaImagen.Borrar_Competencia_Promocion(Integer.valueOf(_idCompetenciaPromo));
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromotor   = params[0];
                String pLatitud     = params[1];
                String pLongitud    = params[2];
                String idUsuario    = params[3];
                String idoperacion  = params[4];
                String idRuta       = params[5];
                String fechahora    = params[6];
                String uploadImage1 = params[7];
                String uploadImage2 = params[8];
                String iconPromo    = params[9];
                String por_participa= params[10];
                String no_frentes   = params[11];
                String por_descuento  = params[12];
                String comentario   = params[13];
                String idproducto   = params[14];
                String precio       = params[15];
                String sVerApp      = params[16];
                String iFoto1       = params[17];
                String iFoto2       = params[18];
                String idCompetenciaPromo      = params[19];

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contexto.getApplicationContext());
                idUsuario = preferences.getString(TAG_USUARIO, idUsuario);

                /*
                Log.e(TAG_ERROR, "*************************");
                Log.e(TAG_ERROR, String.valueOf(idpromotor));
                Log.e(TAG_ERROR, String.valueOf(pLatitud));
                Log.e(TAG_ERROR, String.valueOf(pLongitud));
                Log.e(TAG_ERROR, idUsuario);
                Log.e(TAG_ERROR, String.valueOf(idoperacion));
                Log.e(TAG_ERROR, String.valueOf(idRuta));
                Log.e(TAG_ERROR, fechahora);
                Log.e(TAG_ERROR, uploadImage1);
                Log.e(TAG_ERROR, uploadImage2);
                Log.e(TAG_ERROR, String.valueOf(iconPromo));
                Log.e(TAG_ERROR, String.valueOf(por_participa));
                Log.e(TAG_ERROR, String.valueOf(no_frentes));
                Log.e(TAG_ERROR, String.valueOf(por_descuento));
                Log.e(TAG_ERROR, String.valueOf(comentario));
                Log.e(TAG_ERROR, String.valueOf(idproducto));
                Log.e(TAG_ERROR, String.valueOf(precio));
                Log.e(TAG_ERROR, sVerApp);
                Log.e(TAG_ERROR, "1");
                Log.e(TAG_ERROR, "*************************");

                 */

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);

                data.put(UPLOAD_CON_SIN_PARTICIPACION, String.valueOf(iconPromo));
                data.put(UPLOAD_POR_PARTICIPACION, String.valueOf(por_participa));
                data.put(UPLOAD_NO_FRENTES, String.valueOf(no_frentes));
                data.put(UPLOAD_POR_DESCUENTO, String.valueOf(por_descuento));
                data.put(UPLOAD_COMENTARIOS, String.valueOf(comentario));
                data.put(UPLOAD_IDPRODUCTO, String.valueOf(idproducto));
                data.put(UPLOAD_PRECIO, String.valueOf(precio));

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "1");

                /*
                // *******************************************
                String TAG_ERROR = "ERROR";
                Log.e(TAG_ERROR, "idpromotor "  + String.valueOf(idpromotor));
                Log.e(TAG_ERROR, "platitud "  + String.valueOf(pLatitud));
                Log.e(TAG_ERROR, "plongitud "  + String.valueOf(pLongitud));
                Log.e(TAG_ERROR, "idUsuario "  + idUsuario);
                Log.e(TAG_ERROR, "idoperacion "  + String.valueOf(idoperacion));
                Log.e(TAG_ERROR, "idRuta "  + String.valueOf(idRuta));
                Log.e(TAG_ERROR, "fechahora "  + fechahora);
                Log.e(TAG_ERROR, "uploadImage1 "  + uploadImage1);
                Log.e(TAG_ERROR, "uploadImage2 "  + uploadImage2);
                Log.e(TAG_ERROR, "iconPromo "  + String.valueOf(iconPromo));
                Log.e(TAG_ERROR, "por_participa "  + String.valueOf(por_participa));
                Log.e(TAG_ERROR, "no_frentes "  + String.valueOf(no_frentes));
                Log.e(TAG_ERROR, "comentario "  + String.valueOf(comentario));
                Log.e(TAG_ERROR, "idproducto "  + String.valueOf(idproducto));
                Log.e(TAG_ERROR, "precio "  + String.valueOf(precio));
                Log.e(TAG_ERROR, "sVerApp "  + sVerApp);
                Log.e(TAG_ERROR, "UPLOAD_SINDATOS "  + "1");
                Log.e(TAG_ERROR, "UPLOAD_COMPETENCIA_PROMOCION "  + UPLOAD_COMPETENCIA_PROMOCION_O);
                // *******************************************
                */

                // Log.e(TAG_ERROR, "Enviando datos");
                return rh.sendPostRequest(UPLOAD_COMPETENCIA_PROMOCION_O,data);
            }
        }

        // *******************************************
        // Log.e(TAG_INFO, "Proceso de envio fuera de linea");
        EstableceCompetenciaPromocion ui = new EstableceCompetenciaPromocion();
        ui.execute(_idpromotor, _pLatitud, _pLongitud, _pName, _idoperacion, _idRuta,_fechahora,
                _uploadImage1, _uploadImage2, _iconPromo, _por_participa, _no_frentes, _por_descuento,
                _comentario, _idproducto, _precio, _sVerApp,
                _idfoto1, _idfoto2, _idCompetenciaPromo);
    }
}
