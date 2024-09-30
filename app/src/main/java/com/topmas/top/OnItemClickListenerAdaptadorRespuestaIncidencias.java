package com.topmas.top;

import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_FECHAHORA;
import static com.topmas.top.Constants.TAG_FECHAHORARESPUESTA;
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
        Usuario usr = new Usuario();
        TextView txttienda              = (view.findViewById(R.id.txttienda));
        TextView txtdireccion           = (view.findViewById(R.id.txtdireccion));
        TextView txtidruta              = (view.findViewById(R.id.idruta));
        TextView txtfecha               = (view.findViewById(R.id.txtFecha));
        TextView txtfechahora_respuesta = (view.findViewById(R.id.txtfechahora_respuesta));
        TextView txtrespuesta           = (view.findViewById(R.id.txtrespuesta));
        TextView txtObservaciones       = (view.findViewById(R.id.txtObservaciones));
        TextView txtidinc               = (view.findViewById(R.id.idinc));

        final String tienda = txttienda.getText().toString();
        final String DireccionCompleta = txtdireccion.getText().toString();
        final String idruta = txtidruta.getText().toString();
        final String fecha = txtfecha.getText().toString();
        final String fechahora_respuesta = txtfechahora_respuesta.getText().toString();
        final String respuesta = txtrespuesta.getText().toString();
        final String observaciones = txtObservaciones.getText().toString();
        final String idinc          = txtidinc.getText().toString();

        int pidPromotor = usr.getid();
        Intent RespuestaIncidencias = new Intent(context, RespuestaIncidencia.class);
        RespuestaIncidencias.putExtra(TAG_IDINC, Integer.valueOf(String.valueOf(idinc)));
        RespuestaIncidencias.putExtra(TAG_IDRUTA, Integer.valueOf(String.valueOf(idruta)));
        RespuestaIncidencias.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
        RespuestaIncidencias.putExtra(TAG_LATITUD,Double.valueOf( 0));
        RespuestaIncidencias.putExtra(TAG_LONGITUD, Double.valueOf(0));
        RespuestaIncidencias.putExtra(TAG_TIENDA, tienda);
        RespuestaIncidencias.putExtra(TAG_DIRECCION, DireccionCompleta);
        RespuestaIncidencias.putExtra(TAG_FECHAHORA, fecha);
        RespuestaIncidencias.putExtra(TAG_OBSERVACIONES, observaciones);
        RespuestaIncidencias.putExtra(TAG_FECHAHORARESPUESTA, fechahora_respuesta);
        RespuestaIncidencias.putExtra(TAG_RESPUESTA, respuesta);

        context.startActivity(RespuestaIncidencias);
    }
}
