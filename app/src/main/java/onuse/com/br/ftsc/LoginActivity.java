package onuse.com.br.ftsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import onuse.com.br.ftsc.BancoDados.BancoOnlineLogin;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.Fragments.AdaptadoFragment;
import onuse.com.br.ftsc.Fragments.LoginFragment;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Login;
import onuse.com.br.ftsc.SplashScreen.SplashScreen;

/**
 * Created by maico on 06/10/17.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView txtLogarVisitante;
    private ImageView txtModificarSenha, btnLogo;
    private Button btnLogar;
    private EditText edtSenha, edtEmail;
    private Preferencias preferencias;
    private Login login = new Login();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLogarVisitante = (TextView)findViewById(R.id.txtLogarVisitante);
        txtModificarSenha = (ImageView)findViewById(R.id.txtModificarSenha);
        btnLogo = (ImageView)findViewById(R.id.btnLogo);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        preferencias = new Preferencias(LoginActivity.this);
        preferencias.SalvarLogin(0, 0, "");/////////////////////////////////////////////////////////////<<<<<<<<<<<<<<<

        if(preferencias.getSplashScreen() == 0){
            Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
            startActivity(intent);
        }

        txtLogarVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtModificarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudoFragmentLogin, new LoginFragment(), "LoginFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("LoginFragment") == null) {
                    transaction.commit();
                }
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if(email.equals("") || senha.equals("")){
                    Toast.makeText(LoginActivity.this, "Senha ou email com campo vazio!", Toast.LENGTH_LONG).show();
                }else{
                    BancoOnlineLogin bancoOnlineLogin = new BancoOnlineLogin(LoginActivity.this);
                    bancoOnlineLogin.conectarAobanco(0, email, senha, "");
                }
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spashIntent = new Intent(LoginActivity.this, SplashScreen.class);
                startActivity(spashIntent);
            }
        });
    }

    /**]
     *PARA A ATUALIZAR ESTA VIEW ATRAVEZ DE UMA CLASSE NAO IMPLEMENTADA E UMA THREAD SECUNDARIA RODANDO ATRAVÉZ DELA
     * RECEBEMOS O RETORNO DO BANCO SE FOR POSITIVO E CONCLUIMOS A AÇÃO AQUI!!!
     */
    public void AtualizarLista(String matricula, String nome){
        login.setMatricula(Integer.parseInt(matricula));
        login.setUsuario(nome);
        runOnUiThread(new LoginActivity.AtualizarTextView("Post"));
    }

    private class AtualizarTextView implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarTextView(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
            LoginSucesso();
        }
    }

    private void LoginSucesso(){

        preferencias.SalvarLogin(1, login.getMatricula(), login.getUsuario());
        Toast.makeText(LoginActivity.this, "Bem vindo "+login.getUsuario()+"!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void AtualizarSenha(){
        runOnUiThread(new LoginActivity.AtualizarSenha("Post"));
    }

    private class AtualizarSenha implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarSenha(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
            onBackPressed();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
