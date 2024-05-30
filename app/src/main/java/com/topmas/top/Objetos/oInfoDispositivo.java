package com.topmas.top.Objetos;

public class oInfoDispositivo
{
    public static String _fabricante;
    public static String _marca;
    public static String _modelo;
    public static String _board;
    public static String _hardware;
    public static String _serie;
    public static String _uid;
    public static String _android_id;
    public static String _resolucion;
    public static String _tamaniopantalla;
    public static String _densidad;
    public static String _bootloader;
    public static String _user_value;
    public static String _host_value;
    public static String _version;
    public static String _api_level;
    public static String _build_id;
    public static String _build_time;
    public static String _fingerprint;
    public static String _usuario;
    public static String _seccion;
    public static String _error;
    public static String _fechahora;
    public static int _procesado;

    public oInfoDispositivo()
    {

    }
    public oInfoDispositivo(
            String pfabricante,
            String pmarca,
            String pmodelo,
            String pboard,
            String phardware,
            String pserie,
            String puid,
            String pandroid_id,
            String presolucion,
            String ptamaniopantalla,
            String pdensidad,
            String pbootloader,
            String puser_value,
            String phost_value,
            String pversion,
            String papi_level,
            String pbuild_id,
            String pbuild_time,
            String pfingerprint,
            String pusuario,
            String pseccion,
            String perror,
            String pfechahora,
            int pprocesado
    ){
        _fabricante = pfabricante;
        _marca = pmarca;
        _modelo = pmodelo;
        _board = pboard;
        _hardware = phardware;
        _serie = pserie;
        _uid = puid;
        _android_id = pandroid_id;
        _resolucion = presolucion;
        _tamaniopantalla = ptamaniopantalla;
        _densidad = pdensidad;
        _bootloader = pbootloader;
        _user_value = puser_value;
        _host_value = phost_value;
        _version = pversion;
        _api_level = papi_level;
        _build_id = pbuild_id;
        _build_time = pbuild_time;
        _fingerprint = pfingerprint;
        _usuario = pusuario;
        _seccion = pseccion;
        _error = perror;
        _fechahora = pfechahora;
        _procesado = pprocesado;
    }
    public String get_fabricante(){return _fabricante;}
    public String get_marca(){return _marca;}
    public String get_modelo(){return _modelo;}
    public String get_board(){return _board;}
    public String get_hardware(){return _hardware;}
    public String get_serie(){return _serie;}
    public String get_uid(){return _uid;}
    public String get_android_id(){return _android_id;}
    public String get_resolucion(){return _resolucion;}
    public String get_tamaniopantalla(){return _tamaniopantalla;}
    public String get_densidad(){return _densidad;}
    public String get_bootloader(){return _bootloader;}
    public String get_user_value(){return _user_value;}
    public String get_host_value(){return _host_value;}
    public String get_version(){return _version;}
    public String get_api_level(){return _api_level;}
    public String get_build_id(){return _build_id;}
    public String get_build_time(){return _build_time;}
    public String get_fingerprint(){return _fingerprint;}
    public String get_usuario(){return _usuario;}
    public String get_seccion(){return _seccion;}
    public String get_error(){return _error;}

    public void set_fabricante(String pfabricante ){_fabricante = pfabricante;}
    public void set_marca(String pmarca ){_marca = pmarca;}
    public void set_modelo(String pmodelo ){_modelo = pmodelo;}
    public void set_board(String pboard ){_board = pboard;}
    public void set_hardware(String phardware ){_hardware = phardware;}
    public void set_serie(String pserie ){_serie = pserie;}
    public void set_uid(String puid ){_uid = puid;}
    public void set_android_id(String pandroid_id ){_android_id = pandroid_id;}
    public void set_resolucion(String presolucion ){_resolucion = presolucion;}
    public void set_tamaniopantalla(String ptamaniopantalla ){_tamaniopantalla = ptamaniopantalla;}
    public void set_densidad(String pdensidad ){_densidad = pdensidad;}
    public void set_bootloader(String pbootloader ){_bootloader = pbootloader;}
    public void set_user_value(String puser_value ){_user_value = puser_value;}
    public void set_host_value(String phost_value ){_host_value = phost_value;}
    public void set_version(String pversion ){_version = pversion;}
    public void set_api_value(String papi_level ){_api_level = papi_level;}
    public void set_build_id(String pbuild_id ){_build_id = pbuild_id;}
    public void set_build_time(String pbuild_time ){_build_time = pbuild_time;}
    public void set_fingerprint(String pfingerprint ){_fingerprint = pfingerprint;}
    public void set_usuario(String pusuario ){_usuario = pusuario;}
    public void set_seccion(String pseccion ){_seccion = pseccion;}
    public void set_error(String perror ){_error = perror;}
    public void set_fechahora(String pfechahora ){_fechahora = pfechahora;}
}
