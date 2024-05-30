package com.topmas.top.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;

import com.topmas.top.R;
import com.topmas.top.Usuario;

public class OnItemClickListenerAdaptadorTiendasPromotor implements AdapterView.OnItemClickListener {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Context context = view.getContext();
        Usuario usr = new Usuario();

        TextView txtlatitud = (view.findViewById(R.id.txtlatitud));
        TextView txtlongitud = ( view.findViewById(R.id.txtlongitud));
        TextView txtTienda = ( view.findViewById(R.id.txtTienda));
        TextView txtDireccionCompleta = ( view.findViewById(R.id.txtDireccionCompleta));
        TextView cajatextoruta = ( view.findViewById(R.id.cajatextoruta));

        double latitud;
        // get the clicked item name
        if (txtlatitud.getText().toString().isEmpty()
                || txtlatitud.getText().toString().equals("null")
        )
        {
            latitud = 0;
        }
        else{
            latitud = Double.parseDouble( txtlatitud.getText().toString());
        }

        double longitud;
        if (txtlongitud.getText().toString().isEmpty()
                || txtlongitud.getText().toString().equals("null")){
            longitud = 0;
        }
        else{
            longitud = Double.parseDouble(txtlongitud.getText().toString());
        }

        final String tienda = txtTienda.getText().toString();
        final String DireccionCompleta = txtDireccionCompleta.getText().toString();
        final String idruta = cajatextoruta.getText().toString();

        //Toast.makeText(context, "Ruta " + idruta, LENGTH_SHORT).show();
        int pidPromotor = usr.getid();
        Intent MenuTienda = new Intent(context, com.topmas.top.MenuTienda.class);
        MenuTienda.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
        MenuTienda.putExtra(TAG_IDPROMOTOR, Integer.valueOf(pidPromotor));
        MenuTienda.putExtra(TAG_LATITUD,Double.valueOf( latitud));
        MenuTienda.putExtra(TAG_LONGITUD, Double.valueOf(longitud));
        MenuTienda.putExtra(TAG_TIENDA, tienda);
        MenuTienda.putExtra(TAG_DIRECCION, DireccionCompleta);

        context.startActivity(MenuTienda);
    }
}
