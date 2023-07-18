package com.example.tugas_9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TambahCatatanDBActivity extends AppCompatActivity {
    Button btnSubmit;
    EditText etJudulCatatan, etKontenCatatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan_dbactivity);

        btnSubmit = findViewById(R.id.btnSubmit);
        etJudulCatatan = findViewById(R.id.etJudulCatatan);
        etKontenCatatan = findViewById(R.id.etKontenCatatan);
        DBHelper dbHelper = new DBHelper(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = etJudulCatatan.getText().toString();
                String konten = etKontenCatatan.getText().toString();

                try {
                    dbHelper.addCatatan(judul, konten);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });
    }
}