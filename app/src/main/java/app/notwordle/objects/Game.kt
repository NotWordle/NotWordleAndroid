package app.notwordle.objects

class Game() {
    private var nativePtr: Long = 0

    init {
        nativePtr = createNativeInstance()
    }

    fun wordSize(size: Int) {
        nativeWordSize(nativePtr, size)
    }

    fun randomizeSelectedWord() {
        nativeRandomizeSelectedWord(nativePtr)
    }

    fun selectedWord() : String {
        return nativeSelectedWord(nativePtr)
    }

    fun getGrid() : Grid {
        return Grid(nativeGetGrid(nativePtr))
    }

    fun setDictionaryFile(abs_path : String) {
        nativeSetDictionaryFile(nativePtr, abs_path)
    }

    fun initializeGrid() {
        nativeInitializeGrid(nativePtr)
    }

    fun isValidWord(word: String) : Boolean {
        return nativeIsValidWord(nativePtr, word)
    }

    fun loadDictionary() {
        nativeLoadDictionary(nativePtr)
    }

    fun checkGuess() : Boolean {
        return nativeCheckGuess(nativePtr)
    }

    private external fun createNativeInstance() : Long
    private external fun destroyNativeInstance(p_native_ptr : Long)
    private external fun nativeWordSize(p_native_ptr: Long, word_size: Int)
    private external fun nativeRandomizeSelectedWord(p_native_ptr: Long)
    private external fun nativeSelectedWord(p_native_ptr: Long) : String
    private external fun nativeGetGrid(p_native_ptr: Long) : Long
    private external fun nativeSetDictionaryFile(p_native_ptr : Long, abs_path: String)
    private external fun nativeInitializeGrid(p_native_ptr: Long)
    private external fun nativeIsValidWord(p_native_ptr: Long, word: String) : Boolean
    private external fun nativeLoadDictionary(p_native_ptr: Long)
    private external fun nativeCheckGuess(p_native_ptr: Long) : Boolean
}