package com.perness.runningman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private final RunningManGame game;
    private OrthographicCamera camera;

    public MainMenuScreen(final RunningManGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RunningManGame.VIEWPORTWIDTH, RunningManGame.VIEWPORTHEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.spriteBatch.setProjectionMatrix(camera.combined);

        game.spriteBatch.begin();
        game.bitmapFont.draw(game.spriteBatch, "Welcome to Running Man!! ", 100, 150);
        game.bitmapFont.draw(game.spriteBatch, "Tap anywhere or press space to begin!", 100, 100);
        game.spriteBatch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            GameScreen gameScreen = new GameScreen(game);
            game.setScreen(gameScreen);
            game.gameScreen = gameScreen;
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
