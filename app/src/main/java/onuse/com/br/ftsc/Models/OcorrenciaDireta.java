package onuse.com.br.ftsc.Models;

/**
 * Created by Maico on 30/10/2017.
 */

public class OcorrenciaDireta {
    private int id;
    private int carro;
    private int horario;
    private int tabela;
    private int empresa;
    private String destino;
    private int matricula;
    private String colaborador;
    private int funcao;
    private String descricao;
    private int matriculaFiscal;
    private int data;

    public OcorrenciaDireta() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarro() {
        return carro;
    }

    public void setCarro(int carro) {
        this.carro = carro;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getTabela() {
        return tabela;
    }

    public void setTabela(int tabela) {
        this.tabela = tabela;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public int getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getMatriculaFiscal() {
        return matriculaFiscal;
    }

    public void setMatriculaFiscal(int matriculaFiscal) {
        this.matriculaFiscal = matriculaFiscal;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
