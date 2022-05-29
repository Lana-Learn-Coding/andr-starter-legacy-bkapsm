package com.example.bkapsm.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fullname")
    private String fullname;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "subject")
    private String subject;

    @ColumnInfo(name = "level")
    private Integer level;

    @ColumnInfo(name = "gender")
    private boolean gender;

    @ColumnInfo(name = "note", typeAffinity = ColumnInfo.TEXT)
    private String note;

    @ColumnInfo(name = "birthdate")
    private Date birthdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
