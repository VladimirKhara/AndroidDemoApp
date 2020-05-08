package com.example.easyclusterapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class ImageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private ImageView imageView;
    private Uri imageUri;
    private EditText editText;
    private Bitmap bitmap;
    private ImageDB imageDB;
    private EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.act_image_iv0);
        imageView.setImageResource(R.drawable.spidermanmorales);

        Button button = findViewById(R.id.act_image_btn0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromDevice();
            }
        });

        imageDB = new ImageDB(this);
        editText = findViewById(R.id.act_image_et0);
        Button button1 = findViewById(R.id.act_image_btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")) {
                    if (bitmap != null) {
                        SQLiteDatabase sqLiteDatabase = imageDB.getWritableDatabase();
                        byte[] imageData = getBytes(bitmap);
                        ContentValues cv = new ContentValues();
                        cv.put(ImageDB.image_description, editText.getText().toString());
                        cv.put(ImageDB.image_data, imageData);
                        sqLiteDatabase.insert(ImageDB.image_table, null, cv);
                        Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "We dont have image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "We dont have description for image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button button2 = findViewById(R.id.act_image_btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(0);
            }
        });

        editText1 = findViewById(R.id.act_image_et1);
        Button button3 = findViewById(R.id.act_image_btn3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText1.getText().toString().equals("")) {
                    SQLiteDatabase sqLiteDatabase = imageDB.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.query(ImageDB.image_table, new String[]{ImageDB.image_id, ImageDB.image_description, ImageDB.image_data},
                            ImageDB.image_description + "= '" + editText1.getText().toString() + "'", null, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            byte[] loadedImage = cursor.getBlob(2);
                            Bitmap bitmap1 = getImage(loadedImage);
                            imageView.setImageBitmap(bitmap1);
                        } while (cursor.moveToNext());
                    } else {
                        Toast.makeText(getApplicationContext(), "Cant find image with this description", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                } else {
                    Toast.makeText(getApplicationContext(), "Cant find with empty description", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getImageFromDevice() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void deleteData (View view) {
        SQLiteDatabase sqLiteDatabase =  imageDB.getWritableDatabase();
        sqLiteDatabase.delete(ImageDB.image_table, null, null);
        Toast.makeText(getApplicationContext(), "Dropped!", Toast.LENGTH_SHORT).show();
    }
}
