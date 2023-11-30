package com.bosqueada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class Tiro {
    private Texture textura; // Textura do tiro
    private Vector2 posicao; // Posição do tiro
    private Vector2 velocidade; // Velocidade do tiro
    private Vector2 armaPosicao;
    private float velocidadeMaxima;

    public Tiro(Texture textura, float jacaX, float jacaY) {
        this.textura = textura;
        posicao = new Vector2(jacaX, jacaY); // Inicializa a posição como (0, 0) por padrão
        velocidadeMaxima = 1000;
        velocidade = new Vector2(0, velocidadeMaxima); // Velocidade vertical do tiro (ajuste conforme necessário)
        
    }

    // atira onde o mouse esta
    public void atirarMouse(float mouseX, float mouseY) {
            // Obtém a posição atual do tiro
            float tiroX = posicao.x + textura.getWidth() / 2;
            float tiroY = posicao.y + textura.getHeight() / 2;
    
            // Calcula o ângulo entre a posição do mouse e a posição atual do tiro
            float angle = MathUtils.atan2(mouseY - tiroY, mouseX - tiroX);
    
            // Define a velocidade com base no ângulo calculado
            velocidade.set(MathUtils.cos(angle) * velocidadeMaxima, MathUtils.sin(angle) * velocidadeMaxima);   
    }

    // Método para atualizar a posição do tiro com base no deltaTime
    public void atualiza(float deltaTime) {
        // Atualiza a posição do tiro se estiver em movimento (acima da tela)
        if (posicao.y > 0) {
            // Adiciona a velocidade multiplicada pelo tempo (deltaTime) à posição
            posicao.add(velocidade.cpy().scl(deltaTime));
        }
    }

    // Método para desenhar o tiro na tela usando o SpriteBatch
    public void desenha(SpriteBatch batch) {
        batch.draw(textura, posicao.x, posicao.y);
    }

    // Método para obter a posição atual do tiro
    public Vector2 pegaPosicao() {
        return posicao;
    }

    // Método para obter o retângulo de colisão do tiro (bounding rectangle)
    public Rectangle getBoundingRectangle() {
        // Retorna um retângulo com base na posição e dimensões da textura do tiro
        return new Rectangle(posicao.x, posicao.y, textura.getWidth(), textura.getHeight());
    }
}
