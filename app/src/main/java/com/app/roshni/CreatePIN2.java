package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;
import com.mukesh.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreatePIN2 extends AppCompatActivity {

    OtpView pin, cpin;
    Button submit;
    ProgressBar progress;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_p_i_n2);

        id = getIntent().getStringExtra("id");

        pin = findViewById(R.id.textView5);
        cpin = findViewById(R.id.textView8);
        submit = findViewById(R.id.button3);
        progress = findViewById(R.id.progressBar2);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String p = pin.getText().toString();
                String c = cpin.getText().toString();

                if (p.length() == 4) {


                    if (c.equals(p))
                    {
                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<verifyBean> call = cr.createPIN(id, p);
                        call.enqueue(new Callback<verifyBean>() {
                            @Override
                            public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {


                                if (response.body().getStatus().equals("1")) {


                                    Toast.makeText(CreatePIN2.this, "PIN changed successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreatePIN2.this , SignupLogin.class);
                                    startActivity(intent);
                                    finishAffinity();


                                } else {
                                    Toast.makeText(CreatePIN2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                progress.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<verifyBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(CreatePIN2.this, "PIN did not match", Toast.LENGTH_SHORT).show();
                    }





                } else {
                    Toast.makeText(CreatePIN2.this, "Please enter a valid PIN", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}