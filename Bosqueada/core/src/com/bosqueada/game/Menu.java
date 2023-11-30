package com.bosqueada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu {
    private Texture fundo_textura;
    private SpriteBatch batch;
    private boolean jogoIniciado;

    public Menu() {
        fundo_textura = new Texture("texturas/menu_fundo.jpg");
        batch = new SpriteBatch();
        jogoIniciado = false;
    }

    public void render() {
        batch.begin();
        batch.draw(fundo_textura, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jogoIniciado = true;
        }
    }

    public boolean isJogoIniciado() {
        return jogoIniciado;
    }

    public void dispose() {
        fundo_textura.dispose();
        batch.dispose();
    }
}
