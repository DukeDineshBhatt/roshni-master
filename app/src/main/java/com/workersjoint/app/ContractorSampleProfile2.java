package com.workersjoint.app;

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

import com.workersjoint.app.contractorPOJO.Data;
import com.workersjoint.app.contractorPOJO.contractorBean;
import com.workersjoint.app.samplePOJO.sampleBean;
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

public class ContractorSampleProfile2 extends Fragment {


    RecyclerView grid;
    StaggeredGridLayoutManager manager;
    Button upload , finish;
    ProgressBar progress;
    List<com.workersjoint.app.samplePOJO.Datum> list;
    SampleAdapter adapter;
    ImageView nodata;
    TextView txtStatus;

    String user_id;

    void setData(CustomViewPager pager) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contr_samp_prof3 , container , false);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        upload = view.findViewById(R.id.button16);
        finish = view.findViewById(R.id.button15);
        progress = view.findViewById(R.id.progressBar3);
        nodata = view.findViewById(R.id.imageView5);

        user_id = SharePreferenceUtils.getInstance().getString("user");

        manager = new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL);
        adapter = new SampleAdapter(getContext(), list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

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

        Call<com.workersjoint.app.samplePOJO.sampleBean> call = cr.getSamples(SharePreferenceUtils.getInstance().getString("user"));

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
        List<com.workersjoint.app.samplePOJO.Datum> list;


        SampleAdapter(Context context, List<com.workersjoint.app.samplePOJO.Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.workersjoint.app.samplePOJO.Datum> list)
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

            final com.workersjoint.app.samplePOJO.Datum item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getFile() , holder.image , options);



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
