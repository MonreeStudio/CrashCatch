#include <jni.h>
#include <iostream>
#include <android/log.h>
#define LOG_TAG "JNILog"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#include <signal.h>
#include <setjmp.h>
#include <pthread.h>

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
//    if (env->ExceptionCheck()) {  // 检查JNI调用是否有引发异常
//        env->ExceptionDescribe();
//        jthrowable jthrowable = env->ExceptionOccurred();
//        env->ExceptionClear();        // 清除引发的异常，在Java层不会打印异常的堆栈信息
//        env->Throw(jthrowable);
//        //env->ThrowNew(env->FindClass("java/lang/Exception"), "哦豁");
//        return;
//    }
    int *p = 0; //空指针
    *p = 1;
    mid = env->GetStaticMethodID(clazz,"normalCallback","()V");
    if (mid != NULL) {
        env->CallStaticVoidMethod(clazz,mid);
    }
}

// 定义代码跳转锚点
sigjmp_buf JUMP_ANCHOR;
volatile sig_atomic_t error_cnt = 0;
JNIEnv *mEnv;

void exception_handler(int errorCode){
    error_cnt += 1;
    LOGE("JNI_ERROR, error code %d, cnt %d", errorCode, error_cnt);

    // DO SOME CLEAN STAFF HERE...
        if (mEnv->ExceptionCheck()) {  // 检查JNI调用是否有引发异常
            mEnv->ExceptionDescribe();
        jthrowable jthrowable = mEnv->ExceptionOccurred();
        mEnv->ExceptionClear();        // 清除引发的异常，在Java层不会打印异常的堆栈信息
        mEnv->Throw(jthrowable);
        //env->ThrowNew(env->FindClass("java/lang/Exception"), "哦豁");
        return;
    }

    // jump to main function to do exception process
    siglongjmp(JUMP_ANCHOR, 1);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_monree_crashcatch_JNI_test3(JNIEnv *env, jclass clazz) {
    mEnv = env;
    // 代码跳转锚点
    if (sigsetjmp(JUMP_ANCHOR, 1) != 0) {
//        return -1;
        return;
    }

    // 注册要捕捉的系统信号量
    struct sigaction sigact{};
    struct sigaction old_action{};
    sigaction(SIGABRT, nullptr, &old_action);
    if (old_action.sa_handler != SIG_IGN) {
        sigset_t block_mask;
        sigemptyset(&block_mask);
        sigaddset(&block_mask, SIGABRT); // handler处理捕捉到的信号量时，需要阻塞的信号
        sigaddset(&block_mask, SIGSEGV); // handler处理捕捉到的信号量时，需要阻塞的信号

        sigemptyset(&sigact.sa_mask);
        sigact.sa_flags = 0;
        sigact.sa_mask = block_mask;
        sigact.sa_handler = exception_handler;
        sigaction(SIGABRT, &sigact, nullptr); // 注册要捕捉的信号
        sigaction(SIGSEGV, &sigact, nullptr); // 注册要捕捉的信号
        LOGE("信号量检测");
    }
//    jint value = process(env, jobj, m, n);
//    return value;
}