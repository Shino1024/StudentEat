package net.linuxutopia.studenteat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import net.linuxutopia.studenteat.R;

import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private Animation titleAnimation;
    private TextView splashTitle;

    private Handler splashEndHandler = new Handler();
    private Intent mainActivityIntent;

    public final static int RC_FIREBASE_UI = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.putExtra("splashDone", true);

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
                        finish();
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashTitle = findViewById(R.id.splash_title);
        splashTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.10f * screenWidth);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
            );
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_FIREBASE_UI
            );
        } else {
            splashTitle.startAnimation(titleAnimation);
        }
    }
}
