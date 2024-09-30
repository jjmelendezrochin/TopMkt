package com.topmas.top.Objetos;

public class oRespuestaIncidencia {

    public static int _idinc;
    public static int _idincidencia;
    public static int _idfoto;
    public static int _idpromotor;
    public static int _idruta;
    public static String _fechahora;
    public static String _observaciones;
    public static String _respuesta;
    public static String _fechahora_respuesta;
    public static String _image;
    public static int _leida;

    public static String _tienda;
    public static String _direccioncompleta;

    public oRespuestaIncidencia(
            int pidinc,
            int pidincidencia,
            int pidfoto,
            int pidpromotor,
            int pidruta,
            String pfechahora,
            String pobservaciones,
            String prespuesta,
            String pfechahora_respuesta,
            String pimage,
            int pleida,
            String ptienda,
            String pdireccioncompleta
    ){
        _idinc          = pidinc;
        _idincidencia   = pidincidencia;
        _idfoto         = pidfoto;
        _idpromotor     = pidpromotor;
        _idruta         = pidruta;
        _fechahora      = pfechahora;
        _observaciones  = pobservaciones;
        _respuesta      = prespuesta;
        _fechahora_respuesta     =   pfechahora_respuesta;
        _image          = pimage;
        _leida          = pleida;
        _tienda          = ptienda;
        _direccioncompleta          = pdireccioncompleta;
    }
    /*
    public static Integer get_idinc(){return _idinc;}
    public static Integer get_idincidencia(){return _idincidencia;}
    public static Integer get_idfoto(){return _idfoto;}
    public static Integer get_idpromotor(){return _idpromotor;}
    public static Integer get_idruta(){return _idruta;}
    public static String get_fechahora(){return _fechahora;}
    public static String get_observaciones(){return _observaciones;}
    public static String get_respuesta(){return _respuesta;}
    public static String get_fechahora_respuesta(){return _fechahora_respuesta;}
    public static String get_image(){return _image;}
    public static Integer get_leida(){return _leida;}
     */
}
