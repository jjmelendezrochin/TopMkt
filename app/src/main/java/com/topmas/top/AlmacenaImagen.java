package com.topmas.top;

import static androidx.core.content.ContextCompat.startActivity;
import static com.topmas.top.Caducidad.UPLOAD_CADUCIDAD;
import static com.topmas.top.Caducidad.UPLOAD_caducidad;
import static com.topmas.top.Caducidad.UPLOAD_idproducto;
import static com.topmas.top.Caducidad.UPLOAD_lote;
import static com.topmas.top.Caducidad.UPLOAD_piezas;
import static com.topmas.top.Canjes.UPLOAD_CANJES_COMPLEMENTO;
import static com.topmas.top.Competencia.UPLOAD_COMPETENCIA;
import static com.topmas.top.Competencia_Promocion.UPLOAD_COMENTARIOS;
import static com.topmas.top.Competencia_Promocion.UPLOAD_COMPETENCIA_PROMOCION;
import static com.topmas.top.Competencia_Promocion.UPLOAD_COMPETENCIA_PROMOCION_COMPLEMENTO;
import static com.topmas.top.Canjes.UPLOAD_CANJES;
import static com.topmas.top.Competencia_Promocion.UPLOAD_CON_SIN_PARTICIPACION;
import static com.topmas.top.Competencia_Promocion.UPLOAD_IDPRODUCTO;
import static com.topmas.top.Competencia_Promocion.UPLOAD_NO_FRENTES;
import static com.topmas.top.Competencia_Promocion.UPLOAD_POR_DESCUENTO;
import static com.topmas.top.Competencia_Promocion.UPLOAD_POR_PARTICIPACION;
import static com.topmas.top.Competencia_Promocion.UPLOAD_PRECIO;
import static com.topmas.top.Constants.DATABASE_NAME;
import static com.topmas.top.Constants.DATABASE_VERSION;
import static com.topmas.top.Constants.ERROR_FOTO;
import static com.topmas.top.Constants.TAG_ACTIVIDADBTL;
import static com.topmas.top.Constants.TAG_APLICA;
import static com.topmas.top.Constants.TAG_CANJES;
import static com.topmas.top.Constants.TAG_ERROR;
import static com.topmas.top.Constants.TAG_IDEMOSTRADOR;
import static com.topmas.top.Constants.TAG_IDEMPAQUE;
import static com.topmas.top.Constants.TAG_IDEMPRESA;
import static com.topmas.top.Constants.TAG_IDOBS;
import static com.topmas.top.Constants.TAG_IDPRODUCTO;
import static com.topmas.top.Constants.TAG_IDPROMOCION;
import static com.topmas.top.Constants.TAG_IDPROMOTOR;
import static com.topmas.top.Constants.TAG_IDRUTA;
import static com.topmas.top.Constants.TAG_IEMPLAYE;
import static com.topmas.top.Constants.TAG_IEXHIBIDOR;
import static com.topmas.top.Constants.TAG_INFO;
import static com.topmas.top.Constants.TAG_INVFINAL;
import static com.topmas.top.Constants.TAG_INVINICIAL;
import static com.topmas.top.Constants.TAG_PRECIO;
import static com.topmas.top.Constants.TAG_PRESENTACION;
import static com.topmas.top.Constants.TAG_SERVIDOR;
import static com.topmas.top.Constants.TAG_USUARIO;
import static com.topmas.top.Constants.TAG_producto;
import static com.topmas.top.Foto.UPLOAD_ANDROID_ID;
import static com.topmas.top.Foto.UPLOAD_API_VALUE;
import static com.topmas.top.Foto.UPLOAD_ARREGLOPRODUCTO;
import static com.topmas.top.Foto.UPLOAD_BOARD;
import static com.topmas.top.Foto.UPLOAD_BOOTLOADER;
import static com.topmas.top.Foto.UPLOAD_BUILD_ID;
import static com.topmas.top.Foto.UPLOAD_BUILD_TIME;
import static com.topmas.top.Foto.UPLOAD_DENSIDAD;
import static com.topmas.top.Foto.UPLOAD_ERROR;
import static com.topmas.top.Foto.UPLOAD_ERRORES;
import static com.topmas.top.Foto.UPLOAD_FABRICANTE;
import static com.topmas.top.Foto.UPLOAD_FECHAHORA;
import static com.topmas.top.Foto.UPLOAD_FINGERPRINT;
import static com.topmas.top.Foto.UPLOAD_HARDWARE;
import static com.topmas.top.Foto.UPLOAD_HOST_VALUE;
import static com.topmas.top.Foto.UPLOAD_IDOPERACION;
import static com.topmas.top.Foto.UPLOAD_IDPROMOTOR;
import static com.topmas.top.Foto.UPLOAD_IDRUTA;
import static com.topmas.top.Foto.UPLOAD_IDUSUARIO;
import static com.topmas.top.Foto.UPLOAD_IMAGEN;
import static com.topmas.top.Foto.UPLOAD_IMAGEN1;
import static com.topmas.top.Foto.UPLOAD_LATITUD;
import static com.topmas.top.Foto.UPLOAD_LLAVE;
import static com.topmas.top.Foto.UPLOAD_LONGITUD;
import static com.topmas.top.Foto.UPLOAD_MARCA;
import static com.topmas.top.Foto.UPLOAD_MODELO;
import static com.topmas.top.Foto.UPLOAD_RESOLUCION;
import static com.topmas.top.Foto.UPLOAD_SECCION;
import static com.topmas.top.Foto.UPLOAD_SERIE;
import static com.topmas.top.Foto.UPLOAD_SINDATOS;
import static com.topmas.top.Foto.UPLOAD_TAMANIOPANTALLA;
import static com.topmas.top.Foto.UPLOAD_UID;
import static com.topmas.top.Foto.UPLOAD_URL;
import static com.topmas.top.Foto.UPLOAD_USER_VALUE;
import static com.topmas.top.Foto.UPLOAD_USUARIO;
import static com.topmas.top.Foto.UPLOAD_VERSION;
import static com.topmas.top.Promocion.PROMOCION_URL;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess") // Suprime error ACCESS CAN BE PRIVATE
// TODO Aqui se encuentran todas las funciones de acceso a la base de datos Sqlite

public class AlmacenaImagen extends SQLiteOpenHelper {
    Context contexto;
    public String databasePath;
    Funciones funciones = new Funciones();
    Usuario usr = new Usuario();
    String idUsuario;

    // **********************************
    // Constuctor
    public AlmacenaImagen(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
        this.contexto = context;
        databasePath = context.getDatabasePath(DATABASE_NAME).getPath();

        // ***************************************
        // Obtiene el nombre del usuario en y promotor las preferencias
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(this.contexto);
        idUsuario = preferencias.getString(TAG_USUARIO, usr.getnombre());
        // ***************************************

    }

    // **********************************
    // Crear base de datos  y sus tablas
    // TODO Crear base de datos  y sus tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        // ***************************************************************************************************************
        // Creación de tablas de base de datos
        // ***************************************************************************************************************
        // TODO Tabla listatiendas
        String sSql1 = "Create table listatiendas" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idpromotor INT," +
                "idruta INT," +
                "determinante INT," +
                "tienda TEXT," +
                "direccioncompleta TEXT," +
                "latitud REAL," +
                "longitud REAL" +
                ")";
        db.execSQL(sSql1);
        // Log.e(TAG_INFO, "se creo la tabla lista tiendas");
        // ************************************
        // TODO Tabla de almacenfotos
        String sSql = "Create table almacenfotos" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idpromotor INT," +
                "latitud REAL," +
                "longitud REAL," +
                "fechahora TEXT," +
                "idoperacion INT," +
                "idusuario TEXT," +
                "idruta INT," +
                "imagen TEXT" +
                ")";
        db.execSQL(sSql);
        // Log.e(TAG_INFO, "se creo la tabla almacenfotos");
        // ************************************
        // TODO Tabla de cat_promotor
        String sSql2 = "Create table cat_promotor" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idpromotor INT," +
                "idusuario TEXT," +
                "idempresa TEXT," +
                "pwd TEXT" +
                ")";
        db.execSQL(sSql2);
        // ************************************
        // TODO Tabla de actualizaciones
        String sSql3 = "Create table actualizacion" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Fecha TEXT," +
                "valor INT" +
                ")";
        db.execSQL(sSql3);
        // ************************************
        // TODO Tabla versionapp
        String sSql4 = "Create table versionapp" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "version TEXT" +
                ")";
        db.execSQL(sSql4);

        // ************************************
        // TODO Tabla de cat_productos
        String sSql5 = "Create table cat_productos" +
                "(" +
                "idproducto INTEGER PRIMARY KEY," +
                "upc TEXT, " +
                "descripcion TEXT, " +
                "descripcion1 TEXT, " +
                "cantidad_caja INT," +
                "cantidad_kgs REAL," +
                "idempresa INT," +
                "categoria1 TEXT," +
                "categoria2 TEXT," +
                "udc TEXT," +
                "fdc TEXT," +
                "uda TEXT," +
                "fda TEXT," +
                "idestatus INT," +
                "ruta TEXT, " +
                "imagen TEXT, " +
                "img BLOB " +
                ")";
        db.execSQL(sSql5);

        // ************************************
        // TODO Tabla de producto_formato_precio
        String sSql6 = "Create table producto_formato_precio" +
                "(" +
                "idproductoformatoprecio INTEGER PRIMARY KEY," +
                "idproducto INTEGER," +
                "idformato INTEGER," +
                "idempresa INTEGER," +
                "precio REAL," +
                "udc TEXT," +
                "fdc TEXT," +
                "uda TEXT," +
                "fda TEXT" +
                ")";
        db.execSQL(sSql6);

        // ************************************
        // TODO Tabla de cat_rutas
        String sSql7 = "Create table cat_rutas" +
                "(" +
                "idruta INTEGER PRIMARY KEY," +
                "idcadena INTEGER," +
                "idformato INTEGER," +
                "idempresa INTEGER," +
                "udc TEXT," +
                "fdc TEXT," +
                "uda TEXT," +
                "fda TEXT" +
                ")";
        db.execSQL(sSql7);

        // ************************************
        // Vista vw_producto_ruta_fecha
        String sSql8 = "Create table vw_producto_ruta_fecha" +
                "(" +
                "idproducto INTEGER," +
                "idruta INTEGER," +
                "idpromotor INTEGER," +
                "precioreal REAL," +
                "invinicial INTEGER," +
                "invfinal INTEGER," +
                "fda TEXT," +
                "mod INTEGER, " +
                "idObs INTEGER " +
                ")";
        db.execSQL(sSql8);

        // ************************************
        // TODO Tabla cat_cadena
        String sSql9 = "Create table cat_cadena" +
                "(" +
                "idcadena INTEGER," +
                "idempresa INTEGER," +
                "nombrecorto TEXT" +
                ")";
        db.execSQL(sSql9);

        // ************************************
        // TODO Tabla cat_observa_precios
        String sSql10 = "Create table cat_observa_precios" +
                "(" +
                "_id INTEGER," +
                "observaciones TEXT" +
                ")";
        db.execSQL(sSql10);

        // ************************************
        // TODO Tabla de vw_promociones
        String sSql11 = "Create table vw_promociones" +
                "(" +
                "idpromocion INTEGER," +
                "idempresa INTEGER," +
                "nombre TEXT, " +
                "capacidad TEXT, " +
                "canal TEXT, " +
                "alcance TEXT," +
                "inicio TEXT," +
                "final TEXT," +
                "periodo TEXT," +
                "actividad TEXT," +
                "precioregular TEXT," +
                "preciopromocion TEXT," +
                "idformato INT," +
                "ruta TEXT, " +
                "idproducto INT" +
                ")";
        db.execSQL(sSql11);

        // ************************************
        // TODO Tabla de promociones_tiendas
        String sSql12 = "Create table promociones_tiendas" +
                "(" +
                "idpromocion INTEGER," +
                "idpromotor INTEGER," +
                "idruta INTEGER," +
                "fecha TEXT, " +
                "aplica INTEGER,"+
                "mod INTEGER "+
                ")";
        db.execSQL(sSql12);

        // ************************************
        // TODO Tabla cat_actividad
        String sSql13 = "Create table cat_actividad" +
                "(" +
                "_id INTEGER," +
                "actividad TEXT" +
                ")";
        db.execSQL(sSql13);

        // ************************************
        // TODO Tabla de competencia
        String sSql14 = "Create table competencia" +
                "(" +
                "idcompetencia INTEGER PRIMARY KEY AUTOINCREMENT," +
                "producto TEXT," +
                "precio REAL," +
                "presentacion TEXT, " +
                "idruta INTEGER," +
                "idpromotor INTEGER," +
                "idempaque INTEGER," +
                "demostrador INTEGER," +
                "exhibidor INTEGER," +
                "emplaye INTEGER," +
                "actividadbtl TEXT, " +
                "canjes TEXT, " +
                "fecha TEXT, " +
                "idfoto INTEGER "+
                ")";
        db.execSQL(sSql14);

        // ************************************
        // TODO Tabla cat_empaque
        String sSql15 = "Create table cat_empaque" +
                "(" +
                "_id INTEGER," +
                "empaque TEXT" +
                ")";
        db.execSQL(sSql15);

        // ************************************
        // TODO Tabla vw_productos
        String sSql16 = "Create view vw_productos" +
                " as " +
                " Select 0 as _id, ' -- Seleccionar -- ' as upc " +
                " Union " +
                " Select idproducto as _id, descripcion || ' '  || upc " +
                " from cat_productos order by upc asc";
        db.execSQL(sSql16);

        // ************************************
        // TODO Tabla de caducidad
        String sSql17 = "Create table caducidad" +
                "(" +
                "idcaducidad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idproducto INT," +
                "lote TEXT," +
                "caducidad TEXT, " +
                "piezas INTEGER," +
                "idruta INTEGER," +
                "idpromotor INTEGER," +
                "idfoto INTEGER "+
                ")";
        db.execSQL(sSql17);

        // ************************************
        // TODO Tabla de configuracion
        String sSql18 = "Create table configuracion" +
                "(" +
                "idconf INTEGER PRIMARY KEY AUTOINCREMENT," +
                "solicitainventario INT" +
                ")";
        db.execSQL(sSql18);

        // ************************************
        // TODO Tabla cat_empresa
        String sSql19 = "Create table cat_empresa" +
                "(" +
                "_id INTEGER PRIMARY KEY," +
                "nombreempresa TEXT, " +
                "alias TEXT" +
                ")";
        db.execSQL(sSql19);

        // ************************************
        // TODO Tabla errores
        String sSql20 = "Create table errores" +
                "(" +
                "_id INTEGER PRIMARY KEY," +
                "fabricante TEXT," +
                "marca TEXT," +
                "modelo TEXT," +
                "board TEXT," +
                "hardware TEXT," +
                "serie TEXT," +
                "uid TEXT," +
                "android_id TEXT," +
                "resolucion TEXT," +
                "tamaniopantalla TEXT," +
                "densidad TEXT," +
                "bootloader TEXT," +
                "user_value TEXT," +
                "host_value TEXT," +
                "version TEXT," +
                "api_value TEXT," +
                "build_id TEXT," +
                "build_time TEXT," +
                "fingerprint TEXT," +
                "usuario TEXT," +
                "seccion TEXT," +
                "error TEXT," +
                "fechahora TEXT," +
                "procesado INTEGER " +
                ")";
        db.execSQL(sSql20);

        // ************************************
        // TODO Tabla de competencia_promocion
        String sSql21 = "Create table competencia_promocion" +
                "(" +
                "idcompetenciapromo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idruta INTEGER," +
                "idpromotor INTEGER," +
                "idfoto INTEGER,"+
                "idfoto1 INTEGER,"+
                "fecha TEXT, " +
                "por_participacion INTEGER,"+
                "no_frentes INTEGER," +
                "con_sin_participacion INTEGER," +
                "por_descuento INTEGER," +
                "comentarios TEXT," +
                "idproducto INTEGER," +
                "descripcion TEXT," +
                "precio TEXT" +
                ")";
        db.execSQL(sSql21);

        // TODO Tabla canjes
        String sSql22 = "Create table canjes" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idruta INT," +
                "idpromotor INT," +
                "idfoto INTEGER,"+
                "idfoto1 INTEGER,"+
                "comentario TEXT," +
                "fechahora TEXT," +
                "llave TEXT" +
                ")";
        db.execSQL(sSql22);

        // TODO Tabla canjes_productos
        String sSql23 = "Create table canjes_productos" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idruta INT," +
                "idpromotor INT," +
                "idproducto INT," +
                "cantidad int," +
                "fechahora TEXT," +
                "llave TEXT" +
                ")";
        db.execSQL(sSql23);
        // ***************************************************************************************************************
    }

    // **********************************
    // Actualizar base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // **********************************
    // Método para insertar promotor
    public int insertaoactualiza_act(
            String _Fecha,
            int _valor
    ) {
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int iCta = this.ObtenRegistrosActualizacion(fecha);

        String sSql = null;
        SQLiteDatabase db = getWritableDatabase();

        if (iCta > 0) {
            sSql = "Update actualizacion set Fecha = '" + fecha + "', valor = '" + _valor + "';";
        } else {
            sSql = "Insert into actualizacion(fecha, valor) " +
                    "values ('" + fecha + "'," + _valor + ");";
        }
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "insertaoactualiza_act" );
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la actualizacion " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Método para insertar promotor
    public int consulta_promocion_tienda(
            int _idpromocion,
            int _idpromotor,
            int _idruta
        ) {
        String sSql;
        Cursor cursor = null;
        int aplica = -1;
        SQLiteDatabase db = getReadableDatabase();

        sSql = "Select aplica from promociones_tiendas " +
                "  where idpromocion = '" + _idpromocion +
                "' and idpromotor = '" + _idpromotor +
                "' and idruta = '" + _idruta +
                "' and cast(fecha as date)  = cast(CURRENT_DATE as date)" +
                " limit 1";

        // db.beginTransaction();
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                aplica = cursor.getInt(0);
            }
            cursor.close();
            // db.setTransactionSuccessful();
            return aplica;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "consulta_promocion_tienda" );
            //Log.e(TAG_ERROR, Resultado);
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al consultar+ en la actualizacion " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Método para insertar promotor
    public int inserta_promocion_tienda(
            int _idpromocion,
            int _idpromotor,
            int _idruta,
            int _aplica,
            int _modif
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getReadableDatabase();

        sSql = "Select count(*) from promociones_tiendas " +
                "  where idpromocion = '" + _idpromocion +
                "' and idpromotor = '" + _idpromotor +
                "' and idruta = '" + _idruta +
                "' and cast(fecha as date)  = cast(CURRENT_DATE as date);";

        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {

            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();

            if (cta > 0) {
                sSql = "  Update promociones_tiendas " +
                        " set aplica = '" + _aplica + "', mod = '" + _modif + "'" +
                        "  where idpromocion = '" + _idpromocion + "' and idpromotor = '" + _idpromotor +
                        "' and idruta = '" + _idruta +
                        "' and cast(fecha as date)  = cast(CURRENT_DATE as date);";
            } else {
                sSql = "   Insert into promociones_tiendas(idpromocion, idruta, idpromotor, fecha, aplica, mod)" +
                        " values ('" + _idpromocion + "','" + _idruta + "','" +  _idpromotor + "', CURRENT_DATE, '" + _aplica + "','" + _modif + "');";
            }

            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "inserta_promocion_tienda" );
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la actualizacion " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db1.close();
            db.close();
        }
    }

    // **********************************
    // Método para insertar competencia
    public int inserta_caducidad(
            int _idproducto,
            String _lote,
            String _caducidad,
            int _piezas,
            int _idruta,
            int _idpromotor,
            int _idfoto
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        try {
            sSql = "   Insert into caducidad(idproducto, lote, caducidad, piezas, idruta, idpromotor, idfoto)" +
                    " values ('" + _idproducto + "','" +  _lote + "','" + _caducidad + "', '" + _piezas +
                    "','" + _idruta +"','" + _idpromotor +"','" + _idfoto + "');";

            db.execSQL(sSql);
            //Log.e(TAG_INFO, sSql);
            //Log.e(TAG_INFO, "insercion en la tabla caducidad");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "inserta_caducidad" );
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la tabla caducidad " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Método para insertar competencia
    public int inserta_competencia(
            String _producto,
            double _precio,
            String _presentacion,
            int _idempaque,
            int _idruta,
            int _idpromotor,
            int _demostrador,
            int _exhibidor,
            int _emplaye,
            String _actividadbtl,
            String _canjes,
            int _idfoto
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getReadableDatabase();

        sSql = "Select count(*) from competencia " +
                "  where idfoto = '" + _idfoto +
                "' and cast(fecha as date)  = cast(CURRENT_DATE as date);";

        // Log.e(TAG_INFO, "sSql " + sSql);
        db.beginTransaction();
        try {

            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();

            if (cta > 0) {
                sSql = "  Update competencia " +
                        " set producto = '" + _producto + "', precio = '" + _precio + "', presentacion = '" + _presentacion + "', idruta = '" + _idruta + "'," +
                        " idpromotor = '" + _idpromotor + "', idempaque = '" + _idempaque + "', demostrador = '"  + _demostrador + "', exhibidor = '" + _exhibidor + "'," +
                        " emplaye = '" + _emplaye + "', actividadbtl = '" + _actividadbtl + "', canjes = '" + _canjes + "', idfoto = '"  + _idfoto + "', fecha = cast(CURRENT_DATE as date) " +
                        "  where idfoto = '" + _idfoto +
                        "' and cast(fecha as date)  = cast(CURRENT_DATE as date);";
            } else {
                sSql = "   Insert into competencia(producto, precio, presentacion, idruta, idpromotor, " +
                        "  idempaque, demostrador, exhibidor, emplaye, actividadbtl, canjes, idfoto, fecha)" +
                        " values ('" + _producto + "','" + _precio + "','" +  _presentacion + "','" + _idruta + "', '" + _idpromotor +
                        "','" + _idempaque +"','" + _demostrador +"','" + _exhibidor + "','" + _emplaye +"','" + _actividadbtl+"','" + _canjes + "','" + _idfoto + "',  CURRENT_DATE);";
            }

            db.execSQL(sSql);
            db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "inserta_competencia" );
            String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la tabla competencia " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            db.endTransaction();
            db1.close();
            db.close();
        }
    }

    // **********************************
    // Método para insertar competencia
    public int inserta_competencia_promocion(
            int _idruta,
            int _idpromotor,
            int _idfoto,
            int _idfoto1,
            int _por_participacion,
            int _no_frentes,
            int _con_sin_participacion,
            int _por_descuento,
            String _comentarios,
            int _idproducto,
            double _precio
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getReadableDatabase();

        sSql = "Select count(*) from competencia_promocion " +
                "  where idfoto = '" + _idfoto + "'" +
                "  and idfoto1 = '" + _idfoto1 + "'" +
                "  and cast(fecha as date)  = cast(CURRENT_DATE as date);";

        // Log.e(TAG_INFO, "sSql " + sSql);
        db.beginTransaction();
        try {

            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();

            if (cta > 0) {
                sSql = "  Update competencia_promocion " +
                        " set idruta = '" + _idruta + "', idpromotor = '" + _idpromotor + "', por_participacion = '" + _por_participacion + "', no_frentes = '" + _no_frentes + "'," +
                        " con_sin_participacion = '" + _con_sin_participacion + "', por_descuento = '"  + _por_descuento + "', comentarios = '" + _comentarios+ "'," +
                        " idproducto = '" + _idproducto + "', precio = '" + _precio + "', idfoto = '"  + _idfoto + "',  idfoto1 = '"  + _idfoto1 + "',fecha = cast(CURRENT_DATE as date) " +
                        "  where idfoto = '" + _idfoto + "' and idfoto1 = '" + _idfoto1 + "'" +
                        " and cast(fecha as date)  = cast(CURRENT_DATE as date);";
            } else {
                sSql = "   Insert into competencia_promocion(idruta, idpromotor, idfoto, idfoto1, fecha, por_participacion, no_frentes, con_sin_participacion, " +
                        " por_descuento, comentarios, idproducto, precio)" +
                        " values ('" + _idruta + "','" + _idpromotor + "','" +  _idfoto + "','" + _idfoto1 + "', CURRENT_DATE,'" + _por_participacion +
                        "','" + _no_frentes +"','" + _con_sin_participacion + "','" + _por_descuento+"','" + _comentarios + "','" + _idproducto +
                        "','" + _precio +"');";
            }

            db.execSQL(sSql);
            db.setTransactionSuccessful();
            //Log.e(TAG_INFO, " ***** inserción compentencia_promcion " + sSql);
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "inserta_competencia_promocion" );
            String Resultado = e.getMessage();
            return 0;
        } finally {
            db.endTransaction();
            db1.close();
            db.close();
        }
    }

    // **********************************
    // Método para insertar promotor
    public int actualiza_precioproducto(
            int _idproducto,
            int _idruta,
            int _idpromotor,
            double _precioreal,
            int _invinicial,
            int _invfinal,
            int _mod,
            int _idObs
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getReadableDatabase();

        sSql = "Select count(*) from vw_producto_ruta_fecha " +
                "  where idproducto = '" + _idproducto +
                "' and idruta = '" + _idruta + "' and DATE(fda) = DATE(CURRENT_DATE)";

        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {

            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();

            if (cta > 0) {
                sSql = "  Update vw_producto_ruta_fecha " +
                        " set precioreal = '" + _precioreal + "' " +
                        ", mod = " + _mod +
                        ", idpromotor = '" + _idpromotor + "' " +
                        ", idObs = '" + _idObs + "' " +
                        ", invinicial = '" + _invinicial + "' " +
                        ", invfinal = '" + _invfinal + "' " +
                        " where  idproducto = '" + _idproducto + "' " +
                        " and idruta = '" + _idruta + "' and DATE(fda) = DATE(CURRENT_DATE)";
            } else {
                sSql = "   Insert into vw_producto_ruta_fecha(idproducto, idruta, idpromotor, precioreal, invinicial, invfinal, fda, mod, idObs)" +
                        " values ('" + _idproducto + "','" + _idruta + "','" + + _idpromotor + "','" + _precioreal + "','" + _invinicial + "','" + _invfinal + "', CURRENT_DATE, '" + _mod + "','" + _idObs + "');";
            }

            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "actualiza_precioproducto" );
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la actualizacion " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }


    // **********************************
    // Método para insertar promotor
    public int insertaoactualizapromotor(
            int _idpromotor,
            String _idusuario,
            String _pwd,
            String _idempresa
    ) {
        int idpromotor = this.ObtenRegistrosPromotor(_idusuario, _pwd, _idempresa);
        String sSql = null;
        SQLiteDatabase db = getWritableDatabase();

        if (idpromotor > 0) {
            sSql = "Update cat_promotor " +
                    "set idpromotor = " + _idpromotor + ", pwd = '" + _pwd + "', idempresa =  '" + _idempresa + "' " +
                    "where idusuario = '" + _idusuario + "';";
        } else {
            sSql = "Insert into cat_promotor(idpromotor, idusuario, pwd, idempresa) " +
                    "values (" + _idpromotor + ",'" + _idusuario + "','" + _pwd + "','" + _idempresa + "');";
        }
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "insertaoactualizapromotor" );
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en el catalogo del promotor " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar tienda
    public int insertatienda(
            int _idpromotor,
            int _idruta,
            int _determinante,
            String _tienda,
            String _direccioncompleta,
            double _latitud,
            double _longitud
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into listatiendas(idpromotor, idruta, determinante, tienda, direccioncompleta, latitud, longitud)" +
                "values (" + _idpromotor + "," + _idruta + "," + _determinante + ",'" + _tienda + "','" + _direccioncompleta + "'," + _latitud + "," + _longitud + ");";
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, "insercion en la tabla listatiendas");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "insertatienda" );
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la lista de tiendas " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar producto
    public int insertaproducto(
            int _idproducto,
            String _upc,
            String _descripcion,
            String _descripcion1,
            int _cantidadcaja,
            double _cantidadkgs,
            int _idempresa,
            String _categoria1,
            String _categoria2,
            String _udc,
            String _fdc,
            String _uda,
            String _fda,
            String _ruta
    ) {
        // ***********************************
        // Proceso que convierte una imagen de la ruta y la guarda en el campo imagen BLOB
        Bitmap bitmap = getBitmapFromURL(_ruta.trim());
        // Log.e(TAG_ERROR, "Ruta del archivo " + _ruta.trim());

        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into cat_productos(idproducto, upc, descripcion, descripcion1, cantidad_caja, cantidad_kgs, idempresa, categoria1, categoria2, udc, fdc, uda, fda, idestatus, ruta, imagen)" +
                "values ('" + _idproducto + "','" + _upc + "','" + _descripcion + "','" + _descripcion1 + "','" +
                _cantidadcaja + "','" + _cantidadkgs + "','" + _idempresa + "','" + _categoria1 + "','" + _categoria2 + "','" +
                _udc + "','" + _fdc + "','" + _uda + "','" + _fda + "','1','" + _ruta + "',null);";
        //Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, "insercion en la tabla listatiendas");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "inserta_producto" );
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en cat_productos " +  Resultado,Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Método para insertar empresa
    public int inserta_empresa(
            int _idempresa,
            String _nombreempresa,
            String _alias
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        try {
            sSql = "   Insert into cat_empresa(_id, nombreempresa, alias)" +
                    " values ('" + _idempresa + "','" +  _nombreempresa + "','" + _alias + "');";

            //Log.e(TAG_ERROR, sSql);
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "inserta_empresa" );
            String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la tabla cat_empresa " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para obtener la imagen del catalogo de productos desde url
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.getBitmapFromURL" );
            // e.printStackTrace();
            return null;
        }
    }

    // **********************************
    // Metodo para obtener la imagen del catalogo de productos
    public Bitmap Obtenimagen(int idproducto) {
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        // byte[] image = null;
        String imagenentexto = null;
        String sSql = "Select imagen from cat_productos where idproducto = " + idproducto;
        cursor = db.rawQuery(sSql, null);
        while (cursor.moveToNext()) {
            imagenentexto = cursor.getString(0);
        }
        cursor.close();

        // Convierte la imagen en texto en un bitmap
        byte[] decodedString = Base64.decode(imagenentexto, Base64.DEFAULT);
        Bitmap imagen = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return imagen;
    }

    // **********************************
    // Metodo para insertar producto_formato_precio
    public int inserta_productoformatoprecio(
            int _idproductoformatoprecio,
            int _idproducto,
            int _idformato,
            int _idempresa,
            double _precio,
            String _udc,
            String _fdc,
            String _uda,
            String _fda
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into producto_formato_precio(idproductoformatoprecio, idproducto, idformato, idempresa, precio, udc, fdc, uda, fda)" +
                "values ('" + _idproductoformatoprecio + "','" + _idproducto + "','" + _idformato + "','" + _idempresa + "','" + _precio + "','" +
                _udc + "','" + _fdc + "','" + _uda + "','" + _fda + "');";
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            // Log.e(TAG_INFO, "**** Error aqui " + Resultado);
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar producto_formato_precio
    public int inserta_rutas(
            int _idruta,
            int _idcadena,
            int _idformato,
            int _idempresa,
            String _udc,
            String _fdc,
            String _uda,
            String _fda
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into cat_rutas(idruta, idcadena, idformato, idempresa, udc, fdc, uda, fda)" +
                "values ('" + _idruta + "','" + _idcadena + "','" + _idformato + "','" + _idempresa + "','" +
                _udc + "','" + _fdc + "','" + _uda + "','" + _fda + "');";
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, "insercion en la tabla listatiendas");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.InsertaRutas" );
            String Resultado = e.getMessage();
            // Log.e(TAG_INFO, Resultado);
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en  vw_producto_ruta_fecha
    public int inserta_vista(
            int _idproducto,
            int _idruta,
            Double _precioreal,
            String _fda
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into vw_producto_ruta_fecha(idproducto, idruta, precioreal, fda, mod)" +
                "values ('" + _idproducto + "','" + _idruta + "','" + _precioreal + "','" + _fda + "',0);";
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, "insercion en la tabla listatiendas");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_vista" );
            //String Resultado = e.getMessage();
            // Log.e(TAG_INFO, Resultado);
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en cadena
    public int inserta_cadena(
            int _idcadena,
            int _idempresa,
            String _nombrecorto
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into cat_cadena(idcadena, idempresa, nombrecorto)" +
                "values ('" + _idcadena + "','" + _idempresa + "','" + _nombrecorto + "');";
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, "insercion en la tabla listatiendas");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_cadena" );
            //String Resultado = e.getMessage();
            // Log.e(TAG_INFO, Resultado);
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en cat_observa_precios
    public int inserta_obs(
            int _idobs,
            String _observaciones
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into cat_observa_precios(_id, observaciones)" +
                "values ('" + _idobs + "','" + _observaciones + "');";
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_obs" );
            //String Resultado = e.getMessage();
            return 0;
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en cat_actividad
    public int inserta_actividad(
            int _idactividad,
            String _actividad
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into cat_actividad(_id, actividad)" +
                "values ('" + _idactividad + "','" + _actividad + "');";
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_actividad" );
            //String Resultado = e.getMessage();
            return 0;
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en cat_empaque
    public int inserta_empaque(
            int _idempaque,
            String _empaque
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into cat_empaque(_id, empaque)" +
                "values ('" + _idempaque + "','" + _empaque + "');";
        // Log.e(TAG_ERROR, sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_empaque" );
            //String Resultado = e.getMessage();
            return 0;
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en cat_empaque
    public int inserta_configuracion(
            int _solicita
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into configuracion(solicitainventario)" +
                "values ('" + _solicita + "');";
        // Log.e(TAG_ERROR, sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_configuracion" );
            // String Resultado = e.getMessage();
            return 0;
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar en cat_empaque
    public int consulta_configuracion()
    {
        // ***********************************
        SQLiteDatabase db = getReadableDatabase();

        String sSql = "Select solicitainventario from configuracion";

        // Log.e(TAG_INFO, "sSql " + sSql);
        int solicitainventario = 0;
        // db.beginTransaction();
        try {

            Cursor cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                solicitainventario = cursor.getInt(0);
            }
            cursor.close();
            // db.setTransactionSuccessful();
            return solicitainventario;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.consulta_configuracion" );
            // String Resultado = e.getMessage();
            return 0;
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Metodo para insertar producto_formato_precio
    public int inserta_promo(
            int _idpromocion,
            int _idempresa,
            String _nombre,
            String _capacidad,
            String _canal,
            String _alcance,
            String _inicio,
            String _final,
            String _periodo,
            String _actividad,
            String _precioregular,
            String _preciopromocion,
            int _idformato,
            int _idproducto,
            String _ruta
    ) {
        // ***********************************
        SQLiteDatabase db = getWritableDatabase();
        String sSql = "Insert into vw_promociones(idpromocion, idempresa, nombre, capacidad, canal, alcance, inicio, final, periodo, actividad, precioregular, preciopromocion, idformato, idproducto, ruta)" +
                "values ('" + _idpromocion + "','" + _idempresa + "','" + _nombre + "','" + _capacidad + "','" + _canal + "','" +
                _alcance + "','" + _inicio + "','" + _final + "','" + _periodo + "', '" +
                _actividad + "','" + _precioregular + "','" + _preciopromocion + "','" + _idformato + "','" + _idproducto + "','" + _ruta + "');";
        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, "insercion en la vista de promociones");
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            // String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.inserta_promo" );
            // Log.e(TAG_INFO, "**** Error aqui " + Resultado);
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Método para guardar una foto
    public int guardaFotos(int pidpromotor,
                           double platitud,
                           double plongitud,
                           String pfechahora,
                           int pidoperacion,
                           String pidusuario,
                           int pidruta,
                           Bitmap imagen) {
        String sSql;
        Cursor cursor;

        Bitmap bitmap = funciones.Compacta(imagen);
        String uploadImage = getStringImage(bitmap);
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getReadableDatabase();
        int cta = 0;

        // ********************************
        // Consulta de promotor, tienda, fecha y operación 1 o 2 única
        if (pidoperacion <= 2) {
            sSql = "Select count(*) from almacenfotos where idpromotor = " + pidpromotor + " and idruta = " + pidruta +
                    " and DATE(fechahora) = DATE(CURRENT_DATE) and idoperacion = " + pidoperacion;

            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();
        }
        if (cta > 0) {
            // si ya existe una foto entonces ya no la guarda para que no la pueda subir a la plataforma
            // duplicada
            return 0;
        }

        // ********************************
        // Solo se deben insertar datos repetidos para promotores, tiendas y fechas repetidos para operaciones 3 y 4
        // Operación 1 y 2 deben ser únicos

        sSql = "Insert into almacenfotos(idpromotor, latitud, longitud" +
                ", fechahora, idoperacion, idusuario, idruta, imagen)" +
                "values (" + pidpromotor + "," + platitud + "," + plongitud +
                ",'" + pfechahora.trim() + "'," + pidoperacion + ",'" + pidusuario + "'," + pidruta + ",'" +
                getStringImage(imagen) + "');";
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            // Log.e (TAG_ERROR, "Insercion de foto " +  sSql);
            db1 = getReadableDatabase();
            int id = 0;
            sSql = "Select max(id) from almacenfotos;";
            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
            }
            cursor.close();
            return id;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.guardaFotos" );
            //String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en el almacen de fotos " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // db.endTransaction();
            db1.close();
            db.close();
        }
    }

    // **********************************
    // Función que guards la imagen como una cadena
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    // **********************************
    // Función que consulta el valor de la tabla de actualizacion
    public int ObtenRegistrosActualizacion(String _fecha) {
        int iResultado = 0;
        int valor = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql = "Select count(*) from actualizacion";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();

            // Si el resultado es uno entonces debe indicar el valor de actualizacion
            if (iResultado == 0) {
                return iResultado;
            } else {
                sSql = "Select valor from actualizacion where fecha = '" + _fecha + "';";
                cursor = db.rawQuery(sSql, null);
                while (cursor.moveToNext()) {
                    valor = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenRegistrosActualización" );
            // String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las actualizaciones de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            cursor.close();
            db.close();
        }
        return valor;
    }

    // **********************************
    // Función que obtiene un arreglo de rutas
    public int[] ObtenRutas(int _idpromotor, String _tienda) {
        int iNumTiendas = ObtenRegistrosTiendas(_idpromotor, _tienda);
        int[] retorno = new int[iNumTiendas];
        int iruta;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        if (_tienda.length() == 0) {
            sSql = "Select idruta from listatiendas where idpromotor = " + _idpromotor + ";";
        } else {
            sSql = "Select idruta from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }


        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iruta = cursor.getInt(0);
                retorno[i] = iruta;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenRutas" );
            //String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las rutas de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de determinantes
    public String[] ObtenDeterminantes(int _idpromotor, String _tienda) {
        int iNumTiendas = ObtenRegistrosTiendas(_idpromotor, _tienda);
        String[] retorno = new String[iNumTiendas];
        String determinante;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        if (_tienda.length() == 0) {
            sSql = "Select determinante from listatiendas where idpromotor = " + _idpromotor + ";";
        } else {
            sSql = "Select determinante from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                determinante = cursor.getString(0);
                retorno[i] = determinante;
                i++;
            }
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenDeterminantes" );
            //String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las determinantes de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            cursor.close();
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de tiendas
    public String[] ObtenTiendas(int _idpromotor, String _tienda) {
        int iNumTiendas = ObtenRegistrosTiendas(_idpromotor, _tienda);
        String[] retorno = new String[iNumTiendas];
        String tienda;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        if (_tienda.length() == 0) {
            sSql = "Select tienda from listatiendas where idpromotor = " + _idpromotor + ";";
        } else {
            sSql = "Select tienda from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                tienda = cursor.getString(0);
                retorno[i] = tienda;
                i++;
            }
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenTiendas" );
            //String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener los nombres  de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            cursor.close();
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de direcciones
    public String[] ObtenDirecciones(int _idpromotor, String _tienda) {
        int iNumTiendas = ObtenRegistrosTiendas(_idpromotor, _tienda);
        String[] retorno = new String[iNumTiendas];
        String direccion;
        SQLiteDatabase db;
        db = getReadableDatabase();
        String sSql;
        if (_tienda.length() == 0) {
            sSql = "Select direccioncompleta from listatiendas where idpromotor = " + _idpromotor + ";";
        } else {
            sSql = "Select direccioncompleta from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                direccion = cursor.getString(0);
                retorno[i] = direccion;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenRegistrosTiendas" );
            //String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las direcciones de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de direcciones
    public double[] ObtenLatitudes(int _idpromotor, String _tienda) {
        int iNumTiendas = ObtenRegistrosTiendas(_idpromotor, _tienda);
        double[] retorno = new double[iNumTiendas];
        double latitud;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        if (_tienda.length() == 0) {
            sSql = "Select latitud from listatiendas where idpromotor = " + _idpromotor + ";";
        } else {
            sSql = "Select latitud from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                latitud = cursor.getDouble(0);
                retorno[i] = latitud;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenLatitudes" );
            // String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las latitudes de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de direcciones
    public double[] ObtenLongitudes(int _idpromotor, String _tienda) {
        int iNumTiendas = ObtenRegistrosTiendas(_idpromotor, _tienda);
        double[] retorno = new double[iNumTiendas];
        double longitud;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        if (_tienda.length() == 0) {
            sSql = "Select longitud from listatiendas where idpromotor = " + _idpromotor + ";";
        } else {
            sSql = "Select longitud from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                longitud = cursor.getDouble(0);
                retorno[i] = longitud;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenLongitudes" );
            // String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener los registros de las tiendas " + Resultado, Toast.LENGTH_LONG).show();
            // Por si hay una excepcion
            // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que consulta el número de registros existentes de promotores y entrega el id promotor si existe
    public int ObtenRegistrosPromotor(String _idusuario, String _pwd, String _idempresa) {
        int iResultado = 0;
        int idpromotor = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql = " Select count(*) from cat_promotor " +
                "       where idusuario = '" + _idusuario + "' and pwd = '" + _pwd + "' and idempresa = '" + _idempresa + "'";
        // Log.e(TAG_INFO,sSql);
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
            // Log.e(TAG_INFO,"Resultado " + iResultado);

            // Si el resultado es mayor a uno entonces debe obtener y retornar el idpromotor
            sSql = " Select idpromotor from cat_promotor " +
                    " where idusuario = '" + _idusuario + "' " +
                    " and idempresa = '" + _idempresa + "'";

            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                idpromotor = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenRegistrosPromotor" );
            //String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener registros del promotor " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        return (idpromotor > 0) ? idpromotor : iResultado;
    }


    // **********************************
    // Función que consulta el número de registros existentes de promotores y entrega el id promotor si existe
    public int ObtenPromocionesFormato(int pidformato) {
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql = "Select count(*) from vw_promociones where idformato = " + pidformato;

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
            // Log.e(TAG_INFO,"Resultado " + iResultado);
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenPromocionesFormato" );
            // String Resultado = e.getMessage();
            // Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener el numero de promociones del formato " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        return iResultado;
    }

    // **********************************
    // Función que consulta el número de registros existentes de promotores y entrega el id promotor si existe
    public int ObtenFotosTienda(int idRuta) {
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql = "";

        sSql = "  Select count(*) from almacenfotos " +
                " where idRuta = '" + idRuta + "' and idOperacion<5;";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
            // Log.e(TAG_INFO,"Resultado " + iResultado);
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "AlmacenaImagen.ObtenFotosTienda" );
            // String Resultado = e.getMessage();
            // Log.e(TAG_ERROR, sSql);
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener registros " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        return iResultado;
    }

    // **********************************
    // Función que consulta el número de registros existentes de promotores y entrega el id promotor si existe
    public int ObtenRegistros(int iTabla) {
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql = "";

        switch (iTabla) {
            case 0:
                sSql = "Select count(*) from almacenfotos where idOperacion<5;";
                break;
            case 1:
                sSql = "Select count(*) from cat_productos;";
                break;
            case 2:
                sSql = "Select count(*) from producto_formato_precio;";
                break;
            case 3:
                sSql = "Select count(*) from cat_rutas;";
                break;
            case 4:
                sSql = "Select count(*) from vw_producto_ruta_fecha;";
                break;
            case 5:
                sSql = "Select count(*) from cat_cadena;";
                break;
            case 6:
                sSql = "Select count(*) from cat_observa_precios;";
                break;
            case 7:
                sSql = "Select count(*) from vw_promociones;";
                break;
            case 8:
                sSql = "Select count(*) from cat_actividad;";
                break;
            case 9:
                sSql = "Select count(*) from vw_producto_ruta_fecha where Mod = 1;";
                break;
            case 10:
                sSql = "Select count(*) from competencia;";
                break;
            case 11:
                sSql = "Select count(*) from almacenfotos where idOperacion>=5;";
                break;
            case 12:
                sSql = "Select count(*) from promociones_tiendas where mod>0;";
                break;
            case 13:
                sSql = "Select count(*) from cat_empaque;";
                break;
            case 14:
                sSql = "Select count(*) from caducidad;";
                break;
            case 15:
                sSql = "Select count(*) from cat_empresa;";
                break;
            case 16:
                sSql = "Select count(*) from errores where procesado = 0;";
                break;
            case 18:
                sSql = "Select count(*) from competencia_promocion;";
                break;
            case 20:
                sSql = "Select count(*) from canjes;";
                break;
        }

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
            // Log.e(TAG_INFO,"Resultado " + iResultado);
        } catch (Exception e) {
            String Resultado = e.getMessage();
            // Log.e(TAG_ERROR, sSql);
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener registros " + Resultado, Toast.LENGTH_LONG).show();
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
        }
        return iResultado;
    }

    // **********************************
    // Función que consulta el numero de formato de una tienda
    public int ObtenFormato(Integer _idruta) {
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;

        sSql = "Select idformato from cat_rutas where idruta = " + _idruta;

        //Log.e(TAG_INFO, "Consulta promotor "  +sSql);
        Cursor cursor = null;
        try {

            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener el formato de una ruta " + Resultado, Toast.LENGTH_LONG).show();
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
        }
        return iResultado;
    }

    // **********************************
    // Función que consulta el número de registros existentes en la tabla de tiendas
    public int ObtenRegistrosTiendas(Integer _idpromotor, String _tienda) {
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;

        if (_tienda.length() == 0) {
            sSql = "Select count(*)  from listatiendas where idpromotor = " + _idpromotor;
        } else {
            sSql = "Select count(*)  from listatiendas where idpromotor = " + _idpromotor + " and tienda like '%" + _tienda + "%'";
        }
        // Log.e(TAG_INFO, "Consulta lista tiendas "  +sSql);
        Cursor cursor = null;
        try {

            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener registros de lista de tiendas " + Resultado, Toast.LENGTH_LONG).show();
            // funciones.RegistraError(idUsuario, "AlmacenaImagen, downloadFile", e,  (Activity) AlmacenaImagen.this.contexto , AlmacenaImagen.this.contexto);
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
        }
        return iResultado;
    }

    // **********************************
    // Método para insertar version de app
    public int insertaversion_app(
            String _version
    ) {
        String sSql = null;
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("Delete from versionapp");
        sSql = "insert into versionapp (version) values('" + _version + "')";

        // Log.e(TAG_INFO, "sSql " + sSql);
        // db.beginTransaction();
        try {
            db.execSQL(sSql);
            // Log.e(TAG_INFO, sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la actualizacion " +  Resultado,Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Función que consulta la version de la app
    public String ConsultaVersionApp() {
        String versionapp = "";
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        String sSql = "Select count(*) from almacenfotos;";
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
        } catch (Exception e) {
            // Por si hay una excepcion
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener la versión de la app " +  Resultado,Toast.LENGTH_LONG).show();
        }

        if (iResultado == 0) {
            versionapp = "";
            // Log.e(TAG_ERROR, "valor obtenido en consulta " + versionapp);
            return versionapp;
        }


        sSql = "Select IFNULL(version, ' ') from versionapp;";
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                versionapp = cursor.getString(0);
            }
            cursor.close();
            // Log.e(TAG_ERROR, "valor obtenido en consulta " + versionapp);
        } catch (Exception e) {
            // Por si hay una excepcion
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener la versión de la app " + Resultado, Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        return versionapp;
    }

    // **********************************
    // Borra la foto de la tabla de fotos si se pudo enviar correctamente la imagen
    public int BorraFotoEnviada(int idfoto) {
        String sSql;
        SQLiteDatabase db = getWritableDatabase();
        sSql = "Delete from almacenfotos where id = " + idfoto;

        try {
            db.execSQL(sSql);
            return 0;
        } catch (Exception ex) {
            String Resultado = ex.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al borrar la foto " + Resultado, Toast.LENGTH_LONG).show();
            return idfoto;
            // Log.e(TAG_ERROR, ex.getMessage());
        } finally {
            db.close();
        }
    }

    public int BorraFotoEnviada(int idfoto, int idfoto1) {
        String sSql;
        SQLiteDatabase db = getWritableDatabase();
        sSql = "delete from almacenfotos where id in (" + idfoto + "," + idfoto1 + ");";
        //  Log.e(TAG_INFO, sSql);
        try {
            db.execSQL(sSql);
            return 0;
        } catch (Exception ex) {
            String Resultado = ex.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al borrar la foto enviada " + Resultado, Toast.LENGTH_LONG).show();
            return idfoto;
            // Log.e(TAG_ERROR, ex.getMessage());
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Truncar tabla listatiendas
    public void TruncarTablaTiendas(int idpromotor) {
        String sSql;
        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        sSql = "Delete from listatiendas where idpromotor = " + idpromotor;

        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
        } catch (Exception ex) {
            String Resultado = ex.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al truncar tablas tiendas " + Resultado, Toast.LENGTH_LONG).show();
            // Log.e(TAG_ERROR, ex.getMessage());
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Truncar tabla almacenfotos
    public void TruncarTabla(int iTabla) {
        String sSql = null;
        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        switch (iTabla) {
            case 1:
                sSql = "Delete from cat_productos;";
                break;
            case 2:
                sSql = "Delete from producto_formato_precio;";
                break;
            case 3:
                sSql = "Delete from cat_rutas;";
                break;
            case 4:
                sSql = "Delete from vw_producto_ruta_fecha;";
                break;
            case 5:
                sSql = "Delete from cat_cadena;";
                break;
            case 6:
                sSql = "Delete from cat_observa_precios;";
                break;
            case 7:
                sSql = "Delete from vw_promociones;";
                break;
            case 8:
                sSql = "Delete from cat_actividad;";
                break;
            case 10:
                sSql = "Delete from configuracion;";
                break;
            case 12:
                sSql = "Delete from cat_empaque;";
                break;
            case 15:
                sSql = "Delete from cat_empresa;";
                break;
        }

        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
        } catch (Exception ex) {
            String Resultado = ex.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al truncar tablas " + Resultado, Toast.LENGTH_LONG).show();
            // Log.e(TAG_ERROR, ex.getMessage());
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Truncar tabla fotos
    public void TruncarTablaFotos() {
        String sSql;
        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        sSql = "Delete from almacenfotos";

        try {
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
        } catch (Exception ex) {
            String Resultado = ex.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al truncar tablas " + Resultado, Toast.LENGTH_LONG).show();
            //Error in between database transaction
            // Log.e(TAG_ERROR, ex.getMessage());
        } finally {
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Función que obtiene un arreglo de idproductos
    public int[] Obtenidsproducto() {
        int iNumProductos = this.ObtenRegistros(1);
        int[] retorno = new int[iNumProductos];
        int idproducto;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        sSql = "Select idproducto from cat_productos order by descripcion asc;";

        // Log.e(TAG_ERROR, "Consulta " + sSql);

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                idproducto = cursor.getInt(0);
                retorno[i] = idproducto;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las rutas de las tiendas " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de descripcion de productos
    // TODO Función que obtiene un arreglo de descripcion de productos
    public String[] Obtendescripcionesproducto() {
        int iNumProductos = this.ObtenRegistros(1);
        String[] retorno = new String[iNumProductos];
        String descripcionproducto;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        // sSql = "Select (descripcion || ' ' || descripcion1 || ' ' || cantidad_kgs) as descripcion from cat_productos  order by descripcion asc;";
        sSql = "Select (descripcion || ' ' || cantidad_kgs) as descripcion from cat_productos  order by descripcion asc;";

        // Log.e(TAG_ERROR, "Consulta " + sSql);

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                descripcionproducto = cursor.getString(0);
                retorno[i] = descripcionproducto;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las descripciones de los prouductos " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de descripcion de productos
    public String[] Obtencategoriasproducto() {
        int iNumProductos = this.ObtenRegistros(1);
        String[] retorno = new String[iNumProductos];
        String categoriaproducto;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        sSql = "Select categoria1 from cat_productos  order by descripcion asc;";

        // Log.e(TAG_ERROR, "Consulta " + sSql);

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                categoriaproducto = cursor.getString(0);
                retorno[i] = categoriaproducto;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las categorias de las productos " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Función que obtiene un arreglo de descripcion de productos
    public String[] Obtenupcsproducto() {
        int iNumProductos = this.ObtenRegistros(1);
        String[] retorno = new String[iNumProductos];
        String upcproducto;
        SQLiteDatabase db = getReadableDatabase();
        String sSql = "Select upc from cat_productos  order by descripcion asc;";

        // Log.e(TAG_ERROR, "Consulta " + sSql);

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                upcproducto = cursor.getString(0);
                retorno[i] = upcproducto;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las categorias de las productos " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return retorno;
    }

    // **********************************
    // Regresa un cursos para llenar el spinner de observaciones
    public Cursor CursorObservaciones(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            return db.query("cat_observa_precios", null,null,null,null,null, null);
        }
        catch ( SQLException e)
        {
            //Log.e(TAG_ERROR, e.getMessage());
            return null;
        }
    }

    // **********************************
    // Regresa un cursos para llenar el spinner de actividades
    public Cursor CursorEmpaque(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            return db.query("cat_empaque", null,null,null,null,null, null);
        }
        catch ( SQLException e)
        {
            // Log.e(TAG_ERROR, e.getMessage());
            return null;
        }
    }

    // **********************************
    // Regresa un cursos para llenar el spinner de actividades
    public Cursor CursorProductos(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            return db.query("vw_productos", null,null,null,null,null, null);
        }
        catch ( SQLException e)
        {
            // Log.e(TAG_ERROR, e.getMessage());
            return null;
        }
    }

    // **********************************
    // Regresa un cursos para llenar el spinner de actividades
    public Cursor CursorEmpresas(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            return db.query("cat_empresa", null,null,null,null,null, "alias");
        }
        catch ( SQLException e)
        {
            Log.e(TAG_ERROR, e.getMessage());
            return null;
        }
    }

    // **********************************
    // Regresa un cursos para llenar el spinner de actividades
    public Cursor CursorActividad(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            return db.query("cat_actividad", null,null,null,null,null, null);
        }
        catch ( SQLException e)
        {
            // Log.e(TAG_ERROR, e.getMessage());
            return null;
        }
    }

    // **********************************
    // Función que obtiene las promociones de un formato
    public oPromocion[] ObtenPromociones(int pidformato) {
        int iNumPromociones = this.ObtenPromocionesFormato(pidformato);
        oPromocion[] promociones = new oPromocion[iNumPromociones];
        oPromocion promocion = null;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        sSql = "  Select" +
                " vw.idpromocion, " +
                " vw.idempresa, " +
                " vw.nombre, " +
                " upper(CASE WHEN (ifnull(vw.idproducto,0)=0) THEN vw.nombre ELSE cp.descripcion END) as Promo, " +
                " ifnull(vw.capacidad,'') as capacidad, " +
                " vw.canal, " +
                " vw.inicio, " +
                " vw.final, " +
                " ifnull(vw.periodo,'') as periodo, " +
                " vw.actividad, " +
                " vw.alcance, " +
                " vw.precioregular, " +
                " ifnull(vw.preciopromocion,'') as preciopromocion, " +
                " vw.idformato, " +
                " ifnull(vw.idproducto,0) as idproducto, cp.descripcion, cp.upc, vw.ruta " +
                " from vw_promociones vw left join cat_productos cp " +
                " on vw.idproducto = cp.idproducto " +
                " where idformato = " + pidformato;

        //Log.e(TAG_ERROR, "Consulta " + sSql);

        Cursor cursor = null;
        int i = 0;

        int	_idpromocion;
        int	_idempresa;
        String	_nombre;
        String	_promo;
        String	_capacidad;
        String	_canal;
        String	_inicio;
        String	_final;
        String	_periodo;
        String	_actividad;
        String	_alcance;
        String	_precioregular;
        String	_preciopromocion;
        int	_idformato;
        int	_idproducto;
        String	_descripcion;
        String	_upc;
        String _ruta;

        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                _idpromocion=cursor.getInt(0);
                _idempresa=cursor.getInt(1);
                _nombre=cursor.getString(2);
                _promo=cursor.getString(3);
                _capacidad=cursor.getString(4);
                _canal=cursor.getString(5);
                _inicio=cursor.getString(6);
                _final=cursor.getString(7);
                _periodo=cursor.getString(8);
                _actividad=cursor.getString(9);
                _alcance=cursor.getString(10);
                _precioregular=cursor.getString(11);
                _preciopromocion=cursor.getString(12);
                _idformato=cursor.getInt(13);
                _idproducto=cursor.getInt(14);
                _descripcion=cursor.getString(15);
                _upc=cursor.getString(16);
                _ruta=cursor.getString(17);

                promocion = new oPromocion(_idpromocion,_idempresa,  _nombre, _promo, _capacidad, _canal, _inicio,
                        _final, _periodo, _actividad, _alcance, _precioregular, _preciopromocion, _idformato,
                        _idproducto, _descripcion, _upc, _ruta);
                promociones[i] = promocion;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las promociones " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return promociones;
    }

    // **********************************
    // Función que obtiene las promociones de un formato
    public oPromocion ObtenPromocionTienda(int pidpromocion) {
        oPromocion promocion = null;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        sSql = "  Select" +
                " vw.idpromocion, " +
                " vw.idempresa, " +
                " vw.nombre, " +
                " upper(CASE WHEN (ifnull(vw.idproducto,0)=0) THEN vw.nombre ELSE cp.descripcion END) as Promo, " +
                " ifnull(vw.capacidad,'') as capacidad, " +
                " vw.canal, " +
                " vw.inicio, " +
                " vw.final, " +
                " ifnull(vw.periodo,'') as periodo, " +
                " vw.actividad, " +
                " vw.alcance, " +
                " vw.precioregular, " +
                " ifnull(vw.preciopromocion,'') as preciopromocion, " +
                " vw.idformato, " +
                " ifnull(vw.idproducto,0) as idproducto, cp.descripcion, cp.upc, vw.ruta " +
                " from vw_promociones vw left join cat_productos cp " +
                " on vw.idproducto = cp.idproducto " +
                " where idpromocion = " + pidpromocion;

        //Log.e(TAG_ERROR, "Consulta " + sSql);
        Cursor cursor = null;
        int i = 0;

        int	_idpromocion;
        int	_idempresa;
        String	_nombre;
        String	_promo;
        String	_capacidad;
        String	_canal;
        String	_inicio;
        String	_final;
        String	_periodo;
        String	_actividad;
        String	_alcance;
        String	_precioregular;
        String	_preciopromocion;
        int	_idformato;
        int	_idproducto;
        String	_descripcion;
        String	_upc;
        String	_ruta;

        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                _idpromocion=cursor.getInt(0);
                _idempresa=cursor.getInt(1);
                _nombre=cursor.getString(2);
                _promo=cursor.getString(3);
                _capacidad=cursor.getString(4);
                _canal=cursor.getString(5);
                _inicio=cursor.getString(6);
                _final=cursor.getString(7);
                _periodo=cursor.getString(8);
                _actividad=cursor.getString(9);
                _alcance=cursor.getString(10);
                _precioregular=cursor.getString(11);
                _preciopromocion=cursor.getString(12);
                _idformato=cursor.getInt(13);
                _idproducto=cursor.getInt(14);
                _descripcion=cursor.getString(15);
                _upc=cursor.getString(16);
                _ruta=cursor.getString(17);

                promocion = new oPromocion(_idpromocion,_idempresa,  _nombre, _promo, _capacidad, _canal, _inicio,
                        _final, _periodo, _actividad, _alcance, _precioregular, _preciopromocion, _idformato,
                        _idproducto, _descripcion, _upc, _ruta);
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener la promocion " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        // Log.e(TAG_ERROR, "Numero de tiendas devueltas " + i);
        return promocion;
    }

    // **********************************
    // Función para llenar el spinner de observaciones
    public oObs[] LlenaSpinnerObservaciones()
    {
        int iNumObs = this.ObtenRegistros(6);
        oObs[] arrobs = new oObs[iNumObs];

        SQLiteDatabase db = getReadableDatabase();
        String sSql;
        sSql = "Select _id, observaciones from cat_observa_precio  order by observaciones asc;";

        Log.e(TAG_ERROR, "Consulta " + sSql);

        Cursor cursor = null;
        int i = 0;
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String Obs = cursor.getString(1);
                oObs oobs = new oObs(id, Obs);
                arrobs[i] = oobs;
                i++;
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener las categorias de las productos " +  Resultado,Toast.LENGTH_LONG).show();
        } finally {
            assert cursor != null;
            db.close();
        }
        return arrobs;
    }

    // **********************************
    // Función que obtiene un producto
    public oProducto ObtenProducto(int pidproducto, int pidruta) {
        String p1upc = null;
        String p1descripcion= null;
        String p1descripcion1= null;
        String p1cantidadcaja= null;
        String p1categoria1= null;
        String p1categoria2= null;
        double p1precio=0;
        double p1precioreal=0;
        String sSql, sSql1, sSql2;

        SQLiteDatabase db = getReadableDatabase();
        sSql = "" +
                " Select cp.upc, cp.descripcion, cp.descripcion1, cp.cantidad_caja, cp.categoria1, cp.categoria2, " +
                " 0 as precio, 0 as precioreal " +
                " from cat_productos cp  where cp.idproducto = " + pidproducto;

        sSql1 = "" +
                " Select precio from producto_formato_precio " +
                " where idproducto = '" + pidproducto + "' " +
                " and idformato in ( " +
                " Select idformato from cat_rutas cr  where idruta = '" + pidruta + "' );";

        sSql2 = "Select v.precioreal from vw_producto_ruta_fecha v where idproducto = '" + pidproducto + "' and idruta = '" + pidruta + "';";

        Cursor cursor=null;
        Cursor cursor1=null;
        Cursor cursor2=null;
        cursor = db.rawQuery(sSql, null);
        oProducto ProductoResultado = null;
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                p1upc = cursor.getString(0);
                p1descripcion = cursor.getString(1);
                p1descripcion1 = cursor.getString(2);
                p1cantidadcaja = cursor.getString(3);
                p1categoria1 = cursor.getString(4);
                p1categoria2 = cursor.getString(5);
                // p1precio = cursor.getDouble(6);
                // p1precioreal = cursor.getDouble(7);
                cursor.moveToNext();
            }
            cursor.close();
            // ************************************
            cursor1 = db.rawQuery(sSql1, null);
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()){
                p1precio = cursor1.getDouble(0);
                cursor1.moveToNext();
            }
            cursor1.close();
            // ************************************
            cursor2 = db.rawQuery(sSql2, null);
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()){
                p1precioreal = cursor2.getDouble(0);
                cursor2.moveToNext();
            }
            cursor2.close();
            // ************************************
            ProductoResultado = new oProducto(p1upc, pidproducto, p1descripcion, p1descripcion1, p1cantidadcaja, p1categoria1, p1categoria2, p1precio, p1precioreal, pidruta);
            return ProductoResultado;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            String sTextoResultado = " Error en la consulta del producto " + sSql + ", " + Resultado;
            Toast.makeText(this.contexto, sTextoResultado, Toast.LENGTH_LONG).show();
            Log.e(TAG_ERROR, sTextoResultado);
            return null;
        } finally {
            assert cursor != null;
            assert cursor1 != null;
            assert cursor2 != null;
            db.close();
        }
    }

    // **********************************
    // Obtiene datos para subir foto de la tabla almacenfotos
    public int Colocarfoto() {
        int _id;
        int _idpromotor;
        double _latitud;
        double _longitud;
        String _fechahora;
        int _idoperacion;
        String _idusuario;
        int _idruta;
        String _imagen;
        int i = 0;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        String sSql = "Select distinct id, idpromotor, latitud, longitud, fechahora, idoperacion, idusuario, idruta, imagen  " +
                " from almacenfotos " +
                " where idoperacion<5 " +
                " order by id asc limit 1;";
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        //oFoto estafoto;
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _id = cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _fechahora = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idusuario = cursor.getString(6);
                _idruta = cursor.getInt(7);
                _imagen = cursor.getString(8);

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen
                uploadImage(
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_fechahora),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idusuario),
                        String.valueOf(_idruta),
                        String.valueOf(_imagen)
                );

                i++;
                // *****************************
                // Borrando el registro recien colocado
                this.BorraFotoEnviada(_id);
                // *****************************
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tab1a  al obtener el arreglo de tiendas " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
            // Log.e(TAG_ERROR,Resultado);
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
            db1.close();
        }
    }

    //***********************
    // Upload image function
    public void uploadImage(
            String _idpromotor,
            String _latitud,
            String _longitud,
            String _fechahora,
            String _idoperacion,
            String _idusuario,
            String _idruta,
            String _imagen
    ) {
        class UploadImage extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }


            @Override
            protected String doInBackground(String... params) {
                String idpromotor = params[0];
                String latitud = params[1];
                String longitud = params[2];
                String fechahora = params[3];
                String idoperacion = params[4];
                String idusuario = params[5];
                String idruta = params[6];
                String imagen100 = params[7];

                String uploadImage100 = imagen100;

                HashMap<String, String> data = new HashMap<>();

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp = versionName + ":" + versionCode;

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(latitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(longitud));
                data.put(UPLOAD_IDUSUARIO, idusuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idruta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage100);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "1");
                // Log.e(TAG_ERROR, "-- Enviando Imagen ");

                return rh.sendPostRequest(UPLOAD_URL, data);
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(_idpromotor, _latitud, _longitud, _fechahora, _idoperacion, _idusuario, _idruta, _imagen);
    }

    // **********************************
    // Obtiene datos para subir foto de la tabla almacenfotos
    public int ColocaPreciosCambiados()
    {
        int _idproducto;
        int _idruta;
        int _idpromotor;
        double _precioreal;
        int _invinicial;
        int _invfinal;
        int _idobs;
        int i = 0;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        String sSql = "   Select distinct idruta, idproducto, idpromotor,  precioreal, invinicial, invfinal, idobs " +
                        " from vw_producto_ruta_fecha " +
                        " where mod = 1 order by fda asc limit 1;";
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);

        try {
            // ************************************
            cursor.moveToFirst();
            while ( !cursor.isAfterLast()) {
                _idruta= cursor.getInt(0);
                _idproducto = cursor.getInt(1);
                _idpromotor = cursor.getInt(2);
                _precioreal = cursor.getDouble(3);
                _invinicial = cursor.getInt(4);
                _invfinal = cursor.getInt(5);
                _idobs = cursor.getInt(6);

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen
                subirPreciosCambiados(
                        String.valueOf(_idruta),
                        String.valueOf(_idproducto),
                        String.valueOf(_idpromotor),
                        String.valueOf(_precioreal),
                        String.valueOf(_invinicial),
                        String.valueOf(_invfinal),
                        String.valueOf(_idobs)
                );
                i++;
                // ******************************
                // Borrando el registro recien colocado
                String sAct = "  Delete from  vw_producto_ruta_fecha " +
                        " where  idproducto = '" + _idproducto + "' " + " and idpromotor = '" + _idpromotor + "' " +
                       " and idruta = '" + _idruta + "' and DATE(fda) = DATE(CURRENT_DATE)";
                db1.execSQL(sAct);
                // *****************************
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tab1a  al obtener el arreglo de tiendas " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
            // Log.e(TAG_ERROR,Resultado);
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
            db1.close();
        }
    }

    //***********************
    // Upload image function
    public void subirPreciosCambiados(
            String _idruta,
            String _idproducto,
            String _idpromotor,
            String _precioreal,
            String _invinicial,
            String _invfinal,
            String _idobs
    ) {
        class subirPreciosCambiados extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }


            @Override
            protected String doInBackground(String... params) {
                String pidruta      = params[0];
                String pidproducto  = params[1];
                String pidpromotor  = params[2];
                String pprecioreal  = params[3];
                String pinvinicial  = params[4];
                String pinvfinal    = params[5];
                String pidobs       = params[6];

                HashMap<String, String> data = new HashMap<>();

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                data.put(TAG_IDRUTA, String.valueOf(pidruta));
                data.put(TAG_IDPRODUCTO, String.valueOf(pidproducto));
                data.put(TAG_IDPROMOTOR, String.valueOf(pidpromotor));
                data.put(TAG_PRECIO, String.valueOf(pprecioreal));
                data.put(TAG_INVINICIAL, String.valueOf(pinvinicial));
                data.put(TAG_INVFINAL, String.valueOf(pinvfinal));
                data.put(TAG_IDOBS, String.valueOf(pidobs));
                // TODO /CatalogoProductos/createProductoRutaFecha1.php
                String PRODUCTO_URL = TAG_SERVIDOR + "/CatalogoProductos/createProductoRutaFecha1.php";
                // Log.e(TAG_ERROR, "-- Enviando datos de precios");
                return rh.sendPostRequest(PRODUCTO_URL,data);

            }
        }

        subirPreciosCambiados subirprecios = new subirPreciosCambiados();
        subirprecios.execute(_idruta, _idproducto,  _idpromotor, _precioreal, _invinicial, _invfinal, _idobs);
    }

    // **********************************
    // Obtiene datos para subir foto de la tabla almacenfotos de los datos de la competencia
    public void guardarImagenProducto(int idproducto, Bitmap bitmap){
        // tamaño del baos depende del tamaño de tus imagenes en promedio
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 99 , baos);
        byte[] blob = baos.toByteArray();
        // aqui tenemos el byte[] con el imagen comprimido, ahora lo guardemos en SQLite
        SQLiteDatabase db = getWritableDatabase();

        String sql = "Update cat_productos set img = '" + blob + "' where idproducto =  " + idproducto + ";";
        Log.e(TAG_ERROR, sql);
        db.execSQL(sql);
        db.close();
    }

    // **********************************
    // Obtiene imágen producto con el idproducto
    public boolean HayImagenProducto(int idproducto){
        SQLiteDatabase db = getReadableDatabase();

        String sql = " SELECT img FROM cat_productos " +
                "      WHERE idproducto = " + idproducto;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        try {
            Bitmap bitmap = null;
            if (cursor.moveToFirst()) {
                byte[] blob = cursor.getBlob(0);
                Log.e(TAG_ERROR, "** longitud " + String.valueOf(blob.length));
                if ((blob.length)>0)
                {
                    return true;
                }
                else{
                    return false;
                }
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                return false;
            }
            db.close();
            return false;
        }
        catch (NullPointerException n){
            cursor.close();
            return false;
        }
    }

    // **********************************
    // Obtiene imágen producto con el idproducto
    public Bitmap obtenerImagenProducto(int idproducto){
        SQLiteDatabase db = getReadableDatabase();

        String sql = " SELECT img FROM cat_productos " +
                "      WHERE idproducto = " + idproducto;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        try {
            Bitmap bitmap = null;
            if (cursor.moveToFirst()) {
                if (cursor.getCount()>0) {
                    byte[] blob = cursor.getBlob(0);
                    Log.e(TAG_ERROR, "** longitud " + String.valueOf(blob.length));
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
                else
                    return null;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                return null;
            }
            db.close();
            return bitmap;
        }
        catch (NullPointerException n){
            cursor.close();
            return null;
        }
    }

    // **********************************
    // Obtiene datos para subir foto de la tabla almacenfotos de los datos de la competencia
    public int ColocaCompetencia()
    {
        // Log.e(TAG_ERROR, "** Dentro de coloca competencia **");
        int _id;
        int _idpromotor;
        int _idoperacion;
        int _idruta;
        int _idempaque, _demostrador, _exhibidor, _emplaye;
        int _idcompetencia;
        int i = 0;
        double _latitud;
        double _longitud;
        double _precio;
        String _idusuario;
        String _imagen;
        String _fechahora;
        String _producto;
        String _presentacion;
        String _actividadbtl;
        String _canjes;
        int _idfoto;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();

        String sSql = "Select distinct af.id, af.idpromotor, af.latitud, af.longitud, af.fechahora, af.idoperacion, af.idusuario, af.idruta, af.imagen  " +
                " , c.producto, c.precio, c.presentacion, c.idempaque, c.demostrador, " +
                " c.exhibidor, c.emplaye, c.actividadbtl, c.canjes, c.idcompetencia, c.idfoto " +
                " from almacenfotos af inner join competencia c on af.id = c.idfoto" +
                " where af.idoperacion=5 " +
                " order by af.id asc limit 1;";
        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        //oFoto estafoto;
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _id = cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _fechahora = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idusuario = cursor.getString(6);
                _idruta = cursor.getInt(7);
                _imagen = cursor.getString(8);
                _producto = cursor.getString(9);
                _precio = cursor.getDouble(10);
                _presentacion = cursor.getString(11);
                _idempaque = cursor.getInt(12);
                _demostrador = cursor.getInt(13);
                _exhibidor = cursor.getInt(14);
                _emplaye = cursor.getInt(15);
                _actividadbtl = cursor.getString(16);
                _canjes = cursor.getString(17);
                _idcompetencia = cursor.getInt(18);
                _idfoto = cursor.getInt(19);

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen
                uploadImagenCompetencia(
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_fechahora),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idusuario),
                        String.valueOf(_idruta),
                        String.valueOf(_imagen),
                        String.valueOf(_producto),
                        String.valueOf(_precio),
                        String.valueOf(_presentacion),
                        String.valueOf(_idempaque),
                        String.valueOf(_demostrador),
                        String.valueOf(_exhibidor),
                        String.valueOf(_emplaye),
                        String.valueOf(_actividadbtl),
                        String.valueOf(_canjes)
                );
                i++;
                // *****************************
                // Borrando el registro recien colocado
                this.BorraFotoEnviada(_id);
                String sBorrado0 = "Delete from competencia where idcompetencia = " + _idcompetencia + ";";
                db1.execSQL(sBorrado0);
                // *****************************
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tab1a  al obtener el arreglo de tiendas " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
            // Log.e(TAG_ERROR,Resultado);
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db1.close();
            db.close();
        }
    }

    //***********************
    // Upload image Competencia function
    public void uploadImagenCompetencia(
            String _idpromotor,
            String _latitud,
            String _longitud,
            String _fechahora,
            String _idoperacion,
            String _idusuario,
            String _idruta,
            String _imagen,
            String _producto,
            String _precio,
            String _presentacion,
            String _idempaque,
            String _demostrador,
            String _exhibidor,
            String _emplaye,
            String _actividadbtl,
            String _canjes
    ) {
        class UploadImageCompetencia extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }


            @Override
            protected String doInBackground(String... params) {
                String idpromotor = params[0];
                String latitud = params[1];
                String longitud = params[2];
                String fechahora = params[3];
                String idoperacion = params[4];
                String idusuario = params[5];
                String idruta = params[6];
                String imagen100 = params[7];
                String producto = params[8];
                String precio = params[9];
                String presentacion = params[10];
                String idEmpaque = params[11];
                String demostrador = params[12];
                String exhibidor = params[13];
                String emplaye = params[14];
                String actividadbtl = params[15];
                String canjes = params[16];

                String uploadImage100 = imagen100;

                HashMap<String, String> data = new HashMap<>();

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp = versionName + ":" + versionCode;

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(latitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(longitud));
                data.put(UPLOAD_IDUSUARIO, idusuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idruta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage100);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "1");

                data.put(TAG_producto, String.valueOf(producto));
                data.put(TAG_PRECIO, String.valueOf(precio));
                data.put(TAG_PRESENTACION, String.valueOf(presentacion));
                data.put(TAG_IDEMPAQUE, String.valueOf(idEmpaque));

                data.put(TAG_IDEMOSTRADOR, String.valueOf(demostrador));
                data.put(TAG_IEXHIBIDOR, String.valueOf(exhibidor));
                data.put(TAG_IEMPLAYE, String.valueOf(emplaye));
                data.put(TAG_ACTIVIDADBTL, String.valueOf(actividadbtl));
                data.put(TAG_CANJES, String.valueOf(canjes));

                return rh.sendPostRequest(UPLOAD_COMPETENCIA,data);

            }
        }

        UploadImageCompetencia ui = new UploadImageCompetencia();
        ui.execute(_idpromotor, _latitud, _longitud, _fechahora, _idoperacion, _idusuario, _idruta, _imagen,
                _producto, _precio, _presentacion, _idempaque,_demostrador, _exhibidor,_emplaye, _actividadbtl,_canjes);
    }

    // **********************
    // Muestra elementos almacenados
    public void muestradatosAlmacenados() {
        int iCuenta = this.ObtenRegistros(0);
        int iCuentaPreciosCambiados = this.ObtenRegistros(9);
        int iCuentaRegistrosCompetencia = this.ObtenRegistros(10);
        int iCuentaPromociones = this.ObtenRegistros(12);
        int iCuentaCaducidad = this.ObtenRegistros(14);
        int iCuentaErrores = this.ObtenRegistros(16);
        int iCuentaCompetenciaPromocion = this.ObtenRegistros(18);
        int iCuentaCanjes = this.ObtenRegistros(20);

        String sMensaje = "Usted tiene " + "\n" + String.valueOf(iCuenta) + " imágenes almacenadas "  + "\n" +
                iCuentaPreciosCambiados + " precios cambiados, "  + "\n" +
                iCuentaPromociones + " promociones, "  + "\n" +
                iCuentaRegistrosCompetencia + " datos de competencia,"  + "\n" +
                iCuentaCaducidad  + " datos de caducidad, "  + "\n" +
                iCuentaCompetenciaPromocion + " datos de competencia promoción, "  + "\n" +
                iCuentaErrores  + " datos de error(es) "  + "\n" +
                iCuentaCanjes + " datos de canjes";

        Toast.makeText(this.contexto, sMensaje, Toast.LENGTH_LONG).show();
    }


    // **********************************
    // Obtiene datos para subir la tabla errores
    public int ColocaErrores()
    {
        int i = 0;
        String _fabricante;
        String _marca;
        String _modelo;
        String _board;
        String _hardware;
        String _serie;
        String _uid;
        String _android_id;
        String _resolucion;
        String _tamaniopantalla;
        String _densidad;
        String _bootloader;
        String _user_value;
        String _host_value;
        String _version;
        String _api_value;
        String _build_id;
        String _build_time;
        String _fingerprint;
        String _usuario;
        String _seccion;
        String _error;
        String _fechahora;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        String sSql = "Select fabricante, marca, modelo, board ,hardware ,serie ,uid ,android_id ,resolucion ,tamaniopantalla ,densidad ,bootloader ,user_value ,host_value ,version ,api_value ,build_id ,build_time ,fingerprint ,usuario, seccion, error ,fechahora from errores where procesado = 0;";

        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _fabricante= cursor.getString(0);
                _marca= cursor.getString(1);
                _modelo= cursor.getString(2);
                _board= cursor.getString(3);
                _hardware= cursor.getString(4);
                _serie= cursor.getString(5);
                _uid= cursor.getString(6);
                _android_id= cursor.getString(7);
                _resolucion= cursor.getString(8);
                _tamaniopantalla= cursor.getString(9);
                _densidad= cursor.getString(10);
                _bootloader= cursor.getString(11);
                _user_value= cursor.getString(12);
                _host_value= cursor.getString(13);
                _version= cursor.getString(14);
                _api_value= cursor.getString(15);
                _build_id= cursor.getString(16);
                _build_time= cursor.getString(17);
                _fingerprint= cursor.getString(18);
                _usuario= cursor.getString(19);
                _seccion= cursor.getString(20);
                _error= cursor.getString(21);
                _fechahora= cursor.getString(22);

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                cargaErrores(
                        _fabricante,
                        _marca,
                        _modelo,
                        _board,
                        _hardware,
                        _serie,
                        _uid,
                        _android_id,
                        _resolucion,
                        _tamaniopantalla,
                        _densidad,
                        _bootloader,
                        _user_value,
                        _host_value,
                        _version,
                        _api_value,
                        _build_id,
                        _build_time,
                        _fingerprint,
                        _usuario,
                        _seccion,
                        _error,
                        _fechahora
                );
                i++;

                cursor.moveToNext();
            }
            cursor.close();
            // *****************************
            // Borrando el registro recien colocado

            String sBorrado = "Delete from errores where procesado = 0;";
            db1.execSQL(sBorrado);
            // Log.e(TAG_ERROR, sBorrado);
            // *****************************
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al consultar errores " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            assert cursor != null;
            db.close();
            db1.close();
        }
    }

    //***********************
    // Función utilizada para guardar los errores de la App
    private void cargaErrores(
            String _fabricante,
            String _marca,
            String _modelo,
            String _board,
            String _hardware,
            String _serie,
            String _uid,
            String _android_id,
            String _resolucion,
            String _tamaniopantalla,
            String _densidad,
            String _bootloader,
            String _user_value,
            String _host_value,
            String _version,
            String _api_value,
            String _build_id,
            String _build_time,
            String _fingerprint,
            String _usuario,
            String _seccion,
            String _error,
            String _fechahora
    )
    {
        class EstableceErrores extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String  fabricante= params[0];
                String  marca= params[1];
                String  modelo= params[2];
                String  board= params[3];
                String  hardware= params[4];
                String  serie= params[5];
                String  uid= params[6];
                String  android_id= params[7];
                String  resolucion= params[8];
                String  tamaniopantalla= params[9];
                String  densidad= params[10];
                String  bootloader= params[11];
                String  user_value= params[12];
                String  host_value= params[13];
                String  version= params[14];
                String  api_value= params[15];
                String  build_id= params[16];
                String  build_time= params[17];
                String  fingerprint= params[18];
                String  usuario= params[19];
                String  seccion = params[20];
                String  error= params[21];
                String  fechahora= params[22];

                data.put(UPLOAD_FABRICANTE, fabricante);
                data.put(UPLOAD_MARCA,marca);
                data.put(UPLOAD_MODELO,modelo);
                data.put(UPLOAD_BOARD,board);
                data.put(UPLOAD_HARDWARE,hardware);
                data.put(UPLOAD_SERIE,serie);
                data.put(UPLOAD_UID,uid);
                data.put(UPLOAD_ANDROID_ID,android_id);
                data.put(UPLOAD_RESOLUCION,resolucion);
                data.put(UPLOAD_TAMANIOPANTALLA,tamaniopantalla);
                data.put(UPLOAD_DENSIDAD,densidad);
                data.put(UPLOAD_BOOTLOADER,bootloader);
                data.put(UPLOAD_USER_VALUE,user_value);
                data.put(UPLOAD_HOST_VALUE,host_value);
                data.put(UPLOAD_VERSION,version);
                data.put(UPLOAD_API_VALUE,api_value);
                data.put(UPLOAD_BUILD_ID,build_id);
                data.put(UPLOAD_BUILD_TIME,build_time);
                data.put(UPLOAD_FINGERPRINT,fingerprint);
                data.put(UPLOAD_USUARIO,usuario);
                data.put(UPLOAD_SECCION,seccion);
                data.put(UPLOAD_ERROR,error);
                data.put(UPLOAD_FECHAHORA,fechahora);

                return rh.sendPostRequest(UPLOAD_ERRORES,data);
            }
        }

        EstableceErrores ui = new EstableceErrores();
        ui.execute(
                _fabricante,
                _marca,
                _modelo,
                _board,
                _hardware,
                _serie,
                _uid,
                _android_id,
                _resolucion,
                _tamaniopantalla,
                _densidad,
                _bootloader,
                _user_value,
                _host_value,
                _version,
                _api_value,
                _build_id,
                _build_time,
                _fingerprint,
                _usuario,
                _seccion,
                _error,
                _fechahora);
    }

    // **********************************
    // Obtiene datos para subir foto de la tabla caducidad
    public int ColocaCaducidad()
    {
        // Log.e(TAG_ERROR, "** Dentro de coloca caducidad **");

        int i = 0;
        int _id;
        int _idpromotor;
        int _idcaducidad;
        int _idproducto;
        int _piezas;
        int _idoperacion;
        int _idruta;
        double _latitud;
        double _longitud;
        String _fechahora;
        String _idusuario;
        String _imagen;
        String _lote;
        String _caducidad;
        int _idfoto;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        String sSql = "Select distinct af.id, af.idpromotor, af.latitud, af.longitud, af.fechahora, af.idoperacion, af.idusuario, af.idruta, af.imagen  " +
                " , c.idproducto, c.lote, c.caducidad, c.piezas, c.idcaducidad, c.idfoto" +
                " from almacenfotos af inner join caducidad c on af.id = c.idfoto" +
                " where af.idoperacion=6 " +
                " order by af.id asc limit 1;";

        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        //oFoto estafoto;
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _id = cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _fechahora = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idusuario = cursor.getString(6);
                _idruta = cursor.getInt(7);
                _imagen = cursor.getString(8);
                _idproducto = cursor.getInt(9);
                _lote = cursor.getString(10);
                _caducidad = cursor.getString(11);
                _piezas = cursor.getInt(12);
                _idcaducidad = cursor.getInt(13);
                _idfoto = cursor.getInt(14);

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen
                cargaCaducidad(
                        String.valueOf(_id),
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_fechahora),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idusuario),
                        String.valueOf(_idruta),
                        String.valueOf(_imagen),
                        String.valueOf(_idproducto),
                        String.valueOf(_lote),
                        String.valueOf(_caducidad),
                        String.valueOf(_piezas)
                );
                i++;
                // *****************************
                // Borrando el registro recien colocado
                this.BorraFotoEnviada(_idfoto);
                String sBorrado = "Delete from caducidad where idcaducidad = " + _idcaducidad + ";";
                db1.execSQL(sBorrado);
                // *****************************
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al consultar caducidad " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            assert cursor != null;
            db.close();
            db1.close();
        }
    }

    //***********************
    // Función utilizada para guardar la caducidad del producto
    private void cargaCaducidad(
            String _id,
            String _idpromotor,
            String _latitud,
            String _longitud,
            String _fechahora,
            String _idoperacion,
            String _idusuario,
            String _idruta,
            String _imagen,
            String _idproducto,
            String _lote,
            String _caducidad,
            String _piezas
    )
    {
        class EstableceCaducidad extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String id           = params[0];
                String idpromotor   = params[1];
                String latitud      = params[2];
                String longitud     = params[3];
                String fechahora    = params[4];
                String idoperacion   = params[5];
                String idusuario   = params[6];
                String idruta       = params[7];
                String imagen       = params[8];
                String idproducto  = params[9];
                String lote        = params[10];
                String caducidad    = params[11];
                String piezas       = params[12];

                Calendar c = Calendar.getInstance();
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //String fechahora1 = sdf.format(fechahora);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String sVerApp =  versionName + ":" + versionCode;

                data.put(UPLOAD_idproducto, String.valueOf(idproducto));
                data.put(UPLOAD_lote, String.valueOf(lote));
                data.put(UPLOAD_caducidad, caducidad);
                data.put(UPLOAD_piezas, String.valueOf(piezas));

                data.put(UPLOAD_IDRUTA, String.valueOf(idruta));
                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(latitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(longitud));
                data.put(UPLOAD_IDUSUARIO, idusuario);
                data.put(UPLOAD_IDOPERACION, idoperacion);
                //data.put(UPLOAD_FECHAHORA, fechahora1);

                data.put(UPLOAD_IMAGEN, imagen);
                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "1");

                return rh.sendPostRequest(UPLOAD_CADUCIDAD,data);

            }

        }

        EstableceCaducidad ui = new EstableceCaducidad();
        ui.execute(_id, _idpromotor, _latitud, _longitud, _fechahora, _idoperacion, _idusuario, _idruta, _imagen, _idproducto, _lote, _caducidad, _piezas);

    }


    // **********************************
    // TODO Método para insertar error
    public int inserta_error(oInfoDispositivo oinfo) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;

        Date fechahora = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechahora1 = sdf.format(fechahora);

        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        try {
            sSql = "Insert into errores (fabricante, marca, modelo ,board ,hardware ,serie ,uid ,android_id ,resolucion ,tamaniopantalla ,densidad ," +
                    "bootloader ,user_value ,host_value ,version ,api_value ,build_id ,build_time ,fingerprint ,usuario, seccion, error ,fechahora , procesado ) " +
            " values ('" + oinfo.get_fabricante() + "','" +  oinfo.get_marca() + "','" + oinfo.get_modelo() + "', '" + oinfo.get_board() +
                    "', '" + oinfo.get_hardware() +"','" + oinfo.get_serie() +"','" + oinfo.get_uid() +
                    "', '" + oinfo.get_android_id() + "','" +  oinfo.get_resolucion() + "','" + oinfo.get_tamaniopantalla() +
                    "', '" + oinfo.get_densidad() + "','" + oinfo.get_bootloader() +"','" + oinfo.get_user_value() +"','" + oinfo.get_host_value() +
                    "', '" + oinfo.get_version() + "','" +  oinfo.get_api_level() + "','" + oinfo.get_build_id() +
                    "', '" + oinfo.get_build_time() + "','" + oinfo.get_fingerprint() +"','" + oinfo.get_usuario() +"','" + oinfo.get_seccion()
                    +"','" + oinfo.get_error() + "', '" + fechahora1 + "','0');";
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la tabla error " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Método para insertar error1 sin datos de actividad ni contexto
    public int inserta_error1(String sUsuario, Exception e, String sSeccion) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString();

        Date fechahora = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechahora1 = sdf.format(fechahora);

        //TelephonyManager tManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        oInfoDispositivo oinfo = new oInfoDispositivo();
        DisplayMetrics dm = new DisplayMetrics();
        //activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        double x = Math.pow(Resources.getSystem().getDisplayMetrics().widthPixels / dm.xdpi, 2);
        double y = Math.pow(Resources.getSystem().getDisplayMetrics().heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        DecimalFormat df2 = new DecimalFormat("#,###,###,##0");
        String rounded = df2.format((double) screenInches);
        double densityDpi = (int) (dm.density * 160f);

        oinfo.set_fabricante(Build.MANUFACTURER);
        oinfo.set_marca(Build.BRAND);
        oinfo.set_modelo(Build.MODEL);
        oinfo.set_board(Build.BOARD);
        oinfo.set_hardware(Build.HARDWARE);
        oinfo.set_serie(Build.SERIAL);
        oinfo.set_android_id(Settings.Secure.getString(contexto.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        oinfo.set_resolucion(Resources.getSystem().getDisplayMetrics().heightPixels + " * " + Resources.getSystem().getDisplayMetrics().widthPixels + " Pixels");
        oinfo.set_tamaniopantalla(rounded + " Inches");
        oinfo.set_densidad(String.valueOf(densityDpi) + " dpi");
        oinfo.set_bootloader(Build.BOOTLOADER);
        oinfo.set_user_value(Build.USER);
        oinfo.set_host_value(Build.HOST);
        oinfo.set_version(Build.VERSION.RELEASE);
        oinfo.set_api_value(Build.VERSION.SDK_INT + "");
        oinfo.set_build_id(Build.ID);
        oinfo.set_build_time(Build.TIME + "");
        oinfo.set_fingerprint(Build.FINGERPRINT);
        oinfo.set_usuario(sUsuario);
        oinfo.set_seccion(sSeccion);
        oinfo.set_error(sStackTrace);

        SQLiteDatabase db = getWritableDatabase();
        // db.beginTransaction();
        try {
            sSql = "Insert into errores (fabricante, marca, modelo ,board ,hardware ,serie ,uid ,android_id ,resolucion ,tamaniopantalla ,densidad ," +
                    "bootloader ,user_value ,host_value ,version ,api_value ,build_id ,build_time ,fingerprint ,usuario, seccion, error ,fechahora , procesado ) " +
                    " values ('" + oinfo.get_fabricante() + "','" +  oinfo.get_marca() + "','" + oinfo.get_modelo() + "', '" + oinfo.get_board() +
                    "', '" + oinfo.get_hardware() +"','" + oinfo.get_serie() +"','" + oinfo.get_uid() +
                    "', '" + oinfo.get_android_id() + "','" +  oinfo.get_resolucion() + "','" + oinfo.get_tamaniopantalla() +
                    "', '" + oinfo.get_densidad() + "','" + oinfo.get_bootloader() +"','" + oinfo.get_user_value() +"','" + oinfo.get_host_value() +
                    "', '" + oinfo.get_version() + "','" +  oinfo.get_api_level() + "','" + oinfo.get_build_id() +
                    "', '" + oinfo.get_build_time() + "','" + oinfo.get_fingerprint() +"','" + oinfo.get_usuario() +"','" + oinfo.get_seccion()
                    +"','" + oinfo.get_error() + "', '" + fechahora1 + "','0');";
            db.execSQL(sSql);
            // db.setTransactionSuccessful();
            return 1;
        } catch (Exception e1) {
            String Resultado = e1.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al insertar en la tabla error " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            // Log.e(TAG_INFO, "conclusion de transacciòn en la tabla listatiendas");
            // db.endTransaction();
            db.close();
        }
    }

    // **********************************
    // Obtiene datos para subir foto de la tabla almacenfotos de los datos de la competencia
    public int ColocaPromocion()
    {
        // Log.e(TAG_ERROR, "** Dentro de coloca competencia **");
        int _idpromocion;
        int _idpromotor;
        int _idruta;
        String _fecha;
        int _aplica;

        int i = 0;
        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        String sSql = "Select idpromocion, idpromotor, idruta, fecha, aplica from promociones_tiendas where mod = 1";
        Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        //oFoto estafoto;
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _idpromocion = cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _idruta = cursor.getInt(2);
                _fecha = cursor.getString(3);
                _aplica = cursor.getInt(4);

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen

                cargaPromocion(
                        String.valueOf(_idpromocion),
                        String.valueOf(_idpromotor),
                        String.valueOf("1"),
                        String.valueOf(_idruta),
                        String.valueOf(_aplica)
                );
                i++;
                // *****************************
                // Borrando el registro recien colocado
                String sBorrado = "Delete from promociones_tiendas where idpromocion = " + _idpromocion + ";";
                db1.execSQL(sBorrado);
                Log.e(TAG_ERROR, sBorrado);
                // *****************************
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al obtener promociones " + Resultado, Toast.LENGTH_LONG).show();
            return 0;
            // Log.e(TAG_ERROR,Resultado);
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
            db1.close();
        }
    }

    //***********************
    // Función utilizada para guardar la promoción
    private void cargaPromocion(
        String _idpromocion,
        String _idpromotor,
        String _idempresa,
        String _idruta,
        String _idaplica
        )
    {
        class EstablecePromocion extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromocion = params[0];
                String idpromotor = params[1];
                String idempresa = params[2];
                String idruta = params[3];
                String idaplica= params[4];

                data.put(TAG_IDPROMOCION, String.valueOf(idpromocion));
                data.put(TAG_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(TAG_IDEMPRESA, String.valueOf(idempresa));
                data.put(TAG_IDRUTA, String.valueOf(idruta));
                data.put(TAG_APLICA, String.valueOf(idaplica));
                return rh.sendPostRequest(PROMOCION_URL, data);
            }

        }

        EstablecePromocion ui = new EstablecePromocion();
        ui.execute(_idpromocion, _idpromotor, _idempresa, _idruta, _idaplica);

    }

    // **********************************
    // TODO Coloca competencia promoción en modo desconectado etapa 1
    // Obtiene datos para subir foto de la tabla competencia promocion
    // Los coloca en la web cuando la conexión este disponible
    public int ColocaCompetenciaPromocion()
    {
        int i = 0;
        int _idpromotor;
        double _latitud;
        double _longitud;
        String _fechahora="";
        String _idusuario = "";
        int _idruta = 0;
        String _image = "";
        String _image1 = "";
        int _por_participa = 0;
        int _idoperacion= 8;
        int _no_frentes= 0;
        int _con_sin_participacion= 0;
        int _por_descuento= 0;
        String _comentario = "";
        int _idproducto= 0;
        float _precio= 0;
        int _idcompetenciapromo= 0;
        String _sVerApp;
        int _idfoto = 0;
        int _idfoto1 = 0;

        SQLiteDatabase db = getReadableDatabase();
        //********************************************
        // Primer cursor
        String sSql = "Select distinct  c.idcompetenciapromo, af.idpromotor, af.latitud, af.longitud, af.idusuario, 8 as idoperacion, af.idruta, " +
                " af.fechahora, af.imagen as image,  '' as image1 " +
                " ,c.por_participacion, c.no_frentes, c.con_sin_participacion, 0 as por_descuento, " +
                " c.comentarios, c.idproducto, c.precio, c.idfoto, c.idfoto1" +
                " from competencia_promocion c  " +
                " inner join almacenfotos af on af.id = c.idfoto " +
                " order by c.idcompetenciapromo asc limit 1;";

        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _idcompetenciapromo= cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _idusuario = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idruta = cursor.getInt(6);
                _fechahora = cursor.getString(7);
                _image = cursor.getString(8);
                //_image1 = cursor.getString(9);
                _por_participa = cursor.getInt(10);
                _no_frentes = cursor.getInt(11);
                _con_sin_participacion = cursor.getInt(12);
                _por_descuento = 0;
                _comentario = cursor.getString(14);
                _idproducto = cursor.getInt(15);
                _precio= cursor.getFloat(16);
                _idfoto = cursor.getInt(17);
                _idfoto1= cursor.getInt(18);
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                _sVerApp =  versionName + ":" + versionCode;

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen
                cargaCompetenciaPromocion(
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_idusuario),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idruta),
                        String.valueOf(_fechahora),
                        String.valueOf(_image),
                        String.valueOf(_image1),
                        String.valueOf(_con_sin_participacion),
                        String.valueOf(_por_participa),
                        String.valueOf(_no_frentes),
                        String.valueOf(_por_descuento),
                        _comentario,
                        String.valueOf(_idproducto),
                        String.valueOf(_precio),
                        _sVerApp
                );
                i++;

                // *****************************
                cursor.moveToNext();
            }
            cursor.close();

            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Log.e(TAG_ERROR, " Error en tabla al consultar competencia_promocion c2" + Resultado);
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al consultar competencia_promocion c2" + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            assert cursor != null;
            db.close();
        }
    }

    //***********************
    // Función utilizada para guardar competencia promoción
    private void cargaCompetenciaPromocion(
            String _idpromotor,
            String _pLatitud,
            String _pLongitud,
            String _pName,
            String _idoperacion,
            String _idRuta,
            String _fechahora,
            String _uploadImage1,
            String _uploadImage2,
            String _iconPromo,
            String _por_participa,
            String _no_frentes,
            String _por_descuento,
            String _comentario,
            String _idproducto,
            String _precio,
            String _sVerApp
    )
    {
        class EstableceCompetenciaPromocion extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromotor   = params[0];
                String pLatitud     = params[1];
                String pLongitud    = params[2];
                String idUsuario        = params[3];
                String idoperacion  = params[4];
                String idRuta      = params[5];
                String fechahora   = params[6];
                String uploadImage1= params[7];
                String uploadImage2= params[8];
                String iconPromo   = params[9];
                String por_participa= params[10];
                String no_frentes  = params[11];
                String por_descuento  = params[12];
                String comentario  = params[13];
                String idproducto  = params[14];
                String precio      = params[15];
                String sVerApp      = params[16];

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contexto.getApplicationContext());
                idUsuario = preferences.getString(TAG_USUARIO, idUsuario);

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);

                data.put(UPLOAD_CON_SIN_PARTICIPACION, String.valueOf(iconPromo));
                data.put(UPLOAD_POR_PARTICIPACION, String.valueOf(por_participa));
                data.put(UPLOAD_NO_FRENTES, String.valueOf(no_frentes));
                data.put(UPLOAD_POR_DESCUENTO, String.valueOf(por_descuento));
                data.put(UPLOAD_COMENTARIOS, String.valueOf(comentario));
                data.put(UPLOAD_IDPRODUCTO, String.valueOf(idproducto));
                data.put(UPLOAD_PRECIO, String.valueOf(precio));

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_COMPETENCIA_PROMOCION,data);
            }
        }

        EstableceCompetenciaPromocion ui = new EstableceCompetenciaPromocion();
        ui.execute(_idpromotor, _pLatitud, _pLongitud, _pName, _idoperacion, _idRuta,_fechahora,
                _uploadImage1, _uploadImage2, _iconPromo, _por_participa, _no_frentes, _por_descuento, _comentario, _idproducto, _precio, _sVerApp);
    }

    // **********************************
    // TODO Coloca competencia promoción en modo desconectado etapa 2
    // Obtiene datos para subir foto de la tabla competencia promocion complemento
    public int ColocaCompetenciaPromocionComplemento()
    {
        int i = 0;
        int _idpromotor;
        double _latitud;
        double _longitud;
        String _fechahora="";
        String _idusuario = "";
        int _idruta = 0;
        String _image = "";
        String _image1 = "";
        int _por_participa = 0;
        int _idoperacion= 8;
        int _no_frentes= 0;
        int _con_sin_participacion= 0;
        int _por_descuento= 0;
        String _comentario = "";
        int _idproducto= 0;
        float _precio= 0;
        int _idcompetenciapromo= 0;
        String _sVerApp;
        int _idfoto = 0;
        int _idfoto1 = 0;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        //********************************************
        // Primer cursor
        String sSql = "Select distinct  c.idcompetenciapromo, af.idpromotor, af.latitud, af.longitud, af.idusuario, 8 as idoperacion, af.idruta, " +
                " af.fechahora, '' as image, af.imagen as image1 " +
                " ,c.por_participacion, c.no_frentes, c.con_sin_participacion, 0 as por_descuento, " +
                " c.comentarios, c.idproducto, c.precio, c.idfoto, c.idfoto1" +
                " from competencia_promocion c  " +
                " inner join almacenfotos af on af.id = c.idfoto1 " +
                " order by c.idcompetenciapromo asc limit 1;";

        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _idcompetenciapromo= cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _idusuario = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idruta = cursor.getInt(6);
                _fechahora = cursor.getString(7);
                _image = cursor.getString(8);
                _image1 = cursor.getString(9);
                _por_participa = cursor.getInt(10);
                _no_frentes = cursor.getInt(11);
                _con_sin_participacion = cursor.getInt(12);
                _por_descuento = 0;
                _comentario = cursor.getString(14);
                _idproducto = cursor.getInt(15);
                _precio= cursor.getFloat(16);
                _idfoto = cursor.getInt(17);
                _idfoto1= cursor.getInt(18);
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                _sVerApp =  versionName + ":" + versionCode;

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                // *******************
                // Subir imagen
                cargaCompetenciaPromocionComplemento(
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_idusuario),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idruta),
                        String.valueOf(_fechahora),
                        String.valueOf(_image),
                        String.valueOf(_image1),
                        String.valueOf(_con_sin_participacion),
                        String.valueOf(_por_participa),
                        String.valueOf(_no_frentes),
                        String.valueOf(_por_descuento),
                        _comentario,
                        String.valueOf(_idproducto),
                        String.valueOf(_precio),
                        _sVerApp
                );
                i++;
                // *****************************
                // Borrando el registro recien colocado de competencia_promocion asi como las fotos
                this.BorraFotoEnviada(_idfoto, _idfoto1);
                this.borrar_competencia_promocion(_idcompetenciapromo);
                // *****************************
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Log.e(TAG_ERROR, " Error en tabla al consultar competencia_promocion c2" + Resultado);
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al consultar competencia_promocion c2" + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            assert cursor != null;
            db.close();
            db1.close();
        }
    }

    // **************************
    // Borra competencia promoción
    public void borrar_competencia_promocion(int _idcompetenciapromo){
        SQLiteDatabase db1 = getWritableDatabase();
        try{
        String sBorrado = "Delete from competencia_promocion " +
                " where idcompetenciapromo = " + _idcompetenciapromo + ";";
        Log.e(TAG_INFO, sBorrado);
        db1.execSQL(sBorrado);
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Log.e(TAG_ERROR, " Error al borrar competenciapromocion" + Resultado);
            Toast.makeText(this.contexto, ERROR_FOTO + " Error al borrar competenciapromocion" +
                    Resultado, Toast.LENGTH_LONG).show();
            return;
        } finally {
            db1.close();
        }
    }


    private void cargaCompetenciaPromocionComplemento(
            String _idpromotor,
            String _pLatitud,
            String _pLongitud,
            String _pName,
            String _idoperacion,
            String _idRuta,
            String _fechahora,
            String _uploadImage1,
            String _uploadImage2,
            String _iconPromo,
            String _por_participa,
            String _no_frentes,
            String _por_descuento,
            String _comentario,
            String _idproducto,
            String _precio,
            String _sVerApp
    )
    {
        class EstableceCompetenciaPromocionComplemento extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromotor   = params[0];
                String pLatitud     = params[1];
                String pLongitud    = params[2];
                String idUsuario        = params[3];
                String idoperacion  = params[4];
                String idRuta      = params[5];
                String fechahora   = params[6];
                String uploadImage1= params[7];
                String uploadImage2= params[8];
                String iconPromo   = params[9];
                String por_participa= params[10];
                String no_frentes  = params[11];
                String por_descuento  = params[12];
                String comentario  = params[13];
                String idproducto  = params[14];
                String precio      = params[15];
                String sVerApp      = params[16];

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contexto.getApplicationContext());
                idUsuario = preferences.getString(TAG_USUARIO, idUsuario);

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);

                data.put(UPLOAD_CON_SIN_PARTICIPACION, String.valueOf(iconPromo));
                data.put(UPLOAD_POR_PARTICIPACION, String.valueOf(por_participa));
                data.put(UPLOAD_NO_FRENTES, String.valueOf(no_frentes));
                data.put(UPLOAD_POR_DESCUENTO, String.valueOf(por_descuento));
                data.put(UPLOAD_COMENTARIOS, String.valueOf(comentario));
                data.put(UPLOAD_IDPRODUCTO, String.valueOf(idproducto));
                data.put(UPLOAD_PRECIO, String.valueOf(precio));

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, "0");

                return rh.sendPostRequest(UPLOAD_COMPETENCIA_PROMOCION_COMPLEMENTO,data);
            }
        }

        EstableceCompetenciaPromocionComplemento ui = new EstableceCompetenciaPromocionComplemento();
        ui.execute(_idpromotor, _pLatitud, _pLongitud, _pName, _idoperacion, _idRuta,_fechahora,
                _uploadImage1, _uploadImage2, _iconPromo, _por_participa, _no_frentes, _por_descuento, _comentario, _idproducto, _precio, _sVerApp);
    }

    // **********************************
    // Función que consulta si un promotor realizo el checkin en una tienda y fecha
    public int consultaCheckinPromotorTienda(Integer _idpromotor, Integer _pidruta) {
        int iResultado = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sSql;

        Cursor cursor = null;
        try {

            sSql = "Select count(*) from almacenfotos where idpromotor = " + _idpromotor + " and idruta = " + _pidruta +
                    " and DATE(fechahora) = DATE(CURRENT_DATE) and idoperacion = 1";
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iResultado = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            String Resultado = e.getMessage();
            //Toast.makeText(this.contexto, ERROR_FOTO + " Error al obtener registros de lista de tiendas " + Resultado, Toast.LENGTH_LONG).show();
            // funciones.RegistraError(idUsuario, "AlmacenaImagen, downloadFile", e,  (Activity) AlmacenaImagen.this.contexto , AlmacenaImagen.this.contexto);
            // Por si hay una excepcion
        } finally {
            assert cursor != null;
            db.close();
        }
        return iResultado;
    }


    // **********************************
    // TODO Método para insertar canjes productos
    public int insertaoactualizacanjes(oCanje  ocanje)
    {
        Date fechahora = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechahora1 = sdf.format(fechahora);

        String sSql = null;
        SQLiteDatabase db = getWritableDatabase();

        if (ocanje.get_id()>0) {
            sSql = "  Update canjes_productos " +
                    " set idruta = '" + ocanje.get_ruta()  + "', idpromotor = '" + ocanje.get_promotor() + "', " +
                    " idproducto = '" + ocanje.get_producto() + "', cantidad =  '" + ocanje.get_cantidad() + "', fechahora = '" + fechahora1 + "', " +
                    " llave =  ''" +
                    " where id = '" + ocanje.get_id() + "';";
        } else {
            sSql = "Insert into canjes_productos(idruta, idpromotor, idproducto, cantidad,  fechahora, llave) " +
                    "values ('" + ocanje.get_ruta() + "','" + ocanje.get_promotor() + "','" + ocanje.get_producto() +
                    "','" + ocanje.get_cantidad() + "','" + fechahora1  + "','');";
        }
        // Log.e(TAG_INFO, "Inserciòn canjes " + sSql);
        try {
            db.execSQL(sSql);
            return 1;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "insertaoactualizacanje" );
            return 0;
        } finally {
            db.close();
        }
    }

    // **********************************
    // TODO Método consulta_idcanjes
    public int consulta_idcanjes(
            int _idruta,
            int _idpromotor,
            int _idproducto
    ) {
        String sSql;
        Cursor cursor = null;
        int id = -1;
        SQLiteDatabase db = getReadableDatabase();

        sSql = "  Select id from canjes_productos " +
                " where idruta = '" + _idruta + "'" +
                " and idpromotor  = '" + _idpromotor + "'" +
                " and idproducto = '" + _idproducto + "'" +
                " and llave = '';";
        //Log.e(TAG_ERROR, sSql);
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
            }
            cursor.close();

            return id;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "consulta_idcanjes" );
            return 0;
        } finally {
            db.close();
        }
    }

    // **********************************
    // TODO Método consulta_catidadcanjes
    public int consulta_cantidadcanjes(
            int _idcanje
    ) {
        String sSql;
        Cursor cursor = null;
        int cantidad = 0;
        SQLiteDatabase db = getReadableDatabase();

        sSql = "Select cantidad from canjes_productos where id = '" + _idcanje + "' ";
        Log.e(TAG_ERROR, sSql);
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cantidad = cursor.getInt(0);
            }
            cursor.close();
            return cantidad;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "consulta_cantidadcanjes" );
            return cantidad;
        } finally {
            db.close();
        }
    }

    // **********************************
    // Método para consultar el número de canejes por tienda y promotor
    public int consulta_canjes_tienda_promotor(
            int _idpromotor,
            int _idruta
    ) {
        String sSql;
        Cursor cursor = null;
        int cta = -1;
        SQLiteDatabase db = getReadableDatabase();

        sSql = "  Select sum(cantidad) as Cta from canjes_productos " +
                " where  idpromotor = '" + _idpromotor + "' " +
                " and idruta = '" + _idruta + "' " +
                " and llave = ''";
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();
            return cta;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "consulta_promocion_tienda" );
           return 0;
        } finally {
            db.close();
        }
    }

    // **********************************
    // TODO Método para consultar la cadena  de canjes por tienda y promotor
    public String consulta_cadena_canjes_tienda_promotor(
            int _idpromotor,
            int _idruta
    ) {
        String sSql;
        Cursor cursor = null;
        String sProducto = "";
        int cantidad = 0;
        String sRetorno = "";
        SQLiteDatabase db = getReadableDatabase();

        sSql = "  Select p.upc , c.cantidad from canjes_productos c inner join cat_productos p on c.idproducto = p.idproducto" +
                " where c.cantidad>0 and c.idpromotor = '" + _idpromotor + "' and c.idruta = '" + _idruta + "' and c.llave = ''";

        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                sProducto = cursor.getString(0);
                cantidad = cursor.getInt(1);
                sRetorno += String.valueOf(sProducto)+"="+String.valueOf(cantidad)+"|";
            }
            cursor.close();
            sRetorno = sRetorno.substring(0, sRetorno.length()-1);
            return sRetorno;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "consulta_promocion_tienda" );
            return sRetorno;
        } finally {
            db.close();
        }
    }

    // **********************************
    // TODO Método para consultar la cadena  de canjes por tienda y promotor
    public String consulta_cadena_canjes_tienda_promotor_llave(
            int _idpromotor,
            int _idruta,
            String _llave
    ) {
        String sSql;
        Cursor cursor = null;
        String sProducto = "";
        int cantidad = 0;
        String sRetorno = "";
        SQLiteDatabase db = getReadableDatabase();

        sSql = "  Select p.upc , c.cantidad from canjes_productos c " +
                " inner join cat_productos p on c.idproducto = p.idproducto" +
                " where c.cantidad>0 " +
                " and c.idpromotor = '" + _idpromotor + "'" +
                " and c.idruta = '" + _idruta + "'" +
                " and c.llave = '" + _llave + "'";

        // Log.e(TAG_producto, sSql);
        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                sProducto = cursor.getString(0);
                cantidad = cursor.getInt(1);
                sRetorno += String.valueOf(sProducto)+"="+String.valueOf(cantidad)+"|";
            }
            cursor.close();
            sRetorno = sRetorno.substring(0, sRetorno.length()-1);
            return sRetorno;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            this.inserta_error1(idUsuario, e, "consulta_cadena_canjes_tienda_promotor_llave" );
            return sRetorno;
        } finally {
            db.close();
        }
    }


    // **********************************
    // TODO Método para borrar canjes_almacenfotos
    public int borra_canjes_almacenfotos(
            String llave
    ) {
        String sSql=null;
        String sSql2=null;
        Cursor cursor = null;
        SQLiteDatabase db0 = getReadableDatabase();
        int idfoto=0;
        int idfoto1=0;

        //  CONSULTANDO el idfoto e idfoto1 para borrar ambas fotos
        sSql = " Select idfoto, idfoto1 from canjes where llave = '" + llave + "'";
        try {
            cursor = db0.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                idfoto = cursor.getInt(0);
                idfoto1 = cursor.getInt(1);
            }
            cursor.close();

            this.BorraFotoEnviada(idfoto, idfoto1);
            return 1;
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
            this.inserta_error1(idUsuario, e, "borra canjes_almacenfotos" );
            return 0;
        } finally {
            db0.close();
        }
    }

    // **********************************
    // TODO Método para borrar canjes_productos
    public int borra_canjes_productos(
            String llave
    ) {
        String sSql=null;
        String sSql2=null;
        Cursor cursor = null;

        SQLiteDatabase db1 = getWritableDatabase();
        sSql =  "delete from canjes_productos where llave = '" + llave + "';";

        try {
            db1.execSQL(sSql);
            // Log.e(TAG_ERROR, sSql);
            return 1;
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
            this.inserta_error1(idUsuario, e, "borra canjes_productos" );
            return 0;
        } finally {
            db1.close();
        }
    }

    // **********************************
    // TODO Método para borrar canjes
    public int borra_canjes(
            String llave
    ) {
        String sSql=null;
        SQLiteDatabase db1 = getWritableDatabase();

        sSql =  "delete from canjes where llave = '" + llave + "';";

        try {
            db1.execSQL(sSql);
            // Log.e(TAG_ERROR, sSql);
            return 1;
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
            this.inserta_error1(idUsuario, e, "borra canjes" );
            return 0;
        } finally {
            db1.close();
        }
    }

    // **********************************
    // TODO Método para borrar canjes
    public int consulta_canjes(
            int idRuta,
            int idPromotor,
            String llave
    ) {
        String sSql=null;
        Cursor cursor = null;
        int iCta = 0;
        SQLiteDatabase db = getWritableDatabase();

        sSql = " Select count(*) from canjes_productos " +
                " where idruta = '" + idRuta + "'" +
                " and idpromotor = '" + idPromotor + "'" +
                " and llave = '" + llave + "'" ;

        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                iCta = cursor.getInt(0);
            }
            cursor.close();
            return iCta;
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
            this.inserta_error1(idUsuario, e, "borra_canjes" );
            return 0;
        } finally {
            db.close();
        }
    }

    // **********************************
    // TODO Método para borrar canjes
    public int consulta_total_canjes( ) {
        String sSql=null;
        Cursor cursor = null;
        int iCta = 0;
        SQLiteDatabase db = getWritableDatabase();

        sSql = " Select * from canjes";

        try {
            cursor = db.rawQuery(sSql, null);
            while (cursor.moveToNext()) {

                Log.e(TAG_INFO,  "id "  +String.valueOf(cursor.getInt(0)));
                Log.e(TAG_INFO,  "idruta "  +String.valueOf(cursor.getInt(1)));
                Log.e(TAG_INFO,  "idpromotor "  +String.valueOf(cursor.getInt(2)));
                Log.e(TAG_INFO,  "idfoto "  +String.valueOf(cursor.getInt(3)));
                Log.e(TAG_INFO,  "idfoto1 "  +String.valueOf(cursor.getInt(4)));
                Log.e(TAG_INFO, "comentario "  +cursor.getString (5));
                Log.e(TAG_INFO, "fechahora "  + cursor.getString (6));
                Log.e(TAG_INFO, "llave "  + cursor.getString (7));
                Log.e(TAG_INFO, "-------------- ");
                /* */
                iCta++;
            }
            cursor.close();
            /*
            Log.e(TAG_INFO, "CONSULTA CANJES = " + sSql);
            Log.e(TAG_INFO, "CUENTA = " + String.valueOf(iCta));
             */
            return iCta;
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
            this.inserta_error1(idUsuario, e, "borra_canjes" );
            return 0;
        } finally {
            db.close();
        }
    }


    // **********************************
    // Método para insertar competencia
    // TODO Método para insertar_canjes
    public String inserta_canjes(
            int _idruta,
            int _idpromotor,
            int _idfoto,
            int _idfoto1,
            String _comentario
    ) {
        String sSql = null;
        Cursor cursor = null;
        int cta = 0;
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getReadableDatabase();
        String fecha = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        String llave =  _idruta + "_" + _idpromotor + "_" + fecha;

        sSql = "Select count(*) from canjes " +
                "  where idfoto = '" + _idfoto + "'" +
                "  and idfoto1 = '" + _idfoto1 + "'" +
                "  and idpromotor = '" + _idpromotor + "'" +
                "  and idruta = '" + _idruta + "'" +
                "  and cast(fechahora as date)  = cast(CURRENT_DATE as date);";

        //Log.e(TAG_INFO, "sSql " + sSql);
        db.beginTransaction();
        try {

            cursor = db1.rawQuery(sSql, null);
            while (cursor.moveToNext()) {
                cta = cursor.getInt(0);
            }
            cursor.close();

            if (cta > 0) {
                sSql = "  Update canjes " +
                        " set idruta = '" + _idruta + "', " +
                        " idpromotor = '" + _idpromotor + "', " +
                        " idfoto = '"  + _idfoto + "',  " +
                        " idfoto1 = '"  + _idfoto1 + "', " +
                        " comentario = '"  + _comentario + "', " +
                        " fecha = cast(CURRENT_TIMESTAMP as date) " + ", " +
                        " llave = '" + llave + "' " +
                        " where idfoto = '" + _idfoto + "'" +
                        " and idfoto1 = '" + _idfoto1 + "'" +
                        " and idruta = '" + _idruta + "'" +
                        " and idpromotor = '" + _idpromotor + "'" +
                        " and cast(fechahora as date)  = cast(CURRENT_DATE as date);";
            } else {
                sSql = "   Insert into canjes(idruta, idpromotor, idfoto, idfoto1, fechahora, comentario, llave)" +
                        " values(" + _idruta + "," + _idpromotor + "," +  _idfoto + "," + _idfoto1 +
                        ",datetime(\"now\",\"localtime\") ,'" + _comentario + "', '" + llave + "');";
            }
            // Log.e(TAG_INFO, " ***** inserción canjes " );
            // Log.e(TAG_INFO, sSql);
            db.execSQL(sSql);
            //Log.e(TAG_INFO, " ***** inserción exitosa  ***** " );

            // ***************************
            // Consulta de valor llave
            String sSql2 = null;

            // ***************************
            // inserción canjes_productos

            sSql2 = " Update canjes_productos set llave =  '" + llave + "'" +
                    " where idruta = '" + _idruta + "' " +
                    " and idpromotor = '" + _idpromotor + "' " +
                    " and Cast(fechahora as date) = cast(CURRENT_DATE as date) " +
                    " and llave = ''";
            //Log.e(TAG_INFO, " ***** actualización de canjes_productos ");
            //Log.e(TAG_INFO, sSql2);
            db.execSQL(sSql2);
            db.setTransactionSuccessful();
            //Log.e(TAG_INFO, " ***** actualizacion exitosa  ***** " );
            //db.setTransactionSuccessful();
            // ***************************
            //Log.e(TAG_INFO, " ***** llave insertado " + llave);
            return llave;
        } catch (Exception e) {
            this.inserta_error1(idUsuario, e, "canjes" );
            String Resultado = e.getMessage();
            return "";
        } finally {
            db.endTransaction();
            db1.close();
            db.close();
        }
    }


    // **********************************
    // TODO Coloca cajes en modo desconectado etapa 1
    // Obtiene datos para subir foto de la tabla competencia promocion
    // Los coloca en la web cuando la conexión este disponible
    public int ColocaCanjes()
    {
        int i = 0;
        int _id;
        int _idpromotor;
        double _latitud;
        double _longitud;
        String _idusuario = "";
        int _idoperacion= 9;
        int _idruta = 0;
        String _fechahora="";
        String _image = "";
        String _image1 = "";
        String _llave = "";
        String _comentario = "";
        String _sVerApp;

        SQLiteDatabase db = getReadableDatabase();
        //********************************************
        // Primer cursor
        String sSql = "Select distinct  c.id,  af.idpromotor, af.latitud, af.longitud, af.idusuario, 9 as idoperacion, af.idruta,   " +
                " af.fechahora, af.imagen as image,  '' as image1, c.llave, c.comentario " +
                " from canjes c  " +
                " inner join almacenfotos af on af.id = c.idfoto " +
                " where cast(af.fechahora as date) = cast(DATE() AS date) " +
                " order by c.id asc limit 1;";

        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _id= cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _idusuario = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idruta = cursor.getInt(6);
                _fechahora = cursor.getString(7);
                _image = cursor.getString(8);
                _image1 = cursor.getString(9);
                _llave = cursor.getString(10);
                _comentario = cursor.getString(11);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                _sVerApp =  versionName + ":" + versionCode;

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                String sArregloProductos = this.consulta_cadena_canjes_tienda_promotor_llave(_idpromotor, _idruta, _llave);
                String SinDatos= "1";
                // *******************
                // Subir imagen
                cargaCanjes(
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_idusuario),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idruta),
                        String.valueOf(_fechahora),
                        String.valueOf(_image),
                        String.valueOf(_image1),
                        _llave,
                        _comentario,
                        sArregloProductos,
                        _sVerApp,
                        SinDatos
                );
                i++;

                // *****************************
                cursor.moveToNext();
            }
            cursor.close();

            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Log.e(TAG_ERROR, "Error en tabla al consultar canjes etapa1" + Resultado);
            Toast.makeText(this.contexto, ERROR_FOTO + "Error en tabla al consultar canjes etapa1" + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            assert cursor != null;
            db.close();
        }
    }

    private void cargaCanjes(
            String _idpromotor,
            String _pLatitud,
            String _pLongitud,
            String _idusuario,
            String _idoperacion,
            String _idRuta,
            String _fechahora,
            String _uploadImage1,
            String _uploadImage2,
            String _llave,
            String _comentario,
            String _arregloproductos,
            String _sVerApp,
            String _SinDatos

    )
    {
        class EstableceCanjes extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromotor   = params[0];
                String pLatitud     = params[1];
                String pLongitud    = params[2];
                String idUsuario    = params[3];
                String idoperacion  = params[4];
                String idRuta      = params[5];
                String fechahora   = params[6];
                String uploadImage1= params[7];
                String uploadImage2= params[8];
                String llave       = params[9];
                String sComentarios= params[10];
                String sArregloProductos  = params[11];
                String sVerApp      = params[12];
                String SinDatos     = params[13];

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contexto.getApplicationContext());
                idUsuario = preferences.getString(TAG_USUARIO, idUsuario);

                /*
                Log.e(TAG_ERROR, "*************************");
                Log.e(TAG_ERROR, "Carga de canjes");
                Log.e(TAG_ERROR, "idpromotor "  + String.valueOf(idpromotor));
                Log.e(TAG_ERROR, "platitud "  + String.valueOf(pLatitud));
                Log.e(TAG_ERROR, "plongitud "  + String.valueOf(pLongitud));
                Log.e(TAG_ERROR, "idusuario "  + idUsuario);
                Log.e(TAG_ERROR, "idoperacion "  + String.valueOf(idoperacion));
                Log.e(TAG_ERROR, "idRuta "  + String.valueOf(idRuta));
                Log.e(TAG_ERROR, "fechahora "  + fechahora);
                Log.e(TAG_ERROR, "uploadImage1 "  + uploadImage1);
                Log.e(TAG_ERROR, "uploadImage2 "  + uploadImage2);
                Log.e(TAG_ERROR, "llave "  + llave);
                Log.e(TAG_ERROR,"sComentarios "  + sComentarios);
                Log.e(TAG_ERROR,"sArregloProductos "  + sArregloProductos);
                Log.e(TAG_ERROR,"SinDatos "  + SinDatos);
                Log.e(TAG_ERROR,UPLOAD_CANJES);
                Log.e(TAG_ERROR, "*************************");

                 */

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);
                data.put(UPLOAD_LLAVE, llave);
                data.put(Foto.UPLOAD_COMENTARIOS,sComentarios);
                data.put(UPLOAD_ARREGLOPRODUCTO,sArregloProductos);

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, SinDatos);

                return rh.sendPostRequest(UPLOAD_CANJES,data);
            }
        }

        EstableceCanjes ui = new EstableceCanjes();
        ui.execute( _idpromotor,
                _pLatitud,
                _pLongitud,
                _idusuario,
                _idoperacion,
                _idRuta,
                _fechahora,
                _uploadImage1,
                _uploadImage2,
                _llave,
                _comentario,
                _arregloproductos,
                _sVerApp,
                _SinDatos);
    }

    // **********************************
    // TODO Coloca canjes en modo desconectado etapa 2
    // Obtiene datos para subir foto de la tabla competencia promocion complemento
    public int ColocaCanjesComplemento()
    {
        int i = 0;
        int _id;
        int _idpromotor;
        double _latitud;
        double _longitud;
        String _idusuario = "";
        int _idoperacion= 10;
        int _idruta = 0;
        String _fechahora="";
        String _image = "";
        String _image1 = "";
        String _llave = "";
        String _comentario = "";
        String _sVerApp;
        int _idfoto = 0;
        int _idfoto1 = 0;

        SQLiteDatabase db = getReadableDatabase();

        //********************************************
        // Segundo cursor
        String sSql = "Select distinct  c.id,  af.idpromotor, af.latitud, af.longitud, af.idusuario, 10 as idoperacion, af.idruta,   " +
                " af.fechahora, '' as image,  af.imagen as image1, c.llave, c.comentario, c.idfoto, c.idfoto1 " +
                " from canjes c  " +
                " inner join almacenfotos af on af.id = c.idfoto1 " +
                " where cast(af.fechahora as date) = cast(DATE() AS date) " +
                " order by c.id asc limit 1;";

        // Log.e(TAG_ERROR, sSql);
        Cursor cursor;
        cursor = db.rawQuery(sSql, null);
        try {
            // ************************************
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _id= cursor.getInt(0);
                _idpromotor = cursor.getInt(1);
                _latitud = cursor.getDouble(2);
                _longitud = cursor.getDouble(3);
                _idusuario = cursor.getString(4);
                _idoperacion = cursor.getInt(5);
                _idruta = cursor.getInt(6);
                _fechahora = cursor.getString(7);
                _image = cursor.getString(8);
                _image1 = cursor.getString(9);
                _llave = cursor.getString(10);
                _comentario = cursor.getString(11);
                _idfoto = cursor.getInt(12);
                _idfoto1= cursor.getInt(13);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                _sVerApp =  versionName + ":" + versionCode;

                // Espera un segundo
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);   //1 second

                String sArregloProductos = this.consulta_cadena_canjes_tienda_promotor_llave(_idpromotor, _idruta, _llave);
                String _sinDatos= "1";
                // *******************
                // Subir imagen
                cargaCanjesComplemento(
                        String.valueOf(_idpromotor),
                        String.valueOf(_latitud),
                        String.valueOf(_longitud),
                        String.valueOf(_idusuario),
                        String.valueOf(_idoperacion),
                        String.valueOf(_idruta),
                        String.valueOf(_fechahora),
                        String.valueOf(_image),
                        String.valueOf(_image1),
                        _llave,
                        _comentario,
                        sArregloProductos,
                        _sVerApp,
                        _sinDatos
                );
                i++;
                this.borra_canjes_almacenfotos(_llave);
                this.borra_canjes_productos(_llave);
                this.borra_canjes(_llave);
                cursor.moveToNext();
            }
            cursor.close();
            return i;
        } catch (Exception e) {
            String Resultado = e.getMessage();
            Log.e(TAG_ERROR, " Error en tabla al consultar complento c2 " + Resultado);
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al consultar compmento c2" + Resultado, Toast.LENGTH_LONG).show();
            return 0;
        } finally {
            assert cursor != null;
            db.close();
        }
    }

    private void cargaCanjesComplemento(
            String _idpromotor,
            String _pLatitud,
            String _pLongitud,
            String _idusuario,
            String _idoperacion,
            String _idRuta,
            String _fechahora,
            String _uploadImage1,
            String _uploadImage2,
            String _llave,
            String _comentario,
            String _arregloproductos,
            String _sVerApp,
            String _sSinDatos
    )
    {
        class EstableceCanjesComplemento extends AsyncTask<String, Void, String> {

            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                String idpromotor   = params[0];
                String pLatitud     = params[1];
                String pLongitud    = params[2];
                String idUsuario    = params[3];
                String idoperacion  = params[4];
                String idRuta      = params[5];
                String fechahora   = params[6];
                String uploadImage1= params[7];
                String uploadImage2= params[8];
                String llave       = params[9];
                String sComentarios= params[10];
                String sArregloProductos  = params[11];
                String sVerApp      = params[12];
                String sSinDatos    = params[13];

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contexto.getApplicationContext());
                idUsuario = preferences.getString(TAG_USUARIO, idUsuario);
/*
                Log.e(TAG_ERROR, "*************************");
                Log.e(TAG_ERROR, "Carga de canjes complemento");
                Log.e(TAG_ERROR, "idpromotor "  + String.valueOf(idpromotor));
                Log.e(TAG_ERROR, "platitud "  + String.valueOf(pLatitud));
                Log.e(TAG_ERROR, "plongitud "  + String.valueOf(pLongitud));
                Log.e(TAG_ERROR, "idusuario "  + idUsuario);
                Log.e(TAG_ERROR, "idoperacion "  + String.valueOf(idoperacion));
                Log.e(TAG_ERROR, "idRuta "  + String.valueOf(idRuta));
                Log.e(TAG_ERROR, "fechahora "  + fechahora);
                Log.e(TAG_ERROR, "uploadImage1 "  + uploadImage1);
                Log.e(TAG_ERROR, "uploadImage2 "  + uploadImage2);
                Log.e(TAG_ERROR, "llave "  + llave);
                Log.e(TAG_ERROR,"sComentarios "  + sComentarios);
                Log.e(TAG_ERROR,"sArregloProductos "  + sArregloProductos);
                Log.e(TAG_ERROR,"sSinDatos "  + sSinDatos);
                Log.e(TAG_ERROR,UPLOAD_CANJES_COMPLEMENTO);
                Log.e(TAG_ERROR, "*************************");

 */

                data.put(UPLOAD_IDPROMOTOR, String.valueOf(idpromotor));
                data.put(UPLOAD_LATITUD, String.valueOf(pLatitud));
                data.put(UPLOAD_LONGITUD, String.valueOf(pLongitud));
                data.put(UPLOAD_IDUSUARIO, idUsuario);
                data.put(UPLOAD_IDOPERACION, String.valueOf(idoperacion));
                data.put(UPLOAD_IDRUTA, String.valueOf(idRuta));
                data.put(UPLOAD_FECHAHORA, fechahora);
                data.put(UPLOAD_IMAGEN, uploadImage1);
                data.put(UPLOAD_IMAGEN1, uploadImage2);
                data.put(UPLOAD_LLAVE, llave);
                data.put(Foto.UPLOAD_COMENTARIOS,sComentarios);

                data.put(UPLOAD_ARREGLOPRODUCTO,sArregloProductos);

                data.put(UPLOAD_VERSION, sVerApp);
                data.put(UPLOAD_SINDATOS, sSinDatos);

                return rh.sendPostRequest(UPLOAD_CANJES_COMPLEMENTO,data);
            }
        }

        EstableceCanjesComplemento ui = new EstableceCanjesComplemento();
        ui.execute( _idpromotor,
                _pLatitud,
                _pLongitud,
                _idusuario,
                _idoperacion,
                _idRuta,
                _fechahora,
                _uploadImage1,
                _uploadImage2,
                _llave,
                _comentario,
                _arregloproductos,
                _sVerApp,
                _sSinDatos);
    }

    // *************************************
    // Exportación de tabla de fotos
    public void exportarCSV() throws IOException {
        File carpeta = new File(Environment.getExternalStorageDirectory() + "/ExportarSQLiteCSV");
        String archivo = carpeta.toString() + "/" + "almacenfotos.csv";

        boolean isCreate = false;
        if(!carpeta.exists()) {
            isCreate = carpeta.mkdir();
        }
        SQLiteDatabase db = getWritableDatabase();

        try {

            FileWriter fileWriter = new FileWriter(archivo);
            Cursor fila = db.rawQuery("select * from almacenfotos where idoperacion<5", null);

            if(fila != null && fila.getCount() != 0) {
                fila.moveToFirst();
                do {
                    fileWriter.append(fila.getString(0));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(1));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(2));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(3));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(4));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(5));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(6));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(7));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(8));
                    fileWriter.append("\n");
                } while(fila.moveToNext());
            } else {
                Toast.makeText(contexto, "No hay registros.", Toast.LENGTH_LONG).show();
            }

            db.close();
            fileWriter.close();
            
            Log.e(TAG_INFO, "ARCHIVO "+ "file:/"+ archivo);
            // *****************************
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, "jjcorp2001@hotmail.com");
            emailIntent.putExtra(Intent.EXTRA_CC, "jjcorp2001a@gmail.com");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "mensaje");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/"+archivo));
            emailIntent.setType("text/plain"); //indicamos el tipo de dato
            emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(contexto, emailIntent,null);
            // *****************************
            Toast.makeText(contexto, "SE CREO EL ARCHIVO CSV EXITOSAMENTE", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            String Resultado = e.getMessage();
            Log.e(TAG_ERROR, " Error en tabla almacenfotos " + Resultado);
            Toast.makeText(this.contexto, ERROR_FOTO + " Error en tabla al consultar la tabla almacenfotos" + Resultado, Toast.LENGTH_LONG).show();;
        } finally {
            db.close();
        }
    }
}