package com.mygdx.game.HUD;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;

import static com.mygdx.game.Screens.MainLevelScreen.hero;

public class InfoBar extends BaseActor {
    private  Texture texture;

    private float hp, x;


    public InfoBar(float x, float y, Stage s) {
        super(x, y, s);
        hp = hero.getHealthPoint();
        texture = new Texture("HUD\\leftTopCorner\\hpBarRed.png");
        setTexture("HUD\\leftTopCorner\\hpBar.png");
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
    }
}
