package com.topmas.top;

import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_FECHAHORA;
import static com.topmas.top.Constants.TAG_FECHAHORARESPUESTA;
import static com.topmas.top.Constants.TAG_FUENTE;
import static com.topmas.top.Constants.TAG_IDINC;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_OBSERVACIONES;
import static com.topmas.top.Constants.TAG_RESPUESTA;
import static com.topmas.top.Constants.TAG_TIENDA;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class OnItemClickListenerAdaptadorRespuestaIncidencias implements AdapterView.OnItemClickListener {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Context context = view.getContext();
        TextView txtidinc               = (view.findViewById(R.id.idinc));
        final String idinc          = txtidinc.getText().toString();

        Intent RespuestaIncidencias = new Intent(context, RespuestaIncidencia.class);
        RespuestaIncidencias.putExtra(TAG_IDINC, Integer.valueOf(String.valueOf(idinc)));
        RespuestaIncidencias.putExtra(TAG_FUENTE, "LISTADO TIENDAS");
        context.startActivity(RespuestaIncidencias);
    }
}
