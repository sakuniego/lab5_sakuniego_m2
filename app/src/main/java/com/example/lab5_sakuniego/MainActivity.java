package com.example.lab5_sakuniego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view) {
        // get username/password
        EditText nameField = (EditText) findViewById(R.id.username);
        EditText passField = (EditText) findViewById(R.id.password);
        String name = nameField.getText().toString();
        String pass = passField.getText().toString();

        // add username to SharedPreferences obj
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_sakuniego", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", name).apply();

        // starting 2nd activity
        goToActivity2(name);

    }

    public void goToActivity2(String s) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", s);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_sakuniego", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            // user didn't log out
            // get username stored in SP
            String username = sharedPreferences.getString(usernameKey, "");
            // start activity 2
            goToActivity2(username);
        } else {
            // no username key set (user logged out or nobody logged in)
            setContentView(R.layout.activity_main);
        }
    }
}