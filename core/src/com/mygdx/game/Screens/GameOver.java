package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.BaseGame;

public class GameOver extends BaseScreen {
    public GameOver(BaseGame game) {
        super(game);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        if(Gdx.input.isTouched()){
            game.setActiveScreen(new MainLevelScreen(game));
        }
    }
}
