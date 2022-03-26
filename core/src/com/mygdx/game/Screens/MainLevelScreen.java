package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Hero;
import com.mygdx.game.Actors.Skeleton;
import com.mygdx.game.BaseGame;
import com.mygdx.game.HUD.InfoBar;

public class MainLevelScreen extends BaseScreen {
    private BaseActor map;
    public static Hero hero;

    public MainLevelScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initialize() {
        //создаем карту
        BaseActor map = new BaseActor(0, 0, mainStage);
        map.setTexture("map.png");
        map.setSize(1792, 1024);
        BaseActor.setWorldBound(map);


        //создаем героя.
        hero = new Hero(400, 150, mainStage);
        hero.setHitBox(hero.getWidth(), hero.getHeight(), hero.getOriginX(), hero.getOriginY());
        new Skeleton(MathUtils.random(mainStage.getWidth()), MathUtils.random(mainStage.getHeight()), mainStage);
        new Skeleton(MathUtils.random(mainStage.getWidth()), MathUtils.random(mainStage.getHeight()), mainStage);
        new Skeleton(MathUtils.random(mainStage.getWidth()), MathUtils.random(mainStage.getHeight()), mainStage);


        tableHUD.pad(5);
        tableHUD.add(new InfoBar(15, 15, uiStage)).align(Align.topLeft);
        tableHUD.add().expandX();
        tableHUD.row().expandY();
        tableHUD.add();


    }

    @Override
    public void update() {
        if(hero.getHealthPoint() <= 0){
            game.setActiveScreen(new GameOver(game));
        }
        hero.setCameraAtActor();
    }

}
