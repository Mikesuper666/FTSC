package onuse.com.br.ftsc.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.R;

/**
 * Created by Maico on 27/10/2017.
 */

public class OcorrenciasFragment extends Fragment {
    private TextView fragOcorrenciaData, fragOcorrenciaNome, fragOcorrenciaMatricula;
    private ImageView fragOcorrenciaSair, fragOcorrenciaCompartilhar;

    //Declaracação de classes
    private Preferencias preferencias;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_ocorrecia, container, false);
        fragOcorrenciaData = view.findViewById(R.id.fragOcorrenciaData);
        fragOcorrenciaSair = view.findViewById(R.id.fragOcorrenciaSair);
        fragOcorrenciaNome = view.findViewById(R.id.fragOcorrenciaNome);
        fragOcorrenciaMatricula = view.findViewById(R.id.fragOcorrenciaMatricula);
        fragOcorrenciaCompartilhar = view.findViewById(R.id.fragOcorrenciaCompartilhar);

        //Classes ativações
        preferencias = new Preferencias(getActivity());

        //Configuração de itens automáticos
        fragOcorrenciaData.setText("Data: "+HoraAtual.Data());
        fragOcorrenciaNome.setText("Colaborador: "+ preferencias.getNome());
        fragOcorrenciaMatricula.setText("Matrícula: "+ preferencias.getMatricula());

        //Compartilhar
        fragOcorrenciaCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Sair do fragment
        fragOcorrenciaSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return view;
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}