package com.app.roshni;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.verifyPOJO.verifyBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WorkerApplicantDetails extends AppCompatActivity {

    ImageButton back;
    TextView jobtitle , jobcategory , salary , posted;
    CircleImageView image;
    TextView name , phone , skill , experience , employment , dob , gender , current , permanent , category , religion , educational , marital , sector , employer , home;

    ProgressBar progress;

    Button accept , reject;

    String id , status , tit , cat , sal , pos , sts , jid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_applicant_details);

        id = getIntent().getStringExtra("jid");
        jid = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        tit = getIntent().getStringExtra("title");
        cat = getIntent().getStringExtra("category");
        sal = getIntent().getStringExtra("salary");
        pos = getIntent().getStringExtra("posted");
        sts = getIntent().getStringExtra("sts");

        back = findViewById(R.id.imageButton3);
        jobtitle = findViewById(R.id.textView30);
        jobcategory = findViewById(R.id.textView31);
        salary = findViewById(R.id.textView32);
        posted = findViewById(R.id.imageView6);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        skill = findViewById(R.id.skill);
        experience = findViewById(R.id.experience);
        employment = findViewById(R.id.employment);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        current = findViewById(R.id.current);
        permanent = findViewById(R.id.permanent);
        category = findViewById(R.id.category);
        religion = findViewById(R.id.religion);
        educational = findViewById(R.id.education);
        marital = findViewById(R.id.marital);
        sector = findViewById(R.id.sector);
        employer = findViewById(R.id.employer);
        home = findViewById(R.id.home_based);
        progress = findViewById(R.id.progressBar5);
        reject = findViewById(R.id.button12);
        accept = findViewById(R.id.button10);

        progress.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        jobtitle.setText(tit);
        jobcategory.setText("Job category: " + cat);
        salary.setText("Salary: " + sal);
        posted.setText("Posted on: " + pos);


        if (status.equals("Pending"))
        {

            if (sts.equals("Active"))
            {
                accept.setVisibility(View.VISIBLE);
                reject.setVisibility(View.VISIBLE);
            }
            else
            {
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }


        }
        else
        {
            accept.setVisibility(View.GONE);
            reject.setVisibility(View.GONE);
        }

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<WorkerByIdListBean> call = cr.getWorkerById1(id , SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(@NotNull Call<WorkerByIdListBean> call, @NotNull Response<WorkerByIdListBean> response) {

                WorkerByIdData item = Objects.requireNonNull(response.body()).getData().get(0);


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto() , image , options);

                name.setText(item.getName());
                phone.setText("+" + item.getPhone());
                skill.setText(item.getSkills());
                experience.setText(item.getExperience1());
                employment.setText(item.getEmployment1());
                dob.setText(item.getDob());
                gender.setText(item.getGender1());
                current.setText(item.getCstreet() + ", " + item.getCarea() + ", " + item.getCdistrict() + ", " + item.getCstate() + "-" + item.getCpin());
                permanent.setText(item.getPstreet() + ", " + item.getParea() + ", " + item.getPdistrict() + ", " + item.getPstate() + "-" + item.getPpin());
                category.setText(item.getCategory1());
                religion.setText(item.getReligion1());
                educational.setText(item.getEducational());
                marital.setText(item.getMarital1());
                sector.setText(item.getSector());
                employer.setText(item.getEmployer());
                home.setText(item.getHome1());



                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<WorkerByIdListBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(WorkerApplicantDetails.this)
                        .setTitle(getString(R.string.confirm))
                        .setMessage("Accept Job Application")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int which) {
                                progress.setVisibility(View.VISIBLE);

                                Bean b = (Bean) getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                Call<verifyBean> call = cr.worker_acept_reject(jid , id , "Approved");

                                call.enqueue(new Callback<verifyBean>() {
                                    @Override
                                    public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                                        Toast.makeText(WorkerApplicantDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

                                        progress.setVisibility(View.GONE);

                                        finish();
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .show();




            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(WorkerApplicantDetails.this)
                        .setTitle(getString(R.string.confirm))
                        .setMessage("Reject Job Application")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int which) {
                                progress.setVisibility(View.VISIBLE);

                                Bean b = (Bean) getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                Call<verifyBean> call = cr.worker_acept_reject(jid , id , "Rejected");

                                call.enqueue(new Callback<verifyBean>() {
                                    @Override
                                    public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                                        Toast.makeText(WorkerApplicantDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

                                        progress.setVisibility(View.GONE);

                                        finish();
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .show();




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
