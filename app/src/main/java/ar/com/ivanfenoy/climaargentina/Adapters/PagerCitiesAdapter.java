package ar.com.ivanfenoy.climaargentina.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Fragments.CityFragment;
import ar.com.ivanfenoy.climaargentina.MainActivity;
import ar.com.ivanfenoy.climaargentina.Models.City;

/**
 * Created by ivanj on 09/02/2017.
 */

public class PagerCitiesAdapter extends FragmentStatePagerAdapter {
    ArrayList<City> mListCities = new ArrayList<>();
    Context mContext;

    public PagerCitiesAdapter(Context pContext, FragmentManager fm, ArrayList<City> pListCities) {
        super(fm);
        mContext = pContext;
        mListCities = pListCities;
    }

    @Override
    public Fragment getItem(int position) {
        CityFragment wFragment = CityFragment.newInstance(mListCities.get(position));
        ((MainActivity)mContext).LIST_CITY_FRAGMENT.add(wFragment);
        return wFragment;
    }

    public void addCity(City pCity){
        mListCities.add(pCity);
        this.notifyDataSetChanged();
    }

    public City getCity(int pIndex){
        return mListCities.get(pIndex);
    }

    @Override
    public int getCount() {
        return mListCities.size();
    }


//    public void deleteCity(City pCity) {
//        mListCities.remove(pCity);
//        this.notifyDataSetChanged();
//    }

    public void deleteCity (ViewPager pager, City pCity)
    {
        pager.setAdapter (null);
        this.mListCities.remove(pCity);
        pager.setAdapter (this);
    }

}
