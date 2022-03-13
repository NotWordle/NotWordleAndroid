package app.notwordle.objects

enum class Validity(v: Int) {
    EMPTY(0),
    INVALID(1),
    CLOSE(2),
    CORRECT(3)
}

class Space() {
    private var nativePtr: Long

    init {
        nativePtr = createNativeSpace()
    }

    constructor(addr: Long) : this() {
        nativePtr = addr
    }

    fun getLetter() : Char {
        return getNativeLetter(nativePtr)
    }

    fun setLetter(letter: Char) {
        setNativeLetter(nativePtr, letter)
    }

    fun getValidity() : Validity {
       return Validity.values()[getNativeValidity(nativePtr)]
    }

    private external fun createNativeSpace() : Long
    private external fun destroyNativeInstance(p_native_ptr : Long)
    private external fun getNativeLetter(p_native_ptr: Long) : Char
    private external fun setNativeLetter(p_native_ptr: Long, letter: Char)
    private external fun getNativeValidity(p_native_ptr: Long) : Int
}
