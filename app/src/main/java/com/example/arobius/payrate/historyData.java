package com.example.arobius.payrate;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

class historyData implements Parcelable {

    private String moneyEarned, hourlyRate;
    private String[] date, timeWorked;
    private boolean checked;

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    private boolean checkbox;

    boolean isChecked() {
        return checked;
    }

    void setChecked(boolean checked) {
        this.checked = checked;
    }


    String getMoneyEarned() {
        return moneyEarned;
    }

    void setMoneyEarned(String moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    String getHourlyRate() {
        return hourlyRate;
    }

    void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    String[] getDate() {
        return date;
    }

    void setDate(String[] date) {
        this.date = date;
    }

    String[] getTimeWorked() {
        return timeWorked;
    }

    void setTimeWorked(String[] timeWorked) {
        this.timeWorked = timeWorked;
    }

    public static final Creator<historyData> CREATOR = new Creator<historyData>() {

        public historyData createFromParcel(Parcel source) {
            historyData A = new historyData();
            A.moneyEarned = source.readString();
            A.hourlyRate = source.readString();
            A.date = source.createStringArray();
            A.timeWorked = source.createStringArray();
            return A;
        }
        public historyData[] newArray(int size) {
            return new historyData[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(moneyEarned);
        parcel.writeString(hourlyRate);
        parcel.writeStringArray(date);
        parcel.writeStringArray(timeWorked);
    }

}

