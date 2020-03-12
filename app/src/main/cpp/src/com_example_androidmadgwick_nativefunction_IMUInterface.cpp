#include "com_example_androidmadgwick_nativefunction_IMUInterface.h"
#include "MadgwickAHRS.h"
static Madgwick *mMadgwick;
JNIEXPORT void JNICALL Java_com_example_androidmadgwick_nativefunction_IMUInterface_initMadgwick
(JNIEnv *env, jclass){
    mMadgwick = new Madgwick();
}
JNIEXPORT void JNICALL Java_com_example_androidmadgwick_nativefunction_IMUInterface_updateIMU
(JNIEnv *env, jclass, jfloatArray _gyro, jfloatArray _acc, jlong _timestamp){
    jfloat *gyroData = env->GetFloatArrayElements(_gyro,0);
    jfloat *accData = env->GetFloatArrayElements(_acc,0);
    uint64_t timeStamp = _timestamp;
    mMadgwick->updateIMU(gyroData[0],gyroData[1],gyroData[2],accData[0],accData[1],accData[2],timeStamp);
    float eulerAngle[3];
    mMadgwick->computeAngles();
    env->ReleaseFloatArrayElements(_gyro,gyroData,0);
    env->ReleaseFloatArrayElements(_acc,accData,0);
}