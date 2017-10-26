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
    private String CHAVE_SPLASHSCREEN = "splashscreen";
    private String CHAVE_NOME = "nome";
    private String CHAVE_MATRICULA = "matricula";

    public Preferencias(Context contextoParametro)
    {
        contexto = contextoParametro;
        sharedPreferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();
    }

    public void SalvarLogin(int logado, int matricula, String nome)
    {
        editor.putInt(CHAVE_LOGADO, logado);
        editor.putString(CHAVE_NOME, nome);
        editor.putInt(CHAVE_MATRICULA, matricula);
        editor.commit();
    }

    public void SplashScreenAssistida(int assistiu){
        editor.putInt(CHAVE_SPLASHSCREEN, assistiu);
        editor.commit();
    }

    public int getSplashScreen()
    {
        return sharedPreferences.getInt(CHAVE_SPLASHSCREEN, 0);
    }

    public int getLogin()
    {
        return sharedPreferences.getInt(CHAVE_LOGADO, 0);
    }//retorna quando chamado a id guardada dentro do indentificador

    public int getMatricula()
    {
        return sharedPreferences.getInt(CHAVE_MATRICULA, 0);
    }//retorna quando chamado a matricula guardada dentro do indentificador

    public String getNome()
    {
        return sharedPreferences.getString(CHAVE_NOME, null);
    }//retorna quando chamado a matricula guardada dentro do indentificador
}