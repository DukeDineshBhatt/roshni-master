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

import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.getTncPOJO.Data;
import com.app.roshni.getTncPOJO.getTncBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TermsAndConditions3 extends AppCompatActivity {

    CheckBox chk1, chk2, chk3, chk4, chk5;
    Button proceed, close;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions3);

        type = getIntent().getStringExtra("type");

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

                Bean b1 = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b1.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<getTncBean> call = cr.update_tnc(
                        SharePreferenceUtils.getInstance().getString("user_id"),
                        String.valueOf(chk1.isChecked()),
                        String.valueOf(chk2.isChecked()),
                        String.valueOf(chk3.isChecked()),
                        String.valueOf(chk4.isChecked()),
                        String.valueOf(chk5.isChecked())
                );

                call.enqueue(new Callback<getTncBean>() {
                    @Override
                    public void onResponse(Call<getTncBean> call, Response<getTncBean> response) {

                        Toast.makeText(TermsAndConditions3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onFailure(Call<getTncBean> call, Throwable t) {

                    }
                });

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Bean b1 = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b1.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<getTncBean> call = cr.gettnc(
                SharePreferenceUtils.getInstance().getString("user_id")
        );

        call.enqueue(new Callback<getTncBean>() {
            @Override
            public void onResponse(Call<getTncBean> call, Response<getTncBean> response) {

                Data item = response.body().getData();


                try {
                    boolean c1 = Boolean.parseBoolean(item.getC1());
                    boolean c2 = Boolean.parseBoolean(item.getC2());
                    boolean c3 = Boolean.parseBoolean(item.getC3());
                    boolean c4 = Boolean.parseBoolean(item.getC4());
                    boolean c5 = Boolean.parseBoolean(item.getC5());

                    chk1.setChecked(c1);
                    chk2.setChecked(c2);
                    chk3.setChecked(c3);
                    chk4.setChecked(c4);
                    chk5.setChecked(c5);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<getTncBean> call, Throwable t) {

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
