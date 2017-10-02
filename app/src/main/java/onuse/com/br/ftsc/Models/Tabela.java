package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 27/09/17.
 */

public class Tabela {
    private long id;
    private int carro, tabela1, tabela2, roleta;
    private String origem_destino, tabela_hora, hora_chegada, hora_saida, motivo;

    public Tabela()
    {
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCarro() {
        return carro;
    }

    public void setCarro(int carro) {
        this.carro = carro;
    }

    public int getTabela1() {
        return tabela1;
    }

    public void setTabela1(int tabela1) {
        this.tabela1 = tabela1;
    }

    public int getTabela2() {
        return tabela2;
    }

    public void setTabela2(int tabela2) {
        this.tabela2 = tabela2;
    }

    public int getRoleta() {
        return roleta;
    }

    public void setRoleta(int roleta) {
        this.roleta = roleta;
    }

    public String getOrigem_destino() {
        return origem_destino;
    }

    public void setOrigem_destino(String origem_destino) {
        this.origem_destino = origem_destino;
    }

    public String getTabela_hora() {
        return tabela_hora;
    }

    public void setTabela_hora(String tabela_hora) {
        this.tabela_hora = tabela_hora;
    }

    public String getHora_chegada() {
        return hora_chegada;
    }

    public void setHora_chegada(String hora_chegada) {
        this.hora_chegada = hora_chegada;
    }

    public String getHora_saida() {
        return hora_saida;
    }

    public void setHora_saida(String hora_saida) {
        this.hora_saida = hora_saida;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
