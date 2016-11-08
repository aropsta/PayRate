package com.example.arobius.payrate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clock_fragment clock = new clock_fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.root, clock, "clock");
        fragmentTransaction.commit();

    }

   /* @Override
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
    }*/




 /*   public void openHistoryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivityForResult(intent, 3);
    }*/






}


