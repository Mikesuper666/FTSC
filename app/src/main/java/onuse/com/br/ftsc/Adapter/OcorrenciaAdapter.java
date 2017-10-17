package onuse.com.br.ftsc.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 16/10/17.
 */

public class OcorrenciaAdapter extends ArrayAdapter<Ocorrencias> {
    private Context context;
    private ArrayList<Ocorrencias> ocorrencias;
    private Ocorrencias ocorrencia;
    public OcorrenciaAdapter(@NonNull Context context, @NonNull ArrayList<Ocorrencias> objects) {
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