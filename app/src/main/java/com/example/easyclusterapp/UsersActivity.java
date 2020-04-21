package com.example.easyclusterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    private AccessDB accessDB;
    public static RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private List<User> users = new ArrayList();
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        fragmentManager = getSupportFragmentManager();

        accessDB = new AccessDB(this);
        SQLiteDatabase sqLiteDatabase = accessDB.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(accessDB.log_table, new String[]{accessDB.user_ID, accessDB.user_log, accessDB.user_pas},
                null, null, null, null, null);
        cursor.moveToFirst();
        Random random = new Random();
        do {
            User user = new User();
            switch (random.nextInt(3)) {
                case 0:
                    user.setAva(R.drawable.spidermanmorales);
                    break;
                case 1:
                    user.setAva(R.drawable.logan);
                    break;
                case 2:
                    user.setAva(R.drawable.batman);
                    break;
                default:
                    user.setAva(R.drawable.question);
                    break;
            }
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setLogin(cursor.getString(1));
            user.setPass(cursor.getString(2));
            users.add(user);
        } while (cursor.moveToNext());

        myAdapter = new MyAdapter(this, users);

        recyclerView = findViewById(R.id.au_rv0);
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_main, menu);
    }

}


