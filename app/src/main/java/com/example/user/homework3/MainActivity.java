package com.example.user.homework3;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static public SensorManager mSensorManager;
    Sensor mSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if ((mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!= null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!= null)){
            // Success!.
            if((mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!= null))
            {
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            }
            else
            {
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            }
        }
        else {
            // Failure! No Proximity or Light
            Toast.makeText(getApplicationContext(), "No sensor", Toast.LENGTH_LONG).show();
        }

        setContentView(R.layout.activity_main);

    }



    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.mSensorManager.registerListener(this, mSensor, 5000000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.mSensorManager.unregisterListener(this, mSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if ((event.sensor.getType() == Sensor.TYPE_LIGHT || event.sensor.getType() == Sensor.TYPE_PROXIMITY)) {
            Resources res = getResources();
            String Answer[] = res.getStringArray(R.array.answers);

            float sensVal = event.values[0];
            final EditText question =(EditText)findViewById(R.id.Question) ;
            String aux =question.getText().toString(); //It gets the question and we are allowed to start

            int Min = 0, Max = 19;
            if (aux.contains("?") && ((event.sensor.getType() == Sensor.TYPE_LIGHT && sensVal < 100) || (event.sensor.getType() == Sensor.TYPE_PROXIMITY && sensVal == 0))) {
                int rand = (Min + (int) (Math.random() * (Max - Min)));
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                ((EditText)findViewById(R.id.Question)).setText(null);
                String displayText = Answer[rand];
                TextView valueTextV = (TextView) findViewById(R.id.answers);
                valueTextV.setText(displayText);
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
