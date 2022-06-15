package com.example.trafficlight

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.trafficlight.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.schedule

import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding       //viewBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)       //viewBinder
        setContentView(binding.root)

        var click = -1
        val timerList = mutableListOf<Timer>()                  //list of Timer threads

        binding.nightMode.setOnClickListener {
            val nightTimer = Timer()
            if (timerList.isNotEmpty()) {                           //stop all Timer threads
                for (i in timerList) {
                    i.cancel()
                }
            }
            timerList.add(nightTimer)               //add new Timer to the list
            nightMode(nightTimer)
//            disableButton(binding.nightMode)
//            enableButton(binding.normalMode)
        }

        binding.normalMode.setOnClickListener {
            val normalTimer = Timer()
            if (timerList.isNotEmpty()) {                           //stop all Timer threads
                for (i in timerList) {
                    i.cancel()
                }
            }
            timerList.add(normalTimer)               //add new Timer to the list
            normalMode(normalTimer)
//            enableButton(binding.nightMode)
//            disableButton(binding.normalMode)
        }

        binding.buttonPower.setOnClickListener {
            click++
            if (timerList.isNotEmpty()) {                           //stop all Timer threads
                for (i in timerList) {
                    i.cancel()
                }
            }
            binding.redLight.setImageResource(R.drawable.light_off)
            binding.yellowLight.setImageResource(R.drawable.light_off)
            binding.greenLight.setImageResource(R.drawable.light_off)
            if (click % 2 == 0) {                                       //disable buttons
                disableButton(binding.nightMode)
                disableButton(binding.normalMode)
            } else {                                              //enable buttons
                enableButton(binding.normalMode)
                enableButton(binding.nightMode)
                binding.normalMode.callOnClick()            //start its Day cycle (normal mode)

            }
        }
    }

    private fun disableButton(button: ImageButton) {
        button.isEnabled = false
        button.isClickable = false
//        button.setImageResource(R.drawable.button_default_disabled)
//        button.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun enableButton(button: ImageButton) {
        button.isEnabled = true
        button.isClickable = true
//        button.setImageResource(R.drawable.button_default)
//        button.setImageResource(R.drawable.button_default)
//        button.setBackgroundColor(Color.parseColor("#616161"))
    }

    private fun normalMode(timer: Timer): Unit {

        timer.scheduleAtFixedRate(0, 17000) {

            binding.redLight.setImageResource(R.drawable.red_light)
            binding.yellowLight.setImageResource(R.drawable.light_off)
            binding.greenLight.setImageResource(R.drawable.light_off)

            timer.schedule(5000) {
                binding.yellowLight.setImageResource(R.drawable.yellow_light)
            }
            timer.schedule(7000) {
                binding.redLight.setImageResource(R.drawable.light_off)
                binding.yellowLight.setImageResource(R.drawable.light_off)
                binding.greenLight.setImageResource(R.drawable.green_light)
            }

            timer.schedule(12000) {
                binding.greenLight.setImageResource(R.drawable.light_off)
                binding.yellowLight.setImageResource(R.drawable.yellow_light)
            }
        }
    }

    private fun nightMode(timer: Timer): Unit {
        var i = 0;
        binding.redLight.setImageResource(R.drawable.light_off)
        binding.greenLight.setImageResource(R.drawable.light_off)

        timer.scheduleAtFixedRate(0, 1000) {
            if (i % 2 == 0) {
                binding.yellowLight.setImageResource(R.drawable.light_off)
            } else binding.yellowLight.setImageResource(R.drawable.yellow_light)
            i++;
        }
    }

}