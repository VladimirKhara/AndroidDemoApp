package com.example.easyclusterapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public List<User> users;
    private LayoutInflater inflater;
    private AccessDB accessDB = new AccessDB(globalContext.getAppContext());
    private SQLiteDatabase db;
    private MyAdapter myAdapter;

    MyAdapter(Context context, List<User> users) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_recycler_list, parent, false);
        db = accessDB.getWritableDatabase();
        myAdapter = this;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final User user = users.get(position);
        holder.imageView.setImageResource(user.getAva());
        holder.tvLog.setText(user.getLogin());
        holder.tvPass.setText(user.getPass());
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditorDialogFragment editorDialogFragment = new EditorDialogFragment();
                Bundle bundle = new Bundle();
                OperationCRUD operationCRUD = new OperationCRUD(db, myAdapter, user.getId(), position);
                bundle.putSerializable("update_item", operationCRUD);
                editorDialogFragment.setArguments(bundle);
                editorDialogFragment.show(UsersActivity.fragmentManager, null);
            }
        });
        holder.imageDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogFragment dialog = new MyDialogFragment();
                Bundle bundle = new Bundle();
                OperationCRUD operationCRUD = new OperationCRUD(db, myAdapter, user.getId(), position);
                bundle.putSerializable("del_item", operationCRUD);
                dialog.setArguments(bundle);
                dialog.show(UsersActivity.fragmentManager, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    final ImageView imageView, imageEdit, imageDel;
    final TextView tvLog, tvPass;

    @RequiresApi(api = Build.VERSION_CODES.M)
    MyViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.mrl_iv0);
        tvLog = view.findViewById(R.id.mrl_tv0);
        tvPass = view.findViewById(R.id.mrl_tv1);
        imageEdit = view.findViewById(R.id.mrl_iv1);
        imageDel = view.findViewById(R.id.mrl_iv2);
        view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Your choice");
        final String userAcc = "Your login: " + this.tvLog.getText().toString() + "\nYour password: " +
                this.tvPass.getText().toString();

        contextMenu.add("Send via SMS App");
        MenuItem menuItem0 = contextMenu.getItem(0);
        menuItem0.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SendToDialogFragment sendToDialogFragment = new SendToDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("acc", userAcc);
                bundle.putByte("sms_choice", (byte) 0);
                sendToDialogFragment.setArguments(bundle);
                sendToDialogFragment.show(UsersActivity.fragmentManager, null);
                return false;
            }
        });

        contextMenu.add("Send SMS directly");
        MenuItem menuItem1 = contextMenu.getItem(1);
        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SendToDialogFragment sendToDialogFragment = new SendToDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("acc", userAcc);
                bundle.putByte("sms_choice", (byte) 1);
                sendToDialogFragment.setArguments(bundle);
                sendToDialogFragment.show(UsersActivity.fragmentManager, null);
                return false;
            }
        });

        contextMenu.add("Send SMS with selecting contact");
        MenuItem menuItem2 = contextMenu.getItem(2);
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(globalContext.getAppContext(), ContactsActivity.class);
                intent.putExtra("current_acc", userAcc);
                globalContext.getAppContext().startActivity(intent);
                return false;
            }
        });

        SubMenu subMenu = contextMenu.addSubMenu("Send via app...");
        subMenu.add("WhatsApp").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return sendViaApps("com.whatsapp", userAcc);
            }
        });
        subMenu.add("Viber").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return sendViaApps("com.viber.voip", userAcc);
            }
        });
        subMenu.add("Telegram").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return sendViaApps("org.telegram.messenger", userAcc);
            }
        });
    }

    private boolean sendViaApps(String appName, String mesBody) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, mesBody);
        intent.setType("text/plain");
        intent.setPackage(appName);
        try {
            globalContext.getAppContext().startActivity(intent);
            return true;
        } catch (Exception ex) {
            Toast.makeText(globalContext.getAppContext(), "This application is not installed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}

