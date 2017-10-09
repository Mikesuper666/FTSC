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

    public Preferencias(Context contextoParametro)
    {
        contexto = contextoParametro;
        sharedPreferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();
    }

    public void SalvarLogin(int logado)
    {
        editor.putInt(CHAVE_LOGADO, logado);
        editor.commit();
    }

    public int getLogin()
    {
        return sharedPreferences.getInt(CHAVE_LOGADO, 0);
    }//retorna quando chamado a id guardada dentro do indentificador
}