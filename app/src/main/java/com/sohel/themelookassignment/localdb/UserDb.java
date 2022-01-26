package com.sohel.themelookassignment.localdb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sohel.themelookassignment.model.User;

public class UserDb {
    private Activity activity;
    public UserDb(Activity activity) {
        this.activity = activity;
    }
    public void setUserData(User user) {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("userDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
    }
    public User getUserData(){
        User user=null;
        SharedPreferences sharedPreferences=activity.getSharedPreferences("userDb", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user","");
        user = gson.fromJson(json, User.class);
        return  user;
    }

    public void removeUserData(){
        SharedPreferences userShared = activity.getSharedPreferences("userDb", Context.MODE_PRIVATE);
        userShared.edit().clear().apply();
    }


}
