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
            //ImageView imageAdaptado = (ImageView) view.findViewById(R.id.imagemAdaptados);
            ImageView imagemCarroAdaptado = (ImageView) view.findViewById(R.id.imagemcarroAdaptado);

            //seta valores nos componetes de tela
            carro = carros.get(position);
            codigocarroAdaptado.setText(""+carro.getId());

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
            }

            if(carro.getAdaptado() == 0){
                imagemCarroAdaptado.setImageResource(R.drawable.d_ncadeirante);
            }else if(carro.getAdaptado() == 1){
                imagemCarroAdaptado.setImageResource(R.drawable.d_cadeirante);
            }
        }
        return view;
    }
}