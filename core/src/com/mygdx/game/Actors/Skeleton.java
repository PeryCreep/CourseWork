package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.InterfaceCreatures.Enemy;

import static com.mygdx.game.Screens.MainLevelScreen.hero;
import static com.mygdx.game.Screens.MainLevelScreen.score;

public class Skeleton extends BaseActor {
    private Animation<TextureRegion> walk, attack, death;
    private final int frameWidth = 150, frameHeight = 150;
    private int Hp;
    private final float walkFrameDuration, attackFrameDuration;
    private float heroX, heroY;
    private float speed = 40, damage = 1, expToGain = 10;
    private Stage s;
    private final int point = 1;


    public Skeleton(float x, float y, Stage s) {
        super(x, y, s);

        this.s = s;
        debug();
        walkFrameDuration = 0.1f;
        attackFrameDuration = 0.1f;
        Hp = 100;
        setOrigin((float) frameWidth / 2, (float) frameHeight / 2);
        setHitBox(70, 56, getOriginX(), getOriginY());

        Texture texture = new Texture("Enemy\\Skeleton\\Walk.png");
        TextureRegion[][] textureRegion = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> temp = new Array<>();
        for (int frame = 0; frame < 4; frame++) {
            temp.add(textureRegion[0][frame]);
        }
        walk = new Animation<>(walkFrameDuration, temp, Animation.PlayMode.LOOP_PINGPONG);
        temp.clear();

        texture = new Texture("Enemy\\Skeleton\\Attack.png");
        textureRegion = TextureRegion.split(texture, frameWidth, frameHeight);
        temp = new Array<>();
        for (int frame = 0; frame < 8; frame++) {
            temp.add(textureRegion[0][frame]);
        }
        attack = new Animation<>(walkFrameDuration, temp);
        temp.clear();

        texture = new Texture("Enemy\\Skeleton\\Death.png");
        textureRegion = TextureRegion.split(texture, frameWidth, frameHeight);
        temp = new Array<>();
        for (int frame = 0; frame < 4; frame++) {
            temp.add(textureRegion[0][frame]);
        }
        death = new Animation<>(0.1f, temp);
        temp.clear();
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (Hp <= 0) {
            if ((animation != death) && elapsedTime != 0) {
                elapsedTime = 0;
                setAnimation(death);
            }
            if (!death.isAnimationFinished(elapsedTime)) {
                setAnimation(death);
            } else {
                score += point;
                hero.setCurrentExp(hero.getCurrentExp() + expToGain);
                setAnimation(walk);
                setPosition(MathUtils.random(s.getWidth()), MathUtils.random(s.getHeight()));
                setHp(100);
            }
        } else {
            if (overlaps(hero)) {
                setAnimation(attack);
                if (attack.isAnimationFinished(elapsedTime)) {
                    hero.setHealthPoint((int) hero.getHealthPoint() - (int) damage);
                    elapsedTime = 0;
                }
            } else {
                heroX = hero.getX();
                heroY = hero.getY();
                if (getX() < heroX) {
                    setX(getX() + speed * delta);
                }
                if (getX() > heroX) {
                    setX(getX() - speed * delta);
                }

                if (getY() > heroY) {
                    setY(getY() - speed * delta);
                }

                if (getY() < heroY) {
                    setY(getY() + speed * delta);
                }
                setAnimation(walk);
            }

            collisionDetection();
            updateHitBox(getX() + getWidth() / 2, getY() + getHeight() / 2);
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        heroX = hero.getX();
        boolean flip = !(getX() < heroX);

        batch.draw(animation.getKeyFrame(elapsedTime), flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());

    }

    public void collisionDetection() {
        for (BaseActor actor : getListActor(getStage(), "com.mygdx.game.Actors.Skeleton")) {
            Skeleton anotherSkeleton = (Skeleton) actor;

//                if (getX()> anotherSkeleton.getX()) {
//                    setX(getX() );
//                    anotherSkeleton.setX(anotherSkeleton.getX());
//                } else {
//                    setX(getX()  );
//                    anotherSkeleton.setX(anotherSkeleton.getX() );
//                }
//
//                if (getY() > anotherSkeleton.getY()) {
//                    setY(getY());
//                    anotherSkeleton.setY(anotherSkeleton.getY() );
//                } else {
//                    setY(getY());
//                    anotherSkeleton.setY(anotherSkeleton.getY());
//                }
//           }
        }
    }
}
