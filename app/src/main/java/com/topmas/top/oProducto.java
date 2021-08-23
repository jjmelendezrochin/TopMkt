package com.topmas.top;

public class oProducto {
    public static String _upc;
    public static int _idproducto;
    public static String _descripcion;
    public static String _descripcion1;
    public static String _cantidadcaja;
    public static String _categoria1;
    public static String _categoria2;
    public static double _precio;
    public static double _precioreal;
    public static int _idruta;

    public oProducto(
            String pupc,
            int pidproducto,
            String pdescripcion,
            String pdescripcion1,
            String pcantidadcaja,
            String pcategoria1,
            String pcategoria2,
            double pprecio,
            double pprecioreal,
            int pidruta
    ){
        _upc = pupc;
        _idproducto =   pidproducto;
        _descripcion    =   pdescripcion;
        _descripcion1   =   pdescripcion1;
        _cantidadcaja  =   pcantidadcaja;
        _categoria1 =   pcategoria1;
        _categoria2  =   pcategoria2;
        _precio     =   pprecio;
        _precioreal     =   pprecioreal;
        _idruta     =   pidruta;
    }
    public static String get_upc(){return _upc;}
    public static String get_descripcion(){return _descripcion;}
    public static String get_descripcion1(){return _descripcion1;}
    public static String get_cantidadcaja(){return _cantidadcaja;}
    public static String get_categoria1(){return _categoria1;}
    public static String get_categoria2(){return _categoria2;}
    public static double get_precio(){return _precio;}
    public static double get_precioreal(){return _precioreal;}
}
