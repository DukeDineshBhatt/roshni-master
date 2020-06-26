package com.app.roshni;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.roshni.SkillsPOJO.Datum;
import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verifyPOJO.Data;
import com.app.roshni.verifyPOJO.verifyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Professional2 extends Fragment {

    Spinner experience, employment, home, workers, location, bank, govtinsurance, availability;

    EditText sector, skills;

    String sect = "", skil = "", expe = "", empl = "", hhom = "", work = "", loom = "", loca = "", bann = "", govt = "", avai = "";

    List<String> exp, exp1, emp, emp1, hom, hom1, wor, loc, ban, ban1, ava, ava1, gov, gov1;
    List<String> sec1, ski1, loc1;

    List<Datum> ski;
    List<com.app.roshni.sectorPOJO.Data> sec;

    ProgressBar progress;

    EditText employer, looms;
    String id, profile_id;
    boolean loc_bool = false;

    Button reject, approve, previous;
    EditText editTxtLoc;
    LinearLayout yes;

    private CustomViewPager pager;

    EditText otherwork, othergovt;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    boolean gov_bool = false;

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
        gov = new ArrayList<>();
        gov1 = new ArrayList<>();

        loc1 = new ArrayList<>();
        sec1 = new ArrayList<>();
        ski1 = new ArrayList<>();
        ava = new ArrayList<>();
        ava1 = new ArrayList<>();

        othergovt = view.findViewById(R.id.othergovt);
        otherwork = view.findViewById(R.id.otherwork);
        sector = view.findViewById(R.id.sector);
        previous = view.findViewById(R.id.previous);
        availability = view.findViewById(R.id.availability);
        govtinsurance = view.findViewById(R.id.govtinsurance);
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


        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                expe = exp1.get(i);

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

        availability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0)
                {
                    avai = ava1.get(i);
                }

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
                if (i > 0)
                {
                    hhom = hom1.get(i);

                    if (hhom.equals("2")) {

                        yes.setVisibility(View.VISIBLE);

                    } else {

                        yes.setVisibility(View.GONE);
                    }
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

                if (loca.equals("6")) {
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

        sector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
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
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {


                            adapter.setData(response.body().getData());

                        }

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<sectorBean> call, Throwable t) {
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




        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.sector_dialog);
                dialog.show();

                RecyclerView sectorgrid = dialog.findViewById(R.id.grid);
                Button ok = dialog.findViewById(R.id.button30);
                final ProgressBar bar = dialog.findViewById(R.id.progressBar10);

                final WorkAdapter adapter = new WorkAdapter(getActivity(), ski);
                GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
                sectorgrid.setAdapter(adapter);
                sectorgrid.setLayoutManager(manager);

                final Call<skillsBean> call = cr.getSkills1(TextUtils.join(",", sec1), SharePreferenceUtils.getInstance().getString("lang"));

                bar.setVisibility(View.VISIBLE);

                call.enqueue(new Callback<skillsBean>() {
                    @Override
                    public void onResponse(Call<skillsBean> call, Response<skillsBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            adapter.setData(response.body().getData());

                        }

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<skillsBean> call, Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ski1 = adapter.getIds();
                        dialog.dismiss();

                        skills.setText(TextUtils.join(", ", adapter.getWorks()));
                        Log.d("sectors", TextUtils.join(",", ski1));


                    }
                });

            }
        });

        progress.setVisibility(View.VISIBLE);


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emplo = employer.getText().toString();
                loom = looms.getText().toString();
                String ot = otherwork.getText().toString();
                if (loc_bool) {
                    loca = editTxtLoc.getText().toString();
                }

                if (gov_bool) {
                    govt = othergovt.getText().toString();
                }

                skil = TextUtils.join(",", ski1);
                sect = TextUtils.join(",", sec1);



                if (sect.length() > 0) {
                    if (avai.length() > 0)
                    {
                        if (hhom.length() > 0) {

                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getContext().getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Log.d("asdsad", SharePreferenceUtils.getInstance().getString("user_id"));

                            Call<verifyBean> call = cr.updateWorkerProfessional2(
                                    SharePreferenceUtils.getInstance().getString("survey_id"),
                                    sect,
                                    skil,
                                    ot,
                                    expe,
                                    avai,
                                    empl,
                                    emplo,
                                    hhom,
                                    work,
                                    loom,
                                    loca,
                                    bann,
                                    SharePreferenceUtils.getInstance().getString("id"),
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

                    }
                    else
                    {
                        Toast.makeText(getContext(), "Invalid availability", Toast.LENGTH_SHORT).show();
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
                            String ot = otherwork.getText().toString();
                            if (loc_bool) {

                                loca = editTxtLoc.getText().toString();
                            }

                            if (gov_bool) {
                                govt = othergovt.getText().toString();
                            }

                            skil = TextUtils.join(",", ski1);
                            sect = TextUtils.join(",", sec1);


                            if (sect.length() > 0) {
                                if (avai.length() > 0)
                                {
                                    if (hhom.length() > 0) {

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
                                                ot,
                                                expe,
                                                avai,
                                                empl,
                                                emplo,
                                                hhom,
                                                work,
                                                loom,
                                                loca,
                                                value,
                                                bann,
                                                SharePreferenceUtils.getInstance().getString("id"),
                                                govt
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
                                        Toast.makeText(getContext(), "Invalid home based unit", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Invalid availability", Toast.LENGTH_SHORT).show();
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
                        Log.d("CANCEL", "cancel");
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

        Call<WorkerByIdListBean> call = cr.getWorkerById1(id, SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(Call<WorkerByIdListBean> call, Response<WorkerByIdListBean> response) {

                final List<WorkerByIdData> item = response.body().getData();

                employer.setText(item.get(0).getEmployer());
                looms.setText(item.get(0).getTools());


                sec1 = Arrays.asList(item.get(0).getSectorId().split(","));
                ski1 = Arrays.asList(item.get(0).getSkillsId().split(","));

                sector.setText(item.get(0).getSector());
                skills.setText(item.get(0).getSkills());

                if (item.get(0).getOtherwork().length() > 0) {
                    otherwork.setVisibility(View.VISIBLE);
                } else {
                    otherwork.setVisibility(View.GONE);
                }



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

                            ArrayAdapter<String> adapter4 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
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

                final Call<sectorBean> call72 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

                call72.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            gov.clear();
                            gov1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                gov.add(response.body().getData().get(i).getTitle());
                                gov1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, gov);


                            govtinsurance.setAdapter(adapter6);

                            int sp = 0;
                            for (int i = 0; i < gov1.size(); i++) {

                                if (item.get(0).getGovt().equals(gov1.get(i))) {
                                    sp = i;
                                    othergovt.setText("");
                                    othergovt.setVisibility(View.GONE);
                                    break;
                                } else {
                                    othergovt.setVisibility(View.VISIBLE);
                                    othergovt.setText(item.get(0).getGovt());
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

                final Call<sectorBean> call71 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

                call71.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            ava.clear();
                            ava1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ava.add(response.body().getData().get(i).getTitle());
                                ava1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, ava);


                            availability.setAdapter(adapter6);

                            int cp21 = 0;
                            for (int i = 0; i < ava1.size(); i++) {
                                if (item.get(0).getAvailability().equals(ava1.get(i))) {
                                    cp21 = i;
                                }
                            }
                            availability.setSelection(cp21);


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

    class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.ViewHolder> {

        Context context;
        List<com.app.roshni.sectorPOJO.Data> list = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> secs = new ArrayList<>();

        public SectorAdapter(Context context, List<com.app.roshni.sectorPOJO.Data> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.app.roshni.sectorPOJO.Data> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.sector_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            final com.app.roshni.sectorPOJO.Data item = list.get(position);

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

            TextView title;
            CardView card;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

    class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

        Context context;
        List<com.app.roshni.SkillsPOJO.Datum> list = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> works = new ArrayList<>();

        public WorkAdapter(Context context, List<com.app.roshni.SkillsPOJO.Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.app.roshni.SkillsPOJO.Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.sector_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            holder.setIsRecyclable(false);

            final Datum item = list.get(position);

            holder.title.setText(item.getTitle());

            if (ski1.contains(item.getId())) {
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

            TextView title;
            CardView card;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

}