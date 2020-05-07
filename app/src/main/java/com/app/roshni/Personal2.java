package com.app.roshni;

import android.Manifest;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;
import com.app.roshni.workerListPOJO.Datum;
import com.app.roshni.workerListPOJO.workerListBean;
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
import java.util.Locale;
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

public class Personal2 extends Fragment {

    private static final String TAG = "personal";
    private Spinner gender, category, religion, educational, marital, children, below6, sixto14, fifteento18, goingtoschool, proof , age;


    private EditText name, dob, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, editTxtProof, editTxtRelg, editTxtedu;

    private CircleImageView image;

    private String gend, cate, reli, educ, mari, chil, belo, sixt, fift, goin, id, prf;

    private CheckBox check;
    private File f1;
    private Uri uri;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean rel_bool = false;

    private boolean edu_bool = false;


    private boolean che = false;

    private List<String> gen, cat, rel, edu, mar, chi, prof , agg;

    private LinearLayout permanent, child;

    private Button upload, submit;
    private Location mLastKnownLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient mPlacesClient;

    private ProgressBar progress;

    private CustomViewPager pager;
    private boolean mLocationPermissionGranted;

    TextView getDirection;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    String lat = "" , lng = "";
    String lat1 = "" , lng1 = "";

    String ag;

    int ag2 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal, container, false);


        gen = new ArrayList<>();
        cat = new ArrayList<>();
        rel = new ArrayList<>();
        edu = new ArrayList<>();
        mar = new ArrayList<>();
        chi = new ArrayList<>();
        prof = new ArrayList<>();
        agg = new ArrayList<>();

        Places.initialize(getContext().getApplicationContext(), getString(R.string.google_maps_key));
        mPlacesClient = Places.createClient(getContext());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLocationPermission();

        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {


                        if (location != null) {
                            // Logic to handle location object
                            mLastKnownLocation = location;
                            lat1 = String.valueOf(mLastKnownLocation.getLatitude());
                            lng1 = String.valueOf(mLastKnownLocation.getLongitude());

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
        getDirection = view.findViewById(R.id.getdirection);

        progress = view.findViewById(R.id.progress);
        upload = view.findViewById(R.id.button7);
        submit = view.findViewById(R.id.submit);

        check = view.findViewById(R.id.check);

        permanent = view.findViewById(R.id.permanent);
        child = view.findViewById(R.id.child);
        check = view.findViewById(R.id.check);


        image = view.findViewById(R.id.imageView3);


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


        gen.add("Male");
        gen.add("Female");

        for (int i = 14 ; i <= 100 ; i++)
        {
            agg.add("" + i);
        }

        cat.add("SC");
        cat.add("ST");
        cat.add("OBC");
        cat.add("General");

        rel.add("Hindu");
        rel.add("Muslim");
        rel.add("Sikh");
        rel.add("Christian");
        rel.add("Others");

        edu.add("Uneducated");
        edu.add("Primary (Class 1-5)");
        edu.add("Middle (Class 6-8)");
        edu.add("Secondary (Class 9-10)");
        edu.add("Senior Secondary (Class 11-12)");
        edu.add("Graduation");
        edu.add("Post Graduation");
        edu.add("Others");

        mar.add("Single");
        mar.add("Married");
        mar.add("Divorcee");
        mar.add("Separated");

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

        prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");

        id = SharePreferenceUtils.getInstance().getString("user");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.spinner_model, gen);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, cat);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, rel);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, edu);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, mar);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, chi);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, prof);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, agg);

        gender.setAdapter(adapter);
        category.setAdapter(adapter1);
        religion.setAdapter(adapter2);
        educational.setAdapter(adapter3);
        marital.setAdapter(adapter4);
        children.setAdapter(adapter5);
        below6.setAdapter(adapter5);
        sixto14.setAdapter(adapter5);
        fifteento18.setAdapter(adapter5);
        goingtoschool.setAdapter(adapter5);
        proof.setAdapter(adapter6);
        age.setAdapter(adapter7);

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
                gend = gen.get(i);
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
                cate = cat.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                reli = rel.get(i);

                if (reli.equals("Others")) {
                    rel_bool = true;

                    editTxtRelg.setVisibility(View.VISIBLE);
                } else {
                    rel_bool = false;
                    editTxtRelg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        educational.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                educ = edu.get(i);

                if (educ.equals("Others")) {
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
                mari = mar.get(i);

                if (mari.equals("Single")) {
                    child.setVisibility(View.GONE);

                    chil = "0";
                    belo = "0";
                    sixt = "0";
                    fift = "0";
                    goin = "0";

                } else {
                    child.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chil = chi.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        below6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                belo = chi.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sixto14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sixt = chi.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fifteento18.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fift = chi.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goingtoschool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                goin = chi.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prf = prof.get(i);
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

                                if (ag2 < 18 )
                                {
                                    Toast.makeText(getContext(), "You are not eligible to register in this app", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .display();

                /*final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dob_popup);
                dialog.setCancelable(true);
                dialog.show();

                Button submit = dialog.findViewById(R.id.button11);
                final DatePicker dp = dialog.findViewById(R.id.view14);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String dd = dp.getDayOfMonth() + "-" + (dp.getMonth() + 1) + "-" + dp.getYear();

                        Log.d("dddd", dd);

                        dob.setText(dd);

                        dialog.dismiss();

                    }
                });
*/
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

                String pp;
                String ps;
                String pd;
                String pa;
                String pst;

                if (che) {
                    pp = cp;
                    ps = cs;
                    pd = cd;
                    pa = ca;
                    pst = cst;
                } else {
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
                    if (d.length() > 0) {
                        if (gend.length() > 0) {
                            if (cst.length() > 0) {
                                if (ca.length() > 0) {
                                    if (cd.length() > 0) {
                                        if (cs.length() > 0) {
                                            if (cp.length() == 0 || cp.length() > 5) {
                                                if (pst.length() > 0) {
                                                    if (pa.length() > 0) {
                                                        if (pd.length() > 0) {
                                                            if (ps.length() > 0) {
                                                                if (pp.length() == 0 || pp.length() > 5) {


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
                                                                            SharePreferenceUtils.getInstance().getString("user"),
                                                                            n,
                                                                            prf,
                                                                            idno,
                                                                            lat1,
                                                                            lng1,
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
                                                                            ag,
                                                                            body
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
                                                                    Toast.makeText(getContext(), "Invalid permanent PIN", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getContext(), "Invalid permanent state", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getContext(), "Invalid permanent district", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Invalid permanent area", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Invalid permanent street", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                Toast.makeText(getContext(), "Invalid current PIN", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Invalid current state", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Invalid current district", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Invalid current area", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Invalid current street", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Invalid gender", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Invalid D.O.B.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }

        });


        setPrevious();

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = String.format(Locale.ENGLISH, "geo:"+lat +","+lng);
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

                Log.d("LAT",uri);
                Log.d("LNG",lng);

            }
        });

        return view;

    }




    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
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

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Log.d("DDD", id);

        Call<WorkerByIdListBean> call = cr.getWorkerById1(id);

        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(Call<WorkerByIdListBean> call, Response<WorkerByIdListBean> response) {

                List<WorkerByIdData> item = response.body().getData();

                lat = item.get(0).getLat();
                lng = item.get(0).getLng();

                name.setText(item.get(0).getName());
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.get(0).getPhoto(), image, options);
                editTxtProof.setText(item.get(0).getIdNumber());
                dob.setText(item.get(0).getDob());
                cpin.setText(item.get(0).getCpin());
                cstreet.setText(item.get(0).getCstreet());
                carea.setText(item.get(0).getCarea());
                cdistrict.setText(item.get(0).getCdistrict());
                cstate.setText(item.get(0).getCstate());
                ppin.setText(item.get(0).getPpin());
                pstreet.setText(item.get(0).getPstreet());
                parea.setText(item.get(0).getParea());
                pdistrict.setText(item.get(0).getPdistrict());
                pstate.setText(item.get(0).getPstate());

                ag2 = getAge(item.get(0).getDob());


                int cp12 = 0;
                for (int i = 0; i < agg.size(); i++) {
                    if (item.get(0).getAge().equals(agg.get(i))) {
                        cp12 = i;
                    }
                }
                age.setSelection(cp12);

                int cp = 0;
                for (int i = 0; i < prof.size(); i++) {
                    if (item.get(0).getIdProof().equals(prof.get(i))) {
                        cp = i;
                    }
                }
                proof.setSelection(cp);

                int gd = 0;
                for (int i = 0; i < gen.size(); i++) {
                    if (item.get(0).getGender().equals(gen.get(i))) {
                        gd = i;
                    }
                }
                gender.setSelection(gd);

                int ct = 0;
                for (int i = 0; i < cat.size(); i++) {
                    if (item.get(0).getCategory().equals(cat.get(i))) {
                        ct = i;
                    }
                }
                category.setSelection(ct);

                int re = 0;
                for (int i = 0; i < rel.size(); i++) {
                    if (item.get(0).getReligion().equals(rel.get(i))) {
                        re = i;
                        editTxtRelg.setText("");
                        break;
                    } else {
                        editTxtRelg.setVisibility(View.VISIBLE);
                        editTxtRelg.setText(item.get(0).getReligion());
                        re = 4;
                    }
                }
                religion.setSelection(re);

                int ed = 0;
                for (int i = 0; i < edu.size(); i++) {
                    if (item.get(0).getEducational().equals(edu.get(i))) {
                        ed = i;
                        editTxtedu.setText("");
                        break;
                    } else {
                        editTxtedu.setVisibility(View.VISIBLE);
                        editTxtedu.setText(item.get(0).getEducational());
                        ed = 8;
                    }
                }
                educational.setSelection(ed);

                int mt = 0;
                for (int j = 0; j < mar.size(); j++) {

                    if (item.get(0).getMarital().equals(mar.get(j))) {
                        mt = j;
                        child.setVisibility(View.GONE);
                        break;

                    } else {

                        child.setVisibility(View.VISIBLE);

                        int chp = 0;
                        for (int i = 0; i < chi.size(); i++) {
                            if (item.get(0).getChildren().equals(chi.get(i))) {
                                chp = i;
                            }
                        }
                        children.setSelection(chp);


                        int bp = 0;
                        for (int i = 0; i < chi.size(); i++) {
                            if (item.get(0).getBelowsix().equals(chi.get(i))) {
                                bp = i;
                            }
                        }
                        below6.setSelection(bp);

                        int sp = 0;
                        for (int i = 0; i < chi.size(); i++) {
                            if (item.get(0).getSixtofourteen().equals(chi.get(i))) {
                                sp = i;
                            }
                        }
                        sixto14.setSelection(sp);

                        int fp = 0;
                        for (int i = 0; i < chi.size(); i++) {
                            if (item.get(0).getFifteentoeighteen().equals(chi.get(i))) {
                                fp = i;
                            }
                        }
                        fifteento18.setSelection(fp);

                        int gop = 0;
                        for (int i = 0; i < chi.size(); i++) {
                            if (item.get(0).getGoingtoschool().equals(chi.get(i))) {
                                gop = i;
                            }
                        }
                        goingtoschool.setSelection(gop);


                    }
                }
                marital.setSelection(mt);


                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<WorkerByIdListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return age;
    }

}