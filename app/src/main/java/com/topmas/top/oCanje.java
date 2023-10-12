package com.topmas.top;

public class oCanje {
    public static int _idcanje;
    public static int _idruta;
    public static int _idpromotor;
    public static int _idproducto;
    public static int _cantidad;
    public static String _fechahora;

    public oCanje(
            int pidcanje,
            int pidruta,
            int pidpromotor,
            int pidproducto,
            int pcantidad,
            String pfechahora

    ){
        _idcanje        = pidcanje;
        _idruta         = pidruta;
        _idpromotor     = pidpromotor;
        _idproducto     =   pidproducto;
        _cantidad       =   pcantidad;
        _fechahora      =   pfechahora;
    }

    public static int get_canje(){return _idcanje;}
    public static int get_ruta(){return _idruta;}
    public static int get_promotor(){return _idpromotor;}
    public static int get_producto(){return _idproducto;}
    public static int get_cantidad(){return _cantidad;}
    public static String get_fechahora(){return _fechahora;}
}
