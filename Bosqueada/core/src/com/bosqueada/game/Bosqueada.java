package com.bosqueada.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Bosqueada extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chao;

	// Defina a cor azul bebê (um tom suave de azul)
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		chao = new Texture("chao_floresta_recortado.png");


	}

	@Override
	public void render () {
		ScreenUtils.clear(babyBlue);

		batch.begin();

		batch.draw(chao, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//cenario1.dispose();
		chao.dispose();
	}


}
