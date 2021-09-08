package com.workersjoint.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.Objects;

public class TermsAndConditions extends AppCompatActivity {

    CheckBox chk1, chk2, chk4, chk5;
    Button proceed, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        chk1 = findViewById(R.id.checkBox1);
        chk2 = findViewById(R.id.checkBox2);
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
                if (chk1.isChecked() || chk2.isChecked() || chk4.isChecked() || chk5.isChecked())
                {
                    Intent intent = new Intent(TermsAndConditions.this, CreatePIN.class);
                    intent.putExtra("c1" , chk1.isChecked());
                    intent.putExtra("c2" , chk2.isChecked());
                    intent.putExtra("c3" , false);
                    intent.putExtra("c4" , chk4.isChecked());
                    intent.putExtra("c5" , chk5.isChecked());
                    startActivity(intent);
                    Toast.makeText(TermsAndConditions.this, "Please create a PIN to continue", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                }
                else
                {
                    Toast.makeText(TermsAndConditions.this, "You must agree at least one terms & conditions to proceed", Toast.LENGTH_SHORT).show();
                }


                //}

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();

            }
        });

        chk5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chk1.setChecked(isChecked);
                chk2.setChecked(isChecked);
                chk4.setChecked(isChecked);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
