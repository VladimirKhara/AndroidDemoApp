package com.example.easyclusterapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ImageDB extends SQLiteOpenHelper {

    public static final int db_version = 1;
    public static final String db_name = "imageAppDB";

    public static final String image_table = "uploadedImages";
    public static final String image_id = "_image_id";
    public static final String image_description = "image_log";
    public static final String image_data = "users_pas";

    public ImageDB(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + image_table + "(" +
                image_id + " integer primary key," +
                image_description + " varchar(50)," +
                image_data + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + image_table);
        onCreate(sqLiteDatabase);
    }
}
