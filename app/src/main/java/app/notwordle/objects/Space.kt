package app.notwordle.objects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.View

class Space(addr: Long) {
    private var nativePtr: Long = addr

    class Space() {
    }

    init {
        nativePtr = createNativeSpace()
    }

    fun getLetter() : Char {
        return getNativeLetter(nativePtr)
    }

    fun setLetter(letter: Char) {
        setNativeLetter(nativePtr, letter)
    }


    private external fun createNativeSpace() : Long
    private external fun destroyNativeInstance(p_native_ptr : Long)
    private external fun getNativeLetter(p_native_ptr: Long) : Char
    private external fun setNativeLetter(p_native_ptr: Long, letter: Char)
}
