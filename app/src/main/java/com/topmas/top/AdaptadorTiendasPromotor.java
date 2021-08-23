package com.topmas.top;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorTiendasPromotor extends BaseAdapter {
    public int[] ArrRutas;
    public String[] ArrDeter;
    public String[] ArrTienda;
    public String[] ArrDirComp;
    public Double[] ArrLati;
    public Double[] ArrLong;

    public Activity context;

    private int[] colors = new int[]
    {
        Color.parseColor("#A9A8A9"),
        Color.parseColor("#ffffff")
    };

    public AdaptadorTiendasPromotor(Activity context,
                                     int[] arutas,
                                     String[] adeterminantes,
                                     String[] atiendas,
                                     String[] adireccioncompleta,
                                     Double[] alatitud,
                                     Double[] alongitud) {
        this.context = context;
        this.ArrRutas= arutas;
        this.ArrDeter= adeterminantes;
        this.ArrTienda = atiendas;
        this.ArrDirComp = adireccioncompleta;
        this.ArrLati = alatitud;
        this.ArrLong = alongitud;
    }

    @Override
    public int getCount() {
        return ArrRutas.length;
    }
    @Override
    public Object getItem(int position){return  null;}
    public long getItemId(int position) { return 0;}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.lista_tiendas, parent, false);

        TextView txtDete = (TextView) view.findViewById(R.id.txtDeterminante);
        TextView txtTie = (TextView) view.findViewById(R.id.txtTienda);
        TextView txtDir = (TextView) view.findViewById(R.id.txtDireccionCompleta);
        TextView txtLat = (TextView) view.findViewById(R.id.txtlatitud);
        TextView txtLong = (TextView) view.findViewById(R.id.txtlongitud);
        TextView cajatextoruta = (TextView) view.findViewById(R.id.cajatextoruta);

        /*
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
         */

        if (ArrDeter[position]!="") {
            txtDete.setText(ArrDeter[position]);
            txtDete.setVisibility(View.GONE);
            txtLat.setText(String.valueOf(ArrLati[position]));
            txtLat.setVisibility(View.GONE);
            txtLong.setText(String.valueOf(ArrLong[position]));
            txtLong.setVisibility(View.GONE);
            cajatextoruta.setText(String.valueOf(ArrRutas[position]));
            cajatextoruta.setVisibility(View.GONE);

            txtTie.setText(ArrTienda[position]);
            txtDir.setText(ArrDirComp[position]);

            return  view;
        }
        else {
            return null;
        }
    }
}
