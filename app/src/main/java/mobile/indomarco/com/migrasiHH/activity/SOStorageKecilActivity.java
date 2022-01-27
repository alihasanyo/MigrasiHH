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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.adapter.LIstTransaksiSOAdapter;
import mobile.indomarco.com.migrasiHH.model.ListTransaksiSOModel;

public class SOStorageKecilActivity extends AppCompatActivity {

    private androidx.appcompat.widget.SearchView searchView;
    private ListView listTr;
    private ArrayList<ListTransaksiSOModel> mList;
    private LinearLayout lyNottr;
    private LIstTransaksiSOAdapter mAdapter;
    private TextInputLayout plu,frac,ctn,pcs;
    private TextInputEditText etplu, etfrac, etctn, etpcs;
    private Button btnSubmit, btnClear, btnExit, btnSearch;
    private TextView titleRak, tvTransaksiKosong;
    private SharedPreferences flagBHS;
    private String getBHS;
    private IGRModule mdl = new IGRModule();
    private Activity activity;
    private Dialog dialog;

    //URL
    private static String TAG_PARAM_GETPLU = "url";
    private static String TAG_PARAM_INPUTPLU = "StrInput";
    private static String TAG_PARAM_INPUTFRAG = "u";
    private static String TAG_STATUS = "message";
    private static String TAG_LOCATION = "LOKASI";

    private static String TAG_LOCATION_SUBMIT = "lokasi";
    private static String TAG_PLU_SUBMIT = "plu";
    private static String TAG_FRAG = "frac";
    private static String TAG_QTY_CTN = "qtyctn";
    private static String TAG_QTY_PCS = "qtypcs";
    private static String TAG_USER_ID = "user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_storage_kecil_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        searchView = findViewById(R.id.search_listSOKecil);
        listTr = findViewById(R.id.listTrSOKecil);
        lyNottr = findViewById(R.id.lyNotTrSOKecil);
        plu = findViewById(R.id.input_SOKecilPlu);
        frac = findViewById(R.id.input_SOKecilFrac);
        ctn = findViewById(R.id.input_SOKecilqtyCTN);
        pcs = findViewById(R.id.input_SOKecilqtypcs);
        etplu = findViewById(R.id.et_SOKecilPlu);
        etfrac = findViewById(R.id.et_SOKecilFrac);
        etctn = findViewById(R.id.et_SOKecilqtyCTN);
        etpcs = findViewById(R.id.et_SOKecilqtypcs);

        btnClear = findViewById(R.id.btnClearSOKecil);
        btnSubmit = findViewById(R.id.btnSubmitSOKecil);
        btnExit = findViewById(R.id.btnExitSOKecil);
        btnSearch = findViewById(R.id.btnCariSOKecil);

        titleRak = findViewById(R.id.titleSOKecil);
        tvTransaksiKosong = findViewById(R.id.tvTraKosongSOKecil);

        listTr.setVisibility(View.GONE);
        lyNottr.setVisibility(View.VISIBLE);

        String titleBar = "";
        String hintSearch = "";
        if (getBHS.equals("IDN")){
            titleBar = "SO Storage Kecil";
            btnClear.setText("Hapus");
            btnExit.setText("Keluar");
            btnSubmit.setText("Kirim");
            btnSearch.setText("Cari");
            titleRak.setText("Rak");
            tvTransaksiKosong.setText("Transaksi Kosong");
            hintSearch = "Cari disini...";
        } else {
            titleBar = "SO Small Storage";
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
                getPLU();

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

        listTr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ListTransaksiSOModel m = mList.get(position);

                etplu.setEnabled(false);
                etplu.setText(m.getPlu());
                etfrac.setText(m.getFrac());

                Double ctn = Double.parseDouble(m.getQty()) / Double.parseDouble(m.getFrac());
                Double pcs = Double.parseDouble(m.getQty()) % Double.parseDouble(m.getFrac());

                etctn.setText(String.valueOf(ctn));
                etpcs.setText(String.valueOf(pcs));

                dialogFormInfoPLU(m.getPlu());
                etctn.requestFocus();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;

                // ini ngga maksud ->> dtTemp = listTr
                // For i = 0 To dtTemp.Rows.Count - 1

                if (flag == true){
                    etfrac.setEnabled(true);
                    etplu.setEnabled(false);
                    etplu.setText("");
                    etfrac.setText("");
                    etctn.setText("");
                    etpcs.setText("");
                    etfrac.requestFocus();
                } else {
                    showErrorDialog("ERR7");
                    etplu.setEnabled(true);
                    etplu.setText("");
                    etfrac.setText("");
                    etctn.setText("");
                    etpcs.setText("");
                    etplu.requestFocus();
                }

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;
                // ini ngga maksud ->> dtTemp = listTr
                // For i = 0 To dtTemp.Rows.Count - 1

                if (flag == true){
                    dialogExit();
                }

                Intent exit = new Intent(SOStorageKecilActivity.this, HomeActivity.class);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etfrac.getText().toString().length() == 0){
                    etfrac.requestFocus();
                } else if (etfrac.getText().toString().length() < 4){
                    showErrorDialog("ERR3");
                    etfrac.setText("");
                    etfrac.requestFocus();
                } else {
                    String[] arr = etfrac.getText().toString().split("\\.");
                    String arrr = arr[0];
                    String arrLeft = arrr.substring(1,2);
                    String arrRight = arrr.substring(0,1);
                    String arrLeft1 = arrr.substring(1,3);

                    if (!arrLeft.toUpperCase().equals("S") || !arrRight.toUpperCase().equals("C") || !arrLeft1.toUpperCase().equals("P")){
                        showErrorDialog("ERR4");
                        etfrac.setText("");
                        etfrac.requestFocus();
                    } else {
                        etfrac.setEnabled(false);
                        etplu.setEnabled(true);
                        etplu.setText("");
                        etfrac.setText("");
                        etctn.setText("");
                        etpcs.setText("");
                        etplu.requestFocus();
                    }

                    searchList();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etfrac.getText().toString().length() == 0){
                    showErrorDialog("ERR9");
                    etfrac.requestFocus();
                }

                if (etplu.isEnabled())
                {
                    showErrorDialog("ERR10");
                    etplu.requestFocus();
                }

                String ctn = etctn.getText().toString();
                String pcs = etpcs.getText().toString();

                if (!ctn.isEmpty() && !pcs.isEmpty()){
                    dialogSubmit();
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

    private void dialogFormInfoPLU(String plu) {

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

        tv.setText(plu);
        iv.setImageResource(R.drawable.ic_check_circle);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void getPLU() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSO_StoreKecil_GetPlu);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETPLU, mdl.getIPWS(activity) + getResources().getString(R.string.linkSO_StoreKecil_GetPlu));
        params.put(TAG_PARAM_INPUTPLU, etplu.getText().toString());
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
                            showErrorDialog("ERR1");
                            requestFocusPLU();
                        }

                        boolean flag = false;
                        //blum paham sama kondisi dtTemp = listTr
                        // For i = 0 To dtTemp.Rows.Count - 1
                        //jd aku skip

                        if (flag == true){
                            etplu.setText(PLU);
                            etfrac.setText(FRAC);
                            etctn.setText(0);
                            etpcs.setText(0);
                            etctn.requestFocus();
                        } else {
                            showErrorDialog("ERR2");
                            etplu.setText("");
                            etplu.requestFocus();
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
                    alert = new AlertDialog.Builder(SOStorageKecilActivity.this);
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
                    Toast.makeText(SOStorageKecilActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOStorageKecilActivity.this).add(jobjReqPLU);

    }

    private void requestFocusPLU() {
        etplu.requestFocus();
    }

    private void searchList() {

        listTr.setVisibility(View.GONE);
        mList = new ArrayList<>();
        mAdapter = new LIstTransaksiSOAdapter(activity, mList);
        listTr.setAdapter(mAdapter);

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSO_StoreKecil_GetList);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETPLU, mdl.getIPWS(activity) + getResources().getString(R.string.linkSO_StoreKecil_GetList));
        params.put(TAG_PARAM_INPUTFRAG, etfrac.getText().toString());
        JSONObject jobjParam = new JSONObject(params);


        JsonObjectRequest jobjReqList = new JsonObjectRequest(Request.Method.GET, URL, jobjParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    listTr.setVisibility(View.VISIBLE);

                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("Sukses")) {
                        JSONArray arr = response.getJSONArray(TAG_LOCATION);

                        if (arr.length() > 0) {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject oList = arr.getJSONObject(i);

                                String kode_lokasi = oList.getString("KODELOKASI");
                                String prdcd = oList.getString("PRDCD");
                                String desc = oList.getString("DESKRIPSI");
                                String frac1 = oList.getString("FRAC");
                                String qty = oList.getString("QTY");
                                String modified_by = oList.getString("MODIFY_BY");
                                String modified_dt = oList.getString("MODIFY_DT");
                                String avgCost = oList.getString("ST_AVGCOST");

                                boolean flag_AVGCost = true;
                                if (avgCost.length() <= 0){
                                    flag_AVGCost = false;
                                }

                                if (flag_AVGCost == true){
                                    mList.add(new ListTransaksiSOModel(prdcd, qty, frac1, kode_lokasi, avgCost, desc, modified_by, modified_dt));
                                } else {
                                    showErrorDialog("ERR6");
                                    etfrac.setEnabled(true);
                                    etplu.setEnabled(false);
                                    etfrac.setText("");
                                    etplu.setText("");
                                    etctn.setText("");
                                    etpcs.setText("");
                                    etfrac.requestFocus();
                                }

                            }
                        } else {
                            showErrorDialog("ERR5");
                        }

                        mAdapter = new LIstTransaksiSOAdapter(activity, mList);
                        mAdapter.notifyDataSetChanged();

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
                    alert = new AlertDialog.Builder(SOStorageKecilActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            searchList();
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
                    Toast.makeText(SOStorageKecilActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOStorageKecilActivity.this).add(jobjReqList);

    }

    private void dialogExit() {

        String message = "";
        String Y = "";
        String N = "";

        if (getBHS.equals("ENG"))
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
                        Intent exit = new Intent(SOStorageKecilActivity.this, HomeActivity.class);
                        exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(exit);
                    }
                });
        alert.setNegativeButton(N,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss
                        showErrorDialog("ERR8");
                        etplu.setEnabled(true);
                        etplu.setText("");
                        etfrac.setText("");
                        etctn.setText("");
                        etpcs.setText("");
                        etplu.requestFocus();
                    }
                });
        alert.create();
        alert.show();

    }

    private void dialogSubmit() {

        String message = "";
        String Y = "";
        String N = "";

        if (getBHS.equals("ENG"))
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

    private void cekInitial() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSOLok_Inital);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETPLU, mdl.getIPWS(activity) + getResources().getString(R.string.linkSOLok_Inital));
        JSONObject jobjParam = new JSONObject(params);

        JsonObjectRequest jobjcekInit = new JsonObjectRequest(Request.Method.POST, URL, jobjParam, new Response.Listener<JSONObject>() {
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

                        Intent home = new Intent(SOStorageKecilActivity.this, HomeActivity.class);
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
                    alert = new AlertDialog.Builder(SOStorageKecilActivity.this);
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
                    Toast.makeText(SOStorageKecilActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOStorageKecilActivity.this).add(jobjcekInit);

    }

    private void submitProcess() {

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkSO_StoreKecil_Submit);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETPLU, mdl.getIPWS(activity) + getResources().getString(R.string.linkSO_StoreKecil_Submit));
        //params.put(TAG_LOCATION_SUBMIT, ); --> yang lokasi blum maksud listTr.Item(listTr.CurrentCell.RowNumber, 0).ToString
        params.put(TAG_PLU_SUBMIT, etplu.getText().toString());
        params.put(TAG_FRAG, etfrac.getText().toString());
        params.put(TAG_QTY_CTN, etctn.getText().toString());
        params.put(TAG_QTY_PCS, etpcs.getText().toString());
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
                    } else {
                        showErrorDialog("ERR11");
                    }

                    etplu.setEnabled(true);
                    etplu.setText("");
                    etfrac.setText("");
                    etctn.setText("");
                    etpcs.setText("");

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
                    alert = new AlertDialog.Builder(SOStorageKecilActivity.this);
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
                    Toast.makeText(SOStorageKecilActivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOStorageKecilActivity.this).add(jobjSubmit);

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

    private void showErrorDialog(String messageError) {

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

        if (getBHS.equals("ENG")){
            if (messageError.equalsIgnoreCase("ERR1")){
                message = "Barcode / PLU not registered";
            } else if (messageError.equalsIgnoreCase("ERR2")){
                message = "PLU not find in shelving!";
            } else if (messageError.equalsIgnoreCase("ERR3")){
                message = "Location must be until shelving!";
            } else if (messageError.equalsIgnoreCase("ERR4")){
                message = "Location must small storage!";
            } else if (messageError.equalsIgnoreCase("ERR5")){
                message = "Location not find";
            } else if (messageError.equalsIgnoreCase("ERR6")){
                message = "Any Avg Cost PLU = 0, must fixed more";
            } else if (messageError.equalsIgnoreCase("ERR7")){
                message = "All SO must finished!";
            } else if (messageError.equalsIgnoreCase("ERR8")){
                message = "All SO must finished!";
            } else if (messageError.equalsIgnoreCase("ERR9")){
                message = "Location code must be filled in!";
            } else if (messageError.equalsIgnoreCase("ERR10")){
                message = "PLU must be filled in!";
            } else if (messageError.equalsIgnoreCase("ERR11")){
                message = "Update Lokasi error!";
            }
        } else {
            if (messageError.equalsIgnoreCase("ERR1")){
                message = "Barcode / PLU tidak terdaftar";
            } else if (messageError.equalsIgnoreCase("ERR2")){
                message = "PLU tidak ditemukan di shelving!";
            } else if (messageError.equalsIgnoreCase("ERR3")){
                message = "Lokasi harus sampai shelving!";
            } else if (messageError.equalsIgnoreCase("ERR4")){
                message = "Lokasi harus storage kecil!";
            } else if (messageError.equalsIgnoreCase("ERR5")){
                message = "Lokasi tidak ditemukan";
            } else if (messageError.equalsIgnoreCase("ERR6")){
                message = "Ada Avg Cost PLU = 0, harus perbaiki terlebih dahulu!";
            } else if (messageError.equalsIgnoreCase("ERR7")){
                message = "Semua SO harus diselesaikan terlebih dahulu!";
            } else if (messageError.equalsIgnoreCase("ERR8")){
                message = "Semua SO harus diselesaikan terlebih dahulu!";
            } else if (messageError.equalsIgnoreCase("ERR9")){
                message = "Kode Lokasi harus terisi terlebih dahulu!";
            } else if (messageError.equalsIgnoreCase("ERR10")){
                message = "PLU harus diinput terlebih dahulu!";
            } else if (messageError.equalsIgnoreCase("ERR11")){
                message = "Update Lokasi bermasalah!";
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