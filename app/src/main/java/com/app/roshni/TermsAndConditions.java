package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class TermsAndConditions extends AppCompatActivity {

    CheckBox chk1, chk2, chk3, chk4, chk5;
    Button proceed, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        chk1 = findViewById(R.id.checkBox1);
        chk2 = findViewById(R.id.checkBox2);
        chk3 = findViewById(R.id.checkBox3);
        chk4 = findViewById(R.id.checkBox4);
        chk5 = findViewById(R.id.checkBox5);
        proceed = findViewById(R.id.proceed);
        close = findViewById(R.id.close);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (!chk1.isChecked()) {

                    Toast.makeText(TermsAndConditions.this, "You must agree our all terms & conditions to proceed", Toast.LENGTH_SHORT).show();

                } else if (!chk2.isChecked()) {
                    Toast.makeText(TermsAndConditions.this, "You must agree our all terms & conditions to proceed", Toast.LENGTH_SHORT).show();

                } else if (!chk3.isChecked()) {
                    Toast.makeText(TermsAndConditions.this, "You must agree our all terms & conditions to proceed", Toast.LENGTH_SHORT).show();

                } else if (!chk4.isChecked()) {
                    Toast.makeText(TermsAndConditions.this, "You must agree our all terms & conditions to proceed", Toast.LENGTH_SHORT).show();

                } else if (!chk5.isChecked()) {
                    Toast.makeText(TermsAndConditions.this, "You must agree our all terms & conditions to proceed", Toast.LENGTH_SHORT).show();

                } else {
*/
                    Intent intent = new Intent(TermsAndConditions.this, CreatePIN.class);
                    startActivity(intent);
                    Toast.makeText(TermsAndConditions.this, "Please create a PIN to continue", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                //}

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();

            }
        });
    }
}
