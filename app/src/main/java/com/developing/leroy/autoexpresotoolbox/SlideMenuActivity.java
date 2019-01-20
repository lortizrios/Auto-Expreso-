package com.developing.leroy.autoexpresotoolbox;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);



        //Boton trasparente del menu
        btnTrasparente = (Button) findViewById(R.id.botonTrasparente);

        infoClick = (LinearLayout) findViewById(R.id.infoClick);
        sobreProg = (LinearLayout) findViewById(R.id.sobreProg);




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

                infoClick.setBackgroundColor(Color.parseColor("#1B000000"));
                Toast.makeText(SlideMenuActivity.this, "Oprimiste el menu", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //Layout Sobre el programador
        sobreProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sobreProg.setBackgroundColor(Color.parseColor("#1B000000"));
                Toast.makeText(SlideMenuActivity.this, "Oprimiste el menu", Toast.LENGTH_SHORT).show();
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
