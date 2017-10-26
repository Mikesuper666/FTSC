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
import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.PrincipalActivity;

/**
 * Created by maico on 08/10/17.
 */

public class BancoOnlineInsert {
    private Context context;
    private String url, parametros;
    private ProgressDialog dialogBaixando;
    private int tabela;
    private Execoes execoes;
    private Carros carros;

    public BancoOnlineInsert(Context context) {
        this.context = context;
        execoes = new Execoes();
        carros = new Carros();
    }

    public void conectarAobancoInsersao(int numeroTabela, int idRecebido, String nomeRecebido, int execaoRecebido, int funcao, int horario) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        /*
        *PARA CADA TABELA RECEBENOS UM NUMERO INTEIRO
        * 0 PARA EXEÇÕES
        * 1 PARA ADAPTADOS
        * 2 PARA AS LINHAS (NÃO PREVIAMENTE IMPLMENTADO NESTA VERSAO)
         */
        if (networkInfo != null && networkInfo.isConnected()) {

            switch (numeroTabela){
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/insert-execao.php?id="+idRecebido+"&nome="+nomeRecebido.replace(" ","_")+"&tipoexecao="+execaoRecebido+"&funcao="+funcao+"&horario="+horario;
                    execoes.setId(idRecebido);
                    execoes.setNome(nomeRecebido);
                    execoes.setTipoExecao(execaoRecebido);
                    tabela = 0;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/insert-adaptados.php?id="+idRecebido+"&nome="+nomeRecebido+"&tipoexecao="+execaoRecebido;
                    carros.setId(idRecebido);
                    carros.setTipoCarro(Integer.parseInt(nomeRecebido));
                    carros.setAdaptado(execaoRecebido);
                    tabela = 1;
                    break;

            }
            parametros = "";
            new InserirDados().execute(url);
        }
    }

    public void InserirOcorrencias(int numeroTabela, String idRecebido, int codigo, int matriculaFiscal, String ocorrencia) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        /*
        *PARA CADA TABELA RECEBENOS UM NUMERO INTEIRO
        * 0 PARA EXEÇÕES
        * 1 PARA ADAPTADOS
        * 2 PARA AS LINHAS (NÃO PREVIAMENTE IMPLMENTADO NESTA VERSAO)
         */
        if (networkInfo != null && networkInfo.isConnected()) {

            switch (numeroTabela) {
                case 2:
                    url = "http://maicoheleno.890m.com/tcc/insert-ocorrencia.php?id="+idRecebido+"&matricula_func="+codigo+"&matricula_fiscal="+matriculaFiscal+"&ocorrencia="+ocorrencia;
                    tabela = 2;
                    break;
                case 3:
                    url = "http://maicoheleno.890m.com/tcc/insert-carros-ocorrencia.php?id="+idRecebido+"&codigo="+codigo+"&matricula_fiscal="+matriculaFiscal+"&ocorrencia="+ocorrencia;
                    tabela = 3;
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
            dialogBaixando.setMessage("Inserindo novos dados e atualizando...");
            dialogBaixando.setCancelable(false);
            dialogBaixando.show();
        }

        @Override
        protected void onPostExecute(String resultado) {
            BancoInterno bancoInterno = new BancoInterno(context);
            SQLiteDatabase conn = bancoInterno.getWritableDatabase();
            RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
            if(resultado != null) {
                if (resultado.contains("nao_registrado")) {
                    dialogBaixando.dismiss();
                    Toast.makeText(context, "Erro! Tente novamente!", Toast.LENGTH_LONG).show();
                } else {
                    dialogBaixando.dismiss();
                    Toast.makeText(context, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show();
                    if (tabela == 0) {
                        repositorioAcoes.InserirNovaExecao((int)execoes.getId(), execoes.getNome(), execoes.getTipoExecao());
                        ((ListaExecoes) context).InseridoBancoAposOnline();
                    }else if(tabela == 1) {
                        repositorioAcoes.InserirNovoCarro((int) carros.getId(), carros.getTipoCarro(), carros.getAdaptado());
                        ((ListaAdaptados) context).InseridoBancoAposOnline();
                    }else if(tabela == 2){
                          //nada acontece aqui por enquanto
                    }else if(tabela == 3){
                    //nada acontece aqui por enquanto
                    }else{
                        ((PrincipalActivity) context).AtualizarLista();
                    }
                }
            }else{
                dialogBaixando.dismiss();
                Toast.makeText(context, "Problema na conexão do banco", Toast.LENGTH_LONG).show();
            }
        }
    }
}
