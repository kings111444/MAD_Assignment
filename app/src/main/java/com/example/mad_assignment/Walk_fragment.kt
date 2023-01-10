package com.example.mad_assignment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class Walk_fragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager?=null

    private var running = false
    private var totalSteps = 0f
    private var preTotalSteps = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_walk_fragment,container,false)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //from https://stackoverflow.com/questions/24427414/getsystemservices-is-undefined-when-called-in-a-fragment//

    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(activity,"Cannot detect sensor for this device.", Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }


    override fun onSensorChanged(event: SensorEvent?) {


        var tv_stepsTaken: TextView = view.findViewByID(R.id.tv_step) as TextView

        if (running) {
                totalSteps = event!!.values[0]


                // Current steps are calculated by taking the difference of total steps
                // and previous steps
                val currentSteps = totalSteps.toInt() - preTotalSteps.toInt()



                // It will show the current steps to the user
            tv_stepsTaken.text = "$currentSteps"
            }
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}

private fun View?.findViewByID(tvStep: Int) {

}


private fun LayoutInflater.inflate(fragmentWalkFragment: Int) {

}
