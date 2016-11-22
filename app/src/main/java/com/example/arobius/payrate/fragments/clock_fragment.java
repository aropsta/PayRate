package com.example.arobius.payrate.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arobius.payrate.R;
import com.example.arobius.payrate.Util;
import com.example.arobius.payrate.adapters.databaseAdapter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_CANCELED;

public class clock_fragment extends Fragment {

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE [ H:mm:ss ]\ndd-MMM-yyyy  z");
    DecimalFormat b = new DecimalFormat("#,##0.000");
    DecimalFormat c = new DecimalFormat("###,###,##0.00");

    int centiseconds, seconds, minutes, hours;
    boolean MAX_TIME_REACHED, SAVABLE, TIMER_RUNNING, s;
    int earningsLabelUpdateRate = 1;

    BigDecimal msEarnings, earnings, sixty, ten;

    TextView rate, earningsLabel;
    Drawable[] drawables;

    {
        drawables = new Drawable[]{ContextCompat.getDrawable(getContext(), R.drawable.digit00),
                ContextCompat.getDrawable(getContext(), R.drawable.digit01),
                ContextCompat.getDrawable(getContext(), R.drawable.digit02),
                ContextCompat.getDrawable(getContext(), R.drawable.digit03),
                ContextCompat.getDrawable(getContext(), R.drawable.digit04),
                ContextCompat.getDrawable(getContext(), R.drawable.digit05),
                ContextCompat.getDrawable(getContext(), R.drawable.digit06),
                ContextCompat.getDrawable(getContext(), R.drawable.digit07),
                ContextCompat.getDrawable(getContext(), R.drawable.digit08),
                ContextCompat.getDrawable(getContext(), R.drawable.digit09),
                ContextCompat.getDrawable(getContext(), R.drawable.digit10),
                ContextCompat.getDrawable(getContext(), R.drawable.digit11),
                ContextCompat.getDrawable(getContext(), R.drawable.digit12),
                ContextCompat.getDrawable(getContext(), R.drawable.digit13),
                ContextCompat.getDrawable(getContext(), R.drawable.digit14),
                ContextCompat.getDrawable(getContext(), R.drawable.digit15),
                ContextCompat.getDrawable(getContext(), R.drawable.digit16),
                ContextCompat.getDrawable(getContext(), R.drawable.digit17),
                ContextCompat.getDrawable(getContext(), R.drawable.digit18),
                ContextCompat.getDrawable(getContext(), R.drawable.digit19),
                ContextCompat.getDrawable(getContext(), R.drawable.digit20),
                ContextCompat.getDrawable(getContext(), R.drawable.digit21),
                ContextCompat.getDrawable(getContext(), R.drawable.digit22),
                ContextCompat.getDrawable(getContext(), R.drawable.digit23),
                ContextCompat.getDrawable(getContext(), R.drawable.digit24),
                ContextCompat.getDrawable(getContext(), R.drawable.digit25),
                ContextCompat.getDrawable(getContext(), R.drawable.digit26),
                ContextCompat.getDrawable(getContext(), R.drawable.digit27),
                ContextCompat.getDrawable(getContext(), R.drawable.digit28),
                ContextCompat.getDrawable(getContext(), R.drawable.digit29),
                ContextCompat.getDrawable(getContext(), R.drawable.digit30),
                ContextCompat.getDrawable(getContext(), R.drawable.digit31),
                ContextCompat.getDrawable(getContext(), R.drawable.digit32),
                ContextCompat.getDrawable(getContext(), R.drawable.digit33),
                ContextCompat.getDrawable(getContext(), R.drawable.digit34),
                ContextCompat.getDrawable(getContext(), R.drawable.digit35),
                ContextCompat.getDrawable(getContext(), R.drawable.digit36),
                ContextCompat.getDrawable(getContext(), R.drawable.digit37),
                ContextCompat.getDrawable(getContext(), R.drawable.digit38),
                ContextCompat.getDrawable(getContext(), R.drawable.digit39),
                ContextCompat.getDrawable(getContext(), R.drawable.digit40),
                ContextCompat.getDrawable(getContext(), R.drawable.digit41),
                ContextCompat.getDrawable(getContext(), R.drawable.digit42),
                ContextCompat.getDrawable(getContext(), R.drawable.digit43),
                ContextCompat.getDrawable(getContext(), R.drawable.digit44),
                ContextCompat.getDrawable(getContext(), R.drawable.digit45),
                ContextCompat.getDrawable(getContext(), R.drawable.digit46),
                ContextCompat.getDrawable(getContext(), R.drawable.digit47),
                ContextCompat.getDrawable(getContext(), R.drawable.digit48),
                ContextCompat.getDrawable(getContext(), R.drawable.digit49),
                ContextCompat.getDrawable(getContext(), R.drawable.digit50),
                ContextCompat.getDrawable(getContext(), R.drawable.digit51),
                ContextCompat.getDrawable(getContext(), R.drawable.digit52),
                ContextCompat.getDrawable(getContext(), R.drawable.digit53),
                ContextCompat.getDrawable(getContext(), R.drawable.digit54),
                ContextCompat.getDrawable(getContext(), R.drawable.digit55),
                ContextCompat.getDrawable(getContext(), R.drawable.digit56),
                ContextCompat.getDrawable(getContext(), R.drawable.digit57),
                ContextCompat.getDrawable(getContext(), R.drawable.digit58),
                ContextCompat.getDrawable(getContext(), R.drawable.digit59),
                ContextCompat.getDrawable(getContext(), R.drawable.digit60),
                ContextCompat.getDrawable(getContext(), R.drawable.digit61),
                ContextCompat.getDrawable(getContext(), R.drawable.digit62),
                ContextCompat.getDrawable(getContext(), R.drawable.digit63),
                ContextCompat.getDrawable(getContext(), R.drawable.digit64),
                ContextCompat.getDrawable(getContext(), R.drawable.digit65),
                ContextCompat.getDrawable(getContext(), R.drawable.digit66),
                ContextCompat.getDrawable(getContext(), R.drawable.digit67),
                ContextCompat.getDrawable(getContext(), R.drawable.digit68),
                ContextCompat.getDrawable(getContext(), R.drawable.digit69),
                ContextCompat.getDrawable(getContext(), R.drawable.digit70),
                ContextCompat.getDrawable(getContext(), R.drawable.digit71),
                ContextCompat.getDrawable(getContext(), R.drawable.digit72),
                ContextCompat.getDrawable(getContext(), R.drawable.digit73),
                ContextCompat.getDrawable(getContext(), R.drawable.digit74),
                ContextCompat.getDrawable(getContext(), R.drawable.digit75),
                ContextCompat.getDrawable(getContext(), R.drawable.digit76),
                ContextCompat.getDrawable(getContext(), R.drawable.digit77),
                ContextCompat.getDrawable(getContext(), R.drawable.digit78),
                ContextCompat.getDrawable(getContext(), R.drawable.digit79),
                ContextCompat.getDrawable(getContext(), R.drawable.digit80),
                ContextCompat.getDrawable(getContext(), R.drawable.digit81),
                ContextCompat.getDrawable(getContext(), R.drawable.digit82),
                ContextCompat.getDrawable(getContext(), R.drawable.digit83),
                ContextCompat.getDrawable(getContext(), R.drawable.digit84),
                ContextCompat.getDrawable(getContext(), R.drawable.digit85),
                ContextCompat.getDrawable(getContext(), R.drawable.digit86),
                ContextCompat.getDrawable(getContext(), R.drawable.digit87),
                ContextCompat.getDrawable(getContext(), R.drawable.digit88),
                ContextCompat.getDrawable(getContext(), R.drawable.digit89),
                ContextCompat.getDrawable(getContext(), R.drawable.digit90),
                ContextCompat.getDrawable(getContext(), R.drawable.digit91),
                ContextCompat.getDrawable(getContext(), R.drawable.digit92),
                ContextCompat.getDrawable(getContext(), R.drawable.digit93),
                ContextCompat.getDrawable(getContext(), R.drawable.digit94),
                ContextCompat.getDrawable(getContext(), R.drawable.digit95),
                ContextCompat.getDrawable(getContext(), R.drawable.digit96),
                ContextCompat.getDrawable(getContext(), R.drawable.digit97),
                ContextCompat.getDrawable(getContext(), R.drawable.digit98),
                ContextCompat.getDrawable(getContext(), R.drawable.digit99)};
    }

    RelativeLayout payBar;

    ScheduledExecutorService scheduledExecutorService;
    ScheduledFuture scheduledFuture;

    Button startButton, saveButton, historyButton, resetButton;
    ImageView hoursIMG, minutesIMG, secondsIMG, centiSecondsIMG;

    databaseAdapter helper;
    String timeFilled;
    myThread thread;

    public clock_fragment() {

    }

    class myThread extends Thread {
        boolean s = true;

        @Override
        public void run() {
            Log.e("VIVZ", Thread.currentThread().getName());
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

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (s) {
                        if (isAdded()) {
                            earningsLabelUpdateRate++;
                            drawables(false);
                            if (earningsLabelUpdateRate == 3) {
                                Log.e("VIVZ", Thread.currentThread().getName());

                                earnings = earnings.add(msEarnings.multiply(new BigDecimal(3)));
                                earningsLabel.setText(getString(R.string.currencySign) + " " + b.format(earnings));
                                earningsLabelUpdateRate = 0;
                            }
                        }
                    } else if (isAdded()) {
                        timer("stop");
                        startButton.setText(R.string.btnStart);

                        timeFilled=dateFormat.format(Calendar.getInstance().getTime());
                        maxTimeReachedDialog(timeFilled);


                    }
                }
            });

        }


    }

    void timer(String startOrStop) {

        switch (startOrStop) {

            case "start": {
                SAVABLE = true;
                TIMER_RUNNING = true;


                if (thread == null) {
                    thread = new myThread();
                }
                thread.setPriority(Thread.MAX_PRIORITY);

                if (scheduledExecutorService == null) {
                    scheduledExecutorService = Executors.newScheduledThreadPool(16);
                }

                scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(thread, 0, 100, TimeUnit.MILLISECONDS);
            }
            case "stop": {

                if (!scheduledFuture.isCancelled()) {
                    TIMER_RUNNING = false;
                    scheduledFuture.cancel(true);
                }
            }
        }
    }

    void drawables(boolean f) {
        if (f) {
            if (drawables == null) {
                /*drawables = new Drawable[]{ContextCompat.getDrawable(getContext(), R.drawable.digit00),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit01),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit02),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit03),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit04),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit05),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit06),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit07),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit08),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit09),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit10),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit11),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit12),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit13),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit14),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit15),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit16),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit17),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit18),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit19),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit20),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit21),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit22),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit23),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit24),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit25),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit26),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit27),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit28),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit29),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit30),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit31),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit32),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit33),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit34),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit35),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit36),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit37),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit38),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit39),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit40),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit41),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit42),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit43),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit44),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit45),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit46),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit47),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit48),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit49),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit50),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit51),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit52),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit53),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit54),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit55),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit56),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit57),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit58),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit59),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit60),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit61),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit62),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit63),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit64),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit65),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit66),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit67),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit68),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit69),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit70),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit71),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit72),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit73),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit74),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit75),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit76),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit77),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit78),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit79),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit80),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit81),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit82),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit83),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit84),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit85),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit86),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit87),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit88),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit89),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit90),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit91),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit92),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit93),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit94),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit95),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit96),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit97),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit98),
                        ContextCompat.getDrawable(getContext(), R.drawable.digit99)};
                        */
            }

        } else {
            centiSecondsIMG.setImageDrawable(drawables[centiseconds * 10]);
            secondsIMG.setImageDrawable(drawables[seconds]);
            minutesIMG.setImageDrawable(drawables[minutes]);
            hoursIMG.setImageDrawable(drawables[hours]);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Log.e("VIVZ", "onCreateView");
        super.onCreate(savedInstanceState);
        drawables(true);
        sixty = new BigDecimal(60);
        ten = new BigDecimal(10);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.e("VIVZ", "onActivityCreated()");
        helper = new databaseAdapter(getContext());


        if (savedInstanceState != null) {
            restoreState(savedInstanceState);

        } else {
            initializeValues();
        }
    }

    void restoreState(Bundle state) {
        Log.e("VIVZ", "restoreState()");

        centiseconds = state.getInt("centiseconds");
        seconds = state.getInt("seconds");
        minutes = state.getInt("minutes");
        hours = state.getInt("hours");

        earnings = new BigDecimal(state.getDouble("earnings"));
        earningsLabel.setText(state.getCharSequence("earningsLabel"));

        rate.setText(state.getString("rate"));
        msEarnings = new BigDecimal(state.getDouble("msEarnings"));

        TIMER_RUNNING = state.getBoolean("TIMER_RUNNING");
        MAX_TIME_REACHED = state.getBoolean("MAX_TIME_REACHED");
        SAVABLE = state.getBoolean("SAVABLE");
        s = state.getBoolean("s");

        startButton.setText(state.getCharSequence("startButton"));

        drawables(false);

        if (TIMER_RUNNING) {
            timer("start");
        }
    }

    void saveState(Bundle state) {
        Log.e("VIVZ", "saveState()");
        state.putInt("centiseconds", centiseconds);
        state.putInt("seconds", seconds);
        state.putInt("minutes", minutes);
        state.putInt("hours", hours);

        state.putDouble("earnings", earnings.doubleValue());
        state.putCharSequence("earningsLabel", earningsLabel.getText());

        state.putString("rate", String.valueOf(rate.getText()));
        state.putDouble("msEarnings", msEarnings.doubleValue());

        state.putBoolean("TIMER_RUNNING", TIMER_RUNNING);
        state.putBoolean("MAX_TIME_REACHED", MAX_TIME_REACHED);
        state.putBoolean("SAVABLE", SAVABLE);
        state.putBoolean("s", s);

        state.putCharSequence("startButton", startButton.getText());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.e("VIVZ", "onCreateView()");
        return inflater.inflate(R.layout.tab_clock, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Log.e("VIVZ", "onSaveInstanceState()");
        saveState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.e("VIVZ", "onViewCreated()");

        if (isAdded()) setViews(view);

    }

    void setViews(View view) {
        payBar = (RelativeLayout) view.findViewById(R.id.payrateBar);
        payBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRate();
            }
        });

        startButton = (Button) view.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startClicked();
            }
        });

        saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePay();
            }
        });

        resetButton = (Button) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        historyButton = (Button) view.findViewById(R.id.historyButton);

        hoursIMG = (ImageView) view.findViewById(R.id.hoursImage);
        minutesIMG = (ImageView) view.findViewById(R.id.minutesImage);
        secondsIMG = (ImageView) view.findViewById(R.id.secondsImage);
        centiSecondsIMG = (ImageView) view.findViewById(R.id.centiSecondsImage);

        rate = (TextView) view.findViewById(R.id.rate);
        earningsLabel = (TextView) view.findViewById(R.id.earningsLabel);
    }

    void initializeValues() {
        centiseconds = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        TIMER_RUNNING = false;
        MAX_TIME_REACHED = false;
        SAVABLE = true;
        msEarnings = new BigDecimal(0);
        earnings = new BigDecimal(0);
    }

    public void changeRate() {

        if (TIMER_RUNNING) {
            Toast.makeText(getActivity(), R.string.pauseClockPrompt, Toast.LENGTH_SHORT).show();
        } else {

            fragments_dialogs dialog = new fragments_dialogs();
            dialog.setTargetFragment(this, Util.DIALOG_ENTER_TEXT);
            Bundle bundle = new Bundle();
            bundle.putInt("dialog_type", 1);
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "dialog");
        }
    }

    public void startClicked() {

        if (!MAX_TIME_REACHED) {
            switch (startButton.getText().toString()) {
                case "START": {
                    timer("start");
                    startButton.setText(R.string.btnPause);
                }
                case "PAUSE": {
                    timer("stop");
                    startButton.setText(R.string.btnStart);
                }
            }
        } else  maxTimeReachedDialog(timeFilled);

    }

    void maxTimeReachedDialog(String timeFilled) {
        Toast.makeText(getContext(), R.string.maxReachedPrompt, Toast.LENGTH_SHORT).show();
        fragments_dialogs dialog = new fragments_dialogs();
        dialog.setTargetFragment(this, Util.DIALOG_MAX_TIME_REACHED);
        Bundle bundle = new Bundle();
        bundle.putInt("dialog_type", 2);
        bundle.putString("time", timeFilled);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1: {
                    if (data.getStringExtra("rate") != null && !data.getStringExtra("rate").equals("")) {
                        if ((Double.parseDouble(data.getStringExtra("rate")) == 0)) {
                            rate.setText(getString(R.string.zero));
                        } else
                            rate.setText(c.format(Double.parseDouble(data.getStringExtra("rate"))));

                        msEarnings = new BigDecimal(Double.parseDouble(data.getStringExtra("rate"))).divide(sixty, MathContext.DECIMAL64)
                                .divide(sixty, MathContext.DECIMAL64).divide(ten, MathContext.DECIMAL64);

                    }

                    break;
                }
                case 2: {
                    if (data.getStringExtra("clockReset").equals("1")) {
                        hours = 0;
                        minutes = 0;
                        seconds = 0;
                        s = true;
                        centiseconds = 0;
                        drawables(false);
                        timeFilled = null;
                        MAX_TIME_REACHED = true;

                    } else if (data.getStringExtra("clockReset").equals("2")) {
                        earningsLabel.setText("$00.00");
                        earnings = new BigDecimal(0);
                    } else if (data.getStringExtra("clockReset").equals("3")) {
                        MAX_TIME_REACHED = true;
                        earningsLabel.setText("$00.00");
                        earnings = new BigDecimal(0);
                        hours = 0;
                        minutes = 0;
                        seconds = 0;
                        timeFilled = null;
                        s = true;
                        centiseconds = 0;
                        drawables(false);
                    }
                    break;
                }
            }

        }
    }

    public void clear() {
        fragments_dialogs dialog = new fragments_dialogs();
        dialog.setTargetFragment(this, Util.DIALOG_CLEAR);
        Bundle bundle = new Bundle();
        bundle.putInt("dialog_type", 3);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "dialog");
    }

    public void savePay() {
        if (SAVABLE) {
            payBar.setEnabled(true);
            if (TIMER_RUNNING) {
                //scheduledFuture.cancel(false);
                //communicator.stopClock();
            }
            TIMER_RUNNING = false;
            startButton.setText(R.string.btnStart);

            Date d = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatDate = new SimpleDateFormat("d");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatDay = new SimpleDateFormat("EEE");

            String[] date = {String.valueOf(dateFormatDay.format(d)), String.valueOf(dateFormatDate.format(d)),
                    String.valueOf(dateFormatMonth.format(d)), String.valueOf(dateFormatYear.format(d))};

            String[] timeWorked = {String.format("%02d", hours), String.format("%02d", minutes),
                    String.format("%02d", seconds), String.format("%02d", centiseconds)};

            String moneyEarned = getString(R.string.currencySign) + " " + " " + c.format(earnings);
            String hourlyRate = getString(R.string.currencySign) + " " + " " + c.format(Double.parseDouble(String.valueOf(rate.getText()).replace(",", ""))) + " " + getString(R.string.label_PERHOUR);

            helper.insertData(date, timeWorked, hourlyRate, moneyEarned, getContext());
            Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();

            //communicator.getPagerAdapter().notifyDataSetChanged();


        } else Toast.makeText(getContext(), R.string.nothingToSave, Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public void onStart() {
        super.onStart();
        Log.e("VIVZ", "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("VIVZ", "onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("VIVZ", "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("VIVZ", "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("VIVZ", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("VIVZ", "onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("VIVZ", "onPause()");

    }*/


}

