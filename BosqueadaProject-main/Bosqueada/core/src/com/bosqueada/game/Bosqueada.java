package com.bosqueada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Bosqueada extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img_fundo;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img_fundo = new Texture("cenario1.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img_fundo, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img_fundo.dispose();
	}
}
