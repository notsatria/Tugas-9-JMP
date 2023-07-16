package com.example.tugas_9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TambahActivity extends AppCompatActivity {
    Button btnSubmit;
    EditText etNamaFile, etKontenFile;

    String filePath = "MyFiles";
    String currentFile ="temp.txt";
    File myExternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        btnSubmit = findViewById(R.id.btnSubmit);
        etNamaFile = findViewById(R.id.etNamaFile);
        etKontenFile = findViewById(R.id.etKontenFile);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnSubmit.setEnabled(false);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = etNamaFile.getText().toString();
                String fileContent = etKontenFile.getText().toString();

                myExternalFile = new File(getExternalFilesDir(filePath), fileName + ".txt");

                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(fileContent.getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });
    }

    public static boolean isExternalStorageAvailable() {
        String getExternalStorageStatus = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(getExternalStorageStatus)) return true;
        return false;
    }

    public static boolean isExternalStorageReadOnly() {
        String getExternalStorageStatus = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(getExternalStorageStatus)) return true;
        return false;
    }
}