package game.popov.trackerofhabbits

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

/**
 * Process of brushing
 */
class BrushingTimer : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var progressBar: ProgressBar


    var currentCount = 0
    private val maxCount = 8

    //time between brushes for adding points
    private val periodBetweenBrush = 4*60*60*1000
    var mMediaPlayer: MediaPlayer? = null

    // Seconds of brushing in different stages. Long stages - 30 seconds, short stages - 15 seconds
    private val longStageTime = 30
    private val shortStageTime = 15

    private fun getSound(sharedPref: SharedPreferences): Boolean {
        return sharedPref.getBoolean("sound", true)
    }

    private fun getPoints(sharedPref: SharedPreferences): Int {
        return sharedPref.getInt("points", 0)
    }

    private fun setPoints(sharedPref: SharedPreferences, points: Int) {
        sharedPref.edit().putInt("points", points).apply()
    }

    private fun getLastBrushing(sharedPref: SharedPreferences): Long {
        return sharedPref.getLong("lastBrush", 0)
    }

    private fun setLastBrushing(sharedPref: SharedPreferences, lastBrush: Long) {
        sharedPref.edit().putLong("lastBrush", lastBrush).apply()
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer)
        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        if (getSound(sharedPref)) {
            playSound()
        }
        initWidgets()
        startTimer( shortStageTime)
        Glide.with(applicationContext)
            .load(R.raw.tooth_paste_anim)
            .into(imageView)

    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.timer)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

     private fun initWidgets(){
        textView = findViewById(R.id.time_until_end)
        progressBar = findViewById(R.id.time_progress)
        imageView = findViewById(R.id.gif_instruction)
    }

   private fun startTimer(countSeconds: Int){
       val timer = Timer()
       var taskProgress = 1
       var seconds = countSeconds - 1
       progressBar.max = countSeconds
       textView.clearComposingText()
       val text = getString(R.string.restOfSeconds)

        timer.scheduleAtFixedRate(object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                if (taskProgress < countSeconds) {
                    taskProgress++

                    when (currentCount){
                        1 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.timer_1)
                            .into(imageView)}
                        2 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.timer_2)
                            .into(imageView)}
                        3 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.timer_3)
                            .into(imageView)}
                        4 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.timer_4)
                            .into(imageView)}
                        5 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.timer_5)
                            .into(imageView)}
                        6 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.timer_6)
                            .into(imageView)}
                        7 -> runOnUiThread {Glide.with(applicationContext)
                            .load(R.raw.water)
                            .into(imageView)}
                    }

                    val second = (seconds--).toString()
                    progressBar.progress = taskProgress
                    runOnUiThread {
                        textView.text = "$text $second"
                    }
                    textView.clearComposingText()
                } else {
                    runOnUiThread {
                        textView.text = "$text 0"
                        timer.cancel()
                    }
                    currentCount++
                    startAgainIfNeed()
                }
            }
        }, 1000, 1000)
    }

    private fun saveState(){
        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        var points = getPoints(sharedPref)
        val now = System.currentTimeMillis()
        if (now - getLastBrushing(sharedPref) > periodBetweenBrush) {
            setPoints(sharedPref, ++points)
            setLastBrushing(sharedPref, now)
        }
    }

    private fun startAgainIfNeed() {
        if (currentCount < maxCount){
            if (currentCount < 5){
                progressBar.progress = 0
                startTimer(longStageTime)
                return
            }
            else{
                progressBar.progress = 0
                startTimer (shortStageTime)
                return
            }
        }
        saveState()
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
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