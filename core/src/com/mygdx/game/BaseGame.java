package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

public abstract class BaseGame extends Game {
    private BaseGame game;
    public boolean pause;

    public BaseGame(){
        game = this;
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
    }

    public void setActiveScreen(Screen screen) {
        game.setScreen(screen);
    }
}

