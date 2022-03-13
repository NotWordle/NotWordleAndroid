package app.notwordle

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import app.notwordle.objects.Grid
import android.widget.GridLayout.LayoutParams
import android.widget.LinearLayout
import app.notwordle.objects.Validity

class GameActivity : AppCompatActivity() {
    lateinit var grid: Grid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // create Grid from WordSize given by StartGameActivity
        val wordSize = intent.getIntExtra("WordSize", 0)
        grid = Grid()
        grid.initializeGrid(wordSize)
        createGrid(grid)

        val input_layout = findViewById<LinearLayout>(R.id.input_layout)
        input_layout.removeAllViews()
        val testBtn = Button(this)
        testBtn.setText("TRUNK")
        testBtn.setOnClickListener {
            grid.updateLine("TRUNK")
            println("trying to update line")
            createGrid(grid)
        }
        input_layout.addView(testBtn)

        val nextBtn = Button(this)
        nextBtn.setText("NEXT")
        nextBtn.setOnClickListener {
            grid.incrementGuess();
            println("incremented guess")
            createGrid(grid)
        }
        input_layout.addView(nextBtn)
    }

    private fun createGrid(grid: Grid) {
        val dimensions : Pair<Int, Int> = grid.getGridDimensions()

        println("GRID: \n $grid")

        val vertLayout = findViewById<LinearLayout>(R.id.game_grid)
        vertLayout.removeAllViews()
        vertLayout.layoutParams.height = LayoutParams.MATCH_PARENT
        vertLayout.layoutParams.width = LayoutParams.MATCH_PARENT

        var rowLayout : LinearLayout?
        for(row in 0 until dimensions.first) {
            rowLayout = LinearLayout(this)
            rowLayout.id = row
            rowLayout.orientation = LinearLayout.HORIZONTAL;
            val params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            rowLayout.layoutParams = params

            var txtView : TextView?
            for(col in 0 until dimensions.second) {
                txtView = TextView(this)
                txtView.id = col

                val space = grid.getSpace(row, col)
                val l : Char = space.getLetter();
                val v : Validity = space.getValidity()

                txtView.text = "[ $l : $v ]"
                txtView.setTextColor(Color.GREEN)
                val params2 = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                txtView.layoutParams = params2
                rowLayout.addView(txtView, col)
            }
            vertLayout.addView(rowLayout, row)
        }

    }
}

