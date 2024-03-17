package com.topmas.top;

import static com.topmas.top.Constants.QTY_IMAGES_TO_LOAD;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CargaFotos{
    Context contexto;
    private AlmacenaImagen almacenaImagen;
    int progressStatus =0;
    Funciones funciones = new Funciones();
    private String pName = "";
    ProgressDialog pDialog;

    private Activity activity;

    public CargaFotos(Context context, String pname, Activity activity) {
        this.pName = pname;
        this.contexto = context;
        this.activity = activity;
    }

    public void Cargafotos(){
        try {
            CargaLasFotos cargalasfotos = new CargaLasFotos();
            cargalasfotos.execute();
        }
        catch( java.lang.NullPointerException e)
        {
            // funciones.RegistraError(pName, "Competencia, uploadImageCompetencia", e, Competencia.this, getApplicationContext());
            // Log.e(TAG_ERROR, "Error al tomar la foto " + e);
            Toast.makeText(contexto, "Error al cargar una foto de competencia", Toast.LENGTH_LONG).show();
        }
    }

    public class CargaLasFotos extends AsyncTask<Void, Void, String> {
        int iMagenesGuardadas = 0;      // Imagenes guardadas
        int iPreciosCambiados = 0;      // Precios cambiados
        int iRegistrosCompetencia = 0;  // Registros competencia
        int iPromociones = 0;           // Promociones
        int iCaducidad = 0;             // Caducidades
        int iErrores = 0;               // Errores
        int iCompetenciaPromocion = 0;  // Datos de competencia promoción
        int iCanjes = 0;                // Datos de canjes

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            /**/


        }

        @Override
        protected String doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(String file_url)
        {/*
            try {
                // Sleep for 1 milliseconds.
                // Thread.sleep(1000);
            } catch (InterruptedException e) {
                funciones.RegistraError(pName, "MainActivity, CargaFotos", e, activity, contexto);
                // e.printStackTrace();
            }
            */

        }
    }
}