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

import com.app.roshni.brandDetailsPOJO.Data;
import com.app.roshni.brandDetailsPOJO.brandDetailsBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verifyPOJO.verifyBean;
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

public class brand2 extends Fragment {

    private static final String TAG = "brand";
    private Spinner manufacturing, certification, firm, firmtype;

    private String manuf, certi, frmy, frmytyp, sect;

    private EditText name, regi, person, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, factory, workers, expiry, website, email, contact_details, phone, businessname, sector , otherwork;


    NachoTextView products, countries;

    private CircleImageView image;

    CheckBox check;

    private Button upload, submit;

    private List<String> man, cer, cer1, frm, frm1, frmtyp, frmtyp1, sec, sec1, mar;

    private CustomViewPager pager;

    private Uri uri;
    private File f1;

    private boolean che = false;

    private LinearLayout cert, permanent;

    private ProgressBar progress;

    TextView txtStatus;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient mPlacesClient;

    EditText processes, certification_number;
    Spinner market, outsourcing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_layout2, container, false);

        man = new ArrayList<>();
        cer = new ArrayList<>();
        cer1 = new ArrayList<>();
        frm = new ArrayList<>();
        frm1 = new ArrayList<>();
        frmtyp = new ArrayList<>();
        frmtyp1 = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        mar = new ArrayList<>();

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

        processes = view.findViewById(R.id.processes);
        otherwork = view.findViewById(R.id.otherwork);
        market = view.findViewById(R.id.market);
        certification_number = view.findViewById(R.id.certification_number);
        outsourcing = view.findViewById(R.id.outsourcing);
        phone = view.findViewById(R.id.phone);
        businessname = view.findViewById(R.id.businessname);
        name = view.findViewById(R.id.editText);
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
        txtStatus = view.findViewById(R.id.textViewStatus);
        progress = view.findViewById(R.id.progress);

        permanent = view.findViewById(R.id.permanent);
        cert = view.findViewById(R.id.cert);

        check = view.findViewById(R.id.check);

        image = view.findViewById(R.id.imageView3);

        upload = view.findViewById(R.id.button7);
        submit = view.findViewById(R.id.submit);


        manufacturing = view.findViewById(R.id.manufacturing);
        certification = view.findViewById(R.id.certification);

        upload.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);


        firm.setEnabled(false);
        firmtype.setEnabled(false);
        market.setEnabled(false);
        outsourcing.setEnabled(false);

        //person.setEnabled(false);
        //contact_details.setEnabled(false);
        //cstreet.setEnabled(false);
        //carea.setEnabled(false);
        //cdistrict.setEnabled(false);
        //cstate.setEnabled(false);
        //cpin.setEnabled(false);
        //pstreet.setEnabled(false);
        //parea.setEnabled(false);
        //pdistrict.setEnabled(false);
        //pstate.setEnabled(false);
        //ppin.setEnabled(false);
        manufacturing.setEnabled(false);
        //factory.setEnabled(false);
        //products.setEnabled(false);
        //countries.setEnabled(false);
        //workers.setEnabled(false);
        certification.setEnabled(false);
        check.setEnabled(false);
        //expiry.setEnabled(false);
        //website.setEnabled(false);
        //email.setEnabled(false);


        products.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        products.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        products.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);


        countries.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        countries.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        countries.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);

        cstate.setOnClickListener(new View.OnClickListener() {
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
        });

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

        pstate.setOnClickListener(new View.OnClickListener() {
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
        });

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

        /*cer.add(getString(R.string.yes1));
        cer.add(getString(R.string.no));
*/

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

        /*frm.add(getString(R.string.sole_properietor));
        frm.add(getString(R.string.partnership));
        frm.add(getString(R.string.pvt_ltd));
        frm.add(getString(R.string.ltd_company));
        frm.add(getString(R.string.llc));
        frm.add(getString(R.string.llp));
        frm.add(getString(R.string.cooperative));
        frm.add(getString(R.string.trust));*/

        /*frmtyp.add(getString(R.string.none));
        frmtyp.add(getString(R.string.ssi));
        frmtyp.add(getString(R.string.msme));
        frmtyp.add(getString(R.string.cottage_industry));*/



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

        firm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                frmy = frm.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firmtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                frmytyp = frmtyp.get(i);

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
            f1 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri));

            Log.d("bitmap", String.valueOf(bmp));

            image.setImageBitmap(bmp);

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
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
                    String cii = addresses.get(0).getLocality();

                    cdistrict.setText(cii);

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
                    String cii = addresses.get(0).getSubLocality();

                    pdistrict.setText(cii);

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




        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        final Call<brandDetailsBean> call = cr.getBrandById(SharePreferenceUtils.getInstance().getString("user_id"));

        call.enqueue(new Callback<brandDetailsBean>() {
            @Override
            public void onResponse(Call<brandDetailsBean> call, Response<brandDetailsBean> response) {

                if (response.body().getStatus().equals("1")) {

                    final Data item = response.body().getData();

                    if (item.getStatus().equals("pending")) {

                        txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                        txtStatus.setVisibility(View.VISIBLE);
                    } else if (item.getStatus().equals("rejected")) {
                        txtStatus.setText(item.getRejectReason());
                        txtStatus.setVisibility(View.VISIBLE);
                    }
                    else if (item.getStatus().equals("submitted")) {
                        txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                        txtStatus.setVisibility(View.VISIBLE);
                    }
                    else if (item.getStatus().equals("modifications")) {
                        txtStatus.setText(item.getRejectReason());
                        txtStatus.setVisibility(View.VISIBLE);
                    }
                    else {
                        txtStatus.setVisibility(View.GONE);
                    }

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
                    phone.setText(item.getPhone());
                    businessname.setText(item.getBusinessName());
                    certification_number.setText(item.getCertificationNumber());
                    processes.setText(item.getProcesses());
                    sector.setText(item.getSector2());
                    otherwork.setText(item.getOtherwork());
                    String ppp = item.getProducts();
                    String ccc = item.getCountry();

                    if (item.getSame().equals("1"))
                    {
                        check.setChecked(true);
                    }
                    else
                    {
                        check.setChecked(false);
                    }

                    if (item.getOtherwork().length() > 0)
                    {
                        otherwork.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        otherwork.setVisibility(View.GONE);
                    }


                    products.setText(Arrays.asList(ppp.split(",")));
                    countries.setText(Arrays.asList(ccc.split(",")));

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getLogo() , image , options);




                    final Call<sectorBean> call3 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

                    call3.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                            if (response.body().getStatus().equals("1")) {

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

                                int cp21 = 0;
                                for (int i = 0; i < cer1.size(); i++) {
                                    if (item.getOutsourcing().equals(cer1.get(i))) {
                                        cp21 = i;
                                    }
                                }
                                outsourcing.setSelection(cp21);


                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<sectorBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    certification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            certi = cer1.get(i);

                            if (certi.equals("1")) {
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


                    final Call<sectorBean> call4 = cr.getFirmTypes(SharePreferenceUtils.getInstance().getString("lang"));

                    call4.enqueue(new Callback<sectorBean>() {
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

                    final Call<sectorBean> call5 = cr.getFirmRegistyrationTypes(SharePreferenceUtils.getInstance().getString("lang"));

                    call5.enqueue(new Callback<sectorBean>() {
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
            public void onFailure(Call<brandDetailsBean> call, Throwable t) {
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

}
