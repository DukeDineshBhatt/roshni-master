package com.workersjoint.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.workersjoint.app.verifyPOJO.verifyBean;
import com.workersjoint.app.workerJobListPOJO.Datum;
import com.workersjoint.app.workerJobListPOJO.workerJobDetailBean;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WorkerJobDetails extends AppCompatActivity {

    ImageButton back;
    TextView title,positions , sector , skill_level ,  skills, nature,man_days , piece_rate , place,  location, experience, role, gender, education, hours, salary, stype , commp;

    Button active , edit;

    ProgressBar progress;
    String jid , status;

    CheckBox display_name , display_phone , display_person , display_email;

    LinearLayout man;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_job_details);

        jid = getIntent().getStringExtra("jid");
        status = getIntent().getStringExtra("status");

        back = findViewById(R.id.imageButton3);
        edit = findViewById(R.id.button9);

        display_name = findViewById(R.id.display_name);
        man = findViewById(R.id.man);
        display_phone = findViewById(R.id.display_phone);
        display_person = findViewById(R.id.display_person);
        display_email = findViewById(R.id.display_email);
        title = findViewById(R.id.textView30);
        positions = findViewById(R.id.positions);
        sector = findViewById(R.id.sector);
        skill_level = findViewById(R.id.skill_level);
        skills = findViewById(R.id.skills);
        nature = findViewById(R.id.nature);
        man_days = findViewById(R.id.man_days);
        piece_rate = findViewById(R.id.piece_rate);
        place = findViewById(R.id.place);
        location = findViewById(R.id.location);
        experience = findViewById(R.id.experience);
        role = findViewById(R.id.role);
        gender = findViewById(R.id.gender);
        education = findViewById(R.id.education);
        hours = findViewById(R.id.hours);
        salary = findViewById(R.id.salary);
        stype = findViewById(R.id.stype);
        active = findViewById(R.id.button8);
        progress = findViewById(R.id.progressBar4);

        commp = findViewById(R.id.company);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = active.getText().toString();

                if (t.equals("ACTIVE"))
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<verifyBean> call = cr.worker_ac_inac(jid , "Active");

                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                            Toast.makeText(WorkerJobDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

                            progress.setVisibility(View.GONE);

                            finish();
                        }

                        @Override
                        public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<verifyBean> call = cr.worker_ac_inac(jid , "Inactive");

                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                            Toast.makeText(WorkerJobDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

                            progress.setVisibility(View.GONE);

                            finish();

                        }

                        @Override
                        public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorkerJobDetails.this , UpdateWorkerJob.class);
                intent.putExtra("jid" , jid);
                startActivity(intent);

            }
        });


        commp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomBrand bottom = bottomBrand.newInstance();
                Bundle b = new Bundle();
                b.putString("jid" , jid);
                bottom.setArguments(b);
                bottom.show(getSupportFragmentManager() , "bottomBrand");


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Log.d("userid" , SharePreferenceUtils.getInstance().getString("user_id"));
        Log.d("jid" , jid);

        Call<workerJobDetailBean> call = cr.getJobDetailForWorker(SharePreferenceUtils.getInstance().getString("user_id"), jid , SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<workerJobDetailBean>() {
            @Override
            public void onResponse(@NotNull Call<workerJobDetailBean> call, @NotNull Response<workerJobDetailBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    Datum item = response.body().getData();

                    title.setText("Job ID: WJ-" + item.getId() + " " + item.getTitle());
                    positions.setText(item.getPosition());
                    sector.setText(item.getSector1());
                    skill_level.setText(item.getSkillLevel1());
                    skills.setText(item.getSkills1());
                    nature.setText(item.getNature1());
                    man_days.setText(item.getManDays());
                    piece_rate.setText(item.getPieceRate());
                    place.setText(item.getPlace1());
                    if (item.getLocation1() != null)
                    {
                        location.setText(item.getLocation1());
                    }
                    else
                    {
                        location.setText(item.getLocation());
                    }

                    if (item.getManDays().length() > 0)
                    {
                        man.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        man.setVisibility(View.GONE);
                    }

                    experience.setText(item.getExperience1());
                    role.setText(item.getRole());
                    gender.setText(item.getGender1());
                    education.setText(item.getEducation1());
                    hours.setText(item.getHours());
                    salary.setText(item.getSalary());
                    stype.setText(item.getStype1());

                    display_name.setChecked(Boolean.parseBoolean(item.getDisplayName()));
                    display_phone.setChecked(Boolean.parseBoolean(item.getDisplayPhone()));
                    display_person.setChecked(Boolean.parseBoolean(item.getDisplayPerson()));
                    display_email.setChecked(Boolean.parseBoolean(item.getDisplayEmail()));

                    if (status.equals("Active"))
                    {
                        active.setText("INACTIVE");
                        //apply.setEnabled(false);
                    }
                    else
                    {
                        active.setText("ACTIVE");
                        //apply.setEnabled(true);
                    }

                }


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<workerJobDetailBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
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
