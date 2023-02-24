package com.yziad.ap2_gmagro_android.models;

public class CSOD {

    private String code;
    private String libelle;
    private String site_uai;
    private TypeCSOD typeCSOD;
    private enum TypeCSOD {
        Cause_Defaut,
        Cause_Objet,
        Symptome_Defaut,
        Symptome_Objet
    }

    public CSOD() {

    }

    public CSOD(String code, String libelle, String site_uai) {
        this.code = code;
        this.libelle = libelle;
        this.site_uai = site_uai;
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

    public void setType(String typeCSOD) {
        switch (typeCSOD) {
            case "causes_defaut":
               this.typeCSOD = TypeCSOD.Cause_Defaut;
            break;

            case "causes_objet":
                this.typeCSOD = TypeCSOD.Cause_Objet;
                break;

            case "symptomes_defaut":
                this.typeCSOD = TypeCSOD.Symptome_Defaut;
                break;

            case "symptomes_objet":
                this.typeCSOD = TypeCSOD.Symptome_Objet;
                break;

            default:
                break;
        }
    }

    @Override
    public String toString() {
        return libelle;
    }
}
