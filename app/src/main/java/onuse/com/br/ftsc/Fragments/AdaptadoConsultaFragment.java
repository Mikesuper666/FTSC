package onuse.com.br.ftsc.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Adapter.OcorrenciaCarrosAdapter;
import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.OcorrenciasCarros;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 17/10/17.
 */

public class AdaptadoConsultaFragment extends Fragment{

    private int codigo, tipoCarro, adaptado;
    private ListView listaOcorrencias;
    private ArrayList<OcorrenciasCarros> ocorrencias;
    private ArrayAdapter<OcorrenciasCarros> arrayAdapter;

    //Strings dos xml serao recebidas aqui
    private String[] carros_arrays;
    private TypedArray imgAdaptadoSN, imgTipoCarro;

    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;

    public AdaptadoConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_consulta_adaptados, container, false);

        //casting de componentes deconsulta nap precisa ser global
        TextView fragConsultaAdaptadoCodigo = view.findViewById(R.id.fragConsultaAdaptadoCodigo);
        TextView fragConsultaAdaptadoTipoCarro = view.findViewById(R.id.fragConsultaAdaptadoTipoCarro);
        ImageView fragConsultaAdaptadoImagemAdaptado = view.findViewById(R.id.fragConsultaAdaptadoImagemAdaptado);
        ImageView fragConsultaTipoCarroAdaptados = view.findViewById(R.id.fragConsultaTipoCarroAdaptados);
        ImageView btnRetornar = view.findViewById(R.id.btnRetornar);
        ImageView btnConsultaCompartilhar = view.findViewById(R.id.btnConsultaCompartilhar);
        listaOcorrencias = view.findViewById(R.id.consultaListaAdaptados);

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
        arrayAdapter = new OcorrenciaCarrosAdapter(getActivity(), ocorrencias);
        listaOcorrencias.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaOcorrencias.setNestedScrollingEnabled(true);
        }

        //Dados recebidos da activitty anterior
        codigo = getArguments().getInt("CODIGO");
        adaptado = getArguments().getInt("ADAPTADO");
        tipoCarro = getArguments().getInt("TIPOCARRO");

        //declaração de arrays de xmls
        carros_arrays = getActivity().getResources().getStringArray(R.array.tipos_carros);
        imgAdaptadoSN = getResources().obtainTypedArray(R.array.imagens_adaptado);
        imgTipoCarro = getResources().obtainTypedArray(R.array.imagens_tipocarro);

        //configuração da tela
        fragConsultaAdaptadoCodigo.setText(codigo+"");
        fragConsultaAdaptadoTipoCarro.setText(carros_arrays[tipoCarro]);
        fragConsultaAdaptadoImagemAdaptado.setImageResource(imgAdaptadoSN.getResourceId(adaptado, -1));
        fragConsultaTipoCarroAdaptados.setImageResource(imgTipoCarro.getResourceId(tipoCarro, -1));

        btnRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        AdicionarDados();

        LinearLayout corpoAdaptadoConsulta = view.findViewById(R.id.corpoAdaptadoConsulta);
        LinearLayout corpoAdaptadoConsulta2 = view.findViewById(R.id.corpoAdaptadoConsulta2);

        corpoAdaptadoConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        corpoAdaptadoConsulta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnConsultaCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                                "Informações sobre este veículo"+
                                "\nCódigo: "+codigo +
                                "\nTipo de carro: "+carros_arrays[tipoCarro]+
                                "\nData: "+ HoraAtual.Data()+
                                "\nObservações deste veículo:\n"+
                                AdicionarObservacoes());
                startActivity(Intent.createChooser(intent, "Compartilhar este relatório"));
            }
        });
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

    private String AdicionarObservacoes(){
        ArrayList<OcorrenciasCarros> ocorrenciasRecebido = new ArrayList<>();
        ocorrenciasRecebido = repositorioAcoes.TodasOcorrenciasCarros(codigo);

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

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}