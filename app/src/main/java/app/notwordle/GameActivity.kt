package app.notwordle

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.EditText
import app.notwordle.objects.Grid
import android.widget.GridLayout.LayoutParams
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.updateMargins
import app.notwordle.objects.Game
import app.notwordle.objects.SpaceView
import app.notwordle.objects.Validity
import java.io.File

class GameActivity : AppCompatActivity() {
    lateinit var game: Game
    var curLetter: Int = 0

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // get WordSize given by StartGameActivity Intent
        val wordSize = intent.getIntExtra("WordSize", 0)

        // create Game with grid set to chosen word size
        game = Game()
        game.InitializeGrid(wordSize)

        // set up dictionary by loading dictionary asset into tmp cache file for C++ to access
        val out = File.createTempFile("dictionary", ".tmp", cacheDir)
        out.writeText(assets.open("words").bufferedReader().use {
            it.readText()
        })
        val dict = game.GetDictionary()
        dict.setDictionaryFile(out.absolutePath)
        game.LoadDictionary(wordSize)

        // draw grid
        val grid = game.GetGrid()
        createGrid(grid)

        // create input by allowing user to write in text and enter it
        // TODO: change this to write type user guess into Spaces
        val inputLayout = findViewById<LinearLayout>(R.id.input_layout)
        inputLayout.removeAllViews()

        val input = EditText(this)
        input.hint = "enter input..."
        inputLayout.addView(input)

        val nextBtn = Button(this)
        nextBtn.setText("ENTER")
        nextBtn.setOnClickListener {
            val word = input.text.toString()

            if(game.IsValidWord(word)) {
                grid.updateLine(input.text.toString())
                input.text.clear()

                grid.incrementGuess();
                createGrid(grid)
            } else {
                Toast.makeText(this, "Invalid Word!", Toast.LENGTH_SHORT).show()
            }
        }
        inputLayout.addView(nextBtn)
    }

    // TODO: move this to GridView or GameView class
    private fun createGrid(grid: Grid) {
        val dimensions : Pair<Int, Int> = grid.getGridDimensions()

        val gameLayout = findViewById<LinearLayout>(R.id.game_grid)
        gameLayout.removeAllViews()
        gameLayout.layoutParams.height = LayoutParams.MATCH_PARENT
        gameLayout.layoutParams.width = LayoutParams.MATCH_PARENT

        var rowLayout : LinearLayout?
        for(row in 0 until dimensions.first) {
            rowLayout = LinearLayout(this)
            gameLayout.addView(rowLayout, row)

            rowLayout.orientation = LinearLayout.HORIZONTAL;
            val params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            rowLayout.layoutParams = params

            for(col in 0 until dimensions.second) {
                val spaceView = SpaceView(this)
                rowLayout.addView(spaceView, col)

                // default get width from parent instead of display
                val metrics = DisplayMetrics()
                this.windowManager.defaultDisplay.getMetrics(metrics)
                val maxWidth = metrics.widthPixels
                val maxHeight = metrics.heightPixels
                val unitWidth = maxWidth / dimensions.second // space between Spaces

                val space = grid.getSpace(row, col)
                val l : Char = space.getLetter();
                val v : Validity = space.getValidity()

                spaceView.text = "[ $l ]"
                val color = when (v) {
                    Validity.EMPTY -> Color.GRAY
                    Validity.INVALID -> Color.RED
                    Validity.CLOSE -> Color.BLUE
                    Validity.CORRECT -> Color.GREEN
                }
                spaceView.updateBackground(color)
                val params2 = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                params2.updateMargins(left = unitWidth / 2, top = 0, right = 0, bottom = 0)
                spaceView.layoutParams = params2
            }
        }

    }
}

