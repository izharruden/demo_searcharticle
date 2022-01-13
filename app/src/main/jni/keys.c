#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_demo_articlesearch_BaseConfig_API_1KEY(JNIEnv *env, jclass clazz) {
    return (*env)->NewStringUTF(env, "6WUGPMVVGruhUmZZtKgoCr3KsnGPIDWM");
}

JNIEXPORT jstring JNICALL
Java_com_demo_articlesearch_BaseConfig_BASE_1URL(JNIEnv *env, jclass clazz) {
    return (*env)->NewStringUTF(env, "https://api.nytimes.com/");
}