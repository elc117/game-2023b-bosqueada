package com.bosqueada.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tiro {
    private Texture textura; // Textura do tiro
    private Vector2 position; // Posição do tiro
    private Vector2 velocity; // Velocidade do tiro

    public Tiro(Texture textura) {
        this.textura = textura;
        position = new Vector2(); // Inicializa a posição como (0, 0) por padrão
        velocity = new Vector2(0, 1000); // Velocidade vertical do tiro (ajuste conforme necessário)
    }

    // Método para disparar o tiro a partir de uma posição específica (x, y)
    public void atirar(float x, float y) {
        // Define a posição do tiro centralizada em relação ao personagem
        position.set(x - textura.getWidth() / 2, y);
    }

    // Método para atualizar a posição do tiro com base no deltaTime
    public void atualiza(float deltaTime) {
        // Atualiza a posição do tiro se estiver em movimento (acima da tela)
        if (position.y > 0) {
            // Adiciona a velocidade multiplicada pelo tempo (deltaTime) à posição
            position.add(velocity.cpy().scl(deltaTime));
        }
    }

    // Método para desenhar o tiro na tela usando o SpriteBatch
    public void desenha(SpriteBatch batch) {
        batch.draw(textura, position.x, position.y);
    }

    // Método para obter a posição atual do tiro
    public Vector2 pegaPosicao() {
        return position;
    }

    // Método para obter o retângulo de colisão do tiro (bounding rectangle)
    public Rectangle getBoundingRectangle() {
        // Retorna um retângulo com base na posição e dimensões da textura do tiro
        return new Rectangle(position.x, position.y, textura.getWidth(), textura.getHeight());
    }
}
