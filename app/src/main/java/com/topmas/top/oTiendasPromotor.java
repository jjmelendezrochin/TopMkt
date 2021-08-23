package com.topmas.top;

public class oTiendasPromotor
{
    public static int _idruta;
    public static String _determinante;
    public static String _tienda;
    public static String _direccioncompleta;
    public static double _latitud;
    public static double _longitud;

    public  oTiendasPromotor(int pidruta, String pdeterminante, String ptienda, String pdireccioncompleta, double platitud, double plongitud)
    {
        _idruta = pidruta;
        _determinante = pdeterminante;
        _tienda = ptienda;
        _direccioncompleta = pdireccioncompleta;
        _latitud = platitud;
        _longitud = plongitud;
    }/**/

    public static void Establece(int pidruta, String pdeterminante,
                                 String ptienda, String pdireccioncompleta,
                                 Double platitud, Double plongitud)
    {
        _idruta = pidruta;
        _determinante = pdeterminante;
        _tienda = ptienda;
        _direccioncompleta = pdireccioncompleta;
        _latitud = 0;
        _longitud = 0;
    }

    public static int getidruta(){ return _idruta; }
    public static String getdeterminante(){ return _determinante;}
    public static String gettienda(){ return _tienda;}
    public static String getdireccioncompleta(){ return _direccioncompleta;}
    public static double getlatitud(){ return _latitud;}
    public static double getlongitud(){ return _longitud;}

    /*
     public int getidruta(){  // Log.e(TAG_ERROR, "idruta " + _idruta); return _idruta;}
    public String getdeterminante(){ // Log.e(TAG_ERROR, "_determinante " + _determinante); return _determinante;  }
    public String gettienda(){ // Log.e(TAG_ERROR, "_tienda " + _tienda); return _tienda;}
    public String getdireccioncompleta(){ // Log.e(TAG_ERROR, "_direccioncompleta " + _tienda); return _direccioncompleta;}
    public double getlatitud(){ // Log.e(TAG_ERROR, "_latitud " + _latitud); return _latitud;}
    public double getlongitud(){ // Log.e(TAG_ERROR, "_longitud " + _longitud); return _longitud;}
     */

}
