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

class timer : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var progressBar: ProgressBar
    var currentCount = 0
    private val maxCount = 8
    private val periodBetweenBrush = 14400000
    var mMediaPlayer: MediaPlayer? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer)
        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        if (sharedPref.getBoolean("sound", true)) {
            playSound()
        }
        initWidgets()
        startTimer( 14)
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
       var taskProgress = 0
       var seconds = countSeconds
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
                        timer.cancel()
                    }
                    currentCount++
                    startAgainIfNeed()
                }
            }
        }, 1000, 1000)
    }

    private fun startAgainIfNeed() {
        if (currentCount < maxCount){
            if (currentCount < 5){
                progressBar.progress = 0
                startTimer(29)
                return
            }
            else{
                progressBar.progress = 0
                startTimer (14)
                return
            }
        }
        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        var times = sharedPref.getInt("days", 0)
        if (times == 63){
            sharedPref.edit().putInt("days", 0).apply()
            times = sharedPref.getInt("days", 0)
        }
        val present = System.currentTimeMillis()
        if (present - sharedPref.getLong("lastBrush", 0) > periodBetweenBrush) {
            sharedPref.edit().putInt("days", ++times).apply()
            sharedPref.edit().putLong("lastBrush", System.currentTimeMillis()).apply()
        }
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