package com.yziad.ap2_gmagro_android.models;

public class Machine {

    private String code, date_fabrication, numero_serie, site_uai, type_machine_code;

    public Machine() {

    }

    public Machine(String code, String date_fabrication, String numero_serie, String site_uai, String type_machine_code) {
        this.code = code;
        this.date_fabrication = date_fabrication;
        this.numero_serie = numero_serie;
        this.site_uai = site_uai;
        this.type_machine_code = type_machine_code;
    }

    public String getCode() {
        return code;
    }

    public String getDate_fabrication() {
        return date_fabrication;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public String getSite_uai() {
        return site_uai;
    }

    public String getType_machine_code() {
        return type_machine_code;
    }

    @Override
    public String toString() {
        return code ;
    }

}