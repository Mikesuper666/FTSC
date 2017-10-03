package onuse.com.br.ftsc;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.ExcecoesAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Fragments.ExecaoFragment;
import onuse.com.br.ftsc.Models.Execoes;

public class ListaExecoes extends AppCompatActivity {
    private ListView listaExecoes;
    private ArrayAdapter<Execoes> arrayAdapter;
    private ArrayList<Execoes> execoes;
    private RadioButton radioNome, radioMatricula, radioExececao;
    private RadioGroup radioGroup;
    private Button btnAdicionarExecaoFragment;

    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_execoes);
        radioNome = (RadioButton)findViewById(R.id.radioNome);
        radioMatricula = (RadioButton)findViewById(R.id.radioMatricula);
        radioExececao = (RadioButton)findViewById(R.id.radioExececao);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        btnAdicionarExecaoFragment = (Button) findViewById(R.id.btnAdicionarExecaoFragment);
        /**************************************************
         * MONTA O LIST VIEW E ADAPTER
         **************************************************/
        execoes = new ArrayList<>();
        listaExecoes = (ListView)findViewById(R.id.listaExecoes
        );
        arrayAdapter = new ExcecoesAdapter(this, execoes);
        listaExecoes.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaExecoes.setNestedScrollingEnabled(true);
        }

        bancoInterno = new BancoInterno(this);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);

        AdicionarDados();

        btnAdicionarExecaoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudoFragmentExecoes, new ExecaoFragment(), "ExecaoFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("ExecaoFragment") == null) {
                    transaction.commit();
                }
            }
        });
    }

    private void AdicionarDados(){
        ArrayList<Execoes> execoesRecebido = new ArrayList<>();
        execoesRecebido = repositorioAcoes.TodasExecoes();
        for(int i = 0; i < execoesRecebido.size();){
            execoes.add(execoesRecebido.get(i));
            i++;
        }
        //notifica o array adapter das novas mudanças
        //Ordena as mensagems finais pela data
        final Collator col = Collator.getInstance();
        arrayAdapter.sort(new Comparator<Execoes>() {
            @Override
            public int compare(Execoes cv1, Execoes cv2) {
                return col.compare(cv2.getTipoExecao()+"",cv1.getTipoExecao()+"");
            }
        });
        Ordenar();
    }

    private void Ordenar(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(radioMatricula.isChecked()){
                    //Ordena as mensagems finais pela data
                    final Collator col = Collator.getInstance();
                    arrayAdapter.sort(new Comparator<Execoes>() {
                        @Override
                        public int compare(Execoes cv1, Execoes cv2) {
                            return col.compare(cv1.getId()+"",cv2.getId()+"");
                        }
                    });
                }else if(radioNome.isChecked()){
                    //Ordena as mensagems finais pela data
                    final Collator col = Collator.getInstance();
                    arrayAdapter.sort(new Comparator<Execoes>() {
                        @Override
                        public int compare(Execoes cv1, Execoes cv2) {
                            return col.compare(cv1.getNome(),cv2.getNome());
                        }
                    });
                }else if(radioExececao.isChecked()){
                    //Ordena as mensagems finais pela data
                    final Collator col = Collator.getInstance();
                    arrayAdapter.sort(new Comparator<Execoes>() {
                        @Override
                        public int compare(Execoes cv1, Execoes cv2) {
                            return col.compare(cv1.getTipoExecao()+"",cv2.getTipoExecao()+"");
                        }
                    });
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

    }
}
