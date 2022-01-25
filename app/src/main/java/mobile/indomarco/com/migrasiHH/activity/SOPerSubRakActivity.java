package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mobile.indomarco.com.migrasiHH.R;

public class SOPerSubRakActivity extends AppCompatActivity {

    private androidx.appcompat.widget.SearchView searchView;
    private ListView listTr;
    private LinearLayout lyNottr;
    private TextInputLayout plu,frac,ctn,pcs;
    private TextInputEditText etplu, etfrac, etctn, etpcs;
    private Button btnSubmit, btnClear, btnExit, btnSearch;
    private TextView titleRak, tvTransaksiKosong;
    private SharedPreferences flagBHS;
    private String getBHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_per_sub_rak_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        searchView = findViewById(R.id.search_listSODisplay);
        listTr = findViewById(R.id.listTransaksi);
        lyNottr = findViewById(R.id.lyNotTr);
        plu = findViewById(R.id.input_disPlu);
        frac = findViewById(R.id.input_disFrac);
        ctn = findViewById(R.id.input_disqtyCTN);
        pcs = findViewById(R.id.input_disqtypcs);
        etplu = findViewById(R.id.et_disPlu);
        etfrac = findViewById(R.id.et_disFrac);
        etctn = findViewById(R.id.et_disqtyCTN);
        etpcs = findViewById(R.id.et_disqtypcs);

        btnClear = findViewById(R.id.btnClearDis);
        btnSubmit = findViewById(R.id.btnSubmitDis);
        btnExit = findViewById(R.id.btnExitDis);
        btnSearch = findViewById(R.id.btnCariSODisplay);

        titleRak = findViewById(R.id.titleRak);
        tvTransaksiKosong = findViewById(R.id.tvTransaksiKosong);

        listTr.setVisibility(View.GONE);
        lyNottr.setVisibility(View.VISIBLE);

        String titleBar = "";
        String hintSearch = "";
        if (getBHS.equals("IDN")){
            titleBar = "SO Tampilan / Rak";
            btnClear.setText("Hapus");
            btnExit.setText("Keluar");
            btnSubmit.setText("Kirim");
            btnSearch.setText("Cari");
            titleRak.setText("Rak");
            tvTransaksiKosong.setText("Transaksi Kosong");
            hintSearch = "Cari disini...";
        } else {
            titleBar = "SO Display / Shelving";
            btnClear.setText("Clear");
            btnExit.setText("Exit");
            btnSubmit.setText("Submit");
            btnSearch.setText("Search");
            titleRak.setText("Rack");
            tvTransaksiKosong.setText("Empty Transaction");
            hintSearch = "Search here...";
        }
        getSupportActionBar().setTitle(titleBar);

        searchView.setQueryHint(hintSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        etplu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                plu.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etfrac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                frac.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etctn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ctn.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etpcs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pcs.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etplu.setText("");
                etfrac.setText("");
                etctn.setText("");
                etpcs.setText("");
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exit = new Intent(SOPerSubRakActivity.this, HomeActivity.class);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getBHS.equals("ENG"))
                {
                    if (etplu.getText().toString().trim().length() == 0){
                        plu.setError("This part not empty.");
                    } else if (etfrac.getText().toString().trim().length() == 0){
                        frac.setError("This part not empty.");
                    } else if (etctn.getText().toString().trim().length() == 0){
                        ctn.setError("This part not empty.");
                    } else if (etpcs.getText().toString().trim().length() == 0){
                        pcs.setError("This part not empty.");
                    } else {
                        dialogENG();
                    }

                } else {
                    if (etplu.getText().toString().trim().length() == 0){
                        plu.setError("Bagian ini tidak boleh kosong.");
                    } else if (etfrac.getText().toString().trim().length() == 0){
                        frac.setError("Bagian ini tidak boleh kosong.");
                    } else if (etctn.getText().toString().trim().length() == 0){
                        ctn.setError("Bagian ini tidak boleh kosong.");
                    } else if (etpcs.getText().toString().trim().length() == 0){
                        pcs.setError("Bagian ini tidak boleh kosong.");
                    } else {
                        dialogIDN();
                    }

                }
            }
        });

    }

    private void dialogIDN() {
        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(SOPerSubRakActivity.this);
        alert.setCancelable(false);
        alert.setMessage("Apakah data sudah benar?");
        alert.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        submitData();
                        dialog.dismiss();

                        Intent intent = new Intent(SOPerSubRakActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        alert.setNegativeButton("TIDAK",
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

    private void dialogENG() {
        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(SOPerSubRakActivity.this);
        alert.setCancelable(false);
        alert.setMessage("is data the correct?");
        alert.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        submitData();
                        dialog.dismiss();

                        Intent intent = new Intent(SOPerSubRakActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        alert.setNegativeButton("NO",
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

    private void submitData() {
        if (getBHS.equals("ENG")){
            Toast.makeText(SOPerSubRakActivity.this, "submit data successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SOPerSubRakActivity.this, "kirim data berhasil", Toast.LENGTH_SHORT).show();
        }
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