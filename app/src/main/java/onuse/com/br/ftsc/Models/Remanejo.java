package onuse.com.br.ftsc.Models;

/**
 * Created by Maico on 31/10/2017.
 */

public class Remanejo {

    private int id;
    private int empresa;
    private int tabela;
    private String destino;
    private String aosCuidados;
    private int horario;
    private int carro;
    private int tabela2;
    private String destino2;
    private int horario2;
    private int carro2;
    private int data;
    private int matricula_fiscal;
    public Remanejo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public int getTabela() {
        return tabela;
    }

    public void setTabela(int tabela) {
        this.tabela = tabela;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAosCuidados() {
        return aosCuidados;
    }

    public void setAosCuidados(String aosCuidados) {
        this.aosCuidados = aosCuidados;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getCarro() {
        return carro;
    }

    public void setCarro(int carro) {
        this.carro = carro;
    }

    public int getTabela2() {
        return tabela2;
    }

    public void setTabela2(int tabela2) {
        this.tabela2 = tabela2;
    }

    public String getDestino2() {
        return destino2;
    }

    public void setDestino2(String destino2) {
        this.destino2 = destino2;
    }

    public int getHorario2() {
        return horario2;
    }

    public void setHorario2(int horario2) {
        this.horario2 = horario2;
    }

    public int getCarro2() {
        return carro2;
    }

    public void setCarro2(int carro2) {
        this.carro2 = carro2;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getMatricula_fiscal() {
        return matricula_fiscal;
    }

    public void setMatricula_fiscal(int matricula_fiscal) {
        this.matricula_fiscal = matricula_fiscal;
    }
}
