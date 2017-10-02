package onuse.com.br.ftsc.Helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maico on 30/09/17.
 */

public class Permissoes {
    public static boolean validaPermissoes(int requesteCode, Activity activity, String[] permissoes){

        if(Build.VERSION.SDK_INT >= 23)
        {
            List<String> ListaPermissoes = new ArrayList<String>();
            //Percorre as permissoes e verifica uma a uma se ja tem a permissao
            for(String permissao: permissoes)
            {
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao)
                        == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) ListaPermissoes.add(permissao);
            }

            //caso a lista esteja vazia não é necesario solicitar permissao
            if(ListaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[ListaPermissoes.size()];
            ListaPermissoes.toArray(novasPermissoes);

            //Solicita permissao
            ActivityCompat.requestPermissions(activity, novasPermissoes, requesteCode);
        }
        return true;
    }
}