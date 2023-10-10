package com.topmas.top;

import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_UPC;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class OnItemClickListenerAdaptadorProductosCanjes implements AdapterView.OnItemClickListener{

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
        //Producto.putExtra(TAG_POSICION,posicion);

        Log.e(TAG_ERROR, " *** idproducto " +idproducto);
        Log.e(TAG_ERROR, " *** idruta " +idruta);
        Log.e(TAG_ERROR, " *** upc " + upc);

        Toast.makeText(context.getApplicationContext(),  " idproducto " + idproducto, Toast.LENGTH_LONG).show();

        //AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view1 = inflater.inflate(R.layout.activity_producto_cantidad, null);
        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Datos Producto");
        alertDialog.show();

        Button cmdSalir = view1.findViewById(R.id.cmdRegresar);
        cmdSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
