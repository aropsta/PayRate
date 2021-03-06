package com.example.arobius.payrate.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arobius.payrate.R;
import com.example.arobius.payrate.Util;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<historyData> arrayList;
    private Button yesButton;
    private Context context;
    Util util = new Util();

    public RecyclerAdapter(Context context, ArrayList<historyData> arrayList, Button yesButton) {
        inflater = LayoutInflater.from(context);
        update(arrayList);
        this.context = context;
        this.yesButton = yesButton;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.history_listview, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        historyData a = arrayList.get(position);

        holder.rate.setText(a.getHourlyRate());
        holder.earnings.setText(a.getMoneyEarned());

        String h2 = context.getString(R.string.h)+" ", m2 = context.getString(R.string.m)+" ", s2 = context.getString(R.string.s);

        String h = String.valueOf(a.getTimeWorked()[0]);
        String m = String.valueOf(a.getTimeWorked()[1]);
        String s = String.valueOf(a.getTimeWorked()[2]);

        if(h.equals("00")) {h2 = ""; h = "";}
        if(m.equals("00")) {m2 = ""; m = "";}
        if(s.equals("00")) {s2 = "";s = "";}

        holder.date.setText(a.getDate()[0] + " " + a.getDate()[1]
                + " " + a.getDate()[2] + " " + a.getDate()[3]);

        holder.timeWorked.setText(h + h2 + m + m2 + s + s2);

        if(arrayList.get(0).isCheckbox()){
            holder.checkbox.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_START);
            params.addRule(RelativeLayout.ALIGN_END);

            params.setMargins(0, 0, util.dpToPx(context, 50), 0);
            holder.earnings.setLayoutParams(params);
            holder.checkbox.setChecked(arrayList.get(position).isChecked());
        }else{
            holder.checkbox.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_END);
            params.addRule(RelativeLayout.ALIGN_START);
            params.setMargins(0, 0, util.dpToPx(context, 50), 0);
            holder.earnings.setLayoutParams(params);
            holder.checkbox.setChecked(arrayList.get(position).isChecked());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void update(ArrayList<historyData> arrayList){
       this.arrayList = arrayList;
       notifyItemInserted(arrayList.size());
    }




    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView earnings, date, timeWorked, rate;
        CheckBox checkbox; LinearLayout linearLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            rate = (TextView) itemView.findViewById(R.id.listHourlyRate);
            timeWorked = (TextView) itemView.findViewById(R.id.listTimeWorked);
            date = (TextView) itemView.findViewById(R.id.listDate);
            earnings = (TextView) itemView.findViewById(R.id.listEarnings);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrayList.get(0).isCheckbox()) {
                        yesButton.setEnabled(false);
                        checkbox.toggle();
                        arrayList.get(getAdapterPosition()).setChecked(checkbox.isChecked());

                        for (int i = 0; i < getItemCount(); i++) {
                            if (arrayList.get(i).isChecked()) {
                                yesButton.setEnabled(true);
                            }
                        }
                    }
                }
            });
        }


    }
}

