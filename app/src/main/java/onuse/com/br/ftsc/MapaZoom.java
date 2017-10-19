package onuse.com.br.ftsc;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by maico on 18/10/17.
 */

public class MapaZoom extends AppCompatActivity{
    private PhotoView imagemRota;
    private TypedArray img;
    private int imagemDoArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapazoom);

        imagemRota = (PhotoView) findViewById(R.id.imagemRotaZoom);

        img = getResources().obtainTypedArray(R.array.images_rotas);


        imagemRota.setImageResource(img.getResourceId(imagemDoArray, -1));
    }
}
