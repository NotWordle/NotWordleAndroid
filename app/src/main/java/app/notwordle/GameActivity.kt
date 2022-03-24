package app.notwordle

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import app.notwordle.objects.*
import java.io.File

class GameActivity : AppCompatActivity() {
    lateinit var game: Game
    var curLetter: Int = 0
    lateinit var gridView: GridView

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // get WordSize given by StartGameActivity Intent
        val wordSize = intent.getIntExtra("WordSize", 0)

        // create Game on cpp backend
        game = Game()
        game.wordSize(wordSize)
        game.initializeGrid()
        loadDictionary()
        game.randomizeSelectedWord()
        val game_word = game.selectedWord()

        println("game word: $game_word");

        // draw grid
        val grid = game.getGrid()
        val gameLayout = findViewById<LinearLayout>(R.id.game_grid)
        gameLayout.removeAllViews()
        gridView = GridView(this, gameLayout)
        gridView.generateGrid(grid)

        // create input by allowing user to write in text and enter it
        // TODO: change this to write type user guess into Spaces
        val inputLayout = findViewById<LinearLayout>(R.id.input_layout)
        inputLayout.removeAllViews()

        val input = EditText(this)
        input.hint = "enter input..."
        inputLayout.addView(input)

        val nextBtn = Button(this)
        inputLayout.addView(nextBtn)
        nextBtn.setText("ENTER")
        nextBtn.setOnClickListener {
            val word = input.text.toString()
            if(game.isValidWord(word)) {
                input.text.clear()

                // update backend Grid and GridView with user input
                grid.updateLine(word)
                val res = game.checkGuess()
                gridView.updateGrid(grid)

                // check if game should continue or not (correct guess or out of guesses)
                if(res){
                    Toast.makeText(this, "You got it!", Toast.LENGTH_LONG).show()
                    input.isEnabled = false
                    nextBtn.isEnabled = false
                } else if(!grid.incrementGuess()) {
                    Toast.makeText(this, "Word was: $game_word, nice try!", Toast.LENGTH_LONG).show()
                    input.isEnabled = false
                    nextBtn.isEnabled = false
                }
            } else {
                Toast.makeText(this, "Invalid Word!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDictionary() {
        // set up dictionary by loading dictionary asset into tmp cache file for C++ to access
        val out = File.createTempFile("dictionary", ".tmp", cacheDir)
        out.writeText(assets.open("words").bufferedReader().use {
            it.readText()
        })
        game.setDictionaryFile(out.absolutePath)
        game.loadDictionary()
    }
}

