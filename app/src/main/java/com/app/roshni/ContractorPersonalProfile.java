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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.contractorPOJO.Data;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.sectorPOJO.sectorBean;
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

    private Spinner gender, establishment, availability, firm, proof, firmtype , outsource , govtinsurance , child_labour , supply_chain;

    private String gend, esta, expe, wtyp, avai, frmy, prf, frmytyp , sect , govt = "";

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about , work , looms , phone , sector , experience , otherwork , othergovt;

    TagsEditText location;
    TextView txtStatus;

    private CircleImageView image;

    CheckBox check;
    private List<String> gen, gen1, est, exp, exp1, wty, wty1, ava, ava1, frm, frm1, frmtyp, frmtyp1, prof, prof1, sec, sec1 , out , out1, gov, gov1 , chi , chi1;


    String user_id;
    private boolean che = false;

    private LinearLayout permanent , home_layout;

    private ProgressBar progress;
    private CustomViewPager pager;

    EditText migrant , local;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    EditText email ,non_school , school , without_bank;

    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contractor_personal_profile, container, false);

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
        gov = new ArrayList<>();
        gov1 = new ArrayList<>();
        chi = new ArrayList<>();
        chi1 = new ArrayList<>();

        child_labour = view.findViewById(R.id.child_labour);
        supply_chain = view.findViewById(R.id.supply_chain);
        swipe = view.findViewById(R.id.swipe);
        email = view.findViewById(R.id.email);
        home_layout = view.findViewById(R.id.home_layout);
        othergovt = view.findViewById(R.id.othergovt);
        govtinsurance = view.findViewById(R.id.govtinsurance);
        otherwork = view.findViewById(R.id.otherwork);
        non_school = view.findViewById(R.id.non_school);
        school = view.findViewById(R.id.school);
        without_bank = view.findViewById(R.id.without_bank);
        phone = view.findViewById(R.id.phone);
        migrant = view.findViewById(R.id.migrant);
        local = view.findViewById(R.id.local);
        outsource = view.findViewById(R.id.outsource);
        check = view.findViewById(R.id.check);
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
        txtStatus = view.findViewById(R.id.textViewStatus);
        looms = view.findViewById(R.id.looms);

        user_id = SharePreferenceUtils.getInstance().getString("user_id");


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

        image = view.findViewById(R.id.imageView3);

        gender = view.findViewById(R.id.gender);
        establishment = view.findViewById(R.id.establishment);
        experience = view.findViewById(R.id.experience);
        work = view.findViewById(R.id.work);
        availability = view.findViewById(R.id.availability);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, est);



        gender.setEnabled(false);
        establishment.setEnabled(false);

        availability.setEnabled(false);
        firm.setEnabled(false);
        proof.setEnabled(false);
        firmtype.setEnabled(false);
        outsource.setEnabled(false);
        govtinsurance.setEnabled(false);
        child_labour.setEnabled(false);
        supply_chain.setEnabled(false);


        establishment.setAdapter(adapter1);


        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setPrevious();

                swipe.setRefreshing(false);

            }
        });


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    permanent.setVisibility(View.GONE);
                } else {
                    permanent.setVisibility(View.VISIBLE);
                }

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

        outsource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String outs = out1.get(i);

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

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(user_id, SharePreferenceUtils.getInstance().getString("lang"));

        //Log.d("jid" , id);

        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                final Data item = response.body().getData();


                if (item.getStatus().equals("pending")) {

                    txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                    txtStatus.setVisibility(View.VISIBLE);
                } else if (item.getStatus().equals("rejected")) {
                    txtStatus.setText(item.getRejectReason());
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else if (item.getStatus().equals("verified")) {
                    txtStatus.setText("YOUR PROFILE IS PENDING FOR APPROVAL");
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else if (item.getStatus().equals("modifications")) {
                    txtStatus.setText(item.getRejectReason());
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else {
                    txtStatus.setVisibility(View.GONE);
                }


                if (item.getSame().equals("1"))
                {
                    check.setChecked(true);
                }
                else
                {
                    check.setChecked(false);
                }

                sector.setText(item.getSector2());

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPhoto() , image , options);

                //mw = item.getWorkersMale();
                //fw = item.getWorkersFemale();

                name.setText(item.getName());
                editTxtProof.setText(item.getIdNumber());
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
                business.setText(item.getBusinessName());

                reg_no.setText(item.getRegistrationNo());
                looms.setText(item.getTools());

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
                work.setText(item.getWorkType());
                phone.setText(item.getPhone());
                migrant.setText(item.getMigrant());
                local.setText(item.getLocal());
                non_school.setText(item.getNonSchool());
                school.setText(item.getSchool());
                without_bank.setText(item.getWithoutBank());
                email.setText(item.getEmail());
                experience.setText(item.getExperience());
                otherwork.setText(item.getOtherwork());

                if (item.getOtherwork().length() > 0)
                {
                    otherwork.setVisibility(View.VISIBLE);
                }
                else
                {
                    otherwork.setVisibility(View.GONE);
                }


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

                            ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
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


                final Call<sectorBean> call81 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

                call81.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            gov.clear();
                            gov1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                gov.add(response.body().getData().get(i).getTitle());
                                gov1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                final Call<sectorBean> call82 = cr.getChild(SharePreferenceUtils.getInstance().getString("lang"));

                call82.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            chi.clear();
                            chi1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                chi.add(response.body().getData().get(i).getTitle());
                                chi1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


               String ppp = item.getHomeLocation();

                location.setTags(ppp.split(","));

                int chp = 0;
                for (int i = 0; i < est.size(); i++) {
                    if (item.getEstablishmentYear().equals(est.get(i))) {
                        chp = i;
                    }
                }
                establishment.setSelection(chp);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<contractorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }


}
