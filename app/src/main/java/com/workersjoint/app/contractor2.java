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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.workersjoint.app.contractorPOJO.contractorBean;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verifyPOJO.Data;
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

public class contractor2 extends Fragment {

    private Spinner gender, establishment, availability, firm, proof, firmtype, outsource , govtinsurance , child_labour , supply_chain;

    EditText sector, work, experience;

    private String gend = "", sect = "", esta = "", expe = "", wtyp = "", avai = "", frmy = "", prf = "", frmytyp = "", outs = "" , govt = "", chla = "", supl = "";

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about, looms, migrant, local;

    TagsEditText location;

    private CircleImageView image;
    private static final String TAG = "personal";

    CheckBox check;
    TextView getDirection;

    private List<String> gen;
    private List<String> gen1;
    private List<String> est;
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
    List<com.workersjoint.app.sectorPOJO.Data> sec;
    List<Datum> wty;

    private Uri uri;
    private File f1;

    private boolean che = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private LinearLayout permanent;
    private boolean mLocationPermissionGranted;

    private Location mLastKnownLocation;

    private ProgressBar progress;
    private CustomViewPager pager;
    String id;

    String lat = "", lng = "";
    String lat1 = "", lng1 = "";

    EditText otherwork, othergovt;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    EditText email, non_school, school, without_bank;

    int ag2 = 0;
    String same = "0";

    boolean gov_bool = false;

    LinearLayout home_layout;

    EditText phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contractor_layout2, container, false);

        gen = new ArrayList<>();
        gen1 = new ArrayList<>();
        est = new ArrayList<>();
        List<String> exp = new ArrayList<>();
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
            Log.e("Exception1: %s", Objects.requireNonNull(e.getMessage()));
        }

        phone = view.findViewById(R.id.phone);

        phone.setText(SharePreferenceUtils.getInstance().getString("phone"));


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
        name = view.findViewById(R.id.editText);
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
        sector = view.findViewById(R.id.sector);
        getDirection = view.findViewById(R.id.getdirection);
        govtinsurance = view.findViewById(R.id.govtinsurance);


        id = SharePreferenceUtils.getInstance().getString("user");

        Log.d("ID", id);



        est.add("--- Select ---");
        est.add("2020-2024");
        est.add("2015-2019");
        est.add("2010-2014");
        est.add("2005-2009");
        est.add("2000-2004");
        est.add("1975-1999");
        est.add("1950-1974");
        est.add("before 1950");


        permanent = view.findViewById(R.id.permanent);

        check = view.findViewById(R.id.check);

        image = view.findViewById(R.id.imageView3);

        Button upload = view.findViewById(R.id.button7);
        Button submit = view.findViewById(R.id.submit);

        gender = view.findViewById(R.id.gender);
        establishment = view.findViewById(R.id.establishment);
        experience = view.findViewById(R.id.experience);
        work = view.findViewById(R.id.work);
        availability = view.findViewById(R.id.availability);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, est);



        establishment.setAdapter(adapter1);

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

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "geo:"+lat +","+lng;
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

                Log.d("LAT",uri);
                Log.d("LNG",lng);

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

  //              Log.d("Sec", sect);
//                Log.d("work", wtyp);



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

                                                                            MultipartBody.Part body = null;

                                                                            try {

                                                                                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                                                                body = MultipartBody.Part.createFormData("photo", f1.getName(), reqFile1);


                                                                            } catch (Exception e1) {
                                                                                e1.printStackTrace();
                                                                            }

                                                                            progress.setVisibility(View.VISIBLE);

                                                                            Log.d("DDD", sect);

                                                                            Bean b1 = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                                    .baseUrl(b1.baseurl)
                                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                                    .build();

                                                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                                            Call<verifyBean> call = cr.update_contractor2(
                                                                                    SharePreferenceUtils.getInstance().getString("user"),
                                                                                    n,
                                                                                    prf,
                                                                                    p,
                                                                                    frmy,
                                                                                    frmytyp,
                                                                                    r,
                                                                                    lat1,
                                                                                    lng1,
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
                                                                                    outs,
                                                                                    mig,
                                                                                    loca,
                                                                                    with,
                                                                                    sch,
                                                                                    nons,
                                                                                    ema,
                                                                                    govt,
                                                                                    chla,
                                                                                    supl,
                                                                                    body
                                                                            );

                                                                            call.enqueue(new Callback<verifyBean>() {
                                                                                @Override
                                                                                public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                                                                    assert response.body() != null;
                                                                                    if (response.body().getStatus().equals("1")) {
                                                                                        Data item = response.body().getData();

                                                                                        Intent registrationComplete = new Intent("photo");

                                                                                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(registrationComplete);


                                                                                        pager.setCurrentItem(1);


                                                                                    }
                                                                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                                                                                    progress.setVisibility(View.GONE);

                                                                                }

                                                                                @Override
                                                                                public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                                                                    progress.setVisibility(View.GONE);
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



        setPrevious();

        return view;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()).getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
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

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(id, SharePreferenceUtils.getInstance().getString("lang"));


        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorBean> call, @NotNull Response<contractorBean> response) {

                final com.workersjoint.app.contractorPOJO.Data item = Objects.requireNonNull(response.body()).getData();

                final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                final ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto(), image, options);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        //      WindowManager.LayoutParams.MATCH_PARENT);
                        dialog.setContentView(R.layout.zoom_dialog);
                        dialog.setCancelable(true);
                        dialog.show();

                        ImageView img = dialog.findViewById(R.id.image);
                        loader.displayImage(item.getPhoto() , img , options);

                    }
                });


                lat = item.getLat();
                lng = item.getLng();

                name.setText(item.getName());
                editTxtProof.setText(item.getIdNumber());
                business.setText(item.getBusinessName());
                cstreet.setText(item.getCstreet());
                carea.setText(item.getCarea());
                cdistrict.setText(item.getCdistrict());
                cstate.setText(item.getCstate());
                cpin.setText(item.getCpin());
                pstreet.setText(item.getPstreet());
                parea.setText(item.getParea());
                pdistrict.setText(item.getPdistrict());
                pstate.setText(item.getPstate());
                ppin.setText(item.getPpin());
                male.setText(item.getWorkersMale());
                female.setText(item.getWorkersFemale());
                looms.setText(item.getTools());

                reg_no.setText(item.getRegistrationNo());

                dob.setText(item.getDob());
                home_based.setText(item.getHomeUnits());
                //home_location.setText(item.getHomeLocation());
                male.setText(item.getWorkersMale());
                female.setText(item.getWorkersFemale());
                //type.setText(item.getWorkType());
                employer.setText(item.getEmployer());
                about.setText(item.getAbout());
                migrant.setText(item.getMigrant());
                local.setText(item.getLocal());

                without_bank.setText(item.getWithoutBank());
                school.setText(item.getSchool());
                non_school.setText(item.getNonSchool());
                email.setText(item.getEmail());

                ag2 = getAge(item.getDob());

                experience.setText(item.getExperience());


                sec1 = Arrays.asList(item.getSector().split(","));
                wty1 = Arrays.asList(item.getWorkTypeId().split(","));

                sector.setText(item.getSector2());
                work.setText(item.getWorkType());

                if (item.getOtherwork().length() > 0) {
                    otherwork.setVisibility(View.VISIBLE);
                } else {
                    otherwork.setVisibility(View.GONE);
                }

                final Call<sectorBean> call81 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

                call81.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            gov.clear();
                            gov1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                gov.add(response.body().getData().get(i).getTitle());
                                gov1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, gov);


                            govtinsurance.setAdapter(adapter);

                            int sp = 0;
                            for (int i = 0; i < gov1.size(); i++) {

                                if (item.getGovt().equals(gov1.get(i))) {
                                    sp = i;
                                    othergovt.setText("");
                                    othergovt.setVisibility(View.GONE);
                                    break;
                                } else {
                                    othergovt.setVisibility(View.VISIBLE);
                                    othergovt.setText(item.getGovt());
                                    sp = gov.size() - 1;
                                }

                            }
                            govtinsurance.setSelection(sp);


                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });




                final Call<sectorBean> call3 = cr.getGender(SharePreferenceUtils.getInstance().getString("lang"));

                call3.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            gen.clear();
                            gen1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                gen.add(response.body().getData().get(i).getTitle());
                                gen1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, gen);


                            gender.setAdapter(adapter);

                            int cp2 = 0;
                            for (int i = 0; i < gen1.size(); i++) {
                                if (item.getGender().equals(gen1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            gender.setSelection(cp2);

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



                final Call<sectorBean> call4 = cr.getProof(SharePreferenceUtils.getInstance().getString("lang"));

                call4.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            prof.clear();
                            prof1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                prof.add(response.body().getData().get(i).getTitle());
                                prof1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter6 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, prof);


                            proof.setAdapter(adapter6);

                            int cp2 = 0;
                            for (int i = 0; i < prof1.size(); i++) {
                                if (item.getIdProof().equals(prof1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            proof.setSelection(cp2);

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



                final Call<sectorBean> call6 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

                call6.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            ava.clear();
                            ava1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ava.add(response.body().getData().get(i).getTitle());
                                ava1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter4 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, ava);


                            availability.setAdapter(adapter4);

                            int cp2 = 0;
                            for (int i = 0; i < ava1.size(); i++) {
                                if (item.getAvailability().equals(ava1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            availability.setSelection(cp2);

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


                final Call<sectorBean> call7 = cr.getFirmTypes(SharePreferenceUtils.getInstance().getString("lang"));

                call7.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            frm.clear();
                            frm1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                frm.add(response.body().getData().get(i).getTitle());
                                frm1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter5 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, frm);


                            firm.setAdapter(adapter5);


                            int cp2 = 0;
                            for (int i = 0; i < frm1.size(); i++) {
                                if (item.getFirmType().equals(frm1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            firm.setSelection(cp2);

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


                final Call<sectorBean> call8 = cr.getFirmRegistyrationTypes(SharePreferenceUtils.getInstance().getString("lang"));

                call8.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            frmtyp.clear();
                            frmtyp1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                frmtyp.add(response.body().getData().get(i).getTitle());
                                frmtyp1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter7 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, frmtyp);


                            firmtype.setAdapter(adapter7);


                            int cp2 = 0;
                            for (int i = 0; i < frmtyp1.size(); i++) {
                                if (item.getFirmRegistrationType().equals(frmtyp1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            firmtype.setSelection(cp2);

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

                final Call<sectorBean> call9 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

                call9.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            out.clear();
                            out1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                out.add(response.body().getData().get(i).getTitle());
                                out1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter7 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, out);


                            outsource.setAdapter(adapter7);


                            int cp2 = 0;
                            for (int i = 0; i < out1.size(); i++) {
                                if (item.getOutsource().equals(out1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            outsource.setSelection(cp2);

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

                final Call<sectorBean> call82 = cr.getChild(SharePreferenceUtils.getInstance().getString("lang"));

                call82.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            chi.clear();
                            chi1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                chi.add(response.body().getData().get(i).getTitle());
                                chi1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, chi);


                            supply_chain.setAdapter(adapter);
                            child_labour.setAdapter(adapter);

                            int cp2 = 0;
                            for (int i = 0; i < chi1.size(); i++) {
                                if (item.getChild_labour().equals(chi1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            child_labour.setSelection(cp2);

                            int cp21 = 0;
                            for (int i = 0; i < chi1.size(); i++) {
                                if (item.getSupply_chain().equals(chi1.get(i))) {
                                    cp21 = i;
                                }
                            }
                            supply_chain.setSelection(cp21);

                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                String ppp = item.getHomeLocation();

                location.setTags(ppp.split(","));

                int ep = 0;
                for (int i = 0; i < est.size(); i++) {
                    if (item.getEstablishmentYear().equals(est.get(i))) {
                        ep = i;
                    }
                }
                establishment.setSelection(ep);


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<contractorBean> call, @NotNull Throwable t) {
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

    class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.ViewHolder> {

        final Context context;
        List<com.workersjoint.app.sectorPOJO.Data> list;
        final List<String> ids = new ArrayList<>();
        final List<String> secs = new ArrayList<>();

        public SectorAdapter(Context context, List<com.workersjoint.app.sectorPOJO.Data> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.workersjoint.app.sectorPOJO.Data> list) {
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
            final com.workersjoint.app.sectorPOJO.Data item = list.get(position);

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
        List<com.workersjoint.app.SkillsPOJO.Datum> list;
        final List<String> ids = new ArrayList<>();
        final List<String> works = new ArrayList<>();

        public WorkAdapter(Context context, List<com.workersjoint.app.SkillsPOJO.Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.workersjoint.app.SkillsPOJO.Datum> list) {
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
