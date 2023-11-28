package com.bosqueada.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

// classe para o controle das pedras
public class Pedra {
	private Sprite sprite;
	private float x, y;
	private float velocidade;

	public Pedra(Texture texture, float x, float y, float velocidade) {
		sprite = new Sprite(texture);
		this.x = x;
		this.y = y;
		this.velocidade = velocidade;
	}

	public void atualizar(float deltaTime) {
		// Atualiza a posição da pedra com base na velocidade
		y -= velocidade * deltaTime;
	}

	public void desenhar(SpriteBatch batch) {
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

}