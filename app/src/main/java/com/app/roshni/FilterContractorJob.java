package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FilterContractorJob extends AppCompatActivity {

    ImageView cross;
    ChipGroup location, sector;
    TextView start , end;

    List<String> lo, se;

    ProgressBar progress;
    LayoutInflater inflater;
    List<String> exp, ski;

    String skil1, expe1;

    Button filter, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_contractor_job);

        skil1 = getIntent().getStringExtra("skill");

        expe1 = getIntent().getStringExtra("experience");


        exp = new ArrayList<>();
        ski = new ArrayList<>();


        lo = new ArrayList<>();

        se = new ArrayList<>();


        location = findViewById(R.id.location);
        cross = findViewById(R.id.imageButton4);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);

        sector = findViewById(R.id.sector);


        progress = findViewById(R.id.progressBar6);
        filter = findViewById(R.id.button13);
        clear = findViewById(R.id.button14);


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        final Call<sectorBean> call = cr.getPlace(SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                location.removeAllViews();

                String[] ski1 = skil1.split(",");

                if (response.body().getStatus().equals("1")) {

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        Chip chip = (Chip) inflater.inflate(R.layout.chip , null);
                        chip.setText(response.body().getData().get(i).getTitle());

                        for (String s : ski1) {
                            if (response.body().getData().get(i).getTitle().equals(s)) {
                                chip.setChecked(true);
                            }
                        }

                        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if (b)
                                {
                                    lo.add(compoundButton.getText().toString());
                                }
                                else
                                {
                                    lo.remove(compoundButton.getText().toString());
                                }

                            }
                        });

                        location.addView(chip);


                    }



                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        final Call<sectorBean> call2 = cr.getContractorSector(SharePreferenceUtils.getInstance().getString("lang") , SharePreferenceUtils.getInstance().getString("user_id"));

        call2.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                sector.removeAllViews();

                String[] ski1 = skil1.split(",");

                if (response.body().getStatus().equals("1")) {

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        Chip chip = (Chip) inflater.inflate(R.layout.chip , null);
                        chip.setText(response.body().getData().get(i).getTitle());

                        for (String s : ski1) {
                            if (response.body().getData().get(i).getTitle().equals(s)) {
                                chip.setChecked(true);
                            }
                        }

                        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if (b)
                                {
                                    se.add(compoundButton.getText().toString());
                                }
                                else
                                {
                                    se.remove(compoundButton.getText().toString());
                                }

                            }
                        });

                        sector.addView(chip);


                    }



                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location.clearCheck();

                sector.clearCheck();


                lo.clear();

                se.clear();


            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String skil = TextUtils.join(",", lo);
                String expe = TextUtils.join(",", se);


                Intent intent = new Intent();
                intent.putExtra("skill", skil);
                intent.putExtra("experience", expe);
                setResult(RESULT_OK, intent);
                finish();

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
