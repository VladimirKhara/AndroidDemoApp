package com.example.easyclusterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

    private AccessDB accessdb;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        accessdb = new AccessDB(this);

        final EditText etLog = findViewById(R.id.al_et0);
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

        final EditText etPas = findViewById(R.id.al_et1);
        etPas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Control.LengthControl(getApplicationContext(), etPas, charSequence, "password", 12, 170, 450);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final ImageView imageView = findViewById(R.id.al_iv0);
        final Drawable visibleIcon = getDrawable(R.drawable.ic_visibility_off_black_24dp);
        final Drawable invisibleIcon = getDrawable(R.drawable.ic_visibility_black_24dp);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return Control.SwitchEye(etPas, imageView, visibleIcon, invisibleIcon, motionEvent);
            }
        });
    }

    public void Log(View view) {
        EditText etLog = findViewById(R.id.al_et0);
        EditText etPas = findViewById(R.id.al_et1);
        if (etLog.getText().toString().length() > 0 && etPas.getText().toString().length() > 0) {
            SQLiteDatabase mydb = accessdb.getReadableDatabase();
            Cursor curs = mydb.query(AccessDB.log_table, new String[]{AccessDB.user_log, AccessDB.user_pas},
                    AccessDB.user_log + "='" + etLog.getText().toString() + "' AND " +
                            AccessDB.user_pas + "='" + etPas.getText().toString() + "'",
                    null, null, null, null);
            int admission = curs.getCount();
            curs.close();
            if (admission != 0) {
                Intent myInt = new Intent(this, MainActivity.class);
                startActivity(myInt);
            } else
                Toast.makeText(this, "Access is denied!", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Empty field(s)", Toast.LENGTH_LONG).show();
    }

    public void Sign(View view) {
        Intent myInt = new Intent(this, SignActivity.class);
        startActivity(myInt);
    }
}
