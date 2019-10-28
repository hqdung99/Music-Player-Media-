package com.example.zingmp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;

import com.example.zingmp3.databinding.PlayMusicBinding;

import java.io.IOException;
import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity implements Runnable{

    PlayMusicBinding playMusicBinding;
    ArrayList<DataMusic> dataMusics;
    MediaPlayer mediaPlayer = null;
    DataMusic dataMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);
        this.getSupportActionBar().hide();

        playMusicBinding = DataBindingUtil.setContentView(this, R.layout.play_music);

        Intent intent = this.getIntent();
        this.dataMusics = MainActivity.getDataMusics();
        dataMusic = new DataMusic(intent.getStringExtra("order"),
                intent.getStringExtra("name_song"),intent.getStringExtra("name_singer"));

        this.setLayoutPlayMusic(dataMusic);

        playMusicBinding.playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                playSong();
            }
        });

        playMusicBinding.circularButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                circularSong();
            }
        });

        playMusicBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

                Log.d("*******************", "************************");
                seekBar.setVisibility(View.VISIBLE);

                int minute = progress / (1000 * 60);
                int second = (progress % (1000 * 60)) / 1000;
                playMusicBinding.timeForSeekBar.setText("" + minute + ":" + second);

                double percent = progress / (double)playMusicBinding.seekBar.getMax();
                int offset = seekBar.getThumbOffset();
                int seekWidth = seekBar.getWidth();
                int val = (int)Math.round(percent * (seekWidth - 2 * offset)); // |--|----------.------------|--|
                int labelWidth = playMusicBinding.timeForSeekBar.getWidth();

                // Dùng để di chuyển vị trí của textView theo seekBar
                playMusicBinding.timeForSeekBar.setX(seekBar.getX() + offset + val
                        - Math.round(percent * offset)
                        - Math.round(percent * labelWidth / 2));
                //---------------------------------------------
//                RotateAnimation rotateAnimation =
//                        new RotateAnimation(playMusicBinding.imageCircleRotate.getRotation(),  playMusicBinding.imageCircleRotate.getRotation() + 2 , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                rotateAnimation.setDuration(3000);
//                rotateAnimation.setInterpolator(new LinearInterpolator());
//
//                playMusicBinding.imageCircleRotate.setRotation(playMusicBinding.imageCircleRotate.getRotation() + 1);
//
//                playMusicBinding.imageCircleRotate.animate().rotation(5);
//                playMusicBinding.imageCircleRotate.startAnimation(rotateAnimation);
                if (mediaPlayer.isPlaying()) {
                    playMusicBinding.imageCircleRotate.animate().rotation(playMusicBinding.imageCircleRotate.getRotation() + 15);
                }

                //---------------------------------------------

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(playMusicBinding.seekBar.getProgress());
                }
            }
        });



    }

    private void circularSong() {
        if (mediaPlayer != null && mediaPlayer.isLooping() == true) {
            playMusicBinding.circularButton
                    .setImageDrawable(ContextCompat
                            .getDrawable(PlayMusicActivity.this, R.drawable.circular_arrow));
            mediaPlayer.setLooping(false);
        } else if (mediaPlayer != null && mediaPlayer.isLooping() == false){
            playMusicBinding.circularButton
                    .setImageDrawable(ContextCompat
                            .getDrawable(PlayMusicActivity.this, R.drawable.circular_arraw_choose));
            mediaPlayer.setLooping(true);
        }
    }

    private void playSong() {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playMusicBinding.playButton
                        .setImageDrawable(ContextCompat
                                .getDrawable(PlayMusicActivity.this, R.drawable.play_button));


            } else if (mediaPlayer != null && mediaPlayer.isPlaying() == false) {
                mediaPlayer.seekTo(playMusicBinding.seekBar.getProgress());
                mediaPlayer.start();
                playMusicBinding.playButton
                        .setImageDrawable(ContextCompat
                                .getDrawable(PlayMusicActivity.this, R.drawable.pause));

                new Thread(this).start();
            } else if (mediaPlayer == null) {

                mediaPlayer = new MediaPlayer();
                playMusicBinding.playButton
                        .setImageDrawable(ContextCompat
                                .getDrawable(PlayMusicActivity.this, R.drawable.pause));

                AssetFileDescriptor descriptor;
                try {
                    descriptor = getAssets().openFd(getNameToOpenSong());
                    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                    descriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.prepare();
                playMusicBinding.seekBar.setMax(mediaPlayer.getDuration());

                mediaPlayer.start();
                new Thread(this).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLayoutPlayMusic(DataMusic dataMusic) {
            String order = dataMusic.getNumerical_order();
            Log.d("*****", "-" + order + "-");
            if (order.compareTo("1") == 0) {
                Log.d("*****", "" + order);
                playMusicBinding.imageCircleRotate.setBackgroundResource(R.drawable.circle01);
            }
            else if (order.compareTo("2") == 0) {
                playMusicBinding.imageCircleRotate.setBackgroundResource(R.drawable.circle02);
            }
            else if (order.compareTo("3") == 0) {
                playMusicBinding.imageCircleRotate.setBackgroundResource(R.drawable.circle03);
            }
            else if (order.compareTo("4") == 0) {
                playMusicBinding.imageCircleRotate.setBackgroundResource(R.drawable.circle04);
            }
            else if (order.compareTo("5") == 0) {
                playMusicBinding.imageCircleRotate.setBackgroundResource(R.drawable.circle05);
            }
            else if (order.compareTo("6") == 0) {
                playMusicBinding.imageCircleRotate.setBackgroundResource(R.drawable.circle06);
            }

            playMusicBinding.nameSong.setText(dataMusic.getName_song());
            playMusicBinding.nameSinger.setText(dataMusic.getName_singer());
            playMusicBinding.playButton
                .setImageDrawable(ContextCompat
                        .getDrawable(PlayMusicActivity.this, R.drawable.play_button));
            playMusicBinding.circularButton
                    .setImageDrawable(ContextCompat
                    .getDrawable(PlayMusicActivity.this, R.drawable.circular_arrow));
    }

    public void finish() {
        Intent intent = new Intent();
        super.finish();
    }

    public void backButton(View view) {
        this.onBackPressed();
    }

    @Override
    public void run() {

        Log.d("***", "***");
        int currentPosition = mediaPlayer.getCurrentPosition();
        int total = mediaPlayer.getDuration();

        while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(100);
                currentPosition = mediaPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playMusicBinding.seekBar.setProgress(currentPosition);
        }

        playMusicBinding.seekBar.refreshDrawableState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearMediaPlayer();
    }

    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private String getNameToOpenSong() {
        return dataMusic.numerical_order + ".mp3";
    }


}
