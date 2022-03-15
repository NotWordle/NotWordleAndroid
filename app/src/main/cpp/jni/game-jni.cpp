//
// Created by scott on 3/13/22.
//
#include <jni.h>

#include "game/Game.h"

#include <android/asset_manager.h>

using game::Game;

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Game_createNativeInstance(JNIEnv *env, jobject thiz) {
    return (jlong) new Game();
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_destroyNativeInstance(JNIEnv *env, jobject thiz,
                                                      jlong p_native_ptr) {
    if(p_native_ptr) delete (Game*)p_native_ptr;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Game_nativeGetGrid(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    return (jlong) &((Game*)p_native_ptr)->GetGrid();
}

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Game_nativeGetDictionary(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    return (jlong) &((Game*)p_native_ptr)->GetDictionary();
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_nativeInitializeGrid(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                                     jint word_size) {
    ((Game*)p_native_ptr)->InitializeGrid((const int) word_size);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_app_notwordle_objects_Game_nativeIsValidWord(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                                  jstring word) {
    // convert java string to c++ string
    jboolean isCopy = false;
    auto utf_str = env->GetStringUTFChars(word, &isCopy);
    return ((Game*)p_native_ptr)->IsValidWord(std::string(utf_str));
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_nativeLoadDictionary(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                                     jint word_size) {
    ((Game*)p_native_ptr)->LoadDictionary((int)word_size);
}
