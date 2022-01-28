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
import android.widget.ProgressBar;
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
import mobile.indomarco.com.migrasiHH.adapter.InquiryPlanoAdapter;
import mobile.indomarco.com.migrasiHH.adapter.LIstTransaksiSOAdapter;
import mobile.indomarco.com.migrasiHH.model.InquiryPlanoModel;

public class SOInquiryPlanoctivity extends AppCompatActivity {

    private TextInputLayout inputPLU, inputLPP, inputFrac;
    private TextInputEditText etPLU, etLPP, etFrac;
    private TextView tvDesc, tvDesc1;
    private Button btnClear, btnExit;
    private TextView descbarang;
    private SharedPreferences flagBHS;
    private String getBHS;
    private Activity activity;
    private IGRModule mdl = new IGRModule();
    private ListView listInq;
    private Dialog dialog;
    private ArrayList<InquiryPlanoModel> mList;
    private InquiryPlanoAdapter mAdapter;
    private ProgressBar prog_list;

    //URL
    private static String TAG_PARAM_GETPLU = "url";
    private static String TAG_PARAM_INPUTPLU = "StrInput";
    private static String TAG_STATUS = "message";
    private static String TAG_STATUS_LOCATION = "LOKASI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_inquiry_plano_ctivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

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
        listInq = findViewById(R.id.listInqPlano);
        prog_list = findViewById(R.id.prog_list_inq);

        String titleBar = "";

        if (getBHS.equals("IDN")) {
            titleBar = "Rencana Penyelidikan";
            descbarang.setText("Deskripsi Produk");
        } else {
            titleBar = "Inquiry Plano";
            descbarang.setText("Product Description");
        }
        getSupportActionBar().setTitle(titleBar);
        tvDesc1.setText("-----------------------------------------------------");

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

        listInq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final InquiryPlanoModel m = mList.get(position);

                String message = "";
                if (getBHS.equals("ENG")) {
                    message = "Product code: ";
                } else {
                    message = "Kode produk: ";
                }
                Toast.makeText(activity, message + m.getPrdcd(), Toast.LENGTH_SHORT).show();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFrac.setText("");
                etPLU.setText("");
                if (getBHS.equals("ENG")) {
                    tvDesc.setText("Product Description");
                } else {
                    tvDesc.setText("Deskripsi Barang");
                }
                etPLU.requestFocus();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit();
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

    private void getPLU() {

        prog_list.setVisibility(View.VISIBLE);
        listInq.setVisibility(View.GONE);
        mList = new ArrayList<>();
        mAdapter = new InquiryPlanoAdapter(activity, mList);
        listInq.setAdapter(mAdapter);

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkInqPlano);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETPLU, mdl.getIPWS(activity) + getResources().getString(R.string.linkInqPlano));
        params.put(TAG_PARAM_INPUTPLU, etPLU.getText().toString());
        JSONObject jobjParam = new JSONObject(params);


        JsonObjectRequest jobjReqPLU = new JsonObjectRequest(Request.Method.GET, URL, jobjParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    prog_list.setVisibility(View.GONE);
                    listInq.setVisibility(View.VISIBLE);

                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("Sukses")) {
                        JSONArray arr = response.getJSONArray(TAG_STATUS_LOCATION);

                        if (arr.length() > 0) {
                            for (int i = 0; arr.length() < 0; i++) {
                                JSONObject listPlu = arr.getJSONObject(i);

                                String kode_lokasi = listPlu.getString("KODELOKASI");
                                String prdcd = listPlu.getString("PRDCD");
                                String desc = listPlu.getString("DESKRIPSI");
                                String frac = listPlu.getString("FRAC");
                                String qty = listPlu.getString("QTY");
                                String maxPlano = listPlu.getString("MAXPLANO");
                                String qty_lpp = listPlu.getString("QTY_LPP");

                                mList.add(new InquiryPlanoModel(kode_lokasi, prdcd, desc, frac, qty, maxPlano, qty_lpp));
                            }

                            JSONObject listPlu = arr.getJSONObject(0);
                            String prdcd = listPlu.getString("PRDCD");
                            String desc = listPlu.getString("DESKRIPSI");
                            String frac = listPlu.getString("FRAC");
                            String qty_lpp = listPlu.getString("QTY_LPP");

                            etPLU.setText(prdcd);
                            tvDesc.setText(desc);
                            etFrac.setText(frac);
                            etLPP.setText(qty_lpp);

                        } else {
                            showErrorDialog("ERR1");

                            etFrac.setText("");
                            etPLU.setText("");
                            if (getBHS.equals("ENG")) {
                                tvDesc.setText("Product Description");
                            } else {
                                tvDesc.setText("Deskripsi Barang");
                            }
                            etPLU.requestFocus();
                        }

                        mAdapter = new InquiryPlanoAdapter(activity, mList);
                        mAdapter.notifyDataSetChanged();

                    }

                } catch (Exception e) {
                    prog_list.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog_list.setVisibility(View.GONE);

                String message = "";
                String Y = "";
                String N = "";
                String m1 = "";

                if (getBHS.equals("ENG")) {
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
                        error instanceof ClientError) {
                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(SOInquiryPlanoctivity.this);
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
                } else {
                    Toast.makeText(SOInquiryPlanoctivity.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOInquiryPlanoctivity.this).add(jobjReqPLU);

    }

    private void dialogExit() {

        String message = "";
        String Y = "";
        String N = "";

        if (getBHS.equals("ENG")) {
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
                        Intent exit = new Intent(SOInquiryPlanoctivity.this, HomeActivity.class);
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

        if (getBHS.equals("ENG")) {
            if (messageError.equalsIgnoreCase("ERR1")) {
                message = "PLU not find at Location Master!";
            }
        } else {
            if (messageError.equalsIgnoreCase("ERR1")) {
                message = "PLU tidak ditemukan di Master Lokasi!";
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