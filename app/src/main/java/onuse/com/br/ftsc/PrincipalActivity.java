package onuse.com.br.ftsc;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import onuse.com.br.ftsc.BancoDados.BancoInterno;
import onuse.com.br.ftsc.BancoDados.RepositorioAcoes;
import onuse.com.br.ftsc.Helper.Permissoes;
import onuse.com.br.ftsc.Models.Linha;

public class PrincipalActivity extends AppCompatActivity {
    private EditText edtCodigoLinha;
    private Button btnNomeLinha, btnCodigoLinha;
    private TextView txtCodigoLinha, txtNomeLinha;
    private AutoCompleteTextView textView;
    private ImageView imagemRota, btnAdaptados, btnExcecoes, btnCompartilhar;
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
            Manifest.permission.INTERNET
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Valida as permissoes na classe estatica
        Permissoes.validaPermissoes(1, this, permissoesNecessarias);
        edtCodigoLinha = (EditText)findViewById(R.id.edtCodigoLinha);

        txtCodigoLinha = (TextView)findViewById(R.id.txtCodigoLinha);
        txtNomeLinha = (TextView)findViewById(R.id.txtNomeLinha);

        btnNomeLinha = (Button)findViewById(R.id.btnNomeLinha);
        btnCodigoLinha = (Button)findViewById(R.id.btnCodigoLinha);
        btnAdaptados = (ImageView)findViewById(R.id.btnAdaptados);
        btnExcecoes = (ImageView)findViewById(R.id.btnExcecoes);
        imagemRota = (ImageView) findViewById(R.id.imagemRota);
        btnCompartilhar = (ImageView) findViewById(R.id.btnCompartilhar);

        bancoInterno = new BancoInterno(this);
        conn = bancoInterno.getWritableDatabase();
        repositorioAcoes = new RepositorioAcoes(conn);

        img = getResources().obtainTypedArray(R.array.images_rotas);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, repositorioAcoes.TodosNomes());
        textView = (AutoCompleteTextView)
                findViewById(R.id.edtNomeLinha);
        textView.setAdapter(adapter);

        btnNomeLinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linha = repositorioAcoes.ResultadoNome(textView.getText().toString());

                if(linha.getNome_linha() !=null) {
                    txtCodigoLinha.setText(linha.getNome_linha());
                    txtNomeLinha.setText(linha.getId() + "");
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
                    txtCodigoLinha.setText(linha.getNome_linha());
                    txtNomeLinha.setText(linha.getId() + "");
                    imagemRota.setImageResource(img.getResourceId(linha.getImagem(), -1));
                }else{
                    txtNomeLinha.setText("CÓDIGO NÃO ENCONTRADO!");
                }
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
}
