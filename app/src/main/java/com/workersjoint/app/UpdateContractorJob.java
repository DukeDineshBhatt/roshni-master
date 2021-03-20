package com.workersjoint.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.workersjoint.app.SkillsPOJO.skillsBean;
import com.workersjoint.app.contractorJobDetailsPOJO.Data;
import com.workersjoint.app.contractorJobDetailsPOJO.contractorJobDetailsBean;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verifyPOJO.verifyBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpdateContractorJob extends AppCompatActivity {

    Toolbar toolbar;
    Button submit;
    ProgressBar progress;
    Spinner type , experience , days, sector , place;
    EditText rate;

    ImageView image1, image2, image3, image4, image5;

    List<String> typ, typ1 , exp , exp1 , day , pla , pla1;

    String ty = "" , ex = "" , da = "" , sect = "" , plac = "";
    List<String> sec, sec1;
    private Uri uri1, uri2, uri3, uri4, uri5;
    private File f1, f2, f3, f4, f5;

    String jid;

    CheckBox display_name , phone , contact_person , email , all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contractor_job);

        jid = getIntent().getStringExtra("jid");

        typ = new ArrayList<>();
        typ1 = new ArrayList<>();
        exp = new ArrayList<>();
        exp1 = new ArrayList<>();
        day = new ArrayList<>();
        sec = new ArrayList<>();
        sec1 = new ArrayList<>();
        pla = new ArrayList<>();
        pla1 = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        sector = findViewById(R.id.sector);
        submit = findViewById(R.id.submit);
        progress = findViewById(R.id.progress);
        place = findViewById(R.id.location);
        display_name = findViewById(R.id.display_name);
        phone = findViewById(R.id.phone);
        contact_person = findViewById(R.id.contact_person);
        email = findViewById(R.id.email);
        all = findViewById(R.id.all);
        type = findViewById(R.id.type);
        experience = findViewById(R.id.experience);
        days = findViewById(R.id.days);
        rate = findViewById(R.id.rate);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);

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
        toolbar.setTitle("UPDATE JOB");

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                display_name.setChecked(isChecked);
                phone.setChecked(isChecked);
                contact_person.setChecked(isChecked);
                email.setChecked(isChecked);

            }
        });





        day.add("--- Select ---");
        for (int i = 1 ; i <=500 ; i++)
        {
            day.add(String.valueOf(i));
        }


        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                R.layout.spinner_model, day);


        days.setAdapter(adapter3);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    ty = typ1.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    ex = exp1.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                plac =  pla1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    da = day.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String r = rate.getText().toString();


                if (sect.length() > 0) {
                    if (ty.length() > 0) {
                        if (ex.length() > 0) {
                            if (da.length() > 0) {

                                MultipartBody.Part body1 = null;

                                try {

                                    RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                    body1 = MultipartBody.Part.createFormData("sample1", f1.getName(), reqFile1);


                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }


                                MultipartBody.Part body2 = null;

                                try {

                                    RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f2);
                                    body2 = MultipartBody.Part.createFormData("sample2", f2.getName(), reqFile1);


                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                MultipartBody.Part body3 = null;

                                try {

                                    RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f3);
                                    body3 = MultipartBody.Part.createFormData("sample3", f3.getName(), reqFile1);


                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                MultipartBody.Part body4 = null;

                                try {

                                    RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f4);
                                    body4 = MultipartBody.Part.createFormData("sample4", f4.getName(), reqFile1);


                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                MultipartBody.Part body5 = null;

                                try {

                                    RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f5);
                                    body5 = MultipartBody.Part.createFormData("sample5", f5.getName(), reqFile1);


                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                progress.setVisibility(View.VISIBLE);

                                Bean b = (Bean) getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<verifyBean> call = cr.UpdateContractorJob(
                                        jid,
                                        sect,
                                        ty,
                                        ex,
                                        da,
                                        r,
                                        plac,
                                        String.valueOf(display_name.isChecked()),
                                        String.valueOf(phone.isChecked()),
                                        String.valueOf(contact_person.isChecked()),
                                        String.valueOf(email.isChecked()),
                                        body1,
                                        body2,
                                        body3,
                                        body4,
                                        body5
                                );

                                call.enqueue(new Callback<verifyBean>() {
                                    @Override
                                    public void onResponse(@NotNull Call<verifyBean> call, @NotNull Response<verifyBean> response) {

                                        if (Objects.requireNonNull(response.body()).getStatus().equals("1"))
                                        {
                                            Toast.makeText(UpdateContractorJob.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            finish();

                                        }


                                        progress.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<verifyBean> call, @NotNull Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });


                            } else {
                                Toast.makeText(UpdateContractorJob.this, "Invalid days", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UpdateContractorJob.this, "Invalid experience", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpdateContractorJob.this, "Invalid job type", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(UpdateContractorJob.this, "Invalid sector", Toast.LENGTH_SHORT).show();
                }





            }
        });


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateContractorJob.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f1 = new File(file);
                            try {
                                f1.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(UpdateContractorJob.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateContractorJob.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f2 = new File(file);
                            try {
                                f2.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri2 = FileProvider.getUriForFile(Objects.requireNonNull(UpdateContractorJob.this), BuildConfig.APPLICATION_ID + ".provider", f2);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 3);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 4);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateContractorJob.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f3 = new File(file);
                            try {
                                f3.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri3 = FileProvider.getUriForFile(Objects.requireNonNull(UpdateContractorJob.this), BuildConfig.APPLICATION_ID + ".provider", f3);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri3);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 5);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 6);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateContractorJob.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f4 = new File(file);
                            try {
                                f4.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri4 = FileProvider.getUriForFile(Objects.requireNonNull(UpdateContractorJob.this), BuildConfig.APPLICATION_ID + ".provider", f4);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri4);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 7);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 8);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateContractorJob.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f5 = new File(file);
                            try {
                                f5.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri5 = FileProvider.getUriForFile(Objects.requireNonNull(UpdateContractorJob.this), BuildConfig.APPLICATION_ID + ".provider", f5);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri5);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 9);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 10);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

        setPrevious();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(UpdateContractorJob.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri1));

            Log.d("bitmap", String.valueOf(bmp));

            image1.setImageBitmap(bmp);

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            image1.setImageURI(uri1);
        }

        if (requestCode == 4 && resultCode == RESULT_OK && null != data) {
            uri2 = data.getData();

            Log.d("uri", String.valueOf(uri2));

            String ypath = getPath(UpdateContractorJob.this, uri2);
            assert ypath != null;
            f2 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri2));

            Log.d("bitmap", String.valueOf(bmp));

            image2.setImageBitmap(bmp);

        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            image2.setImageURI(uri2);
        }


        if (requestCode == 6 && resultCode == RESULT_OK && null != data) {
            uri3 = data.getData();

            Log.d("uri", String.valueOf(uri3));

            String ypath = getPath(UpdateContractorJob.this, uri3);
            assert ypath != null;
            f3 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri3));

            Log.d("bitmap", String.valueOf(bmp));

            image3.setImageBitmap(bmp);

        } else if (requestCode == 5 && resultCode == RESULT_OK) {
            image3.setImageURI(uri3);
        }


        if (requestCode == 8 && resultCode == RESULT_OK && null != data) {
            uri4 = data.getData();

            Log.d("uri", String.valueOf(uri4));

            String ypath = getPath(UpdateContractorJob.this, uri4);
            assert ypath != null;
            f4 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri4));

            Log.d("bitmap", String.valueOf(bmp));

            image4.setImageBitmap(bmp);

        } else if (requestCode == 7 && resultCode == RESULT_OK) {
            image4.setImageURI(uri4);
        }


        if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
            uri5 = data.getData();

            Log.d("uri", String.valueOf(uri5));

            String ypath = getPath(UpdateContractorJob.this, uri5);
            assert ypath != null;
            f5 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri5));

            Log.d("bitmap", String.valueOf(bmp));

            image5.setImageBitmap(bmp);

        } else if (requestCode == 9 && resultCode == RESULT_OK) {
            image5.setImageURI(uri5);
        }


    }

    private static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }


    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

    void setPrevious()
    {

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<contractorJobDetailsBean> call = cr.getJobDetailsForContractor(SharePreferenceUtils.getInstance().getString("user_id"), jid);

        call.enqueue(new Callback<contractorJobDetailsBean>() {
            @Override
            public void onResponse(@NotNull Call<contractorJobDetailsBean> call, @NotNull Response<contractorJobDetailsBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                    final Data item = response.body().getData();

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getSample1() , image1 , options);
                    loader.displayImage(item.getSample2() , image2 , options);
                    loader.displayImage(item.getSample3() , image3 , options);
                    loader.displayImage(item.getSample4() , image4 , options);
                    loader.displayImage(item.getSample5() , image5 , options);

                    rate.setText(item.getRate());

                    display_name.setChecked(Boolean.parseBoolean(item.getDisplayName()));
                    phone.setChecked(Boolean.parseBoolean(item.getDisplayPhone()));
                    contact_person.setChecked(Boolean.parseBoolean(item.getDisplayPerson()));
                    email.setChecked(Boolean.parseBoolean(item.getDisplayEmail()));

                    final Call<sectorBean> call2 = cr.getBrandSector(SharePreferenceUtils.getInstance().getString("lang") , SharePreferenceUtils.getInstance().getString("user_id"));

                    call2.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {


                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    sec.add(response.body().getData().get(i).getTitle());
                                    sec1.add(response.body().getData().get(i).getId());

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateContractorJob.this,
                                        R.layout.spinner_model, sec);

                                sector.setAdapter(adapter);

                                int cp2 = 0;
                                for (int i = 0; i < sec1.size(); i++) {
                                    if (item.getSector().equals(sec1.get(i))) {
                                        cp2 = i;
                                    }
                                }
                                sector.setSelection(cp2);

                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                    sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            sect = sec1.get(i);

                            progress.setVisibility(View.VISIBLE);

                            Call<skillsBean> call2 = cr.getRoles(sect, SharePreferenceUtils.getInstance().getString("lang"));
                            call2.enqueue(new Callback<skillsBean>() {
                                @Override
                                public void onResponse(@NotNull Call<skillsBean> call, @NotNull Response<skillsBean> response) {


                                    if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                        typ.clear();
                                        typ1.clear();

                                        for (int i = 0; i < response.body().getData().size(); i++) {

                                            typ.add(response.body().getData().get(i).getTitle());
                                            typ1.add(response.body().getData().get(i).getId());

                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateContractorJob.this,
                                                R.layout.spinner_model, typ);

                                        type.setAdapter(adapter);

                                        int cp = 0;
                                        for (int i = 0; i < typ1.size(); i++) {
                                            if (item.getJobType().equals(typ1.get(i))) {
                                                cp = i;
                                            }
                                        }
                                        type.setSelection(cp);

                                    }

                                    progress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(@NotNull Call<skillsBean> call, @NotNull Throwable t) {
                                    progress.setVisibility(View.GONE);
                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    final Call<sectorBean> call5 = cr.getExperience(SharePreferenceUtils.getInstance().getString("lang"));

                    call5.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                exp.clear();
                                exp1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    exp.add(response.body().getData().get(i).getTitle());
                                    exp1.add(response.body().getData().get(i).getId());

                                }

                                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(UpdateContractorJob.this,
                                        R.layout.spinner_model, exp);


                                experience.setAdapter(adapter2);

                                int cp2 = 0;
                                for (int i = 0; i < exp1.size(); i++) {
                                    if (item.getExperience().equals(exp1.get(i))) {
                                        cp2 = i;
                                    }
                                }
                                experience.setSelection(cp2);

                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                    final Call<sectorBean> call10 = cr.getPlace(SharePreferenceUtils.getInstance().getString("lang"));

                    call10.enqueue(new Callback<sectorBean>() {
                        @Override
                        public void onResponse(@NotNull Call<sectorBean> call, @NotNull Response<sectorBean> response) {

                            if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {

                                pla.clear();
                                pla1.clear();

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    pla.add(response.body().getData().get(i).getTitle());
                                    pla1.add(response.body().getData().get(i).getId());

                                }

                                ArrayAdapter<String> adapter7 = new ArrayAdapter<>(UpdateContractorJob.this,
                                        R.layout.spinner_model, pla);


                                place.setAdapter(adapter7);

                                int cp2 = 0;
                                for (int i = 0; i < pla1.size(); i++) {
                                    if (item.getPlace().equals(pla1.get(i))) {
                                        cp2 = i;
                                    }
                                }
                                place.setSelection(cp2);

                            }



                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(@NotNull Call<sectorBean> call, @NotNull Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                    int cp1 = 0;
                    for (int i = 0; i < day.size(); i++) {
                        if (item.getDays().equals(day.get(i))) {
                            cp1 = i;
                        }
                    }
                    days.setSelection(cp1);







                }





                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<contractorJobDetailsBean> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
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
