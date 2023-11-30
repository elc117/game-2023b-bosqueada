package com.bosqueada.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Text;

public class Bosqueada extends ApplicationAdapter {

	SpriteBatch batch;
	Texture chao;
	Texture jacare_textura;
	Texture pedra_textura;
	Texture background2;
	Sprite jacare;
	Sprite arma_sprite;
	FrameBuffer frameBuffer;
	Texture texturaPausada;
	FreeTypeFontGenerator gerador;
    FreeTypeFontGenerator.FreeTypeFontParameter parametro;
	Texture caixaPerguntas_textura;
	
	Texture arma_textura;
	Ak47 arma;

	Texture tiro_textura;
	Tiro tiro;
	List<Tiro> tiros;
	
	Texture botao_sair;
	Texture botao_alternativa;
	Texture botao_alternativa_errada;
	Texture botao_alternativa_exata;

	BitmapFont fonte_pontos;
	int pontos = 0;
	
	// cria o vetor de pedras
	Pedra[] pedras;

	CaixaPerguntas pergunta;

	boolean arma_virada_esquerda = true;
	boolean virado_esquerda = true;
	boolean pause = false;
	boolean menu_inicio = true;
	boolean menu_pergunta = true;

	int contador_auxiliar_caminhada = 0;
	int nivel = 1;
	int pedras_quantidade = nivel * 50;

	float disparoX, disparoY;

	// cores
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {

		// batch
		batch = new SpriteBatch();

		// Criando um FrameBuffer com o tamanho da tela
        frameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		// textura botao sair
		botao_sair = new Texture("texturas/botao_sair.png");

		// textura botao alternativa
		botao_alternativa = new Texture("texturas/botao_alternativa.png");
		botao_alternativa_exata = new Texture("texturas/botao_alternativa_exata.png");
		botao_alternativa_errada = new Texture("texturas/botao_alternativa_errada.png");

		// textura do personagem
		jacare_textura = new Texture("texturas/jacare.png");

		// textura da pedra
		pedra_textura = new Texture("texturas/pedra.png");

		// textura das arvores de fundo
		chao = new Texture("texturas/background2.jpg");

		fonte_pontos = new BitmapFont();

		// criando o jacare
		jacare = new Sprite(jacare_textura);

		// definindo a posicao inicial
		jacare.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/6 + 20);

		arma_textura = new Texture("texturas/ak.png");

		arma = new Ak47(arma_textura, arma_sprite, jacare.getX(), jacare.getY());

		arma_sprite = new Sprite(arma_textura);

		tiro_textura = new Texture("texturas/tiro.png");

		tiro = new Tiro(tiro_textura, jacare.getX(), jacare.getY());

		tiros = new ArrayList<>();

		tiro_textura = new Texture("texturas/tiro.png");

		tiro = new Tiro(tiro_textura, jacare.getX(), jacare.getY());

		tiros = new ArrayList<>();

		pedras = new Pedra[pedras_quantidade];

		caixaPerguntas_textura = new Texture("texturas/fundoDaPergunta.jpg");
		gerador = new FreeTypeFontGenerator(Gdx.files.internal("fontes/fonte_questoes.ttf"));
        parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();
		pergunta = new CaixaPerguntas(gerador, parametro);

		// Inicialize as pedras com diferentes posicoes e velocidades
        for (int i = 0; i < pedras_quantidade; i++) {
            float x = MathUtils.random(0, 1280);
			float y = Gdx.graphics.getHeight() + MathUtils.random(640, 5000);
            float velocidade = MathUtils.random(200, 600);

            pedras[i] = new Pedra(pedra_textura, x, y, velocidade);
        }

		// le o arquivo
		pergunta.le_arquivo();

	}

	@Override
	public void render() {
		ScreenUtils.clear(babyBlue);

		// pega a cordenada do mouse dentro do loop do game
		float mouseX = Gdx.input.getX();
		// Inverte a coordenada y para ficar de acordo com a libgdx
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); 

		// pega o deltaTime
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// funcao de movimentos do jaca
		moveJacare();
		
		//////////////////////////////////////////////////////////////////////////////////////

		// checa se o jacaras passou do ponto pra direita e bota ele na esquerda
		if (jacare.getX() > Gdx.graphics.getWidth() - jacare.getWidth()){
			jacare.setPosition( -20, Gdx.graphics.getHeight()/6 + 20);
		}

		// checa se o jacas passou do ponto pra esquerda e bota ele na direita
		if (jacare.getX() < -20){
			jacare.setPosition( Gdx.graphics.getWidth() - jacare.getWidth(), Gdx.graphics.getHeight()/6 + 20);
		}

		///////////////////////////////////////////////////////////////////////////////////////

		batch.begin();

		// desenha caso nao esteja pausado
		if(!pause){
			// fundo
			batch.draw(chao, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			// jacaras
			jacare.draw(batch);

			// contador de pontos
			fonte_pontos.getData().setScale(2);

			// Cria um objeto GlyphLayout para calcular as dimensões do texto
			GlyphLayout layout = new GlyphLayout(fonte_pontos, "Pontos: " + pontos);

			// Obtém a largura e altura real do texto com base na fonte e conteúdo
			float textWidth = layout.width;
			float textHeight = layout.height;

			// Calcula a posição X centralizada na tela
			float x = (Gdx.graphics.getWidth() - textWidth) / 2;

			// Calcula a posição Y na parte superior da tela com um pequeno espaço para baixo (20 pixels)
			float y = Gdx.graphics.getHeight() - textHeight - 20;

			// Desenha o texto "Pontos: " seguido pelo valor da variável 'pontos' na tela
			fonte_pontos.draw(batch, "Pontos: " + pontos, x, y);

			
			// Atualiza e desenha as pedras
        	for (Pedra pedra : pedras) {
        	    pedra.atualizar(deltaTime);
        	    pedra.desenhar(batch);

				// Verifica a colisao entre o jacare e cada uma das pedras
				if (detectarColisao(jacare, pedra.getSprite())) {
					// o que acontece quando colide
					// nesse caso, reseta a posicao do jaca
					jacare.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/6 + 20);
					// pausa o game
					pause = true;
				}
        	}

			// botao sair
			batch.draw(botao_sair, Gdx.graphics.getWidth() - botao_sair.getWidth(), Gdx.graphics.getHeight() - botao_sair.getHeight());
			
			// Verifica se o botao_sair foi clicado com o botao esquerdo do mouse
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&
            	mouseX >= Gdx.graphics.getWidth() - botao_sair.getWidth() &&
            	mouseX <= Gdx.graphics.getWidth() &&
            	mouseY >= Gdx.graphics.getHeight() - botao_sair.getHeight() &&
            	mouseY <= Gdx.graphics.getHeight()) {
				// Fecha o jogo
        		Gdx.app.exit(); 
    		}

			

			// desenha e atualiza tiro e pedra
			atualiza();
			desenha();
			
			// se arma nao direita, arma direita
			if(mouseX > jacare.getX() &&
			   arma_virada_esquerda &&
			   Gdx.input.isButtonPressed(Input.Buttons.LEFT) ){
				arma_sprite.flip(true,false);
				arma_virada_esquerda = false;
			}
			// se arma nao esquerda, arma esquerda
			if(mouseX < jacare.getX() &&
			   !arma_virada_esquerda &&
			   Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				arma_sprite.flip(true,false);
				arma_virada_esquerda = true;
			   }
			// atualiza arma e tiro pra esquerda
			if(arma_virada_esquerda){
				arma.atualizaArma(jacare.getX() + jacare.getWidth()/4 - 20, jacare.getY() + jacare.getHeight()/2 + 20, arma_sprite);
				disparoX = jacare.getX() + jacare.getWidth()/4 - 20;
				disparoY = jacare.getY() + jacare.getHeight()/2 + 20;

				// atualiza arma e tiro pra direita
			}else{
				arma.atualizaArma(jacare.getX() + jacare.getWidth()/4 + 30, jacare.getY() + jacare.getHeight()/2 + 20, arma_sprite);
				disparoX = jacare.getX() + jacare.getWidth()/4 + 60;
				disparoY = jacare.getY() + jacare.getHeight()/2 + 15;
			}

			handleInput(mouseX, mouseY, disparoX, disparoY);

			arma.desenha(batch, arma_sprite);

		// se estiver pausado, salva o estado atual no buffer
		}else if(pause && menu_pergunta){
			// fundo pergunta
			// Define a opacidade para 50%
			batch.setColor(1, 1, 1, 0.5f); 
			batch.draw(caixaPerguntas_textura, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			// botao alternativa
			batch.draw(botao_alternativa, 20 , Gdx.graphics.getHeight() - 500);
			batch.draw(botao_alternativa, 20 , Gdx.graphics.getHeight() - 700);
			batch.draw(botao_alternativa, 20 , Gdx.graphics.getHeight() - 900);
			// opacidade volta ao normal
			batch.setColor(1, 1, 1, 1f); 

			// checa se acertou a questao
			// primeiro botao
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&
				mouseX >= 20 && mouseX <= 20 + botao_alternativa.getWidth() &&
        	    mouseY >= Gdx.graphics.getHeight() - 500 && 
				mouseY <= Gdx.graphics.getHeight() - 500 + botao_alternativa.getHeight()){
				// se estiver certo
				if(pergunta.resposta_correta() == 'a'){
					// desenha um botao verde
					batch.draw(botao_alternativa_exata, 20 , Gdx.graphics.getHeight() - 500);
					espera(2);
				// se estiver errado
				}else{
					// desenha um botao vermelho
					batch.draw(botao_alternativa_errada, 20 , Gdx.graphics.getHeight() - 500);
					espera(2);
				}
			// segundo botao
    		}if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&
					mouseX >= 20 && mouseX <= 20 + botao_alternativa.getWidth() &&
					mouseY >= Gdx.graphics.getHeight() - 700 &&
					mouseY <= Gdx.graphics.getHeight() - 700 + botao_alternativa.getHeight()){
					// se estiver certo
					if(pergunta.resposta_correta() == 'b'){
						// desenha um botao verde
						batch.draw(botao_alternativa_exata, 20 , Gdx.graphics.getHeight() - 700);
						espera(2);
					// se estiver errado
					}else{
						// desenha um botao vermelho
						batch.draw(botao_alternativa_errada, 20 , Gdx.graphics.getHeight() - 700);
						espera(2);
					}
			// terceiro botao
			}if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&
					mouseX >= 20 && mouseX <= 20 + botao_alternativa.getWidth() &&
					mouseY >= Gdx.graphics.getHeight() - 900 &&
					mouseY <= Gdx.graphics.getHeight() - 900 + botao_alternativa.getHeight()){
					// se estiver certo
					if(pergunta.resposta_correta() == 'c'){
						// desenha um botao verde
						batch.draw(botao_alternativa_exata, 20 , Gdx.graphics.getHeight() - 900);
						espera(2);
					// se estiver errado
					}else{
						// desenha um botao vermelho
						batch.draw(botao_alternativa_errada, 20 , Gdx.graphics.getHeight() - 900);
						espera(2);
					}
			}

			// pergunta
    		pergunta.desenhar(batch);

			// botao sair
			batch.draw(botao_sair, Gdx.graphics.getWidth() - botao_sair.getWidth(), Gdx.graphics.getHeight() - botao_sair.getHeight());
			
			// Verifica se o botao_sair foi clicado com o botao esquerdo do mouse
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&
            	mouseX >= Gdx.graphics.getWidth() - botao_sair.getWidth() &&
            	mouseX <= Gdx.graphics.getWidth() &&
            	mouseY >= Gdx.graphics.getHeight() - botao_sair.getHeight() &&
            	mouseY <= Gdx.graphics.getHeight()) {
				// Fecha o jogo
        		Gdx.app.exit(); 
    		}

			// inicia o buffer
			frameBuffer.begin();
			
			frameBuffer.end();

			// O que foi desenhado ate este ponto sera salvo no FrameBuffer
			texturaPausada = frameBuffer.getColorBufferTexture();
		}

		// despausa quando aperta A
		if(Gdx.input.isKeyPressed(Input.Keys.R)){
			pause = false;
		}

		batch.end();

		// se nao estiver pausado, desenha a textura salva na tela
        if (texturaPausada != null && pause) {
            batch.begin();
            batch.draw(texturaPausada, 0, 0);
            batch.end();
        }

	}
	
	@Override
	public void dispose() {
		batch.dispose();
		chao.dispose();
		jacare.getTexture().dispose();
		pedra_textura.dispose();
		frameBuffer.dispose();
		tiro_textura.dispose();
		fonte_pontos.dispose();
        if (texturaPausada != null) {
            texturaPausada.dispose();
        }
		gerador.dispose();
		arma_textura.dispose();
	}


	// movimento do jaca
	private void moveJacare(){

		boolean caminhando = false;

		// Movimenta o personagem para a esquerda quando a tecla seta esquerda ou 'A' e pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
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

        // Movimenta o personagem para a direita quando a tecla seta direita ou 'D' e pressionada
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
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

	private void handleInput(float mouseX, float mouseY, float disparoX, float disparoY){
		// cria o tiro, com sua posicao e textura
		Tiro novoTiro = new Tiro(tiro_textura, disparoX, disparoY);
		if( Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			novoTiro.atirarMouse(mouseX, mouseY);
			tiros.add(novoTiro);
		}
	}

	private void atualiza(){
		atualizaPedras();
		atualizaTiros();
	}

	private void atualizaTiros() {
		Iterator<Tiro> iterator = tiros.iterator();
		while (iterator.hasNext()){
			Tiro tiro = iterator.next();
			tiro.atualiza(Gdx.graphics.getDeltaTime());

			if(tiro.pegaPosicao().y > Gdx.graphics.getHeight()){
				iterator.remove();
			}
		}
	}

	private void desenha(){
		for (Tiro tiro : tiros){
		tiro.desenha(batch);
		}
	}


	private void atualizaPedras() {
		List<Pedra> pedrasNaoAtingidas = new ArrayList<>();
	
		for (int i = 0; i < pedras.length; i++) {
			Pedra pedra = pedras[i];
			if (pedra != null) {
				pedra.atualizar(Gdx.graphics.getDeltaTime());
				pedra.desenhar(batch);
	
				Iterator<Tiro> tiroIterator = tiros.iterator();
				boolean colidiu = false;
	
				while (tiroIterator.hasNext()) {
					Tiro tiro = tiroIterator.next();
					if (tiro.getBoundingRectangle().overlaps(pedra.getSprite().getBoundingRectangle())) {
						tiroIterator.remove();
						colidiu = true;
						pontos++;
						break;
					}
				}
	
				if (!colidiu) {
					pedrasNaoAtingidas.add(pedra);
				} else {
					// Adicione uma nova pedra no topo da tela
					float x = MathUtils.random(0, 1280);
					float y = Gdx.graphics.getHeight() + MathUtils.random(640, 5000);
					float velocidade = MathUtils.random(50, 200);
	
					pedras[i] = new Pedra(pedra_textura, x, y, velocidade);
				}
			}
		}
	
		pedras = pedrasNaoAtingidas.toArray(new Pedra[0]);
	}

	// detecta colisoes entre duas sprites
	private boolean detectarColisao(Sprite sprite1, Sprite sprite2) {
		return sprite1.getBoundingRectangle().overlaps(sprite2.getBoundingRectangle());
	}

	// faz o programa esperar alguns segundos
	private void espera(float tempo_de_espera){
		// Variáveis de controle para temporização
		float tempoInicial = 0;
		boolean pause = true;
		if (pause) {
			// Verifica quanto tempo passou desde o início do atraso
			float tempoAtual = Gdx.graphics.getDeltaTime();
			tempoInicial += tempoAtual;
		
		    // Verifica se o tempo de espera foi alcançado
			if (tempoInicial >= tempo_de_espera) {
		        // Lógica para quando o tempo de espera terminar
		        pause = false;
		    }
		}
	}
}	

