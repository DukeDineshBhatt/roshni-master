package com.workersjoint.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.workersjoint.app.contractorPOJO.Data;
import com.workersjoint.app.contractorPOJO.contractorBean;
import com.workersjoint.app.verifyPOJO.verifyBean;
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

public class ContractorApplicantDetails extends AppCompatActivity {

    ImageButton back;
    TextView jobtitle , jobcategory , salary , posted , sector;
    CircleImageView image;
    TextView name , phone , total , experience , availability , dob , gender , current , permanent , home_based , home_location , male , female , type , employer , about , samples;

    ProgressBar progress;

    Button accept , reject;

    String id , status , tit , cat , sal , pos , sts , jid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_applicant_details);

        id = getIntent().getStringExtra("jid");
        jid = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        tit = getIntent().getStringExtra("title");
        cat = getIntent().getStringExtra("category");
        sal = getIntent().getStringExtra("salary");
        pos = getIntent().getStringExtra("posted");
        sts = getIntent().getStringExtra("sts");

        back = findViewById(R.id.imageButton3);
        sector = findViewById(R.id.sector);
        jobtitle = findViewById(R.id.textView30);
        jobcategory = findViewById(R.id.textView31);
        salary = findViewById(R.id.textView32);
        posted = findViewById(R.id.imageView6);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        total = findViewById(R.id.total);
        experience = findViewById(R.id.experience);
        availability = findViewById(R.id.availability);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        current = findViewById(R.id.current);
        permanent = findViewById(R.id.permanent);
        home_based = findViewById(R.id.home_based);
        home_location = findViewById(R.id.home_location);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        type = findViewById(R.id.type);
        employer = findViewById(R.id.employer);
        about = findViewById(R.id.about);
        samples = findViewById(R.id.samples);
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


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ContractorApplicantDetails.this);

                builder.setMessage("Please contact the administrator")
                        .setTitle("View Contractor Phone");

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        Intent intent = new Intent(ContractorApplicantDetails.this , Support.class);
                        intent.putExtra("title" , getString(R.string.support_help));
                        intent.putExtra("url" , "https://mrtecks.com/roshni/support.php");
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = builder.create();

                dialog.show();


            }
        });

        jobtitle.setText(tit);
        jobcategory.setText("Job category: " + cat);
        salary.setText("Piece rate: " + sal);
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


        Call<contractorBean> call = cr.getContractorById(id, SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorBean> call, @NotNull Response<contractorBean> response) {

                Data item = Objects.requireNonNull(response.body()).getData();


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto() , image , options);

                name.setText(item.getName());
                //phone.setText("+" + item.getPhone());
                total.setText((Integer.parseInt(item.getWorkersMale()) + Integer.parseInt(item.getWorkersFemale())) + " workers");
                experience.setText(item.getExperience());
                availability.setText(item.getAvailability1());
                dob.setText(item.getDob());
                gender.setText(item.getGender1());
                current.setText(item.getCstreet() + ", " + item.getCarea() + ", " + item.getCdistrict() + ", " + item.getCstate() + "-" + item.getCpin());
                permanent.setText(item.getPstreet() + ", " + item.getParea() + ", " + item.getPdistrict() + ", " + item.getPstate() + "-" + item.getPpin());
                home_based.setText(item.getHomeUnits());
                home_location.setText(item.getHomeLocation());
                male.setText(item.getWorkersMale());
                female.setText(item.getWorkersFemale());
                type.setText(item.getWorkType());
                employer.setText(item.getEmployer());
                about.setText(item.getAbout());
                sector.setText(item.getSector2());



                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<contractorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(ContractorApplicantDetails.this)
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


                                Call<verifyBean> call = cr.contractor_acept_reject(jid , id , "Approved");

                                call.enqueue(new Callback<verifyBean>() {
                                    @Override
                                    public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                                        Toast.makeText(ContractorApplicantDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

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


                new AlertDialog.Builder(ContractorApplicantDetails.this)
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


                                Call<verifyBean> call = cr.contractor_acept_reject(jid , id , "Rejected");

                                call.enqueue(new Callback<verifyBean>() {
                                    @Override
                                    public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                                        Toast.makeText(ContractorApplicantDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

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

        samples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSsmples bottom = bottomSsmples.newInstance();
                Bundle b = new Bundle();
                b.putString("jid" , id);
                bottom.setArguments(b);
                bottom.show(getSupportFragmentManager() , "bottomBrand");

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
