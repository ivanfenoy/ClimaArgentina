package ar.com.ivanfenoy.climaargentina;

import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Adapters.PagerCitiesAdapter;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.Models.Day;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.bar_toolbar)Toolbar mToolbar;
    @Bind(R.id.pager_cities)ViewPager mPagerCities;

    private City mCity;
    private PagerAdapter mPagerCitiesAdapter;
    private int mCallsFinished = 0;

    private static final long TIMEOUT_MS = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCity = new City();
        try {
            getActualState();
            getNextDaysState();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setContentInsetsAbsolute(0, 0);
        }


        new CountDownTimer(TIMEOUT_MS, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(mCallsFinished == 2){
                    final ArrayList<City> wListCities = new ArrayList<>();
                    wListCities.add(mCity);
                    wListCities.add(mCity);
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
                    setTitle(wListCities.get(0).city);
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    public void setTitle(String pTitle){
        mToolbar.setTitle(pTitle);
    }

    public void getActualState() throws IOException {
        Thread wActualStateThread = new Thread() {
            public void run() {
                Document wDoc = null;
                try {
                    wDoc = Jsoup.connect("http://www.smn.gov.ar/mobile/estado_movil.php?ciudad=Rosario").get();

                    Element wSpanCity = wDoc.select("span.temp_ciudad").first();
                    String wCity = wSpanCity.html();

                    Element wSpanTemp = wDoc.select("span.temp_grande").first();
                    String wNowTemp = wSpanTemp.html();

                    Element wSpanTempText = wDoc.select("span.temp_texto").first();
                    String wNowTempText = wSpanTempText.html();

                    mCity.city = wCity;
                    mCity.actualDegree = wNowTemp;
                    mCity.actualState = wNowTempText;

                    Elements wTable = wDoc.select("table.texto_temp_chico > tBody > tr");
                    mCity.thermal = wTable.get(0).select("td").get(1).html();
                    mCity.visibility = wTable.get(1).select("td").get(1).html();
                    mCity.humidity = wTable.get(2).select("td").get(1).html();
                    mCity.pressure = wTable.get(3).select("td").get(1).html();
                    mCity.wind = wTable.get(4).select("td").get(1).html();

                    mCallsFinished ++;
                } catch (IOException e) {
                    if(wDoc != null){
                        Elements wErrors = wDoc.select("p.texto_verde");
                        if(!wErrors.isEmpty()){
                            String wError = wErrors.first().html();
                        }
                    }
                }
            }
        };
        wActualStateThread.start();
    }

    public void getNextDaysState() throws IOException {
        Thread wNextDaysStateThread = new Thread() {
            public void run() {
                Document wDoc = null;
                try {
                    wDoc = Jsoup.connect("http://www.smn.gov.ar/mobile/pronostico_movil.php?provincia=21&ciudad=Rosario").get();

                    Elements wDivs = wDoc.select("div#pron");

                    for (Element wElement: wDivs) {
                        Day wDay = new Day();
                        Elements wTrs = wElement.select("h6").first().select("table.texto_encabezado").first().select("tr");
                        //Day name
                        wDay.day = wTrs.get(0).select("td").first().html();

                        //morning image
                        wDay.morningImage = "http://www.smn.gov.ar/mobile/" + wTrs.get(1).select("td > img").attr("src");
                        //night image
                        wDay.nightImage = "http://www.smn.gov.ar/mobile/" +  wTrs.get(1).select("td > img").attr("src");

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
                    mCallsFinished ++;

                } catch (IOException e) {
                    if(wDoc != null){
                        Elements wErrors = wDoc.select("p.texto_verde");
                        if(!wErrors.isEmpty()){
                            String wError = wErrors.first().html();
                        }
                    }
                }
            }
        };
        wNextDaysStateThread.start();
    }

}
