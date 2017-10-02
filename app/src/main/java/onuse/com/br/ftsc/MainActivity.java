package onuse.com.br.ftsc;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Models.Tabela;

public class MainActivity extends AppCompatActivity {

    private EditText carro, tabela1, tabela2, roleta, origem_destino, tabela_hora, hora_chegada, hora_saida, motivo;
    private Button btnHoraChegada, btnHoraSaida, btnAdicionar, btnVerificar;

    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    private Tabela tabela = new Tabela();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carro = (EditText)findViewById(R.id.edtCarro);
        tabela1 = (EditText)findViewById(R.id.edtTabela1);
        tabela2 = (EditText)findViewById(R.id.edtTabela2);
        roleta = (EditText)findViewById(R.id.edtRoleta);
        origem_destino = (EditText)findViewById(R.id.edtOrigemDestino);
        tabela_hora = (EditText)findViewById(R.id.edtTabelaHora);
        hora_chegada = (EditText)findViewById(R.id.edtHoraChegada);
        hora_saida = (EditText)findViewById(R.id.edtHoraSaida);
        motivo = (EditText)findViewById(R.id.edtMotivo);

        btnHoraChegada = (Button)findViewById(R.id.btnHoraChegada);
        btnHoraSaida = (Button)findViewById(R.id.btnHoraSaida);
        btnAdicionar = (Button)findViewById(R.id.btnAdicionar);
        btnVerificar = (Button)findViewById(R.id.btnVerificar);

        btnHoraChegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora_chegada.setText(HoraAtual.Horario());
            }
        });

        btnHoraSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora_saida.setText(HoraAtual.Horario());
            }
        });

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Revisa();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabela.setCarro(Integer.parseInt(carro.getText().toString()));
                tabela.setTabela1(Integer.parseInt(tabela1.getText().toString()));
                tabela.setTabela2(Integer.parseInt(tabela2.getText().toString()));
                tabela.setRoleta(Integer.parseInt(roleta.getText().toString()));
                tabela.setOrigem_destino(origem_destino.getText().toString());
                tabela.setTabela_hora(tabela_hora.getText().toString());
                tabela.setHora_chegada(hora_chegada.getText().toString());
                tabela.setHora_saida(hora_saida.getText().toString());
                tabela.setMotivo(motivo.getText().toString());
                AdicionarDados();
            }
        });
    }

    private void Revisa(){
        try {
            bancoInterno = new BancoInterno(this);
            conn = bancoInterno.getWritableDatabase();
            repositorioAcoes = new RepositorioAcoes(conn);

            String lolo = null;
            lolo = repositorioAcoes.CoordenadasRepostas(lolo);
            Toast.makeText(MainActivity.this, lolo, Toast.LENGTH_LONG).show();
        } catch (Exception d) {

            Toast.makeText(MainActivity.this, "Erro no banco", Toast.LENGTH_LONG).show();
        }
    }

    private void AdicionarDados(){
        try {
            bancoInterno = new BancoInterno(this);
            conn = bancoInterno.getWritableDatabase();
            repositorioAcoes = new RepositorioAcoes(conn);

            repositorioAcoes.inserir(tabela);
        } catch (Exception d) {

            Toast.makeText(MainActivity.this, "Erro no banco", Toast.LENGTH_LONG).show();
        }
    }
}
