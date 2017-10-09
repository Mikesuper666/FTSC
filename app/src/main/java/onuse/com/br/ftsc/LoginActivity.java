package onuse.com.br.ftsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import onuse.com.br.ftsc.Helper.Preferencias;

/**
 * Created by maico on 06/10/17.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView txtLogarVisitante;
    private Button btnLogar;
    private EditText edtSenha, edtEmail;
    private Preferencias preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLogarVisitante = (TextView)findViewById(R.id.txtLogarVisitante);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        preferencias = new Preferencias(LoginActivity.this);
        preferencias.SalvarLogin(0);

        txtLogarVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();
                if(email.equals("") && senha.equals("")){
                    preferencias.SalvarLogin(1);
                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Senha ou email errado!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
