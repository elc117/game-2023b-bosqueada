package com.bosqueada.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pedra {
    private Sprite sprite;
    private float velocidade;
    
    // Cria a pedra com a sua velocidade
    public Pedra(Texture textura, float x, float y, float velocidade) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
        this.velocidade = velocidade;
    }

    public void atualizar(float deltaTime) {

        // Atualiza a posição da pedra com base na velocidade
        float novaPosicaoY = sprite.getY() - velocidade * deltaTime;
        sprite.setY(novaPosicaoY);
    }

    // Desenha a pedra no batch
    public void desenhar(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
