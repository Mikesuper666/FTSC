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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import onuse.com.br.ftsc.BancoDados.BancoOcorrencias;
import onuse.com.br.ftsc.Helper.HoraAtual;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.OcorrenciaDireta;
import onuse.com.br.ftsc.R;

/**
 * Created by Maico on 27/10/2017.
 */

public class OcorrenciasFragment extends Fragment {
    private TextView fragOcorrenciaData, fragOcorrenciaNomeFiscal, fragOcorrenciaMatriculaFiscal, fragOcorrenciaProtocolo;
    private ImageView fragOcorrenciaSair, fragOcorrenciaCompartilhar, fragOcorrenciaSalvar;
    private EditText fragOcorrenciaCarro, fragOcorrenciaHorario, fragOcorrenciaTabela, fragOcorrenciaNome, fragOcorrenciaDestino, fragOcorrenciaMatricula, fragOcorrenciaDescricao;
    private RadioGroup fragOcorrenciaEmpresa;
    private RadioButton soul, val;
    private Spinner fragOcorrenciaFuncao;

    private LinearLayout fragOcorrenciaCorpo, fragOcorrenciaCorpo2;

    //Declaração da classe model de ocorrencias diretas
    private OcorrenciaDireta ocorrenciaDireta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_ocorrecia, container, false);
        fragOcorrenciaData = view.findViewById(R.id.fragOcorrenciaData);
        fragOcorrenciaSair = view.findViewById(R.id.fragOcorrenciaSair);
        fragOcorrenciaCarro = view.findViewById(R.id.fragOcorrenciaCarro);
        fragOcorrenciaHorario = view.findViewById(R.id.fragOcorrenciaHorario);
        fragOcorrenciaDestino = view.findViewById(R.id.fragOcorrenciaDestino);
        fragOcorrenciaNome = view.findViewById(R.id.fragOcorrenciaNome);
        fragOcorrenciaTabela = view.findViewById(R.id.fragOcorrenciaTabela);
        fragOcorrenciaMatricula = view.findViewById(R.id.fragOcorrenciaMatricula);
        fragOcorrenciaNomeFiscal = view.findViewById(R.id.fragOcorrenciaNomeFiscal);
        fragOcorrenciaMatriculaFiscal = view.findViewById(R.id.fragOcorrenciaMatriculaFiscal);
        fragOcorrenciaFuncao = view.findViewById(R.id.fragOcorrenciaFuncao);
        fragOcorrenciaDescricao = view.findViewById(R.id.fragOcorrenciaDescricao);
        fragOcorrenciaCompartilhar = view.findViewById(R.id.fragOcorrenciaCompartilhar);
        fragOcorrenciaSalvar = view.findViewById(R.id.fragOcorrenciaSalvar);
        fragOcorrenciaCorpo = view.findViewById(R.id.fragOcorrenciaCorpo);
        fragOcorrenciaCorpo2 = view.findViewById(R.id.fragOcorrenciaCorpo2);
        fragOcorrenciaProtocolo = view.findViewById(R.id.fragOcorrenciaProtocolo);
        fragOcorrenciaEmpresa = view.findViewById(R.id.fragOcorrenciaEmpresa);
        soul = view.findViewById(R.id.soul);
        val = view.findViewById(R.id.val);

        //formatos de mascara para os numeros
        SimpleMaskFormatter horarioMascara = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher horarioMascarado = new MaskTextWatcher(fragOcorrenciaHorario, horarioMascara);
        fragOcorrenciaHorario.addTextChangedListener(horarioMascarado);

        //Classes ativações
        Preferencias preferencias = new Preferencias(getActivity());
        ocorrenciaDireta = new OcorrenciaDireta();

        //Receber Argumentos da classe mão desse fragment
        int protocolo = getArguments().getInt("PROTOCOLO");

        //Configuração de itens automáticos
        fragOcorrenciaProtocolo.setText(""+HoraAtual.Protocolo(protocolo));
        fragOcorrenciaData.setText("Data: "+HoraAtual.Data());
        fragOcorrenciaNomeFiscal.setText("Colaborador: "+ preferencias.getNome());
        fragOcorrenciaMatriculaFiscal.setText("Matrícula: "+ preferencias.getMatricula());
        ocorrenciaDireta.setId(protocolo);
        ocorrenciaDireta.setMatriculaFiscal(preferencias.getMatricula());

        //Salvar no Banco
        fragOcorrenciaSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConfirmarEnvio()){
                    BancoOcorrencias bancoOcorrencias = new BancoOcorrencias(getActivity());
                    bancoOcorrencias.UpdateOcorrencias(0, ocorrenciaDireta);
                }
            }
        });

        //Compartilhar
        fragOcorrenciaCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartilhar();
            }
        });

        //Para não fazer nada
        fragOcorrenciaCorpo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //sair
        fragOcorrenciaCorpo.setOnClickListener(new View.OnClickListener() {
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

    private void  Compartilhar(){
        if(ConfirmarEnvio()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Informações deste funcionário" +
                            "\nMatrícula: " + ocorrenciaDireta.getMatricula() +
                            "\nNome: " + ocorrenciaDireta.getColaborador() +
                            "\nFunção: " + fragOcorrenciaFuncao.getSelectedItem().toString() +
                            "\nData: " + HoraAtual.Data() +
                            "\nCarro: " + ocorrenciaDireta.getCarro() +
                            "\nDestino: " + ocorrenciaDireta.getDestino() +
                            "\nDescrição da ocorrência:\n" +
                            ocorrenciaDireta.getDescricao());
            startActivity(Intent.createChooser(intent, "Compartilhar este relatório"));
            Toast.makeText(getActivity(), "Não esqueça de salvar caso contrário esses dados serão perdidos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean ConfirmarEnvio(){
        if(fragOcorrenciaCarro.getText().toString().isEmpty() || fragOcorrenciaCarro.getText().length() < 4){
            fragOcorrenciaCarro.setError("Preencher o número do carro inválido!");
            return false;
        }else{
            ocorrenciaDireta.setCarro(Integer.parseInt(fragOcorrenciaCarro.getText().toString()));
        }
        if(fragOcorrenciaHorario.getText().toString().isEmpty()){
            fragOcorrenciaHorario.setError("Preencher o horário do itinerário!");
            return false;
        }else{
            ocorrenciaDireta.setHorario(Integer.parseInt(fragOcorrenciaHorario.getText().toString().replace(":","")));
        }
        if(fragOcorrenciaTabela.getText().toString().isEmpty()){
            fragOcorrenciaTabela.setError("Preencher o numero da tabela!");
            return false;
        }else{
            ocorrenciaDireta.setTabela(Integer.parseInt(fragOcorrenciaTabela.getText().toString()));
        }
        fragOcorrenciaEmpresa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (soul.isChecked()) {
                    ocorrenciaDireta.setEmpresa(0);
                } else if (val.isChecked()) {
                    ocorrenciaDireta.setEmpresa(1);
                }
            }
        });
        if(fragOcorrenciaDestino.getText().toString().isEmpty()){
            fragOcorrenciaDestino.setError("Preencher o destino do itinerário!");
            return false;
        }else{
            ocorrenciaDireta.setDestino(fragOcorrenciaDestino.getText().toString());
        }
        if(fragOcorrenciaMatricula.getText().toString().isEmpty()){
            fragOcorrenciaMatricula.setError("Preencher o destino a matrícula do funcionário!");
            return false;
        }else{
            ocorrenciaDireta.setMatricula(Integer.parseInt(fragOcorrenciaMatricula.getText().toString()));
        }
        if(fragOcorrenciaNome.getText().toString().isEmpty()){
            fragOcorrenciaNome.setError("Preencher o nome do colaborador!");
            return false;
        }else{
            ocorrenciaDireta.setColaborador(fragOcorrenciaNome.getText().toString());
        }
        ocorrenciaDireta.setFuncao(fragOcorrenciaFuncao.getSelectedItemPosition());
        if(fragOcorrenciaDescricao.getText().toString().isEmpty()){
            fragOcorrenciaDescricao.setError("Preencher a descrição!");
            return false;
        }else{
            ocorrenciaDireta.setDescricao(fragOcorrenciaDescricao.getText().toString());
        }
        //Data
        ocorrenciaDireta.setData(Integer.parseInt(HoraAtual.Data().replace("/","")));
        return true;
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}