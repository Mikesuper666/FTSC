package onuse.com.br.ftsc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.chrisbanes.photoview.PhotoView;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.Permissoes;
import onuse.com.br.ftsc.Models.Linha;

public class PrincipalActivity extends AppCompatActivity {
    private Button btnNomeLinha, btnCodigoLinha;
    private TextView txtCodigoLinha, txtNomeLinha;
    private AutoCompleteTextView edtNomeLinha, edtCodigoLinha;
    private ImageView btnAdaptados, btnExcecoes, btnCompartilhar, btnAtualizarDados, buscarRota;
    private PhotoView imagemRota;
    private TypedArray img;
    private int numeroImagem = 0;
    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    //variaveis de ação e gettesetter
    private RepositorioAcoes repositorioAcoes;
    private Linha linha = new Linha();

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Valida as permissoes na classe estatica
        Permissoes.validaPermissoes(1, this, permissoesNecessarias);

        txtCodigoLinha = (TextView)findViewById(R.id.txtCodigoLinha);
        txtNomeLinha = (TextView)findViewById(R.id.txtNomeLinha);

        btnNomeLinha = (Button)findViewById(R.id.btnNomeLinha);
        btnCodigoLinha = (Button)findViewById(R.id.btnCodigoLinha);
        btnAdaptados = (ImageView)findViewById(R.id.btnAdaptados);
        btnExcecoes = (ImageView)findViewById(R.id.btnExcecoes);
        btnCompartilhar = (ImageView) findViewById(R.id.btnCompartilhar);
        btnAtualizarDados = (ImageView) findViewById(R.id.btnAtualizarDados);
        buscarRota = (ImageView) findViewById(R.id.buscarRota);
        imagemRota = (PhotoView) findViewById(R.id.imagemRota);

        bancoInterno = new BancoInterno(this);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);


        img = getResources().obtainTypedArray(R.array.images_rotas);

        ConfigurarAutoCompletarTexto();

        btnNomeLinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linha = repositorioAcoes.ResultadoNome(edtNomeLinha.getText().toString());

                if(linha.getNome_linha() !=null) {
                    txtNomeLinha.setText(linha.getNome_linha());
                    txtCodigoLinha.setText(linha.getCodigoLinha());
                    imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));
                }else{
                    txtNomeLinha.setText("CÓDIGO NÃO ENCONTRADO!");
                }
            }
        });

        btnCodigoLinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linha = repositorioAcoes.Resultado(edtCodigoLinha.getText().toString());

                if(linha.getNome_linha() !=null) {
                    txtNomeLinha.setText(linha.getNome_linha());
                    txtCodigoLinha.setText(linha.getCodigoLinha());
                    imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));
                }else{
                    txtNomeLinha.setText("CÓDIGO NÃO ENCONTRADO!");
                }
            }
        });

        btnAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DELETA O BANCO ATUAL OFFLINE DE LIINHAS
                DeletarLinhasAtuais();
            }
        });

        btnAdaptados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, ListaAdaptados.class);
                startActivity(i);
            }
        });

        btnExcecoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, ListaExecoes.class);
                startActivity(i);
            }
        });

        buscarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, RequisicaoWeb.class);
                startActivity(i);
            }
        });

        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Compartilhar();
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Sair do aplicativo");
        alertDialog.setMessage("Você tem certeza que deseja sair?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                return;
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void Compartilhar() {
        Drawable mDrawable = imagemRota.getDrawable();
        if (mDrawable != null) {
            Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
            Uri uri = Uri.parse(path);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "Código: "+txtNomeLinha.getText().toString()+" Linha: "+txtCodigoLinha.getText().toString());
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
    }

    private void DeletarLinhasAtuais() {

        repositorioAcoes.DeletarLinhas("nome_linha");
        BancoOnlineSelect crud = new BancoOnlineSelect(PrincipalActivity.this);
        crud.conectarAobanco(0);
    }

    /**]
     *PARA A ATUALIZAR ESTA VIEW ATRAVEZ DE UMA CLASSE NAO IMPLEMENTADA E UMA THREAD SECUNDARIA RODANDO ATRAVÉZ DELA
     */
    public void AtualizarLista(){
        runOnUiThread(new PrincipalActivity.AtualizarTextView("Post"));
    }

    private class AtualizarTextView implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private String text;

        public AtualizarTextView(final String text) {
            this.text = text;
        }

        @Override
        public void run() {
            ConfigurarAutoCompletarTexto();
        }
    }

    private void ConfigurarAutoCompletarTexto(){
        //Configuração do autoCompleteText
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodasRequisicoes(1,"nome_linha"));
        edtNomeLinha = (AutoCompleteTextView)
                findViewById(R.id.edtNomeLinha);
        edtNomeLinha.setAdapter(adapter);

        ArrayAdapter<String> adapterCodigo = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodasRequisicoes(3,"nome_linha"));
        edtCodigoLinha = (AutoCompleteTextView)
                findViewById(R.id.edtCodigoLinha);
        edtCodigoLinha.setAdapter(adapterCodigo);
    }
}
