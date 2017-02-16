package ar.com.ivanfenoy.climaargentina.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ivanfenoy on 13/2/17.
 */

public class Util {
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
}
