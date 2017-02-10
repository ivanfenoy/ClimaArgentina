package ar.com.ivanfenoy.climaargentina.Elements;

import android.content.Context;
import android.util.AttributeSet;

import com.joanzapata.iconify.widget.IconTextView;

/**
 * Created by vrunoa on 4/20/16.
 */
public class WeatherIcon extends IconTextView {

    public WeatherIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void beClose() {
        setText("{fa-close}");
    }

    public void setIcon(String state, boolean isMorning){
        if(isMorning){
//            if(state.equalsIgnoreCase())
        }
        else{

        }
    }

    /*Dia:
    -despejado: wi-yahoo-32
    -algo nublado, nubosidad en disminucion, mejorando, nubosidad variable: wi-yahoo-34
    -parcialmente nublado, nubosidad en aumento, desmejorado, inestable: wi-yahoo-30
    -nublado: wi-yahoo-26
    -viento blanco, ventoso: wi-yahoo-24
    -llovizna: wi-yahoo-11
    -lluvia, lluvias y lloviznas: wi-yahoo-17
    -tormenta: wi-yahoo-3
    -tormenta y sol: wi-yahoo-3
    -nieve: wi-yahoo-25
    -nieve con sol: wi-yahoo-14
    -lluvia y nieve, llovizna y nieve: wi-yahoo-7
    -inestable con lluvia: wi-yahoo-37

    Noche:
    -despejado: wi-yahoo-31
    -algo nublado, nubosidad en disminucion, mejorando, nubosidad variable: wi-yahoo-33
    -parcialmente nublado, nubosidad en aumento, desmejorado, inestable: wi-yahoo-27
    -viento blanco, ventoso: wi-yahoo-24
    -nublado: wi-yahoo-26
    -llovizna: wi-yahoo-11
    -lluvia, lluvias y lloviznas: wi-yahoo-17
    -tormenta, tormenta y luna: wi-yahoo-3
    -nieve, nieve con luna: wi-yahoo-25
    -lluvia y nieve, llovizna y nieve: wi-yahoo-7
    -inestable con lluvia: wi-yahoo-41
    */
}
