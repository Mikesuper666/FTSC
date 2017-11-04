package onuse.com.br.ftsc;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import onuse.com.br.ftsc.Adapter.ExcecoesAdapter;
import onuse.com.br.ftsc.Adapter.OcorrenciaDiretaAdapter;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.OcorrenciaDireta;

/**
 * Created by Maico on 01/11/2017.
 */

public class OcorrenciaDiretas extends AppCompatActivity implements View.OnClickListener {
    //para adapter
    private ListView listaOcorrenciaDireto;
    private ArrayAdapter<OcorrenciaDireta> arrayAdapter;
    private ArrayList<OcorrenciaDireta> ocorrenciaDiretas;

    private Button btnAdicionarOcorrenciaDiretoFragment, btnProcurarOcorrenciaDiretoFragment;
    private AutoCompleteTextView procurarMatriculaOcorrenciaDireto;
    private TextView btnOcorrenciaDiretoDeletarSelecionado, btnOcorrenciaDiretoCompartilharSelecionado;
    private RadioGroup radioGroupOcorrenciaDireto;
    private RadioButton radioDataOcorrenciaDireto, radioCarroOcorrenciaDireto, radioMatriculaOcorrenciaDireto;
    private ImageView btnAtalizarOcorrenciaDireto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocorrencia_direta);
        ConfigurarComponetes();
    }

    private void ConfigurarComponetes(){
        btnAdicionarOcorrenciaDiretoFragment = (Button)findViewById(R.id.btnAdicionarOcorrenciaDiretoFragment);
        btnProcurarOcorrenciaDiretoFragment = (Button)findViewById(R.id.btnProcurarOcorrenciaDiretoFragment);
        procurarMatriculaOcorrenciaDireto = (AutoCompleteTextView)findViewById(R.id.procurarMatriculaOcorrenciaDireto);
        btnOcorrenciaDiretoDeletarSelecionado = (TextView)findViewById(R.id.btnOcorrenciaDiretoDeletarSelecionado);
        btnOcorrenciaDiretoCompartilharSelecionado = (TextView)findViewById(R.id.btnOcorrenciaDiretoCompartilharSelecionado);
        radioGroupOcorrenciaDireto = (RadioGroup)findViewById(R.id.radioGroupOcorrenciaDireto);
        radioDataOcorrenciaDireto = (RadioButton)findViewById(R.id.radioDataOcorrenciaDireto);
        radioCarroOcorrenciaDireto = (RadioButton)findViewById(R.id.radioCarroOcorrenciaDireto);
        radioMatriculaOcorrenciaDireto = (RadioButton)findViewById(R.id.radioMatriculaOcorrenciaDireto);
        listaOcorrenciaDireto = (ListView)findViewById(R.id.listaOcorrenciaDireto);
        btnAtalizarOcorrenciaDireto = (ImageView)findViewById(R.id.btnAtalizarOcorrenciaDireto);
        listaOcorrenciaDireto = (ListView)findViewById(R.id.listaOcorrenciaDireto);

        btnAdicionarOcorrenciaDiretoFragment.setOnClickListener(this);
        btnProcurarOcorrenciaDiretoFragment.setOnClickListener(this);
        procurarMatriculaOcorrenciaDireto.setOnClickListener(this);
        btnOcorrenciaDiretoDeletarSelecionado.setOnClickListener(this);
        btnOcorrenciaDiretoCompartilharSelecionado.setOnClickListener(this);
        radioGroupOcorrenciaDireto.setOnClickListener(this);
        radioDataOcorrenciaDireto.setOnClickListener(this);
        radioCarroOcorrenciaDireto.setOnClickListener(this);
        radioCarroOcorrenciaDireto.setOnClickListener(this);
        radioMatriculaOcorrenciaDireto.setOnClickListener(this);
        listaOcorrenciaDireto.setOnClickListener(this);
        btnAtalizarOcorrenciaDireto.setOnClickListener(this);

        /**************************************************
         * MONTA O LIST VIEW E ADAPTER
         **************************************************/
        ocorrenciaDiretas = new ArrayList<>();
        arrayAdapter = new OcorrenciaDiretaAdapter(this, ocorrenciaDiretas);
        listaOcorrenciaDireto.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaOcorrenciaDireto.setNestedScrollingEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdicionarOcorrenciaDiretoFragment:
                Toast.makeText(OcorrenciaDiretas.this, "Adiiconar", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void AdicionarDados(){

    }
}
