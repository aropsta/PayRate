package com.example.arobius.payrate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class dialogs extends Activity {
    Button editTextOk, clockFullOk;
    TextView clockFullText;
    EditText editText;
    ListView listView;
    Intent i = new Intent();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent q  = getIntent();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.enter_text_dialog);

        switch(q.getStringExtra("dialogType")){
            case "enter_text_dialog":{
                setContentView(R.layout.enter_text_dialog);
                editText= (EditText)findViewById(R.id.editText);
                editTextOk = (Button)findViewById(R.id.btnOK);
              /*  InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);*/

                break;
            }
            case "clock_full_prompt_dialog":{
                setFinishOnTouchOutside(false);
                setContentView(R.layout.clock_full_prompt_dialog);
                clockFullOk = (Button)findViewById(R.id.btnOK2);
                clockFullText=(TextView)findViewById(R.id.reached);
                clockFullText.append(String.valueOf(q.getStringExtra("time")));
                break;
            }
            case "reset_dialog":{
                setContentView(R.layout.reset_dialog);

                String [] listViewItemsResetDialog = {"Reset Clock", "Reset Earnings"};

                ListAdapter theAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,
                        listViewItemsResetDialog);

                listView = (ListView) findViewById(R.id.listView);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(theAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        listView.setItemChecked(position, listView.isItemChecked(position));

                    }
                });

                break;
            }
            default: setContentView(R.layout.error_dialog);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void enterTextOk(View view) {
        String s = String.valueOf(editText.getText());

        if ((s != null) && !(s.isEmpty()) && !(s.equals("+")) && !(s.equals("-")) && !(s.equals("."))&& !(s.equals("-."))&& !(s.equals("+."))) {

            if(Double.parseDouble(s) > 99999999) {

                Toast.makeText(dialogs.this, R.string.maxIs99Mill, Toast.LENGTH_LONG).show();
                i.putExtra("rate", "99999999");
                setResult(RESULT_OK, i);
            } else if (Double.parseDouble(s)<0) {
                setResult(RESULT_CANCELED);
                Toast.makeText(dialogs.this, R.string.negativeNumberEntered, Toast.LENGTH_LONG).show();
            }
            else{
                i.putExtra("rate", s);
                setResult(RESULT_OK, i);
            }
        }

        finish();
    }

    public void clockFullOk(View view) {
        this.finish();
    }

    public void resetCancel(View view) { this.finish(); }

    public void resetOk(View view) {

        if(listView.isItemChecked(0) && !listView.isItemChecked(1)){
            i.putExtra("clockReset", "1");
            setResult(RESULT_OK, i);

        }else if(listView.isItemChecked(1) && !listView.isItemChecked(0)){
            i.putExtra("clockReset","2");
            setResult(RESULT_OK,i);
        }else if (listView.isItemChecked(0) && listView.isItemChecked(1)){
            i.putExtra("clockReset","3");
            setResult(RESULT_OK,i);
        }


        this.finish();

    }
}
