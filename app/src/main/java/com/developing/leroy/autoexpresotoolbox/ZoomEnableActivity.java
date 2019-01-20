package com.developing.leroy.autoexpresotoolbox;

import android.app.MediaRouteButton;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.content.DialogInterface;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class ZoomEnableActivity extends AppCompatActivity {


    private WebView webView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView bannerAd;
    private InterstitialAd mInterstitialAd;
    private Toolbar toolBar;
    private AlertDialog diaLog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionsMenu floatingActionsMenu;
    private com.getbase.floatingactionbutton.FloatingActionButton fABCerrar;
    private com.getbase.floatingactionbutton.FloatingActionButton fABReload;
    private com.getbase.floatingactionbutton.FloatingActionButton fABZoom;

    private Vibrator vibraTor;
    private MaterialProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_enable);

        //Casting MaterialProgressBar
        progressBar = (MaterialProgressBar) findViewById(R.id.materialProgressBar_ID);

        //Obtiene instancia a Vibrator
        vibraTor = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Obtiene instancia a webview
        webView = (WebView) findViewById(R.id.webView_ID);

        //Firebase Analitics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Habilita el boton de back en menu superior
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        //Habilita el ActionBar en la app
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Admod banner
        bannerAd = (AdView) findViewById(R.id.adView_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        //Admod Interstitial Ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        if (!haveNetworkConection()) {

            Intent intent = new Intent(this, NoConnectionActivity.class);
            startActivity(intent);
        }

        else {

            websiteView();
        }

        fabActionButtons();



    }

    //Toast method
    public void onToast(String message){

        Toast.makeText(this, message , Toast.LENGTH_SHORT).show();

    }

    //WebView method
    private void websiteView() {

        WebSettings webSettings = webView.getSettings();

        //Verificar si autoexpreso utiliza javascript
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        //Enable zoom controls
        webView.getSettings().setBuiltInZoomControls(true);
        //Disable controls buttons on web view
        webView.getSettings().setDisplayZoomControls(false);

        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //Material Progress Bar
        progressBar.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewClientDemo());
        webView.loadUrl("https://www.autoexpreso.com/");

        //Define color del progressbar
        ((ProgressBar)findViewById(R.id.materialProgressBar_ID))
                .getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

    }

    //Funcion que valida si el dispositivo tiene internet al iniciar
    private boolean haveNetworkConection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean  isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        return isConnected;
    }

    //Hace que los iconos del menu bar se vean
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar, menu);
        return true;
    }

    //Logica del menu superior
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.rate:
                diaLog = rateDialog().show();

                break;

            //Cierra la applicacion
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "My new app http://www.----.com");
                startActivity(Intent.createChooser(intent, "Compartir"));

                break;

            //Habilita back button para webview
            case android.R.id.home:
                if (webView.canGoBack()) {
                    webView.goBack();

                } else {
                    exitDialog().show();
                }

                break;
        }

        return true;
    }

    //Exit Dialog Method
    public AlertDialog.Builder exitDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ZoomEnableActivity.this);

        builder.setTitle("Cerrar");
        builder.setMessage("¿Desea cerrar la aplicación?");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    finishAffinity();
                }
            }
        });

        return builder;
    }

    //Rate Dialog Method
    public AlertDialog.Builder rateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ZoomEnableActivity.this);

        builder.setTitle(ZoomEnableActivity.this.getString(R.string.valorar_title));
        builder.setMessage(ZoomEnableActivity.this.getString(R.string.valorar_set_message));
        builder.setNegativeButton(ZoomEnableActivity.this.getString(R.string.no_gracias), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
        builder.setPositiveButton(ZoomEnableActivity.this.getString(R.string.valorar_ahora), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try{
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + "com.android.chrome")));
                } catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        return builder;


    }

    @Override
    public void onBackPressed() {

        //Habilita el uso de back button para web view
        //y para el Floating button
        if (webView.canGoBack() || floatingActionsMenu.isExpanded()) {
            floatingActionsMenu.collapse();
            webView.goBack();


        }

        else {
            diaLog = exitDialog().show();
        }

    }

    @Override
    protected void onResume() {

        if (bannerAd != null) {
            bannerAd.resume();

            super.onResume();

        }
    }

    @Override
    protected void onPause() {

        if (bannerAd != null) {
            bannerAd.resume();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (bannerAd != null) {
            bannerAd.destroy();
        }

        super.onDestroy();
    }

    //Metodo para darle funcionamiento al float menu button
    private void fabActionButtons() {
        //Float menu
        floatingActionsMenu = findViewById(R.id.fabMenu_ID);
        //Float action buttons
        fABCerrar = findViewById(R.id.fabCerrarMini_ID);
        fABReload = findViewById(R.id.fabReloadrMini_ID);
        fABZoom = findViewById(R.id.fabEnableZoom_ID);

        if(vibraTor.hasVibrator()) {

            //Activa el Float Action Button cuando se clickea
            fABCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    vibraTor.vibrate(20);
                    timeSecondsCollapse();
                    exitDialog().show();

                }
            });

            //Activa el Float Action Button cuando se clickea
            fABReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    vibraTor.vibrate(20);
                    Snackbar.make(v, "Recargando página de Auto Expreso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    timeSecondsCollapse();

                    //Web view reloading
                    webView.reload();

                }
            });

            //Habilita el Zoom Out
            fABZoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibraTor.vibrate(20);
                    onToast("Zoom Deshabilitado");
                    timeSecondsCollapse();

                    Intent intent = new Intent(ZoomEnableActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                }
            });

        }

        else{
            Log.v("VIBRATOR", "Este dispositivo NO puede vibrar");

            //Activa el Float Action Button cuando se clickea
            fABCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeSecondsCollapse();
                    exitDialog().show();

                }
            });

            //Activa el Float Action Button cuando se clickea
            fABReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar.make(v, "Recargando página de Auto Expreso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    timeSecondsCollapse();
                    webView.reload();

                }
            });

            fABZoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onToast("Zoom Deshabilitado");
                    timeSecondsCollapse();

                    Intent intent = new Intent(ZoomEnableActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                }
            });
        }


    }

    //Timer para cerrar Float buttons despues que se clickea
    private void timeSecondsCollapse(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                floatingActionsMenu.collapse();
                //2 segundos de espera
            }//Timer 1.5 segundos
        }, 1500);

    }

    //Metodo para que el floating button menu se cierre cuando tocan fuero de su ubicacion
    @Override public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (floatingActionsMenu.isExpanded()) {

                Rect outRect = new Rect();
                floatingActionsMenu.getGlobalVisibleRect(outRect);

                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    floatingActionsMenu.collapse();

            }
        }

        return super.dispatchTouchEvent(event);
    }

    //Enable Material progress bar-------------------------------------------------
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
    //------------------------------------------------------------------------
}
