package com.topmas.top.OffLine;

import static com.topmas.top.Constants.TAG_IDOBS;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_INVFINAL;
import static com.topmas.top.Constants.TAG_INVINICIAL;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_SERVIDOR;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.topmas.top.AlmacenaImagen;
import com.topmas.top.BuildConfig;
import com.topmas.top.RequestHandler;

import java.util.HashMap;

public class PreciosCambiados {
    //***********************
    // Upload image function
    public void subirPreciosCambiados(
            Context contexto,
            String _idruta,
            String _idproducto,
            String _idpromotor,
            String _precioreal,
            String _invinicial,
            String _invfinal,
            String _idobs,
            String _id
    ) {
        class subirPreciosCambiados extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG_INFO, "Valor de retorno " + s);
                if (Integer.valueOf(s) > 0) {
                    // ******************************
                    // Borrando el registro recien colocado en el telefono solo si el resultado es > 0
                    AlmacenaImagen almacenaImagen = new AlmacenaImagen(contexto.getApplicationContext());
                    almacenaImagen.Borra_vw_producto_ruta_fecha(Integer.valueOf(_id));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String pidruta      = params[0];
                String pidproducto  = params[1];
                String pidpromotor  = params[2];
                String pprecioreal  = params[3];
                String pinvinicial  = params[4];
                String pinvfinal    = params[5];
                String pidobs       = params[6];
                String pid          = params[7];

                HashMap<String, String> data = new HashMap<>();

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                // TODO /CatalogoProductos/createProductoRutaFecha1.php
                String PRODUCTO_URL = TAG_SERVIDOR + "/CatalogoProductos/createProductoRutaFecha1_o.php";

                data.put(TAG_IDRUTA, String.valueOf(pidruta));
                data.put(TAG_IDPRODUCTO, String.valueOf(pidproducto));
                data.put(TAG_IDPROMOTOR, String.valueOf(pidpromotor));
                data.put(TAG_PRECIO, String.valueOf(pprecioreal));
                data.put(TAG_INVINICIAL, String.valueOf(pinvinicial));
                data.put(TAG_INVFINAL, String.valueOf(pinvfinal));
                data.put(TAG_IDOBS, String.valueOf(pidobs));

                return rh.sendPostRequest(PRODUCTO_URL,data);

            }
        }

        subirPreciosCambiados subirprecios = new subirPreciosCambiados();
        subirprecios.execute(_idruta, _idproducto,  _idpromotor, _precioreal, _invinicial, _invfinal, _idobs, _id);
    }
}
