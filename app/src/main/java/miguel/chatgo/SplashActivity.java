package miguel.chatgo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final TextView tv = (TextView) findViewById(R.id.welcomeTextview);
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "Lighthouse_PersonalUse.ttf");
        tv.setTypeface(font);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                       iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_imagensplashicono));
                    }
                }, 500);//Tiempo desde Gris hasta Amarillo

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        tv.setVisibility(View.VISIBLE);
                    }
                }, 1000); //Tiempo Amarillo hasta Welcome

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 1000); //Tiempo Welcome hasta MainActivity


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
