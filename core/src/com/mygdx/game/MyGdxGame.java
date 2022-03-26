package com.mygdx.game;

import com.mygdx.game.Screens.MenuScreen;


public class MyGdxGame extends  BaseGame{

    @Override
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen(this));
    }
}
