package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Objects;

public class TermsAndConditions2 extends AppCompatActivity {

    CheckBox chk1, chk2, chk4, chk5;
    Button proceed, close;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        type = getIntent().getStringExtra("type");

        chk1 = findViewById(R.id.checkBox1);
        chk2 = findViewById(R.id.checkBox2);
        chk4 = findViewById(R.id.checkBox4);
        chk5 = findViewById(R.id.checkBox5);
        proceed = findViewById(R.id.proceed);
        close = findViewById(R.id.close);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chk1.isChecked() || chk2.isChecked() || chk4.isChecked() || chk5.isChecked())
                {
                    if (type.equals("worker"))
                    {
                        Intent intent = new Intent(TermsAndConditions2.this , REgister.class);
                        startActivity(intent);
                        finishAffinity();
                    }else if (type.equals("brand"))
                    {
                        Intent intent = new Intent(TermsAndConditions2.this , Register2.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                    else
                    {
                        Intent intent = new Intent(TermsAndConditions2.this , Register3.class);
                        startActivity(intent);
                        finishAffinity();
                    }

                    Toast.makeText(TermsAndConditions2.this, "Profile is incomplete. Please complete your profile first", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(TermsAndConditions2.this, "You must agree at least one terms & conditions to proceed", Toast.LENGTH_SHORT).show();
                }



            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();

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
