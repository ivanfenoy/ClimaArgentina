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

import com.squareup.picasso.Picasso;

import org.jsoup.helper.StringUtil;

import ar.com.ivanfenoy.climaargentina.Elements.WeatherIcon;
import ar.com.ivanfenoy.climaargentina.MainActivity;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.R;
import ar.com.ivanfenoy.climaargentina.Utils.Util;
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
    @Bind(R.id.img_morning)WeatherIcon mTodayMorningImage;
    @Bind(R.id.img_night)WeatherIcon mTodayNightImage;

    //NextDays
    @Bind(R.id.img_day_1)WeatherIcon mImgDay1;
    @Bind(R.id.img_day_2)WeatherIcon mImgDay2;
    @Bind(R.id.img_day_3)WeatherIcon mImgDay3;
//    @Bind(R.id.img_day_4)ImageView mImgDay4;
    @Bind(R.id.degree_day_1)TextView mDegreeDay1;
    @Bind(R.id.degree_day_2)TextView mDegreeDay2;
    @Bind(R.id.degree_day_3)TextView mDegreeDay3;
//    @Bind(R.id.degree_day_4)TextView mDegreeDay4;
    @Bind(R.id.day_1)TextView mDay1;
    @Bind(R.id.day_2)TextView mDay2;
    @Bind(R.id.day_3)TextView mDay3;
//    @Bind(R.id.day_4)TextView mDay4;

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
        mTodayMorningText.setText(Util.capitalize(mCity.listDays.get(0).morningText));
        mTodayNightText.setText(Util.capitalize(mCity.listDays.get(0).nightText));
        mTodayMorningImage.setIcon(mCity.listDays.get(0).morningImage);
        mTodayNightImage.setIcon(mCity.listDays.get(0).nightImage);
//        Picasso.with(getActivity()).load(mCity.listDays.get(0).morningImage).into(mTodayMorningImage);
//        Picasso.with(getActivity()).load(mCity.listDays.get(0).nightImage).into(mTodayNightImage);


//        Picasso.with(getActivity()).load(mCity.listDays.get(1).morningImage).into(mImgDay1);
        mImgDay1.setIcon(mCity.listDays.get(1).morningImage);
        mDegreeDay1.setText(mCity.listDays.get(1).minDegree + "/" + mCity.listDays.get(1).maxDegree);
        mDay1.setText(mCity.listDays.get(1).day);

//        Picasso.with(getActivity()).load(mCity.listDays.get(2).morningImage).into(mImgDay2);
        mImgDay2.setIcon(mCity.listDays.get(2).morningImage);
        mDegreeDay2.setText(mCity.listDays.get(2).minDegree + "/" + mCity.listDays.get(2).maxDegree);
        mDay2.setText(mCity.listDays.get(2).day);

//        Picasso.with(getActivity()).load(mCity.listDays.get(3).morningImage).into(mImgDay3);
        mImgDay3.setIcon(mCity.listDays.get(3).morningImage);
        mDegreeDay3.setText(mCity.listDays.get(3).minDegree + "/" + mCity.listDays.get(3).maxDegree);
        mDay3.setText(mCity.listDays.get(3).day);

//        Picasso.with(getActivity()).load(mCity.listDays.get(4).morningImage).into(mImgDay4);
//        mDegreeDay4.setText(mCity.listDays.get(4).minDegree + "/" + mCity.listDays.get(4).maxDegree);
//        mDay4.setText(mCity.listDays.get(4).day);

    }


}
