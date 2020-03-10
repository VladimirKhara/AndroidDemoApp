package com.example.easyclusterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {

    private AccessDB accessdb;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        accessdb = new AccessDB(this);

        final EditText etLog = findViewById(R.id.as_et0);
        etLog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Control.LengthControl(getApplicationContext(), etLog, charSequence, "login", 12, 215, 450);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText etPas0 = findViewById(R.id.as_et1);
        etPas0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Control.LengthControl(getApplicationContext(), etPas0, charSequence, "password", 12, 170, 450);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText etPas1 = findViewById(R.id.as_et2);
        etPas1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Control.LengthControl(getApplicationContext(), etPas1, charSequence, "password", 12, 170, 450);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final ImageView imPas0 = findViewById(R.id.as_iv0);
        final Drawable visibleIcon = getDrawable(R.drawable.ic_visibility_off_black_24dp);
        final Drawable invisibleIcon = getDrawable(R.drawable.ic_visibility_black_24dp);
        imPas0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return Control.SwitchEye(etPas0, imPas0, visibleIcon, invisibleIcon, motionEvent);
            }
        });

        final ImageView imPas1 = findViewById(R.id.as_iv1);
        imPas1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return Control.SwitchEye(etPas1, imPas1, visibleIcon, invisibleIcon, motionEvent);
            }
        });
    }

    public void CreateNew(View view) {
        EditText userLog = findViewById(R.id.as_et0);
        EditText userPas0 = findViewById(R.id.as_et1);
        EditText userPas1 = findViewById(R.id.as_et2);
        String userL = userLog.getText().toString();
        String userP0 = userPas0.getText().toString();
        String userP1 = userPas1.getText().toString();
        if (userP0.equals(userP1) && userL.length() > 0 && userP0.length() > 0 && userP1.length() > 0) {
            SQLiteDatabase mydb = accessdb.getWritableDatabase();
            Cursor curs = mydb.query(AccessDB.log_table, new String[]{AccessDB.user_log},
                    AccessDB.user_log + "='" + userL + "'",
                    null, null, null, null);
            int duplicate = curs.getCount();
            curs.close();
            if (duplicate == 0) {
                ContentValues conV = new ContentValues();
                conV.put(AccessDB.user_log, userL);
                conV.put(AccessDB.user_pas, userP0);
                mydb.insert(AccessDB.log_table, null, conV);
                Toast.makeText(this, "User has been created", Toast.LENGTH_SHORT).show();
                Intent myInt = new Intent(this, LogActivity.class);
                startActivity(myInt);
            } else
                Toast.makeText(this, "Login is used", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Check the fields", Toast.LENGTH_SHORT).show();
    }

    public void ClearText(View view) {
        EditText userLog = findViewById(R.id.as_et0);
        EditText userPas0 = findViewById(R.id.as_et1);
        EditText userPas1 = findViewById(R.id.as_et2);
        userLog.setText("");
        userPas0.setText("");
        userPas1.setText("");
    }
}