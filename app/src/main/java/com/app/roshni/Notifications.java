package com.app.roshni;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.roshni.notificationBean.Datum;
import com.app.roshni.notificationBean.notificationBean;

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

public class Notifications extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;
    GridLayoutManager manager;
    JobsAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    ImageView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        SharePreferenceUtils.getInstance().saveInt("count" , 0);

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
        toolbar.setTitle(getString(R.string.notifications));

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



        if (SharePreferenceUtils.getInstance().getString("type").equals("worker"))
        {
            Call<notificationBean> call = cr.getWorkerNoti(SharePreferenceUtils.getInstance().getString("user_id"));

            call.enqueue(new Callback<notificationBean>() {
                @Override
                public void onResponse(@NotNull Call<notificationBean> call, @NotNull Response<notificationBean> response) {

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
                public void onFailure(@NotNull Call<notificationBean> call, @NotNull Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });
        }
        else if (SharePreferenceUtils.getInstance().getString("type").equals("brand"))
        {
            Call<notificationBean> call = cr.getBrandNoti(SharePreferenceUtils.getInstance().getString("user_id"));

            call.enqueue(new Callback<notificationBean>() {
                @Override
                public void onResponse(@NotNull Call<notificationBean> call, @NotNull Response<notificationBean> response) {

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
                public void onFailure(@NotNull Call<notificationBean> call, @NotNull Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            Call<notificationBean> call = cr.getContractorNoti(SharePreferenceUtils.getInstance().getString("user_id"));

            call.enqueue(new Callback<notificationBean>() {
                @Override
                public void onResponse(@NotNull Call<notificationBean> call, @NotNull Response<notificationBean> response) {

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
                public void onFailure(@NotNull Call<notificationBean> call, @NotNull Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });
        }





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
            View view = Objects.requireNonNull(inflater).inflate(R.layout.notification_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);


            holder.text.setText(item.getText());
            holder.date.setText(item.getCreated());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (SharePreferenceUtils.getInstance().getString("type").equals("worker"))
                    {

                        if (item.getType().equals("worker_job"))
                        {
                            Intent intent = new Intent(context , JobDetails.class);
                            intent.putExtra("jid" , item.getType_id());
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(context , MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }


                    }
                    else if (SharePreferenceUtils.getInstance().getString("type").equals("brand"))
                    {

                        if (item.getType().equals("worker_job"))
                        {
                            Intent intent = new Intent(context , WorkerApplicants.class);
                            intent.putExtra("jid" , item.getType_id());
                            intent.putExtra("title" , item.getTitle());
                            intent.putExtra("category" , item.getCategory());
                            intent.putExtra("salary" , item.getSalary());
                            intent.putExtra("posted" , item.getCreated());
                            intent.putExtra("sts" , "Active");
                            startActivity(intent);
                        }
                        else if (item.getType().equals("contractor_job"))
                        {
                            Intent intent = new Intent(context , ContractorApplicants.class);
                            intent.putExtra("jid" , item.getType_id());
                            intent.putExtra("title" , item.getTitle());
                            intent.putExtra("category" , item.getCategory());
                            intent.putExtra("salary" , item.getSalary());
                            intent.putExtra("posted" , item.getCreated());
                            intent.putExtra("sts" , "Active");
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(context , MainActivity2.class);
                            startActivity(intent);
                            finishAffinity();
                        }


                    }
                    else if (SharePreferenceUtils.getInstance().getString("type").equals("contractor"))
                    {


                        if (item.getType().equals("worker_job"))
                        {
                            Intent intent = new Intent(context , JobDetails2.class);
                            intent.putExtra("jid" , item.getType_id());
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(context , MainActivity3.class);
                            startActivity(intent);
                            finishAffinity();
                        }



                    }



                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView text;
            final TextView date;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.textView20);
                date = itemView.findViewById(R.id.textView22);

            }
        }
    }

}
