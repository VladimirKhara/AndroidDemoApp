package com.example.easyclusterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private final int REQUEST_CODE_READ_CONTACTS = 1;
    private boolean READ_CONTACTS_GRANTED = false;
    private ArrayList<String> contactsName = new ArrayList<>();
    private ArrayList<String> contactsPhone = new ArrayList<>();
    public static String userAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        int readContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (readContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
        if (READ_CONTACTS_GRANTED) {
            loadContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true;
        }
        if (READ_CONTACTS_GRANTED) {
            loadContacts();
        } else {
            Toast.makeText(this, "Необходимо получить разрешение на чтение контактов", Toast.LENGTH_LONG).show();
        }
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor0 = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String contactName;
        int phoneType;
        if (cursor0 != null) {
            cursor0.moveToFirst();
            do {
                contactName = cursor0.getString(cursor0.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactsName.add(contactName);
                if (Integer.parseInt(cursor0.getString(cursor0.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    String currentContact = cursor0.getString(cursor0.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + currentContact, null, null);
                    cursor1.moveToFirst();
                    do {
                        phoneType = cursor1.getInt(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                            contactsPhone.add(cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)));
                            break;
                        }
                    } while (cursor1.moveToNext());
                    cursor1.close();
                    if (contactsName.size() > contactsPhone.size())
                        contactsPhone.add("We dont have mobile number");
                } else
                    contactsPhone.add("We dont have mobile number");
            } while (cursor0.moveToNext());
            cursor0.close();
        }
        userAcc= getIntent().getExtras().getString("current_acc");
        ContactAdapter contactAdapter = new ContactAdapter(this, contactsName, contactsPhone);
        RecyclerView recyclerView = findViewById(R.id.ac_rv0);
        recyclerView.setAdapter(contactAdapter);
    }

}
