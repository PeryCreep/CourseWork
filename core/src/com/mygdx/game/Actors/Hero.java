package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.Particles.swordAttackParticles;
import com.mygdx.game.Weapons.StandardSword;
import com.mygdx.game.Weapons.Sword;

public class Hero extends BaseActor {
    private Sword currentSword;
    private float healthPoint, maxHP = 100;
    private float speed;
    private int agility = 10, strength = 1, intelligence = 10;
    private boolean left;
    private final Animation<TextureRegion> runLeft, runRight, idleLeft, idleRight, getHit;
    private final swordAttackParticles particles;
    private final Sound swing;

    public Hero(float x, float y, Stage s) {
        super(x, y, s);

        swing = Gdx.audio.newSound(Gdx.files.internal("Sound\\Effects\\swing1.ogg"));

        currentSword = new StandardSword();
        maxHP = 100;
        healthPoint = 100;
        speed = 100;
        left = false;
        float stayFrameDuration = 0.12f;
        float runFrameDuration = 0.11f;
        int frameWidth = 135;
        int frameHeight = 135;
        particles = new swordAttackParticles(0, 0, s, this);
        this.setOrigin(135 / 2, 135 / 2);
        addActor(particles);


        Texture texture = new Texture(Gdx.files.internal("Hero\\RunLeft.png"));
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureRegions = new Array<>();

        //загружаем анимацию бега влево
        for (int f = 0; f < 6; f++) {
            textureRegions.add(temp[0][f]);
        }
        runLeft = new Animation<>(runFrameDuration, textureRegions, Animation.PlayMode.LOOP_PINGPONG);
        textureRegions.clear();

        //анимация бега вправо
        texture = new Texture(Gdx.files.internal("Hero\\Run.png"));
        temp = TextureRegion.split(texture, frameWidth, frameHeight);
        for (int f = 0; f < 6; f++) {
            textureRegions.add(temp[0][f]);
        }
        runRight = new Animation<>(runFrameDuration, textureRegions, Animation.PlayMode.LOOP_PINGPONG);
        textureRegions.clear();

        //анимация стояния влево
        texture = new Texture(Gdx.files.internal("Hero\\IdleLeft.png"));
        temp = TextureRegion.split(texture, frameWidth, frameHeight);
        for (int f = 0; f < 10; f++) {
            textureRegions.add(temp[0][f]);
        }
        idleLeft = new Animation<>(stayFrameDuration, textureRegions, Animation.PlayMode.LOOP_PINGPONG);
        textureRegions.clear();

        // анимация стояния вправо
        texture = new Texture(Gdx.files.internal("Hero\\Idle.png"));
        temp = TextureRegion.split(texture, frameWidth, frameHeight);
        for (int f = 0; f < 10; f++) {
            textureRegions.add(temp[0][f]);
        }
        idleRight = new Animation<>(stayFrameDuration, textureRegions, Animation.PlayMode.LOOP_PINGPONG);
        textureRegions.clear();

        //анимация получения удара
        texture = new Texture(Gdx.files.internal("Hero\\Get Hit.png"));
        temp = TextureRegion.split(texture, frameWidth, frameHeight);
        for (int f = 0; f < 3; f++) {
            textureRegions.add(temp[0][f]);
        }
        getHit = new Animation<>(0.2f, textureRegions, Animation.PlayMode.NORMAL);
        textureRegions.clear();

    }

    public int getDamage() {//метод для нанесения урона врагам
        return currentSword.getDamage() + strength;
    }

    public float getHealthPoint() {// метод чтобы отображать хп бар
        return healthPoint;
    }

    public float getMaxHp() {
        return maxHP;
    }

    public void setHealthPoint(int healthPoint) {// будет вызываться при колизии с врагом
        this.healthPoint = healthPoint;
    }

    public void setAgility(int agility) {// для увеличения ловкости
        this.agility = agility;
    }

    public void setStrength(int strength) {//для увеличения силы
        this.strength = strength;
    }

    public void setIntelligence(int intelligence) {// для увеличения интелекта
        this.intelligence = intelligence;
    }

    public void setSpeed(float speed) {// для увеличеня скорости игрока
        this.speed = speed;
    }

    public void setCurrentSword(Sword currentSword) {// для смены меча
        this.currentSword = currentSword;
    }

    public float getSpeed() {//для перемещения
        return speed;
    }

    public int getAgility() {// пока не придумал
        return agility;
    }

    public int getStrength() {// пока не придумал
        return strength;
    }

    public int getIntelligence() {// пока не придумал
        return intelligence;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {

            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                left = false;
                //particles.setVisible(false);
                particles.elapsedTime = 0;
                setX(getX() + (getSpeed() * delta));
                setAnimation(runRight);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                left = true;
                //particles.setVisible(false);
                particles.elapsedTime = 0;
                setX(getX() - (getSpeed() * delta));
                setAnimation(runLeft);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                setY(getY() + (getSpeed() * delta));
                //particles.setVisible(false);
                particles.elapsedTime = 0;
                if (left)
                    setAnimation(runLeft);
                else
                    setAnimation(runRight);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                setY(getY() - (getSpeed() * delta));
                //particles.setVisible(false);
                particles.elapsedTime = 0;
                if (left)
                    setAnimation(runLeft);
                else
                    setAnimation(runRight);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if(!particles.isVisible())
                    swing.play();
                particles.setVisible(true);
                particles.elapsedTime = 0;
                Gdx.app.log("elapseTime", String.valueOf(particles.elapsedTime));
                if (!particles.animation.isAnimationFinished(particles.elapsedTime)) {
                    if(left) {
                        particles.setLeftView(true);
                        particles.setPosition(getOriginX() - particles.getWidth(), getOriginY() - particles.getHeight() / 2);
                        particles.updateHitBox((getX() + getOriginX()) - particles.getWidth()/2, getY() + getOriginY());
                    }else{
                        particles.setLeftView(false);
                        particles.setPosition(getOriginX(), getOriginY() - particles.getHeight() / 2);
                        particles.updateHitBox((getX() + getOriginX()) + particles.getWidth()/2, getY() + getOriginY());
                    }
                }
            }
        }
        else {
            if(particles.animation.isAnimationFinished(particles.elapsedTime)){
                particles.setVisible(false);
                particles.updateHitBox(4000, 4000);
                particles.elapsedTime = 0;
            }
            if (left)
                setAnimation(idleLeft);
            else
                setAnimation(idleRight);
        }//end else
        updateHitBox(getX() + getWidth() / 2, getY() + getHeight() / 2);
        boundToWorld();
    }//end act
}//end class
