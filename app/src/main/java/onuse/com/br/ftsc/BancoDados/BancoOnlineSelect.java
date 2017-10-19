package onuse.com.br.ftsc.BancoDados;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.ListaAdaptados;
import onuse.com.br.ftsc.ListaExecoes;
import onuse.com.br.ftsc.LoginActivity;
import onuse.com.br.ftsc.PrincipalActivity;

/**
 * Created by maico on 06/10/17.
 */

public class BancoOnlineSelect {
    private Context context;
    private String url, parametros;
    private ProgressDialog dialogBaixando;
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;
    private int tabela;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;

    public BancoOnlineSelect(Context context) {
        this.context = context;
    }

    public void conectarAobanco(int numeroTabela) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (numeroTabela){
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/select-linhas.php";
                    tabela = 0;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/select-adaptados.php";
                    tabela = 1;
                    break;
                case 2:
                    url = "http://maicoheleno.890m.com/tcc/select-execao2.php";
                    tabela = 2;
                    break;
                case 3:
                    url = "http://maicoheleno.890m.com/tcc/select-ocorrencias.php";
                    tabela = 3;
                    break;
                case 4:
                    url = "http://maicoheleno.890m.com/tcc/select-carros-ocorrencias.php";
                    tabela = 4;
                    break;
            }
            parametros = "";

            new RegistraDados().execute(url);
        }
    }

    private class RegistraDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBaixando = new ProgressDialog(context);
            dialogBaixando.setMessage("Baixando dados e atualizando...");
            dialogBaixando.setCancelable(false);
            dialogBaixando.show();
        }

        @Override
        protected void onPostExecute(String resultado) {

            dialogBaixando.dismiss();
            if (resultado == null) {
                Toast.makeText(context, "Problema na conexao com o banco de dados!", Toast.LENGTH_LONG).show();
            } else if (resultado.contains("conexao_PHP_faliu")) {
                Toast.makeText(context, "conexão sem retorno", Toast.LENGTH_LONG).show();
            } else {
                if(tabela == 0)
                    AdicionarLinhas(resultado);
                else if(tabela == 1)
                    AdicionarAdaptados(resultado);
                else if(tabela == 2)
                    AdicionarExecoes(resultado);
                else if(tabela == 3)
                    AdicionarOcorrencias(resultado);
                else if(tabela == 4)
                    AdicionarCarrosOcorrencias(resultado);
                else
                    Toast.makeText(context, "Esta tabela não foi encontrada", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void AdicionarLinhas(final String linhas){

        bancoInterno = new BancoInterno(context);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String nomeLinha = null;
                String imagem = null;
                String codigo = null;
                String linhasMisturadas[] = linhas.split("__");
                if (linhas != null) {
                    for (int i = 0; i < linhasMisturadas.length;) {

                        if (linhasMisturadas[i].contains("^")) {
                            InterfaceAtualizacao();
                            break;
                        } else if(i < linhasMisturadas.length){
                            nomeLinha = linhasMisturadas[i];
                            i++;
                            imagem = linhasMisturadas[i];
                            i++;
                            codigo = linhasMisturadas[i];
                            repositorioAcoes.AdicionarLinha(nomeLinha, imagem, codigo);
                            i++;
                        }
                    }
                }
            }
        }).start();
    }

    private void AdicionarAdaptados(final String adaptados){

        bancoInterno = new BancoInterno(context);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String id = null;
                String tipoCarro = null;
                String d_adaptado = null;
                String adaptadosMisturados[] = adaptados.split("__");
                if (adaptados != null) {
                    for (int i = 0; i < adaptadosMisturados.length;) {

                        if (adaptadosMisturados[i].contains("^")) {
                            InterfaceAtualizacao();
                            break;
                        } else if(i < adaptadosMisturados.length){
                            id = adaptadosMisturados[i];
                            i++;
                            tipoCarro = adaptadosMisturados[i];
                            i++;
                            d_adaptado = adaptadosMisturados[i];
                            repositorioAcoes.AdicionarAdaptados(id, tipoCarro, d_adaptado);
                            i++;
                        }
                    }
                }
            }
        }).start();
    }

    private void AdicionarExecoes(final String execoes){

        bancoInterno = new BancoInterno(context);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String id = null;
                String nome = null;
                String tipo_execao = null;
                String funcao = null;
                String horario = null;
                String execoesMisturados[] = execoes.split("__");
                if (execoes != null) {
                    for (int i = 0; i < execoesMisturados.length;) {

                        if (execoesMisturados[i].contains("^")) {
                            InterfaceAtualizacao();
                            break;
                        } else if(i < execoesMisturados.length){
                            id = execoesMisturados[i];
                            i++;
                            nome = execoesMisturados[i];
                            i++;
                            tipo_execao = execoesMisturados[i];
                            i++;
                            funcao = execoesMisturados[i];
                            i++;
                            horario = execoesMisturados[i];
                            repositorioAcoes.AdicionarExecao(id, nome, tipo_execao,funcao,horario);
                            i++;
                        }
                    }
                }
            }
        }).start();
    }

    private void AdicionarOcorrencias(final String ocorrencias){
        bancoInterno = new BancoInterno(context);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String id = null;
                String matricula_func = null;
                String matricula_fiscal = null;
                String ocorrencia = null;
                String execoesMisturados[] = ocorrencias.split("__");
                if (ocorrencias != null) {
                    for (int i = 0; i < execoesMisturados.length;) {

                        if (execoesMisturados[i].contains("^")) {
                            InterfaceAtualizacao();
                            break;
                        } else if(i < execoesMisturados.length){
                            id = execoesMisturados[i];
                            i++;
                            matricula_func = execoesMisturados[i];
                            i++;
                            matricula_fiscal = execoesMisturados[i];
                            i++;
                            ocorrencia = execoesMisturados[i];
                            repositorioAcoes.AdicionarOcorrencias(id, matricula_func, matricula_fiscal,ocorrencia.replace("_-", " "));
                            i++;
                        }
                    }
                }
            }
        }).start();
    }

    private void AdicionarCarrosOcorrencias(final String ocorrencias){
        bancoInterno = new BancoInterno(context);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String id = null;
                String codigo = null;
                String matricula_fiscal = null;
                String ocorrencia = null;
                String execoesMisturados[] = ocorrencias.split("__");
                if (ocorrencias != null) {
                    for (int i = 0; i < execoesMisturados.length;) {

                        if (execoesMisturados[i].contains("^")) {
                            InterfaceAtualizacao();
                            break;
                        } else if(i < execoesMisturados.length){
                            id = execoesMisturados[i];
                            i++;
                            codigo = execoesMisturados[i];
                            i++;
                            matricula_fiscal = execoesMisturados[i];
                            i++;
                            ocorrencia = execoesMisturados[i];
                            repositorioAcoes.AdicionarCarrosOcorrencias(id, codigo, matricula_fiscal,ocorrencia.replace("_-", " "));
                            i++;
                        }
                    }
                }
            }
        }).start();
    }

    private void InterfaceAtualizacao(){
        dialogBaixando.dismiss();
        if(tabela == 0)
            ((PrincipalActivity) context).AtualizarLista();
        else if(tabela == 1)
            ((ListaAdaptados) context).AtualizarLista();
        else if(tabela == 2)
            ((ListaExecoes) context).AtualizarLista();
    }
}
