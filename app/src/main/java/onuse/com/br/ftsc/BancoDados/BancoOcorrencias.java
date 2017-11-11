package onuse.com.br.ftsc.BancoDados;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import onuse.com.br.ftsc.ListaAdaptados;
import onuse.com.br.ftsc.ListaExecoes;
import onuse.com.br.ftsc.Models.OcorrenciaDireta;
import onuse.com.br.ftsc.Models.Remanejo;
import onuse.com.br.ftsc.PrincipalActivity;

/**
 * Created by Maico on 29/10/2017.
 */

public class BancoOcorrencias {
    private Context context;
    private String url, parametros;
    private ProgressDialog dialogBaixando;
    private int tabela;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    public BancoOcorrencias(Context context) {
        this.context = context;
        bancoInterno = new BancoInterno(this.context);
        conn = bancoInterno.getWritableDatabase();
    }

    public void InsercaoOcorrenciasProtocolo(int tabela) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (tabela) {
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/insert-ocorrencia-direta.php";
                    this.tabela = 0;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/insert-remanejo.php";
                    this.tabela = 1;
                    break;
            }
            parametros = "";
            new InserirDados().execute(url);
        }
    }

    public void UpdateOcorrencias(int tabela, OcorrenciaDireta ocorrenciaDireta) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (tabela) {
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/update-ocorrencia-direta.php?id="+ocorrenciaDireta.getId()+"&carro="+ocorrenciaDireta.getCarro()+
                            "&horario="+ocorrenciaDireta.getHorario()+"&tabela="+ocorrenciaDireta.getTabela()+"&empresa="+ocorrenciaDireta.getEmpresa()+
                            "&destino="+ocorrenciaDireta.getDestino().replace(" ","_-")+"&matricula="+ocorrenciaDireta.getMatricula()+
                            "&colaborador="+ocorrenciaDireta.getColaborador()+"&funcao="+ocorrenciaDireta.getFuncao()+
                            "&descricao="+ocorrenciaDireta.getDescricao().replace(" ","_-")+"&matricula_fiscal="+ocorrenciaDireta.getMatriculaFiscal()+
                            "&data="+ocorrenciaDireta.getData();
                    tabela = 0;
                    break;
            }
            parametros = "";
            new InserirDados().execute(url);
        }
    }

    public void UpdateRemanejo(int tabela, Remanejo remanejo) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (tabela) {
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/update-remanejo.php?id="+remanejo.getId()+"&empresa="+remanejo.getEmpresa()+
                            "&tabela="+remanejo.getTabela()+"&destino="+remanejo.getDestino().replace(" ","_-")+"&horario="+remanejo.getHorario()+"&carro="+remanejo.getCarro()+
                            "&tabela2="+remanejo.getTabela2()+"&destino2="+remanejo.getDestino2().replace(" ","_-")+"&horario2="+remanejo.getHorario2()+"&carro2="+remanejo.getCarro2()+
                            "&data="+remanejo.getData()+"&matricula_fiscal="+remanejo.getMatricula_fiscal();
                    tabela = 0;
                    break;
            }
            parametros = "";
            new InserirDados().execute(url);
        }
    }

    private class InserirDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBaixando = new ProgressDialog(context);
            dialogBaixando.setMessage("Registrando novo numero de protocolo...");
            dialogBaixando.setCancelable(false);
            dialogBaixando.show();
        }

        @Override
        protected void onPostExecute(String resultado) {
            dialogBaixando.dismiss();
            if(resultado != null) {
                if (resultado.contains("nao_registrado")) {
                    Toast.makeText(context, "Erro! Tente novamente!", Toast.LENGTH_LONG).show();
                }else if(resultado.contains("alterou")){
                    Toast.makeText(context, "Registro adicionado com sucesso!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "Protocolo registrado com sucesso", Toast.LENGTH_LONG).show();
                    InterfaceAtualizacao(resultado);
                }
            }else{
                Toast.makeText(context, "Problema na conexão do banco", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void InterfaceAtualizacao(String protocolo){
        if(tabela == 0)
            ((PrincipalActivity) context).RecebendoProtocolo(Integer.parseInt(protocolo.trim()),0);
        else if(tabela == 1)
            ((PrincipalActivity) context).RecebendoProtocolo(Integer.parseInt(protocolo.trim()),1);
    }

}
