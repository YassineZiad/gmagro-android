package com.yziad.ap2_gmagro_android.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.yziad.ap2_gmagro_android.R;
import com.yziad.ap2_gmagro_android.daos.DelegateAsyncTask;
import com.yziad.ap2_gmagro_android.daos.IntervenantDAO;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ActivityResultLauncher<Intent> launcherConnectionActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
            //activityResult.getResultCode() == 1)
        });

        EditText etLogin = findViewById(R.id.caLogin);
        EditText etPassword = findViewById(R.id.caPassword);

        findViewById(R.id.caConnect).setOnClickListener(v -> {
            String login = etLogin.getText().toString();
            String password = etPassword.getText().toString();
            IntervenantDAO.getInstance().connectUser(login, password, new DelegateAsyncTask() {
                @Override
                public void traiterFinWS(Object result, Boolean b) {
                    String message = "Connexion réussie";
                    if (b) {
                        launchInterventionActivity(launcherConnectionActivity);
                    } else {
                        message = (String) result;
                    }
                    connectToast(message);
                }
            });
        });
    }

    private void connectToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void launchInterventionActivity(ActivityResultLauncher<Intent> launcherConnectionActivity){
        Intent intent = new Intent(this, InterventionsActivity.class);
        launcherConnectionActivity.launch(intent);
    }

}