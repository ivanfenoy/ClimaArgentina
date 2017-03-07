package ar.com.ivanfenoy.climaargentina.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.WeathericonsIcons;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Activities.MainActivity;
import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.Elements.WeatherIcon;
import ar.com.ivanfenoy.climaargentina.Models.City;
import ar.com.ivanfenoy.climaargentina.R;
import ar.com.ivanfenoy.climaargentina.Utils.Util;

/**
 * Created by ivanfenoy on 6/3/17.
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int wAppWidgetId = appWidgetIds[i];
            Intent wIntent = new Intent(context, MainActivity.class);
            PendingIntent wPendingIntent = PendingIntent.getActivity(context, 0, wIntent, 0);
            RemoteViews wRootViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            wRootViews.setOnClickPendingIntent(R.id.widget_content, wPendingIntent);

            final ArrayList<City> wListCities = SharedPreferencesController.getListCities(context);
            City wCity = wListCities.get(0);

            int wResId = Util.getResourceId(context, WeatherIcon.getIcon(wCity.actualImage).replace("{","").replace("}",""), "drawable", context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), wResId);
            wRootViews.setImageViewBitmap(R.id.img_current_state, bitmap);

            wRootViews.setTextViewText(R.id.current_city, wCity.city);
            wRootViews.setTextViewText(R.id.current_degree, wCity.actualDegree);

            appWidgetManager.updateAppWidget(wAppWidgetId, wRootViews);
        }

    }

}