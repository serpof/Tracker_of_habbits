package game.popov.trackerofhabbits

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_information.*

class Progress : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        var endChange = sharedPref.getInt("days", 0)
        if (sharedPref.getBoolean("sound", true)) {
            playSound()
        }

        val steps: Array<Int> = arrayOf(
            R.id.step1,
            R.id.step2,
            R.id.step3,
            R.id.step4,
            R.id.step5,
            R.id.step6,
            R.id.step7,
            R.id.step8,
            R.id.step9,
            R.id.step10,
            R.id.step11,
            R.id.step12,
            R.id.step13,
            R.id.step14,
            R.id.step15,
            R.id.step16,
            R.id.step17,
            R.id.step18,
            R.id.step19,
            R.id.step20,
            R.id.step21,
            R.id.step22,
            R.id.step23,
            R.id.step24,
            R.id.step25,
            R.id.step26,
            R.id.step27,
            R.id.step28,
            R.id.step29,
            R.id.step30,
            R.id.step31,
            R.id.step32,
            R.id.step33,
            R.id.step34,
            R.id.step35,
            R.id.step36,
            R.id.step37,
            R.id.step38,
            R.id.step39,
            R.id.step40,
            R.id.step41,
            R.id.step42,
            R.id.step43,
            R.id.step44,
            R.id.step45,
            R.id.step46,
            R.id.step47,
            R.id.step48,
            R.id.step49,
            R.id.step50,
            R.id.step51,
            R.id.step52,
            R.id.step53,
            R.id.step54,
            R.id.step55,
            R.id.step56,
            R.id.step57,
            R.id.step58,
            R.id.step59,
            R.id.step60,
            R.id.step61,
            R.id.step62,
            R.id.step63
        )

        for(i in 0..--endChange){
            val image = this.findViewById<ImageView>(steps[i])
            when(i){
                0 -> image.setImageResource(R.drawable.step_1)
                6 -> image.setImageResource(R.drawable.step_7)
                13 -> image.setImageResource(R.drawable.step_14)
                20 -> image.setImageResource(R.drawable.step_21)
                27 -> image.setImageResource(R.drawable.step_28)
                34 -> image.setImageResource(R.drawable.step_35)
                41 -> image.setImageResource(R.drawable.step_42)
                48 -> image.setImageResource(R.drawable.step_49)
                55 -> image.setImageResource(R.drawable.step_56)
                62 -> image.setImageResource(R.drawable.step_63)
                else -> image.setImageResource(R.drawable.step)
            }
        }

        btn_back.setOnClickListener{
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.progress)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
        finish()
    }
}