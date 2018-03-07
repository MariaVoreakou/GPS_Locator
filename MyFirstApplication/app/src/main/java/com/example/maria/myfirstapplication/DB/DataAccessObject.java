package com.example.maria.myfirstapplication.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

/**
 * Created by Maria on 11/20/2017.
 * Java κλάση η οποία κάνει τη μεταφορά δεδομένων μεταξύ της db και του App
 *
 * Εδώ ορίζονται τα queries που χρειάζονται, μέσω μεθόδων που
 * χρησιμοποιούνται μέσα στα Activities
 *
 */

@Dao
public interface DataAccessObject {

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User")
    Cursor getAllCursor();


    @Query("SELECT * FROM User where user_id = :userid ")
    List <User> findById(String userid);


    @Query("SELECT * FROM User where dt LIKE :timestamp ")
    List<User> findByTimestamp(String timestamp);
    @Query("SELECT * FROM User where user_id = :userid AND dt LIKE :timestamp ")
    List<User> findByIdAndTimestamp(String userid, String timestamp);

    //@Query("DELETE FROM User WHERE id= ;")
    //List<User> deleteByDbID(int id);

    @Query("SELECT DISTINCT dt FROM User")
    List<String> getTimestamps();

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Delete
    void deletebyID(User user);
}
