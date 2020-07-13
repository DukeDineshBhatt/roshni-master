package com.app.roshni;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.contractorPOJO.Data;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ContractorPersonalProfile2 extends Fragment {

    private Spinner gender, establishment, experience, availability, firm, proof, firmtype,sector;

    private String gend, esta, expe, wtyp, avai, frmy, prf, frmytyp,sect;

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about , work;

    TagsEditText location;
    TextView txtStatus;

    private CircleImageView image;


    private List<String> gen, est, exp, wty, ava, frm, frmtyp, prof,sec,sec1,wty1;


    String user_id;
    private boolean che = false;

    private ProgressBar progress;

    void setData(CustomViewPager pager) {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contractor_personal_profile2, container, false);

        gen = new ArrayList<>();
        est = new ArrayList<>();
        exp = new ArrayList<>();
        wty = new ArrayList<>();
        ava = new ArrayList<>();
        frm = new ArrayList<>();
        prof = new ArrayList<>();
        frmtyp = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        wty1 = new ArrayList<>();

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

        user_id = SharePreferenceUtils.getInstance().getString("user");

        Log.d("IDD",user_id);
        gen.add("Male");
        gen.add("Female");

        prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");

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

        exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");

        ava.add("Available");
        ava.add("Within a Month");
        ava.add("Within Two Months");

        frm.add("Sole-properietor");
        frm.add("Partnership");
        frm.add("Pvt.Ltd. Company");
        frm.add("Ltd. Company");
        frm.add("LLC");
        frm.add("LLP");
        frm.add("Co-operative");
        frm.add("Trust");

        frmtyp.add("SSI");
        frmtyp.add("MSME");
        frmtyp.add("Cottage Industry");

        LinearLayout permanent = view.findViewById(R.id.permanent);

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

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, exp);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, wty);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, ava);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, frm);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, prof);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, frmtyp);


        gender.setEnabled(false);
        establishment.setEnabled(false);
        experience.setEnabled(false);
        availability.setEnabled(false);
        firm.setEnabled(false);
        proof.setEnabled(false);
        firmtype.setEnabled(false);
        sector.setEnabled(false);


        gender.setAdapter(adapter);
        establishment.setAdapter(adapter1);
        experience.setAdapter(adapter2);

        availability.setAdapter(adapter4);
        firm.setAdapter(adapter5);
        proof.setAdapter(adapter6);
        firmtype.setAdapter(adapter7);



        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    gend = gen.get(i);

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

        establishment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    esta = est.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    expe = exp.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        availability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    avai = ava.get(i);

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

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

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
            public void onResponse(@NotNull Call<contractorBean> call, @NotNull Response<contractorBean> response) {

                final Data item = Objects.requireNonNull(response.body()).getData();



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
                work.setText(item.getWorkType());

                reg_no.setText(item.getRegistrationNo());

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
                business.setText(item.getBusinessName());


                int gp = 0;
                for (int i = 0; i < gen.size(); i++) {
                    if (item.getGender().equals(gen.get(i))) {
                        gp = i;
                    }
                }
                gender.setSelection(gp);

                int fm = 0;
                for (int i = 0; i < frm.size(); i++) {
                    if (item.getFirmType().equals(frm.get(i))) {
                        fm = i;
                    }
                }
                firm.setSelection(fm);

                int pf = 0;
                for (int i = 0; i < prof.size(); i++) {
                    if (item.getIdProof().equals(prof.get(i))) {
                        pf = i;
                    }
                }
                proof.setSelection(pf);

                int pft = 0;
                for (int i = 0; i < frmtyp.size(); i++) {
                    if (item.getFirmRegistrationType().equals(frmtyp.get(i))) {
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


                final Call<sectorBean> call2 = cr.getSectors();

                call2.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                            sec.clear();
                            sec1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                sec.add(response.body().getData().get(i).getTitle());
                                sec1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
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
                    public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
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
                            public void onResponse(@NotNull Call<skillsBean> call, @NotNull Response<skillsBean> response) {


                                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                    wty.clear();
                                    wty1.clear();

                                    for (int i = 0; i < response.body().getData().size(); i++) {

                                        wty.add(response.body().getData().get(i).getTitle());
                                        wty1.add(response.body().getData().get(i).getId());

                                    }



                                }

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(@NotNull Call<skillsBean> call, @NotNull Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });


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
            public void onFailure(@NotNull Call<contractorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }


}
