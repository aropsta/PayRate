package com.example.arobius.payrate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arobius.payrate.java.databaseAdapter;
import com.example.arobius.payrate.java.historyData;
import com.example.arobius.payrate.java.recyclerAdapter;

import java.util.ArrayList;

class HistoryActivity extends AppCompatActivity  {

    ArrayList<historyData> arrayList;
    RecyclerView recyclerView;
    com.example.arobius.payrate.java.recyclerAdapter recyclerAdapter;
    LinearLayout bottomBar;
    boolean selectionView = false;
    databaseAdapter helper;
    Button yesButton;
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.historyToolBar);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        helper = new databaseAdapter(this);
        arrayList = helper.extractArrayList();

        textView = (TextView)findViewById(R.id.noHistory);
        yesButton = (Button) findViewById(R.id.yesButton);
        bottomBar = (LinearLayout) findViewById(R.id.historyBottomBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        if(arrayList.isEmpty()){
            textView.setVisibility(View.VISIBLE);
            recyclerAdapter = new recyclerAdapter(this, arrayList, false, yesButton);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }else {
            textView.setVisibility(View.GONE);

            recyclerAdapter = new recyclerAdapter(this, arrayList, false, yesButton);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //listView.setAdapter(theAdapter);

        }

    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home: {
                finish();
            }break;

            case R.id.historyMenu_deleteEntries:{
                if(recyclerView.getAdapter().getItemCount() > 0) {
                    if(!selectionView)  {
                        selectionView = true;
                        for(int i = 0; i < arrayList.size(); i++) arrayList.get(i).setChecked(false);
                        arrayList.get(0).setCheckbox(true);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        bottomBar.setVisibility(View.VISIBLE);
                    }
                    else {
                        selectionView = false;
                        arrayList.get(0).setCheckbox(false);
                        recyclerView.getAdapter().notifyDataSetChanged();

                        bottomBar.setVisibility(View.GONE);
                    }
                }else
                    Toast.makeText(HistoryActivity.this, R.string.nothingtodelete, Toast.LENGTH_SHORT).show();
            }break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public void historyBarYes(View view) {

        yesButton.setEnabled(false);
        ArrayList<String> a = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isChecked()) {
                a.add(String.valueOf(i));
            }
        }

        if (!a.isEmpty()) {

            if (a.size() == arrayList.size()) {
                recyclerAdapter.notifyItemRangeRemoved(0, arrayList.size());
                arrayList.get(0).setCheckbox(false);
                arrayList.clear();

            } else {
                arrayList.get(0).setCheckbox(false);
                for (int i = (a.size() - 1); i >= 0; i--) {
                    recyclerAdapter.notifyItemRemoved((Integer.parseInt(a.get(i))));
                    arrayList.remove((Integer.parseInt(a.get(i))));
                }

            }

            helper.deleteDatabase(this);

            helper = new databaseAdapter(this);


            for (int i = 0; i < arrayList.size(); i++) {
                helper.insertData(arrayList.get(i).getDate(), arrayList.get(i).getTimeWorked(), arrayList.get(i).getHourlyRate(), arrayList.get(i).getMoneyEarned(), this);
            }

            if (arrayList.size() == 0) textView.setVisibility(View.VISIBLE);
            else {
                textView.setVisibility(View.GONE);
                arrayList.get(0).setCheckbox(false);

            }
            recyclerView.getAdapter().notifyDataSetChanged();
            bottomBar.setVisibility(View.GONE);
            selectionView = false;

        }


    }

    public void historyBarCancel(View view) {
        yesButton.setEnabled(false);
        arrayList.get(0).setCheckbox(false);
        recyclerView.getAdapter().notifyDataSetChanged();
        bottomBar.setVisibility(View.GONE);
        selectionView = false;
    }

}
