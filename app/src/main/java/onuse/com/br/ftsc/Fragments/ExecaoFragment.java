package onuse.com.br.ftsc.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 02/10/17.
 */

public class ExecaoFragment  extends Fragment {

    private EditText fragExecoesNome, fragExecoesCodigo;
    private Spinner fragExecoesTipoExecao;
    private ImageView fragExecoesAdicionar, fragExecoesCancelar;

    //captura dos dados
    private int codigo, tipoExecao;
    private String nome;
    public ExecaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_execoes, container, false);

        fragExecoesNome = view.findViewById(R.id.fragExecoesNome);
        fragExecoesCodigo = view.findViewById(R.id.fragExecoesCodigo);
        fragExecoesTipoExecao = view.findViewById(R.id.fragExecoesTipoExecao);
        fragExecoesAdicionar = view.findViewById(R.id.fragExecoesAdicionar);
        fragExecoesCancelar = view.findViewById(R.id.fragAdaptadoCancelar);

        fragExecoesAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConfirmarCadastro()){
                    BancoInterno bancoInterno = new BancoInterno(getActivity());
                    SQLiteDatabase conn = bancoInterno.getWritableDatabase();
                    RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
                    repositorioAcoes.InserirNovaExecao(codigo,nome,tipoExecao);
                    Toast.makeText(getActivity(), "Gravando dados", Toast.LENGTH_LONG).show();
                }
            }
        });

        fragExecoesCancelar.setOnClickListener(new View.OnClickListener() {
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
        if(fragExecoesCodigo.length() == 6){
            codigo = Integer.parseInt(fragExecoesCodigo.getText().toString());
        }else{
            fragExecoesCodigo.setError("Matriculas contém 6 números");
            return false;
        }
        tipoExecao = fragExecoesTipoExecao.getId();
        if(fragExecoesNome.getText().equals("")){
            fragExecoesNome.setError("Porfavor digite um nome!");
            return false;
        }else{
            nome = fragExecoesNome.getText().toString();
        }
        return true;
    }
}