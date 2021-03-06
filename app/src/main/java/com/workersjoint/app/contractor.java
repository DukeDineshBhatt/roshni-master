package com.workersjoint.app;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workersjoint.app.SkillsPOJO.Datum;
import com.workersjoint.app.SkillsPOJO.skillsBean;
import com.workersjoint.app.sectorPOJO.Data;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verifyPOJO.verifyBean;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import mabbas007.tagsedittext.TagsEditText;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;

public class contractor extends Fragment {

    private static final String TAG = "conracao";
    private Spinner gender, establishment, availability, firm, proof, firmtype, outsource, govtinsurance , child_labour , supply_chain;

    EditText sector, work, experience;

    private String gend = "", esta = "", expe = "", wtyp = "", avai = "", frmy = "", prf = "", frmytyp = "", sect = "", outs = "", govt = "", chla = "", supl = "";

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about, looms, migrant, local;

    TagsEditText location;

    private CircleImageView image;

    CheckBox check;

    private Button submit;

    private List<String> gen;
    private List<String> gen1;
    private List<String> est;
    private List<String> exp;
    private List<String> wty1;
    private List<String> ava;
    private List<String> ava1;
    private List<String> frm;
    private List<String> frm1;
    private List<String> frmtyp;
    private List<String> frmtyp1;
    private List<String> prof;
    private List<String> prof1;
    private List<String> sec1;
    private List<String> out;
    private List<String> out1;
    private List<String> gov;
    private List<String> gov1;
    private List<String> chi;
    private List<String> chi1;

    List<Data> sec;
    List<Datum> wty;

    private Uri uri;
    private File f1;

    private boolean che = false;

    private LinearLayout permanent;

    private ProgressBar progress;
    private CustomViewPager pager;


    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    boolean c1, c2, c3, c4, c5;

    void setData(CustomViewPager pager, boolean c1, boolean c2, boolean c3, boolean c4, boolean c5) {
        this.pager = pager;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
    }

    boolean gov_bool = false;

    String lat = "", lng = "";

    EditText otherwork, othergovt;

    int ag2 = 0;

    String same = "0";

    EditText email, non_school, school, without_bank;

    LinearLayout home_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contractor_layout, container, false);

        gen = new ArrayList<>();
        gen1 = new ArrayList<>();
        est = new ArrayList<>();
        exp = new ArrayList<>();
        List<String> exp1 = new ArrayList<>();
        wty = new ArrayList<>();
        wty1 = new ArrayList<>();
        ava = new ArrayList<>();
        ava1 = new ArrayList<>();
        frm = new ArrayList<>();
        frm1 = new ArrayList<>();
        prof = new ArrayList<>();
        prof1 = new ArrayList<>();
        frmtyp = new ArrayList<>();
        frmtyp1 = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        out = new ArrayList<>();
        out1 = new ArrayList<>();
        gov = new ArrayList<>();
        gov1 = new ArrayList<>();
        chi = new ArrayList<>();
        chi1 = new ArrayList<>();

        Places.initialize(Objects.requireNonNull(getContext()).getApplicationContext(), getString(R.string.google_maps_key));
        PlacesClient mPlacesClient = Places.createClient(getContext());

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        getLocationPermission();

        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
                }// TODO: Consider calling
//    ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                          int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
                Task locationResult = mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {


                        if (location != null) {
                            // Logic to handle location object
                            mLastKnownLocation = location;
                            lat = String.valueOf(mLastKnownLocation.getLatitude());
                            lng = String.valueOf(mLastKnownLocation.getLongitude());
                            Log.d("location", String.valueOf(mLastKnownLocation.getLatitude()));
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        e.printStackTrace();

                    }
                });

            }
        } catch (Exception e) {
            Log.e("Exception1: %s", Objects.requireNonNull(e.getMessage()));
        }


        child_labour = view.findViewById(R.id.child_labour);
        supply_chain = view.findViewById(R.id.supply_chain);
        email = view.findViewById(R.id.email);
        othergovt = view.findViewById(R.id.othergovt);
        otherwork = view.findViewById(R.id.otherwork);
        home_layout = view.findViewById(R.id.home_layout);
        non_school = view.findViewById(R.id.non_school);
        school = view.findViewById(R.id.school);
        without_bank = view.findViewById(R.id.without_bank);
        looms = view.findViewById(R.id.looms);
        migrant = view.findViewById(R.id.migrant);
        local = view.findViewById(R.id.local);
        outsource = view.findViewById(R.id.outsource);
        Button previous = view.findViewById(R.id.previous);
        name = view.findViewById(R.id.editText);
        sector = view.findViewById(R.id.sector);
        dob = view.findViewById(R.id.dob);
        business = view.findViewById(R.id.business);
        cpin = view.findViewById(R.id.editText3);
        cstate = view.findViewById(R.id.editText4);
        cdistrict = view.findViewById(R.id.editText5);
        carea = view.findViewById(R.id.editText6);
        cstreet = view.findViewById(R.id.editText7);
        ppin = view.findViewById(R.id.editText20);
        pstate = view.findViewById(R.id.editText22);
        pdistrict = view.findViewById(R.id.editText24);
        parea = view.findViewById(R.id.editText26);
        pstreet = view.findViewById(R.id.editText28);
        home_based = view.findViewById(R.id.home_based);
        employer = view.findViewById(R.id.employer);
        progress = view.findViewById(R.id.progress);
        location = view.findViewById(R.id.location);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        about = view.findViewById(R.id.about);
        firm = view.findViewById(R.id.firm);
        editTxtProof = view.findViewById(R.id.editTxtProf);
        proof = view.findViewById(R.id.proof);
        firmtype = view.findViewById(R.id.firmtype);
        reg_no = view.findViewById(R.id.reg_no);
        govtinsurance = view.findViewById(R.id.govtinsurance);

        /* cstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(getActivity());
                startActivityForResult(intent, 11);

            }
        });*/

        cdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.REGIONS)
                        .build(Objects.requireNonNull(getActivity()));
                startActivityForResult(intent, 12);

            }
        });

       /* pstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(getActivity());
                startActivityForResult(intent, 13);

            }
        });*/

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                        .setTitle(getString(R.string.confirm))
                        .setMessage(getString(R.string.go_back_text))

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                                Intent intent = new Intent(getActivity(), TermsAndConditions2.class);
                                intent.putExtra("type", "contractor");
                                startActivity(intent);
                                getActivity().finish();
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

        pdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.REGIONS)
                        .build(Objects.requireNonNull(getActivity()));
                startActivityForResult(intent, 14);

            }
        });

        /*gen.add("Male");
        gen.add("Female");
*/
        /*prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");*/

        est.add("--- Select ---");
        est.add("2020-2024");
        est.add("2015-2019");
        est.add("2010-2014");
        est.add("2005-2009");
        est.add("2000-2004");
        est.add("1975-1999");
        est.add("1950-1974");
        est.add("before 1950");

       /* est.add("1950-2000");
        est.add("2001");
        est.add("2002");
        est.add("2003");
        est.add("2004");
        est.add("2005");
        est.add("2006");
        est.add("2007");
        est.add("2008");
        est.add("2009");
        est.add("2010");
        est.add("2011");
        est.add("2012");
        est.add("2013");
        est.add("2014");
        est.add("2015");
        est.add("2016");
        est.add("2017");
        est.add("2018");
        est.add("2019");
        est.add("2020");
        est.add("2021");
        est.add("2022");
        est.add("2023");
        est.add("2024");
        est.add("2025");*/

        /*exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");
*/
        /*ava.add("Available");
        ava.add("Within a Month");
        ava.add("Within Two Months");
*/
       /* frm.add("Sole-properietor");
        frm.add("Partnership");
        frm.add("Pvt.Ltd. Company");
        frm.add("Ltd. Company");
        frm.add("LLC");
        frm.add("LLP");
        frm.add("Co-operative");
        frm.add("Trust");

        frmtyp.add("None");
        frmtyp.add("SSI");
        frmtyp.add("MSME");
        frmtyp.add("Cottage Industry");*/

        permanent = view.findViewById(R.id.permanent);

        check = view.findViewById(R.id.check);

        image = view.findViewById(R.id.imageView3);

        Button upload = view.findViewById(R.id.button7);
        submit = view.findViewById(R.id.submit);

        gender = view.findViewById(R.id.gender);
        establishment = view.findViewById(R.id.establishment);
        experience = view.findViewById(R.id.experience);
        work = view.findViewById(R.id.work);
        availability = view.findViewById(R.id.availability);


        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, est);


        establishment.setAdapter(adapter1);


        establishment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                esta = est.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    che = true;
                    permanent.setVisibility(View.GONE);
                } else {
                    che = false;
                    permanent.setVisibility(View.VISIBLE);
                }

            }
        });

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        sector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.sector_dialog);
                dialog.show();

                RecyclerView sectorgrid = dialog.findViewById(R.id.grid);
                Button ok = dialog.findViewById(R.id.button30);
                final ProgressBar bar = dialog.findViewById(R.id.progressBar10);

                final SectorAdapter adapter = new SectorAdapter(getActivity(), sec);
                GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
                sectorgrid.setAdapter(adapter);
                sectorgrid.setLayoutManager(manager);

                bar.setVisibility(View.VISIBLE);

                final Call<sectorBean> call = cr.getSectors2(SharePreferenceUtils.getInstance().getString("lang"));

                call.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                            adapter.setData(response.body().getData());

                        }

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sec1 = adapter.getIds();
                        dialog.dismiss();

                        sector.setText(TextUtils.join(", ", adapter.getSecs()));
                        Log.d("sectors", TextUtils.join(",", sec1));

                    }
                });

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call331 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

        call331.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        gov.add(response.body().getData().get(i).getTitle());
                        gov1.add(response.body().getData().get(i).getId());

                    }


                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, gov);


                    govtinsurance.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.sector_dialog);
                dialog.show();

                RecyclerView sectorgrid = dialog.findViewById(R.id.grid);
                Button ok = dialog.findViewById(R.id.button30);
                final ProgressBar bar = dialog.findViewById(R.id.progressBar10);

                final WorkAdapter adapter = new WorkAdapter(getActivity(), wty);
                GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
                sectorgrid.setAdapter(adapter);
                sectorgrid.setLayoutManager(manager);

                final Call<skillsBean> call = cr.getSkills1(TextUtils.join(",", sec1), SharePreferenceUtils.getInstance().getString("lang"));

                bar.setVisibility(View.VISIBLE);

                call.enqueue(new Callback<skillsBean>() {
                    @Override
                    public void onResponse(@NotNull Call<skillsBean> call, @NotNull Response<skillsBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            adapter.setData(response.body().getData());

                        }

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(@NotNull Call<skillsBean> call, @NotNull Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        wty1 = adapter.getIds();
                        dialog.dismiss();

                        work.setText(TextUtils.join(", ", adapter.getWorks()));
                        Log.d("sectors", TextUtils.join(",", wty1));


                    }
                });

            }
        });


        govtinsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                govt = gov1.get(i);

                if (govt.equals("5")) {
                    gov_bool = true;
                    othergovt.setVisibility(View.VISIBLE);
                } else {
                    gov_bool = false;
                    othergovt.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call1 = cr.getGender(SharePreferenceUtils.getInstance().getString("lang"));

        call1.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        gen.add(response.body().getData().get(i).getTitle());
                        gen1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
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

                if (i > 0) {
                    gend = gen1.get(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call2 = cr.getProof(SharePreferenceUtils.getInstance().getString("lang"));

        call2.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        prof.add(response.body().getData().get(i).getTitle());
                        prof1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, prof);


                    proof.setAdapter(adapter6);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                prf = prof1.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call4 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

        call4.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        ava.add(response.body().getData().get(i).getTitle());
                        ava1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, ava);


                    availability.setAdapter(adapter4);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        availability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    avai = ava1.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call5 = cr.getFirmTypes(SharePreferenceUtils.getInstance().getString("lang"));

        call5.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        frm.add(response.body().getData().get(i).getTitle());
                        frm1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter5 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, frm);


                    firm.setAdapter(adapter5);

                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        firm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                frmy = frm1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call6 = cr.getFirmRegistyrationTypes(SharePreferenceUtils.getInstance().getString("lang"));

        call6.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        frmtyp.add(response.body().getData().get(i).getTitle());
                        frmtyp1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, frmtyp);


                    firmtype.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        firmtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                frmytyp = frmtyp1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call7 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

        call7.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        out.add(response.body().getData().get(i).getTitle());
                        out1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, out);


                    outsource.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        outsource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                outs = out1.get(i);

                if (outs.equals("2")) {
                    home_layout.setVisibility(View.VISIBLE);
                } else {
                    home_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call8 = cr.getChild(SharePreferenceUtils.getInstance().getString("lang"));

        call8.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        chi.add(response.body().getData().get(i).getTitle());
                        chi1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                            R.layout.spinner_model, chi);


                    child_labour.setAdapter(adapter7);
                    supply_chain.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        child_labour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                chla = chi1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        supply_chain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                supl = chi1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*work.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                wtyp = wty1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f1 = new File(file);
                            try {
                                f1.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SingleDateAndTimePickerDialog.Builder(getActivity())
                        //.bottomSheet()
                        .curved()
                        .displayMinutes(false)
                        .displayHours(false)
                        .displayDays(false)
                        .displayMonth(true)
                        .displayYears(true)
                        .displayDaysOfMonth(true)
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {

                                //date.getTime();

                                Date dt = new Date(date.getTime());
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String dd = sdf.format(dt);


                                Log.d("dddd", dd);

                                dob.setText(dd);

                                ag2 = getAge(dd);


                            }
                        })
                        .display();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = name.getText().toString();
                String d = dob.getText().toString();
                String b = business.getText().toString();
                String p = editTxtProof.getText().toString();
                String r = reg_no.getText().toString();

                String cp = cpin.getText().toString();
                String cs = cstate.getText().toString();
                String cd = cdistrict.getText().toString();
                String ca = carea.getText().toString();
                String cst = cstreet.getText().toString();
                String ab = about.getText().toString();
                String ot = otherwork.getText().toString();

                String h = home_based.getText().toString();

                List<String> loc = location.getTags();
                String l = TextUtils.join(",", loc);

                String m = male.getText().toString();
                String f = female.getText().toString();
                String e = employer.getText().toString();
                String loo = looms.getText().toString();

                String mig = migrant.getText().toString();
                String loca = local.getText().toString();


                String ema = email.getText().toString();
                String nons = non_school.getText().toString();
                String sch = school.getText().toString();
                String with = without_bank.getText().toString();
                expe = experience.getText().toString();

                String pp;
                String ps;
                String pd;
                String pa;
                String pst;

                wtyp = TextUtils.join(",", wty1);
                sect = TextUtils.join(",", sec1);

                if (che) {
                    same = "1";
                    pp = cp;
                    ps = cs;
                    pd = cd;
                    pa = ca;
                    pst = cst;
                } else {
                    same = "0";
                    pp = ppin.getText().toString();
                    ps = pstate.getText().toString();
                    pd = pdistrict.getText().toString();
                    pa = parea.getText().toString();
                    pst = pstreet.getText().toString();
                }

                if (gov_bool) {
                    govt = othergovt.getText().toString();
                }

                if (n.length() > 0) {
                    if (gend.length() > 0) {
                        if (cd.length() > 0) {
                            if (cs.length() > 0) {
                                if (cp.length() == 0 || cp.length() == 6) {

                                    if (sect.length() > 0) {
                                        if (m.length() > 0) {

                                            if (f.length() > 0) {

                                                int totw = Integer.parseInt(m) + Integer.parseInt(f);

                                                if (mig.length() > 0) {
                                                    if (Integer.parseInt(mig) <= totw) {
                                                        if (loca.length() > 0) {
                                                            if (Integer.parseInt(loca) <= totw) {
                                                                if (expe.length() > 0) {
                                                                    if (wtyp.length() > 0) {

                                                                        if (avai.length() > 0) {
                                                                            Log.d("contractorc1", String.valueOf(c1));

                                                                            submit.setFocusable(false);
                                                                            submit.setClickable(false);

                                                                            MultipartBody.Part body = null;

                                                                            try {

                                                                                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                                                                body = MultipartBody.Part.createFormData("photo", f1.getName(), reqFile1);


                                                                            } catch (Exception e1) {
                                                                                e1.printStackTrace();
                                                                            }

                                                                            progress.setVisibility(View.VISIBLE);

                                                                            Bean b1 = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                                    .baseUrl(b1.baseurl)
                                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                                    .build();

                                                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                                            Call<verifyBean> call = cr.update_contractor(
                                                                                    SharePreferenceUtils.getInstance().getString("user_id"),
                                                                                    n,
                                                                                    prf,
                                                                                    p,
                                                                                    frmy,
                                                                                    frmytyp,
                                                                                    r,
                                                                                    lat,
                                                                                    lng,
                                                                                    d,
                                                                                    gend,
                                                                                    b,
                                                                                    esta,
                                                                                    cp,
                                                                                    cs,
                                                                                    cd,
                                                                                    ca,
                                                                                    cst,
                                                                                    pp,
                                                                                    ps,
                                                                                    pd,
                                                                                    pa,
                                                                                    pst,
                                                                                    h,
                                                                                    l,
                                                                                    m,
                                                                                    f,
                                                                                    expe,
                                                                                    wtyp,
                                                                                    ot,
                                                                                    avai,
                                                                                    e,
                                                                                    ab,
                                                                                    sect,
                                                                                    loo,
                                                                                    same,
                                                                                    body,
                                                                                    String.valueOf(c1),
                                                                                    String.valueOf(c2),
                                                                                    String.valueOf(c3),
                                                                                    String.valueOf(c4),
                                                                                    String.valueOf(c5),
                                                                                    outs,
                                                                                    mig,
                                                                                    loca,
                                                                                    with,
                                                                                    sch,
                                                                                    nons,
                                                                                    ema,
                                                                                    govt,
                                                                                    chla,
                                                                                    supl
                                                                            );

                                                                            call.enqueue(new Callback<verifyBean>() {
                                                                                @Override
                                                                                public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                                                                    assert response.body() != null;
                                                                                    if (response.body().getStatus().equals("1")) {
                                                                                        com.workersjoint.app.verifyPOJO.Data item = response.body().getData();

                                                                                        submit.setFocusable(true);
                                                                                        submit.setClickable(true);

                                                                                        SharePreferenceUtils.getInstance().saveString("name", item.getName());
                                                                                        SharePreferenceUtils.getInstance().saveString("photo", item.getPhoto());
                                                                                        SharePreferenceUtils.getInstance().saveString("dob", item.getDob());
                                                                                        SharePreferenceUtils.getInstance().saveString("gender", item.getGender());
                                                                                        SharePreferenceUtils.getInstance().saveString("phone", item.getPhone());
                                                                                        SharePreferenceUtils.getInstance().saveString("business_name", item.getBusiness_name());
                                                                                        SharePreferenceUtils.getInstance().saveString("establishment_year", item.getEstablishment_year());
                                                                                        SharePreferenceUtils.getInstance().saveString("cpin", item.getCpin());
                                                                                        SharePreferenceUtils.getInstance().saveString("cstate", item.getCstate());
                                                                                        SharePreferenceUtils.getInstance().saveString("cdistrict", item.getCdistrict());
                                                                                        SharePreferenceUtils.getInstance().saveString("carea", item.getCarea());
                                                                                        SharePreferenceUtils.getInstance().saveString("cstreet", item.getCstreet());
                                                                                        SharePreferenceUtils.getInstance().saveString("ppin", item.getPpin());
                                                                                        SharePreferenceUtils.getInstance().saveString("pstate", item.getPstate());
                                                                                        SharePreferenceUtils.getInstance().saveString("pdistrict", item.getPdistrict());
                                                                                        SharePreferenceUtils.getInstance().saveString("parea", item.getParea());
                                                                                        SharePreferenceUtils.getInstance().saveString("pstreet", item.getPstreet());
                                                                                        SharePreferenceUtils.getInstance().saveString("home_units", item.getHome_units());
                                                                                        SharePreferenceUtils.getInstance().saveString("home_location", item.getHome_location());
                                                                                        SharePreferenceUtils.getInstance().saveString("workers_male", item.getWorkers_male());
                                                                                        SharePreferenceUtils.getInstance().saveString("workers_female", item.getWorkers_female());
                                                                                        SharePreferenceUtils.getInstance().saveString("work_type", item.getWork_type());
                                                                                        SharePreferenceUtils.getInstance().saveString("availability", item.getAvailability());
                                                                                        SharePreferenceUtils.getInstance().saveString("employer", item.getEmployer());
                                                                                        SharePreferenceUtils.getInstance().saveString("experience", item.getExperience());
                                                                                        SharePreferenceUtils.getInstance().saveString("about", item.getAbout());
                                                                                        SharePreferenceUtils.getInstance().saveString("sector", item.getSector());


                                                                                        Intent registrationComplete = new Intent("photo");

                                                                                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(registrationComplete);

                                                                                        pager.setCurrentItem(1);

                                                                                    } else {

                                                                                        submit.setFocusable(true);
                                                                                        submit.setClickable(true);

                                                                                    }
                                                                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                                                                                    progress.setVisibility(View.GONE);

                                                                                }

                                                                                @Override
                                                                                public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                                                                    progress.setVisibility(View.GONE);
                                                                                    submit.setFocusable(true);
                                                                                    submit.setClickable(true);
                                                                                }
                                                                            });
                                                                        } else {
                                                                            Toast.makeText(getContext(), "Please select availability", Toast.LENGTH_SHORT).show();
                                                                            availability.requestFocus();
                                                                        }


                                                                    } else {
                                                                        Toast.makeText(getContext(), "Please select type of work", Toast.LENGTH_SHORT).show();
                                                                        work.requestFocus();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getContext(), "Invalid experience", Toast.LENGTH_SHORT).show();
                                                                }

                                                            } else {
                                                                Toast.makeText(getContext(), "Invalid local workers", Toast.LENGTH_SHORT).show();
                                                                local.setError("");
                                                                local.requestFocus();
                                                            }
                                                        } else {
                                                            Toast.makeText(getContext(), "Invalid local workers", Toast.LENGTH_SHORT).show();
                                                            local.setError("");
                                                            local.requestFocus();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Invalid migrant workers", Toast.LENGTH_SHORT).show();
                                                        migrant.setError("");
                                                        migrant.requestFocus();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Invalid migrant workers", Toast.LENGTH_SHORT).show();
                                                    migrant.setError("");
                                                    migrant.requestFocus();
                                                }


                                            } else {
                                                Toast.makeText(getContext(), "Invalid female workers", Toast.LENGTH_SHORT).show();
                                                female.setError("");
                                                female.requestFocus();
                                            }

                                        } else {
                                            Toast.makeText(getContext(), "Invalid male workers", Toast.LENGTH_SHORT).show();
                                            male.setError("");
                                            male.requestFocus();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Invalid sector", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(getContext(), "Invalid current PIN Code", Toast.LENGTH_SHORT).show();
                                    cpin.setError("");
                                    cpin.requestFocus();
                                }
                            } else {
                                Toast.makeText(getContext(), "Invalid current state", Toast.LENGTH_SHORT).show();
                                cstate.setError("");
                                cstate.requestFocus();
                            }
                        } else {
                            Toast.makeText(getContext(), "Invalid current district", Toast.LENGTH_SHORT).show();
                            cdistrict.setError("");
                            cdistrict.requestFocus();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid gender", Toast.LENGTH_SHORT).show();
                        gender.requestFocus();
                    }


                } else {
                    Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                    name.setError("");
                    name.requestFocus();
                }

            }
        });

        //setPrevious();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri = data.getData();

            Log.d("uri", String.valueOf(uri));

            String ypath = getPath(getContext(), uri);
            assert ypath != null;


            File file;
            file = new File(ypath);

            try {
                f1 = new Compressor(Objects.requireNonNull(getContext())).compressToFile(file);

                uri = Uri.fromFile(f1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("path1", ypath);

            image.setImageURI(uri);


            /*f1 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri));

            Log.d("bitmap", String.valueOf(bmp));

            image.setImageBitmap(bmp);*/

        } else if (requestCode == 1 && resultCode == RESULT_OK) {

            Log.d("uri1", String.valueOf(uri));

            try {

                f1 = new Compressor(getContext()).compressToFile(f1);

                uri = Uri.fromFile(f1);

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            image.setImageURI(uri);
        }

        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);


                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);
                    String cii = addresses.get(0).getLocality();
                    String stat = addresses.get(0).getAdminArea();

                    cstate.setText(stat);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }  // The user canceled the operation.

        }

        if (requestCode == 12) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);
                    Log.d("addresss", String.valueOf(addresses.get(0)));
                    String cii = place.getName();
                    String stat = addresses.get(0).getAdminArea();

                    cdistrict.setText(cii);
                    cstate.setText(stat);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }  // The user canceled the operation.

        }

        if (requestCode == 13) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);


                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);
                    String cii = addresses.get(0).getLocality();
                    String stat = addresses.get(0).getAdminArea();

                    pstate.setText(stat);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }  // The user canceled the operation.

        }

        if (requestCode == 14) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);


                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 1);
                    String cii = place.getName();
                    String stat = addresses.get(0).getAdminArea();
                    pdistrict.setText(cii);
                    pstate.setText(stat);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }  // The user canceled the operation.

        }


    }

    private static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }


    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }


    private void setPrevious() {

        name.setText(SharePreferenceUtils.getInstance().getString("name"));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(SharePreferenceUtils.getInstance().getString("photo"), image, options);

        dob.setText(SharePreferenceUtils.getInstance().getString("dob"));
        business.setText(SharePreferenceUtils.getInstance().getString("business_name"));

        home_based.setText(SharePreferenceUtils.getInstance().getString("home_units"));

        String ppp = SharePreferenceUtils.getInstance().getString("home_location");

        location.setTags(ppp.split(","));

        employer.setText(SharePreferenceUtils.getInstance().getString("employer"));
        male.setText(SharePreferenceUtils.getInstance().getString("workers_male"));
        female.setText(SharePreferenceUtils.getInstance().getString("workers_female"));
        ppin.setText(SharePreferenceUtils.getInstance().getString("ppin"));
        pstate.setText(SharePreferenceUtils.getInstance().getString("pstate"));
        pdistrict.setText(SharePreferenceUtils.getInstance().getString("pdistrict"));
        parea.setText(SharePreferenceUtils.getInstance().getString("parea"));
        pstreet.setText(SharePreferenceUtils.getInstance().getString("pstreet"));
        cpin.setText(SharePreferenceUtils.getInstance().getString("cpin"));
        cstate.setText(SharePreferenceUtils.getInstance().getString("cstate"));
        cdistrict.setText(SharePreferenceUtils.getInstance().getString("cdistrict"));
        carea.setText(SharePreferenceUtils.getInstance().getString("carea"));
        cstreet.setText(SharePreferenceUtils.getInstance().getString("cstreet"));
        about.setText(SharePreferenceUtils.getInstance().getString("about"));


        int gp = 0;
        for (int i = 0; i < gen.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("gender").equals(gen.get(i))) {
                gp = i;
            }
        }
        gender.setSelection(gp);


        int ep = 0;
        for (int i = 0; i < est.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("establishment_year").equals(est.get(i))) {
                ep = i;
            }
        }
        establishment.setSelection(ep);

        int rp = 0;
        for (int i = 0; i < exp.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("experience").equals(exp.get(i))) {
                rp = i;
            }
        }
        experience.setSelection(rp);

        int wp = 0;
        for (int i = 0; i < wty.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("work_type").equals(wty.get(i))) {
                wp = i;
            }
        }
        work.setSelection(wp);


        int ap = 0;
        for (int i = 0; i < ava.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("availability").equals(ava.get(i))) {
                ap = i;
            }
        }
        availability.setSelection(ap);


    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()).getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    private int getAge(String dobString) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

    class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.ViewHolder> {

        final Context context;
        List<Data> list;
        final List<String> ids = new ArrayList<>();
        final List<String> secs = new ArrayList<>();

        public SectorAdapter(Context context, List<Data> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Data> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = Objects.requireNonNull(inflater).inflate(R.layout.sector_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            final Data item = list.get(position);

            holder.title.setText(item.getTitle());

            if (sec1.contains(item.getId())) {
                ids.add(item.getId());
                secs.add(item.getTitle());
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            } else {
                ids.remove(item.getId());
                secs.remove(item.getTitle());
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.card.getCardBackgroundColor() == ColorStateList.valueOf(context.getResources().getColor(R.color.white))) {
                        ids.add(item.getId());
                        secs.add(item.getTitle());
                        holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                    } else {
                        ids.remove(item.getId());
                        secs.remove(item.getTitle());
                        holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                    }

                }
            });

        }

        public List<String> getSecs() {
            return secs;
        }

        public List<String> getIds() {
            return ids;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView title;
            final CardView card;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

    class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

        final Context context;
        List<Datum> list;
        final List<String> ids = new ArrayList<>();
        final List<String> works = new ArrayList<>();

        public WorkAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = Objects.requireNonNull(inflater).inflate(R.layout.sector_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            holder.setIsRecyclable(false);

            final Datum item = list.get(position);

            holder.title.setText(item.getTitle());

            if (wty1.contains(item.getId())) {
                if (!ids.contains(item.getId())) {
                    ids.add(item.getId());
                    works.add(item.getTitle());
                }
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            } else {
                ids.remove(item.getId());
                works.remove(item.getTitle());
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.card.getCardBackgroundColor() == ColorStateList.valueOf(context.getResources().getColor(R.color.white))) {

                        if (item.getId().equals("59")) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.other_dialog);
                            dialog.show();

                            final EditText other = dialog.findViewById(R.id.other);
                            Button sub = dialog.findViewById(R.id.submit);

                            sub.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String ot = other.getText().toString();

                                    if (ot.length() > 0) {
                                        dialog.dismiss();
                                        otherwork.setText(ot);
                                        otherwork.setVisibility(View.VISIBLE);
                                        ids.add(item.getId());
                                        works.add(item.getTitle());
                                        holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                                    } else {
                                        Toast.makeText(context, "Invalid work type", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        } else {
                            ids.add(item.getId());
                            works.add(item.getTitle());
                            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                        }


                    } else {

                        if (item.getId().equals("59")) {
                            ids.remove(item.getId());
                            works.remove(item.getTitle());
                            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                            otherwork.setText("");
                            otherwork.setVisibility(View.GONE);
                        } else {
                            ids.remove(item.getId());
                            works.remove(item.getTitle());
                            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                        }


                    }

                }
            });

        }

        public List<String> getWorks() {
            return works;
        }

        public List<String> getIds() {
            return ids;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView title;
            final CardView card;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

}
