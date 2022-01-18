package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import mobile.indomarco.com.migrasiHH.R;

public class SOInfoPLU extends AppCompatActivity {

    private TextView tvInfoPlu;
    private SharedPreferences flagBHS;
    private String getBHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_info_plu_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        tvInfoPlu = findViewById(R.id.tvInfoPlu);

        String titleBar = "";

        if (getBHS.equals("IDN")){
            titleBar = "Info Plu";
            tvInfoPlu.setText("Bagian ini adalah info / Ringkasan SO Plu");
        } else {
            titleBar = "Plu Info";
            tvInfoPlu.setText("This is part of info / SO Plu Summary");
        }
        getSupportActionBar().setTitle(titleBar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }
}