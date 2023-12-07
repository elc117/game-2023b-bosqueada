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
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.TimeUtils;

// fontes
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayDeque;
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

	private Menu menu;
	private Music musicaMenu;

	private Sound somTiro;

	BitmapFont fonte_pontos;
	int pontos = 0;
	
	// cria o vetor de pedras
	Pedra[] pedras;
	boolean limparPedras = false;
	float tempoSpawnPedra = 0f;
    float intervaloSpawnPedra = 2f;

	CaixaPerguntas pergunta;

	boolean arma_virada_esquerda = true;
	boolean virado_esquerda = true;
	boolean pause = false;
	boolean errou;
	boolean menu_inicio = false;
	boolean menu_pergunta = false;
	boolean armado;
	boolean vida = false;
	boolean caixaColetada = false;

	Sprite municao;
	List<Sprite> municoes;
	Texture municao_textura;
	BitmapFont fonte_municao;
	int municao_quantidade = 30;
	
	private boolean teclaAtiraPressionada = false;

	int contador_auxiliar_caminhada = 0;
	int pedras_quantidade = 110;

	int grausSoma = 0;

	float disparoX, disparoY;

	// cores
	Color babyBlue = new Color(0.678f, 0.847f, 0.902f, 1f);
	
	@Override
	public void create () {
		musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("audio/astronauta.mp3"));
        musicaMenu.setLooping(true); // Para reprodução contínua
        musicaMenu.play();

		somTiro = Gdx.audio.newSound(Gdx.files.internal("audio/somtiro.mp3"));

		// cria um menu
		menu = new Menu();

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

		// textura caixa de municoes
		municao_textura = new Texture("texturas/caixa_municao.png");

		// municao sprite
		municao = new Sprite(municao_textura);
		municao.setPosition(MathUtils.random(30, Gdx.graphics.getWidth()), Gdx.graphics.getHeight());
		municoes = new ArrayList<>();

		// textura do personagem
		jacare_textura = new Texture("texturas/jacare.png");

		// textura da pedra
		pedra_textura = new Texture("texturas/meteoro.png");

		// textura das arvores de fundo
		chao = new Texture("texturas/background2.jpg");

		// fontes
		fonte_pontos = new BitmapFont();
		fonte_municao = new BitmapFont();

		// criando o jacare
		jacare = new Sprite(jacare_textura);

		// definindo a posicao inicial
		jacare.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/6 + 20);

		arma_textura = new Texture("texturas/ak.png");

		arma_sprite = new Sprite(arma_textura);

		arma = new Ak47(arma_textura, arma_sprite, jacare.getX(), jacare.getY());

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
			float y = Gdx.graphics.getHeight() + MathUtils.random(640, 20000);
            float velocidade = MathUtils.random(100, 300);

            pedras[i] = new Pedra(pedra_textura, x, y, velocidade);
        }

		// le o arquivo
		pergunta.le_arquivo();

	}

	@Override
	public void render() {
		
		if (!menu.isJogoIniciado()) {
			menu.render();
			vida = true;
		} else {

		ScreenUtils.clear(babyBlue);

		// pega a cordenada do mouse dentro do loop do game
		float mouseX = Gdx.input.getX();
		// Inverte a coordenada y para ficar de acordo com a libgdx
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); 

		// pega o deltaTime
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		batch.begin();
		
		// funcao de movimentos do jaca
		moveJacare();

		// desenha caso nao esteja pausado
		if(!pause){
			// fundo
			batch.draw(chao, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			// jacaras
			jacare.draw(batch);

			// contador de pontos
			fonte_pontos.getData().setScale(2);

			// contador de municao
			fonte_municao.getData().setScale(2);

			// Cria um objeto GlyphLayout para calcular as dimensões do texto
			GlyphLayout layout_pontos = new GlyphLayout(fonte_pontos, "Pontos: " + pontos);

			// objeto glyphlayout para calcular as dimensoes do texto
			GlyphLayout layout_municao = new GlyphLayout(fonte_municao, "Municao: " + municao_quantidade);

			// Obtém a largura e altura real do texto com base na fonte e conteúdo
			float textoWidth = layout_pontos.width;
			float textoHeight = layout_pontos.height;

			// obtem a altura real do texto
			float textoX = layout_municao.width;
			float textoY = layout_municao.height;

			// Calcula a posição X centralizada na tela
			float x = (Gdx.graphics.getWidth() - textoWidth) / 2;

			// Calcula a posição Y na parte superior da tela com um pequeno espaço para baixo (20 pixels)
			float y = Gdx.graphics.getHeight() - textoHeight - 20;

			// Desenha o texto "Pontos: " seguido pelo valor da variável 'pontos' na tela
			fonte_pontos.draw(batch, "Pontos: " + pontos, x, y);

			// altura da tela - texto pontos - texto municao
			float X = (Gdx.graphics.getWidth() - textoX)/3;

			// largura da tela - texto pontos - texto municao
			float Y = (Gdx.graphics.getHeight() - textoY - 20);

			// desenha municao: quantas municoes
			fonte_municao.draw(batch, "Municao: " + municao_quantidade, X, Y);

			// Atualiza e desenha as pedras
        	for (Pedra pedra : pedras) {
        	    pedra.atualizar(deltaTime);
        	    pedra.desenhar(batch);

				// Verifica a colisao entre o jacare e cada uma das pedras
				if (detectarColisao(jacare, pedra.getSprite())) {
					// o que acontece quando colide
					// nesse caso, reseta a posicao do jaca
					menu_pergunta = true;
					pause = true;
				}
        	}

			limpaTelaPedras();

			tempoSpawnPedra += Gdx.graphics.getDeltaTime();
            if (tempoSpawnPedra >= intervaloSpawnPedra) {
                spawnNovaPedra();
                tempoSpawnPedra = 0f;
            }

			botaoSair(mouseX, mouseY);

			// desenha e atualiza tiro e pedra
			atualiza();
			desenha();

			if (!caixaColetada) {
				municao.draw(batch);
			}

			// faz a caixa de municao cair até o chao do jaca
			if(municao.getY() > Gdx.graphics.getHeight() / 6 + 20 && spawnCaixa()){
				municao.setY(municao.getY() - 1);
			}

			// faz com que o player possa coletar a municao apenas uma  vez
			if (!caixaColetada && detectarColisao(jacare, municao)) {
				// adiciona municao e marca a caixa como coletada
				municao_quantidade += MathUtils.random(30, 100);
				caixaColetada = true;
			}

			Iterator<Sprite> iterador_municao = municoes.iterator();
			while(iterador_municao.hasNext()){
				Sprite m = iterador_municao.next();
				if(detectarColisao(jacare, m)){
					// remove a caixa
					iterador_municao.remove();
				}
			}	
			
			if(municao_quantidade > 0){
				armado = true;
			}else{
				armado = false;
			}

			if(armado){
				// se arma nao direita, arma direita
				if(mouseX > jacare.getX() &&
				arma_virada_esquerda){
					arma_sprite.flip(true,false);
					arma_virada_esquerda = false;
		 		}
		 		// se arma nao esquerda, arma esquerda
		 		if(mouseX < jacare.getX() &&
					!arma_virada_esquerda){
						arma_sprite.flip(true,false);
						arma_virada_esquerda = true;
					}
		 		// atualiza arma e tiro pra esquerda
		 		if(arma_virada_esquerda){
					arma.atualizaArma(jacare.getX() + jacare.getWidth()/4 - 30, jacare.getY() + jacare.getHeight()/2 + 20, arma_sprite);
					disparoX = jacare.getX() + jacare.getWidth()/4 - 20;
					disparoY = jacare.getY() + jacare.getHeight()/2 + 20;
					grausSoma = 180;

					// ajusta a posicao de saida do tiro
					if(mouseY >= jacare.getY() * 2 - 30){
						disparoX = jacare.getX() + jacare.getWidth()/4 + 30;
						disparoY = jacare.getY() + jacare.getHeight()/2 + 40;
					}

				// atualiza arma e tiro pra direita
		 		}else{
					arma.atualizaArma(jacare.getX() + jacare.getWidth()/4, jacare.getY() + jacare.getHeight()/2 + 20, arma_sprite);
					disparoX = jacare.getX() + jacare.getWidth()/4 + 100;
					disparoY = jacare.getY() + jacare.getHeight()/2 + 40;
					grausSoma = 360;

					// ajusta a posicao de saida do tiro
					if(mouseY >= jacare.getY() * 2 - 30){
						disparoX = jacare.getX() + jacare.getWidth()/4 + 50;
						disparoY = jacare.getY() + jacare.getHeight()/2 + 40;
					}
		 		}
			
		 		handleInput(mouseX, mouseY, disparoX, disparoY, municao_quantidade);
			
		 		arma.desenha(batch, arma_sprite);

				arma.rotacionaArma(mouseX, mouseY, grausSoma);
			}
			

		// se estiver pausado, salva o estado atual no buffer
		}else if(pause && menu_pergunta){
			menuPerguntaLogica(mouseX, mouseY);
		}

		batch.end();

		// se nao estiver pausado, desenha a textura salva na tela
        if(texturaPausada != null && pause) {
            batch.begin();
            batch.draw(texturaPausada, 0, 0);
            batch.end();
			}
        }
	}
	
	@Override
	public void dispose() {
		if (musicaMenu != null) {
			musicaMenu.dispose();
		}
		batch.dispose();
		chao.dispose();
		jacare.getTexture().dispose();
		pedra_textura.dispose();
		frameBuffer.dispose();
		tiro_textura.dispose();
		fonte_pontos.dispose();
		menu.dispose();
        if (texturaPausada != null) {
            texturaPausada.dispose();
        }
		gerador.dispose();
		arma_textura.dispose();
		municao_textura.dispose();
	}

	// movimento do jaca
	public void moveJacare(){

		boolean caminhando = false;

		// checa se o jacaras passou do ponto pra direita e bota ele na esquerda
		if (jacare.getX() > Gdx.graphics.getWidth() - jacare.getWidth()){
			jacare.setPosition( -20, Gdx.graphics.getHeight()/6 + 20);
		}

		// checa se o jacas passou do ponto pra esquerda e bota ele na direita
		if (jacare.getX() < -20){
			jacare.setPosition( Gdx.graphics.getWidth() - jacare.getWidth(), Gdx.graphics.getHeight()/6 + 20);
		}

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

	private void handleInput(float mouseX, float mouseY, float disparoX, float disparoY, int _quantidade){
		// cria o tiro, com sua posicao e textura
		Tiro novoTiro = new Tiro(tiro_textura, disparoX, disparoY);
		if( Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !teclaAtiraPressionada){
			teclaAtiraPressionada = true;
			novoTiro.atirarMouse(mouseX, mouseY);
			tiros.add(novoTiro);

			if (somTiro != null) {
                somTiro.play();
            }

			// diminui uma municao por cada disparo
			if(municao_quantidade > 0){
				municao_quantidade--;
			}
		}

		// faz com que nao de para atirar segurando o botao esquerdo do mouse (metralhar)
		if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			teclaAtiraPressionada = false;
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

			// remove os tiros quando eles saem da tela
			if(tiro.pegaPosicao().y > Gdx.graphics.getHeight()){
				iterator.remove();
			}else if(tiro.pegaPosicao().y < 0){
				iterator.remove();
			}else if(tiro.pegaPosicao().x < 0){
				iterator.remove();
			}else if(tiro.pegaPosicao().x > Gdx.graphics.getWidth()){
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
		List<Pedra> pedrasNaoAtingidas = criarListaDePedras();

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
						//municao_quantidade++;
	
						// adiciona três novas pedras quando um tiro acerta
						for (int j = 0; j < 3; j++) {
							float x = MathUtils.random(0, Gdx.graphics.getWidth());
							float y = Gdx.graphics.getHeight() + MathUtils.random(640, 5000);
							float velocidade = MathUtils.random(50, 200);
							pedrasNaoAtingidas.add(new Pedra(pedra_textura, x, y, velocidade));
						}
	
						break;
					}
				}

				if (!colidiu) {
					pedrasNaoAtingidas.add(pedra);
				}

			}
		}

		pedras = pedrasNaoAtingidas.toArray(new Pedra[0]);
	}
	

	private void spawnNovaPedra() {
        // adiciona uma nova pedra no topo da tela
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = Gdx.graphics.getHeight() + MathUtils.random(640, 5000);
        float velocidade = MathUtils.random(50, 200);

        // encontra um slot vazio no array de pedras
        for (int i = 0; i < pedras.length; i++) {
            if (pedras[i] == null) {
                pedras[i] = new Pedra(pedra_textura, x, y, velocidade);
                break;
            }
        }
    }

	// detecta colisoes entre duas sprites
	private boolean detectarColisao(Sprite sprite1, Sprite sprite2) {
		return sprite1.getBoundingRectangle().overlaps(sprite2.getBoundingRectangle());
	}

	private void menuPerguntaLogica(float mouseX, float mouseY){
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
					errou = false;
					reiniciarJogo();
				// se estiver errado
				}else{
					// desenha um botao vermelho
					batch.draw(botao_alternativa_errada, 20 , Gdx.graphics.getHeight() - 500);
					errou = true;
					reiniciarJogo();
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
						errou = false;
						reiniciarJogo();
					// se estiver errado
					}else{
						// desenha um botao vermelho
						batch.draw(botao_alternativa_errada, 20 , Gdx.graphics.getHeight() - 700);
						errou = true;
						reiniciarJogo();
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
					errou = false;
					reiniciarJogo();
				// se estiver errado
				}else{
					// desenha um botao vermelho
					batch.draw(botao_alternativa_errada, 20 , Gdx.graphics.getHeight() - 900);
					errou = true;
					reiniciarJogo();
				}
			}

			// pergunta
    		pergunta.desenhar(batch);

			// botao sair
			botaoSair(mouseX, mouseY);

			// inicia o buffer
			frameBuffer.begin();
			frameBuffer.end();

			// O que foi desenhado ate este ponto sera salvo no FrameBuffer
			texturaPausada = frameBuffer.getColorBufferTexture();
	}

	// reinicia as variaveis e lógicas do game
	private void reiniciarJogo(){

		// reinicializacoes que acontecem independente do usuario continuar ou morrer

		// faz com que limpaTelaPedras seja chamada
		limparPedras = true;

		// jaca posicao inicial
		jacare.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 6 + 20);		

		// respondeu a pergunta e sobreviveu
		if(errou == false){
			vida = true;
			pause = false;
		}

		// respondeu a pergunta e morreu
		if(errou == true){

			// reinicia o jogo
			menu.setIsJogoIniciado(false);
			vida = false;
			pause = false;
			menu_pergunta = false;

			// pontos e municao reiniciados
			pontos = 0;
			municao_quantidade = 30;
		}

	}

	// desenha e fecha caso clicado
	public void botaoSair(float mouseX, float mouseY){
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
	}

	public void limpaTelaPedras(){
		if(limparPedras){
			// coloca as pedras para as posicoes de incio
			for(int i = 0; i < pedras.length; i++){	
				Pedra pedra = pedras[i];
				pedra.reiniciaPedra();
			}
			// stop limpar as pedras depois de limpa
			limparPedras = false;
		}
	}

	public long relogio(){
		long startTime = TimeUtils.millis();

		// tempo em segundos decorrido
		long tempoDecorrido = startTime/1000;

		return tempoDecorrido;
	}

	public boolean spawnCaixa(){
		int tempoSpawnCaixa = MathUtils.random(5, 20);

		// se o tempo que passou é o mesmo da espera da caixa spawna
		if(relogio() - tempoSpawnCaixa <= 0){
			return true;
		}

		return false;
	}

	public static List<Pedra> criarListaDePedras(){
		List<Pedra> pedrasNaoAtingidas = new ArrayList<>();
		return pedrasNaoAtingidas;
	}

}
