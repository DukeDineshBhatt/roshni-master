package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class ChooseType extends AppCompatActivity {

    Button brand , contractor , worker , officer;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        brand = findViewById(R.id.button4);
        contractor = findViewById(R.id.button5);
        worker = findViewById(R.id.button6);
        officer = findViewById(R.id.button20);
        check = findViewById(R.id.checkBox);


        SpannableString myString = new SpannableString("I have read Privacy Policies");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(ChooseType.this , Web.class);
                intent.putExtra("title" , getString(R.string.policies));
                intent.putExtra("url" , "https://mrtecks.com/goodbusinessapp/policies.php");
                startActivity(intent);
            }
        };

        //For Click
        myString.setSpan(clickableSpan,12,28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //For UnderLine
        myString.setSpan(new UnderlineSpan(),13,28,0);

        //For Bold
        myString.setSpan(new StyleSpan(Typeface.BOLD),13,28,0);

        //Finally you can set to textView.

        check.setText(myString);
        check.setMovementMethod(LinkMovementMethod.getInstance());


        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.isChecked())
                {
                    Intent intent = new Intent(ChooseType.this , SignupLogin.class);
                    intent.putExtra("type" , "brand");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ChooseType.this, "Please read Privacy Policies", Toast.LENGTH_SHORT).show();
                }


                //finish();


            }
        });

        contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.isChecked())
                {
                    Intent intent = new Intent(ChooseType.this , SignupLogin.class);
                    intent.putExtra("type" , "contractor");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ChooseType.this, "Please read Privacy Policies", Toast.LENGTH_SHORT).show();
                }


                //finish();


            }
        });


        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.isChecked())
                {
                    Intent intent = new Intent(ChooseType.this , SignupLogin.class);
                    intent.putExtra("type" , "worker");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ChooseType.this, "Please read Privacy Policies", Toast.LENGTH_SHORT).show();
                }


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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
