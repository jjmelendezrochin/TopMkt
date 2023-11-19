package com.topmas.top;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.topmas.top.Constants.TAG_ACCESSTOKEN;
import static com.topmas.top.Constants.TAG_CONSULTAENWEB;
import static com.topmas.top.Constants.TAG_DIRECCION;
import static com.topmas.top.Constants.TAG_EMAIL;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_EXPIRESIN;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDFORMATO;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_NAME;
import static com.topmas.top.Constants.TAG_OPERACION;
import static com.topmas.top.Constants.TAG_TIENDA;

public class MenuTienda extends AppCompatActivity
{
    int idruta = 0;
    int pidpromotor = 0;
    Double latitud = 0.0;
    Double longitud = 0.0;
    String tienda = "";
    String direccion = "";
    int idformato = 0;
    AlmacenaImagen almacenaImagen;
    private Funciones funciones = new Funciones();
    private final Usuario usr = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tienda);

        Intent i = getIntent();
        idruta = i.getIntExtra(TAG_IDRUTA,0 );
        pidpromotor = i.getIntExtra(TAG_IDPROMOTOR,0 );
        latitud = i.getDoubleExtra(TAG_LATITUD, 0.0);
        longitud = i.getDoubleExtra(TAG_LONGITUD, 0.0);
        tienda = i.getStringExtra(TAG_TIENDA);
        direccion = i.getStringExtra(TAG_DIRECCION);
        almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
        idformato = almacenaImagen.ObtenFormato(idruta);
        TextView txtTipoconexion = findViewById(R.id.formaConexion);

        if (pidpromotor == 0){
            pidpromotor = usr.getid();
        }
        //****************************
        // Obteniendo la empresa desde la que se firmo el usuario
        String pidEmpresa =  usr.getidempresa();

        //****************************
        // Revisa el tipo de conexión
        if (funciones.RevisarConexion(getApplicationContext())) {
            // Si esta conectado, verificar el tipo de conexion
            if (!funciones.RevisarTipoConexion(getApplicationContext())) {
                txtTipoconexion.setText("Se detectó señal Wifi, tus imágenes podrian no subirse correctamente");
            }
            else{
                txtTipoconexion.setText("");
            }
        }
        else{
            txtTipoconexion.setText("");
        }

        TextView txtTituloTienda = findViewById(R.id.TituloMenu);
        txtTituloTienda.setText(tienda.toUpperCase());

        //****************************
        // Lista de tiendas
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listatiendas = new Intent(getApplicationContext(), listatiendas.class);

                listatiendas.putExtra(TAG_IDPROMOTOR, pidpromotor);
                listatiendas.putExtra(TAG_NAME, "");
                listatiendas.putExtra(TAG_EMAIL, "");
                listatiendas.putExtra(TAG_ACCESSTOKEN, "");
                listatiendas.putExtra(TAG_EXPIRESIN, "");
                listatiendas.putExtra(TAG_CONSULTAENWEB, 0);    // Indica que ya no debe buscar en la lista de tiendas en web sino consulta de tiendas en el teléfono

                startActivity(listatiendas);
            }
        });

        //****************************
        // Imagen Mapa
        ImageView imgUbicacion = findViewById(R.id.imgUbicacion);
        imgUbicacion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                makeText(getApplicationContext(), String.valueOf(direccion), LENGTH_LONG).show();

                Intent MapaTienda = new Intent(getApplicationContext(), MapaTienda.class);
                MapaTienda.putExtra(TAG_LATITUD, latitud);
                MapaTienda.putExtra(TAG_LONGITUD, longitud);
                MapaTienda.putExtra(TAG_TIENDA, tienda);
                startActivity(MapaTienda);
            }
        });

        //****************************
        // Botones
        ImageView imgcheckin = findViewById(R.id.imgcheckin);
        ImageView imgcheckout = findViewById(R.id.checkout);
        ImageView imganaquelin = findViewById(R.id.imganaquelin);
        ImageView imagnaquelout = findViewById(R.id.imagnaquelout);
        ImageView imgCanjes = findViewById(R.id.imgCanjes);

        // *****************************
        // Verifica si tiene un servicio GPS fake
        boolean bResp = funciones.areThereMockPermissionApps(this.getApplicationContext());
        String sResultado =  "Se esta utilizando una aplicación no permitida en su teléfono, favor de contactar al área de sistemas para mayor información, esta información se va a grabar en la bitácora para seguimiento";
        /*
        if(bResp){
            AlmacenaImagen almacenaImagen = new AlmacenaImagen(this.getApplicationContext());
            String sMotivo = "Fake GPS";
            Exception exp = new Exception(sMotivo,null);
            almacenaImagen.inserta_error1(usr.getnombre() , exp, sMotivo);
            Toast.makeText(getApplicationContext(), sResultado , Toast.LENGTH_LONG).show();
            imgcheckin.setVisibility(View.GONE);
            imgcheckout.setVisibility(View.GONE);
            imganaquelin.setVisibility(View.GONE);
            imagnaquelout.setVisibility(View.GONE);
            return;
        }
        else{
            imgcheckin.setVisibility(View.VISIBLE);
            imgcheckout.setVisibility(View.VISIBLE);
            imganaquelin.setVisibility(View.VISIBLE);
            imagnaquelout.setVisibility(View.VISIBLE);
        }
        */

        imgcheckin.setVisibility(View.VISIBLE);
        imgcheckout.setVisibility(View.VISIBLE);
        imganaquelin.setVisibility(View.VISIBLE);
        imagnaquelout.setVisibility(View.VISIBLE);

        // Log.e(TAG_INFO, "La empresa es " + pidEmpresa);
        // Canjes solo visibles para Santa Clara
        if (pidEmpresa.equals("2")){
            imgCanjes.setVisibility(View.VISIBLE);
            //Log.e(TAG_INFO, "Visible" );
        }
        else{
            imgCanjes.setVisibility(View.GONE);
            //Log.e(TAG_INFO, "Oculta" );
        }

        //****************************
        // Check in
        imgcheckin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(),Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
                Foto.putExtra(TAG_OPERACION, 1);    // Checkin
                Foto.putExtra(TAG_IDPROMOTOR, pidpromotor); // idpromotor
                startActivity(Foto);
            }
        });

        //****************************
        // Check out
        imgcheckout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(),Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
                Foto.putExtra(TAG_OPERACION, 2);    // Checkin
                Foto.putExtra(TAG_IDPROMOTOR, pidpromotor); // idpromotor
                startActivity(Foto);
            }
        });

        // ****************************
        // Inventario Entrada
        imganaquelin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(), com.topmas.top.Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));   // Inventario Entrada
                Foto.putExtra(TAG_OPERACION, 3);
                Foto.putExtra(TAG_IDPROMOTOR, pidpromotor); // idpromotor
                startActivity(Foto);
            }
        });

        // ****************************
        // Inventario Salida
        imagnaquelout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(), com.topmas.top.Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
                Foto.putExtra(TAG_OPERACION, 4);    // Inventario Salida
                Foto.putExtra(TAG_IDPROMOTOR, pidpromotor); // idpromotor
                startActivity(Foto);
            }
        });

        // ****************************
        // Promociones usando el botón de imgevidenciaexhibicion
        ImageView imgevidenciaexhibicion = findViewById(R.id.imgevidenciaexhibicion);
        imgevidenciaexhibicion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent ListaPromociones = new Intent(getApplicationContext(), listapromociones.class);
                ListaPromociones.putExtra(TAG_IDRUTA, idruta);
                ListaPromociones.putExtra(TAG_IDPROMOTOR, pidpromotor);
                ListaPromociones.putExtra(TAG_TIENDA, tienda);
                ListaPromociones.putExtra(TAG_LATITUD, latitud);
                ListaPromociones.putExtra(TAG_LONGITUD, longitud);
                ListaPromociones.putExtra(TAG_DIRECCION, direccion);
                ListaPromociones.putExtra(TAG_IDFORMATO, idformato);

                startActivity(ListaPromociones);
            }
        });

        //****************************
        // Imagen Lista de productos
        ImageView imgproductomenu = findViewById(R.id.imgproductomenu);
        imgproductomenu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent ListaProductos = new Intent(getApplicationContext(), listaproductos.class);
                ListaProductos.putExtra(TAG_IDRUTA, idruta);
                ListaProductos.putExtra(TAG_IDPROMOTOR, pidpromotor);
                ListaProductos.putExtra(TAG_TIENDA, tienda);
                ListaProductos.putExtra(TAG_LATITUD, latitud);
                ListaProductos.putExtra(TAG_LONGITUD, longitud);
                ListaProductos.putExtra(TAG_DIRECCION, direccion);
                startActivity(ListaProductos);
            }
        });

        //****************************
        // Imagen Competencia
        ImageView imgreporteresurtido = findViewById(R.id.imgreporteresurtido);
        imgreporteresurtido.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent competencia = new Intent(getApplicationContext(), Competencia.class);
                competencia.putExtra(TAG_IDRUTA, idruta);
                startActivity(competencia);
            }
        });

        //****************************
        // Imagen Caducidad
        ImageView imgCaducidad = findViewById(R.id.imgCaducidad);
        imgCaducidad.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent caducidad = new Intent(getApplicationContext(), Caducidad.class);
                caducidad.putExtra(TAG_IDRUTA, idruta);
                startActivity(caducidad);
            }
        });

        //****************************
        // Imagen Competencia1
        ImageView imgCompetencia1 = findViewById(R.id.imgCompetencia1);
        imgCompetencia1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent competencia1 = new Intent(getApplicationContext(), Competencia_Promocion.class);
                competencia1.putExtra(TAG_IDRUTA, idruta);
                startActivity(competencia1);
            }
        });

        //****************************
        // Imagen Canjes

        imgCanjes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent canjes = new Intent(getApplicationContext(), Canjes.class);
                canjes.putExtra(TAG_IDRUTA, idruta);
                canjes.putExtra(TAG_IDPROMOTOR, pidpromotor);
                canjes.putExtra(TAG_TIENDA, tienda);
                canjes.putExtra(TAG_LATITUD, latitud);
                canjes.putExtra(TAG_LONGITUD, longitud);
                canjes.putExtra(TAG_DIRECCION, direccion);
                startActivity(canjes);
            }
        });

        //****************************
        // Muestra datos almacenados
        FloatingActionButton fab1 = findViewById(R.id.fab1);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almacenaImagen = new AlmacenaImagen(getApplicationContext());
                almacenaImagen.muestradatosAlmacenados();
            }
        });

    }
}