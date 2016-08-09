package tlktechnology.darmok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.sloop.fonts.FontsManager;

/**
 * Created by RSA on 06-08-2016.
 */
public class Query_Mode extends DetailActivity {


    TextView customtittle;
    private Session speechSession;
    private Transaction ttsTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences s_pref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_darmok);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        FontsManager.initFormAssets(this, "fonts/MONTSERRAT-REGULAR.OTF");       //initialization
        FontsManager.changeFonts(this);
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);

    }
}
