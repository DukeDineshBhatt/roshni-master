package com.workersjoint.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.workersjoint.app.contractorPOJO.Data;
import com.workersjoint.app.contractorPOJO.contractorBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SingleContgractor extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView image;
    TextView name , phone , total , experience , availability , dob , gender , current , permanent , home_based , home_location , male , female , type , employer , about , samples , sector;
    ProgressBar progress;

    String id;

    String mw , fw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contgractor);

        id = getIntent().getStringExtra("jid");

        toolbar = findViewById(R.id.toolbar2);
        sector = findViewById(R.id.sector);
        image = findViewById(R.id.image);
        progress = findViewById(R.id.progressBar5);
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
        toolbar.setTitle(getString(R.string.cont_details));

        progress.setVisibility(View.VISIBLE);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SingleContgractor.this);

                builder.setMessage("Please contact the administrator")
                        .setTitle("View Contractor Phone");

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        Intent intent = new Intent(SingleContgractor.this , Support.class);
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

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(id, SharePreferenceUtils.getInstance().getString("lang"));

        Log.d("jid" , id);

        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorBean> call, @NotNull Response<contractorBean> response) {

                Data item = Objects.requireNonNull(response.body()).getData();


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto() , image , options);

                mw = item.getWorkersMale();
                fw = item.getWorkersFemale();

                name.setText(item.getName());
                //phone.setText("+" + item.getPhone());
                total.setText((Integer.parseInt(item.getWorkersMale()) + Integer.parseInt(item.getWorkersFemale())) + " workers");
                experience.setText(item.getExperience());
                availability.setText(item.getAvailability1());
                dob.setText(item.getDob());
                gender.setText(item.getGender1());

                List<String> adrt = new ArrayList<>();
                adrt.add(item.getCstreet());
                adrt.add(item.getCarea());
                adrt.add(item.getCdistrict());
                adrt.add(item.getCstate());
                adrt.add(item.getCpin());
                adrt.removeAll(Collections.singleton(""));
                current.setText(TextUtils.join(", ", adrt));

                //current.setText(item.getCstreet() + ", " + item.getCarea() + ", " + item.getCdistrict() + ", " + item.getCstate() + "-" + item.getCpin());

                List<String> adrt2 = new ArrayList<>();
                adrt2.add(item.getPstreet());
                adrt2.add(item.getParea());
                adrt2.add(item.getPdistrict());
                adrt2.add(item.getPstate());
                adrt2.add(item.getPpin());
                adrt2.removeAll(Collections.singleton(""));
                permanent.setText(TextUtils.join(", ", adrt2));

                //current.setText(item.getCstreet() + ", " + item.getCarea() + ", " + item.getCdistrict() + ", " + item.getCstate() + "-" + item.getCpin());
                //permanent.setText(item.getPstreet() + ", " + item.getParea() + ", " + item.getPdistrict() + ", " + item.getPstate() + "-" + item.getPpin());
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



        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(SingleContgractor.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.worker_dialog);
                dialog.show();

                TextView mmww = dialog.findViewById(R.id.textView44);
                TextView ffww = dialog.findViewById(R.id.textView46);

                mmww.setText(mw);
                ffww.setText(fw);

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
