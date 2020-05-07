package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;
import com.google.gson.internal.LinkedTreeMap;
import com.mukesh.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OTP3 extends AppCompatActivity {

    OtpView otp;
    Button submit;
    TextView resend;
    ProgressBar progress;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p3);

        phone = getIntent().getStringExtra("phone");

        otp = findViewById(R.id.textView5);
        submit = findViewById(R.id.button3);
        resend = findViewById(R.id.textView6);
        progress = findViewById(R.id.progressBar2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ot = otp.getText().toString();

                Log.d("otp", ot);

                if (ot.length() > 0) {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<verifyBean> call = cr.verify(phone, ot);
                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {


                            if (response.body().getStatus().equals("1")) {


                                Data item = response.body().getData();



                                Intent intent = new Intent(OTP3.this , CreatePIN2.class);
                                intent.putExtra("id" , item.getUserId());
                                startActivity(intent);
                                finishAffinity();





                            } else {
                                Toast toast = Toast.makeText(OTP3.this, response.body().getMessage(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                                toast.show();
                            }

                            progress.setVisibility(View.GONE);


                        }

                        @Override
                        public void onFailure(Call<verifyBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                } else {
                    Toast toast = Toast.makeText(OTP3.this, "Please enter a valid OTP", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                    toast.show();

                }


            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                Call<verifyBean> call = cr.resend(phone);

                call.enqueue(new Callback<verifyBean>() {
                    @Override
                    public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {
                        Toast toast = Toast.makeText(OTP3.this, response.body().getMessage(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                        toast.show();

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<verifyBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


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
