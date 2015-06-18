package com.donbaka.reklameuser.helper;

/**
 * Created by brlnt-super on 5/6/2015.
 */

import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    
    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Reklame";

    private static final String IS_LOGGEDIN = "isLoggedIn";

    private static final String KEY = "key";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, String key) {

        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY, key);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGEDIN, false);
    }

    public String getKey() {
        return pref.getString(KEY, "null");
    }

    public void destroySession(){
        editor.clear();
        editor.commit();

        Log.d(TAG, "User login session destroyed!");
    }
}
