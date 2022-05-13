package app.notwordle

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout


class GameKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    // 26 letter keys plus 'enter' and 'delete' keys
    val keys : Array<Button?> = arrayOfNulls(28)
    private val keyValues = SparseArray<String>()
    
    private var inputConnection: InputConnection? = null

    // upper limit of character inputs, reject inputs after this (except special keys)
    var maxInputSize : Int = 0
    var gameCallback : () -> Unit = {}

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)

        // set up on click listeners
        // R.id values for each button are unpredictable, so we have to set them each manually
        keys[getButtonIndex("A")] = findViewById(R.id.button_a)
        getKey("A")!!.setOnClickListener(this)
        keys[getButtonIndex("B")] = findViewById(R.id.button_b)
        getKey("B")!!.setOnClickListener(this)
        keys[getButtonIndex("C")] = findViewById(R.id.button_c)
        getKey("C")!!.setOnClickListener(this)
        keys[getButtonIndex("D")] = findViewById(R.id.button_d)
        getKey("D")!!.setOnClickListener(this)
        keys[getButtonIndex("E")] = findViewById(R.id.button_e)
        getKey("E")!!.setOnClickListener(this)
        keys[getButtonIndex("F")] = findViewById(R.id.button_f)
        getKey("F")!!.setOnClickListener(this)
        keys[getButtonIndex("G")] = findViewById(R.id.button_g)
        getKey("G")!!.setOnClickListener(this)
        keys[getButtonIndex("H")] = findViewById(R.id.button_h)
        getKey("H")!!.setOnClickListener(this)
        keys[getButtonIndex("I")] = findViewById(R.id.button_i)
        getKey("I")!!.setOnClickListener(this)
        keys[getButtonIndex("J")] = findViewById(R.id.button_j)
        getKey("J")!!.setOnClickListener(this)
        keys[getButtonIndex("K")] = findViewById(R.id.button_k)
        getKey("K")!!.setOnClickListener(this)
        keys[getButtonIndex("L")] = findViewById(R.id.button_l)
        getKey("L")!!.setOnClickListener(this)
        keys[getButtonIndex("M")] = findViewById(R.id.button_m)
        getKey("M")!!.setOnClickListener(this)
        keys[getButtonIndex("N")] = findViewById(R.id.button_n)
        getKey("N")!!.setOnClickListener(this)
        keys[getButtonIndex("O")] = findViewById(R.id.button_o)
        getKey("O")!!.setOnClickListener(this)
        keys[getButtonIndex("P")] = findViewById(R.id.button_p)
        getKey("P")!!.setOnClickListener(this)
        keys[getButtonIndex("Q")] = findViewById(R.id.button_q)
        getKey("Q")!!.setOnClickListener(this)
        keys[getButtonIndex("R")] = findViewById(R.id.button_r)
        getKey("R")!!.setOnClickListener(this)
        keys[getButtonIndex("S")] = findViewById(R.id.button_s)
        getKey("S")!!.setOnClickListener(this)
        keys[getButtonIndex("T")] = findViewById(R.id.button_t)
        getKey("T")!!.setOnClickListener(this)
        keys[getButtonIndex("U")] = findViewById(R.id.button_u)
        getKey("U")!!.setOnClickListener(this)
        keys[getButtonIndex("V")] = findViewById(R.id.button_v)
        getKey("V")!!.setOnClickListener(this)
        keys[getButtonIndex("W")] = findViewById(R.id.button_w)
        getKey("W")!!.setOnClickListener(this)
        keys[getButtonIndex("X")] = findViewById(R.id.button_x)
        getKey("X")!!.setOnClickListener(this)
        keys[getButtonIndex("Y")] = findViewById(R.id.button_y)
        getKey("Y")!!.setOnClickListener(this)
        keys[getButtonIndex("Z")] = findViewById(R.id.button_z)
        getKey("Z")!!.setOnClickListener(this)

        keys[getButtonIndex("del")] = findViewById(R.id.button_delete)
        getKey("del")!!.setOnClickListener(this)
        keys[getButtonIndex("ent")] = findViewById(R.id.button_enter)
        getKey("ent")!!.setOnClickListener(this)

        for(key in keys) {
            keyValues.put(key!!.id, key.text.toString())
        }
    }

    /// shortcut to get reference to a specific key
    fun getKey(keyName: String) : Button? {
        return keys[getButtonIndex(keyName)]
    }

    fun updateKeyBackground(keyName: String, color: Int) {
        getKey(keyName)?.setBackgroundColor(color)
    }

    /// helper to convert key's character value to array index
    fun getButtonIndex(keyName: String) : Int {
        // special keys
        if(keyName.length > 1) {
            return when (keyName) {
                "del" -> {
                    26
                }
                "ent" -> {
                    27
                }
                else -> {
                    throw ArrayIndexOutOfBoundsException("invalid key name provided!")
                }
            }
        }
        return keyName[0] - 'A'
    }
    
    override fun onClick(view: View) {
        if (inputConnection == null) return
        if (view.id == R.id.button_delete) {
            val selectedText = inputConnection!!.getSelectedText(0)
            if (TextUtils.isEmpty(selectedText)) {
                inputConnection!!.deleteSurroundingText(1, 0)
            } else {
                inputConnection!!.commitText("", 1)
            }
        } else {
            val etxt = inputConnection!!.getExtractedText(ExtractedTextRequest(), 0);
            if (etxt.text.length < maxInputSize) {
                val value = keyValues[view.id]
                inputConnection!!.commitText(value, 1)
            }
        }
        gameCallback()

    }

    fun setInputConnection(ic: InputConnection?) {
        inputConnection = ic
    }

    init {
        init(context, attrs)
    }
}