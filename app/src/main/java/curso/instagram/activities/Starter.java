package curso.instagram.activities;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by luizf on 19/02/2018.
 */

public class Starter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext()).build());
    }
}
