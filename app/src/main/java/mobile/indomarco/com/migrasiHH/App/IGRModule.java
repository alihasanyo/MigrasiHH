package mobile.indomarco.com.migrasiHH.App;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import mobile.indomarco.com.migrasiHH.R;

public class IGRModule {

    //URL
    public final String getURLStr(){
        String messageAfterDecrypt = "";

        try {
            //ini URL DEV Dammy, disesuaikan saja dengan URL yang sudah disiapkan ke PROD
            //messageAfterDecrypt = AESCrypt.decrypt(getKey(), "Ololo/Ypvq5l2Fb38E4EMwwAN5lWVd5HC3yN/TNHqL4XlEA8CgQXIzKPqLzqORMcOUpbuLA9NQDWiU7uvl2F9g==");
            messageAfterDecrypt = "bla.bla.bla.bla/api/";

        }catch (Exception e){
            //handle error
        }finally {
            return messageAfterDecrypt;
        }
    }

    //default format URL Relay
    public final String formatURLRelay(){
        String messageAfterDecrypt = "";

        try {
            //ini URL DEV Dammy, disesuaikan saja dengan URL yang sudah disiapkan ke PROD
            //messageAfterDecrypt = AESCrypt.decrypt(getKey(), "Ololo/Ypvq5l2Fb38E4EMwwAN5lWVd5HC3yN/TNHqL4XlEA8CgQXIzKPqLzqORMcOUpbuLA9NQDWiU7uvl2F9g==");
            messageAfterDecrypt = "/SONASHHAWS/public/";

        }catch (Exception e){
            //handle error
        }finally {
            return messageAfterDecrypt;
        }
    }

    //default format URL WS
    public final String formatURLWS(){
        String messageAfterDecrypt = "";

        try {
            //ini URL DEV Dammy, disesuaikan saja dengan URL yang sudah disiapkan ke PROD
            //messageAfterDecrypt = AESCrypt.decrypt(getKey(), "Ololo/Ypvq5l2Fb38E4EMwwAN5lWVd5HC3yN/TNHqL4XlEA8CgQXIzKPqLzqORMcOUpbuLA9NQDWiU7uvl2F9g==");
            messageAfterDecrypt = "/SONAS_HHWS/servicehandheld.asmx/";

        }catch (Exception e){
            //handle error
        }finally {
            return messageAfterDecrypt;
        }
    }

    //ip device
    public void setIpDevice(Activity activity, String ip){
        String pref_ip = activity.getResources().getString(R.string.pref_ipDevice);
        SharedPreferences SP_ip = activity.getSharedPreferences(pref_ip, 0);
        SharedPreferences.Editor editor = SP_ip.edit();
        editor.putString("ip", ip);
        editor.apply();
    }

    public String getIPDevice(Activity activity){
        String ip;
        String pref_ip = activity.getResources().getString(R.string.pref_ipDevice);
        SharedPreferences SP_ip = activity.getSharedPreferences(pref_ip, 0);
        ip = SP_ip.getString("ip", null);
        return ip;
    }

    //access login
    public void setUser_Pass(Activity activity, String username, String password, String flag, String typeAccess, String kodestation){
        String pref_user = activity.getResources().getString(R.string.pref_user_login);
        SharedPreferences SP_user = activity.getSharedPreferences(pref_user, 0);
        SharedPreferences.Editor editor = SP_user.edit();
        editor.putString("user", username);
        editor.putString("pass", password);
        editor.putString("fLog", flag);
        editor.putString("acc", typeAccess);
        editor.putString("station", kodestation);
        editor.apply();
    }

    public String getUser(Activity activity){
        String user;
        String pref_user = activity.getResources().getString(R.string.pref_user_login);
        SharedPreferences SP_user = activity.getSharedPreferences(pref_user, 0);
        user = SP_user.getString("user", "None"); //None is default value
        return user;
    }

    public String getAccess(Activity activity){
        String user_acc;
        String pref_user_acc = activity.getResources().getString(R.string.pref_user_login);
        SharedPreferences SP_user_acc = activity.getSharedPreferences(pref_user_acc, 0);
        user_acc = SP_user_acc.getString("acc", null); //null is default value
        return user_acc;
    }

    //access logout
    public void deleteUser(Activity activity){
        String pref_user = activity.getResources().getString(R.string.pref_user_login);
        SharedPreferences SP_user = activity.getSharedPreferences(pref_user, 0);
        SharedPreferences.Editor editor = SP_user.edit();
        editor.clear();
        editor.commit();
    }

    //set IP Relay
    public void setIpRelay(Activity activity, String ip){
        String pref_ipRelay = activity.getResources().getString(R.string.pref_ipRelay);
        SharedPreferences SP_ip_relay = activity.getSharedPreferences(pref_ipRelay, 0);
        SharedPreferences.Editor editor = SP_ip_relay.edit();
        editor.putString("ipRelay", ip);
        editor.apply();
    }

    public String getIPRelay(Activity activity){
        String ip;
        String pref_ip_relay = activity.getResources().getString(R.string.pref_ipRelay);
        SharedPreferences SP_ip_relay = activity.getSharedPreferences(pref_ip_relay, 0);
        ip = SP_ip_relay.getString("ipRelay", null);
        return ip;
    }

    //set IP WS
    public void setIpWS(Activity activity, String ip){
        String pref_ipWS = activity.getResources().getString(R.string.pref_ipWS);
        SharedPreferences SP_ip_ws = activity.getSharedPreferences(pref_ipWS, 0);
        SharedPreferences.Editor editor = SP_ip_ws.edit();
        editor.putString("ipWs", ip);
        editor.apply();
    }

    public String getIPWS(Activity activity){
        String ip;
        String pref_ip_ws = activity.getResources().getString(R.string.pref_ipWS);
        SharedPreferences SP_ip_ws = activity.getSharedPreferences(pref_ip_ws, 0);
        ip = SP_ip_ws.getString("ipWs", null);
        return ip;
    }
}
