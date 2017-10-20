package onuse.com.br.ftsc.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by maico on 28/09/17.
 */

public class Linha {
    private long id;
    private String nome_linha;
    private int imagem, imagemDestino;
    private String codigoLinha;
    private static Drawable mapaImagem;

    public Linha()
    {
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_linha() {
        return nome_linha;
    }

    public void setNome_linha(String nome_linha) {
        this.nome_linha = nome_linha;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public int getImagemDestino() {
        return imagemDestino;
    }

    public void setImagemDestino(int imagemDestino) {
        this.imagemDestino = imagemDestino;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public static Drawable getMapaImagem() {
        return mapaImagem;
    }

    public static void setMapaImagem(Drawable mapaImagem) {
        Linha.mapaImagem = mapaImagem;
    }
}
