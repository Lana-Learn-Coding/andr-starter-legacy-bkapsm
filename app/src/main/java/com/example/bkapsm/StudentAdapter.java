package com.example.bkapsm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.bkapsm.db.Student;
import com.example.bkapsm.form.FormUtils;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(@NonNull Context context, List<Student> weathers) {
        super(context, 0, weathers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Student item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
        }

        if (item == null) return convertView;

        FormUtils.setText(convertView.findViewById(R.id.txt_fullname), item.getFullname());
        FormUtils.setText(convertView.findViewById(R.id.txt_phone), item.getPhone());
        FormUtils.setText(convertView.findViewById(R.id.txt_email), item.getEmail());
        FormUtils.setText(convertView.findViewById(R.id.txt_subject), item.getSubject());
        FormUtils.setText(convertView.findViewById(R.id.txt_level), "Lv " + item.getLevel());
        return convertView;
    }
}
