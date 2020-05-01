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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    String title;

    TextView count;

    ImageButton previous , next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        title = getIntent().getStringExtra("title");

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progressBar7);
        pager = findViewById(R.id.web);
        previous = findViewById(R.id.imageButton5);
        next = findViewById(R.id.imageButton6);
        count = findViewById(R.id.textView49);

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

        Call<knowledgeListBean> call = cr.getKnowledgeList(title);

        call.enqueue(new Callback<knowledgeListBean>() {
            @Override
            public void onResponse(Call<knowledgeListBean> call, Response<knowledgeListBean> response) {

                if (response.body().getData().size() > 0)
                {
                    previous.setVisibility(View.INVISIBLE);

                    count.setText("1 / " + response.body().getData().size());

                    PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager() , response.body().getData());
                    pager.setAdapter(adapter);

                    previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            pager.setCurrentItem(pager.getCurrentItem() - 1);

                        }
                    });

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            pager.setCurrentItem(pager.getCurrentItem() + 1);

                        }
                    });
                }
                else
                {
                    Toast.makeText(Knowledge.this, "No data found", Toast.LENGTH_SHORT).show();
                    finish();
                }



                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<knowledgeListBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                count.setText((position + 1) + " / " + pager.getAdapter().getCount());

                if (position == 0)
                {
                    previous.setVisibility(View.INVISIBLE);
                }
                else
                {
                    previous.setVisibility(View.VISIBLE);
                }

                if (position == pager.getAdapter().getCount() - 1)
                {
                    next.setVisibility(View.INVISIBLE);
                }
                else
                {
                    next.setVisibility(View.VISIBLE);
                }



                Log.d("pos" , String.valueOf(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

                    //Log.d("content" , response.body().getData().getContent());
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
