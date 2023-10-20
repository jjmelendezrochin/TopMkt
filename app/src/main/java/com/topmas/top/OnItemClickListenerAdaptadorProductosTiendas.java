package com.topmas.top;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_POSICION;
import static com.topmas.top.Constants.TAG_UPC;
import static com.topmas.top.Constants.TAG_imagen;

public class OnItemClickListenerAdaptadorProductosTiendas implements AdapterView.OnItemClickListener {



    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Context context = view.getContext();

        TextView cajaruta = ( view.findViewById(R.id.idruta));
        TextView cajaproducto = ( view.findViewById(R.id.idproducto));
        TextView cajaupc = (view.findViewById(R.id.upc_producto));
        //TextView cajaposiciones = (view.findViewById(R.id.posiciones));

        final String idruta = cajaruta.getText().toString();
        final String idproducto = cajaproducto.getText().toString();
        final String upc = cajaupc.getText().toString();
        //final String posicion = cajaposiciones.getText().toString();

        Intent Producto = new Intent(context, Producto.class);
        Producto.putExtra(TAG_IDPRODUCTO, Integer.parseInt(idproducto));
        Producto.putExtra(TAG_IDRUTA, Integer.parseInt(idruta));
        Producto.putExtra(TAG_UPC,upc);

        // Producto.putExtra(TAG_POSICION,posicion);
        // Log.e(TAG_ERROR, " *** idproducto " +idproducto);
        // Log.e(TAG_ERROR, " *** idruta " +idruta);
        // Log.e(TAG_ERROR, " *** upc " + upc);
        // Log.e(TAG_ERROR, " *** posicion " + posicion);
        context.startActivity(Producto);
    }
}
