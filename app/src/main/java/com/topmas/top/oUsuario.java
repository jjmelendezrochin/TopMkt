package com.topmas.top;

public  class oUsuario
{
    public static int _id;
    public static String _idusuario;
    public static String _email;
    public static String _accessToken;
    public static String _expiresIn;
    public static double _latitud;
    public static double _longitud;
    public static String _idempresa;

    public static void Establece(int pid, String pidusuario, String pemail, String paccessToken, String pexpiresIn, String pidempresa)
    {
        _id = pid;
        _idusuario = pidusuario;
        _email = pemail;
        _accessToken = paccessToken;
        _expiresIn = pexpiresIn;
        _latitud = 0;
        _longitud = 0;
        _idempresa = pidempresa;
    }

    public static void Limpia()
    {
        _id = 0;
        _idusuario = null;
        _email = null;
        _accessToken = null;
        _expiresIn = null;
        _latitud = 0;
        _longitud = 0;
        _idempresa = "";
    }

    public static void estableceLatitud(double platitud){ _latitud = platitud ;}
    public static void estableceLongitud(double plongitud){ _longitud = plongitud ;}
    public static void estableceidUsuario(String pidUsuario){ _idusuario = pidUsuario ;}

    public static double obtenLatitud() { return _latitud;}
    public static double obtenLongitud() { return _longitud;}

    public static int obtenid() { return   _id; }

    public static String obtennombre(){return   _idusuario;}
    public static String obtenidusuario(){return   _idusuario;}
    public static String obtenemail(){return _email;}
    public static String obtenidempresa ()
    {
        return _idempresa;
    }
}
