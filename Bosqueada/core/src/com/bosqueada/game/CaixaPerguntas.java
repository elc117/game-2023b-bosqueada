package com.bosqueada.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class CaixaPerguntas {

    private Sprite caixaPergunta_sprite;
    private BitmapFont fonte_pergunta;
    private BitmapFont fonte_alternativas;
    private String[] questoes;
    private String[] questoes_count;
    private String pergunta;
    private String[] alternativas;
    private String resposta;
    private String[] partes;
    private int contador = 0;
    public int questoes_quantidade = 0;
    // posicao da pergunta na tela
    private int posPerguntaX = 100;
    private int posPerguntaY = 500;

    public CaixaPerguntas(BitmapFont fonte_pergunta, BitmapFont fonte_alternativa, Texture caixaPergunta_textura){
        this.fonte_pergunta = fonte_pergunta;
        this.fonte_pergunta.setColor(Color.BLACK);
        this.fonte_pergunta.getData().setScale(1.5f);

        this.fonte_alternativas = fonte_alternativa;
        this.fonte_alternativas.setColor(Color.BLACK);
        this.fonte_alternativas.getData().setScale(1.0f);
    }

    public void le_arquivo(){
        
        // Lê o arquivo de perguntas
        FileHandle file = Gdx.files.internal("questoes/PerguntasParadigmas.txt");
        String conteudoArquivo = file.readString();

        // inicializa variaveis
        alternativas = new String[3];

        // separa as questoes por @
        String[] questoesSeparadas = conteudoArquivo.split("@");

        // quantas questoes sao
        questoes_quantidade = questoesSeparadas.length;
        int random_questao = MathUtils.random(1, questoes_quantidade);

        String questao = questoesSeparadas[random_questao];

        // separa a questao por partes
        partes = questao.split("\n");

        // se a string partes conter uma questao
        if(partes.length >= 1){
            pergunta = partes[1];
            alternativas[0] = partes[2];
            alternativas[1] = partes[3];
            alternativas[2] = partes[4];
            resposta = partes[4];
        }
    }

    // desenha as perguntas
    public void desenhar(SpriteBatch batch){
        fonte_pergunta.draw(batch, pergunta, posPerguntaX, posPerguntaY);
        fonte_alternativas.draw(batch, alternativas[0], posPerguntaX, posPerguntaY - 100);
        fonte_alternativas.draw(batch, alternativas[1], posPerguntaX, posPerguntaY - 200);
        fonte_alternativas.draw(batch, alternativas[2], posPerguntaX, posPerguntaY - 300);
    }
}
