package com.fastmaxi.fastmaxisydney.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fastmaxi.fastmaxisydney.R;


public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private Button btn_call, btn_service, btn_book_now, btn_offer, btn_contact_us, btn_pay_now;
    private Toolbar toolbar;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_toolbar_name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        tv_title.setText("Home");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_service = (Button) findViewById(R.id.btn_service);
        btn_book_now = (Button) findViewById(R.id.btn_book_now);
        btn_offer = (Button) findViewById(R.id.btn_offer);
        btn_contact_us = (Button) findViewById(R.id.btn_contact_us);
        btn_pay_now = (Button) findViewById(R.id.btn_pay_now);
        btn_call.setOnClickListener(this);
        btn_service.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);
        btn_offer.setOnClickListener(this);
        btn_contact_us.setOnClickListener(this);
        btn_pay_now.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+61481700500"));

                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

                break;

            case R.id.btn_service:
                Intent intent = new Intent(Dashboard.this, Service_page.class);
                startActivity(intent);
                break;

            case R.id.btn_book_now:
                Intent intent1 = new Intent(Dashboard.this, Book_now.class);
                startActivity(intent1);
                break;

            case R.id.btn_offer:
                Intent intent2 = new Intent(Dashboard.this, Offers.class);
                intent2.putExtra("getfrom", "0");
                startActivity(intent2);
                break;

            case R.id.btn_contact_us:
                Intent intent3 = new Intent(Dashboard.this, Contact_Us.class);
                startActivity(intent3);
                break;

            case R.id.btn_pay_now:
                Intent intent4 = new Intent(Dashboard.this, Pay_now.class);
                startActivity(intent4);
                break;


        }


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
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setCancelable(false);
        builder.setTitle("Exit..");
        builder.setMessage("Want to exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finishAffinity();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
