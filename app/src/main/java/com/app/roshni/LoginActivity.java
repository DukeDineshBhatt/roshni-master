package com.app.roshni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.roshni.verify2POJO.Data;
import com.app.roshni.verify2POJO.verifyBean2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button button;
    EditText username, password;
    ProgressBar progressBar;
    String Susername, Spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = (findViewById(R.id.password));
        username = (findViewById(R.id.username));
        button = (findViewById(R.id.button));
        progressBar = (findViewById(R.id.progressBar));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Susername = username.getText().toString();
                Spassword = password.getText().toString();

                if (Susername.length() > 0){
                    if (Spassword.length() > 0){

                        progressBar.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<verifyBean2> call = cr.login2(Susername, Spassword);
                        call.enqueue(new Callback<verifyBean2>() {
                            @Override
                            public void onResponse(Call<verifyBean2> call, Response<verifyBean2> response) {

                                if (response.body().getStatus().equals("1")) {

                                    Data item = response.body().getData();

                                    SharePreferenceUtils.getInstance().saveString("user_id", item.getId());
                                    SharePreferenceUtils.getInstance().saveString("name", item.getName());
                                    SharePreferenceUtils.getInstance().saveString("type", "officer");

                                    Intent intent = new Intent(LoginActivity.this, MainActivity4.class);
                                    //intent.putExtra("id" , item.getId());
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(LoginActivity.this, "Invalid details", Toast.LENGTH_SHORT).show();

                                }

                                progressBar.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<verifyBean2> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });


                    }else
                    {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();

                    }


                }else
                {
                    Toast.makeText(LoginActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();

                }




            }
        });


    }
}
