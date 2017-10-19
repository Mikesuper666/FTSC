package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 18/10/17.
 */

public class OcorrenciasCarros
{
    private String id;
    private int codigo;
    private int matricula_fiscal;
    private String ocorrencia;

    public OcorrenciasCarros() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
