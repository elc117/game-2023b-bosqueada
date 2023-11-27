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
	Texture jacare_textura;
	Texture background2;
	Texture pedraTextura;
	Sprite jacare_fofao;
	Sprite pedra;


	// Defina a cor azul bebê (um tom suave de azul)
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {

		batch = new SpriteBatch();

		// textura do personagem
		jacare_textura = new Texture("jacare.png");

		// textura das arvores de fundo
		chao = new Texture("background2.jpg");

		// textura pedra
		pedraTextura = new Texture("pedra.png");

		// criando o jacare fofao com a textura
		jacare_fofao = new Sprite(jacare_textura);

		// criando pedra
		pedra = new Sprite(pedraTextura);

		// posicao inicial pedra para teste tamanho
		pedra.setPosition(0, 0);

		// definindo a posicao inicial
		jacare_fofao.setPosition(0, 20);

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
		batch.draw(pedra, 20, 600);

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
