package com.example.tugas_9;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etNameRegister, etUsernameRegister, etPasswordRegister;
    Button btnRegister;
    TextView btnTxtLogin;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(getBaseContext());

        etNameRegister = findViewById(R.id.etNameRegister);
        etUsernameRegister = findViewById(R.id.etUsernameRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);

        btnRegister = findViewById(R.id.btnRegister);

        btnTxtLogin = findViewById(R.id.btnTxtLogin);
        btnTxtLogin.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNameRegister.getText().toString();
                String username = etUsernameRegister.getText().toString();
                String password = etPasswordRegister.getText().toString();

                db.registerUser(name, username, password);
                boolean registrationSuccessful = db.registerUser(name, username, password);
                if (registrationSuccessful) {
                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Username Telah Diambil", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}