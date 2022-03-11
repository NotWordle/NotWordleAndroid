#include <jni.h>
#include <string>

#include "game/objects/Space.h"
using game::objects::Space;

extern "C" JNIEXPORT jstring JNICALL
Java_app_notwordle_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Space_createNativeSpace(JNIEnv *env, jobject thiz) {
    return (jlong) new Space();
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Space_destroyNativeInstance(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    if(p_native_ptr)
        delete (Space*)p_native_ptr;
}

extern "C"
JNIEXPORT jchar JNICALL
Java_app_notwordle_objects_Space_getNativeLetter(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    return ((Space*)p_native_ptr)->Letter();
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Space_setNativeLetter(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                         jchar letter) {
    // TODO: implement setNativeLetter()
    ((Space*)p_native_ptr)->Letter(static_cast<char>(letter));
}