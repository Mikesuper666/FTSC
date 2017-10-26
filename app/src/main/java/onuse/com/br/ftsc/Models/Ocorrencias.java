package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 16/10/17.
 */

public class Ocorrencias {
    private String id;
    private int matricula_func;
    private int matricula_fiscal;
    private String ocorrencia;

    public Ocorrencias() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMatricula_func() {
        return matricula_func;
    }

    public void setMatricula_func(int matricula_func) {
        this.matricula_func = matricula_func;
    }

    public int getMatricula_fiscal() {
        return matricula_fiscal;
    }

    public void setMatricula_fiscal(int matricula_fiscal) {
        this.matricula_fiscal = matricula_fiscal;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }
}
