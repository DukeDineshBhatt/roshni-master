package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.roshni.contractorPOJO.contractorBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Complaint extends AppCompatActivity {
    Toolbar toolbar;
    EditText subject , body;
    Button submit;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        toolbar = findViewById(R.id.toolbar);
        subject = findViewById(R.id.editText8);
        body = findViewById(R.id.editText9);
        submit = findViewById(R.id.button21);
        progress = findViewById(R.id.progressBar8);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.raise_complaint));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = subject.getText().toString();
                String b = body.getText().toString();

                if (s.length() > 0)
                {
                    if (b.length() > 0)
                    {
                        progress.setVisibility(View.VISIBLE);

                        Bean b1 = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b1.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<contractorBean> call = cr.raiseComplaint(
                                SharePreferenceUtils.getInstance().getString("user_id"),
                                s,
                                b
                        );

                        call.enqueue(new Callback<contractorBean>() {
                            @Override
                            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                                if (response.body().getStatus().equals("1"))
                                {
                                    Toast.makeText(Complaint.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    subject.setText("");
                                    body.setText("");

                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Complaint.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<contractorBean> call, Throwable t) {

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Complaint.this, "Invalid body", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Complaint.this, "Invalid subject", Toast.LENGTH_SHORT).show();
                }

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
