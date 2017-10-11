package onuse.com.br.ftsc.Models;

/**
 * Created by maico on 09/10/17.
 */

public class Login {
    private int id;
    private String usuario, senha;

    public Login()
    {
        id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
