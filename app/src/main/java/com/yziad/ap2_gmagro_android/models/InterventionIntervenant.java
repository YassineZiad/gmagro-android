package com.yziad.ap2_gmagro_android.models;

public class InterventionIntervenant {

    private Intervention intervention;
    private Intervenant intervenant;
    private String tps_time;

    public InterventionIntervenant() {

    }

    public InterventionIntervenant(Intervention intervention, Intervenant intervenant, String tps_time) {
        this.intervention = intervention;
        this.intervenant = intervenant;
        this.tps_time = tps_time;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public String getTps_time() {
        return tps_time;
    }

    @Override
    public String toString() {
        return intervenant.getPrenom() + " " + intervenant.getNom() + " (" + tps_time + ")";
    }
}
