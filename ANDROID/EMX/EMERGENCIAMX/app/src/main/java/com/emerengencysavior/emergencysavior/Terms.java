package com.emerengencysavior.emergencysavior;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jaeger.library.StatusBarUtil;

/**
 * Created by RSA on 13-03-2016.
 */
public class Terms extends AppCompatActivity {
    WebView webView;
    ProgressDialog progressDialog;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        //  getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><small><b>Contacts</b></small> </font>"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>TERM AND CONDITION </b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        webView = (WebView) findViewById(R.id.webView1);
        startWebView(CONFIG.URL+"/staticpages/tnc.html");
        setStatusBar();
    }
    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    private void startWebView(String url) {
            webView.setWebViewClient(new WebViewClient() {


            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(Terms.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }


            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                       // progressDialog = null;
                    }
                    progressDialog.dismiss();
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }


        });
        webView.loadUrl(url);


    }
     @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

}