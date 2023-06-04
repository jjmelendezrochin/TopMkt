package com.topmas.top;

public class Usuario
{
    public static int _id;
    public static String _idusuario;
    public static String _email;
    public static String _accessToken;
    public static String _expiresIn;
    public static double _latitud;
    public static double _longitud;
    public static String _idempresa;

    //************
    // Constructor
    public Usuario(){
    }

    //************
    // Constructor sobrecargado
    public Usuario(int pid,
                   String pname,
                   String pemail,
                   String paccessToken,
                   String pexpiresIn,
                   String pidempresa
    ){
        this.Establece(pid, pname, pemail, paccessToken, pexpiresIn, pidempresa);
    }

    //************
    public void setUsr(int pid,
                  String pname,
                  String pemail,
                  String paccessToken,
                  String pexpiresIn,
                  String pidempresa)
    {
        this.Establece(pid, pname, pemail, paccessToken, pexpiresIn, pidempresa);
    }

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

    //***********************
    // Propiedades get y set

    public int getid(){
        return this._id;
    }
    public String getnombre(){return this._idusuario; }
    public String getidusuario(){return this._idusuario;}
    public String getemail(){return this._email;}
    public String getidempresa(){return this._idempresa;}
    public double getLatitud(){return this._latitud;}
    public double getLongitud(){return this._longitud;}

    public void setLatitud(double pLatitud) { this._latitud = pLatitud;}
    public void setLongitud(double pLongitud) { this._longitud = pLongitud;}
    public void setidusuario(String pidusuario){this._idusuario = pidusuario;}
    //***********************
}
