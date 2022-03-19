package app.notwordle.objects

class Game() {
    private var nativePtr: Long = 0

    init {
        nativePtr = createNativeInstance()
    }

    fun GetGrid() : Grid {
        return Grid(nativeGetGrid(nativePtr))
    }

    fun GetDictionary() : Dictionary {
        return Dictionary(nativeGetDictionary(nativePtr))
    }

    fun InitializeGrid(word_size : Int) {
        nativeInitializeGrid(nativePtr, word_size)
    }

    fun IsValidWord(word: String) : Boolean {
        return nativeIsValidWord(nativePtr, word)
    }

    fun LoadDictionary(word_size: Int) {
        nativeLoadDictionary(nativePtr, word_size)
    }

    fun checkGuess(game_word: String) : Boolean {
        return nativeCheckGuess(nativePtr, game_word)
    }

    private external fun createNativeInstance() : Long
    private external fun destroyNativeInstance(p_native_ptr : Long)
    private external fun nativeGetGrid(p_native_ptr: Long) : Long
    private external fun nativeGetDictionary(p_native_ptr: Long) : Long
    private external fun nativeInitializeGrid(p_native_ptr: Long, word_size: Int)
    private external fun nativeIsValidWord(p_native_ptr: Long, word: String) : Boolean
    private external fun nativeLoadDictionary(p_native_ptr: Long, word_size: Int)
    private external fun nativeCheckGuess(p_native_ptr: Long, game_word: String) : Boolean
}