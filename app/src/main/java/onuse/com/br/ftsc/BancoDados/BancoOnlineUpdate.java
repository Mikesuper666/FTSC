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
 * Created by maico on 09/10/17.
 */

public class BancoOnlineUpdate {
    private Context context;
    private String url, parametros;
    private ProgressDialog dialogBaixando;
    private int tabela;

    public BancoOnlineUpdate(Context context) {
        this.context = context;
    }

    public void ConectarBancoUpdate(int numeroTabela, int codigo, String param1, int param2, int param3, int param4) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (numeroTabela){
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/update-execao2.php?id="+codigo+"&nome="+param1.replace(" ","_")+"&tipoexecao="+param2+"&funcao="+param3+"&horario="+param4;
                    tabela = 0;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/update-adaptados.php?id="+codigo+"&tipoCarro="+param1+"&d_adaptado="+param2;
                    tabela = 1;
                    break;
                case 2:
                    url = "http://maicoheleno.890m.com/tcc/select-execao.php";
                    tabela = 2;
                    break;
            }
            parametros = "";

            new RegistrarUpdate().execute(url);
        }
    }

    private class RegistrarUpdate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBaixando = new ProgressDialog(context);
            dialogBaixando.setMessage("Atualizando dados...");
            dialogBaixando.setCancelable(false);
            dialogBaixando.show();
        }

        @Override
        protected void onPostExecute(String resultado) {
            dialogBaixando.dismiss();
            if (resultado == null) {
                Toast.makeText(context, "Resultado nulo contate o administrador", Toast.LENGTH_LONG).show();
            }else if (resultado.contains("nao_alterou")) {
                Toast.makeText(context, "Houe algum erro no banco", Toast.LENGTH_LONG).show();
            }else if (resultado.contains("alterou")) {
                Toast.makeText(context, "Atualizou o dado com sucesso", Toast.LENGTH_LONG).show();
                if(tabela == 0)
                    ((ListaExecoes) context).InseridoBancoAposOnline();
                else if(tabela == 1)
                    ((ListaAdaptados) context).InseridoBancoAposOnline();
                else if(tabela == 2)
                    ((PrincipalActivity) context).AtualizarLista();
            }
        }
    }
}
