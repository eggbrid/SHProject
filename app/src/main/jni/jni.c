//
// Created by xus on 2017/2/6.
//
#include "com_shpro_xus_shproject_view_call_CallListActivity.h"
#include <stdio.h>
#include <sys/time.h>
JNIEXPORT jstring JNICALL Java_com_shpro_xus_shproject_view_call_CallListActivity_getStringFromNative
        (JNIEnv * env, jobject jobject){
   // LOGINFO("LOGINFO");
    time_t t;
    time(&t);
    return (*env)->NewStringUTF(env, ctime(&t));
}

JNIEXPORT jstring Java_com_shpro_xus_shproject_view_call_CallListActivity_getString_1From_1c
        (JNIEnv * env, jobject jobject){
    return  (*(*env)).NewStringUTF(env,"NDK 来自于C文件");

}

