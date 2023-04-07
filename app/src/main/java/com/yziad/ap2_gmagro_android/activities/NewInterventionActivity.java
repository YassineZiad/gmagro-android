package com.yziad.ap2_gmagro_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yziad.ap2_gmagro_android.R;
import com.yziad.ap2_gmagro_android.daos.DatasDAO;
import com.yziad.ap2_gmagro_android.daos.DelegateAsyncTask;
import com.yziad.ap2_gmagro_android.daos.IntervenantDAO;
import com.yziad.ap2_gmagro_android.daos.InterventionDAO;
import com.yziad.ap2_gmagro_android.models.Activite;
import com.yziad.ap2_gmagro_android.models.CSOD;
import com.yziad.ap2_gmagro_android.models.Intervenant;
import com.yziad.ap2_gmagro_android.models.Intervention;
import com.yziad.ap2_gmagro_android.models.InterventionIntervenant;
import com.yziad.ap2_gmagro_android.models.Machine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewInterventionActivity extends AppCompatActivity {

    private SimpleDateFormat sdfDateHeure = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
    private String date, time = "";
    private String dateFin, timeFin = "";
    private Long dateDebutMillis = 0L;

    private ArrayAdapter<Activite> adapterActivite;
    private ArrayAdapter<Machine> adapterMachine;
    private ArrayAdapter<CSOD> adapterCausesDefaut;
    private ArrayAdapter<CSOD> adapterCausesObjet;
    private ArrayAdapter<CSOD> adapterSymptomesDefaut;
    private ArrayAdapter<CSOD> adapterSymptomesObjet;
    private ArrayAdapter<Intervenant> adapterIntervenant;

    private List<InterventionIntervenant> lesInterventionIntervenants = new ArrayList<>();
    private ArrayAdapter<InterventionIntervenant> adapterInterventionIntervenants;

    private Spinner niActivites;
    private Spinner niMachines;
    private Spinner niCausesDefaut;
    private Spinner niCausesObjet;
    private Spinner niSymptomesDefaut;
    private Spinner niSymptomesObjet;

    private EditText niCommentaire;

    private Spinner niIntervenants;
    private ListView niInterventionIntervenants;
    private Spinner niTempsArret;
    private Spinner niTpsIntervention;

    private CheckBox niTermine;
    private CheckBox niMachineArretee;
    private CheckBox niChangeOrgane;
    private CheckBox niPertes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);

        TextView naTextView = findViewById(R.id.niTextView);
        naTextView.setText("Intervenant : " + IntervenantDAO.getInstance().getConnectedUser());

        Button niDisconnect = findViewById(R.id.niDisconnect);
        niDisconnect.setOnClickListener(v -> cliqueDeconnexion(niDisconnect));

        Button niAnnuler = findViewById(R.id.niAnnuler);
        niAnnuler.setOnClickListener(v -> cliqueRetour(niAnnuler));


        TextView niDateDebut = findViewById(R.id.niDateDebut);
        ImageButton niDateDebutBtn = findViewById(R.id.niDateDebutBtn);
        niDateDebutBtn.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            TimePickerDialog timePickerDialog = new TimePickerDialog(NewInterventionActivity.this, android.R.style.Theme_Holo_Dialog, (view, hh, mm) -> {
                time = hh + ":" + mm + ":00";
                niDateDebut.setText(niDateDebut.getText().toString() + " " + String.format("%02d", hh) + ":" + String.format("%02d", mm));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date parsedDate = sdf.parse(date + " " + time);
                    dateDebutMillis = parsedDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }, hourOfDay, minute, true);
            timePickerDialog.show();
            timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewInterventionActivity.this,(view, yyyy, MM, dd) -> {
                date = yyyy + "-" + (MM + 1) + "-" + dd;
                niDateDebut.setText(String.format("%02d", dd) + "/" + String.format("%02d", (MM + 1)) + "/" + yyyy);
            }, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

        });
        niDateDebutBtn.performClick();

        loadDatas();
        adaptViews();

        niTermine = findViewById(R.id.niTermine);
        niTermine.setOnClickListener(v -> {
            LinearLayout niDateFinLayout = findViewById(R.id.niDateFinLayout);
            if (niDateFinLayout.getVisibility() == View.INVISIBLE) {
                niDateFinLayout.setVisibility(View.VISIBLE);
            } else {
                niDateFinLayout.setVisibility(View.INVISIBLE);
            }

        });

        TextView niDateFin = findViewById(R.id.niDateFin);
        findViewById(R.id.niDateFinBtn).setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            TimePickerDialog timePickerDialog = new TimePickerDialog(NewInterventionActivity.this, android.R.style.Theme_Holo_Dialog, (view, hh, mm) -> {
                timeFin = hh + ":" + mm + ":00";
                niDateFin.setText(niDateFin.getText().toString() + " " + String.format("%02d", hh) + ":" + String.format("%02d", mm));
            }, hourOfDay, minute, true);
            timePickerDialog.show();
            timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewInterventionActivity.this, (view, yyyy, MM, dd) -> {
                dateFin = yyyy + "-" + (MM + 1) + "-" + dd;
                niDateFin.setText(String.format("%02d", dd) + "/" + String.format("%02d", (MM + 1)) + "/" + yyyy);
            }, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            if (dateDebutMillis != 0) {
                datePickerDialog.getDatePicker().setMinDate(dateDebutMillis - 1000);
            }
            datePickerDialog.show();
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

        });

        niMachineArretee = findViewById(R.id.niMachineArretee);
        niMachineArretee.setOnClickListener(v -> {
            LinearLayout niTempsArretLayout = findViewById(R.id.niTempsArretLayout);
            if (niTempsArretLayout.getVisibility() == View.INVISIBLE) {
                niTempsArretLayout.setVisibility(View.VISIBLE);
            } else {
                niTempsArretLayout.setVisibility(View.INVISIBLE);
            }
        });

        Button niAjouterIntervenant = findViewById(R.id.niAjouterIntervenant);
        niAjouterIntervenant.setOnClickListener(v -> ajouterInterventionIntervenant());

        niInterventionIntervenants = findViewById(R.id.niInterventionIntervenants);
        niInterventionIntervenants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                supprimerInterventionIntervenant((InterventionIntervenant) niInterventionIntervenants.getItemAtPosition(pos), arg1);
                return false;
            }
        });

        Button niValider = findViewById(R.id.niValider);
        niValider.setOnClickListener(v -> validerAjoutIntervention(niValider));
    }

    private void loadDatas() {
        DatasDAO.getInstance().loadAllActivites(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterActivite.notifyDataSetChanged();
                }
            }
        });
        DatasDAO.getInstance().loadAllMachines(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterMachine.notifyDataSetChanged();
                }
            }
        });
        DatasDAO.getInstance().loadAllCausesDefaut(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if(b) {
                    adapterCausesDefaut.notifyDataSetChanged();
                }
            }
        });
        DatasDAO.getInstance().loadAllCausesObjet(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterCausesObjet.notifyDataSetChanged();
                }
            }
        });
        DatasDAO.getInstance().loadAllSymptomesDefaut(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterSymptomesDefaut.notifyDataSetChanged();
                }
            }
        });
        DatasDAO.getInstance().loadAllSymptomesObjet(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterSymptomesObjet.notifyDataSetChanged();
                }
            }
        });
        DatasDAO.getInstance().loadAllIntervenants(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterIntervenant.notifyDataSetChanged();
                }
            }
        });
    }

    private void adaptViews() {

        niActivites = findViewById(R.id.niActivites);
        adapterActivite = new ArrayAdapter<Activite>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesActivites());
        niActivites.setAdapter(adapterActivite);

        niMachines = findViewById(R.id.niMachines);
        adapterMachine = new ArrayAdapter<Machine>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesMachines());
        niMachines.setAdapter(adapterMachine);

        niCausesDefaut = findViewById(R.id.niCausesDefaut);
        adapterCausesDefaut = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesCausesDefaut());
        niCausesDefaut.setAdapter(adapterCausesDefaut);

        niCausesObjet = findViewById(R.id.niCausesObjet);
        adapterCausesObjet = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesCausesObjet());
        niCausesObjet.setAdapter(adapterCausesObjet);

        niSymptomesDefaut = findViewById(R.id.niSymptomesDefaut);
        adapterSymptomesDefaut = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesSymptomesDefaut());
        niSymptomesDefaut.setAdapter(adapterSymptomesDefaut);

        niSymptomesObjet = findViewById(R.id.niSymptomesObjet);
        adapterSymptomesObjet = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesSymptomesObjet());
        niSymptomesObjet.setAdapter(adapterSymptomesObjet);

        niIntervenants = findViewById(R.id.niIntervenants);
        adapterIntervenant = new ArrayAdapter<Intervenant>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesIntervenants());
        niIntervenants.setAdapter(adapterIntervenant);

        niInterventionIntervenants = findViewById(R.id.niInterventionIntervenants);
        adapterInterventionIntervenants = new ArrayAdapter<InterventionIntervenant>(this, android.R.layout.simple_list_item_1, lesInterventionIntervenants);
        niInterventionIntervenants.setAdapter(adapterInterventionIntervenants);

        ArrayList<String> lesTempsArret = new ArrayList<>();
        lesTempsArret.add("00:15");
        lesTempsArret.add("00:30");
        lesTempsArret.add("00:45");
        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < 60; j = j + 15) {
                lesTempsArret.add(String.format("%02d",i) + ":" + String.format("%02d", j));
            }
        }
        lesTempsArret.add("08:00");

        niTempsArret = findViewById(R.id.niTempsArret);
        ArrayAdapter adapterTempsArret = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lesTempsArret);
        niTempsArret.setAdapter(adapterTempsArret);

        niTpsIntervention = findViewById(R.id.niTpsIntervention);
        ArrayAdapter adapterTempsIntervention = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lesTempsArret);
        niTpsIntervention.setAdapter(adapterTempsIntervention);

        niTermine = findViewById(R.id.niTermine);
        niMachineArretee = findViewById(R.id.niMachineArretee);
        niChangeOrgane = findViewById(R.id.niChangeOrgane);
        niPertes = findViewById(R.id.niPertes);

        niCommentaire = findViewById(R.id.niCommentaire);
    }

    @Override
    public void onBackPressed() { cliqueRetour(findViewById(R.id.niAnnuler)); }

    private void cliqueRetour(View view) {
        AlertDialog.Builder dialRetour = new AlertDialog.Builder(view.getContext());
        dialRetour.setTitle("Annuler");
        dialRetour.setMessage("Annuler la nouvelle intervention ?");
        dialRetour.setCancelable(false);

        dialRetour.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                setResult(1);
                finish();
            }
        });

        dialRetour.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialRetour.show();
    }

    private void cliqueDeconnexion(View view) {
        AlertDialog.Builder dialRetour = new AlertDialog.Builder(view.getContext());
        dialRetour.setTitle("Déconnexion");
        dialRetour.setMessage("Annuler la nouvelle intervention et se déconnecter ?");
        dialRetour.setCancelable(false);

        dialRetour.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                setResult(2);
                finish();
            }
        });

        dialRetour.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialRetour.show();
    }

    private void ajouterInterventionIntervenant() {
        Spinner niIntervenants = findViewById(R.id.niIntervenants);
        Spinner niTpsIntervention = findViewById(R.id.niTpsIntervention);

        Intervenant i = (Intervenant) niIntervenants.getSelectedItem();
        String temps = niTpsIntervention.getSelectedItem().toString();

        InterventionIntervenant intervInt = new InterventionIntervenant(null, i, temps);

        lesInterventionIntervenants.add(intervInt);
        adapterInterventionIntervenants.notifyDataSetChanged();

        adapterIntervenant.remove(i);
        if (adapterIntervenant.getCount() == 0) {
            findViewById(R.id.niAjouterIntervenant).setVisibility(View.GONE);
        }
    }

    private void supprimerInterventionIntervenant(InterventionIntervenant intervInt, View view) {

        AlertDialog.Builder dialogDeleteIntervInt = new AlertDialog.Builder(view.getContext());
        dialogDeleteIntervInt.setTitle("Annuler l'intervenant");
        dialogDeleteIntervInt.setMessage("Annuler l'intervention de l'intervenant " + intervInt + " ?");
        dialogDeleteIntervInt.setCancelable(false);

        dialogDeleteIntervInt.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                Intervenant i = intervInt.getIntervenant();

                lesInterventionIntervenants.remove(intervInt);
                adapterInterventionIntervenants.notifyDataSetChanged();

                adapterIntervenant.add(i);
                findViewById(R.id.niAjouterIntervenant).setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });

        dialogDeleteIntervInt.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDeleteIntervInt.show();

    }

    private void validerAjoutIntervention(View view) {

        AlertDialog.Builder dialogValiderInterv = new AlertDialog.Builder(view.getContext());
        dialogValiderInterv.setTitle("Créer l'intervention");
        dialogValiderInterv.setMessage("Valider et créer l'intervention ?");
        dialogValiderInterv.setCancelable(false);

        dialogValiderInterv.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                creerIntervention();
                dialogInterface.dismiss();
            }
        });

        dialogValiderInterv.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogValiderInterv.show();

    }

    private void creerIntervention() {

        String dh_debut = date + " " + time;
        String dh_fin = null;

        String activite = ((Activite) niActivites.getSelectedItem()).getCode();
        String machine = ((Machine) niMachines.getSelectedItem()).getCode();
        String cd = ((CSOD) niCausesDefaut.getSelectedItem()).getCode();
        String co = ((CSOD) niCausesObjet.getSelectedItem()).getCode();
        String sd = ((CSOD) niSymptomesDefaut.getSelectedItem()).getCode();
        String so = ((CSOD) niSymptomesObjet.getSelectedItem()).getCode();

        String intervInts = "";
        for (InterventionIntervenant intervInt : lesInterventionIntervenants) {
            intervInts += intervInt.getIntervenant().getLogin() + "|" + intervInt.getTps_time() + "||";
            Log.e("INTERV-INTS", intervInt.getIntervenant().getLogin() + "|" + intervInt.getTps_time() + "||");
        }

        String login = IntervenantDAO.getInstance().getConnectedUser().getLogin();
        String site_uai = IntervenantDAO.getInstance().getConnectedUser().getSite_uai();
        String commentaire = niCommentaire.getText().toString();
        String temps_arret = null;

        if (niTermine.isEnabled()) {
            dh_fin = dateFin + " " + timeFin;
        }
        if (niMachineArretee.isEnabled()) {
            temps_arret = niTempsArret.getSelectedItem().toString();
        }

        Intervention i = new Intervention(0, dh_debut, dh_fin, commentaire, temps_arret, niChangeOrgane.isEnabled() + "", niPertes.isEnabled() + "",
                null, null, login, site_uai, activite, machine, cd, co, sd, so);
        InterventionDAO.getInstance().createIntervention(i, intervInts, new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                setResult(1);
                finish();
            }
        });

    }

}