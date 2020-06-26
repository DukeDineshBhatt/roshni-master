package com.app.roshni;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
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
import com.nostra13.universalimageloader.core.decode.ImageDecoder;

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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class personal extends Fragment {

    private static final String TAG = "personal";
    private Spinner gender, category, religion, educational, marital, children, below6, sixto14, fifteento18, goingtoschool, goingtoschool2, proof, age, annual;

    private String gend = "", cate = "", reli = "", educ = "", mari = "", chil = "", belo = "", sixt = "", fift = "", goin = "", goin2 = "", prf = "", anua = "";

    private EditText name, dob, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, editTxtProof, editTxtRelg, editTxtedu;

    private CheckBox check;
    private CircleImageView image;

    private Button upload, submit;

    private List<String> gen, gen1, cat, cat1, rel, rel1, edu, edu1, mar, mar1, chi, prof, prof1, agg, anu;

    private Uri uri;
    private File f1;

    private boolean che = false;

    private boolean rel_bool = false;

    private boolean edu_bool = false;

    private LinearLayout permanent, child;

    private ProgressBar progress;

    private CustomViewPager pager;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient mPlacesClient;
    boolean c1, c2, c3, c4, c5;

    void setData(CustomViewPager pager, boolean c1, boolean c2, boolean c3, boolean c4, boolean c5) {
        this.pager = pager;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
    }

    String lat = "", lng = "";

    String ag = "";

    int ag2 = 0;

    String same = "0";

    Spinner certified, skill_level;
    TextView certificate_number_title, skill_level_title;
    EditText certificate_number, other_sources;

    List<String> cer, cer1, ski, ski1;
    String cert = "", skil = "";

    Button previous;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_layout, container, false);

        gen = new ArrayList<>();
        gen1 = new ArrayList<>();
        cat = new ArrayList<>();
        cat1 = new ArrayList<>();
        rel = new ArrayList<>();
        rel1 = new ArrayList<>();
        edu = new ArrayList<>();
        edu1 = new ArrayList<>();
        mar = new ArrayList<>();
        mar1 = new ArrayList<>();
        chi = new ArrayList<>();
        prof = new ArrayList<>();
        prof1 = new ArrayList<>();
        agg = new ArrayList<>();
        anu = new ArrayList<>();

        cer = new ArrayList<>();
        cer1 = new ArrayList<>();
        ski = new ArrayList<>();
        ski1 = new ArrayList<>();

        Places.initialize(getContext().getApplicationContext(), getString(R.string.google_maps_key));
        mPlacesClient = Places.createClient(getContext());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLocationPermission();

        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
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
            Log.e("Exception1: %s", e.getMessage());
        }


        certified = view.findViewById(R.id.certified);
        goingtoschool2 = view.findViewById(R.id.goingtoschool2);
        previous = view.findViewById(R.id.previous);
        skill_level = view.findViewById(R.id.skill_level);
        certificate_number_title = view.findViewById(R.id.certificate_number_title);
        skill_level_title = view.findViewById(R.id.skill_level_title);
        certificate_number = view.findViewById(R.id.certificate_number);
        annual = view.findViewById(R.id.annual);
        other_sources = view.findViewById(R.id.other_sources);

        name = view.findViewById(R.id.editText);
        age = view.findViewById(R.id.age);
        dob = view.findViewById(R.id.editText2);
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
        editTxtProof = view.findViewById(R.id.editTxtProf);
        editTxtRelg = view.findViewById(R.id.editTxtRelg);
        editTxtedu = view.findViewById(R.id.editTxtedu);

        progress = view.findViewById(R.id.progress);

        permanent = view.findViewById(R.id.permanent);
        child = view.findViewById(R.id.child);

        check = view.findViewById(R.id.check);

        image = view.findViewById(R.id.imageView3);

        upload = view.findViewById(R.id.button7);
        submit = view.findViewById(R.id.submit);


        gender = view.findViewById(R.id.gender);
        category = view.findViewById(R.id.category);
        religion = view.findViewById(R.id.religion);
        educational = view.findViewById(R.id.educational);
        marital = view.findViewById(R.id.marital);
        children = view.findViewById(R.id.children);
        below6 = view.findViewById(R.id.belowsix);
        sixto14 = view.findViewById(R.id.sixto14);
        fifteento18 = view.findViewById(R.id.fifteento18);
        goingtoschool = view.findViewById(R.id.goingtoschool);
        proof = view.findViewById(R.id.proof);


        /*gen.add("Male");
        gen.add("Female");*/

        for (int i = 18; i <= 100; i++) {
            agg.add("" + i);
        }

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.confirm))
                        .setMessage(getString(R.string.close_app_text))

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                                Intent intent = new Intent(getActivity(), TermsAndConditions2.class);
                                intent.putExtra("type", "worker");
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


        /*cat.add("SC");
        cat.add("ST");
        cat.add("OBC");
        cat.add("General");*/

        /*rel.add("Hindu");
        rel.add("Muslim");
        rel.add("Sikh");
        rel.add("Christian");
        rel.add("Others");
*/
  /*      edu.add("Uneducated");
        edu.add("Primary (Class 1-5)");
        edu.add("Middle (Class 6-8)");
        edu.add("Secondary (Class 9-10)");
        edu.add("Senior Secondary (Class 11-12)");
        edu.add("Graduation");
        edu.add("Post Graduation");
        edu.add("Others");
*/
  /*      mar.add("Single");
        mar.add("Married");
        mar.add("Divorcee");
        mar.add("Separated");
*/
        chi.add("--- Select ---");
        chi.add("0");
        chi.add("1");
        chi.add("2");
        chi.add("3");
        chi.add("4");
        chi.add("5");
        chi.add("6");
        chi.add("7");
        chi.add("8");
        chi.add("9");
        chi.add("10");
        chi.add("11");
        chi.add("12");

        anu.add("--- Select ---");
        anu.add("0-100000");
        anu.add("100001-150000");
        anu.add("150001-200000");
        anu.add("More than 200000");

        /*prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");
*/


        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, chi);


        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, agg);


        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, anu);


        children.setAdapter(adapter5);
        below6.setAdapter(adapter5);
        sixto14.setAdapter(adapter5);
        fifteento18.setAdapter(adapter5);
        goingtoschool.setAdapter(adapter5);
        goingtoschool2.setAdapter(adapter5);
        annual.setAdapter(adapter8);

        age.setAdapter(adapter7);

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call1 = cr.getGender(SharePreferenceUtils.getInstance().getString("lang"));

        call1.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


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
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call21 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

        call21.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        cer.add(response.body().getData().get(i).getTitle());
                        cer1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, cer);


                    certified.setAdapter(adapter1);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        certified.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cert = cer1.get(i);

                if (cert.equals("2")) {
                    certificate_number_title.setVisibility(View.VISIBLE);
                    skill_level_title.setVisibility(View.VISIBLE);
                    certificate_number.setVisibility(View.VISIBLE);
                    skill_level.setVisibility(View.VISIBLE);
                    certificate_number.setText("");
                } else {
                    certificate_number_title.setVisibility(View.GONE);
                    skill_level_title.setVisibility(View.GONE);
                    certificate_number.setVisibility(View.GONE);
                    skill_level.setVisibility(View.GONE);
                    certificate_number.setText("---");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call22 = cr.getSkillLevel(SharePreferenceUtils.getInstance().getString("lang"));

        call22.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        ski.add(response.body().getData().get(i).getTitle());
                        ski1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, ski);


                    skill_level.setAdapter(adapter1);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        skill_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                skil = ski1.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        annual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                anua = anu.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call2 = cr.getCategories(SharePreferenceUtils.getInstance().getString("lang"));

        call2.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        cat.add(response.body().getData().get(i).getTitle());
                        cat1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, cat);


                    category.setAdapter(adapter1);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call3 = cr.getReligion(SharePreferenceUtils.getInstance().getString("lang"));

        call3.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        rel.add(response.body().getData().get(i).getTitle());
                        rel1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, rel);


                    religion.setAdapter(adapter2);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call4 = cr.getEducation(SharePreferenceUtils.getInstance().getString("lang"));

        call4.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        edu.add(response.body().getData().get(i).getTitle());
                        edu1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, edu);


                    educational.setAdapter(adapter3);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call5 = cr.getMarital(SharePreferenceUtils.getInstance().getString("lang"));

        call5.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        mar.add(response.body().getData().get(i).getTitle());
                        mar1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, mar);


                    marital.setAdapter(adapter4);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call6 = cr.getProof(SharePreferenceUtils.getInstance().getString("lang"));

        call6.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        prof.add(response.body().getData().get(i).getTitle());
                        prof1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_model, prof);


                    proof.setAdapter(adapter6);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

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
                        .build(getActivity());
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

        pdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.REGIONS)
                        .build(getActivity());
                startActivityForResult(intent, 14);

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

        age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ag = agg.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    cate = cat1.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    reli = rel1.get(i);

                    if (reli.equals("6")) {
                        rel_bool = true;

                        editTxtRelg.setVisibility(View.VISIBLE);
                    } else {
                        rel_bool = false;
                        editTxtRelg.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        educational.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                educ = edu1.get(i);

                if (educ.equals("9")) {
                    edu_bool = true;
                    editTxtedu.setVisibility(View.VISIBLE);
                } else {
                    edu_bool = false;
                    editTxtedu.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        marital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    mari = mar1.get(i);

                    if (mari.equals("2")) {
                        child.setVisibility(View.GONE);

                        chil = "0";
                        belo = "0";
                        sixt = "0";
                        fift = "0";
                        goin = "0";
                        goin2 = "0";

                    } else {
                        child.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    chil = chi.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        below6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    belo = chi.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sixto14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    sixt = chi.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fifteento18.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    fift = chi.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goingtoschool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                {
                    goin = chi.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goingtoschool2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                {
                    goin2 = chi.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

                long now = System.currentTimeMillis() - 1000;

                new SingleDateAndTimePickerDialog.Builder(getActivity())
                        //.bottomSheet()
                        .curved()
                        .displayMinutes(false)
                        .displayHours(false)
                        .displayDays(false)
                        .displayMonth(true)
                        .displayYears(true)
                        .displayDaysOfMonth(true)
                        .maxDateRange(new Date(now))
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

                                int cp12 = 0;
                                for (int i = 0; i < agg.size(); i++) {
                                    if (String.valueOf(ag2).equals(agg.get(i))) {
                                        cp12 = i;
                                    }
                                }
                                age.setSelection(cp12);

                                if (ag2 < 18) {
                                    Toast.makeText(getContext(), "You are not eligible to register in this app", Toast.LENGTH_SHORT).show();
                                }


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
                String cp = cpin.getText().toString();
                String cs = cstate.getText().toString();
                String cd = cdistrict.getText().toString();
                String ca = carea.getText().toString();
                String cst = cstreet.getText().toString();
                String idno = editTxtProof.getText().toString();

                String cno = certificate_number.getText().toString();
                String oth = other_sources.getText().toString();

                String pp;
                String ps;
                String pd;
                String pa;
                String pst;

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

                if (rel_bool) {
                    reli = editTxtRelg.getText().toString();
                }

                if (edu_bool) {
                    educ = editTxtedu.getText().toString();
                }


                if (n.length() > 0) {
                    if (ag2 > 17) {
                        if (gend.length() > 0) {
                            if (cd.length() > 0) {
                                if (cs.length() > 0) {
                                    if (cp.length() == 0 || cp.length() == 6) {
                                        if (pd.length() > 0) {
                                            if (ps.length() > 0) {
                                                if (pp.length() == 0 || pp.length() == 6) {
                                                    if (cate.length() > 0) {
                                                        if (reli.length() > 0) {
                                                            if (mari.length() > 0) {
                                                                if (chil.length() > 0) {
                                                                    if (belo.length() > 0) {
                                                                        if (sixt.length() > 0) {
                                                                            if (fift.length() > 0) {
                                                                                if (Integer.parseInt(belo) + Integer.parseInt(sixt) + Integer.parseInt(fift) <= Integer.parseInt(chil)) {
                                                                                    if (goin.length() > 0) {
                                                                                        if (Integer.parseInt(goin) <= Integer.parseInt(chil)) {
                                                                                            if (goin2.length() > 0) {
                                                                                                if (Integer.parseInt(goin2) <= Integer.parseInt(chil)) {
                                                                                                    if (Integer.parseInt(goin) + Integer.parseInt(goin2) <= Integer.parseInt(chil)) {
                                                                                                        MultipartBody.Part body = null;
                                                                                                        try {

                                                                                                            RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                                                                                            body = MultipartBody.Part.createFormData("photo", f1.getName(), reqFile1);


                                                                                                        } catch (Exception e1) {
                                                                                                            e1.printStackTrace();
                                                                                                        }

                                                                                                        progress.setVisibility(View.VISIBLE);

                                                                                                        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                                                                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                                                                .baseUrl(b.baseurl)
                                                                                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                                                                                .addConverterFactory(GsonConverterFactory.create())
                                                                                                                .build();

                                                                                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                                                                        Call<verifyBean> call = cr.updateWorkerPersonal(
                                                                                                                SharePreferenceUtils.getInstance().getString("user_id"),
                                                                                                                n,
                                                                                                                prf,
                                                                                                                idno,
                                                                                                                lat,
                                                                                                                lng,
                                                                                                                d,
                                                                                                                gend,
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
                                                                                                                cate,
                                                                                                                reli,
                                                                                                                educ,
                                                                                                                mari,
                                                                                                                chil,
                                                                                                                belo,
                                                                                                                sixt,
                                                                                                                fift,
                                                                                                                goin,
                                                                                                                goin2,
                                                                                                                ag,
                                                                                                                same,
                                                                                                                cert,
                                                                                                                skil,
                                                                                                                cno,
                                                                                                                anua,
                                                                                                                oth,
                                                                                                                body,
                                                                                                                String.valueOf(c1),
                                                                                                                String.valueOf(c2),
                                                                                                                String.valueOf(c3),
                                                                                                                String.valueOf(c4),
                                                                                                                String.valueOf(c5)
                                                                                                        );

                                                                                                        call.enqueue(new Callback<verifyBean>() {
                                                                                                            @Override
                                                                                                            public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

                                                                                                                assert response.body() != null;
                                                                                                                if (response.body().getStatus().equals("1")) {
                                                                                                                    Data item = response.body().getData();

                                                                                                                    SharePreferenceUtils.getInstance().saveString("name", item.getName());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("photo", item.getPhoto());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("dob", item.getDob());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("gender", item.getGender());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("phone", item.getPhone());
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
                                                                                                                    SharePreferenceUtils.getInstance().saveString("category", item.getCategory());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("religion", item.getReligion());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("educational", item.getEducational());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("marital", item.getMarital());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("children", item.getChildren());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("belowsix", item.getBelowsix());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("sixtofourteen", item.getSixtofourteen());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("fifteentoeighteen", item.getFifteentoeighteen());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("goingtoschool", item.getGoingtoschool());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("sector", item.getSector());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("skills", item.getSkills());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("experience", item.getExperience());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("employment", item.getEmployment());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("employer", item.getEmployer());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("home", item.getHome());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("workers", item.getWorkers());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("tools", item.getTools());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("location", item.getLocation());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("idproof", item.getId_proof());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("idproofnumber", item.getId_number());
                                                                                                                    SharePreferenceUtils.getInstance().saveString("status", item.getStatus());

                                                                                                                    Intent registrationComplete = new Intent("photo");

                                                                                                                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(registrationComplete);

                                                                                                                    pager.setCurrentItem(1);


                                                                                                                    Log.d("respo", response.body().getMessage());

                                                                                                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                } else {
                                                                                                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                }


                                                                                                                progress.setVisibility(View.GONE);


                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onFailure(Call<verifyBean> call, Throwable t) {
                                                                                                                progress.setVisibility(View.GONE);
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        Toast.makeText(getContext(), "Invalid children going to school count", Toast.LENGTH_SHORT).show();
                                                                                                    }

                                                                                                } else {
                                                                                                    Toast.makeText(getContext(), "Invalid Number of children going to school b/w 15-18", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(getContext(), "Invalid Number of children going to school b/w 15-18", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(getContext(), "Invalid Number of children going to school b/w 6-14", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(getContext(), "Invalid Number of children going to school b/w 6-14", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(getContext(), "Invalid children count", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                Toast.makeText(getContext(), "Invalid Number of Children in the age group of 15-18 years", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getContext(), "Invalid Number of Children in the age group of 6-14 years", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(getContext(), "Invalid Number of Children below 6 years", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getContext(), "Invalid no. of children", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getContext(), "Invalid marital status", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getContext(), "Invalid religion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Invalid category", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Invalid permanent PIN Code", Toast.LENGTH_SHORT).show();
                                                    ppin.setError("");
                                                    ppin.requestFocus();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Invalid permanent state", Toast.LENGTH_SHORT).show();
                                                pstate.setError("");
                                                pstate.requestFocus();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Invalid permanent district", Toast.LENGTH_SHORT).show();
                                            pdistrict.setError("");
                                            pdistrict.requestFocus();
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
                        }

                    } else {
                        Toast.makeText(getContext(), "Invalid D.O.B.", Toast.LENGTH_SHORT).show();
                        dob.setError("");
                        dob.requestFocus();
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


            File file = null;
            file = new File(ypath);

            try {
                f1 = new Compressor(getContext()).compressToFile(file);

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

                File file = new Compressor(getContext()).compressToFile(f1);

                f1 = file;

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
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String cii = addresses.get(0).getLocality();
                    String stat = addresses.get(0).getAdminArea();

                    cstate.setText(stat);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == 12) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
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
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == 13) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);


                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String cii = addresses.get(0).getLocality();
                    String stat = addresses.get(0).getAdminArea();

                    pstate.setText(stat);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == 14) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);


                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
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
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
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
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

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


    void setPrevious() {
        name.setText(SharePreferenceUtils.getInstance().getString("name"));
        editTxtProof.setText(SharePreferenceUtils.getInstance().getString("idproofnumber"));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(SharePreferenceUtils.getInstance().getString("photo"), image, options);
        dob.setText(SharePreferenceUtils.getInstance().getString("dob"));
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


        int gp = 0;
        for (int i = 0; i < gen.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("gender").equals(gen.get(i))) {
                gp = i;
            }
        }
        gender.setSelection(gp);

        int ip = 0;
        for (int i = 0; i < prof.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("idproof").equals(prof.get(i))) {
                ip = i;
            }
        }
        proof.setSelection(ip);


        int cp = 0;
        for (int i = 0; i < cat.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("category").equals(cat.get(i))) {
                cp = i;
            }
        }
        category.setSelection(cp);


        int rp = 0;
        for (int i = 0; i < rel.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("religion").equals(rel.get(i))) {
                rp = i;
            }
        }
        religion.setSelection(rp);

        int mp = 0;
        for (int i = 0; i < mar.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("marital").equals(mar.get(i))) {
                mp = i;
            }
        }
        marital.setSelection(mp);

        int ep = 0;
        for (int i = 0; i < edu.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("educational").equals(edu.get(i))) {
                ep = i;
            } else {
                ep = 8;
                editTxtedu.setText(SharePreferenceUtils.getInstance().getString("educational"));
            }
        }
        educational.setSelection(ep);

        int chp = 0;
        for (int i = 0; i < chi.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("children").equals(chi.get(i))) {
                chp = i;
            }
        }
        children.setSelection(chp);


        int bp = 0;
        for (int i = 0; i < chi.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("belowsix").equals(chi.get(i))) {
                bp = i;
            }
        }
        below6.setSelection(bp);

        int sp = 0;
        for (int i = 0; i < chi.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("sixtofourteen").equals(chi.get(i))) {
                sp = i;
            }
        }
        sixto14.setSelection(sp);

        int fp = 0;
        for (int i = 0; i < chi.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("fifteentoeighteen").equals(chi.get(i))) {
                fp = i;
            }
        }
        fifteento18.setSelection(fp);

        int gop = 0;
        for (int i = 0; i < chi.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("goingtoschool").equals(chi.get(i))) {
                gop = i;
            }
        }
        goingtoschool.setSelection(gop);

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
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


        int age = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = sdf.parse(dobString);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                Toast.makeText(getContext(), "Can't be born in the future", Toast.LENGTH_SHORT).show();
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age;


    }


}
