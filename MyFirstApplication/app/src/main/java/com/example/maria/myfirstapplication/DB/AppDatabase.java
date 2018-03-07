package com.example.maria.myfirstapplication.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Maria on 11/20/2017.
 *
 * Java κλάση η οποία κάνει το build του table
 * μεσω Room Framework
 */
@Database(entities = {User.class}, version=4)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract DataAccessObject dao();

    public static AppDatabase getAppDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database").allowMainThreadQueries().build();
        }
            return INSTANCE;
        }
        public static void destroyInstance(){
        INSTANCE = null;
    }

}
