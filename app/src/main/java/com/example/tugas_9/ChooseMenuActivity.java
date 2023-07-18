package com.example.tugas_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChooseMenuActivity extends AppCompatActivity {
    Button btnMenuPersistence, btnMenuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_menu);

        btnMenuPersistence = findViewById(R.id.btnPersistence);
        btnMenuDB = findViewById(R.id.btnDatabase);

        btnMenuPersistence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(ChooseMenuActivity.this, MainActivity.class);
                startActivity(toMainActivity);
            }
        });

        btnMenuDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toListCatatanActivity = new Intent(ChooseMenuActivity.this, ListCatatanDBActivity.class);
                startActivity(toListCatatanActivity);
            }
        });
    }
}