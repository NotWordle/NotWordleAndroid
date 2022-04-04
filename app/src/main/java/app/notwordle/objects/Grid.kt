package app.notwordle.objects

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout

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
}

class GridView(context: Context, var parent: LinearLayout) : View(context) {
    fun generateGrid(grid: Grid) {
        val dimensions = grid.getGridDimensions()
        for(row in 0 until dimensions.first) {
            val rowLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT).apply {
                    weight = 1f
                }
            }
            parent.addView(rowLayout, row)

            for(col in 0 until dimensions.second) {
                val spaceView = SpaceView(context).apply {
                    gravity = Gravity.CENTER
                    text = "${grid.getSpace(row, col).getLetter()}"
                }
                rowLayout.addView(spaceView, col)
                spaceView.setTextColor(Color.WHITE)
                spaceView.updateBackgroundColor(Color.GRAY)
            }
        }
    }

    fun getSpaceColor(row: Int, col: Int) : Int {
        val rowLayout = parent.getChildAt(row) as LinearLayout
        val spaceView = rowLayout.getChildAt(col) as SpaceView
        return spaceView.getBackgroundColor()
    }

    fun updateSpace(row: Int, col: Int, letter: String) {
        val rowLayout = parent.getChildAt(row) as LinearLayout
        val spaceView = rowLayout.getChildAt(col) as SpaceView
        if(letter.isNotEmpty()) {
            spaceView.text = letter
        } else {
            spaceView.text = "-"
        }
    }

    fun updateGrid(grid: Grid) {
        val dimensions = grid.getGridDimensions()
        for(row in 0 until dimensions.first) {
            val rowLayout = parent.getChildAt(row) as LinearLayout
            for(col in 0 until dimensions.second) {
                val spaceView = rowLayout.getChildAt(col) as SpaceView
                val space = grid.getSpace(row, col)

                spaceView.text = "${space.getLetter()}"
                val color = when (space.getValidity()) {
                    Validity.EMPTY -> Color.GRAY
                    Validity.INVALID -> Color.DKGRAY
                    Validity.CLOSE -> Color.BLUE
                    Validity.CORRECT -> Color.GREEN
                }
                spaceView.updateBackgroundColor(color)
            }
        }

    }
}