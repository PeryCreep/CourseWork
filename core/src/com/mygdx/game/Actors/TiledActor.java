package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiledActor extends Actor {

    public static int windowWidth = 1280, windowHeight = 720;

    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;

    public TiledActor(String filename, Stage stage) {
        tiledMap = new TmxMapLoader().load(filename);

        int tileWidth = (int) tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int) tiledMap.getProperties().get("tileheight");
        int countTilesHor = (int) tiledMap.getProperties().get("width");
        int countTileVert = (int) tiledMap.getProperties().get("height");

        int mapWidth = tileWidth * countTilesHor;
        int mapHeight = tileHeight * countTileVert;

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, windowWidth, windowHeight);
        camera.update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Camera mainCamera = getStage().getCamera();
        camera.position.x = mainCamera.position.x;
        camera.position.y = mainCamera.position.y;
        camera.update();
        tiledMapRenderer.setView(camera);

        batch.end();
        tiledMapRenderer.render();
        batch.begin();
    }
}
