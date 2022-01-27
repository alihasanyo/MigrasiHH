package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.adapter.InfoPluAdapter;
import mobile.indomarco.com.migrasiHH.adapter.LIstTransaksiSOAdapter;
import mobile.indomarco.com.migrasiHH.model.InfoPluModel;
import mobile.indomarco.com.migrasiHH.model.ListTransaksiSOModel;

public class SOInfoPLU extends AppCompatActivity {

    private TextView tvInfoPlu;
    private SharedPreferences flagBHS;
    private String getBHS;
    private IGRModule mdl = new IGRModule();
    private Activity activity;
    private ArrayList<InfoPluModel> mList;
    private InfoPluAdapter mAdapter;
    private ListView listInfo;
    private LinearLayout lyListInfo, lyNotInfo;
    private ProgressBar progList;

    //URL
    private static String TAG_PARAM_GETINFO = "url";
    private static String TAG_STATUS = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_info_plu_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        listInfo = findViewById(R.id.listInfoPlu);
        lyListInfo = findViewById(R.id.lyListInfoPlu);
        lyNotInfo = findViewById(R.id.lyNotInfo);
        progList = findViewById(R.id.progListPlu);

        String titleBar = "";

        if (getBHS.equals("IDN")){
            titleBar = "Info Plu";
        } else {
            titleBar = "Plu Info";
        }
        getSupportActionBar().setTitle(titleBar);

        loadInfoPlu();

        if (mList.size() > 0){
            lyListInfo.setVisibility(View.VISIBLE);
            lyNotInfo.setVisibility(View.GONE);
        } else {
            lyListInfo.setVisibility(View.GONE);
            lyNotInfo.setVisibility(View.VISIBLE);
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

    private void loadInfoPlu() {

        progList.setVisibility(View.VISIBLE);
        listInfo.setVisibility(View.GONE);
        mList = new ArrayList<>();
        mAdapter = new InfoPluAdapter(activity, mList);
        listInfo.setAdapter(mAdapter);

        String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkInfoPlu);
        Map<String, String> params = new HashMap<>();
        params.put(TAG_PARAM_GETINFO, mdl.getIPWS(activity) + getResources().getString(R.string.linkInfoPlu));
        JSONObject jobjParam = new JSONObject(params);


        JsonObjectRequest jobjReqInfo = new JsonObjectRequest(Request.Method.GET, URL, jobjParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    progList.setVisibility(View.GONE);
                    listInfo.setVisibility(View.VISIBLE);

                    String status = response.getString(TAG_STATUS);
                    //String status = "Sukses";
                    if (status.equals("Sukses")) {
                        JSONArray arr = response.getJSONArray("PLU");

                        if (arr.length() > 0) {
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject oList = arr.getJSONObject(i);

                                String prod_code = oList.getString("PRD_PRDCD");
                                String desc = oList.getString("PRD_DESKRIPSIPENDEK");
                                String unit = oList.getString("PRD_UNIT");
                                String tag = oList.getString("PRD_KODETAG");
                                String price = oList.getString("PRD_HRGJUAL");

                                mList.add(new InfoPluModel(prod_code, desc, unit, tag, price));
                            }

                        } else {

                            String message = "";
                            if (getBHS.equals("ENG")){
                                message = "PLU not find in Prodmast!";
                            } else {
                                message = "PLU tidak ditemukan di Prodmast!";
                            }
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                        }

                        mAdapter = new InfoPluAdapter(activity, mList);
                        mAdapter.notifyDataSetChanged();

                    }

                } catch (Exception e) {
                    progList.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progList.setVisibility(View.GONE);

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
                    alert = new AlertDialog.Builder(SOInfoPLU.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loadInfoPlu();
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
                    Toast.makeText(SOInfoPLU.this, m1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Volley.newRequestQueue(SOInfoPLU.this).add(jobjReqInfo);

    }
}