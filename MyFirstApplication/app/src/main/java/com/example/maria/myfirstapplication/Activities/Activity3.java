package com.example.maria.myfirstapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.maria.myfirstapplication.DB.AppDatabase;
import com.example.maria.myfirstapplication.DB.User;
import com.example.maria.myfirstapplication.R;
import java.util.List;

/**
 * Created by Maria on 11/21/2017.
 *
 * The last Activity which can set the results of Activity 2 from db
 */

public class Activity3 extends AppCompatActivity {

    TableLayout tableLayout;
    Button backMainButton;
    Button backSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();

        setContentView(R.layout.activity3); //Set activity main.xml

        //Ids of Fields
        tableLayout = (TableLayout) findViewById(R.id.table_layout_id);
        backMainButton = (Button) findViewById(R.id.back_button_act3);
        backSearchButton = (Button) findViewById(R.id.back_button2_act3);

        //Get intent of timestamp and userid from Activity2
        String userId = myIntent.getStringExtra("userID");
        String timestamp = myIntent.getStringExtra("timestamp");

        //If timestamp is null, find result by ID
        //post παράδωση αλλαγή. Προστέθηκε το timestamp.equalsIgnoreCase("Please select one")!!!!
        //Γιατί προστέθηκε η τιμή αυτή στο σπίνερ τη τελευταία στιγμή και ξέχασα να το προσθέσω εδώ
        if (timestamp == null || timestamp.length() == 0 || timestamp.equalsIgnoreCase("Please select one")){
            List<User> list = AppDatabase.getAppDatabase(Activity3.this).dao().findById(userId);

            //For debug reasons
            Log.e("inside if"," tm null");
            Log.e("inside if"," Userid "+userId);

            //Loop for search in db for all results with the same id
            for (int i =0; i<list.size(); i++){
                //Create table row and a textview to set the result text
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tv = new TextView(this);
                Log.e("Result Debug","UserID: "+list.get(i).getUserid()+"\t"+"Timestamp: "+list.get(i).getTimestamp()+"\t"+"Latitude: "+list.get(i).getLatitude()+"\t"+"Longtitude: "+list.get(i).getLongtitude()+"\t"+"Id: "+list.get(i).getId());
                tv.setText("UserID: "+list.get(i).getUserid()+"\n"+"Timestamp: "+list.get(i).getTimestamp()+"\n"+"Latitude: "+list.get(i).getLatitude()+"\n"+"Longtitude: "+list.get(i).getLongtitude()+"\n"+"~~~~~~~~~~~~~~~~~~~~~\n");
                row.addView(tv);
                tableLayout.addView(row,i);
            }
        }
        //If Id is null, find result by timestamp
        else if (userId==null || userId.length() == 0) {

            //For Debug reasons
            Log.e("inside if"," userId null");
            Log.e("inside if"," Timestamp "+timestamp);


            List<User> list = AppDatabase.getAppDatabase(Activity3.this).dao().findByTimestamp(timestamp);
            //Loop for search in db for all results with the same id
            for (int i =0; i<list.size(); i++){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tv = new TextView(this);
                //Log.e("Result Debug","UserID: "+list.get(i).getUserid()+"\t"+"Timestamp: "+list.get(i).getTimestamp()+"\t"+"Latitude: "+list.get(i).getLatitude()+"\t"+"Longtitude: "+list.get(i).getLongtitude()+"\t"+"Id: "+list.get(i).getId());
                tv.setText("UserID: "+list.get(i).getUserid()+"\n"+"Timestamp: "+list.get(i).getTimestamp()+"\n"+"Latitude: "+list.get(i).getLatitude()+"\n"+"Longtitude: "+list.get(i).getLongtitude()+"\n"+"~~~~~~~~~~~~~~~~~~~~~\n");
                row.addView(tv);
                tableLayout.addView(row,i);
            }
        }
        //Find result by ID and Timestamp
        else{
            //For Debug reasons
            Log.e("inside if"," no nulls");
            Log.e("inside if"," Userid "+userId);
            Log.e("inside if"," timestamp "+timestamp);


            List<User> list = AppDatabase.getAppDatabase(Activity3.this).dao().findByIdAndTimestamp(userId, timestamp);
            for (int i =0; i<list.size(); i++){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tv = new TextView(this);
                Log.e("Result Debug","UserID: "+list.get(i).getUserid()+"\t"+"Timestamp: "+list.get(i).getTimestamp()+"\t"+"Latitude: "+list.get(i).getLatitude()+"\t"+"Longtitude: "+list.get(i).getLongtitude()+"\t"+"Id: "+list.get(i).getId());
                tv.setText("UserID: "+list.get(i).getUserid()+"\n"+"Timestamp: "+list.get(i).getTimestamp()+"\n"+"Latitude: "+list.get(i).getLatitude()+"\n"+"Longtitude: "+list.get(i).getLongtitude()+"\n"+"~~~~~~~~~~~~~~~~~~~~~\n");
                row.addView(tv);
                tableLayout.addView(row,i);
            }
        }

        //Button gia to Main Activity
        backMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity3.this, MainActivity.class);
                Activity3.this.startActivity(myIntent);
            }
        });
        //Button gia to Activity 2
        backSearchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity3.this, Activity2.class);
                Activity3.this.startActivity(myIntent);
            }
        });


    }

    }
