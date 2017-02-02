package com.example.android.testintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String mCurrentPhotoPath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitIntent(View view) {

        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                // takePicIntent.putExtra("PhotoPath",mCurrentPhotoPath );
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Error","Error while creating file: "+ ex.getMessage(),ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        Log.i("start", "-1");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp ;

        Log.i("bfr dir", "0");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.i("bfr filee temp", "1");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        Log.i("bfr mphoto", "2");
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("file", imageFileName);
        Log.i("PAth", mCurrentPhotoPath);
        return image;
    }

}
