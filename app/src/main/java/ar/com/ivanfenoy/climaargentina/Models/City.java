package ar.com.ivanfenoy.climaargentina.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ivanfenoy on 27/1/17.
 */

public class City implements Parcelable {
    public int stateId;
    public String city;

    public long lastUpdate;

    public String actualState;
    public String actualDegree;
    public String actualImage;
    public String thermal;
    public String visibility;
    public String humidity;
    public String pressure;
    public String wind;

    public String actualError = "";
    public String nextDaysError = "";

    public ArrayList<Day> listDays = new ArrayList<>();


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stateId);
        dest.writeString(this.city);
        dest.writeString(this.actualState);
        dest.writeString(this.actualDegree);
        dest.writeString(this.actualImage);
        dest.writeList(this.listDays);
    }

    public City() {
    }

    protected City(Parcel in) {
        this.stateId = in.readInt();
        this.city = in.readString();
        this.actualState = in.readString();
        this.actualDegree = in.readString();
        this.actualImage = in.readString();
        this.listDays = new ArrayList<Day>();
        in.readList(this.listDays, Day.class.getClassLoader());
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
