package com.perness.runningman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class RunningManGame extends Game {
	public static final int VIEWPORTWIDTH = 800;
	public static final int VIEWPORTHEIGHT = 480;

	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont bitmapFont;
	public GameScreen gameScreen;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		bitmapFont = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		bitmapFont.dispose();
		gameScreen.dispose();
	}
}
