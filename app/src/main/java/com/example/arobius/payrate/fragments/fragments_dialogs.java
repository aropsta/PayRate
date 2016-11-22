package com.example.arobius.payrate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arobius.payrate.R;
import com.example.arobius.payrate.Util;

import static android.app.Activity.RESULT_OK;


public class fragments_dialogs extends DialogFragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = this.getArguments();
        switch (args.getInt("dialog_type")) {
            case Util.DIALOG_ENTER_TEXT: {
                return enterTextDialog(inflater, container);
            }
            case Util.DIALOG_MAX_TIME_REACHED: {


                return maxDialog(inflater, container, args);
            }
            case Util.DIALOG_CLEAR: {

                return clearDialog(inflater, container);
            }
            default:
                view = inflater.inflate(R.layout.dialog_error, container, false);
        }

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        return view;
    }

    private View maxDialog(LayoutInflater inflater, ViewGroup container, Bundle args) {
        this.setCancelable(false);

        Button clockFullOk = (Button) view.findViewById(R.id.btnOK2);
        clockFullOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView clockFullText = (TextView) view.findViewById(R.id.reached);

        clockFullText.setText(getString(R.string.filledAtPrompt)+args.getString("time"));

        return inflater.inflate(R.layout.dialog_full_prompt, container, false);
    }

    private View enterTextDialog(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.dialog_enter_text, container, false);

        Button ok = (Button) view.findViewById(R.id.btnOK);

        final EditText editText = (EditText) view.findViewById(R.id.editText);

        editText.requestFocus();
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final Intent i = new Intent();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = String.valueOf(editText.getText());

                if (!(s==null) && !(s.equals("")) && !(s.isEmpty()) && !(s.equals("+")) && !(s.equals("-")) && !(s.equals(".")) && !(s.equals("-.")) && !(s.equals("+."))) {

                    if (Double.parseDouble(s) > 99999999) {

                        Toast.makeText(getContext(), R.string.maxIs99Mill, Toast.LENGTH_LONG).show();
                        i.putExtra("rate", "99999999");
                    } else if (Double.parseDouble(s) < 0) {
                        Toast.makeText(getContext(), R.string.negativeNumberEntered, Toast.LENGTH_LONG).show();
                    } else {
                        i.putExtra("rate", s);
                    }
                } else {
                    i.putExtra("rate", "");
                }
                getTargetFragment().onActivityResult(1, RESULT_OK, i);
                dismiss();
            }
        });

        return view;
    }

    private View clearDialog(LayoutInflater inflater, ViewGroup container){
        Button ok = (Button) view.findViewById(R.id.btnResetOK);
        Button cancel = (Button) view.findViewById(R.id.btnResetCancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ListView listView = (ListView) view.findViewById(R.id.listView);
                if (listView.isItemChecked(0) && !listView.isItemChecked(1)) {
                    i.putExtra("clockReset", "1");
                    getTargetFragment().onActivityResult(2, -1, i);

                } else if (listView.isItemChecked(1) && !listView.isItemChecked(0)) {
                    i.putExtra("clockReset", "2");
                    getTargetFragment().onActivityResult(2, -1, i);
                } else if (listView.isItemChecked(0) && listView.isItemChecked(1)) {
                    i.putExtra("clockReset", "3");
                    getTargetFragment().onActivityResult(2, -1, i);
                }

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String[] listViewItemsResetDialog = {"Reset Clock", "Reset Earnings"};

        ListAdapter theAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice,
                listViewItemsResetDialog);

        final ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(theAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listView.setItemChecked(position, listView.isItemChecked(position));

            }
        });
        return inflater.inflate(R.layout.dialog_reset, container, false);
    }
    public fragments_dialogs() {

    }
}
