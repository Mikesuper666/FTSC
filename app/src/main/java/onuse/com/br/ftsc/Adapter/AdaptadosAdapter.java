package onuse.com.br.ftsc.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 29/09/17.
 */

public class AdaptadosAdapter extends ArrayAdapter<Carros> {
    private Context context;
    private ArrayList<Carros> carros;
    private Carros carro;

    public AdaptadosAdapter(@NonNull Context context, ArrayList<Carros> objects) {
        super(context, 0, objects);
        this.context = context;
        this.carros = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        //verifica se a lista está preenchida
        if(carros != null)
        {
            //inicializa os objetos para montagem da lista
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view apartir do xml
            view = layoutInflater.inflate(R.layout.widget_adaptados, parent, false);

            //Recuperar elementos da tela
            TextView codigocarroAdaptado = (TextView)view.findViewById(R.id.codigocarroAdaptado); //Armazena o nome no textview
            TextView nomeAdaptado = (TextView)view.findViewById(R.id.nomeAdaptados); //Armazena a ultima conversa no textview
            TextView qtdeOcorrencias = (TextView)view.findViewById(R.id.qtdeAdaptado); //Armazena a ultima conversa no textview
            final CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkAdaptados);
            ImageView imagemCarroAdaptado = (ImageView) view.findViewById(R.id.imagemcarroAdaptado);

            //seta valores nos componetes de tela
            carro = carros.get(position);
            codigocarroAdaptado.setText(""+carro.getId());
            qtdeOcorrencias.setText(carro.getQtdeOcorrencias()+"");

            if(carro.getTipoCarro() == 0){
                //imageAdaptado.setImageResource(R.drawable.d_cmt);
                nomeAdaptado.setText("CMT");
            }else if(carro.getTipoCarro() == 1){
                //imageAdaptado.setImageResource(R.drawable.d_4portas);
                nomeAdaptado.setText("4 Portas");
            }else if(carro.getTipoCarro() == 2){
                //imageAdaptado.setImageResource(R.drawable.d_toco);
                nomeAdaptado.setText("Toco");
            }else if(carro.getTipoCarro() == 3){
                //imageAdaptado.setImageResource(R.drawable.d_articulado);
                nomeAdaptado.setText("Articulado");
            }else if(carro.getTipoCarro() == 4){
                //imageAdaptado.setImageResource(R.drawable.d_toco);
                nomeAdaptado.setText("Lotação/Micro");
            }else if(carro.getTipoCarro() == 5){
                //imageAdaptado.setImageResource(R.drawable.d_articulado);
                nomeAdaptado.setText("Alongado");
            }

            if(carro.getAdaptado() == 0){
                imagemCarroAdaptado.setImageResource(R.drawable.d_arrow);
            }else if(carro.getAdaptado() == 1){
                imagemCarroAdaptado.setImageResource(R.drawable.d_adaptado);
            }

            /*
             ****PÁRAMETROS REFERENTE AO CHECKBOX
             */

            checkbox.setTag(carro);
            if(carro.isChecado()) {
                checkbox.setVisibility(View.VISIBLE);
                checkbox.setChecked(carro.getEstaChecado());//seta se está checado
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox check = (CheckBox)    v;
                        Carros item = (Carros) check.getTag();//adiciona ao models se checou ou nao
                        item.setEstaChecado(check.isChecked());
                    }
                });
            }
        }
        return view;
    }
}