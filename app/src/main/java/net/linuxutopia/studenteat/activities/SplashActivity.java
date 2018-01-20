package net.linuxutopia.studenteat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import net.linuxutopia.studenteat.R;

import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private Animation titleAnimation;
    private TextView splashTitle;

    private SignInButton signInButton;

    private Handler splashEndHandler = new Handler();
    private Intent mainActivityIntent;

    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;

    public final static int RC_SIGN_IN = 1;
    public final static int RC_FIREBASE_UI = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        )
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();

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

//        splashTitle.startAnimation(titleAnimation);

//        signInButton = findViewById(R.id.login_button);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                googleSignIn();
//            }
//        });

//        Button auth_btn = findViewById(R.id.auth_btn);
//        auth_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText login = findViewById(R.id.email_edit);
//                EditText password = findViewById(R.id.password_edit);
//                firebaseAuth.createUserWithEmailAndPassword(login.getText().toString(), password.getText().toString())
//                        .addOnCompleteListener(SplashActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                                    Snackbar.make(findViewById(R.id.splash_container),
//                                            user == null ? "null" : (user.getDisplayName() == null ? "nullDN" : user.getDisplayName()),
//                                            Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Log.e("logging", task.getException().getMessage());
//                                    Snackbar.make(findViewById(R.id.splash_container),
//                                            task.getException() == null ? "null ex emailpass" : task.getException().getMessage(),
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException exception) {
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case RC_FIREBASE_UI:
                if (resultCode == RESULT_OK) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Snackbar.make(findViewById(R.id.splash_container),
                            user == null ? "null user" : (user.getDisplayName() == null ? "null dn" : user.getDisplayName()),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(R.id.splash_container),
                            "nooooo",
                            Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Toast.makeText(this, "logged in as " + firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
        }
    }

//    private void googleSignIn() {
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        Toast.makeText(this, "before sign in to firebase", Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Snackbar.make(findViewById(R.id.splash_container),
                                    user == null ? "null" : (user.getDisplayName() == null ? "nullDN" : user.getDisplayName()),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(findViewById(R.id.splash_container),
                                    task.getException() == null ? "null ex" : task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FirebaseAuth.getInstance().signOut();
    }
}
