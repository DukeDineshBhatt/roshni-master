package com.app.roshni;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class professional extends Fragment {

    Spinner sector, experience, employment, home, workers, location , bank , govtinsurance , availability;

    MultiSelectSpinner skills;

    String sect, skil, expe, empl, hhom, work, loom, loca , bann , govt , avai;

    List<String> sec, ski, exp , exp1, emp , emp1, hom , hom1, wor, loc , ban , ban1 , ava , ava1 , gov , gov1;
    List<String> sec1, ski1, loc1;

    ProgressBar progress;

    EditText employer , looms;

    Button submit , previous;
    EditText editTxtLoc;
    boolean loc_bool = false;

    LinearLayout yes;

    private CustomViewPager pager;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.professional_layout, container, false);

        sec = new ArrayList<>();
        ski = new ArrayList<>();
        exp = new ArrayList<>();
        exp1 = new ArrayList<>();
        emp = new ArrayList<>();
        emp1 = new ArrayList<>();
        hom = new ArrayList<>();
        hom1 = new ArrayList<>();
        wor = new ArrayList<>();
        loc = new ArrayList<>();
        ban = new ArrayList<>();
        ban1 = new ArrayList<>();
        ava = new ArrayList<>();
        ava1 = new ArrayList<>();
        gov = new ArrayList<>();
        gov1 = new ArrayList<>();

        loc1 = new ArrayList<>();
        sec1 = new ArrayList<>();
        ski1 = new ArrayList<>();

        sector = view.findViewById(R.id.sector);
        availability = view.findViewById(R.id.availability);
        bank = view.findViewById(R.id.bank);
        skills = view.findViewById(R.id.skills);
        experience = view.findViewById(R.id.experience);
        employment = view.findViewById(R.id.employment);
        home = view.findViewById(R.id.home);
        workers = view.findViewById(R.id.workers);
        looms = view.findViewById(R.id.looms);
        location = view.findViewById(R.id.location);
        progress = view.findViewById(R.id.progress);
        employer = view.findViewById(R.id.employer);
        submit = view.findViewById(R.id.submit);
        yes = view.findViewById(R.id.yes);
        editTxtLoc = view.findViewById(R.id.editTxtLoc);
        previous = view.findViewById(R.id.previous);
        govtinsurance = view.findViewById(R.id.govtinsurance);


        /*exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");*/

        /*emp.add("Employed");
        emp.add("Unemployed");*/


        /*ban.add("---");
        ban.add("Yes");
        ban.add("No");*/


        /*hom.add("Yes");
        hom.add("No");
*/
        wor.add("---");
        wor.add("1");
        wor.add("2");
        wor.add("3");
        wor.add("4");
        wor.add("5");
        wor.add("6");
        wor.add("7");
        wor.add("8");
        wor.add("9");
        wor.add("10");
        wor.add("11");
        wor.add("12");
        wor.add("13");
        wor.add("14");
        wor.add("15");
        wor.add("16");
        wor.add("17");
        wor.add("18");
        wor.add("19");
        wor.add("20");

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);




        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_model, wor);





        workers.setAdapter(adapter5);



        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call31 = cr.getExperience(SharePreferenceUtils.getInstance().getString("lang"));

        call31.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        exp.add(response.body().getData().get(i).getTitle());
                        exp1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, exp);


                    experience.setAdapter(adapter2);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call131 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

        call131.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        ava.add(response.body().getData().get(i).getTitle());
                        ava1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, ava);


                    availability.setAdapter(adapter2);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call32 = cr.getEmployment(SharePreferenceUtils.getInstance().getString("lang"));

        call32.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        emp.add(response.body().getData().get(i).getTitle());
                        emp1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, emp);


                    employment.setAdapter(adapter3);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call33 = cr.getBank(SharePreferenceUtils.getInstance().getString("lang"));

        call33.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        ban.add(response.body().getData().get(i).getTitle());
                        ban1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, ban);



                    bank.setAdapter(adapter6);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call331 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

        call331.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        gov.add(response.body().getData().get(i).getTitle());
                        gov1.add(response.body().getData().get(i).getId());

                    }


                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, gov);


                    govtinsurance.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call34 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

        call34.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        hom.add(response.body().getData().get(i).getTitle());
                        hom1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, hom);


                    home.setAdapter(adapter4);
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

        availability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                avai = ava1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                bann = ban1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        govtinsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                govt = gov1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager.setCurrentItem(0);

            }
        });

        employment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                empl = emp1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hhom = hom1.get(i);

                if (hhom.equals("1")) {

                    yes.setVisibility(View.VISIBLE);

                } else {

                    yes.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                work = wor.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loca = loc1.get(i);

                if (loca.equals("5")) {
                    loc_bool = true;
                    editTxtLoc.setVisibility(View.VISIBLE);
                } else {
                    loc_bool = false;
                    editTxtLoc.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progress.setVisibility(View.VISIBLE);


        final Call<sectorBean> call = cr.getSectors2(SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        sec.add(response.body().getData().get(i).getTitle());
                        sec1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, sec);

                    sector.setAdapter(adapter);

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
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {

                    sect = sec1.get(i);

                    Call<skillsBean> call2 = cr.getSkills1(sect , SharePreferenceUtils.getInstance().getString("lang"));
                    call2.enqueue(new Callback<skillsBean>() {
                        @Override
                        public void onResponse(Call<skillsBean> call, Response<skillsBean> response) {


                            if (response.body().getStatus().equals("1")) {

                                ski.clear();
                                ski1.clear();


                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    ski.add(response.body().getData().get(i).getTitle());
                                    ski1.add(response.body().getData().get(i).getId());

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_list_item_multiple_choice, ski);



                                skills.setListAdapter(adapter).setListener(new BaseMultiSelectSpinner.MultiSpinnerListener() {
                                    @Override
                                    public void onItemsSelected(boolean[] selected) {

                                        skil = "";
                                        List<String> sklist = new ArrayList<>();

                                        if (selected[0]) {

                                            for (int j = 0; j < selected.length; j++) {
                                                skills.selectItem(j, false);
                                            }
                                            skills.selectItem(0, true);
                                            sklist.add(ski1.get(0));

                                        } else {
                                            for (int i = 0; i < selected.length; i++) {
                                                if (selected[i]) {

                                                    sklist.add(ski1.get(i));
                                                }
                                            }
                                        }

                                        skil = TextUtils.join(",", sklist);

                                    }
                                });

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

/*
        skills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                skil = ski1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

        Call<sectorBean> call3 = cr.getLocations(SharePreferenceUtils.getInstance().getString("lang"));

        call3.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                if (response.body().getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        loc.add(response.body().getData().get(i).getTitle());
                        loc1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_model, loc);

                    location.setAdapter(adapter);


                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sectorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emplo = employer.getText().toString();
                loom = looms.getText().toString();

                if (loc_bool) {
                    loca = editTxtLoc.getText().toString();
                }

                if (sect.length() > 0) {


                    if (hhom.length() > 0) {

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getContext().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<verifyBean> call = cr.updateWorkerProfessional(
                                SharePreferenceUtils.getInstance().getString("user_id"),
                                sect,
                                skil,
                                expe,
                                avai,
                                empl,
                                emplo,
                                hhom,
                                work,
                                loom,
                                loca,
                                bann,
                                govt
                        );

                        call.enqueue(new Callback<verifyBean>() {
                            @Override
                            public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

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

                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finishAffinity();


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
                        Toast.makeText(getContext(), "Invalid home based unit", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Invalid sector", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //setPrevious();



        return view;
    }


    private void setPrevious() {
        employer.setText(SharePreferenceUtils.getInstance().getString("employer"));


        int cp = 0;
        for (int i = 0; i < ski.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("skills").equals(ski.get(i))) {
                cp = i;
            }
        }
        skills.setSelection(cp);


        int rp = 0;
        for (int i = 0; i < exp.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("experience").equals(exp.get(i))) {
                rp = i;
            }
        }
        experience.setSelection(rp);


        int mp = 0;
        for (int i = 0; i < emp.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("employment").equals(emp.get(i))) {
                mp = i;
            }
        }
        employment.setSelection(mp);

        int ep = 0;
        for (int i = 0; i < hom.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("home").equals(hom.get(i))) {
                ep = i;
            }
        }
        home.setSelection(ep);

        int chp = 0;
        for (int i = 0; i < wor.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("workers").equals(wor.get(i))) {
                chp = i;
            }
        }
        workers.setSelection(chp);


        int bp = 0;
        for (int i = 0; i < wor.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("tools").equals(wor.get(i))) {
                bp = i;
            }
        }
        looms.setSelection(bp);

        int sp = 0;
        for (int i = 0; i < loc.size(); i++) {
            if (SharePreferenceUtils.getInstance().getString("location").equals(loc.get(i))) {
                sp = i;
            }
        }
        location.setSelection(sp);


    }


}
