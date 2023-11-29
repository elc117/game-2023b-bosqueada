package com.bosqueada.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CaixaPerguntas {

    private Sprite caixaPergunta_sprite;
    private BitmapFont fonte;
    private String pergunta;
    private String[] alternativas;
    private String resposta;
    private int contador = 1;

    public CaixaPerguntas(BitmapFont fonte, Texture caixaPergunta_textura){
        this.fonte = fonte;
        this.fonte.setColor(Color.BLACK);
        this.fonte.getData().setScale(1.5f);
    }

    public void le_arquivo(){
        /*
         // Lê o arquivo de perguntas
        FileHandle file = Gdx.files.internal("questoes/Easy.txt");
        String conteudoArquivo = file.readString();

        if(conteudoArquivo == "\n"){
            contador++;
            conteudoArquivo.split("\n");
        }

        switch(contador){
            case 1:
                //pergunta = conteudoArquivo;
                pergunta = "aahshashas";
                break;
            case 2:
                alternativas[0] = conteudoArquivo;
                break;
            case 3:
                alternativas[1] = conteudoArquivo;
                break;
            case 4:
                alternativas[2] = conteudoArquivo;
                break;
            case 5:
                resposta = conteudoArquivo;
                break;
            default:
                break;
        }*/
        
        // Apenas para testar, atribuindo uma pergunta fixa para demonstração
        pergunta = "Qual é a capital do Brasil?";
        alternativas = new String[]{"Rio de Janeiro", "Brasília", "São Paulo"};
        resposta = "Brasília";

    }

    public void desenhar(SpriteBatch batch){
        System.out.println(pergunta);
        fonte.draw(batch, pergunta, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }
}
