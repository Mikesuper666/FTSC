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

import onuse.com.br.ftsc.Adapter.AdaptadosAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineDelete;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Fragments.AdaptadoAlteracaoFragment;
import onuse.com.br.ftsc.Fragments.AdaptadoConsultaFragment;
import onuse.com.br.ftsc.Fragments.AdaptadoFragment;
import onuse.com.br.ftsc.Fragments.ExecaoConsultaFragment;
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.Models.OcorrenciasCarros;

public class ListaAdaptados extends AppCompatActivity {
    private ArrayAdapter<Carros> arrayAdapter;
    private ArrayList<Carros> carros;
    private RadioButton radioTipoCarro, radioAdaptados, radioCodigoCarro;
    private RadioGroup radioGroupAdaptados;
    private ListView listaAdptados;
    private Button btnAdicionarAdaptado, btnProcurarrAdaptado;
    private ImageView btnAtualizarAdaptados;
    private TextView btnAdaptadosDeletarSelecionado, btnAdaptadosCompartilharSelecionado;
    private AutoCompleteTextView edtCodigo;
    private Preferencias preferencias;
    private boolean estaChecado = false;
    private String[] carros_arrays;

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
        btnAdaptadosDeletarSelecionado = (TextView)findViewById(R.id.btnAdaptadosDeletarSelecionado);
        btnAdaptadosCompartilharSelecionado = (TextView)findViewById(R.id.btnAdaptadosCompartilharSelecionado);
        //declaração de arrays de xmls
        carros_arrays = getResources().getStringArray(R.array.tipos_carros);
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
                    Ordenar();
                }else if(carrosRecebido.getId() != 0) {
                    carros.add(carrosRecebido);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Ordenar();

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
                if(!estaChecado)
                    AtualizarView();
                else
                    estaChecado = false; Visibilidade();
            }
        });

        btnAdaptadosCompartilharSelecionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartilhar();
            }
        });

        preferencias = new Preferencias(ListaAdaptados.this);
        //Registra o menu_alterar flutuante para a lista
        if(preferencias.getLogin() == 1){
        registerForContextMenu(listaAdptados);
        }else{
            btnAdicionarAdaptado.setVisibility(View.GONE);
        }

        listaAdptados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment adaptadoConsultaFragment = new AdaptadoConsultaFragment();
                transaction.add(R.id.conteudoFragment, adaptadoConsultaFragment, "AdaptadoConsultaFragment");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                if(fragmentManager.findFragmentByTag("AdaptadoConsultaFragment") == null) {

                    Carros carrosConsulta = carros.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("CODIGO", (int) carrosConsulta.getId());
                    bundle.putInt("TIPOCARRO", carrosConsulta.getTipoCarro());
                    bundle.putInt("ADAPTADO", carrosConsulta.getAdaptado());
                    adaptadoConsultaFragment
                            .setArguments(bundle);
                    transaction.commit();
                }
            }
        });
        radioGroupAdaptados.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(radioTipoCarro.isChecked()){
                    AdicionarDados(0);
                }else if(radioAdaptados.isChecked()){
                    AdicionarDados(2);
                }else if(radioCodigoCarro.isChecked()){
                    AdicionarDados(1);
                }
            }
        });
    }

    private void AtualizarView(){
        repositorioAcoes.DeletarLinhas("tipo_carro");
        BancoOnlineSelect crud = new BancoOnlineSelect(ListaAdaptados.this);
        crud.conectarAobanco(1);
    }

    private void AdicionarDados(int ordenarPor){
        carros.clear();
        ArrayList<Carros> carrosRecebido = new ArrayList<>();
        carrosRecebido = repositorioAcoes.TodosAdaptados();
        if(ordenarPor == 0) {
            for (int i = 0; i < carrosRecebido.size(); ) {
                carrosRecebido.get(i).setChecado(estaChecado);
                carros.add(carrosRecebido.get(i));
                i++;
            }
            //notifica o array adapter das novas mudanças
            final Collator col = Collator.getInstance();
            arrayAdapter.sort(new Comparator<Carros>() {
                @Override
                public int compare(Carros cv1, Carros cv2) {
                    return col.compare(cv1.getId() + "", cv2.getId() + "");
                }
            });
        }else if(ordenarPor == 1){
            for(int i = 0; i < carrosRecebido.size();){

                if(carrosRecebido.get(i).getQtdeOcorrencias() > 0) {
                    carrosRecebido.get(i).setChecado(estaChecado);
                    carros.add(carrosRecebido.get(i));
                }
                i++;
            }
            //Ordena as mensagems finais pela data
            final Collator col = Collator.getInstance();
            arrayAdapter.sort(new Comparator<Carros>() {
                @Override
                public int compare(Carros cv1, Carros cv2) {
                    //TRUQUE PEGADO DO STACKOVERFLOW É MULTIPLICAR POR -1
                    //return -1 * col.compare(OcorreciasNumero(cv1.getId())+"", OcorreciasNumero(cv2.getId())+"");
                    return -1 * col.compare(cv1.getQtdeOcorrencias()+"", cv2.getQtdeOcorrencias()+"");
                }
            });
        }else if(ordenarPor == 2){
            for(int i = 0; i < carrosRecebido.size();){

                if(carrosRecebido.get(i).getAdaptado() > 0) {
                    carrosRecebido.get(i).setChecado(estaChecado);
                    carros.add(carrosRecebido.get(i));
                }
                i++;
            }
            //Ordena as mensagems finais pela data
            final Collator col = Collator.getInstance();
            arrayAdapter.sort(new Comparator<Carros>() {
                @Override
                public int compare(Carros cv1, Carros cv2) {
                    return col.compare(cv2.getAdaptado()+"",cv1.getAdaptado()+"");
                }
            });
        }
        arrayAdapter.notifyDataSetChanged();
    }


    private void Ordenar(){
        if(radioTipoCarro.isChecked()){
            AdicionarDados(0);
        }else if(radioAdaptados.isChecked()){
            AdicionarDados(2);
        }else if(radioCodigoCarro.isChecked()){
            AdicionarDados(1);
        }
    }

    /*
    ESTE METODO ENTRA NO BANCO DE DADOS OFFLINE DAS OCORRENCIAS E TRAS TODAS PARA FAZERMOS A VERIFICAÇÃO DE QUE TEM OU NAO OCORRENCIA
     */
    private int OcorreciasNumero(long codigo){
        ArrayList<OcorrenciasCarros> ocorrencias = new ArrayList<>();
        ocorrencias = repositorioAcoes.TodasOcorrenciasCarros((int)codigo);
        return ocorrencias.size();
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
            case R.id.selecionar:
                estaChecado = true;
                Visibilidade();
                Ordenar();
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
            Ordenar();
            //nessa parte forçaremos a atualizao das ocorrencias
            repositorioAcoes.DeletarLinhas("carro_ocorrencias");
            BancoOnlineSelect bancoOnlineSelect = new BancoOnlineSelect(ListaAdaptados.this);
            bancoOnlineSelect.conectarAobanco(4);
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

    private void RemoverFragment(){
            Ordenar();
            AtualizarView();
            onBackPressed();
        }

    private void Visibilidade(){
        if(estaChecado) {
            btnAdaptadosCompartilharSelecionado.setVisibility(View.VISIBLE);
            //btnAdaptadosDeletarSelecionado.setVisibility(View.VISIBLE);
            btnAtualizarAdaptados.setImageResource(R.drawable.d_arrow2);
        }else{
            btnAdaptadosCompartilharSelecionado.setVisibility(View.GONE);
            //btnAdaptadosDeletarSelecionado.setVisibility(View.GONE);
            btnAtualizarAdaptados.setImageResource(R.drawable.d_atualizar);
            Ordenar();
        }
    }

    private void Compartilhar(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                "Informações sobre estes veículos"+
                        "\nData: "+ HoraAtual.Data()+"\n"+
                        BuscarInformacoesSelecionados());
        if(BuscarInformacoesSelecionados().isEmpty())
            Toast.makeText(ListaAdaptados.this, "Selecione pelo menos 1 carro", Toast.LENGTH_LONG).show();
        else
            startActivity(Intent.createChooser(intent, "Compartilhar este relatório"));
    }

    //rerorna a string
    private String BuscarInformacoesSelecionados(){
        String ocorrencias = "";
        int veiculo = 1;
        for(int i = 0; i < carros.size();){
            if(carros.get(i).getEstaChecado()){
                ocorrencias +=
                        "\nVeículo "+veiculo+
                        "\nCódigo: "+ carros.get(i).getId() +
                        "\nTipo de carro: "+ carros_arrays[carros.get(i).getTipoCarro()] +
                        "\nObservações deste veículo:\n"+
                        AdicionarObservacoes(carros.get(i).getId());
                veiculo++;
            }
            i++;
        }
        return ocorrencias;
    }

    private String AdicionarObservacoes(long codigo){
        ArrayList<OcorrenciasCarros> ocorrenciasRecebido = new ArrayList<>();
        ocorrenciasRecebido = repositorioAcoes.TodasOcorrenciasCarros((int)codigo);

        String ocorrencia = "";
        if(ocorrenciasRecebido.size() == 0){
            return (ocorrencia = "Nenhuma ocorrência para esse veículo\n");
        }else {
            for (int i = 0; i < ocorrenciasRecebido.size(); ) {
                ocorrencia += ocorrenciasRecebido.get(i).getOcorrencia() + "\n";
                i++;
            }
        }
        return ocorrencia;
    }
}
