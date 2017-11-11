package net.linuxutopia.studenteat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jaros on 11/5/2017.
 */

public class SplashActivity extends AppCompatActivity {

    Animation titleAnimation;
    TextView title;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        custom_font = Typeface.createFromAsset(getAssets(),"atma.ttf");
        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_animation);
        title = findViewById(R.id.splashTitle);
        title.setTypeface(custom_font);
        title.startAnimation(titleAnimation);
    }

}
