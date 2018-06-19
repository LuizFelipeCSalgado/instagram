package curso.instagram;
import com.parse.Parse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "";
    private static final String SERVER = "https://parseapi.back4app.com/";
    private static final String CLIENT_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(new Parse.Configuration.Builder(
                getApplicationContext())
                .applicationId(APP_ID)
                .server(SERVER)
                .clientKey(CLIENT_KEY)
                .build());
    }

}
