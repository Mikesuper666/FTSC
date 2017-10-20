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

    public void conectarAobanco(int metodoLogin, String matricula, String senha, String senhanova) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (metodoLogin){
                case 0:
                    url = "http://maicoheleno.890m.com/tcc/select-login.php?matricula="+matricula+"&senha="+senha;
                    break;
                case 1:
                    url = "http://maicoheleno.890m.com/tcc/update-login.php?matricula="+matricula+"&senha="+senha+"&senhanova="+senhanova;
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
                ProcessarLogin(resultado);
            } else if (resultado.contains("alterou")) {
                Toast.makeText(context, "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
                ((LoginActivity) context).AtualizarSenha();
            }
        }

        private void ProcessarLogin(final String resultado){

            String id = null;
            String matricula = null;
            String nome = null;
            String bloqueado = null;
            String linhasMisturadas[] = resultado.split("__");
            if (resultado != null) {
                for (int i = 1; i < linhasMisturadas.length;) {

                    if (linhasMisturadas[i].contains("^")) {
                        if(bloqueado.equals("0"))
                            ((LoginActivity) context).AtualizarLista(matricula, nome);
                        else
                            Toast.makeText(context, "Desculpe "+nome+" contate o administrador para resolver  o motivo de seu bloqueio!", Toast.LENGTH_LONG).show();
                        break;
                    } else if(i < linhasMisturadas.length){
                        id = linhasMisturadas[i];
                        i++;
                        nome = linhasMisturadas[i];
                        i++;
                        matricula = linhasMisturadas[i];
                        i++;
                        bloqueado = linhasMisturadas[i];
                        i++;
                    }
                }
            }
        }
    }
}
