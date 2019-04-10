package co.ambystic.restorder.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SpManager {
    private static final String APP_SP_KEY = "SP_RestOrder";

    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String ID_USER = "idUSer";
    public static final String LEVEL_USER = "levelUser";
    public static final String USERNAME = "username";
    public static final String FULL_NAME = "fullName";
    public static final String TABLE_NO = "tableNo";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public SpManager(Context context){
        sharedPreferences = context.getSharedPreferences(APP_SP_KEY, Context.MODE_PRIVATE);
    }

    public void setStringData(@NonNull String key, @NonNull String value){
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntData(@NonNull String key, @NonNull Integer value){
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setBoolData(@NonNull String key, boolean value){
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Integer getIntData(@NonNull String key){
        return sharedPreferences.getInt(key, 0);
    }

    public String getStringData(@NonNull String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolData(@NonNull String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void destroySession(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
