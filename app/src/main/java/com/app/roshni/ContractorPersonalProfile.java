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

    private Spinner gender, establishment, experience, availability, firm, proof, firmtype , sector , outsource;

    private String gend, esta, expe, wtyp, avai, frmy, prf, frmytyp , sect;

    private EditText name, editTxtProof, reg_no, dob, business, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, home_based, employer, male, female, about , work , looms , phone;

    TagsEditText location;
    TextView txtStatus;

    private CircleImageView image;

    CheckBox check;
    private List<String> gen, gen1, est, exp, exp1, wty, wty1, ava, ava1, frm, frm1, frmtyp, frmtyp1, prof, prof1, sec, sec1 , out , out1;


    String user_id;
    private boolean che = false;

    private LinearLayout permanent;

    private ProgressBar progress;
    private CustomViewPager pager;

    EditText migrant , local;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }


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


        /*est.add("1970");
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
        est.add("2025");*/

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
        sector.setEnabled(false);
        establishment.setEnabled(false);
        experience.setEnabled(false);
        availability.setEnabled(false);
        firm.setEnabled(false);
        proof.setEnabled(false);
        firmtype.setEnabled(false);
        outsource.setEnabled(false);


        establishment.setAdapter(adapter1);



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


                if (item.getSame().equals("1"))
                {
                    check.setChecked(true);
                }
                else
                {
                    check.setChecked(false);
                }


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


               String ppp = item.getHomeLocation();

                location.setTags(ppp.split(","));


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<contractorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }


}
