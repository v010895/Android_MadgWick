cmake_minimum_required(VERSION 3.4.1)

add_library(cpufeatures STATIC
            ${ANDROID_NDK}/sources/android/cpufeatures/cpu-features.c)
add_library(madgwick SHARED
        src/MadgwickAHRS.cpp
        src/com_example_androidmadgwick_nativefunction_IMUInterface.cpp)
target_include_directories(madgwick PRIVATE
    ${CMAKE_SOURCE_DIR}/include
    ${ANDROID_NDK}/sources/android/cpufeatures)

target_link_libraries(
madgwick android cpufeatures log)

