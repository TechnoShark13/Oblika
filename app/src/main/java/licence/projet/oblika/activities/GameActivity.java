package licence.projet.oblika.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import licence.projet.oblika.engine.utils.AccelerometerListener;
import licence.projet.oblika.engine.utils.AudioHandler;
import licence.projet.oblika.engine.utils.TouchEventListener;

public class GameActivity extends Activity {
    private GLSurfaceView gLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AccelerometerListener accelerometerListener = new AccelerometerListener();
        TouchEventListener touchEvent = new TouchEventListener();


        super.onCreate(savedInstanceState);

        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(accelerometerListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        AudioHandler.init(this);
        gLView = new GLView(this);

        setContentView(gLView);
        gLView.setOnTouchListener(touchEvent);
    }
}
