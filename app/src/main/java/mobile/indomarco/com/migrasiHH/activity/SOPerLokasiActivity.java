package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;

public class SOPerLokasiActivity extends AppCompatActivity {

    private TextInputLayout inputLokasi, inputPLU, inputFrac, inQtyLamaCTN, InQtyLamaPcs, inQtyBaruCTN, inQtyBaruPcs;
    private TextInputEditText etLokasi, etPLU, etFrac, etQtyLamaCTN, etQtyLamaPcs, etQtyBaruCTN, etQtyBaruPcs;
    private ImageView cekbox;
    private Button btnEditPlu, btnSubmit, btnClear, btnExit;
    private TextView desc, descTitle, titleqtylama, titleqtybaru;
    private SharedPreferences flagBHS;
    private String getBHS;
    private Activity activity;
    private Dialog dialog;
    private IGRModule mdl = new IGRModule();

    //tag URL
    private static String TAG_PARAM_GETLOKASI = "u";
    private static String TAG_PARAM_GETPLU = "StrInput";
    private static String TAG_STATUS = "message";
    private static String TAG_PLU = "PLU";
    private static String TAG_LOCATION = "lokasi";
    private static String TAG_PLU_SUBMIT = "plu";
    private static String TAG_FRAG = "frac";
    private static String TAG_QTY_CTN = "qtyctn";
    private static String TAG_QTY_PCS = "qtypcs";
    private static String TAG_USER_ID = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_perlokasi_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

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
                String[] lok = etLokasi.getText().toString().split(".");
                String getLok = lok[0];
                String getLok1 = getLok.substring(0,1);

                if (etLokasi.getText().toString().length() == 0){
                    showErrorDialog("Lokasi_error", getBHS);
                    etLokasi.requestFocus();
                } else if (!getLok1.equals("S")){
                    showErrorDialog("Lokasi_error1", getBHS);
                    etLokasi.requestFocus();
                } else {
                    getLocationSO();
                }
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
                getPLU();
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
                if (etLokasi.getText().toString().isEmpty()){
                    //ada tulisan Exit. blum tau action detail kayak gimana
                    Toast.makeText(activity, "misalkan lokasinya harus diisi dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    etPLU.setText("");
                    etPLU.requestFocus();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLokasi.setText("");
                etPLU.setText("");
                etFrac.setText("");
                etQtyLamaCTN.setText("");
                etQtyLamaPcs.setText("");
                etQtyBaruCTN.setText("");
                etQtyBaruPcs.setText("");
                etLokasi.requestFocus();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getBHS.equals("ENG")){
                    dialogExit("ENG");
                } else {
                    dialogExit("IDN");
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String baruCTN = etQtyBaruCTN.getText().toString();
                String baruPCS = etQtyBaruPcs.getText().toString();

                if (etLokasi.getText().toString().length() == 0){
                    showErrorDialog("PLU5", getBHS);
                } else if (!baruCTN.isEmpty() && !baruPCS.isEmpty()){
                    dialogSubmit(getBHS);
                }
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

    private void getLocationSO() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSOLok_GetLokasi);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETLOKASI, etLokasi.getText().toString());
        JSONObject jobjParam = new JSONObject(params);


        JsonObjectRequest jobjReqLokasi = new JsonObjectRequest(Request.Method.GET, URL, jobjParam, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("Sukses")) {
                        JSONArray arr = response.getJSONArray(TAG_PLU);
                        if (arr.length() > 0)
                        {
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject oList = arr.getJSONObject(i);

                                String lso_prdcd = oList.getString("LSO_PRDCD");
                                String prd_desc_pnjng = oList.getString("PRD_DESKRIPSIPANJANG");
                                String frac = oList.getString("PRD_FRAC");
                                String lso_qty = oList.getString("LSO_QTY");
                                String lso_frag_sarana = oList.getString("LSO_FLAGSARANA");
                                String lso_modify = oList.getString("LSO_MODIFY_BY");
                                String avg_cost = oList.getString("ST_AVGCOST");

                                if (lso_prdcd.equals("NONE")){
                                    showErrorDialog("PLU1", getBHS);
                                    requestFocusLokasi();
                                }
                                if (lso_frag_sarana.equals("K")){
                                    showErrorDialog("PLU2", getBHS);
                                    requestFocusLokasi();
                                }
                                if (avg_cost.length() <= 0){
                                    showErrorDialog("PLU3", getBHS);
                                    requestFocusLokasi();
                                }

                                etPLU.setText(lso_prdcd);
                                desc.setText(prd_desc_pnjng);
                                etFrac.setText(frac);
                                Double CTN = Double.valueOf(lso_qty)/Double.valueOf(frac);
                                Double PCS = Double.valueOf(lso_qty) % Double.valueOf(frac);
                                etQtyLamaCTN.setText(String.valueOf(CTN));
                                etQtyLamaPcs.setText(String.valueOf(PCS));

                                if (lso_modify.isEmpty()){
                                    cekbox.setVisibility(View.GONE);
                                } else {
                                    cekbox.setVisibility(View.VISIBLE);
                                }
                                etQtyBaruCTN.requestFocus();

                            }
                        }
                        else {
                            showErrorDialog("PLU", getBHS);
                            requestFocusLokasi();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String message = "";
                String Y = "";
                String N = "";
                String m1 = "";

                if (getBHS.equals("ENG"))
                {
                    message = "Connection problem";
                    Y = "Try Again";
                    N = "NO";
                    m1 = "try again later";
                } else {
                    message = "Jaringan bermasalah";
                    Y = "Coba Kembali";
                    N = "TIDAK";
                    m1 = "coba beberapa saat lagi";
                }

                //koment ini diilangin ya semisal setup IP ada condition error di networknya
                if (error instanceof NetworkError ||
                        error instanceof ServerError ||
                        error instanceof ParseError ||
                        error instanceof NoConnectionError ||
                        error instanceof TimeoutError ||
                        error instanceof ClientError)
                {
                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(SOPerLokasiActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getLocationSO();
                        }
                    });
                    alert.setNegativeButton(N, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(SOPerLokasiActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
        }
        });

        Volley.newRequestQueue(SOPerLokasiActivity.this).add(jobjReqLokasi);

    }

    private void getPLU() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSOLok_GetPlu);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETPLU, etPLU.getText().toString());
        JSONObject jobjParam = new JSONObject(params);


        JsonObjectRequest jobjReqPLU = new JsonObjectRequest(Request.Method.GET, URL, jobjParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("Sukses")) {

                        String PLU = response.getString("PLU");
                        String DESC = response.getString("DESKRIPSI");
                        String FRAC = response.getString("FRAC");

                        if (PLU.equals("NONE")){
                            showErrorDialog("PLU4", getBHS);
                            requestFocusPLU();
                        }

                        etPLU.setText(PLU);
                        desc.setText(DESC);
                        etFrac.setText(FRAC);
                        etQtyLamaCTN.setText(0);
                        etQtyLamaPcs.setText(0);
                        cekbox.setVisibility(View.GONE);

                        etQtyBaruCTN.requestFocus();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String message = "";
                String Y = "";
                String N = "";
                String m1 = "";

                if (getBHS.equals("ENG"))
                {
                    message = "Connection problem";
                    Y = "Try Again";
                    N = "NO";
                    m1 = "try again later";
                } else {
                    message = "Jaringan bermasalah";
                    Y = "Coba Kembali";
                    N = "TIDAK";
                    m1 = "coba beberapa saat lagi";
                }

                //koment ini diilangin ya semisal setup IP ada condition error di networknya
                if (error instanceof NetworkError ||
                        error instanceof ServerError ||
                        error instanceof ParseError ||
                        error instanceof NoConnectionError ||
                        error instanceof TimeoutError ||
                        error instanceof ClientError)
                {
                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(SOPerLokasiActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getPLU();
                        }
                    });
                    alert.setNegativeButton(N, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(SOPerLokasiActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOPerLokasiActivity.this).add(jobjReqPLU);

    }

    private void requestFocusPLU() {
        etPLU.requestFocus();
    }

    private void requestFocusLokasi() {
        etLokasi.requestFocus();
    }

    private void dialogSubmit(String bhs) {

        String message = "";
        String Y = "";
        String N = "";

        if (bhs.equals("ENG"))
        {
            message = "Are you sure update this location?";
            Y = "YES";
            N = "NO";
        } else {
            message = "Apakah anda yakin mengupdate lokasi ini?";
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
                        cekInitial();
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

    private void dialogExit(String bhs) {

        String message = "";
        String Y = "";
        String N = "";

        if (bhs.equals("ENG"))
        {
            message = "Are you sure out to menu?";
            Y = "YES";
            N = "NO";
        } else {
            message = "Apakah anda yakin ingin keluar ke menu?";
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
                        Intent exit = new Intent(SOPerLokasiActivity.this, HomeActivity.class);
                        exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(exit);
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

    private void cekInitial() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSOLok_Inital);

        JsonObjectRequest jobjcekInit = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("True")) {
                        submitProcess();
                    } else {
                        String error = response.getString("errorMessage");
                        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();

                        Intent home = new Intent(SOPerLokasiActivity.this, HomeActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String message = "";
                String Y = "";
                String N = "";
                String m1 = "";

                if (getBHS.equals("ENG"))
                {
                    message = "Connection problem";
                    Y = "Try Again";
                    N = "NO";
                    m1 = "try again later";
                } else {
                    message = "Jaringan bermasalah";
                    Y = "Coba Kembali";
                    N = "TIDAK";
                    m1 = "coba beberapa saat lagi";
                }

                //koment ini diilangin ya semisal setup IP ada condition error di networknya
                if (error instanceof NetworkError ||
                        error instanceof ServerError ||
                        error instanceof ParseError ||
                        error instanceof NoConnectionError ||
                        error instanceof TimeoutError ||
                        error instanceof ClientError)
                {
                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(SOPerLokasiActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cekInitial();
                        }
                    });
                    alert.setNegativeButton(N, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(SOPerLokasiActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOPerLokasiActivity.this).add(jobjcekInit);

    }

    private void submitProcess() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSOLok_Submit);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_LOCATION, etLokasi.getText().toString());
        params.put(TAG_PLU_SUBMIT, etPLU.getText().toString());
        params.put(TAG_FRAG, etFrac.getText().toString());
        params.put(TAG_QTY_CTN, etQtyBaruCTN.getText().toString());
        params.put(TAG_QTY_PCS, etQtyBaruPcs.getText().toString());
        params.put(TAG_USER_ID, mdl.getUser(activity));
        JSONObject jobjParam = new JSONObject(params);


        JsonObjectRequest jobjSubmit = new JsonObjectRequest(Request.Method.POST, URL, jobjParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("Sukses")) {
                        showSuccessAlert();
                    } else if (status.equals("AVGCOST = 0")){
                        showErrorDialog("PLU6", getBHS);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String message = "";
                String Y = "";
                String N = "";
                String m1 = "";

                if (getBHS.equals("ENG"))
                {
                    message = "Connection problem";
                    Y = "Try Again";
                    N = "NO";
                    m1 = "try again later";
                } else {
                    message = "Jaringan bermasalah";
                    Y = "Coba Kembali";
                    N = "TIDAK";
                    m1 = "coba beberapa saat lagi";
                }

                //koment ini diilangin ya semisal setup IP ada condition error di networknya
                if (error instanceof NetworkError ||
                        error instanceof ServerError ||
                        error instanceof ParseError ||
                        error instanceof NoConnectionError ||
                        error instanceof TimeoutError ||
                        error instanceof ClientError)
                {
                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(SOPerLokasiActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            submitProcess();
                        }
                    });
                    alert.setNegativeButton(N, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.create();
                    alert.show();
                }
                else
                {
                    Toast.makeText(SOPerLokasiActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOPerLokasiActivity.this).add(jobjSubmit);

    }

    private void showSuccessAlert() {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tv = dialog.findViewById(R.id.titleSuccessDialog);
        ImageView iv = dialog.findViewById(R.id.ivSuccessDialog);
        Button btnClose = dialog.findViewById(R.id.btnCLose);

        String message = "";

        if (getBHS.equals("ENG")){
            message = "Location update success";
        } else {
            message = "Update Lokasi sukses";
        }

        tv.setText(message);
        iv.setImageResource(R.drawable.ic_check_circle);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showErrorDialog(String messageError, String bhs) {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tv = dialog.findViewById(R.id.titleErrorDialog);
        ImageView iv = dialog.findViewById(R.id.ivErrorDialog);
        Button btnClose = dialog.findViewById(R.id.btnCLose);

        String message = "";

        if (bhs.equals("ENG")){
            if (messageError.equalsIgnoreCase("Lokasi_error")){
                message = "Location not valid";
            } else if (messageError.equalsIgnoreCase("Lokasi_error1")){
                message = "Location must be storage type";
            } else if (messageError.equalsIgnoreCase("PLU")){
                message = "Location not find";
            } else if (messageError.equalsIgnoreCase("PLU1")){
                message = "Location not filled with any PLU";
            } else if (messageError.equalsIgnoreCase("PLU2")){
                message = "Location registered in paper path";
            } else if (messageError.equalsIgnoreCase("PLU3")){
                message = "Avg Cost PLU = 0, must fixed more";
            } else if (messageError.equalsIgnoreCase("PLU4")){
                message = "PLU not find";
            } else if (messageError.equalsIgnoreCase("PLU5")){
                message = "Location kode must filled in!";
            } else if (messageError.equalsIgnoreCase("PLU6")){
                message = "Avg Cost PLU = 0, must fixed more";
            }
        } else {
            if (messageError.equalsIgnoreCase("Lokasi_error")){
                message = "Lokasi tidak valid";
            } else if (messageError.equalsIgnoreCase("Lokasi_error1")){
                message = "Lokasi harus jenis storage";
            } else if (messageError.equalsIgnoreCase("PLU1")){
                message = "Lokasi tidak terisi oleh PLU apapun";
            } else if (messageError.equalsIgnoreCase("PLU2")){
                message = "Lokasi terdaftar dalam jalur kertas";
            } else if (messageError.equalsIgnoreCase("PLU3")){
                message = "Avg Cost PLU = 0, harus perbaiki terlebih dahulu";
            } else if (messageError.equalsIgnoreCase("PLU4")){
                message = "PLU tidak ditemukan";
            } else if (messageError.equalsIgnoreCase("PLU5")){
                message = "Kode Lokasi harus terisi terlebih dahulu!";
            } else if (messageError.equalsIgnoreCase("PLU6")){
                message = "Avg Cost PLU = 0, harus perbaiki terlebih dahulu!";
            }
        }

        tv.setText(message);
        iv.setImageResource(R.drawable.ic_error);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}