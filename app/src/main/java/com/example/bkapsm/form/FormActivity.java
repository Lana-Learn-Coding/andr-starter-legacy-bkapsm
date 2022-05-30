package com.example.bkapsm.form;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.bkapsm.MainActivity;
import com.example.bkapsm.MainApplication;
import com.example.bkapsm.NavigableActivity;
import com.example.bkapsm.R;
import com.example.bkapsm.db.MainDatabase;
import com.example.bkapsm.db.Student;
import com.example.bkapsm.db.StudentDao;

import org.apache.commons.lang3.ObjectUtils;

import java.util.concurrent.Executors;

public class FormActivity extends NavigableActivity {
    private StudentDao dao;
    int updateId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        dao = MainDatabase.getDbInstance(this).studentDao();

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.this.saveForm();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.this.updateForm();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.this.deleteForm();
            }
        });

        MainApplication application = (MainApplication) getApplicationContext();
        FormUtils.bindDropDownSelection(findViewById(R.id.select_subject), application.getSubjects());
        FormUtils.bindDatePicker(findViewById(R.id.edit_txt_birthdate));
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.btn_update).setVisibility(View.GONE);
        findViewById(R.id.btn_delete).setVisibility(View.GONE);
        Bundle extras = ObjectUtils.defaultIfNull(getIntent().getExtras(), new Bundle());
        updateId = extras.getInt("id", -1);
        if (updateId < 0) return;

        findViewById(R.id.btn_update).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final Student item = dao.getOne(updateId);
                if (item == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            navigate(FormActivity.this, MainActivity.class);
                            Toast.makeText(FormActivity.this, "Không tìm thấy bản ghi với id " + updateId, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                // set value to form on update
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FormUtils.setText(findViewById(R.id.edit_txt_fullname), item.getFullname());
                        FormUtils.setText(findViewById(R.id.edit_txt_phone), item.getPhone());
                        FormUtils.setText(findViewById(R.id.edit_txt_email), item.getEmail());
                        FormUtils.setText(findViewById(R.id.edit_txt_note), item.getNote());
                        FormUtils.setText(findViewById(R.id.edit_txt_level), item.getLevel());
                        FormUtils.setText(findViewById(R.id.edit_txt_birthdate), item.getBirthdate());
                        FormUtils.setSelection(findViewById(R.id.select_subject), item.getSubject());
                        FormUtils.setChecked(findViewById(R.id.chk_male), item.isGender());
                        FormUtils.setChecked(findViewById(R.id.chk_female), !item.isGender());
                    }
                });
            }
        });
    }

    private void saveForm() {
        final Student student = getFormData();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(student);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigate(FormActivity.this, MainActivity.class);
                        Toast.makeText(FormActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateForm() {
        if (updateId < 0) {
            Toast.makeText(this, "Không thể cập nhật - chưa chọn bản ghi", Toast.LENGTH_SHORT).show();
            return;
        }
        final Student student = getFormData();
        student.setId(updateId);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.update(student);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigate(FormActivity.this, MainActivity.class);
                        Toast.makeText(FormActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void deleteForm() {
        if (updateId < 0) {
            Toast.makeText(this, "Không thể xóa - chưa chọn bản ghi", Toast.LENGTH_SHORT).show();
            return;
        }
        final Student student = getFormData();
        student.setId(updateId);

        new AlertDialog.Builder(this)
            .setMessage("Bạn có chắc muốn xóa?")
            .setPositiveButton("Chắc", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            dao.delete(student);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    navigate(FormActivity.this, MainActivity.class);
                                    Toast.makeText(FormActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            })
            .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(FormActivity.this, "Hủy", Toast.LENGTH_SHORT).show();
                }
            })
            .show();
    }

    private Student getFormData() {
        Student student = new Student();
        student.setFullname(FormUtils.getText(findViewById(R.id.edit_txt_fullname)));
        student.setPhone(FormUtils.getText(findViewById(R.id.edit_txt_phone)));
        student.setEmail(FormUtils.getText(findViewById(R.id.edit_txt_email)));
        student.setNote(FormUtils.getText(findViewById(R.id.edit_txt_note)));
        student.setSubject(FormUtils.getText(findViewById(R.id.select_subject)));
        student.setLevel(FormUtils.getTextAsInteger(findViewById(R.id.edit_txt_level)));
        student.setGender(FormUtils.isChecked(findViewById(R.id.chk_male)));
        student.setBirthdate(FormUtils.getTextAsDate(findViewById(R.id.edit_txt_birthdate)));
        return student;
    }
}
