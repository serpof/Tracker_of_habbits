package game.popov.trackerofhabbits

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null
    private var exit = false
    private val firstPeriod = 21600000
    private val secondPeriod = 57600000
    private val thirdPeriod = 86400000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkSharedPref()
        start.setOnClickListener{
            Intent(this, timer::class.java).apply {
                startActivity(this)
            }
            pauseSound()
        }

        levels.setOnClickListener{
            Intent(this, Progress::class.java).apply {
                startActivity(this)
            }
            pauseSound()
        }

        information.setOnClickListener{
            Intent(this, Information::class.java).apply {
                startActivity(this)
            }
        }

        sound.setOnClickListener{
            val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
            if (sharedPref.getBoolean("sound", true)){
                sharedPref.edit().putBoolean("sound", false).apply()
                sound.setImageResource(R.drawable.music_on_button)
                pauseSound()
            } else{
                sharedPref.edit().putBoolean("sound", true).apply()
                sound.setImageResource(R.drawable.music_off_button)
                playSound()
            }
        }
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.brushing)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun pauseSound() {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause()
    }

    private fun checkSharedPref() {
        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        if (sharedPref.getInt("days", 0) != 0) {
            val str = sharedPref.getInt("days", 0).toString()
            this.textView.text = "$str"
        }
        if (sharedPref.getBoolean("sound", true)){
            sound.setImageResource(R.drawable.music_off_button)
            playSound()
        } else {
            sound.setImageResource(R.drawable.music_on_button)
            pauseSound()
        }
        val lastBrush = sharedPref.getLong("lastBrush", 0)
        if (lastBrush > 0) {
            val diffBetweenBrush = System.currentTimeMillis() - sharedPref.getLong("lastBrush", 0)
            when {
                diffBetweenBrush > thirdPeriod -> {
                    this.view_teeth.setImageResource(R.drawable.dirty_tooth_3)
                }
                diffBetweenBrush > secondPeriod -> {
                    this.view_teeth.setImageResource(R.drawable.dirty_tooth_2)
                }
                diffBetweenBrush > firstPeriod -> {
                    this.view_teeth.setImageResource(R.drawable.dirty_tooth_1)
                }
                else -> {
                    this.view_teeth.setImageResource(R.drawable.clean_tooth)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (exit) {
            super.onBackPressed()
            pauseSound()
            return
        } else {
            Toast.makeText(applicationContext, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
            exit = true
            Handler(Looper.getMainLooper()).postDelayed({ exit = false }, 2000)
        }
    }

    override fun onResume() {
        super.onResume()
        checkSharedPref()
    }

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
}
