package com.topmas.top.OffLine;

import static com.topmas.top.Constants.TAG_APLICA;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDPROMOCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Promocion.PROMOCION_URL;

import android.content.Context;
import android.os.AsyncTask;

import com.topmas.top.AlmacenaImagen;
import com.topmas.top.RequestHandler;

import java.util.HashMap;

public class Promocion {
    //***********************
    // Función utilizada para guardar la promoción
    String PROMOCION_URL_O = TAG_SERVIDOR + "/Promociones/insertapromocionfecha_o.php";
    public void cargaPromocion(
            Context contexto,
            String _idpromocion,
            String _idpromotor,
            String _idempresa,
            String _idruta,
            String _idaplica,
            String _idpro
    )
    {
        class EstablecePromocion extends AsyncTask<String, Void, String> {

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
                    almacenaImagen.Borra_Promociones_Tiendas(Integer.parseInt(_idpromocion));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromocion = params[0];
                String idpromotor = params[1];
                String idempresa = params[2];
                String idruta = params[3];
                String idaplica= params[4];

                data.put(TAG_IDPROMOCION, String.valueOf(idpromocion));
                data.put(TAG_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(TAG_IDEMPRESA, String.valueOf(idempresa));
                data.put(TAG_IDRUTA, String.valueOf(idruta));
                data.put(TAG_APLICA, String.valueOf(idaplica));

                return rh.sendPostRequest(PROMOCION_URL_O, data);
            }
        }

        EstablecePromocion ui = new EstablecePromocion();
        ui.execute(_idpromocion, _idpromotor, _idempresa, _idruta, _idaplica, _idpro);
    }
}
