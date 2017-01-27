package ar.com.ivanfenoy.climaargentina.Models;

import java.util.ArrayList;

/**
 * Created by ivanfenoy on 27/1/17.
 */

public class City {
    public int stateId;
    public String city;

    public String actualState;
    public String actualDegree;

    public ArrayList<Day> listDays = new ArrayList<>();
}
