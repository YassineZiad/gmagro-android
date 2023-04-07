package com.yziad.ap2_gmagro_android.daos;

import android.util.Log;

import com.yziad.ap2_gmagro_android.models.Intervention;
import com.yziad.ap2_gmagro_android.wsHTTPS;

import org.json.JSONException;
import org.json.JSONObject;

public class InterventionDAO {

    private static InterventionDAO instance;
    private String controller = "controller=intervention&";

    private InterventionDAO() {

    }

    public static InterventionDAO getInstance() {
        if (instance == null) {
            instance = new InterventionDAO();
        }
        return instance;
    }

    public void createIntervention(Intervention i, String intervInts, DelegateAsyncTask delegate) {
        String url = controller + "action=create&dh_debut=" + i.getDh_debut() + "&dh_fin=" + i.getDh_fin() + "&commentaire=" + i.getCommentaire()
                + "&temps_arret=" + i.getTemps_arret() + "&changement_organe=" + i.getChangement_organe() + "&perte= " + i.getPerte()
                + "&intervenant_login=" + i.getIntervenant_login() + "&activite_code=" + i.getActivite_code() + "&machine_code=" + i.getMachine_code()
                + "&cause_defaut_code=" + i.getCause_defaut_code() + "&cause_objet_code=" + i.getCause_objet_code()
                + "&symptome_defaut_code=" + i.getSymptome_defaut_code() + "&symptome_objet_code=" + i.getSymptome_objet_code() + "&intervInts=" + intervInts;
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostCreateIntervention(jsonMsg, delegate);
            }
        };
        ws.execute(url);
    }

    private void traiterPostCreateIntervention(String jRetour, DelegateAsyncTask delegate) {
        Boolean b = null;
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");

            if (b) {

            } else {
                Log.e("MAUVAIS RETOUR", jsonMsg.getString("retour"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(b, b);
    }
}
