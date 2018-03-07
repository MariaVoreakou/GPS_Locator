package com.example.maria.myfirstapplication.DB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Maria on 11/20/2017.
 * Java Class For Room Framework
 *
 * Χρησιμοποιήθηκε το Room για αποφυγή της χρήσης "καθαρής" SQLLite.
 * Για αυτό το λόγο, μέσα από το Room έχει γίνει και η δημιουργία του table,
 * όπως και των Columns. Επειδή το Room Framework δεν υποστηρίζει δήλωση μεγέθους
 * των Columns, έχει γίνει πλήρης έλεγχος των fields μέσα από την Java στις μεταβλητές
 * που ορίζονται στο Main Activity
 */
//Initialization of User, Getters and Setters

@Entity(tableName = "User")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name ="user_id")
    private String userid;
    @ColumnInfo(name ="longtitude")
    private float longtitude;
    @ColumnInfo(name ="latitude")
    private float latitude;
    @ColumnInfo(name ="dt" )
    private String timestamp;

    public String getUserid() {
        return userid;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public void setUserid(String userid){
        this.userid = userid;
    }
    public float getLongtitude(){
        return longtitude;
    }
    public void setLongtitude(float longtitude){
        this.longtitude = longtitude;
    }
    public float getLatitude(){
        return latitude;
    }
    public void setLatitude(float latitude){
        this.latitude = latitude;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
}
