package cloud.dishwish.ragmart.dishwish.start;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;

public class PresentationActivity extends AppCompatActivity {

    private static int WELCOME_TIMEOUT = 3000;
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_activity);

        new GetIngredientsTask(this).execute();

        appName = findViewById(R.id.presentation_app_name);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PresentationActivity.this,StartActivity.class);

                Pair pair = new Pair<View,String>(appName,"appNameTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PresentationActivity.this, pair);

                startActivity(intent, options.toBundle());
                //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, WELCOME_TIMEOUT);
    }
}
