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
import android.widget.DatePicker;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workersjoint.app.SkillsPOJO.Datum;
import com.workersjoint.app.SkillsPOJO.skillsBean;
import com.workersjoint.app.brandDetailsPOJO.brandDetailsBean;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verifyPOJO.Data;
import com.workersjoint.app.verifyPOJO.verifyBean;
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
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;

public class brand3 extends Fragment {

    private static final String TAG = "brand";
    private Spinner manufacturing, certification, firm, firmtype , child_labour , supply_chain;

    private String manuf = "", certi = "", frmy = "", frmytyp = "", sect = "" , chla = "", supl = "";

    private EditText name, regi, person, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, factory, workers, expiry, website, email, contact_details, businessname, sector;


    NachoTextView products, countries;

    private CircleImageView image;

    CheckBox check;

    TextView getDirection;

    private List<String> man, cer, cer1, frm, frm1, frmtyp, frmtyp1, sec1, wty1, mar , chi , chi1;

    private CustomViewPager pager;

    private Uri uri;
    private File f1;

    private boolean che = false;

    private LinearLayout cert, permanent;

    private ProgressBar progress;

    String lat1 = "", lng1 = "";
    String lat = "", lng = "";

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    String same = "0";

    List<com.workersjoint.app.sectorPOJO.Data> sec;
    List<Datum> wty;

    String wtyp = "", mark = "", outs = "";

    EditText processes;
    Spinner market, outsourcing;
    EditText certification_number;

    EditText otherwork;

    EditText phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_layout3, container, false);

        man = new ArrayList<>();
        cer = new ArrayList<>();
        cer1 = new ArrayList<>();
        frm = new ArrayList<>();
        frm1 = new ArrayList<>();
        frmtyp = new ArrayList<>();
        frmtyp1 = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        wty = new ArrayList<>();
        wty1 = new ArrayList<>();
        mar = new ArrayList<>();
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
        otherwork = view.findViewById(R.id.otherwork);
        name = view.findViewById(R.id.editText);
        processes = view.findViewById(R.id.processes);
        market = view.findViewById(R.id.market);
        certification_number = view.findViewById(R.id.certification_number);
        outsourcing = view.findViewById(R.id.outsourcing);
        businessname = view.findViewById(R.id.businessname);
        contact_details = view.findViewById(R.id.contact_details);
        regi = view.findViewById(R.id.editText2);
        person = view.findViewById(R.id.person);
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
        factory = view.findViewById(R.id.factory);
        products = view.findViewById(R.id.products);
        countries = view.findViewById(R.id.countries);
        workers = view.findViewById(R.id.workers);
        expiry = view.findViewById(R.id.expiry);
        website = view.findViewById(R.id.website);
        email = view.findViewById(R.id.email);
        firm = view.findViewById(R.id.firm);
        firmtype = view.findViewById(R.id.firmtype);
        sector = view.findViewById(R.id.sector);
        getDirection = view.findViewById(R.id.getdirection);
        progress = view.findViewById(R.id.progress);

        permanent = view.findViewById(R.id.permanent);
        cert = view.findViewById(R.id.cert);

        check = view.findViewById(R.id.check);

        image = view.findViewById(R.id.imageView3);

        Button upload = view.findViewById(R.id.button7);
        Button submit = view.findViewById(R.id.submit);


        manufacturing = view.findViewById(R.id.manufacturing);
        certification = view.findViewById(R.id.certification);


        products.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        //products.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        products.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);


        countries.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        //countries.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        countries.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);

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


        man.add("--- Select ---");
        man.add("0");
        man.add("1");
        man.add("2");
        man.add("3");
        man.add("4");
        man.add("5");
        man.add("6");
        man.add("7");
        man.add("8");
        man.add("9");
        man.add("10");
        man.add("11");
        man.add("12");

        mar.add("--- Select ---");
        mar.add("Domestic");
        mar.add("Export");
        mar.add("Both");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, man);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, mar);

        manufacturing.setAdapter(adapter1);
        market.setAdapter(adapter2);


        manufacturing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                manuf = man.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        market.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mark = mar.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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


        expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
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

                        expiry.setText(dd);

                        dialog.dismiss();

                    }
                });

            }
        });

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


        processes.setOnClickListener(new View.OnClickListener() {
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

                        processes.setText(TextUtils.join(", ", adapter.getWorks()));
                        Log.d("sectors", TextUtils.join(",", wty1));


                    }
                });

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = name.getText().toString();
                String r = regi.getText().toString();
                String p = person.getText().toString();

                String cp = cpin.getText().toString();
                String cs = cstate.getText().toString();
                String cd = cdistrict.getText().toString();
                String ca = carea.getText().toString();
                String cst = cstreet.getText().toString();
                String cde = contact_details.getText().toString();

                String f = factory.getText().toString();

                List<String> pro = products.getChipAndTokenValues();


                String pr = TextUtils.join(",", pro);

                List<String> cou = countries.getChipAndTokenValues();


                String co = TextUtils.join(",", cou);


                String w = workers.getText().toString();
                String e = expiry.getText().toString();
                String we = website.getText().toString();
                String em = email.getText().toString();
                String bn = businessname.getText().toString();
                String cn = certification_number.getText().toString();

                String ot = otherwork.getText().toString();

                wtyp = TextUtils.join(",", wty1);
                sect = TextUtils.join(",", sec1);

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
                    same = "1";
                    pp = ppin.getText().toString();
                    ps = pstate.getText().toString();
                    pd = pdistrict.getText().toString();
                    pa = parea.getText().toString();
                    pst = pstreet.getText().toString();
                }


                if (n.length() > 0) {
                    if (sect.length() > 0) {
                        if (p.length() > 0) {

                            if (cd.length() > 0) {
                                if (cs.length() > 0) {
                                    if (cp.length() == 0 || cp.length() == 6) {
                                        if (pd.length() > 0) {
                                            if (ps.length() > 0) {
                                                if (pp.length() == 0 || pp.length() == 6) {
                                                    if (pr.length() > 0) {
                                                        if (w.length() > 0) {


                                                            Log.d("asdasdasd", ot);


                                                            MultipartBody.Part body = null;

                                                            try {

                                                                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                                                body = MultipartBody.Part.createFormData("photo", f1.getName(), reqFile1);


                                                            } catch (Exception e1) {
                                                                e1.printStackTrace();
                                                            }

                                                            progress.setVisibility(View.VISIBLE);

                                                            Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                                                            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
                                                            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                                                            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
                                                            httpClient.addInterceptor(logging);

                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                    .baseUrl(b.baseurl)
                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                    .client(httpClient.build())
                                                                    .build();

                                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                            Call<verifyBean> call = cr.updateBrand2(
                                                                    SharePreferenceUtils.getInstance().getString("user"),
                                                                    n,
                                                                    frmy,
                                                                    frmytyp,
                                                                    r,
                                                                    lat1,
                                                                    lng1,
                                                                    sect,
                                                                    cde,
                                                                    p,
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
                                                                    manuf,
                                                                    f,
                                                                    pr,
                                                                    co,
                                                                    w,
                                                                    certi,
                                                                    e,
                                                                    we,
                                                                    em,
                                                                    same,
                                                                    bn,
                                                                    wtyp,
                                                                    otherwork.getText().toString(),
                                                                    mark,
                                                                    cn,
                                                                    outs,
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

                                                                        pager.setCurrentItem(1);


                                                                        Log.d("respo", response.body().getMessage());

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
                                                            Toast.makeText(getActivity(), "Invalid workers", Toast.LENGTH_SHORT).show();
                                                            workers.setError("");
                                                            workers.requestFocus();
                                                        }

                                                    } else {
                                                        Toast.makeText(getActivity(), "Invalid product types", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Invalid contact person name", Toast.LENGTH_SHORT).show();
                            person.setError("");
                            person.requestFocus();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid sector", Toast.LENGTH_SHORT).show();
                        sector.requestFocus();
                        sector.setError("");
                    }


                } else {
                    Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                    name.setError("");
                    name.requestFocus();
                }


            }
        });

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "geo:" + lat + "," + lng;
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

                Log.d("LAT", uri);
                Log.d("LNG", lng);

            }
        });

        setPrevious();

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


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        final Call<brandDetailsBean> call = cr.getBrandById(SharePreferenceUtils.getInstance().getString("user"));

        call.enqueue(new Callback<brandDetailsBean>() {
            @Override
            public void onResponse(@NotNull Call<brandDetailsBean> call, @NotNull Response<brandDetailsBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    final com.workersjoint.app.brandDetailsPOJO.Data item = response.body().getData();

                    lat = item.getLat();
                    lng = item.getLng();

                    name.setText(item.getName());
                    regi.setText(item.getRegistrationNumber());
                    person.setText(item.getContactPerson());
                    contact_details.setText(item.getContactDetails());
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
                    factory.setText(item.getFactoryOutlet());
                    workers.setText(item.getWorkers());
                    expiry.setText(item.getExpiry());
                    website.setText(item.getWebsite());
                    email.setText(item.getEmail());
                    certification_number.setText(item.getCertificationNumber());
                    otherwork.setText(item.getOtherwork());
                    businessname.setText(item.getBusinessName());

                    String ppp = item.getProducts();
                    String ccc = item.getCountry();


                    products.setText(Arrays.asList(ppp.split(",")));
                    countries.setText(Arrays.asList(ccc.split(",")));

                    final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                    final ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getLogo(), image, options);

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
                            loader.displayImage(item.getLogo() , img , options);

                        }
                    });


                    sec1 = Arrays.asList(item.getSector().split(","));
                    wty1 = Arrays.asList(item.getProcessesId().split(","));

                    sector.setText(item.getSector2());
                    processes.setText(item.getProcesses());

                    if (item.getOtherwork().length() > 0) {
                        otherwork.setVisibility(View.VISIBLE);
                    } else {
                        otherwork.setVisibility(View.GONE);
                    }

                    final Call<sectorBean> call3 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

                    call3.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                cer.clear();
                                cer1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    cer.add(response.body().getData().get(i).getTitle());
                                    cer1.add(response.body().getData().get(i).getId());

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                        R.layout.spinner_model, cer);

                                certification.setAdapter(adapter);

                                int cp2 = 0;
                                for (int i = 0; i < cer1.size(); i++) {
                                    if (item.getCertification().equals(cer1.get(i))) {
                                        cp2 = i;
                                    }
                                }
                                certification.setSelection(cp2);


                                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                        R.layout.spinner_model, cer);

                                outsourcing.setAdapter(adapter2);

                                int cp22 = 0;
                                for (int i = 0; i < cer1.size(); i++) {
                                    if (item.getOutsourcing().equals(cer1.get(i))) {
                                        cp22 = i;
                                    }
                                }
                                outsourcing.setSelection(cp22);


                            }

                            certification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    certi = cer1.get(i);

                                    if (certi.equals("2")) {
                                        cert.setVisibility(View.VISIBLE);
                                    } else {
                                        cert.setVisibility(View.GONE);
                                        expiry.setText("---");
                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                            outsourcing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    outs = cer1.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                    final Call<sectorBean> call4 = cr.getFirmTypes(SharePreferenceUtils.getInstance().getString("lang"));

                    call4.enqueue(new Callback<sectorBean>() {
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

                                firm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        frmy = frm1.get(i);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call5 = cr.getFirmRegistyrationTypes(SharePreferenceUtils.getInstance().getString("lang"));

                    call5.enqueue(new Callback<sectorBean>() {
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

                                firmtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        frmytyp = frmtyp1.get(i);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
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


                    int cp = 0;
                    for (int i = 0; i < man.size(); i++) {
                        if (item.getManufacturingUnits().equals(man.get(i))) {
                            cp = i;
                        }
                    }
                    manufacturing.setSelection(cp);


                    int cp2 = 0;
                    for (int i = 0; i < mar.size(); i++) {
                        if (item.getMarket().equals(mar.get(i))) {
                            cp2 = i;
                        }
                    }
                    market.setSelection(cp2);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<brandDetailsBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


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
