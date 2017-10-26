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
import android.widget.TextView;
import android.widget.Toast;

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
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.Models.OcorrenciasCarros;

public class ListaExecoes extends AppCompatActivity {
    private ListView listaExecoes;
    private ArrayAdapter<Execoes> arrayAdapter;
    private ArrayList<Execoes> execoes;
    private RadioButton radioNome, radioMatricula, radioExececao;
    private RadioGroup radioGroup;
    private Button btnAdicionarExecaoFragment, btnProcurarExecaoFragment;
    private ImageView btnAtualizarExecaoFragment;
    private TextView btnExecoesDeletarSelecionado, btnExecoesCompartilharSelecionado;
    private AutoCompleteTextView edtMatricula;
    private Preferencias preferencias;
    private boolean estaChecado = false;
    private String[] funiconario_funcao;

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
        btnExecoesDeletarSelecionado = (TextView) findViewById(R.id.btnExecoesDeletarSelecionado);
        btnExecoesCompartilharSelecionado = (TextView) findViewById(R.id.btnExecoesCompartilharSelecionado);
        //declaração de arrays de xmls
        funiconario_funcao = getResources().getStringArray(R.array.funcao_array);
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
                    Ordenar();
                }else if(execoesRecebido.getId() != 0) {
                    execoes.add(execoesRecebido);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Ordenar();
        arrayAdapter.notifyDataSetChanged();

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
                if(!estaChecado)
                    AtualizarView();
                else
                    estaChecado = false; Visibilidade();
            }
        });

        btnProcurarExecaoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnExecoesCompartilharSelecionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartilhar();
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(radioMatricula.isChecked()){
                    AdicionarDados(0);
                }else if(radioNome.isChecked()){
                    AdicionarDados(1);
                }else if(radioExececao.isChecked()){
                    AdicionarDados(2);
                }
            }
        });
    }

    private void AtualizarView(){
        repositorioAcoes.DeletarLinhas("d_execoes");
        BancoOnlineSelect crud = new BancoOnlineSelect(ListaExecoes.this);
        crud.conectarAobanco(2);
    }

    public void AdicionarDados(int ordenarPor){
        execoes.clear();
        ArrayList<Execoes> execoesRecebido = new ArrayList<>();
        execoesRecebido = repositorioAcoes.TodasExecoes();


        if(ordenarPor == 0) {
            for (int i = 0; i < execoesRecebido.size(); ) {
                if(execoesRecebido.get(i).getQtdeExecoes() > 0) {
                    execoesRecebido.get(i).setChecado(estaChecado);
                    execoes.add(execoesRecebido.get(i));
                }
                i++;
            }
            //Ordena pela quantida de exeções
            final Collator col = Collator.getInstance();
            arrayAdapter.sort(new Comparator<Execoes>() {
                @Override
                public int compare(Execoes cv1, Execoes cv2) {
                    //TRUQUE PEGADO DO STACKOVERFLOW É MULTIPLICAR POR -1
                    return -1 * col.compare(cv1.getQtdeExecoes()+"",cv2.getQtdeExecoes()+"");
                }
            });
        }else if(ordenarPor == 1){
            for (int i = 0; i < execoesRecebido.size(); ) {
                execoesRecebido.get(i).setChecado(estaChecado);
                execoes.add(execoesRecebido.get(i));
                i++;
            }
            //Ordena pelo nome
            final Collator col = Collator.getInstance();
            arrayAdapter.sort(new Comparator<Execoes>() {
                @Override
                public int compare(Execoes cv1, Execoes cv2) {
                    return col.compare(cv1.getNome(),cv2.getNome());
                }
            });
        }else if(ordenarPor == 2){
            for (int i = 0; i < execoesRecebido.size(); ) {
                execoesRecebido.get(i).setChecado(estaChecado);
                execoes.add(execoesRecebido.get(i));
                i++;
            }
            //Ordena pelas matricula
            final Collator col = Collator.getInstance();
            arrayAdapter.sort(new Comparator<Execoes>() {
                @Override
                public int compare(Execoes cv1, Execoes cv2) {
                    return col.compare(cv1.getId()+"",cv2.getId()+"");
                }
            });
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void Ordenar(){
        if(radioMatricula.isChecked()){
            AdicionarDados(0);
        }else if(radioNome.isChecked()){
            AdicionarDados(1);
        }else if(radioExececao.isChecked()){
            AdicionarDados(2);
        }
    }

    /*
   ESTE METODO ENTRA NO BANCO DE DADOS OFFLINE DAS OCORRENCIAS E TRAS TODAS PARA FAZERMOS A VERIFICAÇÃO DE QUE TEM OU NAO OCORRENCIA
    */
    private int OcorreciasNumero(long codigo){
        ArrayList<Ocorrencias> ocorrencias = new ArrayList<>();
        ocorrencias = repositorioAcoes.TodasOcorrencias((int)codigo);
        return ocorrencias.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
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
            case R.id.selecionar:
                estaChecado = true;
                Visibilidade();
                Ordenar();
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

    public void AtualizarLista(int rotacao){
        if(rotacao == 1)
            runOnUiThread(new AtualizarTextView("Post"));
        else if(rotacao == 2)
            runOnUiThread(new AtualizarListaVisao("Post"));
    }

    private class AtualizarListaVisao implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarListaVisao(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
            Ordenar();
        }
    }

    private class AtualizarTextView implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarTextView(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
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

    private void RemoverFragment(){Ordenar();onBackPressed(); AtualizarView();}

    private void Visibilidade(){
        if(estaChecado) {
            btnExecoesCompartilharSelecionado.setVisibility(View.VISIBLE);
            //btnExecoesDeletarSelecionado.setVisibility(View.VISIBLE);
            btnAtualizarExecaoFragment.setImageResource(R.drawable.d_arrow2);
        }else{
            btnExecoesCompartilharSelecionado.setVisibility(View.GONE);
            //btnExecoesDeletarSelecionado.setVisibility(View.GONE);
            btnAtualizarExecaoFragment.setImageResource(R.drawable.d_atualizar);
            Ordenar();
        }
    }

    private void Compartilhar(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                "Informações sobre estes funcionários"+
                        "\nData: "+ HoraAtual.Data()+"\n"+
                        BuscarInformacoesSelecionados());
        if(BuscarInformacoesSelecionados().isEmpty())
            Toast.makeText(ListaExecoes.this, "Selecione pelo menos 1 funcionário", Toast.LENGTH_LONG).show();
        else
            startActivity(Intent.createChooser(intent, "Compartilhar este relatório"));
    }

    private String BuscarInformacoesSelecionados(){
        String ocorrencias = "";
        int funcionario = 1;
        for(int i = 0; i < execoes.size();){
            if(execoes.get(i).getEstaChecado()){
                ocorrencias +=
                        "\nFuncionário "+funcionario+
                                "\nCódigo: "+ execoes.get(i).getId() +
                                "\nNome: "+ execoes.get(i).getNome() +
                                "\nFunção: "+ funiconario_funcao[execoes.get(i).getFuncao()] +
                                "\nOcorrências deste funcionário:\n"+
                                AdicionarObservacoes(execoes.get(i).getId());
                funcionario++;
            }
            i++;
        }
        return ocorrencias;
    }

    private String AdicionarObservacoes(long codigo){
        ArrayList<Ocorrencias> ocorrenciasRecebido = new ArrayList<>();
        ocorrenciasRecebido = repositorioAcoes.TodasOcorrencias((int)codigo);

        String ocorrencia = "";
        if(ocorrenciasRecebido.size() == 0){
            return (ocorrencia = "Este funcionário não possui ocorrências\n");
        }else{
            for (int i = 0; i < ocorrenciasRecebido.size(); ) {
                ocorrencia += ocorrenciasRecebido.get(i).getOcorrencia() + "\n";
                i++;
            }
        }
        return ocorrencia;
    }
}
