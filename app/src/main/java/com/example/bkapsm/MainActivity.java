package com.example.bkapsm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bkapsm.db.MainDatabase;
import com.example.bkapsm.db.Student;
import com.example.bkapsm.db.StudentDao;
import com.example.bkapsm.form.FormActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MainActivity extends NavigableActivity {
    private StudentAdapter adapter;
    private StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = MainDatabase.getDbInstance(this).studentDao();
        adapter = new StudentAdapter(this, new ArrayList<Student>());
        final ListView listHistory = findViewById(R.id.list_student);
        listHistory.setAdapter(adapter);
        registerForContextMenu(listHistory);

        // show context menu on single click
        listHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listHistory.showContextMenuForChild(view);
            }
        });
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);

        int pos = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        Student item = adapter.getItem(pos);
        menu.setHeaderTitle(String.format(Locale.ENGLISH, "%s (%d)", item.getFullname(), item.getId()));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        int pos = ((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position;
        final Student item = adapter.getItem(pos);

        if (id == R.id.item_create) {
            navigate(this, FormActivity.class);
        }

        if (id == R.id.item_edit) {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("id", item.getId());
            startActivity(intent);
            finish();
        }

        if (id == R.id.item_delete) {
            new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc muốn xóa?")
                .setPositiveButton("Chắc", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                dao.delete(item);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.remove(item);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(MainActivity.this, String.format(Locale.ENGLISH, "Xóa %s (%d)", item.getFullname(), item.getId()), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                })
                .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Hủy", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
        }

        return true;
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Student> items = dao.getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(items);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
