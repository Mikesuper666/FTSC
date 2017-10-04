package onuse.com.br.ftsc.BancoDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Linha;
import onuse.com.br.ftsc.Models.Tabela;

/**
 * Created by maico on 27/09/17.
 */

public class RepositorioAcoes{

    private SQLiteDatabase conn;

    public RepositorioAcoes(SQLiteDatabase conn){
        this.conn = conn;
    }

    public void inserir(Tabela tabela)
    {
        ContentValues values = new ContentValues();
        values.put("carro", tabela.getCarro() );
        values.put("origem_destino", tabela.getOrigem_destino() );
        values.put("tabela1", tabela.getTabela1() );
        values.put("tabela2", tabela.getTabela2() );
        values.put("tabela_hora", tabela.getTabela_hora() );
        values.put("hora_chegada", tabela.getHora_chegada() );
        values.put("hora_saida", tabela.getHora_saida() );
        values.put("roleta", tabela.getRoleta() );
        values.put("motivo", tabela.getMotivo() );

        conn.insertOrThrow("plataforma",null , values);
    }

    public String CoordenadasRepostas(String coordenada){

        Cursor cursor = conn.query("nome_linha",null,null,null,null,null,null);

        cursor.moveToFirst();


        if (cursor.getCount() > 0)
        {

            coordenada = cursor.getString(1);
        }

        return coordenada;
    }

    public Linha Resultado(String id){
        Cursor cursor = conn.query("nome_linha",new String[]{"_id", "linha","imagem"}, "_id=?", new String[]{id}, null, null, null);
        Linha linha = new Linha();
        cursor.moveToFirst();
        //se encontrou
        if(cursor.getCount() > 0) {
            linha.setId(cursor.getLong(0));
            linha.setNome_linha(cursor.getString(1));
            linha.setImagem(cursor.getInt(2));

        }
        return linha;
    }

    public Linha ResultadoNome(String nome){
        //Cursor cursor = conn.query("nome_linha",new String[]{"_id", "linha"}, "linha LIKE '%=?%'", new String[]{nome}, null, null, null);

        //pega o array de strngs com a pesquisa
        String[] a = new String[]{nome};
        //adicionamos "%" porque quermos todas pesquisas
        a[0]       = nome + '%';
        //adicionamosao nosso cursos o comando sql
        Cursor   c = conn.rawQuery("SELECT * FROM nome_linha WHERE linha LIKE ?", a);

        Linha linha = new Linha();
        c.moveToFirst();
        //se encontrou
        if(c.getCount() > 0) {
            linha.setId(c.getLong(0));
            linha.setNome_linha(c.getString(1));
            linha.setImagem(c.getInt(2));

        }
        return linha;
    }

    public Carros ResultadoCodigo(String codigo){
        //Cursor cursor = conn.query("nome_linha",new String[]{"_id", "linha"}, "linha LIKE '%=?%'", new String[]{nome}, null, null, null);

        //pega o array de strngs com a pesquisa
        String[] a = new String[]{codigo};
        //adicionamos "%" porque quermos todas pesquisas
        a[0]       = codigo + '%';
        //adicionamosao nosso cursos o comando sql
        Cursor   c = conn.rawQuery("SELECT * FROM tipo_carro WHERE _id LIKE ?", a);

        Carros carros = new Carros();
        c.moveToFirst();
        //se encontrou
        if(c.getCount() > 0) {
            carros.setId(c.getLong(0));
            carros.setTipoCarro(c.getInt(1));
            carros.setAdaptado(c.getInt(2));

        }
        return carros;
    }

    public String[] TodasRequisicoes(int coluna, String tabela){
        /**
         *Metodo que traz para o campo de texto todas requesicoes do banco de dados para um radido preenchimwnto
         * Parametro string busca o nome da tabela
         * paramentro inteiro coluna busca a posição da tabela
         */

        Cursor cursor = conn.rawQuery("SELECT * FROM "+tabela, null);
        String[] a = new String[cursor.getCount()];

        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                a[i] = cursor.getString(coluna);
                i++;
            } while (cursor.moveToNext());
        }

        return a;
    }

    public ArrayList<Execoes> TodasExecoes(){

        Cursor cursor = conn.rawQuery("SELECT * FROM d_execoes", null);

        ArrayList<Execoes> execoes = new ArrayList<>();
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Execoes e = new Execoes();
                e.setId(cursor.getLong(0));
                e.setNome(cursor.getString(1));
                e.setTipoExecao(cursor.getInt(2));

                execoes.add(i, e);

                i++;
            } while (cursor.moveToNext());
        }

        return execoes;
    }

    public ArrayList<Carros> TodosAdaptados(){

        Cursor cursor = conn.rawQuery("SELECT * FROM tipo_carro", null);

        ArrayList<Carros> carros = new ArrayList<>();
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Carros e = new Carros();
                e.setId(cursor.getLong(0));
                e.setTipoCarro(cursor.getInt(1));
                e.setAdaptado(cursor.getInt(2));

                carros.add(i, e);

                i++;
            } while (cursor.moveToNext());
        }

        return carros;
    }

    public void InserirNovoCarro(int id, int tipoCarro, int adaptadoSn)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("tipoCarro", tipoCarro );
        values.put("d_adaptado", adaptadoSn );

        conn.insertOrThrow("tipo_carro",null , values);
    }

    public void InserirNovaExecao(int id, String nome, int tipoExecao)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("nome", nome );
        values.put("tipo_execao", tipoExecao );

        conn.insertOrThrow("d_execoes",null , values);
    }

    public void AtualizarNovoCarro(int id, int tipoCarro, int adaptadoSn)
    {
        ContentValues values = new ContentValues();
        values.put("tipoCarro", tipoCarro );
        values.put("d_adaptado", adaptadoSn );

        conn.update("tipo_carro", values, "_id= ?",new String[]{""+id});
    }

    public void AtualizarExecao(int id, String nome, int tipoExecao)
    {
        ContentValues values = new ContentValues();
        values.put("nome", nome );
        values.put("tipo_execao", tipoExecao );

        conn.update("d_execoes", values, "_id= ?",new String[]{""+id});
    }

    public void DeletarCarrro(long id)
    {
        conn.delete("tipo_carro", "_id= ?",new String[]{String.valueOf(id)});
    }

    public void DeletarExecao(long id)
    {
        conn.delete("d_execoes", "_id= ?",new String[]{String.valueOf(id)});
    }
}