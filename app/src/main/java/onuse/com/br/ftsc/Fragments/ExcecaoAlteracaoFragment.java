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
import onuse.com.br.ftsc.BancoDados.BancoOnlineUpdate;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 03/10/17.
 */

public class ExcecaoAlteracaoFragment extends Fragment{
    private EditText fragAlteracaoExecaoCodigo, fragAlteracaoExecaoNome;
    private Spinner fragAlteracaoAdaptadoExecaoTipo;
    private ImageView fragAlteracaoExecaoAdicionar, fragAlteracaoExecaoCancelar;
    //captura dos dados
    private int codigo, tipoExecao;
    private String nome;
    public ExcecaoAlteracaoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_alteracao_execao, container, false);

        fragAlteracaoExecaoCodigo = view.findViewById(R.id.fragAlteracaoExecaoCodigo);
        fragAlteracaoExecaoNome = view.findViewById(R.id.fragAlteracaoExecaoNome);
        fragAlteracaoAdaptadoExecaoTipo = view.findViewById(R.id.fragAlteracaoAdaptadoExecaoTipo);
        fragAlteracaoExecaoAdicionar = view.findViewById(R.id.fragAlteracaoExecaoAdicionar);
        fragAlteracaoExecaoCancelar = view.findViewById(R.id.fragAlteracaoExecaoCancelar);

        //Dados recebidos da activitty anterior
        codigo = getArguments().getInt("CODIGO");
        tipoExecao = getArguments().getInt("EXECAO");
        nome = getArguments().getString("NOME");

        fragAlteracaoExecaoCodigo.setText(""+codigo);
        fragAlteracaoExecaoNome.setText(nome);
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

             /*
        * Obrigatorio para modificação das cores do spinnner
        * */
        SpinnerCustomizado spinnerCustomizado = new SpinnerCustomizado();
        spinnerCustomizado.Spinner(getActivity(), fragAlteracaoAdaptadoExecaoTipo);
        //***********************************************************************

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
}
