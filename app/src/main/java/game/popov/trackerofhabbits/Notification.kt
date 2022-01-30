package game.popov.trackerofhabbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.notification_dialog.*

class Notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_dialog)

        btn_continue.setOnClickListener{
            Intent(this, BrushingTimer::class.java).apply {
                startActivity(this)
            }
            finish()
        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }
}