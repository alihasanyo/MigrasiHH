package mobile.indomarco.com.migrasiHH.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.activity.LoginActivity;
import mobile.indomarco.com.migrasiHH.activity.SettingActivity;
import mobile.indomarco.com.migrasiHH.model.MainMenuModel;

public class MenuOtherAdapter extends ArrayAdapter<MainMenuModel> {

    private final Activity context;
    private ArrayList<MainMenuModel> data;
    private Dialog dialog;

    public MenuOtherAdapter(Activity context, ArrayList<MainMenuModel> data) {
        super(context, R.layout.item_list_other_menu, data);
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_list_other_menu, null, true);

        TextView tvTitle = rowView.findViewById(R.id.titlemenu);
        TextView tvSubtitle = rowView.findViewById(R.id.subtitlemenu);
        ImageView ivIcon = rowView.findViewById(R.id.iconmenu);
        LinearLayout lyMenu = rowView.findViewById(R.id.lyMenu);

        MainMenuModel m = data.get(position);
        tvTitle.setText(m.getTitle());
        tvSubtitle.setText(m.getSubtitle());
        ivIcon.setImageResource(m.getIcon());

        lyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (m.getTitle()){
                    case "Perangkat" :
                        showDialogDevice("IDN");
                        break;

                    case "Device" :
                        showDialogDevice("ENG");
                        break;

                    case "Logout" :
                        showDialogLogout("ENG");
                        break;

                    case "Keluar" :
                        showDialogLogout("IDN");
                        break;

                    default:
                        break;

                }
            }
        });

        return rowView;
    }

    private void showDialogLogout(String bhs) {

        String message = "";
        String Y = "";
        String N = "";

        if (bhs.equals("ENG"))
        {
            message = "Are you sure out this apps?";
            Y = "YES";
            N = "NO";
        } else {
            message = "Anda yakin keluar aplikasi?";
            Y = "YA";
            N = "TIDAK";
        }

        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setMessage(message);
        alert.setPositiveButton(Y,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newTask = new Intent(context, LoginActivity.class);
                        newTask.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(newTask);
                    }
                });
        alert.setNegativeButton(N,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss
                        dialog.dismiss();
                    }
                });
        alert.create();
        alert.show();

    }

    private void showDialogDevice(String bhs) {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_device);
        dialog.setCancelable(true);
        dialog.show();

        Button close = dialog.findViewById(R.id.btnCLose);
        TextView tvInfo = dialog.findViewById(R.id.tvInfoPerangkat);
        TextView titlePerangkat = dialog.findViewById(R.id.titlePerangkat);

        String device = "";

        if (bhs.equals("ENG"))
        {
            device = "Device Information";
            close.setText("Close");
        } else {
            device = "Informasi Perangkat";
            close.setText("Tutup");
        }

        titlePerangkat.setText(device);

        StringBuilder sb = new StringBuilder();
        sb.append("MANUFACTURE : " + Build.MANUFACTURER + "\n");
        sb.append("BRAND : " + Build.BRAND + "\n");
        sb.append("MODEL : " + Build.MODEL + "\n");
        sb.append("SERIAL : " + Build.SERIAL + "\n");
        sb.append("ID : " + Build.ID + "\n");
        sb.append("SDK : " + Build.VERSION.SDK + "\n");
        sb.append("VERSION : " + Build.VERSION.RELEASE);

        tvInfo.setText(sb.toString());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
