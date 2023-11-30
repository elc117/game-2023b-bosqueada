package com.bosqueada.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class CaixaPerguntas {

    FreeTypeFontGenerator gerador;
    FreeTypeFontGenerator.FreeTypeFontParameter parametro;
    
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
    private int posPerguntaX = 20;
    private int posPerguntaY = Gdx.graphics.getHeight() - 100;

    public CaixaPerguntas(FreeTypeFontGenerator gerador, FreeTypeFontGenerator.FreeTypeFontParameter parametro){
        // tamanho da fonte
        parametro.size = 30;
        // cor preto
        parametro.color.set(0.2196f, 0.5412f, 0.2471f, 1f);
        // parametro borda
        parametro.borderWidth = 2;
        // borda cor
        parametro.borderColor.set( 1f, 1f, 1f, 1f);

        // gerando a fonte da pergunta
        fonte_pergunta = gerador.generateFont(parametro);

        // redimencionando tamanho para as alternativas
        parametro.size = 24;
        fonte_alternativas = gerador.generateFont(parametro);
    }

    public void le_arquivo(){
        
        // Le o arquivo de perguntas
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
        partes = questao.split(";");

        // se a string partes conter uma questao
        if(partes.length >= 1){
            pergunta = partes[1];
            alternativas[0] = partes[2];
            alternativas[1] = partes[3];
            alternativas[2] = partes[4];
            resposta = partes[5];
        }
    }

    public String resposta_correta(){
        System.out.println(resposta);
        return resposta;
    }

    // desenha as perguntas
    public void desenhar(SpriteBatch batch){
        fonte_pergunta.draw(batch, pergunta, posPerguntaX, posPerguntaY);
        fonte_alternativas.draw(batch, alternativas[0], posPerguntaX + 20, posPerguntaY - 250);
        fonte_alternativas.draw(batch, alternativas[1], posPerguntaX + 20, posPerguntaY - 450);
        fonte_alternativas.draw(batch, alternativas[2], posPerguntaX + 20, posPerguntaY - 650);
    }
}
