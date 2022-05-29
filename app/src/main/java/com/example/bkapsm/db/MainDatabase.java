package com.example.bkapsm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Student.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class MainDatabase extends RoomDatabase {
    private static final String DBNAME = "bkapsm";

    public abstract StudentDao studentDao();

    public static MainDatabase getDbInstance(Context context) {
        return Room.databaseBuilder(context, MainDatabase.class, DBNAME).build();
    }
}
