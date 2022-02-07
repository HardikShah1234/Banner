package com.deliveryhero.uicomponent

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.deliveryhero.banners.CustomTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Banner Info
        val expandableTextView = text_layout.findViewById<CustomTextView>(R.id.custom_text_view_deal)
        val textView = findViewById<TextView>(R.id.textview)
        expandableTextView.setTextColor(Color.WHITE)
        expandableTextView.text = getString(com.deliveryhero.banners.R.string.Text)

        textView.setOnClickListener {
            textView.text = if (expandableTextView.isExpanded) getBoldString("Show More") else getBoldString("Show Less")
            expandableTextView.toggle()
        }

        //Banner deal
        val customTextViewDeal = text_layout_deal.findViewById<CustomTextView>(R.id.custom_text_view_deal)
        val textViewDeal = text_layout_deal.findViewById<TextView>(R.id.textview_deal)
        val imageView = text_layout_deal.findViewById<ImageView>(R.id.display_pic_deal)
        customTextViewDeal.text = getString(R.string.Text)


        textViewDeal.setOnClickListener {
            textViewDeal.text = if (customTextViewDeal.isExpanded) getBoldString("Show More") else getBoldString("Show Less")
            customTextViewDeal.toggle()
        }

        if (customTextViewDeal.lineCount == 1) {
            imageView.visibility = View.GONE
            textViewDeal.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            textViewDeal.visibility = View.VISIBLE

        }
    }

    private fun getBoldString(show: String) : SpannableString {
        val spannableString: SpannableString = SpannableString(show)
        spannableString.setSpan(StyleSpan(Typeface.BOLD),0,spannableString.length,0)
        return spannableString
    }
}