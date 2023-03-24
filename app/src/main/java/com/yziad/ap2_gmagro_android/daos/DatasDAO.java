package com.yziad.ap2_gmagro_android.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yziad.ap2_gmagro_android.models.Activite;
import com.yziad.ap2_gmagro_android.models.CSOD;
import com.yziad.ap2_gmagro_android.models.Intervention;
import com.yziad.ap2_gmagro_android.models.Machine;
import com.yziad.ap2_gmagro_android.wsHTTPS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatasDAO {

    private static DatasDAO instance;
    private String controller = "controller=datas&";
    private ObjectMapper om = new ObjectMapper();

    private List<Intervention> lesInterventions;
    private List<Activite> lesActivites;
    private List<Machine> lesMachines;

    private List<CSOD> lesCausesDefaut, lesCausesObjet, lesSymptomesDefaut, lesSymptomesObjet;

    private DatasDAO() {
        lesInterventions = new ArrayList<>();
        lesActivites = new ArrayList<>();
        lesMachines = new ArrayList<>();

        lesCausesDefaut = new ArrayList<>();
        lesCausesObjet = new ArrayList<>();
        lesSymptomesDefaut = new ArrayList<>();
        lesSymptomesObjet = new ArrayList<>();
    }

    public static DatasDAO getInstance() {
        if (instance == null) {
            instance = new DatasDAO();
        }
        return instance;
    }

    public List<Intervention> getLesInterventions() {
        return lesInterventions;
    }

    public List<Activite> getLesActivites() {
        return lesActivites;
    }

    public List<Machine> getLesMachines() {
        return lesMachines;
    }

    public List<CSOD> getLesCausesDefaut() {
        return lesCausesDefaut;
    }

    public List<CSOD> getLesCausesObjet() {
        return lesCausesObjet;
    }

    public List<CSOD> getLesSymptomesDefaut() {
        return lesSymptomesDefaut;
    }

    public List<CSOD> getLesSymptomesObjet() {
        return lesSymptomesObjet;
    }

    //INTERVENTIONS
    public void loadAllInterventions(DelegateAsyncTask delegate) {
        String url = controller + "action=interventions";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadInterventions(jsonMsg, delegate);
            }
        };
        ws.execute(url);
    }

    private void traiterPostLoadInterventions(String jRetour, DelegateAsyncTask delegate) {
        Boolean b = null;
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");

            if (b) {
                lesInterventions.clear();
                String jsonLesInterventions = jsonMsg.getString("retour");
                try {
                    Arrays.asList(om.readValue(jsonLesInterventions, Intervention[].class)).forEach(intervention -> lesInterventions.add(intervention));
                    b = true;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MAUVAIS RETOUR", jsonMsg.getString("retour"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(jRetour, b);
    }


    //ACTIVITES
    public void loadAllActivites(DelegateAsyncTask delegate) {
        String url = controller + "action=activites";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadActivites(jsonMsg, delegate);
            }
        };
        ws.execute(url);
    }

    private void traiterPostLoadActivites(String jRetour, DelegateAsyncTask delegate) {
        Boolean b = null;
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");

            if (b) {
                lesActivites.clear();
                String jsonLesActivites = jsonMsg.getString("retour");
                try {
                    Arrays.asList(om.readValue(jsonLesActivites, Activite[].class)).forEach(activite -> lesActivites.add(activite));

                } catch (JsonProcessingException e) {
                    b = false;
                    e.printStackTrace();

                }
            } else {
                Log.e("MAUVAIS RETOUR", jsonMsg.getString("retour"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(jRetour, b);
    }


    //MACHINES
    public void loadAllMachines(DelegateAsyncTask delegate) {
        String url = controller + "action=machines";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadMachines(jsonMsg, delegate);
            }
        };
        ws.execute(url);
    }

    private void traiterPostLoadMachines(String jRetour, DelegateAsyncTask delegate) {
        Boolean b = null;
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");

            if (b) {
                lesMachines.clear();
                String jsonLesMachines = jsonMsg.getString("retour");
                try {
                    Arrays.asList(om.readValue(jsonLesMachines, Machine[].class)).forEach(machine -> lesMachines.add(machine));

                } catch (JsonProcessingException e) {
                    b = false;
                    e.printStackTrace();
                }
            } else {
                Log.e("MAUVAIS RETOUR", jsonMsg.getString("retour"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(jRetour, b);
    }

    //CAUSES ET SYMPTOMES (CSOD)
    public void loadAllCausesDefaut(DelegateAsyncTask delegate) {
        String url = controller + "action=causes_defaut";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadCSOD(jsonMsg, delegate, "causes_defaut");
            }
        };
        ws.execute(url);
    }

    public void loadAllCausesObjet(DelegateAsyncTask delegate) {
        String url = controller + "action=causes_objet";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadCSOD(jsonMsg, delegate, "causes_objet");
            }
        };
        ws.execute(url);
    }

    public void loadAllSymptomesDefaut(DelegateAsyncTask delegate) {
        String url = controller + "action=symptomes_defaut";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadCSOD(jsonMsg, delegate, "symptomes_defaut");
            }
        };
        ws.execute(url);
    }

    public void loadAllSymptomesObjet(DelegateAsyncTask delegate) {
        String url = controller + "action=symptomes_objet";
        wsHTTPS ws = new wsHTTPS() {
            @Override
            protected void onPostExecute(String jsonMsg) {
                traiterPostLoadCSOD(jsonMsg, delegate, "symptomes_objet");
            }
        };
        ws.execute(url);
    }

    private void traiterPostLoadCSOD(String jRetour, DelegateAsyncTask delegate, String typeCSOD) {
        Boolean b = null;
        try {
            JSONObject jsonMsg = new JSONObject(jRetour);
            b = jsonMsg.getBoolean("success");

            if (b) {
                String jsonLesCSOD = jsonMsg.getString("retour");
                try {
                    Arrays.asList(om.readValue(jsonLesCSOD, CSOD[].class)).forEach(csod -> {
                        csod.setType(typeCSOD);
                        switch (typeCSOD) {
                            case "causes_defaut":
                                lesCausesDefaut.add(csod);
                                break;

                            case "causes_objet":
                                lesCausesObjet.add(csod);
                                break;

                            case "symptomes_defaut":
                                lesSymptomesDefaut.add(csod);
                                break;

                            case "symptomes_objet":
                                lesSymptomesObjet.add(csod);
                                break;

                            default:
                                break;
                        }
                    });
                    b = true;

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MAUVAIS RETOUR", jsonMsg.getString("retour"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        delegate.traiterFinWS(jRetour, b);
    }
}
