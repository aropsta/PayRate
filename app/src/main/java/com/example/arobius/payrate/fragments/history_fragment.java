package com.example.arobius.payrate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arobius.payrate.R;
import com.example.arobius.payrate.adapters.databaseAdapter;
import com.example.arobius.payrate.adapters.historyData;
import com.example.arobius.payrate.adapters.recyclerAdapter;

import java.util.ArrayList;


public class history_fragment extends Fragment {

    ArrayList<historyData> arrayList;
    RecyclerView recyclerView;
    com.example.arobius.payrate.adapters.recyclerAdapter recyclerAdapter;
    LinearLayout bottomBar;
    databaseAdapter helper;
    Button yesButton;
    TextView textView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_history, container, false);
        helper = new databaseAdapter(getContext());
        arrayList = helper.extractArrayList();

        textView = (TextView)view.findViewById(R.id.noHistory);
        yesButton = (Button) view.findViewById(R.id.yesButton);
        bottomBar = (LinearLayout) view.findViewById(R.id.historyBottomBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


        if(arrayList.isEmpty()){
            textView.setVisibility(View.VISIBLE);
            recyclerAdapter = new recyclerAdapter(getContext(), arrayList, false, yesButton);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }else {
            textView.setVisibility(View.GONE);

            recyclerAdapter = new recyclerAdapter(getContext(), arrayList, false, yesButton);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerAdapter.notifyDataSetChanged();
    }



    public static history_fragment getInstance(int position){

        return new history_fragment();
    }
}
