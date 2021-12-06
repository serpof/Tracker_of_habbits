package com.example.trackerofhabbits

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.util.rangeTo
import java.util.*

enum class TaskState {WORK}



class timer : AppCompatActivity() {

    //lateinit var button: Button
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var progressBar: ProgressBar
    var currentCount = 0
    private val maxCount = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        initWidgets()
        startTimer(1000, 1000, 10)
        imageView.setImageResource(R.drawable.apply_the_paste)

    }
    private fun initWidgets(){
        //button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
    }

   /* fun initClickListener() {

        button.setOnClickListener {
            startTimer()
        }
    }
*/
   private fun startTimer(delay: Long, period: Long, countSeconds: Int){
        val timer = Timer()
        val taskState = TaskState.WORK
        var taskProgress = 0
        var seconds = countSeconds

        timer.scheduleAtFixedRate(object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                when (taskState) {
                    TaskState.WORK -> {
                        if (taskProgress < countSeconds) {
                            ++taskProgress

                            val second = (seconds--).toString()
                            progressBar.progress = taskProgress
                            runOnUiThread {
                                textView.text = "Осталось секунд: ${second}"
                            }
                        } else {
                            runOnUiThread {
                                textView.text = "Осталось секунд: 0"
                                timer.cancel()
                            }
                            currentCount++
                            when (currentCount){
                                1 -> runOnUiThread {imageView.setImageResource(R.drawable.chewing_surface)}
                                2 -> runOnUiThread {imageView.setImageResource(R.drawable.top_outside_circle)}
                                3 -> runOnUiThread {imageView.setImageResource(R.drawable.top_outside)}
                                4 -> runOnUiThread {imageView.setImageResource(R.drawable.top_inside)}
                                5 -> runOnUiThread {imageView.setImageResource(R.drawable.top_inside)}
                                6 -> runOnUiThread {imageView.setImageResource(R.drawable.bottom)}
                                7 -> runOnUiThread {imageView.setImageResource(R.drawable.bottom)}
                                8 -> runOnUiThread {imageView.setImageResource(R.drawable.chewing_surface)}
                                9 -> runOnUiThread {imageView.setImageResource(R.drawable.tongue)}
                                10 -> runOnUiThread {imageView.setImageResource(R.drawable.rinse_your_mouth)}
                            }
                            startAgainIfNeed()
                        }
                    }

                }
            }
        }, delay, period)
    }

    private fun startAgainIfNeed() {
        if (currentCount < maxCount){
            if (currentCount in 6..10){
                startTimer(1000, 1000, 10)
                return
            }
            else{
                startTimer(1000, 1000, 20)
                return
            }
        }
        finish()
    }
}