package ar.com.ivanfenoy.climaargentina.Fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ar.com.ivanfenoy.climaargentina.MainActivity;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CityFragment extends Fragment {
    @Bind(R.id.rl_current_state)RelativeLayout mRlCurrentState;
    @Bind(R.id.current_state)TextView mCurrentState;
    @Bind(R.id.current_degree)TextView mCurrentDegree;
    @Bind(R.id.thermal_value)TextView mThermal;
    @Bind(R.id.visibility_value)TextView mVisibility;
    @Bind(R.id.humidity_value)TextView mHumidity;
    @Bind(R.id.pressure_value)TextView mPressure;
    @Bind(R.id.wind_value)TextView mWind;

    @Bind(R.id.today_max_degree)TextView mTodayMaxDegree;
    @Bind(R.id.today_min_degree)TextView mTodayMinDegree;
    @Bind(R.id.today_morning_text)TextView mTodayMorningText;
    @Bind(R.id.today_night_text)TextView mTodayNightText;
    @Bind(R.id.img_morning)ImageView mTodayMorningImage;
    @Bind(R.id.img_night)ImageView mTodayNightImage;
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
        ButterKnife.bind(this, mRootView);

        mCurrentState.setText(mCity.actualState);
        mCurrentDegree.setText(mCity.actualDegree);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewTreeObserver vto = mRootView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int height = mRootView.getMeasuredHeight();
                mRlCurrentState.getLayoutParams().height = height - 50;
                mRlCurrentState.requestLayout();
            }
        });

        mThermal.setText(mCity.thermal);
        mVisibility.setText(mCity.visibility);
        mHumidity.setText(mCity.humidity);
        mPressure.setText(mCity.pressure);
        mWind.setText(mCity.wind);

        mTodayMaxDegree.setText(mCity.listDays.get(0).maxDegree);
        mTodayMinDegree.setText(mCity.listDays.get(0).minDegree);
        mTodayMorningText.setText(mCity.listDays.get(0).morningText);
        mTodayNightText.setText(mCity.listDays.get(0).nightText);
    }


}
