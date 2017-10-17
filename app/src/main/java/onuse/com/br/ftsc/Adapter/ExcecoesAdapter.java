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

import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 29/09/17.
 */

public class ExcecoesAdapter extends ArrayAdapter<Execoes> {
    private Context context;
    private ArrayList<Execoes> execoes;
    private Execoes execao;
    public ExcecoesAdapter(@NonNull Context context, @NonNull ArrayList<Execoes> objects) {
        super(context, 0, objects);
        this.context = context;
        this.execoes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        //verifica se a lista está preenchida
        if(execoes != null)
        {
            //inicializa os objetos para montagem da lista
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view apartir do xml
            view = layoutInflater.inflate(R.layout.widget_execoes, parent, false);

            //Recuperar elementos da tela
            TextView textViewNome = view.findViewById(R.id.nomeExecoes); //Armazena o nome no textview
            TextView textMatricula = view.findViewById(R.id.matriculaExeçoes); //Armazena a ultima conversa no textview
            ImageView imageConversa =  view.findViewById(R.id.imagemExecoes);

            //seta valores nos componetes de tela
            execao = execoes.get(position);
            textViewNome.setText(""+execao.getNome().replace("_"," "));//se o nome receber um "_" underline faremos a troca por um espaço
            textMatricula.setText(""+execao.getId());
            if(execao.getTipoExecao() == 0){
                imageConversa.setImageResource(R.drawable.d_nenhuma);
            }else if(execao.getTipoExecao() == 1) {
                imageConversa.setImageResource(R.drawable.d_barba);
            }else if(execao.getTipoExecao() == 2){
                    imageConversa.setImageResource(R.drawable.d_bone);
            }else if(execao.getTipoExecao() == 3){
                imageConversa.setImageResource(R.drawable.d_oculos);
            }else if(execao.getTipoExecao() == 4){
                imageConversa.setImageResource(R.drawable.d_cabelo);
            }else if(execao.getTipoExecao() == 5){
                imageConversa.setImageResource(R.drawable.d_uniforme);
            }
        }
        return view;
    }
}
