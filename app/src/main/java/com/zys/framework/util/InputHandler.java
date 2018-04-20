package com.zys.framework.util;

import android.view.MotionEvent;
import android.view.View;

import com.zys.game.state.State;
import com.zys.ellio.GameMainActivity;

public class InputHandler implements View.OnTouchListener {

    private State currentState;

    public void setCurrentState(State currentState){
        this.currentState = currentState;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event){
        int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(event,scaledX,scaledY);
    }
}
