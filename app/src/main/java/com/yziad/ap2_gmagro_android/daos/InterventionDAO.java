package com.yziad.ap2_gmagro_android.daos;

import android.util.Log;

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

    public void createIntervention(DelegateAsyncTask delegate) {
        String url = controller + "action=create";
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
