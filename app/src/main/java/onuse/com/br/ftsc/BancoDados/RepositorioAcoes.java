package onuse.com.br.ftsc.BancoDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import onuse.com.br.ftsc.Models.Carros;
import onuse.com.br.ftsc.Models.Execoes;
import onuse.com.br.ftsc.Models.Linha;
import onuse.com.br.ftsc.Models.Ocorrencias;
import onuse.com.br.ftsc.Models.OcorrenciasCarros;
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
        Cursor cursor = conn.query("nome_linha",new String[]{"_id", "linha","imagem", "codigos"}, "codigos=?", new String[]{id}, null, null, null);
        Linha linha = new Linha();
        cursor.moveToFirst();
        //se encontrou
        if(cursor.getCount() > 0) {
            linha.setId(cursor.getLong(0));
            linha.setNome_linha(cursor.getString(1));
            linha.setImagem(cursor.getInt(2));
            linha.setCodigoLinha(cursor.getString(3));

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
            linha.setCodigoLinha(c.getString(3));

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

    public Execoes ResultadoMatricula(String codigo){
        //Cursor cursor = conn.query("nome_linha",new String[]{"_id", "linha"}, "linha LIKE '%=?%'", new String[]{nome}, null, null, null);

        //pega o array de strngs com a pesquisa
        String[] a = new String[]{codigo};
        //adicionamos "%" porque quermos todas pesquisas
        a[0]       = codigo + '%';
        //adicionamosao nosso cursos o comando sql
        Cursor   c = conn.rawQuery("SELECT * FROM d_execoes WHERE _id LIKE ?", a);

        Execoes execoes = new Execoes();
        c.moveToFirst();
        //se encontrou
        if(c.getCount() > 0) {
            execoes.setId(c.getLong(0));
            execoes.setNome(c.getString(1));
            execoes.setTipoExecao(c.getInt(2));

        }
        return execoes;
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
                e.setFuncao(cursor.getInt(3));
                e.setHorario(cursor.getInt(4));

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

    public ArrayList<Ocorrencias> TodasOcorrencias(int matriculaFunc){

        Cursor cursor = conn.query("ocorrencias", new String[]{"_id","matricula_fiscal","ocorrencia"}, "matricula_func=?", new String[]{String.valueOf(matriculaFunc)}, null, null, null);

        ArrayList<Ocorrencias> ocorrencia = new ArrayList<>();
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Ocorrencias e = new Ocorrencias();
                e.setId(cursor.getString(0));
                e.setMatricula_fiscal(cursor.getInt(1));
                e.setOcorrencia(cursor.getString(2));

                ocorrencia.add(i, e);

                i++;
            } while (cursor.moveToNext());
        }

        return ocorrencia;
    }

    public ArrayList<OcorrenciasCarros> TodasOcorrenciasCarros(int codigo){

        Cursor cursor = conn.query("carro_ocorrencias", new String[]{"_id","matricula_fiscal","ocorrencia"}, "codigo=?", new String[]{String.valueOf(codigo)}, null, null, null);

        ArrayList<OcorrenciasCarros> ocorrencia = new ArrayList<>();
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                OcorrenciasCarros e = new OcorrenciasCarros();
                e.setId(cursor.getString(0));
                e.setMatricula_fiscal(cursor.getInt(1));
                e.setOcorrencia(cursor.getString(2));

                ocorrencia.add(i, e);

                i++;
            } while (cursor.moveToNext());
        }

        return ocorrencia;
    }

    public void InserirNovaOcorrencia(String id, int matriculaFunc, int matriculaFical, String ocorrencia)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("matricula_func", matriculaFunc );
        values.put("matricula_fiscal", matriculaFical );
        values.put("ocorrencia", ocorrencia );

        conn.insertOrThrow("ocorrencias",null , values);
    }

    public void InserirOcorrenciaCarros(String id, int codigo, int matriculaFical, String ocorrencia)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("codigo", codigo );
        values.put("matricula_fiscal", matriculaFical );
        values.put("ocorrencia", ocorrencia );

        conn.insertOrThrow("carro_ocorrencias",null , values);
    }

    public void InserirNovoCarro(int id, int tipoCarro, int adaptadoSn)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("tipoCarro", tipoCarro );
        values.put("d_adaptado", adaptadoSn );

        conn.insertOrThrow("tipo_carro",null , values);
    }

    public void AdicionarLinha(String nome_linha, String imagem, String codigos){

        ContentValues values = new ContentValues();
        values.put("linha", nome_linha );
        values.put("imagem", imagem );
        values.put("codigos", codigos );

        conn.insertOrThrow("nome_linha",null , values);

    }

    public void AdicionarAdaptados(String id, String tipoCarro, String d_adaptado){

        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("tipoCarro", tipoCarro );
        values.put("d_adaptado", d_adaptado );

        conn.insertOrThrow("tipo_carro",null , values);

    }

    public void AdicionarExecao(String id, String nome, String tipoExecao, String funcao, String horario)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("nome", nome );
        values.put("tipo_execao", tipoExecao );
        values.put("funcao", funcao );
        values.put("horario", horario );

        conn.insertOrThrow("d_execoes",null , values);
    }

    public void AdicionarOcorrencias(String id, String matricula_func, String matricula_fiscal, String ocorrencia)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("matricula_func", matricula_func );
        values.put("matricula_fiscal", matricula_fiscal );
        values.put("ocorrencia", ocorrencia );

        conn.insertOrThrow("ocorrencias ",null , values);
    }

    public void AdicionarCarrosOcorrencias(String id, String codigo, String matricula_fiscal, String ocorrencia)
    {
        ContentValues values = new ContentValues();
        values.put("_id", id );
        values.put("codigo", codigo );
        values.put("matricula_fiscal", matricula_fiscal );
        values.put("ocorrencia", ocorrencia );

        conn.insertOrThrow("carro_ocorrencias ",null , values);
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

    public void DeletarOcorrencia(String id)
    {
        conn.delete("ocorrencias", "_id= ?",new String[]{String.valueOf(id)});
    }

    public void DeletarCarroOcorrencia(String id)
    {
        conn.delete("carro_ocorrencias", "_id= ?",new String[]{id});
    }

    public void DeletarLinhas(String tabela){
        conn.delete(tabela, "",null);
    }
}