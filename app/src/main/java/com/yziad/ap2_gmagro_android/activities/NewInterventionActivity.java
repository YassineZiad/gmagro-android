package com.yziad.ap2_gmagro_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.yziad.ap2_gmagro_android.R;
import com.yziad.ap2_gmagro_android.daos.DatasDAO;
import com.yziad.ap2_gmagro_android.daos.DelegateAsyncTask;
import com.yziad.ap2_gmagro_android.daos.IntervenantDAO;
import com.yziad.ap2_gmagro_android.models.Activite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewInterventionActivity extends AppCompatActivity {

    SimpleDateFormat sdfDateHeure = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
    String date = "";
    String time = "";

    private ArrayAdapter<Activite> adapterActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);

        loadDatas();

        TextView naTextView = findViewById(R.id.naTextView);
        naTextView.setText("Intervenant : " + IntervenantDAO.getInstance().getConnectedUser());

        Button naDisconnect = findViewById(R.id.naDisconnect);
        naDisconnect.setOnClickListener(v -> {
            setResult(2);
            finish();
        });

        Spinner niActivites = findViewById(R.id.niActivites);
        adapterActivite = new ArrayAdapter<Activite>(this, android.R.layout.simple_spinner_item, DatasDAO.getInstance().getLesActivites());
        niActivites.setAdapter(adapterActivite);


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
                niDateDebut.setText(niDateDebut.getText().toString() + " " + time);
            }, hourOfDay, minute, true);
            timePickerDialog.show();

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewInterventionActivity.this, (view, yyyy, MM, dd) -> {
                date = yyyy + "-" + (MM + 1) + "-" + dd;
                niDateDebut.setText(dd + "/" + MM + "/" + yyyy);
            }, year, month, day);
            datePickerDialog.show();

        });
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
        DatasDAO
    }

}