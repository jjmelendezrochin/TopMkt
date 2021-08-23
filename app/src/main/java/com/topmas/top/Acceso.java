package com.topmas.top;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Acceso {
    // Este objeto sirve para saber la forma en que se realizo el acceso

    public static int iAcceso=0;
    public static void EstableceAcceso(int _iAcceso){ iAcceso = _iAcceso;}
    public static int ObtieneAcceso(){ return iAcceso;}
}
