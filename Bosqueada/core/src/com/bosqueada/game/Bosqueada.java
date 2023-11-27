package com.bosqueada.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Bosqueada extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chao;
	Texture jacare_textura;
	Texture pedra_textura;
	Texture background2;
	Sprite jacare_fofao;

	// cria o vetor de pedras
	Pedra[] pedras;

	boolean virado_esquerda = true;

	int contador_auxiliar_caminhada = 0;
	int nivel = 1;
	int pedras_quantidade = nivel * 25;

	// Defina a cor azul bebê (um tom suave de azul)
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {

		batch = new SpriteBatch();

		// textura do personagem
		jacare_textura = new Texture("jacare.png");

		// textura da pedra
		pedra_textura = new Texture("pedra.png");

		// textura das arvores de fundo
		chao = new Texture("background2.jpg");

		// criando o jacare
		jacare_fofao = new Sprite(jacare_textura);

		// definindo a posicao inicial
		jacare_fofao.setPosition(0, Gdx.graphics.getWidth()/10);

		pedras = new Pedra[pedras_quantidade];

		// Inicialize as pedras com diferentes posições e velocidades
        for (int i = 0; i < pedras_quantidade; i++) {
            float x = MathUtils.random(0, 1280);
			float y = Gdx.graphics.getHeight() + MathUtils.random(640, 2000);
            float velocidade = MathUtils.random(15, 30);

            pedras[i] = new Pedra(pedra_textura, x, y, velocidade);
        }

	}

	@Override
	public void render () {
		ScreenUtils.clear(babyBlue);
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		moveJacare();
		
		//////////////////////////////////////////////////////////////////////////////////////

		// checa se o jacaras passou do ponto pra direita e bota ele na esquerda
		if (jacare_fofao.getX() > 1240){
			jacare_fofao.setPosition(-75, Gdx.graphics.getWidth()/10);
		}

		// checa se o jacas passou do ponto pra esquerda e bota ele na direita
		if (jacare_fofao.getX() < -75){
			jacare_fofao.setPosition( 1240, Gdx.graphics.getWidth()/10);
		}

		///////////////////////////////////////////////////////////////////////////////////////

		batch.begin();

		batch.draw(chao, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		jacare_fofao.draw(batch);

		// Atualiza e desenha as pedras
        for (Pedra pedra : pedras) {
            pedra.atualizar(deltaTime);
            pedra.desenhar(batch);
        }

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		chao.dispose();
		jacare_fofao.getTexture().dispose();
		pedra_textura.dispose();
	}


	// movimento do jaca
	private void moveJacare(){

		boolean caminhando = false;

		// Movimenta a sprite do personagem para a esquerda quando a tecla seta esquerda é pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            jacare_fofao.setX(jacare_fofao.getX() - 10);
			caminhando = true;

			// se estiver virado pra direita, vira pra esquerda
			if(virado_esquerda == false){
				jacare_fofao.flip(true, false);
				virado_esquerda = true;
			}

			// movimento da caminhada do jaca
			if(caminhando == true){
				if(contador_auxiliar_caminhada < 4){
					jacare_fofao.setY(jacare_fofao.getY() - 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada >= 4){
					jacare_fofao.setY(jacare_fofao.getY() + 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada == 8){
					contador_auxiliar_caminhada = 0;
				}
			}

        }

        // Movimenta a sprite do personagem para a direita quando a tecla seta direita é pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            jacare_fofao.setX(jacare_fofao.getX() + 10);
			caminhando = true;
			
			// se estiver virado pra esquerda, vira pra direita
			if(virado_esquerda == true){
				jacare_fofao.flip(true, false);
				virado_esquerda = false;
			}

			// movimento da caminhada do jaca
			if(caminhando == true){
				if(contador_auxiliar_caminhada < 4){
					jacare_fofao.setY(jacare_fofao.getY() - 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada >= 4){
					jacare_fofao.setY(jacare_fofao.getY() + 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada == 8){
					contador_auxiliar_caminhada = 0;
				}
			}
        }
	}


}
