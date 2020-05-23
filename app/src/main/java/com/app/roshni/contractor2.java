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
import android.text.TextUtils;
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

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;
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
import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class contractor2 extends Fragment {

    private Spinner gender, establishment, experience, availability, firm, proof, firmtype, sector , outsource;

    MultiSelectSpinner work;

    private String gend, sect, esta, expe, wtyp = "", avai, frmy, prf, frmytyp , outs;

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about , looms , migrant , local;

    TagsEditText location;

    private CircleImageView image;
    private static final String TAG = "personal";

    CheckBox check;
    TextView getDirection;

    private Button upload, submit;

    private List<String> gen, gen1, est, exp, exp1, wty, wty1, ava, ava1, frm, frm1, frmtyp, frmtyp1, prof, prof1, sec, sec1 , out , out1;

    private Uri uri;
    private File f1;

    private boolean che = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private LinearLayout permanent;
    private boolean mLocationPermissionGranted;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient mPlacesClient;
    private Location mLastKnownLocation;

    private ProgressBar progress;
    private CustomViewPager pager;
    String id;

    String lat = "" , lng = "";
    String lat1 = "" , lng1 = "";

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    int ag2 = 0;
    String same = "0";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contractor_layout2, container, false);

        gen = new ArrayList<>();
        gen1 = new ArrayList<>();
        est = new ArrayList<>();
        exp = new ArrayList<>();
        exp1 = new ArrayList<>();
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


        id = SharePreferenceUtils.getInstance().getString("user");

        Log.d("ID", id);



        est.add("1");
        est.add("2");
        est.add("3");
        est.add("4");
        est.add("5");
        est.add("6");
        est.add("7");
        est.add("8");
        est.add("9");
        est.add("10");
        est.add("11");
        est.add("12");
        est.add("13");
        est.add("14");
        est.add("15");
        est.add("16");
        est.add("17");
        est.add("18");
        est.add("19");
        est.add("20");
        est.add("20+");


        permanent = view.findViewById(R.id.permanent);

        check = view.findViewById(R.id.check);

        image = view.findViewById(R.id.imageView3);

        upload = view.findViewById(R.id.button7);
        submit = view.findViewById(R.id.submit);

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

                String h = home_based.getText().toString();

                List<String> loc = location.getTags();
                String l = TextUtils.join(",", loc);

                String m = male.getText().toString();
                String f = female.getText().toString();
                String e = employer.getText().toString();

                String loo = looms.getText().toString();

                String mig = migrant.getText().toString();
                String loca = local.getText().toString();

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


  //              Log.d("Sec", sect);
//                Log.d("work", wtyp);


                if (n.length() > 0) {

                            if (cd.length() > 0) {
                                if (cs.length() > 0) {
                                    if (cp.length() == 0 || cp.length() == 6) {

                                        if (m.length() > 0) {

                                            if (f.length() > 0) {

                                                if (wtyp.length() > 0)
                                                {
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
                                                            avai,
                                                            e,
                                                            ab,
                                                            sect,
                                                            loo,
                                                            same,
                                                            outs,
                                                            mig,
                                                            loca,
                                                            body
                                                    );

                                                    call.enqueue(new Callback<verifyBean>() {
                                                        @Override
                                                        public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

                                                            assert response.body() != null;
                                                            if (response.body().getStatus().equals("1")) {
                                                                Data item = response.body().getData();

                                                                Intent registrationComplete = new Intent("photo");

                                                                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(registrationComplete);


                                                                pager.setCurrentItem(1);


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
                                                }
                                                else
                                                {
                                                    Toast.makeText(getContext(), "Please select type of work", Toast.LENGTH_SHORT).show();
                                                    work.requestFocus();
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
                    Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                    name.setError("");
                    name.requestFocus();
                }


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


    private void setPrevious() {

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(id, SharePreferenceUtils.getInstance().getString("lang"));


        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                final com.app.roshni.contractorPOJO.Data item = response.body().getData();

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto(), image, options);

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
                ag2 = getAge(item.getDob());




                final Call<sectorBean> call2 = cr.getSectors2(SharePreferenceUtils.getInstance().getString("lang"));

                call2.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            sec.clear();
                            sec1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                sec.add(response.body().getData().get(i).getTitle());
                                sec1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, sec);

                            sector.setAdapter(adapter);

                            int cp2 = 0;
                            for (int i = 0; i < sec1.size(); i++) {
                                if (item.getSector().equals(sec1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            sector.setSelection(cp2);

                        }

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        sect = sec1.get(i);

                        progress.setVisibility(View.VISIBLE);

                        Call<skillsBean> call2 = cr.getSkills1(sect, SharePreferenceUtils.getInstance().getString("lang"));
                        call2.enqueue(new Callback<skillsBean>() {
                            @Override
                            public void onResponse(Call<skillsBean> call, Response<skillsBean> response) {


                                if (response.body().getStatus().equals("1")) {

                                    wty.clear();
                                    wty1.clear();

                                    for (int i = 0; i < response.body().getData().size(); i++) {

                                        wty.add(response.body().getData().get(i).getTitle());
                                        wty1.add(response.body().getData().get(i).getId());

                                    }


                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_list_item_multiple_choice, wty);

                                    wtyp = item.getWorkTypeId();

                                    work.setListAdapter(adapter).setListener(new BaseMultiSelectSpinner.MultiSpinnerListener() {
                                        @Override
                                        public void onItemsSelected(boolean[] selected) {

                                            wtyp = "";
                                            List<String> sklist = new ArrayList<>();

                                            if (selected[0]) {

                                                for (int j = 0; j < selected.length; j++) {
                                                    work.selectItem(j, false);
                                                }
                                                work.selectItem(0, true);
                                                sklist.add(wty1.get(0));

                                            } else {
                                                for (int i = 0; i < selected.length; i++) {
                                                    if (selected[i]) {

                                                        sklist.add(wty1.get(i));
                                                    }
                                                }
                                            }


                                            wtyp = TextUtils.join(",", sklist);

                                            Log.d("wtype", wtyp);

                                        }
                                    });

                                    String[] dd = item.getWorkTypeId().split(",");

                                    int cp = 0;
                                    for (int i = 0; i < wty1.size(); i++) {

                                        for (int j = 0 ; j < dd.length ; j++)
                                        {

                                            if (dd[j].equals(wty1.get(i))) {
                                                cp = i;
                                                work.selectItem(i , true);
                                            }

                                        }


                                    }

                                }

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<skillsBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });



                final Call<sectorBean> call3 = cr.getGender(SharePreferenceUtils.getInstance().getString("lang"));

                call3.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        gend = gen1.get(i);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });



                final Call<sectorBean> call4 = cr.getProof(SharePreferenceUtils.getInstance().getString("lang"));

                call4.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            prof.clear();
                            prof1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                prof.add(response.body().getData().get(i).getTitle());
                                prof1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
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


                final Call<sectorBean> call5 = cr.getExperience(SharePreferenceUtils.getInstance().getString("lang"));

                call5.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            exp.clear();
                            exp1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                exp.add(response.body().getData().get(i).getTitle());
                                exp1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, exp);


                            experience.setAdapter(adapter2);

                            int cp2 = 0;
                            for (int i = 0; i < exp1.size(); i++) {
                                if (item.getExperience().equals(exp1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            experience.setSelection(cp2);

                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


                experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        expe = exp1.get(i);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                final Call<sectorBean> call6 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

                call6.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            ava.clear();
                            ava1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ava.add(response.body().getData().get(i).getTitle());
                                ava1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                availability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        avai = ava1.get(i);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                final Call<sectorBean> call7 = cr.getFirmTypes(SharePreferenceUtils.getInstance().getString("lang"));

                call7.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            frm.clear();
                            frm1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                frm.add(response.body().getData().get(i).getTitle());
                                frm1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
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
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            frmtyp.clear();
                            frmtyp1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                frmtyp.add(response.body().getData().get(i).getTitle());
                                frmtyp1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
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
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            out.clear();
                            out1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                out.add(response.body().getData().get(i).getTitle());
                                out1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                outsource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        outs = out1.get(i);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

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
            public void onFailure(Call<contractorBean> call, Throwable t) {
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
