package com.example.easyclusterapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SendToDialogFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_data_send, null);
        builder.setView(view);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText phone = view.findViewById(R.id.dds_et0);
                String phoneNumber = phone.getText().toString();
                if (phoneNumber.startsWith("8") && phoneNumber.length() == 11 && !phoneNumber.contains(".")) {
                    String acc = getArguments().getString("acc");
                    byte choice = getArguments().getByte("sms_choice");
                    switch (choice) {
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
                            intent.putExtra("sms_body", acc);
                            globalContext.getAppContext().startActivity(intent);
                            break;
                        case 1:
                            SmsManager.getDefault().sendTextMessage(phoneNumber, null, acc, null, null);
                            Toast.makeText(globalContext.getAppContext(),"SMS has been sent",Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else
                    Toast.makeText(globalContext.getAppContext(), "Incorrect phone number", Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }
}
