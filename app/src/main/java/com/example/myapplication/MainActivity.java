package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextSentence = findViewById(R.id.editTextSentence);

        Button buttonCheck = findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPalindrome(v);
            }
        });

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSecondScreen(v);
            }
        });
    }

    public void checkPalindrome(View view) {
        String kalimat = editTextSentence.getText().toString().toLowerCase().replaceAll("\\s+", "");
        boolean isPalindrome = isPalindrome(kalimat);

        String pesan = isPalindrome ? "adalah Palindrome" : "bukan Palindrome";
        tampilkanAlertDialog(pesan);
    }

    private boolean isPalindrome(String str) {
        // Implementasi logika pengecekan palindrome
        // Untuk kesederhanaan, membalikkan string dan membandingkannya dengan aslinya
        String terbalik = new StringBuilder(str).reverse().toString();
        return str.equals(terbalik);
    }

    public void goToSecondScreen(View view) {
        // Intent untuk memulai SecondActivity
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void tampilkanAlertDialog(String pesan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(pesan);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}