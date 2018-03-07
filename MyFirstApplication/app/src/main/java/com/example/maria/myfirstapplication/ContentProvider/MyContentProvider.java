package com.example.maria.myfirstapplication.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.maria.myfirstapplication.DB.AppDatabase;
import com.example.maria.myfirstapplication.DB.User;

import javax.crypto.spec.GCMParameterSpec;

public class MyContentProvider extends ContentProvider {

    //Authority of content provider
    private static final String AUTHORITY="com.example.maria.myfirstapplication.ContentProvider";
    //path of table name
    private static final String PATH="User";

    //Orisma tou UriMatcher me int code
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(AUTHORITY, PATH, 1);
        /*
        //matches the user_id of this table
        sUriMatcher.addURI(AUTHORITY, PATH+"/user_id", 2);
        //matches the timestamp of this table
        sUriMatcher.addURI(AUTHORITY, PATH+"/dt", 3);
        */
    }

    public MyContentProvider() {
    }

    //Handle requests to delete one or more rows.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       /* int id= -1;
        switch (sUriMatcher.match(uri)) {
            case 1:
                User GpsUser = new User();
                //Delete to db
                AppDatabase.getAppDatabase(this.getContext()).dao().delete(GpsUser);
                //id  of user for the returned URI
                id = GpsUser.getId();
                Log.d("Deleted Gpsuser:  ", String.valueOf(id));
                Log.d("PROVIDER ", String.valueOf(GpsUser));
                break;
            case 2:
                GpsUser = new User();
                //Delete to db the specific UserID
                AppDatabase.getAppDatabase(this.getContext()).dao().deletebyID(GpsUser);
                //id  of user for the returned URI
                id = GpsUser.getId();
                Log.d("DeletedbyID Gpsuser:  ", String.valueOf(id));
                Log.d("PROVIDER ", String.valueOf(GpsUser));
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }*/
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //Method Insert for Uri Creation
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //init id to -1
        long id = -1;
        Log.d("ID INSERT ", String.valueOf(id));
        switch(sUriMatcher.match(uri)){
            //if URI is PATH/
            case 1:
                //Create a GPS User and get the fields from values (of client)
                User GpsUser = new User();
                GpsUser.setUserid((String) values.get("user_id"));
                GpsUser.setLongtitude((Float) values.get("longtitude"));
                GpsUser.setLatitude((Float) values.get("latitude"));
                GpsUser.setTimestamp((String) values.get("dt"));
                //Insert to db
                AppDatabase.getAppDatabase(this.getContext()).dao().insert(GpsUser);
                //id  of user for the returned URI
                id = GpsUser.getId();
                Log.d("id of Gpsuser:  ", String.valueOf(id));
                Log.d("PROVIDER ", String.valueOf(GpsUser));
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }

        return Uri.parse(uri.toString()+"/"+id);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        /*int id= -1;
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case 1:
                User GpsUser = new User();
                cursor = AppDatabase.getAppDatabase(this.getContext()).dao().getAllCursor();
                id = GpsUser.getId();
                Log.d("id of Gpsuser:  ", String.valueOf(id));
                Log.d("PROVIDER ", String.valueOf(GpsUser));
                break;
            case 2:
                GpsUser = new User();
                cursor = AppDatabase.getAppDatabase(this.getContext()).dao().findById(ContentUris.);
                id = GpsUser.getId();
                Log.d("id of Gpsuser:  ", String.valueOf(id));
                Log.d("PROVIDER ", String.valueOf(GpsUser));

                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }

        return cursor;*/
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
