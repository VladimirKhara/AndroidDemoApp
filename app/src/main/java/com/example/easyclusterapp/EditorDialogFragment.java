package com.example.easyclusterapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class EditorDialogFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_user_edit, null);
        builder.setView(view);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText newLog = view.findViewById(R.id.due_et0);
                EditText newPas = view.findViewById(R.id.due_et1);
                if (!newLog.getText().toString().equals("") && !newPas.getText().toString().equals("")) {
                    Bundle bundle = getArguments();
                    OperationCRUD operationCRUD = (OperationCRUD) bundle.getSerializable("update_item");
                    operationCRUD.updateOperation(newLog.getText().toString(), newPas.getText().toString());
                } else
                    Toast.makeText(globalContext.getAppContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

}
