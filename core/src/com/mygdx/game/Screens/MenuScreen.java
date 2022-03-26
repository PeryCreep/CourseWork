package com.mygdx.game.Screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Hero;
import com.mygdx.game.Actors.TiledActor;
import com.mygdx.game.BaseGame;
import org.graalvm.compiler.replacements.Log;

public class MenuScreen extends BaseScreen {
    private BaseActor mainMenuBackground;


    public MenuScreen(BaseGame game) {
        super(game);
        mainMenuBackground = new BaseActor(0, 0, mainStage);

        mainMenuBackground.setTexture("Screens/MenuScreen.png");
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
