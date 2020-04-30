package com.app.roshni;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.app.roshni.knowledgeDetailsPOJO.knowledgeDetailsBean;
import com.app.roshni.knowledgeListPOJO.Data;
import com.app.roshni.knowledgeListPOJO.knowledgeListBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Knowledge extends AppCompatActivity {
    Toolbar toolbar;
    static ProgressBar progress;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progressBar7);
        pager = findViewById(R.id.web);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.faqs));

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<knowledgeListBean> call = cr.getKnowledgeList("worker");

        call.enqueue(new Callback<knowledgeListBean>() {
            @Override
            public void onResponse(Call<knowledgeListBean> call, Response<knowledgeListBean> response) {

                PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager() , response.body().getData());
                pager.setAdapter(adapter);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<knowledgeListBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });



    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {
        List<Data> list = new ArrayList<>();

        public PagerAdapter(@NonNull FragmentManager fm , List<Data> list) {
            super(fm);
            this.list = list;
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            page frag = new page();
            Bundle b = new Bundle();
            b.putString("id" , list.get(position).getId());
            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    public static class page extends Fragment
    {

        String id;
        WebView web;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.page_layout , container , false);

            id = getArguments().getString("id");

            web = view.findViewById(R.id.web);

            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) getActivity().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<knowledgeDetailsBean> call = cr.getKnowledgeById(id);

            call.enqueue(new Callback<knowledgeDetailsBean>() {
                @Override
                public void onResponse(Call<knowledgeDetailsBean> call, Response<knowledgeDetailsBean> response) {

                    Log.d("content" , response.body().getData().getContent());
                    web.loadData(response.body().getData().getContent() , "text/html", "UTF-8");

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<knowledgeDetailsBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });



            return view;
        }
    }

}
