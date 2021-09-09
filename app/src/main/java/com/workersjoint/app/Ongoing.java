package com.workersjoint.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Ongoing extends Fragment {

    String id;
    OngoingAdapter adapter;
    GridLayoutManager manager;
    ImageView nodata;
    ProgressBar progress;

    List<Datum> list;

    RecyclerView grid;


    void setData(CustomViewPager pager) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ongoing, container, false);

        id = SharePreferenceUtils.getInstance().getString("id");

        grid = view.findViewById(R.id.grid);
        nodata = view.findViewById(R.id.imageView5);
        progress = view.findViewById(R.id.progressBar3);

        list = new ArrayList<>();

        adapter = new OngoingAdapter(getContext() , list);
        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);



        return view;


    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) requireContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<OngoingListBean> call = cr.getOngoingSurvey(id);

        call.enqueue(new Callback<OngoingListBean>() {
            @Override
            public void onResponse(@NotNull Call<OngoingListBean> call, @NotNull Response<OngoingListBean> response) {

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
            public void onFailure(@NotNull Call<OngoingListBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.ViewHolder> {
        final Context context;
        List<Datum> list;

        OngoingAdapter(Context context, List<Datum> list) {
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
            View view = Objects.requireNonNull(inflater).inflate(R.layout.ongoing_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);

            holder.phone.setText(item.getPhone());
            holder.type.setText(item.getType());
            holder.date.setText(item.getCreated());
            holder.status.setText(item.getStatus());
            List<String> adrt = new ArrayList<>();
            adrt.add(item.getCstreet());
            adrt.add(item.getCarea());
            adrt.add(item.getCdistrict());
            adrt.add(item.getCstate());
            adrt.add(item.getCpin());
            adrt.removeAll(Collections.singleton(""));
            holder.address.setText(TextUtils.join(", ", adrt));
            //holder.address.setText(item.getCstreet()+" , "+item.getCarea()+" , "+item.getCdistrict()+" , "+item.getCstate()+" , "+item.getCpin());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (item.getType().equals("worker")){

                        Intent intent = new Intent(context , Profile4.class);
                        SharePreferenceUtils.getInstance().saveString("user", item.getProfile_id());
                        SharePreferenceUtils.getInstance().saveString("survey_id", item.getId());
                        SharePreferenceUtils.getInstance().saveString("phone", item.getPhone());
                        startActivity(intent);

                    }else if (item.getType().equals("brand"))
                    {
                        Intent intent = new Intent(context , Profile5.class);
                        SharePreferenceUtils.getInstance().saveString("user", item.getProfile_id());
                        SharePreferenceUtils.getInstance().saveString("survey_id", item.getId());
                        SharePreferenceUtils.getInstance().saveString("phone", item.getPhone());
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(getContext() , Profile6.class);
                        SharePreferenceUtils.getInstance().saveString("user", item.getProfile_id());
                        SharePreferenceUtils.getInstance().saveString("survey_id", item.getId());
                        SharePreferenceUtils.getInstance().saveString("phone", item.getPhone());
                        startActivity(intent);

                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView phone;
            final TextView date;
            final TextView type;
            final TextView status;
            final TextView address;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                phone = itemView.findViewById(R.id.phone);
                date = itemView.findViewById(R.id.date);
                type = itemView.findViewById(R.id.type);
                status = itemView.findViewById(R.id.status);
                address = itemView.findViewById(R.id.address);


            }
        }
    }

}
