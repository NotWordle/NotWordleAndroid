//
// Created by scott on 3/14/22.
//

#include <jni.h>
#include <android/log.h>
#include <fstream> // TODO temp
#include <iosfwd>

#include "game/Dictionary.h"

using game::Dictionary;

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Dictionary_nativeSetDictionaryFile(JNIEnv *env, jobject thiz,
                                                              jlong p_native_ptr,
                                                              jstring abs_path) {
    jboolean isCopy = false;
    auto abs_path_str = std::string(env->GetStringUTFChars(abs_path, &isCopy));
    ((Dictionary*)p_native_ptr)->SetDictionaryFile(abs_path_str);

    std::ifstream f(abs_path_str);
    bool valid = f.good();
    __android_log_write(ANDROID_LOG_DEBUG, "DICT", ("dict file from frontend: " + abs_path_str + " -- was valid? " + std::to_string(valid)).c_str());
}