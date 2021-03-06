package com.aprosoftech.myclass;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import com.onesignal.OneSignal;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNeta extends AppCompatActivity implements View.OnClickListener,PaymentResultListener {


    EditText et_name, et_party, et_city;
    Button btn_save,btn_save_local;
    ImageButton ib_userImage;
    Uri fileUri;
    ProgressDialog progressDialog;
    static String IMAGE_DIRECTORY_NAME = "ImagesDir";
    Bitmap imageNeta = null;

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
        btn_save_local = (Button) findViewById(R.id.btn_save_local);
        ib_userImage = (ImageButton) findViewById(R.id.iv_userImage);
        ib_userImage.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_save_local.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        setResult(RESULT_CANCELED);
        AddNeta.this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save_local) {
//            DBHelper dbHelper = new DBHelper(AddNeta.this);
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("name",et_name.getText().toString());
//                jsonObject.put("city",et_city.getText().toString());
//                jsonObject.put("party",et_party.getText().toString());
//                dbHelper.insertData(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            Intent intent = new Intent(AddNeta.this,ShowNeta.class);
//            PendingIntent pendingIntent1 = PendingIntent.getActivity(AddNeta.this,(int)System.currentTimeMillis(),intent,0);
//
//            Notification notification = new Notification.Builder(AddNeta.this)
//                    .setContentTitle("Neta has been added")
//                    .setContentText("Some text here as well")
//                    .setSmallIcon(R.mipmap.add_neta)
//                    .setAutoCancel(true).build();
//
//            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//
//            notificationManager.notify(1001,notification);


            Intent intent1 = new Intent(AddNeta.this, MyBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNeta.this,
                    1000, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            long milliseconds = calendar.getTimeInMillis();
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,milliseconds,AlarmManager.INTERVAL_DAY,pendingIntent);



        }
        if (view.getId() == R.id.btn_save) {

//            if (imageNeta == null) {
//                uploadData("");
//            } else {
//                String fileName = et_name.getText().toString() + "_"+et_city.getText().toString()
//                        +"_"+et_party.getText().toString();
//                Backendless.Files.Android.upload( imageNeta,
//                        Bitmap.CompressFormat.PNG,
//                        100,fileName,
//                        "myClassFiles",
//                        new AsyncCallback<BackendlessFile>()
//                {
//                    @Override
//                    public void handleResponse( final BackendlessFile backendlessFile)
//                    {
//                        uploadData(backendlessFile.getFileURL());
//                    }
//                    @Override
//                    public void handleFault( BackendlessFault backendlessFault )
//                    {
//                        Toast.makeText( AddNeta.this, backendlessFault.toString(), Toast.LENGTH_SHORT ).show();
//                    }
//                });
//            }



            //PAYMENT
            Checkout checkout = new Checkout();
            checkout.setImage(R.mipmap.add_neta);
            final Activity activity = this;
            try {
                JSONObject options = new JSONObject();

                /**
                 * Merchant Name
                 * eg: Rentomojo || HasGeek etc.
                 */
                options.put("name", "Aprosoft");

                /**
                 * Description can be anything
                 * eg: Order #123123
                 *     Invoice Payment
                 *     etc.
                 */
                options.put("description", "Order #123456");

                options.put("currency", "INR");

                /**
                 * Amount is always passed in PAISE
                 * Eg: "500" = Rs 5.00
                 */
                options.put("amount", "15000");

                checkout.open(activity, options);
            } catch(Exception e) {
                Log.e("test", "Error in starting Razorpay Checkout", e);
            }


            try {
                JSONObject jsonObject = new JSONObject("{\"contents\": {\"en\":\"Test Message\"}, \"included_segments\": [\"" + "All" + "\"]}");
                OneSignal.postNotification(jsonObject,
                        new OneSignal.PostNotificationResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                Log.i("OneSignalExample", "postNotification Success: " + response.toString());
                            }

                            @Override
                            public void onFailure(JSONObject response) {
                                Log.e("OneSignalExample", "postNotification Failure: " + response.toString());
                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }




        } else if (view.getId() == R.id.iv_userImage) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddNeta.this);
            alertBuilder.setTitle("Confirmation");
            alertBuilder.setMessage("Where would you like to pick images from?");
            alertBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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



    public void uploadData(String url_of_image) {
        String name = et_name.getText().toString();
        String party = et_party.getText().toString();
        String city = et_city.getText().toString();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", name);
            jsonObject.put("City", city);
            jsonObject.put("Party", party);
            jsonObject.put("ImageUrl", url_of_image);
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
                        imageNeta = selectedImageBitmap;
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


                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageNeta = bitmap;
                ib_userImage.setImageBitmap(bitmap);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] showimage1 = baos.toByteArray();
//                sellerimagestring = Base64.encodeToString(showimage1, Base64.DEFAULT);
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


    //PAYMENT RESULT LISTNER METHODS
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(AddNeta.this,"Success "+s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(AddNeta.this,"Error Code "+i+"\nMessage "+s,Toast.LENGTH_LONG).show();
    }
}
