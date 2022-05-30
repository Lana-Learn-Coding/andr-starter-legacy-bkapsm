package com.example.bkapsm;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {
    private final List<String> subjects = new ArrayList<String>() {{
        add("Java");
        add("PHP");
        add("C#");
    }};

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<String> getSubjects() {
        return subjects;
    }
}
