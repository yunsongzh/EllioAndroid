package com.zys.ellio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.WindowManager;

public class GameMainActivity extends Activity {

    private static SharedPreferences prefs;
    private static final String highScoreKey = "highScoreKey";
    private static int highScore;

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 450;
    public static GameView sGame;
    public static AssetManager assets;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        prefs = getPreferences(Activity.MODE_PRIVATE);
        highScore = retrieveHighScore();

        assets = getAssets();
        sGame = new GameView(this,GAME_WIDTH,GAME_HEIGHT);
        setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void setHighScore(int highScore){
        GameMainActivity.highScore = highScore;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(highScoreKey,highScore);
        editor.commit();
    }

    private int retrieveHighScore(){
        return prefs.getInt(highScoreKey,0);
    }

    public static int getHighScore(){
        return highScore;
    }

    @Override
    protected void onResume(){
        super.onResume();
        Assets.onResume();
        sGame.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Assets.onPause();
        sGame.onPause();
    }
}
