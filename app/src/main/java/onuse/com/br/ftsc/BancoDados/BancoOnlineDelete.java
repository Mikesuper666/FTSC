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
import onuse.com.br.ftsc.PrincipalActivity;

/**
 * Created by maico on 08/10/17.
 */

public class BancoOnlineDelete {
    private Context context;
    private String url, parametros;
    private ProgressDialog dialogBaixando;
    private int tabela;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    public BancoOnlineDelete(Context context) {
        this.context = context;
        bancoInterno = new BancoInterno(this.context);
        conn = bancoInterno.getWritableDatabase();
    }

    public void conectarAobancoDeletar(int numeroTabela, int idRecebido) {
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
                    url = "http://maicoheleno.890m.com/tcc/delete-execao.php?id="+idRecebido;
                    tabela = 0;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/delete-adaptados.php?id="+idRecebido;
                    tabela = 1;
                    break;
                case 2:
                    url = "http://maicoheleno.890m.com/tcc/delete-linhas.php?id="+idRecebido;
                    tabela = 2;
                    break;
            }
            parametros = "";
            new DeletarDados().execute(url);
        }
    }

    private class DeletarDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {


            return Conexao.postDados(urls[0], parametros);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBaixando = new ProgressDialog(context);
            dialogBaixando.setMessage("Deletando e atualizando...");
            dialogBaixando.setCancelable(false);
            dialogBaixando.show();
        }

        @Override
        protected void onPostExecute(String resultado) {

            if (resultado != null) {
                if (resultado.contains("nao_existe_esse_dado")) {
                    dialogBaixando.dismiss();
                    Toast.makeText(context, "Esse dado não existe online!", Toast.LENGTH_LONG).show();
                } else {
                    dialogBaixando.dismiss();
                    if (tabela == 0) {
                        ((ListaExecoes) context).AtualizarLista();
                    } else if (tabela == 1) {
                        ((ListaAdaptados) context).AtualizarLista();
                    } else {
                        ((PrincipalActivity) context).AtualizarLista();
                    }
                    Toast.makeText(context, "Exclusão feita com sucesso!", Toast.LENGTH_LONG).show();
                }
            } else {
                dialogBaixando.dismiss();
                Toast.makeText(context, "Problema na conexão do banco", Toast.LENGTH_LONG).show();
            }
        }
    }
}
