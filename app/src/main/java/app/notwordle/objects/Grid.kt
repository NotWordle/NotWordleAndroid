package app.notwordle.objects

import android.content.Context
import android.view.View

class Grid() {
    private var nativePtr: Long = 0

    constructor(addr: Long) : this() {
        nativePtr = addr
    }

    fun initializeGrid(word_size: Int) {
        nativePtr = createNativeGrid(word_size)
    }

    override fun toString() : String {
        return nativeToString(nativePtr);
    }

    fun updateLine(word: String) {
        nativeUpdateLine(nativePtr, word)
    }

    fun clearLine() {
        nativeClearLine(nativePtr);
    }

    fun incrementGuess() : Boolean {
        return nativeIncrementGuess(nativePtr)
    }

    fun getGridDimensions() : Pair<Int, Int> {
        return nativeGetGridDimensions(nativePtr)
    }

    fun getSpace(row: Int, col: Int) : Space {
        return Space(nativeGetSpace(nativePtr, row, col))
    }

    private external fun createNativeGrid(word_size: Int) : Long
    private external fun destroyNativeInstance(p_native_ptr : Long)
    private external fun nativeToString(p_native_ptr: Long) : String
    private external fun nativeUpdateLine(p_native_ptr: Long, word: String)
    private external fun nativeClearLine(p_native_ptr: Long)
    private external fun nativeIncrementGuess(p_native_ptr: Long) : Boolean
    private external fun nativeGetGridDimensions(p_native_ptr: Long) : Pair<Int, Int>
    private external fun nativeGetSpace(p_native_ptr: Long, row: Int, col: Int) : Long
    //private external fun nativeMarkLettersUsed(p_native_ptr: Long,) TODO: Validity. Do I even need this tho
}

// TODO: transfer Grid creation/update to this class
class GridView(context: Context) : View(context) {

}