package com.workersjoint.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.workersjoint.app.sectorPOJO.sectorBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class WorkerPersonalProfile extends Fragment {

    private Spinner gender, category, religion, educational, marital, children, below6, sixto14, fifteento18, goingtoschool , goingtoschool2, proof , age;


    private EditText name, dob, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, editTxtProof, editTxtRelg, editTxtedu , phone;

    private ImageView image;

    TextView txtStatus;

    private List<String> gen, gen1, cat, cat1, rel, rel1, edu, edu1, mar, mar1, chi, prof, prof1 , agg;

    private LinearLayout permanent, child;

    private ProgressBar progress;

    CheckBox check;

    String user_id;

    Spinner certified , skill_level;
    TextView certificate_number_title , skill_level_title;
    EditText certificate_number , annual , other_sources;

    List<String> cer , cer1 , ski , ski1;
    String cert , skil;

    Button submit;

    void setData(CustomViewPager pager) {
    }

    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_worker_personal, container, false);

        gen = new ArrayList<>();
        gen1 = new ArrayList<>();
        cat = new ArrayList<>();
        cat1 = new ArrayList<>();
        rel = new ArrayList<>();
        rel1 = new ArrayList<>();
        edu = new ArrayList<>();
        edu1 = new ArrayList<>();
        mar = new ArrayList<>();
        mar1 = new ArrayList<>();
        chi = new ArrayList<>();
        prof = new ArrayList<>();
        prof1 = new ArrayList<>();
        agg = new ArrayList<>();

        cer = new ArrayList<>();
        cer1 = new ArrayList<>();
        ski = new ArrayList<>();
        ski1 = new ArrayList<>();

        swipe = view.findViewById(R.id.swipe);
        submit = view.findViewById(R.id.submit);
        certified = view.findViewById(R.id.certified);
        goingtoschool2 = view.findViewById(R.id.goingtoschool2);
        skill_level = view.findViewById(R.id.skill_level);
        certificate_number_title = view.findViewById(R.id.certificate_number_title);
        skill_level_title = view.findViewById(R.id.skill_level_title);
        certificate_number = view.findViewById(R.id.certificate_number);
        annual = view.findViewById(R.id.annual);
        other_sources = view.findViewById(R.id.other_sources);

        name = view.findViewById(R.id.editText);
        check = view.findViewById(R.id.check);
        phone = view.findViewById(R.id.phone);
        dob = view.findViewById(R.id.editText2);
        age = view.findViewById(R.id.age);
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
        editTxtProof = view.findViewById(R.id.editTxtProf);
        editTxtRelg = view.findViewById(R.id.editTxtRelg);
        editTxtedu = view.findViewById(R.id.editTxtedu);
        txtStatus = view.findViewById(R.id.textViewStatus);

        progress = view.findViewById(R.id.progress);

        permanent = view.findViewById(R.id.permanent);
        child = view.findViewById(R.id.child);


        image = view.findViewById(R.id.imageView3);


        gender = view.findViewById(R.id.gender);
        category = view.findViewById(R.id.category);
        religion = view.findViewById(R.id.religion);
        educational = view.findViewById(R.id.educational);
        marital = view.findViewById(R.id.marital);
        children = view.findViewById(R.id.children);
        below6 = view.findViewById(R.id.belowsix);
        sixto14 = view.findViewById(R.id.sixto14);
        fifteento18 = view.findViewById(R.id.fifteento18);
        goingtoschool = view.findViewById(R.id.goingtoschool);
        proof = view.findViewById(R.id.proof);

        user_id = SharePreferenceUtils.getInstance().getString("user_id");


        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setPrevious();

                swipe.setRefreshing(false);

            }
        });


        /*gen.add("Male");
        gen.add("Female");*/

        for (int i = 14 ; i <= 100 ; i++)
        {
            agg.add("" + i);
        }



        /*cat.add("SC");
        cat.add("ST");
        cat.add("OBC");
        cat.add("General");*/

        /*rel.add("Hindu");
        rel.add("Muslim");
        rel.add("Sikh");
        rel.add("Christian");
        rel.add("Others");*/

       /* edu.add("Uneducated");
        edu.add("Primary (Class 1-5)");
        edu.add("Middle (Class 6-8)");
        edu.add("Secondary (Class 9-10)");
        edu.add("Senior Secondary (Class 11-12)");
        edu.add("Graduation");
        edu.add("Post Graduation");
        edu.add("Others");*/

        /*mar.add("Single");
        mar.add("Married");
        mar.add("Divorcee");
        mar.add("Separated");
*/
        chi.add("--- Select ---");
        chi.add("0");
        chi.add("1");
        chi.add("2");
        chi.add("3");
        chi.add("4");
        chi.add("5");
        chi.add("6");
        chi.add("7");
        chi.add("8");
        chi.add("9");
        chi.add("10");
        chi.add("11");
        chi.add("12");

       /* prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");*/






        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                R.layout.spinner_model, chi);


        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_model, agg);

        proof.setEnabled(false);
        gender.setEnabled(false);
        category.setEnabled(false);
        religion.setEnabled(false);
        educational.setEnabled(false);
        marital.setEnabled(false);
        children.setEnabled(false);
        below6.setEnabled(false);
        sixto14.setEnabled(false);
        fifteento18.setEnabled(false);
        goingtoschool.setEnabled(false);
        age.setEnabled(false);
        goingtoschool2.setEnabled(false);

        certified.setEnabled(false);
        skill_level.setEnabled(false);
        certificate_number.setFocusable(false);
        annual.setFocusable(false);
        other_sources.setFocusable(false);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() , UpdateWorkerPersonal.class);
                startActivity(intent);

            }
        });



        children.setAdapter(adapter5);
        below6.setAdapter(adapter5);
        sixto14.setAdapter(adapter5);
        fifteento18.setAdapter(adapter5);
        goingtoschool.setAdapter(adapter5);
        goingtoschool2.setAdapter(adapter5);

        age.setAdapter(adapter7);

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





        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        setPrevious();

    }

    void setPrevious() {

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getActivity()).getApplicationContext();

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

                try {
                    final List<WorkerByIdData> item = Objects.requireNonNull(response.body()).getData();

                    switch (item.get(0).getStatus()) {
                        case "pending":

                            txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                            txtStatus.setVisibility(View.VISIBLE);
                            break;
                        case "rejected":
                            txtStatus.setText(item.get(0).getRejectReason());
                            txtStatus.setVisibility(View.VISIBLE);
                            break;
                        case "verified":
                            txtStatus.setText("YOUR PROFILE IS PENDING FOR APPROVAL");
                            txtStatus.setVisibility(View.VISIBLE);
                            break;
                        case "modifications":
                            txtStatus.setText(item.get(0).getRejectReason());
                            txtStatus.setVisibility(View.VISIBLE);
                            break;
                        default:
                            txtStatus.setVisibility(View.GONE);
                            break;
                    }

                    name.setText(item.get(0).getName());
                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

                    if (item.get(0).getSame().equals("1"))
                    {
                        check.setChecked(true);
                    }
                    else
                    {
                        check.setChecked(false);
                    }

                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.get(0).getPhoto(), image, options);
                    editTxtProof.setText(item.get(0).getIdNumber());
                    dob.setText(item.get(0).getDob());
                    cpin.setText(item.get(0).getCpin());
                    cstreet.setText(item.get(0).getCstreet());
                    carea.setText(item.get(0).getCarea());
                    cdistrict.setText(item.get(0).getCdistrict());
                    cstate.setText(item.get(0).getCstate());
                    ppin.setText(item.get(0).getPpin());
                    pstreet.setText(item.get(0).getPstreet());
                    parea.setText(item.get(0).getParea());
                    pdistrict.setText(item.get(0).getPdistrict());
                    pstate.setText(item.get(0).getPstate());
                    phone.setText(item.get(0).getPhone());

                    certificate_number.setText(item.get(0).getCertificationNumber());
                    annual.setText(item.get(0).getAnnualIncome());
                    other_sources.setText(item.get(0).getOtherSource());

                    final Call<sectorBean> call3 = cr.getGender(SharePreferenceUtils.getInstance().getString("lang"));

                    call3.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                gen.clear();
                                gen1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    gen.add(response.body().getData().get(i).getTitle());
                                    gen1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, gen);


                                    gender.setAdapter(adapter);

                                    int cp2 = 0;
                                    for (int i = 0; i < gen1.size(); i++) {
                                        if (item.get(0).getGender().equals(gen1.get(i))) {
                                            cp2 = i;
                                        }
                                    }
                                    gender.setSelection(cp2);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call4 = cr.getCategories(SharePreferenceUtils.getInstance().getString("lang"));

                    call4.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                cat.clear();
                                cat1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    cat.add(response.body().getData().get(i).getTitle());
                                    cat1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, cat);


                                    category.setAdapter(adapter1);

                                    int cp2 = 0;
                                    for (int i = 0; i < cat1.size(); i++) {
                                        if (item.get(0).getCategory().equals(cat1.get(i))) {
                                            cp2 = i;
                                        }
                                    }
                                    category.setSelection(cp2);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call5 = cr.getReligion(SharePreferenceUtils.getInstance().getString("lang"));

                    call5.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                rel.clear();
                                rel1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    rel.add(response.body().getData().get(i).getTitle());
                                    rel1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, rel);


                                    religion.setAdapter(adapter2);

                            /*int cp2 = 0;
                            for (int i = 0; i < rel1.size(); i++) {
                                if (item.get(0).getReligion().equals(rel1.get(i))) {
                                    cp2 = i;
                                }
                            }
                            religion.setSelection(cp2);*/

                                    int re = 0;
                                    for (int i = 0; i < rel.size(); i++) {

                                        if (item.get(0).getReligion().equals(rel1.get(i))) {
                                            re = i;
                                            editTxtRelg.setText("");
                                            editTxtRelg.setVisibility(View.GONE);
                                            break;
                                        } else {
                                            editTxtRelg.setVisibility(View.VISIBLE);
                                            editTxtRelg.setText(item.get(0).getReligion());
                                            re = 5;
                                        }
                                    }
                                    religion.setSelection(re);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call6 = cr.getEducation(SharePreferenceUtils.getInstance().getString("lang"));

                    call6.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                edu.clear();
                                edu1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    edu.add(response.body().getData().get(i).getTitle());
                                    edu1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, edu);


                                    educational.setAdapter(adapter3);



                                    int ed = 0;
                                    for (int i = 0; i < edu1.size(); i++) {
                                        if (item.get(0).getEducational().equals(edu1.get(i))) {
                                            ed = i;
                                            editTxtedu.setText("");
                                            editTxtedu.setVisibility(View.GONE);
                                            break;
                                        } else {
                                            editTxtedu.setVisibility(View.VISIBLE);
                                            editTxtedu.setText(item.get(0).getEducational());
                                            ed = 8;
                                        }
                                    }
                                    educational.setSelection(ed);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call7 = cr.getMarital(SharePreferenceUtils.getInstance().getString("lang"));

                    call7.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                mar.clear();
                                mar1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    mar.add(response.body().getData().get(i).getTitle());
                                    mar1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, mar);


                                    marital.setAdapter(adapter4);

                                    int cp2 = 0;
                                    for (int i = 0; i < mar1.size(); i++) {
                                        if (item.get(0).getMarital().equals(mar1.get(i))) {
                                            cp2 = i;
                                        }
                                    }
                                    marital.setSelection(cp2);


                                    if (item.get(0).getMarital().equals("2")) {
                                        child.setVisibility(View.GONE);
                                    } else {

                                        child.setVisibility(View.VISIBLE);

                                        int chp = 0;
                                        for (int i = 0; i < chi.size(); i++) {
                                            if (item.get(0).getChildren().equals(chi.get(i))) {
                                                chp = i;
                                            }
                                        }
                                        children.setSelection(chp);


                                        int bp = 0;
                                        for (int i = 0; i < chi.size(); i++) {
                                            if (item.get(0).getBelowsix().equals(chi.get(i))) {
                                                bp = i;
                                            }
                                        }
                                        below6.setSelection(bp);

                                        int sp = 0;
                                        for (int i = 0; i < chi.size(); i++) {
                                            if (item.get(0).getSixtofourteen().equals(chi.get(i))) {
                                                sp = i;
                                            }
                                        }
                                        sixto14.setSelection(sp);

                                        int fp = 0;
                                        for (int i = 0; i < chi.size(); i++) {
                                            if (item.get(0).getFifteentoeighteen().equals(chi.get(i))) {
                                                fp = i;
                                            }
                                        }
                                        fifteento18.setSelection(fp);

                                        int gop = 0;
                                        for (int i = 0; i < chi.size(); i++) {
                                            if (item.get(0).getGoingtoschool().equals(chi.get(i))) {
                                                gop = i;
                                            }
                                        }
                                        goingtoschool.setSelection(gop);

                                        int gop1 = 0;
                                        for (int i = 0; i < chi.size(); i++) {
                                            if (item.get(0).getGoingtoschool2().equals(chi.get(i))) {
                                                gop1 = i;
                                            }
                                        }
                                        goingtoschool2.setSelection(gop1);


                                    }
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }




                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call8 = cr.getProof(SharePreferenceUtils.getInstance().getString("lang"));

                    call8.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                prof.clear();
                                prof1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    prof.add(response.body().getData().get(i).getTitle());
                                    prof1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, prof);


                                    proof.setAdapter(adapter6);

                                    int cp2 = 0;
                                    for (int i = 0; i < prof1.size(); i++) {
                                        if (item.get(0).getIdProof().equals(prof1.get(i))) {
                                            cp2 = i;
                                        }
                                    }
                                    proof.setSelection(cp2);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call9 = cr.getCerts(SharePreferenceUtils.getInstance().getString("lang"));

                    call9.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                cer.clear();
                                cer1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    cer.add(response.body().getData().get(i).getTitle());
                                    cer1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, cer);


                                    certified.setAdapter(adapter6);

                                    int cp2 = 0;
                                    for (int i = 0; i < cer1.size(); i++) {
                                        if (item.get(0).getCertified().equals(cer1.get(i))) {
                                            cp2 = i;
                                        }
                                    }
                                    certified.setSelection(cp2);

                                    certified.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            cert = cer1.get(i);

                                            if (cert.equals("2"))
                                            {
                                                certificate_number_title.setVisibility(View.VISIBLE);
                                                skill_level_title.setVisibility(View.VISIBLE);
                                                certificate_number.setVisibility(View.VISIBLE);
                                                skill_level.setVisibility(View.VISIBLE);

                                            }
                                            else
                                            {
                                                certificate_number_title.setVisibility(View.GONE);
                                                skill_level_title.setVisibility(View.GONE);
                                                certificate_number.setVisibility(View.GONE);
                                                skill_level.setVisibility(View.GONE);

                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call10 = cr.getSkillLevel(SharePreferenceUtils.getInstance().getString("lang"));

                    call10.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                ski.clear();
                                ski1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    ski.add(response.body().getData().get(i).getTitle());
                                    ski1.add(response.body().getData().get(i).getId());

                                }

                                try {
                                    ArrayAdapter<String> adapter6 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                            R.layout.spinner_model, ski);


                                    skill_level.setAdapter(adapter6);

                                    int cp2 = 0;
                                    for (int i = 0; i < ski1.size(); i++) {
                                        if (item.get(0).getSkillLevel().equals(ski1.get(i))) {
                                            cp2 = i;
                                        }
                                    }
                                    skill_level.setSelection(cp2);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    int cp12 = 0;
                    for (int i = 0; i < agg.size(); i++) {
                        if (item.get(0).getAge().equals(agg.get(i))) {
                            cp12 = i;
                        }
                    }
                    age.setSelection(cp12);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }












                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(@NotNull Call<WorkerByIdListBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }
}
