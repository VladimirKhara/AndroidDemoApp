package com.example.easyclusterapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;


public class MyDialogFragment extends AppCompatDialogFragment {

    @NonNull
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Confirm operation")
                .setIcon(R.drawable.ic_person_delete_30dp)
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle = getArguments();
                        OperationCRUD operationCRUD = (OperationCRUD) bundle.getSerializable("del_item");
                        operationCRUD.deleteOperation();
                    }
                })
                .setNegativeButton("No", null)
                .create();
    }
}

