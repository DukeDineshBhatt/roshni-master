package com.app.roshni;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WorkerProfessionalProfile extends Fragment {

    Spinner sector, experience, employment, home, workers, location , bank , govtinsurance , availability;

    String sect, skil, expe, empl, hhom, work, loom, loca , bann;

    List<String> sec, ski, exp , exp1, emp , emp1, hom , hom1, wor, loc , ban , ban1 , ava , ava1 , gov , gov1;
    List<String> sec1, ski1, loc1;

    ProgressBar progress;

    String user_id;

    EditText employer,editTextLoc , skills , looms;
    TextView txtStatus;


    LinearLayout yes;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_worker_prof, container, false);

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
        gov1 = new ArrayList<>();
        gov = new ArrayList<>();

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
        yes = view.findViewById(R.id.yes);
        txtStatus = view.findViewById(R.id.textViewStatus);
        editTextLoc = view.findViewById(R.id.editTxtLoc);
        govtinsurance = view.findViewById(R.id.govtinsurance);

        user_id = SharePreferenceUtils.getInstance().getString("user_id");

        /*exp.add("0 to 2 years");
        exp.add("3 to 5 years");
        exp.add("5 to 10 years");
        exp.add("more than 10 years");

        ban.add("---");
        ban.add("Yes");
        ban.add("No");

        emp.add("Employed");
        emp.add("Unemployed");

        hom.add("Yes");
        hom.add("No");*/

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




        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_model, wor);

        sector.setEnabled(false);

        experience.setEnabled(false);
        employment.setEnabled(false);
        home.setEnabled(false);
        workers.setEnabled(false);
        location.setEnabled(false);
        bank.setEnabled(false);
        govtinsurance.setEnabled(false);




        workers.setAdapter(adapter5);




        //setPrevious();

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

        Call<WorkerByIdListBean> call = cr.getWorkerById1(user_id , SharePreferenceUtils.getInstance().getString("lang"));
        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(Call<WorkerByIdListBean> call, Response<WorkerByIdListBean> response) {

                final List<WorkerByIdData> item = response.body().getData();

                if (item.get(0).getStatus().equals("pending")) {

                    txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                    txtStatus.setVisibility(View.VISIBLE);
                } else if (item.get(0).getStatus().equals("rejected")) {
                    txtStatus.setText(item.get(0).getRejectReason());
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else if (item.get(0).getStatus().equals("submitted")) {
                    txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else if (item.get(0).getStatus().equals("modifications")) {
                    txtStatus.setText(item.get(0).getRejectReason());
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else {
                    txtStatus.setVisibility(View.GONE);
                }


                employer.setText(item.get(0).getEmployer());
                skills.setText(item.get(0).getSkills());
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
                            for (int i = 0; i < sec1.size(); i++) {
                                if (item.get(0).getSectorId().equals(sec1.get(i))) {
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

                        Call<skillsBean> call2 = cr.getSkills1(sect,SharePreferenceUtils.getInstance().getString("lang"));
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


                                    int cp = 0;
                                    for (int i = 0; i < ski1.size(); i++) {
                                        if (item.get(0).getSkillsId().equals(ski1.get(i))) {
                                            cp = i;
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

                final Call<sectorBean> call71 = cr.getGovt(SharePreferenceUtils.getInstance().getString("lang"));

                call71.enqueue(new Callback<sectorBean>() {
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


                            int cp21 = 0;
                            for (int i = 0; i < gov1.size(); i++) {
                                if (item.get(0).getGovt().equals(gov1.get(i))) {
                                    cp21 = i;
                                }
                            }
                            govtinsurance.setSelection(cp21);

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
                    public void onFailure(Call<sectorBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                final Call<sectorBean> call9 = cr.getAvailability(SharePreferenceUtils.getInstance().getString("lang"));

                call9.enqueue(new Callback<sectorBean>() {
                    @Override
                    public void onResponse(Call<sectorBean> call, Response<sectorBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            ava.clear();
                            ava1.clear();

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ava.add(response.body().getData().get(i).getTitle());
                                ava1.add(response.body().getData().get(i).getId());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    R.layout.spinner_model, ava);


                            availability.setAdapter(adapter);

                            int cp2 = 0;
                            for (int i = 0; i < ava1.size(); i++) {
                                if (item.get(0).getAvailability().equals(ava1.get(i))) {
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

/*
                int sc = 0;
                for (int i = 0; i < sec.size(); i++) {
                    if (item.get(0).getSector().equals(sec.get(i))) {
                        sc = i;
                    }
                }
                sector.setSelection(sc);
*/

/*
                int cp = 0;
                for (int i = 0; i < ski.size(); i++) {
                    if (item.get(0).getSkills().equals(ski.get(i))) {
                        cp = i;
                    }
                }
                skills.setSelection(cp);*/







                int chp = 0;
                for (int i = 0; i < wor.size(); i++) {
                    if (item.get(0).getWorkers().equals(wor.get(i))) {
                        chp = i;
                    }
                }
                workers.setSelection(chp);





                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<WorkerByIdListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


}



