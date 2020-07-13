package com.app.roshni;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roshni.allWorkContrJobListPOJO.Datum;
import com.app.roshni.allWorkContrJobListPOJO.allWorkContrJobBean;

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

public class contractorActive extends Fragment {

    RecyclerView grid;
    GridLayoutManager manager;
    JobsAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    ImageView nodata;
    RadioGroup date;
    String dd;

    String sort = "DESC";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jobs_layout, container, false);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        date = view.findViewById(R.id.date);
        progress = view.findViewById(R.id.progressBar3);
        nodata = view.findViewById(R.id.imageView5);

        adapter = new JobsAdapter(getContext() , list);
        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        date.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.newest)
                {
                    sort = "DESC";
                }
                else
                {
                    sort = "ASC";
                }
                onResume();

            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<allWorkContrJobBean> call = cr.getAllContractorJobs(SharePreferenceUtils.getInstance().getString("user_id") , "Active" , sort);

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
            View view = Objects.requireNonNull(inflater).inflate(R.layout.worker_active_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);


            holder.category.setText("Job category: " + item.getRole());
            holder.title.setText(item.getTitle());
            holder.salary.setText("Days for completion: " + item.getSalary());
            holder.posted.setText("Posted on: " + item.getCreated());

            holder.applied.setText(item.getApplied() + " Applied");


            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ContractorJobDetails.class);
                    intent.putExtra("jid" , item.getId());
                    intent.putExtra("status" , item.getStatus());
                    startActivity(intent);

                }
            });

            holder.applicants.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ContractorApplicants.class);
                    intent.putExtra("jid" , item.getId());
                    intent.putExtra("title" , item.getTitle());
                    intent.putExtra("category" , item.getRole());
                    intent.putExtra("salary" , item.getSalary());
                    intent.putExtra("posted" , item.getCreated());
                    intent.putExtra("sts" , "Active");
                    startActivity(intent);

                }
            });

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

}
