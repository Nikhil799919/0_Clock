package com.example.a0_oclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RecyclerView recyclerView;
    RadioButton time_button , one_day , two_day , seven_day , fifteen_day , thirty_day , custom_day;
    static MyDatabaseHelper myDB;
    static ArrayList<String> id;
    static ArrayList<String> name;
    static ArrayList<String> dob ;
    static ArrayList<String> universalList = new ArrayList<>();
    static int n=0;
    CustomAdapter customAdapter;
    int time ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        radioGroup = findViewById(R.id.RG);
        one_day = findViewById(R.id.button_1_day);
        two_day = findViewById(R.id.button_2_day);
        seven_day = findViewById(R.id.button_1_week);
        fifteen_day = findViewById(R.id.button_15_days);
        thirty_day = findViewById(R.id.button_1_month);
        //custom_day = findViewById(R.id.button_Custom_date);
        myDB  = new MyDatabaseHelper(TimeActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        dob = new ArrayList<>();

        // Load the previously saved state
        loadSelectedRadioButton();

        // Set a listener for the RadioGroup to detect changes
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Store the checked RadioButton's ID in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("RadioButtonPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selectedRadioButton", checkedId); // Save checked RadioButton's ID
                editor.apply(); // Apply changes
            }
        });

        // Set onClickListeners for RadioButtons
        setRadioButtonListeners();

        // Load and apply saved RadioButton state
        loadAndApplySavedRadioButtonState();

//        createNotificationChannel();
        Button pickTimeButton = findViewById(R.id.pickTimeButton);
        pickTimeButton.setOnClickListener(v -> showTimePicker());
    }

    // Method to load the selected RadioButton
    public void loadSelectedRadioButton() {
        SharedPreferences sharedPreferences = getSharedPreferences("RadioButtonPrefs", MODE_PRIVATE);
        int selectedRadioButtonId = sharedPreferences.getInt("selectedRadioButton", -1); // Default to -1 if nothing is saved

        // Set the saved RadioButton as checked
        if (selectedRadioButtonId != -1) {
            RadioButton savedRadioButton = findViewById(selectedRadioButtonId);
            savedRadioButton.setChecked(true); // Check the saved RadioButton
        }
    }

    private void setRadioButtonListeners() {
        one_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = 1;
                universalList = storeInArrayList(1);
                Toast.makeText(TimeActivity.this, "You will fotify 1 day before !!", Toast.LENGTH_SHORT).show();
                saveRadioButtonState(R.id.button_1_day);
            }
        });

        two_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = 2;
                universalList = storeInArrayList(2);
                Toast.makeText(TimeActivity.this, "You will fotify 2 days before !!", Toast.LENGTH_SHORT).show();
            }
        });

        seven_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = 7;
                universalList = storeInArrayList(7);
                Toast.makeText(TimeActivity.this, "You will fotify 1 week before !!", Toast.LENGTH_SHORT).show();
            }
        });

        fifteen_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = 15;
                universalList = storeInArrayList(15);
                Toast.makeText(TimeActivity.this, "You will fotify 15 days before !!", Toast.LENGTH_SHORT).show();
            }
        });

        thirty_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = 30;
                universalList = storeInArrayList(30);
                Toast.makeText(TimeActivity.this, "You will fotify 1 month before !!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Method to save the checked RadioButton's state
    private void saveRadioButtonState(int radioButtonId) {
        SharedPreferences sharedPreferences = getSharedPreferences("RadioButtonPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selectedRadioButton", radioButtonId);
        editor.apply();
    }

    // Method to load and apply the saved RadioButton state
    public void loadAndApplySavedRadioButtonState() {
        SharedPreferences sharedPreferences = getSharedPreferences("RadioButtonPrefs", MODE_PRIVATE);
        int selectedRadioButtonId = sharedPreferences.getInt("selectedRadioButton", -1); // Default to -1 if nothing is saved

        if (selectedRadioButtonId != -1) {
            RadioButton savedRadioButton = findViewById(selectedRadioButtonId);
            savedRadioButton.setChecked(true); // Set the saved RadioButton as checked

            // Automatically trigger the onClickListener for the checked RadioButton
            savedRadioButton.performClick(); // This will trigger the onClickListener
        }
    }
    public static ArrayList<String> storeInArrayList(int n) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        String formattedDate = sdf.format(currentDate);

        String dayt0 = formattedDate.substring(0,2);
        String montht0 = formattedDate.substring(3,5);
        String yeart0 = formattedDate.substring(6,10);

        int dayt2 = Integer.parseInt(dayt0);
        int montht2 = Integer.parseInt(montht0);
        int yeart2 = Integer.parseInt(yeart0);

        Cursor cursor = myDB.getDOB();
        ArrayList<String> birthday = new ArrayList<>();
        if(cursor.getCount() == 0){
            Log.d("NODATA", "storeDataInArrays DOB is : ");
//            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()) {
                Log.d("TAG", "storeDataInArrays DOB is : ");
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                dob.add(cursor.getString(2));
                String temp = cursor.getString(2);

                String day0 = temp.substring(0, 2);
                String month0 = temp.substring(3, 5);
                String year0 = temp.substring(6, 10);

                int day2 = Integer.parseInt(day0);
                int month2 = Integer.parseInt(month0);
                int year2 = Integer.parseInt(year0);

                int newDate = (int) DateAddition.addDays(dayt2, montht2, yeart2, day2, month2, year2);

                if(newDate == n){
                    birthday.add(cursor.getString(1));
                }
            }
        }
        return birthday;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderChannel";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Show the TimePicker and schedule the notification
    public void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> {
                    Calendar chosenTime = Calendar.getInstance();
                    chosenTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    chosenTime.set(Calendar.MINUTE, minuteOfHour);
                    chosenTime.set(Calendar.SECOND, 0);

                    scheduleNotification(chosenTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    // Schedule the notification at the chosen time
    public void scheduleNotification(Calendar calendar) {

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putStringArrayListExtra("EXTRA_NAMES", universalList); // Send the ArrayList
        intent.putExtra("daydiff", n);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);

        long interval = 1 * 60 * 1000; // 1 minutes
        long startTime = System.currentTimeMillis();

        // Ensure AlarmManager doesn't wake the device unnecessarily
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);



        // for non repeating alarm origional code
//        if (alarmManager != null) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            Toast.makeText(this, "Notification scheduled", Toast.LENGTH_SHORT).show();
//        }
    }
}