package onuse.com.br.ftsc.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maico on 07/10/17.
 */

public class Preferencias {
    private Context contexto;
    private SharedPreferences sharedPreferences;
    private String NOME_ARQUIVO = "preferencia";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private String CHAVE_LOGADO = "logado";
    private String CHAVE_MATRICULA = "matricula";

    public Preferencias(Context contextoParametro)
    {
        contexto = contextoParametro;
        sharedPreferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();
    }

    public void SalvarLogin(int logado, int matricula)
    {
        editor.putInt(CHAVE_LOGADO, logado);
        editor.putInt(CHAVE_MATRICULA, matricula);
        editor.commit();
    }

    public int getLogin()
    {
        return sharedPreferences.getInt(CHAVE_LOGADO, 0);
    }//retorna quando chamado a id guardada dentro do indentificador

    public int getMatricula()
    {
        return sharedPreferences.getInt(CHAVE_MATRICULA, 0);
    }//retorna quando chamado a matricula guardada dentro do indentificador
}