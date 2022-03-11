package app.notwordle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.notwordle.databinding.ActivityMainBinding
import android.widget.Button
import app.notwordle.objects.Grid
import app.notwordle.objects.Space

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startBtn = findViewById<Button>(R.id.start_btn)
        startBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, StartGameActivity::class.java).apply {
                // pass anything along?
            })
        })

        val optionsBtn = findViewById<Button>(R.id.options_btn)
        optionsBtn.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java).apply {
                // pass anything along?
            })
        }
    }

    /**
     * A native method that is implemented by the 'notwordle' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'notwordle' library on application startup.
        init {
            System.loadLibrary("notwordle")
        }
    }
}