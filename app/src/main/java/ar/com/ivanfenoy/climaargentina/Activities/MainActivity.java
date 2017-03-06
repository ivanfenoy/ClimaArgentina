package ar.com.ivanfenoy.climaargentina.Activities;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Adapters.PagerCitiesAdapter;
import ar.com.ivanfenoy.climaargentina.App;
import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.Fragments.AddCityFragment;
import ar.com.ivanfenoy.climaargentina.Fragments.CityFragment;
import ar.com.ivanfenoy.climaargentina.Interfaces.ObjectCallback;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.R;
import ar.com.ivanfenoy.climaargentina.Utils.Util;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.pager_cities)ViewPager mPagerCities;

    private PagerCitiesAdapter mPagerCitiesAdapter;

    public static ArrayList<CityFragment> LIST_CITY_FRAGMENT = new ArrayList<>();

    private Dialog preloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);

        final ArrayList<City> wListCities = SharedPreferencesController.getListCities(this);

        mPagerCitiesAdapter = new PagerCitiesAdapter(MainActivity.this, getSupportFragmentManager(), wListCities);
        mPagerCities.setAdapter(mPagerCitiesAdapter);
        mPagerCities.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(wListCities.get(position).city);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if(wListCities.size() > 0) {
            setTitle(wListCities.get(0).city);
        }
        else{
            if(Util.isOnline()) {
                newCity();
            }
            else{
                Util.Toast(this, R.string.no_connection);
            }
        }

    }

    public void putNewCity(String pCity, int pState) {
        if(preloader != null && preloader.isShowing()){
            preloader.dismiss();
        }
        preloader = Util.Preloader(MainActivity.this, R.string.getting_city);
        App.dataController().getCityWeather(MainActivity.this, pCity, pState, new OnCityWeatherCallback());
    }

    private class OnCityWeatherCallback implements ObjectCallback {
        @Override
        public void done(Exception e, final Object object) {
            if(preloader != null && preloader.isShowing()){
                preloader.dismiss();
            }
            if(e != null) {
//                insertError(e);
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPagerCitiesAdapter.addCity((City) object);
                    mPagerCitiesAdapter.notifyDataSetChanged();
                    mPagerCities.setCurrentItem(mPagerCitiesAdapter.getCount()-1);
                    SharedPreferencesController.saveCity(MainActivity.this, (City) object);
                }
            });

        }
    }

    public void deleteCityFromPager(City pCity) {
        mPagerCitiesAdapter.deleteCity(mPagerCities, pCity);
        mPagerCitiesAdapter.notifyDataSetChanged();
        setTitle(mPagerCitiesAdapter.getCity(mPagerCities.getCurrentItem()).city);
    }

    public void newCity() {
        FragmentManager fm = getSupportFragmentManager();
        AddCityFragment wDialog = new AddCityFragment();
        wDialog.show(fm, "");
    }

}
