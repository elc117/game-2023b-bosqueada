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
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class Bosqueada extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chao;
	Texture jacare_textura;
	Texture pedra_textura;
	Texture background2;
	Sprite jacare;
	FrameBuffer frameBuffer;
	Texture texturaPausada;

	// cria o vetor de pedras
	Pedra[] pedras;

	boolean virado_esquerda = true;
	boolean pause = false;

	int contador_auxiliar_caminhada = 0;
	int nivel = 1;
	int pedras_quantidade = nivel * 50;

	// Defina a cor azul bebê (um tom suave de azul)
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {

		batch = new SpriteBatch();

		// Criando um FrameBuffer com o tamanho da tela
        frameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		// textura do personagem
		jacare_textura = new Texture("texturas/jacare.png");

		// textura da pedra
		pedra_textura = new Texture("texturas/pedra.png");

		// textura das arvores de fundo
		chao = new Texture("texturas/background2.jpg");

		// criando o jacare
		jacare = new Sprite(jacare_textura);

		// definindo a posicao inicial
		jacare.setPosition(0, Gdx.graphics.getWidth()/11);

		pedras = new Pedra[pedras_quantidade];

		// Inicialize as pedras com diferentes posições e velocidades
        for (int i = 0; i < pedras_quantidade; i++) {
            float x = MathUtils.random(0, 1280);
			float y = Gdx.graphics.getHeight() + MathUtils.random(640, 5000);
            float velocidade = MathUtils.random(50, 200);

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
		if (jacare.getX() > 1240){
			jacare.setPosition(-75, Gdx.graphics.getWidth()/11);
		}

		// checa se o jacas passou do ponto pra esquerda e bota ele na direita
		if (jacare.getX() < -75){
			jacare.setPosition( 1240, Gdx.graphics.getWidth()/11);
		}

		///////////////////////////////////////////////////////////////////////////////////////

		batch.begin();

		// desenha caso nao esteja pausado
		if(!pause){
			batch.draw(chao, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			jacare.draw(batch);

			// Atualiza e desenha as pedras
        	for (Pedra pedra : pedras) {
        	    pedra.atualizar(deltaTime);
        	    pedra.desenhar(batch);

				// Verifica a colisão entre o jacaré e cada uma das pedras
				if (detectarColisao(jacare, pedra.getSprite())) {
					// o que acontece quando colide
					// nesse caso, reseta a posicao do jaca
					jacare.setPosition(0, Gdx.graphics.getWidth()/11);
					// pausa o game
					pause = true;
				}
        	}
		// se estiver pausado, salva o estado atual no buffer
		}else{
			frameBuffer.begin();
			frameBuffer.end();
			// O que foi desenhado até este ponto será salvo no FrameBuffer
			texturaPausada = frameBuffer.getColorBufferTexture();
		}

		// despausa quando aperta A
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			pause = false;
		}

		batch.end();

		// se não estiver pausado, desenha a textura salva na tela
        if (texturaPausada != null && pause) {
            batch.begin();
            batch.draw(texturaPausada, 0, 0);
            batch.end();
        }

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		chao.dispose();
		jacare.getTexture().dispose();
		pedra_textura.dispose();
		frameBuffer.dispose();
        if (texturaPausada != null) {
            texturaPausada.dispose();
        }
	}


	// movimento do jaca
	private void moveJacare(){

		boolean caminhando = false;

		// Movimenta o personagem para a esquerda quando a tecla seta esquerda é pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            jacare.setX(jacare.getX() - 10);
			caminhando = true;

			// se estiver virado pra direita, vira pra esquerda
			if(virado_esquerda == false){
				jacare.flip(true, false);
				virado_esquerda = true;
			}

			// movimento da caminhada do jaca
			if(caminhando == true){
				if(contador_auxiliar_caminhada < 4){
					jacare.setY(jacare.getY() - 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada >= 4){
					jacare.setY(jacare.getY() + 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada == 8){
					contador_auxiliar_caminhada = 0;
				}
			}

        }

        // Movimenta o personagem para a direita quando a tecla seta direita é pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            jacare.setX(jacare.getX() + 10);
			caminhando = true;
			
			// se estiver virado pra esquerda, vira pra direita
			if(virado_esquerda == true){
				jacare.flip(true, false);
				virado_esquerda = false;
			}

			// movimento da caminhada do jaca
			if(caminhando == true){
				if(contador_auxiliar_caminhada < 4){
					jacare.setY(jacare.getY() - 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada >= 4){
					jacare.setY(jacare.getY() + 1);
					contador_auxiliar_caminhada += 1;
				}
				if(contador_auxiliar_caminhada == 8){
					contador_auxiliar_caminhada = 0;
				}
			}
        }
	}

	// detecta colisoes entre duas sprites
	private boolean detectarColisao(Sprite sprite1, Sprite sprite2) {
		return sprite1.getBoundingRectangle().overlaps(sprite2.getBoundingRectangle());
	}
}
