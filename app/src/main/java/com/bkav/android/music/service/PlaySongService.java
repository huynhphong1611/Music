package com.bkav.android.music.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bkav.android.music.R;
import com.bkav.android.music.activity.MainActivity;
import com.bkav.android.music.object.MyPlayer;
import com.bkav.android.music.object.Song;

import java.io.IOException;

public class PlaySongService extends Service {
    private static final String CHANNEL_ID = "PLAY_MUSIC";
    private static  final int NOTIFICATION_ID=1;
    private MediaPlayer mMediaPlayer;
    private IBinder mIBinder;
    /*khai bao notification*/
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;

    public PlaySongService() {

    }

    @Override
    public void onCreate() {
        Log.d("ServiceDemo", "Đã gọi onCreate()");
        mIBinder = new MyBinder(); // do MyBinder được extends Binder
        /*notification*/
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mRemoteViews = new RemoteViews(getPackageName(),R.layout.activity_notification);
        super.onCreate();
    }

    // Bắt đầu một Service
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        Song song = intent.getParcelableExtra(MainActivity.PATH_SONG);
        mMediaPlayer  = MediaPlayer.create(this, Uri.parse(song.getmPath()));
        play();
        showNotification(song);
        // trả về đối tượng binder cho ActivityMain
        return mIBinder;
    }

    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        mMediaPlayer.stop();
        return super.onUnbind(intent);
    }
    // Xây dựng các phương thức thực hiện nhiệm vụ,
    public void fastForward(int position){
        mMediaPlayer.seekTo(position); // tua đến giây thứ 60
    }
    // phát nhạc
    public void play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }
    public void pause(){
        if(mMediaPlayer!= null){
            mMediaPlayer.pause();
        }

    }
    public MediaPlayer getMediaPlayer(){
        return mMediaPlayer;
    }
    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public PlaySongService getService() {
            return PlaySongService.this;
        }
    }
    /*Notification*/
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void showNotification(Song song){
        createNotificationChannel();
        Bitmap bitmap=null;
        mBuilder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setColor(ContextCompat.getColor(this,R.color.colorWhite))
                .setSmallIcon(R.drawable.ic_fab_play_btn_normal)
                .setCustomContentView(mRemoteViews);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(song.getmAlbumArt()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap==null){
            mRemoteViews.setImageViewResource(R.id.img_song_notification,R.drawable.unknown_albums);
        }else{
            mRemoteViews.setImageViewBitmap(R.id.img_song_notification,bitmap);
        }

        mRemoteViews.setImageViewResource(R.id.image_pre_song_notification,R.drawable.ic_rew_dark);
        mRemoteViews.setImageViewResource(R.id.image_next_song_notification,R.drawable.ic_fwd_dark);
        mRemoteViews.setImageViewResource(R.id.image_play_song_notification,R.drawable.ic_media_pause_dark);
        mRemoteViews.setTextViewText(R.id.txt_name_song_notification, song.getmNameSong());
        mRemoteViews.setTextViewText(R.id.txt_name_singer_notification, song.getmNameSinger());
        mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
    }
}
