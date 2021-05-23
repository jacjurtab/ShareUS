package com.example.shareus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class Session {

    private final static String USER_ID = "userId";
    private final static String USER_NAME = "userName";

    private final int userId;
    private final String username;

    public Session(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public static void save(Session session, Context cxt) {
        Log.d("LOGIN", "Se guarda la sesión en SharedPreferences para el usuario (" + session.getUserId() + "," + session.getUsername() + ")");
        SharedPreferences sp = cxt.getSharedPreferences("shareus", Context.MODE_PRIVATE);
        SharedPreferences.Editor  spEditor = sp.edit();

        spEditor.putInt(USER_ID, session.getUserId());
        spEditor.putString(USER_NAME, session.getUsername());

        spEditor.apply();
    }

    public static Session get(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences("shareus", Context.MODE_PRIVATE);
        Session result = new Session(sp.getInt(USER_ID, -1), sp.getString(USER_NAME, "null"));
        if (result.getUserId() == -1) {
            destroy(cxt);
            return null;
        }
        return result;
    }

    public static void destroy(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences("shareus", Context.MODE_PRIVATE);
        SharedPreferences.Editor  spEditor = sp.edit();

        spEditor.clear();
        spEditor.apply();
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }
}
