package com.example.easyclusterapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import 	android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccessDB extends SQLiteOpenHelper {

    public static final int db_version = 1;
    public static final String db_name = "clusterDB";

    public static final String log_table = "users_acc";
    public static final String user_ID = "_users_id";
    public static final String user_log = "users_log";
    public static final String user_pas = "users_pas";

    public AccessDB(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + log_table + "(" + user_ID + " integer primary key," +
                user_log + " varchar(30)," + user_pas + " varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + log_table);
        onCreate(sqLiteDatabase);
    }

}
