package com.topmas.top;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDPROMOCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_url;

public class OnItemClickListenerAdaptadorPromociones implements AdapterView.OnItemClickListener{
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Context context = view.getContext();

        TextView cajaidpromocion = ( view.findViewById(R.id.cajaidpromocion));
        TextView cajaidpromotor = ( view.findViewById(R.id.cajaidpromotor));
        TextView cajaidruta = ( view.findViewById(R.id.cajaidruta));
        TextView cajaurl = ( view.findViewById(R.id.cajaurl));

        // get the clicked item name
        final String idpromocion = cajaidpromocion.getText().toString();
        final String idpromotor = cajaidpromotor.getText().toString();
        final String idruta = cajaidruta.getText().toString();
        final String url = cajaurl.getText().toString();

        Intent Promocion = new Intent(context, Promocion.class);
        Promocion.putExtra(TAG_IDPROMOCION, Integer.parseInt(idpromocion));
        Promocion.putExtra(TAG_IDRUTA, Integer.parseInt(idruta));
        Promocion.putExtra(TAG_IDPROMOTOR, Integer.parseInt(idpromotor));
        Promocion.putExtra(TAG_url, url);

        context.startActivity(Promocion);
    }
}
