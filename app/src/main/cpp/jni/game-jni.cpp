//
// Created by scott on 3/13/22.
//
#include <jni.h>
#include <android/log.h>

#include "game/Game.h"

#include <android/asset_manager.h>
#include <fstream>
#include <iosfwd>

using game::Game;

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Game_createNativeInstance(JNIEnv *env, jobject thiz) {
    return (jlong) new Game();
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_nativeWordSize(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                               jint word_size) {
    ((Game*)p_native_ptr)->WordSize(word_size);
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_nativeRandomizeSelectedWord(JNIEnv *env, jobject thiz,
                                                            jlong p_native_ptr) {
    ((Game*)p_native_ptr)->RandomizeSelectedWord();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_app_notwordle_objects_Game_nativeSelectedWord(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    auto cpp_word = ((Game*)p_native_ptr)->SelectedWord();
    jstring j_word = env->NewStringUTF(cpp_word.c_str());
    return j_word;
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
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_nativeInitializeGrid(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    ((Game*)p_native_ptr)->InitializeGrid();
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
Java_app_notwordle_objects_Game_nativeLoadDictionary(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    ((Game*)p_native_ptr)->LoadDictionary();
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Game_nativeSetDictionaryFile(JNIEnv *env, jobject thiz,
                                                        jlong p_native_ptr, jstring abs_path) {
    jboolean isCopy = false;
    auto abs_path_str = std::string(env->GetStringUTFChars(abs_path, &isCopy));
    ((Game*)p_native_ptr)->SetDictionaryFile(abs_path_str);

    std::ifstream f(abs_path_str);
    bool valid = f.good();
    __android_log_write(ANDROID_LOG_DEBUG, "GAME_JNI", ("dict file from frontend: " + abs_path_str + " -- was valid? " + std::to_string(valid)).c_str());
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_app_notwordle_objects_Game_nativeCheckGuess(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    return ((Game*)p_native_ptr)->CheckGuess();
}