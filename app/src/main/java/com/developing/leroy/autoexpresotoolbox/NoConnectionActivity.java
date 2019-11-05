package com.developing.leroy.autoexpresotoolbox;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        Button indiabt  = findViewById(R.id.btnRefresh);
        indiabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //getActiveNetworkInfo
                android.net.NetworkInfo datac = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); //getActiveNetworkInfo

                if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
                    startActivity(new Intent(NoConnectionActivity.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                }

                else{
                    Toast.makeText(NoConnectionActivity.this, "Sigue tratando, no hay internet", Toast.LENGTH_SHORT).show();
                    //no connection
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
