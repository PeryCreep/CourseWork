package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Hero;
import com.mygdx.game.Actors.Skeleton;
import com.mygdx.game.BaseGame;
import com.mygdx.game.HUD.InfoBar;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.States.States;

public class MainLevelScreen extends BaseScreen {
    public static Hero hero;
    private States state = States.RUNNING;
    public static int score;

    private Label scoreLabel;

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
        score = 0;
        scoreLabel = new Label("" + score, MyGdxGame.labelStyle);
        scoreLabel.setPosition(160, 615);
        scoreLabel.setFontScale(4);

        uiStage.addActor(scoreLabel);


        //создаем героя.
        hero = new Hero(400, 150, mainStage);
        hero.setHitBox(hero.getWidth(), hero.getHeight(), hero.getOriginX(), hero.getOriginY());
        new Skeleton(MathUtils.random(mainStage.getWidth()), MathUtils.random(mainStage.getHeight()), mainStage);
        new Skeleton(MathUtils.random(mainStage.getWidth()), MathUtils.random(mainStage.getHeight()), mainStage);
        new Skeleton(MathUtils.random(mainStage.getWidth()), MathUtils.random(mainStage.getHeight()), mainStage);

        //button Style init

        ButtonStyle buttonStyle = new ButtonStyle();

        Texture buttonTexture = new Texture(Gdx.files.internal("HUD\\pauseButton.png"));
        buttonStyle.up = new TextureRegionDrawable(buttonTexture);

        Button pauseButton = new Button(buttonStyle);
        pauseButton.setSize(200, 200);
        //end button style


        //pauseButton listener
        pauseButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                state = States.PAUSED;
                return false;

            }
        });
        //end listener


        tableHUD.pad(5);
        tableHUD.add(new InfoBar(15, 15, uiStage)).align(Align.topLeft);
        tableHUD.add().expandX();
        tableHUD.add(pauseButton).align(Align.topRight).size(70, 70);
        tableHUD.row().expandY();
        tableHUD.add();
    }

    @Override
    public void update() {
        if (hero.getHealthPoint() <= 0) {
            game.setActiveScreen(new GameOver(game));
        }
        hero.setCameraAtActor();
        scoreLabel.setText("" + score);

    }

    @Override
    public void render(float delta) {
        switch (state) {
            case RUNNING:{
                mainStage.act();
                uiStage.act();

                update();

                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                mainStage.draw();
                uiStage.draw();
                break;
            }
            case PAUSED: {
                if(Gdx.input.isKeyPressed(Input.Keys.R))
                    state = States.RUNNING;

                mainStage.draw();
                uiStage.draw();

                break;
            }
            case STOPPED:{
                Gdx.app.log("Stop" , "The game was stopped");
                break;
            }
        }
    }
}
