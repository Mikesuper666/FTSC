package onuse.com.br.ftsc.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.OcorrenciaAdapter;
import onuse.com.br.ftsc.Adapter.OcorrenciaCarrosAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineDelete;
import onuse.com.br.ftsc.BancoDados.BancoOnlineInsert;
import onuse.com.br.ftsc.BancoDados.BancoOnlineLogin;
import onuse.com.br.ftsc.BancoDados.BancoOnlineUpdate;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.LoginActivity;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.Models.OcorrenciasCarros;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 03/10/17.
 */

public class AdaptadoAlteracaoFragment  extends Fragment {

    private EditText fragAlteracaoOcorrenciaAdaptado;
    private TextView fragAlteracaoAdaptadoCodigo;
    private Spinner fragAlteracaoAdaptadoTipoCarro, fragAlteracaoAdaptadoAdaptadoSN;
    private ImageView fragAlteracaoAdaptadoAdicionar, fragAlteracaoAdaptadoCancelar, fragAlteracaoOcorrenciaBtnAdaptado;

    //captura dos dados
    private int codigo, tipoCarro, adaptadoSN;
    private ListView listaOcorrencias;
    private ArrayList<OcorrenciasCarros> ocorrencias;
    private ArrayAdapter<OcorrenciasCarros> arrayAdapter;
    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;

    public AdaptadoAlteracaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_alteracao_adaptado, container, false);

        fragAlteracaoAdaptadoCodigo = view.findViewById(R.id.fragAlteracaoAdaptadoCodigo);
        fragAlteracaoAdaptadoTipoCarro = view.findViewById(R.id.fragAlteracaoAdaptadoTipoCarro);
        fragAlteracaoAdaptadoAdaptadoSN = view.findViewById(R.id.fragAlteracaoAdaptadoAdaptadoSN);
        fragAlteracaoAdaptadoAdicionar = view.findViewById(R.id.fragAlteracaoAdaptadoAdicionar);
        fragAlteracaoAdaptadoCancelar = view.findViewById(R.id.fragAlteracaoAdaptadoCancelar);
        fragAlteracaoOcorrenciaAdaptado = view.findViewById(R.id.fragAlteracaoOcorrenciaAdaptado);
        fragAlteracaoOcorrenciaBtnAdaptado = view.findViewById(R.id.fragAlteracaoOcorrenciaBtnAdaptado);

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
        listaOcorrencias = view.findViewById(R.id.alteracaoListaAdaptado
        );
        arrayAdapter = new OcorrenciaCarrosAdapter(getActivity(), ocorrencias);
        listaOcorrencias.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaOcorrencias.setNestedScrollingEnabled(true);
        }

        //Dados recebidos da activitty anterior
        codigo = getArguments().getInt("CODIGO");
        adaptadoSN = getArguments().getInt("ADAPTADO");
        tipoCarro = getArguments().getInt("TIPOCARRO");

        fragAlteracaoAdaptadoCodigo.setText(""+codigo);
        fragAlteracaoAdaptadoAdaptadoSN.setSelection(adaptadoSN);
        fragAlteracaoAdaptadoTipoCarro.setSelection(tipoCarro);

        fragAlteracaoAdaptadoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoCarro = fragAlteracaoAdaptadoTipoCarro.getSelectedItemPosition();
                adaptadoSN = fragAlteracaoAdaptadoAdaptadoSN.getSelectedItemPosition();

                BancoOnlineUpdate bancoOnlineUpdate = new BancoOnlineUpdate(getActivity());
                bancoOnlineUpdate.ConectarBancoUpdate(1, codigo, ""+tipoCarro, adaptadoSN,0,0);

            }
        });

        fragAlteracaoOcorrenciaBtnAdaptado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(getActivity());
                int matriculaFiscal = preferencias.getMatricula();
                String ocorrencia = fragAlteracaoOcorrenciaAdaptado.getText().toString();

                if(ocorrencia.equals("")){
                    Toast.makeText(getActivity(), "Escreva uma ocorrência!", Toast.LENGTH_LONG).show();
                }else{
                    BancoOnlineInsert bancoOnlineInsert = new BancoOnlineInsert(getActivity());
                    bancoOnlineInsert.InserirOcorrencias(3, HoraAtual.Horario(), codigo, matriculaFiscal, ocorrencia.replace(" ", "_-"));
                    repositorioAcoes.InserirOcorrenciaCarros(HoraAtual.Horario(), codigo, matriculaFiscal, ocorrencia);
                    AdicionarDados();
                    fragAlteracaoOcorrenciaAdaptado.setText("");
                }
            }
        });

        fragAlteracaoAdaptadoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

            /*
          METODO PARA DELETAR UMS OCORRENCIA
              */
        listaOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final OcorrenciasCarros ocorrenciaRecebido = ocorrencias.get(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                alerta.setTitle("Deletar essa ocorrência");
                alerta.setMessage("Você tem certeza que deseja deletar esta ocorrência?\n "+ ocorrenciaRecebido.getOcorrencia()+ " id "+ocorrenciaRecebido.getId());
                alerta.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        BancoOnlineDelete bancoOnlineDelete = new BancoOnlineDelete(getActivity());
                        bancoOnlineDelete.DeletarOcorrencias(4, ocorrenciaRecebido.getId());

                        repositorioAcoes.DeletarCarroOcorrencia(ocorrenciaRecebido.getId());
                        AdicionarDados();
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alerta.show();
            }
        });

        LinearLayout corpoAdaptadoAlterar = view.findViewById(R.id.corpoAdaptadoAlterar);
        LinearLayout corpoAdaptadoAlterar2 = view.findViewById(R.id.corpoAdaptadoAlterar2);

        corpoAdaptadoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        corpoAdaptadoAlterar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        AdicionarDados();
        return view;
    }

    public void AdicionarDados(){
        ocorrencias.clear();
        ArrayList<OcorrenciasCarros> ocorrenciasRecebido = new ArrayList<>();
        ocorrenciasRecebido = repositorioAcoes.TodasOcorrenciasCarros(codigo);
        for(int i = 0; i < ocorrenciasRecebido.size();){
            ocorrencias.add(ocorrenciasRecebido.get(i));
            i++;
        }
        //notifica o array adapter das novas mudanças
        //Ordena as mensagems finais pela data
        final Collator col = Collator.getInstance();
        arrayAdapter.sort(new Comparator<OcorrenciasCarros>() {
            @Override
            public int compare(OcorrenciasCarros cv1, OcorrenciasCarros cv2) {
                return col.compare(cv1.getId()+"",cv2.getId()+"");
            }
        });
        arrayAdapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}