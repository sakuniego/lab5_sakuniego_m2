package com.example.lab5_sakuniego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    TextView textView;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                // remove username from SP
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_sakuniego", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply(); // username is usernameKey

                // start activity 1
                goToActivity1();
                break;
            case R.id.addNote:
                goToActivity3();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToActivity3() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // display welcome message, fetch username
        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        textView.setText("Welcome " + str + "!");

        // get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // create DBHelper obj
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        // initiate notes var using readNotes from DBHelper
        notes = dbHelper.readNotes(str); // str has username

        // create ArrayList object by iterating thru notes obj
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // add onItemClickListener for ListView item (a note in this case)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }
}