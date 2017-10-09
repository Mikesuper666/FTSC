package onuse.com.br.ftsc.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineInsert;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.ListaExecoes;
import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.Models.Execoes;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_execoes, container, false);

        fragExecoesNome = view.findViewById(R.id.fragExecoesNome);
        fragExecoesCodigo = view.findViewById(R.id.fragExecoesCodigo);
        fragExecoesTipoExecao = view.findViewById(R.id.fragExecoesTipoExecao);
        fragExecoesAdicionar = view.findViewById(R.id.fragExecoesAdicionar);
        fragExecoesCancelar = view.findViewById(R.id.fragExecoesCancelar);

        fragExecoesAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConfirmarCadastro()){
                    BancoOnlineInsert bancoOnlineInsert = new BancoOnlineInsert(getActivity());
                    bancoOnlineInsert.conectarAobancoInsersao(0, codigo, nome, tipoExecao);
                    /*BancoInterno bancoInterno = new BancoInterno(getActivity());
                    SQLiteDatabase conn = bancoInterno.getWritableDatabase();
                    RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
                    repositorioAcoes.InserirNovaExecao(codigo,nome,tipoExecao);
                    Toast.makeText(getActivity(), "Gravado com sucesso!", Toast.LENGTH_LONG).show();
                    getActivity().finish();*/
                }
            }
        });


        /*
        * Obrigatorio para modificação das cores do spinnner
        * */
        SpinnerCustomizado spinnerCustomizado = new SpinnerCustomizado();
        spinnerCustomizado.Spinner(getActivity(), fragExecoesTipoExecao);
        /**
         * ***********************************************************************
         */

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
        if(fragExecoesCodigo.length() >= 4){
            codigo = Integer.parseInt(fragExecoesCodigo.getText().toString());
        }else{
            fragExecoesCodigo.setError("Número inválido para matricula");
            return false;
        }
        //pega o numero da exeção
        tipoExecao = fragExecoesTipoExecao.getSelectedItemPosition();
        nome = fragExecoesNome.getText().toString();
        if(nome.equals("") || nome == null){
            fragExecoesNome.setError("Porfavor digite um nome!");
            return false;
        }

        BancoInterno bancoInterno = new BancoInterno(getActivity());
        SQLiteDatabase conn;
        conn = bancoInterno.getWritableDatabase();
        RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
        ArrayList<Execoes> carrosRecebido = new ArrayList<>();
        carrosRecebido = repositorioAcoes.TodasExecoes();
        for(int i = 0; i < carrosRecebido.size();){
            if(codigo == carrosRecebido.get(i).getId()){
                Toast.makeText(getActivity(), "Esta matrícula já foi cadastrado!", Toast.LENGTH_LONG).show();
                return false;
            }
            i++;
        }
        return true;
    }
}