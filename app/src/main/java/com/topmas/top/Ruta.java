package com.topmas.top;

import com.topmas.top.Objetos.oRuta;

public class Ruta {
    private static oRuta ruta;

    //************
    // Constructor
    public Ruta(){
    }

    //************
    // Constructor sobrecargado
    public Ruta(int pidRuta, int pValor)
    {
        ruta.Establecer(pidRuta, pValor);
    }

    public void Establecer(int pidRuta, int pValor)
    {
        ruta.Establecer(pidRuta, pValor);
    }
    //***********************
    // Propiedades get y set

    public oRuta getUsr(){
        return this.ruta;
    }
    public int getid(){return this.ruta.get_idRuta(); }
    public int getvalor(){return this.ruta.get_Valor(); }
}
