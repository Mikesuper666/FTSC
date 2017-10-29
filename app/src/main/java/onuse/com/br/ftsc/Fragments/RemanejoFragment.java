package onuse.com.br.ftsc.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.R;

/**
 * Created by Maico on 28/10/2017.
 */

public class RemanejoFragment extends Fragment {
    private TextView fragOcorrenciaData;
    private ImageView fragRemanejoSair, fragRemanejoCompartilhar;

    //Declaracação de classes
    private Preferencias preferencias;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_remanejo, container, false);

        //compartilhar
        fragRemanejoCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //sair
        fragRemanejoSair.setOnClickListener(new View.OnClickListener() {
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
