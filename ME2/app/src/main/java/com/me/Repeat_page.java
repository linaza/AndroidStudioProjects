package com.me;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import static com.me.MyFirebaseMessagingService.share_pref;
import static com.me.MyFirebaseMessagingService.text;

/*import static com.me.MainActivity.share_pref1;
import static com.me.MainActivity.text1;*/

public class Repeat_page extends AppCompatActivity {

    static TextView design;
    public static String share_pref2="shared";
    public static final String text2="text";
    String msg=null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_activity_layout);
        design = findViewById(R.id.design);
         msg= getIntent().getStringExtra("message");
        if(msg!=null) {
            save1(msg);
            load();
        }
        else
        {
            SharedPreferences sharedPreferences = getSharedPreferences(share_pref, Context.MODE_PRIVATE);
            save1(sharedPreferences.getString(text, ""));
            load();
        }

    }
    //--------------------------------------------------------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void load(){
        SharedPreferences sharedPreferences = getSharedPreferences(share_pref2, Context.MODE_PRIVATE);
        design.setText(sharedPreferences.getString(text,""));


    }

    public void save1(String s){
        SharedPreferences sharedPreferences=getSharedPreferences(share_pref2,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(text2,s);
        editor.commit();

    }


}
