package app.notwordle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class StartGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btn4 = findViewById<Button>(R.id.btn4)
        btn4.setOnClickListener {
            onWordLengthSelected(4)
        }
        val btn5 = findViewById<Button>(R.id.btn5)
        btn5.setOnClickListener {
            onWordLengthSelected(5)
        }
        val btn6 = findViewById<Button>(R.id.btn6)
        btn6.setOnClickListener {
            onWordLengthSelected(6)
        }
        val btn7 = findViewById<Button>(R.id.btn7)
        btn7.setOnClickListener {
            onWordLengthSelected(7)
        }
        val btn8 = findViewById<Button>(R.id.btn8)
        btn8.setOnClickListener {
            onWordLengthSelected(8)
        }
        val btn9 = findViewById<Button>(R.id.btn9)
        btn9.setOnClickListener {
            onWordLengthSelected(9)
        }

    }

    fun onWordLengthSelected(word_size: Int) {
        // TODO proceed to GameActivity and pass it the selected word length
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("WordSize", word_size)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}