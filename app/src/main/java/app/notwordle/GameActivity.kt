package app.notwordle

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import app.notwordle.objects.Grid
import android.widget.GridLayout.LayoutParams
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
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
        game.wordSize(wordSize)
        game.initializeGrid()

        // set up dictionary by loading dictionary asset into tmp cache file for C++ to access
        val out = File.createTempFile("dictionary", ".tmp", cacheDir)
        out.writeText(assets.open("words").bufferedReader().use {
            it.readText()
        })
        game.setDictionaryFile(out.absolutePath)
        game.loadDictionary()
        game.randomizeSelectedWord()

        val game_word = game.selectedWord()

        // TODO: this should be done within the CPP element only
        println("game word: $game_word");

        // draw grid
        val grid = game.getGrid()
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

            if(game.isValidWord(word)) {
                grid.updateLine(input.text.toString())
                val res = game.checkGuess()
                println("was that word correct? $res")

                input.text.clear()

                if(!grid.incrementGuess()) {
                    Toast.makeText(this, "Word was: $game_word, nice try!", Toast.LENGTH_LONG).show()
                    input.isEnabled = false
                    nextBtn.isEnabled = false
                }
                createGrid(grid)

                if(res){
                    Toast.makeText(this, "You got it!", Toast.LENGTH_LONG).show()
                    input.isEnabled = false
                    nextBtn.isEnabled = false
                }

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

        var rowLayout : LinearLayout?
        for(row in 0 until dimensions.first) {
            rowLayout = LinearLayout(this)
            gameLayout.addView(rowLayout, row)

            rowLayout.orientation = LinearLayout.HORIZONTAL;
            val params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            rowLayout.layoutParams = params

            for(col in 0 until dimensions.second) {
                val spaceView = SpaceView(this)
                spaceView.gravity = Gravity.CENTER
                rowLayout.addView(spaceView, col)

                val space = grid.getSpace(row, col)
                val l : Char = space.getLetter();
                val v : Validity = space.getValidity()

                spaceView.text = "$l"
                spaceView.setTextColor(Color.WHITE)

                val color = when (v) {
                    Validity.EMPTY -> Color.GRAY
                    Validity.INVALID -> Color.DKGRAY
                    Validity.CLOSE -> Color.BLUE
                    Validity.CORRECT -> Color.GREEN
                }
                spaceView.updateBackgroundColor(color)

                spaceView.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                    weight = 1f
                    updateMargins(left = 2, top = 2, right = 2, bottom = 2)
                }
            }
        }

    }
}

