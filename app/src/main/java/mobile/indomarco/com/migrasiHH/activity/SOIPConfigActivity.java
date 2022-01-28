package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;

public class SOIPConfigActivity extends AppCompatActivity {

    private Button btnClear,btnSave,btnClearVB,btnSaveVB;
    private TextInputLayout inputIP,inputIPVB;
    private TextInputEditText etIP,etIPVB;
    private TextView titleIPServer ,subtitleIpServer;
    private SharedPreferences flagBHS;
    private String getBHS;
    private Activity activity;
    private IGRModule mdl = new IGRModule();
    private String getIPRelay, getIPWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_ipconfig_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        getIPRelay = mdl.getIPRelay(this);
        getIPWS = mdl.getIPWS(this);

        btnClear = findViewById(R.id.btnClearRelay);
        btnSave = findViewById(R.id.btnSaveRelay);
        btnClearVB = findViewById(R.id.btnClearVB);
        btnSaveVB = findViewById(R.id.btnSaveVB);
        inputIP = findViewById(R.id.input_ipRelay);
        etIP = findViewById(R.id.et_ipRelay);
        inputIPVB = findViewById(R.id.input_ipVB);
        etIPVB = findViewById(R.id.et_ipVB);
        titleIPServer = findViewById(R.id.titleIPServer);
        subtitleIpServer = findViewById(R.id.subtitleIPServer);

        String titleBar = "";
        if (getBHS.equals("IDN")){
            titleBar = "Konfigurasi IP Server";
            titleIPServer.setText("Isikan IP Server");
            subtitleIpServer.setText("Contoh Inputan:");
            btnClear.setText("Hapus");
            btnSave.setText("Simpan");
            btnClearVB.setText("Hapus");
            btnSaveVB.setText("Simpan");
        } else {
            titleBar = "IP Server Configuration";
            titleIPServer.setText("Fill in IP Server");
            subtitleIpServer.setText("Sampling Input:");
            btnClear.setText("Clear");
            btnSave.setText("Save");
            btnClearVB.setText("Clear");
            btnSaveVB.setText("Save");
        }
        getSupportActionBar().setTitle(titleBar);

        if (getIPRelay != null){
            etIP.setText(getIPRelay);
        } else {
            etIP.setText("");
        }

        if (getIPWS != null){
            etIPVB.setText(getIPWS);
        } else {
            etIPVB.setText("");
        }


        etIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputIP.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etIPVB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputIPVB.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etIP.setText("");
            }
        });

        btnClearVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etIPVB.setText("");
            }
        });

        String ID_cond1 = "Harap masukkan data dengan benar";
        String ENG_cond1 = "Please input data the correct";

        btnSaveVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getBHS.equals("IDN")){

                    //ip ws
                    if (etIPVB.getText().toString().trim().length() > 15){
                        inputIPVB.setError(ID_cond1);
                    } else {
                        mdl.setIpWS(activity, etIPVB.getText().toString());
                        dialogSaveIP(etIPVB.getText().toString());
                    }

                }
                else {

                    //ip ws
                    if (etIPVB.getText().toString().trim().length() > 15){
                        inputIPVB.setError(ENG_cond1);
                    } else {
                        mdl.setIpWS(activity, etIPVB.getText().toString());
                        dialogSaveIP(etIPVB.getText().toString());
                    }

                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (getBHS.equals("IDN")){
                        //ip relay
                        if (etIP.getText().toString().trim().length() > 15){
                            inputIP.setError(ID_cond1);
                        } else {
                            mdl.setIpRelay(activity, etIP.getText().toString());
                            dialogSaveIP(etIP.getText().toString());
                        }

                    }
                    else {
                        //ip relay
                        if (etIP.getText().toString().trim().length() > 15){
                            inputIP.setError(ENG_cond1);
                        } else {
                            mdl.setIpRelay(activity, etIP.getText().toString());
                            dialogSaveIP(etIP.getText().toString());
                        }

                    }

            }
        });
    }

    private void dialogSaveIP(String IP) {

        String message = "";
        String Y = "";
        String N = "";

        if (getBHS.equals("ENG"))
        {
            message = "is data the correct for address " + IP + " ?";
            Y = "YES";
            N = "NO";
        } else {
            message = "Apakah data sudah benar untuk alamat " + IP + " ?";
            Y = "YA";
            N = "TIDAK";
        }

        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        alert.setMessage(message);
        alert.setPositiveButton(Y,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        InfosaveData(IP);
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

    private void InfosaveData(String ip_config) {

        if (getBHS.equals("ENG")){
            Toast.makeText(SOIPConfigActivity.this, "Data connection saved for " + ip_config, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SOIPConfigActivity.this, "Koneksi Data tersimpan untuk " + ip_config, Toast.LENGTH_SHORT).show();
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