//
// Created by scott on 3/6/22.
//
#include <jni.h>

#include "game/objects/Grid.h"

using game::objects::Grid;

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Grid_createNativeGrid(JNIEnv *env, jobject thiz, jint word_size) {
    return (jlong) new Grid(word_size);
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Grid_destroyNativeInstance(JNIEnv *env, jobject thiz,
                                                      jlong p_native_ptr) {
    if (p_native_ptr)
        delete (Grid *) (p_native_ptr);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_app_notwordle_objects_Grid_nativeToString(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    return env->NewStringUTF(((Grid *) p_native_ptr)->to_string().c_str());
}


extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Grid_nativeUpdateLine(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                                 jstring word) {
    // convert java string to c++ string
    jboolean isCopy = false;
    auto utf_str = env->GetStringUTFChars(word, &isCopy);
    ((Grid*)p_native_ptr)->UpdateLine(std::string(utf_str));
}

extern "C"
JNIEXPORT void JNICALL
Java_app_notwordle_objects_Grid_nativeClearLine(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
    ((Grid*)p_native_ptr)->ClearLine();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_app_notwordle_objects_Grid_nativeIncrementGuess(JNIEnv *env, jobject thiz,
                                                     jlong p_native_ptr) {
    return ((Grid*)p_native_ptr)->IncrementGuess();
}

extern "C"
JNIEXPORT jobject JNICALL
Java_app_notwordle_objects_Grid_nativeGetGridDimensions(JNIEnv *env, jobject thiz,
                                                        jlong p_native_ptr) {
    auto dim = ((Grid*)p_native_ptr)->GetGridDimensions();

    // Pair class/functions
    auto pairClass = env->FindClass("kotlin/Pair");
    auto pair_constructor = env->GetMethodID(pairClass, "<init>","(Ljava/lang/Object;Ljava/lang/Object;)V");

    // Integer wrapper class/functions
    auto intClass = env->FindClass("java/lang/Integer");
    auto intValueOf = env->GetStaticMethodID(intClass, "valueOf", "(I)Ljava/lang/Integer;");

    // convert C++ values to jobjects
    auto first = env->CallStaticObjectMethod(intClass, intValueOf, dim.first);
    auto second = env->CallStaticObjectMethod(intClass, intValueOf, dim.second);

    return env->NewObject(pairClass, pair_constructor, first, second);

}

extern "C"
JNIEXPORT jlong JNICALL
Java_app_notwordle_objects_Grid_nativeGetSpace(JNIEnv *env, jobject thiz, jlong p_native_ptr,
                                               jint row, jint col) {
    // get C++ Space object
    auto c_row = static_cast<int>(row);
    auto c_col = static_cast<int>(col);
    auto& space = ((Grid*)p_native_ptr)->GetSpace(c_row, c_col);

    // convert C++ object to
    return (jlong)&space;
}
