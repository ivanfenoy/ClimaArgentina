package ar.com.ivanfenoy.climaargentina.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Fragments.CityFragment;
import ar.com.ivanfenoy.climaargentina.Models.City;

/**
 * Created by ivanj on 09/02/2017.
 */

public class PagerCitiesAdapter extends FragmentStatePagerAdapter {
    ArrayList<City> mListCities = new ArrayList<>();

    public PagerCitiesAdapter(FragmentManager fm, ArrayList<City> pListCities) {
        super(fm);
        mListCities = pListCities;
    }

    @Override
    public Fragment getItem(int position) {
        return CityFragment.newInstance(mListCities.get(position));
    }

    @Override
    public int getCount() {
        return mListCities.size();
    }
}
