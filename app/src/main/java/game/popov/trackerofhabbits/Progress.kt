package game.popov.trackerofhabbits

import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class Progress : AppCompatActivity() {

    private var mMediaPlayer: MediaPlayer? = null
    private var maxPointCount = 70
    private var cardNumber = 0
    private var teethNumber = 0
    private val activeAnimal = arrayListOf(
        R.drawable.step_7,R.drawable.step_14,R.drawable.step_21,R.drawable.step_28,
        R.drawable.step_35, R.drawable.step_42,R.drawable.step_49,R.drawable.step_56,
        R.drawable.step_63,R.drawable.step_70
    )
    private val inactiveAnimal = arrayListOf(
        R.drawable.step_7_inactive,R.drawable.step_14_inactive,R.drawable.step_21_inactive,
        R.drawable.step_28_inactive, R.drawable.step_35_inactive, R.drawable.step_42_inactive,
        R.drawable.step_49_inactive,R.drawable.step_56_inactive, R.drawable.step_63_inactive,
        R.drawable.step_70_inactive
    )
    private val progress = arrayListOf(
        ItemsViewModel(
                R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
                R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
                R.drawable.step_7_inactive
            ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_14_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_21_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_28_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_35_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_42_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_49_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_56_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_63_inactive
        ),
        ItemsViewModel(
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
            R.drawable.step_70_inactive
        )
    )


    private fun getPoints(sharedPref: SharedPreferences): Int {
        return sharedPref.getInt("points", 0)
    }

    private fun getSound(sharedPref: SharedPreferences): Boolean {
        return sharedPref.getBoolean("sound", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val sharedPref: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val points = min(getPoints(sharedPref), maxPointCount)
        if (getSound(sharedPref)) {
            playSound()
        }

        cardNumber = if (points % 7 == 0) points / 7 - 1 else points / 7
        teethNumber = points % 7

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        for (card in 0..cardNumber) {
            progress[card] = ItemsViewModel(
                R.drawable.step, R.drawable.step, R.drawable.step, R.drawable.step, R.drawable.step,
                R.drawable.step, activeAnimal[card])
        }
        if (teethNumber != 0) {
            when (teethNumber) {
                1 -> progress[cardNumber] = ItemsViewModel(
                    R.drawable.step, R.drawable.step_inactive, R.drawable.step_inactive,
                    R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
                    inactiveAnimal[cardNumber]
                )

                2 -> progress[cardNumber] = ItemsViewModel(
                    R.drawable.step, R.drawable.step, R.drawable.step_inactive,
                    R.drawable.step_inactive, R.drawable.step_inactive, R.drawable.step_inactive,
                    inactiveAnimal[cardNumber]
                )

                3 -> progress[cardNumber] = ItemsViewModel(
                    R.drawable.step, R.drawable.step, R.drawable.step, R.drawable.step_inactive,
                    R.drawable.step_inactive, R.drawable.step_inactive, inactiveAnimal[cardNumber]
                )

                4 -> progress[cardNumber] = ItemsViewModel(
                    R.drawable.step, R.drawable.step, R.drawable.step, R.drawable.step,
                    R.drawable.step_inactive, R.drawable.step_inactive, inactiveAnimal[cardNumber]
                )

                5 -> progress[cardNumber] = ItemsViewModel(
                    R.drawable.step, R.drawable.step, R.drawable.step, R.drawable.step,
                    R.drawable.step, R.drawable.step_inactive, inactiveAnimal[cardNumber]
                )

                6 -> progress[cardNumber] = ItemsViewModel(
                    R.drawable.step, R.drawable.step, R.drawable.step, R.drawable.step,
                    R.drawable.step, R.drawable.step, inactiveAnimal[cardNumber]
                )
            }
        }

        // This will pass the ArrayList to our Adapter
        val adapter = ProgressAdapter(progress)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
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