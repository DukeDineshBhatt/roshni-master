package com.app.roshni;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.verifyPOJO.verifyBean;
import com.app.roshni.workerJobListPOJO.Datum;
import com.app.roshni.workerJobListPOJO.workerJobDetailBean;
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

public class JobDetails extends AppCompatActivity {

    ImageButton back;
    TextView title, company, address, skills, preferred, location, experience, role, gender, education, hours, salary, stype , commp , positions , sector , nature , man_days , rate , place;

    Button apply;

    ProgressBar progress;
    CircleImageView image;
    String jid;

    View header;

    boolean dname , dphone , dperson , demail;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        jid = getIntent().getStringExtra("jid");

        back = findViewById(R.id.imageButton3);
        image = findViewById(R.id.imageView6);
        title = findViewById(R.id.textView30);
        company = findViewById(R.id.textView31);
        address = findViewById(R.id.textView32);
        skills = findViewById(R.id.skills);
        preferred = findViewById(R.id.preferred);
        location = findViewById(R.id.location);
        experience = findViewById(R.id.experience);
        role = findViewById(R.id.role);
        gender = findViewById(R.id.gender);
        education = findViewById(R.id.education);
        hours = findViewById(R.id.hours);
        salary = findViewById(R.id.salary);
        stype = findViewById(R.id.stype);
        apply = findViewById(R.id.button8);
        progress = findViewById(R.id.progressBar4);
        header = findViewById(R.id.constraintLayout);
        commp = findViewById(R.id.company);
        positions = findViewById(R.id.positions);
        sector = findViewById(R.id.sector);
        nature = findViewById(R.id.nature);
        man_days = findViewById(R.id.man_days);
        rate = findViewById(R.id.rate);
        place = findViewById(R.id.place);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = apply.getText().toString();

                if (t.equals("APPLY NOW"))
                {


                    new AlertDialog.Builder(JobDetails.this)
                            .setTitle(getString(R.string.confirm))
                            .setMessage("Apply For Job")

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


                                    Call<verifyBean> call = cr.apply_job(jid , SharePreferenceUtils.getInstance().getString("user_id"));

                                    call.enqueue(new Callback<verifyBean>() {
                                        @Override
                                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                            if (Objects.requireNonNull(response.body()).getStatus().equals("1"))
                                            {
                                                final Dialog dialog = new Dialog(JobDetails.this);
                                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialog.setCancelable(true);
                                                dialog.setContentView(R.layout.apply_dialog);
                                                dialog.show();

                                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                    @Override
                                                    public void onCancel(DialogInterface dialogInterface) {
                                                        dialog.dismiss();
                                                        finish();
                                                    }
                                                });
                                            }
                                            else
                                            {
                                                Toast.makeText(JobDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            }





                                            progress.setVisibility(View.GONE);
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
                else
                {


                    new AlertDialog.Builder(JobDetails.this)
                            .setTitle(getString(R.string.confirm))
                            .setMessage("Withdraw Job Application")

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


                                    Call<verifyBean> call = cr.withdrawJob(jid , SharePreferenceUtils.getInstance().getString("user_id"));

                                    call.enqueue(new Callback<verifyBean>() {
                                        @Override
                                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                            if (Objects.requireNonNull(response.body()).getStatus().equals("1"))
                                            {
                                                Toast.makeText(JobDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(JobDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            }





                                            progress.setVisibility(View.GONE);
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

            }
        });


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomBrand bottom = bottomBrand.newInstance();
                Bundle b = new Bundle();
                b.putString("jid" , jid);
                bottom.setArguments(b);
                bottom.show(getSupportFragmentManager() , "bottomBrand");


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

        Call<workerJobDetailBean> call = cr.getJobDetailForWorker(SharePreferenceUtils.getInstance().getString("user_id"), jid , SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<workerJobDetailBean>() {
            @Override
            public void onResponse(@NotNull Call<workerJobDetailBean> call, @NotNull Response<workerJobDetailBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    Datum item = response.body().getData();

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getLogo() , image , options);

                    title.setText(item.getTitle());
                    company.setText(item.getBrandName());
                    address.setText(item.getBrandDistrict() + ", " + item.getBrandState());
                    skills.setText(item.getSkills1());
                    preferred.setText(item.getSkillLevel1());
                    location.setText(item.getLocation1());
                    experience.setText(item.getExperience1());
                    role.setText(item.getRole());
                    gender.setText(item.getGender1());
                    education.setText(item.getEducation1());
                    hours.setText(item.getHours());
                    salary.setText(item.getSalary());
                    stype.setText(item.getStype1());
                    positions.setText(item.getPosition());
                    sector.setText(item.getSector1());
                    nature.setText(item.getNature1());
                    man_days.setText(item.getManDays());
                    rate.setText(item.getPieceRate());
                    place.setText(item.getPlace1());

                    if (item.getStatus().equals("1"))
                    {
                        apply.setText("WITHDRAW");
                        //apply.setEnabled(false);
                    }
                    else
                    {
                        apply.setText("APPLY NOW");
                        //apply.setEnabled(true);
                    }

/*
                    dname = Boolean.parseBoolean(item.getDisplayName());
                    dphone = Boolean.parseBoolean(item.getDisplayPhone());
                    dperson = Boolean.parseBoolean(item.getDisplayPerson());
                    demail = Boolean.parseBoolean(item.getDisplayEmail());
*/

                    if (Boolean.parseBoolean(item.getDisplayName()))
                    {
                        company.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        company.setVisibility(View.GONE);
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
