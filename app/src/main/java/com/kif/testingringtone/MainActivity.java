package com.kif.testingringtone;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Uri ringtone;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Log.d(TAG, "RINGTONE PATH " + ringtone.toString());
                    Log.d(TAG, "RINGTONE PATH 2 " + RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    break;

                default:
                    break;
            }
        }
    }

    public void ringtonelist(View view){
        Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtone);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, ringtone);
        startActivityForResult(intent , 1);
    }

    public void startsound(View view){
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(getApplicationContext(), ringtone);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare(); //don't use prepareAsync for mp3 playback
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.d(TAG, "RINGTONE ERROR " + e.getMessage().toString());
                }
    }

    public void stopsound(View view){
        stopPlaying();
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
