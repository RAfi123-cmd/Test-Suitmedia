package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private TextView textViewFirstName;
    private TextView textViewSelectedUserName;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewSelectedUserName = findViewById(R.id.textViewSelectedUserName);
        textViewFirstName = findViewById(R.id.textViewFirstName);

        Button buttonChooseUser = findViewById(R.id.buttonChooseUser);
        buttonChooseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThirdScreen();
            }
        });

        // Retrieve data from the first screen
        Intent intent = getIntent();
        if (intent != null) {
            String firstName = intent.getStringExtra("FIRST_NAME");
            textViewFirstName.setText(firstName);
        }
        // Check for updated user name from ThirdActivity
        if (intent != null && intent.hasExtra("SELECTED_USER_NAME")) {
            String selectedUserName = intent.getStringExtra("SELECTED_USER_NAME");
            textViewSelectedUserName.setText(selectedUserName);
        }
    }

    // Add this method to handle the result from ThirdActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("SELECTED_USER_NAME")) {
                String selectedUserName = data.getStringExtra("SELECTED_USER_NAME");
                textViewSelectedUserName.setText(selectedUserName);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void goToThirdScreen() {
        // Intent to start the ThirdActivity
        Intent intent = new Intent(this, ThirdActivity.class);

        // Send data back to ThirdActivity
        intent.putExtra("SELECTED_USER_NAME", textViewSelectedUserName.getText().toString());

        startActivity(intent);
    }

    // Remove getInstance() method
}
