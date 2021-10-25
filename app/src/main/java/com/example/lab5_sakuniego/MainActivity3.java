package com.example.lab5_sakuniego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    int noteid = -1;
    String content;
    //String content = ""; // TODO: maybe remove
    public void clickFunction(View view) {
        // Get EditText view & user content
        EditText editText = (EditText) findViewById(R.id.noteText);
        content = editText.getText().toString();

        // initialize SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // initialize DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_sakuniego", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // save info to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1){ // add note
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        // go to activity 2 w/ intents
        goToActivity2(username);
    }

    public void goToActivity2(String s) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Get EditText view
        EditText editText = (EditText) findViewById(R.id.noteText);

        // Get intent
        Intent intent = getIntent();

        // Get val of integer noteid from intent & initialize class var noteid w/ val from intent
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            // display content of note by getting notes arraylist in activity 2
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            // use editText.setText() to display contents of note on screen
            editText.setText(noteContent);
        }
    }
}