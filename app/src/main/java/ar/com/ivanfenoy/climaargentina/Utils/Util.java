package ar.com.ivanfenoy.climaargentina.Utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by ivanfenoy on 13/2/17.
 */

public class Util {

    public static String[] DAY_CLEAR_SKY = {"https://c1.staticflickr.com/1/3/4609542_0572c127a4.jpg","https://c1.staticflickr.com/4/3286/3797854026_ee0c84e5fd_b.jpg","https://c1.staticflickr.com/1/31/64165465_993a8a2f32_z.jpg","https://c1.staticflickr.com/6/5010/5239528812_7939a5fafc_b.jpg","https://c1.staticflickr.com/4/3846/14903060946_db94dbe724_b.jpg"};
    public static String[] NIGTH_CLEAR_SKY = {"https://c1.staticflickr.com/8/7513/15904475559_8efff128e0_z.jpg","https://c1.staticflickr.com/8/7112/13893111794_86413b34a3_n.jpg","https://c1.staticflickr.com/8/7394/13788682964_0c9f18dcb6_n.jpg","https://c1.staticflickr.com/6/5487/12243734195_060062d567_k.jpg"};

    public static String[] CLOUDY_SKY = {"https://c1.staticflickr.com/1/657/22863869092_7b9cbf0b67_k.jpg","https://c1.staticflickr.com/8/7456/9429260417_dcdae0789f_k.jpg","https://c1.staticflickr.com/4/3714/9447329128_e9a8e5feec_c.jpg","https://c1.staticflickr.com/4/3904/14844561232_1c00658383_k.jpg"};

    public static String[] DAY_CLOUD_SKY = {"https://c1.staticflickr.com/6/5006/5299123583_b04026b4e8_n.jpg","https://c1.staticflickr.com/4/3530/4003900596_4744ce59af_b.jpg","https://c1.staticflickr.com/4/3701/11963565373_6019f51014_n.jpg","https://c1.staticflickr.com/3/2487/5721470988_51f1ff4ab7_b.jpg"};
    public static String[] NIGTH_CLOUD_SKY = {"https://c1.staticflickr.com/8/7571/15781989378_390c6b10a9_z.jpg","https://c1.staticflickr.com/8/7110/7478343572_3ec3dd32e1_z.jpg","https://c1.staticflickr.com/6/5188/14033650634_3f50e6cc4e_k.jpg","https://c1.staticflickr.com/9/8164/7705299916_d9cc5e7d51_b.jpg"};
    public static String[] RAIN_SKY = {"https://c1.staticflickr.com/6/5008/5257935478_ae317596e4_b.jpg","https://c1.staticflickr.com/4/3667/10964409374_362e2df460_k.jpg","https://c1.staticflickr.com/4/3405/3517419896_46f501a5a3_n.jpg","https://c1.staticflickr.com/4/3215/2600554713_2cf4c100d8.jpg"};
    public static String[] STORM_SKY = {"https://c1.staticflickr.com/4/3214/5871343077_97fd27c305_b.jpg","https://c1.staticflickr.com/1/127/331191753_345e76d805_m.jpg","https://c1.staticflickr.com/4/3910/14724406034_ab667bbba6_n.jpg","https://c1.staticflickr.com/9/8366/8597777733_91d7c5acc8_h.jpg"};
    public static String[] SNOW_SKY = {"https://c1.staticflickr.com/4/3185/3117645955_cc6af7e732_b.jpg","https://c1.staticflickr.com/9/8374/8457442600_dd9fef8741_n.jpg","https://c1.staticflickr.com/6/5051/5437914952_2350a9b43f_b.jpg","https://c1.staticflickr.com/3/2655/32050195743_b08530c95f_z.jpg"};
    public static String[] WINDY = {"https://c1.staticflickr.com/8/7490/16049000658_45f37cb599_k.jpg","https://c1.staticflickr.com/8/7739/16810833814_45f106c68f_z.jpg","https://c1.staticflickr.com/9/8457/8063802625_5262c92597_n.jpg","https://c1.staticflickr.com/6/5328/8856497526_b3cdebf234_h.jpg"};

    public static String capitalize(String pText){
        return pText.substring(0,1).toUpperCase() + pText.substring(1).toLowerCase();
    }

    public static void Toast(Context context, int res) {
        String msg = context.getResources().getString(res);
        Toast(context, msg);
    }

    public static void Toast(final Context context, final String msg) {
        Toast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void Toast(Context context, String msg, int length) {
        Toast.makeText(context, msg, length).show();
    }

    public static String getDate(long milliSeconds)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static Dialog Preloader(Context context, int message) {
        String msg = context.getResources().getString(message);
        return Util.Preloader(context, null, msg);
    }

    public static Dialog Preloader(Context context, String title, String message) {
        Dialog dialog = ProgressDialog.show(context, title, message, true);
        dialog.setCancelable(false);
        return dialog;
    }

    public static String setWheatherBackground(String image){
        boolean isMorning;
        if (image.contains("luna") || image.contains("noche")){
            isMorning = false;
        }
        else{
            isMorning = true;
        }
        String name = image.replace(".png","")
                .replace("http://www.smn.gov.ar/prongraf/iconos/luna/","")
                .replace("http://www.smn.gov.ar/mobile/images/iconos_chicos/","")
                .replace("images/iconos_noche/","")
                .replace("http://www.smn.gov.ar/prongraf/iconos/","")
                .replace("http://www.smn.gov.ar/mobile/images/iconos_chicos/","")
                .replace("images/iconos_dia/","");
        if(StringSimilarity.similarity(name, "depejado") > 0.8){
            if(isMorning){
                return getBackgroundRandom(DAY_CLEAR_SKY);
            }
            else{
                return getBackgroundRandom(NIGTH_CLEAR_SKY);
            }
        }
        else if(StringSimilarity.similarity(name, "algonublado") > 0.8 || StringSimilarity.similarity(name, "nubaum") > 0.8
                || StringSimilarity.similarity(name, "nubdismin") > 0.8 || StringSimilarity.similarity(name, "parcialmentenublado") > 0.8
                || StringSimilarity.similarity(name, "inestable") > 0.8 || StringSimilarity.similarity(name, "parcmnub") > 0.8
                || StringSimilarity.similarity(name, "desmejorando") > 0.8){
            if(isMorning){
                return getBackgroundRandom(DAY_CLOUD_SKY);
            }
            else{
                return getBackgroundRandom(NIGTH_CLOUD_SKY);
            }
        }
        else if(StringSimilarity.similarity(name, "nublado") > 0.8){
            return getBackgroundRandom(CLOUDY_SKY);
        }
        else if(name.contains("ventoso") || name.contains("viento")){
            return getBackgroundRandom(WINDY);
        }
        else if(StringSimilarity.similarity(name, "lluvia") > 0.8 || name.contains("inest") && name.contains("lluvia")
                || name.contains("inestclluvia_luna_movil") || name.contains("inestclluvia_sol_movil")){
            return getBackgroundRandom(RAIN_SKY);
        }

        else if(StringSimilarity.similarity(name, "tormenta") > 0.8 || name.contains("tormentaluna_chico_movil") || name.contains("tormenta_chico_movil") || name.contains("tormentasol_chico_movil")){
            return getBackgroundRandom(STORM_SKY);
        }
        else if(StringSimilarity.similarity(name, "nieve") > 0.8 || StringSimilarity.similarity(name, "lluvianieve") > 0.8){
            return getBackgroundRandom(SNOW_SKY);
        }
        return name;
    }

    public static String getBackgroundRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static String specialCharactersToHexa(String pWord){
        String wRta = "";
        for (int i = 0; i<pWord.length(); i++) {
            char wChar = pWord.charAt(i);
            if(wChar == 'á') {
                wRta += "%E1";
            }else if(wChar == 'Á'){
                wRta += "%C1";
            } else if(wChar == 'é'){
                wRta += "%E9";
            }else if(wChar == 'É'){
                wRta += "%C9";
            }else if(wChar == 'í'){
                wRta += "%ED";
            }else if(wChar == 'Í'){
                wRta += "%CD";
            }else if(wChar == 'ó'){
                wRta += "%F3";
            }else if(wChar == 'Ó'){
                wRta += "%D3";
            }else if(wChar == 'ú'){
                wRta += "%FA";
            }else if(wChar == 'Ú'){
                wRta += "%DA";
            }else if(wChar == 'ñ'){
                wRta += "%F1";
            }else if(wChar == 'Ñ'){
                wRta += "%D1";
            }
            else{
                wRta += wChar;
            }
        }
        return wRta;
    }
}
