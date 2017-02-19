package ar.com.ivanfenoy.climaargentina;

import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.ramotion.foldingcell.FoldingCell;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import ar.com.ivanfenoy.climaargentina.Adapters.PagerCitiesAdapter;
import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.Fragments.AddCityFragment;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.Models.Day;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.bar_toolbar)Toolbar mToolbar;
    @Bind(R.id.pager_cities)ViewPager mPagerCities;

    private City mCity;
    private PagerCitiesAdapter mPagerCitiesAdapter;
    private int mCallsFinished = 0;

    private static final long TIMEOUT_MS = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setContentInsetsAbsolute(0, 0);
        }

        final ArrayList<City> wListCities = SharedPreferencesController.getListCities(this);

        mPagerCitiesAdapter = new PagerCitiesAdapter(getSupportFragmentManager(), wListCities);
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

    public void setTitle(String pTitle){
        mToolbar.setTitle(pTitle);
    }

    public void putNewCity(String pCity, int pState){
        mCity = new City();
        mCity.city = pCity;
        mCity.stateId = pState;
        try {
            getActualState(pCity);
            getNextDaysState(pCity, pState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new CountDownTimer(TIMEOUT_MS, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(mCallsFinished == 2){
                    mPagerCitiesAdapter.addCity(mCity);
                    mPagerCitiesAdapter.notifyDataSetChanged();
                    SharedPreferencesController.saveCity(MainActivity.this, mCity);
                    mCity = null;
                    mCallsFinished = 0;
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void getActualState(final String pCity) throws IOException {
        Thread wActualStateThread = new Thread() {
            public void run() {
                Document wDoc = null;
                try {
                    wDoc = Jsoup.connect("http://www.smn.gov.ar/mobile/estado_movil.php?ciudad=" + pCity).get();

                    Element wSpanCity = wDoc.select("span.temp_ciudad").first();
                    String wCity = wSpanCity.html();

                    Element wSpanTemp = wDoc.select("span.temp_grande").first();
                    String wNowTemp = wSpanTemp.html();

                    Element wSpanTempText = wDoc.select("span.temp_texto").first();
                    String wNowTempText = wSpanTempText.html();

                    mCity.actualImage = wDoc.select("img[width=120px]").first().attr("src");
                    mCity.actualDegree = wNowTemp;
                    mCity.actualState = wNowTempText;

                    Elements wTable = wDoc.select("table.texto_temp_chico > tBody > tr");
                    mCity.thermal = wTable.get(0).select("td").get(1).html();
                    mCity.visibility = wTable.get(1).select("td").get(1).html();
                    mCity.humidity = wTable.get(2).select("td").get(1).html();
                    mCity.pressure = wTable.get(3).select("td").get(1).html();
                    mCity.wind = wTable.get(4).select("td").get(1).html();

                    mCallsFinished ++;
                } catch (Exception e) {
                    if(wDoc != null){
                        Elements wErrors = wDoc.select("p.texto_verde");
                        if(!wErrors.isEmpty()){
                            mCity.actualError = wErrors.first().html();
                        }
                    }
                    mCallsFinished ++;
                }
            }
        };
        wActualStateThread.start();
    }

    public void getNextDaysState(final String pCity, final int pState) throws IOException {
        Thread wNextDaysStateThread = new Thread() {
            public void run() {
                Document wDoc = null;
                try {
                    wDoc = Jsoup.connect("http://www.smn.gov.ar/mobile/pronostico_movil.php?provincia="+ pState +"&ciudad=" + pCity).get();

                    Elements wDivs = wDoc.select("div#pron");
                    if(wDivs.size() == 0){
                        wDivs = wDoc.select("div#pron2");
                    }

                    for (Element wElement: wDivs) {
                        Day wDay = new Day();
                        Elements wTrs = wElement.select("h6").first().select("table.texto_encabezado").first().select("tr");
                        //Day name
                        wDay.day = wTrs.get(0).select("td").first().html();

                        //images
                        if(wTrs.get(1).select("td > img").size() > 1) {
                            //morning image
                            if (wTrs.get(1).select("td > img").get(0).attr("src").contains("../../..")) {
                                wDay.morningImage = wTrs.get(1).select("td > img").get(0).attr("src").replace("../../..", "http://www.smn.gov.ar");
                            } else {
                                wDay.morningImage = "http://www.smn.gov.ar/mobile/" + wTrs.get(1).select("td > img").get(0).attr("src");
                            }

                            //night image
                            if (wTrs.get(1).select("td > img").get(1).attr("src").contains("../../..")) {
                                wDay.nightImage = wTrs.get(1).select("td > img").get(1).attr("src").replace("../../..", "http://www.smn.gov.ar");
                            } else {
                                wDay.nightImage = "http://www.smn.gov.ar/mobile/" + wTrs.get(1).select("td > img").get(1).attr("src");
                            }
                        }
                        else{
                            if (wTrs.get(1).select("td > img").get(0).attr("src").contains("../../..")) {
                                wDay.nightImage = wTrs.get(1).select("td > img").get(0).attr("src").replace("../../..", "http://www.smn.gov.ar");
                            } else {
                                wDay.nightImage = "http://www.smn.gov.ar/mobile/" + wTrs.get(1).select("td > img").get(0).attr("src");
                            }
                        }

                        //temps states
                        Elements wPs = wElement.select("p");
                        if(wPs.size() > 1) { //Significa que ya no aparece el clima de la mañana
                            wDay.morningText = wPs.get(0).html();
                            wDay.nightText = wPs.get(1).html();
                        }
                        else{
                            wDay.nightText = wPs.get(0).html();
                        }

                        //temps
                        wDay.minDegree = wElement.select("table.texto_blanco").first().select("tr").get(0).select("td").get(1).html();
                        wDay.maxDegree = wElement.select("table.texto_blanco").first().select("tr").get(1).select("td").get(1).html();

                        mCity.listDays.add(wDay);
                    }

                    Calendar wCal = Calendar.getInstance();
                    mCity.lastUpdate = wCal.getTimeInMillis();

                    mCallsFinished ++;

                } catch (IOException e) {
                    if(wDoc != null){
                        Elements wErrors = wDoc.select("p.texto_verde");
                        if(!wErrors.isEmpty()){
                            mCity.nextDaysError = wErrors.first().html();
                        }
                    }
                    mCallsFinished ++;
                }
            }
        };
        wNextDaysStateThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //to setup plus icon in toolbar
        menu.findItem(R.id.action_add).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_plus)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id == R.id.action_add) {
            newCity();
            return true;
        }
        return false;
    }

    public void newCity() {
        FragmentManager fm = getSupportFragmentManager();
        AddCityFragment wDialog = new AddCityFragment();
        wDialog.show(fm, "");
    }

}
