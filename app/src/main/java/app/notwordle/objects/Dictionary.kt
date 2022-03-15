package app.notwordle.objects

class Dictionary(addr: Long) {
    private var nativePtr: Long = 0

    init {
        nativePtr = addr
    }

    fun setDictionaryFile(abs_path : String) {
        nativeSetDictionaryFile(nativePtr, abs_path)
    }

    private external fun nativeSetDictionaryFile(p_native_ptr : Long, abs_path: String)
}