package it.spacecoding.cameraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    /*
    Questa riga definisce una costante intera CAMERA_PERMISSION_REQUEST_CODE con il valore 100.
    Viene utilizzata per identificare la richiesta di permesso della fotocamera.
    Quando si richiede l'autorizzazione per utilizzare la fotocamera,
    è necessario specificare un codice di richiesta per identificare la richiesta specifica.
     */
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    /*
    Questa riga definisce una costante intera CAMERA_REQUEST_CODE con il valore 101.
    Viene utilizzata per identificare la richiesta di acquisizione della fotocamera.
    Quando si avvia l'applicazione della fotocamera e si attende il risultato,
    è necessario specificare un codice di richiesta per identificare la richiesta specifica.
     */
    private static final int CAMERA_REQUEST_CODE = 101;

    private Button mTakePhotoButton;
    private ImageView mPhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTakePhotoButton = findViewById(R.id.take_photo_button);
        mPhotoImageView = findViewById(R.id.photo_image_view);

        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    takePhoto();
                }
            }
        });
    }
    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPhotoImageView.setImageBitmap(photo);
        }
    }

}