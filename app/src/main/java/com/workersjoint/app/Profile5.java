package com.workersjoint.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Profile5 extends AppCompatActivity {

    TabLayout tabs;
    CustomViewPager pager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile5);

        tabs = findViewById(R.id.tabLayout2);
        pager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);

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
        toolbar.setTitle("ENTER YOUR DETAILS");

        tabs.addTab(tabs.newTab().setText("COMPANY"));
        tabs.addTab(tabs.newTab().setText("PICTURES"));


        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        pager.setPagingEnabled(false);

        LinearLayout tabStrip = ((LinearLayout)tabs.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }

        Objects.requireNonNull(tabs.getTabAt(0)).setText("COMPANY");
        Objects.requireNonNull(tabs.getTabAt(1)).setText("PICTURES");

    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {



        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {

                brand3 frag = new brand3();
                frag.setData(pager);
                return frag;


            } else {
                Pictures3 frag = new Pictures3();
                frag.setData(pager);
                return frag;

            }


        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
