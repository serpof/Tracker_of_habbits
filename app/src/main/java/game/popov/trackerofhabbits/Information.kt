package game.popov.trackerofhabbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_information.*

class Information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        btn_back.setOnClickListener{
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }
}