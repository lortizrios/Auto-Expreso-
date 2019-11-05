package com.developing.leroy.autoexpresotoolbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class AcercaDelDesarrolladorActivity extends AppCompatActivity {

    public WebView webView;
    private MaterialProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca__del__desarrollador_);

        //Hablita boton de back sin funcionalidad
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //Anade titulo al action bar
            getSupportActionBar().setTitle("Acerca del desarrollador");
        }

        //Obtiene instancia a SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.pullRefresh_ID);

        //Obtiene instancia a webview
        webView = findViewById(R.id.webView_ID);
        progressBar = findViewById(R.id.materialProgressBar_ID);

        pullSwipeReload();
        websiteView();
        onBackPressed();
    }

    //WebView method
    private void websiteView() {

        WebSettings webSettings = webView.getSettings();

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setJavaScriptEnabled(true);
        //Enable zoom controls
        //webview.getSettings().setBuiltInZoomControls(true);
        //Disable controls buttons on web view
        //webview.getSettings().setDisplayZoomControls(false);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.setWebViewClient(new WebViewClient());

        //Material Progress Bar
        progressBar.setVisibility(View.GONE);

        webView.setWebViewClient(new AcercaDelDesarrolladorActivity.WebViewClientDemo());
        webView.loadUrl("https://www.linkedin.com/in/leroy-ortiz-rios-07737b115/");

        onBackPressed();

        //Define color del progressbar
        ((ProgressBar)findViewById(R.id.materialProgressBar_ID)).getIndeterminateDrawable()
        .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    //Enable Material progress bar
    public class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }

    @Override
    public void onBackPressed() {
        //Habilita el uso de back button para web view y para el Floating button
        webView.canGoBack();
    }

    public void pullSwipeReload() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //Show Reload logo
                swipeRefreshLayout.setRefreshing(true);

                Toast.makeText(AcercaDelDesarrolladorActivity.this, "Recargando", Toast.LENGTH_SHORT).show();

                //Add a timer
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        //Web view reloading
                        webView.reload();

                        // Hide reload logo
                        swipeRefreshLayout.setRefreshing(false);

                    }//1.5 segundos de espera
                }, 500);
            }

        });
    }
}
