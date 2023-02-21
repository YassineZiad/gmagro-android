package com.yziad.ap2_gmagro_android.models;

public class Activite {

    private String code;
    private String libelle;

    public Activite() {

    }

    public Activite(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString(){
        return code + " : " + libelle;
    }
}


//@JsonFormat(shape = Shape.NUMBER)