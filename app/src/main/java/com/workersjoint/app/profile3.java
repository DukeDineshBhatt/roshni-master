package com.workersjoint.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class profile3 extends Fragment {
    TabLayout tabs;
    CustomViewPager pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        tabs = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager);

        tabs.addTab(tabs.newTab().setText("CONTRACTOR"));
        tabs.addTab(tabs.newTab().setText("SAMPLES"));


        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        pager.setPagingEnabled(true);


        Objects.requireNonNull(tabs.getTabAt(0)).setText("CONTRACTOR");
        Objects.requireNonNull(tabs.getTabAt(1)).setText("SAMPLES");

        return view;
    }

    static class PagerAdapter extends FragmentStatePagerAdapter {


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new ContractorPersonalProfile();
            } else {
                return new ContractorSampleProfile();
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
