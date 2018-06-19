package com.fastmaxi.fastmaxisydney.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fastmaxi.fastmaxisydney.Datamodel.Datamodel_country_code;
import com.fastmaxi.fastmaxisydney.Datamodel.Datamodel_result;
import com.fastmaxi.fastmaxisydney.R;
import com.fastmaxi.fastmaxisydney.Util.ApiUtils_Interface;
import com.fastmaxi.fastmaxisydney.Util.Service_Generator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Book_now extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Datamodel_country_code> country_codes_list = new ArrayList<>();
    String s_full_name = null,
            s_country_code = null,
            s_phone = null,
            s_email_id = null,
            s_from = null,
            s_to = null,
            s_date = null,
            s_time = null,
            s_total_person = null,
            s_coupon_code = null,
            s_return_trip = null,
            s_extra_info = null;
    int selected_year;
    int selected_month;
    int selected_day;
    int mHour;
    int mMinute;
    ApiUtils_Interface client;
    Datamodel_result result = null;
    private Toolbar toolbar;
    private TextView tv_title;
    private Spinner spnr_country_code;
    private EditText et_full_name, et_phone, et_email_id, et_from, et_to, et_date, et_time, et_total_person, et_coupon_code, et_extra_info;
    private RadioGroup r_grp_trip;
    private Button btn_submit;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            selected_year = year;
            selected_month = monthOfYear;
            selected_day = dayOfMonth;
            try {
                String months = null;
                String dates = null;
                int month = selected_month + 1;
                if (month < 10) {
                    months = "0" + month;
                } else {
                    months = String.valueOf(month);
                }
                int date = selected_day;
                if (date < 10) {
                    dates = "0" + date;
                } else {
                    dates = String.valueOf(date);
                }
                String travelling_date = dates + "/" + months + "/" + selected_year;
                et_date.setText(travelling_date.trim());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // isValidEmailAddress: Check the email address is OK
    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_toolbar_name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tv_title.setText("Book Now");


        et_full_name = (EditText) findViewById(R.id.et_full_name);
        spnr_country_code = (Spinner) findViewById(R.id.spnr_country_code);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email_id = (EditText) findViewById(R.id.et_email);
        et_from = (EditText) findViewById(R.id.et_from);
        et_to = (EditText) findViewById(R.id.et_to);
        et_date = (EditText) findViewById(R.id.et_date);
        et_time = (EditText) findViewById(R.id.et_time);
        et_total_person = (EditText) findViewById(R.id.et_total_person);
        et_coupon_code = (EditText) findViewById(R.id.et_coupon_code);
        et_extra_info = (EditText) findViewById(R.id.et_extra_info);

        r_grp_trip = (RadioGroup) findViewById(R.id.r_grp_trip);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        if (getIntent() != null) {
            s_coupon_code = getIntent().getStringExtra("offer_code");
            try {
                if (s_coupon_code != null) {
                    et_coupon_code.setText(s_coupon_code);

                } else {
                    s_coupon_code = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            //Load File
            String myJson = inputStreamToString(this.getResources().openRawResource(R.raw.countries));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Datamodel_country_code>>() {
            }.getType();

            ArrayList<Datamodel_country_code> country_codes = gson.fromJson(myJson, type);
            for (Datamodel_country_code list : country_codes) {
                ArrayAdapter<Datamodel_country_code> adapter =
                        new ArrayAdapter<Datamodel_country_code>(Book_now.this, android.R.layout.simple_spinner_dropdown_item, country_codes);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnr_country_code.setAdapter(adapter);
                spnr_country_code.setSelection(13);

                Log.i("Contact Details", list.getCode() + "-" + list.getDial_Code());
            }

        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

        btn_submit.setOnClickListener(this);
        et_date.setOnClickListener(this);
        et_time.setOnClickListener(this);

        et_coupon_code.setOnClickListener(this);


//        et_time.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                return false;
//            }
//        });

    }


    //read local json

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

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    private boolean IsValidate() {
        boolean isvalid = true;
        s_full_name = et_full_name.getText().toString();
        s_email_id = et_email_id.getText().toString();
        s_phone = et_phone.getText().toString();
        s_from = et_from.getText().toString();
        s_to = et_to.getText().toString();
        s_date = et_date.getText().toString();
        s_time = et_time.getText().toString();
        s_total_person = (et_total_person.getText().toString());
        s_coupon_code = et_coupon_code.getText().toString();
        s_extra_info = et_extra_info.getText().toString();


        if (!isValidEmailAddress(s_email_id)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle("Invalid Email !!");

            builder.setMessage("Your Email ID is not correct");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_email_id.setText(null);
                    et_email_id.requestFocus();

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;
        } else if (TextUtils.isEmpty(s_phone) || s_phone.length() < 5) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle("Invalid Mobile No !!");

            builder.setMessage("Mobile No could be Empty");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_phone.requestFocus();

                    et_phone.setText(null);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_full_name)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle("Name Empty !!");

            builder.setMessage("Name could be Empty");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_full_name.requestFocus();

                    et_full_name.setText(null);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_from)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Source Name !! ");

            builder.setMessage("Source name could not be Empty");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_from.requestFocus();

                    et_from.setText(null);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_to)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Destination Name !! ");

            builder.setMessage("Destination name could not be Empty");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_to.requestFocus();

                    et_to.setText(null);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_date)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Select Date ");

            builder.setMessage("Date could not be Empty");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_date.requestFocus();

                    et_date.setText(null);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_time)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Select Time ");

            builder.setMessage("Time could not be Empty");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_time.requestFocus();

                    et_time.setText(null);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (s_total_person.equalsIgnoreCase("0")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Select Person ");

            builder.setMessage("Minimum person is 1 ");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_total_person.requestFocus();

                    et_total_person.setText("1");
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_coupon_code)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Select Coupon  ");

            builder.setMessage("Please use any Coupon from Offers");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_coupon_code.requestFocus();

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        } else if (TextUtils.isEmpty(s_extra_info)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setTitle(" Extra Info !! ");

            builder.setMessage("Please describe some extra info ...");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_extra_info.setText(null);
                    et_extra_info.requestFocus();

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            isvalid = false;

        }


        return isvalid;
    }

    @Override
    public void onClick(View v) {

        int check_id = r_grp_trip.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(check_id);
        s_return_trip = radioButton.getText().toString();
        int id = v.getId();

        switch (id) {

            case R.id.et_date:

                DatePickerDialog dialog = null;
                final Calendar minCal = Calendar.getInstance();
                Calendar maxCal = Calendar.getInstance();
                maxCal.set(2030, Calendar.JANUARY, 1, 0, 0, 0);
                minCal.setTimeInMillis(System.currentTimeMillis());
                if (TextUtils.isEmpty(et_date.getText().toString())) {
                    final Calendar c = Calendar.getInstance();
                    int currentyear = c.get(Calendar.YEAR);
                    int currentmonth = c.get(Calendar.MONTH);
                    int currentday = c.get(Calendar.DAY_OF_MONTH);
                    dialog = new DatePickerDialog(Book_now.this, datePickerListener, currentyear, currentmonth, currentday);
                    if (Build.VERSION.SDK_INT > 10)
                        dialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());
                    dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
                } else {
                    dialog = new DatePickerDialog(Book_now.this, datePickerListener, selected_year, selected_month, selected_day);
                    if (Build.VERSION.SDK_INT > 10)
                        dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
                }
                dialog.show();

                break;
            case R.id.et_time:
                final Calendar c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR_OF_DAY);
                mMinute = c1.get(Calendar.MINUTE);
                int am_pm = c1.get(Calendar.AM);
                final String time = ((am_pm == Calendar.AM) ? "am" : "pm");

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Book_now.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                updateTime(hourOfDay, minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
                break;

            case R.id.et_coupon_code:
                Intent intent = new Intent(Book_now.this, Offers.class);
                intent.putExtra("getfrom", "1");
                startActivityForResult(intent, 1);
                break;

            case R.id.btn_submit:

                boolean isvalid = IsValidate();
                if (isvalid) {
                    s_country_code = spnr_country_code.getSelectedItem().toString();
                    s_country_code = s_country_code.substring(2);
                    s_phone = s_country_code + " " + s_phone;
                    String sms = "Booking Detail\nName : " + s_full_name + "\nPhone : " + s_phone + "\nE-Mail : " + s_email_id + "\nFrom : " + s_from + "\nTo : " + s_to + "\nDate : " + s_date + "\nTime : " + s_time + "\nNo Of Passengers :" + s_total_person + "\nCoupon : " + s_coupon_code + "\nRetrun trip : " + s_return_trip + "\nAdditional Information :" + s_extra_info;
                    String email = "<!DOCTYPE html><html><body><h1>Booking Detail</h1><h3>Name : " + s_full_name + "</h3><h3>Phone : " + s_phone + "</h3><h3>E-mail : " + s_email_id + "</h3><h3>From : " + s_from + "</h3><h3>To : " + s_to + "</h3><h3>Date : " + s_date + "</h3><h3>Time : " + s_time + "</h3><h3>No Of Passengers : " + s_total_person + "</h3><h3>Coupon : " + s_coupon_code + "</h3><h3>Retrun trip : " + s_return_trip + "</h3><h3>Additional Information : " + s_extra_info + "</h3></body></html>";

                    boolean isconnect = isInternetOn();

                    if (isconnect) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        final AlertDialog dialog1 = new SpotsDialog(Book_now.this);
                        dialog1.show();
                        JsonObject jsonObject = new JsonObject();
                        try {
                            jsonObject.addProperty("from", s_email_id);
                            jsonObject.addProperty("name", s_full_name);
                            jsonObject.addProperty("sms", sms);
                            jsonObject.addProperty("email", email);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (client == null)
                            client = Service_Generator.Create_Service(ApiUtils_Interface.class);
                        Call<JsonObject> call = client.Book_taxi(jsonObject);

                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                dialog1.dismiss();

                                try {

                                    String result = response.body().toString();
                                    JSONObject jsonObject = new JSONObject(result);
                                    result = jsonObject.getString("success");
                                    if (result.equalsIgnoreCase("Message Sent")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
                                        builder.setCancelable(false);
                                        builder.setIcon(R.drawable.success);
                                        builder.setTitle("Success !!");
                                        builder.setMessage("Your Message is successfully Sent");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent1 = new Intent(Book_now.this, Dashboard.class);
                                                startActivity(intent1);
                                                finishAffinity();
                                                dialog.dismiss();
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    } else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
                                        builder.setCancelable(false);
                                        builder.setTitle("Failed");
                                        builder.setMessage("Your booking is failed , Please check your Mail");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent1 = new Intent(Book_now.this, Dashboard.class);
                                                startActivity(intent1);
                                                finishAffinity();
                                                dialog.dismiss();
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                dialog1.dismiss();

                                Log.e("TAG", t.getMessage());
                            }
                        });


                        et_full_name.setText("");
                        et_phone.setText("");
                        et_email_id.setText("");
                        et_from.setText("");
                        et_to.setText("");
                        et_date.setText("");
                        et_time.setText("");
                        et_total_person.setText("");
                        et_coupon_code.setText("");
                    }
                }
                break;


        }
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Book_now.this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.no_internet);
            builder.setTitle("No Internet !!");

            builder.setMessage("Please On your Internet Connection");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        return false;
    }

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        et_time.setText(aTime);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("offer_code");

                et_coupon_code.setText(strEditText);
            }
        }
    }
}
