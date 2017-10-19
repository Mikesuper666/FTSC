package onuse.com.br.ftsc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.ExcecoesAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineDelete;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Fragments.ExcecaoAlteracaoFragment;
import onuse.com.br.ftsc.Fragments.ExecaoConsultaFragment;
import onuse.com.br.ftsc.Fragments.ExecaoFragment;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Execoes;

public class ListaExecoes extends AppCompatActivity {
    private ListView listaExecoes;
    private ArrayAdapter<Execoes> arrayAdapter;
    private ArrayList<Execoes> execoes;
    private RadioButton radioNome, radioMatricula, radioExececao;
    private RadioGroup radioGroup;
    private Button btnAdicionarExecaoFragment, btnProcurarExecaoFragment;
    private ImageView btnAtualizarExecaoFragment;
    private AutoCompleteTextView edtMatricula;
    private Preferencias preferencias;

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
        btnProcurarExecaoFragment = (Button) findViewById(R.id.btnProcurarExecaoFragment);
        btnAtualizarExecaoFragment = (ImageView) findViewById(R.id.btnAtualizarExecaoFragment);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodasRequisicoes(0,"d_execoes"));
        edtMatricula = (AutoCompleteTextView)findViewById(R.id.procurarMatriculaExecao);
        edtMatricula.setAdapter(adapter);

        edtMatricula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Execoes execoesRecebido = repositorioAcoes.ResultadoMatricula(edtMatricula.getText().toString());
                execoes.clear();
                /*
                Este metodo de if é o valor quando o edittext está vazio, ae eu reencho os valores
                 */
                if(i == 0 && i1 == 1 && i2 == 0){
                    AdicionarDados();
                }else if(execoesRecebido.getId() != 0) {
                    execoes.add(execoesRecebido);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

          /*
        * METODO DE ATUALIZAÇÃO ONLINE
        * */
        btnAtualizarExecaoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtualizarView();
            }
        });

        btnProcurarExecaoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        preferencias = new Preferencias(ListaExecoes.this);
        //Registra o menu_alterar flutuante para a lista
        if(preferencias.getLogin() == 1) {
            registerForContextMenu(listaExecoes);
        }else{
            btnAdicionarExecaoFragment.setVisibility(View.GONE);
        }

        listaExecoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment execaoConsultaFragment = new ExecaoConsultaFragment();
                transaction.add(R.id.conteudoFragmentExecoes, execaoConsultaFragment, "ExecaoConsultaFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("ExecaoConsultaFragment") == null) {

                    Execoes execoesConsulta = execoes.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("CODIGO", (int) execoesConsulta.getId());
                    bundle.putInt("EXECAO", execoesConsulta.getTipoExecao());
                    bundle.putString("NOME", execoesConsulta.getNome());
                    bundle.putInt("FUNCAO", execoesConsulta.getFuncao());
                    bundle.putInt("HORARIO", execoesConsulta.getHorario());
                    execaoConsultaFragment
                            .setArguments(bundle);
                    transaction.commit();
                }
            }
        });
    }

    private void AtualizarView(){
        repositorioAcoes.DeletarLinhas("d_execoes");
        BancoOnlineSelect crud = new BancoOnlineSelect(ListaExecoes.this);
        crud.conectarAobanco(2);
    }

    public void AdicionarDados(){
        execoes.clear();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alterar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Execoes execoesParaEnviar = (Execoes) listaExecoes.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.alterar:

                /*
                Metodo que faz a busta do fragment pega os dados da liste view
                passa os dados para o fragment de alteração
                * */

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment excecaoAlteracaoFragment = new ExcecaoAlteracaoFragment();
                transaction.add(R.id.conteudoFragmentExecoes, excecaoAlteracaoFragment, "excecaoAlteracaoFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("excecaoAlteracaoFragment") == null) {
                    transaction.commit();

                    Bundle bundle = new Bundle();
                    bundle.putInt("CODIGO", Integer.parseInt(""+execoesParaEnviar.getId()));
                    bundle.putInt("EXECAO", execoesParaEnviar.getTipoExecao());
                    bundle.putString("NOME", execoesParaEnviar.getNome());
                    bundle.putInt("FUNCAO", execoesParaEnviar.getFuncao());
                    bundle.putInt("HORARIO", execoesParaEnviar.getHorario());
                    excecaoAlteracaoFragment.setArguments(bundle);
                }


                return true;
            case R.id.delete:
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setTitle("Deletar execão");
                alerta.setMessage("Você tem certeza que deseja deletar esta exeção?");
                alerta.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repositorioAcoes.DeletarExecao(execoesParaEnviar.getId());

                        BancoOnlineDelete bancoOnlineDelete = new BancoOnlineDelete(ListaExecoes.this);
                        bancoOnlineDelete.conectarAobancoDeletar(0, (int)execoesParaEnviar.getId());
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alerta.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**]
     *PARA A ATUALIZAR ESTA VIEW ATRAVEZ DE UMA CLASSE NAO IMPLEMENTADA E UMA THREAD SECUNDARIA RODANDO ATRAVÉZ DELA
     */

    public void AtualizarLista(){
        runOnUiThread(new AtualizarTextView("Post"));
    }

    private class AtualizarTextView implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarTextView(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
            AdicionarDados();
            //nessa parte forçaremos a atualizao das ocorrencias
            repositorioAcoes.DeletarLinhas("ocorrencias");
            BancoOnlineSelect bancoOnlineSelect = new BancoOnlineSelect(ListaExecoes.this);
            bancoOnlineSelect.conectarAobanco(3);
        }
    }

    /**]
     *PARA A ATUALIZAR ESTA VIEW ATRAVEZ DE UMA CLASSE NAO IMPLEMENTADA E UMA THREAD SECUNDARIA RODANDO ATRAVÉZ DELA
     */

    public void InseridoBancoAposOnline(){
        runOnUiThread(new AtualizarAposInseridoOnline("Post"));
    }

    private class AtualizarAposInseridoOnline implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarAposInseridoOnline(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
            RemoverFragment();
        }
    }

    private void RemoverFragment(){AdicionarDados();onBackPressed(); AtualizarView();}
}
