package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class Register2 extends AppCompatActivity {

    TabLayout tabs;
    CustomViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        tabs = findViewById(R.id.tabLayout2);
        pager = findViewById(R.id.pager);

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

        tabs.getTabAt(0).setText("COMPANY");
        tabs.getTabAt(1).setText("PICTURES");

    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {



        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {

                brand frag = new brand();
                frag.setData(pager);
                return frag;


            } else {
                return new Pictures();

            }


        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
