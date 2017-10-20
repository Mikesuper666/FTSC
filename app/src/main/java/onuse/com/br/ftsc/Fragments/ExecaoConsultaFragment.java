package onuse.com.br.ftsc.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.ExcecoesAdapter;
import onuse.com.br.ftsc.Adapter.OcorrenciaAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 15/10/17.
 */

public class ExecaoConsultaFragment extends Fragment {
    private ImageView btnConsultaExecoes;
    private int codigo;
    private ListView listaOcorrencias;
    private ArrayList<Ocorrencias> ocorrencias;
    private ArrayAdapter<Ocorrencias> arrayAdapter;
    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;

    private String[] funcao_arrays, horario_arrays, permissao_arrays;
    public ExecaoConsultaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_consulta_execoes, container, false);
        btnConsultaExecoes = view.findViewById(R.id.btnConsultaExecoes);
        //Dados recebidos da activitty anterior
        codigo = getArguments().getInt("CODIGO");
        int tipoExecao = getArguments().getInt("EXECAO");
        String nome = getArguments().getString("NOME");
        int funcao = getArguments().getInt("FUNCAO");
        int horario = getArguments().getInt("HORARIO");

        TextView fragConsultaExecaoCodigo = view.findViewById(R.id.fragConsultaExecaoCodigo);
        TextView fragConsultaExecaoNome = view.findViewById(R.id.fragConsultaExecaoNome);
        TextView fragConsultaAdaptadoExecaoTipo = view.findViewById(R.id.fragConsultaAdaptadoExecaoTipo);
        TextView fragConsultaFuncao = view.findViewById(R.id.fragConsultaFuncao);
        TextView fragConsultaoHorario = view.findViewById(R.id.fragConsultaoHorario);
        //declaração de arrays de xmls
        funcao_arrays = getActivity().getResources().getStringArray(R.array.funcao_array);
        permissao_arrays = getActivity().getResources().getStringArray(R.array.permissao_para);
        horario_arrays = getActivity().getResources().getStringArray(R.array.horario_array);
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
        listaOcorrencias = view.findViewById(R.id.consultaListaExecoes
        );
        arrayAdapter = new OcorrenciaAdapter(getActivity(), ocorrencias);
        listaOcorrencias.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaOcorrencias.setNestedScrollingEnabled(true);
        }

        AdicionarDados();

        btnConsultaExecoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fragConsultaExecaoNome.setText(nome.replace("_"," "));
        fragConsultaExecaoCodigo.setText(codigo+"");

        //Todos arrays anteriomente declarados recebem os dados finais de tradução aqui
        fragConsultaAdaptadoExecaoTipo.setText(permissao_arrays[tipoExecao]);
        fragConsultaFuncao.setText(funcao_arrays[funcao]);
        fragConsultaoHorario.setText(horario_arrays[horario]);

        LinearLayout corpoExecaoConsultar = view.findViewById(R.id.corpoExecaoConsultar);
        LinearLayout corpoExecaoConsultar2 = view.findViewById(R.id.corpoExecaoConsultar2);

        corpoExecaoConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        corpoExecaoConsultar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //retorna a view
        return view;
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

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}