package com.example.arobius.payrate.adapters;

import android.os.Parcel;
import android.os.Parcelable;

public class historyData implements Parcelable {

    private String moneyEarned, hourlyRate;
    private String[] date, timeWorked;
    private boolean checked;

    boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    private boolean checkbox;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getMoneyEarned() {
        return moneyEarned;
    }

    void setMoneyEarned(String moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String[] getDate() {
        return date;
    }

    void setDate(String[] date) {
        this.date = date;
    }

    public String[] getTimeWorked() {
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

