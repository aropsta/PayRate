package com.example.arobius.payrate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;

public class databaseAdapter  {
    historyDBHelper helper;
    private static historyDBHelper instance = null;
    Context context;

    public databaseAdapter(Context context) {
        helper = historyDBHelper.getInstance(context);
        this.context = context;
    }

    public long insertData (String []date,String []time, String rate, String wage , Context context) {
        SQLiteDatabase d = historyDBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(historyDBHelper.date0Col, date[0]);contentValues.put(historyDBHelper.date1Col, date[1]);
        contentValues.put(historyDBHelper.date2Col, date[2]);contentValues.put(historyDBHelper.date3Col, date[3]);
        contentValues.put(historyDBHelper.time0Col, time[0]);contentValues.put(historyDBHelper.time1Col, time[1]);
        contentValues.put(historyDBHelper.time2Col, time[2]);contentValues.put(historyDBHelper.time3Col, time[3]);
        contentValues.put(historyDBHelper.rateCol, rate);contentValues.put(historyDBHelper.wageCol, wage);

        long id = d.insert(historyDBHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public ArrayList <historyData> extractArrayList(){


        ArrayList<historyData> a = new ArrayList<>();

        SQLiteDatabase db = historyDBHelper.getInstance(context).getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + historyDBHelper.TABLE_NAME, null);
        if (cursor.getCount() == 0){
            a.clear();
        }else{
        if(cursor.moveToFirst()){

            do{
            String[] date = {cursor.getString(cursor.getColumnIndex(historyDBHelper.date0Col)),
                    cursor.getString(cursor.getColumnIndex(historyDBHelper.date1Col)),
                    cursor.getString(cursor.getColumnIndex(historyDBHelper.date2Col)),
                    cursor.getString(cursor.getColumnIndex(historyDBHelper.date3Col))};

            String[] time ={cursor.getString(cursor.getColumnIndex(historyDBHelper.time0Col)),
                    cursor.getString(cursor.getColumnIndex(historyDBHelper.time1Col)),
                    cursor.getString(cursor.getColumnIndex(historyDBHelper.time2Col)),
                    cursor.getString(cursor.getColumnIndex(historyDBHelper.time3Col))};

            String rate = cursor.getString(cursor.getColumnIndex(historyDBHelper.rateCol));
            String wage = cursor.getString(cursor.getColumnIndex(historyDBHelper.wageCol));


            historyData history = new historyData();

            history.setMoneyEarned(wage);
            history.setHourlyRate(rate);
            history.setDate(date);
            history.setTimeWorked(time);

            a.add(history);
        }while (cursor.moveToNext());
        }
            cursor.close();
        }

        return a;
    }

    public historyDBHelper deleteDatabase(Context context){
        context.deleteDatabase(historyDBHelper.DATABASE_NAME);
        instance = null;
        return historyDBHelper.getInstance(context);
}


    static class historyDBHelper extends SQLiteOpenHelper {
        private final static String DATABASE_NAME = "database";
        private final static String TABLE_NAME = "historyTB";
        private final static String UID = "id";
        private final static int DATABASE_VERSION = 1;
        private Context context;

        private final static String date0Col = "date0"; private final static String date1Col = "date1";
        private final static String date2Col = "date2"; private final static String date3Col = "date3";
        private final static String time0Col = "time0";private final static String time1Col = "time1";
        private final static String time2Col = "time2"; private final static String time3Col = "time3";
        private final static String rateCol = "rate";   private final static String wageCol = "wage";

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" " +"("+UID+" integer primary key, "+
              date0Col+" VARCHAR, "+date1Col+" VARCHAR,"+date2Col+" VARCHAR, "+date3Col+" VARCHAR," +
              time0Col+" VARCHAR,"+time1Col+" VARCHAR,"+time2Col+" VARCHAR,"+time3Col+" VARCHAR," +
              rateCol+ " VARCHAR, "+wageCol+" VARCHAR);";
        String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

        public static historyDBHelper getInstance(Context context) {
            if (instance == null) {
                instance = new historyDBHelper(context);
            }
            return instance;
        }

      private historyDBHelper(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
          this.context=context;
      }

      @Override
      public void onCreate(SQLiteDatabase db) {

          try {
              db.execSQL(CREATE_TABLE);

          } catch (SQLException e){
              e.printStackTrace();
          }
      }
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          try {
              db.execSQL(DROP_TABLE);
              onCreate(db);
          }catch (SQLException e){
              e.printStackTrace();
          }
      }
  }
}
