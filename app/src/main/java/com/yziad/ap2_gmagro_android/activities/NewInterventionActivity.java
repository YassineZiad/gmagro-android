package com.yziad.ap2_gmagro_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.yziad.ap2_gmagro_android.R;
import com.yziad.ap2_gmagro_android.daos.DatasDAO;
import com.yziad.ap2_gmagro_android.daos.DelegateAsyncTask;
import com.yziad.ap2_gmagro_android.daos.IntervenantDAO;
import com.yziad.ap2_gmagro_android.models.Activite;
import com.yziad.ap2_gmagro_android.models.CSOD;
import com.yziad.ap2_gmagro_android.models.Machine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewInterventionActivity extends AppCompatActivity {

    SimpleDateFormat sdfDateHeure = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
    String date, time = "";
    String dateFin, timeFin = "";

    private ArrayAdapter<Activite> adapterActivite;
    private ArrayAdapter<Machine> adapterMachine;
    private ArrayAdapter<CSOD> adapterCausesDefaut;
    private ArrayAdapter<CSOD> adapterCausesObjet;
    private ArrayAdapter<CSOD> adapterSymptomesDefaut;
    private ArrayAdapter<CSOD> adapterSymptomesObjet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);

        loadDatas();

        TextView naTextView = findViewById(R.id.niTextView);
        naTextView.setText("Intervenant : " + IntervenantDAO.getInstance().getConnectedUser());

        Button niDisconnect = findViewById(R.id.niDisconnect);
        niDisconnect.setOnClickListener(v -> {
            cliqueRetour(findViewById(R.id.niDisconnect));
        });

        Spinner niActivites = findViewById(R.id.niActivites);
        adapterActivite = new ArrayAdapter<Activite>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesActivites());
        niActivites.setAdapter(adapterActivite);

        Spinner niMachines = findViewById(R.id.niMachines);
        adapterMachine = new ArrayAdapter<Machine>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesMachines());
        niMachines.setAdapter(adapterMachine);

        Spinner niCausesDefaut = findViewById(R.id.niCausesDefaut);
        adapterCausesDefaut = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesCausesDefaut());
        niCausesDefaut.setAdapter(adapterCausesDefaut);

        Spinner niCausesObjet = findViewById(R.id.niCausesObjet);
        adapterCausesObjet = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesCausesObjet());
        niCausesObjet.setAdapter(adapterCausesObjet);

        Spinner niSymptomesDefaut = findViewById(R.id.niSymptomesDefaut);
        adapterSymptomesDefaut = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesSymptomesDefaut());
        niSymptomesDefaut.setAdapter(adapterSymptomesDefaut);

        Spinner niSymptomesObjet = findViewById(R.id.niSymptomesObjet);
        adapterSymptomesObjet = new ArrayAdapter<CSOD>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesSymptomesObjet());
        niSymptomesObjet.setAdapter(adapterSymptomesObjet);

        TextView niDateDebut = findViewById(R.id.niDateDebut);
        findViewById(R.id.niDateDebutBtn).setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            TimePickerDialog timePickerDialog = new TimePickerDialog(NewInterventionActivity.this, (view, hh, mm) -> {
                time = hh + ":" + mm + ":00";
                niDateDebut.setText(niDateDebut.getText().toString() + " " + String.format("%02d", hh) + ":" + String.format("%02d", mm));
            }, hourOfDay, minute, true);
            timePickerDialog.show();

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewInterventionActivity.this, (view, yyyy, MM, dd) -> {
                date = yyyy + "-" + (MM + 1) + "-" + dd;
                niDateDebut.setText(String.format("%02d", dd) + "/" + String.format("%02d", (MM + 1)) + "/" + yyyy);
            }, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        });

        CheckBox niTermine = findViewById(R.id.niTermine);
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

            TimePickerDialog timePickerDialog = new TimePickerDialog(NewInterventionActivity.this, (view, hh, mm) -> {
                timeFin = hh + ":" + mm + ":00";
                niDateFin.setText(niDateFin.getText().toString() + " " + String.format("%02d", hh) + ":" + String.format("%02d", mm));
            }, hourOfDay, minute, true);
            timePickerDialog.show();

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewInterventionActivity.this, (view, yyyy, MM, dd) -> {
                dateFin = yyyy + "-" + (MM + 1) + "-" + dd;
                niDateFin.setText(String.format("%02d", dd) + "/" + String.format("%02d", (MM + 1)) + "/" + yyyy);
            }, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        });

        CheckBox niMachineArretee = findViewById(R.id.niMachineArretee);
        niMachineArretee.setOnClickListener(v -> {
            LinearLayout niTempsArretLayout = findViewById(R.id.niTempsArretLayout);
            if (niTempsArretLayout.getVisibility() == View.INVISIBLE) {
                niTempsArretLayout.setVisibility(View.VISIBLE);
            } else {
                niTempsArretLayout.setVisibility(View.INVISIBLE);
            }
        });

        Spinner niTempsArret = findViewById(R.id.niTempsArret);
        ArrayList<String> lesTempsArret = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 60; j = j + 15) {
                lesTempsArret.add(String.format("%02d",i) + ":" + String.format("%02d", j));
            }
        }
        lesTempsArret.add("08:00");
        ArrayAdapter adapterTempsArret = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lesTempsArret);
        niTempsArret.setAdapter(adapterTempsArret);


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
    }

    @Override
    public void onBackPressed() {
        cliqueRetour(findViewById(R.id.niDisconnect));
    }

    private void cliqueRetour(View view) {
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

}