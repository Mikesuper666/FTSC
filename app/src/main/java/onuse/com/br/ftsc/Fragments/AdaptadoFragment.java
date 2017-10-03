package onuse.com.br.ftsc.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.R;


public class AdaptadoFragment extends Fragment {

    private EditText fragAdaptadoCodigo;
    private Spinner fragAdaptadoTipoCarro, fragAdaptadoAdaptadoSN;
    private ImageView fragAdaptadoAdicionar, fragAdaptadoCancelar;

    //captura dos dados
    private int codigo, tipoCarro, adaptadoSN;
    public AdaptadoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_adaptado, container, false);

        fragAdaptadoCodigo = view.findViewById(R.id.fragAdaptadoCodigo);
        fragAdaptadoTipoCarro = view.findViewById(R.id.fragAdaptadoTipoCarro);
        fragAdaptadoAdaptadoSN = view.findViewById(R.id.fragAdaptadoAdaptadoSN);
        fragAdaptadoAdicionar = view.findViewById(R.id.fragAdaptadoAdicionar);
        fragAdaptadoCancelar = view.findViewById(R.id.fragAdaptadoCancelar);

        fragAdaptadoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConfirmarCadastro()){
                    BancoInterno bancoInterno = new BancoInterno(getActivity());
                    SQLiteDatabase conn = bancoInterno.getWritableDatabase();
                    RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
                    repositorioAcoes.InserirNovoCarro(codigo, tipoCarro,adaptadoSN);
                    Toast.makeText(getActivity(), "Gravado com sucesso!", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
        });

        fragAdaptadoCancelar.setOnClickListener(new View.OnClickListener() {
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
        if(fragAdaptadoCodigo.length() == 4){
            codigo = Integer.parseInt(fragAdaptadoCodigo.getText().toString());
        }else{
            fragAdaptadoCodigo.setError("Carros contém 4 números");
            return false;
        }

        tipoCarro = fragAdaptadoTipoCarro.getSelectedItemPosition();
        adaptadoSN = fragAdaptadoAdaptadoSN.getSelectedItemPosition();

        BancoInterno bancoInterno = new BancoInterno(getActivity());
        SQLiteDatabase conn;
        conn = bancoInterno.getWritableDatabase();
        RepositorioAcoes repositorioAcoes = new RepositorioAcoes(conn);
        ArrayList<Carros> carrosRecebido = new ArrayList<>();
        carrosRecebido = repositorioAcoes.TodosAdaptados();
        for(int i = 0; i < carrosRecebido.size();){
            if(codigo != carrosRecebido.get(i).getId()){
                //não faz nada
            }else{
                Toast.makeText(getActivity(), "Este carro já foi cadastrado!", Toast.LENGTH_LONG).show();
                return false;
            }
            i++;
        }


        return true;
    }
}