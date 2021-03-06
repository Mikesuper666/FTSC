package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 29/09/17.
 */

public class Execoes {
    private long id;
    private String nome;
    private int tipoExecao;
    public int qtdeExecoes;
    private int funcao;
    private int horario;
    private boolean checado;
    private boolean estaChecado;

    public Execoes()
    {
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipoExecao() {
        return tipoExecao;
    }

    public void setTipoExecao(int tipoExecao) {
        this.tipoExecao = tipoExecao;
    }

    public int getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getQtdeExecoes() {
        return qtdeExecoes;
    }

    public void setQtdeExecoes(int qtdeExecoes) {
        this.qtdeExecoes = qtdeExecoes;
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
