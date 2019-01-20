package com.developing.leroy.autoexpresotoolbox;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.widget.SliderPanel;

public class SlideMenuActivity extends AppCompatActivity {

    private SlidrInterface slidr;
    private Button btnTrasparente;

    //Slide menu
    private LinearLayout infoClick;
    private LinearLayout sobreProg;
    private LinearLayout donar;
    private LinearLayout contactProg;
    private LinearLayout bug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);



        //Boton trasparente del menu
        btnTrasparente = (Button) findViewById(R.id.botonTrasparente);

        infoClick = (LinearLayout) findViewById(R.id.infoClick);
        sobreProg = (LinearLayout) findViewById(R.id.sobreProg);
        donar = (LinearLayout) findViewById(R.id.donar);
        contactProg = (LinearLayout) findViewById(R.id.contactProg);
        bug = (LinearLayout) findViewById(R.id.bug);

        //Imprime version de la aplicación
        TextView versionName = findViewById(R.id.version);
        versionName.setText(BuildConfig.VERSION_NAME);


        //Cierra el Activity onClick
        btnTrasparente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Layout onClick info
        infoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infoClick.setBackgroundColor(Color.parseColor("#0D000000"));
                Toast.makeText(SlideMenuActivity.this, "Informacion de la app", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //Layout Sobre el programador
        sobreProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sobreProg.setBackgroundColor(Color.parseColor("#0D000000"));
                Toast.makeText(SlideMenuActivity.this, "Sobre el programador", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //Layout Sobre donar
        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donar.setBackgroundColor(Color.parseColor("#0D000000"));
                Toast.makeText(SlideMenuActivity.this, "Donar", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //Layout Sobre contactar desarrollador
        contactProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contactProg.setBackgroundColor(Color.parseColor("#0D000000"));
                Toast.makeText(SlideMenuActivity.this, "Contactar Desarrollador", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //Layout Sobre reportar bug
        bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bug.setBackgroundColor(Color.parseColor("#0D000000"));
                Toast.makeText(SlideMenuActivity.this, "Reportar bug", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        Slider();

    }

    //Enable Slide the activity
    private void Slider (){
        slidr = Slidr.attach(this);
    }
}
