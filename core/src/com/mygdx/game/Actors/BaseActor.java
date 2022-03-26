package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.sound.sampled.Line;
import java.util.ArrayList;

public class BaseActor extends Group {

    public Animation<TextureRegion> animation;// нужно чтобы хранить кадры/кадр анимации
    public float elapsedTime;// нужно для получения анимации
    private boolean animationPaused;// флаг паузы анимации
    public static Rectangle worldBound;
    public Rectangle hitBox;


    public BaseActor(float x, float y, Stage s) {
        super();

        setPosition(x, y);// устанавливаем позицию
        s.addActor(this);// добавляю актера на сцену

        //Стандартная инициализация переменных
        animation = null;
        elapsedTime = 0;
        animationPaused = false;

    }


    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;// присавем объекту анимацию
        TextureRegion tr = animation.getKeyFrame(0);//получаем самый первый кадр
        float w = tr.getRegionWidth();//получаем ширину первого кадра
        float h = tr.getRegionHeight();// получаем высоту первого кадра
        setSize(w, h);
        setOrigin(w / 2, h / 2);// устанавливаем центральную точку
    }

    public void setAnimationPaused(boolean paused) {
        animationPaused = paused;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!animationPaused) {
            elapsedTime += delta;// автоматически при каждой отрисовки увеличиваем elapsedTime
        }
    }

    public boolean isAnimationPaused() {
        return animationPaused;
    }

    public boolean isAnimFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public Animation<TextureRegion> setAnimationFromFile(String[] fileNames, float frameDuration, boolean loop) {//загружаем только один кадр
        Array<TextureRegion> textureRegionArray = new Array<>();

        for (String file : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(file));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegionArray.add(new TextureRegion(texture));
        }
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureRegionArray);
        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> setAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop) {

        Texture texture = new Texture(Gdx.files.internal(fileName));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureRegionArray = new Array<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                textureRegionArray.add(temp[row][col]);
            }
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureRegionArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);


        if (animation == null) {
            setAnimation(anim);
        }
        return anim;
    }

    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActor actor) {
        centerAtPosition(actor.getX() + actor.getWidth() / 2, actor.getY() + actor.getHeight() / 2);
    }

    public void setCameraAtActor() {
        Camera cam = getStage().getCamera();
        Viewport viewport = getStage().getViewport();

        cam.position.set(getX() + getOriginX(), getY() + getOriginY(), 0);
        cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth / 2, worldBound.width - cam.viewportWidth / 2);
        cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight / 2, worldBound.height - cam.viewportHeight / 2);

    }

    public static void setWorldBound(float w, float h) {
        worldBound = new Rectangle(0, 0, w, h);
    }

    public static void setWorldBound(BaseActor actor) {
        setWorldBound(actor.getWidth(), actor.getHeight());
    }


    public Animation<TextureRegion> setTexture(String fileName) {
        String[] files = new String[1];
        files[0] = fileName;
        return setAnimationFromFile(files, 1, true);
    }

    public boolean overlaps(BaseActor actor) {
        return hitBox.overlaps(actor.getHitBox());
    }

    public boolean boundToWorld() {
        if (getX() < 0) {
            setX(0);
            return true;
        }

        if (getX() + getWidth() > worldBound.getWidth()) {
            setX(worldBound.width - getWidth());
            return true;
        }

        if (getY() < 0) {
            setY(0);
            return true;
        }

        if (getY() + getHeight() > worldBound.getHeight()) {
            setY(worldBound.height - getHeight());
            return true;
        }

        return false;
    }

    public void setHitBox(float w, float h, float originX, float originY) {
        hitBox = new Rectangle(0, 0, w, h);
        hitBox.setCenter(originX, originY);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void updateHitBox(float x, float y) {
        hitBox.setCenter(x, y);
    }

    public static ArrayList<BaseActor> getListActor(Stage s,String name){//возвращаем все элемента на определенной сцене с определенным именем
        ArrayList<BaseActor> array = new ArrayList<>();
        Class theClass = null;

        try{
            theClass = Class.forName(name);
        }catch (Exception e){e.printStackTrace();}
        for(Actor actor: s.getActors()){
            if(theClass.isInstance(actor)){
                array.add((BaseActor) actor);
            }
        }
        return array;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
