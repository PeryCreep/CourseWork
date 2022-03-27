package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.BaseGame;

public class GameOver extends BaseScreen {

    public GameOver(BaseGame game) {
        super(game);
    }

    @Override
    public void initialize() {
        BaseActor gameOver = new BaseActor(0, 0, mainStage);

        gameOver.setTexture("Screens\\gameOver.jpg");
    }

    @Override
    public void update() {
        if(Gdx.input.isTouched()){
            game.setActiveScreen(new MainLevelScreen(game));
        }
    }
}
