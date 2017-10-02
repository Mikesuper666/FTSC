package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 29/09/17.
 */

public class Execoes {
    private long id;
    private String nome;
    private int tipoExecao;

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
}
