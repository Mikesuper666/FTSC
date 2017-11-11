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
import onuse.com.br.ftsc.Models.OcorrenciaDireta;
import onuse.com.br.ftsc.Models.Remanejo;
import onuse.com.br.ftsc.R;

/**
 * Created by Maico on 02/11/2017.
 */

public class RemanejoAdapter extends ArrayAdapter<Remanejo> {
    private Context context;
    private ArrayList<Remanejo> remanejos;
    private Remanejo remanejo;
    public RemanejoAdapter(@NonNull Context context, @NonNull ArrayList<Remanejo> objects) {
        super(context, 0, objects);
        this.context = context;
        this.remanejos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        //verifica se a lista está preenchida
        if(remanejos != null)
        {
            //inicializa os objetos para montagem da lista
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view apartir do xml
            view = layoutInflater.inflate(R.layout.widget_remanejo, parent, false);

            //Recuperar elementos da tela
            TextView ODempresa = view.findViewById(R.id.ODempresa);
            TextView ODcarro = view.findViewById(R.id.ODcarro);
            TextView ODHorario = view.findViewById(R.id.ODHorario);
            TextView ODDestino = view.findViewById(R.id.ODDestino);
            TextView ODid = view.findViewById(R.id.ODid);
            TextView ODcoloborador = view.findViewById(R.id.ODcoloborador);
            TextView ODMatricula = view.findViewById(R.id.ODMatricula);
            TextView ODFuncao = view.findViewById(R.id.ODFuncao);
            TextView ODTabela = view.findViewById(R.id.ODTabela);
            TextView ODDescricao = view.findViewById(R.id.ODDescricao);

            TextView matriculaFiscal = view.findViewById(R.id.ODmastriculaFiscal);
            TextView ODData = view.findViewById(R.id.ODData);

            //declaração de arrays de xmls (se for cmt/articulado essas coisas)
            String[] empresa_array;
            String[] funcao_array;
            empresa_array = context.getResources().getStringArray(R.array.empresa);
            funcao_array = context.getResources().getStringArray(R.array.funcao_array);

            Preferencias preferencias = new Preferencias(context);

            //seta valores nos componetes de tela
            remanejo = remanejos.get(position);
            //empresa
            ODempresa.setText(empresa_array[remanejo.getEmpresa()]);
            ODcarro.setText(remanejo.getCarro());
            ODHorario.setText(remanejo.getHorario());
            ODDestino.setText(remanejo.getDestino());
            ODid.setText(remanejo.getId());
            /*ODcoloborador.setText(remanejo.getColaborador());
            ODMatricula.setText(remanejo.getMatricula());
            ODFuncao.setText(funcao_array[remanejo.getFuncao()]);
            ODTabela.setText(remanejo.getTabela());
            ODDescricao.setText(remanejo.getDescricao());

            //informações do fiscal
            matriculaFiscal.setText(remanejo.getMatriculaFiscal()+"");
            ODData.setText(remanejo.getData()+"");*/

        }
        return view;
    }
}