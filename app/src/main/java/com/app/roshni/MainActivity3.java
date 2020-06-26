package com.app.roshni;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.contractorPOJO.contractorBean;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity3 extends AppCompatActivity {
    DrawerLayout drawer;
    ImageView toggle , notification;
    AHBottomNavigation bottom;

    TextView logout;

    CircleImageView image;
    TextView edit;

    TextView about , faq , policies , terms , support , language;

    TextView faqs , contact , unsubscribe;

    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        count = findViewById(R.id.textView69);
        drawer = findViewById(R.id.drawer);
        toggle = findViewById(R.id.imageButton);
        notification = findViewById(R.id.imageButton2);
        bottom = findViewById(R.id.bottomNavigationView);
        logout = findViewById(R.id.textView25);
        image = findViewById(R.id.imageView1);
        edit = findViewById(R.id.textView56);

        about = findViewById(R.id.textView59);
        faq = findViewById(R.id.textView60);
        policies = findViewById(R.id.textView61);
        terms = findViewById(R.id.textView62);
        support = findViewById(R.id.textView24);
        language = findViewById(R.id.textView58);

        faqs = findViewById(R.id.textView51);
        contact = findViewById(R.id.textView52);
        unsubscribe = findViewById(R.id.textView53);

        bottom.setBehaviorTranslationEnabled(false);
        bottom.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottom.setAccentColor(getResources().getColor(R.color.colorPrimary));

        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.navigation3);
        navigationAdapter.setupWithBottomNavigation(bottom);


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });

        /*edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , UpdateProfile.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });*/

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                    count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));

        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Web.class);
                intent.putExtra("title" ,  getString(R.string.faqs1));
                intent.putExtra("url" , "https://mrtecks.com/goodbusinessapp/faq.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Web.class);
                intent.putExtra("title" ,  getString(R.string.contact_us));
                intent.putExtra("url" , "https://mrtecks.com/goodbusinessapp/contact.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity3.this , Notifications.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(MainActivity3.this);
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FirebaseInstanceId.getInstance().deleteInstanceId();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        SharePreferenceUtils.getInstance().deletePref();

                        Intent intent = new Intent(MainActivity3.this , Splash.class);
                        startActivity(intent);
                        finishAffinity();

                    }
                });





            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Web.class);
                intent.putExtra("title" ,  getString(R.string.about_us));
                intent.putExtra("url" , "https://mrtecks.com/roshni/about.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Web.class);
                intent.putExtra("title" , getString(R.string.faqs));
                intent.putExtra("url" , "https://mrtecks.com/roshni/faq.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        policies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Web.class);
                intent.putExtra("title" , getString(R.string.policies));
                intent.putExtra("url" , "https://mrtecks.com/roshni/policies.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Web.class);
                intent.putExtra("title" , getString(R.string.terms_amp_conditions));
                intent.putExtra("url" , "https://mrtecks.com/roshni/terms.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity3.this , Support.class);
                intent.putExtra("title" , getString(R.string.support_help));
                intent.putExtra("url" , "https://mrtecks.com/roshni/support.php");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(GravityCompat.START);

                final Dialog dialog = new Dialog(MainActivity3.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.unsubscribe_dialog);
                dialog.setCancelable(true);
                dialog.show();


                final EditText feedback = dialog.findViewById(R.id.editText10);
                Button yes = dialog.findViewById(R.id.button25);
                Button no = dialog.findViewById(R.id.button26);
                final ProgressBar bar = dialog.findViewById(R.id.progressBar9);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String f = feedback.getText().toString();

                        bar.setVisibility(View.VISIBLE);

                        Bean b1 = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b1.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<contractorBean> call = cr.unsubscribe(
                                SharePreferenceUtils.getInstance().getString("user_id"),
                                f
                        );

                        call.enqueue(new Callback<contractorBean>() {
                            @Override
                            public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {

                                if (response.body().getStatus().equals("1"))
                                {
                                    Toast.makeText(MainActivity3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                FirebaseInstanceId.getInstance().deleteInstanceId();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();

                                    SharePreferenceUtils.getInstance().deletePref();

                                    Intent intent = new Intent(MainActivity3.this , Splash.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                bar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<contractorBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });

                    }
                });


            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(GravityCompat.START);

                Dialog dialog = new Dialog(MainActivity3.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.language_dialog);
                dialog.show();

                final Button en = dialog.findViewById(R.id.button17);
                final Button hi = dialog.findViewById(R.id.button18);

                String l = SharePreferenceUtils.getInstance().getString("lang");

                if (l.equals("en"))
                {
                    en.setBackground(getResources().getDrawable(R.drawable.green_back_round));
                    hi.setBackground(getResources().getDrawable(R.drawable.white_back_round));
                    en.setTextColor(Color.WHITE);
                    hi.setTextColor(Color.BLACK);
                }
                else
                {
                    en.setBackground(getResources().getDrawable(R.drawable.white_back_round));
                    hi.setBackground(getResources().getDrawable(R.drawable.green_back_round));
                    en.setTextColor(Color.BLACK);
                    hi.setTextColor(Color.WHITE);
                }

                en.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        en.setBackground(getResources().getDrawable(R.drawable.green_back_round));
                        hi.setBackground(getResources().getDrawable(R.drawable.white_back_round));
                        en.setTextColor(Color.WHITE);
                        hi.setTextColor(Color.BLACK);

                        String languageToLoad  = "en"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());

                        SharePreferenceUtils.getInstance().saveString("lang" , languageToLoad);

                        recreate();

                    }
                });

                hi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        en.setBackground(getResources().getDrawable(R.drawable.white_back_round));
                        hi.setBackground(getResources().getDrawable(R.drawable.green_back_round));
                        en.setTextColor(Color.BLACK);
                        hi.setTextColor(Color.WHITE);

                        String languageToLoad  = "hi"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());

                        SharePreferenceUtils.getInstance().saveString("lang" , languageToLoad);

                        recreate();

                    }
                });

            }
        });

        faq.setVisibility(View.GONE);

        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {


                switch (position) {
                    case 0:
                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction ft2 = fm2.beginTransaction();
                        newjobs2 test2 = new newjobs2();
                        ft2.replace(R.id.replace, test2);
                        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft2.commit();

                        return true;
                    case 1:
                        FragmentManager fm3 = getSupportFragmentManager();
                        FragmentTransaction ft3 = fm3.beginTransaction();
                        appliedjobs2 test3 = new appliedjobs2();
                        ft3.replace(R.id.replace, test3);
                        ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft3.commit();

                        return true;
                    case 2:
                        FragmentManager fm4 = getSupportFragmentManager();
                        FragmentTransaction ft4 = fm4.beginTransaction();
                        profile3 test4 = new profile3();
                        ft4.replace(R.id.replace, test4);
                        ft4.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft4.commit();

                        return true;
                }


                return true;
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        newjobs2 test = new newjobs2();
        ft.replace(R.id.replace, test);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //ft.addToBackStack(null);
        ft.commit();

        bottom.setCurrentItem(0);

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.equals(intent.getAction(), "photo")) {
                    Log.d("local" , "called");
                    onResume();
                }

            }
        };


        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("photo"));

    }

    void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    BroadcastReceiver singleReceiver;

    @Override
    protected void onResume() {
        super.onResume();

        count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));

        edit.setText(SharePreferenceUtils.getInstance().getString("name"));

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(SharePreferenceUtils.getInstance().getString("photo") , image , options);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(MainActivity3.this)
                .setTitle(getString(R.string.confirm))
                .setMessage(getString(R.string.close_app_text))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        finishAffinity();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .show();


    }
}
