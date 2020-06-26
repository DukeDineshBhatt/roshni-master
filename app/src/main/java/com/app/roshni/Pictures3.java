package com.app.roshni;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.samplePOJO.sampleBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;

public class Pictures3 extends Fragment {


    RecyclerView grid;
    StaggeredGridLayoutManager manager;
    Button approve,reject , previous;
    ProgressBar progress;
    List<com.app.roshni.samplePOJO.Datum> list;
    SampleAdapter adapter;
    ImageView nodata;
    private Uri uri;
    private File f1;
    FloatingActionButton upload;
    private CustomViewPager pager;

    void setData(CustomViewPager pager) {
        this.pager = pager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.samples_layout2 , container , false);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        upload = view.findViewById(R.id.floatingActionButton3);
        progress = view.findViewById(R.id.progressBar3);
        nodata = view.findViewById(R.id.imageView5);
        approve = view.findViewById(R.id.button15);
        reject = view.findViewById(R.id.button29);
        previous = view.findViewById(R.id.button16);

        manager = new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL);
        adapter = new SampleAdapter(getActivity() , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        Log.d("DDD",SharePreferenceUtils.getInstance().getString("survey_id"));

        /*finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();

            }
        });*/

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager.setCurrentItem(0);

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

                            uri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
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


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.confirm))
                        .setMessage(getString(R.string.accept_text))

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                                progress.setVisibility(View.VISIBLE);

                                Bean b = (Bean) getActivity().getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<contractorBean> call = cr.submit_brand(SharePreferenceUtils.getInstance().getString("survey_id") , SharePreferenceUtils.getInstance().getString("id"));

                                call.enqueue(new Callback<contractorBean>() {
                                    @Override
                                    public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {


                                        Intent intent = new Intent(getContext(), MainActivity4.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        progress.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<contractorBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                        Log.d("SSS","SSS");
                                    }
                                });
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
        });


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("Reason for rejecting this profile?");

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                input.setHint("tab to enter");
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String value = input.getText().toString().trim();

                        if (value.length() > 0 || value.startsWith("  ")) {

                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getActivity().getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<contractorBean> call = cr.reject_brand(SharePreferenceUtils.getInstance().getString("survey_id"),value , SharePreferenceUtils.getInstance().getString("id"));

                            call.enqueue(new Callback<contractorBean>() {
                                @Override
                                public void onResponse(Call<contractorBean> call, Response<contractorBean> response) {


                                    Intent intent = new Intent(getContext(), MainActivity4.class);
                                    startActivity(intent);
                                    getActivity().finish();

                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    progress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<contractorBean> call, Throwable t) {
                                    progress.setVisibility(View.GONE);
                                    Log.d("SSS","SSS");
                                }
                            });

                        } else {

                            Toast.makeText(getContext(), "Invalid Reason ", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        Log.d("CANCEL","cancel");
                        progress.setVisibility(View.GONE);
                    }
                });

                alert.show();
                progress.setVisibility(View.GONE);


            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<sampleBean> call = cr.getSamples(SharePreferenceUtils.getInstance().getString("user"));

        call.enqueue(new Callback<sampleBean>() {
            @Override
            public void onResponse(Call<sampleBean> call, Response<sampleBean> response) {

                if (response.body().getData().size() > 0)
                {
                    nodata.setVisibility(View.GONE);
                }
                else
                {
                    nodata.setVisibility(View.VISIBLE);
                }


                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<sampleBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder>
    {
        Context context;
        List<com.app.roshni.samplePOJO.Datum> list = new ArrayList<>();


        SampleAdapter(Context context, List<com.app.roshni.samplePOJO.Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.app.roshni.samplePOJO.Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.sample_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final com.app.roshni.samplePOJO.Datum item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getFile() , holder.image , options);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progress.setVisibility(View.VISIBLE);

                    Bean b1 = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b1.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<sampleBean> call = cr.deleteSample(
                            item.getId()
                    );

                    call.enqueue(new Callback<sampleBean>() {
                        @Override
                        public void onResponse(Call<sampleBean> call, Response<sampleBean> response) {

                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            progress.setVisibility(View.GONE);

                            onResume();

                        }

                        @Override
                        public void onFailure(Call<sampleBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item.getFile() , img , options);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            ImageView image;
            ImageButton delete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
                delete = itemView.findViewById(R.id.delete);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri = data.getData();

            Log.d("uri", String.valueOf(uri));

            String ypath = getPath(getContext(), uri);
            assert ypath != null;


            File file = null;
            file = new File(ypath);

            try {
                f1 = new Compressor(getContext()).compressToFile(file);

                uri = Uri.fromFile(f1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("path1", ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("file", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            Bean b1 = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b1.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<sampleBean> call = cr.uploadSample(
                    SharePreferenceUtils.getInstance().getString("user"),
                    body
            );

            call.enqueue(new Callback<sampleBean>() {
                @Override
                public void onResponse(Call<sampleBean> call, Response<sampleBean> response) {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    progress.setVisibility(View.GONE);

                    onResume();
                }

                @Override
                public void onFailure(Call<sampleBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

            //image.setImageBitmap(bmp);

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            //image.setImageURI(uri);

            Log.d("uri1", String.valueOf(uri));

            try {

                File file = new Compressor(getContext()).compressToFile(f1);

                f1 = file;

                uri = Uri.fromFile(f1);

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("file", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            Bean b1 = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b1.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<sampleBean> call = cr.uploadSample(
                    SharePreferenceUtils.getInstance().getString("user"),
                    body
            );

            call.enqueue(new Callback<sampleBean>() {
                @Override
                public void onResponse(Call<sampleBean> call, Response<sampleBean> response) {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    progress.setVisibility(View.GONE);
                    onResume();

                }

                @Override
                public void onFailure(Call<sampleBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        }


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
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

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

}
