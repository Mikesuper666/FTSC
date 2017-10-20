package onuse.com.br.ftsc;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import onuse.com.br.ftsc.Models.Linha;

/**
 * Created by maico on 18/10/17.
 */

public class MapaZoom extends AppCompatActivity{
    private PhotoView imagemRota;
    private Linha linha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapazoom);

        imagemRota = (PhotoView) findViewById(R.id.imagemRotaZoom);

        Drawable imagemUsuario = linha.getMapaImagem();

        imagemRota.setImageDrawable(imagemUsuario);

    }
}
