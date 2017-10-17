package onuse.com.br.ftsc.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.OcorrenciaAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineUpdate;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 03/10/17.
 */

public class ExcecaoAlteracaoFragment extends Fragment{
    private TextView fragAlteracaoExecaoCodigo;
    private EditText fragAlteracaoExecaoNome, fragAlteracaoOcorrenciaEdit;
    private Spinner fragAlteracaoAdaptadoExecaoTipo;
    private ImageView fragAlteracaoExecaoAdicionar, fragAlteracaoExecaoCancelar, fragAlteracaoOcorrenciaBtn;
    //captura dos dados
    private int codigo, tipoExecao;
    private String nome;

    private ListView listaOcorrencias;
    private ArrayList<Ocorrencias> ocorrencias;
    private ArrayAdapter<Ocorrencias> arrayAdapter;
    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    public ExcecaoAlteracaoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_alteracao_execao, container, false);

        fragAlteracaoExecaoCodigo = view.findViewById(R.id.fragAlteracaoExecaoCodigo);
        fragAlteracaoOcorrenciaEdit = view.findViewById(R.id.fragAlteracaoOcorrenciaEdit);
        fragAlteracaoOcorrenciaBtn = view.findViewById(R.id.fragAlteracaoOcorrenciaBtn);
        fragAlteracaoExecaoNome = view.findViewById(R.id.fragAlteracaoExecaoNome);
        fragAlteracaoAdaptadoExecaoTipo = view.findViewById(R.id.fragAlteracaoAdaptadoExecaoTipo);
        fragAlteracaoExecaoAdicionar = view.findViewById(R.id.fragAlteracaoExecaoAdicionar);
        fragAlteracaoExecaoCancelar = view.findViewById(R.id.fragAlteracaoExecaoCancelar);

        /************************
         * Ligação para o banco de dados interno
         **********************************/
        bancoInterno = new BancoInterno(getActivity());
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        /**************************************************
         * MONTA O LIST VIEW E ADAPTER
         **************************************************/
        ocorrencias = new ArrayList<>();
        listaOcorrencias = view.findViewById(R.id.alteracaoListaExecoes
        );
        arrayAdapter = new OcorrenciaAdapter(getActivity(), ocorrencias);
        listaOcorrencias.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaOcorrencias.setNestedScrollingEnabled(true);
        }


        //Dados recebidos da activitty anterior
        codigo = getArguments().getInt("CODIGO");
        tipoExecao = getArguments().getInt("EXECAO");
        nome = getArguments().getString("NOME");

        fragAlteracaoExecaoCodigo.setText(""+codigo);
        fragAlteracaoExecaoNome.setText(nome.replace("_"," "));
        fragAlteracaoExecaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ConfirmarCadastro()) {
                    BancoOnlineUpdate bancoOnlineUpdate = new BancoOnlineUpdate(getActivity());
                    bancoOnlineUpdate.ConectarBancoUpdate(0, codigo, nome, tipoExecao);
                    /*BancoInterno bancoInterno = new BancoInterno(getActivity());
                    SQLiteDatabase conn = bancoInterno.getWritableDatabase();
                    RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
                    repositorioAcoes.AtualizarExecao(codigo, nome, tipoExecao);
                    Toast.makeText(getActivity(), "Alterado com sucesso!", Toast.LENGTH_LONG).show();
                    getActivity().finish();*/
                }
            }
        });

        fragAlteracaoOcorrenciaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Preferencias preferencias = new Preferencias(getActivity());
                    int matriculaFiscal = preferencias.getMatricula();
                    String ocorrencia = fragAlteracaoOcorrenciaEdit.getText().toString();
                    repositorioAcoes.InserirNovaOcorrencia(codigo, matriculaFiscal, ocorrencia);
                    AdicionarDados();
                    fragAlteracaoOcorrenciaEdit.setText("");
            }
        });

             /*
        * Obrigatorio para modificação das cores do spinnner

        SpinnerCustomizado spinnerCustomizado = new SpinnerCustomizado();
        spinnerCustomizado.Spinner(getActivity(), fragAlteracaoAdaptadoExecaoTipo);
        //***********************************************************************/

        AdicionarDados();

        fragAlteracaoExecaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return view;
    }
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    private boolean ConfirmarCadastro(){


        tipoExecao = fragAlteracaoAdaptadoExecaoTipo.getSelectedItemPosition();
        nome = fragAlteracaoExecaoNome.getText().toString();
        fragAlteracaoAdaptadoExecaoTipo.setSelection(tipoExecao);

        if(nome.equals("") || nome == null){
            fragAlteracaoExecaoNome.setError("Porfavor digite um nome!");
            return false;
        }
        return true;
    }

    public void AdicionarDados(){
        ocorrencias.clear();
        ArrayList<Ocorrencias> ocorrenciasRecebido = new ArrayList<>();
        ocorrenciasRecebido = repositorioAcoes.TodasOcorrencias(codigo);
        for(int i = 0; i < ocorrenciasRecebido.size();){
            ocorrencias.add(ocorrenciasRecebido.get(i));
            i++;
        }
        //notifica o array adapter das novas mudanças
        //Ordena as mensagems finais pela data
        final Collator col = Collator.getInstance();
        arrayAdapter.sort(new Comparator<Ocorrencias>() {
            @Override
            public int compare(Ocorrencias cv1, Ocorrencias cv2) {
                return col.compare(cv1.getId()+"",cv2.getId()+"");
            }
        });
        arrayAdapter.notifyDataSetChanged();
    }
}
