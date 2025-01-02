package com.example.a0_oclock;

import android.os.Build;
import android.util.Log;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class DateAddition {

    // Method to calculate the difference in total days (supports both pre-O and post-O)
    public static long addDays(int day, int month, int year, int day1, int month1, int year1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android O and above, use java.time package
            return calculateDifferenceInDaysPostO(day, month, year, day1, month1, year1);
        } else {
            // For Android versions below O, use Calendar
            return calculateDifferenceInDaysPreO(day, month, year, day1, month1, year1);
        }
    }

    // Method for Android O and above (using java.time package)
    private static long calculateDifferenceInDaysPostO(int day, int month, int year, int day1, int month1, int year1) {
        LocalDate date1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date1 = LocalDate.of(year, month, day);
        }
        LocalDate date2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date2 = LocalDate.of(year1, month1, day1);
        }

        long daysBetween = 0; // Get total difference in days
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            daysBetween = ChronoUnit.DAYS.between(date1, date2);
        }
        Log.d("DateDifferencePostO", "Difference in days: " + daysBetween);

        return daysBetween;
    }

    // Method for Android versions below O (using Calendar)
    private static long calculateDifferenceInDaysPreO(int day, int month, int year, int day1, int month1, int year1) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.set(year, month - 1, day); // Calendar months are 0-based
        endCalendar.set(year1, month1 - 1, day1);

        // Calculate the difference in milliseconds and convert to days
        long diffInMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        long daysBetween = diffInMillis / (24 * 60 * 60 * 1000); // Convert milliseconds to days

        Log.d("DateDifferencePreO", "Difference in days: " + daysBetween);

        return daysBetween;
    }
}
