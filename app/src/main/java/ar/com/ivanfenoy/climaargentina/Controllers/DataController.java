package ar.com.ivanfenoy.climaargentina.Controllers;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

import ar.com.ivanfenoy.climaargentina.Interfaces.ObjectCallback;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.Models.Day;
import ar.com.ivanfenoy.climaargentina.Utils.Util;

/**
 * Created by ivanfenoy on 2/3/17.
 */

public class DataController {

    public void getCityWeather(final Context pContext, String pCity, int pState, final ObjectCallback pCallback){
        final City wCity = new City();
        wCity.city = pCity;
        wCity.stateId = pState;
        getActualState(pContext, wCity, pCallback);
    }

    public void getActualState(final Context pContext, final City pCity, final ObjectCallback pCallback) {
        Thread wActualStateThread = new Thread() {
            public void run() {
                Document wDoc = null;
                try {
                    wDoc = Jsoup.connect("http://www.smn.gov.ar/mobile/estado_movil.php?ciudad=" + Util.specialCharactersToHexa(pCity.city).replace(" ", "_")).get();
                    Log.d("ACTUAL STATE", "http://www.smn.gov.ar/mobile/estado_movil.php?ciudad=" + Util.specialCharactersToHexa(pCity.city).replace(" ", "_"));

                    Element wSpanCity = wDoc.select("span.temp_ciudad").first();
                    String wCity = wSpanCity.html();

                    Element wSpanTemp = wDoc.select("span.temp_grande").first();
                    String wNowTemp = wSpanTemp.html();

                    Element wSpanTempText = wDoc.select("span.temp_texto").first();
                    String wNowTempText = wSpanTempText.html();

                    pCity.actualImage = wDoc.select("img[width=120px]").first().attr("src");
                    pCity.actualDegree = wNowTemp;
                    pCity.actualState = wNowTempText;

                    Elements wTable = wDoc.select("table.texto_temp_chico > tBody > tr");
                    pCity.thermal = wTable.get(0).select("td").get(1).html();
                    pCity.visibility = wTable.get(1).select("td").get(1).html();
                    pCity.humidity = wTable.get(2).select("td").get(1).html();
                    pCity.pressure = wTable.get(3).select("td").get(1).html();
                    pCity.wind = wTable.get(4).select("td").get(1).html();

                    pCity.actualError = "";

                    Log.d("ACTUAL STATE", "OK");

                } catch (Exception e) {
                    if(wDoc != null){
                        Elements wErrors = wDoc.select("p.texto_verde");
                        if(!wErrors.isEmpty()){
                            pCity.actualError = wErrors.first().html();
                        }
                    }
                    Log.d("ACTUAL STATE", "ERROR");
                }
                finally {
                    getNextDaysState(pContext, pCity, pCallback);
                }
            }
        };
        wActualStateThread.start();
    }

    public void getNextDaysState(final Context pContext, final City pCity, final ObjectCallback pCallback) {
        Thread wNextDaysStateThread = new Thread() {
            public void run() {
                Document wDoc = null;
                try {
                    wDoc = Jsoup.connect("http://www.smn.gov.ar/mobile/pronostico_movil.php?provincia="+ pCity.stateId +"&ciudad=" + Util.specialCharactersToHexa(pCity.city).replace(" ", "_")).get();
                    Log.d("NEXT DAYS", "http://www.smn.gov.ar/mobile/pronostico_movil.php?provincia="+ pCity.stateId +"&ciudad=" + Util.specialCharactersToHexa(pCity.city).replace(" ", "_"));

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

                        pCity.listDays.add(wDay);
                    }

                    pCity.nextDaysError = "";

                    Log.d("NEXT DAYS", "OK");

                } catch (IOException e) {
                    if(wDoc != null){
                        Elements wErrors = wDoc.select("p.texto_verde");
                        if(!wErrors.isEmpty()){
                            pCity.nextDaysError = wErrors.first().html();
                        }
                    }

                    Log.d("NEXT DAYS", "ERROR");
                }
                finally {
                    Calendar wCal = Calendar.getInstance();
                    pCity.lastUpdate = wCal.getTimeInMillis();

                    pCallback.done(null, pCity);
                    SharedPreferencesController.updateCity(pContext, pCity);

                }
            }
        };
        wNextDaysStateThread.start();
    }
}
