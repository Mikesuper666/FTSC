package onuse.com.br.ftsc.SplashScreen;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import onuse.com.br.ftsc.Helper.Preferencias;
import onuse.com.br.ftsc.R;

/**
 * Created by Maico on 22/10/2017.
 */

public class SplashScreen extends AppCompatActivity implements SurfaceHolder.Callback{
    private MediaPlayer mp = null;
    private SurfaceView mSurfaceView=null;
    private Context context;
    private Thread spalshsThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        context = this;
        mSurfaceView = (SurfaceView) findViewById(R.id.videoIncial);
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setZOrderOnTop(false);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mp = new MediaPlayer();
        AssetFileDescriptor arquivoVideo = getResources().openRawResourceFd(R.raw.splash);
        try {
            mp.setDataSource(arquivoVideo.getFileDescriptor(), arquivoVideo.getStartOffset(), arquivoVideo.getDeclaredLength());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pega as dimensões da tela
        int LarguraDaTela = mSurfaceView.getWidth(); //100;// getWindowManager().getDefaultDisplay().getWidth();
        int AlturaDaTela = mSurfaceView.getHeight();//100; //getWindowManager().getDefaultDisplay().getHeight();

        //Pega os parametros de layout do surfaceView
        android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();

        //Seta a altura e largura da tela para o surfaceview
        lp.width = LarguraDaTela;
        lp.height = AlturaDaTela;

        //Comit os parametros do layout para surface
        mSurfaceView.setLayoutParams(lp);

        //Inicia o video
        mp.setDisplay(holder);
        //mp.setLooping(true);
        mp.start();

        StartAnimation();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    private void StartAnimation(){

        spalshsThread = new Thread(){
            @Override
            public void run(){
                try{
                    int espere = 0;//define o tempo para zero
                    while(espere < 5500){
                        sleep(100);
                        espere += 100;
                    }
                    SplashScreen.this.finish();
                }catch (InterruptedException e){
                    //faz nada
                }finally {
                    SplashScreen.this.finish();
                    Preferencias preferencias = new Preferencias(context);
                    preferencias.SplashScreenAssistida(1);
                }
            }
        };
        spalshsThread.start();
    }
}
