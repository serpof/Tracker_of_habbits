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

/**
 * Main screen activity
 *
 */
class MainActivity : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null

    private var exit = false

    /*
     * periods for different states of tooth (6-16-24-hours)
     */
    private val firstPeriod = 6*60*60*1000
    private val secondPeriod = 16*60*60*1000
    private val thirdPeriod = 24*60*60*1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateStates()
        start.setOnClickListener{
            Intent(this, BrushingTimer::class.java).apply {
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
            if (getSound(sharedPref)){
                setSound(sharedPref, false)
                pauseSound()
            } else{
                setSound(sharedPref, true)
                playSound()
            }
        }
    }

    private fun playSound() {
        sound.setImageResource(R.drawable.music_off_button)
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.brushing)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun pauseSound() {
        sound.setImageResource(R.drawable.music_on_button)
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause()
    }

    private fun getPoints(sharedPref: SharedPreferences): Int {
        return sharedPref.getInt("points", 0)
    }

    private fun getSound(sharedPref: SharedPreferences): Boolean {
        return sharedPref.getBoolean("sound", true)
    }

    private fun setSound(sharedPref: SharedPreferences, mode: Boolean) {
        sharedPref.edit().putBoolean("sound", mode).apply()
    }

    private fun getLastBrushing(sharedPref: SharedPreferences): Long {
        return sharedPref.getLong("lastBrush", 0)
    }


    private fun updateStates() {

        //get information and settings from device
        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)

        this.textView.text = getPoints(sharedPref).toString()
        updateMusicState(sharedPref)
        updateToothState(sharedPref)
    }

    private fun updateToothState(sharedPref: SharedPreferences) {
        val lastBrushing = getLastBrushing(sharedPref)
        if (lastBrushing > 0) {
            val diffBetweenBrush = System.currentTimeMillis() - lastBrushing
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

    private fun updateMusicState(sharedPref: SharedPreferences) {
        if (getSound(sharedPref)){
            playSound()
        } else {
            pauseSound()
        }
    }

    override fun onBackPressed() {
        if (exit) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(applicationContext, getString(R.string.pressToExit),
                Toast.LENGTH_SHORT).show()
            exit = true
            Handler(Looper.getMainLooper()).postDelayed({ exit = false }, 2000)
        }
    }

    override fun onResume() {
        super.onResume()
        updateStates()
    }

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
}
