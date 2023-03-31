package com.yziad.ap2_gmagro_android.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yziad.ap2_gmagro_android.models.Activite;
import com.yziad.ap2_gmagro_android.models.Intervenant;
import com.yziad.ap2_gmagro_android.wsHTTPS;

import org.json.JSONException;
import org.json.JSONObject;

public class IntervenantDAO {

    private static IntervenantDAO instance = null;
    private String controller = "controller=intervenant&";
    private ObjectMapper om = new ObjectMapper();

    private Intervenant connectedUser;

    public static IntervenantDAO getInstance() {
        if (instance == null) {
            instance = new IntervenantDAO();
        }
        return instance;
    }

    public Intervenant getConnectedUser() {
        return connectedUser;
    }

    public void connectUser(String login, String password, DelegateAsyncTask delegate) {
        String url = controller + "action=connexion&connexion=Utilisateur&login=" + login + "&password=" + password;
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostConnectUser(jsonMsg, delegate);
            }
        };
        ws.execute(url);
    }

    private void traiterPostConnectUser(String jRetour, DelegateAsyncTask delegate) {
        Boolean b = null;
        String retour = "";
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");
            retour = jsonMsg.getString("retour");

            if (b) {
                try {
                    connectedUser = om.readValue(retour, Intervenant.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(retour, b);
    }

    public void disconnectUser(DelegateAsyncTask delegate) {
        String url = controller + "action=deconnexion";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostDisconnectUser(jsonMsg, delegate);
            }
        };
        ws.execute(url);
    }

    private void traiterPostDisconnectUser(String jRetour, DelegateAsyncTask delegate) {
        Boolean b = null;
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");

            if (b) {
                connectedUser = null;
            } else {
                Log.e("MAUVAIS RETOUR", jsonMsg.getString("retour"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(b, b);
    }

    //public
}