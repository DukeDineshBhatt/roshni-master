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

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CompletedProfile extends AppCompatActivity {

    TabLayout tabs;
    CustomViewPager pager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_profile);

        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabLayout2);
        pager = findViewById(R.id.pager);

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
        toolbar.setTitle("DETAILS");

        tabs.addTab(tabs.newTab().setText("PERSONAL"));
        tabs.addTab(tabs.newTab().setText("PROFESSIONAL"));


        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);


        Objects.requireNonNull(tabs.getTabAt(0)).setText("PERSONAL");
        Objects.requireNonNull(tabs.getTabAt(1)).setText("PROFESSIONAL");

    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                CompletedPersonal frag = new CompletedPersonal();
                frag.setData(pager);
                return frag;
            } else {
                return new CompletedProfessional();
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
