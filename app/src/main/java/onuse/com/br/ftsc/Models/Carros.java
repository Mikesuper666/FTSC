package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 01/10/17.
 */

public class Carros {
    private long id;
    private int tipoCarro;
    private int adaptado;
    private int qtdeOcorrencias;
    private boolean checado;
    private boolean estaChecado;

    public Carros()
    {
        checado = false;
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTipoCarro() {
        return tipoCarro;
    }

    public void setTipoCarro(int tipoCarro) {
        this.tipoCarro = tipoCarro;
    }

    public int getAdaptado() {
        return adaptado;
    }

    public void setAdaptado(int adaptado) {
        this.adaptado = adaptado;
    }

    public int getQtdeOcorrencias() {
        return qtdeOcorrencias;
    }

    public void setQtdeOcorrencias(int qtdeOcorrencias) {
        this.qtdeOcorrencias = qtdeOcorrencias;
    }

    public boolean isChecado() {
        return checado;
    }

    public void setChecado(boolean checado) {
        this.checado = checado;
    }

    public boolean getEstaChecado() {
        return estaChecado;
    }

    public void setEstaChecado(boolean estaChecado) {
        this.estaChecado = estaChecado;
    }
}
