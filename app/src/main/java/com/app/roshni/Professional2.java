package com.app.roshni;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Professional2 extends Fragment {

    Spinner sector, experience, employment, home, workers, location , bank;

    MultiSelectSpinner skills;

    String sect, skil, expe, empl, hhom, work, loom, loca , bann;

    List<String> sec, ski, exp , exp1, emp , emp1, hom , hom1, wor, loc , ban , ban1;
    List<String> sec1, ski1, loc1;

    ProgressBar progress;

    EditText employer , looms;
    String id, profile_id;
    boolean loc_bool = false;

    Button reject, approve;
    EditText editTxtLoc;
    LinearLayout yes;

    private CustomViewPager pager;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.professional_layout2, container, false);

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

        loc1 = new ArrayList<>();
        sec1 = new ArrayList<>();
        ski1 = new ArrayList<>();

        sector = view.findViewById(R.id.sector);
        skills = view.findViewById(R.id.skills);
        bank = view.findViewById(R.id.bank);
        experience = view.findViewById(R.id.experience);
        employment = view.findViewById(R.id.employment);
        home = view.findViewById(R.id.home);
        workers = view.findViewById(R.id.workers);
        looms = view.findViewById(R.id.looms);
        location = view.findViewById(R.id.location);
        progress = view.findViewById(R.id.progress);
        employer = view.findViewById(R.id.employer);
        reject = view.findViewById(R.id.reject);
        approve = view.findViewById(R.id.approve);
        yes = view.findViewById(R.id.yes);
        editTxtLoc = view.findViewById(R.id.editTxtLoc);

        id = SharePreferenceUtils.getInstance().getString("user");
        profile_id = SharePreferenceUtils.getInstance().getString("survey_id");


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


        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                expe = exp1.get(i);

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

                if (hhom.equals("Yes")) {

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









        approve.setOnClickListener(new View.OnClickListener() {
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

                        Log.d("asdsad" , SharePreferenceUtils.getInstance().getString("user_id"));

                        Call<verifyBean> call = cr.updateWorkerProfessional2(
                                SharePreferenceUtils.getInstance().getString("survey_id"),
                                sect,
                                skil,
                                expe,
                                empl,
                                emplo,
                                hhom,
                                work,
                                loom,
                                loca,
                                bann,
                                SharePreferenceUtils.getInstance().getString("id")
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

                                    Intent intent = new Intent(getContext(), MainActivity4.class);
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



        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("Reason for rejecting this profile?");

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                input.setHint("tab to enter");
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String value = input.getText().toString().trim();

                        if (value.length() > 0 || value.startsWith("  ")) {

                            String emplo = employer.getText().toString();
                            loom = looms.getText().toString();
                            if (loc_bool) {

                                loca = editTxtLoc.getText().toString();
                            }
                            Log.d("SEC", sect);
                            Log.d("Skil", skil);
                            Log.d("loc", loca);
                            Log.d("res", value);

                            if (sect.length() > 0) {
                                if (skil.length() > 0) {
                                    if (expe.length() > 0) {
                                        if (empl.length() > 0) {
                                            if (hhom.length() > 0) {
                                                if (work.length() > 0) {
                                                    if (loom.length() > 0) {
                                                        if (loca.length() > 0) {

                                                            progress.setVisibility(View.VISIBLE);

                                                            Bean b = (Bean) getContext().getApplicationContext();

                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                    .baseUrl(b.baseurl)
                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                    .build();

                                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                            Call<verifyBean> call = cr.rejectWorkerProfessional(
                                                                    profile_id,
                                                                    sect,
                                                                    skil,
                                                                    expe,
                                                                    empl,
                                                                    emplo,
                                                                    hhom,
                                                                    work,
                                                                    loom,
                                                                    loca,
                                                                    value,
                                                                    bann,
                                                                    SharePreferenceUtils.getInstance().getString("id")
                                                            );

                                                            call.enqueue(new Callback<verifyBean>() {
                                                                @Override
                                                                public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

                                                                    if (response.body().getStatus().equals("1")) {
                                                                        Data item = response.body().getData();

                                                                        Intent intent = new Intent(getContext(), MainActivity4.class);
                                                                        startActivity(intent);
                                                                        getActivity().finish();


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
                                                            Toast.makeText(getContext(), "Invalid location", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Invalid no. of looms", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(getContext(), "Invalid no. of workers", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Invalid home based unit", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Invalid employment status", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Invalid experience", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Invalid skill", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Invalid sector", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            
                            Toast.makeText(getContext(), "Invalid Reason ", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        Log.d("CANCEL","cancel");
                        progress.setVisibility(View.GONE);
                    }
                });

                alert.show();
                progress.setVisibility(View.GONE);


            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        setPrevious();

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

        Call<WorkerByIdListBean> call = cr.getWorkerById1(id , SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(Call<WorkerByIdListBean> call, Response<WorkerByIdListBean> response) {

                final List<WorkerByIdData> item = response.body().getData();

                employer.setText(item.get(0).getEmployer());
                looms.setText(item.get(0).getTools());


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
                            for (int i = 0; i < sec.size(); i++) {
                                if (item.get(0).getSector().equals(sec.get(i))) {
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

                                    ski.clear();
                                    ski1.clear();

                                    for (int i = 0; i < response.body().getData().size(); i++) {

                                        ski.add(response.body().getData().get(i).getTitle());
                                        ski1.add(response.body().getData().get(i).getId());

                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_list_item_multiple_choice, ski);


                                    skil = item.get(0).getSkillsId();


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

                                    String[] dd = item.get(0).getSkillsId().split(",");

                                    int cp = 0;
                                    for (int i = 0; i < ski1.size(); i++) {

                                        for (int j = 0 ; j < dd.length ; j++)
                                        {

                                            if (dd[j].equals(ski1.get(i))) {
                                                cp = i;
                                                skills.selectItem(i , true);
                                            }

                                        }


                                    }
                                    //skills.setSelection(cp);

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

                final Call<sectorBean> call4 = cr.getExperience(SharePreferenceUtils.getInstance().getString("lang"));

                call4.enqueue(new Callback<sectorBean>() {
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
                                if (item.get(0).getExperience().equals(exp1.get(i))) {
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

                final Call<sectorBean> call5 = cr.getEmployment(SharePreferenceUtils.getInstance().getString("lang"));

                call5.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            emp.clear();
                            emp1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                emp.add(response.body().getData().get(i).getTitle());
                                emp1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, emp);


                            employment.setAdapter(adapter3);

                            int cp2 = 0;
                            for (int i = 0; i < emp1.size(); i++) {
                                if (item.get(0).getEmployment().equals(emp1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            employment.setSelection(cp2);

                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                final Call<sectorBean> call6 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

                call6.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            hom.clear();
                            hom1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                hom.add(response.body().getData().get(i).getTitle());
                                hom1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, hom);


                            home.setAdapter(adapter4);

                            int cp2 = 0;
                            for (int i = 0; i < hom1.size(); i++) {
                                if (item.get(0).getHome().equals(hom1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            home.setSelection(cp2);

                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                final Call<sectorBean> call7 = cr.getBank(SharePreferenceUtils.getInstance().getString("lang"));

                call7.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            ban.clear();
                            ban1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ban.add(response.body().getData().get(i).getTitle());
                                ban1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, ban);


                            bank.setAdapter(adapter6);

                            int cp2 = 0;
                            for (int i = 0; i < ban1.size(); i++) {
                                if (item.get(0).getBank().equals(ban1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            bank.setSelection(cp2);

                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                final Call<sectorBean> call8 = cr.getLocations(SharePreferenceUtils.getInstance().getString("lang"));

                call8.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            loc.clear();
                            loc1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                loc.add(response.body().getData().get(i).getTitle());
                                loc1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, loc);


                            location.setAdapter(adapter);

                            int sp = 0;
                            for (int i = 0; i < loc1.size(); i++) {

                                if (item.get(0).getLocation().equals(loc1.get(i))) {
                                    sp = i;
                                    editTxtLoc.setText("");
                                    editTxtLoc.setVisibility(View.GONE);
                                    break;
                                } else {
                                    editTxtLoc.setVisibility(View.VISIBLE);
                                    editTxtLoc.setText(item.get(0).getLocation());
                                    sp = loc.size() - 1;
                                }

                            }
                            location.setSelection(sp);


                        }



                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


                int chp = 0;
                for (int i = 0; i < wor.size(); i++) {
                    if (item.get(0).getWorkers().equals(wor.get(i))) {
                        chp = i;
                    }
                }
                workers.setSelection(chp);





            }

            @Override
            public void onFailure(Call<WorkerByIdListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


}