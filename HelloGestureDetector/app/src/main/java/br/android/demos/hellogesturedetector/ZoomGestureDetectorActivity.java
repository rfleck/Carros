package br.android.demos.hellogesturedetector;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.TextView;

/**
 * Demonstração do gesto de pinch, utilizado para fazer zoom in/out *
 *
 * @author rlecheta
 */
public class ZoomGestureDetectorActivity extends Activity {
    TextView text;
    private ScaleGestureDetector gestureDetector;
    private MyImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_gesture_detector);
        img = (MyImageView) findViewById(R.id.img);
        text = (TextView) findViewById(R.id.text);
        text.setText("Faça um gesto de pinch/zoom");
        gestureDetector = new ScaleGestureDetector(this, new ZoomGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ok = gestureDetector.onTouchEvent(event);
        if (ok) {
            return super.onTouchEvent(event);
        }
        return ok;
    }

    // Detecta o zoom
    class ZoomGestureListener extends SimpleOnScaleGestureListener {
        private float scaleFactor = 1;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
// Exibe o fator de escala no TextView text.setText(String.valueOf(scaleFactor));
// Altera o fator de escala da view customizada, e pede para ela se redesenhar img.setScaleFactor(scaleFactor);
            img.invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }
}

