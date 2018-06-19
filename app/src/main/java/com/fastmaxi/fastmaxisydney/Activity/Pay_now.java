package com.fastmaxi.fastmaxisydney.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.fastmaxi.fastmaxisydney.R;

import dmax.dialog.SpotsDialog;

public class Pay_now extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_toolbar_name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        tv_title.setText("Pay Now");
        final android.app.AlertDialog dialog1 = new SpotsDialog(Pay_now.this);
        dialog1.show();
        webView = (WebView) findViewById(R.id.wv_bagkart);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setLongClickable(false);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                dialog1.dismiss();

            }
        });

        webView.loadUrl("https://wavsydney.com.au/paynow.php");
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final android.app.AlertDialog dialog1 = new SpotsDialog(Pay_now.this);
                        dialog1.show();
                        mSwipeRefreshLayout.setRefreshing(false);
                        webView.reload();
                        dialog1.dismiss();
                    }
                }, 3000);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
            finish();

//            Toast.makeText(this, "You are already at Home page", Toast.LENGTH_SHORT).show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            //Uncomment the below code to Set the message and title from the strings.xml file
//            //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
//
//            //Setting message manually and performing action on button click
//            builder.setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //  Action for 'NO' Button
//                            dialog.cancel();
//                        }
//                    });
//
//            //Creating dialog box
//            AlertDialog alert = builder.create();
//            //Setting the title manually
//            alert.setTitle("Want to exit ?");
//            alert.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }


}
