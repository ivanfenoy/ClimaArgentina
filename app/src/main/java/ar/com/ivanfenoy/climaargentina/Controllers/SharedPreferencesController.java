package ar.com.ivanfenoy.climaargentina.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.R;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesController {
    private static Context mContext;
    private static SharedPreferences mSp;

    public static void setUpSP(Context context) {
        mContext = context;
        if(mSp == null){
            mSp = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        }
    }

    public static void saveCity(Context pContext, String pCity){
        setUpSP(pContext);
        SharedPreferences.Editor wEditor = mSp.edit();
        wEditor.putString(mContext.getString(R.string.sp_cities), getCities(mContext) + pCity + ",");
        wEditor.commit();
    }

    public static String getCities(Context pContext){
        setUpSP(pContext);
        String wCities = mSp.getString(mContext.getString(R.string.sp_cities), "");
        return wCities;
    }

    public static void deleteCity(Context pContext, String pCity){
        setUpSP(pContext);
        String wOldCities = getCities(mContext);
        if(wOldCities.contains(pCity)){
           wOldCities.replace(pCity + ",", "");
        }
    }

    public static void saveCity(Context pContext, City pCity){
        setUpSP(pContext);
        ArrayList<City> wListCities = getListCities(pContext);
        wListCities.add(pCity);
        Gson gson = new Gson();
        String json = gson.toJson(wListCities);
        SharedPreferences.Editor wEditor = mSp.edit();
        wEditor.putString(mContext.getString(R.string.sp_list_cities), json);
        wEditor.commit();
    }

    public static ArrayList<City> getListCities(Context pContext){
        ArrayList<City> wListCities;
        setUpSP(pContext);
        Gson wGson = new Gson();
        String wJson = mSp.getString(mContext.getString(R.string.sp_list_cities), "");
        Type wType = new TypeToken<ArrayList<City>>() {}.getType();
        wListCities = wGson.fromJson(wJson, wType);
        return (wListCities == null)? new ArrayList<City>() : wListCities;
    }
}
