package com.example.androidmadgwick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
  ImuFragment mImuFragment;
  static{
    System.loadLibrary("madgwick");
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mImuFragment = new ImuFragment();
    setContentView(R.layout.activity_main);
    getSupportFragmentManager().beginTransaction().replace(R.id.container,mImuFragment).commit();
  }
}
