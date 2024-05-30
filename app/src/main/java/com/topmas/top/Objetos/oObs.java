package com.topmas.top.Objetos;

public class oObs {
    public static int _idobs;
    public static String _observaciones;

    public oObs(int id, String obs) {
        _idobs = id; _observaciones=obs;
    }

    public static int get_idobs(){return _idobs;}
    public static String get_observaciones(){return _observaciones;}
}
