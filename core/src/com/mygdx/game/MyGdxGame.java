package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.Screens.MenuScreen;


public class MyGdxGame extends  BaseGame{
    public static LabelStyle labelStyle;

    @Override
    public void create() {
        super.create();
        labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("Fonts\\mainLabel.fnt"));
        setActiveScreen(new MenuScreen(this));
    }
}
