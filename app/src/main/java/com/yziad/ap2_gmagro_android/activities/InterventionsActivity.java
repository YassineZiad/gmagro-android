package com.yziad.ap2_gmagro_android.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yziad.ap2_gmagro_android.R;
import com.yziad.ap2_gmagro_android.daos.DatasDAO;
import com.yziad.ap2_gmagro_android.daos.DelegateAsyncTask;
import com.yziad.ap2_gmagro_android.daos.IntervenantDAO;
import com.yziad.ap2_gmagro_android.models.Activite;
import com.yziad.ap2_gmagro_android.models.Intervention;

public class InterventionsActivity extends AppCompatActivity {

    private ArrayAdapter<Intervention> adapterIntervention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions);

        loadInterventions();

        View.OnClickListener deconnexion = new View.OnClickListener() {
            public void onClick(View view) {
                IntervenantDAO.getInstance().disconnectUser(new DelegateAsyncTask() {
                    @Override
                    public void traiterFinWS(Object result, Boolean b) {
                        if (b) {
                            disconnectToast("Déconnexion réussie");
                            finish();
                        } else {
                            disconnectToast((String) result);
                        }
                    }
                });
            }
        };

        ListView iInterventions = findViewById(R.id.iInterventions);
        adapterIntervention = new ArrayAdapter<Intervention>(this, android.R.layout.simple_list_item_1, DatasDAO.getInstance().getLesInterventions());
        iInterventions.setAdapter(adapterIntervention);

        Button iaDisconnect = findViewById(R.id.naDisconnect);
        iaDisconnect.setOnClickListener(deconnexion);

        ActivityResultLauncher<Intent> launcherInterventionsActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
            switch (activityResult.getResultCode()) {
                case 1:
                    loadInterventions();
                    break;
                case 2:
                    iaDisconnect.performClick();
                    break;
            }
            //activityResult.getResultCode() == 1)
        });

        TextView iaTextView = findViewById(R.id.iaTextView);
        iaTextView.setText("Intervenant : " + IntervenantDAO.getInstance().getConnectedUser());

        Button iaCreerIntervention = findViewById(R.id.iaCreerIntervention);
        iaCreerIntervention.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewInterventionActivity.class);
            launcherInterventionsActivity.launch(intent);
        });
    }

    private void loadInterventions() {
        DatasDAO.getInstance().loadAllInterventions(new DelegateAsyncTask() {
            @Override
            public void traiterFinWS(Object result, Boolean b) {
                if (b) {
                    adapterIntervention.notifyDataSetChanged();
                }
            }
        });
    }

    private void disconnectToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}