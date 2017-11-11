package onuse.com.br.ftsc.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import onuse.com.br.ftsc.BancoDados.BancoOcorrencias;
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Remanejo;
import onuse.com.br.ftsc.R;

/**
 * Created by Maico on 28/10/2017.
 */

public class RemanejoFragment extends Fragment {
    private TextView fragRemanejoData, fragRemanejoProtocolo, fragRemanejoNomeFiscal, fragRemanejoMatricula;
    private ImageView fragRemanejoSair, fragRemanejoCompartilhar, fragRemanejoSalvar;
    private LinearLayout fragRemanejoCorpo, fragRemanejoCorpo2;
    private EditText fragRemanejoTabela, fragRemanejoDestino, fragRemanejoHorario, fragRemanejoCarro;
    private EditText fragRemanejoTabela2, fragRemanejoDestino2, fragRemanejoHorario2, fragRemanejoCarro2;
    private RadioGroup fragRemanejoEmpresa;
    private RadioButton fragRemanejoSoul,fragRemanejoVal;

    //Declaracação de classes
    private Remanejo remanejo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_remanejo, container, false);

        fragRemanejoSair = view.findViewById(R.id.fragRemanejoSair);
        fragRemanejoCompartilhar = view.findViewById(R.id.fragRemanejoCompartilhar);
        fragRemanejoSalvar = view.findViewById(R.id.fragRemanejoSalvar);
        fragRemanejoCorpo = view.findViewById(R.id.fragRemanejoCorpo);
        fragRemanejoCorpo2 = view.findViewById(R.id.fragRemanejoCorpo2);
        fragRemanejoProtocolo = view.findViewById(R.id.fragRemanejoProtocolo);
        fragRemanejoData = view.findViewById(R.id.fragRemanejoData);
        fragRemanejoNomeFiscal = view.findViewById(R.id.fragRemanejoNomeFiscal);
        fragRemanejoMatricula = view.findViewById(R.id.fragRemanejoMatricula);
        fragRemanejoTabela = view.findViewById(R.id.fragRemanejoTabela);
        fragRemanejoDestino = view.findViewById(R.id.fragRemanejoDestino);
        fragRemanejoHorario = view.findViewById(R.id.fragRemanejoHorario);
        fragRemanejoCarro = view.findViewById(R.id.fragRemanejoCarro);
        fragRemanejoTabela2 = view.findViewById(R.id.fragRemanejoTabela2);
        fragRemanejoDestino2 = view.findViewById(R.id.fragRemanejoDestino2);
        fragRemanejoHorario2 = view.findViewById(R.id.fragRemanejoHorario2);
        fragRemanejoCarro2 = view.findViewById(R.id.fragRemanejoCarro2);
        fragRemanejoEmpresa = view.findViewById(R.id.fragRemanejoEmpresa);
        fragRemanejoSoul = view.findViewById(R.id.fragRemanejoSoul);
        fragRemanejoVal = view.findViewById(R.id.fragRemanejoVal);

        //formatos de mascara para os numeros
        SimpleMaskFormatter horarioMascara = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher horarioMascarado = new MaskTextWatcher(fragRemanejoHorario, horarioMascara);
        fragRemanejoHorario.addTextChangedListener(horarioMascarado);

        SimpleMaskFormatter horarioMascara2 = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher horarioMascarado2 = new MaskTextWatcher(fragRemanejoHorario2, horarioMascara2);
        fragRemanejoHorario2.addTextChangedListener(horarioMascarado2);

        //Classes ativações
        Preferencias preferencias = new Preferencias(getActivity());
        remanejo = new Remanejo();

        //Receber Argumentos da classe mão desse fragment
        int protocolo = getArguments().getInt("PROTOCOLO");

        //Configuração de itens automáticos
        fragRemanejoProtocolo.setText(""+ HoraAtual.Protocolo(protocolo));
        fragRemanejoData.setText("Data: "+HoraAtual.Data());
        fragRemanejoNomeFiscal.setText("Colaborador: "+ preferencias.getNome());
        fragRemanejoMatricula.setText("Matrícula: "+ preferencias.getMatricula());
        fragRemanejoProtocolo.setId(protocolo);
        remanejo.setMatricula_fiscal(preferencias.getMatricula());
        remanejo.setId(protocolo);

        //Salvar no Banco
        fragRemanejoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConfirmarEnvio()){
                    BancoOcorrencias bancoOcorrencias = new BancoOcorrencias(getActivity());
                    bancoOcorrencias.UpdateRemanejo(0, remanejo);
                }
            }
        });

        //compartilhar
        fragRemanejoCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConfirmarEnvio()) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "Informações deste funcionário" +
                                    "\nData: " + remanejo.getData() +
                                    "\nHorário: " + remanejo.getHorario() +
                                    "\nCarro: " + remanejo.getCarro() +
                                    "\nDestino: " + remanejo.getDestino() +
                                    "\nTabela: " + remanejo.getTabela() +
                                    "\nRemanejado para:"+
                                    "\nHorário: " + remanejo.getHorario2() +
                                    "\nCarro: " + remanejo.getCarro2() +
                                    "\nDestino: " + remanejo.getDestino2() +
                                    "\nTabela: " + remanejo.getTabela2() +
                                    "\nRemanejado por:"+
                                    "\nMatrícula do fiscal:" + remanejo.getMatricula_fiscal());
                    startActivity(Intent.createChooser(intent, "Compartilhar este relatório"));
                    Toast.makeText(getActivity(), "Não esqueça de salvar caso contrário esses dados serão perdidos", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Para não fazer nada
        fragRemanejoCorpo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //sair
        fragRemanejoCorpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

    private boolean ConfirmarEnvio(){
        //Empresa
        fragRemanejoEmpresa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (fragRemanejoSoul.isChecked()) {
                    remanejo.setEmpresa(0);
                } else if (fragRemanejoVal.isChecked()) {
                    remanejo.setEmpresa(1);
                }
            }
        });
        //Tabela
        if(fragRemanejoTabela.getText().toString().isEmpty()){
            fragRemanejoTabela.setError("Preencher o número da tabela!");
            return false;
        }else{
            remanejo.setTabela(Integer.parseInt(fragRemanejoTabela.getText().toString()));
        }
        if(fragRemanejoTabela2.getText().toString().isEmpty()){
            fragRemanejoTabela2.setError("Preencher o número da tabela!");
            return false;
        }else{
            remanejo.setTabela2(Integer.parseInt(fragRemanejoTabela2.getText().toString()));
        }
        //Destino
        if(fragRemanejoDestino.getText().toString().isEmpty()){
            fragRemanejoDestino.setError("Preencher o destino do itinerário!");
            return false;
        }else{
            remanejo.setDestino(fragRemanejoDestino.getText().toString());
        }
        if(fragRemanejoDestino2.getText().toString().isEmpty()){
            fragRemanejoDestino2.setError("Preencher o destino do itinerário!");
            return false;
        }else{
            remanejo.setDestino2(fragRemanejoDestino2.getText().toString());
        }
        //horario
        if(fragRemanejoHorario.getText().toString().isEmpty() || fragRemanejoHorario.getText().length() < 5){
            fragRemanejoHorario.setError("Preencher o horário do itinerário!");
            return false;
        }else{
            remanejo.setHorario(Integer.parseInt(fragRemanejoHorario.getText().toString().replace(":","")));
        }
        if(fragRemanejoHorario2.getText().toString().isEmpty() || fragRemanejoHorario2.getText().length() < 5){
            fragRemanejoHorario2.setError("Preencher o horário do itinerário!");
            return false;
        }else{
            remanejo.setHorario2(Integer.parseInt(fragRemanejoHorario2.getText().toString().replace(":","")));
        }
        //carro
        if(fragRemanejoCarro.getText().toString().isEmpty() || fragRemanejoCarro.getText().length() < 4){
            fragRemanejoCarro.setError("Preencher o número do carro inválido!");
            return false;
        }else{
            remanejo.setCarro(Integer.parseInt(fragRemanejoCarro.getText().toString()));
        }
        if(fragRemanejoCarro2.getText().toString().isEmpty() || fragRemanejoCarro2.getText().length() < 4){
            fragRemanejoCarro2.setError("Preencher o número do carro inválido!");
            return false;
        }else{
            remanejo.setCarro2(Integer.parseInt(fragRemanejoCarro2.getText().toString()));
        }
        //Data
        remanejo.setData(Integer.parseInt(HoraAtual.Data().replace("/","")));
        return true;
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}
