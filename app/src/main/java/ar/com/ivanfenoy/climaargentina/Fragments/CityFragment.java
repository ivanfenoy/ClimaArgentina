package ar.com.ivanfenoy.climaargentina.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.R;

public class CityFragment extends Fragment {
    private View mRootView;
    private static final String ARG_CITY = "city";
    private City mCity;

    public CityFragment() {
    }

    public static CityFragment newInstance(City pCity) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CITY, pCity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = getArguments().getParcelable(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_city, container, false);

        ((TextView)mRootView.findViewById(R.id.text)).setText(mCity.city + " " + mCity.actualDegree);
        return mRootView;
    }


}
