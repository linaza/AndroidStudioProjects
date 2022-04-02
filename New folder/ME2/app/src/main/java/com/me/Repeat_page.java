package com.me;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Repeat_page extends AppCompatActivity {
    NotificationReciever a = new NotificationReciever();
    TextView note;
    String noteTitle;
    String noteText;
    Button msg ;
    Button design;
    @Override
    public void onCreate( Bundle savedInstanceState) {

        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_activity_layout);
        note = findViewById(R.id.design);
        msg = findViewById(R.id.button4);
        noteTitle = a.noteTitle().toString();
        noteText = a.noteText().toString();
        note.setText("\n\n"+noteTitle + "\n"+ noteText +"!");

    }
}
