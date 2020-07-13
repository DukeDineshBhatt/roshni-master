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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.contractorJobDetailsPOJO.Data;
import com.app.roshni.contractorJobDetailsPOJO.contractorJobDetailsBean;
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

public class JobDetails2 extends AppCompatActivity {

    ImageButton back;
    TextView title, company, address, type, experience, days, rate , commp , sector  ,location;

    Button apply;

    ProgressBar progress;
    CircleImageView image;
    String jid;
    ImageView sample1 , sample2 , sample3 , sample4 , sample5;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details2);

        jid = getIntent().getStringExtra("jid");

        back = findViewById(R.id.imageButton3);
        location = findViewById(R.id.location);
        image = findViewById(R.id.imageView6);
        title = findViewById(R.id.textView30);
        company = findViewById(R.id.textView31);
        address = findViewById(R.id.textView32);
        type = findViewById(R.id.type);
        experience = findViewById(R.id.experience);
        days = findViewById(R.id.days);
        rate = findViewById(R.id.rate);
        apply = findViewById(R.id.button8);
        progress = findViewById(R.id.progressBar4);
        header = findViewById(R.id.constraintLayout);
        commp = findViewById(R.id.company);
        sample1 = findViewById(R.id.image1);
        sample2 = findViewById(R.id.image2);
        sample3 = findViewById(R.id.image3);
        sample4 = findViewById(R.id.image4);
        sample5 = findViewById(R.id.image5);

        sector = findViewById(R.id.sector);

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


                    new AlertDialog.Builder(JobDetails2.this)
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


                                    Call<verifyBean> call = cr.apply_job2(jid , SharePreferenceUtils.getInstance().getString("user_id"));

                                    call.enqueue(new Callback<verifyBean>() {
                                        @Override
                                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                            if (Objects.requireNonNull(response.body()).getStatus().equals("1"))
                                            {
                                                final Dialog dialog = new Dialog(JobDetails2.this);
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
                                                Toast.makeText(JobDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(JobDetails2.this)
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


                                    Call<verifyBean> call = cr.withdrawJob2(jid , SharePreferenceUtils.getInstance().getString("user_id"));

                                    call.enqueue(new Callback<verifyBean>() {
                                        @Override
                                        public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                            if (Objects.requireNonNull(response.body()).getStatus().equals("1"))
                                            {
                                                Toast.makeText(JobDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(JobDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

                bottomBrand2 bottom = bottomBrand2.newInstance();
                Bundle b = new Bundle();
                b.putString("jid" , jid);
                bottom.setArguments(b);
                bottom.show(getSupportFragmentManager() , "bottomBrand");


            }
        });

        commp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomBrand2 bottom = bottomBrand2.newInstance();
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

        Call<contractorJobDetailsBean> call = cr.getJobDetailsForContractor(SharePreferenceUtils.getInstance().getString("user_id"), jid);

        call.enqueue(new Callback<contractorJobDetailsBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorJobDetailsBean> call, @NotNull Response<contractorJobDetailsBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    final Data item = response.body().getData();

                    final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    final ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getLogo() , image , options);


                    loader.displayImage(item.getSample1() , sample1 , options);
                    loader.displayImage(item.getSample2() , sample2 , options);
                    loader.displayImage(item.getSample3() , sample3 , options);
                    loader.displayImage(item.getSample4() , sample4 , options);
                    loader.displayImage(item.getSample5() , sample5 , options);

                    title.setText("Job ID: CJ-" + item.getTitle());
                    company.setText(item.getBrandName());
                    address.setText(item.getBrandDistrict() + ", " + item.getBrandState());

                    type.setText(item.getJobType1());
                    days.setText(item.getDays());
                    experience.setText(item.getExperience1());
                    rate.setText(item.getRate());
                    sector.setText(item.getSector1());
                    location.setText(item.getPlace1());

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

                    if (Boolean.parseBoolean(item.getDisplayName()))
                    {
                        company.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        company.setVisibility(View.GONE);
                    }

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

                    sample1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(JobDetails2.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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

                            Dialog dialog = new Dialog(JobDetails2.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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

                            Dialog dialog = new Dialog(JobDetails2.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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

                            Dialog dialog = new Dialog(JobDetails2.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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

                            Dialog dialog = new Dialog(JobDetails2.this , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
