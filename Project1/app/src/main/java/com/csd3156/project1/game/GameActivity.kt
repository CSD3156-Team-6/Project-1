package com.csd3156.project1.game

import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.csd3156.project1.R
import com.csd3156.project1.databinding.ActivityGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityGameBinding

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                while (Snake.alive) {
                    when (Snake.direction) {
                        "up" -> {
                            // create new head position
                            Snake.headY -= 50
                            if (!Snake.IsMovePossible()) {
                                Snake.alive = false
                                Snake.resetSnake()
                            }
                        }
                        "down" -> {
                            // create new head position
                            Snake.headY += 50
                            if (!Snake.IsMovePossible()) {
                                Snake.alive = false
                                Snake.resetSnake()
                            }
                        }
                        "left" -> {
                            // create new head position
                            Snake.headX -= 50
                            if (!Snake.IsMovePossible()) {
                                Snake.alive = false
                                Snake.resetSnake()
                            }

                        }
                        "right" -> {
                            // create new head position
                            Snake.headX += 50
                            if (!Snake.IsMovePossible()) {
                                Snake.alive = false
                                Snake.resetSnake()
                            }
                        }
                    }
                    // convert head to body
                    Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))

                    // delete tail if not eat
                    if (Snake.headX == Apple.posX && Snake.headY == Apple.posY)
                        Apple.generateAppleRandom()
                    else
                        Snake.bodyParts.removeAt(0)

                    //game speed in millisecond
                    findViewById<CanvasView>(R.id.gameCanvasID).invalidate()
                    delay(150)
                }
            }
        }

        // Up
        findViewById<Button>(R.id.buttonUpID).setOnClickListener {
            Snake.alive = true
            if(Snake.direction != "down")
                Snake.direction = "up"
        }

        //Down
        findViewById<Button>(R.id.buttonDownID).setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "up")
                Snake.direction = "down"
        }

        //Left
        findViewById<Button>(R.id.buttonLeftID).setOnClickListener{
            Snake.alive = true
            if (Snake.direction != "right")
                Snake.direction = "left"
        }

        //Right
        findViewById<Button>(R.id.buttonRightID).setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "left")
                Snake.direction = "right"
        }

        sensorManager.registerListener(object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // do nothing
            }

            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    // perform calculations with x, y, and z values to detect tilt

                    if (x < -2) {
                        // device is tilted to the right
                        Snake.alive = true
                        if (Snake.direction != "left")
                            Snake.direction = "right"
                    } else if (x > 2) {
                        // device is tilted to the left
                        Snake.alive = true
                        if (Snake.direction != "right")
                            Snake.direction = "left"
                    }
                    else if (y > 2) {
                        // device is tilted to the bottom
                        Snake.alive = true
                        if(Snake.direction != "up")
                            Snake.direction = "down"
                    }
                    else if (y< 2) {
                        // device is tilted to the top
                        Snake.alive = true
                        if(Snake.direction != "down")
                            Snake.direction = "up"
                    }else {
                        // device is not tilted left or right
                    }

                }
            }
        }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
}