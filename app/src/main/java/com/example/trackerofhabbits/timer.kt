package com.example.trackerofhabbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import java.util.*

enum class TaskState {WORK}



class timer : AppCompatActivity() {

    //lateinit var button: Button
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var progressBar: ProgressBar
    var currentCount = 0
    private val maxCount = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        initWidgets()
        startTimer(1000, 1000, 10)
        imageView.setImageResource(R.drawable.cat)

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
        var taskState = TaskState.WORK
        var taskProgress = 0
        var seconds = countSeconds

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                when (taskState) {
                    TaskState.WORK -> {
                        if (taskProgress < seconds) {
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
                                0 -> runOnUiThread {imageView.setImageResource(R.drawable.cat)}
                                1 -> runOnUiThread {imageView.setImageResource(R.drawable.cat2)}
                                2 -> runOnUiThread {imageView.setImageResource(R.drawable.cat)}
                                3 -> runOnUiThread {imageView.setImageResource(R.drawable.cat2)}
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
            when (currentCount) {
                1 -> {
                    startTimer(100, 90, 10)
                    return
                }
                2 -> {
                    startTimer(500, 450, 20)
                    return
                }
                3 -> {
                    startTimer(100, 90, 10)
                    return
                }
            }
        }
        finish()
    }
}