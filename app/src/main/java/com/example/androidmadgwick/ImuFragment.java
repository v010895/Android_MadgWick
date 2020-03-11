package com.example.androidmadgwick;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
public class ImuFragment extends Fragment {
  TextView yawText;
  TextView pitchText;
  TextView rollText;
  ReadImuThread mReadImuThread;
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.imufragment,container,false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    yawText = (TextView)view.findViewById(R.id.yaw);
    pitchText = (TextView)view.findViewById(R.id.pitch);
    rollText = (TextView)view.findViewById(R.id.roll);
    mReadImuThread = new ReadImuThread((SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE));
  }

  @Override
  public void onResume() {
    mReadImuThread.start();
    super.onResume();
  }

  class ReadImuThread extends Thread{
    private SensorManager mSensorManager;
    private float[] imuData; //gx, gy, gz, ax, ay, az;
    private Looper mSensorLooper;
    private SensorEventListener mSensorEventListener;
    private Sensor mAccSensor, mGyroSensor;

    public ReadImuThread(SensorManager _sensorManager){
        mSensorManager = _sensorManager;
    }
    @Override
    public void run() {
      Looper.prepare();
      mSensorLooper = Looper.myLooper();
      Handler myHandler = new Handler();
      mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
          if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
          {
              Log.i("imuData", String.format("Acc(%10f, %10f, %10f)",event.values[0],event.values[1],event.values[2]));
          }
          else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
          {
            Log.i("imuData", String.format("Gyro(%10f, %10f, %10f)",event.values[0],event.values[1],event.values[2]));
          }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
      };
      mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
      mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      mSensorManager.registerListener(mSensorEventListener,mGyroSensor,2000,myHandler);
      mSensorManager.registerListener(mSensorEventListener,mAccSensor,2000,myHandler);
      Looper.loop();
    }
  }
}
