package com.example.tugas_9;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ListView listViewCatatan;
    ArrayAdapter<String[]> adapter;
    List<String[]> listItemCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);
        listViewCatatan = findViewById(R.id.listItemCatatan);

        initListItem();

        initAdapter();

        listViewCatatan.setAdapter(adapter);
        listViewCatatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTambahScreen = new Intent(MainActivity.this, TambahActivity.class);
                tambahActivityResultLauncher.launch(toTambahScreen);
            }
        });

    }

    ActivityResultLauncher<Intent> tambahActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        initListItem();
                        initAdapter();
                        listViewCatatan.setAdapter(adapter);
                        Toast.makeText(MainActivity.this, "File berhasil tersimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public void initListItem() {
        listItemCatatan = new ArrayList<>();
        String filePath = "MyFiles";
        File myExternalFile;

        myExternalFile = new File(getExternalFilesDir(filePath) + "/");
        if (myExternalFile.exists()) {
            File[] files = myExternalFile.listFiles();
            for (File file:files) {
                String title = file.getName();
                String content = readFileContent(file);
                String[] item = {title, content};
                listItemCatatan.add(item);
            }
        }
    }

    public void initAdapter() {
        adapter = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listItemCatatan) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                String[] items = listItemCatatan.get(position);
                text1.setText(items[0]);
                text2.setText(items[1]);

                return view;
            }
        };
    }

    public String readFileContent(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  contentBuilder.toString();
    }

    public void showDeleteDialog(int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Hapus Catatan");

        alertBuilder.setMessage("Apakah Anda yakin ingin menghapus catatan?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                        initAdapter();
                        initListItem();
                        listViewCatatan.setAdapter(adapter);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog NewAlertDialog = alertBuilder.create();
        NewAlertDialog.show();
    };

    public void deleteItem(int position) {
        String filePath = "MyFiles";
        File myExternalFile;
        String[] item = listItemCatatan.get(position);
        String fileName = item[0];

        myExternalFile = new File(getExternalFilesDir(filePath), fileName);
        if (myExternalFile.exists()) {
            myExternalFile.delete();
        }
        initAdapter();
        initListItem();
        listViewCatatan.setAdapter(adapter);
    }
}
