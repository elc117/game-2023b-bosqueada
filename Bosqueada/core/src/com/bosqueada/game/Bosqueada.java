package com.bosqueada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Bosqueada extends ApplicationAdapter {
	SpriteBatch batch;
	Texture cenario1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cenario1 = new Texture("cenario1-pixelicious.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		batch.draw(cenario1, 0, 0);
		

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cenario1.dispose();
	}


}
