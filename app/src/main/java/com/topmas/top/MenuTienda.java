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

        // Log.e(TAG_ERROR,  " ** idruta " + idruta);
        // Log.e(TAG_ERROR,  " ** pidpromotor " + pidpromotor);
        // Log.e(TAG_ERROR,  " ** tienda " + tienda);
        // Log.e(TAG_ERROR,  " ** idformato " + idformato);


        TextView txtTituloTienda = findViewById(R.id.TituloMenu);
        txtTituloTienda.setText(tienda.toUpperCase());

        //****************************
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
        // Check in
        ImageView imgcheckin = findViewById(R.id.imgcheckin);

        imgcheckin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(),Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
                Foto.putExtra(TAG_OPERACION, 1);    // Checkin
                startActivity(Foto);
            }
        });

        //****************************
        // Check out
        ImageView imgcheckout = findViewById(R.id.checkout);

        imgcheckout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(),Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
                Foto.putExtra(TAG_OPERACION, 2);    // Checkin
                startActivity(Foto);
            }
        });

        // ****************************
        // Inventario Entrada
        ImageView imganaquelin = findViewById(R.id.imganaquelin);

        imganaquelin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(), com.topmas.top.Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));   // Inventario Entrada
                Foto.putExtra(TAG_OPERACION, 3);
                startActivity(Foto);
            }
        });


       // ****************************
        // Inventario Salida
        ImageView imagnaquelout = findViewById(R.id.imagnaquelout);

        imagnaquelout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent Foto = new Intent(getApplicationContext(), com.topmas.top.Foto.class);
                Foto.putExtra(TAG_IDRUTA, Integer.valueOf(idruta));
                Foto.putExtra(TAG_OPERACION, 4);    // Inventario Salida
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
        // Reporte Inventario
        ImageView imgproductomenu = findViewById(R.id.imgproductomenu);
        imgproductomenu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                // Log.e(TAG_ERROR,  " ** idruta " + idruta);
                // Log.e(TAG_ERROR,  " ** pidpromotor " + pidpromotor);
                // Log.e(TAG_ERROR,  " ** tienda " + tienda);

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
        // Reporte Resurtido
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
