package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mobile.indomarco.com.migrasiHH.R;

public class SOInquiryPlanoctivity extends AppCompatActivity {

    private TextInputLayout inputPLU, inputLPP, inputFrac;
    private TextInputEditText etPLU, etLPP, etFrac;
    private TextView tvDesc, tvDesc1;
    private Button btnClear, btnExit;
    private TextView descbarang;
    private SharedPreferences flagBHS;
    private String getBHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_inquiry_plano_ctivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        inputPLU = findViewById(R.id.input_inqPlu);
        inputFrac = findViewById(R.id.input_inqFrac);
        inputLPP = findViewById(R.id.input_inqLpp);
        etPLU = findViewById(R.id.et_inqPlu);
        etLPP = findViewById(R.id.et_inqLpp);
        etFrac = findViewById(R.id.et_inqFrac);
        tvDesc = findViewById(R.id.descSOPlano);
        tvDesc1 = findViewById(R.id.descPlano);
        btnClear = findViewById(R.id.btnClearInq);
        btnExit = findViewById(R.id.btnExitInq);
        descbarang = findViewById(R.id.descbarang);

        String titleBar = "";

        if (getBHS.equals("IDN")){
            titleBar = "Rencana Penyelidikan";
            tvDesc.setText("Ini adalah deskripsi produk untuk rencana penyelidikan");
            tvDesc1.setText("Kalau ini, saya tidak tahu untuk apa. tapi dalam design ada kolom kosong");
            descbarang.setText("Deskripsi Produk");
        } else {
            titleBar = "Inquiry Plano";
            tvDesc.setText("This is product description for inquiry Plano");
            tvDesc1.setText("If here, I don't know for what, but in design exist empty column");
            descbarang.setText("Product Description");
        }
        getSupportActionBar().setTitle(titleBar);

        etPLU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputPLU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etLPP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLPP.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etFrac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputFrac.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFrac.setText("");
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent close = new Intent(SOInquiryPlanoctivity.this, HomeActivity.class);
                close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(close);
            }
        });

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