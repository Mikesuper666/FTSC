package onuse.com.br.ftsc;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.AdaptadosAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineDelete;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Fragments.AdaptadoAlteracaoFragment;
import onuse.com.br.ftsc.Fragments.AdaptadoFragment;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Carros;

public class ListaAdaptados extends AppCompatActivity {
    private ArrayAdapter<Carros> arrayAdapter;
    private ArrayList<Carros> carros;
    private RadioButton radioTipoCarro, radioAdaptados, radioCodigoCarro;
    private RadioGroup radioGroupAdaptados;
    private ListView listaAdptados;
    private Button btnAdicionarAdaptado, btnProcurarrAdaptado;
    private ImageView btnAtualizarAdaptados;
    private AutoCompleteTextView edtCodigo;
    private Preferencias preferencias;

    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;
    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_adaptados);

        radioTipoCarro = (RadioButton)findViewById(R.id.radioTipoCarro);
        radioAdaptados = (RadioButton)findViewById(R.id.radioAdaptados);
        radioCodigoCarro = (RadioButton)findViewById(R.id.radioCodigoCarro);
        radioGroupAdaptados = (RadioGroup)findViewById(R.id.radioGroupAdaptados);
        btnAdicionarAdaptado = (Button) findViewById(R.id.btnAdicionarAdaptado);
        btnProcurarrAdaptado = (Button) findViewById(R.id.btnProcurarrAdaptado);
        btnAtualizarAdaptados = (ImageView) findViewById(R.id.btnAtualizarAdaptados);
        /**************************************************
         * MONTA O LIST VIEW E ADAPTER
         **************************************************/
        carros = new ArrayList<>();
        listaAdptados = (ListView)findViewById(R.id.listaAdaptados
        );
        arrayAdapter = new AdaptadosAdapter(this, carros);
        listaAdptados.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaAdptados.setNestedScrollingEnabled(true);
        }

        bancoInterno = new BancoInterno(this);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodasRequisicoes(0,"tipo_carro"));
        edtCodigo = (AutoCompleteTextView)findViewById(R.id.procurarCodigoAdaptado);
        edtCodigo.setAdapter(adapter);

        edtCodigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Carros carrosRecebido = repositorioAcoes.ResultadoCodigo(edtCodigo.getText().toString());
                carros.clear();

                /*
                Este metodo de if é o valor quando o edittext está vazio, ae eu reencho os valores
                 */
                if(i == 0 && i1 == 1 && i2 == 0){
                    AdicionarDados();
                }else if(carrosRecebido.getId() != 0) {
                    carros.add(carrosRecebido);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AdicionarDados();

        btnAdicionarAdaptado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudoFragment, new AdaptadoFragment(), "AdaptadoFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("AdaptadoFragment") == null) {
                    transaction.commit();
                }
            }
        });


        btnProcurarrAdaptado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carros carrosRecebido = repositorioAcoes.ResultadoCodigo(edtCodigo.getText().toString());
                carros.clear();
                if(carrosRecebido.getId() != 0) {
                    carros.add(carrosRecebido);
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(ListaAdaptados.this, "Requisição não encontrada", Toast.LENGTH_LONG).show();
                }

            }
        });//faz a busca do codigo no banco e retorna o valor no array

        /*
        * METODO DE ATUALIZAÇÃO ONLINE
        * */
        btnAtualizarAdaptados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AtualizarView();
            }
        });

        preferencias = new Preferencias(ListaAdaptados.this);
        //Registra o menu_alterar flutuante para a lista
        if(preferencias.getLogin() == 1){
        registerForContextMenu(listaAdptados);
        }else{
            btnAdicionarAdaptado.setVisibility(View.GONE);
        }
    }

    private void AtualizarView(){
        repositorioAcoes.DeletarLinhas("tipo_carro");
        BancoOnlineSelect crud = new BancoOnlineSelect(ListaAdaptados.this);
        crud.conectarAobanco(1);
    }

    private void AdicionarDados(){
        carros.clear();
        ArrayList<Carros> carrosRecebido = new ArrayList<>();
        carrosRecebido = repositorioAcoes.TodosAdaptados();
        for(int i = 0; i < carrosRecebido.size();){
            carros.add(carrosRecebido.get(i));
            i++;
        }
        //notifica o array adapter das novas mudanças
        //Ordena as mensagems finais pela data
        final Collator col = Collator.getInstance();
        arrayAdapter.sort(new Comparator<Carros>() {
            @Override
            public int compare(Carros cv1, Carros cv2) {
                return col.compare(cv1.getId()+"",cv2.getId()+"");
            }
        });
        Ordenar();
    }

    private void Ordenar(){
        radioGroupAdaptados.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(radioTipoCarro.isChecked()){
                    //Ordena as mensagems finais pela data
                    final Collator col = Collator.getInstance();
                    arrayAdapter.sort(new Comparator<Carros>() {
                        @Override
                        public int compare(Carros cv1, Carros cv2) {
                            return col.compare(cv1.getTipoCarro()+"",cv2.getTipoCarro()+"");
                        }
                    });
                }else if(radioAdaptados.isChecked()){
                    //Ordena as mensagems finais pela data
                    final Collator col = Collator.getInstance();
                    arrayAdapter.sort(new Comparator<Carros>() {
                        @Override
                        public int compare(Carros cv1, Carros cv2) {
                            return col.compare(cv2.getAdaptado()+"",cv1.getAdaptado()+"");
                        }
                    });
                }else if(radioCodigoCarro.isChecked()){
                    //Ordena as mensagems finais pela data
                    final Collator col = Collator.getInstance();
                    arrayAdapter.sort(new Comparator<Carros>() {
                        @Override
                        public int compare(Carros cv1, Carros cv2) {
                            return col.compare(cv1.getId()+"",cv2.getId()+"");
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
        final Carros carroMenus = (Carros) listaAdptados.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.alterar:

                /*
                Metodo que faz a busta do fragment pega os dados da liste view
                passa os dados para o fragment de alteração
                * */

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment AdaptadoAlteracaoFragment = new AdaptadoAlteracaoFragment();
                transaction.add(R.id.conteudoFragment, AdaptadoAlteracaoFragment, "AdaptadoAlteracaoFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("AdaptadoAlteracaoFragment") == null) {
                    transaction.commit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("CODIGO", Integer.parseInt(""+carroMenus.getId()));
                    bundle.putInt("ADAPTADO", carroMenus.getAdaptado());
                    bundle.putInt("TIPOCARRO", carroMenus.getTipoCarro());
                    AdaptadoAlteracaoFragment.setArguments(bundle);
                }

                return true;
            case R.id.delete:
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setTitle("Deletar carro");
                alerta.setMessage("Você tem certeza que deseja deletar este carro?");
                alerta.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repositorioAcoes.DeletarCarrro(carroMenus.getId());
                        BancoOnlineDelete bancoOnlineDelete = new BancoOnlineDelete(ListaAdaptados.this);
                        bancoOnlineDelete.conectarAobancoDeletar(1, (int)carroMenus.getId());
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
        runOnUiThread(new ListaAdaptados.AtualizarTextView("Post"));
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
        }
    }

    /**]
     *PARA A ATUALIZAR ESTA VIEW ATRAVEZ DE UMA CLASSE NAO IMPLEMENTADA E UMA THREAD SECUNDARIA RODANDO ATRAVÉZ DELA
     * NESSA SEGUNDA PARTE EXECUTAMOS PARA TAMBEM O FECHAMENTO DO FRAGMENT PARA NAO FICAR POLUIDO A TELA!
     */

    public void InseridoBancoAposOnline(){
        runOnUiThread(new ListaAdaptados.AtualizarAposInseridoOnline("Post"));
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
