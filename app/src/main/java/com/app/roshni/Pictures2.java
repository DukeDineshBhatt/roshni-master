package com.app.roshni;

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

import com.app.roshni.contractorPOJO.Data;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.samplePOJO.sampleBean;
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

public class Pictures2 extends Fragment {


    RecyclerView grid;
    StaggeredGridLayoutManager manager;
    Button upload , finish;
    ProgressBar progress;
    List<com.app.roshni.samplePOJO.Datum> list;
    SampleAdapter adapter;
    ImageView nodata;
    TextView txtStatus;

    private CustomViewPager pager;
    String user_id;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contr_samp_prof2 , container , false);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        upload = view.findViewById(R.id.button16);
        finish = view.findViewById(R.id.button15);
        progress = view.findViewById(R.id.progressBar3);
        nodata = view.findViewById(R.id.imageView5);

        user_id = SharePreferenceUtils.getInstance().getString("user");

        manager = new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL);
        adapter = new SampleAdapter(getContext() , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<contractorBean> call = cr.getContractorById(user_id);


        call.enqueue(new Callback<contractorBean>() {
            @Override
            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                Data item = response.body().getData();


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<contractorBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

                return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<sampleBean> call = cr.getSamples(SharePreferenceUtils.getInstance().getString("user"));

        call.enqueue(new Callback<sampleBean>() {
            @Override
            public void onResponse(Call<sampleBean> call, Response<sampleBean> response) {

                if (response.body().getData().size() > 0)
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
            public void onFailure(Call<sampleBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }

    class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder>
    {
        Context context;
        List<com.app.roshni.samplePOJO.Datum> list = new ArrayList<>();


        SampleAdapter(Context context, List<com.app.roshni.samplePOJO.Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.app.roshni.samplePOJO.Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.sample_list_model2 , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final com.app.roshni.samplePOJO.Datum item = list.get(position);

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

            ImageView image;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);


            }
        }
    }

}
