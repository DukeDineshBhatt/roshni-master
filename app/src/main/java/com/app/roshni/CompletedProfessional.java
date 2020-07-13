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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.sectorPOJO.sectorBean;

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

public class CompletedProfessional extends Fragment {

    Spinner sector, skills, experience, employment, home, workers, location;

    String sect, skil, expe, empl, hhom, work, loom, loca;

    List<String> sec, ski, exp, emp, hom, wor, loc;
    List<String> sec1, ski1, loc1;

    ProgressBar progress;

    String user_id;
    boolean loc_bool = false;

    EditText employer, editTextLoc , looms;


    LinearLayout yes;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_completed_professional, container, false);

        sec = new ArrayList<>();
        ski = new ArrayList<>();
        exp = new ArrayList<>();
        emp = new ArrayList<>();
        hom = new ArrayList<>();
        wor = new ArrayList<>();
        loc = new ArrayList<>();

        loc1 = new ArrayList<>();
        sec1 = new ArrayList<>();
        ski1 = new ArrayList<>();

        sector = view.findViewById(R.id.sector);
        skills = view.findViewById(R.id.skills);
        experience = view.findViewById(R.id.experience);
        employment = view.findViewById(R.id.employment);
        home = view.findViewById(R.id.home);
        workers = view.findViewById(R.id.workers);
        looms = view.findViewById(R.id.looms);
        location = view.findViewById(R.id.location);
        progress = view.findViewById(R.id.progress);
        employer = view.findViewById(R.id.employer);
        yes = view.findViewById(R.id.yes);
        editTextLoc = view.findViewById(R.id.editTxtLoc);

        user_id = SharePreferenceUtils.getInstance().getString("user");

        Log.d("ID",user_id);

        exp.add("Select one --- ");
        exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");

        emp.add("Select one --- ");
        emp.add("Employed");
        emp.add("Unemployed");

        hom.add("Select one --- ");
        hom.add("Yes");
        hom.add("No");

        wor.add("Select one --- ");
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

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.spinner_model, exp);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, emp);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, hom);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, wor);

        sector.setEnabled(false);
        skills.setEnabled(false);
        experience.setEnabled(false);
        employment.setEnabled(false);
        home.setEnabled(false);
        workers.setEnabled(false);
        location.setEnabled(false);

        experience.setAdapter(adapter2);
        employment.setAdapter(adapter3);
        home.setAdapter(adapter4);
        workers.setAdapter(adapter5);




        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);




        setPrevious();
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

        Call<WorkerByIdListBean> call = cr.getWorkerById1(user_id , SharePreferenceUtils.getInstance().getString("lang"));
        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(@NotNull Call<WorkerByIdListBean> call, @NotNull Response<WorkerByIdListBean> response) {

                final List<WorkerByIdData> item = Objects.requireNonNull(response.body()).getData();


                employer.setText(item.get(0).getEmployer());
                looms.setText(item.get(0).getTools());


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

                                    ski.clear();
                                    ski1.clear();

                                    for (int i = 0; i < response.body().getData().size(); i++) {

                                        ski.add(response.body().getData().get(i).getTitle());
                                        ski1.add(response.body().getData().get(i).getId());

                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                            R.layout.spinner_model, ski);

                                    skills.setAdapter(adapter);

                                    int cp = 0;
                                    for (int i = 0; i < ski.size(); i++) {
                                        if (item.get(0).getSkills().equals(ski.get(i))) {
                                            cp = i;
                                        }
                                    }
                                    skills.setSelection(cp);

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

                int rp = 0;
                for (int i = 0; i < exp.size(); i++) {
                    if (item.get(0).getExperience().equals(exp.get(i))) {
                        rp = i;
                    }
                }
                experience.setSelection(rp);

                int mp = 0;
                for (int i = 0; i < emp.size(); i++) {
                    if (item.get(0).getEmployment().equals(emp.get(i))) {
                        mp = i;
                    }
                }
                employment.setSelection(mp);

                int ep = 0;
                for (int i = 0; i < hom.size(); i++) {
                    if (item.get(0).getHome().equals(hom.get(i))) {
                        ep = i;
                    }
                }
                home.setSelection(ep);

                int chp = 0;
                for (int i = 0; i < wor.size(); i++) {
                    if (item.get(0).getWorkers().equals(wor.get(i))) {
                        chp = i;
                    }
                }
                workers.setSelection(chp);

                int bp = 0;
                for (int i = 0; i < wor.size(); i++) {
                    if (item.get(0).getTools().equals(wor.get(i))) {
                    }
                }




                Call<sectorBean> call3 = cr.getLocations(SharePreferenceUtils.getInstance().getString("lang"));

                call3.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                        if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                            for (int i = 0; i < response.body().getData().size(); i++) {

                                loc.add(response.body().getData().get(i).getTitle());
                                loc1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    R.layout.spinner_model, loc);

                            location.setAdapter(adapter);


                            int sp = 0;
                            for (int i = 0; i < loc.size(); i++) {

                                if (item.get(0).getLocation().equals(loc.get(i))) {
                                    sp = i;
                                    editTextLoc.setText("");
                                    editTextLoc.setVisibility(View.GONE);
                                    break;
                                } else {
                                    editTextLoc.setVisibility(View.VISIBLE);
                                    editTextLoc.setText(item.get(0).getLocation());
                                    sp = loc.size() - 1;
                                }

                            }
                            location.setSelection(sp);


                        }

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<WorkerByIdListBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


}
