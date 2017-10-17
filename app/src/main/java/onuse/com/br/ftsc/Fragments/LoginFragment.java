package onuse.com.br.ftsc.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import onuse.com.br.ftsc.BancoDados.BancoOnlineLogin;
import onuse.com.br.ftsc.R;

/**
 * Created by maico on 09/10/17.
 */

public class LoginFragment extends Fragment {
    private TextView fragLoginNome, fragLoginSenha, fragLoginSenhaNovo;
    private ImageView btnFragLoginConfirmar, btnFragLoginCancelar;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // converte a view para ser retornada o fragmente view e ser usados os componentes
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        fragLoginNome = (TextView)view.findViewById(R.id.fragLoginNome);
        fragLoginSenha = (TextView)view.findViewById(R.id.fragLoginSenha);
        fragLoginSenhaNovo = (TextView)view.findViewById(R.id.fragLoginSenhaNovo);
        btnFragLoginCancelar = view.findViewById(R.id.btnFragLoginCancelar);
        btnFragLoginConfirmar = view.findViewById(R.id.btnFragLoginConfirmar);



        btnFragLoginCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnFragLoginConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = fragLoginNome.getText().toString();
                String senha = fragLoginSenha.getText().toString();
                String senhaNova = fragLoginSenhaNovo.getText().toString();
                if(nome.equals("") || senha.equals("") || senhaNova.equals("")){
                    Toast.makeText(getActivity(), "Senha ou email com campo vazio!", Toast.LENGTH_LONG).show();
                }else{
                    BancoOnlineLogin bancoOnlineLogin = new BancoOnlineLogin(getActivity());
                    bancoOnlineLogin.conectarAobanco(1, nome, senha, senhaNova.trim());
                }
            }
        });
        return view;
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}