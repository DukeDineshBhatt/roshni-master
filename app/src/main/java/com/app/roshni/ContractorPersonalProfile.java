package com.app.roshni;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.roshni.contractorPOJO.Data;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.verifyPOJO.verifyBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
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

public class ContractorPersonalProfile extends Fragment {

    private Spinner gender, establishment, experience, work, availability, firm, proof, firmtype;

    private String gend, esta, expe, wtyp, avai, frmy, prf, frmytyp;

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about;

    TagsEditText location;
    TextView txtStatus;

    private CircleImageView image;


    private List<String> gen, est, exp, wty, ava, frm, frmtyp, prof;

    private Uri uri;
    private File f1;

    String user_id;
    private boolean che = false;

    private LinearLayout permanent;

    private ProgressBar progress;
    private CustomViewPager pager;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contractor_personal_profile, container, false);

        gen = new ArrayList<>();
        est = new ArrayList<>();
        exp = new ArrayList<>();
        wty = new ArrayList<>();
        ava = new ArrayList<>();
        frm = new ArrayList<>();
        prof = new ArrayList<>();
        frmtyp = new ArrayList<>();

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
        txtStatus = view.findViewById(R.id.textViewStatus);

        user_id = SharePreferenceUtils.getInstance().getString("user_id");

        gen.add("Select one --- ");
        gen.add("Male");
        gen.add("Female");

        prof.add("Select one --- ");
        prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");

        est.add("Select one --- ");
        est.add("1970");
        est.add("1971");
        est.add("1972");
        est.add("1973");
        est.add("1974");
        est.add("1975");
        est.add("1976");
        est.add("1977");
        est.add("1978");
        est.add("1979");
        est.add("1980");
        est.add("1981");
        est.add("1982");
        est.add("1983");
        est.add("1984");
        est.add("1985");
        est.add("1986");
        est.add("1987");
        est.add("1988");
        est.add("1989");
        est.add("1990");
        est.add("1991");
        est.add("1992");
        est.add("1993");
        est.add("1994");
        est.add("1995");
        est.add("1996");
        est.add("1997");
        est.add("1998");
        est.add("1999");
        est.add("2000");
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
        est.add("2025");

        exp.add("Select one --- ");
        exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");

        wty.add("Select one --- ");
        wty.add("Adda Work");
        wty.add("Fashion Jewelry");
        wty.add("Zari Work");
        wty.add("Embroidery");
        wty.add("Tassel Work");

        ava.add("Select one --- ");
        ava.add("Available");
        ava.add("Within a Month");
        ava.add("Within Two Months");

        frm.add("Select one --- ");
        frm.add("Sole-properietor");
        frm.add("Partnership");
        frm.add("Pvt.Ltd. Company");
        frm.add("Ltd. Company");
        frm.add("LLC");
        frm.add("LLP");
        frm.add("Co-operative");
        frm.add("Trust");

        frmtyp.add("Select one --- ");
        frmtyp.add("SSI");
        frmtyp.add("MSME");
        frmtyp.add("Cottage Industry");

        permanent = view.findViewById(R.id.permanent);

        image = view.findViewById(R.id.imageView3);

        gender = view.findViewById(R.id.gender);
        establishment = view.findViewById(R.id.establishment);
        experience = view.findViewById(R.id.experience);
        work = view.findViewById(R.id.work);
        availability = view.findViewById(R.id.availability);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.spinner_model, gen);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, est);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_model, exp);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_model, wty);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_model, ava);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_model, frm);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, prof);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, frmtyp);


        gender.setEnabled(false);
        establishment.setEnabled(false);
        experience.setEnabled(false);
        work.setEnabled(false);
        availability.setEnabled(false);
        firm.setEnabled(false);
        proof.setEnabled(false);
        firmtype.setEnabled(false);


        gender.setAdapter(adapter);
        establishment.setAdapter(adapter1);
        experience.setAdapter(adapter2);
        work.setAdapter(adapter3);
        availability.setAdapter(adapter4);
        firm.setAdapter(adapter5);
        proof.setAdapter(adapter6);
        firmtype.setAdapter(adapter7);



        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    gend = gen.get(i);
                } else {
                    gend = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {

                    prf = prof.get(i);

                } else {

                    prf = "";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    frmy = frm.get(i);
                } else {
                    frmy = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firmtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    frmytyp = frmtyp.get(i);
                } else {
                    frmytyp = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        establishment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    esta = est.get(i);
                } else {
                    esta = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    expe = exp.get(i);
                } else {
                    expe = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        work.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    wtyp = wty.get(i);
                } else {
                    wtyp = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        availability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    avai = ava.get(i);
                } else {
                    avai = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        setPrevious();

        return view;
    }


    private void setPrevious() {

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(user_id);

        //Log.d("jid" , id);

        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                Data item = response.body().getData();


                if (item.getStatus().equals("pending")) {

                    txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");

                } else if (item.getStatus().equals("rejected")) {

                    txtStatus.setText("YOUR PROFILE IS Rejected");
                } else {

                    txtStatus.setText(item.getStatus());

                }

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto() , image , options);

                //mw = item.getWorkersMale();
                //fw = item.getWorkersFemale();

                name.setText(item.getName());
                editTxtProof.setText(item.getId_number());
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

                reg_no.setText(item.getRegistration_no());

                //experience.setText(item.getExperience());
                //availability.setText(item.getAvailability());
                dob.setText(item.getDob());
                home_based.setText(item.getHomeUnits());
                //home_location.setText(item.getHomeLocation());
                male.setText(item.getWorkersMale());
                female.setText(item.getWorkersFemale());
                //type.setText(item.getWorkType());
                employer.setText(item.getEmployer());
                about.setText(item.getAbout());


                int gp = 0;
                for (int i = 0; i < gen.size(); i++) {
                    if (item.getGender().equals(gen.get(i))) {
                        gp = i;
                    }
                }
                gender.setSelection(gp);

                int fm = 0;
                for (int i = 0; i < frm.size(); i++) {
                    if (item.getFirm_type().equals(frm.get(i))) {
                        fm = i;
                    }
                }
                firm.setSelection(fm);

                int pf = 0;
                for (int i = 0; i < prof.size(); i++) {
                    if (item.getId_proof().equals(prof.get(i))) {
                        pf = i;
                    }
                }
                proof.setSelection(pf);

                int pft = 0;
                for (int i = 0; i < frmtyp.size(); i++) {
                    if (item.getFirm_registration_type().equals(frmtyp.get(i))) {
                        pft = i;
                    }
                }
                firmtype.setSelection(pft);

                int rp = 0;
                for (int i = 0; i < exp.size(); i++) {
                    if (item.getExperience().equals(exp.get(i))) {
                        rp = i;
                    }
                }
                experience.setSelection(rp);

                int ap = 0;
                for (int i = 0; i < ava.size(); i++) {
                    if (item.getAvailability().equals(ava.get(i))) {
                        ap = i;
                    }
                }
                availability.setSelection(ap);


                int wp = 0;
                for (int i = 0; i < wty.size(); i++) {
                    if (item.getWorkType().equals(wty.get(i))) {
                        wp = i;
                    }
                }
                work.setSelection(wp);

               String ppp = item.getHomeLocation();

                location.setTags(ppp.split(","));

                /*int ep = 0;
                for (int i = 0; i < est.size(); i++) {
                    if (item.getEstablishment_year().equals(est.get(i))) {
                        ep = i;
                    }
                }
                establishment.setSelection(ep);


*/

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<contractorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }


}
