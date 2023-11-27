package com.bosqueada.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Bosqueada extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chao;
	Texture jacare_testura;
	Texture imgfundo2;
	Sprite jacare_fofao;
	float posX, posY;


	// Defina a cor azul bebê (um tom suave de azul)
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {

		batch = new SpriteBatch();

		// textura do personagem
		jacare_testura = new Texture("jacare.png");

		// textura das arvores de fundo
		chao = new Texture("background2.jpg");

		// criando o jacare fofao com a textura
		jacare_fofao = new Sprite(jacare_testura);

		// definindo a posicao inicial
		jacare_fofao.setPosition(20, 0);

	}

	@Override
	public void render () {
		ScreenUtils.clear(babyBlue);

		// Movimenta a sprite do personagem para a esquerda quando a tecla seta esquerda é pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            jacare_fofao.setX(jacare_fofao.getX() - 20);
        }
        // Movimenta a sprite do personagem para a direita quando a tecla seta direita é pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            jacare_fofao.setX(jacare_fofao.getX() + 20);
        }

		batch.begin();

		batch.draw(chao, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		jacare_fofao.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		chao.dispose();
		jacare_fofao.getTexture().dispose();
	}


}
