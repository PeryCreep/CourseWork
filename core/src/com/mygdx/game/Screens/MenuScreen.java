package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.BaseGame;

public class MenuScreen extends BaseScreen {


    public MenuScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initialize() {
        BaseActor mainMenuBackground = new BaseActor(0, 0, mainStage);
        mainMenuBackground.setTexture("Screens/MenuScreen.png");
    }

    @Override
    public void update() {
        if(Gdx.input.isTouched()){
            game.setActiveScreen(new MainLevelScreen(game));
        }
    }
}
