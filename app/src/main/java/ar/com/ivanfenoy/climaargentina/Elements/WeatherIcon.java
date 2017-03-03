package ar.com.ivanfenoy.climaargentina.Elements;

import android.content.Context;
import android.util.AttributeSet;

import com.joanzapata.iconify.widget.IconTextView;

import ar.com.ivanfenoy.climaargentina.Utils.StringSimilarity;

public class WeatherIcon extends IconTextView {

    public WeatherIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImage(String image){
        if(image.contains("prongraf/iconos")){
            if(image.contains("/luna/")){
                String name = image.replace("http://www.smn.gov.ar/prongraf/iconos/luna/","").replace(".png","");
                if(StringSimilarity.similarity(name, "depejado") > 0.8){
                    setText("{wi_yahoo_31}");
                }
                else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8){
                    setText("{wi_yahoo_33}");
                }
                else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                    setText("{wi_yahoo_27}");
                }
                else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(name.contains("ventoso") || name.contains("viento")){
                    setText("{wi_yahoo_24}");
                }
                else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                    setText("{wi_yahoo_11}");
                }
                else if(name.contains("inest") && name.contains("lluvia")){
                    setText("{wi_yahoo_13}");
                }
                else if(name.contains("tormentaluna_chico_movil") || name.contains("tormenta_chico_movil")){
                    setText("{wi_yahoo_3}");
                }
                else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                    setText("{wi_yahoo_25}");
                }
                else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                    setText("{wi_yahoo_7}");
                }
                else if(name.contains("inestclluvia_luna_movil")){
                    setText("{wi_yahoo_42}");
                }
            }
            else{
                String name = image.replace("http://www.smn.gov.ar/prongraf/iconos/","").replace(".png","");
                if(StringSimilarity.similarity(name, "depejado") > 0.8){
                    setText("{wi_yahoo_32}");
                }
                else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8){
                    setText("{wi_yahoo_34}");
                }
                else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(name.contains("ventoso") || name.contains("viento")){
                    setText("{wi_yahoo_24}");
                }
                else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                    setText("{wi_yahoo_11}");
                }
                else if(name.contains("inest") && name.contains("lluvia")){
                    setText("{wi_yahoo_13}");
                }
                else if(name.contains("tormenta_chico_movil")){
                    setText("{wi_yahoo_3}");
                }
                else if(name.contains("tormentasol_chico_movil")){
                    setText("{wi_yahoo_37}");
                }
                else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                    setText("{wi_yahoo_25}");
                }
                else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                    setText("{wi_yahoo_7}");
                }
                else if(name.contains("inestclluvia_sol_movil")){
                    setText("{wi_yahoo_37}");
                }
            }
        }
        else if(image.contains("images/iconos")){
            if(image.contains("/iconos_noche/")){
                String name = image.replace("images/iconos_noche/","").replace(".png","");
                if(StringSimilarity.similarity(name, "depejado") > 0.8){
                    setText("{wi_yahoo_31}");
                }
                else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8){
                    setText("{wi_yahoo_33}");
                }
                else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                    setText("{wi_yahoo_27}");
                }
                else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(name.contains("ventoso") || name.contains("viento")){
                    setText("{wi_yahoo_24}");
                }
                else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                    setText("{wi_yahoo_11}");
                }
                else if(name.contains("inest") && name.contains("lluvia")){
                    setText("{wi_yahoo_13}");
                }
                else if(name.contains("tormentaluna_chico_movil") || name.contains("tormenta_chico_movil")){
                    setText("{wi_yahoo_3}");
                }
                else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                    setText("{wi_yahoo_25}");
                }
                else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                    setText("{wi_yahoo_7}");
                }
                else if(name.contains("inestclluvia_luna_movil")){
                    setText("{wi_yahoo_42}");
                }
            }
            else{
                String name = image.replace("images/iconos_dia/","").replace(".png","");
                if(StringSimilarity.similarity(name, "depejado") > 0.8){
                    setText("{wi_yahoo_32}");
                }
                else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8){
                    setText("{wi_yahoo_34}");
                }
                else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(name.contains("ventoso") || name.contains("viento")){
                    setText("{wi_yahoo_24}");
                }
                else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                    setText("{wi_yahoo_11}");
                }
                else if(name.contains("inest") && name.contains("lluvia")){
                    setText("{wi_yahoo_13}");
                }
                else if(name.contains("tormenta_chico_movil")){
                    setText("{wi_yahoo_3}");
                }
                else if(name.contains("tormentasol_chico_movil")){
                    setText("{wi_yahoo_37}");
                }
                else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                    setText("{wi_yahoo_25}");
                }
                else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                    setText("{wi_yahoo_7}");
                }
                else if(name.contains("inestclluvia_sol_movil")){
                    setText("{wi_yahoo_37}");
                }
            }
        }
        else{
            if(image.contains("luna")){
                String name = image.replace("http://www.smn.gov.ar/mobile/images/iconos_chicos/","").replace(".png","");
                if(StringSimilarity.similarity(name, "depejado") > 0.8){
                    setText("{wi_yahoo_31}");
                }
                else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8){
                    setText("{wi_yahoo_33}");
                }
                else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                    setText("{wi_yahoo_27}");
                }
                else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(name.contains("ventoso") || name.contains("viento")){
                    setText("{wi_yahoo_24}");
                }
                else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                    setText("{wi_yahoo_11}");
                }
                else if(name.contains("inest") && name.contains("lluvia")){
                    setText("{wi_yahoo_13}");
                }
                else if(name.contains("tormentaluna_chico_movil") || name.contains("tormenta_chico_movil")){
                    setText("{wi_yahoo_3}");
                }
                else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                    setText("{wi_yahoo_25}");
                }
                else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                    setText("{wi_yahoo_7}");
                }
                else if(name.contains("inestclluvia_luna_movil")){
                    setText("{wi_yahoo_42}");
                }
            }
            else{
                String name = image.replace("http://www.smn.gov.ar/mobile/images/iconos_chicos/","").replace(".png","");
                if(StringSimilarity.similarity(name, "depejado") > 0.8){
                    setText("{wi_yahoo_32}");
                }
                else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8){
                    setText("{wi_yahoo_34}");
                }
                else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                    setText("{wi_yahoo_26}");
                }
                else if(name.contains("ventoso") || name.contains("viento")){
                    setText("{wi_yahoo_24}");
                }
                else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                    setText("{wi_yahoo_11}");
                }
                else if(name.contains("inest") && name.contains("lluvia")){
                    setText("{wi_yahoo_13}");
                }
                else if(name.contains("tormenta_chico_movil")){
                    setText("{wi_yahoo_3}");
                }
                else if(name.contains("tormentasol_chico_movil")){
                    setText("{wi_yahoo_37}");
                }
                else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                    setText("{wi_yahoo_25}");
                }
                else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                    setText("{wi_yahoo_7}");
                }
                else if(name.contains("inestclluvia_sol_movil")){
                    setText("{wi_yahoo_37}");
                }
            }
        }
    }

    public void setIcon(String image){
        boolean isMorning;
        if (image.contains("luna") || image.contains("noche")){
            isMorning = false;
        }
        else{
            isMorning = true;
        }
        if(!isMorning){
            String name = image.replace(".png","")
                                .replace("http://www.smn.gov.ar/prongraf/iconos/luna/","")
                                .replace("http://www.smn.gov.ar/mobile/images/iconos_chicos/","")
                                .replace("images/iconos_noche/","");
            if(StringSimilarity.similarity(name, "depejado") > 0.8){
                setText("{wi_yahoo_31}");
            }
            else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8
                    || StringSimilarity.similarity(name, "nubdismin") > 0.8 || StringSimilarity.similarity(name, "parcialmentenublado") > 0.8){
                setText("{wi_yahoo_33}");
            }
            else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                setText("{wi_yahoo_27}");
            }
            else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                setText("{wi_yahoo_26}");
            }
            else if(name.contains("ventoso") || name.contains("viento")){
                setText("{wi_yahoo_24}");
            }
            else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                setText("{wi_yahoo_11}");
            }
            else if(name.contains("inest") && name.contains("lluvia")){
                setText("{wi_yahoo_13}");
            }
            else if(StringSimilarity.similarity(name, "tormenta") > 0.8 || name.contains("tormentaluna_chico_movil") || name.contains("tormenta_chico_movil")){
                setText("{wi_yahoo_3}");
            }
            else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                setText("{wi_yahoo_25}");
            }
            else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                setText("{wi_yahoo_7}");
            }
            else if(name.contains("inestclluvia_luna_movil")){
                setText("{wi_yahoo_42}");
            }
        }
        else{
            String name = image.replace(".png","")
                    .replace("http://www.smn.gov.ar/prongraf/iconos/","")
                    .replace("http://www.smn.gov.ar/mobile/images/iconos_chicos/","")
                    .replace("images/iconos_dia/","");
            if(StringSimilarity.similarity(name, "depejado") > 0.8){
                setText("{wi_yahoo_32}");
            }
            else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8
                    || StringSimilarity.similarity(name, "nubdismin") > 0.8 || StringSimilarity.similarity(name, "parcialmentenublado") > 0.8){
                setText("{wi_yahoo_34}");
            }
            else if(StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8){
                setText("{wi_yahoo_26}");
            }
            else if(StringSimilarity.similarity(name, "nublado") > 0.8){
                setText("{wi_yahoo_26}");
            }
            else if(name.contains("ventoso") || name.contains("viento")){
                setText("{wi_yahoo_24}");
            }
            else if(StringSimilarity.similarity(name, "lluvia") > 0.8){
                setText("{wi_yahoo_11}");
            }
            else if(name.contains("inest") && name.contains("lluvia")){
                setText("{wi_yahoo_13}");
            }
            else if(StringSimilarity.similarity(name, "tormenta") > 0.8 || name.contains("tormenta_chico_movil")){
                setText("{wi_yahoo_3}");
            }
            else if(name.contains("tormentasol_chico_movil")){
                setText("{wi_yahoo_37}");
            }
            else if(StringSimilarity.similarity(name, "nieve") > 0.8){
                setText("{wi_yahoo_25}");
            }
            else if(StringSimilarity.similarity(name, "lluvianieve") > 0.8){
                setText("{wi_yahoo_7}");
            }
            else if(name.contains("inestclluvia_sol_movil")){
                setText("{wi_yahoo_37}");
            }
        }
    }

    /*Dia:
    -despejado: wi-yahoo-32 (depejado)
    -algo nublado, nubosidad en disminucion, mejorando, nubosidad variable: wi-yahoo-34  (algonublado)
    -parcialmente nublado, nubosidad en aumento, desmejorado, inestable: wi-yahoo-30 (inestable, parcmnub)
    -nublado: wi-yahoo-26 (nublado)
    -viento blanco, ventoso: wi-yahoo-24 (ventoso_chico_movil)
    -llovizna: wi-yahoo-11
    -lluvia, lluvias y lloviznas: wi-yahoo-17
    -tormenta: wi-yahoo-3 (tormenta_chico_movil)
    -tormenta y sol: wi-yahoo-3 (tormentasol_chico_movil)
    -nieve: wi-yahoo-25
    -nieve con sol: wi-yahoo-14
    -lluvia y nieve, llovizna y nieve: wi-yahoo-7
    -inestable con lluvia: wi-yahoo-37 (inestclluvia_sol_movil)

    Noche:
    -despejado: wi-yahoo-31 (depejado)
    -algo nublado, nubosidad en disminucion, mejorando, nubosidad variable: wi-yahoo-33 (algonublado)
    -parcialmente nublado, nubosidad en aumento, desmejorado, inestable: wi-yahoo-27 (parcmnub, inestable)
    -viento blanco, ventoso: wi-yahoo-24 (ventoso_chico_movil)
    -nublado: wi-yahoo-26 (nubLado)
    -llovizna: wi-yahoo-11
    -lluvia, lluvias y lloviznas: wi-yahoo-17
    -tormenta,tormenta y luna: wi-yahoo-3 (tormenta_chico_movil)
    -nieve, nieve con luna: wi-yahoo-25
    -lluvia y nieve, llovizna y nieve: wi-yahoo-7
    -inestable con lluvia: wi-yahoo-41 (inestclluvia_luna_movil)
    */
}
