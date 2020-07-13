package com.app.roshni;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.roshni.contractorPOJO.Data;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.samplePOJO.Datum;
import com.app.roshni.samplePOJO.sampleBean;
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

public class ContractorSampleProfile extends Fragment {


    RecyclerView grid;
    StaggeredGridLayoutManager manager;
    Button upload , finish;
    ProgressBar progress;
    List<Datum> list;
    SampleAdapter adapter;
    ImageView nodata;
    TextView txtStatus;

    String user_id;

    void setData(CustomViewPager pager) {
    }

    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contr_samp_prof , container , false);

        list = new ArrayList<>();

        swipe = view.findViewById(R.id.swipe);
        grid = view.findViewById(R.id.grid);
        upload = view.findViewById(R.id.button16);
        finish = view.findViewById(R.id.button15);
        progress = view.findViewById(R.id.progressBar3);
        nodata = view.findViewById(R.id.imageView5);
        txtStatus = view.findViewById(R.id.textViewStatus);

        user_id = SharePreferenceUtils.getInstance().getString("user_id");

        manager = new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL);
        adapter = new SampleAdapter(getActivity(), list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();

                Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                Call<contractorBean> call = cr.getContractorById(user_id, SharePreferenceUtils.getInstance().getString("lang"));


                call.enqueue(new Callback<contractorBean>() {
                    @Override
                    public void onResponse(@NotNull Call<contractorBean> call, @NotNull Response<contractorBean> response) {

                        Data item = Objects.requireNonNull(response.body()).getData();


                        switch (item.getStatus()) {
                            case "pending":

                                txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                                txtStatus.setVisibility(View.VISIBLE);
                                break;
                            case "rejected":
                                txtStatus.setText(item.getRejectReason());
                                txtStatus.setVisibility(View.VISIBLE);
                                break;
                            case "verified":
                                txtStatus.setText("YOUR PROFILE IS PENDING FOR APPROVAL");
                                txtStatus.setVisibility(View.VISIBLE);
                                break;
                            case "modifications":
                                txtStatus.setText(item.getRejectReason());
                                txtStatus.setVisibility(View.VISIBLE);
                                break;
                            default:
                                txtStatus.setVisibility(View.GONE);
                                break;
                        }


                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(@NotNull Call<contractorBean> call, @NotNull Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                swipe.setRefreshing(false);

            }
        });

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(user_id, SharePreferenceUtils.getInstance().getString("lang"));


        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorBean> call, @NotNull Response<contractorBean> response) {

                Data item = Objects.requireNonNull(response.body()).getData();


                switch (item.getStatus()) {
                    case "pending":

                        txtStatus.setText("YOUR PROFILE IS PENDING FOR VERIFICATION");
                        txtStatus.setVisibility(View.VISIBLE);
                        break;
                    case "rejected":
                        txtStatus.setText(item.getRejectReason());
                        txtStatus.setVisibility(View.VISIBLE);
                        break;
                    case "verified":
                        txtStatus.setText("YOUR PROFILE IS PENDING FOR APPROVAL");
                        txtStatus.setVisibility(View.VISIBLE);
                        break;
                    case "modifications":
                        txtStatus.setText(item.getRejectReason());
                        txtStatus.setVisibility(View.VISIBLE);
                        break;
                    default:
                        txtStatus.setVisibility(View.GONE);
                        break;
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<contractorBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

                return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getActivity()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<sampleBean> call = cr.getSamples(SharePreferenceUtils.getInstance().getString("user_id"));

        call.enqueue(new Callback<sampleBean>() {
            @Override
            public void onResponse(@NotNull Call<sampleBean> call, @NotNull Response<sampleBean> response) {

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
            public void onFailure(@NotNull Call<sampleBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }

    static class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder>
    {
        final Context context;
        List<Datum> list;


        SampleAdapter(Context context, List<Datum> list)
        {
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
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = Objects.requireNonNull(inflater).inflate(R.layout.sample_list_model2 , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getFile() , holder.image , options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item.getFile() , img , options);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            final ImageView image;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);


            }
        }
    }

}
