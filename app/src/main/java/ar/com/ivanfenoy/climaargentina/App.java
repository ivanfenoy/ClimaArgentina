package ar.com.ivanfenoy.climaargentina;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

/**
 * Created by ivanfenoy on 13/2/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Iconify.with(new FontAwesomeModule())
                .with(new WeathericonsModule());
    }
}
