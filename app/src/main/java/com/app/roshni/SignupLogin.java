package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roshni.verifyPOJO.verifyBean;
import com.rilixtech.CountryCodePicker;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SignupLogin extends AppCompatActivity {

    Button login , signup;
    CountryCodePicker code;
    EditText phone;
    ProgressBar progress;
    TextView language , forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String languageToLoad  = SharePreferenceUtils.getInstance().getString("lang"); // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        login = findViewById(R.id.button);
        signup = findViewById(R.id.button2);
        code = findViewById(R.id.code);
        phone = findViewById(R.id.phone);
        progress = findViewById(R.id.progressBar);
        language = findViewById(R.id.textView47);
        forgot = findViewById(R.id.textView50);

        code.registerPhoneNumberTextView(phone);

        String languageToLoad1  = SharePreferenceUtils.getInstance().getString("lang");

        if (languageToLoad1.length() == 0)
        {

            Dialog dialog = new Dialog(SignupLogin.this);
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

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dialog dialog = new Dialog(SignupLogin.this);
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p = phone.getText().toString();

                if (p.length() == 10)
                {


                    final String pho = code.getFullNumber();

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Log.d("token" , SharePreferenceUtils.getInstance().getString("token"));

                    Call<verifyBean> call = cr.login(pho , SharePreferenceUtils.getInstance().getString("token"));
                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                Intent intent = new Intent(SignupLogin.this , OTP.class);
                                intent.putExtra("phone" , pho);
                                startActivity(intent);
                                Toast toast = Toast.makeText(SignupLogin.this, "Please verify OTP", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                                toast.show();
                                finish();

                            } else if(response.body().getStatus().equals("2"))
                            {
                                SharePreferenceUtils.getInstance().saveString("user_id", response.body().getMessage());
                                Toast toast = Toast.makeText(SignupLogin.this, "Please enter PIN to continue", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                                toast.show();
                                //Toast.makeText(SignupLogin.this, "Please enter PIN to continue", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupLogin.this, EnterPIN.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else
                            {
                                Toast toast = Toast.makeText(SignupLogin.this, response.body().getMessage(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                                toast.show();
                                //Toast.makeText(SignupLogin.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<verifyBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });



                }
                else
                {
                    Toast toast = Toast.makeText(SignupLogin.this, "Invalid contact number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                    toast.show();
                    //Toast.makeText(SignupLogin.this, "Invalid contact number", Toast.LENGTH_SHORT).show();
                }




            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p = phone.getText().toString();

                if (p.length() == 10)
                {


                    final String pho = code.getFullNumber();

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Log.d("token" , SharePreferenceUtils.getInstance().getString("token"));

                    Call<verifyBean> call = cr.login(pho , SharePreferenceUtils.getInstance().getString("token"));
                    call.enqueue(new Callback<verifyBean>() {
                        @Override
                        public void onResponse(Call<verifyBean> call, Response<verifyBean> response) {

                            if (response.body().getStatus().equals("1") || response.body().getStatus().equals("2"))
                            {

                                Intent intent = new Intent(SignupLogin.this , OTP3.class);
                                intent.putExtra("phone" , pho);
                                startActivity(intent);
                                Toast toast = Toast.makeText(SignupLogin.this, "Please verify OTP", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                                toast.show();
                                finish();

                            }
                            else
                            {
                                Toast toast = Toast.makeText(SignupLogin.this, response.body().getMessage(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                                toast.show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<verifyBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });



                }
                else
                {
                    Toast toast = Toast.makeText(SignupLogin.this, "Invalid contact number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
                    toast.show();
                }




            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupLogin.this , ChooseAccount.class);
                startActivity(intent);
            }
        });

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
