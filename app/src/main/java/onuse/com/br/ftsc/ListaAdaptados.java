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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.AdaptadosAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Fragments.AdaptadoAlteracaoFragment;
import onuse.com.br.ftsc.Fragments.AdaptadoFragment;
import onuse.com.br.ftsc.Models.Carros;

public class ListaAdaptados extends AppCompatActivity {
    private ArrayAdapter<Carros> arrayAdapter;
    private ArrayList<Carros> carros;
    private RadioButton radioTipoCarro, radioAdaptados, radioCodigoCarro;
    private RadioGroup radioGroupAdaptados;
    private ListView listaAdptados;
    private Button btnAdicionarAdaptado;

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

        AdicionarDados();
        //Registra o menu_alterar flutuante para a lista
        registerForContextMenu(listaAdptados);
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
    }

    private void AdicionarDados(){
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
                        finish();
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repositorioAcoes.DeletarCarrro(carroMenus.getId());
                    }
                });
                alerta.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
