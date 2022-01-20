package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.adapter.MenuOtherAdapter;
import mobile.indomarco.com.migrasiHH.model.MainMenuModel;

public class SettingActivity extends AppCompatActivity {

    private ArrayList<MainMenuModel> listMenu;
    private Activity activity;
    private ListView list_other_menu;
    private MenuOtherAdapter mAdapter;
    private SharedPreferences flagBHS;
    private String getBHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        activity = this;

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        list_other_menu = findViewById(R.id.list_other_menu);

        listMenu = new ArrayList<>();

        if (getBHS.equals("ENG"))
        {
            listMenu.add(new MainMenuModel(1, R.drawable.ic_infoapps, activity.getResources().getString(R.string.device), "Device Information"));
            listMenu.add(new MainMenuModel(2, R.drawable.ic_logout, activity.getResources().getString(R.string.logout), "Account Logout"));
            listMenu.add(new MainMenuModel(3, R.drawable.ip1, activity.getResources().getString(R.string.setIP), "Connect Configuration"));
        } else {
            listMenu.add(new MainMenuModel(1, R.drawable.ic_infoapps, activity.getResources().getString(R.string.deviceIDN), "Informasi Perangkat"));
            listMenu.add(new MainMenuModel(2, R.drawable.ic_logout, activity.getResources().getString(R.string.logoutIDN), "Keluar Akun"));
            listMenu.add(new MainMenuModel(3, R.drawable.ip1, activity.getResources().getString(R.string.setIPIDN), "Konfigurasi Koneksi"));
        }

        mAdapter = new MenuOtherAdapter(activity, listMenu);
        list_other_menu.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ((androidx.appcompat.widget.AppCompatImageButton) findViewById(R.id.btnClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent close = new Intent(activity, HomeActivity.class);
                close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(close);
            }
        });

    }
}