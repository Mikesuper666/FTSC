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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.BancoOcorrencias;
import onuse.com.br.ftsc.BancoDados.BancoOnlineSelect;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Fragments.ExecaoConsultaFragment;
import onuse.com.br.ftsc.Fragments.ExecaoFragment;
import onuse.com.br.ftsc.Fragments.OcorrenciasFragment;
import onuse.com.br.ftsc.Fragments.RemanejoFragment;
import onuse.com.br.ftsc.Helper.Permissoes;
import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.Models.Linha;
import onuse.com.br.ftsc.Models.OcorrenciaDireta;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNomeLinha, btnCodigoLinha;
    private AutoCompleteTextView edtNomeLinha, edtCodigoLinha;
    private ImageView buscarRota, imagemRota;
    private TypedArray img;
    private String linhaString, codigoString;
    private int imagemAlvorada, imagemDestino;
    private ImageButton btn_att_revelar, btn_execoes_revelar, btn_adaptados_revelar, btn_compartilhar_revelar, btnOcorrencias, btnRemanejo, btnIdVirtual;
    private Button navAlvorada, navDestino;
    private LinearLayout revelar_itens, revelar_itens_seLogado;
    private boolean esconder = true;
    private boolean estaAberto = false;
    private Preferencias preferencias;

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

        bancoInterno = new BancoInterno(this);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);
        preferencias = new Preferencias(this);
        ConfigurarAutoCompletarTexto();
        IniciarComponentes();
    }

    private void IniciarComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnIdVirtual = (ImageButton) findViewById(R.id.btnIdVirtual);
        btnRemanejo = (ImageButton) findViewById(R.id.btnRemanejo);
        btnOcorrencias = (ImageButton) findViewById(R.id.btnOcorrencias);
        revelar_itens = (LinearLayout) findViewById(R.id.revelar_itens);
        revelar_itens_seLogado = (LinearLayout) findViewById(R.id.revelar_itens_seLogado);
        revelar_itens_seLogado.setVisibility(View.GONE);
        revelar_itens.setVisibility(View.GONE);
        if(preferencias.getLogin() == 1)
        {
            revelar_itens_seLogado.setVisibility(View.VISIBLE);
        }

        btn_att_revelar = (ImageButton) findViewById(R.id.btn_att_revelar);
        btn_execoes_revelar = (ImageButton) findViewById(R.id.btn_execoes_revelar);
        btn_adaptados_revelar = (ImageButton) findViewById(R.id.btn_adaptados_revelar);
        btn_compartilhar_revelar = (ImageButton) findViewById(R.id.btn_compartilhar_revelar);
        btnNomeLinha = (Button)findViewById(R.id.btnNomeLinha);
        btnCodigoLinha = (Button)findViewById(R.id.btnCodigoLinha);
        buscarRota = (ImageView) findViewById(R.id.buscarRota);
        imagemRota = (ImageView) findViewById(R.id.imagemRota);
        navAlvorada = (Button) findViewById(R.id.navAlvorada);
        navDestino = (Button) findViewById(R.id.navDestino);

        btn_att_revelar.setOnClickListener(this);
        btn_execoes_revelar.setOnClickListener(this);
        btn_adaptados_revelar.setOnClickListener(this);
        btn_compartilhar_revelar.setOnClickListener(this);
        btnNomeLinha.setOnClickListener(this);
        btnCodigoLinha.setOnClickListener(this);
        buscarRota.setOnClickListener(this);
        navAlvorada.setOnClickListener(this);
        navDestino.setOnClickListener(this);
        revelar_itens.setOnClickListener(this);
        btnOcorrencias.setOnClickListener(this);
        btnRemanejo.setOnClickListener(this);
        btnIdVirtual.setOnClickListener(this);
        imagemRota.setOnClickListener(this);

        //configurar a imagem inicial
        img = getResources().obtainTypedArray(R.array.images_rotas);
        imagemAlvorada = 109;
        imagemDestino = 109; //as variaveis recebem valores padrao do logo de imagem
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
            case R.id.navAlvorada:
                navAlvorada.setTextColor(getResources().getColor(R.color.paleta_azul_11));
                navDestino.setTextColor(getResources().getColor(R.color.paleta_azul_9));
                imagemRota.setImageResource(img.getResourceId(imagemAlvorada, -1));
                break;
            case R.id.navDestino:
                navDestino.setTextColor(getResources().getColor(R.color.paleta_azul_11));
                navAlvorada.setTextColor(getResources().getColor(R.color.paleta_azul_9));
                imagemRota.setImageResource(img.getResourceId(imagemDestino, -1));
                break;
            case R.id.btnNomeLinha://RECEBE DO BANCO INTERNO A INFORMAÇÃO
                linha = repositorioAcoes.ResultadoNome(edtNomeLinha.getText().toString());
                ConfiguraImagemElabel();
                break;
            case R.id.btnCodigoLinha://RECEBE DO BANCO INTERNO A INFORMAÇÃO
                linha = repositorioAcoes.Resultado(edtCodigoLinha.getText().toString());
                ConfiguraImagemElabel();
                break;
            case R.id.buscarRota:
                Bundle bundle = new Bundle();
                bundle.putString("LINHA", edtNomeLinha.getText().toString());
                Intent requisicaoweb = new Intent(PrincipalActivity.this, RequisicaoWeb.class);
                requisicaoweb.putExtras(bundle);
                startActivity(requisicaoweb);
                break;
            case R.id.imagemRota:
                Intent i = new Intent(PrincipalActivity.this, MapaZoom.class);
                Drawable bitmapDrawable = imagemRota.getDrawable();

                linha.setMapaImagem(bitmapDrawable);
                startActivity(i);
                break;
            case R.id.btnOcorrencias:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Nova ocorrência");
                alertDialog.setMessage("Você tem certeza que deseja gerar uma nova ocorrência?\nSerá gerado um novo protocolo para isso certifique-se que sua conexão está online.");
                alertDialog.setPositiveButton("Gerar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     //Ativar o banco de dados
                        BancoOcorrencias protocolo = new BancoOcorrencias(PrincipalActivity.this);
                        protocolo.InsercaoOcorrenciasProtocolo(0);
                    }
                });
                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
                break;
            case R.id.btnRemanejo:
                Intent ior = new Intent(PrincipalActivity.this, RemanejoActivity.class);
                startActivity(ior);
              /*  AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
                alertDialog2.setTitle("Novo remanejo");
                alertDialog2.setMessage("Você tem certeza que deseja gerar um novo remanejo?\nSerá gerado um novo protocolo para isso certifique-se que sua conexão está online.");
                alertDialog2.setPositiveButton("Gerar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Ativar o banco de dados
                        BancoOcorrencias protocolo = new BancoOcorrencias(PrincipalActivity.this);
                        protocolo.InsercaoOcorrenciasProtocolo(1);
                    }
                });
                alertDialog2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog2.show();*/
                break;
            case R.id.btnIdVirtual:
                Toast.makeText(PrincipalActivity.this, "Esta opção ainda não está ativada", Toast.LENGTH_LONG).show();
                Intent iod = new Intent(PrincipalActivity.this, OcorrenciaDiretas.class);
                startActivity(iod);
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
        if(estaAberto) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.conteudoPrincipal)).commit();
            estaAberto = false;
        }else if(esconder) {
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
        }else{
            EsconderView();
        }
    }

    private void Compartilhar() {
        Drawable mDrawable = imagemRota.getDrawable();

        if (!mDrawable.getCurrent().toString().contains("Vector")) {
            Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
            Uri uri = Uri.parse(path);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "Código: "+linhaString+" Linha: "+codigoString);
            startActivity(Intent.createChooser(intent, "Compartilhar esta imagem"));
        }
    }

    private void DeletarLinhasAtuais() {

        repositorioAcoes.DeletarLinhas("nome_linha");
        BancoOnlineSelect crud = new BancoOnlineSelect(PrincipalActivity.this);
        crud.conectarAobanco(0);
    }

    private void ConfigurarAutoCompletarTexto(){
        //Configuração do autoCompleteText
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodasRequisicoes(1,"nome_linha"));
        edtNomeLinha = (AutoCompleteTextView)
                findViewById(R.id.edtNomeLinha);
        edtNomeLinha.setAdapter(adapter);

        ArrayAdapter<String> adapterCodigo = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodasRequisicoes(4,"nome_linha"));
        edtCodigoLinha = (AutoCompleteTextView)
                findViewById(R.id.edtCodigoLinha);
        edtCodigoLinha.setAdapter(adapterCodigo);
    }

    private void ConfiguraImagemElabel(){
        if(linha.getNome_linha() !=null) {
            navAlvorada.setTextColor(getResources().getColor(R.color.paleta_azul_11));
            navDestino.setTextColor(getResources().getColor(R.color.paleta_azul_9));
            imagemAlvorada = linha.getImagem();
            imagemDestino = linha.getImagemDestino();
            edtNomeLinha.setText(linha.getNome_linha());
            edtCodigoLinha.setText(linha.getCodigoLinha());
            linhaString = edtNomeLinha.getText().toString();
            codigoString = edtCodigoLinha.getText().toString();
            imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));
        }else{
            Toast.makeText(PrincipalActivity.this,"LINHA NÃO ENCONTRADA!",Toast.LENGTH_LONG).show();
        }
    }

    /*
    Recebe os parametros apos ter gerado um protocolo no banco de dados
    parametro protocolo >> recebido do bando de dados e enviado para cá
    parametro tabela decidi qual dos fragmentos será aberto via tabela consultada conforme a tela escolhida
     */
    private void AbrirOcorrencias(int protocolo, int tabela){
        if(tabela == 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment ocorrenciasFragment = new OcorrenciasFragment();
            transaction.add(R.id.conteudoPrincipal, ocorrenciasFragment, "OcorrenciasFragment");
            transaction.addToBackStack(null);
            Bundle bundle = new Bundle();
            bundle.putInt("PROTOCOLO", protocolo);
            ocorrenciasFragment.setArguments(bundle);
            if (!estaAberto) {
                estaAberto = true;
                transaction.commit();
            }
        }else if(tabela == 1){
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
            Fragment remanejoFragment = new RemanejoFragment();
            transaction2.add(R.id.conteudoPrincipal, remanejoFragment, "RemanejoFragment");
            transaction2.addToBackStack(null); //Linha super importante para  o retorno do fragment
            Bundle bundle = new Bundle();
            bundle.putInt("PROTOCOLO", protocolo);
            remanejoFragment.setArguments(bundle);
            if(!estaAberto) {
                estaAberto = true;
                transaction2.commit();
            }
        }
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

    public void RecebendoProtocolo(int protocolo, int tabela){
        runOnUiThread(new PrincipalActivity.RecebendoProtocolo(protocolo, tabela));
    }

    private class RecebendoProtocolo implements Runnable {

        //Caso queiramos passar algum dado passe por aqui deixei isso para vc lembrar no futuro seu burro
        private int protocolo, tabela;
        public RecebendoProtocolo(final int protocolo, final int tabela) {
            this.protocolo = protocolo;
            this.tabela = tabela;
        }

        @Override
        public void run() {
            AbrirOcorrencias(protocolo, tabela);
        }
    }
}
