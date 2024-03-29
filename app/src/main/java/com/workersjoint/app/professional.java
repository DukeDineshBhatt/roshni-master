package com.workersjoint.app;

import android.app.Dialog;
import android.content.Context;
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

import com.workersjoint.app.SkillsPOJO.Datum;
import com.workersjoint.app.SkillsPOJO.skillsBean;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verifyPOJO.Data;
import com.workersjoint.app.verifyPOJO.verifyBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class professional extends Fragment {

    Spinner experience, employment, home, workers, location, bank, govtinsurance, availability, child_labour, supply_chain;

    EditText sector, skills;

    String sect = "", skil = "", expe = "", empl = "", hhom = "", work = "", loom = "", loca = "", bann = "", govt = "", avai = "", chla = "", supl = "";

    List<String> exp, exp1, emp, emp1, hom, hom1, wor, loc, ban, ban1, ava, ava1, gov, gov1, chi, chi1;
    List<String> sec1, ski1, loc1;
    List<Datum> ski;
    List<com.workersjoint.app.sectorPOJO.Data> sec;
    ProgressBar progress;

    EditText employer, looms;

    Button submit, previous;
    EditText editTxtLoc;
    boolean loc_bool = false;

    LinearLayout yes;
    boolean gov_bool = false;

    private CustomViewPager pager;

    EditText otherwork, othergovt;

    List<String> fac_hom;
    List<String> fac_hom1;
    String fact_home = "";
    Spinner factory_home;
    LinearLayout factory_home_layout;
    EditText area;

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
        chi = new ArrayList<>();
        chi1 = new ArrayList<>();

        fac_hom = new ArrayList<>();
        fac_hom1 = new ArrayList<>();

        factory_home = view.findViewById(R.id.factory_home);
        factory_home_layout = view.findViewById(R.id.factory_home_layout);
        area = view.findViewById(R.id.area);

        child_labour = view.findViewById(R.id.child_labour);
        supply_chain = view.findViewById(R.id.supply_chain);
        othergovt = view.findViewById(R.id.othergovt);
        otherwork = view.findViewById(R.id.otherwork);
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

        Bean b = (Bean) requireContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, wor);


        workers.setAdapter(adapter5);


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call31 = cr.getExperience(SharePreferenceUtils.getInstance().getString("lang"));

        call31.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        exp.add(response.body().getData().get(i).getTitle());
                        exp1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, exp);


                    experience.setAdapter(adapter2);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call131 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

        call131.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        ava.add(response.body().getData().get(i).getTitle());
                        ava1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, ava);


                    availability.setAdapter(adapter2);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call32 = cr.getEmployment(SharePreferenceUtils.getInstance().getString("lang"));

        call32.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        emp.add(response.body().getData().get(i).getTitle());
                        emp1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, emp);


                    employment.setAdapter(adapter3);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call33 = cr.getBank(SharePreferenceUtils.getInstance().getString("lang"));

        call33.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        ban.add(response.body().getData().get(i).getTitle());
                        ban1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, ban);


                    bank.setAdapter(adapter6);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call331 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

        call331.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        gov.add(response.body().getData().get(i).getTitle());
                        gov1.add(response.body().getData().get(i).getId());

                    }


                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, gov);


                    govtinsurance.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call34 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

        call34.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        hom.add(response.body().getData().get(i).getTitle());
                        hom1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, hom);


                    home.setAdapter(adapter4);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
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

                if (i > 0) {
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
                if (i > 0) {
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

                final Dialog dialog = new Dialog(requireActivity());
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


        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(requireActivity());
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

                        ski1 = adapter.getIds();
                        dialog.dismiss();

                        skills.setText(TextUtils.join(", ", adapter.getWorks()));
                        Log.d("sectors", TextUtils.join(",", ski1));


                    }
                });

            }
        });


        Call<sectorBean> call3 = cr.getLocations(SharePreferenceUtils.getInstance().getString("lang"));

        call3.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        loc.add(response.body().getData().get(i).getTitle());
                        loc1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, loc);

                    location.setAdapter(adapter);


                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call8 = cr.getChild(SharePreferenceUtils.getInstance().getString("lang"));

        call8.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        chi.add(response.body().getData().get(i).getTitle());
                        chi1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, chi);


                    child_labour.setAdapter(adapter7);
                    supply_chain.setAdapter(adapter7);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<sectorBean> call82 = cr.getFactoryHome(SharePreferenceUtils.getInstance().getString("lang"));

        call82.enqueue(new Callback<sectorBean>() {
            @Override
            public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        fac_hom.add(response.body().getData().get(i).getTitle());
                        fac_hom1.add(response.body().getData().get(i).getId());

                    }

                    ArrayAdapter<String> adapter7 = new ArrayAdapter<>(requireContext(),
                            R.layout.spinner_model, fac_hom);

                    factory_home.setAdapter(adapter7);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        factory_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    fact_home = fac_hom1.get(i);

                    if (fact_home.equals("3")) {

                        factory_home_layout.setVisibility(View.VISIBLE);

                    } else {
                        factory_home_layout.setVisibility(View.GONE);
                    }
                } else {
                    fact_home = "";
                }

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emplo = employer.getText().toString();
                final String arr = area.getText().toString();
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
                    if (avai.length() > 0) {
                        if (fact_home.length() > 0) {

                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) requireContext().getApplicationContext();

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
                                    govt,
                                    chla,
                                    supl,
                                    arr,
                                    fact_home
                            );

                            call.enqueue(new Callback<verifyBean>() {
                                @Override
                                public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                    if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
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
                                        requireActivity().finishAffinity();


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
                            Toast.makeText(getContext(), "Invalid factory/ home worker", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Invalid availability", Toast.LENGTH_SHORT).show();
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
