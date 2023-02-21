package com.yziad.ap2_gmagro_android.models;

public class CausesEtSymptomes {

    private String code;
    private String libelle;
    private String site_uai;
    private enum typeCSOD {
        Cause_Defaut,
        Cause_Objet,
        Symptome_Defaut,
        Symptome_Objet
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getSite_uai() {
        return site_uai;
    }
}
