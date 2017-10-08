package onuse.com.br.ftsc.BancoDados;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by maico on 06/10/17.
 */

public class CRUD {
    Context context;
    String url, parametros;
    private ProgressDialog dialogBaixando;
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;

    public CRUD(Context context) {
        this.context = context;
    }

    public void conectarAobanco() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            url = "http://maicoheleno.890m.com/tcc/select-linhas.php";

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

            if (resultado == null) {
            } else if (resultado.contains("conexao_PHP_faliu")) {
                dialogBaixando.dismiss();
                Toast.makeText(context, "conxão sem retorno", Toast.LENGTH_LONG).show();
            }else {
                dialogBaixando.dismiss();
                AdicionarLinhas(resultado);
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

    private void InterfaceAtualizacao(){
        dialogBaixando.dismiss();
    }
}
