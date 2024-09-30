package com.topmas.top;

import static com.topmas.top.Constants.TAG_USUARIO;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdaptadorRespuestasIncidencias extends BaseAdapter {
    private final int[] aidinc;
    private final int[] aleida;
    private final String[] afechasdeincidencias;
    private final String[] atiposdeincidencias;
    private final String[] aobservacionesincidencias;

    private final Activity context;

    String pName = "";

    public AdaptadorRespuestasIncidencias(
            Activity context,
            int[] idinc2,
            int[] leida2,
            String[] fechasdeincidencias2,
            String[] tiposdeincidencias2,
            String[] observacionesincidencias2
    ) {
        this.context = context;
        this.aidinc = idinc2;
        this.aleida = leida2;
        this.afechasdeincidencias= fechasdeincidencias2;
        this.atiposdeincidencias= tiposdeincidencias2;
        this.aobservacionesincidencias = observacionesincidencias2;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        pName = preferences.getString(TAG_USUARIO, pName);
    }

    @Override
    public int getCount() {
        return aidinc.length;
    }
    @Override
    public Object getItem(int position){return  null;}
    public long getItemId(int position) { return 0;}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AlmacenaImagen almacenaImagen =
                new AlmacenaImagen(context);

        String sRutaImagen = null;
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.lista_respuestasincidencias, parent, false);

        ImageView imagen                = view.findViewById(R.id.imagenincidencia);
        TextView txtIdinc               = view.findViewById(R.id.idinc);
        TextView txtFecha               = view.findViewById(R.id.txtFecha);
        TextView txtIncidencia          = view.findViewById(R.id.txtIncidencia);
        TextView txtObservaciones       = view.findViewById(R.id.txtObservaciones);

        try {
            if (!afechasdeincidencias[position].equals("") &&
                    afechasdeincidencias[position] != null ) {

                // *************************************************
                txtIdinc.setText(String.valueOf(aidinc[position]));
                txtIdinc.setVisibility(View.GONE);
                String sTexto =  "[" + String.valueOf(aidinc[position]) + "] " + afechasdeincidencias[position];
                txtFecha.setText(sTexto);
                txtFecha.setVisibility(View.VISIBLE);
                txtIncidencia.setText(String.valueOf(atiposdeincidencias[position]));
                txtIncidencia.setVisibility(View.VISIBLE);
                txtObservaciones.setText(aobservacionesincidencias[position]);
                txtObservaciones.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        100,  // Width
                        100   // Height
                );
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imagen.getLayoutParams());
                lp.setMargins(500, 500, 0, 0);
                imagen.setLayoutParams(lp);
                if (aleida[position]==1) {
                    imagen.setBackgroundResource(R.drawable.id_check);
                }
                else{
                    imagen.setBackgroundResource(R.drawable.respuesta_ticket);
                }
                imagen.setLayoutParams(layoutParams);
                return view;
            } else {
                return null;
            }
        } catch (Exception e) {
            String Resultado = "Se generó el siguiente error : " + e.toString();
        }
        return view;
    }
}
