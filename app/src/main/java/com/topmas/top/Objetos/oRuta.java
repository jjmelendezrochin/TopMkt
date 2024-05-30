package com.topmas.top.Objetos;

public class oRuta {
    public static int _idRuta;
    public static int _Valor;

    public static void oRuta() { }
    public static void oRuta(int pRuta, int pValor) { _idRuta = pRuta; _Valor=pValor; }
    public static void Establecer(int pRuta, int pValor) { _idRuta = pRuta; _Valor=pValor; }
    public static int get_idRuta(){return _idRuta;}
    public static int get_Valor(){return _Valor;}
}
