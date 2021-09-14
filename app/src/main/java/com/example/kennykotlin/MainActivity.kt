package com.example.kennykotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var imageList = ArrayList<ImageView>()
    private var scoreNumber = 0
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private val random = Random
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        hideImages()
        moveKenny()
        println("Handler worked")
        setTime()

    }

    private fun setTime() {
        object : CountDownTimer(16000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                time.setText("Time:${millisUntilFinished / 1000}")
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                time.text = "Time:0"
                hideImages()
                cancelRunnable()
                val dialog1 = AlertDialog.Builder(this@MainActivity)
                dialog1.setTitle("Do you want to start again?")
                dialog1.setPositiveButton("Yes") { dialog, which ->
                    val intent: Intent = intent
                    finish()
                    startActivity(intent)
                }
                dialog1.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_SHORT).show()
                }
                dialog1.show()
            }
        }.start()
    }

    private fun moveKenny() {
        runnable = object : Runnable {
            override fun run() {
                hideImages()
                randomImages()
                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)
    }

    @SuppressLint("SetTextI18n")
    fun clickImage(view: View) {
        scoreNumber++
        score.setText("Score-${scoreNumber}")
    }

    fun initialize() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable { }
        imageList.add(image1)
        imageList.add(image2)
        imageList.add(image3)
        imageList.add(image4)
        imageList.add(image5)
        imageList.add(image6)
        imageList.add(image7)
        imageList.add(image8)
        imageList.add(image9)
    }

    fun hideImages() {
        imageList.forEach {
            it.visibility = View.INVISIBLE
        }
    }
    fun randomImages() {
        val randomNumber = random.nextInt(9)
        imageList.get(randomNumber).visibility = View.VISIBLE
    }

    fun cancelRunnable() {
        handler.removeCallbacks(runnable)
    }
}