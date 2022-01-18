package mobile.indomarco.com.migrasiHH.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.activity.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputUsername,inputPass;
    private TextInputEditText etUsername,etPass;
    private Button btnLogin, btnExit;
    Activity activity;
    private TextView tvBhs;
    private Spinner spbhs;
    private ArrayList<String> listBahasa;
    private ArrayAdapter adapterBhs;
    private String pilihBahasa = "";
    private IGRModule mdl = new IGRModule();
    private String IP_Device;
    private SpotsDialog sDialog;
    private Dialog dialog;

    //tag URL
    private static String TAG_PARAM_USERID = "username";
    private static String TAG_PARAM_USERPASS = "password";
    private static String TAG_PARAM_IPDEVICE = "ip_device";
    private static String TAG_PARAM_URLWS = "url";
    private static String TAG_STATUS = "message";
    private static String TAG_ACCESS = "access_RST";
    private static String TAG_ACCESS_NAME = "access_name";
    private static String TAG_USER_LEVEL = "user_level";
    private static String TAG_KODE_IGR = "kode_igr";
    private static String TAG_STATION = "station";
    private static String TAG_KODESTATION = "kode_station";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        activity = this;

        IP_Device = getDeviceIP();
        mdl.setIpDevice(activity, IP_Device);

        inputUsername = findViewById(R.id.login_input_username);
        inputPass = findViewById(R.id.login_input_password);
        etUsername = findViewById(R.id.login_username);
        etPass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.loginSO);
        btnExit = findViewById(R.id.exitSO);
        tvBhs = findViewById(R.id.tvlanguage);
        spbhs = findViewById(R.id.splanguage);

        listBahasa = new ArrayList<String>();
        listBahasa.add("IDN");
        listBahasa.add("ENG");
        adapterBhs = new ArrayAdapter(activity,
                R.layout.support_simple_spinner_dropdown_item, listBahasa);
        spbhs.setAdapter(adapterBhs);
        spbhs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedBhs = (String) parent.getItemAtPosition(position);
                if (selectedBhs.equals("IDN"))
                {
                    String flagIDN = "IDN";
                    SharedPreferences prefID = activity.getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefID.edit();
                    editor.putString("getFBHS", flagIDN);
                    editor.commit();
                    pilihBahasa = "IDN";
                    tvBhs.setText("Pilih Bahasa");
                    btnLogin.setText("Masuk");
                    btnExit.setText("Keluar");

                } else {
                    String flagENG = "ENG";
                    SharedPreferences prefEN = activity.getSharedPreferences("Flag_BHS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefEN.edit();
                    editor.putString("getFBHS", flagENG);
                    editor.commit();
                    pilihBahasa = "ENG";
                    tvBhs.setText("Choose Language");
                    btnLogin.setText("Login");
                    btnExit.setText("Exit");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputUsername.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputPass.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String error = "";
                String error1 = "";
                String error2 = "";

                if (pilihBahasa.equals("ENG"))
                {
                    error = "Username must be filled";
                    error1 = "Password don't empty";
                    error2 = "Password wrong";
                } else {
                    error = "Username harus diisi";
                    error1 = "Password tidak boleh kosong";
                    error2 = "Password salah";
                }

                if (etUsername.getText().toString().trim().length() == 0) {
                    inputUsername.setError(error);
                }
                else if (etPass.getText().toString().trim().length() == 0){
                    inputPass.setError(error1);
                }
                else {
                    String user = etUsername.getText().toString();
                    String pass = etPass.getText().toString();

                    //level ADM
                    if (user.equalsIgnoreCase("ADM") && !pass.equalsIgnoreCase("ADMINIGR"))
                    {
                        inputPass.setError(error2);
                    }
                    else if (user.equalsIgnoreCase("ADM"))
                    {
                        mdl.setUser_Pass(activity, user, pass, "Y", null, null);

                        Intent login = new Intent(LoginActivity.this, HomeActivity.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login);
                    }
                    //level RST
                    else if (user.equalsIgnoreCase("RST") && !pass.equalsIgnoreCase("RST"))
                    {
                        inputPass.setError(error2);
                    }
                    else if (user.equalsIgnoreCase("RST"))
                    {
                        login();
                    }
                    //except ADM & RST
                    else
                    {
                        mdl.setUser_Pass(activity, user, pass, null, null, null);

                        Intent login = new Intent(LoginActivity.this, HomeActivity.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login);
                    }

                }

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void login() {
        sDialog = new SpotsDialog(LoginActivity.this, R.style.LoadingDialog);
        sDialog.setCancelable(false);
        sDialog.show();

        //String URL = mdl.getIPRelay(activity) + getResources().getString(R.string.linkLogin);
        //Map<String, String> params = new HashMap<>();
        //params.put(TAG_PARAM_URLWS, mdl.getIPWS(activity) + getResources().getString(R.string.linkLogin));
        //params.put(TAG_PARAM_USERID, etUsername.getText().toString());
        //params.put(TAG_PARAM_USERPASS, etPass.getText().toString());
        //params.put(TAG_PARAM_IPDEVICE, IP_Device);
        //JSONObject jobjParam = new JSONObject(params);


        //JsonObjectRequest jobjReq = new JsonObjectRequest(Request.Method.POST, URL, jobjParam, new Response.Listener<JSONObject>() {
            //@Override
            //public void onResponse(JSONObject response) {
                try {
                    //Log.e("RESPONSE", response.toString());
                    //String status = response.getString(TAG_STATUS);
                    String status = "OK";
                    if (status.equals("OK")) {
                        //JSONArray arr = response.getJSONArray(TAG_ACCESS);
                        //String kodestation = response.getString(TAG_STATION);
                        //if (arr.length() > 0){
                            //for (int i = 0; i < arr.length(); i++){

                                /*JSONObject oList = arr.getJSONObject(i);
                                String access_name = oList.getString(TAG_ACCESS_NAME);
                                String user_level = oList.getString(TAG_USER_LEVEL);
                                String kodeIGR = oList.getString(TAG_KODE_IGR);

                                String station = response.getString(TAG_STATION);
                                String kode_station = station.getString(TAG_KODESTATION);

                                switch (access_name){
                                    case "WRONGPASSWORD" :
                                        showErrorAlert("Nama akun atau kata sandi salah!");
                                        break;
                                    case "OTHERUSER" :
                                        showErrorAlert("Masih terdapat akun yang terdaftar pada perangkat ini!");
                                        break;
                                    case "OTHERDEVICE" :
                                        showErrorAlert("Akun masih terdaftar pada perangkat lainnya!");
                                        break;
                                    case "REGISTERIP" :
                                        showErrorAlert("Alamat IP tidak terdaftar : " + IP_Device + " !");
                                        break;
                                    case "NOACCESS" :
                                        showErrorAlert("Akun tidak memiliki akses untuk menggunakan Handheld!");
                                        break;
                                    case "HANDHELD" :
                                    case "PLANO GUDANG" :
                                    case "PLANO TOKO" :
                                        String username = etUsername.getText().toString();
                                        String passw = etPass.getText().toString();

                                        mdl.setUser_Pass(activity, username, passw, null, access_name, kode_station);

                                        Intent main = new Intent(LoginActivity.this, HomeActivity.class);
                                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(main);

                                        break;
                                    default:
                                        break;
                                }*/

                                String access_name = "HANDHELD"; // nanti ini diilangin, cuma nyoba aja
                                String kodestation = "TB"; // nanti ini diilangin, cuma nyoba aja
                                String username = etUsername.getText().toString();
                                String passw = etPass.getText().toString();

                                //mdl.setUser_Pass_RST(activity, username, passw, access_name, kodestation);
                                mdl.setUser_Pass(activity, username, passw, null, access_name, kodestation);

                                Intent main = new Intent(LoginActivity.this, HomeActivity.class);
                                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(main);
                            //}
                        //}
                    }
                    sDialog.dismiss();

                } catch (Exception e) {
                    sDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            //}
        //}
        //, new Response.ErrorListener() {
        //    @Override
        //    public void onErrorResponse(VolleyError error) {
        //        sDialog.dismiss();

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
                    alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            login();
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
                    Toast.makeText(LoginActivity.this, "Cobalah beberapa saat lagi", Toast.LENGTH_SHORT).show();
                }*/
            //}
        //});

        //Volley.newRequestQueue(LoginActivity.this).add(jobjReq);

    }

    public static String getDeviceIP() {
        try{

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

        }catch (SocketException e){
            e.printStackTrace();
        }
        return null;
    }

    private void showErrorAlert(String message)
    {
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

        tv.setText(message);
        iv.setVisibility(View.GONE);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}