package br.android.demos.hellogesturedetector;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class FlingGestureDetectorActivity extends AppCompatActivity {
    private TextView text;
    private ImageView img;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling_gesture_detector);
        img = (ImageView) findViewById(R.id.img);
        text = (TextView) findViewById(R.id.text);
        text.setText("Faça um gesto");
        gestureDetector = new GestureDetector(this, new MyFlingGestureDetector());
    }

    class MyFlingGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private float swipeMinDistance = 100;
        private float swipeThreasholdVelocity = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThreasholdVelocity) {
                    text.setText("<<< Left");
                    mover(-100);
                } else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThreasholdVelocity) {
                    text.setText("Right >>>");
                    mover(100);
                }
            } catch (Exception e) {
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ok = gestureDetector.onTouchEvent(event);
        if (ok) {
            return super.onTouchEvent(event);

        }
        return ok;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void mover(int x) {
        img.animate().setDuration(1000).xBy(x);
    }
}

