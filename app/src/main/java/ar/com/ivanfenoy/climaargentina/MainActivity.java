package ar.com.ivanfenoy.climaargentina;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Adapters.PagerCitiesAdapter;
import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.Fragments.AddCityFragment;
import ar.com.ivanfenoy.climaargentina.Fragments.CityFragment;
import ar.com.ivanfenoy.climaargentina.Interfaces.ObjectCallback;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.Utils.Util;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
//    @Bind(R.id.bar_toolbar)Toolbar mToolbar;
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
//        if(mToolbar != null) {
//            setSupportActionBar(mToolbar);
//            mToolbar.setContentInsetsAbsolute(0, 0);
//        }

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
            newCity();
        }
    }

//    public void setTitle(String pTitle){
//        mToolbar.setTitle(pTitle);
//    }

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        //to setup plus icon in toolbar
//        menu.findItem(R.id.refresh).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_refresh)
//                        .colorRes(R.color.white)
//                        .actionBarSize());
//
//        menu.findItem(R.id.action_add).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_plus)
//                        .colorRes(R.color.white)
//                        .actionBarSize());
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        int id = item.getItemId();
//        if(id == R.id.refresh) {
//            refreshCurrentCity();
//            return true;
//        }
//        if(id == R.id.action_add) {
//            newCity();
//            return true;
//        }
//        return false;
//    }
//
//    private void refreshCurrentCity() {
//        LIST_CITY_FRAGMENT.get(mPagerCities.getCurrentItem()).updateCity();
//    }
//
    public void newCity() {
        FragmentManager fm = getSupportFragmentManager();
        AddCityFragment wDialog = new AddCityFragment();
        wDialog.show(fm, "");
    }

}
