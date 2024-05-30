package com.topmas.top.Objetos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class oCanje {
    public static int _idruta;
    public static int _idpromotor;
    public static int _idproducto;
    public static int _cantidad;
    public static String _fechahora;
    public static int _id;
    public oCanje(
            int pidruta,
            int pidpromotor,
            int pidproducto,
            int pcantidad,
            String pfechahora,
            int pid

    ){
        _idruta         = pidruta;
        _idpromotor     = pidpromotor;
        _idproducto     = pidproducto;
        _cantidad       = pcantidad;
        _fechahora      = pfechahora;
        _id             = pid;
    }

    public static String get_llave(){
        Date fechahora = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechahora1 = sdf.format(fechahora);
        return String.valueOf(_idruta) + "_" + String.valueOf(_idpromotor)+ "_" +fechahora1;
    }
    public static int get_ruta(){return _idruta;}
    public static int get_promotor(){return _idpromotor;}
    public static int get_producto(){return _idproducto;}
    public static int get_cantidad(){return _cantidad;}
    public static String get_fechahora(){return _fechahora;}
    public static int get_id(){return _id;}
}
