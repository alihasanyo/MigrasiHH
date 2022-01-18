package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;

public class HomeActivity extends AppCompatActivity  {

    private androidx.appcompat.widget.AppCompatImageButton btnSetting;
    private Activity activity;
    private SwipeRefreshLayout SwipeRefreshSO;
    private Dialog dialog;
    private ArrayAdapter spinnerSOHandAdapter;
    private ArrayList<String> listSOHand;
    private LinearLayout cvMenu;
    private LinearLayout layhand, layIPServer, layPerLokasi, layinfoPLU;
    private ImageView ivIcon;
    private AutoCompleteTextView searchMenu;
    private ArrayList<String> list_menu;
    private ArrayAdapter adapterMenu;
    private TextView titleHandheld, titleIP, titleSOLokasi, titleInfo;
    private SharedPreferences flagBHS;
    private String getBHS;
    private String setHintSearch = "";
    private IGRModule mdl = new IGRModule();
    private String getIPRelay,getIPWS;

    //tag URL
    private static String TAG_PARAM_URLWS = "url";
    private static String TAG_PARAM_IPDEVICE = "ip_device";
    private static String TAG_STATUS = "message";

    //cek yang masuk apakah user ADM/RST/selain itu
    private String cekUser, cekAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        activity = this;

        //cuma buat cek aja, ntr hapus juga gapapa
        cekUser = mdl.getUser(this);
        cekAccess = mdl.getAccess(this);
        try {
            if (cekUser.equals("None"))
            {
                Toast.makeText(activity, "Sorry your user " + cekUser + " not allowed", Toast.LENGTH_SHORT).show();
                mdl.deleteUser(activity);
                Intent forceOut = new Intent(activity, LoginActivity.class);
                forceOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(forceOut);
            } else {
                Toast.makeText(activity, "User login as " + cekUser, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // End

        flagBHS = getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
        getBHS = flagBHS.getString("getFBHS", "");

        layhand = findViewById(R.id.layHandheld);
        layIPServer = findViewById(R.id.layIPServer);
        layPerLokasi = findViewById(R.id.layPerLokasi);
        layinfoPLU = findViewById(R.id.layinfoPLU);
        btnSetting = findViewById(R.id.btnsettings);
        SwipeRefreshSO = findViewById(R.id.swipeRefreshSO);
        cvMenu = findViewById(R.id.cvMenu);
        ivIcon = findViewById(R.id.ivLauncher);
        searchMenu = findViewById(R.id.etSearchMenu);
        titleHandheld = findViewById(R.id.titleHandhled);
        titleIP = findViewById(R.id.titleIP);
        titleSOLokasi = findViewById(R.id.titleSOLokasi);
        titleInfo = findViewById(R.id.titleInfoPlu);

        list_menu = new ArrayList<String>();
        switch (getBHS){
            case "IDN" :
                titleHandheld.setText("Handheld");
                titleIP.setText("Konfigurasi IP Server");
                titleSOLokasi.setText("SO Per Lokasi");
                titleInfo.setText("Info Plu");

                list_menu.clear();
                list_menu.add("Handheld - Tampilan");
                list_menu.add("Handheld - Penyimpanan Kecil");
                list_menu.add("Handheld - Rencana Penyelidikan");
                list_menu.add("Konfigurasi IP Server");
                list_menu.add("SO Per Lokasi");
                list_menu.add("Info Plu");
                list_menu.add("Pengaturan");

                setHintSearch = "Cari di Stock Opname";
                break;

            case "ENG" :
                titleHandheld.setText("Handheld");
                titleIP.setText("IP Server Configuration");
                titleSOLokasi.setText("SO Per Location");
                titleInfo.setText("Plu Info");

                list_menu.clear();
                list_menu.add("Handheld - Display");
                list_menu.add("Handheld - Small Storage");
                list_menu.add("Handheld - Inquiry Plano");
                list_menu.add("IP Server Configuration");
                list_menu.add("SO Per Location");
                list_menu.add("Plu Info");
                list_menu.add("Settings");

                setHintSearch = "Search in Stock Opname";
                break;

            default:
                break;
        }

        adapterMenu = new ArrayAdapter(activity,
                R.layout.support_simple_spinner_dropdown_item, list_menu);
        searchMenu.setHint(setHintSearch);
        searchMenu.setThreshold(2);
        searchMenu.setAdapter(adapterMenu);
        searchMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.contains("IP Server")) {
                    moveActivity(1);
                } else if (selectedItem.contains("SO Per Location") || selectedItem.contains("SO Per Lokasi")) {
                    moveActivity(2);
                } else if (selectedItem.contains("Info Plu") || selectedItem.contains("Plu Info")) {
                    moveActivity(3);
                } else if (selectedItem.contains("Display") || selectedItem.contains("Tampilan")) {
                    moveActivity(4);
                } else if (selectedItem.contains("Small Storage") || selectedItem.contains("Penyimpanan Kecil")) {
                    moveActivity(5);
                } else if (selectedItem.contains("Plano") || selectedItem.contains("Rencana Penyelidikan")) {
                    moveActivity(6);
                } else {
                    moveActivity(7);
                }
            }
        });
        adapterMenu.notifyDataSetChanged();

        SwipeRefreshSO.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        SwipeRefreshSO.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do not things
                getMenu();
            }
        });

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMenu();
            }
        });

        layhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHandheldDialog();
            }
        });

        layIPServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SOIPConfigActivity.class));
            }
        });

        layPerLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SOPerLokasiActivity.class));
            }
        });

        layinfoPLU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SOInfoPLU.class));
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOtherMenu();
            }
        });

        getMenu();
    }

    private void showOtherMenu () {
           startActivity(new Intent(activity, SettingActivity.class));
        }

    private void showHandheldDialog () {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_so_handheld);
        dialog.setCancelable(true);
        dialog.show();

        final SearchableSpinner sp = dialog.findViewById(R.id.spTypeHand);
        Button btnclose = dialog.findViewById(R.id.closeDialogHand);
        TextView titlePilihTipe = dialog.findViewById(R.id.titlePilihTipe);

        listSOHand = new ArrayList<>();
        if (getBHS.equals("IDN"))
        {
            titlePilihTipe.setText("Pilih Tipe");
            listSOHand.clear();
            listSOHand.add("SONAS - Tampilan");
            listSOHand.add("SONAS - Penyimpanan Kecil");
            listSOHand.add("SONAS - Rencana Penyelidikan");

            sp.setTitle("Pilih Tipe SO-Handheld");
        }
        else
        {
            titlePilihTipe.setText("Choose Type");
            listSOHand.clear();
            listSOHand.add("SONAS - Display");
            listSOHand.add("SONAS - Small Storage");
            listSOHand.add("SONAS - Inquiry Plano");

            sp.setTitle("Choose SO-Handheld Type");
        }

        spinnerSOHandAdapter = new ArrayAdapter(activity,
                R.layout.support_simple_spinner_dropdown_item, listSOHand);
        sp.setAdapter(spinnerSOHandAdapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] kode = sp.getSelectedItem().toString().split(" - ");
                String kodeSO = kode[1];

                if (getBHS.equals("IDN"))
                {
                    if (kodeSO.contains("Tampilan")) {
                        moveActivity(4);
                    } else if (kodeSO.contains("Penyimpanan Kecil")) {
                        moveActivity(5);
                    } else {
                        moveActivity(6);
                    }
                }
                else
                {
                    if (kodeSO.contains("Display")) {
                        moveActivity(4);
                    } else if (kodeSO.contains("Small Storage")) {
                        moveActivity(5);
                    } else {
                        moveActivity(6);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void moveActivity(int i) {
        switch (i){
            case 1:
                startActivity(new Intent(activity, SOIPConfigActivity.class));
                break;
            case 2 :
                startActivity(new Intent(activity, SOPerLokasiActivity.class));
                break;
            case 3 :
                startActivity(new Intent(activity, SOInfoPLU.class));
                break;
            case 4 :
                startActivity(new Intent(activity, SOPerSubRakActivity.class));
                break;
            case 5 :
                startActivity(new Intent(activity, SOStorageKecilActivity.class));
                break;
            case 6:
                startActivity(new Intent(activity, SOInquiryPlanoctivity.class));
                break;
            case 7 :
                showOtherMenu();
                break;
            default:
                break;
        }
    }

    private void getMenu () {
        SwipeRefreshSO.setRefreshing(true);
        cvMenu.setVisibility(View.GONE);
        getIPRelay = mdl.getIPRelay(this);
        getIPWS = mdl.getIPWS(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshSO.setRefreshing(false);
                cvMenu.setVisibility(View.VISIBLE);
                if (getIPRelay == null || getIPRelay.equalsIgnoreCase(""))
                {
                    showDialogError("ipr0");
                } else if (getIPWS == null || getIPWS.equalsIgnoreCase("")){
                    showDialogError("ipw0");
                } else {
                    //nothing to do
                }
            }
        }, 2000);

    }

    @Override
    public void onBackPressed () {
        closeApp(getBHS);
    }

    private void closeApp (String bahasa) {

        if (bahasa.equals("IDN")){
            DialogClose("IDN");
        } else {
            DialogClose("ENG");
        }
    }

    private void DialogClose(String bhs) {

        String message = "";
        String Y = "";
        String N = "";

        if (bhs.equals("ENG"))
        {
            message = "Are you sure out this apps?";
            Y = "YES";
            N = "NO";
        } else {
            message = "Apakah kamu yakin keluar aplikasi?";
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
                        if (cekUser.equals("RST") && cekAccess.equals("HANDHELD"))
                        {
                            processUserRST();
                        }
                        mdl.deleteUser(activity);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1500);
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

    private void showDialogError(String text) {

        String message = "";

        if (getBHS.equals("ENG"))
        {
            if (text.equals("ipr0")){
                message = "IP Relay Connection not yet configured";
            } else {
                message = "IP WS Connection not yet configured";
            }
        } else {
            if (text.equals("ipr0"))
            {
                message = "Koneksi IP Relay belum disetting";
            } else {
                message = "Koneksi IP WS belum disetting";
            }
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
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

        tv.setText(message);
        iv.setImageResource(R.drawable.ip1);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void processUserRST() {

        //String IP_Device = mdl.getIPDevice(activity);
        //String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkLogout);
        //Map<String, String> params = new HashMap<>();
        //params.put(TAG_PARAM_URLWS, mdl.getIPWS(activity) + getResources().getString(R.string.linkLogout));
        //params.put(TAG_PARAM_IPDEVICE, IP_Device);
        //JSONObject jobjParam = new JSONObject(params);


        //JsonObjectRequest jobjReqOut = new JsonObjectRequest(Request.Method.POST, URL, jobjParam, new Response.Listener<JSONObject>() {
        //@Override
        //public void onResponse(JSONObject response) {
        try {
            //Log.e("RESPONSE", response.toString());
            //String status = response.getString(TAG_STATUS);
            String status = "Sukses";                                      ///////////////// baris illangin aja ini, nyoba aja
            if (status.equals("Sukses")) {
                showSuccessAlert("Berhasil membuka akun Handheld");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //}
        //}
        //, new Response.ErrorListener() {
        //    @Override
        //    public void onErrorResponse(VolleyError error) {

        //koment ini diilangin ya semisal setup IP ada condition error di networknya
                /*if (error instanceof NetworkError ||
                        error instanceof ServerError ||
                        error instanceof ParseError ||
                        error instanceof NoConnectionError ||
                        error instanceof TimeoutError ||
                        error instanceof ClientError)
                {
                    String message = "";
                    String Y = "";
                    String N = "";

                    if (pilihBahasa.equals("ENG"))
                    {
                        message = "Connection problem";
                        Y = "Try Again";
                        N = "NO";
                    } else {
                        message = "Jaringan bermasalah";
                        Y = "Coba Kembali";
                        N = "TIDAK";
                    }

                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(HomeActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            processUserRST();
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
                } else
                {
                    Toast.makeText(HomeActivity.this, "Cobalah beberapa saat lagi", Toast.LENGTH_SHORT).show();
                }*/
        //}
        //});

        //Volley.newRequestQueue(HomeActivity.this).add(jobjReqOut);

    }

    private void showSuccessAlert(String message) {

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

        tv.setText(message);
        if (message.contains("Handheld"))
        {
            iv.setVisibility(View.GONE);
            btnClose.setVisibility(View.GONE);
        }

        iv.setVisibility(View.GONE);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}