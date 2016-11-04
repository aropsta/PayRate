package com.example.arobius.payrate;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import java.math.BigDecimal;
import android.widget.Toast;

import com.example.arobius.payrate.java.databaseAdapter;

import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE [ H:mm:ss ]\ndd-MMM-yyyy  z");
    DecimalFormat b = new DecimalFormat("#,##0.000");
    DecimalFormat c = new DecimalFormat("###,###,##0.00");

    int centiseconds, seconds, minutes, hours ;
    long startMS , endMS , elapsed ;
    boolean clockNotFull, savable, timerRunning; int bool =1;

    BigDecimal msEarnings,earnings,sixty,ten;

    TextView rate, earningsLabel;
    Drawable []drawables;
    RelativeLayout payBar;

    Button startButton, saveButton, historyButton;
    ImageView hoursIMG, minutesIMG, secondsIMG, centiSecondsIMG;

    databaseAdapter helper;
    String timeFilled;
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    ScheduledFuture scheduledFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        payBar = (RelativeLayout) findViewById(R.id.payrateBar);
        startButton = (Button) findViewById(R.id.startButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        historyButton = (Button) findViewById(R.id.historyButton);

        hoursIMG = (ImageView) findViewById(R.id.hoursImage);
        minutesIMG = (ImageView) findViewById(R.id.minutesImage);
        secondsIMG = (ImageView) findViewById(R.id.secondsImage);
        centiSecondsIMG = (ImageView) findViewById(R.id.centiSecondsImage);

        rate = (TextView) findViewById(R.id.rate);
        earningsLabel = (TextView) findViewById(R.id.earningsLabel);


        helper = new databaseAdapter(this);
        drawables();
        
        centiseconds = 0; seconds = 0; minutes = 0; hours = 0;
        clockNotFull = true; savable = false; timerRunning = false;
        startMS = 0; endMS = 0; elapsed = 0;

        msEarnings = new BigDecimal(0); earnings = new BigDecimal(0); sixty = new BigDecimal(60); ten = new BigDecimal(10);

        if(savedInstanceState != null) {

            hours = savedInstanceState.getInt("hours");
            minutes = savedInstanceState.getInt("minutes");
            seconds = savedInstanceState.getInt("seconds");
            centiseconds = savedInstanceState.getInt("centiseconds");
            startMS = savedInstanceState.getLong("startMS");

            earnings = BigDecimal.valueOf(savedInstanceState.getDouble("earnings"));
            earningsLabel.setText(getString(R.string.currencySign) + " " + b.format(earnings));

            centiSecondsIMG.setImageDrawable(drawables[centiseconds * 10]);
            secondsIMG.setImageDrawable(drawables[seconds]);
            minutesIMG.setImageDrawable(drawables[minutes]);
            hoursIMG.setImageDrawable(drawables[hours]);
            

            earningsLabel.setText(getString(R.string.currencySign) + " " + b.format(earnings));
            rate.setText(savedInstanceState.getString("rate"));
            
        }

    }
    
    void drawables(){

        drawables = new Drawable[]{ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit00),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit01),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit02),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit03),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit04),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit05),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit06),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit07),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit08),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit09),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit10),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit11),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit12),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit13),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit14),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit15),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit16),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit17),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit18),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit19),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit20),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit21),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit22),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit23),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit24),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit25),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit26),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit27),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit28),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit29),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit30),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit31),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit32),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit33),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit34),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit35),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit36),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit37),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit38),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit39),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit40),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit41),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit42),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit43),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit44),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit45),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit46),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit47),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit48),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit49),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit50),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit51),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit52),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit53),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit54),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit55),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit56),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit57),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit58),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit59),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit60),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit61),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit62),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit63),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit64),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit65),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit66),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit67),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit68),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit69),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit70),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit71),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit72),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit73),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit74),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit75),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit76),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit77),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit78),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit79),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit80),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit81),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit82),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit83),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit84),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit85),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit86),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit87),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit88),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit89),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit90),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit91),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit92),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit93),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit94),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit95),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit96),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit97),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit98),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.digit99)};
        

    }
    
    Runnable timer = new Runnable() {
        boolean s = true;
        @Override
        public void run() {

            if ((hours == 99) && (minutes == 59) && (seconds == 59) && (centiseconds >= 9)) {
                s = false;
            } else {
                centiseconds++;
                if (centiseconds > 9) {
                    centiseconds = 0;
                    seconds++;
                }
                if (seconds > 59) {
                    seconds = 0;
                    minutes++;
                }
                if (minutes > 59) {
                    minutes = 0;
                    hours++;
                }

            }

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(s) {
                        centiSecondsIMG.setImageDrawable(drawables[centiseconds * 10]);
                        secondsIMG.setImageDrawable(drawables[seconds]);
                        minutesIMG.setImageDrawable(drawables[minutes]);
                        hoursIMG.setImageDrawable(drawables[hours]);
                        if (bool == 3) {
                            earnings = earnings.add(msEarnings.multiply(new BigDecimal(3)));
                            earningsLabel.setText(getString(R.string.currencySign) + " " + b.format(earnings));
                            bool = 1;
                        }
                        bool++;
                    } else {
                        Toast.makeText(MainActivity.this, R.string.maxReachedPrompt, Toast.LENGTH_SHORT).show();
                        Intent w = new Intent(MainActivity.this, dialogs.class);
                        clockNotFull =false; s=true;
                        timeFilled = dateFormat.format(Calendar.getInstance().getTime());
                        w.putExtra("dialogType", getString(R.string.clockFullPromptDialog));
                        w.putExtra("time", timeFilled);
                        startActivity(w);
                        startButton.setText(R.string.btnStart);
                        timerRunning = false;
                        scheduledFuture.cancel(true);
                    }

                }
            });

        }
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("hours", hours);
        outState.putInt("minutes", minutes);
        outState.putInt("seconds", seconds);
        outState.putInt("centiseconds", centiseconds);

        outState.putString("rate", String.valueOf(rate.getText()));
        outState.putDouble("earnings", earnings.doubleValue());

        outState.putLong("startMS", startMS);
        outState.putLong("endMS", endMS);

        super.onSaveInstanceState(outState);
    }

    public void clear(View v) {
        Intent reset = new Intent(MainActivity.this, dialogs.class);
        reset.putExtra("dialogType", "reset_dialog");
        startActivityForResult(reset, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeRate(View view) {

        if (timerRunning) {
            Toast.makeText(MainActivity.this, R.string.pauseClockPrompt, Toast.LENGTH_SHORT).show();
        } else {
            Intent startDialog = new Intent(this, dialogs.class);
            startDialog.putExtra("dialogType", getString(R.string.enterTextDialog));
            startActivityForResult(startDialog, 1);
        }
    }

    public void openHistoryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1: {
                    if(Double.parseDouble(data.getStringExtra("rate")) == 0)
                    {
                        rate.setText("00.00");
                    }else rate.setText(c.format(Double.parseDouble(data.getStringExtra("rate"))));

                    msEarnings = new BigDecimal(Double.parseDouble(data.getStringExtra("rate"))).divide(sixty, MathContext.DECIMAL64);
                    msEarnings = msEarnings.divide(sixty, MathContext.DECIMAL64);
                    msEarnings = msEarnings.divide(ten, MathContext.DECIMAL64);

                    break;
                }
                case 2: {
                    if (data.getStringExtra("clockReset").equals("1")) {
                        hours = 0;
                        minutes = 0;
                        seconds = 0;
                        centiseconds = 0;
                        centiSecondsIMG.setImageDrawable(drawables[centiseconds*10]);
                        secondsIMG.setImageDrawable(drawables[seconds]);
                        minutesIMG.setImageDrawable(drawables[minutes]);
                        hoursIMG.setImageDrawable(drawables[hours]);
                        clockNotFull = true;

                    } else if (data.getStringExtra("clockReset").equals("2")) {
                        earningsLabel.setText("$00.00");
                        earnings = new BigDecimal(0);
                    } else if (data.getStringExtra("clockReset").equals("3")){
                        clockNotFull = true;
                        earningsLabel.setText("$00.00");
                        earnings = new BigDecimal(0);
                        hours = 0;
                        minutes = 0;
                        seconds = 0;
                        centiseconds = 0;
                        centiSecondsIMG.setImageDrawable(drawables[centiseconds*10]);
                        secondsIMG.setImageDrawable(drawables[seconds]);
                        minutesIMG.setImageDrawable(drawables[minutes]);
                        hoursIMG.setImageDrawable(drawables[hours]);
                    }
                    break;
                }
            }

        }
    }

    public void startClicked(View view) {
        if (clockNotFull) {
            if (String.valueOf(startButton.getText()).equals(getString(R.string.btnStart))) {
                if (Double.parseDouble(String.valueOf(rate.getText()).replace(",", "")) != 0.0) {

                    scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(timer, 0, 100, TimeUnit.MILLISECONDS);
                    savable = true; timerRunning = true;
                    startButton.setText(R.string.btnPause);
                }
            } else if (String.valueOf(startButton.getText()).equals(getString(R.string.btnPause))) {
                scheduledFuture.cancel(true); timerRunning=false;
                startButton.setText(R.string.btnStart);
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.maxReachedPrompt, Toast.LENGTH_SHORT).show();
            Intent w = new Intent(MainActivity.this, dialogs.class);
            //String time = dateFormat.format(Calendar.getInstance().getTime());
            w.putExtra("dialogType", getString(R.string.clockFullPromptDialog));
            w.putExtra("time", timeFilled);
            startActivity(w);
        }
    }

    public void savePay(View view) {
        if (savable) {
            payBar.setEnabled(true);
            timerRunning = false;
            startButton.setText(R.string.btnStart);

            Date d = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatDate = new SimpleDateFormat("d");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatDay = new SimpleDateFormat("EEE");

            String[] date = {String.valueOf(dateFormatDay.format(d)), String.valueOf(dateFormatDate.format(d)),
                    String.valueOf(dateFormatMonth.format(d)), String.valueOf(dateFormatYear.format(d))};

            String []timeWorked = {String.format("%02d", hours), String.format("%02d", minutes),
                    String.format("%02d", seconds), String.format("%02d", centiseconds)};

            String moneyEarned = getString(R.string.currencySign) + " " + " " + c.format(earnings);
            String hourlyRate = getString(R.string.currencySign) + " " + " " + c.format(Double.parseDouble(String.valueOf(rate.getText()).replace(",", ""))) + " " + getString(R.string.label_PERHOUR);

            helper.insertData(date, timeWorked, hourlyRate, moneyEarned, this);
            Toast.makeText(MainActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
            scheduledFuture.cancel(true);

        } else Toast.makeText(MainActivity.this, R.string.nothingToSave, Toast.LENGTH_SHORT).show();
    }

}


