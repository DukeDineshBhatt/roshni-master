package com.app.roshni;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CompletedPersonal extends Fragment {

    private Spinner gender, category, religion, educational, marital, children, below6, sixto14, fifteento18, goingtoschool, proof;


    private EditText name, dob, cpin, cstate, cdistrict, carea, cstreet, ppin, pstate, pdistrict, parea, pstreet, editTxtProof, editTxtRelg, editTxtedu;

    private CircleImageView image;

    private String gend, cate, reli, educ, mari, chil, belo, sixt, fift, goin, id,prf;

    private CheckBox check;
    private File f1;
    private Uri uri;

    private boolean rel_bool = false;

    private boolean edu_bool = false;

    TextView txtStatus;
    private boolean che = false;

    private List<String> gen, cat, rel, edu, mar, chi, prof;

    private LinearLayout permanent, child;

    private Button upload, submit;

    private ProgressBar progress;

    private CustomViewPager pager;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_completed_personal, container, false);


        gen = new ArrayList<>();
        cat = new ArrayList<>();
        rel = new ArrayList<>();
        edu = new ArrayList<>();
        mar = new ArrayList<>();
        chi = new ArrayList<>();
        prof = new ArrayList<>();

        name = view.findViewById(R.id.editText);
        dob = view.findViewById(R.id.editText2);
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


        gen.add("Select one --- ");
        gen.add("Male");
        gen.add("Female");

        cat.add("Select one --- ");
        cat.add("SC");
        cat.add("ST");
        cat.add("OBC");
        cat.add("General");

        rel.add("Select one --- ");
        rel.add("Hindu");
        rel.add("Muslim");
        rel.add("Sikh");
        rel.add("Christian");
        rel.add("Others");

        edu.add("Select one --- ");
        edu.add("Uneducated");
        edu.add("Primary (Class 1-5)");
        edu.add("Middle (Class 6-8)");
        edu.add("Secondary (Class 9-10)");
        edu.add("Senior Secondary (Class 11-12)");
        edu.add("Graduation");
        edu.add("Post Graduation");
        edu.add("Others");

        mar.add("Select one --- ");
        mar.add("Single");
        mar.add("Married");
        mar.add("Divorcee");
        mar.add("Separated");

        chi.add("Select one --- ");
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

        prof.add("Select one --- ");
        prof.add("Aadhaar Card");
        prof.add("Voter ID");
        prof.add("PAN Card");
        prof.add("Driving License");
        prof.add("Passport");
        prof.add("Bank passbook");

        id = SharePreferenceUtils.getInstance().getString("user");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.spinner_model, gen);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, cat);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, rel);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, edu);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, mar);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, chi);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_model, prof);


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

        gender.setAdapter(adapter);
        category.setAdapter(adapter1);
        religion.setAdapter(adapter2);
        educational.setAdapter(adapter3);
        marital.setAdapter(adapter4);
        children.setAdapter(adapter5);
        below6.setAdapter(adapter5);
        sixto14.setAdapter(adapter5);
        fifteento18.setAdapter(adapter5);
        goingtoschool.setAdapter(adapter5);
        proof.setAdapter(adapter6);



        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    gend = gen.get(i);
                } else {
                    gend = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    cate = cat.get(i);
                } else {
                    cate = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                reli = rel.get(i);

                if (i == 5) {

                    rel_bool = true;

                    editTxtRelg.setVisibility(View.VISIBLE);


                } else {

                    rel_bool = false;
                    editTxtRelg.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        educational.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                educ = edu.get(i);

                if (i != 8) {

                    edu_bool = false;
                    editTxtedu.setVisibility(View.GONE);

                } else {

                    edu_bool = true;

                    editTxtedu.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        marital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mari = mar.get(i);

                    if (mari.equals("Single")) {
                        child.setVisibility(View.GONE);

                        chil = "0";
                        belo = "0";
                        sixt = "0";
                        fift = "0";
                        goin = "0";

                    } else {
                        child.setVisibility(View.VISIBLE);
                    }
                } else {
                    mari = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    chil = chi.get(i);
                } else {
                    chil = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        below6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    belo = chi.get(i);
                } else {
                    belo = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sixto14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    sixt = chi.get(i);
                } else {
                    sixt = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fifteento18.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    fift = chi.get(i);
                } else {
                    fift = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goingtoschool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    goin = chi.get(i);
                } else {
                    goin = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {

                    prf = prof.get(i);

                } else {

                    prf = "";

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        setPrevious();

        return view;

    }




    void setPrevious() {

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<WorkerByIdListBean> call = cr.getWorkerById1(id , SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<WorkerByIdListBean>() {
            @Override
            public void onResponse(Call<WorkerByIdListBean> call, Response<WorkerByIdListBean> response) {

                List<WorkerByIdData> item = response.body().getData();

                name.setText(item.get(0).getName());
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

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

                int cp = 0;
                for (int i = 0; i < prof.size(); i++) {
                    if (item.get(0).getIdProof().equals(prof.get(i))) {
                        cp = i;
                    }
                }
                proof.setSelection(cp);

                int gd = 0;
                for (int i = 0; i < gen.size(); i++) {
                    if (item.get(0).getGender().equals(gen.get(i))) {
                        gd = i;
                    }
                }
                gender.setSelection(gd);

                int ct = 0;
                for (int i = 0; i < cat.size(); i++) {
                    if (item.get(0).getCategory().equals(cat.get(i))) {
                        ct = i;
                    }
                }
                category.setSelection(ct);

                int re = 0;
                for (int i = 0; i < rel.size(); i++) {
                    if (item.get(0).getReligion().equals(rel.get(i))) {
                        re = i;
                        editTxtRelg.setText("");
                        break;
                    } else {
                        editTxtRelg.setVisibility(View.VISIBLE);
                        editTxtRelg.setText(item.get(0).getReligion());
                        re = 5;
                    }
                }
                religion.setSelection(re);

                int ed = 0;
                for (int i = 0; i < edu.size(); i++) {
                    if (item.get(0).getEducational().equals(edu.get(i))) {
                        ed = i;
                        editTxtedu.setText("");
                        break;
                    } else {
                        editTxtedu.setVisibility(View.VISIBLE);
                        editTxtedu.setText(item.get(0).getEducational());
                        ed = 8;
                    }
                }
                educational.setSelection(ed);

                int mt = 0;
                for (int j = 0; j < mar.size(); j++) {

                    if (item.get(0).getMarital().equals(mar.get(j))) {
                        mt = j;
                        child.setVisibility(View.GONE);
                        break;

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


                    }
                }
                marital.setSelection(mt);


                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<WorkerByIdListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }
}
