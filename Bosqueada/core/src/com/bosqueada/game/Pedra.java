package com.bosqueada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Pedra {
    private Sprite pedra_sprite;
    private float velocidade;
    private float x;
    private float y;

    // Cria a pedra com a sua velocidade
    public Pedra(Texture textura, float x, float y, float velocidade) {
        this.x = x;
        this.y = y;
        pedra_sprite = new Sprite(textura);
        pedra_sprite.setPosition(x, y);
        this.velocidade = velocidade;
    }

    // retorna cordenadas da pedra
    public float getX(){
        return pedra_sprite.getX();
    }

    public float getY(){
        return pedra_sprite.getY();
    }

    // pega o sprite pra fazer a colisao
    public Sprite getSprite(){
        return pedra_sprite;
    }

    // pega o hitbox da pedra
    public Rectangle getBoundingRectangle(){
        return pedra_sprite.getBoundingRectangle();
    }

    public void atualizar(float deltaTime) {
        // Atualiza a posicao da pedra com base na velocidade
        float novaPosicaoY = pedra_sprite.getY() - velocidade * deltaTime;
        pedra_sprite.setY(novaPosicaoY);
    }

    public void reiniciaPedra(){
        pedra_sprite.setY(Gdx.graphics.getHeight() + MathUtils.random( 640, 20000));
    }

    // Desenha a pedra no batch
    public void desenhar(SpriteBatch batch) {
        pedra_sprite.draw(batch);
    }

}
