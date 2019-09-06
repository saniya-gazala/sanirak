package com.example.m.textrecoginition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String results = null;
    ImageView imageView;
    Button snap_image, reco_text;




    private final int PICK_IMAGE_REQUEST = 100;




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            recognize(uri);

        }
    }


    private void recognize(Uri uri) {


        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // Log.d(TAG, String.valueOf(bitmap));
            imageView.setImageBitmap(bitmap);

            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            if (!textRecognizer.isOperational()) {
                Log.e("ERROR", "Recognizer dependencies is not available yet");
            } else {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < items.size(); ++i) {
                    TextBlock item = items.valueAt(i);
                    stringBuilder.append(item.getValue());
                    stringBuilder.append("\n");
                }
                String result = stringBuilder.toString();
                results = result;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.Image_view);
        snap_image = (Button) findViewById(R.id.snap_image);
        reco_text = (Button) findViewById(R.id.reco_image);









        snap_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        reco_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_text(results);
            }
        });






    }







    // final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

    // AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // builder.setTitle("Click Image");
    // builder.setItems(items, new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dialogInterface, int i) {
    //if (items[i].equals("Camera")) {
    // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // startActivityForResult(intent, REQUEST_CAMERA);

    // }else{
    //  Toast.makeText(MainActivity.this, "cannot click image", Toast.LENGTH_SHORT).show();
    //  }
    // dialogInterface.dismiss();

    // }








    private void show_text(String results) {
        Intent intent = new Intent(MainActivity.this, TextDisplay.class);
        intent.putExtra("DATA", results);
        startActivity(intent);
    }


}









