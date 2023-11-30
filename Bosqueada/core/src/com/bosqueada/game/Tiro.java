package com.bosqueada.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tiro {
    private Texture textura;
    private Vector2 position;
    private Vector2 velocity;

    public Tiro(Texture textura) {
        this.textura = textura;
        position = new Vector2();
        velocity = new Vector2(0, 300); // Ajuste a velocidade conforme necessário
    }

    public void atirar(float x, float y) {
        position.set(x - textura.getWidth() / 2, y); // Centraliza o tiro em relação ao personagem
    }

    public void atualiza(float deltaTime) {
        // Atualiza a posição do tiro se estiver em movimento
        if (position.y > 0) {
            position.add(velocity.cpy().scl(deltaTime));
        }
    }

    public void desenha(SpriteBatch batch) {
        batch.draw(textura, position.x, position.y);
    }

    public Vector2 pegaPosicao() {
        return position;
    }

    public Rectangle getBoundingRectangle(){
        return new Rectangle(position.x, position.y, textura.getWidth(), textura.getHeight());
    }
}