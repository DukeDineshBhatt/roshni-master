package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class CompletedContractorProfile extends AppCompatActivity {

    TabLayout tabs;
    CustomViewPager pager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_contractor_profile);

        tabs = findViewById(R.id.tabLayout2);
        pager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);

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
        toolbar.setTitle("DETAILS");

        tabs.addTab(tabs.newTab().setText("CONTRACTOR"));
        tabs.addTab(tabs.newTab().setText("SAMPLES"));


        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        pager.setPagingEnabled(true);


        tabs.getTabAt(0).setText("CONTRACTOR");
        tabs.getTabAt(1).setText("SAMPLES");

    }
    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                ContractorPersonalProfile2 frag = new ContractorPersonalProfile2();
                frag.setData(pager);
                return frag;
            } else {
                return new ContractorSampleProfile2();
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
