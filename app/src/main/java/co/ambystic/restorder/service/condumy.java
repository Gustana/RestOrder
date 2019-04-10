package co.ambystic.restorder.service;

import android.content.Context;
import android.content.SharedPreferences;

public class condumy {
    private final String Sp_APPNAME = "sldjflksdf";

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private Context context;

    public condumy(Context context){
        sharedPreferences = context.getSharedPreferences(Sp_APPNAME, Context.MODE_PRIVATE);
    }

    public void setStringData(String key, String value){
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntData(String key, int value){
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
