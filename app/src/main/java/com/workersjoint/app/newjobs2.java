package com.workersjoint.app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workersjoint.app.workerJobListPOJO.Datum;
import com.workersjoint.app.workerJobListPOJO.workerJobListBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;

public class newjobs2 extends Fragment {

    RecyclerView grid;
    GridLayoutManager manager;
    JobsAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    ImageView nodata;
    TextView date;
    String dd;
    List<String> lo , se;
    FloatingActionButton filter;

    String sort = "DESC";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jobs_layout3, container, false);

        list = new ArrayList<>();
        lo = new ArrayList<>();

        se = new ArrayList<>();


        grid = view.findViewById(R.id.grid);
        filter = view.findViewById(R.id.floatingActionButton2);
        date = view.findViewById(R.id.date);
        progress = view.findViewById(R.id.progressBar3);
        nodata = view.findViewById(R.id.imageView5);

        adapter = new JobsAdapter(getContext() , list);
        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);




        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_dialog);
                dialog.show();


                final DatePicker picker = dialog.findViewById(R.id.date);
                Button ok = dialog.findViewById(R.id.ok);

                long now = System.currentTimeMillis() - 1000;
                picker.setMaxDate(now);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int year = picker.getYear();
                        int month = picker.getMonth();
                        int day = picker.getDayOfMonth();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = format.format(calendar.getTime());

                        dialog.dismiss();

                        date.setText("Date - " + strDate + " (click to change)");

                        dd = strDate;

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<workerJobListBean> call = cr.getJobListForContractor(SharePreferenceUtils.getInstance().getString("user_id") , dd , SharePreferenceUtils.getInstance().getString("lang"), sort);

                        call.enqueue(new Callback<workerJobListBean>() {
                            @Override
                            public void onResponse(@NotNull Call<workerJobListBean> call, @NotNull Response<workerJobListBean> response) {

                                if (Objects.requireNonNull(response.body()).getData().size() > 0)
                                {
                                    nodata.setVisibility(View.GONE);
                                }
                                else
                                {
                                    nodata.setVisibility(View.VISIBLE);
                                }


                                list = response.body().getData();
                                adapter.setData(list);

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(@NotNull Call<workerJobListBean> call, @NotNull Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });



                    }
                });


            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String skil = TextUtils.join(",", lo);

                String expe = TextUtils.join(",", se);




                Log.d("skills1" , skil);

                Log.d("experience1" , expe);





                Intent intent = new Intent(getContext() , FilterContractorJob.class);
                intent.putExtra("location" , skil);
                intent.putExtra("date" , date1);
                intent.putExtra("sector" , expe);
                intent.putExtra("sort" , sort);
                startActivityForResult(intent , 123);

            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        Log.d("dddd", formattedDate);

        date.setText("Date - " + formattedDate + " (click to change)");

        dd = formattedDate;

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


        Call<workerJobListBean> call = cr.getJobListForContractor(SharePreferenceUtils.getInstance().getString("user_id") , date1, SharePreferenceUtils.getInstance().getString("lang"), sort);

        call.enqueue(new Callback<workerJobListBean>() {
            @Override
            public void onResponse(@NotNull Call<workerJobListBean> call, @NotNull Response<workerJobListBean> response) {

                if (Objects.requireNonNull(response.body()).getData().size() > 0)
                {
                    nodata.setVisibility(View.GONE);
                }
                else
                {
                    nodata.setVisibility(View.VISIBLE);
                }



                lo.clear();
                se.clear();


                list.clear();


                list = response.body().getData();
                List<Datum> ll1;
                List<Datum> ll2 = new ArrayList<>();

                ll1 = list;

                if (loca1.length() > 0)
                {
                    String [] ski1 = loca1.split(",");
                    lo.addAll(Arrays.asList(ski1));

                    ll2.clear();

                    for (int i = 0 ; i < ll1.size() ; i++)
                    {
                        for (int j = 0 ; j < lo.size() ; j++)
                        {

                            //Log.d("sec" , sk.get(j));
                            //Log.d("sec1" , ll1.get(i).getJobType());
                            if (ll1.get(i).getPlace().equals(lo.get(j)))
                            {
                                ll2.add(ll1.get(i));
                            }
                        }
                    }

                    ll1 = ll2;

                }
                else
                {
                    lo.clear();
                    ll1 = list;
                }







                if (sect1.length() > 0)
                {
                    String [] exp1 = sect1.split(",");
                    se.addAll(Arrays.asList(exp1));

                    ll2.clear();

                    for (int i = 0 ; i < ll1.size() ; i++)
                    {
                        for (int j = 0 ; j < se.size() ; j++)
                        {
                            if (ll1.get(i).getSector1().equals(se.get(j)))
                            {
                                ll2.add(ll1.get(i));
                            }
                        }
                    }

                    ll1 = ll2;

                }
                else
                {
                    se.clear();
                }







                Log.d("lll1" , String.valueOf(ll1.size()));

                adapter.setData(ll1);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<workerJobListBean> call, @NotNull Throwable t) {
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
            View view = Objects.requireNonNull(inflater).inflate(R.layout.job_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);


            holder.company.setText(item.getPlace());
            holder.title.setText(item.getTitle());
            holder.salary.setText("Piece rate: " + item.getSalary());
            holder.address.setText(item.getBrandStreet() + ", " + item.getBrandArea());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , JobDetails2.class);
                    intent.putExtra("jid" , item.getId());
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
            final TextView company;
            final TextView address;
            final TextView salary;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.textView20);
                company = itemView.findViewById(R.id.textView26);
                address = itemView.findViewById(R.id.textView28);
                salary = itemView.findViewById(R.id.textView22);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK)
        {

            loca1 = data.getStringExtra("location");

            sect1 = data.getStringExtra("sector");

            date1 = data.getStringExtra("date");

            sort = data.getStringExtra("sort");






            Log.d("skills" , loca1);

            Log.d("experience" , sect1);







        }
    }

    private String loca1 = "" , sect1 = "" , date1 = "";

}
