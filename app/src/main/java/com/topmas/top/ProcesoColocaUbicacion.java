package com.topmas.top;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static com.topmas.top.Constants.TAG_SERVIDOR;


public class ProcesoColocaUbicacion extends AsyncTask<Void, Void, String>
{
    private String data = "";
    private String Error = null;
    private Double sLatitud = 0.0;
    private Double sLongitud = 0.0;
    private int idPromotor = 0;
    private String sRuta = "";
    private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            sRuta = TAG_SERVIDOR + "/Ubicacion/ubicacion1.php?" +
                    "idpromotor=" + idPromotor
                    + "&latitud=" + sLatitud
                    + "&longitud=" + sLongitud;
            // Log.e("Ruta", sRuta);

            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Consultando en el servicio Web ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // Defined URL where to send data
                URL url = new URL(sRuta);
                // Log.e(TAG_ERROR, "1");
                // Send POST data request
                URLConnection conn = url.openConnection();

                // Log.e(TAG_ERROR, "2");
                conn.setDoOutput(true);
                // Log.e(TAG_ERROR, "2A");
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                // Log.e(TAG_ERROR, "2B");
                wr.write(data);
                // Log.e(TAG_ERROR, "2C");
                wr.flush();

                // Log.e(TAG_ERROR, "3");
                // Get the server response
                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line ;


                // Log.e(TAG_ERROR, "4");
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line + " ");
                }

                // Log.e(TAG_ERROR, "5");
                // Append Server Response To Content String
                String sRespuesta = sb.toString();
                // Log.e(TAG_ERROR, sRespuesta);


                // Log.e(TAG_ERROR, "6");
            } catch (Exception ex) {
                Error = ex.getMessage();
                //Toast.makeText(this.context, ERROR_FOTO + " Error en el proceso de lectura de datos " +  Resultado,Toast.LENGTH_LONG).show();
                // Log.e("Ruta", Error);
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    Error = ex.getMessage();
                    // Log.e("Ruta", Error);
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {
            //pDialog.dismiss();
        }

    public void setsLatitud(Double sLatitud) {
        this.sLatitud = sLatitud;
    }

    public void setsLongitud(Double sLongitud) {
        this.sLongitud = sLongitud;
    }

    public void setIdPromotor(int idPromotor) {
        this.idPromotor = idPromotor;
    }
}
