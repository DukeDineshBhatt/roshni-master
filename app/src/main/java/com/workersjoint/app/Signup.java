package com.workersjoint.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.workersjoint.app.verifyPOJO.verifyBean;
import com.rilixtech.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Signup extends AppCompatActivity {

    Button login , signup;
    CountryCodePicker code;
    EditText phone;
    ProgressBar progress;
    String type;

    TextView read;

    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        type = getIntent().getStringExtra("type");

        login = findViewById(R.id.button2);
        signup = findViewById(R.id.button);

        code = findViewById(R.id.code);
        phone = findViewById(R.id.phone);
        progress = findViewById(R.id.progressBar);
        read = findViewById(R.id.textView66);
        group = findViewById(R.id.radioGroup);

        code.registerPhoneNumberTextView(phone);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String p = phone.getText().toString();

                if (p.length() == 10)
                {

                    if (group.getCheckedRadioButtonId() != -1)
                    {

                        signup.setClickable(false);
                        signup.setFocusable(false);

                        final String pho = code.getFullNumber();

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<verifyBean> call = cr.worker_signup(pho , type , SharePreferenceUtils.getInstance().getString("token"));
                        call.enqueue(new Callback<verifyBean>() {
                            @Override
                            public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                if (Objects.requireNonNull(response.body()).getStatus().equals("1"))
                                {
                                    Intent intent = new Intent(Signup.this , OTP2.class);
                                    intent.putExtra("phone" , pho);
                                    startActivity(intent);
                                    Toast.makeText(Signup.this, "Please verify OTP", Toast.LENGTH_SHORT).show();
                                    //finishAffinity();

                                }
                                else
                                {
                                    Toast.makeText(Signup.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                signup.setClickable(true);
                                signup.setFocusable(true);

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                progress.setVisibility(View.GONE);
                                signup.setClickable(true);
                                signup.setFocusable(true);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(Signup.this, "Please accept above condition", Toast.LENGTH_SHORT).show();
                    }






                }
                else
                {
                    Toast.makeText(Signup.this, "Invalid contact number", Toast.LENGTH_SHORT).show();
                }



            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Signup.this , Web.class);
                intent.putExtra("title" , getString(R.string.terms_amp_conditions));
                intent.putExtra("url" , "https://mrtecks.com/goodbusinessapp/terms.php");
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this , SignupLogin.class);
                startActivity(intent);
                finishAffinity();
            }
        });

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
