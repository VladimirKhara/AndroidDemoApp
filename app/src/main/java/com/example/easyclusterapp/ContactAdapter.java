package com.example.easyclusterapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private LayoutInflater inflater;
    private List<String> contactsName;
    private List<String> contactsPhone;

    ContactAdapter(Context context, List<String> contactsName, List<String> contactsPhone) {
        this.inflater = LayoutInflater.from(context);
        this.contactsName = contactsName;
        this.contactsPhone = contactsPhone;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_recycler, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final String contactName = contactsName.get(position);
        final String contactPhone = contactsPhone.get(position);
        holder.contactName.setText(contactName);
        holder.contactPhone.setText(contactPhone);
    }

    @Override
    public int getItemCount() {
        return contactsName.size();
    }
}

class ContactViewHolder extends RecyclerView.ViewHolder {
    final TextView contactName, contactPhone;

    @RequiresApi(api = Build.VERSION_CODES.M)
    ContactViewHolder(View view) {
        super(view);
        contactName = view.findViewById(R.id.cr_tv0);
        contactPhone = view.findViewById(R.id.cr_tv1);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (ContextCompat.checkSelfPermission(globalContext.getAppContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager.getDefault().sendTextMessage(contactPhone.getText().toString(), null, ContactsActivity.userAcc, null, null);
                    Toast.makeText(globalContext.getAppContext(), "SMS has been sent", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(globalContext.getAppContext(), "You must get permission for sending", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
