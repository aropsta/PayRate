package com.example.arobius.payrate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.arobius.payrate.R;
import com.example.arobius.payrate.adapters.customRecyclerView;
import com.example.arobius.payrate.adapters.databaseAdapter;
import com.example.arobius.payrate.adapters.historyData;
import com.example.arobius.payrate.adapters.RecyclerAdapter;

import java.util.ArrayList;


public class history_fragment extends Fragment{

    ArrayList<historyData> arrayList;
    customRecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    LinearLayout bottomBar;


    databaseAdapter helper;
    Button yesButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_history, container, false);
        helper = new databaseAdapter(getContext());
        arrayList = helper.extractArrayList();

        yesButton = (Button) view.findViewById(R.id.yesButton);
        bottomBar = (LinearLayout) view.findViewById(R.id.historyBottomBar);
        recyclerView = (customRecyclerView) view.findViewById(R.id.recyclerView);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        recyclerView.getView(relativeLayout);
        recyclerAdapter = new RecyclerAdapter(getContext(), arrayList, yesButton);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

public void refresh(){
    recyclerAdapter.notifyDataSetChanged();
}
}
