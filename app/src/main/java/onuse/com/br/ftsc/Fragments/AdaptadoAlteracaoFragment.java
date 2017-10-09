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

import java.util.ArrayList;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineDelete;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 03/10/17.
 */

public class AdaptadoAlteracaoFragment  extends Fragment {

    private EditText fragAlteracaoAdaptadoCodigo;
    private Spinner fragAlteracaoAdaptadoTipoCarro, fragAlteracaoAdaptadoAdaptadoSN;
    private ImageView fragAlteracaoAdaptadoAdicionar, fragAlteracaoAdaptadoCancelar;

    //captura dos dados
    private int codigo, tipoCarro, adaptadoSN;

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


                /*BancoInterno bancoInterno = new BancoInterno(getActivity());
                SQLiteDatabase conn = bancoInterno.getWritableDatabase();
                RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
                repositorioAcoes.AtualizarNovoCarro(codigo, tipoCarro, adaptadoSN);
                Toast.makeText(getActivity(), "Alterado com sucesso!", Toast.LENGTH_LONG).show();
                getActivity().finish();*/

            }
        });

        fragAlteracaoAdaptadoCancelar.setOnClickListener(new View.OnClickListener() {
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
}