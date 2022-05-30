package com.example.bkapsm.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student WHERE fullname like :fullname and email like :email")
    List<Student> getAll(String fullname, String email);

    @Delete
    void delete(Student... students);

    @Insert
    void insert(Student... students);

    @Query("SELECT * FROM student WHERE id = :id LIMIT 1")
    Student getOne(int id);

    @Update
    void update(Student... students);
}
