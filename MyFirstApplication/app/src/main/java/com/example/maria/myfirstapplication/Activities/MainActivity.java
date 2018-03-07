package com.example.maria.myfirstapplication.Activities;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.maria.myfirstapplication.DB.AppDatabase;
import com.example.maria.myfirstapplication.DB.User;
import com.example.maria.myfirstapplication.R;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;


/*Author Maria Voreakou (it214123)
*
* Main Activity is the first Activity which can insert in db Fields such as
* userid, longtitude, latitude, date and time. Also in the insertion
* db has a primary key of each row*/

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    Calendar myTimestamp = Calendar.getInstance();

    EditText latitude; //latitude field
    EditText longtitude; // longtitude field
    EditText userid; //Userid field
    EditText dateText; //Date field, Λόγω των μεμονομένων Widget (ημερολόγιο, και ώρα) χωρίστηκε σε 2 fields το timestamp
    EditText timeText; //Time field
    Button submitButton;// Submit button
    Button searchButton;// Search button

    //eksetasi
    Button deleteButton;// Search button
    EditText deleteText; //Delete field

    ConstraintLayout mainLayout;

    //Update date with Format MM/DD/YY
    private void updateDate() {
        String myFormat = "MM/dd/yy"; // Format of Date
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(sdf.format(myTimestamp.getTime()));
    }
    //Update time with Format HH:MM
    private void updateTime() {
        String myFormat = "HH:mm"; // Format of Time
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        timeText.setText(sdf.format(myTimestamp.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); //Set activity main.xml
        dateText = (EditText) findViewById(R.id.date_id);//Find id for date field


        //Constructor for Date Picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myTimestamp.set(Calendar.YEAR, year);
                myTimestamp.set(Calendar.MONTH, monthOfYear);
                myTimestamp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        //Listener for Date Picker
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myTimestamp
                        .get(Calendar.YEAR), myTimestamp.get(Calendar.MONTH),
                        myTimestamp.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // Same for Time
        timeText = (EditText) findViewById(R.id.time_id);
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myTimestamp.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myTimestamp.set(Calendar.MINUTE, minute);
                updateTime();
            }
        };
        // Listener for Time picker
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this, time, myTimestamp
                        .get(Calendar.HOUR_OF_DAY), myTimestamp.get(Calendar.MINUTE), true).show();
            }
        });

        //Define ids to buttons and editTexts
        submitButton = (Button) findViewById(R.id.insert_button);
        latitude = (EditText) findViewById(R.id.latitude_id);
        longtitude = (EditText) findViewById(R.id.longtitude_id);
        userid = (EditText) findViewById(R.id.userid_id);
        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout_id);

        //Eksetasi
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteText = (EditText) findViewById(R.id.deleteEditText);

        //Set Input type for negative numbers
        latitude.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        longtitude.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED|InputType.TYPE_NUMBER_FLAG_DECIMAL);






        //Submit Button, Insert Button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //If userid is null, cannot insert in the db
                if(userid.getText().length()==0 || userid.getText() == null ) {
                    //Create context for popup message
                    Context context = getApplicationContext();
                    CharSequence text = "Please insert UserId!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                else if(userid.getText().toString().length() >10 ){
                    //Create context for popup message
                    Context context = getApplicationContext();
                    CharSequence text = "Please insert UserId up to 10 letters!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //If latitude is null, cannot insert in the db
                else if(latitude.getText().length()==0 || latitude.getText() == null) {
                    //Create context for popup message
                    Context context = getApplicationContext();
                    CharSequence text = "Please insert Latitude!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Also latitude must be from -180  to 180
                else if(Float.parseFloat(latitude.getText().toString())<-180f || Float.parseFloat(latitude.getText().toString())>180f){
                    Context context = getApplicationContext();
                    CharSequence text = "Latitude must be between -180 and 180";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Same as above for longtitude
                else if(longtitude.getText().length()==0 || longtitude.getText() == null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please insert Longtitude!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Longitude must be from -180 to 180
                else if(Float.parseFloat(longtitude.getText().toString())<-180f || Float.parseFloat(longtitude.getText().toString())>180f){
                    Context context = getApplicationContext();
                    CharSequence text = "Longtitude must be between -180 and 180";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Check for date picker not to be null
                else if(dateText.getText().length()==0 || dateText.getText() == null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please insert Date!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Check for time picker not to be null
                //Δεν χρειάζεται έλεγχος για τo μέγεθος του timestamp, διότι δεν μπορεί να ξεπερνάει
                //τους 14 χαρακτήρες.
                //Format: mm/dd/yyy hh:mm (συνολο 14 χαρακτήρες)
                else if(timeText.getText().length()==0 || timeText.getText() == null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please insert Time!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                //Create a user
                User user = new User();
                //Get all the fields and set them
                user.setUserid(userid.getText().toString());
                user.setLongtitude(Float.parseFloat(longtitude.getText().toString()));
                user.setLatitude(Float.parseFloat(latitude.getText().toString()));
                // Get date and time to one variable (timestamp)
                user.setTimestamp(dateText.getText().toString() + " " + timeText.getText().toString());


                //Insert to db
                AppDatabase.getAppDatabase(MainActivity.this).dao().insert(user);

                //Set text to null
                userid.setText("");
                longtitude.setText("");
                latitude.setText("");
                dateText.setText("");
                timeText.setText("");

                //Create popup message "Success"
                Context context = getApplicationContext();
                CharSequence text = "Insertion Successful!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //User retrievedUser = AppDatabase.getAppDatabase(MainActivity.this).dao().findById(user.getUserid());

                //System.out.println(""+retrievedUser.getId()+" "+retrievedUser.getLatitude()+" "+retrievedUser.getLongtitude()+" "+retrievedUser.getId()+" "+retrievedUser.getTimestamp());


            }
        });


        searchButton = (Button) findViewById(R.id.search_id);
        //Button for the next Activity
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Activity2.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Create a user
                User user = new User();
                //Get all the fields and set them
                user.setId(Integer.parseInt(deleteText.getText().toString()));
                //delete to db
                AppDatabase.getAppDatabase(MainActivity.this).dao().deletebyID(user);


                deleteText.setText("");

                //User retrievedUser = AppDatabase.getAppDatabase(MainActivity.this).dao().deletebyID(((String) user.getId()));

                //System.out.println(""+retrievedUser.getId()+" "+retrievedUser.getLatitude()+" "+retrievedUser.getLongtitude()+" "+retrievedUser.getId()+" "+retrievedUser.getTimestamp());



                //Create popup message "Success"
                Context context = getApplicationContext();
                CharSequence text = "Delete id Successful!";          
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();




            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
