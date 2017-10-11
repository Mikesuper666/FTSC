package onuse.com.br.ftsc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by maico on 09/10/17.
 */

public class RequisicaoWeb extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = (WebView)findViewById(R.id.webRequisicao);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("http://www.soul.com.br/site/itinerarios.php?");

        webView.setWebViewClient(new WebViewClient(){
            public boolean naoPodeAbrir(WebView view, String url){
                return false;
            }
        });

    }
}
