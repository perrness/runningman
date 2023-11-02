package com.perness.runningman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final RunningManGame game;
    private final OrthographicCamera camera;
    private final GameOverScreen gameOverScreen;

    private Rectangle playerRectangle;
    private Array<Rectangle> obstacles;
    private float ground;
    private float velocity = -600;
    private long lastScoreTime = 0;
    private int score = 0;
    private int obstacleSpeed = 400;

    private long lastObstacleTime;
    private boolean canJump = true;

    public GameScreen(final RunningManGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RunningManGame.VIEWPORTWIDTH, RunningManGame.VIEWPORTHEIGHT);

        ground = (float) RunningManGame.VIEWPORTHEIGHT / 3;

        playerRectangle = new Rectangle();
        playerRectangle.x = 10;
        playerRectangle.y = ground;
        playerRectangle.width = 64;
        playerRectangle.height = 64;

        obstacles = new Array<>();
        spawnObstacles();

        gameOverScreen = new GameOverScreen(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.rect(playerRectangle.x, playerRectangle.y, playerRectangle.width, playerRectangle.height);
        for (Rectangle obstacle: obstacles) {
            game.shapeRenderer.setColor(Color.GREEN);
            game.shapeRenderer.rect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }
        game.shapeRenderer.end();

        // Player controller
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canJump) {
            velocity = 900;
            canJump = false;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) || playerRectangle.y > ground + (playerRectangle.height * 2.5)) {
            velocity = -250;
        }
        playerRectangle.y += velocity * Gdx.graphics.getDeltaTime();

        if (playerRectangle.y < ground) {
            playerRectangle.y = ground;
            canJump = true;
        }

        // Score
        if(TimeUtils.timeSinceMillis(lastScoreTime) > 50) {
            score += 1;
            lastScoreTime = TimeUtils.millis();
        }

        // Spawn new obstacles
        if (TimeUtils.millis() - lastObstacleTime > MathUtils.random(1000,4000)) {
            spawnObstacles();
        }

        // Increase obstacle speed based on score
        if (score % 100 == 0) {
            obstacleSpeed += 20;
        }

        // Game logic
        Iterator<Rectangle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Rectangle obstacle = iter.next();
            obstacle.x -= obstacleSpeed * Gdx.graphics.getDeltaTime();
            if (obstacle.x + 32 < 0)
                iter.remove();
            if (obstacle.overlaps(playerRectangle)) {
                gameOverScreen.setScore(score);
                game.setScreen(gameOverScreen);
                score = 0;
            }
        }

        // Score
        game.spriteBatch.begin();
        game.bitmapFont.draw(game.spriteBatch, "Score: " + score, 10, RunningManGame.VIEWPORTHEIGHT - 20);
        game.spriteBatch.end();
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

    private void spawnObstacles() {
        Rectangle obstacle = new Rectangle();
        obstacle.x = RunningManGame.VIEWPORTWIDTH;

        int type = MathUtils.random(0, 8);
        switch (type) {
            case 0:
                obstacle.y = ground;
                obstacle.width = 32;
                break;
            case 1:
                obstacle.y = ground;
                obstacle.width = 64;
                break;
            case 2:
                obstacle.y = ground;
                obstacle.width = 96;
                break;
            case 3:
                obstacle.y = ground + 64;
                obstacle.width = 32;
                break;
            case 4:
                obstacle.y = ground + 64;
                obstacle.width = 64;
                break;
            case 5:
                obstacle.y = ground + 64;
                obstacle.width = 96;
                break;
            case 6:
                obstacle.y = ground + 32;
                obstacle.width = 32;
                break;
            case 7:
                obstacle.y = ground + 32;
                obstacle.width = 64;
                break;
            case 8:
                obstacle.y = ground + 32;
                obstacle.width = 96;
                break;
        }

        if (score < 100) {
            obstacle.width = 32;
        }

        if (MathUtils.random(1, 10) % 2 == 0) {
            obstacle.height = 32;
        } else {
            obstacle.height = 64;
        }

        obstacles.add(obstacle);
        lastObstacleTime = TimeUtils.millis();
    }
}
