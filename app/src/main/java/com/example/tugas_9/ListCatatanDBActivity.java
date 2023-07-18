package com.example.tugas_9;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListCatatanDBActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ListView listViewCatatan;
    ArrayAdapter<String[]> adapter;
    List<String[]> listItemCatatan;
    DBHelper dbHelper = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_catatan_dbactivity);

        fab = findViewById(R.id.floatingActionButton);
        listViewCatatan = findViewById(R.id.listItemCatatan);

        listItemCatatan = dbHelper.getCatatanList();

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
                Intent toTambahCatatanActivity = new Intent(ListCatatanDBActivity.this, TambahCatatanDBActivity.class);
                tambahActivityResultLauncher.launch(toTambahCatatanActivity);
            }
        });

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

    public void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Catatan");
        builder.setMessage("Apakah Anda yakin ingin menghapus catatan ini?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] catatan = listItemCatatan.get(position);
                String judul = catatan[0];
                String konten = catatan[1];

                DBHelper dbHelper = new DBHelper(ListCatatanDBActivity.this);
                boolean result = dbHelper.deleteCatatan(judul, konten);

                if (result) {
                    listItemCatatan.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ListCatatanDBActivity.this, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListCatatanDBActivity.this, "Gagal menghapus catatan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    ActivityResultLauncher<Intent> tambahActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        listItemCatatan = dbHelper.getCatatanList();
                        initAdapter();
                        listViewCatatan.setAdapter(adapter);
                        Toast.makeText(ListCatatanDBActivity.this, "Catatan berhasil tersimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


}