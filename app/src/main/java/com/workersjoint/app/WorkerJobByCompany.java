package com.workersjoint.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import com.workersjoint.app.allWorkContrJobListPOJO.Datum;
import com.workersjoint.app.allWorkContrJobListPOJO.allWorkContrJobBean;

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

public class WorkerJobByCompany extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;
    GridLayoutManager manager;
    JobsAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    ImageView nodata;

    String bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_job_by_company);

        bid = getIntent().getStringExtra("bid");

        list = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar3);


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
        toolbar.setTitle(getString(R.string.comp_jobs));

        adapter = new JobsAdapter(this, list);
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


        Call<allWorkContrJobBean> call = cr.getAllWorkerJobs(bid , "Active" , "");

        call.enqueue(new Callback<allWorkContrJobBean>() {
            @Override
            public void onResponse(@NotNull Call<allWorkContrJobBean> call, @NotNull Response<allWorkContrJobBean> response) {

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
            public void onFailure(@NotNull Call<allWorkContrJobBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    static class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {
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
            View view = Objects.requireNonNull(inflater).inflate(R.layout.worker_active_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);


            holder.category.setText("Job category: " + item.getRole());
            holder.title.setText(item.getTitle());
            holder.salary.setText("Salary: " + item.getSalary());
            holder.posted.setText("Posted on: " + item.getCreated());

            holder.applied.setText(item.getApplied() + " Applied");

            holder.details.setVisibility(View.GONE);
            holder.applicants.setVisibility(View.GONE);

            /*holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , WorkerJobDetails.class);
                    intent.putExtra("jid" , item.getId());
                    intent.putExtra("status" , item.getStatus());
                    startActivity(intent);

                }
            });

            holder.applicants.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , WorkerApplicants.class);
                    intent.putExtra("jid" , item.getId());
                    startActivity(intent);

                }
            });*/

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView title;
            final TextView category;
            final TextView salary;
            final TextView posted;
            final TextView applied;
            final TextView applicants;
            final TextView details;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.textView20);
                category = itemView.findViewById(R.id.textView26);
                salary = itemView.findViewById(R.id.textView28);
                posted = itemView.findViewById(R.id.textView22);
                applied = itemView.findViewById(R.id.textView29);
                applicants = itemView.findViewById(R.id.textView38);
                details = itemView.findViewById(R.id.textView39);

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
