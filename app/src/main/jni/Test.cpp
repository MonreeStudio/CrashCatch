#include <jni.h>
#include <iostream>
#include <android/log.h>
#define LOG_TAG "JNILog"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


int checkExc(JNIEnv *env) {
    if(env->ExceptionCheck()) {
        env->ExceptionDescribe(); // writes to logcat
        env->ExceptionClear();
        return 1;
    }
    return -1;
}

void JNU_ThrowByName(JNIEnv *env, const char *name, const char *msg)
{
    // 查找异常类
    jclass cls = env->FindClass(name);
    /* 如果这个异常类没有找到，VM会抛出一个NowClassDefFoundError异常 */
    if (cls != NULL) {
        env->ThrowNew(cls, msg);  // 抛出指定名字的异常
    }
    /* 释放局部引用 */
    env->DeleteLocalRef(cls);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_monree_crashcatch_JNI_test(
        JNIEnv *env,
        jclass obj){
    return env -> NewStringUTF("Hello, JNI!");
}

extern "C"
JNIEXPORT void JNICALL
Java_com_monree_crashcatch_JNI_test2(JNIEnv *env, jclass clazz) {
    jthrowable exc = NULL;
    jmethodID mid = env->GetStaticMethodID(clazz,"exceptionCallback","()V");
    if (mid != NULL) {
        env->CallStaticVoidMethod(clazz,mid);
    }
    if (env->ExceptionCheck()) {  // 检查JNI调用是否有引发异常
        env->ExceptionDescribe();
        env->ExceptionClear();        // 清除引发的异常，在Java层不会打印异常的堆栈信息
        env->ThrowNew(env->FindClass("java/lang/Exception"),"JNI抛出的异常！" );
        return;
    }
    mid = env->GetStaticMethodID(clazz,"normalCallback","()V");
    if (mid != NULL) {
        env->CallStaticVoidMethod(clazz,mid);
    }
}