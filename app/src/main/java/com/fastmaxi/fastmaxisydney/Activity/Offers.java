package com.fastmaxi.fastmaxisydney.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fastmaxi.fastmaxisydney.R;


public class Offers extends AppCompatActivity implements View.OnClickListener {
    Button btn_call;
    String s_getfrom = null;
    private Toolbar toolbar;
    private TextView tv_title;
    private Button btn_aps, btn_css, btn_gts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_toolbar_name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        tv_title.setText("Offers");

        btn_aps = (Button) findViewById(R.id.btn_aps);
        btn_css = (Button) findViewById(R.id.btn_css);
        btn_gts = (Button) findViewById(R.id.btn_gts);
        btn_call = (Button) findViewById(R.id.btn_call);
        if (getIntent() != null) {

            s_getfrom = getIntent().getStringExtra("getfrom");

        }


        btn_aps.setOnClickListener(this);
        btn_css.setOnClickListener(this);
        btn_gts.setOnClickListener(this);
        btn_call.setOnClickListener(this);
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
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btn_call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+61481700500"));

                if (ActivityCompat.checkSelfPermission(Offers.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
                break;

            case R.id.btn_aps:
                if (s_getfrom.equalsIgnoreCase("1")) {
                    Intent intent = new Intent();
                    intent.putExtra("offer_code", "APSY10");
                    setResult(RESULT_OK, intent);
                    finish();

                } else {

                    Intent i_aps = new Intent(Offers.this, Book_now.class);
                    i_aps.putExtra("offer_code", "APSY10");

                    startActivity(i_aps);
                    finish();
                }
                break;

            case R.id.btn_css:
                if (s_getfrom.equalsIgnoreCase("1")) {
                    Intent intent = new Intent();
                    intent.putExtra("offer_code", "CSSY8");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent i_css = new Intent(Offers.this, Book_now.class);
                    i_css.putExtra("offer_code", "CSSY8");
                    startActivity(i_css);
                    finish();

                }


                break;

            case R.id.btn_gts:
                if (s_getfrom.equalsIgnoreCase("1")) {
                    Intent intent = new Intent();
                    intent.putExtra("offer_code", "GTSY10");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent i_gts = new Intent(Offers.this, Book_now.class);
                    i_gts.putExtra("offer_code", "GTSY10");
                    startActivity(i_gts);
                    finish();

                }

                break;

        }
    }
}
