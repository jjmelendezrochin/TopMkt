package com.topmas.top;

public class Usuario
{
    private static oUsuario usr;

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
        usr.Establece(pid, pname, pemail, paccessToken, pexpiresIn, pidempresa);
    }

    //************
    public static void setUsr(int pid,
                  String pname,
                  String pemail,
                  String paccessToken,
                  String pexpiresIn,
                  String pidempresa)
    {
        usr.Establece(pid, pname, pemail, paccessToken, pexpiresIn, pidempresa);
    }

    //***********************
    // Propiedades get y set

    public oUsuario getUsr(){
        return this.usr;
    }
    public int getid(){
        return this.usr.obtenid();
    }
    public String getnombre(){return this.usr.obtennombre(); }
    public String getidusuario(){return this.usr.obtenidusuario();}
    public String getemail(){return this.usr.obtenemail();}
    public String getidempresa(){return this.usr.obtenidempresa();}
    public double getLatitud(){return this.usr.obtenLatitud();}
    public double getLongitud(){return this.usr.obtenLongitud();}

    public void setLatitud(double pLatitud) { this.usr.estableceLatitud(pLatitud);}
    public void setLongitud(double pLongitud) { this.usr.estableceLongitud(pLongitud);}
    public void setidusuario(String pidusuario){this.usr.estableceidUsuario(pidusuario);}
    //***********************
}
