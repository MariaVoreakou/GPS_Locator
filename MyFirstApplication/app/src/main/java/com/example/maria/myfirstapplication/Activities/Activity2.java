package com.example.maria.myfirstapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maria.myfirstapplication.DB.AppDatabase;
import com.example.maria.myfirstapplication.R;

import java.util.List;

/**
 * Created by Maria on 11/21/2017
 *
 * In Activity 2, user can search in db with timestamp or userid, or timestamp and userid together.
 * There is a dropdown list with all the possible timestamps that user can select.
 */

public class Activity2 extends AppCompatActivity {

    EditText userIDText;
    Spinner spinnerDropDown;
    Button queryButton;
    Button backButton;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity2); //Set activity main.xml
        //Ids of Fields
        userIDText = (EditText) findViewById(R.id.act2_userid);
        spinnerDropDown = (Spinner) findViewById(R.id.spinner_id);
        queryButton = (Button) findViewById(R.id.query_button_id);
        backButton = (Button) findViewById(R.id.back_button_act2);

        //Create list for timestamps
        List<String> timestamps = AppDatabase.getAppDatabase(Activity2.this).dao().getTimestamps();
        String[] data= new String[timestamps.size()+1]; //+1 gia na mporoume sti prwti thesi na orisoume to parakatw
        data[0]= "Please select one";
        for(int i=0;i<timestamps.size();i++){
            data[i+1]=timestamps.get(i);
        }

        //Array sto opoio fortwnontai ola ta timestamps gia to dropDown
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity2.this,android.R.layout.simple_spinner_item,data);
        spinnerDropDown.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Button gia to Main Activity
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity2.this, MainActivity.class);
                Activity2.this.startActivity(myIntent);
            }
        });

        //Button gia to epomeno Activity 3
        queryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //If dropdown and userid are null
                if(((Spinner) findViewById(R.id.spinner_id)).getSelectedItem().toString().equalsIgnoreCase("Please select one") && ((EditText) findViewById(R.id.act2_userid)).getText().toString().length() ==0 ){
                    Context context = getApplicationContext();
                    CharSequence text = "Please fill at least one field!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Log.d for debug
                Log.d("Spinner value ","My debug is "+((Spinner) findViewById(R.id.spinner_id)).getSelectedItem().toString());
                //Create intent for the next activity
                Intent myIntent = new Intent(Activity2.this, Activity3.class);
                myIntent.putExtra("userID", ((EditText) findViewById(R.id.act2_userid)).getText().toString());
                myIntent.putExtra("timestamp", ((Spinner) findViewById(R.id.spinner_id)).getSelectedItem().toString());
                Activity2.this.startActivity(myIntent);
            }
        });




    }




}
