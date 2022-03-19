package app.notwordle.objects

class Dictionary(addr: Long) {
    private var nativePtr: Long = 0

    init {
        nativePtr = addr
    }

    fun setDictionaryFile(abs_path : String) {
        nativeSetDictionaryFile(nativePtr, abs_path)
    }

    fun selectRandomWord(word_size: Int) : String {
        return nativeSelectRandomWord(nativePtr, word_size)
    }

    private external fun nativeSetDictionaryFile(p_native_ptr : Long, abs_path: String)
    private external fun nativeSelectRandomWord(p_native_ptr: Long, word_size: Int) : String
}