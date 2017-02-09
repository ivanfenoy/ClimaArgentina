package ar.com.ivanfenoy.climaargentina;

import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private City mCity;
    private ViewPager mPagerCities;
    private PagerAdapter mPagerCitiesAdapter;
    private boolean callFinished = false;

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

        new CountDownTimer(TIMEOUT_MS, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(callFinished){
                    ArrayList<City> wListCities = new ArrayList<>();
                    wListCities.add(mCity);
                    mPagerCities = (ViewPager) findViewById(R.id.pager_cities);
                    mPagerCitiesAdapter = new PagerCitiesAdapter(getSupportFragmentManager(), wListCities);
                    mPagerCities.setAdapter(mPagerCitiesAdapter);
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();



//        // get our folding cell
//        final FoldingCell fc = (FoldingCell) findViewById(R.id.folding_cell);
//        fc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fc.toggle(false);
//            }
//        });
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

                    String wExtraInfo = "";
                    Elements wTable = wDoc.select("table.texto_temp_chico > tr");
                    for (Element wElement: wTable) {
                        Elements wTds = wElement.select("td");
                        wExtraInfo += wTds.get(0).html() + " " + wTds.get(1).html() + "\n";
                    }

                    mCity.city = wCity;
                    mCity.actualDegree = wNowTemp;
                    mCity.actualState = wNowTempText;

//                    TextView txttitle = (TextView) findViewById(R.id.title);
//                    txttitle.setText(wNowTemp);
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

                    Elements wDivs = wDoc.select("div#pron2");

                    for (Element wElement: wDivs) {
                        Day wDay = new Day();
                        Elements wTrs = wElement.select("h6").first().select("table.texto_encabezado").first().select("tr");
                        //Day name
                        wDay.day = wTrs.get(0).select("td").first().html();

                        //morning image
                        wDay.morningImage = wTrs.get(1).select("td").get(0).select("img").first().attr("src").replace("../../..", "http://www.smn.gov.ar");
                        //night image
                        wDay.nightImage = wTrs.get(1).select("td").get(1).select("img").first().attr("src").replace("../../..", "http://www.smn.gov.ar");

                        //temps states
                        Elements wPs = wElement.select("p");
                        wDay.morningText = wPs.get(0).html();
                        wDay.nightText = wPs.get(1).html();

                        //temps
                        wDay.minDegree = wElement.select("table.texto_blanco").first().select("tr").get(0).select("td").get(1).html();
                        wDay.maxDegree = wElement.select("table.texto_blanco").first().select("tr").get(1).select("td").get(1).html();

                        mCity.listDays.add(wDay);
                    }
                    callFinished = true;

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
