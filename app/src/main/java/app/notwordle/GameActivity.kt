package app.notwordle

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import app.notwordle.objects.*
import java.io.File

class GameActivity : AppCompatActivity() {
    lateinit var game: Game
    var curRow: Int = 0
    var curCol: Int = 0
    lateinit var gridView: GridView
    lateinit var keyboard: GameKeyboard
    lateinit var input: EditText

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
        this.input = EditText(this)
        input.visibility = EditText.GONE
        input.hint = "enter input..."
        inputLayout.addView(input)
        input.setRawInputType(InputType.TYPE_CLASS_TEXT)
        input.setTextIsSelectable(true)
        val ic = input.onCreateInputConnection(EditorInfo())

        keyboard = findViewById(R.id.keyboard)
        keyboard.setInputConnection(ic)
        keyboard.maxInputSize = wordSize
        keyboard.gameCallback = {
            val curTxt = input.text
            val curRow = game.getGrid().CurrentRow()

            for(col in 0 until wordSize) {
                val letter : String = if (col < curTxt.length) curTxt[col].uppercase() else '-'.toString()
                println("updated grid at [$curRow, $col] with '$letter'")
                gridView.updateSpace(curRow, col, letter)
            }
        }

        val enterBtn = keyboard.keys[keyboard.getButtonIndex("ent")]
        enterBtn!!.setOnClickListener {
            val word = input.text.toString()
            if(game.isValidWord(word)) {
                input.text.clear()

                // update backend Grid and GridView with user input
                grid.updateLine(word)
                val res = game.checkGuess()
                gridView.updateGrid(grid)
                updateKeyboard()

                // check if game should continue or not (correct guess or out of guesses)
                if(res){
                    Toast.makeText(this, "You got it!", Toast.LENGTH_LONG).show()
                    input.isEnabled = false
                    enterBtn.isEnabled = false
                } else if(!grid.incrementGuess()) {
                    Toast.makeText(this, "Word was: $game_word, nice try!", Toast.LENGTH_LONG).show()
                    input.isEnabled = false
                    enterBtn.isEnabled = false
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

    private fun updateKeyboard() {
        game.markAvailableLetters();

        val valList = game.getAvailableLetters()

        for((idx, v) in valList.withIndex()) {
            val keyName : String = (idx.toChar() + 'A'.toInt()).toString()
            val color = when (v) {
                Validity.EMPTY -> Color.GRAY
                Validity.INVALID -> Color.DKGRAY
                Validity.CLOSE -> Color.BLUE
                Validity.CORRECT -> Color.GREEN
            }

            keyboard.updateKeyBackground(keyName, color)

        }
    }
}

