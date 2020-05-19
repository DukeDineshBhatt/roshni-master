package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verifyPOJO.verifyBean;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

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


public class PostJob extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Toolbar toolbar;
    EditText title, positions, man_days, piece_rate, role, hours, salary , job_location;
    Spinner sector, skill_level, skills, nature, place, location, experience, gender, education, stype;

    TextView man_days_title , piece_rate_title;

    Button submit;
    ProgressBar progress;

    String skil, expe, loca, gend, educ, styp, sect, leve, natu , plac;

    List<String> ski, exp, loc, gen, edu, rol, sty , pla;
    List<String> ski1, loc1, rol1;
    List<String> sec, sec1, lev, nat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);


        ski = new ArrayList<>();
        exp = new ArrayList<>();
        gen = new ArrayList<>();
        loc = new ArrayList<>();
        edu = new ArrayList<>();
        loc1 = new ArrayList<>();
        rol = new ArrayList<>();
        sty = new ArrayList<>();
        ski1 = new ArrayList<>();
        rol1 = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        lev = new ArrayList<>();
        nat = new ArrayList<>();
        pla = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        man_days_title = findViewById(R.id.man_days_title);
        piece_rate_title = findViewById(R.id.piece_rate_title);
        title = findViewById(R.id.title);
        positions = findViewById(R.id.positions);
        man_days = findViewById(R.id.man_days);
        piece_rate = findViewById(R.id.piece_rate);
        role = findViewById(R.id.role);
        hours = findViewById(R.id.hours);
        salary = findViewById(R.id.salary);
        job_location = findViewById(R.id.job_location);

        sector = findViewById(R.id.sector);
        skill_level = findViewById(R.id.skill_level);
        skills = findViewById(R.id.skills);
        nature = findViewById(R.id.nature);
        place = findViewById(R.id.place);
        location = findViewById(R.id.location);
        experience = findViewById(R.id.experience);
        gender = findViewById(R.id.gender);
        education = findViewById(R.id.education);
        stype = findViewById(R.id.stype);
        submit = findViewById(R.id.submit);
        progress = findViewById(R.id.progress);


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
        toolbar.setTitle(getString(R.string.post_job));

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        PostJob.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");

            }
        });

        gen.add("Male");
        gen.add("Female");
        gen.add("No Preference");

        lev.add("Unskilled");
        lev.add("Semi-skilled");
        lev.add("Skilled");
        lev.add("Highly Skilled");

        nat.add("Permanent");
        nat.add("Temporary");
        nat.add("Contractual");
        nat.add("Home-Based");

        pla.add("Factory(In-house)");
        pla.add("Outside factory");

        exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");

        edu.add("Not required");
        edu.add("Primary");
        edu.add("Secondary");
        edu.add("Higher secondary");
        edu.add("Graduation");
        edu.add("Post-graduation");

        sty.add("Monthly");
        sty.add("Fortnightly");
        sty.add("Daily");
        sty.add("Piece-rate");
        sty.add("Weekly");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.spinner_model, gen);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.spinner_model, exp);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                R.layout.spinner_model, edu);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                R.layout.spinner_model, sty);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,
                R.layout.spinner_model, lev);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this,
                R.layout.spinner_model, nat);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(this,
                R.layout.spinner_model, pla);

        experience.setAdapter(adapter2);
        education.setAdapter(adapter3);
        gender.setAdapter(adapter);
        stype.setAdapter(adapter4);
        skill_level.setAdapter(adapter5);
        nature.setAdapter(adapter6);
        place.setAdapter(adapter7);


        stype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                styp = sty.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                gend = gen.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        skills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                skil = ski1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    expe = exp.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        skill_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                leve = lev.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                plac =  pla.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                natu = nat.get(i);

                if (natu.equals("Home-Based"))
                {
                    man_days.setVisibility(View.VISIBLE);
                    man_days_title.setVisibility(View.VISIBLE);
                    piece_rate.setVisibility(View.VISIBLE);
                    piece_rate_title.setVisibility(View.VISIBLE);
                }
                else
                {
                    man_days.setVisibility(View.GONE);
                    man_days_title.setVisibility(View.GONE);
                    piece_rate.setVisibility(View.GONE);
                    piece_rate_title.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    educ = edu.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loca = loc.get(i);

                if (loca.equals("Others"))
                {
                    job_location.setVisibility(View.VISIBLE);
                }
                else
                {
                    job_location.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        progress.setVisibility(View.VISIBLE);


        final Call<sectorBean> call = cr.getSectors();

        call.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        sec.add(response.body().getData().get(i).getTitle());
                        sec1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostJob.this,
                            R.layout.spinner_model, sec);

                    sector.setAdapter(adapter);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sect = sec1.get(i);

                progress.setVisibility(View.VISIBLE);

                Call<skillsBean> call2 = cr.getSkills1(sect, SharePreferenceUtils.getInstance().getString("lang"));
                call2.enqueue(new Callback<skillsBean>() {
                    @Override
                    public void onResponse(Call<skillsBean> call, Response<skillsBean> response) {


                        if (response.body().getStatus().equals("1")) {

                            ski.clear();
                            ski1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ski.add(response.body().getData().get(i).getTitle());
                                ski1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostJob.this,
                                    R.layout.spinner_model, ski);

                            skills.setAdapter(adapter);

                        }

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<skillsBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Call<sectorBean> call3 = cr.getLocations();

        call3.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {


                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        loc.add(response.body().getData().get(i).getTitle());
                        loc1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostJob.this,
                            R.layout.spinner_model, loc);

                    location.setAdapter(adapter);


                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = title.getText().toString();
                String p = positions.getText().toString();
                String m = man_days.getText().toString();
                String pi = piece_rate.getText().toString();
                String r = role.getText().toString();
                String h = hours.getText().toString();
                String s = salary.getText().toString();

                Log.d("skill" , skil);


                if (t.length() > 0) {

                    if (p.length() > 0)
                    {

                        if (styp.length() > 0) {

                            if (loca.equals("Others"))
                            {
                                loca = job_location.getText().toString();
                            }

                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<verifyBean> call1 = cr.postjob(
                                    SharePreferenceUtils.getInstance().getString("user_id"),
                                    t,
                                    p,
                                    sect,
                                    leve,
                                    skil,
                                    natu,
                                    m,
                                    pi,
                                    plac,
                                    loca,
                                    expe,
                                    r,
                                    gend,
                                    educ,
                                    h,
                                    s,
                                    styp
                            );

                            call1.enqueue(new Callback<verifyBean>() {
                                @Override
                                public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

                                    if (response.body().getStatus().equals("1")) {
                                        Toast.makeText(PostJob.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        finish();

                                    }


                                    progress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<verifyBean> call, Throwable t) {
                                    progress.setVisibility(View.GONE);
                                }
                            });


                        } else {
                            Toast.makeText(PostJob.this, "Invalid salary type", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(PostJob.this, "Invalid no. of positions", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(PostJob.this, "Invalid job title", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {

        String time1 = ((hourOfDay > 12) ? hourOfDay % 12 : hourOfDay) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + ((hourOfDay >= 12) ? "PM" : "AM");
        String time2 = ((hourOfDayEnd > 12) ? hourOfDayEnd % 12 : hourOfDayEnd) + ":" + (minuteEnd < 10 ? ("0" + minuteEnd) : minuteEnd) + " " + ((hourOfDayEnd >= 12) ? "PM" : "AM");


        hours.setText(time1 + " to " + time2);


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
