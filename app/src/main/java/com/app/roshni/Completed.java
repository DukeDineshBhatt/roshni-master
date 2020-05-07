package com.app.roshni;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Completed extends Fragment {

    private CustomViewPager pager;
    String id;
    CompletedAdapter adapter;
    GridLayoutManager manager;
    ImageView nodata;
    ProgressBar progress;

    List<Datum> list;

    RecyclerView grid;


    void setData(CustomViewPager pager) {
        this.pager = pager;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_completeed, container, false);


        id = SharePreferenceUtils.getInstance().getString("id");

        grid = view.findViewById(R.id.grid);
        nodata = view.findViewById(R.id.imageView5);
        progress = view.findViewById(R.id.progressBar3);

        list = new ArrayList<>();

        adapter = new CompletedAdapter(getContext() , list);
        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);



        return view;


    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<CompletedListBean> call = cr.getCompletedSurvey(id);

        call.enqueue(new Callback<CompletedListBean>() {
            @Override
            public void onResponse(Call<CompletedListBean> call, Response<CompletedListBean> response) {

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
            public void onFailure(Call<CompletedListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {
        Context context;
        List<Datum> list = new ArrayList<>();

        CompletedAdapter(Context context, List<Datum> list) {
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
        public CompletedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.ongoing_list_model, parent, false);
            return new CompletedAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompletedAdapter.ViewHolder holder, int position) {

            final Datum item = list.get(position);


            holder.phone.setText(item.getPhone());
            holder.type.setText(item.getType());
            holder.date.setText(item.getCreated());
            holder.status.setText(item.getStatus());
            holder.address.setText(item.getCstreet()+" , "+item.getCarea()+" , "+item.getCdistrict()+" , "+item.getCstate()+" , "+item.getCpin());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (item.getType().equals("worker")){

                        Intent intent = new Intent(context , CompletedProfile.class);
                        SharePreferenceUtils.getInstance().saveString("user", item.getProfile_id());
                        startActivity(intent);

                    }else if (item.getType().equals("brand"))
                    {
                        /*Intent intent = new Intent(context , CompletedBrandProfile.class);
                        SharePreferenceUtils.getInstance().saveString("user_id", item.getProfile_id());
                        startActivity(intent);*/

                    }
                    else
                    {
                        /*Intent intent = new Intent(getContext() , CompletedContractorProfile.class);
                        SharePreferenceUtils.getInstance().saveString("user_id", item.getProfile_id());

                        startActivity(intent);
*/
                    }

                }
            });



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView phone,date,type,status,address;

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
