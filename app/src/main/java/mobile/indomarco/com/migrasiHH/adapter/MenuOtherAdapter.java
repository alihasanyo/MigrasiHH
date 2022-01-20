package mobile.indomarco.com.migrasiHH.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.indomarco.com.migrasiHH.App.IGRModule;
import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.activity.LoginActivity;
import mobile.indomarco.com.migrasiHH.activity.SOIPConfigActivity;
import mobile.indomarco.com.migrasiHH.activity.SettingActivity;
import mobile.indomarco.com.migrasiHH.model.MainMenuModel;

public class MenuOtherAdapter extends ArrayAdapter<MainMenuModel> {

    private final Activity context;
    private ArrayList<MainMenuModel> data;
    private Dialog dialog;
    private IGRModule mdl = new IGRModule();
    private String getIPDevice;
    private static String TAG_PARAM_URLWS = "url";
    private static String TAG_STATUS = "message";
    private static String TAG_FLAG_IP = "flag_masukan";

    public MenuOtherAdapter(Activity context, ArrayList<MainMenuModel> data) {
        super(context, R.layout.item_list_other_menu, data);
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_list_other_menu, null, true);

        TextView tvTitle = rowView.findViewById(R.id.titlemenu);
        TextView tvSubtitle = rowView.findViewById(R.id.subtitlemenu);
        ImageView ivIcon = rowView.findViewById(R.id.iconmenu);
        LinearLayout lyMenu = rowView.findViewById(R.id.lyMenu);

        MainMenuModel m = data.get(position);
        tvTitle.setText(m.getTitle());
        tvSubtitle.setText(m.getSubtitle());
        ivIcon.setImageResource(m.getIcon());

        lyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (m.getTitle()){
                    case "Perangkat" :
                        showDialogDevice("IDN");
                        break;

                    case "Device" :
                        showDialogDevice("ENG");
                        break;

                    case "Logout" :
                        showDialogLogout("ENG");
                        break;

                    case "Keluar" :
                        showDialogLogout("IDN");
                        break;

                    case "IP Settings" :
                        showDialogIP("ENG");
                        break;

                    case "Pengaturan IP" :
                        showDialogIP("IDN");
                        break;

                    default:
                        break;

                }
            }
        });

        return rowView;
    }

    private void showDialogIP(String bhs) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_list_set_ip);
        dialog.setCancelable(true);
        dialog.show();

        LinearLayout ly_iprelay = dialog.findViewById(R.id.lyRelay);
        LinearLayout ly_ipws = dialog.findViewById(R.id.lyWS);

        String getIP_relay = mdl.getIPRelay(context);
        String getIP_ws = mdl.getIPWS(context);
        String message1 = "";

        if (bhs.equals("ENG"))
        {
            message1 = "IP address don't empty!";
        } else {
            message1 = "Alamat IP tidak boleh kosong!";
        }

        String message11 = message1;
        ly_iprelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIP_relay == null || getIP_relay.equalsIgnoreCase("")){
                    Toast.makeText(context, message11, Toast.LENGTH_SHORT).show();
                } else {
                    saveIP("relay",bhs);
                }
            }
        });

        ly_ipws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIP_ws == null || getIP_ws.equalsIgnoreCase("")){
                    Toast.makeText(context, message11, Toast.LENGTH_SHORT).show();
                } else {
                    saveIP("ws",bhs);
                }
            }
        });

    }

    private void showDialogLogout(String bhs) {

        String message = "";
        String Y = "";
        String N = "";

        if (bhs.equals("ENG"))
        {
            message = "Are you sure out this apps?";
            Y = "YES";
            N = "NO";
        } else {
            message = "Anda yakin keluar aplikasi?";
            Y = "YA";
            N = "TIDAK";
        }

        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setMessage(message);
        alert.setPositiveButton(Y,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mdl.deleteUser(context);

                        Intent newTask = new Intent(context, LoginActivity.class);
                        newTask.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(newTask);
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

    private void showDialogDevice(String bhs) {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_device);
        dialog.setCancelable(true);
        dialog.show();

        Button close = dialog.findViewById(R.id.btnCLose);
        TextView tvInfo = dialog.findViewById(R.id.tvInfoPerangkat);
        TextView titlePerangkat = dialog.findViewById(R.id.titlePerangkat);

        String device = "";

        if (bhs.equals("ENG"))
        {
            device = "Device Information";
            close.setText("Close");
        } else {
            device = "Informasi Perangkat";
            close.setText("Tutup");
        }

        titlePerangkat.setText(device);
        getIPDevice = mdl.getIPDevice(context);

        StringBuilder sb = new StringBuilder();
        sb.append("IP : " + getIPDevice + "\n");
        sb.append("MANUFACTURE : " + Build.MANUFACTURER + "\n");
        sb.append("BRAND : " + Build.BRAND + "\n");
        sb.append("MODEL : " + Build.MODEL + "\n");
        sb.append("SERIAL : " + Build.SERIAL + "\n");
        sb.append("ID : " + Build.ID + "\n");
        sb.append("SDK : " + Build.VERSION.SDK + "\n");
        sb.append("VERSION : " + Build.VERSION.RELEASE);

        tvInfo.setText(sb.toString());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void saveIP(String typeIP, String bhs) {

        //String URL;
        //JSONObject jobjParam;

        //if (typeIP.equals("relay")){
        //    URL = mdl.getIPRelay(context) + context.getResources().getString(R.string.linkVersionRelay);
        //    jobjParam = null;
        //} else {
        //    URL = mdl.getIPRelay(context) + context.getResources().getString(R.string.linkVersionWS);
        //    Map<String, String> params = new HashMap<>();
        //    params.put(TAG_PARAM_URLWS, mdl.getIPWS(context) + context.getResources().getString(R.string.linkVersionWS));
        //    jobjParam = new JSONObject(params);
        //}

        //JsonObjectRequest jobjReqIP = new JsonObjectRequest(Request.Method.POST, URL, jobjParam, new Response.Listener<JSONObject>() {
        //@Override
        //public void onResponse(JSONObject response) {
                try {
        //            Log.e("RESPONSE", response.toString());
        //            String status = response.getString(TAG_STATUS);
                    String status = "Sukses";
                    if (status.equals("Sukses")) {
        //                String flagMessage = response.getString(TAG_FLAG_IP);
                        String flagMessage = "Web Service";                             // ini buat nyoba aja
                        switch (typeIP){
                            case "relay" :
                                if (flagMessage.equals("Laravel"))
                                {
                                    showSuccessAlert("Laravel v1.0.0", bhs);
                                } else {
                                    showErrorAlertLaravel(bhs);
                                }
                                break;

                            case "ws" :
                                if (flagMessage.equals("Web Service"))
                                {
                                    showSuccessAlert("Web Service v1.0.0", bhs);
                                } else {
                                    showErrorAlertWS(bhs);
                                }

                                break;

                            default:
                                break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context.getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
        //    }
        //}
        //, new Response.ErrorListener() {
        //    @Override
        //    public void onErrorResponse(VolleyError error) {

                //koment ini diilangin ya semisal setup IP ada condition error di networknya
                /*String errorMessage = "";
                String message = "";
                String Y = "";
                String N = "";
                String nav = "";

                if (bhs.equals("ENG"))
                {
                    message = "Connection problem";
                    Y = "Try Again";
                    N = "NO";
                    nav = "you will try again in IP Configuration page";
                    errorMessage = "Please try again later";
                } else {
                    message = "Jaringan bermasalah";
                    Y = "Coba Kembali";
                    N = "TIDAK";
                    nav = "anda akan mencoba kembali di halaman Configurasi IP";
                    errorMessage = "Harap coba lagi nanti";
                }

                String nav1 = nav;
                String errorMessage1 = errorMessage;

                if (error instanceof NetworkError ||
                        error instanceof ServerError ||
                        error instanceof ParseError ||
                        error instanceof NoConnectionError ||
                        error instanceof TimeoutError ||
                        error instanceof ClientError)
                {
                    final AlertDialog.Builder alert;
                    alert = new AlertDialog.Builder(context);
                    alert.setCancelable(false);
                    alert.setMessage(message);
                    alert.setPositiveButton(Y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(context, nav1, Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent logIP = new Intent(context, SOIPConfigActivity.class);
                                    logIP.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(logIP);
                                }
                            }, 2000);

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
                    Toast.makeText(context, errorMessage1, Toast.LENGTH_SHORT).show();
                }*/
        //    }
        //});

        //Volley.newRequestQueue(context).add(jobjReqIP);

    }

    private void showSuccessAlert(String message, String bhs) {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog = new Dialog(context);
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

        String messageRelay = "";

        if (bhs.equals("ENG")){
            messageRelay = "IP Relay address success saved";
        } else {
            messageRelay = "Alamat IP Relay berhasil disimpan";
        }

        if (message.contains("Laravel") || message.contains("Web Service"))
        {
            tv.setText(messageRelay);
        }
        iv.setImageResource(R.drawable.ic_check_circle);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showErrorAlertLaravel(String bhs) {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog = new Dialog(context);
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

        String messageRelay = "";

        if (bhs.equals("ENG")){
            messageRelay = "IP Relay address fill in wrong!";
        } else {
            messageRelay = "Alamat IP Relay yang dimasukan salah!";
        }

        tv.setText(messageRelay);
        iv.setImageResource(R.drawable.ic_error);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showErrorAlertWS(String bhs) {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog = new Dialog(context);
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

        String messageWS = "";

        if (bhs.equals("ENG")){
            messageWS = "IP Web Service address fill in wrong!";
        } else {
            messageWS = "Alamat IP Web Service yang dimasukan salah!";
        }

        tv.setText(messageWS);
        iv.setImageResource(R.drawable.ic_error);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
