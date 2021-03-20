package com.workersjoint.app;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.workersjoint.app.SkillsPOJO.skillsBean;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verifyPOJO.verifyBean;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;

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
    EditText title, positions, man_days, piece_rate, role, hours, salary, job_location;
    Spinner sector, skill_level, skills, nature, place, location, experience, gender, education, stype;

    TextView man_days_title, piece_rate_title;

    Button submit;
    ProgressBar progress;

    String skil = "", expe = "", loca = "", gend = "", educ = "", styp = "", sect = "", leve = "", natu = "", plac = "";

    List<String> ski, exp, exp1, loc, gen, gen1, edu, edu1, rol, sty, sty1, pla, pla1;
    List<String> ski1, loc1, rol1;
    List<String> sec, sec1, lev, lev1, nat, nat1;

    CheckBox display_name, phone, contact_person, email, all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);


        ski = new ArrayList<>();
        exp = new ArrayList<>();
        exp1 = new ArrayList<>();
        gen = new ArrayList<>();
        gen1 = new ArrayList<>();
        loc = new ArrayList<>();
        edu = new ArrayList<>();
        edu1 = new ArrayList<>();
        loc1 = new ArrayList<>();
        rol = new ArrayList<>();
        sty = new ArrayList<>();
        sty1 = new ArrayList<>();
        ski1 = new ArrayList<>();
        rol1 = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        lev = new ArrayList<>();
        lev1 = new ArrayList<>();
        nat = new ArrayList<>();
        nat1 = new ArrayList<>();
        pla = new ArrayList<>();
        pla1 = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        display_name = findViewById(R.id.display_name);
        phone = findViewById(R.id.phone);
        contact_person = findViewById(R.id.contact_person);
        email = findViewById(R.id.email);
        all = findViewById(R.id.all);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
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

        /*gen.add("Male");
        gen.add("Female");
        gen.add("No Preference");*/

        /*lev.add("Unskilled");
        lev.add("Semi-skilled");
        lev.add("Skilled");
        lev.add("Highly Skilled");*/

        /*nat.add("Permanent");
        nat.add("Temporary");
        nat.add("Contractual");
        nat.add("Home-Based");*/

        /*pla.add("Factory(In-house)");
        pla.add("Outside factory");*/

       /* exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");*/

        /*edu.add("Not required");
        edu.add("Primary");
        edu.add("Secondary");
        edu.add("Higher secondary");
        edu.add("Graduation");
        edu.add("Post-graduation");*/

        /*sty.add("Monthly");
        sty.add("Fortnightly");
        sty.add("Daily");
        sty.add("Piece-rate");
        sty.add("Weekly");*/


        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                display_name.setChecked(isChecked);
                phone.setChecked(isChecked);
                contact_person.setChecked(isChecked);
                email.setChecked(isChecked);

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


        final Call<sectorBean> call = cr.getBrandSector(SharePreferenceUtils.getInstance().getString("lang") , SharePreferenceUtils.getInstance().getString("user_id"));

        call.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        sec.add(response.body().getData().get(i).getTitle());
                        sec1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, sec);

                    sector.setAdapter(adapter);

                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    sect = sec1.get(i);

                    progress.setVisibility(View.VISIBLE);

                    Call<skillsBean> call2 = cr.getSkills2(sect, SharePreferenceUtils.getInstance().getString("lang"));
                    call2.enqueue(new Callback<skillsBean>() {
                        @Override
                        public void onResponse(@NotNull Call<skillsBean> call, @NotNull Response<skillsBean> response) {


                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                ski.clear();
                                ski1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    ski.add(response.body().getData().get(i).getTitle());
                                    ski1.add(response.body().getData().get(i).getId());

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(PostJob.this,
                                        R.layout.spinner_model, ski);

                                skills.setAdapter(adapter);

                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<skillsBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        skills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    skil = ski1.get(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call31 = cr.getGenderPreference(SharePreferenceUtils.getInstance().getString("lang"));

        call31.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        gen.add(response.body().getData().get(i).getTitle());
                        gen1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(PostJob.this),
                            R.layout.spinner_model, gen);

                    gender.setAdapter(adapter);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                gend = gen1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call32 = cr.getSkillLevelJob(SharePreferenceUtils.getInstance().getString("lang"));

        call32.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        lev.add(response.body().getData().get(i).getTitle());
                        lev1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter5 = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, lev);

                    skill_level.setAdapter(adapter5);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        skill_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    leve = lev1.get(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call33 = cr.getNature(SharePreferenceUtils.getInstance().getString("lang"));

        call33.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        nat.add(response.body().getData().get(i).getTitle());
                        nat1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, nat);

                    nature.setAdapter(adapter6);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        nature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    natu = nat1.get(i);

                    if (natu.equals("5")) {
                        man_days.setVisibility(View.VISIBLE);
                        man_days_title.setVisibility(View.VISIBLE);
                        piece_rate.setVisibility(View.VISIBLE);
                        piece_rate_title.setVisibility(View.VISIBLE);
                    } else {
                        man_days.setVisibility(View.GONE);
                        man_days_title.setVisibility(View.GONE);
                        piece_rate.setVisibility(View.GONE);
                        piece_rate_title.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call34 = cr.getPlace(SharePreferenceUtils.getInstance().getString("lang"));

        call34.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        pla.add(response.body().getData().get(i).getTitle());
                        pla1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, pla);

                    place.setAdapter(adapter7);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    plac = pla1.get(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        stype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    styp = sty1.get(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call35 = cr.getExperience(SharePreferenceUtils.getInstance().getString("lang"));

        call35.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        exp.add(response.body().getData().get(i).getTitle());
                        exp1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, exp);

                    experience.setAdapter(adapter2);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    expe = exp1.get(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call36 = cr.getEducationJob(SharePreferenceUtils.getInstance().getString("lang"));

        call36.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        edu.add(response.body().getData().get(i).getTitle());
                        edu1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, edu);

                    education.setAdapter(adapter3);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                educ = edu1.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call37 = cr.getSalaryType(SharePreferenceUtils.getInstance().getString("lang"));

        call37.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        sty.add(response.body().getData().get(i).getTitle());
                        sty1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, sty);

                    stype.setAdapter(adapter4);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loca = loc1.get(i);

                if (loca.equals("6")) {
                    job_location.setVisibility(View.VISIBLE);
                } else {
                    job_location.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Call<sectorBean> call3 = cr.getLocations(SharePreferenceUtils.getInstance().getString("lang"));

        call3.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        loc.add(response.body().getData().get(i).getTitle());
                        loc1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(PostJob.this,
                            R.layout.spinner_model, loc);

                    location.setAdapter(adapter);


                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
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

                Log.d("skill", skil);


                if (t.length() > 0) {

                    if (p.length() > 0) {

                        if (sect.length() > 0) {
                            if (skil.length() > 0) {

                                if (natu.length() > 0) {
                                    if (plac.length() > 0) {
                                        if (expe.length() > 0) {
                                            if (r.length() > 0) {
                                                if (styp.length() > 0) {

                                                    if (loca.equals("6")) {
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
                                                            styp,
                                                            String.valueOf(display_name.isChecked()),
                                                            String.valueOf(phone.isChecked()),
                                                            String.valueOf(contact_person.isChecked()),
                                                            String.valueOf(email.isChecked())
                                                    );

                                                    call1.enqueue(new Callback<verifyBean>() {
                                                        @Override
                                                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
                                                                Toast.makeText(PostJob.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                finish();

                                                            } else {
                                                                Toast.makeText(PostJob.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }


                                                            progress.setVisibility(View.GONE);

                                                        }

                                                        @Override
                                                        public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                                            progress.setVisibility(View.GONE);
                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(PostJob.this, "Invalid salary type", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(PostJob.this, "Invalid job role", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(PostJob.this, "Invalid experience", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(PostJob.this, "Invalid place", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(PostJob.this, "Invalid job nature", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(PostJob.this, "Invalid skill set", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PostJob.this, "Invalid sector", Toast.LENGTH_SHORT).show();
                        }


                    } else {
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
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


}
