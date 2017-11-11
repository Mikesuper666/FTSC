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

import onuse.com.br.ftsc.Adapter.RemanejoAdapter;
import onuse.com.br.ftsc.Models.Remanejo;

/**
 * Created by Maico on 02/11/2017.
 */

public class RemanejoActivity extends AppCompatActivity implements View.OnClickListener {
    //para adapter
    private ListView listaRemanejo;
    private ArrayAdapter<Remanejo> arrayAdapter;
    private ArrayList<Remanejo> remanejos;

    private Button btnAdicionarRemanejo, btnProcurarRemanejo;
    private AutoCompleteTextView procurarMatriculaRemanejo;
    private TextView btnRemanejoDeletarSelecionado, btnRemanejoCompartilharSelecionado;
    private RadioGroup radioGroupRemanejo;
    private RadioButton radioDataRemanejo, radioCarroRemanejo, radioMatriculaRemanejo;
    private ImageView btnAtalizarRemanejo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remanejo);
        ConfigurarComponetes();
    }

    private void ConfigurarComponetes() {
        btnAdicionarRemanejo = (Button)findViewById(R.id.btnAdicionarRemanejo);
        btnProcurarRemanejo = (Button)findViewById(R.id.btnProcurarRemanejo);
        procurarMatriculaRemanejo = (AutoCompleteTextView) findViewById(R.id.procurarMatriculaRemanejo);
        btnRemanejoDeletarSelecionado = (TextView) findViewById(R.id.btnRemanejoDeletarSelecionado);
        btnRemanejoCompartilharSelecionado = (TextView) findViewById(R.id.btnRemanejoCompartilharSelecionado);
        radioGroupRemanejo = (RadioGroup) findViewById(R.id.radioGroupRemanejo);
        radioDataRemanejo = (RadioButton) findViewById(R.id.radioDataRemanejo);
        radioCarroRemanejo = (RadioButton) findViewById(R.id.radioCarroRemanejo);
        radioMatriculaRemanejo = (RadioButton) findViewById(R.id.radioMatriculaRemanejo);
        btnAtalizarRemanejo = (ImageView) findViewById(R.id.btnAtalizarRemanejo);

        /**************************************************
         * MONTA O LIST VIEW E ADAPTER
         **************************************************/
        remanejos = new ArrayList<>();
        arrayAdapter = new RemanejoAdapter(this, remanejos);
        listaRemanejo.setAdapter(arrayAdapter);
        if(Build.VERSION.SDK_INT >= 21){
            listaRemanejo.setNestedScrollingEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdicionarRemanejo:
                Toast.makeText(RemanejoActivity.this, "Adiiconar", Toast.LENGTH_LONG).show();
                break;
        }
    }
}