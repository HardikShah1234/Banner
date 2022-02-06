package com.deliveryhero.uicomponent

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.deliveryhero.banners.CustomTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expandableTextView = findViewById<CustomTextView>(R.id.expand_tv)
        val textView = findViewById<TextView>(R.id.textview)

        textView.setOnClickListener {
            textView.text = if (expandableTextView.isExpanded) "Show More" else "Show Less"
            expandableTextView.toggle()
        }
    }
}