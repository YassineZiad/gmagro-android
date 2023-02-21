package com.yziad.ap2_gmagro_android.models;

public class Intervenant {

    private String login;
    private String nom;
    private String prenom;
    private String email;
    private String estActif;
    private String role;
    private String site_uai;

    public Intervenant() {

    }

    public Intervenant(String login, String nom, String prenom, String email, String estActif, String role, String site_uai) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.estActif = estActif;
        this.role = role;
        this.site_uai = site_uai;
    }

    public String getLogin() {
        return login;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getEstActif() {
        return estActif;
    }

    public String getRole() {
        return role;
    }

    public String getSite_uai() {
        return site_uai;
    }

    @Override
    public String toString() {
        return this.prenom + " " + this.nom;
    }
}
