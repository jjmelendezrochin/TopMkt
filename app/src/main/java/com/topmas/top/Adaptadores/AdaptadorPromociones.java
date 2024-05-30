package com.topmas.top.Adaptadores;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.topmas.top.Funciones;
import com.topmas.top.R;

public class AdaptadorPromociones extends BaseAdapter{
    private int[] _ArridPromociones;
    private String[] _ArrPromocion;
    private String[] _ArrDatosPromocion;
    private String[] _ArrUrls;
    private int[] _ArridRutas;
    private int[] _ArridPromotores;
    private Bitmap[] _ArrImagenesPromo;

    private ImageView imagen= null;
    private Bitmap bitmap1 = null;

    private Activity context;

    private final int[] colors = new int[]
    {
            Color.parseColor("#A9A8A9"),
            Color.parseColor("#ffffff")
    };

    public AdaptadorPromociones (
            Activity context,
            int[] aidPromociones,
            int[] aidRutas,
            int[] aidPromotores,
            String[] aPromocion,
            String[] aDatosPromocion,
            Bitmap[] aimagenespromo,
            String[] aUrl
    ) {
        this.context = context;
        this._ArridPromociones= aidPromociones;
        this._ArrPromocion= aPromocion;
        this._ArrDatosPromocion = aDatosPromocion;
        this._ArridRutas= aidRutas;
        this._ArridPromotores = aidPromotores;
        this._ArrImagenesPromo = aimagenespromo;
        this._ArrUrls = aUrl;
    }

    @Override
    public int getCount() {
        return _ArridPromociones.length;
    }
    @Override
    public Object getItem(int position){return  null;}
    public long getItemId(int position) { return 0;}

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Funciones funciones = new Funciones();
        LayoutInflater inflater = context.getLayoutInflater();
        View view;
        view = inflater.inflate(R.layout.lista_promociones, parent, false);

        imagen = view.findViewById(R.id.imagenproducto);
        TextView txtPromocion = (TextView) view.findViewById(R.id.txtPromocion);
        TextView txtDatosPromocion = (TextView) view.findViewById(R.id.txtDatosPromocion);
        TextView txtcajaidpromocion = (TextView) view.findViewById(R.id.cajaidpromocion);
        TextView txtcajaidruta = (TextView) view.findViewById(R.id.cajaidruta);
        TextView txtcajaidpromotor = (TextView) view.findViewById(R.id.cajaidpromotor);
        TextView txtcajaurl = (TextView) view.findViewById(R.id.cajaurl);

/*        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);*/

        if (!_ArrPromocion[position].equals("") && _ArrPromocion[position] != null) {
            txtcajaidpromocion.setText(String.valueOf(_ArridPromociones[position]));
            txtcajaidpromocion.setVisibility(View.VISIBLE);
            txtPromocion.setText(_ArrPromocion[position]);
            txtPromocion.setVisibility(View.VISIBLE);
            txtDatosPromocion.setText(String.valueOf(_ArrDatosPromocion[position]));
            txtDatosPromocion.setVisibility(View.VISIBLE);
            txtcajaidruta.setText(String.valueOf(_ArridRutas[position]));
            txtcajaidruta.setVisibility(View.VISIBLE);
            txtcajaidpromotor.setText(String.valueOf(_ArridPromotores[position]));
            txtcajaidpromotor.setVisibility(View.VISIBLE);
            txtcajaurl.setText(String.valueOf(_ArrUrls[position]));
            txtcajaurl.setVisibility(View.GONE);

            if(funciones.RevisarConexion(view.getContext())) {
                imagen.setImageBitmap(_ArrImagenesPromo[position]);
            }
            //else{
            //    imagen.setImageResource(R.drawable.a1_7503006577000);
            //}

            return  view;
        }
        else {
            return null;
        }

    }



}


