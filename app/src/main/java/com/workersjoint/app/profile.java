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

public class profile extends Fragment {
    TabLayout tabs;
    CustomViewPager pager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout , container , false);

        tabs = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager);

        tabs.addTab(tabs.newTab().setText("PERSONAL"));
        tabs.addTab(tabs.newTab().setText("PROFESSIONAL"));


        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        pager.setPagingEnabled(true);


        Objects.requireNonNull(tabs.getTabAt(0)).setText("PERSONAL");
        Objects.requireNonNull(tabs.getTabAt(1)).setText("PROFESSIONAL");

        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {



        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0)
            {
                WorkerPersonalProfile frag = new WorkerPersonalProfile();
                frag.setData(pager);
                return frag;
            }
            else
            {
                return new WorkerProfessionalProfile();
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
