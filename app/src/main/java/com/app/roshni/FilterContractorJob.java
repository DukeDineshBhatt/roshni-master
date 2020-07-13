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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.roshni.sectorPOJO.sectorBean;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FilterContractorJob extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageView cross;
    ChipGroup location, sector;
    TextView date;

    List<String> lo, se;

    ProgressBar progress;
    LayoutInflater inflater;
    List<String> exp, ski;

    String loca1, sect1 , date1 , sort;

    Button filter, clear;

    RadioButton newest , oldest;
    RadioGroup sort1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_contractor_job);

        loca1 = getIntent().getStringExtra("location");
        date1 = getIntent().getStringExtra("date");
        sect1 = getIntent().getStringExtra("sector");
        sort = getIntent().getStringExtra("sort");


        exp = new ArrayList<>();
        ski = new ArrayList<>();


        lo = new ArrayList<>();

        se = new ArrayList<>();


        location = findViewById(R.id.location);
        sort1 = findViewById(R.id.sort);
        oldest = findViewById(R.id.oldest);
        newest = findViewById(R.id.newest);
        cross = findViewById(R.id.imageButton4);
        date = findViewById(R.id.date);

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

        date.setText(date1);

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
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                location.removeAllViews();

                String[] ski1 = loca1.split(",");

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        Chip chip = (Chip) inflater.inflate(R.layout.chip, null);
                        chip.setText(response.body().getData().get(i).getTitle());

                        for (String s : ski1) {
                            if (response.body().getData().get(i).getTitle().equals(s)) {
                                chip.setChecked(true);
                            }
                        }

                        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if (b) {
                                    lo.add(compoundButton.getText().toString());
                                } else {
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
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        sort1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.newest)
                {
                    sort = "DESC";
                }
                else
                {
                    sort = "ASC";
                }

            }
        });

        if (sort.equals("DESC"))
        {
            newest.setChecked(true);
        }
        else
        {
            oldest.setChecked(true);
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FilterContractorJob.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        final Call<sectorBean> call2 = cr.getContractorSector(SharePreferenceUtils.getInstance().getString("lang") , SharePreferenceUtils.getInstance().getString("user_id"));

        call2.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                sector.removeAllViews();

                String[] ski1 = sect1.split(",");

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

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
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location.clearCheck();

                sector.clearCheck();

                newest.setChecked(true);

                date.setText("");
                date1 = "";

                lo.clear();

                se.clear();


            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loca1 = TextUtils.join(",", lo);
                String sect1 = TextUtils.join(",", se);



                Intent intent = new Intent();
                intent.putExtra("location", loca1);
                intent.putExtra("sector", sect1);
                intent.putExtra("sort", sort);
                intent.putExtra("date", date.getText().toString());
                setResult(RESULT_OK, intent);
                finish();

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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);
        cal1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal1.set(Calendar.MONTH, monthOfYear);
        String format1= new SimpleDateFormat("yyyy-MM-dd").format(cal1.getTime());

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, yearEnd);
        cal2.set(Calendar.DAY_OF_MONTH, dayOfMonthEnd);
        cal2.set(Calendar.MONTH, monthOfYearEnd);
        String format2= new SimpleDateFormat("yyyy-MM-dd").format(cal2.getTime());

        date.setText(format1 + " to " + format2);

    }
}
