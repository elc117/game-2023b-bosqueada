package com.bosqueada.game;

import java.util.Vector;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ak47 {
    private Texture textura;
    private Sprite arma_sprite;

    public Ak47(Texture textura, Sprite arma_sprite, float jacaX, float jacaY){
        this.textura = textura;
        arma_sprite = new Sprite(textura);
        arma_sprite.setPosition(jacaX, jacaY);
    }

    public void atualizaArma(float jacareX, float jacareY, Sprite arma_sprite) {
        // Ajusta a posição da arma de acordo com a posição do jaca
        arma_sprite.setX(jacareX);
        arma_sprite.setY(jacareY);
    }

    // Método para desenhar a ak na tela usando o SpriteBatch
    public void desenha(SpriteBatch batch, Sprite arma_sprite) {
        arma_sprite.draw(batch);
    }
}
