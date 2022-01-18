package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mobile.indomarco.com.migrasiHH.R;

public class SOPerLokasiActivity extends AppCompatActivity {

    private TextInputLayout inputLokasi, inputPLU, inputFrac, inQtyLamaCTN, InQtyLamaPcs, inQtyBaruCTN, inQtyBaruPcs;
    private TextInputEditText etLokasi, etPLU, etFrac, etQtyLamaCTN, etQtyLamaPcs, etQtyBaruCTN, etQtyBaruPcs;
    private ImageView cekbox;
    private Button btnEditPlu, btnSubmit, btnClear, btnExit;
    private TextView desc, descTitle, titleqtylama, titleqtybaru;
    private SharedPreferences flagBHS;
    private String getBHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_perlokasi_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        inputLokasi = findViewById(R.id.input_lok);
        etLokasi = findViewById(R.id.et_lok);
        inputPLU = findViewById(R.id.input_plu);
        etPLU = findViewById(R.id.et_plu);
        cekbox = findViewById(R.id.ivcek_lok);
        btnEditPlu = findViewById(R.id.btnEditPlu);
        desc = findViewById(R.id.descSOStorage);
        descTitle = findViewById(R.id.settitleDesc);
        titleqtylama = findViewById(R.id.titleQtyLama);
        titleqtybaru = findViewById(R.id.titleQtyBaru);

        inputFrac = findViewById(R.id.input_Frac);
        etFrac = findViewById(R.id.et_frac);
        inQtyLamaCTN = findViewById(R.id.input_Lamaqty);
        etQtyLamaCTN = findViewById(R.id.et_Lamaqty);
        InQtyLamaPcs = findViewById(R.id.input_Lamapcs);
        etQtyLamaPcs = findViewById(R.id.et_Lamapcs);
        inQtyBaruCTN = findViewById(R.id.input_Baruqty);
        etQtyBaruCTN = findViewById(R.id.et_Baruqty);
        inQtyBaruPcs = findViewById(R.id.input_Barupcs);
        etQtyBaruPcs = findViewById(R.id.et_Barupcs);

        btnSubmit = findViewById(R.id.btnSubmitStorage);
        btnClear = findViewById(R.id.btnClearStorage);
        btnExit = findViewById(R.id.btnExitStorage);

        String titleBar = "";
        if (getBHS.equals("IDN")){
            titleBar = "SO Penyimpanan";
            inputLokasi.setHint("Lokasi");
            descTitle.setText("Deskripsi Produk");
            titleqtylama.setText("Qty Lama");
            titleqtybaru.setText("Qty Baru");
            btnClear.setText("Hapus");
            btnExit.setText("Keluar");
            btnSubmit.setText("Kirim");
        } else {
            titleBar = "SO Storage";
            inputLokasi.setHint("Location");
            descTitle.setText("Product Description");
            titleqtylama.setText("Old Qty");
            titleqtybaru.setText("New Qty");
            btnClear.setText("Clear");
            btnExit.setText("Exit");
            btnSubmit.setText("Submit");
        }
        getSupportActionBar().setTitle(titleBar);

        etLokasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLokasi.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        etQtyLamaCTN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inQtyLamaCTN.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etQtyLamaPcs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InQtyLamaPcs.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etQtyBaruCTN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inQtyBaruCTN.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etQtyBaruPcs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inQtyBaruPcs.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnEditPlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPLU.setText("");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFrac.setText("");
                etQtyLamaCTN.setText("");
                etQtyLamaPcs.setText("");
                etQtyBaruCTN.setText("");
                etQtyBaruPcs.setText("");
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exit = new Intent(SOPerLokasiActivity.this, HomeActivity.class);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getBHS.equals("ENG"))
                {
                    if (etLokasi.getText().toString().trim().length() == 0){
                        inputLokasi.setError("This part not empty.");
                    } else if (etPLU.getText().toString().trim().length() == 0){
                        inputPLU.setError("This part not empty.");
                    } else if (etFrac.getText().toString().trim().length() == 0){
                        inputFrac.setError("This part not empty.");
                    } else if (etQtyLamaCTN.getText().toString().trim().length() == 0){
                        inQtyLamaCTN.setError("This part not empty.");
                    } else if (etQtyLamaPcs.getText().toString().trim().length() == 0){
                        InQtyLamaPcs.setError("This part not empty.");
                    } else if (etQtyBaruCTN.getText().toString().trim().length() == 0){
                        inQtyBaruCTN.setError("This part not empty.");
                    } else if (etQtyBaruPcs.getText().toString().trim().length() == 0){
                        inQtyBaruPcs.setError("This part not empty.");
                    } else {
                        dialogSubmitENG();
                    }

                }
                else {
                    if (etLokasi.getText().toString().trim().length() == 0){
                        inputLokasi.setError("Bagian ini tidak boleh kosong.");
                    } else if (etPLU.getText().toString().trim().length() == 0){
                        inputPLU.setError("Bagian ini tidak boleh kosong.");
                    } else if (etFrac.getText().toString().trim().length() == 0){
                        inputFrac.setError("Bagian ini tidak boleh kosong.");
                    } else if (etQtyLamaCTN.getText().toString().trim().length() == 0){
                        inQtyLamaCTN.setError("Bagian ini tidak boleh kosong.");
                    } else if (etQtyLamaPcs.getText().toString().trim().length() == 0){
                        InQtyLamaPcs.setError("Bagian ini tidak boleh kosong.");
                    } else if (etQtyBaruCTN.getText().toString().trim().length() == 0){
                        inQtyBaruCTN.setError("Bagian ini tidak boleh kosong.");
                    } else if (etQtyBaruPcs.getText().toString().trim().length() == 0){
                        inQtyBaruPcs.setError("Bagian ini tidak boleh kosong.");
                    } else {
                        dialogSubmitIDN();
                    }

                }
            }
        });

    }

    private void dialogSubmitIDN() {
        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(SOPerLokasiActivity.this);
        alert.setCancelable(false);
        alert.setMessage("Apakah data sudah benar?");
        alert.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        submitData();
                        dialog.dismiss();

                        Intent intent = new Intent(SOPerLokasiActivity.this, HomeActivity.class);
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

    private void dialogSubmitENG() {
        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(SOPerLokasiActivity.this);
        alert.setCancelable(false);
        alert.setMessage("is data the correct?");
        alert.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        submitData();
                        dialog.dismiss();

                        Intent intent = new Intent(SOPerLokasiActivity.this, HomeActivity.class);
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
            Toast.makeText(SOPerLokasiActivity.this, "Submit data successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SOPerLokasiActivity.this, "Masukkan data berhasil", Toast.LENGTH_SHORT).show();
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