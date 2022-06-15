package com.mygdx.game.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;

import static com.mygdx.game.Screens.MainLevelScreen.hero;

public class InfoBar extends BaseActor {
    private  Texture texture, heroFace;
    private Texture expBar;

    private float hp, x;


    public InfoBar(float x, float y, Stage s) {
        super(x, y, s);
        debug();
        hp = hero.getHealthPoint();
        texture = new Texture("HUD\\leftTopCorner\\hpBarRed.png");
        setTexture("HUD\\leftTopCorner\\hpBar.png");
        heroFace = new Texture("HUD\\leftTopCorner\\heroface.png");
        expBar = new Texture("HUD\\leftTopCorner\\ExpLine.png");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hp = hero.getHealthPoint() / hero.getMaxHp();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,   getX() + (120 - 120* hp), getY() - 2, getWidth() * hp, getHeight());
        batch.draw(heroFace, getX() - 45, getY() - 28 , heroFace.getWidth() - 25, heroFace.getHeight() - 50);
        batch.draw(expBar, 0, 0, 0,0, 128 + ((int) (1.9f * (hero.getCurrentExp()/(hero.getExpToNextLvl()/100)))), 720);
    }
}
