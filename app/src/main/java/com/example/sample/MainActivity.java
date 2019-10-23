package com.example.sample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static MainActivity inst;
    private TextView alarmTextView;
    AlarmManager alarmManager;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
    }

    public void onToggleClicked(View view) throws ParseException {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");

            SimpleDateFormat df = new SimpleDateFormat("KK:mm");
            Date d1 = df.parse("01:22");
            Date d2 = df.parse("01:24");

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY, d1.getHours());
            calendar1.set(Calendar.MINUTE, d1.getMinutes());

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, d2.getHours());
            calendar2.set(Calendar.MINUTE, d2.getMinutes());

            final int _id1 = (int) calendar1.getTimeInMillis();
            final int _id2 = (int) calendar2.getTimeInMillis();

            int resID_1= R.raw.audio_1;
            int resID_2= R.raw.audio_2;


            if(df.format(new Date()) == d1.toString()){
                setReminder(_id1, calendar1, resID_1);
            }
            if(System.currentTimeMillis() == _id2){
                setReminder(_id2, calendar2, resID_2);
            }

        } else {
            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    public void setReminder(int taskId, Calendar when, int url){
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        Bundle b = new Bundle();
        b.putInt("url", url);
        myIntent.putExtra("url", url);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, (int) System.currentTimeMillis(), myIntent, 0);
        alarmManager.set(AlarmManager.RTC, when.getTimeInMillis(), pendingIntent);
    }
}
