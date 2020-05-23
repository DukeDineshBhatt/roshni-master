package com.app.roshni;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.roshni.contractorPOJO.Data;
import com.app.roshni.contractorPOJO.contractorBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    TextView name , phone , total , experience , availability , dob , gender , current , permanent , home_based , home_location , male , female , type , employer , about , samples;
    ProgressBar progress;

    String id;

    String mw , fw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contgractor);

        id = getIntent().getStringExtra("jid");

        toolbar = findViewById(R.id.toolbar2);
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

                builder.setMessage("Contact Admin for this Feature")
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
            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                Data item = response.body().getData();


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto() , image , options);

                mw = item.getWorkersMale();
                fw = item.getWorkersFemale();

                name.setText(item.getName());
                //phone.setText("+" + item.getPhone());
                total.setText(String.valueOf(Integer.parseInt(item.getWorkersMale()) + Integer.parseInt(item.getWorkersFemale())) + " workers");
                experience.setText(item.getExperience1());
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



                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<contractorBean> call, Throwable t) {
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
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
