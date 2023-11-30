package com.bosqueada.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pedra {
    private Sprite pedra_sprite;
    private float velocidade;
    
    // Cria a pedra com a sua velocidade
    public Pedra(Texture textura, float x, float y, float velocidade) {
        pedra_sprite = new Sprite(textura);
        pedra_sprite.setPosition(x, y);
        this.velocidade = velocidade;
    }

    // pega o sprite pra fazer a colisao
    public Sprite getSprite(){
        return pedra_sprite;
    }

    public void atualizar(float deltaTime) {
        // Atualiza a posicao da pedra com base na velocidade
        float novaPosicaoY = pedra_sprite.getY() - velocidade * deltaTime;
        pedra_sprite.setY(novaPosicaoY);
    }

    // Desenha a pedra no batch
    public void desenhar(SpriteBatch batch) {
        pedra_sprite.draw(batch);
    }
}
