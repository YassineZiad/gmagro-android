package com.yziad.ap2_gmagro_android.models;

public class Intervention {

    private int id;
    private String dh_debut;
    private String dh_fin;
    private String commentaire;
    private String temps_arret;
    private String changement_organe;
    private String perte;
    private String dh_creation;
    private String dh_derniere_maj;
    private String intervenant_login;
    private String site_uai;
    private String activite_code;
    private String machine_code;
    private String cause_defaut_code;
    private String cause_objet_code;
    private String symptome_defaut_code;
    private String symptome_objet_code;

    public Intervention() {

    }

    public Intervention(int id, String dh_debut, String dh_fin, String commentaire, String temps_arret, String changement_organe, String perte,
                        String dh_creation, String dh_derniere_maj, String intervenant_login, String site_uai, String activite_code, String machine_code,
                        String cause_defaut_code, String cause_objet_code, String symptome_defaut_code, String symptome_objet_code) {
        this.id = id;
        this.dh_debut = dh_debut;
        this.dh_fin = dh_fin;
        this.commentaire = commentaire;
        this.temps_arret = temps_arret;
        this.changement_organe = changement_organe;
        this.perte = perte;
        this.dh_creation = dh_creation;
        this.dh_derniere_maj = dh_derniere_maj;
        this.intervenant_login = intervenant_login;
        this.site_uai = site_uai;
        this.activite_code = activite_code;
        this.machine_code = machine_code;
        this.cause_defaut_code = cause_defaut_code;
        this.cause_objet_code = cause_objet_code;
        this.symptome_defaut_code = symptome_defaut_code;
        this.symptome_objet_code = symptome_objet_code;
    }

    public int getId() {
        return id;
    }

    public String getDh_debut() {
        return dh_debut;
    }

    public String getDh_fin() {
        return dh_fin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getTemps_arret() {
        return temps_arret;
    }

    public String getChangement_organe() {
        return changement_organe;
    }

    public String getPerte() {
        return perte;
    }

    public String getDh_creation() {
        return dh_creation;
    }

    public String getDh_derniere_maj() {
        return dh_derniere_maj;
    }

    public String getIntervenant_login() {
        return intervenant_login;
    }

    public String getSite_uai() {
        return site_uai;
    }

    public String getActivite_code() {
        return activite_code;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public String getCause_defaut_code() {
        return cause_defaut_code;
    }

    public String getCause_objet_code() {
        return cause_objet_code;
    }

    public String getSymptome_defaut_code() {
        return symptome_defaut_code;
    }

    public String getSymptome_objet_code() {
        return symptome_objet_code;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.dh_debut + " - " + this.machine_code;
    }

}
