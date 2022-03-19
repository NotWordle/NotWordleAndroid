package app.notwordle.objects

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import app.notwordle.R

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

@SuppressLint("AppCompatCustomView")
class SpaceView(context: Context) : TextView(context) {
    init {
        // get GradientDrawable from Resources and make any changes to it before saving it to background
        val gradDraw = ResourcesCompat.getDrawable(resources, R.drawable.space_shape, null) as GradientDrawable?
        gradDraw!!.cornerRadius = 20f
        background = gradDraw
        TextViewCompat.setAutoSizeTextTypeWithDefaults(this, AUTO_SIZE_TEXT_TYPE_UNIFORM)
    }

    fun updateBackgroundColor(color_val : Int) {
        (background as GradientDrawable).setColor(color_val)
    }
}