package app.notwordle

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout


class GameKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val keyValues = SparseArray<String>()
    private var inputConnection: InputConnection? = null
    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)

        // set up on click listeners
        val buttonA = findViewById<Button>(R.id.button_a)
        buttonA!!.setOnClickListener(this)
        val buttonB = findViewById<Button>(R.id.button_b)
        buttonB!!.setOnClickListener(this)
        val buttonC = findViewById<Button>(R.id.button_c)
        buttonC!!.setOnClickListener(this)
        val buttonD = findViewById<Button>(R.id.button_d)
        buttonD!!.setOnClickListener(this)
        val buttonE = findViewById<Button>(R.id.button_e)
        buttonE!!.setOnClickListener(this)
        val buttonF = findViewById<Button>(R.id.button_f)
        buttonF!!.setOnClickListener(this)
        val buttonG = findViewById<Button>(R.id.button_g)
        buttonG!!.setOnClickListener(this)
        val buttonH = findViewById<Button>(R.id.button_h)
        buttonH!!.setOnClickListener(this)
        val buttonI = findViewById<Button>(R.id.button_i)
        buttonI!!.setOnClickListener(this)
        val buttonJ = findViewById<Button>(R.id.button_j)
        buttonJ!!.setOnClickListener(this)
        val buttonK = findViewById<Button>(R.id.button_k)
        buttonK!!.setOnClickListener(this)
        val buttonL = findViewById<Button>(R.id.button_l)
        buttonL!!.setOnClickListener(this)
        val buttonM = findViewById<Button>(R.id.button_m)
        buttonM!!.setOnClickListener(this)
        val buttonN = findViewById<Button>(R.id.button_n)
        buttonN!!.setOnClickListener(this)
        val buttonO = findViewById<Button>(R.id.button_o)
        buttonO!!.setOnClickListener(this)
        val buttonP = findViewById<Button>(R.id.button_p)
        buttonP!!.setOnClickListener(this)
        val buttonQ = findViewById<Button>(R.id.button_q)
        buttonQ!!.setOnClickListener(this)
        val buttonR = findViewById<Button>(R.id.button_r)
        buttonR!!.setOnClickListener(this)
        val buttonS = findViewById<Button>(R.id.button_s)
        buttonS!!.setOnClickListener(this)
        val buttonT = findViewById<Button>(R.id.button_t)
        buttonT!!.setOnClickListener(this)
        val buttonU = findViewById<Button>(R.id.button_u)
        buttonU!!.setOnClickListener(this)
        val buttonV = findViewById<Button>(R.id.button_v)
        buttonV!!.setOnClickListener(this)
        val buttonW = findViewById<Button>(R.id.button_w)
        buttonW!!.setOnClickListener(this)
        val buttonX = findViewById<Button>(R.id.button_x)
        buttonX!!.setOnClickListener(this)
        val buttonY = findViewById<Button>(R.id.button_y)
        buttonY!!.setOnClickListener(this)
        val buttonZ = findViewById<Button>(R.id.button_z)
        buttonZ!!.setOnClickListener(this)

        val buttonDelete = findViewById<Button>(R.id.button_delete)
        buttonDelete!!.setOnClickListener(this)
        val buttonEnter = findViewById<Button>(R.id.button_enter)
        buttonEnter!!.setOnClickListener(this)

        // map key values
        keyValues.put(R.id.button_a, "a")
        keyValues.put(R.id.button_b, "b")
        keyValues.put(R.id.button_c, "c")
        keyValues.put(R.id.button_d, "d")
        keyValues.put(R.id.button_e, "e")
        keyValues.put(R.id.button_f, "f")
        keyValues.put(R.id.button_g, "g")
        keyValues.put(R.id.button_h, "h")
        keyValues.put(R.id.button_i, "i")
        keyValues.put(R.id.button_j, "j")
        keyValues.put(R.id.button_k, "k")
        keyValues.put(R.id.button_l, "l")
        keyValues.put(R.id.button_m, "m")
        keyValues.put(R.id.button_n, "n")
        keyValues.put(R.id.button_o, "o")
        keyValues.put(R.id.button_p, "p")
        keyValues.put(R.id.button_q, "q")
        keyValues.put(R.id.button_r, "r")
        keyValues.put(R.id.button_s, "s")
        keyValues.put(R.id.button_t, "t")
        keyValues.put(R.id.button_u, "u")
        keyValues.put(R.id.button_v, "v")
        keyValues.put(R.id.button_w, "w")
        keyValues.put(R.id.button_x, "x")
        keyValues.put(R.id.button_y, "y")
        keyValues.put(R.id.button_z, "z")

        // TODO this shouldn't new line but should try to check the grid
        keyValues.put(R.id.button_enter, "\n")
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
            val value = keyValues[view.id]
            inputConnection!!.commitText(value, 1)
        }
    }

    fun setInputConnection(ic: InputConnection?) {
        inputConnection = ic
    }

    init {
        init(context, attrs)
    }
}