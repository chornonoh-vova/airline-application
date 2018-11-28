package com.airline.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Class, responsible for main page
 *
 * @see R.layout.activity_main - xml layout of page
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
