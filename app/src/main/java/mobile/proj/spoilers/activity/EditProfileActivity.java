package mobile.proj.spoilers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.app.AppConfig;
import mobile.proj.spoilers.app.AppController;
import mobile.proj.spoilers.app.AppHelper;
import mobile.proj.spoilers.util.VolleyMultipartRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static mobile.proj.spoilers.app.AppConfig.URL_UPDATEIMAGE;

public class EditProfileActivity extends AppCompatActivity {
    // LogCat tag
    private static final String TAG = EditProfileActivity.class.getSimpleName();
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri; // file url to store image/video
    private String SERVER_URL = URL_UPDATEIMAGE;
    ImageView ivAttachment;
    Button btnCapturePicture;
    TextView tvFileName;
    EditText pseudo,firstname,lastname;
    ProgressDialog dialog;
    private String image;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        Drawable leftArrow =  toolbar.getNavigationIcon();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        ivAttachment = (ImageView) findViewById(R.id.ivAttachment);
        btnCapturePicture = (Button) findViewById(R.id.b_upload);
        tvFileName = (TextView) findViewById(R.id.tv_file_name);
        pseudo = (EditText) findViewById(R.id.username);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        final SharedPreferences sharedPreferences = EditProfileActivity.this.getSharedPreferences("USER", 0);
        pseudo.setText(sharedPreferences.getString("pseudo",null));
        firstname.setText(sharedPreferences.getString("firstname",null));
        lastname.setText(sharedPreferences.getString("lastname",null));
        ivAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendImage(image,pseudo.getText().toString(),sharedPreferences.getString("email",null),firstname.getText().toString(),lastname.getText().toString());
                SharedPreferences settings = EditProfileActivity.this.getSharedPreferences("USER", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("pseudo", pseudo.getText().toString());
                editor.putString("firstname", firstname.getText().toString());
                editor.putString("lastname", lastname.getText().toString());
                editor.commit();
            }
        });
        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
              //  launchUploadActivity(true);
                Uri filePath = data.getData();
                ivAttachment.setImageURI(filePath);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    Bitmap lastBitmap = null;
                    lastBitmap = bitmap;
                    //encoding image to string
                     image = getStringImage(lastBitmap);
                    Log.d("image",image);
                    //passing the image to volley

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
    private void SendImage( final String image, final String pseudo, final String email, final String firstname,final  String lastname) {
        final VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, SERVER_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    SharedPreferences settings = EditProfileActivity.this.getSharedPreferences("USER", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("img", obj.getString("img"));
                    editor.commit();
                    Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(i);

                } catch (JSONException e) {
                    Log.e("Json",e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pseudo", pseudo);
                params.put("email", email);
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("image",image);
                return params;
            }
        @Override
        protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
            Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
            // file name could found file base or direct access from real path
            // for now just get bitmap data from ImageView
            params.put("image", new VolleyMultipartRequest.DataPart(pseudo+"_image.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), ivAttachment.getDrawable()), "image/jpeg"));
            return params;
        }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    private void launchUploadActivity(boolean isImage){
        Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

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
                AppConfig.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + AppConfig.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

}
