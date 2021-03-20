package com.workersjoint.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.workersjoint.app.workerListPOJO.Datum;
import com.workersjoint.app.workerListPOJO.workerListBean;

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

public class ContractorApplicants extends AppCompatActivity {

    RecyclerView grid;
    GridLayoutManager manager;
    JobsAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    ImageView nodata;
    Toolbar toolbar;
    String jid , title , category , salary , posted , sts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_applicants);


        jid = getIntent().getStringExtra("jid");
        title = getIntent().getStringExtra("title");
        category = getIntent().getStringExtra("category");
        salary = getIntent().getStringExtra("salary");
        posted = getIntent().getStringExtra("posted");
        sts = getIntent().getStringExtra("sts");

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar3);
        nodata = findViewById(R.id.imageView5);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.applicants));


        adapter = new JobsAdapter(this , list);
        manager = new GridLayoutManager(this, 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

    }


    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<workerListBean> call = cr.getAppliedContractors(jid , SharePreferenceUtils.getInstance().getString("lang"));

        call.enqueue(new Callback<workerListBean>() {
            @Override
            public void onResponse(@NotNull Call<workerListBean> call, @NotNull Response<workerListBean> response) {

                if (Objects.requireNonNull(response.body()).getData().size() > 0)
                {
                    nodata.setVisibility(View.GONE);
                }
                else
                {
                    nodata.setVisibility(View.VISIBLE);
                }


                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<workerListBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {
        final Context context;
        List<Datum> list;

        JobsAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = Objects.requireNonNull(inflater).inflate(R.layout.worker_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);

            holder.reg.setAllCaps(true);

            holder.name.setText(item.getName());
            holder.skill.setText(item.getSkills());
            holder.exp.setText(item.getExperience());
            holder.emp.setText(item.getEmployment());
            holder.reg.setText(item.getStatus());
            holder.address.setText(item.getCstreet() + ", " + item.getCarea() + ", " + item.getCdistrict() + ", " + item.getCstate() + "-" + item.getCpin());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ContractorApplicantDetails.class);
                    intent.putExtra("jid" , item.getUserId());
                    intent.putExtra("id" , jid);
                    intent.putExtra("status" , item.getStatus());
                    intent.putExtra("title" , title);
                    intent.putExtra("category" , category);
                    intent.putExtra("salary" , salary);
                    intent.putExtra("posted" , posted);
                    intent.putExtra("sts" , sts);
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView name;
            final TextView address;
            final TextView skill;
            final TextView exp;
            final TextView emp;
            final TextView reg;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textView20);
                address = itemView.findViewById(R.id.textView26);
                skill = itemView.findViewById(R.id.textView28);
                exp = itemView.findViewById(R.id.textView34);
                emp = itemView.findViewById(R.id.textView35);
                reg = itemView.findViewById(R.id.textView22);

            }
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
