package ar.com.ivanfenoy.climaargentina.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import ar.com.ivanfenoy.climaargentina.App;
import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.Elements.WeatherIcon;
import ar.com.ivanfenoy.climaargentina.Interfaces.ObjectCallback;
import ar.com.ivanfenoy.climaargentina.Activities.MainActivity;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.Models.Day;
import ar.com.ivanfenoy.climaargentina.R;
import ar.com.ivanfenoy.climaargentina.Utils.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityFragment extends Fragment {
    @Bind(R.id.add_city)IconTextView mAddCity;
    @Bind(R.id.refresh_city)IconTextView mRefreshCity;
    @Bind(R.id.rl_content)RelativeLayout mRlContent;
//    @Bind(R.id.swipe) SwipeRefreshLayout mSwipe;
    @Bind(R.id.scroll) ScrollView mScroll;
    @Bind(R.id.rl_current_state)RelativeLayout mRlCurrentState;
    @Bind(R.id.ll_detail_state)LinearLayout mLlDetailState;
    @Bind(R.id.current_state)TextView mCurrentState;
    @Bind(R.id.img_current_state)WeatherIcon mImgCurrentState;
    @Bind(R.id.img_no_data) ImageView mImgNoData;
    @Bind(R.id.current_degree)TextView mCurrentDegree;
    @Bind(R.id.current_error)TextView mCurrentError;
    @Bind(R.id.thermal_value)TextView mThermal;
    @Bind(R.id.visibility_value)TextView mVisibility;
    @Bind(R.id.humidity_value)TextView mHumidity;
    @Bind(R.id.pressure_value)TextView mPressure;

    @Bind(R.id.wind_value)TextView mWind;
    @Bind(R.id.ll_today)LinearLayout mLlToday;
    @Bind(R.id.today_lbl)TextView mTodayTitle;
    @Bind(R.id.today_max_degree)TextView mTodayMaxDegree;
    @Bind(R.id.today_min_degree)TextView mTodayMinDegree;
    @Bind(R.id.today_morning_text)TextView mTodayMorningText;
    @Bind(R.id.today_night_text)TextView mTodayNightText;
    @Bind(R.id.img_morning)WeatherIcon mTodayMorningImage;
    @Bind(R.id.img_night)WeatherIcon mTodayNightImage;

    //NextDays
    @Bind(R.id.ll_next_days)LinearLayout mLlNextDays;
    @Bind(R.id.ll_day_1)LinearLayout mLlDay1;
    @Bind(R.id.ll_day_2)LinearLayout mLlDay2;
    @Bind(R.id.ll_day_3)LinearLayout mLlDay3;
    @Bind(R.id.img_day_1)WeatherIcon mImgDay1;
    @Bind(R.id.img_day_2)WeatherIcon mImgDay2;
    @Bind(R.id.img_day_3)WeatherIcon mImgDay3;
    @Bind(R.id.degree_day_1)TextView mDegreeDay1;
    @Bind(R.id.degree_day_2)TextView mDegreeDay2;
    @Bind(R.id.degree_day_3)TextView mDegreeDay3;
    @Bind(R.id.day_1)TextView mDay1;
    @Bind(R.id.day_2)TextView mDay2;
    @Bind(R.id.day_3)TextView mDay3;

    @Bind(R.id.expandable_day_x) ExpandableLinearLayout mExpandableDayX;
    @Bind(R.id.day_x_max_degree)TextView mDayXMaxDegree;
    @Bind(R.id.day_x_min_degree)TextView mDayXMinDegree;
    @Bind(R.id.day_x_morning_text)TextView mDayXMorningText;
    @Bind(R.id.day_x_night_text)TextView mDayXNightText;
    @Bind(R.id.img_morning_day_x)WeatherIcon mDayXMorningImage;
    @Bind(R.id.img_night_day_x)WeatherIcon mDayXNightImage;

    @Bind(R.id.update_time)TextView mUpdatedTime;
    @Bind(R.id.back_image) ImageView mBackImage;

    private View mRootView;
    private static final String ARG_CITY = "city";
    private City mCity;
    private Dialog preloader;

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

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar wCal = Calendar.getInstance();
        wCal.setTimeInMillis(mCity.lastUpdate);
        Calendar wNow = Calendar.getInstance();
        wNow.add(Calendar.HOUR, -2);

        if(wCal.before(wNow)){
            if(Util.isOnline()) {
                updateCity();
            }
            else{
                Util.Toast(getActivity(), R.string.no_connection);
            }
        }
        initView();

    }

    private void initView() {
        mRlContent.setPadding(0, Util.getStatusBarHeight(getActivity()), 0, 0);
        ViewTreeObserver vto = mRootView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Actual state
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int height = mRootView.getMeasuredHeight();

                int wActionBarHeight = 0;
                TypedValue wTv = new TypedValue();
                if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, wTv, true))
                {
                    wActionBarHeight = TypedValue.complexToDimensionPixelSize(wTv.data,getResources().getDisplayMetrics());
                }
                mRlCurrentState.getLayoutParams().height = height - wActionBarHeight - Util.getStatusBarHeight(getActivity());

                if(mCity.actualError.isEmpty()) {
                    mImgCurrentState.setIcon(mCity.actualImage);
                    mCurrentState.setText(mCity.actualState);
                    mCurrentDegree.setText(mCity.actualDegree);
                    mThermal.setText(mCity.thermal);
                    mVisibility.setText(mCity.visibility);
                    mHumidity.setText(mCity.humidity);
                    mPressure.setText(mCity.pressure);
                    mWind.setText(mCity.wind);

                    Picasso.with(getActivity()).load(Util.setWheatherBackground(mCity.actualImage)).into(mBackImage);
                }
                else{
                    mImgNoData.setVisibility(View.VISIBLE);
                    mImgCurrentState.setVisibility(View.GONE);
                    mLlDetailState.setVisibility(View.GONE);
                    mCurrentDegree.setVisibility(View.GONE);
                    mCurrentState.setVisibility(View.GONE);
                    mCurrentError.setText(R.string.no_actual_data);

                    Calendar wUpdateTime = Calendar.getInstance();
                    wUpdateTime.setTimeInMillis(mCity.lastUpdate);
                    if(wUpdateTime.get(Calendar.HOUR_OF_DAY) > 19){
//                        Picasso.with(getActivity()).load(Util.getBackgroundRandom(Util.NIGTH_CLEAR_SKY)).into(mBackImage);
                        Picasso.with(getActivity()).load(Util.setWheatherBackground(mCity.listDays.get(0).nightImage)).into(mBackImage);
                    }
                    else{
                        Picasso.with(getActivity()).load(Util.setWheatherBackground(mCity.listDays.get(0).morningImage)).into(mBackImage);
                    }
                }

            }
        });

        mUpdatedTime.setText(getString(R.string.update_time) + " " + Util.getDate(mCity.lastUpdate));

        //NextDays
        if(mCity.nextDaysError.isEmpty()) {
            mTodayMaxDegree.setText(mCity.listDays.get(0).maxDegree);
            mTodayMinDegree.setText(mCity.listDays.get(0).minDegree);
            if(mCity.listDays.get(0).morningText != null && !mCity.listDays.get(0).morningText.isEmpty()) {
                mTodayMorningText.setText(Util.capitalize(mCity.listDays.get(0).morningText));
                mTodayMorningImage.setIcon(mCity.listDays.get(0).morningImage);
            }
            else{
                hideMorningInfo();
            }
            mTodayNightText.setText(Util.capitalize(mCity.listDays.get(0).nightText));
            mTodayNightImage.setIcon(mCity.listDays.get(0).nightImage);


            mImgDay1.setIcon(mCity.listDays.get(1).morningImage);
            mDegreeDay1.setText(mCity.listDays.get(1).minDegree + "/" + mCity.listDays.get(1).maxDegree);
            mDay1.setText(mCity.listDays.get(1).day);

            mImgDay2.setIcon(mCity.listDays.get(2).morningImage);
            mDegreeDay2.setText(mCity.listDays.get(2).minDegree + "/" + mCity.listDays.get(2).maxDegree);
            mDay2.setText(mCity.listDays.get(2).day);

            mImgDay3.setIcon(mCity.listDays.get(3).morningImage);
            mDegreeDay3.setText(mCity.listDays.get(3).minDegree + "/" + mCity.listDays.get(3).maxDegree);
            mDay3.setText(mCity.listDays.get(3).day);
        }
        mScroll.fullScroll(ScrollView.FOCUS_UP);
    }

    private void hideMorningInfo(){
        mTodayMorningText.setVisibility(View.GONE);
        mTodayMorningImage.setVisibility(View.GONE);
        mTodayTitle.setVisibility(View.GONE);
    }

    @OnClick(R.id.ll_day_1)
    public void openDay1(){
        if(!mExpandableDayX.isExpanded()){
            resetDayscolor();
            mImgDay1.setTextColor(getResources().getColor(R.color.colorPrimary));
            mDay1.setTextColor(getResources().getColor(R.color.colorPrimary));
            mDegreeDay1.setTextColor(getResources().getColor(R.color.colorPrimary));
            setUpDayX(mCity.listDays.get(1));
            mExpandableDayX.expand();
//            mScroll.fullScroll(ScrollView.FOCUS_DOWN);
        }
        else{
            resetDayscolor();
            mExpandableDayX.collapse();
        }
    }

    @OnClick(R.id.ll_day_2)
    public void openDay2(){
        if(!mExpandableDayX.isExpanded()){
            resetDayscolor();
            mImgDay2.setTextColor(getResources().getColor(R.color.colorPrimary));
            mDay2.setTextColor(getResources().getColor(R.color.colorPrimary));
            mDegreeDay2.setTextColor(getResources().getColor(R.color.colorPrimary));
            setUpDayX(mCity.listDays.get(2));
            mExpandableDayX.expand();
//            mScroll.fullScroll(ScrollView.FOCUS_DOWN);
        }
        else{
            resetDayscolor();
            mExpandableDayX.collapse();
        }
    }

    @OnClick(R.id.ll_day_3)
    public void openDay3(){
        if(!mExpandableDayX.isExpanded()){
            resetDayscolor();
            mImgDay3.setTextColor(getResources().getColor(R.color.colorPrimary));
            mDay3.setTextColor(getResources().getColor(R.color.colorPrimary));
            mDegreeDay3.setTextColor(getResources().getColor(R.color.colorPrimary));
            setUpDayX(mCity.listDays.get(3));
            mExpandableDayX.expand();
//            mScroll.fullScroll(ScrollView.FOCUS_DOWN);
        }
        else{
            resetDayscolor();
            mExpandableDayX.collapse();
        }
    }

    private void setUpDayX(Day pDay) {
        mDayXMaxDegree.setText(pDay.maxDegree);
        mDayXMinDegree.setText(pDay.minDegree);
        mDayXMorningText.setText(pDay.morningText);
        mDayXNightText.setText(pDay.nightText);
        mDayXMorningImage.setIcon(pDay.morningImage);
        mDayXNightImage.setIcon(pDay.nightImage);
    }

    private void resetDayscolor(){
        mImgDay1.setTextColor(getResources().getColor(R.color.white));
        mDay1.setTextColor(getResources().getColor(R.color.white));
        mDegreeDay1.setTextColor(getResources().getColor(R.color.white));
        mImgDay2.setTextColor(getResources().getColor(R.color.white));
        mDay2.setTextColor(getResources().getColor(R.color.white));
        mDegreeDay2.setTextColor(getResources().getColor(R.color.white));
        mImgDay3.setTextColor(getResources().getColor(R.color.white));
        mDay3.setTextColor(getResources().getColor(R.color.white));
        mDegreeDay3.setTextColor(getResources().getColor(R.color.white));
    }

    public void updateCity() {
        if(preloader != null && preloader.isShowing()){
            preloader.dismiss();
        }
        preloader = Util.Preloader(getActivity(), R.string.updating_city);
        App.dataController().getCityWeather(getActivity(), mCity.city, mCity.stateId, new OnUpdateCityCallback());
    }

    private class OnUpdateCityCallback implements ObjectCallback {
        @Override
        public void done(Exception e, final Object object) {
            if(preloader != null && preloader.isShowing()){
                preloader.dismiss();
            }
            if(e != null) {
//                insertError(e);
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCity = (City) object;
                    initView();
                    SharedPreferencesController.updateCity(getActivity(), mCity);
                }
            });

        }
    }

    @OnClick(R.id.delete_city)
    public void deleteCity(View view){
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(which == DialogInterface.BUTTON_POSITIVE) {
                    SharedPreferencesController.deleteCity(getActivity(), mCity);
                    ((MainActivity)getActivity()).deleteCityFromPager(mCity);
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage(R.string.confirm_delete_city);
        builder.setPositiveButton(R.string.accept, listener);
        builder.setNegativeButton(R.string.cancel, listener);
        builder.create().show();
    }

    @OnClick(R.id.add_city)
    public void addCity(View view){
        ((MainActivity)getActivity()).newCity();
    }

    @OnClick(R.id.refresh_city)
    public void refreshCity(View view){
        if(Util.isOnline()) {
            updateCity();
        }
        else{
            Util.Toast(getActivity(), R.string.no_connection);
        }
    }

}
