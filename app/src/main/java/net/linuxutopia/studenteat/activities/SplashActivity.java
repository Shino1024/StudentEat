package net.linuxutopia.studenteat.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import net.linuxutopia.studenteat.R;

/**
 * Created by jaros on 11/5/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private Animation titleAnimation;
    private TextView splashTitle;
    private Typeface titleFont;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private Handler splashEndHandler = new Handler();
    private Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        titleFont = Typeface.createFromAsset(getAssets(),"atma.ttf");

        mainActivityIntent = new Intent(this, MainActivity.class);

        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_animation);
        titleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashEndHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(mainActivityIntent);
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashTitle = findViewById(R.id.splash_title);
        splashTitle.setTypeface(titleFont);
        splashTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.10f * screenWidth);
        splashTitle.startAnimation(titleAnimation);

    }

}
