package com.example.bkapsm.form;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormUtils {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private FormUtils() {
    }

    public static void setSelection(View view, String selection) {
        AutoCompleteTextView textView = (AutoCompleteTextView) view;
        if (!ObjectUtils.allNotNull(textView)) return;
        if (!ObjectUtils.allNotNull(textView.getText())) return;
        ArrayAdapter adapter = (ArrayAdapter) textView.getAdapter();
        textView.setAdapter(null);
        textView.setText(selection);
        textView.setAdapter(adapter);
    }

    public static void setChecked(View view, Boolean isChecked) {
        RadioButton radio = (RadioButton) view;
        if (!ObjectUtils.allNotNull(radio)) return;
        if (!ObjectUtils.allNotNull(isChecked)) isChecked = false;
        ((RadioButton) view).setChecked(isChecked);
    }

    public static void setText(View view, String text) {
        TextView textView = (TextView) view;
        if (!ObjectUtils.allNotNull(textView)) return;
        textView.setText(text);
    }

    public static void setText(View view, Integer integer) {
        String value = integer == null ? null : integer.toString();
        setText(view, value);
    }

    public static void setText(View view, Date date, SimpleDateFormat format) {
        String value = date == null ? null : format.format(date);
        setText(view, value);
    }

    public static void setText(View view, Date date) {
        setText(view, date, DATE_FORMAT);
    }

    public static String getText(View view) {
        TextView textView = (TextView) view;
        if (!ObjectUtils.allNotNull(textView)) return "";
        if (!ObjectUtils.allNotNull(textView.getText())) return "";
        return textView.getText().toString();
    }

    public static Date getTextAsDate(View view, SimpleDateFormat format) {
        TextView textView = (TextView) view;
        String text = getText(textView);
        if (StringUtils.isBlank(text)) return null;
        try {
            return format.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getTextAsDate(View textView) {
        return getTextAsDate(textView, DATE_FORMAT);
    }

    public static Integer getTextAsInteger(View view) {
        String raw = FormUtils.getText(view);
        if (StringUtils.isBlank(raw)) return null;
        return Integer.parseInt(raw);
    }

    public static boolean isChecked(View view) {
        return ((RadioButton) view).isChecked();
    }

    public static void bindDatePicker(View view) {
        final TextInputEditText textInputEditText = (TextInputEditText) view;
        Date dateFromInput = getTextAsDate(textInputEditText);
        final Date initDate = dateFromInput == null ? new Date() : dateFromInput;

        textInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatePickerDialog datePicker = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        setText(textInputEditText, new Date(year - 1900, month, day));
                    }
                }, initDate.getYear() + 1900, initDate.getMonth(), initDate.getDay());

                datePicker.show();
            }
        });
    }

    public static <T> void bindDropDownSelection(View view, List<T> selections) {
        final AutoCompleteTextView select = (AutoCompleteTextView) view;
        ArrayAdapter<T> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, selections);
        select.setAdapter(adapter);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.showDropDown();
            }
        });
    }
}
