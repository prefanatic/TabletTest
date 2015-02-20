package egr.uri.edu.tablettest;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity implements ValueAnimator.AnimatorUpdateListener {

    private static final int DARKGREEN = Color.parseColor("#388E3C");
    private static final int GREEN = Color.parseColor("#4CAF50");
    private static final int RED = Color.parseColor("#F44336");
    private static final int DARKRED = Color.parseColor("#D32F2F");

    private boolean mRecording;
    private ObjectAnimator animator;
    private ValueAnimator statusAnimator;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecording = false;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setElevation(5);

        // Generate tasks
        int taskSize = 7;
        String[] tasks = new String[taskSize];
        for (int i = 0; i < taskSize; i++)
            tasks[i] = "Task " + (i + 1);

        Spinner mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tasks);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

        // Animator for our toolbar
        animator = ObjectAnimator.ofArgb(mToolbar, "backgroundColor", GREEN, RED);
        animator.addUpdateListener(this);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(200);

        statusAnimator = ValueAnimator.ofArgb();
        statusAnimator.addUpdateListener(this);
        statusAnimator.setEvaluator(new ArgbEvaluator());
        statusAnimator.setDuration(200);

        mToolbar.setBackgroundColor(RED);

    }

    public void onAnimationUpdate(ValueAnimator animator) {
        if (animator instanceof ObjectAnimator)
            mToolbar.setBackgroundColor((int) animator.getAnimatedValue());
        else
            getWindow().setStatusBarColor((int) animator.getAnimatedValue());
    }

    public void onRecordClick(View view) {
        if (mRecording) {
            animator.setIntValues(GREEN, RED);
            statusAnimator.setIntValues(DARKGREEN, DARKRED);
        } else {
            animator.setIntValues(RED, GREEN);
            statusAnimator.setIntValues(DARKRED, DARKGREEN);
        }
        statusAnimator.setEvaluator(new ArgbEvaluator());
        statusAnimator.start();
        animator.start();
        mRecording = !mRecording;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
