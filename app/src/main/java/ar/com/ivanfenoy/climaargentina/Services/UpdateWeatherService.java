package ar.com.ivanfenoy.climaargentina.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.App;
import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.Fragments.CityFragment;
import ar.com.ivanfenoy.climaargentina.Interfaces.ObjectCallback;
import ar.com.ivanfenoy.climaargentina.Models.City;

/**
 * Created by ivanfenoy on 7/4/17.
 */

public class UpdateWeatherService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<City> wListCities = SharedPreferencesController.getListCities(this);

        for (City wCity : wListCities) {
            App.dataController().getCityWeather(getApplicationContext(), wCity.city, wCity.stateId, new OnUpdateCityCallback());
            App.dataController().getWeatherAlerts(getApplicationContext(), wCity, new OnUpdateCityCallback());
        }
    }

    private class OnUpdateCityCallback implements ObjectCallback {
        @Override
        public void done(Exception e, final Object object) {
            if(e != null) {
                return;
            }
            SharedPreferencesController.updateCity(getApplicationContext(), (City) object);

        }
    }
}
