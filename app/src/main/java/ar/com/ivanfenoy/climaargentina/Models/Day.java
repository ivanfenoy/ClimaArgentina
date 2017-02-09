package ar.com.ivanfenoy.climaargentina.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ivanfenoy on 27/1/17.
 */

public class Day implements Parcelable {
    public String day;
    public String maxDegree;
    public String minDegree;
    public String morningText;
    public String morningImage;
    public String nightText;
    public String nightImage;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeString(this.maxDegree);
        dest.writeString(this.minDegree);
        dest.writeString(this.morningText);
        dest.writeString(this.morningImage);
        dest.writeString(this.nightText);
        dest.writeString(this.nightImage);
    }

    public Day() {
    }

    protected Day(Parcel in) {
        this.day = in.readString();
        this.maxDegree = in.readString();
        this.minDegree = in.readString();
        this.morningText = in.readString();
        this.morningImage = in.readString();
        this.nightText = in.readString();
        this.nightImage = in.readString();
    }

    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
