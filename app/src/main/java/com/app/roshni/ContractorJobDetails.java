package com.app.roshni;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.roshni.contractorJobDetailsPOJO.Data;
import com.app.roshni.contractorJobDetailsPOJO.contractorJobDetailsBean;
import com.app.roshni.verifyPOJO.verifyBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ContractorJobDetails extends AppCompatActivity {

    ImageButton back;
    ImageView sample1 , sample2 , sample3 , sample4 , sample5;
    TextView title, type, experience, days, rate , sector , location;

    Button active , edit;

    ProgressBar progress;
    String jid , status;

    CheckBox display_name , display_phone , display_person , display_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_job_details);

        jid = getIntent().getStringExtra("jid");
        status = getIntent().getStringExtra("status");

        back = findViewById(R.id.imageButton3);
        edit = findViewById(R.id.button9);
        sector = findViewById(R.id.sector);
        display_name = findViewById(R.id.display_name);
        display_phone = findViewById(R.id.display_phone);
        display_person = findViewById(R.id.display_person);
        display_email = findViewById(R.id.display_email);
        title = findViewById(R.id.textView30);
        location = findViewById(R.id.location);
        type = findViewById(R.id.type);
        experience = findViewById(R.id.experience);
        days = findViewById(R.id.days);
        rate = findViewById(R.id.rate);
        active = findViewById(R.id.button8);
        progress = findViewById(R.id.progressBar4);
        sample1 = findViewById(R.id.image1);
        sample2 = findViewById(R.id.image2);
        sample3 = findViewById(R.id.image3);
        sample4 = findViewById(R.id.image4);
        sample5 = findViewById(R.id.image5);

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


                    Call<verifyBean> call = cr.contractor_ac_inac(jid , "Active");

                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                            Toast.makeText(ContractorJobDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

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


                    Call<verifyBean> call = cr.contractor_ac_inac(jid , "Inactive");

                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {


                            Toast.makeText(ContractorJobDetails.this, Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();

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

                Intent intent = new Intent(ContractorJobDetails.this , UpdateContractorJob.class);
                intent.putExtra("jid" , jid);
                startActivity(intent);

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

        Log.d("jid" , jid);

        Call<contractorJobDetailsBean> call = cr.getJobDetailsForContractor(SharePreferenceUtils.getInstance().getString("user_id"), jid);

        call.enqueue(new Callback<contractorJobDetailsBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorJobDetailsBean> call, @NotNull Response<contractorJobDetailsBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    final Data item = response.body().getData();


                    final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                    final ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getSample1() , sample1 , options);
                    loader.displayImage(item.getSample2() , sample2 , options);
                    loader.displayImage(item.getSample3() , sample3 , options);
                    loader.displayImage(item.getSample4() , sample4 , options);
                    loader.displayImage(item.getSample5() , sample5 , options);

                    if (item.getImage1().length() > 0)
                    {
                        sample1.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        sample1.setVisibility(View.GONE);
                    }


                    if (item.getImage2().length() > 0)
                    {
                        sample2.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        sample2.setVisibility(View.GONE);
                    }


                    if (item.getImage3().length() > 0)
                    {
                        sample3.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        sample3.setVisibility(View.GONE);
                    }


                    if (item.getImage4().length() > 0)
                    {
                        sample4.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        sample4.setVisibility(View.GONE);
                    }


                    if (item.getImage5().length() > 0)
                    {
                        sample5.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        sample5.setVisibility(View.GONE);
                    }


                    title.setText("Job ID: CJ-" + item.getTitle());
                    sector.setText(item.getSector1());
                    type.setText(item.getJobType1());
                    days.setText(item.getDays());
                    experience.setText(item.getExperience1());
                    rate.setText(item.getRate());
                    location.setText(item.getPlace1());

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

                    sample1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(ContractorJobDetails.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            //      WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.setContentView(R.layout.zoom_dialog);
                            dialog.setCancelable(true);
                            dialog.show();

                            ImageView img = dialog.findViewById(R.id.image);
                            loader.displayImage(item.getSample1() , img , options);

                        }
                    });


                    sample2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(ContractorJobDetails.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            //      WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.setContentView(R.layout.zoom_dialog);
                            dialog.setCancelable(true);
                            dialog.show();

                            ImageView img = dialog.findViewById(R.id.image);
                            loader.displayImage(item.getSample2() , img , options);

                        }
                    });


                    sample3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(ContractorJobDetails.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            //      WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.setContentView(R.layout.zoom_dialog);
                            dialog.setCancelable(true);
                            dialog.show();

                            ImageView img = dialog.findViewById(R.id.image);
                            loader.displayImage(item.getSample3() , img , options);

                        }
                    });


                    sample4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(ContractorJobDetails.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            //      WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.setContentView(R.layout.zoom_dialog);
                            dialog.setCancelable(true);
                            dialog.show();

                            ImageView img = dialog.findViewById(R.id.image);
                            loader.displayImage(item.getSample4() , img , options);

                        }
                    });


                    sample5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(ContractorJobDetails.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            //      WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.setContentView(R.layout.zoom_dialog);
                            dialog.setCancelable(true);
                            dialog.show();

                            ImageView img = dialog.findViewById(R.id.image);
                            loader.displayImage(item.getSample5() , img , options);

                        }
                    });



                }


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<contractorJobDetailsBean> call, @NotNull Throwable t) {
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
