package com.example.a0_oclock;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    EditText name_input, dob_input ;
    Button add_button;
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        dob_input = findViewById(R.id.DOB_input);


        add_button  = findViewById(R.id.add_button);


//
//        // Step 1: Get the LayoutParams of the Button
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) add_button.getLayoutParams();
//
//        // Step 2: Reduce the top margin (reduce by 20px)
//        params.topMargin = 0;
//
//        // Step 3: Apply the new LayoutParams back to the Button
//        add_button.setLayoutParams(params);

//        add_button.setVisibility(View.GONE);
        dob_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Date Picker Dialog
                picker = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(dayOfMonth<10 && month+1 <10){
                            dob_input.setText("0" + dayOfMonth + "-0" + (month+1) + "-" + year);
                        }
                        else if(dayOfMonth>=10 && month+1 <10){
                            dob_input.setText(dayOfMonth + "-0" + (month+1) + "-" + year);
                        }
                        else if(dayOfMonth<10 && month+1 >=10){
                            dob_input.setText("0" + dayOfMonth + "-" + (month+1) + "-" + year);
                        }
                        else{
                            dob_input.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                        }
                    }
                },year , month, day);
                picker.show();
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(name_input.getText().toString().trim(),
                        dob_input.getText().toString().trim());
            }

        });
    }
}