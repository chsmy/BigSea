package com.chs.bigsea.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chs.bigsea.R
import io.flutter.embedding.android.FlutterFragment

class FlutterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter)
        val flutterFragment = FlutterFragment.createDefault()
        supportFragmentManager.beginTransaction().add(R.id.container,flutterFragment).commit()
    }
}