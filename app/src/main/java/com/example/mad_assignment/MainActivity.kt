package com.example.mad_assignment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mad_assignment.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var appDb: AppDatabase
    private var magnitudePreviousStep = 0.0
    private val previousTotalSteps = 0f
    private var totalSteps = 0f
    private var running: Boolean = false
    private var sensorManager: SensorManager? = null
    private val ACTIVITY_RECOGNITION_REQUEST_CODE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDb = AppDatabase.getDatabase(this)
        var Date = findViewById<TextView>(R.id.tv_date)
        Date.text = LocalDate.now().toString()


        binding.btnInsertData.setOnClickListener {
            writeData()
        }

        binding.btnViewData.setOnClickListener {
            readData()
        }

        if(isPermissionGranted()){
            requestPermission()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    private fun readData() {

        lateinit var stepcount : StepCount
        var step_text = binding.etStep.text
        var date_text = binding.tvDate.text

        GlobalScope.launch {
            Log.d("date?",date_text.toString())
            stepcount = appDb.stepcountDao().findbydate(date_text.toString())
            Log.d("Robin Data",stepcount.toString())
            binding.tvShowrecord.text = stepcount.stepcounted

        }


    }

    private fun writeData(){


        var step_text = binding.etStep.text
        var date_text = binding.tvDate.text



        if(step_text.isNotEmpty()) {
            lateinit var stepcount : StepCount
            Log.d("step?", step_text.toString())
            Log.d("date", date_text.toString())
            stepcount = appDb.stepcountDao().findbydate(date_text as String)

            val stepdata = StepCount(
                null,step_text.toString(), date_text.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {

                if(stepcount.toString() == (date_text.toString())){
                    appDb.stepcountDao().update(step_text.toString(), date_text.toString())
                }

                appDb.stepcountDao().insert(stepdata)
            }



            Toast.makeText(this@MainActivity,"Successfully written",Toast.LENGTH_SHORT).show()
        }else Toast.makeText(this@MainActivity,"Please Enter Data",Toast.LENGTH_SHORT).show()

    }


    override fun onSensorChanged(event: SensorEvent?) {
        var steptaken = findViewById<TextView>(R.id.etStep)

        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){ // For phone with accelermeter sensor
            val xaccel: Float = event.values[0]
            val yaccel: Float = event.values[1]
            val zaccel: Float = event.values[0]
            val magnitude: Double =
                kotlin.math.sqrt((xaccel * xaccel + yaccel * yaccel + zaccel * zaccel).toDouble())
            val magnitudeDelta: Double = magnitude - magnitudePreviousStep
            magnitudePreviousStep = magnitude

            if(magnitudeDelta > 6){
                totalSteps++
            }
            val step: Int = totalSteps.toInt()
            steptaken.text = step.toString()

        }else{
            if(running){
                totalSteps = event!!.values[0]
                val currentStep = totalSteps.toInt() - previousTotalSteps.toInt()
                steptaken.text = currentStep.toString()

            }
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        when{
            countSensor != null -> {
                sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI)
            }
            detectorSensor != null -> {
                sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_UI)
            }
            accelerometer != null -> {
                sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_UI)
            }
            else -> {
                Toast.makeText(this,"Your device is not compatible", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    private fun requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),ACTIVITY_RECOGNITION_REQUEST_CODE)
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            ACTIVITY_RECOGNITION_REQUEST_CODE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){

                }
            }
        }
    }
}

//From https://www.youtube.com/watch?v=sOGmivei73I