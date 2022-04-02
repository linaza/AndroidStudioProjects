#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_gregor_printnumbers_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
using namespace std;
    std:: string  hello;
        return env->NewStringUTF(hello.c_str());
}
