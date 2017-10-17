package onuse.com.br.ftsc;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.chrisbanes.photoview.PhotoView;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.Permissoes;
import onuse.com.br.ftsc.Models.Linha;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNomeLinha, btnCodigoLinha;
    private AutoCompleteTextView edtNomeLinha, edtCodigoLinha;
    private ImageView btnAdaptados, btnExcecoes, btnCompartilhar, btnAtualizarDados, buscarRota;
    private PhotoView imagemRota;
    private TypedArray img;
    private String linhaString, codigoString;
    //variaiveis do bando de dados
    private BancoInterno bancoInterno;
    private SQLiteDatabase conn;

    private LinearLayout revelar_itens;
    private boolean esconder = true;
    private ImageButton btn_att_revelar, btn_execoes_revelar, btn_adaptados_revelar, btn_compartilhar_revelar;

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


        imagemRota = (PhotoView) findViewById(R.id.imagemRota);

        bancoInterno = new BancoInterno(this);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);

        img = getResources().obtainTypedArray(R.array.images_rotas);

        //configurar a imagem inicial
        linha.setImagem(109);
        imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));

        ConfigurarAutoCompletarTexto();
        IniciarComponentes();
        ConfigurarComponentesIniciais();
    }

    private void IniciarComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        revelar_itens = (LinearLayout) findViewById(R.id.revelar_itens);
        revelar_itens.setVisibility(View.GONE);

        btn_att_revelar = (ImageButton) findViewById(R.id.btn_att_revelar);
        btn_execoes_revelar = (ImageButton) findViewById(R.id.btn_execoes_revelar);
        btn_adaptados_revelar = (ImageButton) findViewById(R.id.btn_adaptados_revelar);
        btn_compartilhar_revelar = (ImageButton) findViewById(R.id.btn_compartilhar_revelar);

        btn_att_revelar.setOnClickListener(this);
        btn_execoes_revelar.setOnClickListener(this);
        btn_adaptados_revelar.setOnClickListener(this);
        btn_compartilhar_revelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EsconderView();
        switch (view.getId()) {

            case R.id.btn_att_revelar:
                DeletarLinhasAtuais();
                break;
            case R.id.btn_execoes_revelar:
                Intent ie = new Intent(PrincipalActivity.this, ListaExecoes.class);
                startActivity(ie);
                break;
            case R.id.btn_adaptados_revelar:
                Intent ia = new Intent(PrincipalActivity.this, ListaAdaptados.class);
                startActivity(ia);
                break;
            case R.id.btn_compartilhar_revelar:
                Compartilhar();
                break;
        }
    }

    private void EsconderView() {
        if (revelar_itens.getVisibility() == View.VISIBLE) {
            revelar_itens.setVisibility(View.GONE);
            esconder = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_opcoes:

                int cx = (revelar_itens.getLeft() + revelar_itens.getRight());
                int cy = revelar_itens.getTop();
                int radius = Math.max(revelar_itens.getWidth(), revelar_itens.getHeight());

                if (esconder) {
                    Animator anim = android.view.ViewAnimationUtils.createCircularReveal(revelar_itens, cx, cy, 0, radius);
                    revelar_itens.setVisibility(View.VISIBLE);
                    anim.start();
                    esconder = false;
                } else {
                    Animator anim = android.view.ViewAnimationUtils.createCircularReveal(revelar_itens, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            revelar_itens.setVisibility(View.INVISIBLE);
                            esconder = true;
                        }
                    });
                    anim.start();
                }

                return true;

            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (mDrawable != null && linha.getImagem() != 109) {
            Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
            Uri uri = Uri.parse(path);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "Código: "+linhaString+" Linha: "+codigoString);
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

    private void ConfigurarComponentesIniciais(){
        btnNomeLinha = (Button)findViewById(R.id.btnNomeLinha);
        btnCodigoLinha = (Button)findViewById(R.id.btnCodigoLinha);
        buscarRota = (ImageView) findViewById(R.id.buscarRota);

        btnNomeLinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linha = repositorioAcoes.ResultadoNome(edtNomeLinha.getText().toString());

                if(linha.getNome_linha() !=null) {
                    edtNomeLinha.setText(linha.getNome_linha());
                    edtCodigoLinha.setText(linha.getCodigoLinha());
                    linhaString = edtNomeLinha.getText().toString();
                    codigoString = edtCodigoLinha.getText().toString();
                    imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));
                }else{
                    edtNomeLinha.setText("CÓDIGO NÃO ENCONTRADO!");
                }
            }
        });

        btnCodigoLinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linha = repositorioAcoes.Resultado(edtCodigoLinha.getText().toString());

                if(linha.getNome_linha() !=null) {
                    edtNomeLinha.setText(linha.getNome_linha());
                    edtCodigoLinha.setText(linha.getCodigoLinha());
                    linhaString = edtNomeLinha.getText().toString();
                    codigoString = edtCodigoLinha.getText().toString();
                    imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));
                }else{
                    edtNomeLinha.setText("CÓDIGO NÃO ENCONTRADO!");
                }
            }
        });

        buscarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("LINHA", edtNomeLinha.getText().toString());
                Intent i = new Intent(PrincipalActivity.this, RequisicaoWeb.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        /*btnAdaptados = (ImageView)findViewById(R.id.btnAdaptados);
        btnExcecoes = (ImageView)findViewById(R.id.btnExcecoes);
        btnCompartilhar = (ImageView) findViewById(R.id.btnCompartilhar);
        btnAtualizarDados = (ImageView) findViewById(R.id.btnAtualizarDados);*/

        /*btnAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DELETA O BANCO ATUAL OFFLINE DE LIINHAS
                DeletarLinhasAtuais();
            }
        });*/

        /*btnAdaptados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, ListaAdaptados.class);
                startActivity(i);
            }
        });*/

        /*btnExcecoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, ListaExecoes.class);
                startActivity(i);
            }
        });*/

        /*btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Compartilhar();
            }
        });*/
    }
}
