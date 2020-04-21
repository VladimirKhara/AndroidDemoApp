package com.example.easyclusterapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import java.io.Serializable;

public class OperationCRUD implements Serializable {
    private SQLiteDatabase sqLiteDatabase;
    private MyAdapter myAdapter;
    private int id;
    private int position;
    private EditText log;

    public OperationCRUD(SQLiteDatabase sqLiteDatabase, MyAdapter myAdapter, int id, int position) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.myAdapter = myAdapter;
        this.id = id;
        this.position = position;
    }

    public void deleteOperation() {
        sqLiteDatabase.delete(AccessDB.log_table, "_users_id = ?", new String[] {String.valueOf(id)});
        myAdapter.users.remove(position);
        UsersActivity.recyclerView.removeViewAt(position);
        myAdapter.notifyItemRemoved(position);
        myAdapter.notifyItemRangeChanged(position, myAdapter.users.size());
    }

    public void updateOperation(String newLogin, String newPassword) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccessDB.user_log, newLogin);
        contentValues.put(AccessDB.user_pas, newPassword);
        sqLiteDatabase.update(AccessDB.log_table,contentValues,"_users_id = ?",new String[] {String.valueOf(id)});
        User user = myAdapter.users.get(position);
        user.setLogin(newLogin);
        user.setPass(newPassword);
        myAdapter.users.set(position,user);
        myAdapter.notifyDataSetChanged();
    }

}
