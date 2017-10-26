package onuse.com.br.ftsc.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.OcorrenciasCarros;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 18/10/17.
 */

public class OcorrenciaCarrosAdapter extends ArrayAdapter<OcorrenciasCarros> {
    private Context context;
    private ArrayList<OcorrenciasCarros> ocorrencias;
    private OcorrenciasCarros ocorrencia;
    public OcorrenciaCarrosAdapter(@NonNull Context context, @NonNull ArrayList<OcorrenciasCarros> objects) {
        super(context, 0, objects);
        this.context = context;
        this.ocorrencias = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        //verifica se a lista está preenchida
        if(ocorrencias != null)
        {
            //inicializa os objetos para montagem da lista
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view apartir do xml
            view = layoutInflater.inflate(R.layout.widget_ocorrencias, parent, false);

            //Recuperar elementos da tela
            TextView textViewNome = view.findViewById(R.id.nomeOcorrencias); //Armazena o nome no textview
            TextView textMatricula = view.findViewById(R.id.matriculaOcorrencias); //Armazena a ultima conversa no textview

            Preferencias preferencias = new Preferencias(context);
            //seta valores nos componetes de tela
            ocorrencia = ocorrencias.get(position);
            textViewNome.setText(ocorrencia.getOcorrencia());
            textMatricula.setText(ocorrencia.getMatricula_fiscal()+"");

        }
        return view;
    }
}