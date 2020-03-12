package com.example.androidmadgwick.nativefunction;

public class IMUInterface {
  public static native void initMadgwick();
  public static native void updateIMU(float[] gryo, float[] acc, long timestamp);
}
