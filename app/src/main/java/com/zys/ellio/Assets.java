package com.zys.ellio;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.zys.framework.animation.Animation;
import com.zys.framework.animation.Frame;

import java.io.IOException;
import java.io.InputStream;

public class Assets {

    private static MediaPlayer mediaPlayer;

    private static SoundPool soundPool;
    public  static int hitID,onJumpID;
    public static Bitmap welcome,block,cloud1,cloud2,duck,grass,jump,run1,run2,run3,run4,run5,
            scoreDown,score,startDown,start;
    public static Animation runAnim;

    public static void load(){
        welcome = loadBitmap("welcome.png",false);
        block = loadBitmap("block.png",false);
        cloud1 = loadBitmap("cloud1.png",true);
        cloud2 = loadBitmap("cloud2.png",true);
        duck = loadBitmap("duck.png",true);
        grass = loadBitmap("grass.png",false);
        jump = loadBitmap("jump.png",true);
        run1 = loadBitmap("run_anim1.png",true);
        run2 = loadBitmap("run_anim2.png",true);
        run3 = loadBitmap("run_anim3.png",true);
        run4 = loadBitmap("run_anim4.png",true);
        run5 = loadBitmap("run_anim5.png",true);
        scoreDown = loadBitmap("score_button_down.png",true);
        score = loadBitmap("score_button.png",true);
        startDown = loadBitmap("start_button_down.png",true);
        start = loadBitmap("start_button.png",true);

        Frame f1 = new Frame(run1,.1f);
        Frame f2 = new Frame(run2,.1f);
        Frame f3 = new Frame(run3,.1f);
        Frame f4 = new Frame(run4,.1f);
        Frame f5 = new Frame(run5,.1f);
        runAnim = new Animation(f1,f2,f3,f4,f5,f3,f2);

        //hitID = loadSound("hit.wav");
        //onJumpID = loadSound("onjump.wav");
    }

    private static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try{
            inputStream = GameMainActivity.assets.open(filename);
        } catch (IOException e){
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (transparency){
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        }else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null, options);
        return bitmap;
    }

    private static int loadSound(String filename){
        int soundID = 0;
        if (soundPool == null){
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC,0);
        }
        try {
            soundID = soundPool.load(GameMainActivity.assets.openFd(filename),1);
        }catch (IOException e){
            e.printStackTrace();
        }
        return soundID;
    }

    public static void playSound(int soundID){
        if (soundPool != null){
            soundPool.play(soundID,1,1,1,0,1);
        }
    }

    public static void onResume(){
        hitID = loadSound("hit.wav");
        onJumpID = loadSound("onjump.wav");
        playMusic("bgmusic.mp3",true);
    }
    public static void onPause(){
        if (soundPool != null){
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void playMusic(String filename,boolean looping){
        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
        try {
            AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),
                    afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
