package com.example.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(final Context context, Intent intent) {

        /*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        // Acquire the lock
        wl.acquire();*/


        // The DialogClass will make alert dialog for indication

        // Release the lock
//        wl.release();

        //this will update the UI with message
        MainActivity inst = MainActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");

        int audioID = intent.getIntExtra("url", 0);

        mediaPlayer = MediaPlayer.create(context, audioID);
        mediaPlayer.start();

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)

        /*mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();*/

    }

    private void playSound(final Context context, Uri alert) {

        Thread background = new Thread(new Runnable() {
            public void run() {
                try {

                    mediaPlayer.start();

                } catch (Throwable t) {
                    Log.i("Animation", "Thread  exception " + t);
                }
            }
        });
        background.start();
    }
}
