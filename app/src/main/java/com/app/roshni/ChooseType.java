package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseType extends AppCompatActivity {

    Button brand , contractor , worker , officer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        brand = findViewById(R.id.button4);
        contractor = findViewById(R.id.button5);
        worker = findViewById(R.id.button6);
        officer = findViewById(R.id.button20);


        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ChooseType.this , SignupLogin.class);
                startActivity(intent);
                //finish();


            }
        });

        contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ChooseType.this , SignupLogin.class);
                startActivity(intent);
                //finish();


            }
        });


        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ChooseType.this , SignupLogin.class);
                startActivity(intent);
                //finish();


            }
        });

        officer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ChooseType.this , LoginActivity.class);
                startActivity(intent);
                //finish();


            }
        });


    }
}
