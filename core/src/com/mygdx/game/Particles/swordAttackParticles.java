package com.mygdx.game.Particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Hero;
import com.mygdx.game.Actors.Skeleton;

import static com.mygdx.game.Screens.MainLevelScreen.hero;

public class swordAttackParticles extends BaseActor {
    private final Stage stage;
    private final Animation<TextureRegion> left, right;
    private final float frameDur = 0.12f;
    private boolean leftView;

    public swordAttackParticles(float x, float y, Stage s, Hero hero) {
        super(x, y, s);
        stage = s;
        hero.addActor(this);

        String[] filenames = {"Hero\\AttackParticles\\SP301_01.png", "Hero\\AttackParticles\\SP301_02.png", "Hero\\AttackParticles\\SP301_03.png", "Hero\\AttackParticles\\SP301_04.png", "Hero\\AttackParticles\\SP301_05.png"};
        left = setAnimationFromFile(filenames, frameDur, false);
        right = setAnimationFromFile(filenames, frameDur, false);
        Object[] a = right.getKeyFrames();
        for(Object frame : a){
            ((TextureRegion)frame).flip(true, false);
        }
        setVisible(false);
        setHitBox(getWidth(), getHeight(), getOriginX(), getOriginY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (BaseActor skeleton : getListActor(stage, "com.mygdx.game.Actors.Skeleton")) {
            if (skeleton.getHitBox().overlaps(hitBox)) {
                Skeleton temp = (Skeleton) skeleton;
                if (animation.isAnimationFinished(elapsedTime))
                    temp.setHp(temp.getHp() - hero.getDamage());
            }
        }

        if(leftView)
            setAnimation(left);
        else
            setAnimation(right);

        if(isAnimFinished()) {
            setVisible(false);
            updateHitBox(4000, 4000);
            elapsedTime = 0;
            setAnimationPaused(true);
        }
    }


    public void setLeftView(boolean leftView) {
        this.leftView = leftView;
    }
}
