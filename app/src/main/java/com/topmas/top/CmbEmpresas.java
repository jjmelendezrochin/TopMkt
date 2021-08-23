package com.topmas.top;

public class CmbEmpresas {
    int idEmpresa;
    String empresa;

    public CmbEmpresas(int idEmpresa, String empresa){
        this.idEmpresa=idEmpresa;
        this.empresa=empresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return this.idEmpresa + " " + this.empresa;
    }

}
