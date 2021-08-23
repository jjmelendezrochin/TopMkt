package com.topmas.top;

public  class oPromocion {
    public  int _idpromocion;
    public  int _idempresa;
    public  String _nombre;
    public  String _promo;
    public  String _capacidad;
    public  String _canal;
    public  String _inicio;
    public  String _final;
    public  String _periodo;
    public  String _actividad;
    public  String _alcance;
    public  String _precioregular;
    public  String _preciopromocion;
    public  int _idformato;
    public  int _idproducto;
    public  String _descripcion;
    public  String _upc;
    public  String _url;

    public oPromocion(
          int pidpromocion,
          int pidempresa,
          String pnombre,
          String ppromo,
          String pcapacidad,
          String pcanal,
          String pinicio,
          String pfinal,
          String pperiodo,
          String pactividad,
          String palcance,
          String pprecioregular,
          String ppreciopromocion,
          int pidformato,
          int pidproducto,
          String pdescripcion,
          String pupc,
          String purl
    ){
        _idpromocion=pidpromocion;
        _idempresa=pidempresa;
        _nombre=pnombre;
        _promo=ppromo;
        _capacidad=pcapacidad;
        _canal=pcanal;
        _inicio=pinicio;
        _final=pfinal;
        _periodo=pperiodo;
        _actividad=pactividad;
        _alcance=palcance;
        _precioregular=pprecioregular;
        _preciopromocion=ppreciopromocion;
        _idformato=pidformato;
        _idproducto=pidproducto;
        _descripcion=pdescripcion;
        _upc=pupc;
        _url=purl;
    }
    public  int get_idpromocion(){return _idpromocion;}
    public  int get_idempresa(){return _idempresa;}
    public  int get_idformato(){return _idformato;}
    public  int get_idproducto(){return _idproducto;}
    public  String get_nombre(){return _nombre;}
    public  String get_promo(){return _promo;}
    public  String get_capacidad(){return _capacidad;}
    public  String get_canal(){return _canal;}
    public  String get_inicio(){return _inicio;}
    public  String get_final(){return _final;}
    public  String get_periodo(){return _periodo;}
    public  String get_actividad(){return _actividad;}
    public  String get_alcance(){return _alcance;}
    public  String get_precioregular(){return _precioregular;}
    public  String get_preciopromocion(){return _preciopromocion;}
    public  String get_descripcion(){return _descripcion;}
    public  String get_upc(){return _upc;}
    public  String get_url(){return _url;}
}
