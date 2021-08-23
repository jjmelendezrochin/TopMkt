package com.topmas.top;

public class oFoto {
    public static int _idpromotor;
    public static double _latitud;
    public static double _longitud;
    public static String _fechahora;
    public static int _idoperacion;
    public static String _idusuario;
    public static int _idruta;
    public static String _imagen;

    public oFoto(
            int pidpromotor, double platitud,
            double plongitud, String pfechahora,
            int pidoperacion,  String pidusuario,
            int pidruta, String pimagen
    ){
        _idpromotor =   pidpromotor;
        _latitud    =   platitud;
        _longitud   =   plongitud;
        _fechahora  =   pfechahora;
        _idoperacion =   pidoperacion;
        _idusuario  =   pidusuario;
        _idruta     =   pidruta;
        _imagen     =   pimagen;
    }
}
