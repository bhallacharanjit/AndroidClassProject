package com.aprosoftech.myclass;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNeta extends AppCompatActivity implements View.OnClickListener {


    EditText et_name, et_party, et_city;
    Button btn_save;
    ImageButton ib_userImage;
    Uri fileUri;
    ProgressDialog progressDialog;
    static String IMAGE_DIRECTORY_NAME = "ImagesDir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_neta);


        et_name = (EditText) findViewById(R.id.et_name);

        et_name.setBackgroundColor(Color.parseColor("#ffffff"));
        et_name.setBackgroundColor(Color.RED);


        et_city = (EditText) findViewById(R.id.et_city);
        et_party = (EditText) findViewById(R.id.et_party);

        btn_save = (Button) findViewById(R.id.btn_save);
        ib_userImage = (ImageButton) findViewById(R.id.iv_userImage);
        ib_userImage.setOnClickListener(this);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        setResult(RESULT_CANCELED);
        AddNeta.this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            String name = et_name.getText().toString();
            String party = et_party.getText().toString();
            String city = et_city.getText().toString();


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("City", city);
                jsonObject.put("Party", party);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "https://api.backendless.com/65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00/0B0003D1-E1FB-A85B-FFC9-634D746D3100/data/MLAs";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response", response.toString());
                    progressDialog.dismiss();
                    Toast.makeText(AddNeta.this, "Data Saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.putExtra("Name", et_name.getText().toString());
                    setResult(RESULT_OK, intent);
                    AddNeta.this.finish();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", "Err" + error.getLocalizedMessage());
                    progressDialog.dismiss();
                    Toast.makeText(AddNeta.this, "Error is there!", Toast.LENGTH_LONG).show();

                }
            });

            progressDialog = new ProgressDialog(AddNeta.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Loading !");
            progressDialog.setMessage("Sending data to server");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(AddNeta.this);
            requestQueue.add(jsonObjectRequest);


        } else if (view.getId() == R.id.iv_userImage) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddNeta.this);
            alertBuilder.setTitle("Confirmation");
            alertBuilder.setMessage("Where would you like to pick images from?");
            alertBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fileUri = getOutputMediaFileUri(1);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                    // start the image capture Intent
                    startActivityForResult(intent, 1001);

                }
            });
            alertBuilder.setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1002);
                }
            });
            alertBuilder.setNegativeButton("Cancel",null);
            alertBuilder.show();


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    try {
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap selectedImageBitmap = BitmapFactory.decodeStream(imageStream);
                        ib_userImage.setImageBitmap(selectedImageBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                Toast.makeText(this, "Not Selected Image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {

                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                        options);

                ib_userImage.setImageBitmap(bitmap);
                }
        }
    }


    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
}
