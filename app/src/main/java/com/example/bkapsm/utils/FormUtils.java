package com.example.bkapsm.utils;

import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormUtils {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyy-mm-dd", Locale.forLanguageTag("vi"));

    private FormUtils() {
    }

    public static String getTextValue(TextView textView) {
        if (ObjectUtils.anyNull(textView)) return "";
        if (ObjectUtils.anyNull(textView.getText())) return "";
        return textView.getText().toString();
    }

    public static void setTextValue(TextView textView, String text) {
        if (ObjectUtils.anyNull(textView)) return;
        textView.setText(text);
    }

    public static Date getTextDate(TextView textView, SimpleDateFormat format) {
        String text = getTextValue(textView);
        if (StringUtils.isBlank(text)) return null;
        try {
            return format.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getTextDate(TextView textView) {
        return getTextDate(textView, DATE_FORMAT);
    }
}
