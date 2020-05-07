package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity4 extends AppCompatActivity {

    TabLayout tabs;
    CustomViewPager pager;
    DrawerLayout drawer;
    ImageView toggle;
    Toolbar toolbar;
    TextView about, logout, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        drawer = findViewById(R.id.drawer);
        toggle = findViewById(R.id.imageButton);
        tabs = findViewById(R.id.tabLayout2);
        pager = findViewById(R.id.pager);
        about = findViewById(R.id.textView59);
        logout = findViewById(R.id.textView25);
        name = findViewById(R.id.textView56);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("SURVEY OFFICER");

        tabs.addTab(tabs.newTab().setText("Ongoing"));
        tabs.addTab(tabs.newTab().setText("Completed"));

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        pager.setPagingEnabled(true);

        tabs.getTabAt(0).setText("Ongoing");
        tabs.getTabAt(1).setText("Completed");



        name.setText(SharePreferenceUtils.getInstance().getString("name"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(MainActivity4.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.quit_dialog_layout);
                dialog.show();

                Button ookk = dialog.findViewById(R.id.button2);
                Button canc = dialog.findViewById(R.id.button4);

                canc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ookk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        SharePreferenceUtils.getInstance().deletePref();

                        Intent intent = new Intent(MainActivity4.this, Splash.class);
                        startActivity(intent);
                        finishAffinity();

                    }
                });


            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity4.this, Web.class);
                intent.putExtra("title", getString(R.string.about_us));
                intent.putExtra("url", "https://mrtecks.com/roshni/about.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("DDD","DDD");

        name.setText(SharePreferenceUtils.getInstance().getString("name"));
    }


    void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }


    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Ongoing frag = new Ongoing();
                frag.setData(pager);
                return frag;
            } else {
                return new Completed();
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
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
