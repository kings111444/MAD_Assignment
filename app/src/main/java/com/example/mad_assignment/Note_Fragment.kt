package com.example.mad_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter


class Note_fragment : Fragment() {


override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_fragment, container, false)
    }
}
