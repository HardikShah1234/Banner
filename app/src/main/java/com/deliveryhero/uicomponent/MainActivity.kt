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
/*
        val imageView = findViewById<ImageView>(R.id.display_pic)
        val textView = findViewById<TextView>(R.id.textview)
        val expandableTextView = findViewById<ExpandableTextView>(R.id.expand_tv)
        val informationText = findViewById<TextView>(R.id.info_text)
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)



        imageView.startAnimation(animation)
        expandableTextView.setInterpolator(OvershootInterpolator())
        expandableTextView.collapseInterpolator = OvershootInterpolator()

        setTextView(informationText, "Information")

        if (expandableTextView.maxLines > 2) {
            textView.visibility = View.VISIBLE
            textView.setOnClickListener {
                textView.text = if (expandableTextView.expanded()) "Show More" else "Show Less"
                expandableTextView.toggle()
            }

        }
    }

    private fun setTextView(textView: TextView, text: String) {
        textView.text = text
        val spannableString = SpannableString(text)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, spannableString.length, 0)
        textView.setText(spannableString)
        textView.setPadding(16, 0, 0, 0)
    }*/
    }
}