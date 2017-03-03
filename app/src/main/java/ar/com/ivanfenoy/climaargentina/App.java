package ar.com.ivanfenoy.climaargentina;

import android.app.Application;
import android.widget.ArrayAdapter;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Controllers.DataController;
import ar.com.ivanfenoy.climaargentina.Models.City;

public class App extends Application {
    private ArrayList<City> mListCities = new ArrayList<>();
    private static DataController mDataController;
    @Override
    public void onCreate() {
        super.onCreate();

        Iconify.with(new FontAwesomeModule())
                .with(new WeathericonsModule());

        mDataController = new DataController();

    }

    public static DataController dataController() {
        if(mDataController == null){
            mDataController = new DataController();
        }
        return mDataController;
    }
}
