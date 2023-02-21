package com.csd3156.project1.game

import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.csd3156.project1.R
import com.csd3156.project1.databinding.ActivityGameBinding
import kotlinx.coroutines.*
import org.w3c.dom.Text

class GameActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityGameBinding
    private lateinit var job: Job

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    //val buttonTextX = findViewById<Button>(R.id.buttontestViewX)
    //val buttonTextY = findViewById<Button>(R.id.buttontestViewY)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        supportActionBar?.hide()

        findViewById<TextView>(R.id.idScoreTextView).text = "0"
        val timeTextView = findViewById<TextView>(R.id.idTimeTextView)
        val startTime = System.currentTimeMillis()

        job = CoroutineScope(Dispatchers.IO).launch {
            while (true)
            {
                while (Snake.alive)
                {
                    when (Snake.direction)
                    {
                        "up" -> {
                            Snake.headY -= 50
                            // Make head loop to the other side when it goes past
                            if (Snake.headY < 0f)
                                Snake.headY = 1000f
                        }
                        "down" -> {
                            Snake.headY += 50
                            if (Snake.headY > 1000f)
                                Snake.headY = 0f
                        }
                        "left" -> {
                            Snake.headX -= 50
                            if (Snake.headX < 0f)
                                Snake.headX = 1000f
                        }
                        "right" -> {
                            Snake.headX += 50
                            if (Snake.headX > 1000f)
                                Snake.headX = 0f
                        }
                    }

                    // kill snake if he bite himself
                    for(bodyPart in Snake.bodyParts)
                    {
                        if (Snake.headX == bodyPart[0] && Snake.headY == bodyPart[1])
                        {
                            Snake.alive = false
                            Snake.resetSnake()
                        }
                    }

                    // Convert the snake head to a body
                    Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))

                    //If apple is eaten, then we dont delete the tail, leaving the snake extended by 1
                    if (Snake.headX == Apple.posX && Snake.headY == Apple.posY)
                    {
                        withContext(Dispatchers.Main) {
                            val textView = findViewById<TextView>(R.id.idScoreTextView)
                            val currentValue = textView.text.toString().toInt()
                            val newValue = currentValue + 1
                            textView.text = newValue.toString()
                        }
                        Apple.generateAppleRandom()
                    }
                    else
                        Snake.bodyParts.removeAt(0)

                    // Game speed
                    findViewById<CanvasView>(R.id.gameCanvasID).invalidate()

                    //Get Time
                    withContext(Dispatchers.Main) {
                        val elapsedTime = System.currentTimeMillis() - startTime
                        val elapsedSeconds = elapsedTime / 1000
                        val formattedTime = String.format(
                            "%02d:%02d",
                            (elapsedSeconds % 3600) / 60,
                            elapsedSeconds % 60
                        )
                        timeTextView.text = formattedTime
                    }

                    delay(200)
                }
            }
        }

        //Tilt controls <Accelerometer>
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
                    val textViewtextX = findViewById<TextView>(R.id.textViewtiltvalX)
                    val textViewtextY = findViewById<TextView>(R.id.textViewtiltvalY)
                    textViewtextX.text =  x.toString()
                    textViewtextY.text =  y.toString()
                    if ( x < -5)
                    {
                        // device is tilted to the right
                        Snake.alive = true
                        if (Snake.direction != "left")
                            Snake.direction = "right"
                    } else if (x > 5)
                    {
                        // device is tilted to the left
                        Snake.alive = true
                        if (Snake.direction != "right")
                            Snake.direction = "left"
                    }
                    else if (y > 5)
                    {
                        // device is tilted to the bottom
                        Snake.alive = true
                        if(Snake.direction != "up")
                            Snake.direction = "down"
                    }
                    else if (y< -5)
                    {
                        // device is tilted to the top
                        Snake.alive = true
                        if(Snake.direction != "down")
                            Snake.direction = "up"
                    }
                    else {
                        // device is not tilted left or right
                        //Snake.alive =false
                    }

                }
            }
        }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy()
    {
        Snake.alive = false
        Snake.resetSnake()
        super.onDestroy()
        job.cancel()
    }

    override fun onPause()
    {
        Snake.alive = false
        super.onPause()
    }

    override fun onStop()
    {
        Snake.alive = false
        super.onStop()
    }


}