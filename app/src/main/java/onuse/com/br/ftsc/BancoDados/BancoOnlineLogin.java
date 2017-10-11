package onuse.com.br.ftsc.BancoDados;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import onuse.com.br.ftsc.LoginActivity;

/**
 * Created by maico on 09/10/17.
 */

public class BancoOnlineLogin {
    private Context context;
    private String url, parametros;
    private ProgressDialog dialogBaixando;

    public BancoOnlineLogin(Context context) {
        this.context = context;
    }

    public void conectarAobanco(int metodoLogin, String usuario, String senha, String senhanova) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (metodoLogin){
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/select-login.php?usuario="+usuario+"&senha="+senha;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/update-login.php?usuario="+usuario+"&senha="+senha+"&senhanova="+senhanova;
                    break;
            }
            parametros = "";

            new RegistrarLogin().execute(url);
        }
    }

    private class RegistrarLogin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBaixando = new ProgressDialog(context);
            dialogBaixando.setMessage("Efetuando Login...");
            dialogBaixando.setCancelable(false);
            dialogBaixando.show();
        }

        @Override
        protected void onPostExecute(String resultado) {

            dialogBaixando.dismiss();
            if (resultado == null) {
                Toast.makeText(context, "Problema na conexao com o banco de dados!", Toast.LENGTH_LONG).show();
            } else if (resultado.contains("login_invalido")) {
                Toast.makeText(context, "Login ou senha inválido", Toast.LENGTH_LONG).show();
            } else if (resultado.contains("login_sucesso")) {
                Toast.makeText(context, "Login Efetuado com sucesso!", Toast.LENGTH_LONG).show();
                ((LoginActivity) context).AtualizarLista();
            } else if (resultado.contains("alterou")) {
                Toast.makeText(context, "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
                ((LoginActivity) context).AtualizarSenha();
            }
        }
    }
}
